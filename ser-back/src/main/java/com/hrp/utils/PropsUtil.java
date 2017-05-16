package com.hrp.utils;

import jodd.props.Props;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具类-》IO处理工具类-》properties 配置文件操作工具类
 * <p>
 * [依赖 fastJson.jar]
 * </p>
 */
public final class PropsUtil {
	private PropsUtil() {
		throw new Error("工具类不能实例化！");
	}

	private static Map<String, Props> props = new HashMap<String, Props>();

	/**
	 * 读取properties文件参数
	 * 
	 * @param key
	 * @param propFilePath
	 *            propFile文件名 如system.properties
	 * @return 值
	 */
	public static String getValue(final String key, final String propFilePath) {
		Props prop = null;
		if (props.get(propFilePath) != null) {
			prop = props.get(propFilePath);
		} else {
		    try {
                prop = new Props();
                String filePath = Thread.currentThread().getContextClassLoader().getResource("") + propFilePath;
                System.out.println(filePath);
                filePath = filePath.replaceAll("file:/", "");
                filePath = filePath.replaceAll("%20", " ");
                System.out.println(filePath);
                prop.load(new File(filePath));
                props.put(propFilePath, prop);
            } catch (IOException e) {
		        e.printStackTrace();
            }
		}
		return prop.getValue(key);
	}

	/**
	 * 设置properties文件参数
	 * 
	 * @param key
	 * @param propFile
	 *            propFile文件名 如system.properties
	 */
	public static void setValue(final String key, final String value, final String propFile) {
		Props prop = null;
		if (props.get(propFile) != null) {
			prop = props.get(propFile);
		} else {
			prop = new Props();
			try {
				prop.load(new File(propFile));
				props.put(propFile, prop);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		prop.setValue(key, value);
	}

	public static void main(String[] args) {
	    String str = PropsUtil.getValue("sms.account", Constant.CONFIG);
        System.out.println(str);
    }
}
