package kredit.web.util.checker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "UTL_CHECKER")
public class Checker {

	@Id
	Integer id;
	@Column(length = 50)
	String title;
	@Column(length = 3)
	String type; // WRN: warning, INF: info, ERR: Error
	@Column(length = 3000)
	String sql;
	@Column(length = 50)
	String statusMsg;
	@Column(length = 500)
	String validationMsg;
	String seq;
	String admin;
	
	@Column(length=100)
	String colProp;

	@Transient
	String result;

	@Transient
	String cssClass;
	
	@Transient
	int found;
	
	String active;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		if (type == null)
			return "";
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param sql
	 *            the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the statusMsg
	 */
	public String getStatusMsg() {
		return statusMsg;
	}

	/**
	 * @param statusMsg
	 *            the statusMsg to set
	 */
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	/**
	 * @return the validationMsg
	 */
	public String getValidationMsg() {
		return validationMsg;
	}

	/**
	 * @param validationMsg
	 *            the validationMsg to set
	 */
	public void setValidationMsg(String validationMsg) {
		this.validationMsg = validationMsg;
	}

	/**
	 * @return the seq
	 */
	public String getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(String seq) {
		this.seq = seq;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the cssClass
	 */
	public String getCssClass() {
		if (cssClass != null)
			return cssClass;
		switch (getType()) {
		case CheckType.ERROR:
			cssClass = "red";
			break;
		case CheckType.WARNING:
			cssClass = "k-orange";
			break;
		default:
			cssClass = "blue";
			break;
		}
		return cssClass;
	}

	/**
	 * @param cssClass
	 *            the cssClass to set
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * @return the colProp
	 */
	public String getColProp() {
		if(colProp != null) return colProp;
		//default
		colProp = "[{\"name\":\"ID\", \"w\":\"80px\"},{\"name\":\"C1\", \"w\":\"120px\"},{\"name\":\"C2\", \"w\":\"160px\"}]";
		return colProp;
	}

	/**
	 * @param colProp the colProp to set
	 */
	public void setColProp(String colProp) {
		this.colProp = colProp;
	}

	/**
	 * @return the found
	 */
	public int getFound() {
		return found;
	}

	/**
	 * @param found the found to set
	 */
	public void setFound(int found) {
		this.found = found;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	
	public String getSummary(){
		if(validationMsg.length() > 120){
			return validationMsg.substring(0, 120) + "...";
		}
		return validationMsg;
	}

	/**
	 * @return the admin
	 */
	public String getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
