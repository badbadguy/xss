package com.lry.xxs.controller;

import com.lry.xxs.service.QuestionService;
import com.lry.xxs.utils.BaseController;
import com.lry.xxs.utils.FastDFS;
import com.lry.xxs.utils.PageData;
import com.lry.xxs.utils.ResultJson;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    //题目图片上传
    @RequestMapping("picture")
    public String picture(HttpServletResponse response, MultipartFile file)throws Exception{
        init(response);
        FastDFS fastDFS = new FastDFS();
        return fastDFS.saveFile(file);
    }

    //新增数学应用题
    @RequestMapping("/addMath")
    public void addMathApp(HttpServletResponse response,String[] imagesurl) {
        init(response);
        PageData pd = this.getPageData();
        JSONObject jsonObject = JSONObject.fromObject(pd.getString("tempjson"));
//        pd.putAll((PageData)JSONObject.toBean(jsonObject,PageData.class));
//        int i = 0;
//        String tempLink =null;
//        while (true){
//            if(!pd.containsKey("question_title"+i))
//                break;
//            PageData temppd = new PageData();
//            temppd.put("question_type",pd.getString("question_type"));
//            temppd.put("question_title",pd.getString("question_title"+i));
//            temppd.put("question_answer1",pd.getString("question_answer1"+i));
//            temppd.put("question_answer2",pd.getString("question_answer2"+i));
//            temppd.put("question_answer3",pd.getString("question_answer3"+i));
//            temppd.put("question_answer4",pd.getString("question_answer4"+i));
//            temppd.put("question_answerr",pd.getString("question_answerr"+i));
//            temppd.put("question_remark",pd.getString("question_remark"+i));
////            temppd.put("question_image",tempUrl[i]);
//            if(i>0)
//                temppd.put("question_link",tempLink);
//            temppd.put("subject_id",pd.getString("subject_id"));
//            tempLink = questionService.addMath(temppd);
//            i++;
//        }
    }

    //根据id删除题目
    @RequestMapping("/delete")
    public void delete(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        questionService.delete(pd.getString("question_id"));
    }

    //根据id修改题目信息
    @RequestMapping("/update")
    public void update(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        questionService.update(pd);
    }

    //查询题目信息
    @ResponseBody
    @RequestMapping("select")
    public MappingJacksonValue select(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        try {
            List<PageData> list = questionService.select(pd);
            resultJson = new ResultJson(Boolean.TRUE, "查询成功", list);
        } catch (Exception e) {
            resultJson = new ResultJson(Boolean.FALSE, "查询出错", e);
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }
}
