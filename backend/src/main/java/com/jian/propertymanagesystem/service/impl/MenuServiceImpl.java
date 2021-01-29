package com.jian.propertymanagesystem.service.impl;

import com.jian.propertymanagesystem.entity.Menu;
import com.jian.propertymanagesystem.mapper.MenuDao;
import com.jian.propertymanagesystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/26 8:06
 * @Version
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;

    @Override
    @Transactional
    public List<Menu> getMenuList(Object[] auths) {
        return menuDao.queryMenuList(auths);
    }
}
