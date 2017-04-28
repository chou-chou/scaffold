package com.hrp.utils.net;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import com.hrp.utils.lang.StringUtil;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

/**
 * 工具类-》网络相关工具类-》request请求工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public class RequestUtil {
	
	private static Logger log = LoggerFactory.getLogger(RequestUtil.class);

	/**
	 * 获取QueryString的参数，并使用URLDecoder以UTF-8格式转码。如果请求是以post方法提交的， 那么将通过HttpServletRequest#getParameter获取。
	 * 
	 * @param request
	 *            web请求
	 * @param name
	 *            参数名称
	 */
	public static String getsimpleQueryParam(final HttpServletRequest request, final String name) {
		if (StringUtil.isNullOrBlank(name)) {
			return null;
		}
		if (request.getMethod().equalsIgnoreCase("POST")) {
			return request.getParameter(name);
		}
		String s = request.getQueryString();
		if (StringUtil.isNullOrBlank(s)) {
			return null;
		}
		try {
			s = URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("encoding " + "UTF-8" + " not support?", e);
		}
		String[] values = parseQueryString(s).get(name);
		if (values != null && values.length > 0) {
			return values[values.length - 1];
		} else {
			return null;
		}
	}

	/**
	 * 得到简单的查询参数
	 */
	public static Map<String, Object> getSimpleQueryParams(final HttpServletRequest request) {
		Map<String, String[]> map;
		if (request.getMethod().equalsIgnoreCase("POST")) {
			map = request.getParameterMap();
		} else {
			String s = request.getQueryString();
			if (StringUtil.isNullOrBlank(s)) {
				return new HashMap<String, Object>();
			}
			try {
				s = URLDecoder.decode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				log.error("encoding " + "UTF-8" + " not support?", e);
			}
			map = parseQueryString(s);
		}

		Map<String, Object> params = new HashMap<String, Object>(map.size());
		int len;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			len = entry.getValue().length;
			if (len == 1) {
				params.put(entry.getKey(), entry.getValue()[0]);
			} else if (len > 1) {
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}

	/**
	 * Parses a query string passed from the client to the server and builds a <code>HashTable</code> object with key-value pairs. The query string should be in the form of a string packaged by the GET or POST method, that is, it should have key-value pairs in the form <i>key=value</i>, with each
	 * pair separated from the next by a &amp; character.
	 * 
	 */
	public static Map<String, String[]> parseQueryString(final String s) {
		String[] valArray = null;
		if (s == null) {
			throw new IllegalArgumentException();
		}
		Map<String, String[]> ht = new HashMap<String, String[]>();
		StringTokenizer st = new StringTokenizer(s, "&");
		while (st.hasMoreTokens()) {
			String pair = (String) st.nextToken();
			int pos = pair.indexOf('=');
			if (pos == -1) {
				continue;
			}
			String key = pair.substring(0, pos);
			String val = pair.substring(pos + 1, pair.length());
			if (ht.containsKey(key)) {
				String[] oldVals = (String[]) ht.get(key);
				valArray = new String[oldVals.length + 1];
				for (int i = 0; i < oldVals.length; i++) {
					valArray[i] = oldVals[i];
				}
				valArray[oldVals.length] = val;
			} else {
				valArray = new String[1];
				valArray[0] = val;
			}
			ht.put(key, valArray);
		}
		return ht;
	}

	/**
	 * 得到requestMap
	 */
	public static Map<String, String> getRequestMap(final HttpServletRequest request, final String prefix) {
		return getRequestMap(request, prefix, false);
	}

	/**
	 * 得到requestMap
	 */
	public static Map<String, String> getRequestMapWithPrefix(final HttpServletRequest request, final String prefix) {
		return getRequestMap(request, prefix, true);
	}

	/**
	 * 得到requestMap
	 */
	private static Map<String, String> getRequestMap(final HttpServletRequest request, final String prefix, final boolean nameWithPrefix) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> names = request.getParameterNames();
		String name, key, value;
		while (names.hasMoreElements()) {
			name = names.nextElement();
			if (name.startsWith(prefix)) {
				key = nameWithPrefix ? name : name.substring(prefix.length());
				value = StringUtil.join(request.getParameterValues(name), ",");
				map.put(key, value);
			}
		}
		return map;
	}
	
	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 */
	public static String getIpAddr(final HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtil.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtil.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * 获得当的访问路径
	 * 
	 * HttpServletRequest.getRequestURL+"?"+HttpServletRequest.getQueryString
	 * 
	 * @param request
	 */
	public static String getLocation(final HttpServletRequest request) {
		UrlPathHelper helper = new UrlPathHelper();
		StringBuffer buff = request.getRequestURL();
		String uri = request.getRequestURI();
		String origUri = helper.getOriginatingRequestUri(request);
		buff.replace(buff.length() - uri.length(), buff.length(), origUri);
		String queryString = helper.getOriginatingQueryString(request);
		if (queryString != null) {
			buff.append("?").append(queryString);
		}
		return buff.toString();
	}

	/**
	 * 简单get请求
	 */
	public static HttpResponse simpleGetRequest(final String url, final Map<String, String> httpParams) {
		HttpRequest request = HttpRequest.get(url).acceptEncoding("gzip");
		if (httpParams != null) {
			request.query(httpParams);
		}
		HttpResponse response = request.send().unzip();
		return response;
	}

	/**
	 * 简单post请求
	 */
	public static HttpResponse simplePostRequest(final String url, final Map<String, Object> httpParams) {
		HttpRequest request = HttpRequest.post(url).acceptEncoding("gzip");
		if (httpParams != null) {
			request.form(httpParams);
		}
		HttpResponse response = request.send().unzip();
		return response;
	}

	/**
	 * @Title:			isAjax
	 * @Description:	判断是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(final HttpServletRequest request) {
		// 普通请求和ajax请求的报文头不一样
		String requestType = request.getHeader("X-Requested-With"); 
		if (requestType == null) {
			return false;
		}
		
		if ("xmlhttprequest".equals(requestType)) {
			return true;
		}
		
		return false;
	}
	
}
