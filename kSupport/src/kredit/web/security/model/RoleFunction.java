package kredit.web.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.util.Common;

@Entity
@Table(name="SYS_ROLE_FUNCTION")
public class RoleFunction {
	@Id
	Integer id;
	
	String right = "";
	
	String create_by;
	
	Date create_on;
	
	String change_by;
	
	Date change_on;
	
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	Role role;
	
	@ManyToOne
	@JoinColumn(name = "FUNCTION_ID")
	Function function;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getRight() {
		return right;
	}
	
	public void setRight(String right) {
		this.right = right;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	//region List
	
		@Transient
		boolean checkedNew = false;
		@Transient
		boolean checkedCopy = false;
		@Transient
		boolean checkedView = false;
		@Transient
		boolean checkedUpdate = false;
		@Transient
		boolean checkedDelete = false;
		
		@Transient
		boolean disabledNew = true;
		@Transient
		boolean disabledCopy = true;
		@Transient
		boolean disabledView = true;
		@Transient
		boolean disabledUpdate = true;
		@Transient
		boolean disabledDelete = true;

		public boolean isCheckedNew() {
			if(right.contains("N"))
				checkedNew = true;
			
			return checkedNew;
		}

		public void setCheckedNew(boolean checkedN) {
			if(checkedN)
			{
				if(!right.contains("N"))
					right += "N";
			}
			else
			{
				if(right.contains("N"))
					right = Common.deleteCharacter(right, "N");
			}
			this.checkedNew = checkedN;
		}

		public boolean isCheckedCopy() {
			if(right.contains("C"))
				checkedCopy = true;
				
			return checkedCopy;
		}

		public void setCheckedCopy(boolean checkedC) {
			if(checkedC)
			{
				if(!right.contains("C"))
					right += "C";
			}
			else
			{
				if(right.contains("C"))
					right = Common.deleteCharacter(right, "C");
			}
			this.checkedCopy = checkedC;
		}

		public boolean isCheckedView() {
			if(right.contains("V"))
				checkedView = true;
				
			return checkedView;
		}

		public void setCheckedView(boolean checkedV) {
			if(checkedV)
			{
				if(!right.contains("V"))
					right += "V";
			}
			else
			{
				if(right.contains("V"))
					right = Common.deleteCharacter(right, "V");
			}
			this.checkedView = checkedV;
		}

		public boolean isCheckedUpdate() {
			if(right.contains("U"))
				checkedUpdate = true;
			
			return checkedUpdate;
		}

		public void setCheckedUpdate(boolean checkedU) {
			if(checkedU)
			{
				if(!right.contains("U"))
					right += "U";
			}
			else
			{
				if(right.contains("U"))
					right = Common.deleteCharacter(right, "U");
			}
			this.checkedUpdate = checkedU;
		}

		public boolean isCheckedDelete() {
			if(right.contains("D"))
				checkedDelete = true;
			
			return checkedDelete;
		}

		public void setCheckedDelete(boolean checkedD) {
			if(checkedD)
			{
				if(!right.contains("D"))
					right += "D";
			}
			else
			{
				if(right.contains("D"))
					right = Common.deleteCharacter(right, "D");
			}
			this.checkedDelete = checkedD;
		}

		public boolean isDisabledNew() {
			if(function.getRight().contains("N"))
			{
				disabledNew = false;
			}
				
			return disabledNew;
		}

		public void setDisabledNew(boolean disabledNew) {
			this.disabledNew = disabledNew;
		}

		public boolean isDisabledCopy() {
			if(function.getRight().contains("C"))
			{
				disabledCopy = false;
			}
				
			return disabledCopy;
		}

		public void setDisabledCopy(boolean disabledCopy) {
			this.disabledCopy = disabledCopy;
		}

		public boolean isDisabledView() {
			if(function.getRight().contains("V"))
			{
				disabledView = false;
			}
				
			return disabledView;
		}

		public void setDisabledView(boolean disabledView) {
			this.disabledView = disabledView;
		}

		public boolean isDisabledUpdate() {
			if(function.getRight().contains("U"))
			{
				disabledUpdate = false;
			}
				
			return disabledUpdate;
		}

		public void setDisabledUpdate(boolean disabledUpdate) {
			this.disabledUpdate = disabledUpdate;
		}

		public boolean isDisabledDelete() {
			if(function.getRight().contains("D"))
			{
				disabledDelete = false;
			}
				
			return disabledDelete;
		}

		public void setDisabledDelete(boolean disabledDelete) {
			this.disabledDelete = disabledDelete;
		}
		
	//endregion List
		
//region changed

	@Transient
	String original_right;
	
	public String getOriginal_right() {
		if(original_right == null)
		{
			original_right = right;			
		}
		return original_right;
	}

	public void setOriginal_right(String original_right) {
			this.original_right = original_right;
	}
		
//endregion changed
}