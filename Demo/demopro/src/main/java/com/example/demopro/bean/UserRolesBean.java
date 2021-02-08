package com.example.demopro.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesBean {
    private int Id;  //ID
    private String username;  //用户名
    private String roles;  //角色
}
