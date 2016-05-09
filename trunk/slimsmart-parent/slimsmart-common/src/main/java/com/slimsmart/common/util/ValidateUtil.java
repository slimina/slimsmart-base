package com.shq.common.util;

import org.apache.commons.lang3.Validate;

import com.shq.common.util.string.StringUtil;

/**
 *  数据格式校验工具类
 * @author zhutianwei
 *
 */
public class ValidateUtil extends Validate{
	
	public static final String REGX_MOBILE="^0?\\d{11}$";
	public static final String REGX_PHONE="^\\(?\\d{3,4}[-\\)]?\\d{7,8}$";
	public static final String REGX_MAIL="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";;
	
	
	public static boolean length(String str,int minLen,int maxLen){
		if(StringUtil.isBlank(str) && minLen == 0 ){
			return true;
		}
		if(StringUtil.isNotBlank(str) &&str.length() >= minLen && str.length()<= maxLen){
			str = str.trim();
			return true;
		}
		return false;
	}
	
	public static boolean lengthTrim(String str,int minLen,int maxLen){
		if(StringUtil.isNotBlank(str)){
			str = str.trim();
		}
		return length(str,minLen,maxLen);
	}
	
	/**
	 * 移动电话
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str){
		if(StringUtil.isNotBlank(str)){
			return str.matches(REGX_MOBILE);
		}
		return false;
	}
	
	/**
	 * 电话格式校验
	 * @param str
	 * @return
	 */
	public static boolean isPhone(String str){
		if(StringUtil.isNotBlank(str)){
			return str.matches(REGX_PHONE);
		}
		return false;
	}
	
	/**
	 * 邮件格式校验
	 * @param str
	 * @return
	 */
	public static boolean isMail(String str){
		if(StringUtil.isNotBlank(str)){
			return str.matches(REGX_MAIL);
		}
		return false;
	}
}
