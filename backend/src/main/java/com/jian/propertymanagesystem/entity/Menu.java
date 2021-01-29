package com.jian.propertymanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/1/23 22:51
 * @Version
 */
@Data
@TableName("t_user")
public class Menu {
    @TableId
    private int menuId;
    private String menuName;
    private String path;
    private List<Menu> children;

}
