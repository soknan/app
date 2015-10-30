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
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.zkoss.zk.ui.Sessions;

/**
 * @author vathenan
 *
 */
public class EmailHelper {
	
	public static String sendEmail(String to, String subject, String content){
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
	    //Session.getDefaultInstance(props);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));
	         message.addRecipient(Message.RecipientType.BCC,
                   new InternetAddress("sovathena_neth@kredit.com.kh"));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Send the actual HTML message, as big as you like
	         message.setContent(content,
	                            "text/html;charset=UTF-8" );

	         // Send message
	         Transport.send(message);
	         
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	         result = "failed to send email to " + to; 
	      }
	      return result;
	}
	
	public static String sendEmail(String to, String bcc, String subject, String content){
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
	    //Session.getDefaultInstance(props);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));
	         message.addRecipient(Message.RecipientType.BCC,
                   new InternetAddress(bcc));
	         message.addRecipient(Message.RecipientType.BCC,
	                   new InternetAddress("sovathena_neth@kredit.com.kh"));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Send the actual HTML message, as big as you like
	         message.setContent(content,
	                            "text/html;charset=UTF-8" );

	         // Send message
	         Transport.send(message);
	         
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	         result = "failed to send email to " + to; 
	      }
	      return result;
	}
	
	public static String readFileTemplate(String filePath){
		StringBuilder builder = new StringBuilder();
		try {
			String path = Sessions.getCurrent().getWebApp().getRealPath(filePath);
			BufferedReader in =
				    new BufferedReader(
				        new InputStreamReader(new FileInputStream(path), "UTF-8"));
			try {
		    	String str;
		    	
				while ((str = in.readLine()) != null) {
				    builder.append(str);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return builder.toString();
	}
}
