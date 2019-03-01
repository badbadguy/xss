package com.lry.xxs.service;

import com.lry.xxs.mapper.StudentMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public void add(PageData pd){
        studentMapper.add(pd);
    }

    public void deleteById(String id){
        studentMapper.deleteById(id);
    }
}
