package com.slimsmart.common.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

public class ResourcesUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourcesUtil.class);
	
	public static ResourceBundle getResourceBundle(String baseName) {
		return ResourceBundle.getBundle(baseName);
	}
	public static String getString(String prop) {
		ResourceBundle rb =getResourceBundle("application");
		String result = rb.getString(prop);
		logger.debug("application.properties : " + prop + " ==> " + result);
		return result;
	}
	
	public static Integer getInt(String prop) {
		return Integer.valueOf(getString(prop));
	}
	
	public static Long getLong(String prop) {
		return Long.valueOf(getString(prop));
	}

	public static File getFile(String path) throws FileNotFoundException {
		return ResourceUtils.getFile("classpath:" + path);
	}
}
