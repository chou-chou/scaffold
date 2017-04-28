package com.hrp.utils.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类-》基础工具类-》日期工具类  建议使用jdk中的新的时间类
 */
public final class DateUtil {
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private DateUtil() {
		throw new Error("工具类不能实例化！");
	}
	
	/**
	 * @Title:			DiffTime
	 * @Description:	两个时间之间的间隔(小时)
	 * @param time1
	 * @param time2
	 */
	public static long DiffTime(Date time1, Date time2) {
		long between = (time1.getTime() - time2.getTime());  // 单位：毫秒
		long days = between / (24 * 3600);
		long hours = between % (24*3600)/3600;
		long minutms = between % 3600/60;
		long seconds = between % 60/60;
		return hours;
	}
	
	/**
	 * @Title:			DiffTime
	 * @Description:	两个时间之间的间隔(单位毫秒)
	 * @param time1
	 * @param time2
	 * @throws ParseException 
	 */
	public static long DiffTime(String time1, String time2) throws ParseException {
		Date d1 = df.parse(time1);
		Date d2 = df.parse(time2);
		long diff  = d1.getTime() - d2.getTime();
		long days = diff / (1000 * 60 * 60 * 24);
		long day=diff/(24*60*60*1000);
		long hour=(diff/(60*60*1000)-day*24);
		long min=((diff/(60*1000))-day*24*60-hour*60);
		long s=(diff/1000-day*24*60*60-hour*60*60-min*60);
		return diff;
	}
	
	/**
	 * @Title:			CompareTime
	 * @Description:	时间比较
	 * @param time1
	 * @param time2
	 * @return 			当time1 < time2, 返回true，否则为false
	 */
	public static boolean before(Date time1, Date time2) {
		
		return time1.before(time2);
	}
	
	/**
	 * @Title:			DiffTime
	 * @Description:	获取时间间隔hours小时前后的一个时间点
	 * @param time1
	 * @param hours
	 * @param beforeOrafter	之前或之后的时间   true: 之后的一个时间点  false: 之前的一个时间点
	 * @return
	 */
	public static Date getTime(Date time, long hours, boolean beforeOrafter) {
		int sub = 1;
		if (beforeOrafter)  sub = 1;
		else sub = -1;
		long t = time.getTime() + (1000 * 60 * 60) * hours * sub;
		
		Date tmp = new Date(t);
		
		return tmp;
	}
	
	public static String formatDate(Date date) {
        return df.format(date);
    }
    
    public static Date parse(String strDate) throws ParseException{

        return df.parse(strDate);
    }
    
    public static String now() {
    	return df.format(new Date());
    }
    
	public static void main(String[] args) {
		Date t = getTime(new Date(), 3, true);
		System.out.println(df.format(t));
	}
	
}
