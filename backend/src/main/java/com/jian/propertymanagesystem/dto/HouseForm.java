package com.jian.propertymanagesystem.dto;

import lombok.Data;

/**
 * @Author: qtj
 * @Date: 2021/2/27 19:49
 * @Version
 */
@Data
public class HouseForm {
    private Integer unit;
    private Integer building;
    private Integer floor;
    private Integer room;
    private Integer currentPage;
    private Integer size;
}
