package com.zjmzxfzhl.common.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * 密码加密解密
 *
 * @author 庄金明
 */
public class PasswordUtil {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        return getPasswordEncoder().encode(password);
    }

    private static PasswordEncoder getPasswordEncoder() {
        PasswordEncoder passwordEncoder = SpringContextUtils.getBeanIgnoreNotFound("passwordEncoder");
        if (passwordEncoder == null) {
            passwordEncoder = ENCODER;
        }
        return passwordEncoder;
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        return getPasswordEncoder().matches(rawPassword, encodedPassword);
    }
}