package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Data;

import java.util.Map;

@Data
public class ExecuteTaskDataVo {
    private String startUserId;
    private String startFormKey;
    private String taskFormKey;
    private Object renderedStartForm;
    private Object renderedTaskForm;
    Map<String, Object> variables;
    private Boolean showBusinessKey;
    private Boolean initiator;
    private String [] buttons;
}
