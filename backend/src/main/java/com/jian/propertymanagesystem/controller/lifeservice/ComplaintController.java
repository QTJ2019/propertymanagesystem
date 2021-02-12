package com.jian.propertymanagesystem.controller.lifeservice;

import com.jian.propertymanagesystem.entity.Complaint;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

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



}
