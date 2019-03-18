package com.lry.xxs.controller;

import com.lry.xxs.service.SubjectService;
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

@RestController
@RequestMapping("/subject")
public class SubjectController extends BaseController {

    @Autowired
    private SubjectService subjectService;

    private ResultJson resultJson = null;

    //设置cors跨域请求
    public void init(ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }

    @RequestMapping("/add")
    public void add(HttpServletResponse response) {
        init(response);
        subjectService.add(this.getPageData());
    }

    @RequestMapping("/delete")
    public void delete(HttpServletResponse response) {
        init(response);
        subjectService.delete(this.getPageData().getString("subject_id"));
    }

    @RequestMapping("/update")
    public void update(HttpServletResponse response) {
        init(response);
        subjectService.update(this.getPageData());
    }

    @ResponseBody
    @RequestMapping("/select")
    public MappingJacksonValue select(HttpServletResponse response) {
        init(response);
        List<PageData> list = subjectService.select(this.getPageData());
        resultJson = new ResultJson(Boolean.TRUE, "查询成功", list);
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }
}
