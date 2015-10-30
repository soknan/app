package kredit.web.security.model;

import kredit.web.core.util.model.CodeItem;

public class ParamEmp {

	String filter;
	CodeItem position;
	CodeItem branch;

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	public CodeItem getBranch() {
		if(branch == null)
		{
			branch = new CodeItem();
			branch.setId(0);
			branch.setCode("");
			branch.setDescription("All");
		}
		
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}

	public CodeItem getPosition() {
		if(position == null)
		{
			position = new CodeItem();
			position.setId(0);
			position.setCode("");
			position.setDescription("All");
		}
		return position;
	}

	public void setPosition(CodeItem position) {
		this.position = position;
	}
	
}
