package kredit.web.opp.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import kredit.web.core.util.model.CodeItem;
import kredit.web.opp.data.model.facade.OperationFacade;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="OP_DUMMY_CO")
public class SysCo {

	@Id
	Integer id;
	
	private String short_name;
	private String branch_code;
	
	@Transient
	private String full_name;
	
	@CreatedTimestamp
    private Date createOn;

    @Version
    private Date changeOn;
 
    @Column(length=12)
    private String createBy;
    
    @Column(length=12)
    private String changeBy;
    
    @Transient
    private CodeItem branch;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getShort_name() {
		return short_name;
	}
	
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	
	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	
	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
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
	
	public CodeItem getBranch() {
		if(branch == null) {
			branch = OperationFacade.getSubBranch(branch_code);
		}
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
		branch_code = this.branch.getCode();
	}
}
