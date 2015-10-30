package kredit.web.kiva.model;

import java.util.Date;

import kredit.web.core.util.model.CodeItem;

public class ParamModel {
	String filter;
	Date startDate;
	Date endDate;
	CodeItem status;
	CodeItem branch;
	CodeItem sub;
	
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public CodeItem getStatus() {
		if (status != null) {
			return status;
		}
		status = new CodeItem();
		status.setCode("%");
		status.setDescription("All");
		return status;
	}
	public void setStatus(CodeItem status) {
		this.status = status;
	}
	public CodeItem getBranch() {
		if (branch != null)
			return branch;
		branch = new CodeItem();		
		branch.setCode("");
		branch.setDescription("All");
		return branch;
	}
	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}
	public CodeItem getSub() {
		if (sub != null)
			return sub;
		sub = new CodeItem();		
		sub.setCode("");
		sub.setDescription("All");
		return sub;
	}
	public void setSub(CodeItem sub) {
		this.sub = sub;
	}
	
	
}
