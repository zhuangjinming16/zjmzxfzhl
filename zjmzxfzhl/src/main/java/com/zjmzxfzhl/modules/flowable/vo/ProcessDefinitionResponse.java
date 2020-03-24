package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Getter
@Setter
public class ProcessDefinitionResponse {
	private String id;
	private String key;
	private int version;
	private String name;
	private String description;
	private String tenantId;
	private String category;
	private String formKey;
	private boolean graphicalNotationDefined = false;
	private boolean suspended = false;
}
