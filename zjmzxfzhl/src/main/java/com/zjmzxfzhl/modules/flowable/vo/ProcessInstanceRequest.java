package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessInstanceRequest {
	private String processDefinitionId;
	private String processDefinitionKey;
	private String tenantId;
	private String businessKey;
	private Map<String, Object> values;
	private String processInstanceId;
}
