package com.hrp.utils.io;

import java.io.IOException;
import jodd.io.ZipUtil;

/**
 * 工具类-》IO处理工具类-》压缩解压缩工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class CompressUtil {
	public static final String ZIP = "zip";
	public static final String GZIP = "gzip";

	private CompressUtil() {
		throw new Error("工具类不能实例化！");
	}

	/**
	 * 压缩路径下所有文件
	 * 
	 * @param sourcePath
	 *            源路径
	 * 
	 * @throws IOException
	 */
	public static void doZip(final String sourcePath) throws IOException {
		ZipUtil.zip(sourcePath);
	}

	/**
	 * 解压路径下所有文件到目标路径下
	 * 
	 * @param sourceFilePath
	 *            源文件路径
	 * @param targetPath
	 *            目标路径
	 * @throws IOException
	 */
	public static void upZip(final String sourceFilePath, final String targetPath) throws IOException {
		ZipUtil.unzip(sourceFilePath, targetPath);
	}

}
