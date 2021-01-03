package com.example.demopro.controller.ErrorPageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPage400 {
    @RequestMapping("/Error-400")
    public String Error400(){
        return "ErrorPage/Error-400.html";
    }
}
