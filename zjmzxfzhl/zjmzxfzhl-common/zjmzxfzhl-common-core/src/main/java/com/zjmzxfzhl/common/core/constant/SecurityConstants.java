package com.zjmzxfzhl.common.core.constant;

/**
 * 安全相关常量
 *
 * @author 庄金明
 */
public class SecurityConstants {
    public static final String CLIENT_ID = "client_id";
    /**
     * 客户端用户登录获取用户信息 remoteUserInfoService
     */
    public static final String REMOTE_USER_INFO_SERVICE = "remoteUserInfoService";
    /**
     * 令牌类型
     */
    public static final String BEARER_TOKEN_TYPE = "Bearer";
    /**
     * 授权token url
     */
    public static final String OAUTH_TOKEN_URL = "/oauth/token";
    /**
     * 默认code Url
     */
    public static final String OAUTH_AUTHORIZE_URL = "/oauth/authorize";
    /**
     * grant_type
     */
    public static final String REFRESH_TOKEN = "refresh_token";
}
