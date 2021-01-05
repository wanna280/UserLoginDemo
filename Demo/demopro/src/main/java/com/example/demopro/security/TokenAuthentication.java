package com.example.demopro.security;

import com.example.demopro.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.util.Collection;

/**
 * 自定义实现SpringSecurity的Authentication实现，用于校验token
 */
public class TokenAuthentication implements Authentication {
    private String token;   //jwt

    public TokenAuthentication(String token) {  //构造函数
        this.token = token;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {  //Return Token
        return this.token;   //Credential-资格，用于鉴权
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {  //是否认证？
        System.out.println(this.token);
        try {
            Claims claims = JwtUtils.VerifyJwt(this.token);
            String claims_username = (String) claims.get("username");  //尝试从claims获取username字段，获取失败则认证失败
            System.out.println("认证的用户名为"+claims_username);
            return true;
        } catch (Exception ex) {//如果没有认证成功
            System.out.println("认证失败");  //后台打印认证失败信息
            return false;
        }

    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
