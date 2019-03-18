package com.lry.xxs.service;

import com.lry.xxs.mapper.QuestionMapper;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    //新增数学题
    public String addMath(PageData pd) {
        pd.put("question_id", UuidUtil.get32UUID());
        questionMapper.add(pd);
        return pd.getString("question_id");
    }

    //根据id删除题目
    public void delete(String id) {
        questionMapper.deleteById(id);
    }

    //根据id修改题目信息
    public void update(PageData pd) {
        questionMapper.updateById(pd);
    }

    //查询题目信息
    public List<PageData> select(PageData pd){
        return questionMapper.select(pd);
    }
}