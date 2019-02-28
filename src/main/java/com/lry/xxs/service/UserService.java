package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User selectById(Integer id){
        return userMapper.selectById(id);
    }
}
