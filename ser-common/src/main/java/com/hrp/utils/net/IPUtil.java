/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.hrp.utils.net;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.hrp.utils.lang.StringUtil;

import jodd.io.NetUtil;

public final class IPUtil {
	private IPUtil() {
		throw new Error("工具类不能实例化！");
	}

	public static String getIpByHostName(String hostName) {
		return NetUtil.resolveIpAddress(hostName);
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtil.isNullOrBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		} else {
			ip = request.getHeader("X-Forwarded-For");
			if (!StringUtil.isNullOrBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
				int index = ip.indexOf(44);
				return index != -1 ? ip.substring(0, index) : ip;
			} else {
				return request.getRemoteAddr();
			}
		}
	}

	public static List<String> getLocalIpList() throws Exception {
		ArrayList ips = new ArrayList();
		String hostName = "";
		InetAddress addr = InetAddress.getLocalHost();
		hostName = addr.getHostName();
		if (hostName.length() > 0) {
			InetAddress[] addrs = InetAddress.getAllByName(hostName);
			int length = addrs.length;
			if (length > 0) {
				for (int i = 0; i < length; ++i) {
					ips.add(addrs[i].getHostAddress());
				}
			}
		}

		return ips;
	}
	
}