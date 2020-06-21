package com.zjmzxfzhl.common.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.zjmzxfzhl.common.core.util.SpringContextUtils;

/**
 * @author 庄金明
 *
 */
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static UserDetails getUserDetails() {
        Authentication authentication = getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }
        return (UserDetails) authentication.getPrincipal();
    }

    public static String getUserId() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getName();
    }

    public static String getClientId() {
        Authentication authentication = getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            return auth2Authentication.getOAuth2Request().getClientId();
        }
        return null;
    }

    /// public static String resolveToken(HttpServletRequest request) {
    /// String bearerToken = request.getHeader("Authorization");
    /// if (bearerToken != null && !bearerToken.isEmpty() && bearerToken.startsWith("Bearer")) {
    /// return bearerToken.substring(7);
    /// }
    /// String token = request.getParameter("access_token");
    /// if (token != null && !token.isEmpty()) {
    /// return token;
    /// }
    /// return null;
    /// }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password
     *            密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = SpringContextUtils.getBean("passwordEncoder");
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword
     *            真实密码
     * @param encodedPassword
     *            加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = SpringContextUtils.getBean("passwordEncoder");
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
