/**
 * 
 */
package kredit.web.core.util.authentication.model;

import java.sql.Date;

/**
 * @author vathenan
 *
 */
public class Login {
	
	int id;
	String username;
	String password;
	String subBranchCode;
	String f_subBranchCode;
	String subBranch;
	String f_subBranch;
	String branchCode;
	String f_branchCode;
	String branch;
	String f_branch;
	Date lastLoginDate;
	String email;
	int securityCode;
	String fName;
	String lName;
	int branchId;
	int f_branchId;
	boolean adm;
	String role;
		
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the subBranchCode
	 */
	public String getSubBranchCode() {
		return subBranchCode;
	}
	/**
	 * @param subBranchCode the subBranchCode to set
	 */
	public void setSubBranchCode(String subBranchCode) {
		this.subBranchCode = subBranchCode;
	}
	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the securityCode
	 */
	public int getSecurityCode() {
		return securityCode;
	}
	/**
	 * @param securityCode the securityCode to set
	 */
	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}
	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}
	/**
	 * @param fName the fName to set
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
	 * @param lName the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}
	
	public String getFullName(){
		if(lName == null) return "";
		return lName + " " + fName;
	}
	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}
	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	/**
	 * @return the subBranch
	 */
	public String getSubBranch() {
		return subBranch;
	}
	/**
	 * @param subBranch the subBranch to set
	 */
	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}
	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * @return the branchId
	 */
	public int getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	/**
	 * @return the adm
	 */
	public boolean isAdm() {
		return adm;
	}
	/**
	 * @param adm the adm to set
	 */
	public void setAdm(boolean adm) {
		this.adm = adm;
	}
	/**
	 * @return the f_subBranchCode
	 */
	public String getF_subBranchCode() {
		return f_subBranchCode;
	}
	/**
	 * @param f_subBranchCode the f_subBranchCode to set
	 */
	public void setF_subBranchCode(String f_subBranchCode) {
		this.f_subBranchCode = f_subBranchCode;
	}
	/**
	 * @return the f_branchCode
	 */
	public String getF_branchCode() {
		return f_branchCode;
	}
	/**
	 * @param f_branchCode the f_branchCode to set
	 */
	public void setF_branchCode(String f_branchCode) {
		this.f_branchCode = f_branchCode;
	}
	/**
	 * @return the f_subBranch
	 */
	public String getF_subBranch() {
		return f_subBranch;
	}
	/**
	 * @param f_subBranch the f_subBranch to set
	 */
	public void setF_subBranch(String f_subBranch) {
		this.f_subBranch = f_subBranch;
	}
	/**
	 * @return the f_branch
	 */
	public String getF_branch() {
		return f_branch;
	}
	/**
	 * @param f_branch the f_branch to set
	 */
	public void setF_branch(String f_branch) {
		this.f_branch = f_branch;
	}
	/**
	 * @return the f_branchId
	 */
	public int getF_branchId() {
		return f_branchId;
	}
	/**
	 * @param f_branchId the f_branchId to set
	 */
	public void setF_branchId(int f_branchId) {
		this.f_branchId = f_branchId;
	}
	

}
