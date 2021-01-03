package com.example.demopro.controller.ErrorPageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPage404 {
    @RequestMapping("/Error-404")
    public String Error404(){
        return "ErrorPage/Error-404.html";
    }
}
