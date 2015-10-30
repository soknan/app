package kredit.web.opp.data.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="OP_SAVING")
public class OpSaving {

	@Id
	Integer id;
	
	@CreatedTimestamp
    private Date createOn;

    @Version
    private Date changeOn;
 
    @Column(length=12)
    private String createBy;
    
    @Column(length=12)
    private String changeBy;
    
    @ManyToOne
	@JoinColumn(name = "OP_PLAN_CO_ID")
    OpPlanCo planCo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "saving")
    List<OpSavingDetail> savingDetails;
    
    private String auth_status;
    private String note;
    
    @Transient
    private int n_saving;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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
	
	public OpPlanCo getPlanCo() {
		return planCo;
	}
	
	public void setPlanCo(OpPlanCo planCo) {
		this.planCo = planCo;
	}
	
	public String getAuth_status() {
		return auth_status;
	}

	public void setAuth_status(String auth_status) {
		this.auth_status = auth_status;
	}

	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	public List<OpSavingDetail> getSavingDetails() {
		return savingDetails;
	}

	public void setSavingDetails(List<OpSavingDetail> savingDetails) {
		this.savingDetails = savingDetails;
	}

	public int getN_saving() {
		n_saving = 0;
		if(savingDetails != null) {
			n_saving = savingDetails.size();
		}
		return n_saving;
	}

	public void setN_saving(int n_saving) {
		this.n_saving = n_saving;
	}

}
