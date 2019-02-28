package com.lry.xxs.controller;

import com.lry.xxs.model.User;
import com.lry.xxs.service.RedisService;
import com.lry.xxs.service.UserService;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/add")
    public void add(User user)throws Exception{
        user.setUser_id(UuidUtil.get32UUID());
        user.setCreattime(new Date());
        user.setUpdatetime(new Date());
        userService.add(user);
    }

    @RequestMapping("/delete")
    public void delete(String id)throws Exception{
        userService.deleteById(id);
    }

    @RequestMapping("/update")
    public void updateById(User user)throws Exception{
        user.setUpdatetime(new Date());
        userService.updateById(user);
    }
}
