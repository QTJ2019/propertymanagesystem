package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jian.propertymanagesystem.entity.HouseUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author: qtj
 * @Date: 2021/2/2 12:13
 * @Version
 */
@Component
@Repository
public interface HouseUserDao extends BaseMapper<HouseUser> {


}
