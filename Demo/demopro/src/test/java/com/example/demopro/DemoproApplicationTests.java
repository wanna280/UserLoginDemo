package com.example.demopro;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoproApplicationTests {
    @Resource(name = "druidDataSource")
    DruidDataSource druidDataSource;

    @Test
    void TestConfig() {
        System.out.println(druidDataSource);

    }
}
