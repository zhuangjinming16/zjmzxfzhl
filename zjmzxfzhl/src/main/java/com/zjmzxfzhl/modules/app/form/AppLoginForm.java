package com.zjmzxfzhl.modules.app.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AppLoginForm {
	@NotBlank
	private String mobile;
	@NotBlank
	private String password;
}
