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

import com.zjmzxfzhl.common.Result;
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

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
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
		allowedSortProperties.put(FlowableConstant.EXECUTION_ID, HistoricTaskInstanceQueryProperty.EXECUTION_ID);
		allowedSortProperties.put("taskInstanceId", HistoricTaskInstanceQueryProperty.HISTORIC_TASK_INSTANCE_ID);
		allowedSortProperties.put(FlowableConstant.PROCESS_DEFINITION_ID, HistoricTaskInstanceQueryProperty.PROCESS_DEFINITION_ID);
		allowedSortProperties.put(FlowableConstant.PROCESS_INSTANCE_ID, HistoricTaskInstanceQueryProperty.PROCESS_INSTANCE_ID);
		allowedSortProperties.put("assignee", HistoricTaskInstanceQueryProperty.TASK_ASSIGNEE);
		allowedSortProperties.put(FlowableConstant.TASK_DEFINITION_KEY, HistoricTaskInstanceQueryProperty.TASK_DEFINITION_KEY);
		allowedSortProperties.put("description", HistoricTaskInstanceQueryProperty.TASK_DESCRIPTION);
		allowedSortProperties.put("dueDate", HistoricTaskInstanceQueryProperty.TASK_DUE_DATE);
		allowedSortProperties.put(FlowableConstant.NAME, HistoricTaskInstanceQueryProperty.TASK_NAME);
		allowedSortProperties.put("owner", HistoricTaskInstanceQueryProperty.TASK_OWNER);
		allowedSortProperties.put("priority", HistoricTaskInstanceQueryProperty.TASK_PRIORITY);
		allowedSortProperties.put(FlowableConstant.TENANT_ID, HistoricTaskInstanceQueryProperty.TENANT_ID_);
		allowedSortProperties.put("startTime", HistoricTaskInstanceQueryProperty.START);

		allowedSortPropertiesTodo.put(FlowableConstant.PROCESS_DEFINITION_ID, TaskQueryProperty.PROCESS_DEFINITION_ID);
		allowedSortPropertiesTodo.put(FlowableConstant.PROCESS_INSTANCE_ID, TaskQueryProperty.PROCESS_INSTANCE_ID);
		allowedSortPropertiesTodo.put(FlowableConstant.TASK_DEFINITION_KEY, TaskQueryProperty.TASK_DEFINITION_KEY);
		allowedSortPropertiesTodo.put("dueDate", TaskQueryProperty.DUE_DATE);
		allowedSortPropertiesTodo.put(FlowableConstant.NAME, TaskQueryProperty.NAME);
		allowedSortPropertiesTodo.put("priority", TaskQueryProperty.PRIORITY);
		allowedSortPropertiesTodo.put(FlowableConstant.TENANT_ID, TaskQueryProperty.TENANT_ID);
		allowedSortPropertiesTodo.put("createTime", TaskQueryProperty.CREATE_TIME);
	}

	protected HistoricTaskInstanceQuery createHistoricTaskInstanceQuery(Map<String, String> requestParams) {
		HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_ID))) {
			query.taskId(requestParams.get(FlowableConstant.TASK_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_INSTANCE_ID))) {
			query.processInstanceId(requestParams.get(FlowableConstant.PROCESS_INSTANCE_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_INSTANCE_BUSINESS_KEY))) {
			query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.PROCESS_INSTANCE_BUSINESS_KEY)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_DEFINITION_KEY))) {
			query.processDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.PROCESS_DEFINITION_KEY)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_DEFINITION_ID))) {
			query.processDefinitionId(requestParams.get(FlowableConstant.PROCESS_DEFINITION_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_DEFINITION_NAME))) {
			query.processDefinitionNameLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.PROCESS_DEFINITION_NAME)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.EXECUTION_ID))) {
			query.executionId(requestParams.get(FlowableConstant.EXECUTION_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_NAME))) {
			query.taskNameLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.TASK_NAME)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_DESCRIPTION))) {
			query.taskDescriptionLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.TASK_DESCRIPTION)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_DEFINITION_KEY))) {
			query.taskDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.TASK_DEFINITION_KEY)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_ASSIGNEE))) {
			query.taskAssignee(requestParams.get(FlowableConstant.TASK_ASSIGNEE));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_OWNER))) {
			query.taskOwner(requestParams.get(FlowableConstant.TASK_OWNER));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_INVOLVED_USER))) {
			query.taskInvolvedUser(requestParams.get(FlowableConstant.TASK_INVOLVED_USER));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_PRIORITY))) {
			query.taskPriority(ObjectUtils.convertToInteger(requestParams.get(FlowableConstant.TASK_PRIORITY)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.FINISHED))) {
			boolean isFinished = ObjectUtils.convertToBoolean(requestParams.get(FlowableConstant.FINISHED));
			if (isFinished) {
				query.finished();
			} else {
				query.unfinished();
			}
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_FINISHED))) {
			boolean isProcessFinished = ObjectUtils.convertToBoolean(requestParams.get("processFinished"));
			if (isProcessFinished) {
				query.processFinished();
			} else {
				query.processUnfinished();
			}
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PARENT_TASK_ID))) {
			query.taskParentTaskId(requestParams.get(FlowableConstant.PARENT_TASK_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TENANT_ID))) {
			query.taskTenantId(requestParams.get(FlowableConstant.TENANT_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_CANDIDATE_USER))) {
			query.taskCandidateUser(requestParams.get(FlowableConstant.TASK_CANDIDATE_USER));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_CANDIDATE_GROUP))) {
			query.taskCandidateGroup(requestParams.get(FlowableConstant.TASK_CANDIDATE_GROUP));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_CANDIDATE_GROUPS))) {
			query.taskCandidateGroupIn(Arrays.asList(requestParams.get(FlowableConstant.TASK_CANDIDATE_GROUPS).split(",")));
		}
		setTaskQueryDate(requestParams, query);
		return query;
	}

	private void setTaskQueryDate(Map<String, String> requestParams, HistoricTaskInstanceQuery query) {
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.DUE_DATE_AFTER))) {
			query.taskDueAfter(ObjectUtils.convertToDate(requestParams.get(FlowableConstant.DUE_DATE_AFTER)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.DUE_DATE_BEFORE))) {
			query.taskDueBefore(ObjectUtils.convertToDate(requestParams.get(FlowableConstant.DUE_DATE_BEFORE)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_CREATED_BEFORE))) {
			query.taskCreatedBefore(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.TASK_CREATED_BEFORE)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_CREATED_AFTER))) {
			query.taskCreatedAfter(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.TASK_CREATED_AFTER)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_COMPLETED_BEFORE))) {
			query.taskCompletedBefore(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.TASK_COMPLETED_BEFORE)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_COMPLETED_AFTER))) {
			query.taskCompletedAfter(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.TASK_COMPLETED_AFTER)));
		}
	}

	protected TaskQuery createTaskQuery(Map<String, String> requestParams) {
		TaskQuery query = taskService.createTaskQuery();
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_INSTANCE_ID))) {
			query.processInstanceId(requestParams.get(FlowableConstant.PROCESS_INSTANCE_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_NAME))) {
			query.taskNameLike(requestParams.get(FlowableConstant.TASK_NAME));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_INSTANCE_BUSINESS_KEY))) {
			query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.PROCESS_INSTANCE_BUSINESS_KEY)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_DEFINITION_KEY))) {
			query.processDefinitionKeyLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.PROCESS_DEFINITION_KEY)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_DEFINITION_ID))) {
			query.processDefinitionId(requestParams.get(FlowableConstant.PROCESS_DEFINITION_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.PROCESS_DEFINITION_NAME))) {
			query.processDefinitionNameLike(ObjectUtils.convertToLike(requestParams.get(FlowableConstant.PROCESS_DEFINITION_NAME)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.DUE_DATE_AFTER))) {
			query.taskDueAfter(ObjectUtils.convertToDate(requestParams.get(FlowableConstant.DUE_DATE_AFTER)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.DUE_DATE_BEFORE))) {
			query.taskDueBefore(ObjectUtils.convertToDate(requestParams.get(FlowableConstant.DUE_DATE_BEFORE)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_CREATED_BEFORE))) {
			query.taskCreatedBefore(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.TASK_CREATED_BEFORE)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TASK_CREATED_AFTER))) {
			query.taskCreatedAfter(ObjectUtils.convertToDatetime(requestParams.get(FlowableConstant.TASK_CREATED_AFTER)));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.TENANT_ID))) {
			query.taskTenantId(requestParams.get(FlowableConstant.TENANT_ID));
		}
		if (ObjectUtils.isNotEmpty(requestParams.get(FlowableConstant.SUSPENDED))) {
			boolean isSuspended = ObjectUtils.convertToBoolean(requestParams.get(FlowableConstant.SUSPENDED));
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
	public Result list(@RequestParam Map<String, String> requestParams) {
		HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(requestParams);
		FlowablePage page = this.pageList(requestParams, query, TaskListWrapper.class, allowedSortProperties,
				HistoricTaskInstanceQueryProperty.START);
		return Result.ok(page);
	}

	@RequiresPermissions("flowable:task:listDone")
	@GetMapping(value = "/listDone")
	public Result listDone(@RequestParam Map<String, String> requestParams) {
		HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(requestParams);
		query.finished().or().taskAssignee(ShiroUtils.getUserId()).taskOwner(ShiroUtils.getUserId()).endOr();
		FlowablePage page = this.pageList(requestParams, query, TaskListWrapper.class, allowedSortProperties,
				HistoricTaskInstanceQueryProperty.START);
		return Result.ok(page);
	}

	@RequiresPermissions("flowable:task:listTodo")
	@GetMapping(value = "/listTodo")
	public Result listTodo(@RequestParam Map<String, String> requestParams) {
		String userId = ShiroUtils.getUserId();
		TaskQuery query = createTaskQuery(requestParams);
		query.or().taskCandidateOrAssigned(userId).taskOwner(userId).endOr();
		FlowablePage page = this.pageList(requestParams, query, TaskTodoListWrapper.class, allowedSortProperties, TaskQueryProperty.CREATE_TIME);
		return Result.ok(page);
	}

	@RequiresPermissions(value = { "flowable:task:list", "flowable:task:listTodo", "flowable:task:listDone" }, logical = Logical.OR)
	@GetMapping(value = "/queryById")
	public Result queryById(@RequestParam String taskId) {
		TaskResponse task = flowableTaskService.getTask(taskId);
		return Result.ok(task);
	}

	@SysLogAuto(value = "修改任务")
	@RequiresPermissions("flowable:task:update")
	@PutMapping(value = "/update")
	public Result update(@RequestBody TaskUpdateRequest taskUpdateRequest) {
		TaskResponse task = flowableTaskService.updateTask(taskUpdateRequest);
		return Result.ok(task);
	}

	@SysLogAuto(value = "删除任务")
	@RequiresPermissions("flowable:task:delete")
	@DeleteMapping(value = "/delete")
	public Result delete(@RequestParam String taskId) {
		flowableTaskService.deleteTask(taskId);
		return Result.ok();
	}

	@SysLogAuto(value = "转办任务")
	@PutMapping(value = "/assign")
	public Result assign(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.assignTask(taskRequest);
		return Result.ok();
	}

	@SysLogAuto(value = "委派任务")
	@PutMapping(value = "/delegate")
	public Result delegate(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.delegateTask(taskRequest);
		return Result.ok();
	}

	@SysLogAuto(value = "认领任务")
	@PutMapping(value = "/claim")
	public Result claim(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.claimTask(taskRequest);
		return Result.ok();
	}

	@SysLogAuto(value = "取消认领任务")
	@PutMapping(value = "/unclaim")
	public Result unclaim(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.unclaimTask(taskRequest);
		return Result.ok();
	}

	@SysLogAuto(value = "完成任务")
	@PutMapping(value = "/complete")
	public Result complete(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.completeTask(taskRequest);
		return Result.ok();
	}

	@SysLogAuto(value = "结束流程实例")
	@PutMapping(value = "/stopProcessInstance")
	public Result stopProcessInstance(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.stopProcessInstance(taskRequest);
		return Result.ok();
	}

	@GetMapping(value = "/renderedTaskForm")
	public Result renderedTaskForm(@RequestParam String taskId) {
		permissionService.validateReadPermissionOnTask2(taskId, ShiroUtils.getUserId(), true, true);
		Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
		return Result.ok(renderedTaskForm);
	}

	@GetMapping(value = "/executeTaskData")
	public Result executeTaskData(@RequestParam String taskId) {
		Task task = permissionService.validateReadPermissionOnTask2(taskId, ShiroUtils.getUserId(), true, true);
		Object renderedStartForm = formService.getRenderedStartForm(task.getProcessDefinitionId());
		Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
		Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());

		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
		Map<String, Object> ret = new HashMap<String, Object>(7);
		ret.put("startUserId", processInstance.getStartUserId());
		ret.put("renderedStartForm", renderedStartForm);
		ret.put("renderedTaskForm", renderedTaskForm);
		ret.put("variables", variables);
		boolean showBusinessKey = isShowBusinessKey(task.getProcessDefinitionId());
		ret.put("showBusinessKey", showBusinessKey);
		ret.put(FlowableConstant.BUSINESS_KEY, processInstance.getBusinessKey());
		// 当前任务是发起者
		if (FlowableConstant.INITIATOR.equals(task.getTaskDefinitionKey())) {
			ret.put("isInitiator", true);
		}
		return Result.ok(ret);
	}

	@GetMapping(value = "/backNodes")
	public Result backNodes(@RequestParam String taskId) {
		List<FlowNodeResponse> datas = flowableTaskService.getBackNodes(taskId);
		return Result.ok(datas);
	}

	@SysLogAuto(value = "退户任务")
	@PutMapping(value = "/back")
	public Result back(@RequestBody TaskRequest taskRequest) {
		flowableTaskService.backTask(taskRequest);
		return Result.ok();
	}
}
