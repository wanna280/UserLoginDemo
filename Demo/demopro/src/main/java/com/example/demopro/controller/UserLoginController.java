package com.example.demopro.controller;

import com.example.demopro.entity.UserBean;
import com.example.demopro.entity.UserRolesBean;
import com.example.demopro.service.UserRolesService;
import com.example.demopro.service.UserService;
import com.example.demopro.utils.CaptchaUtils;
import com.example.demopro.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserLoginController {
    @Autowired
    UserService userService;
    @Autowired
    UserRolesService userRolesService;

    @RequestMapping("/login")   //前端请求登录的页面
    public String Login(UserBean userBean) {
        return "login.html";
    }

    @ResponseBody
    @RequestMapping("/success")  //用户成功登录，后端跳转到success页面
    public Map<String, Object> Success_Login() {

        //读取登录用户的信息（用户名）
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //System.out.println(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);  //用户名
        claims.put("admin", true);  //是admin权限
        String token = JwtUtils.GenerateToken(claims);  //使用工具生成jwt字符串

        Map<String, Object> map = new HashMap<>();
        map.put("status", true);  //往响应中加入状态true，表示请求成功
        map.put("msg", "登录成功");  //往响应中增加信息msg表示登录成功
        //map.put("username", username);
        map.put("token", token);  //往响应中增加token信息，从而存储在客户端的localStorage中
        return map;  //返回响应的字典
    }

    @ResponseBody
    @RequestMapping("/failed")  //用户登录失败的处理接口，登录失败时后端跳转页面
    public Map<String, Object> Failed_Login() {
        //String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);  //往响应中写入status-false表示登录失败
        map.put("msg", "登录失败");  //往响应中写入msg表示登录失败
        map.put("username", null);  //往响应中写入用户名，null
        return map;  //返回响应的字典
    }

    @ResponseBody
    @RequestMapping("/register")  //用户注册接口，前端的注册页面提交的表单，这里写入数据库
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
                map.put("reason", "");
            }
        } else {
            map.put("status", false);
            map.put("msg", "注册失败");
            map.put("reason", "用户名已经存在");
        }


        return map;

    }

    @ResponseBody
    @RequestMapping("/verify")
    public Map<String, Object> Verify() {
        //前端每次请求页面之前请求这个接口判断是否登录成功，如果请求这个接口都失败了，说明没登录
        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("msg", "认证成功");
        return map;
    }

    @ResponseBody
    @RequestMapping("/captcha")  //返回一个html页面，并且是图片格式的
    public Map<String,Object> Captcha(HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> map = new HashMap<>();

        Jedis jedis = new Jedis("47.107.157.20", 6379);
        jedis.auth("123456");
        CaptchaUtils vc = new CaptchaUtils();
        String captchaString = "";  //验证码的字符
        try {
            UUID uuid = UUID.randomUUID();
            String code_base64 = vc.BufferedImageToBase64(vc.getImage());

            map.put("uuid",uuid.toString());
            map.put("captcha_base64",code_base64);
            captchaString = vc.getText();
            jedis.set("capt_key_" + uuid.toString(), captchaString);
            jedis.expire("capt_key_" + uuid.toString(),60);  //验证码60秒过期
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
