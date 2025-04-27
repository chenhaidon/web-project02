package com.itheima.interceptor;

import com.itheima.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
//@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前请求的完整URL地址
        String url = request.getRequestURL().toString();

        // 判断是否为登录请求
        if (url.contains("login")) {
            // 如果是登录请求，记录日志并放行
            log.info("登录操作，放行...");
            return true;
        }

        // 从请求头中获取token
        String token = request.getHeader("token");

        // 检查token是否为空
        if (!StringUtils.hasLength(token)) {
            // 如果token为空，记录日志并返回401未授权状态
            log.info("获取到jwt令牌为空, 返回错误结果");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }

        try {
            // 尝试解析JWT令牌
            JwtUtils.parseJWT(token);
        } catch (Exception e) {
            // 如果解析失败，打印异常并记录日志
            e.printStackTrace();
            log.info("解析令牌失败, 返回错误结果");
            // 返回401未授权状态
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }

        // 如果令牌验证通过，记录日志并放行
        log.info("令牌合法, 放行");
        return true;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
