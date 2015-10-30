package kredit.web.util.checker.model;

import java.util.HashSet;
import java.util.Set;

import kredit.web.core.util.model.CodeItem;

public class ParamChecker {
	CodeItem branch;
	CodeItem subBranch;
	Set<CodeItem> sbrList;
	
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

	public Set<CodeItem> getSbrList() {
		if (sbrList == null)
			sbrList = new HashSet<CodeItem>();
		return sbrList;
	}

	public void setSbrList(Set<CodeItem> sbrList) {
		this.sbrList = sbrList;
	}
}
