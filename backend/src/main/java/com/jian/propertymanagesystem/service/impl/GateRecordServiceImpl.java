package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jian.propertymanagesystem.dto.GateRecordSearchForm;
import com.jian.propertymanagesystem.entity.GateRecord;
import com.jian.propertymanagesystem.mapper.GateRecordDao;
import com.jian.propertymanagesystem.service.GateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: qtj
 * @Date: 2021/2/20 16:04
 * @Version
 */
@Service
public class GateRecordServiceImpl extends ServiceImpl<GateRecordDao,GateRecord> implements GateRecordService {
    @Autowired
    GateRecordDao gateRecordDao;
    @Override
    public IPage<GateRecord> queryGateRecordByPage(Page<GateRecord> page, GateRecordSearchForm form) {
        QueryWrapper<GateRecord> queryWrapper = new QueryWrapper<>();
        if (form!=null){
            if (form.getGate() != null){
                queryWrapper.like("gate",form.getGate());
            }
            if (form.getType() != null){
                queryWrapper.eq("type",form.getType());
            }
            if (form.getStart() != null){
                queryWrapper.ge("time",form.getStart());
            }
            if (form.getEnd() != null){
                queryWrapper.le("time",form.getEnd());
            }
        }
        return gateRecordDao.selectPage(page,queryWrapper);
    }
}
