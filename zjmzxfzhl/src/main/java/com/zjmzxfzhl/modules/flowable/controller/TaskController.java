package com.zjmzxfzhl.modules.flowable.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.HistoricTaskInstanceQueryProperty;
import org.flowable.task.service.impl.TaskQueryProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.util.ObjectUtils;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.service.FlowableTaskService;
import com.zjmzxfzhl.modules.flowable.vo.FlowNodeResponse;
import com.zjmzxfzhl.modules.flowable.vo.TaskRequest;
import com.zjmzxfzhl.modules.flowable.vo.TaskResponse;
import com.zjmzxfzhl.modules.flowable.vo.TaskUpdateRequest;
import com.zjmzxfzhl.modules.flowable.wapper.TaskListWrapper;
import com.zjmzxfzhl.modules.flowable.wapper.TaskTodoListWrapper;

@RestController
@RequestMapping("/flowable/task")
public class TaskController extends BaseFlowableController {
	@Autowired
	protected FlowableTaskService flowableTaskService;

	private static Map<String, QueryProperty> allowedSortProperties = new HashMap<>();
	private static Map<String, QueryProperty> allowedSortPropertiesTodo = new HashMap<>();

	static {
		allowedSortProperties.put("deleteReason", HistoricTaskInstanceQueryProperty.DELETE_REASON);
		allowedSortProperties.put("duration", HistoricTaskInstanceQueryProperty.DURATION);
		allowedSortProperties.put("endTime", HistoricTaskInstanceQueryProperty.END);
		allowedSortProperties.put("executionId", HistoricTaskInstanceQueryProperty.EXECUTION_ID);
		allowedSortProperties.put("taskInstanceId", HistoricTaskInstanceQueryProperty.HISTORIC_TASK_INSTANCE_ID);
		allowedSortProperties.put("processDefinitionId", HistoricTaskInstanceQueryProperty.PROCESS_DEFINITION_ID);
		allowedSortProperties.put("processInstanceId", HistoricTaskInstanceQueryProperty.PROCESS_INSTANCE_ID);
		allowedSortProperties.put("assignee", HistoricTaskInstanceQueryProperty.TASK_ASSIGNEE);
		allowedSortProperties.put("taskDefinitionKey", HistoricTaskInstanceQueryProperty.TASK_DEFINITION_KEY);
		allowedSortProperties.put("description", HistoricTaskInstanceQueryProperty.TASK_DESCRIPTION);
		allowedSortProperties.put("dueDate", HistoricTaskInstanceQueryProperty.TASK_DUE_DATE);
		allowedSortProperties.put("name", HistoricTaskInstanceQueryProperty.TASK_NAME);
		allowedSortProperties.put("owner", HistoricTaskInstanceQueryProperty.TASK_OWNER);
		allowedSortProperties.put("priority", HistoricTaskInstanceQueryProperty.TASK_PRIORITY);
		allowedSortProperties.put("tenantId", HistoricTaskInstanceQueryProperty.TENANT_ID_);
		allowedSortProperties.put("startTime", HistoricTaskInstanceQueryProperty.START);

		allowedSortPropertiesTodo.put("processDefinitionId", TaskQueryProperty.PROCESS_DEFINITION_ID);
		allowedSortPropertiesTodo.put("processInstanceId", TaskQueryProperty.PROCESS_INSTANCE_ID);
		allowedSortPropertiesTodo.put("taskDefinitionKey", TaskQueryProperty.TASK_DEFINITION_KEY);
		allowedSortPropertiesTodo.put("dueDate", TaskQueryProperty.DUE_DATE);
		allowedSortPropertiesTodo.put("name", TaskQueryProperty.NAME);
		allowedSortPropertiesTodo.put("priority", TaskQueryProperty.PRIORITY);
		allowedSortPropertiesTodo.put("tenantId", TaskQueryProperty.TENANT_ID);
		allowedSortPropertiesTodo.put("createTime", TaskQueryProperty.CREATE_TIME);
	}

