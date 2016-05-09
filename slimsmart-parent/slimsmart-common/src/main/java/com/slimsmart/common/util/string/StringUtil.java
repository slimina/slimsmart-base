package com.shq.common.util.string;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.shq.common.util.collections.MapUtil;

public class StringUtil extends StringUtils{
	
	
	/**
	 * 字符串转换成Map<br/>
	 * name1=key1&name2=key2&...
	 * @param queryString
	 * @return
	 */
	public static Map<String,Object> str2Map(String str, String split, String subSplit) {
		Map<String,Object> m = new HashMap<String,Object>();
		if(null != str && !"".equals(str)) {
			String[] strArray = str.split(split);
			for(int index = 0; index < strArray.length; index++) {
				String[] pair = strArray[index].split(subSplit);
				String v = "";
				if(pair.length > 1) {
					v = pair[1];
				}
				if(!pair[0].equals("")) {
					m.put(pair[0], v);
				}
			}
		}
		return m;
	}
	
	/**
	 * 将map转换为字符串 如:name1=key1&name2=key2&...
	 * @param params
	 * @param split
	 * @return
	 */
	public static String map2Str(Map<String,String> params ,String split){
		StringBuffer strb = new StringBuffer();
		if(MapUtil.isNotEmpty(params)){
			for(Entry<String, String> entry : params.entrySet()){
				strb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
		}
		return strb.length() > 0  ? strb.substring(1) : strb.toString();
	}
	
	
	/**
	 * 对需要出现在HTML属性里的不信任输入进行编码 
	 * @param str
	 * @return
	 * 		< --> &lt;
			> --> &gt;
			' --> &#39;
			" --> &quot;
			& --> &amp;
			= --> &#61;
			` --> &#96;
	 */
	public static String htmlAttributeEncode(String str)
	{
		if(str == null)
		{
			return "";
		}
		// 不用正则表达式替换，直接通过循环，节省cpu时间
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.length(); ++i)
		{
			char c = str.charAt(i);
			switch(c)
			{				
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '\'':
					sb.append("&#39;");
				case '\"':
					sb.append("&quot;");
					break;
				case '&':
					sb.append("&amp;");
					break;
				case '=':
					sb.append("&#61;");
					break;
				case '`':
					sb.append("&#96;");
					break;
				default:
					sb.append(c);
					break;
			}
		}
		return sb.toString();
	}
	
	/**
	 *  把 数组1 与数组2 相加。
	 * @param src 开始数组，
	 * @param dst 结束数组
	 * @return 返回 开始数组+结束数据
	 */
	public static byte[] appendArray(byte[] src, byte[] dst) {
		byte[] newBytes = new byte[src.length + dst.length];
		System.arraycopy(src, 0, newBytes, 0, src.length);
		System.arraycopy(dst, 0, newBytes, src.length, dst.length);
		return newBytes;
	}
	
	/**
	 * 把对象转换成字符串
	 * @param obj
	 * @return String 转换成字符串,若对象为null,则返回空字符串.
	 */
	public static String toString(Object obj) {
		if(obj == null)
			return "";

		return obj.toString();
	}
	
	/**
	 * 根据给定的原字符串，及其分割符，返回分割后的字符串组成的数组。若原字符串为空，则返回null。 若分割符为空，则返回仅有原字符串一个元素的数组。
	 * 
	 * @param srcStr
	 *            要分割的原字符串。
	 * @param splitChar
	 *            分割符。
	 * @return 分割后的字符串组成的数组。
	 */
	public static String[] getTargetStrs(String srcStr, String splitChar) {
		String[] targetStrs = { "" + srcStr };
		if (srcStr == null || srcStr == "") {
			return null;
		} else if (splitChar == null || splitChar == "") {
			return targetStrs;
		} else {
			targetStrs = srcStr.split(splitChar);
			return targetStrs;
		}
	}
	
	/***
	 * 左补位
	 * @param srcStr 字符串源
	 * @param n  规定长度
	 * @param ch  补位字符串
	 * @return
	 */
	public static String fillLeft(String srcStr,int n , char ch){
		int t =  n - srcStr.length();
		if(t <= 0){
			return srcStr.substring(srcStr.length()-n);
		}
		for(;t > 0 ; t--){
			srcStr = ch+srcStr;
		}
		return srcStr;
	}
	
