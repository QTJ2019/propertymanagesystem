package com.jian.propertymanagesystem.controller;

import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.UserService;
import com.jian.propertymanagesystem.util.BCryptUtil;
import com.jian.propertymanagesystem.util.RSAEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/21 23:26
 * @Version
 */
@RestController
public class RegisterController {
    @Autowired
    UserService userService;
    @RequestMapping("/register")
    public Result Register(@RequestBody User ruleForm){
        Result result = Result.error();
        List<User> savedUsers = userService.list();
        if (ruleForm == null || ruleForm.getPhone() == null ||ruleForm.getAccount()== null || ruleForm.getPassword() == null ){
            result.setMessage("缺乏信息，注册失败");
            return result;
        }
        for (int i=0;i<savedUsers.size();i++){
            if (savedUsers.get(i).getPhone().equals(ruleForm.getPhone()) ){
                result.setMessage("该手机号已被使用");
                return result;
            }
        }
        try {
            //前端传过来的密码是以RSA加密
            String decodedPassword = RSAEncryptUtil.decrypt(ruleForm.getPassword());
            //数据库存储的密码以BCrypt来加密
            ruleForm.setPassword(BCryptUtil.generate(decodedPassword));
        } catch (Exception e) {
            result.setMessage("密码解密失败");
            return result;
        }
        ruleForm.setState(1);
        ruleForm.setRoleId(2);
        ruleForm.setGmtCreate(new Date());
        try {
            userService.save(ruleForm);
        } catch (Exception e) {
            result.setMessage("数据库保存失败");
            return result;
        }
        result = Result.ok();
        return result;
    }
}
