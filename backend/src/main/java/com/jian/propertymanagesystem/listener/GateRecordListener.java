package com.jian.propertymanagesystem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.jian.propertymanagesystem.dto.ExcelFee;
import com.jian.propertymanagesystem.entity.Fee;
import com.jian.propertymanagesystem.entity.GateRecord;
import com.jian.propertymanagesystem.service.FeeService;
import com.jian.propertymanagesystem.service.GateRecordService;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/20 16:26
 * @Version
 */
@Slf4j
public class GateRecordListener extends AnalysisEventListener<GateRecord> {
    private static final int BATCH_COUNT = 5;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
    List<GateRecord> list = new ArrayList<>();
    private GateRecordService gateRecordService;

    public GateRecordListener(GateRecordService gateRecordService){
        this.gateRecordService = gateRecordService;
    }

    @Override
    public void invoke(GateRecord gateRecord, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSON.toJSONString(gateRecord));
        list.add(gateRecord);
        if(list.size()>=BATCH_COUNT){
            saveData();
            list.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完成");

    }

    private void saveData(){
        log.info("{}条数据，开始存储数据库！",list.size());
        gateRecordService.saveBatch(list);
        log.info("存储数据库成功!");
    }
}
