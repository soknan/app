/**
 * Authentication.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package kredit.web.kbureau.model;

import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import kredit.web.core.util.db.DbHelper;
import kredit.web.kbureau.model.report.CodeItem;

public class Authentication {

	private String appVersion;

	private int id;

	private String lastLoginDate;

	private String password;

	private String pcName;

	private String pcUsername;

	private int securityCode;

	private String subBranchCode;

	private String branchCode;

	private String username;

	private String email = "";

	private String dateCreated;

	private String status;

	private String note = "";

	String fName = "";
	String lName = "";
	String sex;

	CodeItem branch;
	CodeItem subBranch;
	CodeItem statusObj;
	CodeItem sexObj;
	
	String subBranchFull;
	
	int userType;
	
	int validity;

	public Authentication() {
	}

	public Authentication(String appVersion, Integer id, String lastLoginDate,
			String password, String pcName, String pcUsername,
			Integer securityCode, String subBranchCode, String username) {
		this.appVersion = appVersion;
		this.id = id;
		this.lastLoginDate = lastLoginDate;
		this.password = password;
		this.pcName = pcName;
		this.pcUsername = pcUsername;
		this.securityCode = securityCode;
		this.subBranchCode = subBranchCode;
		this.username = username;
	}

	/**
	 * Gets the appVersion value for this Authentication.
	 * 
	 * @return appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * Sets the appVersion value for this Authentication.
	 * 
	 * @param appVersion
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	/**
	 * Gets the id value for this Authentication.
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id value for this Authentication.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the lastLoginDate value for this Authentication.
	 * 
	 * @return lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * Sets the lastLoginDate value for this Authentication.
	 * 
	 * @param lastLoginDate
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Gets the password value for this Authentication.
	 * 
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password value for this Authentication.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the pcName value for this Authentication.
	 * 
	 * @return pcName
	 */
	public String getPcName() {
		return pcName;
	}

	/**
	 * Sets the pcName value for this Authentication.
	 * 
	 * @param pcName
	 */
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}

	/**
	 * Gets the pcUsername value for this Authentication.
	 * 
	 * @return pcUsername
	 */
	public String getPcUsername() {
		return pcUsername;
	}

	/**
	 * Sets the pcUsername value for this Authentication.
	 * 
	 * @param pcUsername
	 */
	public void setPcUsername(String pcUsername) {
		this.pcUsername = pcUsername;
	}

	/**
	 * Gets the securityCode value for this Authentication.
	 * 
	 * @return securityCode
	 */
	public int getSecurityCode() {
		return securityCode;
	}

	/**
	 * Sets the securityCode value for this Authentication.
	 * 
	 * @param securityCode
	 */
	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}

	/**
	 * Gets the subBranchCode value for this Authentication.
	 * 
	 * @return subBranchCode
	 */
	public String getSubBranchCode() {
		return subBranchCode;
	}

	/**
	 * Sets the subBranchCode value for this Authentication.
	 * 
	 * @param subBranchCode
	 */
	public void setSubBranchCode(String subBranchCode) {
		this.subBranchCode = subBranchCode;
	}

	/**
	 * Gets the username value for this Authentication.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username value for this Authentication.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		if(email == null) email = "";
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		if(status == null)
			status = "A";
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated
	 *            the dateCreated to set
	 */
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName
	 *            the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName
	 *            the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		if (sex == null)
			sex = "M";
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		if (branchCode == null)
			branchCode = "";
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the branch
	 */
	public CodeItem getBranch() {
		if (branch == null) {
			branch = new CodeItem();
			if (branchCode != null) {
				branch.setCode(branchCode);
				if(branchCode.equals("*"))
					branch.setDescription("(All branches)");
				else
					branch.setDescription(getBranchName(branchCode));
			}
		}
		return branch;
	}

	private String getBranchName(String code) {
		String name = "";
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<ScalareString> h = new BeanHandler<ScalareString>(
					ScalareString.class);
			String sql = "SELECT Branch Value FROM Branch WHERE Code = ?";
			ScalareString scalare = queryRunner.query(conn, sql, h, code);
			if (scalare != null)
				name = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {

		}
		return name;
	}

	private String getSubBranchName(String code) {
		String name = "";
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<ScalareString> h = new BeanHandler<ScalareString>(
					ScalareString.class);
			String sql = "SELECT SubBranch Value FROM SubBranch WHERE Code = ?";
			ScalareString scalare = queryRunner.query(conn, sql, h, code);
			if (scalare != null)
				name = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {

		}
		return name;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}

	/**
	 * @return the subBranch
	 */
	public CodeItem getSubBranch() {
		if (subBranch == null) {
			subBranch = new CodeItem();
			if (subBranchCode != null) {
				subBranch.setCode(subBranchCode);
				if(subBranchCode.equals("*"))
					subBranch.setDescription("(All subs)");
				else
					subBranch.setDescription(getSubBranchName(subBranchCode));
			}
		}
		return subBranch;
	}

	/**
	 * @param subBranch
	 *            the subBranch to set
	 */
	public void setSubBranch(CodeItem subBranch) {
		this.subBranch = subBranch;
	}

	/**
	 * @return the statusObj
	 */
	public CodeItem getStatusObj() {
		if (statusObj == null) {
			statusObj = new CodeItem();
			statusObj.setCode(getStatus());
			switch (getStatus()) {
			case "A":
				statusObj.setDescription("Active");
				break;

			default:
				statusObj.setDescription("Inactive");
				break;
			}
		}

		return statusObj;
	}

	/**
	 * @param statusObj
	 *            the statusObj to set
	 */
	public void setStatusObj(CodeItem statusObj) {
		this.statusObj = statusObj;
	}

	/**
	 * @return the sexObj
	 */
	public CodeItem getSexObj() {
		if (sexObj == null) {
			sexObj = new CodeItem();
			sexObj.setCode(getSex());
			switch (getSex()) {
			case "M":
				sexObj.setDescription("Male");
				break;
			default:
				sexObj.setDescription("Female");
				break;
			}
		}
		return sexObj;
	}

	/**
	 * @param sexObj
	 *            the sexObj to set
	 */
	public void setSexObj(CodeItem sexObj) {
		this.sexObj = sexObj;
	}

	/**
	 * @return the subBranchFull
	 */
	public String getSubBranchFull() {
		if(subBranchFull == null){
			if(subBranchCode != null){
				subBranchFull = subBranchCode + " " + getSubBranchName(subBranchCode);
			}
		}
		return subBranchFull;
	}

	/**
	 * @param subBranchFull the subBranchFull to set
	 */
	public void setSubBranchFull(String subBranchFull) {
		this.subBranchFull = subBranchFull;
	}

	/**
	 * @return the userType
	 */
	public int getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(int userType) {
		this.userType = userType;
	}

	/**
	 * @return the validity
	 */
	public int getValidity() {
		return validity;
	}

	/**
	 * @param validity the validity to set
	 */
	public void setValidity(int validity) {
		this.validity = validity;
	}
}
