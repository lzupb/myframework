package config;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;

import com.mywork.framework.spring.config.DBConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Import({DBConfig.class})
public class TestConfig {
    @Bean
    public Config typeSafeConfig() {
        return ConfigFactory.load("app.conf");
    }

    @Bean(name = "auditorAware")
    public AuditorAware<String> auditorAware() {
        return new AuditorAware<String>() {
            @Override
            public String getCurrentAuditor() {
                return RandomStringUtils.randomAlphabetic(10);
            }
        };
    }
}
