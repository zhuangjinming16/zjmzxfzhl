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
 * 【角色和用户关系】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_ROLE_USER")
public class SysRoleUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.UUID)
	@NotNull
	@LengthForUTF8(max = 32)
	private String roleUserId;// UUID

	@NotNull
	@LengthForUTF8(max = 32)
	private String roleId;// 角色ID

	@NotNull
	@LengthForUTF8(max = 32)
	private String userId;// 用户ID

	@TableField(exist = false)
	private String userName;// 用户姓名

	@TableField(exist = false)
	private String authFilterSql;

	public SysRoleUser() {
	}

	public SysRoleUser(String roleId, String userId) {
		this.roleUserId = roleId + '-' + userId;
		this.roleId = roleId;
		this.userId = userId;
	}
}