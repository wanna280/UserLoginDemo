package com.example.demopro.service;

import com.example.demopro.dao.UserDao;
import com.example.demopro.entity.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public ArrayList<UserBean> GetUserById(int id) {  //通过id查询
        return userDao.GetUserById(id);
    }

    public ArrayList<UserBean> UserLogin(UserBean user) {
        return userDao.UserLogin(user);
    }

    public ArrayList<UserBean> GetUserByUserName(String username) {
        return userDao.GetUserByUserName(username);
    }
}
