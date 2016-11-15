package com.mywork.framework.spring.helper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringEscapeUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * Json转化日期格式指定和对于json数据
 *
 * @author Ranger
 * @date 2015/9/13
 * @since 1.0
 */
public class CustomObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = -6580366269486700520L;
    private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public CustomObjectMapper() {
        setDateFormat(DATEFORMAT);
        getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
                jgen.writeString("");
            }
        });
        // 增加返回json数据时，对于html内容的编码
        SimpleModule module = new SimpleModule("HTML XSS Serializer",
                new Version(1, 0, 0, "FINAL", "com.baidu.bpit.nengli.dataprocess", "dataprocess-admin"));
        module.addSerializer(new JsonSerializer<String>() {

            @Override
            public Class<String> handledType() {
                return String.class;
            }

            @Override
            public void serialize(String value, JsonGenerator jgen, SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                if (value != null) {
                    String escapedValue = StringEscapeUtils.escapeHtml4(value);
                    jgen.writeString(escapedValue);
                }
            }
        });
        this.registerModule(module);
    }

}