package com.jian.propertymanagesystem.service;

import com.jian.propertymanagesystem.entity.HouseUser;

/**
 * @Author: qtj
 * @Date: 2021/2/2 15:53
 * @Version
 */

public interface HouseUserService {
     int insertByHouseIdAndPhones(Integer houseId,String[] phones);

    int deleteByHouseIdAndPhones(Integer houseId,String[] phones);
}
