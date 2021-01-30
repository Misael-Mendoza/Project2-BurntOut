package com.jobportal.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class RecoveryEmail {
	
	/**
	 * Method to send a recovery email if a user requests one
	 * @param recipient - email address of the user requesting account recovery
	 * @param securityCode - 6 digit randomly generated code to verify a user's authenticity when they are resetting their password
	 * @throws Exception - multiple exceptions that mail throws if something has gone wrong
	 */
	public static void sendRecoveryMail(String recipient, String securityCode) throws Exception {
		
		System.out.println("attempting to send recovery email");
		
		Properties properties = new Properties();
		
		//settings necessary for JavaMail to login to the gmail account
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		//gmail account credentials
		String myEmailAccount = "burntoutrecovery@gmail.com";
		String password = "burningup21";
		
		//creates a session in gmail to send the email
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmailAccount, password);
			}
		});
		
		//creates the message itself
		Message message = prepareMessage(session, myEmailAccount, recipient, securityCode);
		
		Transport.send(message);
		System.out.println("Message successfully sent");
		
	}

	/**
	 * Method to create the message itself
	 * @param session - session in gmail created with settings and credentials
	 * @param myEmailAccount - the sender's email address
	 * @param recipient - the receiver's email address
	 * @param securityCode - code used to verify user's authenticity when they try to reset their password
	 * @return
	 */
	private static Message prepareMessage(Session session, String myEmailAccount, String recipient, String securityCode) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myEmailAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Recover Your BurntOut Account");
			String htmlContent = "<h1>Use the provided security code to reset your password</h1><br/><h3>"+securityCode+"</h3><br/><a href='http://localhost:4200/passwordreset'>Password Reset</a>";
			message.setContent(htmlContent, "text/html");
			return message;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
