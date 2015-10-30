package kredit.web.mfi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avaje.ebean.annotation.Formula;

import kredit.web.core.model.Domain;
import kredit.web.mfi.model.facade.MfiFacade;

@Entity
@Table(name = "MFI_CB")
public class CB extends Domain {

	// don't know why ZK needs startDate endDate in CB????? So just add them to
	// avoid such error "property not found exception"

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length = 8)
	String villageID;

	@Column(length = 50)
	String nameEN = "";

	@Column(length = 50)
	String nameKH = "";
	
	@Column(length = 1)
	String recordStat="";
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cb")
	List<CbCycle> cbCycles;

	@Formula(select = "b${ta}.max_cycle_no", join = "LEFT OUTER JOIN VW_MFI_CBCYCLE_MAX_NO b${ta} on b${ta}.cb_id = ${ta}.id")
	Integer lastCycleNo;
	
	@Formula(select = "coalesce(d${ta}.current_cycle_id,0)", join = "LEFT OUTER JOIN (SELECT cb_id, max(id) current_cycle_id FROM mfi_cbcycle group by cb_id) d${ta} on d${ta}.cb_id = ${ta}.id")
	int currentCycleID;

	@Formula(select = "coalesce(c${ta}.count_group,0)", join = "LEFT OUTER JOIN VW_MFI_CBCYCLE_MAX_NO b${ta} on b${ta}.cb_id = ${ta}.id LEFT OUTER JOIN (SELECT cb_id, max(cycle_no) cycle_no, count_group FROM VW_MFI_CBCYCLE_COUNT_GROUP GROUP BY cb_id, count_group) c${ta} ON c${ta}.cb_id = ${ta}.id AND c${ta}.cycle_no = b${ta}.max_cycle_no")
	Integer countGroup;

	// @Formula(select = "d${ta}.village", join =
	// "LEFT OUTER JOIN addr d${ta} ON d${ta}.id = TO_NUMBER(village_id)")
	String villageName = "";
	
	

	Integer villageSeq;

	@Transient
	CbCycle currentCbCycle;
	
	@Transient
	List<CbCycle> prevCbCycles;
	
	@Transient
	String recordStatDes;

	/**
	 * @return the nameEN
	 */
	public String getNameEN() {
		return nameEN;
	}

	/**
	 * @param nameEN
	 *            the nameEN to set
	 */
	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	/**
	 * @return the nameKH
	 */
	public String getNameKH() {
		return nameKH;
	}

	/**
	 * @param nameKH
	 *            the nameKH to set
	 */
	public void setNameKH(String nameKH) {
		this.nameKH = nameKH;
	}

	/**
	 * @return the cbCycles
	 */
	public List<CbCycle> getCbCycles() {
		return cbCycles;
	}

	/**
	 * @param cbCycles
	 *            the cbCycles to set
	 */
	public void setCbCycles(List<CbCycle> cbCycles) {
		this.cbCycles = cbCycles;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the villageID
	 */
	public String getVillageID() {
		return villageID;
	}

	/**
	 * @param villageID
	 *            the villageID to set
	 */
	public void setVillageID(String villageID) {
		this.villageID = villageID;
	}

	/**
	 * @return the lastCycleNo
	 */
	public Integer getLastCycleNo() {
		return lastCycleNo;
	}

	/**
	 * @param lastCycleNo
	 *            the lastCycleNo to set
	 */
	public void setLastCycleNo(Integer lastCycleNo) {
		this.lastCycleNo = lastCycleNo;
	}

	/**
	 * @return the countGroup
	 */
	public Integer getCountGroup() {
		return countGroup;
	}

	/**
	 * @param countGroup
	 *            the countGroup to set
	 */
	public void setCountGroup(Integer countGroup) {
		this.countGroup = countGroup;
	}

	/**
	 * @return the villageName
	 */
	public String getVillageName() {
		return villageName;
	}

	/**
	 * @param villageName
	 *            the villageName to set
	 */
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	/**
	 * @return the villageSeq
	 */
	public Integer getVillageSeq() {
		return villageSeq;
	}

	/**
	 * @param villageSeq
	 *            the villageSeq to set
	 */
	public void setVillageSeq(Integer villageSeq) {
		this.villageSeq = villageSeq;
	}

	/**
	 * @return the currentCycleID
	 */
	public Integer getCurrentCycleID() {
		return currentCycleID;
	}

	/**
	 * @return the currentCbCycle
	 */
	public CbCycle getCurrentCbCycle() {

		/*
		 * if(currentCbCycle == null){ if(currentCycleID > 0){ currentCbCycle =
		 * Ebean.find(CbCycle.class).select(
		 * "cycleNo, startDate, endDate, leaderCIF, leaderName"
		 * ).where().idEq(currentCycleID).findUnique(); }else{ currentCbCycle =
		 * new CbCycle(); currentCbCycle.setCb(this); } }
		 */

		if (currentCbCycle == null) {
			if(currentCycleID > 0){
				currentCbCycle = MfiFacade.getCbCycle(currentCycleID);
				currentCbCycle.setCb(this);
			}else{
				currentCbCycle = new CbCycle();
				currentCbCycle.setCb(this);
			}
		}
		return currentCbCycle;
	}

	/**
	 * @param currentCbCycle
	 *            the currentCbCycle to set
	 */
	public void setCurrentCbCycle(CbCycle currentCbCycle) {
		this.currentCbCycle = currentCbCycle;
	}

	/**
	 * @return the prevCbCycles
	 */
	public List<CbCycle> getPrevCbCycles() {
		if(prevCbCycles != null)
			return prevCbCycles;
		
		if(currentCycleID == 0)
			return new ArrayList<CbCycle>();
		
		prevCbCycles = MfiFacade.getPrevCbCycles(this.getId(), currentCycleID);
		if(prevCbCycles == null) 
			prevCbCycles = new ArrayList<CbCycle>();
		
		return prevCbCycles;
	}

	/**
	 * @param prevCbCycles the prevCbCycles to set
	 */
	public void setPrevCbCycles(List<CbCycle> prevCbCycles) {
		this.prevCbCycles = prevCbCycles;
	}

	/**
	 * @param currentCycleID the currentCycleID to set
	 */
	public void setCurrentCycleID(int currentCycleID) {
		this.currentCycleID = currentCycleID;
	}
	
	
	public String getRecordStat() {
		return recordStat;
	}

	public void setRecordStat(String recordStat) {
		this.recordStat = recordStat;
	}
	

	public String getRecordStatDes() {
		if(recordStat==null) return recordStatDes;
		switch (recordStat) {
		case "O":
			recordStatDes = "Open";
			break;
		case "C":
			recordStatDes = "Close";
			break;
		}
		return recordStatDes;
	}

	public void setRecordStatDes(String recordStatDes) {
		this.recordStatDes = recordStatDes;
	}

	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "BR:" );
        sb.append(this.getBrCd());
        sb.append( "|" );
        sb.append( "CB EN:" );
        sb.append(this.getNameEN());
        sb.append( "|" );
        sb.append( "CB KH:" );
        sb.append(this.getNameKH());
        
        return sb.toString();
    }
}
