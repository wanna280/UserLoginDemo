package com.example.demopro.dao;

import com.example.demopro.bean.BlogBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BlogDao {
    public BlogBean GetBlogById(int id);  //通过ID查找
    public ArrayList<BlogBean> GetBlogsByUserName(String username);  //通过username查询
    public void AddBlog(BlogBean blogBean);  //新增博客
}
