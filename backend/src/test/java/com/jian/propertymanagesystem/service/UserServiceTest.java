package com.jian.propertymanagesystem.service;

import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.UserDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/3 11:19
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;
    @Test
    public void getAllUsersTest(){
        List<User> users = userService.getAllUsers();
        System.out.println(users.size());
    }
}
