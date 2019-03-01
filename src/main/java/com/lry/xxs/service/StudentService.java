package com.lry.xxs.service;

import com.lry.xxs.mapper.StudentMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public void add(PageData pd) {
        studentMapper.add(pd);
    }

    public void deleteById(String id) {
        studentMapper.deleteById(id);
    }

    public void updateById(PageData pd) {
        studentMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return studentMapper.select(pd);
    }
}
