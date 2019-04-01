package com.lry.xxs.service;

import com.lry.xxs.mapper.HomeworkMapper;
import com.lry.xxs.mapper.QuestionMapper;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private QuestionMapper questionMapper;

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

    //学生查询当天作业量
    public PageData selectNum(PageData pd) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tempdate = sdf.format(date);
        pd.put("startTime",tempdate+" 00:00:00");
        pd.put("endTime",tempdate+" 23:59:59");
        PageData temppd = homeworkMapper.selectNum(pd);
        PageData returnPD = new PageData();
        if (temppd==null){
            returnPD.put("q0_num",0);
            returnPD.put("q1_num",0);
            returnPD.put("q2_num",0);
            returnPD.put("q3_num",0);
            returnPD.put("num",0);
            return returnPD;
        }
        String[] q0 = null;
        //将作业存入redis中
        if(temppd.getString("question0_id")!=null && !redisService.checkKey(pd.getString("user_id") + tempdate + "q0")) {
            q0 = temppd.getString("question0_id").split(",");
            redisService.addListAll(pd.getString("user_id") + tempdate + "q0", q0);
            //redisService.addString(pd.getString("user_id") + tempdate + "q0", temppd.getString("question0_id"));
            returnPD.put("q0_num",q0.length);
        }else {
            returnPD.put("q0_num",redisService.sizeList(pd.getString("user_id") + tempdate + "q0"));
        }
        String[] q1 = null;
        if(temppd.getString("question1_id")!=null && !redisService.checkKey(pd.getString("user_id") + tempdate + "q1")) {
            q1 = temppd.getString("question1_id").split(",");
            redisService.addListAll(pd.getString("user_id")+tempdate+"q1",q1);
            //redisService.addString(pd.getString("user_id")+tempdate+"q1",temppd.getString("question1_id"));
            returnPD.put("q1_num",q1.length);
        }else {
            returnPD.put("q1_num",redisService.sizeList(pd.getString("user_id") + tempdate + "q1"));
        }
        String[] q2 = null;
        if(temppd.getString("question2_id")!=null && !redisService.checkKey(pd.getString("user_id") + tempdate + "q2")) {
            q2 = temppd.getString("question2_id").split(",");
            redisService.addListAll(pd.getString("user_id")+tempdate+"q2",q2);
            //redisService.addString(pd.getString("user_id")+tempdate+"q2",temppd.getString("question2_id"));
            returnPD.put("q2_num",q2.length);
        }else {
            returnPD.put("q2_num",redisService.sizeList(pd.getString("user_id") + tempdate + "q2"));
        }
        String[] q3 = null;
        if(temppd.getString("question3_id")!=null && !redisService.checkKey(pd.getString("user_id") + tempdate + "q3")) {
            q3 = temppd.getString("question3_id").split(",");
            redisService.addListAll(pd.getString("user_id")+tempdate+"q3",q3);
            //redisService.addString(pd.getString("user_id")+tempdate+"q3",temppd.getString("question3_id"));
            returnPD.put("q3_num",q3.length);
        }else {
            returnPD.put("q3_num",redisService.sizeList(pd.getString("user_id") + tempdate + "q3"));
        }
        returnPD.put("num",temppd.get("num"));
        return returnPD;
    }

    //依次获取作业
    public PageData selectOne(PageData pd){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tempdate = sdf.format(date);
        String tempQid = redisService.getListOne(pd.getString("user_id") + tempdate + pd.getString("q"),0);
        pd = questionMapper.select(pd).get(0);
        PageData returnPD = new PageData();
        returnPD.put("question_title",pd.getString("question_title"));
        //将选项随机排序
        String[] tempReturn = {pd.getString("question_answer1")+"1",pd.getString("question_answer2")+"2",pd.getString("question_answer3")+"3",pd.getString("question_answer4")+"4"};
        Random random = new Random();
        for(int i = 0; i < tempReturn.length; i++){
            int p = random.nextInt(i+1);
            String tmp = tempReturn[i];
            tempReturn[i] = tempReturn[p];
            tempReturn[p] = tmp;
        }
        List<Map> returnListMap = new ArrayList<>();
        for(int i = 0; i < tempReturn.length; i++){
            String key = tempReturn[i].substring(tempReturn[i].length()-1);
            String value = tempReturn[i].substring(0,tempReturn[i].length()-1);
            Map returnMap = new HashMap();
            returnMap.put("key",key);
            returnMap.put("value",value);
            returnListMap.add(returnMap);
        }
        returnPD.put("select",returnListMap);
        return returnPD;
    }
}
