package com.example.demopro.service;

import com.example.demopro.bean.BlogLogoItemsBean;

public interface BlogLogoItemsService {
    public BlogLogoItemsBean GetBlogItems(int id);   //查询

    public boolean DecreasingCommentNumbers(int id);   //减少评论数量

    public boolean IncreasingCommentNumbers(int id);   //增加评论的数量

    public boolean DecreasingThumbsUpNumbers(int id);  //减少点赞的数量

    public boolean IncreasingThumbsUpNumbers(int id);  //增加点赞的数量

    public boolean DecreasingWatchingNumbers(int id);  //减少观看的数量

    public boolean IncreasingWatchingNumbers(int id);  //增加观看的数量
}
