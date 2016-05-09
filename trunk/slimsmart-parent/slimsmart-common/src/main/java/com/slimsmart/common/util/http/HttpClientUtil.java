package com.slimsmart.common.util.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slimsmart.common.util.TaskPool;

/**
 * <P>Description: HttpClient工具类</P>
 * @ClassName: HttpClientUtil
 */
public class HttpClientUtil {

	protected final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	//HttpClient 连接超时、读取数据超时时间设置(单位：毫秒)
	public static final int HTTPCLIENT_CONNECTION_TIMEOUT = 6000;
	public static final int HTTPCLIENT_SO_TIMEOUT = 30000;

	private static String DEFAULT_ENCODE = "UTF-8";
	
	public static final String HTTP_ACCEPT_HTML="text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	public static final String HTTP_ACCEPT_JSON="application/json, text/javascript, */*; q=0.01";
	
	private static String post(String url,Map<String,Object> params,Map<String,Object> heads,HttpCallBack callback,RequestConfig config){
		String message = null;
		CloseableHttpClient httpClient =null;
		CloseableHttpResponse response =null;
		try{
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			if(config==null){
				config = RequestConfig.custom()
						.setSocketTimeout(HTTPCLIENT_SO_TIMEOUT).setConnectTimeout(HTTPCLIENT_CONNECTION_TIMEOUT).build();//设置请求和传输超时时间
			}
			httpPost.setConfig(config);
			if(heads!=null && heads.size()>0){
				for(Entry<String, Object> head : heads.entrySet()){
					httpPost.addHeader(head.getKey(),head.getValue()==null ? "": head.getValue().toString());
				}
			}
			HttpEntity entity = null;
			if(params!=null && params.size()>0){
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(); 
				for(Entry<String, Object> param : params.entrySet()){
					 pairs.add(new BasicNameValuePair(param.getKey(), param.getValue() ==null ? "" : param.getValue().toString()));
				}
				 entity = new UrlEncodedFormEntity(pairs, DEFAULT_ENCODE);
				 httpPost.setEntity(entity);
			}
			
			response = httpClient.execute(httpPost);//执行请求
			if(response.getStatusLine().getStatusCode()==200){
				entity = response.getEntity(); 
				message = EntityUtils.toString(entity,DEFAULT_ENCODE);
				if(callback!=null){
					callback.execute(message);
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return message;
	}
	
	
	private static String post(String url,byte[] postData,Map<String,Object> heads,HttpCallBack callback,RequestConfig config){
		String message = null;
		CloseableHttpClient httpClient =null;
		CloseableHttpResponse response =null;
		try{
			httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			if(config==null){
				config = RequestConfig.custom()
						.setSocketTimeout(HTTPCLIENT_SO_TIMEOUT).setConnectTimeout(HTTPCLIENT_CONNECTION_TIMEOUT).build();//设置请求和传输超时时间
			}
			httpPost.setConfig(config);
			if(heads!=null && heads.size()>0){
				for(Entry<String, Object> head : heads.entrySet()){
					httpPost.addHeader(head.getKey(),head.getValue()==null ? "": head.getValue().toString());
				}
			}
			HttpEntity entity = new ByteArrayEntity(postData);
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);//执行请求
			if(response.getStatusLine().getStatusCode()==200){
				entity = response.getEntity(); 
				message = EntityUtils.toString(entity,DEFAULT_ENCODE);
				if(callback!=null){
					callback.execute(message);
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return message;
	}
	
	private static String buildGetUrl(String url,Map<String,Object> params){
		if(params!=null && params.size()>0){
			StringBuilder paramStr = new StringBuilder();
			for (Entry<String, Object> entry : params.entrySet()) {
				paramStr.append("&").append(entry.getKey()).append("=").append(entry.getValue()==null ? "" : entry.getValue().toString());
			}
			if(!url.contains("?")){
				url += "?";
			}
			url +=paramStr.substring(1);
		}
		return url;
	}
	/**
	 * get 请求
	 * @param url
	 * @param params
	 * @param heads
	 * @param callback
	 * @return
	 * @throws InterruptedException 
	 */
	private static String get(String url,Map<String,Object> params,Map<String,Object> heads,HttpCallBack callback,RequestConfig config) {
		String message = null;
		CloseableHttpClient httpClient =null;
		CloseableHttpResponse response =null;
		try{
			httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(buildGetUrl(url,params));
			if(config==null){
				config = RequestConfig.custom()
						.setSocketTimeout(HTTPCLIENT_SO_TIMEOUT).setConnectTimeout(HTTPCLIENT_CONNECTION_TIMEOUT).build();//设置请求和传输超时时间
			}
			httpGet.setConfig(config);
			if(heads!=null && heads.size()>0){
				for(Entry<String, Object> head : heads.entrySet()){
					httpGet.addHeader(head.getKey(),head.getValue()==null ? "" : head.getValue().toString());
				}
			}
			HttpEntity entity = null;
			response = httpClient.execute(httpGet);//执行请求
			if(response.getStatusLine().getStatusCode()==200){
				entity = response.getEntity(); 
				message = EntityUtils.toString(entity,DEFAULT_ENCODE);
				if(callback!=null){
					callback.execute(message);
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage());
		}finally{
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
			if(httpClient != null){
				try {
					httpClient.close();
				} catch (IOException e) {
					logger.error(e.toString());
				}
			}
		}
		return message;
	}
	

	/**
	 * 
	 * http post提交
	 * @param: url  访问url
	 * @param: text  提交的的文本 xml json
	 * @return: String   返回数据
	 */
	public static String postText(String url,String text){
		try {
			return post(url,text.getBytes(DEFAULT_ENCODE),null,null,null);
		} catch (UnsupportedEncodingException e) {
			logger.error(" post xml error : {}",e);
		}
		return "";
	}
	
	/**
	 * 
	 * http post提交
	 * @param: url  访问url
	 * @param: params  提交的参数
	 * @return: String   返回数据
	 */
	public static String post(String url,Map<String,Object> params,RequestConfig config){
		return post(url,params,null,null,config);
	}
	public static String post(String url,Map<String,Object> params){
		return post(url,params,null,null,null);
	}
	
	/**
	 * 
	 * http post提交
	 * @param: url  访问url
	 * @param: params  提交的参数
	 * @param: heads   提交head头
	 * @return: String   返回数据
	 */
	
	public static String post(String url,Map<String,Object> params,Map<String,Object> heads,RequestConfig config){
		return post(url,params,heads,null,config);
	}
	public static String post(String url,Map<String,Object> params,Map<String,Object> heads){
		return post(url,params,heads,null,null);
	}
	
	/**
	 * http post异步提交
	 * @param:  url 访问url
	 * @param:  params 提交参数
	 * @param:  callback   异步提交回调
	 */
	public static void postAsync(final String url,final Map<String,Object> params,final HttpCallBack callback){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				post(url,params,null,callback,null);
			}
		};
		TaskPool.submitTask(task);
	}
	
	public static void postAsync(final String url,final Map<String,Object> params,final HttpCallBack callback,final RequestConfig config){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				post(url,params,null,callback,config);
			}
		};
		TaskPool.submitTask(task);
	}
	
	/**
	 * http post异步提交
	 * @param:  url 访问url
	 * @param:  params 提交参数
	 * @param: heads   提交head头
	 * @param:  callback   异步提交回调
	 */
	public static void postAsync(final String url,final Map<String,Object> params, final Map<String,Object> heads,final HttpCallBack callback){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				post(url,params,heads,callback,null);
			}
		};
		TaskPool.submitTask(task);
	}
	public static void postAsync(final String url,final Map<String,Object> params, final Map<String,Object> heads,final HttpCallBack callback,final RequestConfig config){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				post(url,params,heads,callback,config);
			}
		};
		TaskPool.submitTask(task);
	}
	
	/**
	 * 
	 * http get提交
	 * @param: url  访问url
	 * @param: params  提交的参数
	 * @return: String   返回数据
	 */
	public static String get(String url,Map<String,Object> params,RequestConfig config){
		return get(url,params,null,null,config);
	}
	public static String get(String url,Map<String,Object> params){
		return get(url,params,null,null,null);
	}
	
	/**
	 * 
	 * http get提交
	 * @param: url  访问url
	 * @param: params  提交的参数
	 * @param: heads   提交head头
	 * @return: String   返回数据
	 */
	
	public static String get(String url,Map<String,Object> params,Map<String,Object> heads,RequestConfig config){
		return get(url,params,heads,null,config);
	}
	public static String get(String url,Map<String,Object> params,Map<String,Object> heads){
		return get(url,params,heads,null,null);
	}
	
	/**
	 * http get异步提交
	 * @param:  url 访问url
	 * @param:  params 提交参数
	 * @param:  callback   异步提交回调
	 */
	public static void getAsync(final String url,final Map<String,Object> params,final HttpCallBack callback,final RequestConfig config){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				get(url,params,null,callback,config);
			}
		};
		TaskPool.submitTask(task);
	}
	public static void getAsync(final String url,final Map<String,Object> params,final HttpCallBack callback){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				get(url,params,null,callback,null);
			}
		};
		TaskPool.submitTask(task);
	}
	
	/**
	 * http get异步提交
	 * @param:  url 访问url
	 * @param:  params 提交参数
	 * @param: heads   提交head头
	 * @param:  callback   异步提交回调
	 */
	public static void getAsync(final String url,final Map<String,Object> params, final Map<String,Object> heads,final HttpCallBack callback){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				get(url,params,heads,callback,null);
			}
		};
		TaskPool.submitTask(task);
	}
	
	public static void getAsync(final String url,final Map<String,Object> params, final Map<String,Object> heads,final HttpCallBack callback,final RequestConfig config){
		Runnable task = new Runnable() {
			@Override
			public void run() {
				get(url,params,heads,callback,config);
			}
		};
		TaskPool.submitTask(task);
	}
}