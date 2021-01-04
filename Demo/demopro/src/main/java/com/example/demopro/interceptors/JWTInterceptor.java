package com.example.demopro.interceptors;

import com.alibaba.fastjson.JSON;
import com.example.demopro.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {   //实现拦截器接口

    //先经历过滤器，再经历拦截器？？？
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();   //map

        if(request.getRequestURI().equals("/login") ||request.getRequestURI().equals("/api/login")){
            System.out.println("Incept-"+request.getRequestURI());
            return true;  //放行
        }
        String token = request.getHeader("token");  //获取请求头当中的token
        //System.out.println("token----"+token);
        try {
            Claims claims = JwtUtil.VerifyJwt(token);  //校验token
            claims.get("username");  //尝试去获取sub，获取失败则失败
            return true;  //校验成功，直接放行（return true）
        } catch (Exception ex) {
            map.put("msg", "请求失败");
            map.put("Ex", ex.getMessage());
            //ex.printStackTrace();
        }
        String json_str = JSON.toJSONString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(json_str);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
