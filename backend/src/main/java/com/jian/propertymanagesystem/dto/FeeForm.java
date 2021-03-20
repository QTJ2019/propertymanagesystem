package com.jian.propertymanagesystem.dto;

import lombok.Data;

/**
 * @Author: qtj
 * @Date: 2021/2/13 14:39
 * @Version
 */
@Data
public class FeeForm {
    private String phone;
    private Integer houseId;
    private Integer type;
    private Integer currentPage;
    private Integer size;
    private String startDate;
    private String endDate;
}
