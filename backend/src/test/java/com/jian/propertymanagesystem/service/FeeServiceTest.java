package com.jian.propertymanagesystem.service;

import com.jian.propertymanagesystem.entity.Fee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/13 11:16
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FeeServiceTest {
    @Autowired
    FeeService feeService;

    @Test
    public void saveBatchTest(){
        Fee fee = new Fee();
        fee.setHouseId(1);
        fee.setPrice(2.8f);
        fee.setNumber(100f);
        fee.setTotal(280f);
        fee.setType(3);
        fee.setState(0);
        fee.setTime(new Date());
        List<Fee> list = new ArrayList<>();
        list.add(fee);
        feeService.saveBatch(list);



    }
}
