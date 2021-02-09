package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.entity.House;

/**
 * @Author: qtj
 * @Date: 2021/2/1 18:02
 * @Version
 */
public interface HouseService {
    public IPage<House> queryHouse(Page<House> page);
}
