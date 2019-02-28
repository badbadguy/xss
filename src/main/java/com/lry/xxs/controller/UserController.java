package com.lry.xxs.controller;

import com.lry.xxs.model.User;
import com.lry.xxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public void add(User user)throws Exception{
        userService.add(user);
    }

    @RequestMapping("/delete")
    public void delete(String id)throws Exception{
        userService.deleteById(id);
    }

    @RequestMapping("/update")
    public void updateById(User user)throws Exception{
        userService.updateById(user);
    }
}
