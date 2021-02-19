package com.jian.propertymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: qtj
 * @Date: 2021/2/1 17:53
 * @Version
 */
@Data
@TableName("t_house")
public class House {
    private int id;
    private int unit;
    private int building;
    private int floor;
    private int room;
    @TableField(exist = false)
    private String owner;

}
