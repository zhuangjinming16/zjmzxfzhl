package com.zjmzxfzhl.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具类
 * 
 * @author 庄金明
 */
public class DateUtil {

	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_SLASH = "yyyy/MM/dd";
	public static final String DATE_FORMAT_NOT_ALL = "yyyyMMdd";
	public static final String DATE_FORMAT_CN = "yyyy'年'MM'月'dd'日'";
	public static final String DATE_FORMAT_MONTH_DAY_YEAR = "M/d/yyyy";

	public static final String DATETIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
	public static final String DATETIME_FORMAT_NOT_SLASH = "yyyyMMdd HH:mm:ss";
	public static final String DATETIME_FORMAT_NOT_ALL = "yyyyMMddHHmmss";

	public static final String TIME_FORMAT_DEFAULT = "HH:mm:ss";
	public static final String TIME_FORMAT_NOT_ALL = "HHmmss";

	private static ThreadLocal<Date> threadlocalDate = new ThreadLocal<Date>();

	/**
	 * 同一线程内获取统一的时间，用于数据库入库时间字段统一，无需一直将时间作为参数一直传入其余方法内部，同时减少new Date()
	 * 
	 * 使用方式: DateUtil.getNow()
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date date = threadlocalDate.get();
		if (date == null) {
			date = new Date();
			threadlocalDate.set(date);
		}
		return date;
	}

	/**
	 * 应在拦截所有请求的过滤器中调用该方法(参考XssFilter.java)，使请求结束后，清空当前线程时间，防止下次请求继续拿到的是之前请求的时间
	 */
	public static void clearNow() {
		Date date = threadlocalDate.get();
		if (date != null) {
			threadlocalDate.remove();
		}
	}

