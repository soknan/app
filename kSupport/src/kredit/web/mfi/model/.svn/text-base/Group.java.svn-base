package kredit.web.mfi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.EnumValue;
import com.avaje.ebean.annotation.Formula;

import kredit.web.core.model.Domain;
import kredit.web.mfi.model.facade.MfiFacade;

@Entity
@Table(name="MFI_GROUP")
public class Group extends Domain{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Type{
		@EnumValue("CB")
		CB,
		@EnumValue("TB")
		TB,
		@EnumValue("SG")
		SG
	}
	
	String villageID;
	
	/*
	 * Type of Group
	 * CB: VSU
	 * TB: Trust Bank
	 * SG: Group
	 * 
	 */
	@Enumerated(value=EnumType.STRING)
	Type type;
	
	@Transient
	String groupType;
	
	@Transient 
	String groupStatus;
	
	@Column(length = 1)
	String recordStat="";
	
	@Transient
	String recordStatDes = "";
	
	/*
	 * Group Sequence: display as 4 digits, e.g. 0001
	 */
	Integer groupSeq;
	
	/*
	 * Cycle Sequence: display as 2 digits, e.g. 01
	 */
	Integer cycleSeq;
	
	String groupAccNo;
	
	Integer leaderID;
	
	String leaderCIF = "";
	
	String refGrp;
	
	String refBank;
	
	@Formula(select = "coalesce(fc${ta}.full_name,' ')", join = "LEFT OUTER JOIN sttm_customer fc${ta} ON ${ta}.leader_cif = fc${ta}.customer_no")
	String leaderName;
	
	@Formula(select = "coalesce(v2_${ta}.FULL_NAME_KH,' ')", join = "LEFT OUTER JOIN VW_CUSTOMER_NAME_KH v2_${ta} ON ${ta}.leader_cif = v2_${ta}.customer_no")
	String leaderNameKH;
	
	@Formula(select = "e${ta}.village", join = "LEFT OUTER JOIN sttm_cust_add_main_bfsi e${ta} ON e${ta}.id = ${ta}.village_id")
	String villageName = "";
	
	@Formula(select = "coalesce(v4_${ta}.name_en,'')", join = "LEFT OUTER JOIN MFI_CBCYCLE v3_${ta} ON v3_${ta}.id = ${ta}.cbcycle_id LEFT OUTER JOIN MFI_CB v4_${ta} ON v3_${ta}.cb_id = v4_${ta}.id")
	String cbName;
	
	@Formula(select = "coalesce(e${ta}.count_leader,0)", join = "LEFT OUTER JOIN (SELECT group_id, COUNT(id) count_leader FROM MFI_LEADER GROUP BY group_id) e${ta} ON e${ta}.group_id = ${ta}.id")
	int countLeader;
	
	@Formula(select = "coalesce(f${ta}.count_mbr,0)", join = "LEFT OUTER JOIN (SELECT group_id, COUNT(distinct cif) count_mbr FROM MFI_GROUP_MEMBER GROUP BY group_id) f${ta} ON f${ta}.group_id = ${ta}.id")
	int countMember;
	
	@Formula(select = "coalesce(to_char(v3_${ta}.cycle_no),'')", join = "LEFT OUTER JOIN MFI_CBCYCLE v3_${ta} ON v3_${ta}.id = ${ta}.cbcycle_id LEFT OUTER JOIN MFI_CB v4_${ta} ON v3_${ta}.cb_id = v4_${ta}.id")
	String cbCycleNo;
	
