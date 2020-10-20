package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class TaskUpdateRequest {
    private String id;
    private String name;
    private String assignee;
    private String owner;
    private Date dueDate;
    private String category;
    private String description;
    private int priority;
}
