package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.mapper.EmpExprMapper;
import com.itheima.mapper.EmpMapper;
import com.itheima.pojo.*;
import com.itheima.service.EmpLogService;
import com.itheima.service.EmpService;
import com.itheima.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 员工管理业务层实现类
 */
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public PageResult page(EmpQueryParam empQueryParam) {
            //1. 开启分页查询
            PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
            //2. 执行查询
            List<Emp> empList = empMapper.list(empQueryParam);

            //3. 封装分页结果
            Page<Emp> p = (Page<Emp>) empList;


            //3. 封装结果
            return new PageResult(p.getTotal(), p.getResult());


    }

    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Emp emp) {

        try {
            //1. 补全属性值
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            //2. 调用mapper层方法
            empMapper.insert(emp);
            //模拟：异常发生
//            int i = 1/0;
            //3. 保存员工的工作经历信息 - 批量 (稍后完成)
            Integer empId = emp.getId();
            List<EmpExpr> exprList = emp.getExprList();
            if (!CollectionUtils.isEmpty(exprList)) {
                exprList.forEach(empExpr -> empExpr.setEmpId(empId));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), emp.toString());
            empLogService.insertLog(empLog);
        }
    }

    @Override
    @Transactional
    public void deleteByIds(ArrayList<Integer> ids) {
        //1. 根据ID批量删除员工基本信息
        empMapper.deleteByIds(ids);
        //2. 根据ID批量删除员工的工作经历信息
        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Override
    @Transactional
    public void updateById(Emp emp) {
        // //1. 根据ID更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);
        //2. 根据员工ID删除员工的工作经历信息 【删除老的】
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));
        //3. 保存员工的工作经历信息 - 批量 (稍后完成)
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }

    }

    @Override
    public List<Emp> listAllEmpName() {
       List<Emp> list= empMapper.listAllName();
       return list;
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp empLogin=empMapper.getUsernameAndPassword(emp);
        if (empLogin!=null){
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("id", empLogin.getId());
            dataMap.put("username", empLogin.getUsername());
            String generateJwt = JwtUtils.generateJwt(dataMap);
            LoginInfo loginInfo=new LoginInfo(empLogin.getId(),empLogin.getUsername(),empLogin.getName(),generateJwt);
            return loginInfo;
        }
        return null;
    }


}
