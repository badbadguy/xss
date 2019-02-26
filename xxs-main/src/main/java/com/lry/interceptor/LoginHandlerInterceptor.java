package com.lry.interceptor;

import com.lry.service.utils.SessionProvider;
import com.lry.utils.Jurisdiction;
import com.lry.utils.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import com.lry.utils.Const;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
* 类名称：登录过滤，权限验证
* 类描述： 
* @author FH qq313596790[青苔]
* 作者单位： 
* 联系方式：
* 创建时间：2015年11月2日
* @version 1.6
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	@Resource(name="sessionService")
	private SessionProvider sessionProvider;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept");
		String path = request.getServletPath();
		if(handler instanceof DefaultServletHttpRequestHandler) {
			return true;
		}
		if(path.matches(Const.NO_INTERCEPTOR_PATH) || path.matches(Const.INTERFACE_PATH)){
			return true;
		}else{
			String sessionId = RequestUtils.getCSESSIONID(request, response);
			String userStr = sessionProvider.getAttribute(sessionId, Const.SESSION_USER);
			if(StringUtils.isNotBlank(userStr)){
				path = path.substring(1, path.length());
				Jurisdiction.hasJurisdiction(path,sessionId,sessionProvider); //访问权限校验

				return true;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;		
			}
		}
	}
	
}
