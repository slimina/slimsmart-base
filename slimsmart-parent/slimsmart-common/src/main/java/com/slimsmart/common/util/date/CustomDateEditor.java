package com.slimsmart.common.util.date;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class CustomDateEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 dateFormat.setLenient(false);
		 SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 datetimeFormat.setLenient(false);
		 SimpleDateFormat datetimeFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		 datetimeFormat.setLenient(false);
		 Date date = null;
		 if(StringUtils.isNotBlank(text)){
			 text = text.trim();
			 if(text.length() == 10){
				 try {
					date = dateFormat.parse(text);
				} catch (ParseException e) {
				}
			 }else if(text.length() == 16){
				 try {
					date = datetimeFormat1.parse(text);
				} catch (ParseException e) {
				}
			 }else if(text.length() == 19){
				 try {
					date = datetimeFormat.parse(text);
				} catch (ParseException e) {
				}
			 }else{
				 throw new IllegalArgumentException("日期格式有误");
			 }
		 }
		setValue(date);
	}
}
