package kredit.web.util.transfer.utility;

import java.util.List;

import kredit.web.util.transfer.model.Email;

import org.apache.commons.mail.EmailAttachment;


public class EmailThread extends Thread{
	
	private String text;
	private String subject;
	private List<Email> sendToLst;
	private List<EmailAttachment> lstAttachment;
	
	public EmailThread(String text, String subject, List<Email> sendToLst, List<EmailAttachment> lstAttachment){
		this.text = text;
		this.subject = subject;
		this.sendToLst = sendToLst;
		this.lstAttachment = lstAttachment;
	}
	
	
	@Override
	public void run(){
		Common.testEmail(text, subject, sendToLst, lstAttachment);
	}
	
}
