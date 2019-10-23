package com.zjmzxfzhl.modules.app.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AppDemo {
	@NotBlank
	private String transId;

	@NotBlank
	private String version;

	@NotBlank
	private String data;
}
