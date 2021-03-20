package com.jian.propertymanagesystem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.jian.propertymanagesystem.entity.House;
import com.jian.propertymanagesystem.service.HouseService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/28 14:35
 * @Version
 */

/**
 * 导入房屋
 */
@Slf4j
public class HouseListener extends AnalysisEventListener<House> {
    private static final int BATCH_COUNT = 5;
    List<House> list = new ArrayList<>();
    HouseService houseService;

    public HouseListener(HouseService houseService){
        this.houseService = houseService;
    }

    @Override
    public void invoke(House house, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(house));
        list.add(house);
        if (list.size()>=BATCH_COUNT){
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("｛｝条数据，开始存储数据库",list.size());
    }

    private void saveData(){
        log.info("{}条数据，开始存储数据库！",list.size());
        houseService.saveBatch(list);
        log.info("存储数据库成功!");
    }
}
