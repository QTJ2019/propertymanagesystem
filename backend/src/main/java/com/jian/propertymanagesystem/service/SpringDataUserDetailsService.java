package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: qtj
 * @Date: 2020/12/25 13:13
 * @Version
 */
@Service
public class SpringDataUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    //根据账号查询用户信息
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",s);
        User user = userDao.selectOne(queryWrapper);
        if (user == null)
            return  null; //返回空Spring Security会帮我们抛出错误
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getPhone()).password(user.getPassword()).authorities("p1").build();//这里是比对密码
        return userDetails;
    }
}
