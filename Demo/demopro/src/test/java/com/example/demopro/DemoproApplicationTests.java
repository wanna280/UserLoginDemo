package com.example.demopro;

import com.example.demopro.service.Impl.RedisServiceImpl;
import com.example.demopro.utils.JedisPoolUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoproApplicationTests {
    @Autowired
    JedisPoolUtils jedisPoolUtils;

    @Autowired
    RedisServiceImpl redisService;

    @Test
    void TestConfig() {
//        Jedis jedis = jedisPoolUtils.GetJedisConn();
//        System.out.println(jedis.ping());
//
//        System.out.println(redisService.get("1"));
    }
}
