package com.jian.propertymanagesystem.service;

import com.jian.propertymanagesystem.entity.Menu;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/26 8:05
 * @Version
 */
public interface MenuService {
    public List<Menu> getMenuList(Object[] auths);
}
