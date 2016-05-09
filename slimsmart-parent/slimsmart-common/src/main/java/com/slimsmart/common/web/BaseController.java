package com.slimsmart.common.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import com.slimsmart.common.model.BaseEntity;
import com.slimsmart.common.util.ReflectionUtil;
import com.slimsmart.common.util.string.StringUtil;

/**
 * controller控制层基类
 */
public abstract class BaseController<T extends BaseEntity> {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";

	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String HEADER_ENCODING = "encoding";
	private static final boolean DEFAULT_NOCACHE = true;

	protected Map<String, Object> filterMap = null;

	private String serviceName;
	private String entityClassName;

	public BaseController() {
		Class<T> entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
		logger.debug("entityClass:{}", entityClass);
		entityClassName = entityClass.getSimpleName();
		char c = entityClassName.charAt(0);
		entityClassName =  StringUtil.lowerCase(c+"")+entityClassName.substring(1);
		serviceName = entityClassName + "Service";
		logger.debug("getBaseService:{}", serviceName);
		logger.debug("getNameSpace:{}", getNameSpace());
	}
	
	protected abstract String getNameSpace();
	
	@RequestMapping("index")
	public String index(){
		return getNameSpace()+entityClassName+"-list";
	}
	
	public void renderExcel(HttpServletResponse response, final byte[] bytes, final String filename) {
		initResponseHeader(response, EXCEL_TYPE);
		setFileDownloadHeader(response, filename);
		if (null != bytes) {
			try {
				response.getOutputStream().write(bytes);
				response.getOutputStream().flush();
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}
	
	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public void setFileDownloadHeader(HttpServletResponse response, String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + ".xls\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 分析并设置contentType与headers.
	 */
	private HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType, final String... headers) {
		// 分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");
			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}
		}
		// 设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			// Http 1.0 header
			response.setDateHeader("Expires", 0);
			response.addHeader("Pragma", "no-cache");
			// Http 1.1 header
			response.setHeader("Cache-Control", "no-cache");
		}
		return response;
	}
}
