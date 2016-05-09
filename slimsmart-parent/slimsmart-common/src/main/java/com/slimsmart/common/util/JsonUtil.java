package com.shq.common.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings({ "unchecked"})
public class JsonUtil {

	protected final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 
	 * JSON,使用Jackson转换Java对象.
	 * 
	 */
	public static <X> X renderObject(String json,Class<X> clazz){
		try {
			return (X)objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
		return null;
	}
	
	public static <X> X renderMap(Map<String,String> map,Class<X> clazz){
		try {
			return (X)objectMapper.readValue(objectMapper.writeValueAsString(map), clazz);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
		return null;
	}
	
	public static Map<String, Object> json2Map(String jsonStr){
		try {
			return objectMapper.readValue(jsonStr, Map.class);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
		return null;
	}
	
	public static String objct2Json(Object object){
		if(object==null){
			return "";
		}
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		} 
		return "";
	}
	public static void main(String[] aa){
		System.out.println(json2Map("{\"age\":22,\"name\":\"jack\"}"));	
	}

}
