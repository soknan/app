/**
 * 
 */
package kredit.web.kbureau.model.admin;

import java.sql.Date;

import kredit.web.core.util.Common;

/**
 * @author vathenan
 *
 */
public class CbcAuthen {
	int id;
	String username = "";
	String password;
	String passwordDec;
	String subBranchCode;
	String subBranchName;
	String branchCode;
	Date resetedDate;
	Date expiredDate;
	int remainDay;
	
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
	 * @return the passwordDec
	 */
	public String getPasswordDec() {
		if(passwordDec == null && password != null){
			passwordDec = Common.decrypt(password, username);
		}
		return passwordDec;
	}
	/**
	 * @param passwordDec the passwordDec to set
	 */
	public void setPasswordDec(String passwordDec) {
		this.passwordDec = passwordDec;
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
	 * @return the subBranchName
	 */
	public String getSubBranchName() {
		return subBranchName;
	}
	/**
	 * @param subBranchName the subBranchName to set
	 */
	public void setSubBranchName(String subBranchName) {
		this.subBranchName = subBranchName;
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
	 * @return the resetedDate
	 */
	public Date getResetedDate() {
		return resetedDate;
	}
	/**
	 * @param resetedDate the resetedDate to set
	 */
	public void setResetedDate(Date resetedDate) {
		this.resetedDate = resetedDate;
	}
	/**
	 * @return the expiredDate
	 */
	public Date getExpiredDate() {
		return expiredDate;
	}
	/**
	 * @param expiredDate the expiredDate to set
	 */
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	/**
	 * @return the remainDay
	 */
	public int getRemainDay() {
		return remainDay;
	}
	/**
	 * @param remainDay the remainDay to set
	 */
	public void setRemainDay(int remainDay) {
		this.remainDay = remainDay;
	}
	
	public String getFlagImg(){
		if(remainDay < 1){
			return "/img/kbureau/clock_red.png";
		}else if(remainDay < 7){
			return "/img/kbureau/clock_yellow.png";
		}
		return "/img/kbureau/clock_green.png";
	}
	
	public String getFlagMsg(){
		if(remainDay < 1){
			return "This account has been disabled or password expired.";
		}else if(remainDay < 7){
			String tmp = "This account will be expired in the next " + remainDay + " days.";
			if(remainDay == 1)
				tmp = "This account will be expired tomorrow.";
			return tmp; 
		}
		return "This account will be expired in the next " + remainDay + " days.";
	}
}
