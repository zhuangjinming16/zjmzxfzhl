package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskRequest {
	private String taskId;
	private String userId;
	private String message;
	private String activityId;
	private String activityName;
	private Map<String, Object> values;
	private boolean isInitiator = false;

	public void setIsInitiator(boolean isInitiator) {
		this.isInitiator = isInitiator;
	}
}
