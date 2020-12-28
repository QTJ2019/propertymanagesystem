package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jian.propertymanagesystem.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Author: qtj
 * @Date: 2020/12/26 15:56
 * @Version
 */
@Repository
@Component
public interface UserDao  extends BaseMapper<User> {
    User getUserByUserName(String name);
}
