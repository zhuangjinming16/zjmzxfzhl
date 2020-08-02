package com.zjmzxfzhl.common.log.util;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjmzxfzhl.common.core.util.IpUtils;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.sys.entity.SysLog;

/**
 * @author 庄金明
 */
public class SysLogUtils {
    public static SysLog getSysLog() {
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLog sysLog = new SysLog();
        sysLog.setUserId(SecurityUtils.getUserId());
        sysLog.setIp(IpUtils.getIpAddr(request));
        sysLog.setRequestUrl(request.getRequestURI());
        sysLog.setRequestType(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setClientId(getClientId());
        return sysLog;
    }

    public static String getClientId() {
        Authentication authentication = SecurityUtils.getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            return auth2Authentication.getOAuth2Request().getClientId();
        }
        return null;
    }
}
