package com.shq.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
	
	private static ApplicationContext ctx;

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}
	
    public void setApplicationContext(ApplicationContext ctx) throws BeansException { 
    	ApplicationContextProvider.ctx = ctx; 
    } 
    
	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) ctx.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 * 如果有多个Bean符合Class, 取出第一个.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		checkApplicationContext();
		return ctx.getBean(requiredType);
	}

	/**
	 * 清除applicationContext静态变量.
	 */
	public static void cleanApplicationContext() {
		ctx = null;
	}

	private static void checkApplicationContext() {
		if (ctx == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义ApplicationContextProvider");
		}
	}

}