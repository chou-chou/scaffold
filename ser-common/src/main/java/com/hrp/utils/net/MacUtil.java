package com.hrp.utils.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

/** 
 * @ClassName	MacUtil.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月17日 下午6:57:04
 * @Version 	V1.0    
 */
public class MacUtil {
	
	private static final int K = 255;

	private MacUtil() {
		throw new Error("工具类不能实例化！");
	}

	public static String getMACAddress(InetAddress ia) throws Exception {
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < mac.length; ++i) {
			if (i != 0) {
				sb.append("-");
			}

			String s = Integer.toHexString(mac[i] & 255);
			if (s.length() == 1) {
				sb.append(0 + s);
			} else {
				sb.append(s);
			}
		}

		return sb.toString().toUpperCase();
	}

	public static List<String> getAllMACAddress() throws Exception {
		ArrayList macs = new ArrayList();
		String hostName = "";
		InetAddress addr = InetAddress.getLocalHost();
		hostName = addr.getHostName();
		if (hostName.length() > 0) {
			InetAddress[] addrs = InetAddress.getAllByName(hostName);
			int length = addrs.length;
			if (length > 0) {
				for (int i = 0; i < length; ++i) {
					macs.add(getMACAddress(addrs[i]));
				}
			}
		}

		return macs;
	}

}
 