package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

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
	@LengthForUTF8(max = 32)
	private String menuId;// 菜单ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String menuName;// 菜单名称

	@LengthForUTF8(max = 32)
	private String parentMenuId;// 上级菜单ID

	@LengthForUTF8(max = 100)
	private String menuIcon;// 图标

	@LengthForUTF8(max = 255)
	private String menuUrl;// 菜单URL

	@LengthForUTF8(max = 255)
	private String menuPermissions;// 授权

	@LengthForUTF8(max = 255)
	private String component;// 组件

	@LengthForUTF8(max = 255)
	private String redirect;// 重定向URL

	@LengthForUTF8(max = 1)
	private String hidden;// 是否隐藏

	@LengthForUTF8(max = 1)
	private String isRoute;// 是否路由

	@LengthForUTF8(max = 255)
	private String routeName;// 路由名称

	@LengthForUTF8(max = 1)
	private String isCache;// tagsView是否缓存 1-缓存 0-不缓存

	@LengthForUTF8(max = 1)
	private String affix;// 是否常驻菜单1-是0-否

	@LengthForUTF8(max = 1)
	private String isLeaf;// 是否叶子节点

	@Max(99999)
	private Integer sortNo;// 排序号

	@TableField(exist = false)
	private String parentMenuName;// 上级菜单名称
}