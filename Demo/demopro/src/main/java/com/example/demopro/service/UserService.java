package com.example.demopro.service;

import com.example.demopro.bean.UserBean;

public interface UserService {
    public UserBean GetUserById(int id);  //通过ID查询用户

    public UserBean UserLogin(UserBean user);  //根据User对象查询用户

    public UserBean GetUserByUserName(String username);  //根据UserName查询用户

    public boolean InsertOneUser(UserBean userBean); //插入一个UserBean到User表
}
