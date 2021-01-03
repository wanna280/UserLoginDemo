package com.example.demopro.dao;

import com.example.demopro.entity.UserBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface UserDao {
    public ArrayList<UserBean> GetUserById(int id);   //根据用户ID查询用户
    public ArrayList<UserBean> UserLogin(UserBean userBean);  //根据用户名和用户名查询数据
    public ArrayList<UserBean> GetUserByUserName(String username);  //根据用户名查询数据

}
