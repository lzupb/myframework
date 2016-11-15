package com.mywork.framework.spring.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.mywork.framework.util.SimpleMailService;
import com.mywork.framework.util.WebUtil;


public class CustomerExceptionHandler implements HandlerExceptionResolver {
	
	private SimpleMailService mailService;
	
	private String emailFrom;
	
	private String emailReceive;
	
	private String emailFlag;

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public void setEmailReceive(String emailReceive) {
		this.emailReceive = emailReceive;
	}

	public void setMailService(SimpleMailService mailService) {
		this.mailService = mailService;
	}

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ex.printStackTrace();
		String clientIP = WebUtil.getClientIP(request);		
		String message = "system exception";
		sendErrorMail(ex, clientIP);
		
		/* 在HTTP头中加错误头 */
		// 将异常信息已json数组的方式写到回复的头中
		response.setStatus(HttpURLConnection.HTTP_INTERNAL_ERROR);
		try {
			message = URLEncoder.encode(message, "UTF-8");
			response.addHeader("ERROR_MSG", message);
		} catch (UnsupportedEncodingException e) {
			handleException(e);
		}
		return null;
	}
	
	/**
	 * 异常处理
	 * @param e
	 */
	protected void handleException(Exception e) {
		StringWriter sw = new StringWriter();  
		e.printStackTrace(new PrintWriter(sw, true));
		String str = sw.toString(); 		
	}	

	private void sendErrorMail(Exception ex, String clientIP) {
		if(StringUtils.hasText(emailFlag)&&"true".equals(emailFlag)){
			String localIP = "";
			try {
				localIP = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringWriter sw = new StringWriter();  
			ex.printStackTrace(new PrintWriter(sw, true));
			
			String exceptionString = sw.toString();
			try {
				sw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mailService.sendTxtMail(emailFrom, emailReceive, "系统发生系统异常:"+localIP, exceptionString);
		}
	}

}
