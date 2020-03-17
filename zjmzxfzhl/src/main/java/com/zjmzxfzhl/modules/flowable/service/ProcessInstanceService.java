package com.zjmzxfzhl.modules.flowable.service;

import java.util.List;
import java.util.Map;

import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.ObjectUtils;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.flowable.common.CommentTypeEnum;
import com.zjmzxfzhl.modules.flowable.common.ResponseFactory;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.vo.ProcessInstanceRequest;
import com.zjmzxfzhl.modules.sys.entity.SysUser;

@Service
public class ProcessInstanceService {
	@Autowired
	protected ResponseFactory responseFactory;
	@Autowired
	protected ManagementService managementService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected PermissionService permissionService;
	@Autowired
	protected FlowableTaskService flowableTaskService;
	@Autowired
	protected TaskService taskService;

	public ProcessInstance getProcessInstanceById(String processInstanceId) {
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		if (processInstance == null) {
			throw new FlowableObjectNotFoundException("No process instance found with id " + processInstanceId);
		}
		return processInstance;
	}

	public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		if (historicProcessInstance == null) {
			throw new FlowableObjectNotFoundException("No process instance found with id " + processInstanceId);
		}
		return historicProcessInstance;
	}

	@Transactional
	public R start(ProcessInstanceRequest processInstanceRequest) {
		String processDefinitionId = CommonUtil.trimToEmptyStr(processInstanceRequest.getProcessDefinitionId());
		String processDefinitionKey = CommonUtil.trimToEmptyStr(processInstanceRequest.getProcessDefinitionKey());
		if (processDefinitionId.length() == 0 && processDefinitionKey.length() == 0) {
			throw new FlowableException("request param both processDefinitionId and processDefinitionKey is not found");
		} else if (processDefinitionId.length() != 0 && processDefinitionKey.length() != 0) {
			throw new FlowableException("request param both processDefinitionId and processDefinitionKey is found");
		}
		SysUser user = ShiroUtils.getSysUser();
		String userId = user.getUserId();

		ProcessDefinition definition = permissionService.validateReadPermissionOnProcessDefinition(userId, processDefinitionId, processDefinitionKey,
				processInstanceRequest.getTenantId());
		Map<String, Object> startVariables = null;
		if (processInstanceRequest.getValues() != null && !processInstanceRequest.getValues().isEmpty()) {
			startVariables = processInstanceRequest.getValues();
		}

		Authentication.setAuthenticatedUserId(userId);

		ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
		processInstanceBuilder.processDefinitionId(definition.getId());
		processInstanceBuilder.name(user.getUserName() + definition.getName()); // 流程实例标题
		processInstanceBuilder.businessKey(processInstanceRequest.getBusinessKey()); // 业务key
		processInstanceBuilder.variables(startVariables);

		ProcessInstance instance = processInstanceBuilder.start();
		String processInstanceId = instance.getProcessInstanceId();
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
		for (Task task : tasks) {
			if (FlowableConstant.__INITIATOR__.equals(task.getTaskDefinitionKey())) {// 约定：发起者节点为 __initiator__ ,则自动完成任务
				flowableTaskService.addComment(task.getId(), processInstanceId, userId, CommentTypeEnum.TJ, null);
				if (ObjectUtils.isEmpty(task.getAssignee())) {
					taskService.setAssignee(task.getId(), userId);
				}
				taskService.complete(task.getId());
			}
		}
		return R.ok();
	}

	@Transactional
	public void delete(String processInstanceId, boolean cascade, String deleteReason) {
		HistoricProcessInstance historicProcessInstance = getHistoricProcessInstanceById(processInstanceId);
		if (historicProcessInstance.getEndTime() != null) {
			historyService.deleteHistoricProcessInstance(historicProcessInstance.getId());
			return;
		}
		ExecutionEntity executionEntity = (ExecutionEntity) getProcessInstanceById(processInstanceId);
		if (CommonUtil.isNotEmptyAfterTrim(executionEntity.getSuperExecutionId())) {
			throw new FlowableException("This is a subprocess");
		}
		runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
		if (cascade) {
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
	}

	@Transactional
	public void activate(String processInstanceId) {
		ProcessInstance processInstance = getProcessInstanceById(processInstanceId);
		if (!processInstance.isSuspended()) {
			throw new FlowableException("Process instance is not suspended with id " + processInstanceId);
		}
		runtimeService.activateProcessInstanceById(processInstance.getId());
	}

	@Transactional
	public void suspend(String processInstanceId) {
		ProcessInstance processInstance = getProcessInstanceById(processInstanceId);
		if (processInstance.isSuspended()) {
			throw new FlowableException("Process instance is already suspended with id {0}" + processInstanceId);
		}
		runtimeService.suspendProcessInstanceById(processInstance.getId());
	}
}
