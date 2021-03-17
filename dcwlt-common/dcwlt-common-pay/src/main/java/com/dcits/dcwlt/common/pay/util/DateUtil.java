/*********************************************
 * Copyright (c) 2020 LI-ECS.
 * All rights reserved.
 * Created on 2020年1月6日
 *
 * Contributors:
 *     ECS - initial implementation
 *********************************************/

package com.dcits.dcwlt.common.pay.util;


import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理工具类
 *
 *
 */
public class DateUtil {

	public static final FastDateFormat STANDARD_DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss");
	public static final FastDateFormat DEFAULT_MILLITIME_FORMATTER = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");
	public static final FastDateFormat DEFAULT_SECONDS_FORMATTER = FastDateFormat.getInstance("yyyyMMddHHmmss");
	public static final FastDateFormat DEFAULT_DATE_FORMATTER = FastDateFormat.getInstance("yyyyMMdd");
	public static final FastDateFormat DEFAULT_TIME_FORMATTER = FastDateFormat.getInstance("HHmmss");
	public static final FastDateFormat STANDARD_DATE_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");
	private static final String DATE_FORMAT = "yyyyMMdd";


	private static final ThreadLocal<SimpleDateFormat> sdfThreadLocal = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue() {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf;
		}
	};

	private static final String DEFAULT_DATE_PATTERN = "yyyyMMDD";

	private static final DateTimeFormatter DEFAULT_DTF = DateTimeFormatter.BASIC_ISO_DATE;

	private static final Map<String,DateTimeFormatter> DTF_MAP = new HashMap<>(8);

	static {
		DTF_MAP.put(DEFAULT_DATE_PATTERN,DEFAULT_DTF);
	}

	
	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}

	
	/**
	 * @Description:获取ISODateTime
	 * @return
	 */
	public static String getISODateTime() {
		return STANDARD_DATETIME_FORMATTER.format(new Date());
	}
	

	/**
	 * @Description: 获取日期时间精确到秒
	 *  格式：yyyyMMddHHmmss
	 * @return
	 */
	public static String formatSeconds() {
		return DEFAULT_SECONDS_FORMATTER.format(new Date());
	}

	/**
	 * @Description:获取yyyy-MM-dd'T'HH:mm:ss格式
	 * @param dateTime yyyyMMddHHmmss格式字符串
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String formatStandardDateTime(String dateTime) throws ParseException {
		return DEFAULT_SECONDS_FORMATTER.format(STANDARD_DATETIME_FORMATTER.parse(dateTime));
	}


	/**
	 * @Description:获取yyyyMMdd格式
	 * @param dateTime yyyy-MM-dd 转换为yyyyMMdd
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String getDateStr(String dateTime) throws ParseException {
		return DEFAULT_DATE_FORMATTER.format(STANDARD_DATE_FORMATTER.parse(dateTime));
	}

	/**
	 * @Description:获取yyyy-MM-dd格式
	 * @param date 为yyyyMMdd 转换 yyyy-MM-dd
	 * @return
	 * @throws java.text.ParseException
	 */
	public static String formatDateStr(String date) throws ParseException {
		return STANDARD_DATE_FORMATTER.format(DEFAULT_DATE_FORMATTER.parse(date));

	}

	/**
	 * @Description: 获取日期时间精确到毫秒
	 *  格式：yyyyMMddHHmmssSSS
	 * @return
	 */
	public static String formatMilliTime() {
		return DEFAULT_MILLITIME_FORMATTER.format(new Date());
	}

	/**
	 * @// TODO: 2020/11/16  use DateTimeFormatter to replace SimpleDateFormat
	 * @param date
	 * @return <tt>true</tt> if this date is valid
	 */
	public static boolean checkDate(String date){
		SimpleDateFormat sdf = sdfThreadLocal.get();
		try{
			//设置为严格模式
			sdf.setLenient(false);
			sdf.parse(date);
		}catch (ParseException e){
			return false;
		}
		return true;
	}

	/**
	 * @Description: 获取日期
	 * 格式yyyyMMdd
	 * @return
	 */
	public static String getDefaultDate(){
		return DEFAULT_DATE_FORMATTER.format(new Date());
	}

	/**
	 * @Description: 获取时间
	 * 格式HHmmss
	 * @return
	 */
	public static String getDefaultTime(){
		return DEFAULT_TIME_FORMATTER.format(new Date());
	}


	/**
	 * @Description: 获取yyyyMMdd格式
	 *  格式：yyyyMMdd
	 * @return
	 */
	public static String getSysDate() {
		return DEFAULT_DATE_FORMATTER.format(new Date());
	}

	/**
	 * @Description: 获取yyyyMMddHHmmss格式
	 *  格式：yyyyMMddHHmmss
	 * @return
	 */
	public static String getSysTime() {
		return DEFAULT_SECONDS_FORMATTER.format(new Date());
	}

	/**
	 * @Description: 获取yyyyMMddHHmmss格式
	 *  格式：yyyyMMddHHmmss
	 * @return
	 */
	public static String getSystemTime() {
		return System.currentTimeMillis()+"";
	}

	/**
	 * 获取当前月份
	 * @return current month
	 */
	public static String getCurMonth(){
		return String.valueOf(LocalDate.now().getMonthValue());
	}

	/**
	 * 获取当前日
	 * @return current day of month
	 */
	public static String getCurDay(){
		return String.valueOf(LocalDate.now().getDayOfMonth());
	}

	/**
	 * 获取当前时间
	 * @return pattern of hh:mm:ss
	 */
	public static String getCurTime(){
		String curTime = getISODateTime();
		return curTime.substring(curTime.length() - 8);
	}

	// TODO
	public static String formatISODateTimeToDate(String fctvDt) {
		return "";
	}


	/**
	 * @param date   当前时间
	 * @param amount 增加小时数
	 * @return 增加后时间
	 * @description 增加小时
	 */
	public static Date addHours(Date date, int amount) {
		return DateUtils.addHours(date, amount);
	}


	/**
	 * @Description: 获取时间
	 * 格式HHmmss
	 * @return
	 */
	public static String getDefaultTimeFromDate(Date date){
		return DEFAULT_TIME_FORMATTER.format(date);
	}


}
