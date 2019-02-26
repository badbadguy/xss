package com.lry.manager.controller;

import com.google.gson.Gson;
import com.lry.controller.BaseController;
import com.lry.entity.system.User;
import com.lry.service.utils.SessionProvider;
import com.lry.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController extends BaseController{

    @Resource(name="sessionService")
    private SessionProvider sessionProvider;

    /**
     * 请求登录，验证用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login_login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String sessionId = RequestUtils.getCSESSIONID(request, response);
        Map<String, String> map = new HashMap<String, String>();
        PageData pd = this.getPageData();
        String errInfo = "";
        String KEYDATA[] = pd.getString("KEYDATA").split(",fh,");
        if (null != KEYDATA && KEYDATA.length == 3) {
            String sessionCode = sessionProvider.getAttribute(sessionId,
                    Const.CODE);
            String code = KEYDATA[2];
            if (null == code || "".equals(code)) {// 判断效验码
                errInfo = "nullcode"; // 效验码为空
            } else {
                String USERNAME = KEYDATA[0]; // 登录过来的用户名
                String PASSWORD = KEYDATA[1]; // 登录过来的密码
                pd.put("USERNAME", USERNAME);
                if ((Tools.notEmpty(sessionCode) && sessionCode
                        .equalsIgnoreCase(code))) { // 判断登录验证码
                    String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD)
                            .toString(); // 密码加密
                    pd.put("PASSWORD", passwd);
                    pd = userService.getUserByNameAndPwd(pd); // 根据用户名和密码去读取用户信息
                    if (pd != null) {
                        pd.put("LAST_LOGIN", DateUtil.getTime().toString());
                        //userService.updateLastLogin(pd);
                        User user = new User();
                        user.setUser_id(pd.getString("USER_ID"));
                        user.setUser_name(pd.getString("USERNAME"));
                        user.setUser_password(pd.getString("PASSWORD"));
                        user.setUser_nickname(pd.getString("NAME"));
                        user.setUser_type(pd.getString("ROLE_ID"));
                        user.setCreattime(pd.getString("LAST_LOGIN"));
                        user.setUpdatetime(pd.getString("QYNAME"));
                        user.setUser_image(pd.getString("STATUS"));
                        user.setRole(role);
                        sessionProvider.removeAttribute(sessionId, Const.CODE);
                        // 保存用户信息到Redis中
                        sessionProvider.setAttribute(sessionId,
                                Const.SESSION_USER, new Gson().toJson(user));

                        // shiro加入身份验证
                        Subject subject = SecurityUtils.getSubject();
                        UsernamePasswordToken token = new UsernamePasswordToken(
                                USERNAME, PASSWORD);
                        try {
                            subject.login(token);
                        } catch (AuthenticationException e) {
                            errInfo = "身份验证失败！";
                        }
                    } else {
                        errInfo = "usererror"; // 用户名或密码有误
                        logBefore(logger, USERNAME + "登录系统密码或用户名错误");
                    }
                } else {
                    errInfo = "codeerror"; // 验证码输入有误
                }
                if (Tools.isEmpty(errInfo)) {
                    //添加最后登录时间
                    PageData pdtemp = new PageData();
                    pdtemp.put("USER_ID",pd.getString("USER_ID"));
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    pdtemp.put("LAST_LOGIN",df.format(new Date()));
                    userService.updLoginTime(pdtemp);

                    errInfo = "success"; // 验证成功
                    logBefore(logger, USERNAME + "登录系统");
                }
            }
        } else {
            errInfo = "error"; // 缺少参数
        }
        map.put("result", errInfo);
        return AppUtil.returnObject(new PageData(), map);
    }

}
