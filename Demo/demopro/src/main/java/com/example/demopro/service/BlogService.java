package com.example.demopro.service;

import com.example.demopro.bean.BlogBean;

import java.util.ArrayList;

public interface BlogService {
    public BlogBean GetBlogById(int id);  //通过ID查找

    public ArrayList<BlogBean> GetBlogsByUserName(String username);  //通过username查询

    public void AddBlog(BlogBean blogBean);  //插入一个BlogBean
}
