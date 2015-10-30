package kredit.web.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.avaje.ebean.annotation.CreatedTimestamp;

@MappedSuperclass
public class DomainNoBranch {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;

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
}
