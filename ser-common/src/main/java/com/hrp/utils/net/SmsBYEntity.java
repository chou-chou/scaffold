package com.hrp.utils.net;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

//import common.util.web.Base;

/**
 * 
 * 八优短信网,发送短信,拼接参数
 */
public class SmsBYEntity {

	private String encode = "GB2312";

	public String getEncode() {
		return encode;
	}

	public void setEncode(final String encodeStr) {
		this.encode = encodeStr;
	}

	// 服务器请求地址(需要加密);
	private String servicesRequestAddRess = "";
	// 登录的用户名(需要加密);
	private String username = "";
	// 登录的密码(需要加密);
	private String password = "";
	// 短信发送方式;
	private int smstype;
	// 短信发送是否定时;
	private int timerflag;
	// 短信发送定时时间;
	private String timervalue;
	// 短信发送定时的类型;
	private int timertype;
	// 短信发送的编号(需要加密);
	private int timerid;
	// 发送的手机号码(需要加密);
	private String mobiles;
	// 发送的内容(需要加密);
	private String message;

	public String getServicesRequestAddRess() {
		return servicesRequestAddRess;
	}

	public void setServicesRequestAddRess(final String servicesRequestAddRessParm) {
		this.servicesRequestAddRess = servicesRequestAddRessParm; // Base.base64Decode(servicesRequestAddRess);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String usernameParm) {
		this.username = usernameParm; // Base.base64Decode(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String passwordParm) {
		this.password = passwordParm; // Base.base64Decode(password);
	}

	public int getSmstype() {
		return smstype;
	}

	public void setSmstype(final int smstypeParm) {
		this.smstype = smstypeParm;
	}

	public int getTimerflag() {
		return timerflag;
	}

	public void setTimerflag(final int timerflagParm) {
		this.timerflag = timerflagParm;
	}

	public String getTimervalue() {
		return timervalue;
	}

	public void setTimervalue(final String timervalueParm) {
		this.timervalue = timervalueParm;
	}

	public int getTimertype() {
		return timertype;
	}

	public void setTimertype(final int timertypeParm) {
		this.timertype = timertypeParm;
	}

	public int getTimerid() {
		return timerid;
	}

	public void setTimerid(final int timeridParm) {
		this.timerid = timeridParm; // Base.base64Decode(timerid);
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(final String mobilesParm) {
		this.mobiles = mobilesParm; // Base.base64Decode(mobiles);
	}

	public String getMessage() {
		return message;
	}

	/**
	 * setMessage
	 */
	public void setMessage(final String messageParm) {
		try {
			this.message = URLEncoder.encode(messageParm, this.getEncode());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// this.message = message; //Base.base64Decode(message);
	}

	/**
	 * 发送短信
	 */
	public Map<String, String> sendSMS() {
		// 使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// 生成的解析器将提供对 XML 名称空间的支持
		dbf.setNamespaceAware(true);
		Document doc;
		Map<String, String> result = new LinkedHashMap<String, String>();
		try {
			// 新的 DocumentBuilder 实例。
			DocumentBuilder db = dbf.newDocumentBuilder();
			/*
			 * 发送
			 */
			InputStream is = this.getSoapInputStream(this.getServicesRequestAddRess(), this.getRequestData().toString());
			/*
			 * 发送的回复信息
			 */
			// 将给定 InputStream 的内容解析为一个 XML 文档，并且返回一个新的 DOM Document 对象。
			doc = db.parse(is);
			// 状态码 0:成功
			result.put("errorcode", doc.getElementsByTagName("errorcode").item(0).getFirstChild().getNodeValue());
			// 返回的信息
			result.put("errordescription", doc.getElementsByTagName("errordescription").item(0).getFirstChild().getNodeValue());
			// 时间
			result.put("time", doc.getElementsByTagName("time").item(0).getFirstChild().getNodeValue());
			// 短信剩余条数
			result.put("msgcount", doc.getElementsByTagName("msgcount").item(0).getFirstChild().getNodeValue());
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "发送失败!!!");
			return result;
		}
		return result;
	}

	/**
	 * 取消定时短信
	 */
	public Map<String, String> cancelTimingSMS() {
		// 使应用程序能够从 XML 文档获取生成 DOM 对象树的解析器
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		// 生成的解析器将提供对 XML 名称空间的支持
		dbf.setNamespaceAware(true);
		Document doc;
		Map<String, String> result = new LinkedHashMap<String, String>();
		try {
			// 新的 DocumentBuilder 实例。
			DocumentBuilder db = dbf.newDocumentBuilder();
			/*
			 * 发送
			 */
			InputStream is = this.getSoapInputStream(this.getServicesRequestAddRess(), this.getRequestData2().toString());
			/*
			 * 发送的回复信息
			 */
			// 将给定 InputStream 的内容解析为一个 XML 文档，并且返回一个新的 DOM Document 对象。
			doc = db.parse(is);
			// 状态码 0:成功
			result.put("errorcode", doc.getElementsByTagName("errorcode").item(0).getFirstChild().getNodeValue());
			// 返回的信息
			result.put("errordescription", doc.getElementsByTagName("errordescription").item(0).getFirstChild().getNodeValue());
			// 时间
			result.put("time", doc.getElementsByTagName("time").item(0).getFirstChild().getNodeValue());
			// 短信剩余条数
			result.put("msgcount", doc.getElementsByTagName("msgcount").item(0).getFirstChild().getNodeValue());
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "取消定时短信失败!!!");
			return result;
		}
		return result;
	}

