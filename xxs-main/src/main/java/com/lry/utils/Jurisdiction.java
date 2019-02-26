package com.lry.utils;

import com.lry.service.utils.SessionProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * 权限处理
 *
 */
public class Jurisdiction {
    /**
     * 获取当前登录的用户名
     *
     * @return
     */
    public static String getUsername(String sessionid, SessionProvider provider) {
        String username = provider.getAttribute(sessionid, Const.SESSION_USERNAME);
        System.out.println(username);
        if (StringUtils.isNotBlank(username)) {
            return username;
        }
        return null;
    }

    /**
     * 获取当前登录的用户id
     *
     * @return
     */
    public static String getUserid(String sessionid, SessionProvider provider) {
        return provider.getAttribute(sessionid, Const.SESSION_USERID).toString();
    }

    /**
     * 获取当前登录的用户身份
     * 0:超级管理员 1:管理员 2:教师 3:学生 4:家长
     */
    public static String getUserType(String sessionid, SessionProvider provider) {
        return provider.getAttribute(sessionid, Const.SESSION_USERTYPE).toString();
    }

    /**
     * shiro管理的session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
}
