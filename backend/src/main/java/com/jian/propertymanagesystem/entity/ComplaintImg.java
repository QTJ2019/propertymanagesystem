package com.jian.propertymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/10 9:19
 * @Version
 */
@Data
@TableName("t_complaint_img")
public class ComplaintImg {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer complaintId;
    private String addr;
    private Date createdTime;
}
