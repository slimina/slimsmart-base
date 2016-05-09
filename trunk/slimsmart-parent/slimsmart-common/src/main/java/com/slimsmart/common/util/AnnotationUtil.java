package com.slimsmart.common.util;

import com.slimsmart.common.annotation.Module;


public class AnnotationUtil {
	
	public static String getAnnotationModelName(Class<?> clazz){
		String retval = clazz.getSimpleName();
		if(clazz.isAnnotationPresent(Module. class)){
			Module modelName = clazz.getAnnotation(Module.class);
			retval = modelName.value();
		}
		return retval;
	}
}
