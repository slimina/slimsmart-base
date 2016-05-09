package com.shq.common.web;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class CommonInterceptor extends HandlerInterceptorAdapter{
	
	protected Logger log = Logger.getLogger(getClass());
	
	/** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 
     *  
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */  
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//处理时间
		 long startTime = System.currentTimeMillis();
		 log.debug("Request URL::" + request.getRequestURL().toString()+":: Start Time=" + System.currentTimeMillis());
		 request.setAttribute("_startTime", startTime);
		return true;
	}

	//在业务处理器处理请求执行完成后,生成视图之前执行的动作 
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("Request URL::" + request.getRequestURL().toString()
		        + " Sent to Handler :: Current Time=" + System.currentTimeMillis());
	}

	/** 
     * 在DispatcherServlet完全处理完请求后被调用  
     *   当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */  
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		 long startTime = (Long) request.getAttribute("_startTime");
		 log.debug("Request URL::" + request.getRequestURL().toString()
		        + ":: End Time=" + System.currentTimeMillis());
		 log.debug("Request URL::" + request.getRequestURL().toString()
		        + ":: Time Taken=" + (System.currentTimeMillis() - startTime));
		 HandlerMethod handlerMethod = (HandlerMethod) handler;
	     Method method = handlerMethod.getMethod();
	     log.debug("request method : "+method.getClass().getName());
	}
}
