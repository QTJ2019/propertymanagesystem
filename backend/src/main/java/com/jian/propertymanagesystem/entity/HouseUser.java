package com.jian.propertymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/2 12:14
 * @Version
 */
@Data
@TableName("t_house_user")
public class HouseUser {
    private Integer houseId;
    private String phone;
    private Date gmtTime;
    private Integer cancelFlag;
    private Date cancel_time;
}

