package com.example.demopro.service.Impl;

import com.example.demopro.service.RedisService;
import com.example.demopro.utils.JedisPoolUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import sun.rmi.runtime.Log;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource
    private JedisPoolUtils jedisPoolUtils;  //注入连接池

    @Override
    public String get(String key) {

        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        String result = jedis.get(key);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        String result = jedis.set(key, value);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public Long del(String key) {
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        Long result = jedis.del(key);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public Long setExpire(String key, int seconds) {
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        Long result = jedis.expire(key, seconds);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public String set(String key, String value, int seconds) {
        Jedis jedis = jedisPoolUtils.GetJedisConn();  //从连接池获取连接
        String result = jedis.set(key, value);
        jedis.expire(key, seconds);
        jedisPoolUtils.ReturnJedisConn(jedis); //归还连接给连接池
        return result;
    }

    @Override
    public Long lpush(String key, String value) {
        Jedis jedis = jedisPoolUtils.GetJedisConn();
        Long result = jedis.lpush(key, value);
        jedisPoolUtils.ReturnJedisConn(jedis);
        return result;
    }

    @Override
    public Long rpush(String key, String value) {
        Jedis jedis = jedisPoolUtils.GetJedisConn();
        Long result = jedis.rpush(key, value);
        jedisPoolUtils.ReturnJedisConn(jedis);
        return result;
    }

    @Override
    public List<String> lrange(String key, Long start, Long end) {
        Jedis jedis = jedisPoolUtils.GetJedisConn();
        List<String> list = jedis.lrange(key, start, end);
        jedisPoolUtils.ReturnJedisConn(jedis);
        return list;
    }

    @Override
    public void flushdb(){
        Jedis jedis = jedisPoolUtils.GetJedisConn();
        jedis.flushDB();
        jedisPoolUtils.ReturnJedisConn(jedis);
    }

}
