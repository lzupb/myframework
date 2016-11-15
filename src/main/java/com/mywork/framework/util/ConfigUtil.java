package com.mywork.framework.util;

import com.typesafe.config.Config;

public class ConfigUtil {

	public static String get(String key) {
		return SpringContextUtil.getBeanByType(Config.class).getString(key);
	}

	public static void main(String[] args) {
		System.out.println(get("app.bpm.url"));
	}

}
