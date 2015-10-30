package kredit.web.util.transfer.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="INC_ACC_TRNF")
public class AccTrnf {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;
	
	private Integer trn_empid;
	private String trn_name;
	private String trn_sex;
	private String trn_branch;
	
	private Integer rcr_empid;
	private String rcr_name;
	private String rcr_sex;
	private String rcr_branch;
	
	private String require_type;
	
	private Double trn_pre_loan_amt;
	private Integer trn_pre_client_act;
	private Double trn_pre_sav_amt;
	private Integer trn_pre_sav_act;
	private Double trn_pre_par30_amt;
	private Double trn_pre_par30_pct;
	private Double trn_pre_par0_amt;
	private Double trn_pre_par0_pct;
	private Double trn_pre_wof_amt;
	private Double trn_pre_wof_pct;
	
	private Double trn_loan_amt;
	private Integer trn_client_act;
	private Double trn_sav_amt;
	private Integer trn_sav_act;
	private Double trn_par30_amt;
	private Double trn_par30_pct;
	private Double trn_par0_amt;
	private Double trn_par0_pct;
	private Double trn_wof_amt;
	private Double trn_wof_pct;
	
	private Double trn_post_loan_amt;
	private Integer trn_post_client_act;
	private Double trn_post_sav_amt;
	private Integer trn_post_sav_act;
	private Double trn_post_par30_amt;
	private Double trn_post_par30_pct;
	private Double trn_post_par0_amt;
	private Double trn_post_par0_pct;
	private Double trn_post_wof_amt;
	private Double trn_post_wof_pct;
	
	private Double rcr_loan_amt;
	private Integer rcr_client_act;
	private Double rcr_sav_amt;
	private Integer rcr_sav_act;
	private Double rcr_par30_amt;
	private Double rcr_par30_pct;
	private Double rcr_par0_amt;
	private Double rcr_par0_pct;
	private Double rcr_wof_amt;
	private Double rcr_wof_pct;
	
	private Double rcr_post_loan_amt;
	private Integer rcr_post_client_act;
	private Double rcr_post_sav_amt;
	private Integer rcr_post_sav_act;
	private Double rcr_post_par30_amt;
	private Double rcr_post_par30_pct;
	private Double rcr_post_par0_amt;
	private Double rcr_post_par0_pct;
	private Double rcr_post_wof_amt;
	private Double rcr_post_wof_pct;
	
	@CreatedTimestamp
    private Date createOn;

    @Version
    private Date changeOn;
 
    private String createBy;
    
    private String changeBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTrn_empid() {
		return trn_empid;
	}

	public void setTrn_empid(Integer trn_empid) {
		this.trn_empid = trn_empid;
	}

	public String getTrn_name() {
		return trn_name;
	}

	public void setTrn_name(String trn_name) {
		this.trn_name = trn_name;
	}

	public String getTrn_sex() {
		return trn_sex;
	}

	public void setTrn_sex(String trn_sex) {
		this.trn_sex = trn_sex;
	}

	public String getTrn_branch() {
		return trn_branch;
	}

	public void setTrn_branch(String trn_branch) {
		this.trn_branch = trn_branch;
	}

	public Integer getRcr_empid() {
		return rcr_empid;
	}

	public void setRcr_empid(Integer rcr_empid) {
		this.rcr_empid = rcr_empid;
	}

	public String getRcr_name() {
		return rcr_name;
	}

	public void setRcr_name(String rcr_name) {
		this.rcr_name = rcr_name;
	}

	public String getRcr_sex() {
		return rcr_sex;
	}

	public void setRcr_sex(String rcr_sex) {
		this.rcr_sex = rcr_sex;
	}

	public String getRcr_branch() {
		return rcr_branch;
	}

	public void setRcr_branch(String rcr_branch) {
		this.rcr_branch = rcr_branch;
	}

	public String getRequire_type() {
		return require_type;
	}

	public void setRequire_type(String require_type) {
		this.require_type = require_type;
	}

