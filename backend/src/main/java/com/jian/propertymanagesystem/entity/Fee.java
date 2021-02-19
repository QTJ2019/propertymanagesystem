package com.jian.propertymanagesystem.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/12 22:00
 * @Version
 */
@Data
@TableName("t_fee")
public class Fee {
    @ExcelProperty("费用编号")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ExcelProperty("房屋编号")
    private Integer houseId;
    @ExcelProperty("单价")
    private Float price;
    @ExcelProperty("数量")
    private Float number;
    @ExcelProperty("总费用")
    private Float total;
    @ExcelProperty("费用类型")
    private Integer type;
    @ExcelProperty("状态")
    private Integer state;
    @ExcelProperty("时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM")
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date time;
}
