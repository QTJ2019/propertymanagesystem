package com.jian.propertymanagesystem.mapper;

import com.jian.propertymanagesystem.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/24 16:21
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuDaoTest {
    @Autowired
    MenuDao menuDao;

    @Test
    public void queryMenuList(){
        Object[] auths = {"p1","p2"};
        List<Menu> menus = menuDao.queryMenuList(auths);
        for (Menu each : menus){
            System.out.println(each.getMenuName());
            System.out.println("子目录的数量："+each.getChildren().size());
        }
    }
}
