package com.mywork.framework.util;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

/**
 * session辅助类<br>
 * 
 * 
 */
public class ThreadSessionHelper {

	public static final String CURRENT_USER_NAME = "CURRENT_USER_NAME";

	public static final String REQUEST_URI_KEY = "REQUEST_URI_KEY";

	public static final String CLIENT_IP_KEY = "CLIENT_IP_KEY";

	public static final String SERVER_IP_KEY = "SERVER_IP_KEY";

	public static final String HTTP_SESSION = "HTTP_SESSION";

	public static final String CONTEXT_PATH = "CONTEXT_PATH";

	public static void clearThreadSession() {
		ThreadSession.clear();
	}

	public static Map getAttributes() {
		return ThreadSession.getAttributes();
	}

	public static void setAttribute(String key, Object value) {
		ThreadSession.setAttribute(key, value);
	}

	public static Object getAttribute(String key) {
		return getAttributes().get(key);
	}

	static void setRequestURI(String uri) {
		ThreadSession.setAttribute(REQUEST_URI_KEY, uri);
	}

	public static String getRequestURI() {
		return (String) ThreadSession.getAttribute(REQUEST_URI_KEY);
	}

	public static String getCurrentLoginUserName() {
		if (getSession() == null)
			return null;
		return (String) getSession().getAttribute(CURRENT_USER_NAME);
	}

	static void setClientIP(String clientIP) {
		ThreadSession.setAttribute(CLIENT_IP_KEY, clientIP);
	}

	public static String getClientIP() {
		return (String) ThreadSession.getAttribute(CLIENT_IP_KEY);
	}

	static void setSession(HttpSession httpSession) {
		ThreadSession.setAttribute(HTTP_SESSION, httpSession);
	}

	public static HttpSession getSession() {
		return (HttpSession) ThreadSession.getAttribute(HTTP_SESSION);
	}

}

class ThreadSession implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 3266905893684756575L;
	private static ThreadLocal<Map> threadSessionHolder = new ThreadLocal<Map>();

	public static void clear() {
		threadSessionHolder.set(null);
	}

	public static Map getAttributes() {
		if (threadSessionHolder.get() == null) {
			threadSessionHolder.set(new ConcurrentHashMap());
		}

		return (Map) threadSessionHolder.get();
	}

	public static void setAttribute(String key, Object value) {
		Assert.notNull(key, "Only non-null key are permitted");
		Assert.notNull(value, "Only non-null value are permitted");
		getAttributes().put(key, value);
	}

	public static Object getAttribute(String key) {
		Assert.notNull(key, "Only non-null key are permitted");
		return getAttributes().get(key);
	}
}
