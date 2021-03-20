package com.jian.propertymanagesystem.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.net.Proxy;

/**
 * @Author: qtj
 * @Date: 2021/2/1 17:53
 * @Version
 */
@Data
@TableName("t_house")
public class House {
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;
    private Integer unit;
    private Integer building;
    private Integer floor;
    private Integer room;
    @TableField(exist = false)
    @ExcelIgnore
    private String owner;

}
