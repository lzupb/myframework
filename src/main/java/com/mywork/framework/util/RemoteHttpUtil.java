/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.mywork.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;
import com.google.gson.JsonObject;

/**
 * 封装了操作远程http的操作工具类.
 * 
 * @author pengbo01
 */
public class RemoteHttpUtil {

    private static final int TIMEOUT_SECONDS = 10;

    private static final int POOL_SIZE = 200;

    private static Logger logger = LoggerFactory.getLogger(RemoteHttpUtil.class);

    private static CloseableHttpClient httpClient;

    // 创建包含connection pool与超时设置的client
    static {
        RequestConfig requestConfig =
                RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
                        .setConnectTimeout(TIMEOUT_SECONDS * 1000).build();

        httpClient =
                HttpClientBuilder.create().setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
                        .setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 封装了发送json格式的post请求操作。
     * 
     * @throws IOException
     * @throws
     * 
     */
    public static String fetchJsonHttpResponse(String contentUrl, Map<String, String> headerMap, JsonObject bodyJson)
            throws IOException {
        Executor executor = Executor.newInstance(httpClient);
        Request request = Request.Post(contentUrl);
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> m : headerMap.entrySet()) {
                request.addHeader(m.getKey(), m.getValue());
            }
        }
        if (bodyJson != null) {
            request.bodyString(bodyJson.toString(), ContentType.APPLICATION_JSON);
        }
        return executor.execute(request).returnContent().asString();
    }

    /**
     * 发送 Post请求
     * @return
     */
    public static String doPost(String contentUrl, Map<String, String> headerMap, String jsonBody) {
        String result = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(contentUrl);
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(TIMEOUT_SECONDS * 1000).setConnectTimeout(TIMEOUT_SECONDS * 1000)
                .setSocketTimeout(TIMEOUT_SECONDS * 1000).build();
        post.setConfig(config);

        try {
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> m : headerMap.entrySet()) {
                    post.setHeader(m.getKey(), m.getValue());
                }
            }

            if (jsonBody != null) {
                StringEntity entity = new StringEntity(jsonBody, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                post.setEntity(entity);
            }
            long start = System.currentTimeMillis();
            response = httpClient.execute(post);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            logger.info("url = " + contentUrl +  " request spend time = " + (System.currentTimeMillis() - start));
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 封装了发送json格式的post请求操作。
     *
     * @throws IOException
     *
     */
    public static String fetchJsonHttpResponse(String contentUrl, Map<String, String> headerMap, String jsonBody)
            throws IOException {
        Executor executor = Executor.newInstance(httpClient);
        Request request = Request.Post(contentUrl);
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> m : headerMap.entrySet()) {
                request.addHeader(m.getKey(), m.getValue());
            }
        }
        if (jsonBody != null) {
            request.bodyString(jsonBody, ContentType.APPLICATION_JSON);
        }
        long start = System.currentTimeMillis();
        String response = executor.execute(request).returnContent().asString();
        logger.info("url = " + contentUrl +  " request spend time = " + (System.currentTimeMillis() - start));
        return response;
    }

    /**
     * 封装了get与post的简单请求操作，参数只包括字符串。
     */
    public static String fetchSimpleHttpResponse(String method, String contentUrl, Map<String, String> headerMap,
            Map<String, String> bodyMap) throws IOException {
        Executor executor = Executor.newInstance(httpClient);
        if (HttpGet.METHOD_NAME.equalsIgnoreCase(method)) {
        	String result = contentUrl;
        	StringBuilder sb = new StringBuilder();
        	sb.append(contentUrl);
        	if (bodyMap != null && !bodyMap.isEmpty()) {
        		if (contentUrl.indexOf("?") > 0) {
            		sb.append("&");
            	} else {
            		sb.append("?");
            	}        		
        		result = Joiner.on("&").appendTo(sb, bodyMap.entrySet()).toString();
        	}       

            return executor.execute(Request.Get(result)).returnContent().asString();
        }
        if (HttpPost.METHOD_NAME.equalsIgnoreCase(method)) {
            Request request = Request.Post(contentUrl);
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> m : headerMap.entrySet()) {
                    request.addHeader(m.getKey(), m.getValue());
                }
            }
            if (bodyMap != null && !bodyMap.isEmpty()) {
                Form form = Form.form();
                for (Map.Entry<String, String> m : bodyMap.entrySet()) {
                    form.add(m.getKey(), m.getValue());
                }
                request.bodyForm(form.build());
            }
            return executor.execute(request).returnContent().asString();
        }
        return null;

    }

    /**
     * 封装了MultipartHttp请求操作，参数可以包含任意类型，需要构造ContentBody map。
     */
    public static String fetchMultipartHttpResponse(String contentUrl, Map<String, String> headerMap,
            Map<String, ContentBody> bodyMap) throws IOException {
        try {
            HttpPost httpPost = new HttpPost(contentUrl);
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> m : headerMap.entrySet()) {
                    httpPost.addHeader(m.getKey(), m.getValue());
                }
            }
            if (bodyMap != null && !bodyMap.isEmpty()) {
                for (Map.Entry<String, ContentBody> m : bodyMap.entrySet()) {
                    multipartEntityBuilder.addPart(m.getKey(), m.getValue());
                }
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity resEntity = response.getEntity();
                String resStr = IOUtils.toString(resEntity.getContent());
                return resStr;
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                response.close();
            }

        } finally {
            httpClient.close();
        }
        return null;

    }

    public static byte[] getImageByUrl(String imageUrl) throws ClientProtocolException, IOException {
        if (imageUrl != null) {
            byte[] resultBytes =
                    Request.Get(imageUrl).connectTimeout(TIMEOUT_SECONDS * 1000).socketTimeout(TIMEOUT_SECONDS * 1000)
                            .execute().returnContent().asBytes();
            return resultBytes;
        }
        return null;

    }

    /**
     * 演示使用多线程安全且带连接池的Apache HttpClient.
     */

    private void fetchContentByApacheHttpClient(HttpServletResponse response, String contentUrl) throws IOException {
        // 获取内容
        HttpGet httpGet = new HttpGet(contentUrl);
        CloseableHttpResponse remoteResponse = httpClient.execute(httpGet);
        try {
            // 判断返回值
            int statusCode = remoteResponse.getStatusLine().getStatusCode();
            if (statusCode >= 400) {
                response.sendError(statusCode, "fetch image error from " + contentUrl);
                return;
            }

            HttpEntity entity = remoteResponse.getEntity();

            // 设置Header
            response.setContentType(entity.getContentType().getValue());
            if (entity.getContentLength() > 0) {
                response.setContentLength((int) entity.getContentLength());
            }
            // 输出内容
            InputStream input = entity.getContent();
            OutputStream output = response.getOutputStream();
            // 基于byte数组读取InputStream并直接写入OutputStream, 数组默认大小为4k.
            IOUtils.copy(input, output);
            output.flush();
        } finally {
            remoteResponse.close();
        }
    }

    /**
     * 演示使用 JDK获取远程静态内容并进行展示
     */

    private void fetchContentByJDKConnection(HttpServletResponse response, String contentUrl) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(contentUrl).openConnection();
        // 设置Socket超时
        connection.setReadTimeout(TIMEOUT_SECONDS * 1000);
        try {
            connection.connect();

            // 真正发出请求
            InputStream input;
            try {
                input = connection.getInputStream();
            } catch (FileNotFoundException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, contentUrl + " is not found.");
                return;
            }

            // 设置Header
            response.setContentType(connection.getContentType());
            if (connection.getContentLength() > 0) {
                response.setContentLength(connection.getContentLength());
            }

            // 输出内容
            OutputStream output = response.getOutputStream();
            try {
                // 基于byte数组读取InputStream并直接写入OutputStream, 数组默认大小为4k.
                IOUtils.copy(input, output);
                output.flush();
            } finally {
                // 保证InputStream的关闭.
                IOUtils.closeQuietly(input);
            }
        } finally {
            connection.disconnect();
        }
    }

    private void destroyApacheHttpClient() {
        try {
            httpClient.close();
        } catch (IOException e) {
            logger.error("httpclient close fail", e);
        }
    }

    /**
     * 演示FluentAPI。
     */
    @SuppressWarnings("unused")
    public void fluentAPIDemo(String contentUrl) throws IOException {
        try {
            // demo1: 获取文字 , 使用默认连接池(200 total/100 per route), returnContent()会自动获取全部内容后关闭inputstream。
            String resultString = Request.Get(contentUrl).execute().returnContent().asString();

            // demo2: 获取图片, 增加超时设定。
            byte[] resultBytes =
                    Request.Get(contentUrl).connectTimeout(TIMEOUT_SECONDS * 1000)
                            .socketTimeout(TIMEOUT_SECONDS * 1000).execute().returnContent().asBytes();

            // demo3: 获取图片，使用之前设置好了的自定义连接池与超时的httpClient
            Executor executor = Executor.newInstance(httpClient);
            String resultString2 = executor.execute(Request.Get(contentUrl)).returnContent().asString();
        } catch (HttpResponseException e) {
            logger.error("Status code:" + e.getStatusCode(), e);
        }
    }

    public static void main(String[] args) {
        try {

            // 测试json请求
            {
                JsonObject bodyJson = new JsonObject();
                bodyJson.addProperty("uid", "testusername");
                bodyJson.addProperty("pic_file", "rcGysFG2xqLaO8tlvW4rFVmVqlnx+4qGewYs8a+enmoZ");
                String getResponse =
                        RemoteHttpUtil.fetchJsonHttpResponse("http://10.48.26.196:8080/face/json/postjson", null,
                                bodyJson);
                System.out.print(getResponse);
            }

            // // 测试get请求
            // {
            // String getResponse =
            // RemoteHttpUtil.fetchSimpleHttpResponse("get", "http://10.48.26.196:8080/face/json/getData",
            // null, null);
            // System.out.print(getResponse);
            // }
            //
            // // 测试post请求
            // {
            // Map<String, String> bodyMap = new HashMap<String, String>();
            // bodyMap.put("aaa", "goodgod");
            // String postResponse =
            // RemoteHttpUtil.fetchSimpleHttpResponse("post", "http://10.48.26.196:8080/face/json/postData",
            // null, bodyMap);
            // System.out.print(postResponse);
            // }
            //
            // // 测试带多文件上传的form请求
            // {
            // Map<String, ContentBody> bodyMap = new HashMap<String, ContentBody>();
            // bodyMap.put("name", new StringBody("heiheiheihei", ContentType.TEXT_PLAIN));
            // bodyMap.put("file2", new FileBody(new File("D:\\temp\\test.txt")));
            // bodyMap.put("file3", new FileBody(new File("D:\\temp\\test2.txt")));
            // String multiHttpResponse =
            // RemoteHttpUtil.fetchMultipartHttpResponse("http://10.48.26.196:8080/face/file/uploadMultifile",
            // null, bodyMap);
            // System.out.print(multiHttpResponse);
            // }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
