package com.jian.propertymanagesystem.mapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qtj
 * @Date: 2020/12/21 22:25
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMapperTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    public void query() {
        com.jian.propertymanagesystem.entity.Test one = testMapper.selectById(1);
        Assert.assertEquals("nihao",one.getName());
    }


}
