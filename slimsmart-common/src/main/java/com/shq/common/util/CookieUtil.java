package com.shq.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	
	/**
	 * 设置一个Cookie，自定义Cookie时间，如果Cookie存在会进行覆盖
	 * 
	 * @param response
	 * @param cookieName
	 * @param value
	 * @param cookieTime
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String value, int cookieTime) {
		Cookie cookie = new Cookie(cookieName,value);
		cookie.setPath("/");
		cookie.setMaxAge(cookieTime);
		response.addCookie(cookie);
	}
	
	/**
	 * 增加一个Cookie, 自定义Cookie时间，如果Cookie存在会反回失败
	 * 
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param value
	 * @param cookieTime
	 * @return
	 */
	public static boolean addCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,String value, int cookieTime) {
		if (null == getCookie(request, cookieName)) {
			Cookie cookie = new Cookie(cookieName, value);
			cookie.setPath("/");
			cookie.setMaxAge(cookieTime);
			response.addCookie(cookie);
			return true;
		}
		return false;
	}
	/**
	 * 设置一个Cookie，默认永久Cookie，如果Cookie存在会进行覆盖
	 * 
	 * @param response
	 * @param cookieName
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String cookieName, String value) {
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}
 
	/**
	 * 增加一个Cookie，默认永久Cookie，如果Cookie存在会反回失败
	 * 
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param value
	 * @return
	 */
	public static boolean addCookie(HttpServletRequest request, HttpServletResponse response, String cookieName,
			String value) {
		if (null == getCookie(request, cookieName)) {
			Cookie cookie = new Cookie(cookieName, value);
			cookie.setPath("/");
			cookie.setMaxAge(-1);
			response.addCookie(cookie);
			return true;
		}
		return false;
	}
	
	/**
	 * 移除一个Cookie
	 * 
	 * @param request
	 * @param response
	 * @param cookieName
	 * @return
	 */
	public static boolean removeCookie(HttpServletRequest request, HttpServletResponse response, final String cookieName) {
		if (cookieName != null && !cookieName.isEmpty()) {
			Cookie cookie = getCookie(request, cookieName);
			if (cookie != null) {
				cookie.setPath("/");// 不要漏掉
				cookie.setMaxAge(0);// 如果0，就说明立即删除
				response.addCookie(cookie);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获得一个Cookie
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, final String cookieName) {
		Cookie[] cookies =  request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equalsIgnoreCase(cookieName)) {
					return c;
				}
			}
		}
		return null;
	}
	
	public static String get(HttpServletRequest request, final String k) {
		// 从cookie中取
		Cookie cookie = getCookie(request, k);
		return (null != cookie) ? cookie.getValue() : null;
	}
}
