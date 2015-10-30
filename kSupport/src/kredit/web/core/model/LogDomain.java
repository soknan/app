package kredit.web.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.avaje.ebean.annotation.CreatedTimestamp;

@MappedSuperclass
public class LogDomain {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Integer id;
	
	@Column(name="BRANCH_CODE", length=3)
	String brCd;

	@CreatedTimestamp
    private Date createOn;
	
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
	 * @return the brCd
	 */
	public String getBrCd() {
		return brCd;
	}

	/**
	 * @param brCd the brCd to set
	 */
	public void setBrCd(String brCd) {
		this.brCd = brCd;
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
}
