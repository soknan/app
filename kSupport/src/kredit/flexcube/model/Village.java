package kredit.flexcube.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="sttm_cust_add_main_bfsi")
public class Village {
	
	@Id
	Integer id;
	
	@Transient
	String strID = "%";
	
	String province = "%";
	String district = "%";
	String commune = "%";
	String village = "%";
	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * @return the commune
	 */
	public String getCommune() {
		return commune;
	}
	/**
	 * @param commune the commune to set
	 */
	public void setCommune(String commune) {
		this.commune = commune;
	}
	/**
	 * @return the village
	 */
	public String getVillage() {
		return village;
	}
	/**
	 * @param village the village to set
	 */
	public void setVillage(String village) {
		this.village = village;
	}
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
	 * @return the strID
	 */
	public String getStrID() {
		return strID;
	}
	/**
	 * @param strID the strID to set
	 */
	public void setStrID(String strID) {
		this.strID = strID;
	}
}
