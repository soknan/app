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

import kredit.web.core.util.model.CodeItem;
import kredit.web.opp.data.model.facade.OperationFacade;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="OP_PLAN_CO")
public class OpPlanCo {

	@Id
	Integer id;
	
	private String branchCode;
	
	@CreatedTimestamp
    private Date createOn;

    @Version
    private Date changeOn;
 
    @Column(length=12)
    private String createBy;
    
    @Column(length=12)
    private String changeBy;
    
    @ManyToOne
	@JoinColumn(name = "PLAN_ID")
    OpPlan plan;
    
    @Transient
    SysCo co;
    
    private String co_sname;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planCo")
    List<OpLoan> loans;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planCo")
    List<OpSaving> savings;
    
    @Transient
    private int n_loans;
    
    @Transient
    private int n_savings;
    
    @Transient
    private CodeItem branch;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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

	public OpPlan getPlan() {
		return plan;
	}

	public void setPlan(OpPlan plan) {
		this.plan = plan;
	}

	public SysCo getCo() {
		if(co == null) {
			co = OperationFacade.getCo(co_sname);
		}
		return co;
	}

	public void setCo(SysCo co) {
		this.co = co;
		co_sname = this.co.getShort_name();
	}

	public List<OpLoan> getLoans() {
		return loans;
	}

	public void setLoans(List<OpLoan> loans) {
		this.loans = loans;
	}

	public List<OpSaving> getSavings() {
		return savings;
	}

	public void setSavings(List<OpSaving> savings) {
		this.savings = savings;
	}

	public int getN_loans() {
		if(loans == null) return 0;
		n_loans = loans.size();
		
		return n_loans;
	}

	public void setN_loans(int n_loans) {
		this.n_loans = n_loans;
	}

	public int getN_savings() {
		if(savings == null) return 0;
		n_savings = savings.size();
		return n_savings;
	}

	public void setN_savings(int n_savings) {
		this.n_savings = n_savings;
	}

	public String getCo_sname() {
		return co_sname;
	}

	public void setCo_sname(String co_sname) {
		this.co_sname = co_sname;
	}
    
	public CodeItem getBranch() {
		if(branch == null) {
			branch = OperationFacade.getSubBranch(branchCode);
		}
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
		branchCode = this.branch.getCode();
	}
}
