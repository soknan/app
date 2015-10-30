package kredit.web.opp.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import kredit.web.opp.data.model.facade.OperationFacade;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="OP_PLAN")
public class OpPlan {

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

	private Integer plan_year;
	private String plan_month;
	private Integer year_01;
	private Integer year_02;
	private Integer year_03;
	
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "plan")
	@Transient
	List<OpPlanCo> planCo;
	
	@Transient
	List<OpStaff> planStaff;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlan_year() {
		return plan_year;
	}
	
	public void setPlan_year(Integer plan_year) {
		this.plan_year = plan_year;
	}
	
	public String getPlan_month() {
		return plan_month;
	}
	
	public void setPlan_month(String plan_month) {
		this.plan_month = plan_month;
	}
	
	public Integer getYear_01() {
		return year_01;
	}
	
	public void setYear_01(Integer year_01) {
		this.year_01 = year_01;
	}
	
	public Integer getYear_02() {
		return year_02;
	}
	
	public void setYear_02(Integer year_02) {
		this.year_02 = year_02;
	}
	
	public Integer getYear_03() {
		return year_03;
	}
	
	public void setYear_03(Integer year_03) {
		this.year_03 = year_03;
	}

	public List<OpPlanCo> getPlanCo() {
		if(planCo == null) {
			planCo = new ArrayList<OpPlanCo>(OperationFacade.getPlanCoList());
		}
		return planCo;
	}

	public void setPlanCo(List<OpPlanCo> planCo) {
		this.planCo = planCo;
	}
	
	public List<OpStaff> getPlanStaff() {
		if(planStaff == null) {
			planStaff = new ArrayList<OpStaff>(OperationFacade.getStaffList());
		}
		
		return planStaff;
	}

	public void setPlanStaff(List<OpStaff> planStaff) {
		this.planStaff = planStaff;
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
