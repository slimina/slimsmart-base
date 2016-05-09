package com.shq.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shq.common.util.collections.MapUtil;
import com.shq.common.util.http.HttpClientUtil;
import com.shq.common.util.string.StringUtil;

/**
 * 微信工具类
 * @author slimina
 *
 */
public class WebchatUtil {
	
	private static Logger logger = LoggerFactory.getLogger(WebchatUtil.class);
	
	//用户基本信息
	private static final String USER_INFO_URL="https://api.weixin.qq.com/cgi-bin/user/info";
	//批量获取用户信息
	private static final String BATCHGET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget";
	//获取access_token
	private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	//获取用户订阅列表
	private static final String USER_SUBSCRIBE_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/get";
	//发送模板消息
	private static final String TPL_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";
	
	//获取jsapi票据
	private static final String JSAPI_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	
	
	/**
	 * 获取accessToken
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static String getAccessToken(String appid,String secret){
		StringBuffer sendUrl = new StringBuffer();
		sendUrl.append(TOKEN_URL);
		sendUrl.append("?appid=").append(appid);
		sendUrl.append("&secret=").append(secret);
		sendUrl.append("&grant_type=client_credential");
		String jsonStr =  HttpClientUtil.get(sendUrl.toString(), null);
		logger.info("jsonStr :" + jsonStr);
		if(StringUtil.isNotBlank(jsonStr)){
			Map<String,Object> resultMap = JsonUtil.json2Map(jsonStr);
			return (String)resultMap.get("access_token");
		}else{
			return null;
		}
	}
	
	/**
	 * 获取jsapi票据
	 * @param accessToken
	 * @return
	 */
	public static String getJSApiTicket(String accessToken){
		StringBuffer sendUrl = new StringBuffer();
		sendUrl.append(JSAPI_TICKET_URL);
		sendUrl.append("?access_token=").append(accessToken);
		sendUrl.append("&type=jsapi");
		String jsonStr =  HttpClientUtil.get(sendUrl.toString(), null);
		logger.info("jsonStr :" + jsonStr);
		if(StringUtil.isNotBlank(jsonStr)){
			Map<String,Object> resultMap = JsonUtil.json2Map(jsonStr);
			return (String)resultMap.get("ticket");
		}else{
			return null;
		}
	}
	
	/**
	 * 获取用户信息
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static Map<String,Object> getUserInfo(String accessToken,String openid){
		StringBuffer sendUrl = new StringBuffer();
		sendUrl.append(USER_INFO_URL);
		sendUrl.append("?access_token=").append(accessToken);
		sendUrl.append("&openid=").append(openid);
		sendUrl.append("&lang=zh_CN");
		String jsonStr =  HttpClientUtil.get(sendUrl.toString(), null);
		logger.info("jsonStr :" + jsonStr);
		if(StringUtil.isNotBlank(jsonStr)){
			Map<String,Object> resultMap = JsonUtil.json2Map(jsonStr);
			return resultMap.containsKey("errcode") ? null : resultMap;
		}else{
			return null;
		}
	}
	
	/**
	 * 批量获取微信用户信息
	 * @param accessToken
	 * @param openidList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getUserInfoList(String accessToken,List<String> openidList){
		List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
		for(String openid : openidList){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("openid", openid);
			//map.put("language", "zh_CN");
			userList.add(map);
		}
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("user_list", userList);
		String jsonStr =  HttpClientUtil.postText(BATCHGET_USER_INFO_URL+"?access_token="+accessToken, JsonUtil.objct2Json(params));
		logger.info("jsonStr :" + jsonStr);
		if(StringUtil.isNotBlank(jsonStr)){
			Map<String,Object> resultMap = JsonUtil.json2Map(jsonStr);
			return resultMap.containsKey("errcode") ? null : (List<Map<String,Object>>)resultMap.get("user_info_list");
		}else{
			return null;
		}
	}
	
	/**
	 * 获取用户订阅列表
	 * @param accessToken
	 * @param openidList
	 * @return
	 */
	public static Map<String,Object> getUserSubscribeInfoList(String accessToken,String nextOpenid){
		StringBuffer url = new StringBuffer(USER_SUBSCRIBE_INFO_URL);
		url.append("?access_token="+accessToken);
		if(StringUtil.isNotEmpty(nextOpenid)){
			url.append("&next_openid="+(nextOpenid==null ? "":nextOpenid));
		}
		System.out.println(url.toString());
		String jsonStr =  HttpClientUtil.get(url.toString(),null);
		logger.info("jsonStr :" + jsonStr);
		if(StringUtil.isNotBlank(jsonStr)){
			return JsonUtil.json2Map(jsonStr);
		}else{
			return null;
		}
	}
	
