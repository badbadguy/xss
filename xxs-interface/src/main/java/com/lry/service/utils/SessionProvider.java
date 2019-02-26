package com.lry.service.utils;

public interface SessionProvider {
	public void setAttribute(String sessionid, String key, String value);

	public String getAttribute(String sessionid, String key);

	public void removeAttribute(String sessionid, String key);
}
