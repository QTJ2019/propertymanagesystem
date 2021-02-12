package com.jian.propertymanagesystem.controller.lifeservice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.entity.Complaint;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: qtj
 * @Date: 2021/2/11 9:59
 * @Version
 */
@RestController
@RequestMapping("/lifeservice")
public class ComplaintController {
    @Autowired
    ComplaintService complaintService;
    @RequestMapping("/addcomplaint")
    public Result addComplaint(HttpServletRequest request,@RequestParam(name="upLoadImgList",required = false) List<MultipartFile> upLoadImgList) {
        Result result = Result.error();
        Complaint complaint = new Complaint();
        String userPhone = request.getParameter("phone");
        if (userPhone == null){
            result.setMessage("缺少手机号");
            return  result;
        }
        String complaintContext = request.getParameter("complaintContext");
        if (complaintContext == null){
            result.setMessage("缺少投诉内容");
            return result;
        }
        complaint.setUserPhone(userPhone);
        complaint.setContext(complaintContext);
        complaint.setState(0);
        try {
           complaintService.insertComplaintRecord(complaint,upLoadImgList);
        } catch (IOException e) {
            result.setMessage("生成图片错误");
            return result;
        }
        return Result.ok();
    }

    @RequestMapping("/getallcomplaintrecords")
    public Result getAllComplaintRecords(Integer currentPage,Integer size){
        if (currentPage == null || currentPage<=0) currentPage=1;
        if (size == null || size<=0) size=10;
        Page<Complaint> page = new Page<>(currentPage,size);
        IPage<Complaint> complaints = null;
        Map<String,Object> data = new HashMap<>();
        Result result = Result.error();
        try {
            complaints = complaintService.queryAllRecords(page);
        } catch (RuntimeException e) {
            result.setMessage("读取房屋信息失败");
            return result;
        }
        data.put("complaints",complaints.getRecords());
        data.put("total",complaints.getTotal());
        result = Result.ok();
        result.setData(data);
        return result;
    }

    @RequestMapping("/confirmcomplaint")
    public Result confirmComplaint(Integer complaintId,Integer handlerId){
        Result result = Result.error();
        if (complaintId == null || complaintId<=0){
            result.setMessage("请传入正确的投诉编号");
            return result;
        }
        try {
            complaintService.updateState(complaintId,handlerId);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("更新投诉状态出错");
            return result;
        }
        result = Result.ok();
        return result;
    }

}
