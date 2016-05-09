package com.slimsmart.common.util.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil extends DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat DateFormatWithoutTime = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat longDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	
	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if( null == date ) {
			return 0;
		}
		return date.getTime()/1000;
	}
	
	/**
	 * 获取当前时间 yyyyMMddHHmmss
	 * @return String
	 */ 
	public static String getCurrTime() {
		Date now = new Date();
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = outFormat.format(now);
		return s;
	}
	
	/**
	 * 将日期类型转化为yyyy-MM-dd　HH:mm:ss格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatToSimpleDate(Date date) {
		return simpleDateFormat.format(date);
	}

	/**
	 * 将日期类型转化为yyyy-MM-dd格式字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToString(Date date) {
		return DateFormatWithoutTime.format(date);
	}
	
	public static String formatDateTolongDateString(Date date) {
		return longDateFormat.format(date);
	}
	/**
	 * 将日期类型转化为指定格式字符串
	 * 
	 * @param date
	 *            日期
	 * @return 字符串
	 */
	public static String formatDateToString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	/**
	 * <p>Title: formatStringToSimpleDate</p>
	 * <p>Description: yyyy-MM-dd　HH:mm:ss</p>
	 * @param date
	 * @return
	 * @author shuai.wang  2013-4-22 上午11:11:21
	 */
	public static Date formatStringToSimpleDate(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("e:{}", e);
		}
		return new Date();
	}
	
	public static Date formatStringToSimpleDate(String date,String pattern) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			return simpleDateFormat.parse(date);
		} catch (ParseException e) {
			logger.error("e:{}", e);
		}
		return new Date();
	}

	/**
	 * 将yyyy-MM-dd格式字符串，转成Date对象
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date formatStringToDate(String date) {
		try {
			return DateFormatWithoutTime.parse(date);
		} catch (ParseException e) {
			logger.error("e:{}", e);
		}
		return new Date();
	}

	/**
	 * 根据pattern,将String转换成Timestamp
	 * 
	 * @param pattern
	 *            yyyy-MM-dd HH:mm:ss
	 * @param dateString
	 * @return Timestamp
	 */
	public static Timestamp convertStringToTimestamp(String pattern, String dateString) {
		Timestamp newTimestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			newTimestamp = new Timestamp(sdf.parse(dateString).getTime());
		} catch (ParseException e) {
			logger.error("{}", e);
		}
		return newTimestamp;
	}

	/**
	 * 根据pattern,将String转换成Date
	 * 
	 * @param pattern
	 *            yyyy-MM-dd
	 * @param date
	 * @return Date
	 */
	public static Date convertStringToDate(String pattern, Date date) {
		Date newDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			newDate = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			logger.error("{}", e);
		}
		return newDate;

	}

	/**
	 * 当前时间分钟的偏移
	 * 
	 * @param minute
	 * @return 偏移的时间
	 */
	public static Date getMinuteOffset(int minute) {
		return addMinutes(new Date(), minute);
	}

	/**
	 * 当前时间日期的偏移
	 * 
	 * @param offset
	 *            偏移
	 * @return 偏移的时间
	 */
	public static Date getDateOffset(int offset) {
		return addDays(new Date(), offset);
	}

	/**
	 * 获取今天，不带时间
	 * 
	 * @return 获取今天，不带时间
	 */
	public static Date getToday() {
		Date date = new Date();
		return truncate(date, Calendar.DATE);
	}

	/**
	 * 获得昨天，不带时间
	 * 
	 * @return 获取昨天，不带时间
	 */
	public static Date getYesterday() {
		Date date = new Date();

		Date ret = truncate(date, Calendar.DATE);
		ret = addDays(ret, -1);
		return ret;
	}

	/**
	 * 获得明天，不带时间
	 * 
	 * @return 获得明天，不带时间
	 */
	public static Date getTomorrow() {
		Date date = new Date();
		Date ret = truncate(date, Calendar.DATE);
		ret = addDays(ret, 1);
		return ret;
	}

	/**
	 * 获得明天，不带时间
	 * 
	 * @return 获得明天，不带时间
	 */
	public static Date getDateBySecond(long sec) {
		Date date = new Date();
		sec *= 1000;
		date.setTime(sec);
		return date;
	}
	
	/**
	 * <p>Title: getSubtractYear</p>
	 * <p>Description: 两个时间相差的年</p>
	 * @param startDate
	 * @param endDate
	 * @return 两个时间相差的年
	 * @author songzhi.Ma  2014-4-21 下午2:33:25
	 */
	public static int getSubtractYear(Date startDate,Date endDate){
		if(startDate==null || endDate==null){
			return 0;
		}
		
		try {
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			return start.get(Calendar.YEAR)-end.get(Calendar.YEAR);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return 0;
	}
	
	/**
	 * 两个时间的间隔秒数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getSecondsBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return 0L;
		}
		return  (endDate.getTime() - startDate.getTime())/1000;
	}
	
	public static Long getMillisecondsBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return 0L;
		}
		return  endDate.getTime() - startDate.getTime();
	}
	
	/**
	 * 两个时间的间隔分钟数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getMinutesBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return 0L;
		}
		return  (endDate.getTime() - startDate.getTime())/(1000*60);
	}
	
	
	/**
	 * 两个时间的间隔小时数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getHoursBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return 0L;
		}
		return  (endDate.getTime() - startDate.getTime())/(1000*60*60);
	}
	
	/**
	 * 两个时间的间隔天数
	 * @param startDate 小时间
	 * @param endDate   大时间
	 * @return
	 */
	public static Long getDayBetweenDates(Date startDate,Date endDate){
		if(startDate == null || endDate == null ){
			return 0L;
		}
		return  (endDate.getTime() - startDate.getTime())/(1000*60*60*24);
	}
	
	/**
	 * 通过小时计算天数
	 * @param hours
	 * @return
	 */
	public static Double getHoursToDay(long hours){
		double day1,day2;
		if(hours <= 0){
			return 0.0d;
		}
		day1 = hours / 24; 
		day2 = hours % 24;
		if(day2>=0 && day2<6){
			day2 = 0;
		}
		else if(day2>=6 && day2<18){
			day2 = 0.5;
		}
		else if(day2>=18 && day2<24){
			day2 = 1;
		}
		return  day1 + day2;
	}
	
	
	/**
	 * 通过分钟计算小时
	 * @param hours
	 * @return
	 */
	public static Long getMinutesToHours(long minutes){
		long minutes1,minutes2;
		if(minutes <= 0){
			return 0L;
		}
		minutes1 = minutes / 60;
		minutes2 = minutes % 60;
		if(minutes2>=30){
			minutes2 = 1;
		}
		else{
			minutes2 = 0;
		}
		return  minutes1 + minutes2;
	}
	
	public static String getFmtDateStrToString(String srcDate, String fmtpattn){
		String resString="";
		if (srcDate != null) {
			try {
				Date tmpDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", new Locale("ENGLISH", "CHINA")).parse(srcDate);
				SimpleDateFormat sDateFormat = new SimpleDateFormat(fmtpattn);
				resString = sDateFormat.format(tmpDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resString;
	}
	public static void main(String[] aa){
		//System.out.println(DateUtil.formatDateToString(new Date(),"HH:mm").substring(0,4)+"0");
		
		Date startDate = DateUtil.formatStringToSimpleDate("2016-01-19 15:20:00");
		Date endDate = DateUtil.formatStringToSimpleDate("2016-02-19 18:10:00");
		int i=0;
		for(;startDate.before(endDate);){
			i +=264;
			startDate = addMinutes(startDate, 10);
		}
		System.out.println(i);
	}
}
