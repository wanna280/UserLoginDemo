package com.example.demopro.controller.Blog;

import com.example.demopro.bean.CommentBean;
import com.example.demopro.service.Impl.BlogCommentServiceImpl;
import com.example.demopro.service.Impl.BlogLogoItemsServiceImpl;
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

//博客评论的Controller
@RestController
public class BlogCommentController {
    @Resource
    private BlogCommentServiceImpl blogCommentService;
    @Resource
    private BlogLogoItemsServiceImpl blogLogoItemsService;

    /**
     * 给定blog_id，查询数据库中的评论，将评论展示再details页中
     *
     * @param blog_id 博客数据库中的Id
     * @return resultMap
     */
    @RequestMapping(value = "/blog/comments/{blog_id}", method = RequestMethod.GET)
    public Map<String, Object> GetCommentsByBlogId(@PathVariable int blog_id) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "请求成功");
        //创建Comment的pojo
        ArrayList<CommentBean> commentBeans = blogCommentService.GetCommentsByBlogId(blog_id);

        map.put("data", commentBeans);
        return map;
    }

    /**
     * 给定博客的数据库Id和评论的内容，将内容写入数据库
     *
     * @param blog_id 博客的数据库id
     * @param content 评论的内容
     * @return
     */
    @RequestMapping("/blog/comments/{blog_id}/{content}")
    public Map<String, Object> AddComment(@PathVariable int blog_id, @PathVariable String content) {
        //获取当前登录的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //创建一个map，存放返回给前端的json数据
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("msg", "发送留言成功");

        //创建一个评论的pojo
        CommentBean commentBean = new CommentBean();
        commentBean.setBlog_id(blog_id);
        commentBean.setContent(content);
        commentBean.setUsername(username);
        commentBean.setPublishTime(now);

        //操作数据库并将留言数量增加1
        blogCommentService.AddCommentToBlog(commentBean);
        blogLogoItemsService.IncreasingCommentNumbers(blog_id);
        return map;  //return
    }

}
