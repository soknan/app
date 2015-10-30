package kredit.web.core.util;

import java.util.List;

import kredit.web.core.util.EmailHelper;

public class EmailThread extends Thread{
	
	private String title;
	private List<String> lstTo;
	private List<String> lstBcc;
	private List<String> lstCc;
	private String content;
	
	public EmailThread(String title, List<String> lstTo, List<String> lstCc, List<String> lstBcc, String content){
		this.title = title;
		this.lstTo = lstTo;
		this.lstBcc = lstBcc;
		this.content = content;
		this.lstCc = lstCc;
	}
	
	
	@Override
	public void run(){
		EmailHelper.sendEmailRequest(lstTo, lstCc, lstBcc, title,
				content);
	}
	
}
