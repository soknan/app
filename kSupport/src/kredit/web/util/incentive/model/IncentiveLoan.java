package kredit.web.util.incentive.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INC_ACMT_LOAN")
public class IncentiveLoan {
	
	@Id
	Integer id;
	
	Integer crdtofcr_id;
	String crdtofcr_name;
	String crdtofcr_shortname;
	String position;
	String branch_code;
	Date acmt_date;
	Double osnd_balance;
	Double par30;
	Integer act_client;
	Integer int_collected; 
	Integer new_client;
	Integer ongoing_client;
	Integer rural_client;
	Integer femal_client;
	Integer female_client_vsu;
	Integer new_rural_client;
	Double total_acc_disb;
	Double new_acc_disb;
	Date created_date;
	String created_user;
	Date modified_date;
	String modified_user;
	String note;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCrdtofcr_id() {
		return crdtofcr_id;
	}
	
	public void setCrdtofcr_id(Integer crdtofcr_id) {
		this.crdtofcr_id = crdtofcr_id;
	}
	
	public String getCrdtofcr_name() {
		return crdtofcr_name;
	}
	
	public void setCrdtofcr_name(String crdtofcr_name) {
		this.crdtofcr_name = crdtofcr_name;
	}
	
	public String getCrdtofcr_shortname() {
		return crdtofcr_shortname;
	}

	public void setCrdtofcr_shortname(String crdtofcr_shortname) {
		this.crdtofcr_shortname = crdtofcr_shortname;
	}

	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getBranch_code() {
		return branch_code;
	}
	
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	
	public Date getAcmt_date() {
		return acmt_date;
	}
	
	public void setAcmt_date(Date acmt_date) {
		this.acmt_date = acmt_date;
	}
	
	public Double getOsnd_balance() {
		return osnd_balance;
	}
	
	public void setOsnd_balance(Double osnd_balance) {
		this.osnd_balance = osnd_balance;
	}
	
	public Double getPar30() {
		return par30;
	}
	
	public void setPar30(Double par30) {
		this.par30 = par30;
	}
	
	public Integer getAct_client() {
		return act_client;
	}
	
	public void setAct_client(Integer act_client) {
		this.act_client = act_client;
	}
	
	public Integer getInt_collected() {
		return int_collected;
	}
	
	public void setInt_collected(Integer int_collected) {
		this.int_collected = int_collected;
	}
	
	public Integer getNew_client() {
		return new_client;
	}
	
	public void setNew_client(Integer new_client) {
		this.new_client = new_client;
	}
	
	public Integer getOngoing_client() {
		return ongoing_client;
	}
	
	public void setOngoing_client(Integer ongoing_client) {
		this.ongoing_client = ongoing_client;
	}
	
	public Integer getRural_client() {
		return rural_client;
	}
	
	public void setRural_client(Integer rural_client) {
		this.rural_client = rural_client;
	}
	
	public Integer getFemal_client() {
		return femal_client;
	}
	
	public void setFemal_client(Integer femal_client) {
		this.femal_client = femal_client;
	}
	
	public Integer getFemale_client_vsu() {
		return female_client_vsu;
	}
	
	public void setFemale_client_vsu(Integer female_client_vsu) {
		this.female_client_vsu = female_client_vsu;
	}
	
	public Integer getNew_rural_client() {
		return new_rural_client;
	}
	
	public void setNew_rural_client(Integer new_rural_client) {
		this.new_rural_client = new_rural_client;
	}
	
	public Double getTotal_acc_disb() {
		return total_acc_disb;
	}
	
	public void setTotal_acc_disb(Double total_acc_disb) {
		this.total_acc_disb = total_acc_disb;
	}
	
	public Double getNew_acc_disb() {
		return new_acc_disb;
	}
	
	public void setNew_acc_disb(Double new_acc_disb) {
		this.new_acc_disb = new_acc_disb;
	}
	
	public Date getCreated_date() {
		return created_date;
	}
	
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	public String getCreated_user() {
		return created_user;
	}
	
	public void setCreated_user(String created_user) {
		this.created_user = created_user;
	}
	
	public Date getModified_date() {
		return modified_date;
	}
	
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	
	public String getModified_user() {
		return modified_user;
	}
	
	public void setModified_user(String modified_user) {
		this.modified_user = modified_user;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

}
