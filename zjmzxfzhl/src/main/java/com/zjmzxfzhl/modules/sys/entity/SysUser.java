package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【用户】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_USER")
public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String userId;

	@NotNull
	@LengthForUtf8(max = 100)
	private String userName;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String salt;

	@LengthForUtf8(max = 1)
	private String sex;

	@LengthForUtf8(max = 32)
	private String roleId;

	@LengthForUtf8(max = 32)
	private String orgId;

	@LengthForUtf8(max = 20)
	private String mobile;

	@LengthForUtf8(max = 20)
	private String idCardNo;

	@LengthForUtf8(max = 100)
	private String email;

	@LengthForUtf8(max = 1)
	private String status;

	@Max(999999)
	private Integer sortNo;

	@LengthForUtf8(max = 255)
	private String remark;

	@TableField(exist = false)
	private String orgName;

	@TableField(exist = false)
	private String authFilterSql;
}