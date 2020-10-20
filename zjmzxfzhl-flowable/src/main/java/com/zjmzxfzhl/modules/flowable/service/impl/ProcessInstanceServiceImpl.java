package com.zjmzxfzhl.modules.flowable.service.impl;

import com.zjmzxfzhl.common.core.security.SecurityUser;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.common.core.util.ObjectUtils;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.flowable.common.CommentTypeEnum;
import com.zjmzxfzhl.modules.flowable.common.ResponseFactory;
import com.zjmzxfzhl.modules.flowable.common.cmd.AddCcIdentityLinkCmd;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.mapper.FlowableCommonMapper;
import com.zjmzxfzhl.modules.flowable.service.ProcessInstanceService;
import com.zjmzxfzhl.modules.flowable.vo.CategoryVo;
import com.zjmzxfzhl.modules.flowable.vo.ProcessDefinitionVo;
import com.zjmzxfzhl.modules.flowable.vo.ProcessInstanceRequest;
import com.zjmzxfzhl.modules.flowable.vo.query.ProcessInstanceQueryVo;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {
    @Autowired
    protected ResponseFactory responseFactory;
    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected PermissionServiceImpl permissionService;
    @Autowired
    protected FlowableTaskServiceImpl flowableTaskService;
    @Autowired
    protected TaskService taskService;
    @Resource
    private FlowableCommonMapper flowableCommonMapper;

    @Override
    public ProcessInstance getProcessInstanceById(String processInstanceId) {
        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            throw new FlowableObjectNotFoundException("No process instance found with id " + processInstanceId);
        }
        return processInstance;
    }

    @Override
    public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance == null) {
            throw new FlowableObjectNotFoundException("No process instance found with id " + processInstanceId);
        }
        return historicProcessInstance;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void start(ProcessInstanceRequest processInstanceRequest) {
        String processDefinitionId = CommonUtil.trimToEmptyStr(processInstanceRequest.getProcessDefinitionId());
        String processDefinitionKey = CommonUtil.trimToEmptyStr(processInstanceRequest.getProcessDefinitionKey());
        if (processDefinitionId.length() == 0 && processDefinitionKey.length() == 0) {
            throw new FlowableException("request param both processDefinitionId and processDefinitionKey is not found");
        } else if (processDefinitionId.length() != 0 && processDefinitionKey.length() != 0) {
            throw new FlowableException("request param both processDefinitionId and processDefinitionKey is found");
        }
        SecurityUser user = (SecurityUser) SecurityUtils.getUserDetails();
        String userId = user.getUsername();

        ProcessDefinition definition = permissionService.validateReadPermissionOnProcessDefinition(userId,
                processDefinitionId, processDefinitionKey, processInstanceRequest.getTenantId());
        Map<String, Object> startVariables = null;
        if (processInstanceRequest.getValues() != null && !processInstanceRequest.getValues().isEmpty()) {
            startVariables = processInstanceRequest.getValues();
        }

        Authentication.setAuthenticatedUserId(userId);

        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
        processInstanceBuilder.processDefinitionId(definition.getId());
        // 流程实例标题
        processInstanceBuilder.name(user.getUserRealName() + definition.getName());
        // 业务key
        processInstanceBuilder.businessKey(processInstanceRequest.getBusinessKey());
        processInstanceBuilder.variables(startVariables);

        ProcessInstance instance = processInstanceBuilder.start();
        String processInstanceId = instance.getProcessInstanceId();
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        for (Task task : tasks) {
            // 约定：发起者节点为 __initiator__ ,则自动完成任务
            if (FlowableConstant.INITIATOR.equals(task.getTaskDefinitionKey())) {
                flowableTaskService.addComment(task.getId(), processInstanceId, userId, CommentTypeEnum.TJ, null);
                if (ObjectUtils.isEmpty(task.getAssignee())) {
                    taskService.setAssignee(task.getId(), userId);
                }
                taskService.complete(task.getId());
                if (CommonUtil.isNotEmptyObject(processInstanceRequest.getCcToVos())) {
                    managementService.executeCommand(new AddCcIdentityLinkCmd(processInstanceId, task.getId(), userId
                            , processInstanceRequest.getCcToVos()));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activate(String processInstanceId) {
        ProcessInstance processInstance = getProcessInstanceById(processInstanceId);
        if (!processInstance.isSuspended()) {
            throw new FlowableException("Process instance is not suspended with id " + processInstanceId);
        }
        runtimeService.activateProcessInstanceById(processInstance.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspend(String processInstanceId) {
        ProcessInstance processInstance = getProcessInstanceById(processInstanceId);
        if (processInstance.isSuspended()) {
            throw new FlowableException("Process instance is already suspended with id {0}" + processInstanceId);
        }
        runtimeService.suspendProcessInstanceById(processInstance.getId());
    }

    @Override
    public List listMyInvolvedSummary(ProcessInstanceQueryVo processInstanceQueryVo){
        List<ProcessDefinitionVo> vos = flowableCommonMapper.listMyInvolvedSummary(processInstanceQueryVo);
        List<CategoryVo> result = new ArrayList<>();
        Map<String, List<ProcessDefinitionVo>> categorysByParent = new HashMap<>();
        for (ProcessDefinitionVo vo : vos) {
            List<ProcessDefinitionVo> childs = categorysByParent.computeIfAbsent(vo.getCategory(), k -> new ArrayList<>());
            childs.add(vo);
        }
        for (Map.Entry<String, List<ProcessDefinitionVo>> entry : categorysByParent.entrySet()){
            CategoryVo aCategoryVo = new CategoryVo();
            aCategoryVo.setCategory(entry.getKey());
            aCategoryVo.setProcessDefinitionVoList(entry.getValue());
            String categoryName = entry.getValue().iterator().next().getCategoryName();
            aCategoryVo.setCategoryName(categoryName);
            result.add(aCategoryVo);
        }
        return result;
    }
}
