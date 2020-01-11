package com.isapsw.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	/*
	 * Class for reading data from application.properties file
	 */
	@Autowired
	private Environment env;

	/*
	 * Logic for mail sending
	 */
	@Async
	public void sendNotification(String email, String subject, String text) throws MailException, InterruptedException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject(subject);
		mail.setText("Poštovani, " + "\n\n" + text);
		javaMailSender.send(mail);
	}

	@Async
	public void sendNotificationAsync(String email, String text) throws MailException, InterruptedException {

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Registration request");
		mail.setText(text);

		javaMailSender.send(mail);
	}

}
