package kredit.web.opp.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import kredit.web.core.util.model.CodeItem;
import kredit.web.opp.data.model.facade.OperationFacade;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.Formula;

@Entity
@Table(name="OP_STAFF")
public class OpStaff {

	@Id
	Integer id;
	
	private String staff_code;
	private String branch_code;
	private Integer ny0 = 0;
	private Integer ny1q1 = 0;
	private Integer ny1q2 = 0;
	private Integer ny1q3 = 0;
	private Integer ny1q4 = 0;
	private Integer ny2 = 0;
	private Integer ny3 = 0;
	
	@ManyToOne
	@JoinColumn(name = "PLAN_ID")
    private OpPlan plan;
	
	@Formula(select = "b${ta}.DESC_EN", join = "INNER JOIN SYS_LOV b${ta} ON b${ta}.CODE = ${ta}.STAFF_CODE AND b${ta}.TYPE = 'OP_STAFF_CODE'")
	private String staffDesc;
	
	@Transient
    private CodeItem position;
	
	@Transient
	private Integer sY1 = 0;
	
	@Transient
	private Integer sY2 = 0;
	
	@Transient
	private Integer sY3 = 0;
	
	@CreatedTimestamp
    private Date createOn;

    @Version
    private Date changeOn;
 
    @Column(length=12)
    private String createBy;
    
    @Column(length=12)
    private String changeBy;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStaff_code() {
		return staff_code;
	}

	public void setStaff_code(String staff_code) {
		this.staff_code = staff_code;
	}

	public String getBranch_code() {
		return branch_code;
	}

	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}

	public Integer getNy0() {
		return ny0;
	}

	public void setNy0(Integer ny0) {
		this.ny0 = ny0;
	}

	public Integer getNy1q1() {
		return ny1q1;
	}

	public void setNy1q1(Integer ny1q1) {
		this.ny1q1 = ny1q1;
	}

	public Integer getNy1q2() {
		return ny1q2;
	}

	public void setNy1q2(Integer ny1q2) {
		this.ny1q2 = ny1q2;
	}

	public Integer getNy1q3() {
		return ny1q3;
	}

	public void setNy1q3(Integer ny1q3) {
		this.ny1q3 = ny1q3;
	}

	public Integer getNy1q4() {
		return ny1q4;
	}

	public void setNy1q4(Integer ny1q4) {
		this.ny1q4 = ny1q4;
	}

	public Integer getNy2() {
		return ny2;
	}

	public void setNy2(Integer ny2) {
		this.ny2 = ny2;
	}

	public Integer getNy3() {
		return ny3;
	}

	public void setNy3(Integer ny3) {
		this.ny3 = ny3;
	}

	public OpPlan getPlan() {
		return plan;
	}

	public void setPlan(OpPlan plan) {
		this.plan = plan;
	}

	public String getStaffDesc() {
		return staffDesc;
	}

	public void setStaffDesc(String staffDesc) {
		this.staffDesc = staffDesc;
	}

	public Integer getsY1() {
		return sY1;
	}

	public void setsY1(Integer sY1) {
		this.sY1 = sY1;
	}

	public Integer getsY2() {
		return sY2;
	}

	public void setsY2(Integer sY2) {
		this.sY2 = sY2;
	}

	public Integer getsY3() {
		return sY3;
	}

	public void setsY3(Integer sY3) {
		this.sY3 = sY3;
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

	public CodeItem getPosition() {
		if(position == null) {
			position = new CodeItem();
			
			if(staff_code != null) {
				position = OperationFacade.getPosition(staff_code, OpCommon.OP_STAFF_CODE);
			}
		}
		
		return position;
	}

	public void setPosition(CodeItem position) {
		this.position = position;
		staff_code = this.position.getCode();
	}
	
}
