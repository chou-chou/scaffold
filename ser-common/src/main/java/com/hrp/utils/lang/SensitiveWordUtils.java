package com.hrp.utils.lang;

/**
 * 屏蔽关键词 工具类
 */
public class SensitiveWordUtils {
	private static final String DEFAULT_MASK = "***"; // 默认的遮罩文字

	/**
	 * 替换input中的屏蔽关键词为默认的掩码
	 */
	public static void replace(StringBuilder sb, String searchString) {
		replace(sb, searchString, DEFAULT_MASK);
	}

	/**
	 * 将屏蔽关键词 替换
	 */
	public static void replace(StringBuilder sb, String searchString, String replacement) {
		int start = 0;
		int end = sb.indexOf(searchString, start);
		if (end == -1) {
			return;
		}
		int searchLength = searchString.length();
		int replaceLength = replacement.length();
		while (end != -1) {
			sb.replace(end, end + searchLength, replacement);
			start = end + replaceLength;
			end = sb.indexOf(searchString, start);
		}
	}

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder("张家界张家口北京张");
		System.out.println("before:" + sb);
		replace(sb, "张", "**");
		System.out.println("after:" + sb);
	}

	/**
	 * 是否包含屏蔽关键词
	 */
	public static boolean containsForbiddenWord(String input) {
		// for (int i = 0, l = forbiddenWords.size(); i < l; i++) {
		// Pattern forbiddenWordPattern = forbiddenWords.get(i);
		// if (forbiddenWordPattern.matcher(input).find()) {
		// return true;
		// }
		// }
		return false;
	}
}
