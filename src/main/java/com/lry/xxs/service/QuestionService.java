package com.lry.xxs.service;

import com.lry.xxs.mapper.QuestionMapper;
import com.lry.xxs.utils.BaiduApi;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private BaiduApi baiduApi;

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

    //答题
    public PageData answer(PageData pd) {
        PageData returnPD = new PageData();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tempdate = sdf.format(date);
        Boolean t = redisService.checkKey(pd.getString("user_id") + tempdate + pd.getString("q"));
        String QID = redisService.getListOne(pd.getString("user_id") + tempdate + pd.getString("q"), 0);
        PageData tempPD = new PageData();
        tempPD.put("question_id", QID);
        tempPD = questionMapper.select(tempPD).get(0);
        Boolean isRight = true;
        String QType = pd.getString("QType");
        //判断题目对错
        if (QType.equals("0")) {                 //单选题
            if (tempPD.get("question_answerr").toString().equals(pd.getString("answer"))) {
                isRight = true;
            } else {
                isRight = false;
            }
        } else if (QType.equals("1")) {            //语音题
            //调用百度语义识别API
            Double tempScore = (Double) baiduApi.getScore(tempPD.getString("question_answers"), pd.getString("answer"));
            if (tempScore > 0.8) {
                returnPD.put("RorW", 1);
            } else {
                returnPD.put("RorW", 0);
                returnPD.put("question_remark", tempPD.getString("question_remark"));
            }
        } else if (QType.equals("2")) {            //填空题
            if (tempPD.getString("question_answers").equals(pd.getString("answer"))) {
                isRight = true;
            } else {
                isRight = false;
            }
        }
        redisService.removeList(pd.getString("user_id") + tempdate + pd.getString("q"), 1, QID);
        if (!redisService.checkKey(pd.getString("user_id") + tempdate + pd.getString("q"))) {
            redisService.addList(pd.getString("user_id") + tempdate + pd.getString("q"), "finish");
        }
        if (isRight) {
            returnPD.put("RorW", 1);
        } else {
            returnPD.put("RorW", 0);
            returnPD.put("question_remark", tempPD.getString("question_remark"));
            //将题目加入错题库中
            PageData errorPD = new PageData();
            errorPD.put("answer_id", UuidUtil.get32UUID());
            errorPD.put("homework_id", pd.getString("homework_id"));
            errorPD.put("question_id", QID);
            errorPD.put("answer_message", pd.getString("answer"));
            errorPD.put("student_id", pd.getString("user_id"));
            errorPD.put("creattime", new Date());
            answerService.add(errorPD);
        }
        return returnPD;
    }
}