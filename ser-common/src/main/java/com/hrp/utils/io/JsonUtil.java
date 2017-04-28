package com.hrp.utils.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.hrp.utils.lang.StringUtil;

/**
 * 工具类-》IO处理工具类-》 json 操作工具类
 * <p>
 * [依赖 fastJson.jar]
 * </p>
 */
public class JsonUtil {

	/**
	 * 将JSON解析成map
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> json2Map(final String json) {
		return (Map<String, Object>) JSON.parse(json);
	}

	/**
	 * 将JSON解析成对象
	 */
	public static final <T> T json2Object(final String json, final Class<T> c) {
		return JSON.parseObject(json, c);
	}

	/**
	 * 将字符串包装成json数组
	 */
	public static String warpJson2ListJson(final String json) {
		String jsonStr = json;
		if (!StringUtil.isNullOrBlank(json)) {
			if (!json.startsWith("[")) {
				jsonStr = "[" + json + "]";
			}
		}
		return jsonStr;
	}

	/**
	 * 将JSON解析成对象list
	 */
	public static final <T> List<T> json2List(final String json, final Class<T> c) {
		String jsonStr = json;
		if (!StringUtil.isNullOrBlank(json)) {
			if (!json.startsWith("[")) {
				jsonStr = "[" + json + "]";
			}
			return JSON.parseArray(jsonStr, c);
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 * 将对象转换成json
	 */
	public static final String object2Json(final Object entity) {
		return JSON.toJSONString(entity);
	}

	// ////////////////////////jackson///////////////////////////////////////////////////
	// ObjectMapper 线程安全具有缓存机制，重用可显著提高效率，实际使用中可设为全局公用
	// @Getter
	// private static ObjectMapper mapper = new ObjectMapper();
	//
	// /**
	// * 将JSON解析成map
	// */
	// @SuppressWarnings("unchecked")
	// public static Map<String, Object> json2Map(final String json) {
	// Map<String, Object> map = null;
	// try {
	// map = mapper.readValue(json, Map.class);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return map;
	// }
	//
	// /**
	// * 将JSON解析成对象
	// */
	// public final Object json2Object(final String json, final Class<T> c) {
	// Object obj = null;
	// try {
	// obj = mapper.readValue(json, c);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return obj;
	// }
	//
	// /**
	// * 将字符串包装成json数组
	// */
	// public static String warpJson2ListJson(final String json) {
	// String jsonStr = "";
	// if (!Strings.isNullOrEmpty(json)) {
	// if (!json.startsWith("[")) {
	// jsonStr = "[" + json + "]";
	// }
	// }
	// return jsonStr;
	// }
	//
	// /**
	// * 将JSON解析成对象list
	// */
	// public final List<T> json2List(final String json) {
	// String jsonStr = "";
	// if (!Strings.isNullOrEmpty(json)) {
	// if (!json.startsWith("[")) {
	// jsonStr = "[" + json + "]";
	// }
	// List<T> list = null;
	// try {
	// list = mapper.readValue(jsonStr, new TypeReference<List<T>>() {});
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return list;
	// } else {
	// return null;
	// }
	// }

	// public static void main(String[] args) {
	// String s = "[{\"taskItemId\":\"\",\"naming\":\"\",\"startTime\":\"\",\"endTime\":\"\",\"weight\":\"\"}]";
	// IoJsonUtil<ProjectPlanDetailForm> u = new IoJsonUtil<ProjectPlanDetailForm>();
	// List<ProjectPlanDetailForm> m = u.json2List(s);
	//
	// }
}
