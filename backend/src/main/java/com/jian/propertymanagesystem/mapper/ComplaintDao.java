package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jian.propertymanagesystem.entity.Complaint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author: qtj
 * @Date: 2021/2/10 9:22
 * @Version
 */
@Component
@Repository
public interface ComplaintDao extends BaseMapper<Complaint> {
}
