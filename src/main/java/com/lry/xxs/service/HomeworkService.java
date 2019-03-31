package com.lry.xxs.service;

import com.lry.xxs.mapper.HomeworkMapper;
import com.lry.xxs.utils.PageData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class HomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private RedisService redisService;

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
        String[] q0 = null;
        //将作业存入redis中
        if(temppd.getString("question0_id")!=null) {
            q0 = temppd.getString("question0_id").split(",");
            redisService.addString(pd.getString("user_id") + tempdate + "q0", temppd.getString("question0_id"));
            returnPD.put("q0_num",q0.length);
        }else {
            returnPD.put("q0_num",0);
        }
        String[] q1 = null;
        if(temppd.getString("question1_id")!=null) {
            q1 = temppd.getString("question1_id").split(",");
            redisService.addString(pd.getString("user_id")+tempdate+"q1",temppd.getString("question1_id"));
            returnPD.put("q1_num",q1.length);
        }else {
            returnPD.put("q1_num",0);
        }
        String[] q2 = null;
        if(temppd.getString("question2_id")!=null) {
            q2 = temppd.getString("question2_id").split(",");
            redisService.addString(pd.getString("user_id")+tempdate+"q2",temppd.getString("question2_id"));
            returnPD.put("q2_num",q2.length);
        }else {
            returnPD.put("q2_num",0);
        }
        String[] q3 = null;
        if(temppd.getString("question3_id")!=null) {
            q3 = temppd.getString("question3_id").split(",");
            redisService.addString(pd.getString("user_id")+tempdate+"q3",temppd.getString("question3_id"));
            returnPD.put("q3_num",q3.length);
        }else {
            returnPD.put("q3_num",0);
        }
        returnPD.put("num",temppd.get("num"));
        return returnPD;
    }
}
