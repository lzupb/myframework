package com.mywork.framework.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextUtil.context = applicationContext;
	}

	public static <T> T getBeanByType(Class<T> classtype) {
		return context.getBean(classtype);
	}

}
