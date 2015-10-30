/**
 * 
 */
package kredit.web.kbureau.model;

import java.sql.Date;

/**
 * @author vathenan
 *
 */
public class UserLog {
	
	private int id;
	private String dateTime;
	private int userId;
	private String loginName;
	private String subBranch;
	private String branch;
	private String pcName;
	private String pcUsername;
	private String appVersion;
	private String message;
	private String messageSummary;
	private Date date;
	
	//total records
	int total;
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
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	 * @return the pcName
	 */
	public String getPcName() {
		return pcName;
	}
	/**
	 * @param pcName the pcName to set
	 */
	public void setPcName(String pcName) {
		this.pcName = pcName;
	}
	/**
	 * @return the pcUsername
	 */
	public String getPcUsername() {
		return pcUsername;
	}
	/**
	 * @param pcUsername the pcUsername to set
	 */
	public void setPcUsername(String pcUsername) {
		this.pcUsername = pcUsername;
	}
	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}
	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
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
	 * @return the messageSummary
	 */
	public String getMessageSummary() {
		if(message == null) return "";
		messageSummary = message;
		if(message.length() > 45){
			messageSummary = message.substring(0, 40) + "...";
		}
		return messageSummary;
	}
	/**
	 * @param messageSummary the messageSummary to set
	 */
	public void setMessageSummary(String messageSummary) {
		this.messageSummary = messageSummary;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
