package com.mywork.framework.spring.config;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

@Configuration
@Import({JpaConfig.class})
public class DBConfig {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(DBConfig.class);

	
	@Autowired
	private Config config;
	
	@Bean
	public DataSource dataSource() {
		Config db = config.getConfig("db");
        BasicDataSource dataSource = new BasicDataSource();
        for (Map.Entry<String, ConfigValue> entry : db.entrySet()) {
            try {
                if (StringUtils.contains(entry.getKey(), ".")) {
                    continue;
                }
                BeanUtils.setProperty(dataSource, entry.getKey(), entry.getValue().unwrapped());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("set dataSource config property={}, value={}", entry.getKey(), entry.getValue());
                }
            } catch (Exception e) {
                LOGGER.warn("set property={},value={}", entry.getKey(), entry.getValue());
            }
        }
        return dataSource;
		
	}

}
