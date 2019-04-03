package com.lry.xxs.service;

import com.lry.xxs.mapper.AnswerMapper;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    public void add(PageData pd) {
        answerMapper.add(pd);
    }

    public void deleteById(String id) {
        answerMapper.deleteById(id);
    }

    public void updateById(PageData pd) {
        answerMapper.updateById(pd);
    }

    public List<PageData> select(PageData pd) {
        return answerMapper.select(pd);
    }
}