	/**
	 * 发送模板消息
	 * @param accessToken
	 * @param json
	 * @return
	 */
	public static Map<String,Object> sendTemplateMessage(String accessToken,String json){
		String result = HttpClientUtil.postText(TPL_MESSAGE_URL+"?access_token="+accessToken, json);
		logger.debug("sendTemplateMessage result:"+result);
		if(StringUtil.isNotBlank(result)){
			return JsonUtil.json2Map(result);
		}else{
			return null;
		}
	}
	
	public static String sendTemplateMessage(String accessToken,String touser,String templateId,String url,Map<String,Object> data){
		Map<String,Object> sendData = new HashMap<String, Object>();
		sendData.put("touser", touser);
		sendData.put("template_id", templateId);
		sendData.put("url", url);
		sendData.put("data", data);
		Map<String,Object> result =  sendTemplateMessage(accessToken, JsonUtil.objct2Json(sendData));
		if(MapUtil.isEmpty(result)){
			return "发送失败";
		}
		if("0".equals(result.get("errcode"))){
			return null;
		}else{
			return (String)result.get("errmsg");
		}
	}
	
	public static Map<String,Object> toTemplateParam(String value,String color){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("value", value);
		params.put("color", color);
		return params;
	}
	
	public static void main(String[] aa){
		String accessToken= getAccessToken("wxe3b3b9fe0ebde756", "13843d6ff5113b2028620446e9ae04f2");
		//String accessToken="HB16nZ_uc7-0ryxjc-pDJeeWPDErm5a2t6Z-wiJXJEXqqkHYaGbJUVrZK9dHjSC9yK9nq03zh-3Z5NPXsKe6vQYjLQlRxvMwXFc09LZo6xo8TAEkrnWowx7Z5T7p3JOjAMHgAIAMYT";
		System.out.println(accessToken);
		System.out.println(getUserInfo(accessToken, "o4bHbvjL9aWoRCa29vdOQ9aJMq0w"));
		System.out.println(getJSApiTicket(accessToken));
		List<String> openidList = new ArrayList<String>();
		openidList.add("o4bHbvtwWAdhXamkbs5uxVu16lP0");
		openidList.add("o4bHbvnmGLUYtwTmR3fXHeJCHVB0");
		System.out.println(getUserInfoList(accessToken, openidList));
		System.out.println(getUserSubscribeInfoList(accessToken,"o4bHbvtwWAdhXamkbs5uxVu16lP0"));
		Map<String,Object> sendData = new HashMap<String, Object>();
		sendData.put("first", toTemplateParam("您好，四声母域名价格已达到您所设置的上涨价格。", "#173177"));
		sendData.put("keyword1", toTemplateParam("2016-03-01 16:30:00", "#000000"));
		sendData.put("keyword2",toTemplateParam("最低价：16000，涨幅：3.00%。", "#FF0000"));
		sendData.put("keyword3", toTemplateParam("以上价格变动仅供参考。", "#000000"));
		sendData.put("remark", toTemplateParam("\n如有疑问和建议，请发送邮件到kf@yumai.com。", "#000000"));
		String msg = sendTemplateMessage(accessToken, "o4bHbvjL9aWoRCa29vdOQ9aJMq0w", "z2byXn9mkwLab95vEGpIMah6CNKxg4CapTCLa7sdxP4",
				"http://wx.yumai.com", sendData);
		System.out.println(msg);
	}
}
