package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jian.propertymanagesystem.dto.UserHouse;
import com.jian.propertymanagesystem.entity.User;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/2 12:04
 * @Version
 */
public interface UserService extends IService<User> {
    public List<User> getUsersByHouseId(Integer houseId);

    List<User> getAllUsers();

    IPage<UserHouse> getUserHouse(String phone, Page<UserHouse> page);

    IPage<User>  getUsersByPage(Page<User> page, QueryWrapper<User> queryWrapper);
}