	public Double getTrn_pre_loan_amt() {
		return trn_pre_loan_amt;
	}

	public void setTrn_pre_loan_amt(Double trn_pre_loan_amt) {
		this.trn_pre_loan_amt = trn_pre_loan_amt;
	}

	public Integer getTrn_pre_client_act() {
		return trn_pre_client_act;
	}

	public void setTrn_pre_client_act(Integer trn_pre_client_act) {
		this.trn_pre_client_act = trn_pre_client_act;
	}

	public Double getTrn_pre_sav_amt() {
		return trn_pre_sav_amt;
	}

	public void setTrn_pre_sav_amt(Double trn_pre_sav_amt) {
		this.trn_pre_sav_amt = trn_pre_sav_amt;
	}

	public Integer getTrn_pre_sav_act() {
		return trn_pre_sav_act;
	}

	public void setTrn_pre_sav_act(Integer trn_pre_sav_act) {
		this.trn_pre_sav_act = trn_pre_sav_act;
	}

	public Double getTrn_pre_par30_amt() {
		return trn_pre_par30_amt;
	}

	public void setTrn_pre_par30_amt(Double trn_pre_par30_amt) {
		this.trn_pre_par30_amt = trn_pre_par30_amt;
	}

	public Double getTrn_pre_par30_pct() {
		return trn_pre_par30_pct;
	}

	public void setTrn_pre_par30_pct(Double trn_pre_par30_pct) {
		this.trn_pre_par30_pct = trn_pre_par30_pct;
	}

	public Double getTrn_pre_par0_amt() {
		return trn_pre_par0_amt;
	}

	public void setTrn_pre_par0_amt(Double trn_pre_par0_amt) {
		this.trn_pre_par0_amt = trn_pre_par0_amt;
	}

	public Double getTrn_pre_par0_pct() {
		return trn_pre_par0_pct;
	}

	public void setTrn_pre_par0_pct(Double trn_pre_par0_pct) {
		this.trn_pre_par0_pct = trn_pre_par0_pct;
	}

	public Double getTrn_pre_wof_amt() {
		return trn_pre_wof_amt;
	}

	public void setTrn_pre_wof_amt(Double trn_pre_wof_amt) {
		this.trn_pre_wof_amt = trn_pre_wof_amt;
	}

	public Double getTrn_pre_wof_pct() {
		return trn_pre_wof_pct;
	}

	public void setTrn_pre_wof_pct(Double trn_pre_wof_pct) {
		this.trn_pre_wof_pct = trn_pre_wof_pct;
	}

	public Double getTrn_loan_amt() {
		return trn_loan_amt;
	}

	public void setTrn_loan_amt(Double trn_loan_amt) {
		this.trn_loan_amt = trn_loan_amt;
	}

	public Integer getTrn_client_act() {
		return trn_client_act;
	}

	public void setTrn_client_act(Integer trn_client_act) {
		this.trn_client_act = trn_client_act;
	}

	public Double getTrn_sav_amt() {
		return trn_sav_amt;
	}

	public void setTrn_sav_amt(Double trn_sav_amt) {
		this.trn_sav_amt = trn_sav_amt;
	}

	public Integer getTrn_sav_act() {
		return trn_sav_act;
	}

	public void setTrn_sav_act(Integer trn_sav_act) {
		this.trn_sav_act = trn_sav_act;
	}

	public Double getTrn_par30_amt() {
		return trn_par30_amt;
	}

	public void setTrn_par30_amt(Double trn_par30_amt) {
		this.trn_par30_amt = trn_par30_amt;
	}

	public Double getTrn_par30_pct() {
		return trn_par30_pct;
	}

	public void setTrn_par30_pct(Double trn_par30_pct) {
		this.trn_par30_pct = trn_par30_pct;
	}

	public Double getTrn_par0_amt() {
		return trn_par0_amt;
	}

	public void setTrn_par0_amt(Double trn_par0_amt) {
		this.trn_par0_amt = trn_par0_amt;
	}

