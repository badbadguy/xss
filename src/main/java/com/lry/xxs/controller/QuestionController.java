package com.lry.xxs.controller;

import com.lry.xxs.service.QuestionService;
import com.lry.xxs.utils.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @RequestMapping("wori")
    public void s(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        String json = pd.getString("hengji").replace("[","'").replace("]","").replace("'","");
        String img = pd.getString("img").replace("[","'").replace("]","").replace("\"","").replace("'","");
        String[] tempJson = json.split("},");
        String[] imgJson = img.split(",");
        List<PageData> wasai = new ArrayList<>();
        for(int i=0;i<tempJson.length;i++){
            PageData tempPD = new PageData();
            if(i!=tempJson.length-1){
                tempJson[i] = tempJson[i] +"}";
            }
            JSONObject jsonObject = JSONObject.fromObject(tempJson[i]);
            tempPD = (PageData) JSONObject.toBean(jsonObject, PageData.class);
            wasai.add(tempPD);
        }
        String question_link = UuidUtil.get32UUID();
        int tempI = 0;
        for(PageData wangleta : wasai){
            PageData zyzz = new PageData();
            if(tempI == 0){
                zyzz.put("question_id",question_link);
            }else {
                zyzz.put("question_id",UuidUtil.get32UUID());
                zyzz.put("question_link",question_link);
            }
            zyzz.put("question_title",wangleta.getString("eques"));
            zyzz.put("question_answer1",wangleta.getString("eans1"));
            zyzz.put("question_answer2",wangleta.getString("eans2"));
            zyzz.put("question_answer3",wangleta.getString("eans3"));
            zyzz.put("question_answer4",wangleta.getString("eans4"));
            zyzz.put("question_type",3);
            zyzz.put("question_answerr",wangleta.getString("estate"));
            zyzz.put("question_remark",wangleta.getString("eexp"));
            zyzz.put("question_image",imgJson[tempI]);
            zyzz.put("subject_id",pd.getString("subject_id"));
            questionService.add1(zyzz);
            tempI++;
        }
    }

    //题目图片上传
    @RequestMapping("picture")
    public String picture(HttpServletResponse response, MultipartFile file) throws Exception {
        init(response);
        FastDFS fastDFS = new FastDFS();
        return fastDFS.saveFile(file);
    }

    //新增题目
    @RequestMapping("/add")
    public void add(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        questionService.add(pd);
    }

    //新增语文英语题目
    @RequestMapping("/addCommon")
    public void addCommon(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        JSONObject jsonObject = JSONObject.fromObject(pd.getString("tempjson"));
        pd.putAll((PageData) JSONObject.toBean(jsonObject, PageData.class));
        pd.remove("tempjson");
        if (StringUtils.isBlank(pd.getString("question_image")))
            pd.remove("question_image");
        if (StringUtils.isBlank(pd.getString("question_answer1")))
            pd.remove("question_answer1");
        if (StringUtils.isBlank(pd.getString("question_answer2")))
            pd.remove("question_answer2");
        if (StringUtils.isBlank(pd.getString("question_answer3")))
            pd.remove("question_answer3");
        if (StringUtils.isBlank(pd.getString("question_answer4")))
            pd.remove("question_answer4");
        if (StringUtils.isBlank(pd.getString("question_answerr")))
            pd.remove("question_answerr");
        if (StringUtils.isBlank(pd.getString("question_answers")))
            pd.remove("question_answers");
        if (StringUtils.isBlank(pd.getString("question_remark")))
            pd.remove("question_remark");
        questionService.add(pd);
    }

    //新增数学题
    @RequestMapping("/addMath")
    public void addMathApp(HttpServletResponse response, String[] imagesurl) {
        init(response);
        PageData pd = this.getPageData();
        JSONObject jsonObject = JSONObject.fromObject(pd.getString("tempjson"));
        pd.putAll((PageData) JSONObject.toBean(jsonObject, PageData.class));
        imagesurl[0] = imagesurl[0].substring(1, imagesurl[0].length());
        imagesurl[imagesurl.length - 1] = imagesurl[imagesurl.length - 1].substring(0, imagesurl[imagesurl.length - 1].length() - 1);
        int tempi = 0;
        for (String tempString : imagesurl) {
            if (StringUtils.isNotBlank(tempString)) {
                if (tempString.equals("null")) tempString = "";
                else tempString = tempString.substring(1, tempString.length() - 1);
                imagesurl[tempi] = tempString;
            }
            tempi++;
        }
        String tempLink = null;
        int i = 0;
        while (true) {
            if (!pd.containsKey("question_title" + i))
                break;
            PageData temppd = new PageData();
            if (StringUtils.isNotBlank(pd.getString("question_type")))
                temppd.put("question_type", pd.getString("question_type"));
            if (StringUtils.isNotBlank(pd.getString("question_title" + i)))
                temppd.put("question_title", pd.getString("question_title" + i));
            if (StringUtils.isNotBlank(pd.getString("question_answer1" + i)))
                temppd.put("question_answer1", pd.getString("question_answer1" + i));
            if (StringUtils.isNotBlank(pd.getString("question_answer2" + i)))
                temppd.put("question_answer2", pd.getString("question_answer2" + i));
            if (StringUtils.isNotBlank(pd.getString("question_answer3" + i)))
                temppd.put("question_answer3", pd.getString("question_answer3" + i));
            if (StringUtils.isNotBlank(pd.getString("question_answer4" + i)))
                temppd.put("question_answer4", pd.getString("question_answer4" + i));
            if (StringUtils.isNotBlank(pd.getString("question_answers" + i)))
                temppd.put("question_answers", pd.getString("question_answers" + i));
            if (StringUtils.isNotBlank(pd.getString("question_answerr" + i)))
                temppd.put("question_answerr", pd.getString("question_answerr" + i));
            if (StringUtils.isNotBlank(pd.getString("question_remark" + i)))
                temppd.put("question_remark", pd.getString("question_remark" + i));
            if (imagesurl.length > i && StringUtils.isNotBlank(imagesurl[i]))
                temppd.put("question_image", imagesurl[i]);
            if (i > 0)
                temppd.put("question_link", tempLink);
            temppd.put("subject_id", pd.getString("subject_id"));
            tempLink = questionService.add(temppd);
            i++;
        }
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
            for (PageData tempPD : list) {
                switch ((Integer) tempPD.get("question_type")) {
                    case 0:
                        tempPD.put("type", "单选题");
                        break;
                    case 1:
                        tempPD.put("type", "语音题");
                        break;
                    case 2:
                        tempPD.put("type", "填空题");
                        break;
                    case 3:
                        tempPD.put("type", "应用题");
                        break;
                }
            }
            resultJson = new ResultJson(Boolean.TRUE, "查询成功", list);
        } catch (Exception e) {
            System.out.println(e);
            resultJson = new ResultJson(Boolean.FALSE, "查询出错", e);
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //查询题目信息（指定类型返回）
    @ResponseBody
    @RequestMapping("select1")
    public MappingJacksonValue select1(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        try {
            List<PageData> list = questionService.select1(pd);
            if (pd.getString("question_type").equals("3")) {
                for (PageData temppd : list) {
                    PageData temptemp = new PageData();
                    temptemp.put("question_link", temppd.getString("question_id"));
                    List<PageData> tempList = questionService.select(temptemp);
                    temppd.put("children", tempList);
                }
            }
            resultJson = new ResultJson(Boolean.TRUE, "查询成功", list);
        } catch (Exception e) {
            System.out.println(e);
            resultJson = new ResultJson(Boolean.FALSE, "查询出错", e);
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //查询题目信息（指定类型返回-用于布置作业）
    @ResponseBody
    @RequestMapping("select2")
    public MappingJacksonValue select2(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        try {
            List<PageData> list = questionService.select1(pd);
            List<PageData> tempList = new ArrayList<PageData>();
            for (PageData tempPd : list) {
                PageData temp = new PageData();
                temp.put("key", tempPd.getString("question_id"));
                temp.put("value", tempPd.getString("question_title"));
                tempList.add(temp);
            }
            resultJson = new ResultJson(Boolean.TRUE, "查询成功", tempList);
        } catch (Exception e) {
            System.out.println(e);
            resultJson = new ResultJson(Boolean.FALSE, "查询出错", e);
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //答题
    @ResponseBody
    @RequestMapping("/answer")
    public PageData answer(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        return questionService.answer(pd);
    }

    //应用题提交完成状态以及错题录入
    @ResponseBody
    @RequestMapping("/xigua")
    public void update3(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        questionService.update3(pd);
    }
}