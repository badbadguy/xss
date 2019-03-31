package com.lry.xxs.controller;

import com.lry.xxs.service.HomeworkService;
import com.lry.xxs.utils.BaseController;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RequestMapping("/homework")
@RestController
public class HomeworkController extends BaseController {

    @Autowired
    private HomeworkService homeworkService;

    private ResultJson resultJson = null;

    //设置cors跨域请求
    public void init(ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }

    //布置作业
    @RequestMapping("/add")
    public void add(HttpServletResponse response){
        init(response);
        PageData pd = this.getPageData();
        //处理数据
        pd.put("question0_id",pd.getString("question0_id").replace("[","").replace("]","").replace("\"",""));
        pd.put("question1_id",pd.getString("question1_id").replace("[","").replace("]","").replace("\"",""));
        pd.put("question2_id",pd.getString("question2_id").replace("[","").replace("]","").replace("\"",""));
        pd.put("question3_id",pd.getString("question3_id").replace("[","").replace("]","").replace("\"",""));
        pd.put("class_id",pd.getString("class_id").replace("[","").replace("]","").replace("\"",""));
        pd.put("creattime",new Date());
        homeworkService.add(pd);
    }

    //学生查询当天作业量
    @ResponseBody
    @RequestMapping("selectNum")
    public PageData selectNum(HttpServletResponse response){
        init(response);
        PageData pd = this.getPageData();
        return homeworkService.selectNum(pd);
    }
}