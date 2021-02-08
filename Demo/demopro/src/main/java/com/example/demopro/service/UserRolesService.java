package com.example.demopro.service;

import com.example.demopro.bean.UserRolesBean;

public interface UserRolesService {
    public UserRolesBean GetUserRolesById(int id);  //通过ID获取用户的角色

    public UserRolesBean GetUserRolesByUserName(String username);  //通过UserName查询用户角色

    public Long InsertOneUserRole(UserRolesBean userRolesBean);  //插入一个UserRolesBean
}
