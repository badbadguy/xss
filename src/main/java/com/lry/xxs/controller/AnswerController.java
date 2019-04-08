package com.lry.xxs.controller;

import com.lry.xxs.service.AnswerService;
import com.lry.xxs.service.QuestionService;
import com.lry.xxs.utils.BaseController;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/answer")
@RestController
public class AnswerController extends BaseController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;

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
    public MappingJacksonValue add(HttpServletResponse response) {
        init(response);
        resultJson = new ResultJson(Boolean.TRUE, "查询成功");
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //学生查看错题
    @ResponseBody
    @RequestMapping("/checkWrong")
    public List<PageData> checkWrong(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        List<PageData> wrongList = answerService.select(pd);
        List<PageData> returnList = new ArrayList<>();
        for (PageData temppd : wrongList) {
            PageData xixixi = new PageData();
            xixixi.put("question_id", temppd.getString("question_id"));
            PageData tempxixixi = questionService.select(xixixi).get(0);
            if (tempxixixi.get("question_type").toString().equals(pd.getString("question_type")))
                returnList.add(questionService.select(xixixi).get(0));
        }
        return returnList;
    }

    //错题排行榜
    @ResponseBody
    @RequestMapping("/ranking")
    public List<PageData> ranking(HttpServletResponse response) throws Exception {
        init(response);
        return answerService.selectRanking();
    }
}
