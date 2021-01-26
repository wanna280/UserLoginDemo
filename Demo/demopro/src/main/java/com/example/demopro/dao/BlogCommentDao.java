package com.example.demopro.dao;

import com.example.demopro.bean.CommentBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface BlogCommentDao {
    public CommentBean GetCommentById(int id);  //通过评论Id查询数据库

    public ArrayList<CommentBean> GetCommentsByBlogId(int blog_id);  //通过博客Id查询数据库

    public Boolean AddCommentToBlog(CommentBean commentBean); //将评论添加到数据库中对应的博客位置

    public Boolean DeleteCommentById(int id);  //删除评论

    public Boolean UpdateComment(CommentBean commentBean); //更新评论
}
