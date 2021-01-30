package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.UserDao;
import com.jian.propertymanagesystem.service.BaseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: qtj
 * @Date: 2021/1/29 11:26
 * @Version
 */
@Service
public class BaesInformationImpl implements BaseInformationService {
    @Autowired
    UserDao userDao;

    @Override
    public User queryUserInformation(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",phone);
        User user = userDao.selectOne(queryWrapper);
        user.setPassword(null);
        return user;
    }

    @Override
    @Transactional
    public int updateUserInformationByPhone(User user, String phone) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("phone",phone);
        return userDao.update(user, updateWrapper);
    }
}
