package com.example.demopro.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
