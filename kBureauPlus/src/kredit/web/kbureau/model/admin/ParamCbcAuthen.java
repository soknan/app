/**
 * 
 */
package kredit.web.kbureau.model.admin;

import kredit.web.kbureau.model.report.CodeItem;

/**
 * @author vathenan
 *
 */
public class ParamCbcAuthen {
	
	String filter = "";
	CodeItem branch;
	CodeItem subBranch;
	CodeItem flag;
	boolean includeReserve;
	
	/**
	 * @return the branch
	 */
	public CodeItem getBranch() {
		if (branch == null) {
			CodeItem item = new CodeItem();
			item.setCode("");
			item.setDescription("All");
			branch = item;
		}
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}
	/**
	 * @return the subBranch
	 */
	public CodeItem getSubBranch() {
		if (subBranch == null) {
			CodeItem item = new CodeItem();
			item.setCode("");
			item.setDescription("All");
			subBranch = item;
		}
		return subBranch;
	}
	/**
	 * @param subBranch the subBranch to set
	 */
	public void setSubBranch(CodeItem subBranch) {
		this.subBranch = subBranch;
	}
	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	/**
	 * @return the flag
	 */
	public CodeItem getFlag() {
		if(flag != null)
			return flag;
		
		flag = new CodeItem();
		flag.setCode("");
		flag.setDescription("All");
		
		return flag;
	}
	/**
	 * @param flag the flag to set
	 */
	public void setFlag(CodeItem flag) {
		this.flag = flag;
	}
	/**
	 * @return the includeReserve
	 */
	public boolean isIncludeReserve() {
		return includeReserve;
	}
	/**
	 * @param includeReserve the includeReserve to set
	 */
	public void setIncludeReserve(boolean includeReserve) {
		this.includeReserve = includeReserve;
	}
}
