package com.zjmzxfzhl.modules.flowable.common.model;

import lombok.Data;
import org.flowable.bpmn.model.MultiInstanceLoopCharacteristics;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZjmzxfzhlUserTask {
    private String id;
    private String name;
    private String documentation;
    private String assignee;
    private String owner;
    private String formKey;
    private String dueDate;
    private String category;
    private List<String> candidateUsers = new ArrayList<>();
    private List<String> candidateGroups = new ArrayList<>();
    private String skipExpression;
    private boolean asynchronous;
    private MultiInstanceLoopCharacteristics loopCharacteristics;
    private String buttons;
}
