package kredit.web.risk.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "clr_monthly_rating")
public class MonthlyRateModel{	
	private static final long serialVersionUID = 1L;

	String branch_code;
	String branch_name;
	String cif;
	String cust_name;
	Integer num_account;	
	String initial_class;	
	String initial_type;	
	String pre_class;	
	String pre_type;
	Double pre_bal;
	String cur_class;	
	String cur_type;	
	Double balance;
	Double monthly_deposit;
	String monthly_status;
	String fin_month;
	String fin_year;	
	Integer seq;
	Integer no;
	
	String create_by;	
	Date create_on;	
	String change_by;	
	Date change_on;
	
	
	public String getBranch_name() {
		return branch_name;
	}
	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
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
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public Integer getNum_account() {
		return num_account;
	}
	public void setNum_account(Integer num_account) {
		this.num_account = num_account;
	}
	public String getInitial_class() {
		return initial_class;
	}
	public void setInitial_class(String initial_class) {
		this.initial_class = initial_class;
	}
	public String getInitial_type() {
		return initial_type;
	}
	public void setInitial_type(String initial_type) {
		this.initial_type = initial_type;
	}
	public String getPre_class() {
		return pre_class;
	}
	public void setPre_class(String pre_class) {
		this.pre_class = pre_class;
	}
	public String getPre_type() {
		return pre_type;
	}
	public void setPre_type(String pre_type) {
		this.pre_type = pre_type;
	}
	public String getCur_class() {
		return cur_class;
	}
	public void setCur_class(String cur_class) {
		this.cur_class = cur_class;
	}
	public String getCur_type() {
		return cur_type;
	}
	public void setCur_type(String cur_type) {
		this.cur_type = cur_type;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getMonthly_deposit() {
		return monthly_deposit;
	}
	public void setMonthly_deposit(Double monthly_deposit) {
		this.monthly_deposit = monthly_deposit;
	}
	public String getMonthly_status() {
		return monthly_status;
	}
	public void setMonthly_status(String monthly_status) {
		this.monthly_status = monthly_status;
	}
	public String getFin_month() {
		return fin_month;
	}
	public void setFin_month(String fin_month) {
		this.fin_month = fin_month;
	}
	public String getFin_year() {
		return fin_year;
	}
	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_on() {
		return create_on;
	}
	public void setCreate_on(Date create_on) {
		this.create_on = create_on;
	}
	public String getChange_by() {
		return change_by;
	}
	public void setChange_by(String change_by) {
		this.change_by = change_by;
	}
	public Date getChange_on() {
		return change_on;
	}
	public void setChange_on(Date change_on) {
		this.change_on = change_on;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
