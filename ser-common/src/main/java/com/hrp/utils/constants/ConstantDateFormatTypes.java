package com.hrp.utils.constants;

/**
 * 常量-》时间格式化类型[基于simpleDateFormat]
 * 
 * <p>
 * G Era 标志符 Text AD
 * </p>
 * <p>
 * y 年 Year 1996; 96
 * </p>
 * <p>
 * M 年中的月份 Month July; Jul; 07
 * </p>
 * <p>
 * w 年中的周数 Number 27
 * </p>
 * <p>
 * W 月份中的周数 Number 2
 * </p>
 * <p>
 * D 年中的天数 Number 189
 * </p>
 * <p>
 * d 月份中的天数 Number 10
 * </p>
 * <p>
 * F 月份中的星期 Number 2
 * </p>
 * <p>
 * E 星期中的天数 Text Tuesday; Tue
 * </p>
 * <p>
 * a Am/pm 标记 Text PM
 * </p>
 * <p>
 * H 一天中的小时数（0-23） Number 0
 * </p>
 * <p>
 * k 一天中的小时数（1-24） Number 24
 * </p>
 * <p>
 * K am/pm 中的小时数（0-11） Number 0
 * </p>
 * <p>
 * h am/pm 中的小时数（1-12） Number 12
 * </p>
 * <p>
 * m 小时中的分钟数 Number 30
 * </p>
 * <p>
 * s 分钟中的秒数 Number 55
 * </p>
 * <p>
 * S 毫秒数 Number 978
 * </p>
 * <p>
 * z 时区 General time zone Pacific Standard Time; PST; GMT-08:00
 * </p>
 * <p>
 * Z 时区 RFC 822 time zone -0800
 * </p>
 */
public interface ConstantDateFormatTypes {
	/**
	 * yyyy年MM月 ，如：2015年01月
	 */
	String YEAR_MONTH = "yyyy年MM月";
	/**
	 * yyyy年MM月dd日 ，如：2015年01月25日
	 */
	String YEAR_DATE = "yyyy年MM月dd日";
	/**
	 * yyyy年MM月dd日 E ，如：2015年01月25日 星期五
	 */
	String YEAR_DATE_WEEK = "yyyy年MM月dd日 E";
	/**
	 * yyyy年MM月dd日 hh:mm:ss ，如：2015年01月25日 12:45:36
	 */
	String YEAR_DATE_TIME12 = "yyyy年MM月dd日 hh:mm:ss";
	/**
	 * yyyy年MM月dd日 HH:mm:ss ，如：2015年01月25日 14:45:36 simpleDateFormat HH 24小时制 hh12小时制
	 */
	String YEAR_DATE_TIME24 = "yyyy年MM月dd日 HH:mm:ss"; // simpleDateFormat HH 24小时制 hh12小时制
	/**
	 * yyyy ，如：2015
	 */
	String YYYY = "yyyy";
	/**
	 * MM ，如：11
	 */
	String MM = "MM";
	/**
	 * dd ，如：23
	 */
	String DD = "dd";
	/**
	 * yyyy-MM ，如：2015-09
	 */
	String YYYY_MM = "yyyy-MM";
	/**
	 * yyyyMM ，如：201509
	 */
	String YYYYMM = "yyyyMM";
	/**
	 * yyyy-MM-dd ，如：2015-05-23
	 */
	String YYYY_MM_DD = "yyyy-MM-dd";
	/**
	 * yyyyMMdd ，如：20150523
	 */
	String YYYYMMDD = "yyyyMMdd";
	/**
	 * yyyy-MM-dd hh:mm ，如：2015-05-23 10:40
	 */
	String YYYY_MM_DD_HH12_MM = "yyyy-MM-dd hh:mm";
	/**
	 * yyyy-MM-dd HH:mm ，如：2015-05-23 24:40 simpleDateFormat HH 24小时制 hh12小时制
	 */
	String YYYY_MM_DD_HH24_MM = "yyyy-MM-dd HH:mm"; // simpleDateFormat HH 24小时制 hh12小时制
	/**
	 * yyyy-MM-dd hh:mm:ss ，如：2015-05-23 10:40:36
	 */
	String YYYY_MM_DD_HH12_MM_SS = "yyyy-MM-dd hh:mm:ss";
	/**
	 * yyyy-MM-dd HH:mm:ss ，如：2015-05-23 24:40:36 simpleDateFormat HH 24小时制 hh12小时制
	 */
	String YYYY_MM_DD_HH24_MM_SS = "yyyy-MM-dd HH:mm:ss"; // simpleDateFormat HH 24小时制 hh12小时制
	/**
	 * yyyy-mm-dd hh:mm:ss.fffffffff，如2016-07-19 23:42:12.000101100
	 */
	String YYYY_MM_DD_HH24_MM_SS_FF = "yyyy-mm-dd hh:mm:ss.fffffffff";  // Timestamp格式
	/**
	 * hh:mm:ss ，如：10:40:36
	 */
	String HH12_MM_SS = "hh:mm:ss";
	/**
	 * HH:mm:ss ，如：22:40:36 simpleDateFormat HH 24小时制 hh12小时制
	 */
	String HH24_MM_SS = "HH:mm:ss"; // simpleDateFormat HH 24小时制 hh12小时制
	/**
	 * hhmmss ，如：104036
	 */
	String HH12MMSS = "hhmmss";
	/**
	 * HHmmss ，如：224036
	 */
	String HH24MMSS = "HHmmss";
	/**
	 * hhmmssmss ，如：104036375
	 */
	String HH12MMSSMSS = "hhmmssSSS";
	/**
	 * HHmmssmss ，如：224036375
	 */
	String HH24MMSSMSS = "HHmmssSSS";
	/**
	 * hh:mm ，如：10:40
	 */
	String HH12_MM = "hh:mm";
	/**
	 * HH:mm ，如：22:40 simpleDateFormat HH 24小时制 hh12小时制
	 */
	String HH24_MM = "HH:mm"; // simpleDateFormat HH 24小时制 hh12小时制
}
