package com.shq.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import com.shq.common.exception.ServiceException;

/**
 * 服务异常处理工具类
 */
public class ExceptionUtil {

	/**
	 * 将check的异常转化为uncheck的服务异常
	 */
	public static ServiceException convertToServiceException(Exception e) {
		if (e instanceof ServiceException) {
			return (ServiceException) e;
		} else {
			return new ServiceException(e);
		}
	}

	public static int getErrorCode(Throwable e) {
		if (e instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) e;
			return serviceException.getCode();
		} else {
			throw new ServiceException(e,"not ServiceException");
		}
	}

	public static String getMsg(Throwable e) {
		if (e instanceof ServiceException) {
			ServiceException serviceException = (ServiceException) e;
			return serviceException.getMessage();
		} else {
			return "";
		}
	}
	
	//输出异常堆栈
	public static String getExceptionTrace(Throwable e) {
		if(e!=null){
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			 e.printStackTrace(new PrintWriter(buf, true));
			 return buf.toString();
		}else{
			return "";
		}
	}
	
}
