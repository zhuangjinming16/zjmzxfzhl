package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.core.base.BaseEntity;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【菜单】实体类
 * 
 * @author 庄金明
 */
@Data
@TableName("T_SYS_MENU")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    @TableId
    @NotNull
    @LengthForUtf8(max = 32)
    private String menuId;

    /**
     * 菜单名称
     */
    @NotNull
    @LengthForUtf8(max = 100)
    private String menuName;

    /**
     * 上级菜单ID
     */
    @LengthForUtf8(max = 32)
    private String parentMenuId;

    /**
     * 图标
     */
    @LengthForUtf8(max = 100)
    private String menuIcon;

    /**
     * 菜单URL
     */
    @LengthForUtf8(max = 255)
    private String menuUrl;

    /**
     * 授权
     */
    @LengthForUtf8(max = 255)
    private String menuPermissions;

    /**
     * 组件
     */
    @LengthForUtf8(max = 255)
    private String component;

    /**
     * 重定向URL
     */
    @LengthForUtf8(max = 255)
    private String redirect;

    /**
     * 是否隐藏
     */
    @LengthForUtf8(max = 1)
    private String hidden;

    /**
     * 是否路由
     */
    @LengthForUtf8(max = 1)
    private String isRoute;

    /**
     * 路由名称
     */
    @LengthForUtf8(max = 255)
    private String routeName;

    /**
     * tagsView是否缓存 1-缓存 0-不缓存
     */
    @LengthForUtf8(max = 1)
    private String isCache;

    /**
     * 是否常驻菜单1-是0-否
     */
    @LengthForUtf8(max = 1)
    private String affix;

    /**
     * 是否叶子节点
     */
    @LengthForUtf8(max = 1)
    private String isLeaf;

    /**
     * 排序号
     */
    @Max(99999)
    private Integer sortNo;

    @TableField(exist = false)
    private String parentMenuName;
}