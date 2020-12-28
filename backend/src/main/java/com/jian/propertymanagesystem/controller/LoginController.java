package com.jian.propertymanagesystem.controller;

import com.jian.propertymanagesystem.result.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/**
 * @Author: qtj
 * @Date: 2020/12/27 10:55
 * @Version
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/success")
    public Result success(HttpSession session){
        String userName = null;
        Result result = Result.ok();
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userName = ((UserDetails) principle).getUsername();
        result.setMessage(userName+"登录成功");
        System.out.println(session.getId());
        return result;
    }

    @RequestMapping("/fail")
    public Result fail(){
        Result result = Result.error();
        result.setMessage("登录失败");
        return result;
    }



}
