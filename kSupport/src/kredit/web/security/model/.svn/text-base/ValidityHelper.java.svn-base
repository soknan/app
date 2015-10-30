package kredit.web.security.model;

import java.util.Date;

import kredit.web.security.model.facade.SecurityFacade;

public class ValidityHelper {
	
	Boolean exist = false;
	Integer user_as;
	String homeBranch;
	String type;
	String validityBrDes;
	Date from;
	Date to;
	
	public Boolean getExist() {
		return exist;
	}
	
	public void setExist(Boolean exist) {
		this.exist = exist;
	}
	
	public Integer getUser_as() {
		return user_as;
	}
	
	public void setUser_as(Integer user_as) {
		this.user_as = user_as;
	}

	public String getHomeBranch() {
		return homeBranch;
	}

	public void setHomeBranch(String homeBranch) {
		this.homeBranch = homeBranch;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidityBrDes() {
		if(homeBranch != null)
		{
			validityBrDes = getType() + ": " + getHomeBranch() + " - " + SecurityFacade.getBranch(getHomeBranch()).getDescription();
		}
		else
		{
			validityBrDes = "";
		}
		return validityBrDes;
	}

	public void setValidityBrDes(String validityBrDes) {
		this.validityBrDes = validityBrDes;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}
}
