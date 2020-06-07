package com.zjmzxfzhl.framework.config.security.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zjmzxfzhl.common.util.SpringContextUtils;
import com.zjmzxfzhl.modules.sys.common.SessionObject;
import com.zjmzxfzhl.modules.sys.entity.SysUser;

// @Slf4j
public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static SessionObject getSessionObject() {
        final Authentication authentication = getAuthentication();
        if (authentication == null) {
            // throw new SysException("当前登录状态过期");
            return null;
        }
        if (authentication.getPrincipal() instanceof SessionObject) {
            SessionObject sessionObject = (SessionObject) authentication.getPrincipal();
            // UserDetailsService userDetailsService = SpringContextUtils.getBean(UserDetailsService.class);
            return sessionObject;
        }
        // throw new SysException("找不到当前登录的信息");
        return null;
    }

    public static SysUser getSysUser() {
        SessionObject sessionObject = getSessionObject();
        if (sessionObject == null) {
            return null;
        }
        return sessionObject.getSysUser();
    }

    public static String getUserId() {
        SessionObject sessionObject = getSessionObject();
        if (sessionObject == null) {
            return null;
        }
        return sessionObject.getUsername();
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && !bearerToken.isEmpty() && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        String token = request.getParameter("access_token");
        if (token != null && !token.isEmpty()) {
            return token;
        }
        return null;
    }

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
