package com.example.demopro.service.Impl;

import com.example.demopro.bean.BlogBean;
import com.example.demopro.dao.BlogDao;
import com.example.demopro.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class BlogServiceImpl implements BlogService {
    @Resource
    BlogDao blogDao;

    @Override
    public BlogBean GetBlogById(int id)  //通过ID查找
    {
        return blogDao.GetBlogById(id);
    }

    @Override
    public ArrayList<BlogBean> GetBlogsByUserName(String username)  //通过username查询
    {
        return blogDao.GetBlogsByUserName(username);
    }

    @Override
    public void AddBlog(BlogBean blogBean) {
        blogDao.AddBlog(blogBean);
    }
}
