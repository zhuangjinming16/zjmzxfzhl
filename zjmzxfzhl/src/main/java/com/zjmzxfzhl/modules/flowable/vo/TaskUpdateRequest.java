package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Getter
@Setter
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
