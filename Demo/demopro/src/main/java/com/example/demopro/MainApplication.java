package com.example.demopro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ServletComponentScan("com.example.demopro.filter")
@SpringBootApplication
@MapperScan(value = "com.example.demopro.dao")   //扫描的是dao包，不是dao接口，包名是用.而不是用/
public class MainApplication {  //新版的IDEA复制路径复制的是/

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
