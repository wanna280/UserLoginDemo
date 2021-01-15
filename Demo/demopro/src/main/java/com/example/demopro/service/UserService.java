package com.example.demopro.service;

import com.example.demopro.dao.UserDao;
import com.example.demopro.bean.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public UserBean GetUserById(int id) {  //通过id查询
        UserBean user;
        try{
            user = userDao.GetUserById(id);
        }catch (Exception ex){
            user = null;
        }
        return user;
    }

    public UserBean UserLogin(UserBean user) {
        UserBean userBean;
        try{
            userBean = userDao.UserLogin(user);
        }catch (Exception ex){
            userBean = null;
        }
        return userBean;
    }

    public UserBean GetUserByUserName(String username) {
        UserBean user;
        try{
            user = userDao.GetUserByUserName(username);
        }catch (Exception ex){
            user = null;
        }
        return user;
    }

    public boolean InsertOneUser(UserBean userBean) {
        try {   //插入成功return true
            userDao.InsertOneUser(userBean);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }
}
