package com.jian.propertymanagesystem.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/10 19:59
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionDaoTest {
    @Autowired
    PermissionDao permissionDao;

    @Test
    public  void findPermissionByUserIdTest(){
        List<String> list = permissionDao.findPermissionByUserId(2);

    }
}
