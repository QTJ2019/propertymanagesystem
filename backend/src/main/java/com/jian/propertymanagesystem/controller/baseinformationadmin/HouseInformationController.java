package com.jian.propertymanagesystem.controller.baseinformationadmin;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.dto.ExcelFee;
import com.jian.propertymanagesystem.dto.HouseForm;
import com.jian.propertymanagesystem.dto.OwnerChangeForm;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.entity.HouseUser;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.listener.FeeListener;
import com.jian.propertymanagesystem.listener.HouseListener;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.HouseService;
import com.jian.propertymanagesystem.service.HouseUserService;
import com.jian.propertymanagesystem.service.UserService;
import com.jian.propertymanagesystem.util.HttpServletRequestUtil;
import net.sf.jsqlparser.expression.operators.relational.OldOracleJoinBinaryExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    /**
     * 查询房屋信息
     * @param houseForm
     * @return
     */
    @RequestMapping("/gethouseinformation")
    public Result getHouseInformation(@RequestBody HouseForm houseForm){
        IPage<House> houses = null;
        Map<String,Object> data = new HashMap<>();
        Result result = null;
        try {
           houses = houseService.queryHouseByPage(houseForm);
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

    @RequestMapping("/getallhouses")
    public Result getAllHouses(){
        Result result = Result.error();
        Map<String,Object> data = new HashMap<>();
        List<House> houses = null;
        try {
             houses = houseService.queryAllHouse();
        } catch (Exception e) {
            result.setMessage("数据库查询失败");
            return result;
        }
        data.put("houses",houses);
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

    /**
     * 查询所有用户，用于填充选项
     * @return
     */
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

    /**
     * 为房屋绑定业主
     * @param form
     * @return
     */
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

    /**
     * 取消绑定
     * @param form
     * @return
     */
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

    /**
     * 通过上传excel文件，创建房屋，但还没有绑定用户信息
     * @param file
     * @return
     */
    @PostMapping("/uploadhouseexcel")
    public Result uploadHouseExcel(MultipartFile file) {
        Result result = Result.error();
        try {
            EasyExcel.read(file.getInputStream(), House.class,new HouseListener(houseService)).sheet().doRead();
        } catch (IOException e) {
            result.setMessage("导入文件错误");
            return result;
        }
        result = Result.ok();
        return result;
    }

    /**
     * 手动增加房屋
     * @param house
     * @return
     */
    @RequestMapping("addhouse")
    public Result addHouse(@RequestBody House house){
        Result result = Result.error();
        if (house == null || house.getUnit() == null
                || house.getBuilding() == null
                || house.getFloor()== null
                || house.getRoom() == null){
            result.setMessage("缺少必要的信息");
            return result;
        }

        try {
            houseService.save(house);
        } catch (Exception e) {
            result.setMessage("增加房屋失败");
            return result;
        }
        result = Result.ok();
        return result;
    }

    @RequestMapping("updatehouse")
    public Result updateHouse(@RequestBody House house){
        Result result = Result.error();
        try {
            houseService.updateById(house);
        } catch (Exception e) {
            result.setMessage(("修改房屋成功"));
            return  result;
        }
        result = Result.ok();
        return result;
    }

}
