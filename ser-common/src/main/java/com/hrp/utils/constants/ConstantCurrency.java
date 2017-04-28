package com.hrp.utils.constants;

/**
 * 常量-》货币类型
 */
public class ConstantCurrency {
	/**
	 * CNY
	 */
	public static final String CNY = "CNY";
	/**
	 * 人民币
	 */
	public static final String CNY_NAME = "人民币";
	/**
	 * USD
	 */
	public static final String USD = "USD";
	/**
	 * 美元
	 */
	public static final String USD_NAME = "美元";
	/**
	 * JPY
	 */
	public static final String JPY = "JPY";
	/**
	 * 日元
	 */
	public static final String JPY_NAME = "日元";
	/**
	 * HKD
	 */
	public static final String HKD = "HKD";
	/**
	 * 港元
	 */
	public static final String HKD_NAME = "港元";
	/**
	 * EUR
	 */
	public static final String EUR = "EUR";
	/**
	 * 欧元
	 */
	public static final String EUR_NAME = "欧元";
	/**
	 * GBP
	 */
	public static final String GBP = "GBP";
	/**
	 * 英镑
	 */
	public static final String GBP_NAME = "英镑";

	/**
	 * 通过货币代码得到货币名称
	 */
	public static String getCurrencyName(final String currencyCode) {
		if ("CNY".equals(currencyCode)) {
			return "人民币";
		}

		if ("USD".equals(currencyCode)) {
			return "美元";
		}

		if ("JPY".equals(currencyCode)) {
			return "日元";
		}

		if ("HKD".equals(currencyCode)) {
			return "港元";
		}

		if ("EUR".equals(currencyCode)) {
			return "欧元";
		}

		if ("GBP".equals(currencyCode)) {
			return "英镑";
		}

		return null;
	}
}
