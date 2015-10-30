package kredit.web.util.transfer.utility;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import kredit.web.util.transfer.model.Email;

import org.apache.commons.io.IOUtils; 
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.zkoss.util.media.Media;


public class Common {
	
	public static double getFileSize(Media media)
	{
		double size = 0;
		
		try 
		{
			byte[] file = null;
			if(media.isBinary())
			{
				file = IOUtils.toByteArray(media.getStreamData());
			}
			else
			{
				file = IOUtils.toByteArray(media.getReaderData());
			}
			
			size = file.length;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return size;
	}
	
	public static String getFileSizeFormat(Media media)
	{
		String size_format = "";
		double size = getFileSize(media);
		DecimalFormat numFormat;
		numFormat = new DecimalFormat(".##");
		
		if(size < 1024)
		{
			size_format = size + " bytes";
		}
		
		if(size >= 1024 && size < 1048576)
		{
			double tmp = size / 1024;
			size_format = numFormat.format(tmp) + " KB";
		}
		
		if(size >= 1048576)
		{
			double tmp = size / 1048576;
			size_format = numFormat.format(tmp) + " MB";
		}
		
		return size_format;
	}
	
	//sendEmail(EmailText, subject, sendTo, AttachmentList)
	public static void sendEmail(String text, String subject, Email sendTo, List<EmailAttachment> lstAttachment)
	{
		 try {
			// Create the email message
			  MultiPartEmail email = new MultiPartEmail();
			  email.setHostName("exmail.kredit.com.kh");
			  email.setSmtpPort(465);
			  email.setAuthenticator(new DefaultAuthenticator("noreply-it", "angryit"));
			  email.setSSLOnConnect(true);
			  email.addTo(sendTo.getEmail(), sendTo.getName());
			  email.setFrom("noreply-it@kredit.com.kh", "noreply-it@kredit.com.kh");
			  email.setSubject(subject);
			  email.setMsg(text);
			  
			  if(lstAttachment.size() > 0)
			  {
				  for(int i = 0; i < lstAttachment.size(); i++)
				  {
					  email.attach(lstAttachment.get(i));
				  }
			  }
			  
			  email.send();
			  
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	public static void testEmail(String text, String subject, List<Email> sendToLst, List<EmailAttachment> lstAttachment)
	{
		try {
			for(int j = 0; j < sendToLst.size(); j++)
			{
				MultiPartEmail email = new MultiPartEmail();
				email.setHostName("smtp.gmail.com");
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator("sumputhi@gmail.com", "09031993"));
				email.setSSLOnConnect(true);
				email.addTo(sendToLst.get(j).getEmail(), sendToLst.get(j).getName());
				email.setFrom("sumputhi@gmail.com", "Me");
				email.setSubject(subject);
				email.setMsg(text);
				  
				if(lstAttachment.size() > 0)
				{
					for(int i = 0; i < lstAttachment.size(); i++)
					{
						email.attach(lstAttachment.get(i));
					}
				}
				
				email.send();
			}
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

}
