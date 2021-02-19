package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jian.propertymanagesystem.dto.FeeForm;
import com.jian.propertymanagesystem.entity.Fee;
import com.jian.propertymanagesystem.mapper.FeeDao;
import com.jian.propertymanagesystem.service.FeeService;
import com.jian.propertymanagesystem.util.DateFormatUitl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/12 22:14
 * @Version
 */
@Service
public class FeeServiceImpl extends ServiceImpl<FeeDao,Fee> implements FeeService {
    @Autowired
    FeeDao feeDao;
    @Override
    @Transactional
    public int insertFees(List<Fee> feeList) throws RuntimeException {
        int count =0;
        try {
            for (int i=0;i<feeList.size();i++){
                count += feeDao.insert(feeList.get(i));
            }
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    public IPage<Fee> queryLists(Page<Fee> page, FeeForm feeForm)throws ParseException {
        QueryWrapper<Fee> queryWrapper = new QueryWrapper<>();
        if (feeForm != null){
            Integer currentPage = feeForm.getCurrentPage();
            Integer size = feeForm.getSize();
            if (currentPage == null || currentPage<=0 ){
                page.setCurrent(1);
            }else{
                page.setCurrent(currentPage);
            }
            if (size == null || size<=0 ){
                page.setSize(1);
            }else{
                page.setSize(size);
            }
            if (feeForm.getHouseId() != null){
                queryWrapper.eq("house_id",feeForm.getHouseId());
            }
            if (feeForm.getType() != null){
                queryWrapper.eq("type", feeForm.getType());
            }
            if (feeForm.getStartDate() != null && feeForm.getStartDate() !=""){
                Date start = DateFormatUitl.YMFORMAT.parse(feeForm.getStartDate());
                queryWrapper.ge("time",start);
            }
            if (feeForm.getEndDate() != null && feeForm.getEndDate() != ""){
                Date end = DateFormatUitl.YMFORMAT.parse(feeForm.getEndDate());
                queryWrapper.le("time",end);
        }

        }
        return feeDao.selectPage(page,queryWrapper);
    }
}
