package com.jian.propertymanagesystem.mapper;

import com.jian.propertymanagesystem.entity.HouseUser;
import com.jian.propertymanagesystem.service.HouseUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/4 11:39
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseUserDaoTest {
    @Autowired
    HouseUserDao houseUserDao;

    @Test
    public void insertByHouseIdAndPhonesTest() {
        Integer houseId = 1;
        String[] phones = {"15521072884","15521072885"};
        HouseUser[] houseUsers = new HouseUser[phones.length];
        int result = 0;
        for (int i=0;i<houseUsers.length;i++){
            houseUsers[i] = new HouseUser();
            houseUsers[i].setHouseId(houseId);
            houseUsers[i].setPhone(phones[i]);
            houseUsers[i].setGmtTime(new Date());
            result +=houseUserDao.insert(houseUsers[i]);
        }
    }
}
