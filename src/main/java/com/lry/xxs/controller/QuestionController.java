package com.lry.xxs.controller;

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
import java.util.List;

@RequestMapping("/question")
@RestController
public class QuestionController extends BaseController {

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

    //新增数学题
    @ResponseBody
    @RequestMapping("/addMath")
    public String addMath(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        return questionService.addMath(pd);
    }

    //根据id删除题目
    @ResponseBody
    @RequestMapping("/delete")
    public void delete(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        questionService.delete(pd.getString("question_id"));
    }

    //根据id修改题目信息
    @ResponseBody
    @RequestMapping("/update")
    public void update(HttpServletResponse response){
        init(response);
        PageData pd = this.getPageData();
        questionService.update(pd);
    }

    //查询题目信息
    @ResponseBody
    @RequestMapping("select")
    public MappingJacksonValue select(HttpServletResponse response){
        init(response);
        PageData pd = this.getPageData();
        try {
            List<PageData> list = questionService.select(pd);
            resultJson = new ResultJson(Boolean.TRUE,"查询成功",list);
        } catch (Exception e){
            resultJson = new ResultJson(Boolean.FALSE,"查询出错",e);
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }
}
