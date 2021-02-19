package com.jian.propertymanagesystem.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

/**
 * @Author: qtj
 * @Date: 2021/2/13 8:48
 * @Version
 */
@Data
public class ExcelFee {
    @ExcelProperty("费用编号")
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
    @DateTimeFormat("yyyy-MM")
    private String time;
}
