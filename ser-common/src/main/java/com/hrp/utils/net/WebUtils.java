package com.hrp.utils.net;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web层相关的实用工具类
 */
public class WebUtils {
	
	private final static File wordfilter = new File("C:/wordfilter.txt");
	private static List<String> words = new ArrayList<String>();
	
	/**
	 * 将请求参数封装为Map<br>
	 * request中的参数t1=1&t1=2&t2=3<br>
	 * 形成的map结构：<br>
	 * key=t1;value[0]=1,value[1]=2<br>
	 * key=t2;value[0]=3<br>
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap<String, String> getPraramsAsMap(final HttpServletRequest request) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			hashMap.put(key, value);
		}
		return hashMap;
	}
	
	/**
	 * @Title:			writeToBrowser
	 * @Description:	敏感字处理
	 * @param content
	 * @param response
	 * @throws Exception
	 */
	public static void writeToBrowser(String content, final HttpServletResponse response) {
		
	}
	
}
