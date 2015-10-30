package kredit.web.risk.model;

import java.util.Date;

public class RateDetailModel{
	private static final long serialVersionUID = 1L;
	String branch_code;
	String cif;
	String acc_no;
	String product_name;
	String ccy;
	Double b_balance;
	Double deposit;
	Double withdrawal;
	Double other_deposit;
	Double disbursement;
	Double principal_repayment;
	Double other_loan;
	Double e_balance;
	Date value_date;
	Integer no;
	
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Date getValue_date() {
		return value_date;
	}
	public void setValue_date(Date value_date) {
		this.value_date = value_date;
	}
	public String getBranch_code() {
		return branch_code;
	}
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(String acc_no) {
		this.acc_no = acc_no;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public Double getB_balance() {
		return b_balance;
	}
	public void setB_balance(Double b_balance) {
		this.b_balance = b_balance;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getWithdrawal() {
		return withdrawal;
	}
	public void setWithdrawal(Double withdrawal) {
		this.withdrawal = withdrawal;
	}
	public Double getOther_deposit() {
		return other_deposit;
	}
	public void setOther_deposit(Double other_deposit) {
		this.other_deposit = other_deposit;
	}
	public Double getDisbursement() {
		return disbursement;
	}
	public void setDisbursement(Double disbursement) {
		this.disbursement = disbursement;
	}
	public Double getPrincipal_repayment() {
		return principal_repayment;
	}
	public void setPrincipal_repayment(Double principal_repayment) {
		this.principal_repayment = principal_repayment;
	}
	public Double getOther_loan() {
		return other_loan;
	}
	public void setOther_loan(Double other_loan) {
		this.other_loan = other_loan;
	}
	public Double getE_balance() {
		return e_balance;
	}
	public void setE_balance(Double e_balance) {
		this.e_balance = e_balance;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
