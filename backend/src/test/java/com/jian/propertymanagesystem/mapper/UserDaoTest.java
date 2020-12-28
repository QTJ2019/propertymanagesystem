package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.propertymanagesystem.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qtj
 * @Date: 2020/12/26 16:04
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
   public void getUserbyUserNameTest(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone","15521072883");
        User user = userDao.selectOne(queryWrapper);
        System.out.println(user.getPassword());
    }
}
