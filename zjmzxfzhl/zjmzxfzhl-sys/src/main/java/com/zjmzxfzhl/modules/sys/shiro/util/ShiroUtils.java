package com.zjmzxfzhl.modules.sys.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.zjmzxfzhl.modules.sys.common.SessionObject;
import com.zjmzxfzhl.modules.sys.entity.SysUser;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static SessionObject getSessionObject() {
        return (SessionObject) SecurityUtils.getSubject().getPrincipal();
    }

    public static SysUser getSysUser() {
        SessionObject sessionObject = getSessionObject();
        if (sessionObject == null) {
            return null;
        }
        return sessionObject.getSysUser();
    }

    public static String getUserId() {
        SysUser sysUser = getSysUser();
        if (sysUser == null) {
            return null;
        }
        return sysUser.getUserId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }
}
