package com.jian.propertymanagesystem.controller.baseinformationadmin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jian.propertymanagesystem.dto.UserHouse;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.BaseInformationService;
import com.jian.propertymanagesystem.service.UserService;
import com.jian.propertymanagesystem.util.BCryptUtil;
import com.jian.propertymanagesystem.util.RSAEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: qtj
 * @Date: 2021/1/29 10:29
 * @Version
 */
@RestController
@RequestMapping("/baseinformationadmin")
public class UserInformationController {
    @Autowired
    BaseInformationService baseInformationService;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/getuserinformation")
    private Result getUserInformation(Authentication authentication){
        Result result;
        User user;
        String phone = authentication.getName();
        Map<String,Object> data = new HashMap<>();
        try {
              user = baseInformationService.queryUserInformation(phone);
            result = Result.ok();
            data.put("userInformation",user);
            result.setData(data);
        }catch (RuntimeException e){
            result = Result.error();
            result.setMessage("查询用户信息错误");
        }
        return result;
    }

    @RequestMapping("/updateuserinformation")
    private Result updateUserInformation(@RequestBody String userInfor, Authentication authentication){
        Result result = Result.error();
        JsonNode node;
        User user = new User();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        try {
             node = objectMapper.readValue(userInfor,JsonNode.class);
        } catch (JsonProcessingException e) {
            result = Result.error();
            result.setMessage("json格式转换错误");
            return result;
        }
        String oldPass = node.get("oldPass").asText().toString();
        //需要写解码
        try {
            oldPass = RSAEncryptUtil.decrypt(oldPass);
        } catch (Exception e) {
            result.setMessage("解码错误");
            return result;
        }
        if (oldPass != null && oldPass!=""){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("phone",node.get("phone").asText().toString());
            String password = userService.getOne(queryWrapper).getPassword();
            if(!passwordEncoder.matches(oldPass,password)){
                result.setMessage("原密码错误");
                return result;
            }
            String newPassword = node.get("pass").asText().toString();
            try {
                user.setPassword(BCryptUtil.generate(RSAEncryptUtil.decrypt(newPassword)));
            } catch (Exception e) {
                result.setMessage("解码错误");
                return result;
            }
        }
        user.setModified(new Date());
        user.setAccount(node.get("account").asText().toString());
        user.setPhone(principal.getUsername());
        try {
            baseInformationService.updateUserInformationByPhone(user,principal.getUsername());
        } catch (RuntimeException e) {
            result = Result.error();
            result.setMessage("数据库更新失败");
            return result;
        }
        result = Result.ok();
        result.setMessage("修改成功，请重新登录");
        return result;
    }

    @RequestMapping("/getuserhouse")
    public Result getUserHouse(String phone,Integer currentPage, Integer size){
        Result result = Result.error();
        Map<String,Object> data = new HashMap<>();
        IPage<UserHouse> userHouses = null;
        if (currentPage == null || currentPage <= 0){
            currentPage = 1;
        }
        if (size == null || size <= 0){
            size = 10;
        }
        Page<UserHouse> page = new Page<>(currentPage,size);
        try {
            userHouses = userService.getUserHouse(phone,page);
        } catch (RuntimeException e) {
            result.setMessage("查询失败");
            return result;
        }
        result = Result.ok();
        data.put("userHouses",userHouses.getRecords());
        data.put("total",userHouses.getTotal());
        result.setData(data);
        return result;
    }


    @RequestMapping("/getusersbyhouseid")
    private Result getUsersByHouseId(Integer houseId){
        Result result = Result.error();
        Map<String,Object> data = new HashMap<>();
        List<User> users = null;
        if (houseId == null) {
            result.setMessage("缺乏查询参数");
            return result;
        }
        try {
            users = baseInformationService.getUsersByHouseId(houseId);
        } catch (RuntimeException e) {
            result.setMessage("读取用户信息错误");
            return result;
        }
        data.put("users",users);
        result = Result.ok();
        result.setData(data);
        return result;
    }
}
