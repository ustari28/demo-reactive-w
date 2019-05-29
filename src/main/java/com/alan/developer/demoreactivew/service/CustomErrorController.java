package com.alan.developer.demoreactivew.service;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping(value = "/error")
    public String error() {
        return "customError";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
