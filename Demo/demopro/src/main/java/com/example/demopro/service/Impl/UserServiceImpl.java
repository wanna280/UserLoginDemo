package com.example.demopro.service.Impl;

import com.example.demopro.dao.UserDao;
import com.example.demopro.bean.UserBean;
import com.example.demopro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;

    @Override
    public UserBean GetUserById(int id) {  //通过id查询
        UserBean user;
        try {
            user = userDao.GetUserById(id);
        } catch (Exception ex) {
            user = null;
        }
        return user;
    }

    @Override
    public UserBean UserLogin(UserBean user) {
        UserBean userBean;
        try {
            userBean = userDao.UserLogin(user);
        } catch (Exception ex) {
            userBean = null;
        }
        return userBean;
    }

    @Override
    public UserBean GetUserByUserName(String username) {
        UserBean user;
        try {
            user = userDao.GetUserByUserName(username);
        } catch (Exception ex) {
            user = null;
        }
        return user;
    }

    @Override
    public Long InsertOneUser(UserBean userBean) {
        return userDao.InsertOneUser(userBean);
    }

    @Override
    public Boolean ResetPassword(UserBean userBean) {

        try {
//            System.out.println(userBean.getPassword());
//            System.out.println(userBean.getUsername());
//            System.out.println(userDao.ResetPassword(userBean));
            userDao.ResetPassword(userBean);
            return true;
        } catch (Exception ex) {

        }
        return false;

    }
}
