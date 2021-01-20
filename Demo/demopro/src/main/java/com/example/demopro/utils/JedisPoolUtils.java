package com.example.demopro.utils;

import com.example.demopro.DemoproApplication;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URL;
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

            host = (String) map.get("host");
            port = (Integer) map.get("port");
            password = String.valueOf(map.get("password"));
            timeout = (Integer) map.get("timeout");
            db = (Integer) map.get("db");
            maxActive = (Integer) map.get("max-active");
            maxIdle = (Integer) map.get("max-idle");
            maxWait = (Integer) map.get("max-wait");
            testOnBorrow = (boolean) map.get("testOnBorrow");
            testOnReturn = (boolean) map.get("testOnReturn");

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

    public Jedis GetJedisConn() {  //获取Jedis连接
        return pool.getResource();
    }

    public void ReturnJedisConn(Jedis jedis) {  //归还连接
        pool.returnResourceObject(jedis);
    }


}

