package com.lry.xxs.controller;

import com.lry.xxs.service.UserService;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/test")
    public List<PageData> selectByUserId(PageData pd)throws Exception{
        return userService.datalistPage(pd);
    }
}
