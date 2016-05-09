package com.shq.common;

import java.util.ArrayList;
import java.util.List;

import com.shq.common.util.file.ResourcesUtil;

/**
 * 存储静态常量
 * @author zhutianwei
 *
 */
public class Constants {
	
	/**
	 * session存储标识
	 */
	public static final String BOSS_SESSION_KEY = "_session-backuser";
	public static final String KAPTCHA_SESSION_KEY="_kaptcha-key";
	
	//开关常量
	public static final String YES="1";
	public static final String NO="2";
	
	/**
	 * 
	 * 获取图片url的前缀 
	 * @return
	 */
	public static String getImageUrlPrefix(){
		return ResourcesUtil.getString("image.url.prefix");
	}
	
	/**
	 * 上传图片所适用的后缀名
	 */
	public static final List<String> IMAGE_TYPE_LIST = new ArrayList<String>();
	
	static {
		IMAGE_TYPE_LIST.add(".jpg");
		IMAGE_TYPE_LIST.add(".gif");
		IMAGE_TYPE_LIST.add(".png");
		IMAGE_TYPE_LIST.add(".jpeg");
		IMAGE_TYPE_LIST.add(".bmp");
	}
}
