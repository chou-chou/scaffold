package com.hrp.utils.math;

import java.util.UUID;

/**
 * 工具类-》基础工具类-》uuid工具类
 * <p>
 * [依赖 jodd.jar]
 * </p>
 */
public final class UUIDUtil {
	private UUIDUtil() {
		throw new Error("工具类不能实例化！");
	}

	/**
	 * get uuid by JUG
	 * 
	 * @return String 通过jug生成的uuid
	 */
	// public String getUUIDByJUG() {
	// return StringUtils.remove(UUIDGenerator.getInstance().generateRandomBasedUUID().toString(), '-');
	// }
}
