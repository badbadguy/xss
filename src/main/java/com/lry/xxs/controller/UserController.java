package com.lry.xxs.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lry.xxs.model.User;
import com.lry.xxs.service.*;
import com.lry.xxs.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

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

    //检查用户名重复情况
    @RequestMapping("/checkUserName")
    public Boolean checkUserName(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        return userService.checkUserName(pd);
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
        if (StringUtils.isNotBlank(user.getUser_name()))
            user.setUser_name(Base64.encodeBase64String(user.getUser_name().getBytes("UTF-8")));
        if (StringUtils.isNotBlank(user.getUser_nickname()))
            user.setUser_nickname(Base64.encodeBase64String(user.getUser_nickname().getBytes("UTF-8")));
        userService.updateById(user);
    }

    //修改密码
    @ResponseBody
    @RequestMapping("/changePw")
    public MappingJacksonValue changePw(String user_id, String oldPassword, String newPassword, HttpServletResponse response) throws Exception {
        init(response);
        switch (userService.checkPw(user_id, oldPassword)) {
            case 1:
                resultJson = new ResultJson(Boolean.FALSE, "密码错误");
                break;
            case 2:
                resultJson = new ResultJson(Boolean.FALSE, "用户不存在");
                break;
            case 666: {
                userService.changePw(user_id, newPassword);
                resultJson = new ResultJson(Boolean.TRUE, "修改成功");
                break;
            }
            default:
                resultJson = new ResultJson(Boolean.FALSE, "未知错误");
                break;
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //小程序内修改密码
    @RequestMapping("changePws")
    public void changePws(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        User user = new User();
        user.setUser_id(pd.getString("user_id"));
        user.setUser_password(pd.getString("user_password"));
        userService.changePws(user);
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
                resultJson = new ResultJson(Boolean.FALSE, "密码错误");
                break;
            case 2:
                resultJson = new ResultJson(Boolean.FALSE, "用户不存在");
                break;
            case 666: {
                PageData pd = userService.login(name, type);
                if (StringUtils.isNotBlank(pd.getString("error"))) {
                    resultJson = new ResultJson(Boolean.FALSE, pd.getString("error"));
                    break;
                } else {
                    Map tempMap = new HashMap();
                    tempMap.put("user_nickname", pd.getString("user_nickname"));
                    tempMap.put("user_id", pd.getString("user_id"));
                    redisService.addMap(session.getId(), tempMap);
                    resultJson = new ResultJson(Boolean.TRUE, "登录成功", session.getId());
                }
            }
        }
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //获取登录信息
    @RequestMapping("/getLogin")
    public PageData getLogin(HttpServletResponse response) {
        init(response);
        PageData pd = this.getPageData();
        Map returnMap = redisService.getMap(pd.getString("key"));
        if (returnMap.isEmpty()) {
            pd.put("success", false);
        } else {
            pd.put("success", true);
        }
        pd.putAll(returnMap);
        pd.remove("key");
        return pd;
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

    //根据用户id查询基本+角色信息
    @ResponseBody
    @RequestMapping("/selectAll")
    public MappingJacksonValue selectAll(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        if (StringUtils.isBlank(pd.getString("user_id")))
            resultJson = new ResultJson(Boolean.FALSE, "user_id不能为空");
        List<PageData> list = userService.select(pd);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 E");
        for (PageData temppd : list) {
            if (temppd.containsKey("creattime"))
                temppd.put("creattime", sf.format(temppd.get("creattime")));
            if (temppd.containsKey("updatetime"))
                temppd.put("updatetime", sf.format(temppd.get("updatetime")));
            if (temppd.containsKey("lastLogintime"))
                temppd.put("lastLogintime", sf.format(temppd.get("lastLogintime")));
        }
        List<PageData> list1 = new ArrayList<>();
        switch ((Integer) list.get(0).get("user_type")) {
            case 2:
                list1 = teacherService.select(pd);
                break;
            case 3:
                list1 = studentService.select(pd);
                break;
            case 4:
                list1 = parentService.select(pd);
                break;
        }
        pd.putAll(list.get(0));
        pd.putAll(list1.get(0));
        resultJson = new ResultJson(Boolean.TRUE, "查询成功", pd);
        MappingJacksonValue mjv = new MappingJacksonValue(resultJson);
        return mjv;
    }

    //更新登录最后登录时间
    @RequestMapping("/updateLastLoginTime")
    public void updateLastLoginTime(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        User user = new User();
        user.setUser_id(pd.getString("user_id"));
        user.setLastLogintime(new Date());
        userService.updateLastLoginTime(user);
    }

    //微信登录获取openid
    @ResponseBody
    @RequestMapping("/getOpenid")
    public String getOpenid(String code, HttpServletResponse response) throws Exception {
        init(response);
        String url = CommonInfo.WECHAT_GET_TOKEN_BY_CODE + "appid=" + CommonInfo.APPID + "&secret=" + CommonInfo.SECRET + "&code=" + code + "&grant_type=authorization_code";
        url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx53dac20eb7248329&secret=4f82e5b3b68c4a1d801d6cbf7430b895&js_code=" + code + "&grant_type=authorization_code";
        String jsonStr = HttpUtil.sendGet(url);
        String openId = null;
        if (jsonStr.contains("\"errcode\":40029")) {
            /*********************** 这里可以处理 ***********************/
            openId = "error";

        } else if (jsonStr.contains("\"errcode\":40163")) {
            /*********************** 这里可以处理 ***********************/
            openId = "error";
        } else {
            JSONObject jsonObject = JSONObject.fromObject(jsonStr);
//            String accessToken = (String) jsonObject.get("access_token");
//            String refreshToken = (String) jsonObject.get("refresh_token");
            openId = (String) jsonObject.get("openid");
        }
        return openId;
    }

    //查询学生（用于家长绑定学生）
    @ResponseBody
    @RequestMapping("/bindStudent")
    public List<PageData> bindStudent(HttpServletResponse response) throws Exception {
        init(response);
        PageData pd = this.getPageData();
        String tempName = pd.getString("user_name");
        if (StringUtils.isNotBlank(tempName)) {
            tempName = Base64.encodeBase64String(tempName.getBytes("UTF-8"));
            pd.put("user_name", tempName);
        }
        List<PageData> returnList = userService.bindStudent(pd);
        String[] c = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        for (PageData tempPD : returnList) {
            tempName = tempPD.getString("user_name");
            tempName = new String(Base64.decodeBase64(tempName), "UTF-8");
            tempPD.put("user_name", tempName);
            String nj = "";
            for (int i = 1; i <= c.length; i++) {
                if ((Integer) tempPD.get("class_grade") == i) {
                    nj = c[i - 1] + "年级";
                    break;
                }
            }
            for (int i = 1; i <= c.length; i++) {
                if ((Integer) tempPD.get("class_class") == i) {
                    nj = nj + c[i - 1] + "班";
                    break;
                }
            }
            tempPD.put("nj", nj);
            tempPD.put("switcher", "#off");
            tempPD.put("height", 80);
        }
        return returnList;
    }

    //返回对应信息（用于校验功能是否显示）
    @ResponseBody
    @RequestMapping("/sf")
    public PageData sf(HttpServletResponse response) throws Exception {
        init(response);
        PageData returnPD = new PageData();
        PageData pd = this.getPageData();
        PageData temppd = userService.select(pd).get(0);
        Integer type = (Integer) temppd.get("user_type");
        returnPD.put("user_type", type);
        switch (type) {
            case 2: //教师
                temppd = teacherService.select(pd).get(0);
                returnPD.put("teacher_class", StringUtils.isNotBlank(temppd.getString("teacher_class")));
                returnPD.put("teacher_subject", StringUtils.isNotBlank(temppd.getString("teacher_subject")));
                returnPD.put("teacher_ishead", (Integer) temppd.get("teacher_ishead") == 0);
                returnPD.put("teacher_subject", temppd.getString("teacher_subject"));
                break;
            case 3: //学生
                temppd = studentService.select(pd).get(0);
                returnPD.put("student_class", StringUtils.isNotBlank(temppd.getString("student_class")));
                returnPD.put("student_status", (Integer) temppd.get("student_status") == 2);
                break;
            case 4: //家长
                temppd = parentService.select(pd).get(0);
                returnPD.put("children_id", StringUtils.isNotBlank(temppd.getString("children_id")));
                break;
            default:
                break;
        }
        return returnPD;
    }

    //返回所有教师信息（用于管理员管理）
    @RequestMapping("checkAllT")
    public List<PageData> checkAllT(HttpServletResponse response) throws Exception {
        init(response);
        return teacherService.checkAllT();
    }
}
