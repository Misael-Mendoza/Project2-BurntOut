package com.jobportal.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ApplicationAlertMail {

	public static void sendApplicationAlert(String recipient, String jobName, String jobId) throws Exception {
		
		System.out.println("attempting to send app alert email");
		
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myEmailAccount = "burntoutnotifications@gmail.com";
		String password = "burningup22";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmailAccount, password);
			}
		});
		
		Message message = prepareMessage(session, myEmailAccount, recipient, jobId, jobName);
		
		Transport.send(message);
		System.out.println("Message successfully sent");
		
	}

	private static Message prepareMessage(Session session, String myEmailAccount, String recipient, String jobId, String jobName) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myEmailAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Someone Has Applied To Your Job Posting!");
			String htmlContent = "<h2>You Have An Applicant!</h2><br/><h3>Your Job: "+jobName+" has a new applicant</h3><br/>"
					+ "<a href='http://localhost:4200/jobs/review-applicants/'"+jobId+"'>View Your Applicants</a>";
			message.setContent(htmlContent, "text/html");
			return message;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
