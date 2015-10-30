package org.k.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.model.Domain;
import kredit.web.core.util.Common;
import kredit.web.security.model.ValidityHelper;

@Entity
@Table(name = "SYS_USER")
public class User extends Domain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer empID;
	@Column(name = "BRANCH_NAME")
	String brName;
	@Transient
	String homeBranch;
	@Transient
	ValidityHelper validity;
	String username;
	String fullName;
	String pwd;
	int securityNo;
	String email;
	String phone;
	String status;

	/**
	 * @return the empID
	 */
	public Integer getEmpID() {
		return empID;
	}

	/**
	 * @param empID
	 *            the empID to set
	 */
	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	/**
	 * @return the brName
	 */
	public String getBrName() {
		if (brName == null)
			brName = "";
		return brName;
	}

	/**
	 * @param brName
	 *            the brName to set
	 */
	public void setBrName(String brName) {
		this.brName = brName;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd
	 *            the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getHomeBranch() {
		if(homeBranch == null)
		{
			return super.getBrCd();
		}
		
		return homeBranch;
	}
	
	public void setHomeBranch(String homeBranch)
	{
		this.homeBranch = homeBranch;
	}

	public String getBrDesc() {
		return super.getBrCd() + " - " + getBrName();
	}

	/**
	 * @return the securityNo
	 */
	public int getSecurityNo() {
		return securityNo;
	}

	/**
	 * @param securityNo
	 *            the securityNo to set
	 */
	public void setSecurityNo(int securityNo) {
		this.securityNo = securityNo;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		if (fullName == null)
			fullName = "";
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(super.getId());
		strBuilder.append("|");
		strBuilder.append(super.getBrCd());
		strBuilder.append("|");
		strBuilder.append(username);
		strBuilder.append("|");
		strBuilder.append(fullName);
		strBuilder.append("|");
		strBuilder.append(securityNo);
		strBuilder.append("|");
		strBuilder.append(pwd);

		return strBuilder.toString();
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		if (phone == null)
			phone = "";
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public ValidityHelper getValidity() {
		if(validity == null)
		{
			validity = Common.varifyValidity();
		}
		
		return validity;
	}

	public void setValidity(ValidityHelper validity) {
		this.validity = validity;
	}
}
