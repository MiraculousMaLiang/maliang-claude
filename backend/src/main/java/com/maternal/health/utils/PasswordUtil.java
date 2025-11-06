package com.maternal.health.utils;

import cn.hutool.crypto.digest.BCrypt;

/**
 * 密码工具类
 * 功能：提供密码加密和验证方法
 */
public class PasswordUtil {

    /**
     * 加密密码
     * 功能：使用BCrypt算法对密码进行加密
     */
    public static String encrypt(String password) {
        return BCrypt.hashpw(password);
    }

    /**
     * 验证密码
     * 功能：验证明文密码是否与加密后的密码匹配
     */
    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
