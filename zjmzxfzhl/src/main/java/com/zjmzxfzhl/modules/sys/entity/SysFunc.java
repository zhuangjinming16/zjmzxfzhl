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
 * 【功能】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_FUNC")
public class SysFunc extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String funcId;

	@NotNull
	@LengthForUtf8(max = 100)
	private String funcName;

	@NotNull
	@LengthForUtf8(max = 32)
	private String menuId;

	@LengthForUtf8(max = 255)
	private String funcPermissions;

	@LengthForUtf8(max = 255)
	private String remark;

	@Max(9999)
	private Integer sortNo;

}