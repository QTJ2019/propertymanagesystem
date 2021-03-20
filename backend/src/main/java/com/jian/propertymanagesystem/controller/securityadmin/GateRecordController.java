package com.jian.propertymanagesystem.controller.securityadmin;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.GateRecordSearchForm;
import com.jian.propertymanagesystem.entity.GateRecord;
import com.jian.propertymanagesystem.listener.GateRecordListener;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.GateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qtj
 * @Date: 2021/2/20 16:15
 * @Version
 */

@RestController
@RequestMapping("/securityadmin")
public class GateRecordController {
    @Autowired
    GateRecordService gateRecordService;

    @RequestMapping("/uploadgaterecord")
    public Result uploadGateRecord(MultipartFile file){
        Result result = Result.error();
        try {
            EasyExcel.read(file.getInputStream(), GateRecord.class,new GateRecordListener(gateRecordService)).sheet().doRead();
        } catch (IOException e) {
            result.setMessage("文件读取失败");
        }
        result = Result.ok();
        return result;

    }

    /**
     * 分页查询gaterecord
     * @param form
     * @return
     */
    @RequestMapping("/getgaterecords")
    public Result getGateRecord(@RequestBody GateRecordSearchForm form){
        Result result = Result.error();
        Page<GateRecord> page = null;
        Map<String,Object> data = new HashMap<>();
        IPage<GateRecord> iPage = null;
        if (form != null){
            if (form.getCurrentPage() == null || form.getCurrentPage() <=0){
                form.setCurrentPage(1);
            }
            if (form.getSize() == null || form.getSize() <= 0){
                form.setSize(10);
            }
        }
        page = new Page<>(form.getCurrentPage(),form.getSize());
        try {
           iPage  = gateRecordService.queryGateRecordByPage(page,form);
        } catch (Exception e) {
            result.setMessage("数据库查询失败");
            return result;
        }
        data.put("gateRecords",iPage.getRecords());
        data.put("total",iPage.getTotal());
        result = Result.ok();
        result.setData(data);
        return result;
    }

}
