package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jian.propertymanagesystem.entity.House;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/1 18:02
 * @Version
 */
public interface HouseService  {
    public IPage<House> queryHouse(Page<House> page);

    public List<House>  queryAllHouse();
}
