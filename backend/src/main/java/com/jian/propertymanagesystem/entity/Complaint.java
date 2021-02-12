package com.jian.propertymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/10 9:09
 * @Version
 */
@Data
@TableName("t_complaint")
public class Complaint {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userPhone;
    private String context;
    private Integer state;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createdTime;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date handledTime;
    private Integer handlerId;
}
