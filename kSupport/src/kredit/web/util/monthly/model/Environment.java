package kredit.web.util.monthly.model;

public class Environment {
	
	private String environment;
	private String username;
	private String password;
	
	private int mode;
	
	public String getEnvironment() {
		return environment;
	}
	
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
	
}
