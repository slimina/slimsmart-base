package com.shq.common.util.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

import com.shq.common.util.string.StringUtil;

public class NumberUtil extends NumberUtils{
	
	/**
	 * 把对象转换为int数值.
	 * 
	 * @param obj
	 *            包含数字的对象.
	 * @return int 转换后的数值,对不能转换的对象返回0。
	 */
	public static int toInt(Object obj) {
		int a = 0;
		try {
			if (obj != null)
				a = Integer.parseInt(obj.toString());
		} catch (Exception e) {
		}
		return a;
	}

	/**
	 * 截取2位小数精度
	 * 
	 * @param t
	 *            数值
	 * @return 数值
	 */
	public static double round(double t) {
		BigDecimal bigDecimal = BigDecimal.valueOf(t).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}
	
	/**
	 * 截取length位小数精度
	 * 
	 * @param t
	 *            数值
	 * @return 数值
	 */
	public static double round(double t,int length) {
		BigDecimal bigDecimal = BigDecimal.valueOf(t).setScale(length, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

	/**
	 * Double类型转换为String类型
	 * 
	 * @param arg0
	 *            double数据
	 * @param precision
	 *            精度 例如:1(保留小数点后一位) , 2(保留小数点后两位)..
	 * @return
	 */
	public static String doubleConvertToString(double srcDouble, int precision) {
		BigDecimal bigDecimal = BigDecimal.valueOf(srcDouble).setScale(precision, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.toString();
	}
	
	/**
	 * Double保留小数 srcDouble: 25363.100000  format:0.####### ==>25363.1
	 * @param srcDouble
	 * @param format
	 * @return
	 */
	public static String formatDouble(double srcDouble,String format) {
		DecimalFormat textFormat = new DecimalFormat(format);
		return textFormat.format(srcDouble);
	}
	
	public static boolean isSame(double d1,Double d2){
		if(d2 == null && d1 == 0){
			return true;
		}
		
		if(d2 != null && d1 == d2){
			return true;
		}
		return false;
	}
	
	public static int strToInt(String str){
		if(StringUtil.isBlank(str)){
			return 0;
		}
		try {
			BigDecimal b = new BigDecimal(str);
			return b.intValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static long strToLong(String str){
		if(StringUtil.isBlank(str)){
			return 0;
		}
		try {
			BigDecimal b = new BigDecimal(str);
			return b.longValue();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static double strToDouble(String str){
		if(StringUtil.isBlank(str)){
			return 0.0;
		}
		try {
			BigDecimal b = new BigDecimal(str);
			return b.doubleValue();
		} catch (Exception e) {
			return 0.0;
		}
	}
	/**
	 * 判断str是正整数,如果小数位都为0 则也是
	 * @param str
	 * @return
	 */
	public static boolean isPositiveInteger(String str){
		if(StringUtil.isBlank(str)){
			return false;
		}
		//正整数,或者小数位是0
		String reg = "^[1-9]\\d*|[1-9]\\d*\\.0+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 判断str上个合法的正数
	 * @param str
	 * @return
	 */
	public static boolean isPositiveNumber(String str){
		if(StringUtil.isBlank(str)){
			return false;
		}
		String reg = "^[1-9]\\d*|[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	/**
	 * str是数字小数点倍数小于等于 decimal位
	 * @param str
	 * @param decimal 要求小数位数
	 * @return
	 */
	public static boolean isPositiveNumber(String str,int decimal){
		boolean result = isPositiveNumber(str);
		if(result && str.contains(".")){
			String[] s = str.split("\\.");
			return s[1].length()<=3;
		}
		return result;
	}

	public static boolean isNumberStr(String str){
		if(StringUtil.isBlank(str)){
			return false;
		}
		String reg = "^\\d+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public static boolean isContains04(String str){
		if(StringUtil.isBlank(str) || !isNumberStr(str)){
			return false;
		}
		for(int i=0,l=str.length();i<l;i++){
			String code = String.valueOf(str.charAt(i));
			if(code.equals("0") || 
				    code.equals("4")){ 
				   return true;   
			} 
			
		}
		return false;
	}
	
	public static void main(String[] aa){
		System.out.println(isNumberStr("0991220"));
	}
}
