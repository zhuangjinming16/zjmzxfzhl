package com.zjmzxfzhl.modules.flowable.controller;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.redis.aspect.annotation.RedissonLock;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.common.core.util.ObjectUtils;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.common.log.annotation.Log;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.service.FlowableTaskService;
import com.zjmzxfzhl.modules.flowable.vo.FlowNodeResponse;
import com.zjmzxfzhl.modules.flowable.vo.TaskRequest;
import com.zjmzxfzhl.modules.flowable.vo.TaskResponse;
import com.zjmzxfzhl.modules.flowable.vo.TaskUpdateRequest;
import com.zjmzxfzhl.modules.flowable.vo.query.TaskQueryVo;
import com.zjmzxfzhl.modules.flowable.wapper.TaskListWrapper;
import com.zjmzxfzhl.modules.flowable.wapper.TaskTodoListWrapper;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.task.service.impl.HistoricTaskInstanceQueryProperty;
import org.flowable.task.service.impl.TaskQueryProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        allowedSortProperties.put(FlowableConstant.PROCESS_DEFINITION_ID,
                HistoricTaskInstanceQueryProperty.PROCESS_DEFINITION_ID);
        allowedSortProperties.put(FlowableConstant.PROCESS_INSTANCE_ID,
                HistoricTaskInstanceQueryProperty.PROCESS_INSTANCE_ID);
        allowedSortProperties.put("assignee", HistoricTaskInstanceQueryProperty.TASK_ASSIGNEE);
        allowedSortProperties.put(FlowableConstant.TASK_DEFINITION_KEY,
                HistoricTaskInstanceQueryProperty.TASK_DEFINITION_KEY);
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

    protected HistoricTaskInstanceQuery createHistoricTaskInstanceQuery(TaskQueryVo taskQueryVo) {
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskId())) {
            query.taskId(taskQueryVo.getTaskId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceId())) {
            query.processInstanceId(taskQueryVo.getProcessInstanceId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceBusinessKey())) {
            query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessInstanceBusinessKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionKey())) {
            query.processDefinitionKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionId())) {
            query.processDefinitionId(taskQueryVo.getProcessDefinitionId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionName())) {
            query.processDefinitionNameLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getExecutionId())) {
            query.executionId(taskQueryVo.getExecutionId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskName())) {
            query.taskNameLike(ObjectUtils.convertToLike(taskQueryVo.getTaskName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDescription())) {
            query.taskDescriptionLike(ObjectUtils.convertToLike(taskQueryVo.getTaskDescription()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDefinitionKey())) {
            query.taskDefinitionKeyLike(ObjectUtils.convertToLike(taskQueryVo.getTaskDefinitionKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskAssignee())) {
            query.taskAssignee(taskQueryVo.getTaskAssignee());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskOwner())) {
            query.taskOwner(taskQueryVo.getTaskOwner());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskInvolvedUser())) {
            query.taskInvolvedUser(taskQueryVo.getTaskInvolvedUser());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskPriority())) {
            query.taskPriority(taskQueryVo.getTaskPriority());
        }
        Boolean finished = CommonUtil.isEmptyDefault(taskQueryVo.getFinished(), false);
        Boolean unfinished = CommonUtil.isEmptyDefault(taskQueryVo.getUnfinished(), false);
        if (!finished.equals(unfinished)) {
            if (finished) {
                query.finished();
            }
            if (unfinished) {
                query.unfinished();
            }
        }
        Boolean processFinished = CommonUtil.isEmptyDefault(taskQueryVo.getProcessFinished(), false);
        Boolean processUnfinished = CommonUtil.isEmptyDefault(taskQueryVo.getProcessUnfinished(), false);
        if (!processFinished.equals(processUnfinished)) {
            if (processFinished) {
                query.processFinished();
            }
            if (processUnfinished) {
                query.processUnfinished();
            }
        }

        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskParentTaskId())) {
            query.taskParentTaskId(taskQueryVo.getTaskParentTaskId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTenantId())) {
            query.taskTenantId(taskQueryVo.getTenantId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCandidateUser())) {
            query.taskCandidateUser(taskQueryVo.getTaskCandidateUser());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCandidateGroup())) {
            query.taskCandidateGroup(taskQueryVo.getTaskCandidateGroup());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCandidateGroupIn())) {
            query.taskCandidateGroupIn(Arrays.asList(taskQueryVo.getTaskCandidateGroupIn().split(",")));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueAfter())) {
            query.taskDueAfter(ObjectUtils.convertToDate(taskQueryVo.getTaskDueAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueBefore())) {
            query.taskDueBefore(ObjectUtils.convertToDate(taskQueryVo.getTaskDueBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedAfter())) {
            query.taskCreatedAfter(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedBefore())) {
            query.taskCreatedBefore(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCompletedAfter())) {
            query.taskCompletedAfter(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCompletedAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCompletedBefore())) {
            query.taskCompletedBefore(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCompletedBefore()));
        }

        return query;
    }

    protected TaskQuery createTaskQuery(TaskQueryVo taskQueryVo) {
        TaskQuery query = taskService.createTaskQuery();
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceId())) {
            query.processInstanceId(taskQueryVo.getProcessInstanceId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskName())) {
            query.taskNameLike(ObjectUtils.convertToLike(taskQueryVo.getTaskName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessInstanceBusinessKey())) {
            query.processInstanceBusinessKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessInstanceBusinessKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionKey())) {
            query.processDefinitionKeyLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionKey()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionId())) {
            query.processDefinitionId(taskQueryVo.getProcessDefinitionId());
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getProcessDefinitionName())) {
            query.processDefinitionNameLike(ObjectUtils.convertToLike(taskQueryVo.getProcessDefinitionName()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueAfter())) {
            query.taskDueAfter(ObjectUtils.convertToDate(taskQueryVo.getTaskDueAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskDueBefore())) {
            query.taskDueBefore(ObjectUtils.convertToDate(taskQueryVo.getTaskDueBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedAfter())) {
            query.taskCreatedAfter(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedAfter()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTaskCreatedBefore())) {
            query.taskCreatedBefore(ObjectUtils.convertToDatetime(taskQueryVo.getTaskCreatedBefore()));
        }
        if (ObjectUtils.isNotEmpty(taskQueryVo.getTenantId())) {
            query.taskTenantId(taskQueryVo.getTenantId());
        }
        Boolean suspended = CommonUtil.isEmptyDefault(taskQueryVo.getSuspended(), false);
        Boolean active = CommonUtil.isEmptyDefault(taskQueryVo.getActive(), false);
        if (!suspended.equals(active)) {
            if (suspended) {
                query.suspended();
            }
            if (active) {
                query.active();
            }
        }
        return query;
    }

    @PreAuthorize("@elp.single('flowable:task:list')")
    @GetMapping(value = "/list")
    public Result list(TaskQueryVo taskQueryVo) {
        HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(taskQueryVo);
        FlowablePage page = this.pageList(taskQueryVo, query, TaskListWrapper.class, allowedSortProperties,
                HistoricTaskInstanceQueryProperty.START);
        return Result.ok(page);
    }

    @GetMapping(value = "/listDone")
    public Result listDone(TaskQueryVo taskQueryVo) {
        HistoricTaskInstanceQuery query = createHistoricTaskInstanceQuery(taskQueryVo);
        query.finished().or().taskAssignee(SecurityUtils.getUserId()).taskOwner(SecurityUtils.getUserId()).endOr();
        FlowablePage page = this.pageList(taskQueryVo, query, TaskListWrapper.class, allowedSortProperties,
                HistoricTaskInstanceQueryProperty.START);
        return Result.ok(page);
    }

    @GetMapping(value = "/listTodo")
    public Result listTodo(TaskQueryVo taskQueryVo) {
        String userId = SecurityUtils.getUserId();
        TaskQuery query = createTaskQuery(taskQueryVo);
        query.taskCategory(FlowableConstant.CATEGORY_TODO);
        query.or().taskCandidateOrAssigned(userId).taskOwner(userId).endOr();
        FlowablePage page = this.pageList(taskQueryVo, query, TaskTodoListWrapper.class, allowedSortProperties,
                TaskQueryProperty.CREATE_TIME);
        return Result.ok(page);
    }

    @GetMapping(value = "/listToRead")
    public Result listToRead(TaskQueryVo taskQueryVo) {
        String userId = SecurityUtils.getUserId();
        TaskQuery query = createTaskQuery(taskQueryVo);
        query.taskCategory(FlowableConstant.CATEGORY_TO_READ);
        query.or().taskAssignee(userId).taskOwner(userId).endOr();
        FlowablePage page = this.pageList(taskQueryVo, query, TaskTodoListWrapper.class, allowedSortProperties,
                TaskQueryProperty.CREATE_TIME);
        return Result.ok(page);
    }

    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String taskId) {
        TaskResponse task = flowableTaskService.getTask(taskId);
        return Result.ok(task);
    }

    @Log(value = "修改任务")
    @PreAuthorize("@elp.single('flowable:task:update')")
    @PutMapping(value = "/update")
    public Result update(@RequestBody TaskUpdateRequest taskUpdateRequest) {
        TaskResponse task = flowableTaskService.updateTask(taskUpdateRequest);
        return Result.ok(task);
    }

    @Log(value = "删除任务")
    @PreAuthorize("@elp.single('flowable:task:delete')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String taskId) {
        flowableTaskService.deleteTask(taskId);
        return Result.ok();
    }

    @Log(value = "转办任务")
    @PutMapping(value = "/assign")
    public Result assign(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.assignTask(taskRequest);
        return Result.ok();
    }

    @Log(value = "委派任务")
    @PutMapping(value = "/delegate")
    public Result delegate(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.delegateTask(taskRequest);
        return Result.ok();
    }

    @Log(value = "认领任务")
    @PutMapping(value = "/claim")
    @RedissonLock(lockIndexs = 0, fieldNames = "taskId")
    public Result claim(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.claimTask(taskRequest);
        return Result.ok();
    }

    @Log(value = "取消认领任务")
    @PutMapping(value = "/unclaim")
    public Result unclaim(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.unclaimTask(taskRequest);
        return Result.ok();
    }

    @Log(value = "完成任务")
    @PutMapping(value = "/complete")
    public Result complete(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.completeTask(taskRequest);
        return Result.ok();
    }

    @Log(value = "结束流程实例")
    @PutMapping(value = "/stopProcessInstance")
    public Result stopProcessInstance(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.stopProcessInstance(taskRequest);
        return Result.ok();
    }

    @GetMapping(value = "/renderedTaskForm")
    public Result renderedTaskForm(@RequestParam String taskId) {
        permissionService.validateReadPermissionOnTask2(taskId, SecurityUtils.getUserId(), true, true);
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
        return Result.ok(renderedTaskForm);
    }

    @GetMapping(value = "/executeTaskData")
    public Result executeTaskData(@RequestParam String taskId) {
        Task task = permissionService.validateReadPermissionOnTask2(taskId, SecurityUtils.getUserId(), true, true);
        String startFormKey = formService.getStartFormKey(task.getProcessDefinitionId());
        String taskFormKey = formService.getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        Object renderedStartForm = formService.getRenderedStartForm(task.getProcessDefinitionId());
        Object renderedTaskForm = formService.getRenderedTaskForm(taskId);
        Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());

        ProcessInstance processInstance =
                runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        Map<String, Object> ret = new HashMap<String, Object>(7);
        ret.put("startUserId", processInstance.getStartUserId());
        ret.put("startFormKey", startFormKey);
        ret.put("taskFormKey", taskFormKey);
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

    @Log(value = "退回任务")
    @PutMapping(value = "/back")
    public Result back(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.backTask(taskRequest);
        return Result.ok();
    }

    @PutMapping(value = "/read")
    public Result read(@RequestBody TaskRequest taskRequest) {
        flowableTaskService.readTask(taskRequest);
        return Result.ok();
    }
}
