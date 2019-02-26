package com.lry.utils;

import com.lry.service.utils.SessionProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * 权限处理
 *
 * @author:fh qq 313596790[青苔]
 * 修改日期：2015/11/19
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
     * 获取当前登录的用户名
     *
     * @return
     */
    public static String getUserid(String sessionid, SessionProvider provider) {
        return provider.getAttribute(sessionid, Const.SESSION_USERID).toString();
    }

    /**
     * shiro管理的session
     *
     * @return
     */
    public static Session getSession() {
        //Subject currentUser = SecurityUtils.getSubject();
        return SecurityUtils.getSubject().getSession();
    }
}
