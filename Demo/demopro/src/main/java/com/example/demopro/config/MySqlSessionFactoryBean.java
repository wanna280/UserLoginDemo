package com.example.demopro.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class MySqlSessionFactoryBean extends org.mybatis.spring.SqlSessionFactoryBean {
    @Resource
    private DataSource druidDataSource;

    private String mybatis_main_config = "classpath:mybatis/config/mybatis-config.xml";   //xml扫描路径


    @Bean(name = "sqlSessionFactoryBean")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        final org.mybatis.spring.SqlSessionFactoryBean sqlSessionFactoryBean = new org.mybatis.spring.SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSource);
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(mybatis_main_config));
        return sqlSessionFactoryBean.getObject();
    }

//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("com.example.demopro.dao");
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
//        mapperScannerConfigurer.setAnnotationClass(Repository.class);
//        //mapperScannerConfigurer.setProcessPropertyPlaceHolders(true);
//        return mapperScannerConfigurer;
//    }


}
