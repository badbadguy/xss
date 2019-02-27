package com.lry.xxs.service;

import com.lry.xxs.mapper.UserMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<PageData> datalistPage(PageData pd)throws Exception{
        return userMapper.datalistPage(pd);
    }
}
