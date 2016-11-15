package com.mywork.framework.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


@Configuration
@ComponentScan(
        basePackageClasses = {DBConfig.class }
)
@Import({ DBConfig.class })
public class RootConfig {

	@Bean
	public Config getConfig() {
		return ConfigFactory.load("app.conf");
	}

}
