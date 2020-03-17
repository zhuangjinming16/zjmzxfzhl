package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponse {

	private String id;
	private String userId;
	private String userName;
	private String type;
	private String typeDesc;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date time;
	private String taskId;
	private String taskName;
	private String taskDefinitionKey;
	private String taskDefinitionName;
	private String processInstanceId;
	private String message;
	private String fullMessage;
}
