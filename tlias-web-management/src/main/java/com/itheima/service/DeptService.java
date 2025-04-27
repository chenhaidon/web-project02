package com.itheima.service;

import com.itheima.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     *
     * @return
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 新增部门
     * @param dept
     */
    void save(Dept dept);
    /**
     * 根据id查询部门
     */
    Dept findById(Integer id);

    void update(Dept dept);
}
