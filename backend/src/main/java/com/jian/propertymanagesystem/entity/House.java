package com.jian.propertymanagesystem.entity;

import lombok.Data;

/**
 * @Author: qtj
 * @Date: 2021/2/1 17:53
 * @Version
 */
@Data
public class House {
    private int id;
    private int unit;
    private int building;
    private int floor;
    private int room;
    private String owner;

}
