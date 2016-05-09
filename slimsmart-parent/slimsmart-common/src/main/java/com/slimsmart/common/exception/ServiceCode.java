package com.shq.common.exception;

public enum ServiceCode {
	
	SUCCESS(0,"操作成功"),
	FAULT(1,"操作失败"),
	
	//系统异常 400-599 以“E_”开头枚举
	E_400(400, "系统无法访问，请刷新再试"),
	E_403(403, "您没有权限访问"),
	E_404(404, "您访问的资源不存在"),
	E_500(500, "系统无法访问,请刷新再试"),
	
	
	//业务异常 400-499 以“S_”开头枚举
	S_600(600, "抱歉，您没有操作权限");
	
	
	private ServiceCode(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 通过值获取enmu对象
	 * 
	 * @return
	 */
	public static ServiceCode getByCode(int code) {
		for (ServiceCode dot : ServiceCode.values()) {
			if (code == dot.getCode()) {
				return dot;
			}
		}
		throw new IllegalArgumentException("error:Can't get enum with this code.");
	}
}
