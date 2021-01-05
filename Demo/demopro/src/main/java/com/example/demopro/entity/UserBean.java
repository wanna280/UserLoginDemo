package com.example.demopro.entity;

public class UserBean {
    private int Id;  //ID
    private String username;  //用户名
    private String password;  //密码

    public UserBean() {
    }

    public UserBean(int id, String username, String password) {
        Id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
