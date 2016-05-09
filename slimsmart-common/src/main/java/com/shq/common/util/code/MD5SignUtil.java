package com.shq.common.util.code;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

/**
 * 签名工具类
 * @author zhutianwei
 *
 */
public class MD5SignUtil {
	
	/**
	 * 获取签名字符串
	 * @param params
	 * @param key
	 * @param charset
	 * @return
	 */
	public static String getSign(Map<String,Object> params,String key,String charset){
		Map<String, Object> map = new TreeMap<String, Object>();
		map.putAll(params);
		StringBuffer sb = new StringBuffer();
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while(iter.hasNext()) {
			String k = iter.next();
			String v = (String)map.get(k);
			if(StringUtils.isNotEmpty(v)){
				sb.append(k + "=" + v + "&");
			}
		}
        sb.append("key="+key);
        return MD5Util.MD5Encode(sb.toString(), charset);
	}
	
	/**
	 * 验证签名
	 * @param params  数据参数
	 * @param key   密钥
	 * @param sign  签名字符串
	 * @return
	 */
	public static boolean checkSign(Map<String,Object> params,String key,String sign,String charset){
		return sign.equalsIgnoreCase(getSign(params,key,charset));
	}
}