	/***
	 * 右补位
	 * @param srcStr 字符串源
	 * @param n  规定长度
	 * @param ch  补位字符串
	 * @return
	 */
	public static String fillRight(String srcStr,int n , char ch){
		int t =  n - srcStr.length();
		if(srcStr.length()  <= 0){
			return srcStr.substring(0, n);
		}
		for(;t > 0 ; t--){
			srcStr += ch;
		}
		return srcStr;
	}

	/**
	 * 判断给定字符串中是否存在指定的字符。若给定的字符为空，则直接返回false。
	 * 
	 * @param str
	 *            给定的字符串。
	 * @param specChar
	 *            给定的字符。
	 * @return 一个布尔值，为ture时，表示给定字符串中存在指定的字符，否则表示不存在。
	 */
	public static boolean isExistSpecChar(String str, String specChar) {
		if (specChar == null || specChar == "") {
			return false;
		} else {
			return str.contains(specChar);
		}
	}

	/**
	 * 根据给定的字符串，以及特定字符，删除字符串中的特定字符，然后返回一个新串。 注意： 1. 若要删除的特定字符为空，则返回的串为给定的字符串。 2. 若要删除的特定字符在给定的字符串中不存在，则返回的串为给定的字符串。
	 * 
	 * @param specChar
	 *            要删除特定字符的原字符串。
	 * @param specChar
	 *            要删除的特定字符。
	 * @return 删除了特定字符的目标字符串。
	 */
	public static String removeSpecChar(String srcStr, String specChar) {
		if (specChar == null || specChar == "") {
			return srcStr;
		} else {
			if (srcStr.contains(specChar)) {
				return srcStr.replace(specChar, "");
			} else {
				return srcStr;
			}
		}
	}

	/**
	 * 根据给定的字符串数组，然后返回一个以","分割的新串。
	 * 
	 * @param srcStr
	 *            要拼接的字符串数组。
	 * @return 拼接后的字符串。
	 */
	public static String arrayToStringForSql(String[] srcStr) {
		StringBuffer sb = new StringBuffer();
		if (srcStr == null || srcStr.length < 1) {
			return null;
		} else {
			for (String val : srcStr) {
				if (val == null || "".equals(val)) {
					continue;
				}
				sb.append("'").append(val).append("'");
				sb.append(",");
			}
			int len = sb.length() - 1;
			if (',' == sb.charAt(len)) {
				sb.deleteCharAt(len);
			}
			return sb.toString();
		}
	}
	
	public static boolean isSame(String str1,String str2){
		if(str1 == null && str2 == null){
			return true;
		}
		
		if(str1 != null && str1.equals(str2)){
			return true;
		}
		if(str2 != null && str2.equals(str1)){
			return true;
		}
		return false;
		
	}

	/**
	 * 首字母转小写
	 * 
	 * @param str
	 * @return
	 */
	public static String lowercaseFirstLetter(String str) {
		char c = str.charAt(0);
		String temp = new String(c + "");
		return str.replaceFirst(temp, org.apache.commons.lang.StringUtils.lowerCase(temp));
	}
	/**
	 * 
	 * 判断字符是否是空
	 * @param str
	 * @return
	 */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    
    /**
     * 
     * 将字符串数组转换为List集合
     * @param arr 字符串数据
     * @return
     */
    public static List<String> strArrayToList(String[] arr){
    	if(arr == null || arr.length ==0){
    		return null;
    	}
    	List<String> strList = new ArrayList<String>();
    	for(String str : arr){
    		strList.add(str);
    	}
    	return strList;
    }
    
