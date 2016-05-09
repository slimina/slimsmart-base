package com.shq.common.util;

import com.shq.common.annotation.Module;


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
