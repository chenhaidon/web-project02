package com.itheima.controller;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;
import com.itheima.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryParam empQueryParam) {
        log.info("查询请求参数： {}", empQueryParam);
        PageResult pageResult = empService.page(empQueryParam);
        log.info("查询结果：{}", pageResult);
        return Result.success(pageResult);
    }

    @GetMapping("/list")
    public Result page() {
        log.info("查询请求参数： {}");
        List<Emp> emps = empService.listAllEmpName();

        return Result.success(emps);
    }

    /**
     * 添加员工
     */
    @PostMapping
    public Result save(@RequestBody Emp emp) throws Exception {
        log.info("请求参数emp: {}", emp);
        empService.save(emp);
        return Result.success();
    }

    /**
     * 批量删除员工
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam ArrayList<Integer> ids) {
        log.info("删除员工id: {}", ids);
        empService.deleteByIds(ids);
        return Result.success();
    }

    /**
     * 查询回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable  Integer id) {
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }
    /**
     * 修改员工
     * @param emp
     * @return
     */
    @PutMapping
    public Result updateById(@RequestBody Emp emp) {
        log.info("请求参数emp: {}", emp);
        empService.updateById(emp);
        return Result.success();
    }
}
