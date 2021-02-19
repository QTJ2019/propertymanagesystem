package com.jian.propertymanagesystem.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.jian.propertymanagesystem.dto.ExcelFee;
import com.jian.propertymanagesystem.entity.Fee;
import com.jian.propertymanagesystem.service.FeeService;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/12 22:41
 * @Version
 */
@Slf4j
public class FeeListener extends AnalysisEventListener<ExcelFee> {
    private static final int BATCH_COUNT = 5;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
    List<Fee> list = new ArrayList<>();
    List<ExcelFee> excelFeeList = new ArrayList<>();
    private FeeService feeService;
    public FeeListener(FeeService feeService){
        this.feeService = feeService;
    }

    @Override
    public void invoke(ExcelFee excelFee, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}",JSON.toJSONString(excelFee));
        excelFeeList.add(excelFee);
        if(list.size()>=BATCH_COUNT)
            saveData();
        list.clear();
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
        log.info("所有数据解析完成");
    }

    private void saveData() {

        try {
            excelFeeTransferToFee();
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        log.info("{}条数据，开始存储数据库！",list.size());
        feeService.saveBatch(list);
        log.info("存储数据库成功!");
    }

    /**
     * 将ExcelFeeList转换成FeeList
     */
    private void excelFeeTransferToFee() throws ParseException {
        log.info("{}条ExcelFee开始转换成Fee",excelFeeList.size());
        for (int i=0;i<excelFeeList.size();i++){
            Fee fee = new Fee();
            ExcelFee excelFee = excelFeeList.get(i);
            fee.setId(excelFee.getId());
            fee.setHouseId(excelFee.getHouseId());
            fee.setPrice(excelFee.getPrice());
            fee.setNumber(excelFee.getNumber());
            fee.setTotal(excelFee.getTotal());
            fee.setType(excelFee.getType());
            fee.setTime(simpleDateFormat.parse(excelFee.getTime()));
            list.add(fee);
        }
        log.info("转换数据成功");
    }

}
