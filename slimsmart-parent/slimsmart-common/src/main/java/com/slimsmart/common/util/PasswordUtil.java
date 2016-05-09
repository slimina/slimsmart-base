package com.slimsmart.common.util;

import com.slimsmart.common.util.code.MD5Util;

/**
 * 密码工具类
 * @author zhutianwei
 */
public class PasswordUtil {

	private final static String SALT = "NDIxMzYzMzRGOUVEQzQ4NUQ3NTZGMEY0NjA2NENEQ0M=";
	
	/**
	 * 获取明文的一次MD5密码 
	 * @param password 明文
	 * @return
	 */
	public static String getMD5(String password){
		return MD5Util.getDefaultMD5(password);
	}
	
	/**
	 * 获取加salt的MD5，存储数据库的数据
	 * @param passwordMd5 明文的一次MD5
	 * @return
	 */
	public static String getSaltMD5(String passwordMd5){
		return MD5Util.getDefaultMD5(passwordMd5+SALT);
	}
	
	/**
	 * 验证密码
	 * @param passwordMd5  明文的一次MD5
	 * @param source 数据密文密码
	 * @return
	 */
	public static boolean verifyPassword(String passwordMd5,String source){
		return source.equals(getSaltMD5(passwordMd5));
	}
}
