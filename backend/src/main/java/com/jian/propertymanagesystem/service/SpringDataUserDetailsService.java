package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.PermissionDao;
import com.jian.propertymanagesystem.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2020/12/25 13:13
 * @Version
 */
@Service
@Primary
public class SpringDataUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Autowired
    PermissionDao permissionDao;
    //根据账号查询用户信息
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",s);
        User user = userDao.selectOne(queryWrapper);
        //如果是禁用则返回null
        if (user == null || user.getState() == null || user.getState() == 0)
            return  null; //返回空Spring Security会帮我们抛出错误
        List<String> permissions = permissionDao.findPermissionByUserId(user.getId());
        String[] perArray = new String[permissions.size()];
        permissions.toArray(perArray);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getPhone()).password(user.getPassword()).authorities(perArray).build();//这里是比对密码
        return userDetails;
    }


}
