package com.example.demopro.service;

import com.example.demopro.dao.UserRolesDao;
import com.example.demopro.entity.UserBean;
import com.example.demopro.entity.UserRolesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserRolesService {
    @Autowired
    UserRolesDao userRolesDao;

    public UserRolesBean GetUserRolesById(int id) {
        UserRolesBean userRolesBean;
        try{
            userRolesBean = userRolesDao.GetUserRolesById(id);
        }catch (Exception ex){
            userRolesBean = null;
        }
        return userRolesBean;
    }

    public UserRolesBean GetUserRolesByUserName(String username) {
        UserRolesBean userRolesBean;
        try{
            userRolesBean = userRolesDao.GetUserRolesByUserName(username);
        }catch (Exception ex){
            userRolesBean = null;
        }
        return userRolesBean;
    }

    public boolean InsertOneUserRole(UserRolesBean userRolesBean) {
        try {   //插入成功return true
            userRolesDao.InsertOneUserRole(userRolesBean);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }
}
