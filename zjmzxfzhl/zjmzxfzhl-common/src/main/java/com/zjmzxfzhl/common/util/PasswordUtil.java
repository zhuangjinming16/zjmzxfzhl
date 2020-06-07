package com.zjmzxfzhl.common.util;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/***
 * 密码加密**
 * 
 * @author 庄金明
 */
public class PasswordUtil {

    /**
     * 定义加密次数
     */
    // private static final int HASH_ITERATIONS = 1000;

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password
     *            密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        return ENCODER.encode(password);
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
        return ENCODER.matches(rawPassword, encodedPassword);
    }

    // public static String encrypt(String password, String salt) {
    // // return new Sha256Hash(password, salt, HASH_ITERATIONS).toHex();
    // return null;
    // }

    public static String randomGen(int place) {
        String base = "jhgfdsazxcvbnmqwertyuioplkEDCRFVTGBYHNUJMIKLOPQAZWSX0123456789";
        StringBuffer sb = new StringBuffer();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < place; i++) {
            sb.append(base.charAt(random.nextInt(base.length())));
        }
        return sb.toString();
    }

    /// public static void main(String[] args) {
    /// String password = "1";
    /// String salt = randomGen(8);
    /// Sha256Hash sha256Hash1 = new Sha256Hash(password);
    /// Sha256Hash sha256Hash2 = new Sha256Hash(password, salt);
    /// Sha256Hash sha256Hash3 = new Sha256Hash(password, salt, HASH_ITERATIONS);
    /// System.out.println(salt);
    /// System.out.println("Sha256Hash加密--不加盐：" + sha256Hash1.toHex());
    /// System.out.println("Sha256Hash加密--加盐：" + sha256Hash2.toHex());
    /// System.out.println("Sha256Hash加密--加盐--二次加密：" + sha256Hash3.toHex());
    /// }
}