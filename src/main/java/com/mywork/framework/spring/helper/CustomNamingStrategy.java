package com.mywork.framework.spring.helper;

import org.hibernate.cfg.ImprovedNamingStrategy;

public class CustomNamingStrategy extends ImprovedNamingStrategy {

	private static final long serialVersionUID = -468244715779028822L;

	@Override
	public String classToTableName(String className) {
		return "MY_" + super.classToTableName(className);
	}

}