	private static final Object lockObj = new Object(); // 锁对象
	/**
	 * 存放不同的日期模板格式的sdf的Map
	 */
	private static Map<String, ThreadLocal<SimpleDateFormat>> simpleDateFormatMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	/**
	 * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
	 * 
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(final String pattern) {
		ThreadLocal<SimpleDateFormat> threadLocalSimpleDateFormat = simpleDateFormatMap.get(pattern);
		// 此处的双重判断和同步是为了防止simpleDateFormatMap这个单例被多次put重复的sdf
		if (threadLocalSimpleDateFormat == null) {
			synchronized (lockObj) {
				threadLocalSimpleDateFormat = simpleDateFormatMap.get(pattern);
				if (threadLocalSimpleDateFormat == null) {
					// 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
					// 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
					threadLocalSimpleDateFormat = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							return new SimpleDateFormat(pattern);
						}
					};
					simpleDateFormatMap.put(pattern, threadLocalSimpleDateFormat);
				}
			}
		}
		return threadLocalSimpleDateFormat.get();
	}

	/**
	 * date根据不同的格式,转换成String
	 * 
	 * @param date
	 *            日期型
	 * @param pattern
	 *            目标转换的日期格式
	 * @return
	 */
	public static String dateToStr(Date date, String pattern) {
		if (date == null)
			return "";
		if (pattern == null)
			return "";
		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);
		return (simpleDateFormat.format(date));
	}

	/**
	 * 返回:20131128
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringYYYYMMDD(Date date) {
		return (dateToStr(date, DATE_FORMAT_NOT_ALL));
	}

	/**
	 * 返回:2013-11-28
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date) {
		return (dateToStr(date, DATE_FORMAT_DEFAULT));
	}

	/**
	 * 返回:2013-11-28 18:44:39
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStrTime(Date date) {
		return (dateToStr(date, DATETIME_FORMAT_DEFAULT));
	}

	/**
	 * 比较日期的天数
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @param pattern
	 * @return
	 */
	public static int compare(Date firstDate, Date secondDate, String pattern) {

		if (pattern == null)
			pattern = DATE_FORMAT_DEFAULT;
		SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);
		firstDate = strToDate(simpleDateFormat.format(firstDate), pattern);
		secondDate = strToDate(simpleDateFormat.format(secondDate), pattern);
		Calendar firstCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
		}

		Calendar secondCalendar = null;
		/** 使用给定的 Date 设置此 Calendar 的时间。 **/
		if (firstDate != null) {
			secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
		}

		try {
			/**
			 * 比较两个 Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。
			 * 
			 * 如果参数表示的时间等于此 Calendar 表示的时间，则返回 0 值；
			 * 
			 * 如果此 Calendar 的时间在参数表示的时间之前，则返回小于 0 的值； 如果此 Calendar 的时间在参数表示的时间之后，则返回大于 0 的值
			 **/
			return firstCalendar.compareTo(secondCalendar);
		} catch (NullPointerException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 把对象返回:2013-11-28 18:44:39
	 * 
	 * @param obj
	 * @return
	 */
	public static String objToStr(Object obj) {
		if (obj.getClass() == String.class)
			return (String) obj;
		else if (obj.getClass() == Date.class)
			return dateToStrTime((Date) obj);
		else if (obj.getClass() == Timestamp.class) {
			return dateToStrTime(new Date(((Timestamp) obj).getTime()));
		} else
			return obj.toString();
	}

	/**
	 * 字符串转换成Date格式
	 * 
	 * @param dateStr
	 *            日期型字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date strToDate(String dateStr, String pattern) {
		try {
			if ((dateStr == null) || (dateStr.equals(""))) {
				return null;
			}

			if (pattern == null)
				pattern = DATE_FORMAT_DEFAULT;

			SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);
			return simpleDateFormat.parse(dateStr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转换的带time的Date格式
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date strToDateTime(String dateStr) {
		try {
			return strToDate(dateStr, DATETIME_FORMAT_DEFAULT);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 给时间加上或减去指定毫秒,秒,分,时,天、月或年等,返回变动后的时间
	 * 
	 * @param date
	 *            要加减前的时间,如果不传,则为当前日期
	 * @param field
	 *            时间域,有Calendar.MILLISECOND,Calendar.SECOND,Calendar.MINUTE,<br>
	 *            Calendar.HOUR,Calendar.DATE, Calendar.MONTH,Calendar.YEAR
	 * @param amount
	 *            按指定时间域加减的时间数量,正数为加,负数为减.
	 * @return 变动后的时间
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			date = new Date();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);

		return cal.getTime();
	}

	public static Date addMilliSecond(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	public static Date addSecond(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	public static Date addMiunte(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	public static Date addHour(Date date, int amount) {
		return add(date, Calendar.HOUR, amount);
	}

	public static Date addDay(Date date, int amount) {
		return add(date, Calendar.DATE, amount);
	}

	public static Date addMonth(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	public static Date addYear(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 获取日期相差天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDateDeff(Date startDate, Date endDate) {

		int intValue = 0;
		String df = DATE_FORMAT_DEFAULT;
		startDate = DateUtil.strToDate(DateUtil.dateToStr(startDate, df), df);
		endDate = DateUtil.strToDate(DateUtil.dateToStr(endDate, df), df);
		intValue = (int) ((startDate.getTime() - endDate.getTime()) / 86400000);
		return intValue;

	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(dateToStr(new Date()));
		System.out.println(dateToStr(new Date(), DATETIME_FORMAT_DEFAULT));
		System.out.println(dateToStr(new Date(), DATETIME_FORMAT_NOT_ALL));

		System.out.println(objToStr("2014-09-01"));
		System.out.println(dateToStringYYYYMMDD(new Date()));

		System.out.println(getDateDeff(new Date(), strToDate("2015-1-20", DATE_FORMAT_DEFAULT)));

		String kb = "非高峰年卡次";
		if (kb.contains("次")) {
			System.out.println("222");
		}

		System.out.println(new Date());
		Thread.sleep(1000);
		System.out.println(DateUtil.getNow());
		Thread.sleep(1000);
		System.out.println(DateUtil.getNow());
		System.out.println(DateUtil.getNow());
		Thread.sleep(1000);
		System.out.println(DateUtil.getNow());
		System.out.println(DateUtil.getNow());
		System.out.println(DateUtil.getNow());
	}
}
