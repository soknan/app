/**
 * 
 */
package kredit.web.kbureau.model.admin;

import kredit.web.kbureau.model.report.CodeItem;

/**
 * @author vathenan
 * 
 */
public class ParamUser {
	String filter;
	CodeItem branch;
	CodeItem subBranch;
	CodeItem status;
	CodeItem sex;

	/**
	 * @return the filter
	 */
	public String getFilter() {
		if (filter == null)
			filter = "";
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

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
	 * @param branch
	 *            the branch to set
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
	 * @param subBranch
	 *            the subBranch to set
	 */
	public void setSubBranch(CodeItem subBranch) {
		this.subBranch = subBranch;
	}

	/**
	 * @return the status
	 */
	public CodeItem getStatus() {
		if(status == null){
			CodeItem item = new CodeItem();
			item.setCode("");
			item.setDescription("All");
			status = item;
		}
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(CodeItem status) {
		this.status = status;
	}

	/**
	 * @return the sex
	 */
	public CodeItem getSex() {
		if (sex == null) {
			CodeItem item = new CodeItem();
			item.setCode("");
			item.setDescription("All");
			sex = item;
		}
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(CodeItem sex) {
		this.sex = sex;
	}
}
