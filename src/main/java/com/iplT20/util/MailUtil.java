package com.iplT20.util;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	public static void sendEmail(String toAddr, String mailContent, String mailSubj){
		
	     String to = toAddr;
	     String from = "ipladmin@ipl2015.com";
	     String host = "localhost";

	     Properties props = new Properties();

	     props.put("mail.smtp.host", host);
	     //props.put("mail.debug", "true");
	     Session session = Session.getInstance(props);

	     try {
	         Message msg = new MimeMessage(session);

	         msg.setFrom(new InternetAddress(from));
	         InternetAddress[] address = {new InternetAddress(to)};
	         msg.setRecipients(Message.RecipientType.TO, address);
	         msg.setSubject(mailSubj);
	         msg.setSentDate(new Date());

	         msg.setText(mailContent);
	         //System.out.println("************************** GOING TO SEND EMAIL*****************");
	         //Transport.send(msg);
	         //System.out.println("************************** EMAIL QUEUED SUCCESSFULY*****************");
	         
	     }
	     catch (MessagingException mex) {
	         mex.printStackTrace();
	     }

		
	}

}
