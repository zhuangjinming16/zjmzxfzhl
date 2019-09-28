package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【操作权限】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_ROLE_PERMISSION")
public class SysRolePermission extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.UUID)
	@NotNull
	@LengthForUTF8(max = 32)
	private String rolePermissionId;// 操作权限ID

	@NotNull
	@LengthForUTF8(max = 32)
	private String roleId;// 角色ID

	@NotNull
	@LengthForUTF8(max = 1)
	private String permissionType;// 权限类型

	@NotNull
	@LengthForUTF8(max = 32)
	private String menuOrFuncId;// 菜单或功能ID

	@TableField(exist = false)
	private String menuOrFuncName;

	public SysRolePermission() {
	}

	public SysRolePermission(String permissionType, String menuOrFuncId, String menuOrFuncName) {
		this.permissionType = permissionType;
		this.menuOrFuncId = menuOrFuncId;
		this.menuOrFuncName = menuOrFuncName;
	}
}