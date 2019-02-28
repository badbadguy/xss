package com.lry.xxs.controller;

import com.lry.xxs.model.User;
import com.lry.xxs.service.UserService;
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
    public User selectByUserId(Integer id)throws Exception{
        return userService.selectById(id);
    }
}
