package com.jian.propertymanagesystem.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @Author: qtj
 * @Date: 2021/2/23 0:06
 * @Version
 */
public class BCryptUtil {
    /**
     * 对密码加密
     * @param password
     * @return
     */
    public static String generate(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    public static void main(String[] args) {
        System.out.println(generate("1234"));
        System.out.println(BCrypt.checkpw("12345678","$2a$12$63ZgjPmpV9BS5/wZs1WvH.WHojqhyMgnWFD8NqdJRa5zUQr9xIere"));
        System.out.println(BCrypt.checkpw("1234","$2a$10$s.t7A6ZqtHoc0TMRhBel7e8xxdu/CvEAQ6F0LIfhXkqNt3JtRdEkm"));

    }
}
