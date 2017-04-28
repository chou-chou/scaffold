package com.hrp.utils.net;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jodd.servlet.ServletUtil;
import jodd.util.StringUtil;

/**
 * 工具类-》网络相关工具类-》Cookie 辅助类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public class CookieUtil {
	/**
	 * 每页条数cookie名称
	 */
	public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
	/**
	 * 默认每页条数
	 */
	public static final int DEFAULT_SIZE = 20;
	/**
	 * 最大每页条数
	 */
	public static final int MAX_SIZE = 200;

	/**
	 * 获得cookie的每页条数
	 * 
	 * 使用_cookie_page_size作为cookie name
	 * 
	 * @return default:20 max:200
	 */
	public static int getPageSize(final HttpServletRequest request) {
		Cookie cookie = getCookie(request, COOKIE_PAGE_SIZE);
		int count = 0;
		if (cookie != null) {
			count = Integer.parseInt(cookie.getValue());
		}
		if (count <= 0) {
			count = DEFAULT_SIZE;
		} else if (count > MAX_SIZE) {
			count = MAX_SIZE;
		}
		return count;
	}

	/**
	 * 获得所有的cookie
	 * 
	 * @param cookieName
	 *            cookie名称
	 * @return 返回客户端所有符合cookie名称的cookie
	 */
	public static Cookie[] getAllCookies(final HttpServletRequest request, final String cookieName) {
		return ServletUtil.getAllCookies(request, cookieName);
	}

	/**
	 * 获得cookie
	 * 
	 * @param cookieName
	 *            cookie名称
	 * @return 返回客户端第一个符合cookie名称的cookie
	 */
	public static Cookie getCookie(final HttpServletRequest request, final String cookieName) {
		return ServletUtil.getCookie(request, cookieName);
	}

	/**
	 * 根据部署路径，将cookie保存在根目录。
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param value
	 * @param expiry
	 * @param domain
	 * @return cookie
	 */
	public static Cookie addCookie(final HttpServletRequest request, final HttpServletResponse response, final String name, final String value, final Integer expiry, final String domain) {
		Cookie cookie = new Cookie(name, value);
		if (expiry != null) {
			cookie.setMaxAge(expiry);
		}
		if (StringUtil.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		String ctx = request.getContextPath();
		cookie.setPath(StringUtil.isBlank(ctx) ? "/" : ctx);
		response.addCookie(cookie);
		return cookie;
	}

	/**
	 * 取消cookie
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @param domain
	 */
	public static void cancleCookie(final HttpServletRequest request, final HttpServletResponse response, final String name, final String domain) {
		Cookie cookie = new Cookie(name, "");
		cookie.setMaxAge(0);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtil.isBlank(ctx) ? "/" : ctx);
		if (StringUtil.isNotBlank(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}
}
