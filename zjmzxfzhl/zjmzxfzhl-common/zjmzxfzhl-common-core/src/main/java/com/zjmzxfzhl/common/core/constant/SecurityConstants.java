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
     * 令牌类型
     */
    public static final String BASIC_TOKEN_TYPE = "Basic";
    /**
     * 令牌类型
     */
    public static final String X_ZJMZXFZHL_INNER_APP_TOKEN_TYPE = "X-ZJMZXFZHL-INNER-APP ";
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
    /**
     * 微服务间调用变量标志
     */
    public static final String INNER = "inner";
    /**
     * 是微服务间调用
     */
    public static final String INNER_TRUE = "true";

    /**
     * 请求表达式
     */
    public static final String ANT_MATCHERS = "antMatchers";
    /**
     * 任意请求
     */
    public static final String ANY_REQUEST = "anyRequest";
    /**
     * 授权方法名
     */
    public static final String ACCESS = "access";
    public static final String NOT_ACCESS = "notAccess";
    public static final String ANONYMOUS = "anonymous";
    public static final String DENY_ALL = "denyAll";
    public static final String FULLY_AUTHENTICATED = "fullyAuthenticated";
    public static final String HAS_ANY_AUTHORITY = "hasAnyAuthority";
    public static final String HAS_ANY_ROLE = "hasAnyRole";
    public static final String HAS_IP_ADDRESS = "hasIpAddress";
    public static final String PERMIT_ALL = "permitAll";
    public static final String REMEMBER_ME = "rememberMe";
    public static final String AUTHENTICATED = "authenticated";
}
