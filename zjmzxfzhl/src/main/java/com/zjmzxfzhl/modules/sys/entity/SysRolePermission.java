package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

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

	@TableId(type = IdType.ASSIGN_UUID)
	@NotNull
	@LengthForUtf8(max = 32)
	private String rolePermissionId;

	@NotNull
	@LengthForUtf8(max = 32)
	private String roleId;

	@NotNull
	@LengthForUtf8(max = 1)
	private String permissionType;

	@NotNull
	@LengthForUtf8(max = 32)
	private String menuOrFuncId;

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