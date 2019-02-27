package com.lry.xxs.utils;

import java.util.ResourceBundle;

public class Const {

	//private static final String IP = "http://www.gd12316.com.cn/"; //正式环境IP
	//private static final String IP = "http://120.77.169.160:7006/";	//测试环境IP
	private static final String IP = "http://120.77.169.160:7006/"; //新正式环境IP
	public static final String URL = IP;
	private static ResourceBundle res = ResourceBundle.getBundle("resource");
	public static final String ADDRESS = IP + "z.do?m=";
	public static final String BIAOQIAN_ADDRESS = IP+"b.do?m=";
	public static final String JCADDRESS = IP + "jc.do?m=";
	//主体码地址
	public static final String APPLYCODEURL = IP + "portal/g?c=";

	public static final String CODE = "code";//保存到redis中验证码的key
	public static final String SESSION_USER = "sessionUser";	//redis用的用户key
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	public static final String SESSION_USERID = "USER_ID";				//用户ID
	public static final String SESSION_USERTYPE = "USER_TYPE";			//用户类型
	public static final String LOGIN = "/login_toLogin";				//登录地址
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
	public static final Integer PAGE = 10;								//分页数量

	//不对匹配该值的访问路径拦截（正则）
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(.*/(register))";

	public static final String KHDBM = "ca9bb5a2b6e948599b460e2013c21533";//客户端ID
	public static final String UserSig = "admin/config/UserSig.txt";//UserSig   聊天关键加密
	public static final String JSFW = "admin/config/JSFW.txt";//UserSig   聊天关键加密

	
	//=====================================创建聊天室=================================================
	public static final String USERSIG ="eJxlj01Pg0AURff8CsK2RofpjBQTF4poa1pbYzG2m8nIDPSl5cPHUKHG-65iE0m823Nyb*6HZdu2s5w*nco4LurcCNOW2rEvbIc4J3*wLEEJacQQ1T*omxJQC5kYjR10OeeUkL4DSucGEjgaUmWQQ2VQmgJ7WqW2otv67WHfJT4fMdZXIO3gLIyCyeON1FG2Qn0-joBt1vxhJ5O7ev7sxYvrYBPObg848PP2rX0l6SQNXhqoF1MSookGzZatDuzsKp4DYrnDhHjr0ZJRFeH7OL7sTRrI9PEY9Tj3OT-v0b3GCoq8EyhxuUuH5CeO9Wl9AZ*PYXs_";
	public static final String CREAT_CHATROOM1 ="https://console.tim.qq.com/v4/group_open_http_svc/create_group?usersig=";
	public static final String CREAT_CHATROOM2 ="&identifier=administrator";
	public static final String CREAT_CHATROOM3 ="&sdkappid=1400095844";
	public static final String CREAT_CHATROOM4 ="&random=";
	public static final String CREAT_CHATROOM5 ="&contenttype=json";
	//======================================创建聊天室结束============================================
	//======================================账号接入开始==============================================
	public static final String CREAT_CHATROOM6 ="https://console.tim.qq.com/v4/im_open_login_svc/account_import?usersig=";
	//======================================账号接入结束==============================================
	//======================================增加群组成员开始==========================================
	public static final String CREAT_CHATROOM7 ="https://console.tim.qq.com/v4/group_open_http_svc/add_group_member?usersig=";
	//======================================增加群组成员结束==========================================
	//======================================获取聊天内容开始==========================================
	public static final String CREAT_CHATROOM8 ="https://console.tim.qq.com/v4/group_open_http_svc/group_msg_get_simple?usersig=";
	//======================================获取聊天内容结束==========================================
	//======================================获取在线状态开始==========================================
	public static final String CREAT_CHATROOM9 ="https://console.tim.qq.com/v4/openim/querystate?usersig=";
	//======================================获取在线状态结束==========================================
	//======================================删除群组成员开始==========================================
	public static final String CREAT_CHATROOM10 = "https://console.tim.qq.com/v4/group_open_http_svc/delete_group_member?usersig=";
	//======================================删除群组成员结束==========================================
	//======================================解散群组开始==============================================
	public static final String CREAT_CHATROOM11 = "https://console.tim.qq.com/v4/group_open_http_svc/destroy_group?usersig=";
	//======================================解散群组结束==============================================
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
	
	
	public static final int REDIS_NYZSM=1;      //追溯码码表
	public static final int NYZSMRead=2;      //追溯码码表追溯查询记录表
	public static final int KCPD=3;      //农药追溯码码表追溯查询记录表
	//指定redis服务器
	public static final int NYZSM_NUM = 1;    //追溯码码表
	public static final int NYZSMRED_NUM = 2;    //追溯码码表追溯查询记录表
	public static final int REDIS_ZSMFZGX = 4; // 农药追溯码父子关系
	public static final int FZGX_NUM = 2;
	public static final int REDIS_NYZSPFZSM = 7; // 子码的父码
	public static final int ZFGX_NUM = 3;
	public static final int KCPD_NUM = 2;    //农药追溯码码表追溯查询记录表
	public static final int AUTHZSM_NUM = 3;    //农药追溯码码表追溯查询记录表
	public static final int AUTHZSM = 1;    //与包装箱关联的二维码存储的库
	public static final int ZSMJC = 2;    //与包装箱关联的二维码存储的库
	public static final int CZRQ_POOL = 3;    //保存采摘日期字符串： 端口
	public static final int CZRQ_KU = 10;    //保存采摘日期字符串 ：库
}
