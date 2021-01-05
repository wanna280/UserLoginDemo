package com.example.demopro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import static com.example.demopro.utils.JwtUtils.GenerateToken;


@ServletComponentScan("com.example.demopro.filter")
@SpringBootApplication
@MapperScan("com.example.demopro.dao")   //扫描的是dao包，不是dao接口，包名是用.而不是用/
public class DemoproApplication {  //新版的IDEA复制路径复制的是/

    public static void main(String[] args) {
        SpringApplication.run(DemoproApplication.class, args);
    }

}
