package com.lry.manager.service;

import com.lry.service.utils.SessionProvider;
import com.lry.utils.JedisUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 保存用户名或验证码到Redis中
 * Session共享
 * @author lx
 */
@Service("sessionService")
public class SessionService implements SessionProvider {

	@Autowired
	private JedisUtilService jedisUtil;
	private Integer exp = 30;
	
	public void setExp(Integer exp) {
		this.exp = exp;
	}

	@Override
	public void setAttribute(String cSessionId, String key, String value) {
		// TODO Auto-generated method stub
		//保存用户名到Redis中
		//　K　：　CSESSIONID:Constants.USER_NAME   == name
		jedisUtil.getSTRINGS().set(cSessionId + ":" +  key, value);
		//时间 
		jedisUtil.expire(cSessionId + ":" +  key, 60*exp);
	}

	@Override
	public String getAttribute(String cSessionId, String key) {
		// TODO Auto-generated method stub
		//fbb2016
		String value = jedisUtil.getSTRINGS().get(cSessionId + ":" +  key);
		if(null != value){
			//时间 
			jedisUtil.expire(cSessionId + ":" +  key, 60*exp);
		}
		return value;
	}

	@Override
	public void removeAttribute(String sessionid, String key) {
		jedisUtil.getKEYS().del(sessionid + ":" +  key);
	}
	
}

