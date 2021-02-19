package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jian.propertymanagesystem.entity.Fee;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author: qtj
 * @Date: 2021/2/12 22:03
 * @Version
 */
@Component
@Repository
public interface FeeDao extends BaseMapper<Fee> {

}
