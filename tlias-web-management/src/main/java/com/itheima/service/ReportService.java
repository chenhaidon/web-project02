package com.itheima.service;

import com.itheima.pojo.JobOption;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface ReportService {
     /**
      * 添加各个职位的数量
      * @return
      */
     JobOption getEmpJobData();

     List<Map> getEmpGenderData();
}
