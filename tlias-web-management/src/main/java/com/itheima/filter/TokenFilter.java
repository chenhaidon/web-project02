package com.itheima.filter;

import com.itheima.utils.CurrentHolder;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1. 获取请求url。
        String url = request.getRequestURL().toString();
        //
        //        //2. 判断请求url中是否包含login，如果包含，说明是登录操作，放行。
        if (url.contains("login")) { //登录请求
            log.info("登录请求 , 直接放行");
            filterChain.doFilter(request, response);
            return;
        }
        //3. 获取请求头中的令牌（token）。
        String jwt = request.getHeader("token");
        //4. 判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if (!StringUtils.hasLength(jwt)) {
            log.info("获取到jwt令牌为空, 返回错误结果");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }
        //5. 解析token，如果解析失败，返回错误结果（未登录）。
        try {
            Claims claims = JwtUtils.parseJWT(jwt);
            Integer empId = (Integer) claims.get("id");
            CurrentHolder.setCurrentId(empId);
            log.info("token解析成功,放行");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("解析令牌失败, 返回错误结果");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }
        //6. 放行。

        filterChain.doFilter(request, response);
        //7. 清空当前线程绑定的id
        CurrentHolder.remove();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
