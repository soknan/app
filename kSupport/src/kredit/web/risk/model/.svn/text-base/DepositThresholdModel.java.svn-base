package kredit.web.risk.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "clr_deposit_threshold")
public class DepositThresholdModel{
	private static final long serialVersionUID = 1L;
	
	@Id
	Integer id;
	String rating_class;
	String per_day_deposit;
	String per_month_deposit;
	String title;
	
	@Transient
	Integer no;
	
	String create_by;	
	Date create_on;	
	String change_by;	
	Date change_on;	
	
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRating_class() {
		return rating_class;
	}
	public void setRating_class(String rating_class) {
		this.rating_class = rating_class;
	}
	public String getPer_day_deposit() {
		return per_day_deposit;
	}
	public void setPer_day_deposit(String per_day_deposit) {
		this.per_day_deposit = per_day_deposit;
	}
	public String getPer_month_deposit() {
		return per_month_deposit;
	}
	public void setPer_month_deposit(String per_month_deposit) {
		this.per_month_deposit = per_month_deposit;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
