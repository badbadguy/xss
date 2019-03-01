package com.lry.xxs.utils;

import java.util.List;
import java.util.Map;

public class ResultJson {
	private Boolean success;
	private String message;
	private Map returnMap;
	private Object data;
	private List resultList;

	public ResultJson() {
		super();
	}

	public ResultJson(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public ResultJson(Boolean success, String message, Object data) {
		super();
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public ResultJson(Boolean success, String message, Map returnMap) {
		super();
		this.success = success;
		this.message = message;
		this.returnMap = returnMap;
	}
	
	public ResultJson(Boolean success, String message, List resultList) {
		super();
		this.success = success;
		this.message = message;
		this.resultList = resultList;
	}

	public ResultJson(Boolean success, String message, List resultList, Object data) {
		super();
		this.success = success;
		this.message = message;
		this.resultList = resultList;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(Map returnMap) {
		this.returnMap = returnMap;
	}
	
	@Override
	public String toString() {
		return "ResultJson [success=" + success + ", message=" + message
				+ ", returnMap=" + returnMap + ", data=" + data
				+ ", resultList=" + resultList + "]";
	}

}
