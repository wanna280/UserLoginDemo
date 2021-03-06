package com.example.demopro.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

//阿里德鲁伊连接池
@Component
public class DataSourceConfig {
    //创建数据源1
    @ConfigurationProperties(prefix = "spring.datasource.druid1")  //扫描配置文件
    @Bean(name = "druidDataSource1")
    public DataSource druidDataSource1() {
        return new DruidDataSource();
    }

    //创建数据源2
    @ConfigurationProperties(prefix = "spring.datasource.druid2")  //扫描配置文件
    @Bean(name = "druidDataSource2")
    public DataSource druidDataSource2() {
        return new DruidDataSource();
    }

}