	/**
	 * 拼接非定时发送短信,定时短信参数;
	 */
	private StringBuffer getRequestData() {

		StringBuffer requestAddRess = new StringBuffer();
		requestAddRess.append("func=sendsms&username=");
		requestAddRess.append(this.username);
		requestAddRess.append("&password=");
		requestAddRess.append(password);
		requestAddRess.append("&smstype=");
		requestAddRess.append(smstype);
		requestAddRess.append("&timerflag=");
		requestAddRess.append(this.timerflag);
		if (this.timerflag != 0) {
			requestAddRess.append("&timervalue=");
			requestAddRess.append(this.timervalue);
		}
		requestAddRess.append("&timertype=");
		requestAddRess.append(this.timertype);
		requestAddRess.append("&timerid=");
		requestAddRess.append(this.timerid);
		requestAddRess.append("&mobiles=");
		requestAddRess.append(this.mobiles);
		requestAddRess.append("&message=");
		requestAddRess.append(this.message);
		return requestAddRess;
	}

	/**
	 * 拼接取消定时短信参数
	 */
	private StringBuffer getRequestData2() {
		StringBuffer requestAddRess = new StringBuffer();
		requestAddRess.append("func=deletetimer&username=");
		requestAddRess.append(this.username);
		requestAddRess.append("&password=");
		requestAddRess.append(password);
		requestAddRess.append("&timerid=");
		requestAddRess.append(this.timerid);
		return requestAddRess;
	}

	/**
	 * getSoapInputStream
	 */
	public InputStream getSoapInputStream(final String requestAddress, final String requestData) {
		InputStream is = null;
		try {
			URL url = new URL(requestAddress);
			// 获得远程资源连接
			URLConnection conn = url.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
			// 是否允许缓存
			byte[] bts = requestData.getBytes();
			// 设置一般请求属性。
			httpUrlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			// 设置请求方式
			httpUrlConnection.setRequestMethod("POST");
			// 设置请求的长度
			httpUrlConnection.setRequestProperty("Content-Length", Integer.toString(bts.length));
			// 使用 URL 连接进行输出
			httpUrlConnection.setDoOutput(true);
			// 连接
			httpUrlConnection.connect();
			// 写入到此连接的输出流。
			httpUrlConnection.getOutputStream().write(bts, 0, bts.length);
			// 刷出
			httpUrlConnection.getOutputStream().flush();
			// 关闭写出流
			httpUrlConnection.getOutputStream().close();
			// 打开的连接读入的输入流,获得读入的信息
			is = httpUrlConnection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
}
