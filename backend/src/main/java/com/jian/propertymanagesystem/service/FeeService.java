package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jian.propertymanagesystem.dto.FeeForm;
import com.jian.propertymanagesystem.entity.Fee;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/12 22:13
 * @Version
 */
public interface FeeService extends IService<Fee> {
    int insertFees(List<Fee> feeList);

    IPage<Fee> queryLists (Page<Fee> page, FeeForm feeForm) throws ParseException;

    IPage<Fee> queryUserFee ( FeeForm feeForm);
}
