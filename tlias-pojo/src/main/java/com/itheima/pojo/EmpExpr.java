package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 员工工作经历
 */
@Data
public class EmpExpr {
    private Integer id;
    private  Integer empId;
    private LocalDate begin;
    private LocalDate end;
    private String company;
    private String job;
}
