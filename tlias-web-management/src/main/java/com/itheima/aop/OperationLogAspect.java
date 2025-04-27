package com.itheima.aop;

import com.itheima.mapper.OperateLogMapper;
import com.itheima.pojo.OperateLog;
import com.itheima.utils.CurrentHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.itheima.anno.LogOperation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法执行的开始时间
        long begin = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 获取方法执行的结束时间
        long end = System.currentTimeMillis();
        // 记录操作日志
        OperateLog operateLog = new OperateLog();
        operateLog.setOperateEmpId(getUserID()); // 设置操作员工ID
        operateLog.setOperateTime(LocalDateTime.now()); // 设置操作时间
        operateLog.setClassName(joinPoint.getTarget().getClass().getName()); // 设置操作的类名
        operateLog.setMethodName(joinPoint.getSignature().getName()); // 设置操作的方法名
        operateLog.setMethodParams(joinPoint.getArgs().toString()); // 设置方法参数
        operateLog.setReturnValue(result.toString()); // 设置方法返回值
        operateLog.setCostTime(end - begin); // 设置方法执行耗时
        operateLogMapper.insert(operateLog); // 保存操作日志
        return result;
    }
    public Integer getUserID(){
        Integer currentId = CurrentHolder.getCurrentId();
        return currentId;
    }

}
