package com.itheima.mapper;

import com.itheima.pojo.Emp;
import com.itheima.pojo.EmpQueryParam;
import com.itheima.pojo.JobOption;
import org.apache.ibatis.annotations.*;
/**
 * 员工管理mapper
 */

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    @Select("SELECT * FROM emp")
    List<Emp> listAllName();
    /**
     * 查询所有的员工信息，如果员工关联了部门，也要查询出部门名称
     *
     * @return 员工信息列表
     */
    List<Emp> list(EmpQueryParam empQueryParam);

    @Select("SELECT COUNT(*) FROM emp")
    public Long count();

    /**
     * 新增员工数据
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    /**
     * 批量删除基本员工
     *
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

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

    /**
     * 统计个职位数量
     */
    @MapKey("total")
    List<Map<String, Object>> getEmpJobData();

    @MapKey("name")
    List<Map> getEmpGenderData();

    Emp getUsernameAndPassword(Emp emp);
}

