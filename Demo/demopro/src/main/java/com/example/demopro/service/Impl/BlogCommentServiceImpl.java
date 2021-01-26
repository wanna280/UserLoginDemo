package com.example.demopro.service.Impl;

import com.example.demopro.bean.CommentBean;
import com.example.demopro.dao.BlogCommentDao;
import com.example.demopro.service.BlogCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {
    @Resource
    BlogCommentDao blogCommentDao;

    @Override
    public CommentBean GetCommentById(int id) {
        return blogCommentDao.GetCommentById(id);
    }

    @Override
    public ArrayList<CommentBean> GetCommentsByBlogId(int blog_id) {
        return blogCommentDao.GetCommentsByBlogId(blog_id);
    }

    @Override
    public Boolean AddCommentToBlog(CommentBean commentBean) {
        return blogCommentDao.AddCommentToBlog(commentBean);
    }

    @Override
    public Boolean DeleteCommentById(int id) {
        return blogCommentDao.DeleteCommentById(id);
    }

    @Override
    public Boolean UpdateComment(CommentBean commentBean) {
        return blogCommentDao.UpdateComment(commentBean);
    }
}
