package com.zjmzxfzhl.modules.flowable.service;

import java.util.List;

import org.flowable.engine.task.Comment;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;

import com.zjmzxfzhl.modules.flowable.common.CommentTypeEnum;
import com.zjmzxfzhl.modules.flowable.vo.FlowNodeResponse;
import com.zjmzxfzhl.modules.flowable.vo.IdentityRequest;
import com.zjmzxfzhl.modules.flowable.vo.TaskRequest;
import com.zjmzxfzhl.modules.flowable.vo.TaskResponse;
import com.zjmzxfzhl.modules.flowable.vo.TaskUpdateRequest;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
public interface FlowableTaskService {
    /**
     * 查询任务详情
     *
     * @param taskId
     * @return
     */
    TaskResponse getTask(String taskId);

    /**
     * 查询子任务列表
     *
     * @param taskId
     * @return
     */
    List<TaskResponse> getSubTasks(String taskId);

    /**
     * 修改任务
     *
     * @param taskUpdateRequest
     * @return
     */
    TaskResponse updateTask(TaskUpdateRequest taskUpdateRequest);

    /**
     * 转办任务
     *
     * @param taskRequest
     */
    void assignTask(TaskRequest taskRequest);

    /**
     * 新增任务参与人
     *
     * @param taskId
     * @param involveUserId
     */
    void involveUser(String taskId, String involveUserId);

    /**
     * 移除任务参与人
     *
     * @param taskId
     * @param involveUserId
     */
    void removeInvolvedUser(String taskId, String involveUserId);

    /**
     * 认领任务
     *
     * @param taskRequest
     */
    void claimTask(TaskRequest taskRequest);

    /**
     * 取消认领
     *
     * @param taskRequest
     */
    void unclaimTask(TaskRequest taskRequest);

    /**
     * 新增任务关联人
     *
     * @param task
     * @param userId
     * @param linkType
     */
    void addIdentiyLinkForUser(Task task, String userId, String linkType);

    /**
     * 委派任务
     *
     * @param taskRequest
     */
    void delegateTask(TaskRequest taskRequest);

    /**
     * 完成任务
     *
     * @param taskRequest
     */
    void completeTask(TaskRequest taskRequest);

    /**
     * 删除任务
     *
     * @param taskId
     */
    void deleteTask(String taskId);

    /**
     * 终止流程
     *
     * @param taskRequest
     */
    void stopProcessInstance(TaskRequest taskRequest);

    /**
     * 查询可退回节点
     *
     * @param taskId
     * @return
     */
    List<FlowNodeResponse> getBackNodes(String taskId);

    /**
     * 退回任务
     *
     * @param taskRequest
     */
    void backTask(TaskRequest taskRequest);

    /**
     * 查询单一任务详情
     *
     * @param taskId
     * @return
     */
    Task getTaskNotNull(String taskId);

    /**
     * 查询单一历史任务详情
     *
     * @param taskId
     * @return
     */
    HistoricTaskInstance getHistoricTaskInstanceNotNull(String taskId);

    /**
     * 新增过程意见
     *
     * @param taskId
     * @param processInstanceId
     * @param userId
     * @param type
     * @param message
     */
    void addComment(String taskId, String processInstanceId, String userId, CommentTypeEnum type, String message);

    /**
     * 查询过程意见
     *
     * @param taskId
     * @param processInstanceId
     * @param type
     * @param userId
     * @return
     */
    List<Comment> getComments(String taskId, String processInstanceId, String type, String userId);

    /**
     * 新增任务关联信息
     *
     * @param taskIdentityRequest
     */
    void saveTaskIdentityLink(IdentityRequest taskIdentityRequest);

    /**
     * 删除任务关联信息
     *
     * @param taskId
     * @param identityId
     * @param identityType
     */
    void deleteTaskIdentityLink(String taskId, String identityId, String identityType);

    /**
     * 任务已阅
     * @param taskRequest
     */
    void readTask(TaskRequest taskRequest);
}