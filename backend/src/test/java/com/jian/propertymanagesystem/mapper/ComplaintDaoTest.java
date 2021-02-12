package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.entity.Complaint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: qtj
 * @Date: 2021/2/12 12:05
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintDaoTest {
    @Autowired
    ComplaintDao complaintDao;
    @Test
    public void queryAllRecordsTest(){

    }
}
