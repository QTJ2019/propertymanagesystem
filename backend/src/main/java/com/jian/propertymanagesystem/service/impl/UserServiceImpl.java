package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jian.propertymanagesystem.dto.UserHouse;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.UserDao;
import com.jian.propertymanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/2 12:05
 * @Version
 */
@Service
@Component
public class UserServiceImpl extends ServiceImpl<UserDao,User> implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public List<User> getUsersByHouseId(Integer houseId) {
        return userDao.queryUsersByHouseId(houseId);
    }

    @Override
    public List<User> getAllUsers(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",2);
        return userDao.selectList(queryWrapper);
    }

    @Override
    public IPage<UserHouse> getUserHouse(String phone, Page<UserHouse> page) {

        return  userDao.getUserHouse(phone,page);
    }

    @Override
    public IPage<User> getUsersByPage(Page<User> page, QueryWrapper<User> queryWrapper) {
        return userDao.selectPage(page, queryWrapper);
    }
}