	public Double getTrn_par0_pct() {
		return trn_par0_pct;
	}

	public void setTrn_par0_pct(Double trn_par0_pct) {
		this.trn_par0_pct = trn_par0_pct;
	}

	public Double getTrn_wof_amt() {
		return trn_wof_amt;
	}

	public void setTrn_wof_amt(Double trn_wof_amt) {
		this.trn_wof_amt = trn_wof_amt;
	}

	public Double getTrn_wof_pct() {
		return trn_wof_pct;
	}

	public void setTrn_wof_pct(Double trn_wof_pct) {
		this.trn_wof_pct = trn_wof_pct;
	}

	public Double getTrn_post_loan_amt() {
		return trn_post_loan_amt;
	}

	public void setTrn_post_loan_amt(Double trn_post_loan_amt) {
		this.trn_post_loan_amt = trn_post_loan_amt;
	}

	public Integer getTrn_post_client_act() {
		return trn_post_client_act;
	}

	public void setTrn_post_client_act(Integer trn_post_client_act) {
		this.trn_post_client_act = trn_post_client_act;
	}

	public Double getTrn_post_sav_amt() {
		return trn_post_sav_amt;
	}

	public void setTrn_post_sav_amt(Double trn_post_sav_amt) {
		this.trn_post_sav_amt = trn_post_sav_amt;
	}

	public Integer getTrn_post_sav_act() {
		return trn_post_sav_act;
	}

	public void setTrn_post_sav_act(Integer trn_post_sav_act) {
		this.trn_post_sav_act = trn_post_sav_act;
	}

	public Double getTrn_post_par30_amt() {
		return trn_post_par30_amt;
	}

	public void setTrn_post_par30_amt(Double trn_post_par30_amt) {
		this.trn_post_par30_amt = trn_post_par30_amt;
	}

	public Double getTrn_post_par30_pct() {
		return trn_post_par30_pct;
	}

	public void setTrn_post_par30_pct(Double trn_post_par30_pct) {
		this.trn_post_par30_pct = trn_post_par30_pct;
	}

	public Double getTrn_post_par0_amt() {
		return trn_post_par0_amt;
	}

	public void setTrn_post_par0_amt(Double trn_post_par0_amt) {
		this.trn_post_par0_amt = trn_post_par0_amt;
	}

	public Double getTrn_post_par0_pct() {
		return trn_post_par0_pct;
	}

	public void setTrn_post_par0_pct(Double trn_post_par0_pct) {
		this.trn_post_par0_pct = trn_post_par0_pct;
	}

	public Double getTrn_post_wof_amt() {
		return trn_post_wof_amt;
	}

	public void setTrn_post_wof_amt(Double trn_post_wof_amt) {
		this.trn_post_wof_amt = trn_post_wof_amt;
	}

	public Double getTrn_post_wof_pct() {
		return trn_post_wof_pct;
	}

	public void setTrn_post_wof_pct(Double trn_post_wof_pct) {
		this.trn_post_wof_pct = trn_post_wof_pct;
	}

	public Double getRcr_loan_amt() {
		return rcr_loan_amt;
	}

	public void setRcr_loan_amt(Double rcr_loan_amt) {
		this.rcr_loan_amt = rcr_loan_amt;
	}

	public Integer getRcr_client_act() {
		return rcr_client_act;
	}

	public void setRcr_client_act(Integer rcr_client_act) {
		this.rcr_client_act = rcr_client_act;
	}

	public Double getRcr_sav_amt() {
		return rcr_sav_amt;
	}

	public void setRcr_sav_amt(Double rcr_sav_amt) {
		this.rcr_sav_amt = rcr_sav_amt;
	}

	public Integer getRcr_sav_act() {
		return rcr_sav_act;
	}

	public void setRcr_sav_act(Integer rcr_sav_act) {
		this.rcr_sav_act = rcr_sav_act;
	}

	public Double getRcr_par30_amt() {
		return rcr_par30_amt;
	}

