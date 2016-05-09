package com.slimsmart.common.util.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slimsmart.common.util.string.StringUtil;

public class HttpUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public static final String DEFAULT_ENCODING = "UTF-8";
	
	/**
	 * 获取编码字符集
	 * @param request
	 * @param response
	 * @return String
	 */
	public static String getCharacterEncoding(HttpServletRequest request,
			HttpServletResponse response) {

		if(null == request || null == response) {
			return DEFAULT_ENCODING;
		}

		String enc = request.getCharacterEncoding();
		if(null == enc || "".equals(enc)) {
			enc = response.getCharacterEncoding();
		}

		if(null == enc || "".equals(enc)) {
			enc = DEFAULT_ENCODING;
		}
		return enc;
	}

	/**
	 * 判断是否为Ajax请求
	 * 
	 * @return 是true, 否false
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) 
				|| request.getHeader("accept").indexOf("application/json")!=-1;
        String ajaxFlag = null == request.getParameter("ajax") ?  "false": request.getParameter("ajax") ;
        return ajax || ajaxFlag.equalsIgnoreCase("true");
	}
	
	/**
	 * 从request中获得参数Map，并返回可读的Map
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String,String> getParameterMap(HttpServletRequest request,String chatset) {
	    // 参数Map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map<String,String> returnMap = new HashMap<String,String>();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	        	value = valueObj.toString();
	        }
	        if(StringUtil.isNotBlank(chatset)){
	        	try {
				value = new String(value.getBytes("ISO-8859-1"), chatset);
				} catch (UnsupportedEncodingException e) {
					logger.error("error : {}",e);
				}
	        }
	        returnMap.put(name, value);
	    }
	    logger.debug("alipay return data : " + returnMap );
	    return returnMap;
	}
	
	/**
	 * 获取客户端IP
	 * 
	 * @return
	 */
	public static String getRequestIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
