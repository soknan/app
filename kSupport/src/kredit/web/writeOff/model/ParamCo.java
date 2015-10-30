package kredit.web.writeOff.model;

import kredit.web.core.util.model.CodeItem;

public class ParamCo {

	String filter = "";
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
			branch.setCode("");
			branch.setId(0);
			branch.setDescription("All");
		}
			
		return branch;
	}
	
	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}
}
