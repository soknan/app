package kredit.web.mfi.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.annotation.Formula;

import kredit.web.core.model.Domain;
import kredit.web.mfi.model.facade.MfiFacade;

@Entity
@Table(name = "MFI_CBCYCLE")
public class CbCycle extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Integer cycleNo;

	Integer amountMaxIndiv = 0;
	String amountMaxIndivTxt = "សូនរៀល";

	Integer leaderID;

	String leaderCIF = "";

	@Transient
	Leader currentLeader;

	@Transient
	List<Leader> historyLeaders;

	@Formula(select = "coalesce(e${ta}.count_leader,0)", join = "LEFT OUTER JOIN (SELECT cbcycle_id, COUNT(id) count_leader FROM MFI_LEADER GROUP BY cbcycle_id) e${ta} ON e${ta}.cbcycle_id = ${ta}.id")
	int countLeader;

	@Formula(select = "coalesce(f${ta}.count_group,0)", join = "LEFT JOIN (SELECT cbcycle_id, count_group FROM VW_MFI_CBCYCLE_COUNT_GROUP) f${ta} ON f${ta}.cbcycle_id = ${ta}.id")
	Integer countGroup;

	@Column(length = 8)
	String villageID;

	Date startDate;
	Date endDate;

	@Formula(select = "coalesce(c${ta}.FULL_NAME,'')", join = "LEFT JOIN sttm_customer c${ta} ON c${ta}.customer_no = ${ta}.leader_cif")
	String leaderName = "";

	@ManyToOne
	@JoinColumn(name = "CB_ID")
	CB cb;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cbCycle")
	List<Leader> leaders;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cbCycle")
	List<Group> groups;

	public Leader addLeader(Leader leader) {
		getLeaders().add(leader);
		leader.setCbCycle(this);
		return leader;
	}

	public Leader removeLeader(Leader leader) {
		getLeaders().remove(leader);
		leader.setCbCycle(null);
		return leader;
	}

	/**
	 * @return the cycleNo
	 */
	public Integer getCycleNo() {
		return cycleNo;
	}

	/**
	 * @param cycleNo
	 *            the cycleNo to set
	 */
	public void setCycleNo(Integer cycleNo) {
		this.cycleNo = cycleNo;
	}

	/**
	 * @return the leaderID
	 */
	public Integer getLeaderID() {
		return leaderID;
	}

	/**
	 * @param leaderID
	 *            the leaderID to set
	 */
	public void setLeaderID(Integer leaderID) {
		this.leaderID = leaderID;
	}

	/**
	 * @return the groups
	 */
	public List<Group> getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the cb
	 */
	public CB getCb() {
		return cb;
	}

	/**
	 * @param cb
	 *            the cb to set
	 */
	public void setCb(CB cb) {
		this.cb = cb;
	}

	/**
	 * @return the leaders
	 */
	public List<Leader> getLeaders() {
		return leaders;
	}

	/**
	 * @param leaders
	 *            the leaders to set
	 */
	public void setLeaders(List<Leader> leaders) {
		this.leaders = leaders;
	}

	/**
	 * @return the leaderCIF
	 */
	public String getLeaderCIF() {
		return leaderCIF;
	}

	/**
	 * @param leaderCIF
	 *            the leaderCIF to set
	 */
	public void setLeaderCIF(String leaderCIF) {
		this.leaderCIF = leaderCIF;
	}

	/**
	 * @return the leaderName
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * @param leaderName
	 *            the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * @return the currentLeader
	 */
	public Leader getCurrentLeader() {
		if (currentLeader == null) {
			if (leaderID != null) {
				currentLeader = MfiFacade.getLeader(leaderID);
				if (currentLeader == null)
					currentLeader = new Leader();
				currentLeader.setCbCycle(this);
			} else {
				currentLeader = new Leader();
				currentLeader.setCbCycle(this);
			}
		}
		return currentLeader;
	}

	/**
	 * @param currentLeader
	 *            the currentLeader to set
	 */
	public void setCurrentLeader(Leader currentLeader) {
		this.currentLeader = currentLeader;
	}

	/**
	 * @return the countLeader
	 */
	public int getCountLeader() {
		return countLeader;
	}

	/**
	 * @param countLeader
	 *            the countLeader to set
	 */
	public void setCountLeader(int countLeader) {
		this.countLeader = countLeader;
	}

	/**
	 * @return the historyLeaders
	 */
	public List<Leader> getHistoryLeaders() {
		if (countLeader > 0) {
			historyLeaders = Ebean.find(Leader.class)
					.select("cif, leaderName, startDate, endDate").where()
					.eq("cbCycle", this).not(Expr.eq("id", this.leaderID))
					.findList();
		} else {
			historyLeaders = new ArrayList<Leader>();
		}
		return historyLeaders;
	}

	/**
	 * @param historyLeaders
	 *            the historyLeaders to set
	 */
	public void setHistoryLeaders(List<Leader> historyLeaders) {
		this.historyLeaders = historyLeaders;
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
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the amountMaxIndiv
	 */
	public Integer getAmountMaxIndiv() {
		return amountMaxIndiv;
	}

	/**
	 * @param amountMaxIndiv
	 *            the amountMaxIndiv to set
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
	 * @param amountMaxIndivTxt
	 *            the amountMaxIndivTxt to set
	 */
	public void setAmountMaxIndivTxt(String amountMaxIndivTxt) {
		this.amountMaxIndivTxt = amountMaxIndivTxt;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID:");
		sb.append(this.getId());
		sb.append("|");
		sb.append("BR:");
		sb.append(this.getBrCd());
		sb.append("|");
		sb.append("CbCycle:");
		sb.append(this.getCycleNo());
		sb.append("|");
		sb.append("VillageID:");
		sb.append(this.getVillageID());
		sb.append("|");
		sb.append("AmountMaxIndiv:");
		sb.append(this.getAmountMaxIndiv());
		
		return sb.toString();
	}
}
