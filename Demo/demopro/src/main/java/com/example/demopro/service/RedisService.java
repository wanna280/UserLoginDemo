package com.example.demopro.service;

public interface RedisService {
    public String get(String key);  //get

    public String set(String key, String value);  //set

    public String set(String key, String value, int seconds);  //set

    public Long del(String key);  //del

    public Long setExpire(String key, int seconds);  //setExpire
}
