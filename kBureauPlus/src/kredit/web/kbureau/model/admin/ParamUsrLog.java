/**
 * 
 */
package kredit.web.kbureau.model.admin;

import java.text.SimpleDateFormat;
import java.util.Date;

import kredit.web.kbureau.model.report.CodeItem;

/**
 * @author vathenan
 *
 */
public class ParamUsrLog {
	String filter; 
	CodeItem branch;
	CodeItem subBranch; 
	CodeItem appType;
	CodeItem usrType;
	CodeItem logType;
	Date startDate;
	Date endDate;
	String fromDate;
	String toDate;
	
	/**
	 * @return the filter
	 */
	public String getFilter() {
		if(filter == null) filter = "";
		return filter;
	}
	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}
	/**
	 * @return the branch
	 */
	public CodeItem getBranch() {
		if(branch == null){
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
		if(subBranch == null){
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
	 * @return the usrType
	 */
	public CodeItem getUsrType() {
		if(usrType == null){
			CodeItem item = new CodeItem();
			item.setCode("0");
			item.setDescription("All");
			usrType = item;
		}
		return usrType;
	}
	/**
	 * @param usrType the usrType to set
	 */
	public void setUsrType(CodeItem usrType) {
		this.usrType = usrType;
	}
	
	public CodeItem getAppType() {
		if(appType == null){
			CodeItem item = new CodeItem();
			item.setCode("0");
			item.setDescription("All");
			appType = item;
		}
		return appType;
	}
	
	public void setAppType(CodeItem appType) {
		this.appType = appType;
	}
	
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		if(startDate == null) fromDate = "";
		else{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			fromDate = formatter.format(startDate);
		}
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		if(endDate == null) toDate = "";
		else{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			toDate = formatter.format(endDate);
		}
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the logType
	 */
	public CodeItem getLogType() {
		if(logType == null){
			CodeItem item = new CodeItem();
			item.setCode("0");
			item.setDescription("All");
			logType = item;
		}
		
		return logType;
	}
	/**
	 * @param logType the logType to set
	 */
	public void setLogType(CodeItem logType) {
		this.logType = logType;
	}

}
