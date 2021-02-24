package com.jian.propertymanagesystem.util;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.Base64;

/**
 * @Author: qtj
 * @Date: 2021/2/23 19:44
 * @Version
 */
public class Base64Util {
    public static String decoder(String password){
        return  Base64.getDecoder().decode(password).toString();
    }

    public static String encoder(String password){
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static void main(String[] args) {
        String decoder = Base64Util.encoder("123");
        System.out.println(decoder);
        String encoder = Base64Util.decoder(decoder);
        System.out.println(encoder);
    }
}
