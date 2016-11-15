package com.mywork.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 纯文本邮件服务类.
 */
public class SimpleMailService {
	private static Logger logger = LoggerFactory.getLogger(SimpleMailService.class);

	private JavaMailSender mailSender;
	
	public void sendTxtMail(String from,String to,String subject,String content) {
		try {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from);
		String[] receives = to.split(",");
		if (receives != null && receives.length > 1) {
			msg.setTo(receives);
		}else {
			msg.setTo(to);
		}
		
		msg.setSubject(subject);	
		msg.setText(content);
		
		mailSender.send(msg);
		if (logger.isInfoEnabled()) {
			logger.info("纯文本邮件已发送至{}", to);
		}
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
}
