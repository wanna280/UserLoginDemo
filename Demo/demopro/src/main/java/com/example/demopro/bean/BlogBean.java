package com.example.demopro.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogBean {
    int id;  //id
    String username;  //用户名
    String content;  //文章的内容
    String blog_title; //标题
    Date publishTime;  //发表的时间
    int is_delete;  //数据库中是否删除
}
