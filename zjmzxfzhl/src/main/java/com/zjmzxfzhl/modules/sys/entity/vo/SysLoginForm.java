package com.zjmzxfzhl.modules.sys.entity.vo;

import lombok.Data;

@Data
public class SysLoginForm {
	private String userId;
	private String password;
	private String uuid;
	private String captcha;
}