package com.zjmzxfzhl.modules.app.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class AppLoginForm {
	@NotBlank
	private String mobile;
	@NotBlank
	private String password;
}