	protected HistoricTaskInstanceQuery createHistoricTaskInstanceQuery(Map<String, String> requestParams) {
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		if (ObjectUtils.isNotEmpty(requestParams.get("taskId"))) {
			query.taskId(requestParams.get("taskId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processInstanceId"))) {
			query.processInstanceId(requestParams.get("processInstanceId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processInstanceBusinessKey"))) {
			query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(requestParams.get("processInstanceBusinessKey")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionKey"))) {
			query.processDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get("processDefinitionKey")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionId"))) {
			query.processDefinitionId(requestParams.get("processDefinitionId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionName"))) {
			query.processDefinitionNameLike(ObjectUtils.convertToLike(requestParams.get("processDefinitionName")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("executionId"))) {
			query.executionId(requestParams.get("executionId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskName"))) {
			query.taskNameLike(ObjectUtils.convertToLike(requestParams.get("taskName")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskDescription"))) {
			query.taskDescriptionLike(ObjectUtils.convertToLike(requestParams.get("taskDescription")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskDefinitionKey"))) {
			query.taskDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get("taskDefinitionKey")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskAssignee"))) {
			query.taskAssignee(requestParams.get("taskAssignee"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskOwner"))) {
			query.taskOwner(requestParams.get("taskOwner"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskInvolvedUser"))) {
			query.taskInvolvedUser(requestParams.get("taskInvolvedUser"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskPriority"))) {
			query.taskPriority(ObjectUtils.convertToInteger(requestParams.get("taskPriority")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("finished"))) {
			boolean isFinished = ObjectUtils.convertToBoolean(requestParams.get("finished"));
			if (isFinished) {
				query.finished();
			} else {
				query.unfinished();
			}
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processFinished"))) {
			boolean isProcessFinished = ObjectUtils.convertToBoolean(requestParams.get("processFinished"));
			if (isProcessFinished) {
				query.processFinished();
			} else {
				query.processUnfinished();
			}
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("parentTaskId"))) {
			query.taskParentTaskId(requestParams.get("parentTaskId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("dueDateAfter"))) {
			query.taskDueAfter(ObjectUtils.convertToDate(requestParams.get("dueDateAfter")));
		}

		if (ObjectUtils.isNotEmpty(requestParams.get("dueDateBefore"))) {
			query.taskDueBefore(ObjectUtils.convertToDate(requestParams.get("dueDateBefore")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCreatedBefore"))) {
			query.taskCreatedBefore(ObjectUtils.convertToDatetime(requestParams.get("taskCreatedBefore")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCreatedAfter"))) {
			query.taskCreatedAfter(ObjectUtils.convertToDatetime(requestParams.get("taskCreatedAfter")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCompletedBefore"))) {
			query.taskCompletedBefore(ObjectUtils.convertToDatetime(requestParams.get("taskCompletedBefore")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCompletedAfter"))) {
			query.taskCompletedAfter(ObjectUtils.convertToDatetime(requestParams.get("taskCompletedAfter")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("tenantId"))) {
			query.taskTenantId(requestParams.get("tenantId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCandidateUser"))) {
			query.taskCandidateUser(requestParams.get("taskCandidateUser"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCandidateGroup"))) {
			query.taskCandidateGroup(requestParams.get("taskCandidateGroup"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCandidateGroups"))) {
			String[] candidateGroups = requestParams.get("taskCandidateGroups").split(",");
			query.taskCandidateGroupIn(Arrays.asList(candidateGroups));
		}
		return query;
	}

	protected TaskQuery createTaskQuery(Map<String, String> requestParams) {
		TaskQuery query = taskService.createTaskQuery();
		if (ObjectUtils.isNotEmpty(requestParams.get("processInstanceId"))) {
			query.processInstanceId(requestParams.get("processInstanceId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskName"))) {
			query.taskNameLike(requestParams.get("taskName"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processInstanceBusinessKey"))) {
			query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(requestParams.get("processInstanceBusinessKey")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionKey"))) {
			query.processDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get("processDefinitionKey")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionId"))) {
			query.processDefinitionId(requestParams.get("processDefinitionId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("processDefinitionName"))) {
			query.processDefinitionNameLike(ObjectUtils.convertToLike(requestParams.get("processDefinitionName")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("dueDateAfter"))) {
			query.taskDueAfter(ObjectUtils.convertToDate(requestParams.get("dueDateAfter")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("dueDateBefore"))) {
			query.taskDueBefore(ObjectUtils.convertToDate(requestParams.get("dueDateBefore")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCreatedBefore"))) {
			query.taskCreatedBefore(ObjectUtils.convertToDatetime(requestParams.get("taskCreatedBefore")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("taskCreatedAfter"))) {
			query.taskCreatedAfter(ObjectUtils.convertToDatetime(requestParams.get("taskCreatedAfter")));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("tenantId"))) {
			query.taskTenantId(requestParams.get("tenantId"));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get("suspended"))) {
			boolean isSuspended = ObjectUtils.convertToBoolean(requestParams.get("suspended"));
			if (isSuspended) {
				query.suspended();
			} else {
				query.active();
			}
		}
		return query;
	}

	@RequiresPermissions("flowable:task:list")
	@GetMapping(value = "/list")
	public R list(@RequestParam Map<String, String> requestParams) {
		HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(requestParams);
		FlowablePage page = this.pageList(requestParams, query, TaskListWrapper.class, allowedSortProperties,
				HistoricTaskInstanceQueryProperty.START);
		return R.ok(page);
	}

	@RequiresPermissions("flowable:task:listDone")
	@GetMapping(value = "/listDone")
	public R listDone(@RequestParam Map<String, String> requestParams) {
		HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(requestParams);
		query.finished().or().taskAssignee(ShiroUtils.getUserId()).taskOwner(ShiroUtils.getUserId()).endOr();
		FlowablePage page = this.pageList(requestParams, query, TaskListWrapper.class, allowedSortProperties,
				HistoricTaskInstanceQueryProperty.START);
		return R.ok(page);
	}

	@RequiresPermissions("flowable:task:listTodo")
	@GetMapping(value = "/listTodo")
	public R listTodo(@RequestParam Map<String, String> requestParams) {
		String userId = ShiroUtils.getUserId();
		TaskQuery query = createTaskQuery(requestParams);
		query.or().taskCandidateOrAssigned(userId).taskOwner(userId).endOr();
		// query.taskCandidateOrAssigned(ShiroUtils.getUserId());
		FlowablePage page = this.pageList(requestParams, query, TaskTodoListWrapper.class, allowedSortProperties, TaskQueryProperty.CREATE_TIME);
		return R.ok(page);
	}

	@RequiresPermissions(value = { "flowable:task:list", "flowable:task:listTodo", "flowable:task:listDone" }, logical = Logical.OR)
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam String taskId) {
		TaskResponse task = flowableTaskService.getTask(taskId);
		return R.ok(task);
	}

	@SysLogAuto(value = "修改任务")
	@RequiresPermissions("flowable:task:update")
	@PutMapping(value = "/update")
	public R update(@RequestBody TaskUpdateRequest taskUpdateRequest) {
		TaskResponse task = flowableTaskService.updateTask(taskUpdateRequest);
		return R.ok(task);
	}

	@SysLogAuto(value = "删除任务")
	@RequiresPermissions("flowable:task:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam String taskId) {
		flowableTaskService.deleteTask(taskId);
		return R.ok();
	}

	@SysLogAuto(value = "转办任务")
	@PutMapping(value = "/assign")
	public R assign(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.assignTask(taskRequest);
		return R.ok();
	}

	@SysLogAuto(value = "委派任务")
	@PutMapping(value = "/delegate")
	public R delegate(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.delegateTask(taskRequest);
		return R.ok();
	}

	@SysLogAuto(value = "认领任务")
	@PutMapping(value = "/claim")
	public R claim(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.claimTask(taskRequest);
		return R.ok();
	}

	@SysLogAuto(value = "取消认领任务")
	@PutMapping(value = "/unclaim")
	public R unclaim(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.unclaimTask(taskRequest);
		return R.ok();
	}

	@SysLogAuto(value = "完成任务")
	@PutMapping(value = "/complete")
	public R complete(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.completeTask(taskRequest);
		return R.ok();
	}

	@SysLogAuto(value = "结束流程实例")
	@PutMapping(value = "/stopProcessInstance")
	public R stopProcessInstance(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.stopProcessInstance(taskRequest);
		return R.ok();
	}

	@GetMapping(value = "/renderedTaskForm")
	public R renderedTaskForm(@RequestParam String taskId) {
		permissionService.validateReadPermissionOnTask2(taskId, ShiroUtils.getUserId(), true, true);
		Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
		return R.ok(renderedTaskForm);
	}

	@GetMapping(value = "/executeTaskData")
	public R executeTaskData(@RequestParam String taskId) {
		Task task = permissionService.validateReadPermissionOnTask2(taskId, ShiroUtils.getUserId(), true, true);
		Object renderedStartForm = formService.getRenderedStartForm(task.getProcessDefinitionId());
		Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
		Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("startUserId", processInstance.getStartUserId());
		ret.put("renderedStartForm", renderedStartForm);
		ret.put("renderedTaskForm", renderedTaskForm);
		ret.put("variables", variables);
		boolean showBusinessKey = isShowBusinessKey(task.getProcessDefinitionId());
		ret.put("showBusinessKey", showBusinessKey);
		ret.put("businessKey", processInstance.getBusinessKey());
		if (FlowableConstant.__INITIATOR__.equals(task.getTaskDefinitionKey())) {// 当前任务是发起者
			ret.put("isInitiator", true);
		}
		return R.ok(ret);
	}

	@GetMapping(value = "/backNodes")
	public R backNodes(@RequestParam String taskId) {
		List<FlowNodeResponse> datas = flowableTaskService.getBackNodes(taskId);
		return R.ok(datas);
	}

	@SysLogAuto(value = "退户任务")
	@PutMapping(value = "/back")
	public R back(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.backTask(taskRequest);
		return R.ok();
	}
}