    /**
     * 
     * 字符串中存在星号（表示多个字符）匹配
     * @param pattern  包含星号的字符串
     * @param str  要匹配的字符串
     * @return
     */
    public static boolean wildcardStarMatch(String pattern, String str) {
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0,patternLength = pattern.length(); patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				// 通配符星号*表示可以匹配任意多个字符
				while (strIndex < strLength) {
					if (wildcardStarMatch(pattern.substring(patternIndex + 1), str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			} else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}
    /**
     * 获取area 的 short name
     * @author masongzhi  2013-11-1
     * @param areaName
     * @param areaLevel 0:省级		1:市级	other:不操作  
     * @return
     */
    public static String getAreaShortName(String areaName,int areaLevel){
    	if(isEmpty(areaName)){
    		return "";
    	}
    	if(areaLevel==0){
    		if(areaName.indexOf("黑龙江")>-1){
    			return "黑龙江";
    		}else{
    			return areaName.substring(0,2);
    		}
    	}else if(areaLevel==1){
    		if(areaName.indexOf("市")>-1){
    			return areaName.substring(0,areaName.indexOf("市"));
    		}else{
    			return areaName;
    		}
    	}else{
    		return areaName;
    	}
    }
    
	public static Double str2Double(String str,int divisor,int decimal){
		if(isBlank(str)){
			return 0d;
		}
		try {
			double d = Double.valueOf(str);
			BigDecimal dividend = new BigDecimal(d);
			BigDecimal divis = new BigDecimal(divisor);
			return Double.valueOf(dividend.divide(divis,decimal,RoundingMode.HALF_UP).toString());
		} catch (Exception e) {
			return 0d;
		}
	}
	
	public static Double str2DoubleMultiply(String str,int multiply){
		if(isBlank(str)){
			return 0d;
		}
		try {
			double d = Double.valueOf(str);
			BigDecimal num = new BigDecimal(d);
			BigDecimal multiplicand = new BigDecimal(multiply);
			return Double.valueOf(num.multiply(multiplicand).toString());
		} catch (Exception e) {
			return 0d;
		}
	}
	
	public static Date utc2Date(String utc) {
		if(utc==null || utc.trim().length()<1){
			return null;
		}
		try {
			long utcLong = Long.valueOf(utc);
			return new Date(utcLong);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 字符串匹配
	 * @param regEx
	 * @param str
	 * @return
	 */
	public static boolean matchStr(String regEx,String str){
		if(isBlank(str)){
			return false;
		}
		Pattern pat = Pattern.compile(regEx);  
		Matcher matcher = pat.matcher(str);  
		return matcher.matches();
	}
	
	/**
	 * 匹配电话
	 * @param phoneNo
	 * @return
	 */
	public static boolean matchPhoneNo(String phoneNo){
		String regEx="^\\(?\\d{3,4}[-\\)]?\\d{7,8}$";
		return matchStr(regEx,phoneNo);
	}
	
	/**
	 * 匹配移动电话
	 * @param phoneNo
	 * @return
	 */
	public static boolean matchMobilePhone(String mobilePhone){
		String regEx="^0?\\d{11}$";
		return matchStr(regEx,mobilePhone);
	}
	
	/**
	 * 给定字符串，以split分割，去重后，返回以resultSplit分割的字符串
	 * @return
	 */
	public static String delRepetition(String str,String split,String resultSplit){
		if(isBlank(str)){
			return null;
		}
		String[] strs = str.split(split);
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		for(String s : strs){
			if(isNotBlank(s)){
				if(list.indexOf(s)==-1){
					list.add(s);
					sb.append(s+",");
				}
			}
		}
		return sb.toString().replaceAll(",$", "");
	}
	
	public static boolean isCharSequence(String str){
		if(StringUtil.isBlank(str)){
			return false;
		}
		String reg = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	/**
	 * 元音字符
	 * @param str
	 * @return
	 */
	public static boolean isVowelStr(String str){
		if(StringUtil.isBlank(str) || !isCharSequence(str)){
			return false;
		}
		for(int i=0,l=str.length();i<l;i++){
			String code = String.valueOf(str.charAt(i));
			if(code.equalsIgnoreCase("a") || 
				    code.equalsIgnoreCase("e") || 
				    code.equalsIgnoreCase("i") || 
				    code.equalsIgnoreCase("o") || 
				    code.equalsIgnoreCase("u") ||
				    code.equalsIgnoreCase("v")){ 
				   return true;   
			} 
			
		}
		return false;
	}
	
	public static void main(String[] aa){
		System.out.println(isVowelStr("idbc"));
	}
}
