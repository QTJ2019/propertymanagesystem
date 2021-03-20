package com.jian.propertymanagesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.entity.Complaint;
import com.jian.propertymanagesystem.entity.ComplaintImg;
import com.jian.propertymanagesystem.mapper.ComplaintDao;
import com.jian.propertymanagesystem.mapper.ComplaintImgDao;
import com.jian.propertymanagesystem.service.ComplaintService;
import com.jian.propertymanagesystem.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: qtj
 * @Date: 2021/2/11 9:05
 * @Version
 */
@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    ComplaintDao complaintDao;
    @Autowired
    ComplaintImgDao complaintImgDao;

    /**
     *
     * @param complaint
     * @param complaintImgs
     * @return 0表示失败，1表示成功
     */
    @Override
    @Transactional
    public int insertComplaintRecord(Complaint complaint, List<MultipartFile> complaintImgs) throws RuntimeException,IOException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String baseImgPath = PathUtil.getImgBasePath();
        String dateTime = simpleDateFormat.format(new Date());

        if (complaint != null && complaint.getUserPhone()!= null){
            complaint.setCreatedTime(new Date());
        } else {
            return 0;
        }

        String path = baseImgPath+ PathUtil.seperator+dateTime;
        File file = new File(baseImgPath+ PathUtil.seperator+dateTime);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            System.out.println("dao层执行了插入语句");
            complaintDao.insert(complaint);
            String fileName= null;
            if (complaintImgs == null) return 1;
            for(int i=0;i<complaintImgs.size();i++){
                fileName = UUID.randomUUID().toString().replace("-","")+"_"+complaintImgs.get(i).getOriginalFilename();
                String addr = PathUtil.seperator + dateTime + PathUtil.seperator + fileName;
                ComplaintImg complaintImg = new ComplaintImg();
                complaintImg.setComplaintId(complaint.getId());
                complaintImg.setAddr(addr);
                complaintImg.setCreatedTime(new Date());
                complaintImgDao.insert(complaintImg);

            }
            if (complaintImgs == null) return 1;
            for (int i=0;i<complaintImgs.size();i++)
                complaintImgs.get(i).transferTo(new File(path,fileName));
        } catch (RuntimeException e) {
            throw  e;
        }catch (IOException e){
            throw  e;
        }
        return 1;
    }

    @Override
    public IPage<Complaint> queryAllRecords(Page<Complaint> page,String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("state","id");
        if (phone != null &&  phone != ""){
            queryWrapper.like("user_phone",phone);
        }
        return complaintDao.selectPage(page,queryWrapper);
    }

    @Override
    public IPage<Complaint> queryUserRecords(Page<Complaint> page, String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_phone",phone);
        return complaintDao.selectPage(page, queryWrapper);
    }

    @Override
    public int updateState(Integer complaintId,Integer handlerId) {
        UpdateWrapper<Complaint> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",complaintId);
        updateWrapper.set("state",1);
        updateWrapper.set("handler_id",handlerId);
        Date date = new Date();
        updateWrapper.set("handled_time",date);
        return complaintDao.update(null,updateWrapper);
    }
}
