package com.example.demopro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//扫描ApplicationContext.xml配置文件
@Configuration
@ImportResource(value = "classpath:config/ApplicationContext.xml")
public class ConfigFileScan {
}
