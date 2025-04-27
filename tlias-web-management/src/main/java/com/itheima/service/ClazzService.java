package com.itheima.service;

import com.itheima.pojo.Clazz;
import com.itheima.pojo.ClazzQueryParam;
import com.itheima.pojo.PageResult;

public interface ClazzService {
   PageResult clazzPage(ClazzQueryParam clazzQueryParam);

    void save(Clazz clazz);
}
