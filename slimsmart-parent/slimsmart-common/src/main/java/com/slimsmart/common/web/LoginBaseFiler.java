package com.slimsmart.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 登录过滤器基类
 * 
 * @author zhutianwei
 * 
 */
public abstract class LoginBaseFiler extends OncePerRequestFilter {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	  

	/**
	 * 不需要过滤的URL 里面可以包含星号
	 */
	private String excludeUrl; // 多个以分号分隔

	public String getExcludeUrl() {
		return excludeUrl;
	}

	public void setExcludeUrl(String excludeUrl) {
		this.excludeUrl = excludeUrl;
	}

	/**
	 * 验证请求路径是否过滤
	 * 
	 * @param path
	 * @param excludeUrlArray
	 * @return
	 */
	protected boolean validateExcludeUrl(String path, String[] excludeUrlArray) {
		for (String url : excludeUrlArray) {
			if (wildcardStarMatch(url, path)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 
	 * 字符串中存在星号（表示多个字符）匹配
	 * 
	 * @param pattern
	 *            包含星号的字符串
	 * @param str
	 *            要匹配的字符串
	 * @return
	 */
	private boolean wildcardStarMatch(String pattern, String str) {
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0, patternLength = pattern.length(); patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				// 通配符星号*表示可以匹配任意多个字符
				while (strIndex < strLength) {
					if (wildcardStarMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			} else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}
}
