package com.lry.xxs.service;

import com.lry.xxs.mapper.AnswerMapper;
import com.lry.xxs.utils.PageData;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<PageData> selectRanking() throws Exception {
        List<PageData> returnList = new ArrayList<>();
        returnList = answerMapper.selectRanking();
        for (PageData pd : returnList) {
            if (pd.containsKey("s")) {
                String tempName = new String(Base64.decodeBase64(pd.getString("s")), "UTF-8");
                pd.put("s", tempName);
            }
        }
        return returnList;
    }
}
