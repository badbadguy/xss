package com.lry.xxs.controller;

import com.lry.xxs.model.User;
import com.lry.xxs.service.UserService;
import com.lry.xxs.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private ResultJson resultJson = null;

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
    public MappingJacksonValue changePw(String name, String oldPassword, String newPassword)throws Exception{
        switch (userService.checkPw(name, oldPassword)){
            case 1: resultJson = new ResultJson(Boolean.TRUE, "密码错误");break;
            case 2: resultJson = new ResultJson(Boolean.TRUE, "用户不存在");break;
            case 666: {
                userService.changePw(name, newPassword);
                resultJson = new ResultJson(Boolean.TRUE, "登录成功");
                break;
            }
            default: resultJson = new ResultJson(Boolean.TRUE, "未知错误");break;
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    @ResponseBody
    @RequestMapping("/login")
    public MappingJacksonValue login(String name, String password, String type)throws Exception{
        switch (userService.checkPw(name, password)) {
            case 1:
                resultJson = new ResultJson(Boolean.TRUE, "密码错误");
                break;
            case 2:
                resultJson = new ResultJson(Boolean.TRUE, "用户不存在");
                break;
            case 666: {
                if (userService.checkType(name, type)) {

                }
                resultJson = new ResultJson(Boolean.TRUE, "登录成功");
                break;
            }
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }
}
