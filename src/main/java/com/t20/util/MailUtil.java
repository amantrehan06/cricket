package com.t20.util;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	public static void main(String args[]){
		MailUtil.sendEmail("amantrehan06@gmail.com", "Hii", "IPL Email");
	}
	public static void sendEmail(String toAddr, String mailContent, String mailSubj){
		final String username = "";
		final String password = "";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		
	     String to = toAddr;
	     String from = username;

	    
	     try {
	         Message msg = new MimeMessage(session);

	         msg.setFrom(new InternetAddress(from));
	         InternetAddress[] address = {new InternetAddress(to)};
	         msg.setRecipients(Message.RecipientType.TO, address);
	         msg.setSubject(mailSubj);
	         msg.setSentDate(new Date());

	         msg.setText(mailContent);
	         //System.out.println("************************** GOING TO SEND EMAIL*****************");
	        Transport.send(msg);
	        // System.out.println("************************** EMAIL QUEUED SUCCESSFULY*****************");
	         
	     }
	     catch (Exception e) {
	         e.printStackTrace();
	     }

		
	}

}
