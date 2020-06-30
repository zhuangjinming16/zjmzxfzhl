package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.core.base.BaseEntity;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【应用客户端】实体类
 * 
 * @author 庄金明
 */
@Data
@TableName("T_SYS_OAUTH_CLIENT_DETAILS")
public class SysOauthClientDetails extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 应用标识
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @LengthForUtf8(max = 256)
    private String clientId;

    /**
     * 资源限定串
     */
    @LengthForUtf8(max = 256)
    private String resourceIds;

    /**
     * 应用密钥
     */
    @NotNull
    @LengthForUtf8(max = 256)
    private String clientSecret;

    /**
     * 范围
     */
    @LengthForUtf8(max = 256)
    private String scope;

    /**
     * 授权方式
     */
    @LengthForUtf8(max = 256)
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    @LengthForUtf8(max = 256)
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @LengthForUtf8(max = 256)
    private String authorities;

    /**
     * token有效期
     */
    @Max(999999999)
    private Integer accessTokenValidity;

    /**
     * refresh有效期
     */
    @Max(999999999)
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    @LengthForUtf8(max = 4096)
    private String additionalInformation;

    /**
     * 是否自动授权
     */
    @LengthForUtf8(max = 256)
    private String autoapprove;

    /**
     * 是否有效
     */
    @LengthForUtf8(max = 1)
    private String status;

}