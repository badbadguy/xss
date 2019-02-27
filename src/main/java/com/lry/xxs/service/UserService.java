package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<PageData> datalistPage(PageData pd)throws Exception{
        return userMapper.datalistPage(pd);
    }
}
