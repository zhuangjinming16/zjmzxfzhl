package com.zjmzxfzhl.common.log.util;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zjmzxfzhl.common.core.util.IpUtils;
import com.zjmzxfzhl.common.security.util.SecurityUtils;
import com.zjmzxfzhl.modules.sys.entity.SysLog;

/**
 * @author 庄金明
 *
 */
public class SysLogUtils {
    public static SysLog getSysLog() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLog sysLog = new SysLog();
        sysLog.setUserId(SecurityUtils.getUserId());
        sysLog.setIp(IpUtils.getIpAddr(request));
        sysLog.setRequestUrl(request.getRequestURI());
        sysLog.setRequestType(request.getMethod());
        sysLog.setUserAgent(request.getHeader("user-agent"));
        sysLog.setClientId(SecurityUtils.getClientId());
        return sysLog;
    }
}
