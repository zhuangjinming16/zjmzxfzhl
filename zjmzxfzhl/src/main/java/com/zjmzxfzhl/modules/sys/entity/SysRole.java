package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【角色】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_ROLE")
public class SysRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String roleId;

	@NotNull
	@LengthForUtf8(max = 100)
	private String roleName;

	@NotNull
	@Max(9999)
	private Integer sortNo;

	@LengthForUtf8(max = 255)
	private String remark;

}