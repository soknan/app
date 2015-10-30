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
@Table(name="OP_LOAN")
public class OpLoan {
	
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "loan")
    List<OpLoanDetail> loanDetails;

    private String auth_status;
    private String note;
    
    @Transient
    private int n_loan;
    
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

	public List<OpLoanDetail> getLoanDetails() {
		return loanDetails;
	}

	public void setLoanDetails(List<OpLoanDetail> loanDetails) {
		this.loanDetails = loanDetails;
	}

	public int getN_loan() {
		n_loan = 0;
		if(loanDetails != null) {
			n_loan = loanDetails.size();
		}
		return n_loan;
	}

	public void setN_loan(int n_loan) {
		this.n_loan = n_loan;
	}

}