	@ManyToOne
	@JoinColumn(name="CBCYCLE_ID")
	CbCycle cbCycle;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="group")
	List<GroupMember> groupMemers;
	
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="group")
	List<Leader> leaders;

	@Transient
	Leader currentLeader;
	
	@Transient
	List<Leader> historyLeaders;
	
	@Transient
	String notifyDirty;
	
	Integer amountMaxIndiv = 0;
	
	@Transient
	String amountMaxIndivTxt = "សូនរៀល";
	
	/**
	 * @return the groupSeq
	 */
	public Integer getGroupSeq() {
		return groupSeq;
	}

	/**
	 * @param groupSeq the groupSeq to set
	 */
	public void setGroupSeq(Integer groupSeq) {
		this.groupSeq = groupSeq;
	}

	/**
	 * @return the cycleSeq
	 */
	public Integer getCycleSeq() {
		return cycleSeq;
	}

	/**
	 * @param cycleSeq the cycleSeq to set
	 */
	public void setCycleSeq(Integer cycleSeq) {
		this.cycleSeq = cycleSeq;
	}

	/**
	 * @return the groupMemers
	 */
	public List<GroupMember> getGroupMemers() {
		if(groupMemers == null){
			groupMemers = new ArrayList<GroupMember>();
		}
		return groupMemers;
	}

	/**
	 * @param groupMemers the groupMemers to set
	 */
	public void setGroupMemers(List<GroupMember> groupMemers) {
		this.groupMemers = groupMemers;
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
	 * @return the villageID
	 */
	public String getVillageID() {
		if(villageID == null) villageID = "";
		return villageID;
	}

	/**
	 * @param villageID the villageID to set
	 */
	public void setVillageID(String villageID) {
		this.villageID = villageID;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the groupAccNo
	 */
	public String getGroupAccNo() {
		return groupAccNo;
	}

	/**
	 * @param groupAccNo the groupAccNo to set
	 */
	public void setGroupAccNo(String groupAccNo) {
		this.groupAccNo = groupAccNo;
	}

	/**
	 * @return the leaders
	 */
	public List<Leader> getLeaders() {
		return leaders;
	}

	/**
	 * @param leaders the leaders to set
	 */
	public void setLeaders(List<Leader> leaders) {
		this.leaders = leaders;
	}

	/**
	 * @return the leaderID
	 */
	public Integer getLeaderID() {
		return leaderID;
	}

	/**
	 * @param leaderID the leaderID to set
	 */
	public void setLeaderID(Integer leaderID) {
		this.leaderID = leaderID;
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
	 * @return the villageName
	 */
	public String getVillageName() {
		if(villageName == null) villageName = "";
		return villageName;
	}

	/**
	 * @param villageName the villageName to set
	 */
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	/**
	 * @return the leaderCIF
	 */
	public String getLeaderCIF() {
		return leaderCIF;
	}

	/**
	 * @param leaderCIF the leaderCIF to set
	 */
	public void setLeaderCIF(String leaderCIF) {
		this.leaderCIF = leaderCIF;
	}

	/**
	 * @return the currentLeader
	 */
	public Leader getCurrentLeader() {
		if (currentLeader == null) {
			if (leaderID != null) {
				currentLeader = MfiFacade.getLeader(leaderID);
				if(currentLeader == null)
					currentLeader = new Leader();
				currentLeader.setGroup(this);
			} else {
				currentLeader = new Leader();
				currentLeader.setGroup(this);
			}
		}
		return currentLeader;
	}

	/**
	 * @param currentLeader the currentLeader to set
	 */
	public void setCurrentLeader(Leader currentLeader) {
		this.currentLeader = currentLeader;
	}

	/**
	 * @return the historyLeaders
	 */
	public List<Leader> getHistoryLeaders() {
		if (countLeader > 0) {
			historyLeaders = Ebean.find(Leader.class)
					.select("cif, leaderName, startDate, endDate").where()
					.eq("group", this).not(Expr.eq("id", this.leaderID))
					.findList();
		} else {
			historyLeaders = new ArrayList<Leader>();
		}
		return historyLeaders;
	}


	/**
	 * @param historyLeaders the historyLeaders to set
	 */
	public void setHistoryLeaders(List<Leader> historyLeaders) {
		this.historyLeaders = historyLeaders;
	}

	/**
	 * @return the countLeader
	 */
	public int getCountLeader() {
		return countLeader;
	}

	/**
	 * @param countLeader the countLeader to set
	 */
	public void setCountLeader(int countLeader) {
		this.countLeader = countLeader;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		if(groupType != null)
			return groupType;
		
		if(type != null){
			groupType = type.toString();
		}
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the groupStatus
	 */
	public String getGroupStatus() {
		if(groupStatus != null)
			return groupStatus;
		
		if(cycleSeq == null || cycleSeq == 1)
			return "N";
		
		return "E";
	}

	/**
	 * @param groupStatus the groupStatus to set
	 */
	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}
	
	@Transient
	public String getGroupNo(){
		if(groupAccNo == null)
			return "";
		
		return groupAccNo.substring(0, 10);
	}
	
	@Transient
	public Leader addLeader(Leader leader){
		getLeaders().add(leader);
		leader.setGroup(this);
		return leader;
	}
	
	public Leader removeLeader(Leader leader){
		getLeaders().remove(leader);
		leader.setGroup(null);
		return leader;
	}

	/**
	 * @return the countMember
	 */
	public int getCountMember() {
		return countMember;
	}

	/**
	 * @param countMember the countMember to set
	 */
	public void setCountMember(int countMember) {
		this.countMember = countMember;
	}

	/**
	 * @return the cbName
	 */
	public String getCbName() {
		return cbName == null? "": cbName;
	}

	/**
	 * @param cbName the cbName to set
	 */
	public void setCbName(String cbName) {
		this.cbName = cbName;
	}

	/**
	 * @return the cbCycleNo
	 */
	public String getCbCycleNo() {
		return cbCycleNo;
	}

	/**
	 * @param cbCycleNo the cbCycleNo to set
	 */
	public void setCbCycleNo(String cbCycleNo) {
		this.cbCycleNo = cbCycleNo;
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
        sb.append(this.getGroupAccNo());
        sb.append( "|" );
        sb.append( "CB:" );
        sb.append(this.getCbName());
        sb.append( "|" );
        sb.append( "CbCyleNo:" );
        sb.append(this.getCbCycleNo());
        
        return sb.toString();
    }
	
	public String toStringSummary()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append(this.getGroupAccNo());
        
        return sb.toString();
    }

	/**
	 * @return the notifyDirty
	 */
	public String getNotifyDirty() {
		return notifyDirty;
	}

	/**
	 * @param notifyDirty the notifyDirty to set
	 */
	public void setNotifyDirty(String notifyDirty) {
		this.notifyDirty = notifyDirty;
	}

	/**
	 * @return the refGrp
	 */
	public String getRefGrp() {
		return refGrp;
	}

	/**
	 * @param refGrp the refGrp to set
	 */
	public void setRefGrp(String refGrp) {
		this.refGrp = refGrp;
	}

	/**
	 * @return the refBank
	 */
	public String getRefBank() {
		return refBank;
	}

	/**
	 * @param refBank the refBank to set
	 */
	public void setRefBank(String refBank) {
		this.refBank = refBank;
	}

	/**
	 * @return the amountMaxIndiv
	 */
	public Integer getAmountMaxIndiv() {
		return amountMaxIndiv;
	}

	/**
	 * @param amountMaxIndiv the amountMaxIndiv to set
	 */
	public void setAmountMaxIndiv(Integer amountMaxIndiv) {
		this.amountMaxIndiv = amountMaxIndiv;
	}

	/**
	 * @return the amountMaxIndivTxt
	 */
	public String getAmountMaxIndivTxt() {
		return amountMaxIndivTxt;
	}

	/**
	 * @param amountMaxIndivTxt the amountMaxIndivTxt to set
	 */
	public void setAmountMaxIndivTxt(String amountMaxIndivTxt) {
		this.amountMaxIndivTxt = amountMaxIndivTxt;
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
	
}
