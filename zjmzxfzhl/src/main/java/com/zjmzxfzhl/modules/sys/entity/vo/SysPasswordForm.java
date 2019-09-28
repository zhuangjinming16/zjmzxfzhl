package com.zjmzxfzhl.modules.sys.entity.vo;

import lombok.Data;

@Data
public class SysPasswordForm {
	/**
	 * 原密码
	 */
	private String password;
	/**
	 * 新密码
	 */
	private String newPassword;

}
