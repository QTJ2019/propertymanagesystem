package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jian.propertymanagesystem.dto.GateRecordSearchForm;
import com.jian.propertymanagesystem.entity.GateRecord;

/**
 * @Author: qtj
 * @Date: 2021/2/20 16:01
 * @Version
 */

public interface GateRecordService extends IService<GateRecord> {
    IPage<GateRecord> queryGateRecordByPage(Page<GateRecord> page, GateRecordSearchForm form);
}
