package com.example.demopro.controller.Blog;

import com.example.demopro.bean.BlogBean;
import com.example.demopro.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    //使用Restful风格
    @RequestMapping(value = "/blog", method = RequestMethod.POST) //POST方式请求
    public Map<String, Object> AddBlog(String content, String blog_title) {
        System.out.println(content);
        System.out.println(blog_title);
        Map<String, Object> map = new HashMap<>();

        //根据SpringSecurity上下文得到认证的对象的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Date now = new Date(System.currentTimeMillis());   //获取当前时间
        try {
            BlogBean blogBean = new BlogBean();
            blogBean.setId(0);
            blogBean.setContent(content);
            blogBean.setUsername(username);
            blogBean.setPublishTime(now);
            blogBean.setBlog_title(blog_title);

            blogService.AddBlog(blogBean);  //调用service层的方法写入数据库

            map.put("status", true);
            map.put("msg", "提交成功");
        } catch (Exception ex) {
            map.put("status", false);
            map.put("msg", "提交失败");
            System.out.println("Error");
        }

        map.put("username", username);
        return map;

    }

    @RequestMapping(value = "/blog_all", method = RequestMethod.GET) //Get方式请求
    public ArrayList<BlogBean> QueryAll() {  //根据用户名查询当前用户所写的全部的博客信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ArrayList<BlogBean> blogs = blogService.GetBlogsByUserName(username);  //调用service层的方法查询出来博客的列表
        return blogs;
    }

    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public BlogBean QueryById(int blog_id) {  //给定id去查询，比如用在博客的跳转到详情的页面
        BlogBean blogBean = blogService.GetBlogById(blog_id);
        return blogBean;
    }

}
