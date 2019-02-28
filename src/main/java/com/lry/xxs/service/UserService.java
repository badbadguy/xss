package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void add(User user){
        userMapper.add(user);
    }

    public void deleteById(String id){
        userMapper.deleteById(id);
    }

    public void updateById(User user){
        userMapper.updateById(user);
    }

    public User selectById(String id){
        return userMapper.selectById(id);
    }
}
