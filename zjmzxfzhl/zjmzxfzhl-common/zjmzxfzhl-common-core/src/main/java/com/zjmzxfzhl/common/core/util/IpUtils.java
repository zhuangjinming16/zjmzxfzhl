package com.zjmzxfzhl.common.core.util;

import com.zjmzxfzhl.common.core.constant.Constants;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
public class IpUtils {
    private static final String UNKNOWN = "unknown";
    private static final String LOCAL_IP6 = "0:0:0:0:0:0:0:1";
    private static final String LOCAL_IP4 = "127.0.0.1";
    /**
     * ***.***.***.***".length() = 15
     */
    private static final int IP_MAX_LENGTH = 15;

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCAL_IP4.equals(ipAddress) || LOCAL_IP6.equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        // "***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > IP_MAX_LENGTH) {
            if (ipAddress.indexOf(Constants.COMMA) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return "127.0.0.1";
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }
}
