package com.zjmzxfzhl.modules.flowable.vo.query;

import lombok.Data;

@Data
public class TaskQueryVo extends BaseQueryVo {
    private String taskId;
    private String taskName;
    private String taskCategory;
    private String taskDescription;
    private String taskDefinitionKey;
    private String taskAssignee;
    private String taskOwner;
    private String taskInvolvedUser;
    private Integer taskPriority;
    private Boolean finished = false;
    private Boolean unfinished = false;
    private String executionId;
    private String processInstanceId;
    private String processInstanceName;
    private String processDefinitionName;
    private String processDefinitionKey;
    private String processDefinitionId;
    private String processInstanceBusinessKey;
    private String processCategoryIn;
    private String processCategoryNotIn;
    private Boolean processFinished = false;
    private Boolean processUnfinished = false;
    private String taskParentTaskId;
    private String taskCandidateUser;
    private String taskCandidateGroup;
    private String taskCandidateGroupIn;
    private String taskDueAfter;
    private String taskDueBefore;
    private String taskCreatedAfter;
    private String taskCreatedBefore;
    private String taskCompletedAfter;
    private String taskCompletedBefore;
    private Boolean suspended = false;
    private Boolean active = false;
}
