package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jian.propertymanagesystem.dto.HouseForm;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.mapper.HouseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qtj
 * @Date: 2021/2/28 14:10
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseServiceTest {
    @Autowired
    HouseService houseService;

    @Test
    public void  queryHouseByPageTest(){
        HouseForm houseForm = new HouseForm();
        houseForm.setUnit(2);
        IPage<House> houses = houseService.queryHouseByPage(houseForm);
        System.out.println(houses.getRecords().size());
    }
}
