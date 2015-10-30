package kredit.web.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.zul.ListModelList;

import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.facade.SecurityFacade;

@Entity
@Table(name="SYS_USER_VALIDITY")
public class UserValidity {
	@Id
	Integer id;
	
	Integer user_id;
	
	Integer user_id_as;
	
	Date start_on;
	
	Date end_on;
	
	String branch_code;
	
	String note;
	
	String create_by;
	
	Date create_on;
	
	String change_by;
	
	Date change_on;
	
	String type;
	
	@Transient
	User userAs;
	
	@Transient
	CodeItem branch;
	
	@Transient
	CodeItem typeC;
	
	@Transient
	ListModelList<User> lstUserAs;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUser_id_as() {
		return user_id_as;
	}
	
	public void setUser_id_as(Integer user_id_as) {
		this.user_id_as = user_id_as;
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
	
	public String getBranch_code() {
		return branch_code;
	}
	
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public User getUserAs() {
		if(userAs == null)
		{
			int i = 0;
			if(user_id_as != null)
				i = user_id_as;
			
			userAs = SecurityFacade.getUser(i);
		}
		return userAs;
	}

	public void setUserAs(User userAs) {
		this.userAs = userAs;
		setUser_id_as(userAs.getId());
	}

	public CodeItem getBranch() {
		if(branch == null)
		{
			String c = "";
			if(branch_code != null)
				c = branch_code;
			branch = SecurityFacade.getBranch(c);
		}
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
		setBranch_code(this.branch.getCode());
	}
	
	public CodeItem getTypeC() {
		if(typeC == null)
		{
			typeC = new CodeItem();
			if(type != null && type.equals("A"))
			{
				typeC.setCode("A");
				typeC.setDescription("Acting");
			}
			
			if(type != null && type.equals("R"))
			{
				typeC.setCode("R");
				typeC.setDescription("Replacement");
			}
		}
		return typeC;
	}

	public void setTypeC(CodeItem typeC) {
		this.typeC = typeC;
		setType(this.typeC.getCode());
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public ListModelList<User> getLstUserAs() {
		if(lstUserAs == null)
		{	
			lstUserAs = new ListModelList<User>(SecurityFacade.getUserByBranch(branch_code, user_id));
		}
		return lstUserAs;
	}

	public void setLstUserAs(ListModelList<User> lstUserAs) {
		this.lstUserAs = lstUserAs;
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "UserID:" );
        sb.append(this.getUser_id());
        sb.append( "|" );
        sb.append( "UserID As:" );
        sb.append(this.getUser_id_as());
        sb.append( "|" );
        sb.append( "Type:" );
        sb.append(this.getTypeC().getDescription());
        sb.append( "|" );
        sb.append( "BR:" );
        sb.append(this.getBranch_code());
        
        return sb.toString();
    }
}