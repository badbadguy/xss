package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.model.User;
import com.lry.xxs.utils.MD5;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void add(User user)throws Exception{
        user.setUser_id(UuidUtil.get32UUID());
        user.setCreattime(new Date());
        user.setUpdatetime(new Date());
        MD5 md5 = new MD5();
        String temppw = md5.EncoderByMd5(user.getUser_password().toString().trim());
        userMapper.add(user);
    }

    public void deleteById(String id){
        userMapper.deleteById(id);
    }

    public void updateById(User user){
        user.setUpdatetime(new Date());
        userMapper.updateById(user);
    }

    public User selectById(String id){
        return userMapper.selectById(id);
    }

    public Boolean checkPw(String name, String password)throws Exception{
        String temppw = userMapper.checkPw(name);
        MD5 md5 = new MD5();
        if(md5.checkpassword(password,temppw))
            return true;
        else
            return false;
    }
}
