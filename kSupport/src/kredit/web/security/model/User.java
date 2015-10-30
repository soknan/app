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

import kredit.web.core.model.Employee;
import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.facade.SecurityFacade;

@Entity
@Table(name="SYS_USER")
public class User {
	@Id
	Integer id;
	
	Integer emp_id;
	
	Integer branch_id;
	
	String branch_code;
	
	String branch_name;
	
	String full_name;
	
	String username;
	
	String pwd;
	
	Date pwd_change_on;
	
	String pwd_change_force;
	
	Integer time_level;
	
	String authorized;
	
	Date start_on;
	
	Date end_on;
	
	Integer success;
	
	Integer fail;
	
	String locked;
	
	String status;
	
	String create_by;
	
	Date create_on;
	
	String change_by;
	
	Date change_on;
	
	Integer security_no;
	
	String email;
	
	String phone;
	
	String right;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	List<UserFunction> userFunction;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	List<UserRole> userRole;
	
	@Transient
	List<UserValidity> userValidities;
	
	@Transient
	List<Function> avaFunctions;

	@Transient
	CodeItem branch;
	
	@Transient
	CodeItem statusC;
	
	@Transient
	CodeItem pwdF;
	
	@Transient
	CodeItem authorizedC;
	
	@Transient
	CodeItem lockedC;
	
	@Transient
	List<UserRole> avaUserRole;
	
	@Transient
	Employee emp;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getEmp_id() {
		return emp_id;
	}
	
	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}
	
	public Integer getBranch_id() {
		return branch_id;
	}
	
	public void setBranch_id(Integer branch_id) {
		this.branch_id = branch_id;
	}
	
	public String getBranch_code() {
		return branch_code;
	}
	
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	
	public String getBranch_name() {
		return branch_name;
	}
	
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	
	public String getFull_name() {
		return full_name;
	}
	
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public Date getPwd_change_on() {
		return pwd_change_on;
	}
	
	public void setPwd_change_on(Date pwd_change_on) {
		this.pwd_change_on = pwd_change_on;
	}
	
	public String getPwd_change_force() {
		return pwd_change_force;
	}
	
	public void setPwd_change_force(String pwd_change_force) {
		this.pwd_change_force = pwd_change_force;
	}
	
	public Integer getTime_level() {
		return time_level;
	}
	
	public void setTime_level(Integer time_level) {
		this.time_level = time_level;
	}
	
	public String getAuthorized() {
		return authorized;
	}
	
	public void setAuthorized(String authorized) {
		this.authorized = authorized;
	}
	
	public Date getStart_on() {
		return start_on;
	}
	
	public void setStart_on(Date start_on) {
		this.start_on = start_on;
	}
	
	public Date getEnd_on() {
		return end_on;
	}
	
	public void setEnd_on(Date end_on) {
		this.end_on = end_on;
	}
	
	public Integer getSuccess() {
		return success;
	}
	
	public void setSuccess(Integer success) {
		this.success = success;
	}
	
	public Integer getFail() {
		return fail;
	}
	
	public void setFail(Integer fail) {
		this.fail = fail;
	}
	
	public String getLocked() {
		return locked;
	}
	
	public void setLocked(String locked) {
		this.locked = locked;
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

	public List<UserFunction> getUserFunction() {
		return userFunction;
	}

	public void setUserFunction(List<UserFunction> userFunction) {
		this.userFunction = userFunction;
	}

	public List<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}

	public Integer getSecurity_no() {
		return security_no;
	}

	public void setSecurity_no(Integer security_no) {
		this.security_no = security_no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}
	
	public CodeItem getBranch() {
		if(branch == null)
		{
			int i = 0;
			if(branch_id != null)
				i = branch_id;
			branch = SecurityFacade.getBranch(i);
		}
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
		setBranch_id(this.branch.getId());
		setBranch_code(this.branch.getCode());
		setBranch_name(this.branch.getDescription());
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

	public CodeItem getPwdF() {
		if(pwdF == null)
		{
			pwdF = new CodeItem();
			if(pwd_change_force != null && pwd_change_force.equals("Y"))
			{
				pwdF.setCode("Y");
				pwdF.setDescription("True");
			}
			
			if(pwd_change_force != null && pwd_change_force.equals("N"))
			{
				pwdF.setCode("N");
				pwdF.setDescription("False");
			}
		}
		return pwdF;
	}

	public void setPwdF(CodeItem pwdF) {
		this.pwdF = pwdF;
		setPwd_change_force(this.pwdF.getCode());
	}

	public CodeItem getAuthorizedC() {
		if(authorizedC == null)
		{
			authorizedC = new CodeItem();
			if(authorized != null && authorized.equals("Y"))
			{
				authorizedC.setCode("Y");
				authorizedC.setDescription("True");
			}
			
			if(authorized != null && authorized.equals("N"))
			{
				authorizedC.setCode("N");
				authorizedC.setDescription("False");
			}
		}
		return authorizedC;
	}

	public void setAuthorizedC(CodeItem authorizedC) {
		this.authorizedC = authorizedC;
		setAuthorized(this.authorizedC.getCode());
	}

	public CodeItem getLockedC() {
		if(lockedC == null)
		{
			lockedC = new CodeItem();
			if(locked != null && locked.equals("Y"))
			{
				lockedC.setCode("Y");
				lockedC.setDescription("True");
			}
			
			if(locked != null && locked.equals("N"))
			{
				lockedC.setCode("N");
				lockedC.setDescription("False");
			}
		}
		return lockedC;
	}

	public void setLockedC(CodeItem lockedC) {
		this.lockedC = lockedC;
		setLocked(this.lockedC.getCode());
	}
	
	public List<UserValidity> getUserValidities() {
		if(userValidities == null)
		{
			userValidities = SecurityFacade.getValiditiesList(id);
			
		}
		return userValidities;
	}

	public void setUserValidities(List<UserValidity> userValidities) {
		this.userValidities = userValidities;
	}

	public List<Function> getAvaFunctions() {
		if(avaFunctions == null)
		{
			avaFunctions = SecurityFacade.getAvailableFunctionsList(id) ;
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
			List<Role> avaRole = new ArrayList<Role>();
			avaRole = SecurityFacade.getAvailableRolesList(id);
			if(avaRole == null)
				return avaUserRole;
			
			for(int i = 0; i < avaRole.size(); i ++)
			{
				UserRole ur = new UserRole();
				ur.setRole(avaRole.get(i));
				avaUserRole.add(ur);
			}
		}
		return avaUserRole;
	}

	public void setAvaUserRole(List<UserRole> avaUserRole) {
		this.avaUserRole = avaUserRole;
	}
	
	public Employee getEmp() {
		if(emp == null)
		{
			if(emp_id != null)
				emp = SecurityFacade.getEmployee(emp_id);
			else
				emp = new Employee();
		}
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "EmpID:" );
        sb.append(this.getEmp_id());
        sb.append( "|" );
        sb.append( "Username:" );
        sb.append(this.getUsername());
        sb.append( "|" );
        sb.append( "Full Name:" );
        sb.append(this.getFull_name());
        sb.append( "|" );
        sb.append( "BR:" );
        sb.append(this.getBranch_code());
        
        return sb.toString();
    }
}