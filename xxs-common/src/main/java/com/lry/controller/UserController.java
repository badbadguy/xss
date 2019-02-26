package com.lry.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.lry.service.UserManager;
import com.lry.service.utils.SessionProvider;
import com.lry.utils.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/** 
 * 说明：用户表
 * 创建人：FH Q313596790
 * 创建时间：2019-02-26
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	String menuUrl = "user/list.do"; //菜单地址(权限用)
	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="sessionService")
	private SessionProvider sessionProvider;
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String sessionId = RequestUtils.getCSESSIONID(request, response);
		logBefore(logger, Jurisdiction.getUsername(sessionId,sessionProvider)+"新增User");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		pd.put("USER_ID", this.get32UUID());	//主键
		userService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String sessionId = RequestUtils.getCSESSIONID(request, response);
		logBefore(logger, Jurisdiction.getUsername(sessionId,sessionProvider)+"删除User");
		PageData pd = this.getPageData();
		userService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest request,HttpServletResponse response) throws Exception{
	     String sessionId = RequestUtils.getCSESSIONID(request, response);
		logBefore(logger, Jurisdiction.getUsername(sessionId,sessionProvider)+"修改User");
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		userService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String sessionId = RequestUtils.getCSESSIONID(request, response);
		logBefore(logger, Jurisdiction.getUsername(sessionId,sessionProvider)+"列表User");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha",sessionId,sessionProvider)){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = userService.list(page);	//列出User列表
		if(varList.size()>0) mv.addObject("page",(Page) varList.get(0).remove("page"));
		mv.setViewName("controller/user/user_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
