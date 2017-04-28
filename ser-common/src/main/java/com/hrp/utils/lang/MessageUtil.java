package com.hrp.utils.lang;

import org.springframework.context.MessageSource;

/**
 * 工具类-》基础工具类-》LangMessageUtil
 */
public class MessageUtil {
	private static MessageSource messageSource = null;

	/**
	 * getMessage
	 */
	public static String getMessage(final String code) {
		if (messageSource == null) {
			return null;
		}
		return messageSource.getMessage(code, null, null, LocaleUtil.getLocale());
	}

	/**
	 * getMessage
	 */
	public static String getMessage(final String code, final Object[] args, final String defaultMessage) {
		if (messageSource == null) {
			return null;
		}
		return messageSource.getMessage(code, args, defaultMessage, LocaleUtil.getLocale());
	}
}
