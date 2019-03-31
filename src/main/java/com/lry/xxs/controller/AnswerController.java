package com.lry.xxs.controller;

import com.lry.xxs.service.AnswerService;
import com.lry.xxs.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/answer")
@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    private ResultJson resultJson = null;

    //设置cors跨域请求
    public void init(ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }

    //学生进行答题
    public MappingJacksonValue add(HttpServletResponse response){
        init(response);
        resultJson = new ResultJson(Boolean.TRUE, "查询成功");
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }
}
