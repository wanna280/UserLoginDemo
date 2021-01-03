package com.example.demopro.dao;


import com.example.demopro.entity.UserRolesBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserRolesDao {
    public ArrayList<UserRolesBean> GetUserRolesById(int id);   //根据用户ID查询数据
    public ArrayList<UserRolesBean> GetUserRolesByUserName(String username);  //根据用户名查询数据
}
