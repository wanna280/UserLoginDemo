package com.example.demopro.config;

import com.example.demopro.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求，放行/login，不然无法登录，其他接口登录认证
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**")   //拦截所有路径
                .excludePathPatterns("/login")  //放行/login
                .excludePathPatterns("/success") //放行/success
                .excludePathPatterns("/failed") //放行/failed
                .excludePathPatterns("/register");  //放行register
    }
}

//1.如果拦截器设置放行了某些路径，这些路径不会经过过滤器Filter
//2.如果拦截器没有放行的路径，这些路径都会经过过滤器Filter
//3.可以过滤器进行判断token，因为在拦截器中已经放行了/login，/success，/failed，/register