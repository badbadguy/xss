package com.lry.xxs.service;

import com.lry.xxs.mapper.QuestionMapper;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    //新增题目
    public String add(PageData pd) {
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
    public List<PageData> select(PageData pd) {
        List<PageData> list = new ArrayList<>();
        list = questionMapper.select(pd);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).containsKey("question_link")) {
                PageData xixixi = questionMapper.selectById(list.get(i).getString("question_id"));
                if (xixixi != null) {
                    list.add(xixixi);
                }
            }
        }
        return list;
    }

    //查询题目信息（指定类型返回）
    public List<PageData> select1(PageData pd) {
        return questionMapper.select1(pd);
    }
}