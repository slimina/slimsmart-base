package com.slimsmart.common.web;


import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import com.slimsmart.common.util.date.CustomDateEditor;

/**
 * 数据绑定将页面传递的日期字符串，转换为Date
 * @author slimina
 *
 */
public class FormDateDataBinding implements WebBindingInitializer {

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		 binder.registerCustomEditor(Date.class,new CustomDateEditor());
	}
}
