package com.lry.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lry.entity.system.Menu;
import com.lry.service.utils.SessionProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 权限处理
 *
 * @author:fh qq 313596790[青苔]
 * 修改日期：2015/11/19
 */
public class Jurisdiction {

    /**
     * 访问权限及初始化按钮权限(控制按钮的显示 增删改查)
     *
     * @param menuUrl 菜单路径
     *                sessionid:RequestUtils.getCSESSIONID(request, response);
     */
    public static boolean hasJurisdiction(String menuUrl, String sessionid, SessionProvider provider) {
        //判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
        /**
         * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
         * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
         */
        String USERNAME = getUsername(sessionid, provider);    //获取当前登录者loginname
        List<Menu> menuList = new Gson().fromJson(provider.getAttribute(sessionid, USERNAME + Const.SESSION_menuList),
                new TypeToken<List<Menu>>() {
                }.getType());
        return readMenu(menuList, menuUrl, sessionid, provider, USERNAME);
    }

    /*
     * @Author HLS
     * @Description 校验菜单权限并初始按钮权限用于页面按钮显示与否(递归处理)
     * @Date 13:02 2018/12/7
     * @Param [menuList:传入的总菜单(设置菜单时，.do前面的不要重复), menuUrl:访问菜单地址, sessionid, provider, USERNAME]
     * @return boolean
     **/
    public static boolean readMenu(List<Menu> menuList, String menuUrl, String sessionid, SessionProvider provider, String USERNAME) {
        Boolean b = false;

        for (Menu menu : menuList) {
            //判断是否有子菜单
            List<Menu> subMenu = menu.getSubMenu();
            if (subMenu == null || subMenu.size() < 1) {
                b = isflag(menu, menuUrl, sessionid, provider, USERNAME);
            } else {
                b = readMenu(subMenu, menuUrl, sessionid, provider, USERNAME);
            }

            //是否查到了该菜单的权限
            if (b) {
                return b;
            }
        }

        return b;
    }

    /*
     * @Author HLS
     * @Description 校验菜单权限
     * @Date 11:47 2018/12/7
     * @Param [subMenu, menuUr, sessionid, provider, USERNAME]
     * @return boolean
     **/
    public static boolean isflag(Menu subMenu, String menuUrl, String sessionid, SessionProvider provider, String USERNAME) {
        boolean b = false;

        if (StringUtils.isNotBlank(subMenu.getMENU_URL()) && subMenu.getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])) {
            if (!subMenu.isHasMenu()) {
                //判断有无此菜单权限
                return b;
            } else {                                            //按钮判断
                Map<String, String> map = new Gson().fromJson(provider.getAttribute(sessionid, USERNAME + Const.SESSION_QX),
                        new TypeToken<Map<String, String>>() {
                        }.getType());//按钮权限(增删改查)
                map.remove("add");
                map.remove("del");
                map.remove("edit");
                map.remove("cha");
                String MENU_ID = subMenu.getMENU_ID();
                Boolean isAdmin = "admin".equals(USERNAME);
                map.put("add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin ? "1" : "0");
                map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin ? "1" : "0");
                map.put("edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin ? "1" : "0");
                map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin ? "1" : "0");
                provider.setAttribute(sessionid, USERNAME + Const.SESSION_QX, new Gson().toJson(map));    //重新分配按钮权限

                return true;
            }
        }

        return b;
    }


    /**校验菜单权限并初始按钮权限用于页面按钮显示与否(递归处理)
     * @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
     * @param menuUrl:访问地址
     * @return
     */
	/*public static boolean readMenu(List<Menu> menuList,String menuUrl,String sessionid,SessionProvider provider,String USERNAME){
		Boolean b  = false;
		for(int i=0;i<menuList.size();i++){
			//访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
			List<Menu> subMenuList = menuList.get(i).getSubMenu();

			for (Menu subMenu : subMenuList) {
				if(StringUtils.isNotBlank(subMenu.getMENU_URL())&&subMenu.getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])){
					if(!subMenu.isHasMenu()){
						//判断有无此菜单权限
						return b;
					}else{											//按钮判断
						Map<String, String> map = new Gson().fromJson(provider.getAttribute(sessionid,USERNAME + Const.SESSION_QX),
								new TypeToken<Map<String,String>>(){}.getType());//按钮权限(增删改查)
						map.remove("add");
						map.remove("del");
						map.remove("edit");
						map.remove("cha");
						String MENU_ID =  menuList.get(i).getMENU_ID();
						Boolean isAdmin = "admin".equals(USERNAME);
						map.put("add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin?"1":"0");
						map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin?"1":"0");
						map.put("edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin?"1":"0");
						map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin?"1":"0");
						provider.setAttribute(sessionid,USERNAME + Const.SESSION_QX, new Gson().toJson(map));	//重新分配按钮权限

						return true;
					}
				}
			}

		}

		return b;
	}*/

    /**
     * 按钮权限(方法中校验)
     *
     * @param menuUrl 菜单路径
     * @param type    类型(add、del、edit、cha)
     * @return
     */
    public static boolean buttonJurisdiction(String menuUrl, String type, String sessionid, SessionProvider provider) {
        //判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
        /**
         * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
         * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
         */
        String USERNAME = getUsername(sessionid, provider);    //获取当前登录者loginname
        List<Menu> menuList = new Gson().fromJson(provider.getAttribute(sessionid, USERNAME + Const.SESSION_menuList), new TypeToken<List<Menu>>() {
        }.getType()); //获取菜单列表
        return readMenuButton(menuList, menuUrl, sessionid, provider, USERNAME, type);
    }

    /**
     * 校验按钮权限(递归处理)
     *
     * @param menuList:传入的总菜单(设置菜单时，.do前面的不要重复)
     * @param menuUrl:访问地址
     * @return
     */
    public static boolean readMenuButton(List<Menu> menuList, String menuUrl, String sessionid, SessionProvider provider, String USERNAME, String type) {
        for (int i = 0; i < menuList.size(); i++) {
            if (StringUtils.isNotBlank(menuList.get(i).getMENU_URL()) && menuList.get(i).getMENU_URL().split(".do")[0].equals(menuUrl.split(".do")[0])) { //访问地址与菜单地址循环匹配，如何匹配到就进一步验证，如果没匹配到就不处理(可能是接口链接或其它链接)
                if (!menuList.get(i).isHasMenu()) {                //判断有无此菜单权限
                    return false;
                } else {                                            //按钮判断
                    Map<String, String> map = new Gson().fromJson(provider.getAttribute(sessionid, USERNAME + Const.SESSION_QX),
                            new TypeToken<Map<String, String>>() {
                            }.getType());//按钮权限(增删改查)
                    String MENU_ID = menuList.get(i).getMENU_ID();
                    Boolean isAdmin = "admin".equals(USERNAME);
                    if ("add".equals(type)) {
                        return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
                    } else if ("del".equals(type)) {
                        return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
                    } else if ("edit".equals(type)) {
                        return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
                    } else if ("cha".equals(type)) {
                        return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
                    }
                }
            } else {
                if (!readMenuButton(menuList.get(i).getSubMenu(), menuUrl, sessionid, provider, USERNAME, type)) {//继续排查其子菜单
                    return false;
                }
                ;
            }
        }
        return true;
    }

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
     * 获取当前按钮权限(增删改查)
     *
     * @return
     */
    public static Map<String, String> getHC(String sessionid, SessionProvider provider) {
        String a = provider.getAttribute(sessionid, getUsername(sessionid, provider) + Const.SESSION_QX);
        Type b = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> map = new Gson().fromJson(a,
                b);
        return map;
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
