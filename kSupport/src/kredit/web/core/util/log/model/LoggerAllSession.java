package kredit.web.core.util.log.model;

import java.util.Date;

import kredit.web.core.model.LogDomain;

public class LoggerAllSession extends LogDomain{
private static final long serialVersionUID = 1L;

	Date loginOn;
	Date logoutOn;
	String username;
	String message;
	String branchName;
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	String module;
	String type; 
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getLoginOn() {
		return loginOn;
	}
	public void setLoginOn(Date loginOn) {
		this.loginOn = loginOn;
	}
	public Date getLogoutOn() {
		return logoutOn;
	}
	public void setLogoutOn(Date logoutOn) {
		this.logoutOn = logoutOn;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	String ip;
	String host;
	String agent;
}
