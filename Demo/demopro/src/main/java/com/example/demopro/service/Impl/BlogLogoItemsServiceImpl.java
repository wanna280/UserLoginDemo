package com.example.demopro.service.Impl;

import com.example.demopro.bean.BlogLogoItemsBean;
import com.example.demopro.dao.BlogLogoItemsDao;
import com.example.demopro.service.BlogLogoItemsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogLogoItemsServiceImpl implements BlogLogoItemsService {
    @Resource
    BlogLogoItemsDao blogLogoItemsDao;

    @Override
    public BlogLogoItemsBean GetBlogItems(int id) {
        return blogLogoItemsDao.GetBlogItems(id);
    }

    @Override
    public boolean DecreasingCommentNumbers(int id) {
        blogLogoItemsDao.DecreasingCommentNumbers(id);
        return true;
    }

    @Override
    public boolean IncreasingCommentNumbers(int id) {
        blogLogoItemsDao.IncreasingCommentNumbers(id);
        return true;
    }

    @Override
    public boolean DecreasingThumbsUpNumbers(int id) {
        blogLogoItemsDao.DecreasingThumbsUpNumbers(id);
        return true;
    }

    @Override
    public boolean IncreasingThumbsUpNumbers(int id) {
        blogLogoItemsDao.IncreasingThumbsUpNumbers(id);
        return true;
    }

    @Override
    public boolean DecreasingWatchingNumbers(int id) {
        blogLogoItemsDao.DecreasingWatchingNumbers(id);
        return true;
    }

    @Override
    public boolean IncreasingWatchingNumbers(int id) {
        blogLogoItemsDao.IncreasingWatchingNumbers(id);
        return true;
    }
}
