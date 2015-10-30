/**
 * 
 */
package kredit.web.kbureau.model.report;

import java.sql.Date;

/**
 * @author vathenan
 * 
 */
public class CbcReport {

	int id;
	String loanId;
	String idNumber;
	String name;
	String subBranchCode;
	String status;
	String refNumber;
	String action;
	String reportDate;
	String enquiryType;
	String productType;
	String accountType;
	double amount;
	String currency;
	String amountCurrency;
	String applicant;
	String request;
	String response;
	int userId;
	int decision;
	int numActiveAcc;
	double totalOutstanding;
	double feeKredit;
	double feeCbc;
	String branchCode;
	String note;
	String decisionDesc;
	String actionDesc;
	String subBranch;
	Date date;
		
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the loanId
	 */
	public String getLoanId() {
		return loanId;
	}

	/**
	 * @param loanId
	 *            the loanId to set
	 */
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @param idNumber
	 *            the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @return the label
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the subBranchCode
	 */
	public String getSubBranchCode() {
		return subBranchCode;
	}

	/**
	 * @param subBranchCode
	 *            the subBranchCode to set
	 */
	public void setSubBranchCode(String subBranchCode) {
		this.subBranchCode = subBranchCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the refNumber
	 */
	public String getRefNumber() {
		return refNumber;
	}

	/**
	 * @param refNumber
	 *            the refNumber to set
	 */
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the reportDate
	 */
	public String getReportDate() {
		return reportDate;
	}

	/**
	 * @param reportDate
	 *            the reportDate to set
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * @return the enquiryType
	 */
	public String getEnquiryType() {
		return enquiryType;
	}

	/**
	 * @param enquiryType
	 *            the enquiryType to set
	 */
	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	/**
	 * @return the productType
	 */
	public String getProductType() {
		return productType;
	}

	/**
	 * @param productType
	 *            the productType to set
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType
	 *            the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the applicant
	 */
	public String getApplicant() {
		return applicant;
	}

	/**
	 * @param applicant
	 *            the applicant to set
	 */
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @param request
	 *            the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the decision
	 */
	public int getDecision() {
		return decision;
	}

	/**
	 * @param decision
	 *            the decision to set
	 */
	public void setDecision(int flag) {
		this.decision = flag;
	}

	/**
	 * @return the subBranch
	 */
	public String getSubBranch() {
		return subBranch;
	}

	/**
	 * @param subBranch
	 *            the subBranch to set
	 */
	public void setSubBranch(String subBranch) {
		this.subBranch = subBranch;
	}

	/**
	 * @return the numActiveAcc
	 */
	public int getNumActiveAcc() {
		return numActiveAcc;
	}

	/**
	 * @param numActiveAcc
	 *            the numActiveAcc to set
	 */
	public void setNumActiveAcc(int numActiveAcc) {
		this.numActiveAcc = numActiveAcc;
	}

	/**
	 * @return the totalOutstanding
	 */
	public double getTotalOutstanding() {
		return totalOutstanding;
	}

	/**
	 * @param totalOutstanding
	 *            the totalOutstanding to set
	 */
	public void setTotalOutstanding(double totalOutstanding) {
		this.totalOutstanding = totalOutstanding;
	}

	/**
	 * @return the feeKredit
	 */
	public double getFeeKredit() {
		return feeKredit;
	}

	/**
	 * @param feeKredit
	 *            the feeKredit to set
	 */
	public void setFeeKredit(double feeKredit) {
		this.feeKredit = feeKredit;
	}

	/**
	 * @return the feeCbc
	 */
	public double getFeeCbc() {
		return feeCbc;
	}

	/**
	 * @param feeCbc
	 *            the feeCbc to set
	 */
	public void setFeeCbc(double feeCbc) {
		this.feeCbc = feeCbc;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the decisionDesc
	 */
	public String getDecisionDesc() {
		switch (decision) {
			case 0:
				decisionDesc = "Pending";
				break;
			case 1:
				decisionDesc = "Approved";
				break;
			case -1:
				decisionDesc = "Rejected";
				break;
			case -2:
				decisionDesc = "Canceled";
				break;
			default:
				decisionDesc = "";
				break;
		}
		return decisionDesc;
	}

	/**
	 * @param decisionDesc
	 *            the decisionDesc to set
	 */
	public void setDecisionDesc(String decisionDesc) {
		this.decisionDesc = decisionDesc;
	}

	/**
	 * @return the actionDesc
	 */
	public String getActionDesc() {
		if(action == null) action ="";
		switch(action){
			case "A":
				actionDesc = "Standard";
				break;
			case "L":
				actionDesc = "Lite";
				break;
			default:
				actionDesc = "";
				break;
		}
		return actionDesc;
	}

	/**
	 * @param actionDesc the actionDesc to set
	 */
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	public String getAmountCurrency() {
		amountCurrency = amount + " " +currency;
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}
	
}
