package com.example.demopro.controller;

import com.example.demopro.entity.UserBean;
import com.example.demopro.entity.UserRolesBean;
import com.example.demopro.service.UserRolesService;
import com.example.demopro.service.UserService;
import com.example.demopro.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserLoginController {
    @Autowired
    UserService userService;
    @Autowired
    UserRolesService userRolesService;

    @RequestMapping("/login")
    public String Login(UserBean userBean) {
        return "login.html";
    }

    @ResponseBody
    @RequestMapping("/success")
    public Map<String, Object> Success_Login() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //System.out.println(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);  //用户名
        claims.put("admin", true);  //是admin权限
        String token = JwtUtil.GenerateToken(claims);  //生成jwt字符串

        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("msg", "登录成功");
        //map.put("username", username);
        map.put("token", token);
        return map;
    }

    @ResponseBody
    @RequestMapping("/failed")
    public Map<String, Object> Failed_Login() {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("msg", "登录失败");
        map.put("username", null);
        return map;
    }

    @ResponseBody
    @RequestMapping("/register")
    public Map<String, Object> Register(String username, String password) {
        String passwd = new BCryptPasswordEncoder().encode(password);  //将密码加密
        String roles = "user";  //注册用户默认权限都是user
        Map<String, Object> map = new HashMap<>();
        UserBean userBean = new UserBean(0, username, passwd);  //创建用户的实体，后端数据库自增id，所以id不用管
        UserRolesBean userRolesBean = new UserRolesBean(0, username, roles);  //创建角色的实体，后端数据库自增id，所以id不用管
        UserBean user_db = userService.GetUserByUserName(username);  //从后端数据库查询这个用户名的数据
        if (user_db == null) {  //如果不是空，数据库已经存在这个用户了
            boolean status1 = userService.InsertOneUser(userBean);  //插入用户表
            boolean status2 = userRolesService.InsertOneUserRole(userRolesBean); //插入角色表
            if (status1 == true && status2 == true) {  //如果两条语句都执行成功
                map.put("status", true);
                map.put("msg", "注册成功");
                map.put("username", username);
            } else {
                map.put("status", false);
                map.put("msg", "注册失败");
                map.put("reason","");
            }
        }else{
            map.put("status", false);
            map.put("msg", "注册失败");
            map.put("reason","用户名已经存在");
        }


        return map;

    }

    @ResponseBody
    @RequestMapping("/verify")
    public Map<String,Object> Verify(){
        Map<String,Object> map = new HashMap<>();
        map.put("status",true);
        map.put("msg","认证成功");
        return map;
    }

}
