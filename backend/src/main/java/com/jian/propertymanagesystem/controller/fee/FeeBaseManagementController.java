package com.jian.propertymanagesystem.controller.fee;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.ExcelFee;
import com.jian.propertymanagesystem.dto.FeeForm;
import com.jian.propertymanagesystem.entity.Fee;
import com.jian.propertymanagesystem.listener.FeeListener;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qtj
 * @Date: 2021/2/13 8:16
 * @Version
 */
@RestController
@RequestMapping("/feeservice")
public class FeeBaseManagementController {
    @Autowired
    FeeService feeService;

    @PostMapping("/uploadfeeexcel")
    public Result uploadFeeExcel(MultipartFile file) {
        Result result = Result.error();
        try {
            EasyExcel.read(file.getInputStream(), ExcelFee.class,new FeeListener(feeService)).sheet().doRead();
        } catch (IOException e) {
            result.setMessage("导入文件错误");
            return result;
        }
            result = Result.ok();
        return result;
    }

    @RequestMapping("/getfeelist")
    public Result getFeeList(@RequestBody FeeForm feeForm){
        Result result = Result.error();
        Map<String,Object> data = new HashMap<>();
        IPage<Fee> iPage = null;
        Page<Fee> page = new Page<>();
        try {
            iPage = feeService.queryLists(page,feeForm);
        } catch (ParseException e) {
            result.setMessage("时间格式错误");
            return result;
        }
        result = Result.ok();
        result.setData(data);
        data.put("records",iPage.getRecords());
        data.put("total",iPage.getTotal());
        return result;
    }

    @RequestMapping("/getuserfee")
    public  Result getUserFee(@RequestBody FeeForm feeForm){
        Result result = Result.error();
        Map<String,Object> data = new HashMap<>();
        IPage<Fee> iPage = null;
        if (feeForm == null || feeForm.getPhone() == null || feeForm.getPhone() ==""){
            result.setMessage("缺乏用户手机号");
            return result;
        }
        try {
            iPage = feeService.queryUserFee(feeForm);
        } catch (Exception e) {
            result.setMessage("数据库");
            return result;
        }
        result = Result.ok();
        data.put("records",iPage.getRecords());
        data.put("total",iPage.getTotal());
        result.setData(data);
        return result;
    }

    @RequestMapping("/addfee")
    public Result addFee(@RequestBody Fee fee){
        Result result = Result.error();
        if(fee.getPrice() != null || fee.getNumber() != null){
            fee.setTotal(fee.getPrice()*fee.getNumber());
        }else {
            result.setMessage("单价和数量不能为空");
            return result;
        }
        fee.setState(0);
        try {
            feeService.save(fee);
        } catch (Exception e) {
            result.setMessage("新增失败");
            return result;
        }
        result = Result.ok();
        return result;
    }

    @RequestMapping("/updatefee")
    public Result updateFee(@RequestBody Fee fee){
        Result result = Result.error();
        if(fee.getPrice() != null || fee.getNumber() != null){
            fee.setTotal(fee.getPrice()*fee.getNumber());
        }else {
            result.setMessage("单价和数量不能为空");
            return result;
        }
        try {
            feeService.updateById(fee);
        } catch (Exception e) {
            result.setMessage("修改失败");
            return result;
        }
        result = Result.ok();
        return result;
    }

    @RequestMapping("/confirmfee")
    public Result confirmfee(@RequestBody Fee fee){
        Result result = Result.error();
        fee.setState(1);
        try {
            feeService.updateById(fee);
        } catch (Exception e) {
            result.setMessage("数据库更新失败");
            return result;
        }
        result = Result.ok();
        return result;
    }
}
