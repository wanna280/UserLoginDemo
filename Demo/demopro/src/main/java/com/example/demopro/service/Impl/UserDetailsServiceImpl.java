package com.example.demopro.service.Impl;

import com.example.demopro.entity.UserBean;
import com.example.demopro.entity.UserRolesBean;
import com.example.demopro.service.UserRolesService;
import com.example.demopro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Autowired
    UserRolesService userRolesService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            UserBean user = userService.GetUserByUserName(username);   //根据用户名查询用户
            UserRolesBean userRoles = userRolesService.GetUserRolesByUserName(user.getUsername());
            Collection<GrantedAuthority> auth = new ArrayList<>();
            auth.add(new SimpleGrantedAuthority(userRoles.getRoles()));
            return new User(user.getUsername(), user.getPassword(), auth);
        } catch (Exception ex){   //登录失败怎么处理呢？？？
            ex.printStackTrace();

        }
        return null;
    }
}
