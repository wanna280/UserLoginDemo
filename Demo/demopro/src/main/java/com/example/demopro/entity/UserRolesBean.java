package com.example.demopro.entity;

public class UserRolesBean {
    private int Id;  //ID
    private String username;  //用户名
    private String roles;  //角色

    public UserRolesBean() {
    }

    public UserRolesBean(int id, String username, String roles) {
        Id = id;
        this.username = username;
        this.roles = roles;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
