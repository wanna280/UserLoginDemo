package com.example.demopro.controller.ErrorPageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPage500 {
    @RequestMapping("/Error-500")
    public String Error500(){
        return "ErrorPage/Error-500.html";
    }
}
