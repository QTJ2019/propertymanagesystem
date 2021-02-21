package com.jian.propertymanagesystem.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/20 15:57
 * @Version
 */
@Data
@TableName("t_gate_record")
public class GateRecord {
    @TableId(type= IdType.AUTO)
    private Integer id;
    @ExcelProperty("门岗")
    private String gate;
    @ExcelProperty("类型")
    private Integer type;
    @ExcelProperty("时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}
