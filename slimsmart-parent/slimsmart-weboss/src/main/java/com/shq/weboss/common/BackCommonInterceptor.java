package com.shq.weboss.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.shq.common.Constants;
import com.shq.common.web.CommonInterceptor;

/**
 * 管理后台 mvc拦截器
 */
public class BackCommonInterceptor extends CommonInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData)session.getAttribute(Constants.BOSS_SESSION_KEY);
		if(sessionData!=null){
			SessionHolder.set(sessionData);
		}
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		if(SessionHolder.get()!=null){
			SessionHolder.remove();
		}
		super.afterCompletion(request, response, handler, ex);
	}

}
