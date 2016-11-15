package com.mywork.framework.spring;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.mywork.framework.spring.config.RootConfig;
import com.mywork.framework.spring.config.WebMvcConfig;

@Order(2)
public class ApplicationInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {	

	@Override
	protected Class<?>[] getRootConfigClasses() {		
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {		
		return new Class[] {WebMvcConfig.class};
	}

	@Override
	protected String[] getServletMappings() {		
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[] {encodingFilter};
	}

	
}
