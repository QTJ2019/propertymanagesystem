package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.FeeForm;
import com.jian.propertymanagesystem.entity.Fee;
import org.apache.ibatis.annotations.Param;
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

    IPage<Fee>  queryUserFee(Page<Fee> page, @Param("feeForm")FeeForm feeForm);
}
