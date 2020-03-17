package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessDefinitionRequest {
	private String processDefinitionId;
	private boolean includeProcessInstances = false;
	private Date date;
}
