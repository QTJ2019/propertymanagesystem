package com.jian.propertymanagesystem.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qtj
 * @Date: 2020/12/27 11:05
 * @Version
 */
@Data
public class Result {
    private boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data=new HashMap<>();

    private Result(){}

    //成功时调用的
    public static Result ok(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功");
        return result;
    }
    //失败时调用的
    public static Result error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setMessage("失败");
        result.setCode(ResultCode.ERROR);
        return result;
    }

    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> maps){
        this.setData(maps);
        return this;
    }
}
