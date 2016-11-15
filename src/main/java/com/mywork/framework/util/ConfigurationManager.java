package com.mywork.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置管理器
 * 
 */
public class ConfigurationManager {

    static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

    /**
     * property文件
     */
    private static Properties prop = null;

    /**
     * 文件名
     */
    private static final String CONFIG_FILE = "application.properties";

    /**
     * 加载文件
     */
    static {
        prop = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResource(CONFIG_FILE).openStream();
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(CONFIG_FILE + " is not found");
        } catch (IOException e) {
            throw new RuntimeException(CONFIG_FILE + " is not found");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 根据key获取value
     * 
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        String propertyValue = prop.getProperty(key);
        logger.trace("ConfigurationManager key:[" + key + "] value:[" + propertyValue + "]");
        return propertyValue;
    }

}
