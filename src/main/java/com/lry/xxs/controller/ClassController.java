package com.lry.xxs.controller;

import com.lry.xxs.model.ClassReult;
import com.lry.xxs.service.ClassService;
import com.lry.xxs.service.TeacherService;
import com.lry.xxs.utils.BaseController;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RequestMapping("/class")
@RestController
public class ClassController extends BaseController {

    @Autowired
    private ClassService classService;
    @Autowired
    private TeacherService teacherService;

    private ResultJson resultJson = null;

    //设置cors跨域请求
    public void init(ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }

    //查询数据库中的班级
    @ResponseBody
    @RequestMapping("/checkclass2")
    public List<ClassReult> checkclass2()throws Exception{
        return classService.selectByGrade();
    }

    //管理员设置教师负责班级
    @RequestMapping("/chooses")
    public void chooses(HttpServletResponse response){
        init(response);
        PageData pd = this.getPageData();
        classService.chooseClass(pd);
    }

    //查询该老师负责的班级
    @ResponseBody
    @RequestMapping("/checkclass")
    public List<Map> checkclass(HttpServletResponse response)throws Exception{
        init(response);
        PageData pd = this.getPageData();
        List<PageData> list = teacherService.select(pd);
        pd = list.get(0);
        return classService.selectByTeacher(pd.getString("teacher_class"));
    }

    //查询班主任负责班级的学生申请
    @ResponseBody
    @RequestMapping("/checkstudent")
    public List<Map> checkstudent(HttpServletResponse response)throws Exception{
        init(response);
        PageData pd = this.getPageData();
        return classService.checkstudent(pd);
    }
}
