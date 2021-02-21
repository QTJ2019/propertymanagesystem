package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.GateRecordSearchForm;
import com.jian.propertymanagesystem.entity.GateRecord;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author: qtj
 * @Date: 2021/2/20 16:00
 * @Version
 */
@Component
@Repository
public interface GateRecordDao extends BaseMapper<GateRecord> {
}
