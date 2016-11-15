package com.mywork.framework.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	/**
	 * 直接取得当前服务端网络设置
	 * 
	 * @return
	 */
	public static final String getServerIP() {
		String serverName = "UnkownServerIP";
		try {
			InetAddress address = InetAddress.getLocalHost();
			serverName = address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return serverName;
		}
		return serverName;
	}

	/**
	 * 根据请求Request取得服务器的主机名称
	 * 
	 * @param request
	 * @return
	 */
	public static final String getServerName(final HttpServletRequest request) {
		String serverName = request.getServerName();
		if ("127.0.0.1".equals(serverName) || "localhost".equals(serverName)) {
			try {
				InetAddress address = InetAddress.getLocalHost();
				serverName = address.getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				return serverName;
			}
		}
		return serverName;
	}

	/**
	 * 取得WEB客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIP(final HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 根据请求Request构建URL
	 * 
	 * @param request
	 * @param servletPath
	 * @return
	 */
	public static final String getAbsoluteUrl(final HttpServletRequest request,
			String servletPath) {
		String serverName = getServerName(request);
		String path = request.getContextPath() + servletPath;
		StringBuffer urlBuff = new StringBuffer(request.getScheme());
		urlBuff.append("://");
		String port = request.getServerPort() == 80 ? "" : ":"
				+ String.valueOf(request.getServerPort());
		urlBuff.append(serverName).append(port).append(path);
		return urlBuff.toString();
	}

}
