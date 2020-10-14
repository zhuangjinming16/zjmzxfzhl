package com.zjmzxfzhl.common.log.util;


import com.zjmzxfzhl.common.core.util.DateUtil;
import com.zjmzxfzhl.common.core.util.IpUtils;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.sys.entity.SysLog;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 庄金明
 */
public class SysLogUtils {
    public static SysLog getSysLog() {
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLog sysLog = new SysLog();
        String userId = SecurityUtils.getUserId();
        sysLog.setUserId(userId);
        sysLog.setIp(IpUtils.getIpAddr(request));
        sysLog.setRequestUrl(request.getRequestURI());
        sysLog.setRequestType(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setClientId(getClientId());
        sysLog.setCreateBy(userId);
        sysLog.setCreateDate(DateUtil.getNow());
        sysLog.setCreateTime(DateUtil.getNow());
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
