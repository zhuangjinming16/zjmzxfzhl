package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【菜单】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_MENU")
public class SysMenu extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String menuId;

	@NotNull
	@LengthForUtf8(max = 100)
	private String menuName;

	@LengthForUtf8(max = 32)
	private String parentMenuId;

	@LengthForUtf8(max = 100)
	private String menuIcon;

	@LengthForUtf8(max = 255)
	private String menuUrl;

	@LengthForUtf8(max = 255)
	private String menuPermissions;

	@LengthForUtf8(max = 255)
	private String component;

	@LengthForUtf8(max = 255)
	private String redirect;

	@LengthForUtf8(max = 1)
	private String hidden;

	@LengthForUtf8(max = 1)
	private String isRoute;

	@LengthForUtf8(max = 255)
	private String routeName;

	@LengthForUtf8(max = 1)
	private String isCache;

	@LengthForUtf8(max = 1)
	private String affix;

	@LengthForUtf8(max = 1)
	private String isLeaf;

	@Max(99999)
	private Integer sortNo;

	@TableField(exist = false)
	private String parentMenuName;
}