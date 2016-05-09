package com.slimsmart.weboss.common;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.slimsmart.common.util.string.StringUtil;
import com.slimsmart.common.web.LoginBaseFiler;
import com.slimsmart.common.Constants;

/**
 * 后台登录过滤器
 */
public class BackLoginFiler extends LoginBaseFiler {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String path = request.getServletPath();
		logger.trace("servletPath = " + path);
		String excludeUrl = getExcludeUrl();
		if(StringUtil.isNotBlank(excludeUrl) && validateExcludeUrl(path, excludeUrl.split(";"))){
			// 去除不需要过滤的URL
			logger.trace("excludeUrl = " + path);
			filterChain.doFilter(request, response);
		}else{
			if (request.getSession().getAttribute(Constants.BOSS_SESSION_KEY) != null) {
				filterChain.doFilter(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + "/login");
			}
		}
	}
}
