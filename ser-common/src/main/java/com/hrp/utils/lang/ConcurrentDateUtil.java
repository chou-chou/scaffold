package com.hrp.utils.lang;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @ClassName	ConcurrentDateUtil.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月6日 上午2:48:24
 * @Version 	V1.0    
 */
public class ConcurrentDateUtil {

	private static final String date_format = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(); 
 
    public static DateFormat getDateFormat()   
    {  
        DateFormat df = threadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(date_format);  
            threadLocal.set(df);  
        }  
        return df;  
    }  

    public static String formatDate(Date date) throws ParseException {
        return getDateFormat().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }
    
}
 