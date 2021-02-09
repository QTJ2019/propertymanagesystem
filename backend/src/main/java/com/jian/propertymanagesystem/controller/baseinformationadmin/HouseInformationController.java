package com.jian.propertymanagesystem.controller.baseinformationadmin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.OwnerChangeForm;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.entity.HouseUser;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.HouseService;
import com.jian.propertymanagesystem.service.HouseUserService;
import com.jian.propertymanagesystem.service.UserService;
import com.jian.propertymanagesystem.util.HttpServletRequestUtil;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: qtj
 * @Date: 2021/2/2 7:49
 * @Version
 */
@RestController
@RequestMapping("/baseinformationadmin")
public class HouseInformationController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseUserService houseUserService;
    @Autowired
    private UserService userService;

    @RequestMapping("/gethouseinformation")
    public Result getHouseInformation(Integer currentPage, Integer size){
        if (currentPage == null || currentPage<=0) currentPage=1;
        if (size == null || size<=0) size=10;
        Page<House> page = new Page<>(currentPage,size);
        IPage<House> houses = null;
        Map<String,Object> data = new HashMap<>();
        Result result = null;
        try {
           houses = houseService.queryHouse(page);
        } catch (RuntimeException e) {
            result = Result.error();
            result.setMessage("读取房屋信息失败");
            return result;
        }
        data.put("houses",houses.getRecords());
        data.put("total",houses.getTotal());
        result = Result.ok();
        result.setData(data);
        return result;
    }

    @RequestMapping("/getowners")
    public Result getOwners(Integer houseId){
        Result result = Result.error();
        List<User> owners = null;
        Map<String,Object> map = new HashMap<>();
        if (houseId == null){
            result.setMessage("请传入houseId");
            return result;
        }
        try {
            owners = userService.getUsersByHouseId(houseId);
        } catch (Exception e) {
            result.setMessage("查询用户出错");
            return result;
        }
        map.put("owners",owners);
        result = Result.ok();
        result.setData(map);
        return result;
    }

    @RequestMapping("/getusers")
    public Result getUsers(){
        Result result = Result.error();
        List<User> users = null;
        Map<String,Object> data = new HashMap<>();
        try {
            users = userService.getAllUsers();
        } catch (Exception e) {
            result.setMessage("查询所有用户失败");
            return  result;
        }
        data.put("users",users);
        result = Result.ok();
        result.setData(data);
        return result;
    }

    @RequestMapping("/addownersofhouse")
    public Result addHouseOfUsers(@RequestBody OwnerChangeForm form){
        String houseId = form.getHouseId();
        String[] phones = form.getPhones();
        Result result = Result.error();
        if (houseId == null){
            result.setMessage("缺少houseId");
            return result;
        }
        try {
            houseUserService.insertByHouseIdAndPhones(Integer.valueOf(houseId),phones);
        } catch (Exception e) {
            result.setMessage("绑定新的用户失败");
            return result;
        }
        result = Result.ok();
        return result;
    }

    @PreAuthorize("hasAuthority('p1')")
    @RequestMapping(value = "/deletebyhouseidandphones",method = {RequestMethod.POST})
    public Result deleteByHouseIdAndPhones(@RequestBody OwnerChangeForm form){
        String houseId = form.getHouseId();
        String[] phones = form.getPhones();
        Result result = Result.error();
        if (houseId == null){
            result.setMessage("缺少houseId");
            return result;
        }
        try {
            houseUserService.deleteByHouseIdAndPhones(Integer.valueOf(houseId),phones);
        } catch (Exception e) {
            result.setMessage("删除用户失败");
            return result;
        }
        result = Result.ok();
        return result;
    }




}
