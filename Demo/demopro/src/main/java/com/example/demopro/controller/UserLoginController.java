package com.example.demopro.controller;

import com.example.demopro.entity.UserBean;
import com.example.demopro.service.UserService;
import com.example.demopro.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserLoginController {
    @Autowired
    UserService userService;


    //使用wanna登录
    //先访问login会返回404，再访问test可以访问？？
    //使用baby登录
    //先访问login会返回404，再访问test会返回403
    //也就是说，实际上login之后是真的登录了的？？？
    @RequestMapping("/login")
    public String Login(UserBean userBean) {
        return "login.html";
    }

    @ResponseBody
    @RequestMapping("/success")
    public Map<String, Object> success_login() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //System.out.println(username);
        Map<String,Object> claims = new HashMap<>();
        claims.put("username",username);  //用户名
        claims.put("admin",true);  //是admin权限
        String token = JwtUtil.GenerateToken(claims);  //生成jwt字符串

        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("msg", "登录成功");
        //map.put("username", username);
        map.put("token",token);
        return map;
    }

    @ResponseBody
    @RequestMapping("/failed")
    public Map<String, Object> failed_login() {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("msg", "登录失败");
        map.put("username", null);
        return map;
    }

}
