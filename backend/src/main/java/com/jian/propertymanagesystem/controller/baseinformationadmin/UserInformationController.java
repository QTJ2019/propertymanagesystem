package com.jian.propertymanagesystem.controller.baseinformationadmin;

import com.jian.propertymanagesystem.result.Result;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qtj
 * @Date: 2021/1/29 10:29
 * @Version
 */
@RestController
@RequestMapping("/baseinformationadmin")
public class UserInformationController {
    @RequestMapping("getuserinformation")
    private Result getUserInformation(){
        Result result = Result.ok();
        return result;
    }

    @RequestMapping("updateuserinformation")
    private Result updateUserInformation(Authentication authentication){
        return null;
    }
}
