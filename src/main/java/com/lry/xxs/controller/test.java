package com.lry.xxs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
