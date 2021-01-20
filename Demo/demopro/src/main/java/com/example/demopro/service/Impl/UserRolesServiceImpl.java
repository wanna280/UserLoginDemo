package com.example.demopro.service.Impl;

import com.example.demopro.dao.UserRolesDao;
import com.example.demopro.bean.UserRolesBean;
import com.example.demopro.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRolesServiceImpl implements UserRolesService {
    @Resource
    UserRolesDao userRolesDao;

    @Override
    public UserRolesBean GetUserRolesById(int id) {
        UserRolesBean userRolesBean;
        try{
            userRolesBean = userRolesDao.GetUserRolesById(id);
        }catch (Exception ex){
            userRolesBean = null;
        }
        return userRolesBean;
    }

    @Override
    public UserRolesBean GetUserRolesByUserName(String username) {
        UserRolesBean userRolesBean;
        try{
            userRolesBean = userRolesDao.GetUserRolesByUserName(username);
        }catch (Exception ex){
            userRolesBean = null;
        }
        return userRolesBean;
    }

    @Override
    public boolean InsertOneUserRole(UserRolesBean userRolesBean) {
        try {   //插入成功return true
            userRolesDao.InsertOneUserRole(userRolesBean);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }
}
