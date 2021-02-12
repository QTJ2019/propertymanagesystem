package com.jian.propertymanagesystem.service;

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
 * @Date: 2021/2/12 12:08
 * @Version
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintServiceTest {
    @Autowired
    ComplaintService complaintService;

    @Test
    public void queryAllRecordsTest(){
        Page<Complaint> page = new Page<>(1,10);
        IPage<Complaint> iPage = complaintService.queryAllRecords(page);
        System.out.println(iPage.getRecords().size());
    }

    @Test
    public void updateStateTest(){
//        complaintService.updateState(9);
    }
}
