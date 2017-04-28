package com.hrp.utils.net;

import java.util.Map;

import com.hrp.utils.io.PropsUtil;

/**
 * 短信
 */
public class SmsBYUtil {
	// String userName:八优用户名
	// String passWord:八优用户名密码,需要MD5 32位加密
	// String mobiles:需要发送的手机
	// message:发送的内容
	// int timerid:每次定时提交的标识，定时发送时必须提供，可以默认为0，可重复此标识主要用来 调用接口删除定时当要删除本次提交的定时时，只要提供该ID就可以删除了
	// int smstype:0 表示 一个或多个号码对应一个短信内容，1 表示 号码和内容一一对应，也就是多个号码对应多个内容
	// String servicesRequestAddRess:八优短信平台接口
	// int timertype:1.只发一次 2.每天一次 3.每周一次 4.每月一次 5.每年一次
	// int timerflag:1.表示定时 0.表示非定时
	// timervalue:定时时间 yyyy-mm-dd hh:mm:ss
	// 给一个人发短信,或多个人,内容相同,多条短信:"xxxxxxx,xxxxxxxxx"[一个字符串逗号隔开]
	//
	private static String userName = PropsUtil.getValue("sms.user", "system.properties");
	private static String passWord = PropsUtil.getValue("sms.password", "system.properties");

	private static int timerid = 0;

	public static final int TIMERTYPE_ONE = 1;
	public static final int TIMERTYPE_DAY = 2;
	public static final int TIMERTYPE_WEEK = 3;
	public static final int TIMERTYPE_MONTH = 4;
	public static final int TIMERTYPE_YEAR = 5;

	/**
	 * 发送短信后接口提供商传递回来的信息
	 */
	public static Map<String, String> sendOneSms(final String message, final String mobile) {
		SmsBYEntity send = new SmsBYEntity();
		send.setUsername(userName);
		send.setPassword(passWord);

		send.setMobiles(mobile);

		send.setMessage(message);
		send.setTimerflag(0);
		send.setTimertype(TIMERTYPE_ONE);
		// 以下为固定参数
		send.setSmstype(0);
		send.setServicesRequestAddRess("http://sms.c8686.com/Api/BayouSmsApiEx.aspx");
		// 发送短信
		Map<String, String> map = send.sendSMS();
		return map;
	}

	/**
	 * 给一个人发短信,或多个人,内容相同的定时短信
	 * 
	 * @param message
	 *            发送的内容
	 * @param timertype
	 *            1.只发一次 2.每天一次 3.每周一次 4.每月一次 5.每年一次
	 * @param timervalue
	 *            定时时间 yyyy-mm-dd hh:mm:ss
	 * @param mobile
	 *            需要发送的手机
	 */
	public static Map<String, String> sendOneTimingSms(final String message, final int timertype, final String timervalue, final String mobile) {
		SmsBYEntity send = new SmsBYEntity();
		timerid = timerid++;
		send.setUsername(userName);
		send.setPassword(passWord);
		send.setMobiles(mobile);
		send.setMessage(message);
		send.setTimerid(timerid);
		send.setTimertype(timertype);

		send.setTimervalue(timervalue);
		// 以下为固定参数
		send.setSmstype(0);
		send.setTimerflag(1);
		send.setServicesRequestAddRess("http://sms.c8686.com/Api/BayouSmsApiEx.aspx");
		// 发送短信
		Map<String, String> map = send.sendSMS();
		// 接受发送后的信息
		map.put("timerid", String.valueOf(send.getTimerid()));
		return map;
	}

	/**
	 * 取消定时短信,根据发送定时短信时提供的:timerid
	 */
	public static Map<String, String> cancelTimingSms(final int timerId) {
		SmsBYEntity send = new SmsBYEntity();
		send.setUsername(userName);
		send.setPassword(passWord);
		send.setTimerid(timerId);
		send.setServicesRequestAddRess("http://sms.c8686.com/Api/BayouSmsApiEx.aspx");
		Map<String, String> map = send.cancelTimingSMS();
		return map;
	}
}
