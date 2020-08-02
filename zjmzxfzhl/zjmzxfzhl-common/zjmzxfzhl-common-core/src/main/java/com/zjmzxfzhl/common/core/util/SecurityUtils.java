package com.zjmzxfzhl.common.core.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author 庄金明
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
}
