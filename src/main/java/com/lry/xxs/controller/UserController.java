package com.lry.xxs.controller;

import com.lry.xxs.model.User;
import com.lry.xxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @ResponseBody
    @RequestMapping("/changePw")
    public boolean changePw(String name, String oldPassword, String newPassword)throws Exception{
        if(userService.checkPw(name, oldPassword)) {
            userService.changePw(name, newPassword);
            return true;
        }else {
            return false;
        }
    }
}
