package kredit.web.security.model;

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
@Table(name="SYS_FUNCTION")
public class Function {
	@Id
	Integer id;
	
	String name;
	
	String code;
	
	String type;
	
	String right;
	
	String note;
	
	String status;
	
	String create_by;
	
	Date create_on;
	
	String change_by;
	
	Date change_on;
	
	String href;
	
	String icon;
	
	String parent_code;
	
	Integer seq;
	
	String menu;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "function")
	List<RoleFunction> roleFunction;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "function")
	List<UserFunction> userFunction;
	
	@Transient
	CodeItem typeC;
	
	@Transient
	CodeItem statusC;
	
	@Transient
	List<Role> avaRoles;
	
	@Transient
	List<User> avaUsers;
	
	@Transient
	boolean menuB = false;
	
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
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getRight() {
		return right;
	}
	
	public void setRight(String right) {
		this.right = right;
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public List<RoleFunction> getRoleFunction() {
		return roleFunction;
	}

	public void setRoleFunction(List<RoleFunction> roleFunction) {
		this.roleFunction = roleFunction;
	}

	public List<UserFunction> getUserFunction() {
		return userFunction;
	}

	public void setUserFunction(List<UserFunction> userFunction) {
		this.userFunction = userFunction;
	}
	
	public CodeItem getTypeC() {
		if(typeC == null)
		{
			typeC = new CodeItem();
			if(type != null && type.equals("F"))
			{
				typeC.setCode("F");
				typeC.setDescription("Form");
			}
			
			if(type != null && type.equals("R"))
			{
				typeC.setCode("R");
				typeC.setDescription("Report");
			}
		}
		return typeC;
	}

	public void setTypeC(CodeItem typeC) {
		this.typeC = typeC;
		setType(typeC.getCode());
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

	public List<Role> getAvaRoles() {
		if(avaRoles == null)
		{
			avaRoles = SecurityFacade.getAvailableRole(id);
		}
		return avaRoles;
	}

	public void setAvaRoles(List<Role> avaRoles) {
		this.avaRoles = avaRoles;
	}

	public List<User> getAvaUsers() {
		if(avaUsers == null)
		{
			avaUsers = SecurityFacade.getAvailableUser(id);			
		}
		return avaUsers;
	}

	public void setAvaUsers(List<User> avaUsers) {
		this.avaUsers = avaUsers;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public boolean getMenuB() {
		if(menu != null && menu.equals("Y"))
			menuB = true;
		else
			menuB = false;
		return menuB;
	}

	public void setMenuB(boolean menuB) {
		this.menuB = menuB;
		if(this.menuB)
			menu = "Y";
		else
			menu = "N";
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "Name:" );
        sb.append(this.getName());
        sb.append( "|" );
        sb.append( "Code:" );
        sb.append(this.getCode());
        
        return sb.toString();
    }
}