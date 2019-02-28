package com.lry.xxs.controller;

import com.lry.xxs.model.User;
import com.lry.xxs.service.RedisService;
import com.lry.xxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    /*@ResponseBody
    @RequestMapping("/add")
    public void add(User user)throws Exception{
        userService.selectById(id);
    }*/

    @ResponseBody
    @RequestMapping("/id")
    public void selectr(Integer id)throws Exception{
        userService.selectById(id);
    }
}
