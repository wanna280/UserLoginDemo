package com.example.demopro.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

@Component
public class JedisPoolUtils {
    private static String host;  //Redis Server Host
    private static Integer port;  //Redis Server Port
    private static String password;  //Redis Server Password
    private static int timeout;  //Redis Time to Out
    private static int db;  //数据库序号
    private static int maxActive;  //最大激活数量
    private static int maxIdle; //最大Idle
    private static int maxWait;  //最大等待时间
    private static boolean testOnBorrow;
    private static boolean testOnReturn;
    private static final JedisPool pool;  //jedis连接池

    static {   //这里要异常处理
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        try {
            String yaml_class_path = "/application.yaml";  //yaml文件的类路径
            RedisUtils redisUtils = new RedisUtils();  //导入读取Yaml配置文件的类
            Map map = redisUtils.YamlReadJedisConfigFromResource(yaml_class_path);

            host = map.get("host") == null ? "127.0.0.1" : (String) map.get("host");
            port = map.get("port") == null ? 6379 : (Integer) map.get("port");
            password = String.valueOf(map.get("password"));
            timeout = map.get("timeout") == null ? 5000 : (Integer) map.get("timeout");
            db = map.get("db") == null ? 0 : (Integer) map.get("db");
            maxActive = map.get("max-active") == null ? 5000 : (Integer) map.get("max-active");
            maxIdle = map.get("max-active") == null ? 2000 : (Integer) map.get("max-idle");
            maxWait = map.get("max-wait") == null ? 5000 : (Integer) map.get("max-wait");
            testOnBorrow = map.get("testOnBorrow") == null ? true : (boolean) map.get("testOnBorrow");
            testOnReturn = map.get("testOnReturn") == null ? true : (boolean) map.get("testOnReturn");

            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setTestOnBorrow(testOnBorrow);
            jedisPoolConfig.setTestOnReturn(testOnReturn);
            jedisPoolConfig.setMaxWaitMillis(maxWait);
            jedisPoolConfig.setMaxTotal(maxActive);

        } catch (Exception ex) {
            System.out.println("连接池创建失败" + ex.getMessage());
        }

        pool = new JedisPool(jedisPoolConfig, host, port, timeout, password, db);

    }

    //获取Jedis连接
    public Jedis GetJedisConn() {  //获取Jedis连接
        return pool.getResource();
    }

    //归还Jedis连接
    public void ReturnJedisConn(Jedis jedis) {  //归还连接
        pool.returnResourceObject(jedis);
    }

}

