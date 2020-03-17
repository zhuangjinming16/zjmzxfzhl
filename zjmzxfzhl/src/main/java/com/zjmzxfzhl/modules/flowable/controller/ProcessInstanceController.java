package com.zjmzxfzhl.modules.flowable.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.history.HistoricProcessInstanceQuery;
import org.flowable.engine.impl.HistoricProcessInstanceQueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.task.Comment;
import org.flowable.variable.api.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.ObjectUtils;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage;
import com.zjmzxfzhl.modules.flowable.service.ProcessInstanceService;
import com.zjmzxfzhl.modules.flowable.vo.ProcessInstanceDetailResponse;
import com.zjmzxfzhl.modules.flowable.vo.ProcessInstanceRequest;
import com.zjmzxfzhl.modules.flowable.wapper.CommentListWrapper;
import com.zjmzxfzhl.modules.flowable.wapper.ProcInsListWrapper;

@RestController
@RequestMapping("/flowable/processInstance")
public class ProcessInstanceController extends BaseFlowableController {

	private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

	@Autowired
	private ProcessInstanceService processInstanceService;

	static {
		allowedSortProperties.put("id", HistoricProcessInstanceQueryProperty.PROCESS_INSTANCE_ID_);
		allowedSortProperties.put("processDefinitionId", HistoricProcessInstanceQueryProperty.PROCESS_DEFINITION_ID);
		allowedSortProperties.put("processDefinitionKey", HistoricProcessInstanceQueryProperty.PROCESS_DEFINITION_KEY);
		allowedSortProperties.put("businessKey", HistoricProcessInstanceQueryProperty.BUSINESS_KEY);
		allowedSortProperties.put("startTime", HistoricProcessInstanceQueryProperty.START_TIME);
		allowedSortProperties.put("endTime", HistoricProcessInstanceQueryProperty.END_TIME);
		allowedSortProperties.put("duration", HistoricProcessInstanceQueryProperty.DURATION);
		allowedSortProperties.put("tenantId", HistoricProcessInstanceQueryProperty.TENANT_ID);
	}

