package kredit.web.core.util.log.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.Formula;

import kredit.web.core.model.LogDomain;

@Entity
@Table(name = "SYS_SESSION")
public class LoggerSession extends LogDomain{
	private static final long serialVersionUID = 1L;
	
	String username;

	@CreatedTimestamp
	Date loginOn;
	
	@CreatedTimestamp
	Date logoutOn;
	
	@Column(length=15)
	String ip;
	String host;
	String agent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "session")
	List<LoggerSessionLog> sessionLogs;

	@Formula(select = " v${ta}.name_en", join = " LEFT OUTER JOIN sys_branch v${ta} ON ${ta}.branch_code = v${ta}.branch_code")
	String branchName;

	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	 * @return the loginOn
	 */
	public Date getLoginOn() {
		return loginOn;
	}

	/**
	 * @param loginOn
	 *            the loginOn to set
	 */
	public void setLoginOn(Date loginOn) {
		this.loginOn = loginOn;
	}

	/**
	 * @return the logoutOn
	 */
	public Date getLogoutOn() {
		return logoutOn;
	}

	/**
	 * @param logoutOn
	 *            the logoutOn to set
	 */
	public void setLogoutOn(Date logoutOn) {
		this.logoutOn = logoutOn;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the agent
	 */
	public String getAgent() {
		return agent;
	}

	/**
	 * @param agent
	 *            the agent to set
	 */
	public void setAgent(String agent) {
		this.agent = agent;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the sessionLogs
	 */
	public List<LoggerSessionLog> getSessionLogs() {
		if(sessionLogs == null){
			sessionLogs = new ArrayList<LoggerSessionLog>();
		}
		return sessionLogs;
	}

	/**
	 * @param sessionLogs the sessionLogs to set
	 */
	public void setSessionLogs(List<LoggerSessionLog> sessionLogs) {
		this.sessionLogs = sessionLogs;
	}
}
