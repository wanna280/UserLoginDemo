package com.example.demopro.controller.Blog;

import com.example.demopro.bean.BlogBean;
import com.example.demopro.service.Impl.BlogServiceImpl;
import com.example.demopro.service.Impl.RedisServiceImpl;
import com.example.demopro.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//博客的Controller
@RestController
public class BlogController {
    @Resource
    BlogServiceImpl blogService;
    @Resource
    RedisServiceImpl redisService;

    /**
     * 添加博客，使用POST请求
     *
     * @param content    博客的内容
     * @param blog_title 博客的标题
     * @return resultMap
     */
    @RequestMapping(value = "/blog", method = RequestMethod.POST) //POST方式请求
    public Map<String, Object> addBlog(String content, String blog_title) {
        //上下文中的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(content);
        System.out.println(blog_title);
        Map<String, Object> map = new HashMap<>();

        //根据SpringSecurity上下文得到认证的对象的用户名

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

            redisService.del("blog_all");
            redisService.del("blog_username_" + username);

            blogService.GetAllBlogs();
            blogService.GetBlogsByUserName(username);
        } catch (Exception ex) {
            map.put("status", false);
            map.put("msg", "提交失败");
            System.out.println("Error");
        }

        map.put("username", username);
        return map;

    }

    /**
     * 查询当前登录用户的所有博客，用于在myblogs页面中显示
     *
     * @return
     */
    @RequestMapping(value = "/blog_all_self", method = RequestMethod.GET) //Get方式请求
    public ArrayList<BlogBean> queryAll_Self() {  //根据用户名查询当前用户所写的全部的博客信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ArrayList<BlogBean> blogs = blogService.GetBlogsByUserName(username);  //调用service层的方法查询出来博客的列表
        return blogs;
    }

    /**
     * 通过博客的Id查询数据库，GET请求，用于从主页或者个人页中跳转到details页
     *
     * @param blog_id
     * @return
     */
    @RequestMapping(value = "/blog", method = RequestMethod.GET)
    public BlogBean queryById(int blog_id) {  //给定id去查询，比如用在博客的跳转到详情的页面
        BlogBean blogBean = blogService.GetBlogById(blog_id);
        return blogBean;
    }

    /**
     * 查询所有博客，用于在主页中显示，GET请求
     *
     * @return
     */
    @RequestMapping(value = "/blog_all", method = RequestMethod.GET)
    public ArrayList<BlogBean> queryAll() {
        return blogService.GetAllBlogs();
    }

    /**
     * 编辑博客，使用PUT请求
     *
     * @param blog_id    博客Id
     * @param content    博客的内容
     * @param blog_title 博客的标题
     * @return
     */
    @RequestMapping(value = "/blog", method = RequestMethod.PUT)
    public Map<String, Object> EditBlog(int blog_id, String content, String blog_title) {
        Map<String, Object> map = new HashMap<>();
        //上下文中的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Date now = new Date(System.currentTimeMillis());   //获取当前时间
        try {
            BlogBean blogBean = new BlogBean();
            blogBean.setId(blog_id);
            blogBean.setContent(content);
            blogBean.setUsername(username);
            blogBean.setPublishTime(now);
            blogBean.setBlog_title(blog_title);
            blogService.UpdateBlog(blogBean);
            map.put("msg", "提交成功");
            map.put("status", true);
            map.put("blog_id", blog_id);
            redisService.del("blog_all");
            redisService.del("blog_username_" + username);
            redisService.del("blog_id_" + blog_id);

            blogService.GetAllBlogs();
            blogService.GetBlogById(blog_id);
            blogService.GetBlogsByUserName(username);

        } catch (Exception ex) {
            map.put("msg", "提交失败");
            map.put("status", false);
        }
        return map;
    }

    @RequestMapping(value = "/blog/delete/{blog_id}", method = RequestMethod.DELETE)
    public Map<String, Object> DeleteBlogById(@PathVariable int blog_id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "删除成功");
        blogService.DeleteBlogById(blog_id);
        redisService.del("blog_all");
        redisService.del("blog_username_" + username);
        redisService.del("blog_id_" + blog_id);

        blogService.GetAllBlogs();
        blogService.GetBlogById(blog_id);
        blogService.GetBlogsByUserName(username);
        return map;
    }

}
