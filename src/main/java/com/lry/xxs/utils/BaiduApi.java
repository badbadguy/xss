package com.lry.xxs.utils;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class BaiduApi {

    //百度词义相似度
    public Object getScore(String what, String right) {

        AipNlp client = new AipNlp(CommonInfo.AppID, CommonInfo.API_Key, CommonInfo.Secret_Key);
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        // 短文本相似度
        JSONObject res1 = client.simnet(what, right, options);
        return res1.get("score");
    }
}