	@RequiresPermissions("flowable:processInstance:list")
	@GetMapping(value = "/list")
	public R list(@RequestParam Map<String, String> requestParams) {
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery();

		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("processInstanceId"))) {
			query.processInstanceId(requestParams.get("processInstanceId"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("processInstanceName"))) {
			query.processInstanceNameLike(requestParams.get("processInstanceName"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("processDefinitionName"))) {
			query.processDefinitionName(requestParams.get("processDefinitionName"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("processDefinitionKey"))) {
			query.processDefinitionKey(requestParams.get("processDefinitionKey"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("processDefinitionId"))) {
			query.processDefinitionId(requestParams.get("processDefinitionId"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("businessKey"))) {
			query.processInstanceBusinessKey(requestParams.get("businessKey"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("involvedUser"))) {
			query.involvedUser(requestParams.get("involvedUser"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("finished"))) {
			boolean isFinished = ObjectUtils.convertToBoolean(requestParams.get("finished"));
			if (isFinished) {
				query.finished();
			} else {
				query.unfinished();
			}
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("superProcessInstanceId"))) {
			query.superProcessInstanceId(requestParams.get("superProcessInstanceId"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("excludeSubprocesses"))) {
			query.excludeSubprocesses(ObjectUtils.convertToBoolean(requestParams.get("excludeSubprocesses")));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("finishedAfter"))) {
			query.finishedAfter(ObjectUtils.convertToDatetime(requestParams.get("finishedAfter")));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("finishedBefore"))) {
			query.finishedBefore(ObjectUtils.convertToDatetime(requestParams.get("finishedBefore")));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("startedAfter"))) {
			query.startedAfter(ObjectUtils.convertToDatetime(requestParams.get("startedAfter")));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("startedBefore"))) {
			query.startedBefore(ObjectUtils.convertToDatetime(requestParams.get("startedBefore")));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("startedBy"))) {
			query.startedBy(requestParams.get("startedBy"));
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("startByMe"))) {// startByMe 覆盖 startedBy
			boolean isStartByMe = ObjectUtils.convertToBoolean(requestParams.get("startByMe"));
			if (isStartByMe) {
				query.startedBy(ShiroUtils.getUserId());
			}
		}
		if (CommonUtil.isNotEmptyAfterTrim(requestParams.get("tenantId"))) {
			query.processInstanceTenantIdLike(requestParams.get("tenantId"));
		}

		FlowablePage page = this.pageList(requestParams, query, ProcInsListWrapper.class, allowedSortProperties);
		return R.ok(page);
	}

	@RequiresPermissions("flowable:processInstance:listMyInvolved")
	@GetMapping(value = "/listMyInvolved")
	public R listMyInvolved(@RequestParam Map<String, String> requestParams) {
		requestParams.put("involvedUser", ShiroUtils.getUserId());
		return list(requestParams);
	}

	@RequiresPermissions(value = { "flowable:processInstance:list", "flowable:processInstance:listMyInvolved" }, logical = Logical.OR)
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam String processInstanceId) {
		permissionService.validateReadPermissionOnProcessInstance(ShiroUtils.getUserId(), processInstanceId);
		ProcessInstance processInstance = null;
		HistoricProcessInstance historicProcessInstance = processInstanceService.getHistoricProcessInstanceById(processInstanceId);
		if (historicProcessInstance.getEndTime() == null) {
			processInstance = processInstanceService.getProcessInstanceById(processInstanceId);
		}
		ProcessInstanceDetailResponse pidr = responseFactory.createProcessInstanceDetailResponse(historicProcessInstance, processInstance);
		return R.ok(pidr);
	}

	@SysLogAuto(value = "启动流程实例")
	@PostMapping(value = "/start")
	@Transactional
	public R start(@RequestBody ProcessInstanceRequest processInstanceRequest) {
		processInstanceService.start(processInstanceRequest);
		return R.ok();
	}

	@SysLogAuto(value = "删除流程实例")
	@RequiresPermissions("flowable:processInstance:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam String processInstanceId, @RequestParam(required = false) boolean cascade,
			@RequestParam(required = false) String deleteReason) {
		processInstanceService.delete(processInstanceId, cascade, deleteReason);
		return R.ok();
	}

	@SysLogAuto(value = "挂起流程实例")
	@RequiresPermissions("flowable:processInstance:suspendOrActivate")
	@PutMapping(value = "/suspend")
	public R suspend(@RequestBody ProcessInstanceRequest processInstanceRequest) {
		processInstanceService.suspend(processInstanceRequest.getProcessInstanceId());
		return R.ok();
	}

	@SysLogAuto(value = "激活流程实例")
	@RequiresPermissions("flowable:processInstance:suspendOrActivate")
	@PutMapping(value = "/activate")
	public R activate(@RequestBody ProcessInstanceRequest processInstanceRequest) {
		processInstanceService.activate(processInstanceRequest.getProcessInstanceId());
		return R.ok();
	}

	@GetMapping(value = "/comments")
	public R comments(@RequestParam String processInstanceId) {
		permissionService.validateReadPermissionOnProcessInstance(ShiroUtils.getUserId(), processInstanceId);
		List<Comment> datas = taskService.getProcessInstanceComments(processInstanceId);
		Collections.reverse(datas);
		return R.ok(this.listWrapper(CommentListWrapper.class, datas));
	}

	@GetMapping(value = "/formData")
	public R formData(@RequestParam String processInstanceId) {
		HistoricProcessInstance processInstance = permissionService.validateReadPermissionOnProcessInstance(ShiroUtils.getUserId(),
				processInstanceId);
		Object renderedStartForm = formService.getRenderedStartForm(processInstance.getProcessDefinitionId());
		Map<String, Object> variables = null;
		if (processInstance.getEndTime() == null) {
			variables = runtimeService.getVariables(processInstanceId);
		} else {
			List<HistoricVariableInstance> hisVals = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
			variables = new HashMap<>();
			for (HistoricVariableInstance variableInstance : hisVals) {
				variables.put(variableInstance.getVariableName(), variableInstance.getValue());
			}
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		boolean showBusinessKey = isShowBusinessKey(processInstance.getProcessDefinitionId());
		ret.put("showBusinessKey", showBusinessKey);
		ret.put("businessKey", processInstance.getBusinessKey());
		ret.put("renderedStartForm", renderedStartForm);
		ret.put("variables", variables);
		return R.ok(ret);
	}
}
