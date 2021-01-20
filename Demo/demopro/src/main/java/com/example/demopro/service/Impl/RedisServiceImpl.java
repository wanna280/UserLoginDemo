package com.example.demopro.service.Impl;

import com.example.demopro.service.RedisService;
import com.example.demopro.utils.JedisPoolUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private JedisPoolUtils jedisPoolUtils;  //注入连接池

    @Override
    public String get(String key){

        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        String result = jedis.get(key);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public String set(String key,String value){
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        String result = jedis.set(key,value);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public Long del(String key){
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        Long result = jedis.del(key);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public Long setExpire(String key,int seconds){
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        Long result = jedis.expire(key,seconds);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public String set(String key,String value,int seconds){
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        String result = jedis.set(key,value);
        jedis.expire(key, seconds);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

}
