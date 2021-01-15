package com.example.demopro.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(value = "classpath:config/ApplicationContext.xml")
public class ConfigFileScan {
}
