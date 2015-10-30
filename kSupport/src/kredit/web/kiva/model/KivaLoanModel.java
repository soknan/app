package kredit.web.kiva.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.avaje.ebean.annotation.Formula;

import kredit.web.core.model.Domain;

@Entity
@Table(name = "KIVA_LOAN")
public class KivaLoanModel extends Domain {
	private static final long serialVersionUID = 1L;
 
	private String loanId;
	private String refAcc;
	private Integer loanAmount;
	private String clientId;
	private String clientName;
	private Date dateRaise;
	private String coId;
	private String coName;
	private Date dateJournal;
	private Date dateClose;
	private Integer dayNumber;
	private String journalStatus;
	private Integer term;
	private Date dateDisburse;
	private String branchCode;
	@Formula(select = "b${ta}.name_en", join = "LEFT OUTER JOIN sys_branch b${ta} on b${ta}.branch_code = ${ta}.branch_code")
	String subName;
	private String payState;
	private String active;
	private Integer payAmount;
	
	public Integer getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}

	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public Integer getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Integer loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Date getDateRaise() {
		return dateRaise;
	}
	public void setDateRaise(Date dateRaise) {
		this.dateRaise = dateRaise;
	}
	public String getCoId() {
		return coId;
	}
	public void setCoId(String coId) {
		this.coId = coId;
	}
	public String getCoName() {
		return coName;
	}
	public void setCoName(String coName) {
		this.coName = coName;
	}
	public Date getDateJournal() {
		return dateJournal;
	}
	public void setDateJournal(Date dateJournal) {
		this.dateJournal = dateJournal;
	}
	public Date getDateClose() {
		return dateClose;
	}
	public void setDateClose(Date dateClose) {
		this.dateClose = dateClose;
	}
	public Integer getDayNumber() {
		return dayNumber;
	}
	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}
	public String getJournalStatus() {
		return journalStatus;
	}
	public void setJournalStatus(String journalStatus) {
		this.journalStatus = journalStatus;
	}
	public Integer getTerm() {
		if(term==null){
			term = 0;
		}
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Date getDateDisburse() {
		return dateDisburse;
	}
	public void setDateDisburse(Date dateDisburse) {
		this.dateDisburse = dateDisburse;
	}	
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getRefAcc() {
		return refAcc;
	}
	public void setRefAcc(String refAcc) {
		this.refAcc = refAcc;
	}	
	
}