	public void setRcr_par30_amt(Double rcr_par30_amt) {
		this.rcr_par30_amt = rcr_par30_amt;
	}

	public Double getRcr_par30_pct() {
		return rcr_par30_pct;
	}

	public void setRcr_par30_pct(Double rcr_par30_pct) {
		this.rcr_par30_pct = rcr_par30_pct;
	}

	public Double getRcr_par0_amt() {
		return rcr_par0_amt;
	}

	public void setRcr_par0_amt(Double rcr_par0_amt) {
		this.rcr_par0_amt = rcr_par0_amt;
	}

	public Double getRcr_par0_pct() {
		return rcr_par0_pct;
	}

	public void setRcr_par0_pct(Double rcr_par0_pct) {
		this.rcr_par0_pct = rcr_par0_pct;
	}

	public Double getRcr_wof_amt() {
		return rcr_wof_amt;
	}

	public void setRcr_wof_amt(Double rcr_wof_amt) {
		this.rcr_wof_amt = rcr_wof_amt;
	}

	public Double getRcr_wof_pct() {
		return rcr_wof_pct;
	}

	public void setRcr_wof_pct(Double rcr_wof_pct) {
		this.rcr_wof_pct = rcr_wof_pct;
	}

	public Double getRcr_post_loan_amt() {
		return rcr_post_loan_amt;
	}

	public void setRcr_post_loan_amt(Double rcr_post_loan_amt) {
		this.rcr_post_loan_amt = rcr_post_loan_amt;
	}

	public Integer getRcr_post_client_act() {
		return rcr_post_client_act;
	}

	public void setRcr_post_client_act(Integer rcr_post_client_act) {
		this.rcr_post_client_act = rcr_post_client_act;
	}

	public Double getRcr_post_sav_amt() {
		return rcr_post_sav_amt;
	}

	public void setRcr_post_sav_amt(Double rcr_post_sav_amt) {
		this.rcr_post_sav_amt = rcr_post_sav_amt;
	}

	public Integer getRcr_post_sav_act() {
		return rcr_post_sav_act;
	}

	public void setRcr_post_sav_act(Integer rcr_post_sav_act) {
		this.rcr_post_sav_act = rcr_post_sav_act;
	}

	public Double getRcr_post_par30_amt() {
		return rcr_post_par30_amt;
	}

	public void setRcr_post_par30_amt(Double rcr_post_par30_amt) {
		this.rcr_post_par30_amt = rcr_post_par30_amt;
	}

	public Double getRcr_post_par30_pct() {
		return rcr_post_par30_pct;
	}

	public void setRcr_post_par30_pct(Double rcr_post_par30_pct) {
		this.rcr_post_par30_pct = rcr_post_par30_pct;
	}

	public Double getRcr_post_par0_amt() {
		return rcr_post_par0_amt;
	}

	public void setRcr_post_par0_amt(Double rcr_post_par0_amt) {
		this.rcr_post_par0_amt = rcr_post_par0_amt;
	}

	public Double getRcr_post_par0_pct() {
		return rcr_post_par0_pct;
	}

	public void setRcr_post_par0_pct(Double rcr_post_par0_pct) {
		this.rcr_post_par0_pct = rcr_post_par0_pct;
	}

	public Double getRcr_post_wof_amt() {
		return rcr_post_wof_amt;
	}

	public void setRcr_post_wof_amt(Double rcr_post_wof_amt) {
		this.rcr_post_wof_amt = rcr_post_wof_amt;
	}

	public Double getRcr_post_wof_pct() {
		return rcr_post_wof_pct;
	}

	public void setRcr_post_wof_pct(Double rcr_post_wof_pct) {
		this.rcr_post_wof_pct = rcr_post_wof_pct;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getChangeOn() {
		return changeOn;
	}

	public void setChangeOn(Date changeOn) {
		this.changeOn = changeOn;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getChangeBy() {
		return changeBy;
	}

	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}

}
