package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.HouseDao;
import com.jian.propertymanagesystem.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/1 18:03
 * @Version
 */
@Service
@Component
public class HouseServiceImpl  implements HouseService  {
    @Autowired
    private HouseDao houseDao;


    @Override
    public IPage<House> queryHouse(Page<House> page) {
        return houseDao.queryHouse(page);
    }

    @Override
    public List<House> queryAllHouse() {
        return houseDao.selectList(null);
    }
}
