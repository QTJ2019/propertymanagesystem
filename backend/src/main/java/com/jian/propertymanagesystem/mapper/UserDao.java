package com.jian.propertymanagesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.UserHouse;
import com.jian.propertymanagesystem.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2020/12/26 15:56
 * @Version
 */
@Repository
@Component
public interface UserDao  extends BaseMapper<User> {
    public List<User>  queryUsersByHouseId(@Param("houseId") Integer houseID);

    public List<User> getAllUsers();

    public IPage<UserHouse> getUserHouse(@Param("phone") String phone, Page<UserHouse> page);

    public List<String> getOneUserHouse(@Param("phone") String phone);

}
