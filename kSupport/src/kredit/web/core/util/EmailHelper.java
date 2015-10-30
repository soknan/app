/**
 * 
 */
package kredit.web.core.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Sessions;

/**
 * @author vathenan
 * 
 */
public class EmailHelper {

	static Logger logger = Logger.getLogger(EmailHelper.class.getName());
	
	public static String sendEmail(String to, String subject, String content) {
		String result = "Sent email successfully to " + to;
		// Recipient's email ID needs to be mentioned.

		// Sender's email ID needs to be mentioned
		String from = "noreply-it@kredit.com.kh";
		// Assuming you are sending email from localhost
		String host = "exmail.kredit.com.kh";

		// Get system properties
		Properties props = System.getProperties();

		// Setup mail server
		props.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noreply-it", "angryit");
			}
		});
		// Session.getDefaultInstance(props);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));
			message.addRecipient(Message.RecipientType.BCC,
					new InternetAddress("sovathena_neth@kredit.com.kh"));

			// Set Subject: header field
			message.setSubject(subject);

			// Send the actual HTML message, as big as you like
			message.setContent(content, "text/html;charset=UTF-8");

			// Send message
			Transport.send(message);

		} catch (MessagingException mex) {
			logger.error("failed to send email to " + to, mex);
			result = "failed to send email to " + to;
		}
		return result;
	}

	public static String readFileTemplate(String filePath) {
		StringBuilder builder = new StringBuilder();
		try {
			String path = Sessions.getCurrent().getWebApp()
					.getRealPath(filePath);
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "UTF-8"));
			try {
				String str;

				while ((str = in.readLine()) != null) {
					builder.append(str);
				}
			} catch (IOException e) {
				logger.error("Error reading file template.", e);
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("Error reading file template.", e1);
		} catch (FileNotFoundException e1) {
			logger.error("Error reading file template.", e1);
		}
		return builder.toString();
	}

	public static String sendEmail(List<String> lstTo, List<String> lstBcc,
			String subject, String content) {
		String result = "Sent email successfully";
		// Recipient's email ID needs to be mentioned.

		// Sender's email ID needs to be mentioned
		String from = "noreply-it@kredit.com.kh";
		// Assuming you are sending email from localhost
		String host = "exmail.kredit.com.kh";

		// Get system properties
		Properties props = System.getProperties();

		// Setup mail server
		props.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noreply-it", "angryit");
			}
		});
		// Session.getDefaultInstance(props);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			for (String to : lstTo) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			}
			for (String bcc : lstBcc) {
				message.addRecipient(Message.RecipientType.BCC,
						new InternetAddress(bcc));
			}
			// Set Subject: header field
			message.setSubject(subject);

			// Send the actual HTML message, as big as you like
			message.setContent(content, "text/html;charset=UTF-8");

			// Send message
			Transport.send(message);

		} catch (MessagingException mex) {
			mex.printStackTrace();
			result = "failed to send email to ";
		}
		return result;
	}

	public static String sendEmailRequest(List<String> lstTo,
			List<String> lstCc, List<String> lstBcc, String subject,
			String content) {
		String result = "Sent email successfully";
		// Recipient's email ID needs to be mentioned.

		// Sender's email ID needs to be mentioned
		String from = "noreply-it@kredit.com.kh";
		// Assuming you are sending email from localhost
		String host = "exmail.kredit.com.kh";

		// Get system properties
		Properties props = System.getProperties();

		// Setup mail server
		props.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noreply-it", "angryit");
			}
		});
		// Session.getDefaultInstance(props);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			for (String email : lstTo) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(email));
			}

			if (lstCc != null) {

				for (String email : lstCc) {
					message.addRecipient(Message.RecipientType.CC,
							new InternetAddress(email));
				}

			}

			if (lstBcc != null) {

				for (String email : lstBcc) {
					message.addRecipient(Message.RecipientType.BCC,
							new InternetAddress(email));
				}

			}
			// Set Subject: header field
			message.setSubject(subject);

			// Send the actual HTML message, as big as you like
			message.setContent(content, "text/html;charset=UTF-8");

			// Send message
			Transport.send(message);

		} catch (MessagingException mex) {
			logger.error("failed to send emails", mex);
			result = "failed to send emails";
		}
		return result;
	}

}
