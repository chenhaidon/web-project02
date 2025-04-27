package com.itheima.service;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.LoginInfo;
import com.itheima.pojo.PageResult;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface EmpService {
    /**
     * 分页查询
     *
     * @param page     页码
     * @param pageSize 每页显示的记录数
     * @return
     */
    PageResult page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     *
     * @param emp
     */
    void save(Emp emp) throws Exception;

    /**
     * 批量删除
     *
     * @param ids
     */
    void deleteByIds(ArrayList<Integer> ids);

    /**
     * 查询回显
     *
     * @param id
     * @return
     */
    Emp getById(Integer id);

    /**
     * 修改员工信息
     *
     * @param emp
     */
    void updateById(Emp emp);



    List<Emp> listAllEmpName();

    LoginInfo login(Emp emp);
}
