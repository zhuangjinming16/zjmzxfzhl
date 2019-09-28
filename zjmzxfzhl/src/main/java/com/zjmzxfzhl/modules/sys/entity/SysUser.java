package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

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
	@LengthForUTF8(max = 32)
	private String userId;// 用户ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String userName;// 用户姓名

	@JsonIgnore
	private String password;// 密码

	@JsonIgnore
	private String salt;// 密码盐

	@LengthForUTF8(max = 1)
	private String sex;// 性别

	@LengthForUTF8(max = 32)
	private String roleId;// 所属角色ID

	@LengthForUTF8(max = 32)
	private String orgId;// 所属机构ID

	@LengthForUTF8(max = 20)
	private String mobile;// 手机号码

	@LengthForUTF8(max = 20)
	private String idCardNo;// 身份证号码

	@LengthForUTF8(max = 100)
	private String email;// 邮箱

	@LengthForUTF8(max = 1)
	private String status;// 用户状态

	@Max(999999)
	private Integer sortNo;// 排序号

	@LengthForUTF8(max = 255)
	private String remark;// 备注

	@TableField(exist = false)
	private String orgName;// 所属机构名称

	@TableField(exist = false)
	private String authFilterSql;
}