package com.example.demopro.controller.ErrorPageController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPage403 {
    @RequestMapping("/Error-403")
    public String Error403(){
        return "ErrorPage/Error-403.html";
    }
}
