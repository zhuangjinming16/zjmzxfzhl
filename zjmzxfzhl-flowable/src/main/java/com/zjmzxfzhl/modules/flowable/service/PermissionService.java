package com.zjmzxfzhl.modules.flowable.service;

import java.util.List;

import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskInfo;
import org.flowable.task.api.history.HistoricTaskInstance;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public interface PermissionService {

    /**
     * 校验用户是否有权限读取任务
     *
     * @param taskId
     * @param userId
     * @param validateReadProcessInstance
     * @param validateReadParentTask
     * @return
     */
    HistoricTaskInstance validateReadPermissionOnTask(String taskId, String userId,
                                                      boolean validateReadProcessInstance,
                                                      boolean validateReadParentTask);

    /**
     * 校验用户是否有权限读取任务
     *
     * @param taskId
     * @param userId
     * @param validateReadProcessInstance
     * @param validateReadParentTask
     * @return
     */
    Task validateReadPermissionOnTask2(String taskId, String userId, boolean validateReadProcessInstance,
                                       boolean validateReadParentTask);

    /**
     * 判断用户是否是任务的所有者或者执行人
     *
     * @param currentUser
     * @param task
     * @return
     */
    boolean isTaskOwnerOrAssignee(String currentUser, Task task);

    /**
     * 判断用户是否是任务的所有者或者执行人
     *
     * @param currentUser
     * @param taskId
     * @return
     */
    boolean isTaskOwnerOrAssignee(String currentUser, String taskId);

    /**
     * 判断是否流程启动人，且流程启动人是否可以完成任务
     *
     * @param userId
     * @param task
     * @return
     */
    boolean validateIfUserIsInitiatorAndCanCompleteTask(String userId, TaskInfo task);

    /**
     * 判断流程启动人是否可以完成任务
     *
     * @param task
     * @return
     */
    boolean validateIfInitiatorCanCompleteTask(TaskInfo task);

    /**
     * 判断是否任务的关联人
     *
     * @param userId
     * @param taskId
     * @return
     */
    boolean isInvolved(String userId, String taskId);

    /**
     * 校验用户是否有权限读取该流程实例
     *
     * @param userId
     * @param processInstanceId
     * @return
     */
    HistoricProcessInstance validateReadPermissionOnProcessInstance(String userId, String processInstanceId);

    /**
     * 判断用户是否有权限读取该流程实例
     *
     * @param userId
     * @param processInstanceId
     * @return
     */
    boolean hasReadPermissionOnProcessInstance(String userId, String processInstanceId);

    /**
     * 判断用户是否有权限读取该流程实例
     *
     * @param userId
     * @param historicProcessInstance
     * @param processInstanceId
     * @return
     */
    boolean hasReadPermissionOnProcessInstance(String userId, HistoricProcessInstance historicProcessInstance,
                                               String processInstanceId);

    /**
     * 判断用户是否有权限读取该流程定义
     *
     * @param userId
     * @param processDefinitionId
     * @param processDefinitionKey
     * @param tenantId
     * @return
     */
    ProcessDefinition validateReadPermissionOnProcessDefinition(String userId, String processDefinitionId,
                                                                String processDefinitionKey, String tenantId);

    /**
     * 校验用户是否有权限读取该流程定义
     *
     * @param userId
     * @param processDefinitionId
     */
    void validateReadPermissionOnProcessDefinition(String userId, String processDefinitionId);

    /**
     * 获取潜在的启动组数组
     *
     * @param identityLinks
     * @return
     */
    List<String> getPotentialStarterGroupIds(List<IdentityLink> identityLinks);

    /**
     * 获取潜在的启动人数组
     *
     * @param identityLinks
     * @return
     */
    List<String> getPotentialStarterUserIds(List<IdentityLink> identityLinks);

    /**
     * 判断任务是否挂起
     *
     * @param task
     * @return
     */
    boolean isTaskPending(Task task);

    /**
     * 是否可以转办任务
     * <p>
     * 1.任务所有人可以转办
     * <p>
     * 2.任务执行人可以转办，但要求任务非委派状态
     * <p>
     * 3.被转办人不能是当前任务执行人
     *
     * @param taskId
     * @param userId
     * @param assignee
     * @return
     */
    Task validateAssignPermissionOnTask(String taskId, String userId, String assignee);

    /**
     * 是否可以委派任务
     * <p>
     * 1.任务所有人可以委派
     * <p>
     * 2.任务执行人可以委派
     * <p>
     * 3.被委派人不能是任务所有人和当前任务执行人
     *
     * @param taskId
     * @param userId
     * @param delegater
     * @return
     */
    Task validateDelegatePermissionOnTask(String taskId, String userId, String delegater);

    /**
     * 校验用户是否可以执行任务
     *
     * @param taskId
     * @param userId
     * @return
     */
    Task validateExcutePermissionOnTask(String taskId, String userId);

    /**
     * 校验用户是否可以终止流程
     *
     * @param taskId
     * @param userId
     * @return
     */
    ProcessInstance validateStopProcessInstancePermissionOnTask(String taskId, String userId);

    /**
     * 是否admin用户
     *
     * @param userId
     * @return
     */
    boolean isAdmin(String userId);
}
