package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jian.propertymanagesystem.dto.HouseForm;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.entity.User;
import com.jian.propertymanagesystem.mapper.HouseDao;
import com.jian.propertymanagesystem.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/1 18:03
 * @Version
 */
@Service
@Component
public class HouseServiceImpl extends ServiceImpl<HouseDao,House> implements HouseService  {
    @Autowired
    private HouseDao houseDao;

//    public IPage<House> queryHouseByPage(HouseForm houseForm) {
//        QueryWrapper<House> queryWrapper = new QueryWrapper<>();
//        Page<House> page = new Page<>();
//        if (houseForm != null){
//            if (houseForm.getCurrentPage()!= null){
//                page.setCurrent(houseForm.getCurrentPage());
//            }else {
//                page.setCurrent(1);
//            }
//            if (houseForm.getSize()!=null){
//                page.setSize(houseForm.getSize());
//            }else {
//                page.setSize(10);
//            }
//        }
//        if (houseForm.getUnit() != null){
//            queryWrapper.eq("unit",houseForm.getUnit());
//        }
//        if (houseForm.getBuilding() != null){
//            queryWrapper.eq("building",houseForm.getBuilding());
//        }
//        if (houseForm.getFloor() != null){
//            queryWrapper.eq("floor",houseForm.getFloor());
//        }
//        if (houseForm.getRoom() != null){
//            queryWrapper.eq("room",houseForm.getRoom());
//        }
//        return houseDao.selectPage(page,queryWrapper);
//    }

    @Override
    public IPage<House> queryHouseByPage(HouseForm houseForm){
        Page<House> page = new Page<>();
        int currentPage = 1;
        int size = 10;
        if (houseForm != null){
            if (houseForm.getCurrentPage()!= null){
                currentPage = houseForm.getCurrentPage();
            }
            if (houseForm.getSize()!=null){
                size = houseForm.getSize();
            }
        }
        page.setCurrent(currentPage);
        page.setSize(size);
        return houseDao.queryHouse(page,houseForm);
    }


    @Override
    public List<House> queryAllHouse() {
        return houseDao.selectList(null);
    }
}
