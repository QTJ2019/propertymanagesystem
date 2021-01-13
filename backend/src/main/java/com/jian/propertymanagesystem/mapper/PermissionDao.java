package com.jian.propertymanagesystem.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/10 19:35
 * @Version
 */
@Repository
@Component
public interface PermissionDao {
    public List<String> findPermissionByUserId( @Param("userId") int userId);
}
