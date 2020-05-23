package com.zjmzxfzhl.modules.app.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;
import com.zjmzxfzhl.framework.app.common.AppBaseEntity;

import lombok.Data;

/**
 * 【app用户】实体类
 * 
 * @author 庄金明
 */
@Data
@TableName("T_APP_USER")
public class AppUser extends AppBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    @NotNull
    @LengthForUtf8(max = 32)
    private String userId;

    /**
     * 用户姓名
     */
    @NotNull
    @LengthForUtf8(max = 100)
    private String userName;

    /**
     * 手机号码
     */
    @LengthForUtf8(max = 20)
    private String mobile;

    /**
     * 密码
     */
    @NotNull
    @LengthForUtf8(max = 100)
    @JsonIgnore
    private String password;

    /**
     * 密码盐
     */
    @JsonIgnore
    private String salt;

    /**
     * 性别
     */
    @LengthForUtf8(max = 1)
    private String sex;

    /**
     * 身份证号码
     */
    @LengthForUtf8(max = 20)
    private String idCardNo;

    /**
     * 邮箱
     */
    @LengthForUtf8(max = 100)
    private String email;
}