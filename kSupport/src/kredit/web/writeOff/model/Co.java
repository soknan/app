package kredit.web.writeOff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.writeOff.model.facade.WriteOffFacade;

@Entity
@Table(name="STTM_CUSTOMER")
public class Co {
	
	String fullName;
	String shortName;
	
	@Column(name="LOCAL_BRANCH", length=3)
	String branchCode;
	String shortName2;
	
	@Transient
	String branchName;
	
	@Transient
	int co_id;
	
	@Transient
	String cus_no;
	
	@Transient
	String sex;
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getShortName() {
		return shortName;
	}
	
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getBranchCode() {
		return branchCode;
	}
	
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getShortName2() {
		return shortName2;
	}
	
	public void setShortName2(String shortName2) {
		this.shortName2 = shortName2;
	}

	public String getBranchName() {
		if(!(branchCode == null))
			branchName = WriteOffFacade.getBranchName(branchCode);
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getCo_id() {
		if(!(shortName == null || shortName.trim().equals("")))
		{
			co_id = Integer.parseInt(shortName.replaceAll("\\D+", ""));
		}
		return co_id;
	}

	public void setCo_id(int co_id) {
		this.co_id = co_id;
	}

	public String getCus_no() {
		return cus_no;
	}

	public void setCus_no(String cus_no) {
		this.cus_no = cus_no;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
