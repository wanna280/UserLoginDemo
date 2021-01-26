package com.example.demopro.service;

import com.example.demopro.bean.CommentBean;

import java.util.ArrayList;

//博客的评论service interface
public interface BlogCommentService {
    public CommentBean GetCommentById(int id);  //通过评论的Id查询数据库

    public ArrayList<CommentBean> GetCommentsByBlogId(int blog_id);  //通过博客的Id查询数据库

    public Boolean AddCommentToBlog(CommentBean commentBean); //将博客添加到数据库中

    public Boolean DeleteCommentById(int id);  //通过评论的Id删除掉评论的数据

    public Boolean UpdateComment(CommentBean commentBean);  //更新评论

}
