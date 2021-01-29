package com.jian.propertymanagesystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @Author: qtj
 * @Date: 2020/12/26 22:05
 * @Version
 */
@RestController
@RequestMapping("res")
public class ResourceController {

   @RequestMapping(value="/test")
    public String r1(HttpSession session) {
       System.out.println(session.getId());
       return getName()+"访问资源1,我是拥有p1权限才能访问的资源";
   }

    @RequestMapping(value="/r2")
    @PreAuthorize("hasAuthority('p1') or hasAuthority('p2')")
    public String r2(HttpSession session) {
        System.out.println(session.getId());
        return getName()+"访问资源1,我是拥有p1和p2权限才能访问的资源";
    }

   /*
   通过SecurityContextHolder获取登录用户的身份信息
    */
   private String getName(){
        String userName = null;
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       Object principle = authentication.getPrincipal();
       if (principle == null)
           userName="匿名用户";
       else if (principle instanceof UserDetails){
           System.out.println(((UserDetails) principle).getAuthorities());
           return ((UserDetails) principle).getUsername();
       }
       return userName;
   }
}
