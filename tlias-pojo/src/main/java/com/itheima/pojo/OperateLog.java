package com.itheima.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLog {
      private Integer id; // 操作日志ID
    private Integer operateEmpId; // 操作员工ID
    private LocalDateTime operateTime; // 操作时间
    private String className; // 操作的类名
    private String methodName; // 操作的方法名
    private String methodParams; // 方法参数
    private String returnValue; // 方法返回值
    private Long costTime; // 方法执行耗时（单位：毫秒）

}
