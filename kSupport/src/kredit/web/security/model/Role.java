package kredit.web.security.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.facade.SecurityFacade;

@Entity
@Table(name="SYS_ROLE")
public class Role {
	@Id
	Integer id;
	
	String name;
	
	String note;
	
	String status;
	
	String create_by;
	
	Date create_on;
	
	String change_by;
	
	Date change_on;
	
	String role_code;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	List<RoleFunction> roleFunction;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
	List<UserRole> userRole;
	
	@Transient
	CodeItem statusC;
	
	@Transient
	List<Function> avaFunctions;
	
	@Transient
	List<UserRole> avaUserRole;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
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

	public String getRole_code() {
		return role_code;
	}

	public void setRole_code(String role_code) {
		this.role_code = role_code;
	}

	public List<RoleFunction> getRoleFunction() {
		return roleFunction;
	}

	public void setRoleFunction(List<RoleFunction> roleFunction) {
		this.roleFunction = roleFunction;
	}

	public List<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}
	
	public CodeItem getStatusC() {
		if(statusC == null)
		{
			statusC = new CodeItem();
			if(status != null && status.equals("A"))
			{
				statusC.setCode("A");
				statusC.setDescription("Active");
			}
			
			if(status != null && status.equals("I"))
			{
				statusC.setCode("I");
				statusC.setDescription("Inactive");
			}
		}
		return statusC;
	}

	public void setStatusC(CodeItem state) {
		this.statusC = state;
		setStatus(this.statusC.getCode());
	}

	public List<Function> getAvaFunctions() {
		if(avaFunctions == null)
		{
			avaFunctions = SecurityFacade.getAvailableFunctionsListForRole(id);
		
		}
		return avaFunctions;
	}

	public void setAvaFunctions(List<Function> avaFunctions) {
		this.avaFunctions = avaFunctions;
	}
	
	public List<UserRole> getAvaUserRole() {
		if(avaUserRole == null)
		{
			avaUserRole = new ArrayList<UserRole>();
			List<User> avaUser = new ArrayList<User>();
			avaUser = SecurityFacade.getAvailableUsersList(id);
			if(avaUser == null)
				return avaUserRole;
			
			for(int i = 0; i < avaUser.size(); i ++)
			{
				UserRole ur = new UserRole();
				ur.setUser(avaUser.get(i));
				avaUserRole.add(ur);
			}
		}
		return avaUserRole;
	}

	public void setAvaUserRole(List<UserRole> avaUserRole) {
		this.avaUserRole = avaUserRole;
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "Name:" );
        sb.append(this.getName());
        
        return sb.toString();
    }
}