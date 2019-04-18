package com.lry.xxs.controller;

import com.lry.xxs.service.LeaveService;
import com.lry.xxs.utils.BaseController;
import com.lry.xxs.utils.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController extends BaseController {

    @Autowired
    private LeaveService leaveService;

    //设置cors跨域请求
    public void init(ServletResponse response) {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    }

    //新增留言
    @RequestMapping("/add")
    public void add(HttpServletResponse response){
        init(response);
        PageData pd = this.getPageData();
        leaveService.add(pd);
    }

    //删除留言
    @RequestMapping("delete")
    public void delete(HttpServletResponse response){
        init(response);
        PageData pd = this.getPageData();
        leaveService.delete(pd);
    }

    //查询留言
    @RequestMapping("select")
    public List<PageData> select(HttpServletResponse response)throws Exception{
        init(response);
        PageData pd = this.getPageData();
        return leaveService.select(pd);
    }
}
