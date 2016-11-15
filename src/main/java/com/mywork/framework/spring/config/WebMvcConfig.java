package com.mywork.framework.spring.config;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mywork.framework.spring.helper.CustomObjectMapper;
import com.mywork.framework.spring.helper.CustomerExceptionHandler;
import com.mywork.framework.spring.helper.JqSortHandlerMethodArgumentResolver;
import com.typesafe.config.Config;

@Configuration
@ComponentScan(basePackageClasses = {

}, includeFilters = @ComponentScan.Filter(classes = { Controller.class }))
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	Config config;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	/**
	 * ContentNegotiationConfigurer 配置
	 * 
	 * @param configurer
	 */
	@Override
	public void configureContentNegotiation(
			ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.TEXT_HTML);
		Map<String, MediaType> mediaTypes = Maps.newHashMap();
		mediaTypes.put("html", MediaType.TEXT_HTML);
		mediaTypes.put("json", MediaType.APPLICATION_JSON);
		mediaTypes.put("xml", MediaType.APPLICATION_XML);
		configurer.mediaTypes(mediaTypes);
		configurer.parameterName("format");
		configurer.favorPathExtension(true);
		configurer.favorParameter(true);
		configurer.useJaf(false);
	}

	/**
	 * 视图解析器
	 * 
	 * @return ContentNegotiatingViewResolver
	 */
	@Bean
	public ContentNegotiatingViewResolver setUpContentNegotiatingViewResolver() {
		ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
		List<View> defaultViews = Lists.newArrayList();
		defaultViews.add(getMappingJackson2JsonView());
		viewResolver.setDefaultViews(defaultViews);
		List<ViewResolver> resolvers = Lists.newArrayList();
		resolvers.add(new InternalResourceViewResolver("/views/", ".jsp"));
		viewResolver.setViewResolvers(resolvers);
		return viewResolver;
	}

	/**
	 * MappingJackson2JsonView
	 * 
	 * @return MappingJackson2JsonView
	 */
	public MappingJackson2JsonView getMappingJackson2JsonView() {
		MappingJackson2JsonView jackson2JsonView = new MappingJackson2JsonView();
		// jackson2JsonView.setObjectMapper(new CustomObjectMapper());
		return jackson2JsonView;
	}

	@Override
	public void configureHandlerExceptionResolvers(
			List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new CustomerExceptionHandler());
	}

	/**
	 * 文件上传
	 * 
	 * @return CommonsMultipartResolver
	 */
	@Bean
	public MultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	/**
	 * Http消息转换器
	 * 
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		// json convert
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(new CustomObjectMapper());
		converters.add(converter);
		converters.add(new ByteArrayHttpMessageConverter());
		// date convert

	}

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {

		JqSortHandlerMethodArgumentResolver sortResolver = new JqSortHandlerMethodArgumentResolver();
		sortResolver.setSortParameter("sidx"); // 设置排序参数名称
		sortResolver.setDirectParameterName("sord");

		PageableHandlerMethodArgumentResolver pageResolver = new PageableHandlerMethodArgumentResolver(
				sortResolver);
		pageResolver.setPageParameterName("page");
		pageResolver.setSizeParameterName("rows");
		pageResolver.setOneIndexedParameters(true);
		argumentResolvers.add(pageResolver);
	}

	/**
	 * 注册拦截器
	 * 
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		OpenEntityManagerInViewInterceptor interceptor = new OpenEntityManagerInViewInterceptor();
		interceptor.setEntityManagerFactory(entityManagerFactory);
		registry.addWebRequestInterceptor(interceptor);
	}

	/**
	 * 常见的错误页面
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error/500").setViewName("/error/500");
		registry.addViewController("/error/403").setViewName("/error/403");
		registry.addViewController("/error/401").setViewName("/error/403");
		registry.addViewController("/error/404").setViewName("/error/404");
	}

	/**
	 * 注册静态资源
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/static/");
		registry.addResourceHandler("/favicon.ico").addResourceLocations(
				"/images/favicon.ico");
	}

}
