package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.propertymanagesystem.entity.HouseUser;
import com.jian.propertymanagesystem.mapper.HouseUserDao;
import com.jian.propertymanagesystem.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: qtj
 * @Date: 2021/2/2 15:55
 * @Version
 */
@Service
@Component
public class HouseUserServiceImpl implements HouseUserService {
    @Autowired
    HouseUserDao houseUserDao;

    @Override
    @Transactional
    public int insertByHouseIdAndPhones(Integer houseId, String[] phones) {
        HouseUser[] houseUsers = new HouseUser[phones.length];
        int result =0;
        for (int i=0;i<houseUsers.length;i++){
            houseUsers[i] = new HouseUser();
            houseUsers[i].setHouseId(houseId);
            houseUsers[i].setPhone(phones[i]);
            houseUsers[i].setGmtTime(new Date());
            result +=houseUserDao.insert(houseUsers[i]);
        }
        return result;
    }

    @Override
    public int deleteByHouseIdAndPhones(Integer houseId, String[] phones) {
        QueryWrapper<HouseUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_id",houseId);
        queryWrapper.eq("cancel_flag",0);
        queryWrapper.in("phone",phones);
        return houseUserDao.delete(queryWrapper);
    }
}
