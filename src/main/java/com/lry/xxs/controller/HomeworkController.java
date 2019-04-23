package com.lry.xxs.controller;

import com.lry.xxs.service.HomeworkService;
import com.lry.xxs.service.ParentService;
import com.lry.xxs.service.StudentService;
import com.lry.xxs.utils.BaseController;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.ResultJson;
import com.lry.xxs.utils.UuidUtil;
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
    @Autowired
    private ParentService parentService;
    @Autowired
    private StudentService studentService;

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
    public void add(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        pd.put("homework_id", UuidUtil.get32UUID());
        //处理数据
        pd.put("question0_id", pd.getString("question0_id").replace("[", "").replace("]", "").replace("\"", ""));
        pd.put("question1_id", pd.getString("question1_id").replace("[", "").replace("]", "").replace("\"", ""));
        pd.put("question2_id", pd.getString("question2_id").replace("[", "").replace("]", "").replace("\"", ""));
        pd.put("question3_id", pd.getString("question3_id").replace("[", "").replace("]", "").replace("\"", ""));
        pd.put("class_id", pd.getString("class_id").replace("[", "").replace("]", "").replace("\"", ""));
        pd.put("creattime", new Date());
        homeworkService.add(pd);
    }

    //检查该班级是否已布置作业
    @RequestMapping("/bulin")
    public Boolean bulin(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        return homeworkService.bulin(pd);
    }

    //学生查询当天作业量
    @ResponseBody
    @RequestMapping("/selectNum")
    public PageData selectNum(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        return homeworkService.selectNum(pd);
    }

    //依次获取作业
    @ResponseBody
    @RequestMapping("/selectOne")
    public PageData selectOne(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        return homeworkService.selectOne(pd);
    }

    //家长查询学生作业情况
    @ResponseBody
    @RequestMapping("/checkStudent")
    public PageData checkStudent(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        PageData returnPD = new PageData();
        pd = parentService.select(pd).get(0);
        PageData temppd = new PageData();
        PageData aaaa = new PageData();
        aaaa.put("user_id",pd.getString("children_id"));
        aaaa = studentService.select(aaaa).get(0);
        temppd.put("class_id", aaaa.getString("student_class"));
        temppd.put("subject_id", "33018ef1b3b74a18b6d9f94bff995d79");
        returnPD.put("chinese", homeworkService.selectNum(temppd));
        temppd.put("subject_id", "79bed2b0e57c4f7f8e71b9817f03e3b9");
        returnPD.put("english", homeworkService.selectNum(temppd));
        temppd.put("subject_id", "cd84a79d6ee04e4d9630731b25b589d0");
        returnPD.put("math", homeworkService.selectNum(temppd));
        return returnPD;
    }
}