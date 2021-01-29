package com.jian.propertymanagesystem.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: qtj
 * @Date: 2020/12/26 15:54
 * @Version
 */
@Data
@TableName("t_user")
public class User {
    private  int id;
    private  String account;
    private String password;
    private String phone;
}