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

	/**
	 * 用户ID
	 */
	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String userId;

	/**
	 * 用户姓名
	 */
	@NotNull
	@LengthForUtf8(max = 100)
	private String userName;

	/**
	 * 密码
	 */
	@JsonIgnore
	private String password;

	@JsonIgnore
	private String salt;

	/**
	 * 性别
	 */
	@LengthForUtf8(max = 1)
	private String sex;

	/**
	 * 所属角色ID
	 */
	@LengthForUtf8(max = 32)
	private String roleId;

	/**
	 * 所属机构ID
	 */
	@LengthForUtf8(max = 32)
	private String orgId;

	/**
	 * 手机号码
	 */
	@LengthForUtf8(max = 20)
	private String mobile;

	/**
	 * 身份证号码
	 */
	@LengthForUtf8(max = 20)
	private String idCardNo;

	/**
	 * 邮箱
	 */
	@LengthForUtf8(max = 100)
	private String email;

	/**
	 * 用户状态
	 */
	@LengthForUtf8(max = 1)
	private String status;

	/**
	 * 排序号
	 */
	@Max(999999)
	private Integer sortNo;

	/**
	 * 备注
	 */
	@LengthForUtf8(max = 255)
	private String remark;

	@TableField(exist = false)
	private String orgName;

	@TableField(exist = false)
	private String authFilterSql;
}