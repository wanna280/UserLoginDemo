package com.example.demopro.dao;

import com.example.demopro.bean.UserRolesBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRolesDao {
    public UserRolesBean GetUserRolesById(int id);   //根据用户ID查询数据
    public UserRolesBean GetUserRolesByUserName(String username);  //根据用户名查询数据
    public Long InsertOneUserRole(UserRolesBean userRolesBean);    //插入一个用户
}
