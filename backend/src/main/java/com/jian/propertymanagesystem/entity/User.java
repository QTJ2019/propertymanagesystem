package com.jian.propertymanagesystem.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

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
    private Date gmtCreate;
    private Integer roleId;
    @TableField("gmt_modified")
    private Date  modified;
    private Integer state;

}
