package com.example.demopro.service;

import java.util.List;

public interface RedisService {
    public String get(String key);  //get

    public String set(String key, String value);  //set

    public String set(String key, String value, int seconds);  //set

    public Long del(String key);  //del

    public Long setExpire(String key, int seconds);  //setExpire

    public Long lpush(String key, String value);  //lpush

    public Long rpush(String key, String value);  //rpush

    public List<String> lrange(String key, Long start, Long end); //lrange

    public void flushdb();  //flushdb
}
