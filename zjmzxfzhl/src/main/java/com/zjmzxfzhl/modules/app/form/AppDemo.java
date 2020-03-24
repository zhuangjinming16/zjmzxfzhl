package com.zjmzxfzhl.modules.app.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class AppDemo {
	@NotBlank
	private String transId;

	@NotBlank
	private String version;

	@NotBlank
	private String data;
}
