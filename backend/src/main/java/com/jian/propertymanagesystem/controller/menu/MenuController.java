package com.jian.propertymanagesystem.controller.menu;

import com.jian.propertymanagesystem.entity.Menu;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.result.ResultCode;
import com.jian.propertymanagesystem.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author: qtj
 * @Date: 2021/1/26 8:09
 * @Version
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    MenuService menuService;

    /**
     * 会根据用户的权限来获取菜单项
     * @return
     */
    @RequestMapping("/getmenulist")
    private Result getMenuList(){
        Result result = Result.ok();
        Map<String,Object> data = new HashMap<String,Object>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<GrantedAuthority> authsTemp = (Set<GrantedAuthority>)  ((User) authentication.getPrincipal()).getAuthorities();
        String[] auths = new String[authsTemp.size()];
        Iterator<GrantedAuthority> authorityIterator =  authsTemp.iterator();
        for (int i=0;authorityIterator.hasNext();i++)
            auths[i] = authorityIterator.next().toString();

        if (auths == null){
            result.setCode(401);
            result.setCode(ResultCode.ERROR);
            result.setMessage("请登录再访问资源");
        }else {
            result.setCode(ResultCode.SUCCESS);
            List<Menu> menuList = menuService.getMenuList(auths);
            data.put("menuList",menuList);
            result.setData(data);
        }
        return result;
    }
}
