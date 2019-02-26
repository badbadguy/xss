package com.lry.utils;

import java.util.ResourceBundle;

/**
 * 项目名称：
 * @author:fh qq313596790[青苔]
 * 修改日期：2015/11/2
*/
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
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	public static final String SESSION_USERID = "USER_ID";			//用户ID
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_toLogin";				//登录地址
	public static final String SYSNAME = "admin/config/SYSNAME.txt";	//系统名称路径
	public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt";	//文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt";	//图片水印配置路径
	public static final String WEIXIN	= "admin/config/WEIXIN.txt";	//微信配置路径
	public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";//WEBSOCKET配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径

	
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(ynshistory_list)|(golist)|(yys_history_lists)|(yyskhlist)|(listMore)|(go_homepage)|(goMapDetail)|(login_toLogin)|(flushYnsCount)|(getBigData)|(flushManagerMap)|(refreshPortalMap)|(toSafe_detail)|(getGoodsDetail)"+
			"|(gosave)|(getYnsList)|(edit)|(imgUpload)|(uploadFile)|(retleaflet_save)|(Enterprise_positioning)|(logout)|(ynsApp)|(logout)|(main)|(plugins)|(code)|(app)|(uploadFiles)|(weixin)"+
			"|(static)|(invokMethod)|(main)|(websocket)|(getYnsDetail)|(jcx)|(nhup)|(z)|(getYnsListAjax)|(toPolicy_detail)|(toWork_detail)|(b)|(listGoods)|(jc)|(redwine01)|(userpromoter)|(retleaflet_edit)|(dynamic_work)).*";	//不对匹配该值的访问路径拦截（正则）

	public static final String INTERFACE_PATH = "/((jcxApp)|(head)|(scqyApp)|(pushMsg)|(jcxUpApp)|(tradeResult)|(nhUpApp)|(common)|(tool)|(xzqy)|(portal)|(portal2)|(authentic))/*.*";
	public static final String XZQY = "admin/config/XZQY.txt";//行政树
	public static final String KHDBM = "ca9bb5a2b6e948599b460e2013c21533";//客户端ID
	public static final String UserSig = "admin/config/UserSig.txt";//UserSig   聊天关键加密
	public static final String JSFW = "admin/config/JSFW.txt";//UserSig   聊天关键加密
	public static final int YX_TIME = 24;//检测箱检测数据有效时间 按小时为单位
	
	
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
