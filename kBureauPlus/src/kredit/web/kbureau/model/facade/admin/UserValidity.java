/**
 * 
 */
package kredit.web.kbureau.model.facade.admin;

import java.util.Date;

import kredit.web.core.util.Common;

/**
 * @author vathenan
 *
 */
public class UserValidity {
	int id;
	int userId;
	Date requestDate;
	String type;
	Date startDate;
	Date endDate;
	String note;
	int createdBy;
	String createdDate;
	String strRequestDate;
	String strStartDate;
	String strEndDate;
	int status;
	String statusImg;
	int countEmailSent;
	
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
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		if(note == null) note = "";
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the createdBy
	 */
	public int getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the strRequestDate
	 */
	public String getStrRequestDate() {
		if(requestDate == null) strRequestDate = "";
		else
			strRequestDate = Common.getSqlDateString(requestDate);
		return strRequestDate;
	}
	/**
	 * @param strRequestDate the strRequestDate to set
	 */
	public void setStrRequestDate(String strRequestDate) {
		this.strRequestDate = strRequestDate;
	}
	/**
	 * @return the strStartDate
	 */
	public String getStrStartDate() {
		if(startDate == null) strStartDate = "";
		else
			strStartDate = Common.getSqlDateString(startDate);
		return strStartDate;
	}
	/**
	 * @param strStartDate the strStartDate to set
	 */
	public void setStrStartDate(String strStartDate) {
		this.strStartDate = strStartDate;
	}
	/**
	 * @return the strEndDate
	 */
	public String getStrEndDate() {
		if(endDate == null) strEndDate = "";
		else
			strEndDate = Common.getSqlDateString(endDate);
		return strEndDate;
	}
	/**
	 * @param strEndDate the strEndDate to set
	 */
	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	/**
	 * @return the statusImg
	 */
	public String getStatusImg() {
		switch (status) {
		case -1:
			statusImg = "/img/kbureau/clock_red.png";
			break;
		case 1:
			statusImg = "/img/kbureau/clock_green.png";
			break;
		default:
			statusImg = "/img/kbureau/clock_yellow.png";
			break;
		}
		return statusImg;
	}
	
	public String getStatusTooltip() {
		String txt;
		switch (status) {
		case -1:
			txt = "The validity has expired";
			break;
		case 1:
			txt = "The validity is active";
			break;
		default:
			txt = "The validity is pending";
			break;
		}
		return txt;
	}
	/**
	 * @param statusImg the statusImg to set
	 */
	public void setStatusImg(String statusImg) {
		this.statusImg = statusImg;
	}
	/**
	 * @return the countEmailSent
	 */
	public int getCountEmailSent() {
		return countEmailSent;
	}
	/**
	 * @param countEmailSent the countEmailSent to set
	 */
	public void setCountEmailSent(int countEmailSent) {
		this.countEmailSent = countEmailSent;
	}
}
