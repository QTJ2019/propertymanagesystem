package com.jian.propertymanagesystem.controller;

import com.jian.propertymanagesystem.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qtj
 * @Date: 2020/12/27 22:54
 * @Version
 */
@RestController
@RequestMapping("logout")
public class LogoutController {

    @RequestMapping("success")
    public Result success(){
        Result result = Result.ok();
        result.setMessage("退出成功");
        return result;
    }
}
