package com.slimsmart.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.slimsmart.common.util.string.StringUtil;

public class IpUtil {
	public static String getLocalHostIP(){
		StringBuffer ipstr = new StringBuffer();
		try {
			Enumeration<?> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				if (netInterface.isUp() && !netInterface.isLoopback()) {
					Enumeration<?> addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = (InetAddress) addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address) {
							ipstr.append(ip.getHostAddress());
							break;
						}
					}
				}
				if(StringUtil.isNotBlank(ipstr)){
					break;
				}
			}
		} catch (SocketException e) {
		}
		return StringUtil.isBlank(ipstr) ? "unknown" : ipstr.toString();
	}
}
