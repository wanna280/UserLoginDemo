package com.example.demopro.service;

import com.example.demopro.dao.UserRolesDao;
import com.example.demopro.entity.UserRolesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserRolesService {
    @Autowired
    UserRolesDao userRolesDao;

    public ArrayList<UserRolesBean> GetUserRolesById(int id){
        return userRolesDao.GetUserRolesById(id);
    }

    public ArrayList<UserRolesBean> GetUserRolesByUserName(String username){
        return userRolesDao.GetUserRolesByUserName(username);
    }
}
