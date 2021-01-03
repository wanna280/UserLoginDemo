package com.example.demopro.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/home")
    public String main(){
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "home page";
    }

    @RequestMapping("/test")
    public String test(){
       //System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        return "test page";
    }

}
