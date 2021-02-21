package com.jian.propertymanagesystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/20 17:18
 * @Version
 */
@Data
public class GateRecordSearchForm {
    private Integer cunrrentPage;
    private Integer size;
    private String gate;
    private Integer type;
    @JsonFormat(timezone ="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;
    @JsonFormat(timezone ="GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;
}
