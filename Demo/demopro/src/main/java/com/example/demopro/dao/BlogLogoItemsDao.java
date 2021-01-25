package com.example.demopro.dao;

import com.example.demopro.bean.BlogLogoItemsBean;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogLogoItemsDao {
    public BlogLogoItemsBean GetBlogItems(int id);   //查询
    public void DecreasingCommentNumbers(int id);   //减少评论数量
    public void IncreasingCommentNumbers(int id);   //增加评论的数量
    public void DecreasingThumbsUpNumbers(int id);  //减少点赞的数量
    public void IncreasingThumbsUpNumbers(int id);  //增加点赞的数量
    public void DecreasingWatchingNumbers(int id);  //减少观看的数量
    public void IncreasingWatchingNumbers(int id);  //增加观看的数量
}
