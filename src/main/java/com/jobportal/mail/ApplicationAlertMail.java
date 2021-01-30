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

	/**
	 * Method to send an email notification to a user when another user has applied to that job
	 * @param recipient - receiver email address
	 * @param jobName - title of the job posting
	 * @param jobId - ID of the job posting
	 * @throws Exception - multiple exceptions if Java Mail gets mad
	 */
	public static void sendApplicationAlert(String recipient, String jobName, String jobId) throws Exception {
		
		System.out.println("attempting to send app alert email");
		
		Properties properties = new Properties();
		
		//settings for JavaMail to log into the gmail account
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		//gmail account credentials
		String myEmailAccount = "burntoutnotifications@gmail.com";
		String password = "burningup22";
		
		//create the session in gmail
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

	/**
	 * Method to create the email message itself
	 * @param session - session inside gmail
	 * @param myEmailAccount - sender email address
	 * @param recipient - receiver email address
	 * @param jobId - job posting id
	 * @param jobName - job posting title
	 * @return - the created email message
	 */
	private static Message prepareMessage(Session session, String myEmailAccount, String recipient, String jobId, String jobName) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myEmailAccount));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			message.setSubject("Someone Has Applied To Your Job Posting!");
			String htmlContent = "<h2>You Have An Applicant!</h2><br/><h3>Your Job: "+jobName+" has a new applicant</h3><br/>"
					+ "<a href='http://localhost:4200/jobs/review-applicants/"+jobId+"'>View Your Applicants</a>";
			message.setContent(htmlContent, "text/html");
			return message;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
