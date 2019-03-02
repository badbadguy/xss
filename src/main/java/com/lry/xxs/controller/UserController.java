package com.lry.xxs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.xxs.model.User;
import com.lry.xxs.service.*;
import com.lry.xxs.utils.BaseController;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.ResultJson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private ParentService parentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private RedisService redisService;

    private ResultJson resultJson = null;

    //设置cors跨域请求
    public void init(ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }

    //新增
    @RequestMapping("/add")
    public void add(HttpServletResponse response) throws Exception {
        init(response);
        userService.add(this.getPageData());
    }

    //删除
    @RequestMapping("/delete")
    public void delete(String id, HttpServletResponse response) throws Exception {
        init(response);
        userService.deleteById(id);
    }

    //修改基本信息
    @RequestMapping("/update")
    public void updateById(User user, HttpServletResponse response) throws Exception {
        init(response);
        userService.updateById(user);
    }

    //修改密码
    @ResponseBody
    @RequestMapping("/changePw")
    public MappingJacksonValue changePw(String name, String oldPassword, String newPassword, HttpServletResponse response) throws Exception {
        init(response);
        switch (userService.checkPw(name, oldPassword)) {
            case 1:
                resultJson = new ResultJson(Boolean.TRUE, "密码错误");
                break;
            case 2:
                resultJson = new ResultJson(Boolean.TRUE, "用户不存在");
                break;
            case 666: {
                userService.changePw(name, newPassword);
                resultJson = new ResultJson(Boolean.TRUE, "登录成功");
                break;
            }
            default:
                resultJson = new ResultJson(Boolean.TRUE, "未知错误");
                break;
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //查询所有用户基本信息
    @ResponseBody
    @RequestMapping("/select")
    public MappingJacksonValue select(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        if (StringUtils.isBlank(pd.getString("pageNum")))
            pd.put("pageNum", "1");
        if (StringUtils.isBlank(pd.getString("pageSize")))
            pd.put("pageSize", "10");
        PageHelper.startPage(Integer.valueOf(pd.getString("pageNum")), Integer.valueOf(pd.getString("pageSize")));
        List<PageData> list = userService.select(pd);
        PageInfo<PageData> listInfo = new PageInfo<>(list);
        resultJson = new ResultJson(Boolean.TRUE, "查询成功", listInfo);
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //登录
    @ResponseBody
    @RequestMapping("/login")
    public MappingJacksonValue login(HttpSession session, String name, String password, Integer type, HttpServletResponse response) throws Exception {
        init(response);
        switch (userService.checkPw(name, password)) {
            case 1:
                resultJson = new ResultJson(Boolean.TRUE, "密码错误");
                break;
            case 2:
                resultJson = new ResultJson(Boolean.TRUE, "用户不存在");
                break;
            case 666: {
                PageData pd = userService.login(name, type);
                if (StringUtils.isNotBlank(pd.getString("error"))) {
                    resultJson = new ResultJson(Boolean.TRUE, pd.getString("error"));
                    break;
                } else {
                    redisService.addMap(session.getId(), pd);
                    resultJson = new ResultJson(Boolean.TRUE, "登录成功", session.getId());
                }
            }
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //修改父母用户信息
    @ResponseBody
    @RequestMapping("/updatep")
    public void updatep(HttpServletResponse response) {
        init(response);
        parentService.updateById(this.getPageData());
    }

    //查询父母用户信息
    @ResponseBody
    @RequestMapping("/selectp")
    public MappingJacksonValue selectp(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        if (StringUtils.isBlank(pd.getString("pageNum")))
            pd.put("pageNum", "1");
        if (StringUtils.isBlank(pd.getString("pageSize")))
            pd.put("pageSize", "10");
        PageHelper.startPage(Integer.valueOf(pd.getString("pageNum")), Integer.valueOf(pd.getString("pageSize")));
        List<PageData> list = parentService.select(pd);
        PageInfo<PageData> listInfo = new PageInfo<>(list);
        resultJson = new ResultJson(Boolean.TRUE, "查询成功", listInfo);
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //修改学生用户信息
    @ResponseBody
    @RequestMapping("/updates")
    public void updates(HttpServletResponse response) {
        init(response);
        studentService.updateById(this.getPageData());
    }

    //查询学生用户信息
    @ResponseBody
    @RequestMapping("/selects")
    public MappingJacksonValue selects(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        if (StringUtils.isBlank(pd.getString("pageNum")))
            pd.put("pageNum", "1");
        if (StringUtils.isBlank(pd.getString("pageSize")))
            pd.put("pageSize", "10");
        PageHelper.startPage(Integer.valueOf(pd.getString("pageNum")), Integer.valueOf(pd.getString("pageSize")));
        List<PageData> list = studentService.select(pd);
        PageInfo<PageData> listInfo = new PageInfo<>(list);
        resultJson = new ResultJson(Boolean.TRUE, "查询成功", listInfo);
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //修改教师用户信息
    @ResponseBody
    @RequestMapping("/updatet")
    public void updatet(HttpServletResponse response) {
        init(response);
        teacherService.updateById(this.getPageData());
    }

    //查询教师用户信息
    @ResponseBody
    @RequestMapping("/selectt")
    public MappingJacksonValue selectt(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        if (StringUtils.isBlank(pd.getString("pageNum")))
            pd.put("pageNum", "1");
        if (StringUtils.isBlank(pd.getString("pageSize")))
            pd.put("pageSize", "10");
        PageHelper.startPage(Integer.valueOf(pd.getString("pageNum")), Integer.valueOf(pd.getString("pageSize")));
        List<PageData> list = teacherService.select(pd);
        PageInfo<PageData> listInfo = new PageInfo<>(list);
        resultJson = new ResultJson(Boolean.TRUE, "查询成功", listInfo);
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }
}
