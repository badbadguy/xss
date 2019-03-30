package com.lry.xxs.service;

import com.lry.xxs.mapper.HomeworkMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;

    public void add(PageData pd) { homeworkMapper.add(pd);}

    public void deleteById(String id) {
        homeworkMapper.deleteById(id);
    }

    public void updateById(PageData pd) {
        homeworkMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return homeworkMapper.select(pd);
    }
}
