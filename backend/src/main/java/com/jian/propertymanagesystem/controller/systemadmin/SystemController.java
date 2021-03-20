package com.jian.propertymanagesystem.controller.systemadmin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.result.Result;
import com.jian.propertymanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author: qtj
 * @Date: 2021/2/18 8:21
 * @Version
 */
@RestController
@RequestMapping("/systemadmin")
public class SystemController {
    @Autowired
    UserService userService;

    /**
     * 分页获取物业管理人员
     * @param currentPage
     * @param size
     * @return
     */
    @PreAuthorize("hasAuthority('p4')")
    @GetMapping("/getmanagerusers")
    public Result getManagerUsers(Integer currentPage,Integer size,String phone){
        Result result = Result.error();
        Page<User> page = null;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String,Object> data = new HashMap<>(15521);
        IPage<User> managerUsers = null;
        if (currentPage == null || currentPage <=0){
            currentPage = 1;
        }
        if (size == null || size <=0){
            size =10;
        }
        page = new Page<>(currentPage,size);
        if (phone != null && phone !=""){
            queryWrapper.like("phone",phone);
        }
        queryWrapper.eq("role_id",1);
        try {
           managerUsers = userService.getUsersByPage(page,queryWrapper);
        } catch (Exception e) {
            result.setMessage("数据库查询失败");
            return result;
        }
        data.put("managerUsers",managerUsers.getRecords());
        data.put("total",managerUsers.getTotal());
        result = Result.ok();
        result.setData(data);
        return  result;
    }

    @PreAuthorize("hasAuthority('p4')")
    @PostMapping("/addmanageruser")
    public Result addManagerUser(@RequestBody User managerUser){
        Result result = Result.error();
        managerUser.setGmtCreate(new Date());
        managerUser.setRoleId(1);
        managerUser.setState(1);
        try {
            Boolean isSave = userService.save(managerUser);
        } catch (Exception e) {
            result.setMessage("数据库保存失败");
            return result;
        }
        result = Result.ok();
        return result;
    }

    @PreAuthorize("hasAuthority('p4')")
    @PostMapping("/updatemanageruser")
    public Result updateManagerUser(@RequestBody User managerUser){
        Result result = Result.error();
        managerUser.setGmtCreate(new Date());
        try {
            userService.updateById(managerUser);
        } catch (Exception e) {
            result.setMessage("数据库修改失败");
            return result;
        }
        result = Result.ok();
        return result;
    }


}
