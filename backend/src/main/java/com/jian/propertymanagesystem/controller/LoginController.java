package com.jian.propertymanagesystem.controller;

import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.util.RSAEncryptUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

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
        Map<String,Object> map = new HashMap<>();
        map.put("jsessionid",session.getId());
        map.put("publicKey", RSAEncryptUtil.PUBLICKEY);
        result.setData(map);
        System.out.println(session.getId());
        return result;
    }

    @RequestMapping("/fail")
    public Result fail(HttpServletResponse response){
        Result result = Result.error();
        result.setMessage("账号或密码错误");
        //response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return result;
    }



}
