package com.example.demopro.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBean {
    private int Id;  //ID
    private String username;  //用户名
    private String password;  //密码
}
