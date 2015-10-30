package kredit.web.mfi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avaje.ebean.annotation.Formula;

import kredit.web.core.model.Domain;
import kredit.web.core.util.Common;

@Entity
@Table(name="MFI_LEADER")
public class Leader extends Domain{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length=9)
	String cif;
	
	@Formula(select = "c${ta}.FULL_NAME", join = "LEFT JOIN sttm_customer c${ta} ON c${ta}.customer_no = ${ta}.cif")
	String leaderName;
	
	@Formula(select = "d${ta}.FULL_NAME_KH", join = "LEFT JOIN VW_CUSTOMER_NAME_KH d${ta} ON d${ta}.customer_no = ${ta}.cif")
	String leaderNameKH;
	
	@ManyToOne
	@JoinColumn(name="GROUP_ID")
	Group group;
	
	@Transient
	int groupID;
	
	@ManyToOne
	@JoinColumn(name="CBCYCLE_ID")
	CbCycle cbCycle;
	
	@Transient
	int cbcycleID;
	
	@Column(name="START_DATE")
	Date startDate;
	
	@Column(name="END_DATE")
	Date endDate;
	
	Integer groupMemberID;

	/**
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the cbCycle
	 */
	public CbCycle getCbCycle() {
		return cbCycle;
	}

	/**
	 * @param cbCycle the cbCycle to set
	 */
	public void setCbCycle(CbCycle cbCycle) {
		this.cbCycle = cbCycle;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param leaderName the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the groupID
	 */
	public int getGroupID() {
		return groupID;
	}

	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	/**
	 * @return the cbcycleID
	 */
	public int getCbcycleID() {
		return cbcycleID;
	}

	/**
	 * @param cbcycleID the cbcycleID to set
	 */
	public void setCbcycleID(int cbcycleID) {
		this.cbcycleID = cbcycleID;
	}

	/**
	 * @return the groupMemberID
	 */
	public Integer getGroupMemberID() {
		return groupMemberID;
	}

	/**
	 * @param groupMemberID the groupMemberID to set
	 */
	public void setGroupMemberID(Integer groupMemberID) {
		this.groupMemberID = groupMemberID;
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
        sb.append( "CIF:" );
        sb.append(this.getCif());
        sb.append( "|" );
        sb.append(this.getLeaderName());
        sb.append( "|" );
        sb.append( "StartDt:" );
        sb.append(Common.getDateFormat(startDate, "dd-MM-yyyy"));
        sb.append( "|" );
        sb.append( "EndDt:" );
        sb.append(Common.getDateFormat(endDate, "dd-MM-yyyy"));
        sb.append( "|" );
        sb.append( "GroupID:" );
        sb.append(this.getGroupID());
        sb.append( "|" );
        sb.append( "CbCycleID:" );
        sb.append(this.getCbcycleID());
        
        return sb.toString();
    }
	
	public String toStringSummary()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "CIF:" );
        sb.append(this.getCif());
        sb.append( "|" );
        sb.append(this.getLeaderName());
        sb.append( "|" );
        sb.append( "MbrID:" );
        sb.append(groupMemberID);
        sb.append( "|" );
        sb.append( "StartDt:" );
        sb.append(Common.getDateFormat(startDate, "dd-MM-yyyy"));
        sb.append( "|" );
        sb.append( "EndDt:" );
        sb.append(Common.getDateFormat(endDate, "dd-MM-yyyy"));
        
        return sb.toString();
    }

	/**
	 * @return the leaderNameKH
	 */
	public String getLeaderNameKH() {
		return leaderNameKH;
	}

	/**
	 * @param leaderNameKH the leaderNameKH to set
	 */
	public void setLeaderNameKH(String leaderNameKH) {
		this.leaderNameKH = leaderNameKH;
	}
	
}
