package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jian.propertymanagesystem.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/24 8:53
 * @Version
 */
@Repository
@Component
public interface MenuDao extends BaseMapper<Menu> {
    public List<Menu> queryMenuList( @Param("auths") Object[] auths);
}
