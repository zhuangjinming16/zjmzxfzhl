package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

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
	@LengthForUTF8(max = 32)
	private String funcId;// 功能ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String funcName;// 功能名称

	@NotNull
	@LengthForUTF8(max = 32)
	private String menuId;// 菜单ID

	@LengthForUTF8(max = 255)
	private String funcPermissions;// 授权

	@LengthForUTF8(max = 255)
	private String remark;// 备注

	@Max(9999)
	private Integer sortNo;// 排序号

}