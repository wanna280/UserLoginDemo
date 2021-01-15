package com.example.demopro.service;

import com.example.demopro.bean.BlogBean;
import com.example.demopro.dao.BlogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BlogService {
    @Autowired
    BlogDao blogDao;

    public BlogBean GetBlogById(int id)  //通过ID查找
    {
        return blogDao.GetBlogById(id);
    }

    public ArrayList<BlogBean> GetBlogsByUserName(String username)  //通过username查询
    {
        return blogDao.GetBlogsByUserName(username);
    }

    public void AddBlog(BlogBean blogBean){
        blogDao.AddBlog(blogBean);
    }
}
