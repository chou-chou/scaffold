package com.hrp.utils.io;

import com.lowagie.text.html.HtmlEncoder;

import jodd.util.HtmlDecoder;

/**
 * 工具类-》IO处理工具类-》 html工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class HtmlUtil {

	private HtmlUtil() {
		throw new Error("工具类不能实例化");
	}

	/**
	 * decode
	 * 
	 * @param html
	 *            html
	 * @return String
	 */
	public static String decode(final String html) {
		return HtmlDecoder.decode(html);
	}

	/**
	 * encode
	 */
	public static String encode(final String str) {
		return HtmlEncoder.encode(str);
	}

	// ////////////其他 HtmlEncoder
}
