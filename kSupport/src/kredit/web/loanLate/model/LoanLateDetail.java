package kredit.web.loanLate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="LOAN_LATE_DETAIL")
public class LoanLateDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;
	
	private String accNo;
	private String cosName;
	private Double overduePrin;
	private Double overdueInt;
	private Integer overdueDay;
	private String situation;
	private String status;
	private Integer recordNo;
	private String hq_situation;

    @CreatedTimestamp
    private Date createOn;

    @Version
    private Date changeOn;
 
    @Column(length=12)
    private String createBy;
    
    @Column(length=12)
    private String changeBy;
    
    

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getCosName() {
		return cosName;
	}

	public void setCosName(String cosName) {
		this.cosName = cosName;
	}

	public Double getOverduePrin() {
		return overduePrin;
	}

	public void setOverduePrin(Double overduePrin) {
		this.overduePrin = overduePrin;
	}

	public Double getOverdueInt() {
		return overdueInt;
	}

	public void setOverdueInt(Double overdueInt) {
		this.overdueInt = overdueInt;
	}

	public Integer getOverdueDay() {
		return overdueDay;
	}

	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(Integer recordNo) {
		this.recordNo = recordNo;
	}

	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the changeBy
	 */
	public String getChangeBy() {
		return changeBy;
	}

	/**
	 * @param changeBy the changeBy to set
	 */
	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}

	/**
	 * @return the createOn
	 */
	public Date getCreateOn() {
		return createOn;
	}

	/**
	 * @param createOn the createOn to set
	 */
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	/**
	 * @return the changeOn
	 */
	public Date getChangeOn() {
		return changeOn;
	}

	/**
	 * @param changeOn the changeOn to set
	 */
	public void setChangeOn(Date changeOn) {
		this.changeOn = changeOn;
	}

	public String getHq_situation() {
		return hq_situation;
	}

	public void setHq_situation(String hq_situation) {
		this.hq_situation = hq_situation;
	}

}
