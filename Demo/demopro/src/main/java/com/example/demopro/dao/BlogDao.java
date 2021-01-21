package com.example.demopro.dao;

import com.example.demopro.bean.BlogBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface BlogDao {
    public BlogBean GetBlogById(int id);  //通过ID查找

    public ArrayList<BlogBean> GetBlogsByUserName(String username);  //通过username查询

    public void AddBlog(BlogBean blogBean);  //新增博客

    public ArrayList<BlogBean> GetAllBlogs();  //查询所有的博客

    public void UpdateBlog(BlogBean blogBean);  //更新一条博客记录
}
