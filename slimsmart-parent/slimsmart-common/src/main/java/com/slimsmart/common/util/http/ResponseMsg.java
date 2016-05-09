package com.slimsmart.common.util.http;

import java.io.Serializable;

public class ResponseMsg implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int code;
	private String message;
	private Object data;
	
	public int getCode() {
		return code;
	}
	public ResponseMsg setCode(int code) {
		this.code = code;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ResponseMsg setMessage(String message) {
		this.message = message;
		return this;
	}
	public Object getData() {
		return data;
	}
	public ResponseMsg setData(Object data) {
		this.data = data;
		return this;
	}
}
