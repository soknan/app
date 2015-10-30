package kredit.web.opp.data.model;

import kredit.web.core.util.model.CodeItem;

public class ParamCommune {
	
	private String filter = "";
	private CodeItem branch;
	private CodeItem subBranch;

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
	
	public CodeItem getSubBranch() {
		if(subBranch == null)
		{
			subBranch = new CodeItem();
			subBranch.setId(0);
			subBranch.setCode("");
			subBranch.setDescription("All");
		}
		return subBranch;
	}

	public void setSubBranch(CodeItem subBranch) {
		this.subBranch = subBranch;
	}
	
}
