package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.core.base.BaseEntity;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【角色和用户关系】实体类
 *
 * @author 庄金明
 */
@Data
@TableName("T_SYS_ROLE_USER")
public class SysRoleUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @NotNull
    @LengthForUtf8(max = 32)
    private String roleUserId;

    /**
     * 角色ID
     */
    @NotNull
    @LengthForUtf8(max = 32)
    private String roleId;

    /**
     * 用户ID
     */
    @NotNull
    @LengthForUtf8(max = 32)
    private String userId;

    @TableField(exist = false)
    private String userName;

    public SysRoleUser() {
    }

    public SysRoleUser(String roleId, String userId) {
        this.roleUserId = roleId + '-' + userId;
        this.roleId = roleId;
        this.userId = userId;
    }
}