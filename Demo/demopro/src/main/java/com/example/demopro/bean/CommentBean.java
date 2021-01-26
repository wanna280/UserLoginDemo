package com.example.demopro.bean;

import java.util.Date;

public class CommentBean {  //评论的POJO
    private int id;
    private int blog_id;  //是哪篇博客的评论，对应博客的id
    private String username;  //用户名
    private Date publishTime; //发布时间
    private String content;  //内容
    private String others1;  //其他
    private String others2;  //其他
    private String others3;  //其他
    private String others4;  //其他

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlog_id() {
        return blog_id;
    }

    public void setBlog_id(int blog_id) {
        this.blog_id = blog_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
