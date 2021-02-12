package com.jian.propertymanagesystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jian.propertymanagesystem.entity.Complaint;
import com.jian.propertymanagesystem.entity.ComplaintImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Author: qtj
 * @Date: 2021/2/11 9:00
 * @Version
 */
public interface ComplaintService {
    int insertComplaintRecord(Complaint complaint, List<MultipartFile> complaintImgs)throws IOException;

    IPage<Complaint> queryAllRecords(Page<Complaint> page);
}
