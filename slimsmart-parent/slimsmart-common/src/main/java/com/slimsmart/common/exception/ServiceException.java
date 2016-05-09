package com.slimsmart.common.exception;

/**
 * 自定义业务异常类
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int code;
	private String message;

	public ServiceException() {
	}

	public ServiceException(ServiceException e) {
		super(e);
		this.code = e.getCode();
		this.message = e.getMessage();
	}

	public ServiceException(String message) {
		super(message);
		this.message = message;
	}

	public ServiceException(String message, int code) {
		super(message);
		this.message = message;
		this.code = code;
	}

	public ServiceException(int code) {
		this.code = code;
	}

	public ServiceException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	public ServiceException(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}

	public ServiceException(Throwable cause, String message, int code) {
		super(cause);
		this.message = message;
		this.code = code;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

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
}
