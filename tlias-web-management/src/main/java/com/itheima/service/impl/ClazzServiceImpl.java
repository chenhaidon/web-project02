package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.ClazzMapper;
import com.itheima.pojo.*;
import com.itheima.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult clazzPage(ClazzQueryParam clazzQueryParam) {
        //1. 开启分页查询
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        //2. 执行查询
        List<Clazz> clazzList = clazzMapper.list(clazzQueryParam);
        // ... existing code ...
        for (Clazz clazz : clazzList) {
            if (clazz.getEndDate() != null && clazz.getEndDate().isBefore(LocalDate.now())) {
                clazz.setStatus("已结课");
            } else if (clazz.getBeginDate() != null && clazz.getBeginDate().isAfter(LocalDate.now())) {
                clazz.setStatus("未开班");
            } else {
                clazz.setStatus("在读");
            }
        }
// ... existing code ...

        //3. 封装分页结果
        Page<Clazz> p = (Page<Clazz>) clazzList;


        //3. 封装结果
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 新增班级
     *
     * @param clazz
     */
    @Override
    public void save(Clazz clazz) {


        //1. 补全属性值
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        //2. 调用mapper层方法
        clazzMapper.insert(clazz);


    }
}
