package com.itheima.controller;
import com.itheima.pojo.*;
import com.itheima.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("clazzs")
public class ClazzController {
    /**
     * 分页查询
     *
     * @param clazzQueryParam
     * @return
     */
    @Autowired
    private ClazzService clazzService;
    @GetMapping
    public Result clazzPage(ClazzQueryParam clazzQueryParam) {
        log.info("查询请求参数： {}", clazzQueryParam);
        PageResult pageResult = clazzService.clazzPage(clazzQueryParam);
        log.info("查询结果：{}", pageResult);
        return Result.success(pageResult);
    }
    /**
     * 新增班级
     */
    @PostMapping
    public Result save(@RequestBody Clazz clazz){
        log.info("新增clazz: {}" , clazz);
        clazzService.save(clazz);
        return Result.success();

    }
}
