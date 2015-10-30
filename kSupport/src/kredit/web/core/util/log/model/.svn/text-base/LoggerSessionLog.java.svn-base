package kredit.web.core.util.log.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kredit.web.core.model.LogDomain;

@Entity
@Table(name="SYS_SESSION_LOG")
public class LoggerSessionLog extends LogDomain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date logOn;
	
	@ManyToOne
	@JoinColumn(name="SESSION_ID")
	LoggerSession session;
	
	@Column(length=1)
	String priority;
	@Column(length=128)
	String module;
	@Column(length=128)
	String type;	

	@Column(length=128)
	String message;
	
	/**
	 * @return the session
	 */
	public LoggerSession getSession() {
		return session;
	}
	/**
	 * @param session the session to set
	 */
	public void setSession(LoggerSession session) {
		this.session = session;
	}
	/**
	 * @return the priority
	 */
	public String getPriority() {
		return priority;
	}
	/**
	 * @param priority the priority to set
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}
	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the logOn
	 */
	public Date getLogOn() {
		return logOn;
	}
	/**
	 * @param logOn the logOn to set
	 */
	public void setLogOn(Date logOn) {
		this.logOn = logOn;
	}
	
}
