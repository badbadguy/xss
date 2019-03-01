package com.lry.xxs.service;

import com.lry.xxs.mapper.TeacherMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    public void add(PageData pd){
        teacherMapper.add(pd);
    }

    public void deleteById(String id){
        teacherMapper.deleteById(id);
    }
}
