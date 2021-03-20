package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.HouseForm;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author: qtj
 * @Date: 2021/2/1 17:55
 * @Version
 */
@Repository
@Component
public interface HouseDao extends BaseMapper<House> {
    IPage<House> queryHouse(Page<?> page, @Param("houseForm") HouseForm houseForm);


}
