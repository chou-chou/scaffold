package com.hrp.utils.lang;

import java.util.Locale;

/**
 * 工具类-》基础工具类-》Locale本地化工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public class LocaleUtil {

	/**
	 * getLocale
	 */
	public static Locale getLocale() {
		return jodd.util.LocaleUtil.getLocale("zh", "CN");
	}

	/**
	 * getLocale
	 */
	public static Locale getLocale(final String languageCode) {
		return jodd.util.LocaleUtil.getLocale(languageCode);
	}

	/**
	 * getLocale
	 */
	public static Locale getLocale(final String language, final String country) {
		return jodd.util.LocaleUtil.getLocale(language, country);
	}

	/**
	 * getLocale
	 */
	public static Locale getLocale(final String language, final String country, final String variant) {
		return jodd.util.LocaleUtil.getLocale(language, country, variant);
	}

}
