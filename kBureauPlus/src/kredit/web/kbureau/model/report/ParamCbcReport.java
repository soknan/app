/**
 * 
 */
package kredit.web.kbureau.model.report;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vathenan
 *
 */
public class ParamCbcReport {
	
	String filter; 
	CodeItem branch;
	CodeItem subBranch; 
	CodeItem rptType; 
	CodeItem status; 
	CodeItem decision;
	String fromAmount; 
	String toAmount; 
	CodeItem currency; 
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
	 * @return the rptType
	 */
	public CodeItem getRptType() {
		if(rptType == null){
			CodeItem item = new CodeItem();
			item.setCode("");
			item.setDescription("All");
			rptType = item;
		}
		return rptType;
	}
	/**
	 * @param rptType the rptType to set
	 */
	public void setRptType(CodeItem rptType) {
		this.rptType = rptType;
	}
	/**
	 * @return the status
	 */
	public CodeItem getStatus() {
		if(status == null){
			CodeItem item = new CodeItem();
			item.setCode("OK");
			item.setDescription("OK");
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
	 * @return the decision
	 */
	public CodeItem getDecision() {
		if(decision == null){
			CodeItem item = new CodeItem();
			item.setCode("2");
			item.setDescription("All");
			decision = item;
		}
		return decision;
	}
	/**
	 * @param decision the decision to set
	 */
	public void setDecision(CodeItem decision) {
		this.decision = decision;
	}
	/**
	 * @return the fromAmount
	 */
	public String getFromAmount() {
		if(fromAmount == null) fromAmount = "";
		return fromAmount;
	}
	/**
	 * @param fromAmount the fromAmount to set
	 */
	public void setFromAmount(String fromAmount) {
		this.fromAmount = fromAmount;
	}
	/**
	 * @return the toAmount
	 */
	public String getToAmount() {
		if(toAmount == null) toAmount = "";
		return toAmount;
	}
	/**
	 * @param toAmount the toAmount to set
	 */
	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}
	/**
	 * @return the currency
	 */
	public CodeItem getCurrency() {
		if(currency == null){
			CodeItem item = new CodeItem();
			item.setCode("");
			item.setDescription("All");
			currency = item;
		}
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(CodeItem currency) {
		this.currency = currency;
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

}
