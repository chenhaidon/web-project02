package com.itheima.controller;

import com.itheima.anno.LogOperation;
import com.itheima.pojo.Dept;
import com.itheima.pojo.Result;
import com.itheima.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;
    @GetMapping
    public Result list(){
        List<Dept> deptList = deptService.findAll();
        log.info("查询部门列表");
        return Result.success(deptList);
    }
    @DeleteMapping
   public Result delete(Integer id){
        log.info("根据id删除部门, id: {}" , id);
        deptService.deleteById(id);
        return Result.success();
    }
    /**
     * 新增部门 - POST http://localhost:8080/depts   请求参数：{"name":"研发部"}
     */
    @PostMapping
    @LogOperation
    public Result save(@RequestBody Dept dept){
        log.info("新增部门, dept: {}" , dept);
        deptService.save(dept);
        return Result.success();

    }
    /**
     * 根据ID查询 - GET http://localhost:8080/depts/1
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
//        System.out.println("根据ID查询, id=" + id);
        log.info("根据ID查询, id: {}" , id);
        Dept dept = deptService.findById(id);
        return Result.success(dept);
    }
    /**
    /**
     * 修改部门 - PUT URL_ADDRESS     * 修改部门 - PUT http://localhost:8080/depts
     * 请求参数：{"id":1,"name":"市场部","updateTime":"2024-03-17 16:42:38","createTime":"2024-03-17 16:42:38"}
     */
    @PutMapping
    public Result update(@RequestBody Dept dept){
        log.info("修改部门, dept: {}" , dept);
        deptService.update(dept);
        return Result.success();
    }
}
