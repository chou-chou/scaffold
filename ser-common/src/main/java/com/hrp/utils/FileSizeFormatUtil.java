package com.hrp.utils;

import java.text.DecimalFormat;

/**
 * Created by zhangdp on 2016/6/24.
 */
public class FileSizeFormatUtil {
	public static String formatFileSize(long size) {
		DecimalFormat formater = new DecimalFormat("####.00");
		if (size < 1024) {
			return size + "bytes";
		} else if (size < 1024 * 1024) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < 1024 * 1024 * 1024) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < 1024 * 1024 * 1024 * 1024) {
			float gbsize = size / 1024f / 1024f / 1024f;
			return formater.format(gbsize) + "GB";
		} else if (size < 1024 * 1024 * 1024 * 1024 * 1024) {
			float tbsize = size / 1024f / 1024f / 1024f / 1024f;
			return formater.format(tbsize) + "TB";
		} else {
			return "size: error";
		}
	}
}
