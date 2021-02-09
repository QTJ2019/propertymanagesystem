package com.jian.propertymanagesystem.service;

import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/29 11:24
 * @Version
 */
public interface BaseInformationService {

    public User queryUserInformation(String phone);

    public int updateUserInformationByPhone(User user,String phone);

    public List<User> getUsersByHouseId(Integer houseId);


}
