package com.zjmzxfzhl.modules.app.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;
import com.zjmzxfzhl.modules.app.common.AppBaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * 【app用户】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_APP_USER")
public class AppUser extends AppBaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUTF8(max = 32)
	private String userId;// 用户ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String userName;// 用户姓名

	@LengthForUTF8(max = 20)
	private String mobile;// 手机号码

	@NotNull
	@LengthForUTF8(max = 100)
	@JsonIgnore
	private String password;// 密码

	@JsonIgnore
	private String salt;// 密码盐

	@LengthForUTF8(max = 1)
	private String sex;// 性别

	@LengthForUTF8(max = 20)
	private String idCardNo;// 身份证号码

	@LengthForUTF8(max = 100)
	private String email;// 邮箱
}