package com.shq.common.util;

/**
 * 
 * <b>短信验证码工具类</b><br/>
 * @date 2015年12月3日 下午5:45:00
 * @version V1.0
 */
public class MessageCodeUtil {
	/**
	 * 
	 * 获取短信验证码
	 * @param numberFlag 是否全部是数字
	 * @param length 获取的字符串长度
	 * @return
	 */
	public static String getVerCode(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
		int len = strTable.length();
		for (int i = 0; i < length; i++) {
			double dblR = Math.random() * len;
			int intR = (int) Math.floor(dblR);
			retStr += strTable.charAt(intR);
		}
		return retStr;
	}

}
