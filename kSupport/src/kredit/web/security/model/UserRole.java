package kredit.web.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SYS_USER_ROLE")
public class UserRole {
	@Id
	Integer id;
	
	String create_by;
	
	Date create_on;
	
	String change_by;
	
	Date change_on;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	User user;
	
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	Role role;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCreate_by() {
		return create_by;
	}
	
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	
	public Date getCreate_on() {
		return create_on;
	}
	
	public void setCreate_on(Date create_on) {
		this.create_on = create_on;
	}
	
	public String getChange_by() {
		return change_by;
	}
	
	public void setChange_by(String change_by) {
		this.change_by = change_by;
	}
	
	public Date getChange_on() {
		return change_on;
	}
	
	public void setChange_on(Date change_on) {
		this.change_on = change_on;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}