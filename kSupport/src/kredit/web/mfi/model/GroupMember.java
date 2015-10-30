package kredit.web.mfi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.avaje.ebean.annotation.Formula;

import kredit.web.core.model.Domain;
import kredit.web.mfi.model.facade.MfiFacade;

@Entity
@Table(name = "MFI_GROUP_MEMBER")
public class GroupMember extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length = 9)
	String cif = "";

	@Column(length = 20)
	String account = "";

	@ManyToOne
	@JoinColumn(name = "GROUP_ID")
	Group group;
	
	String refBank;
	
	String refAccNo;
	
	@Formula(select = "coalesce(f${ta}.account_status,' ')", join = "LEFT OUTER JOIN cltb_account_apps_master f${ta} ON f${ta}.account_number = ${ta}.account")
	String status;
	
	@Transient
	String statusDes= "";

	@Formula(select = "f${ta}.group_acc_no", join = "LEFT OUTER JOIN mfi_group f${ta} ON f${ta}.id = ${ta}.group_id")
	String groupAccNo;

	@Formula(select = "c${ta}.FULL_NAME", join = "LEFT OUTER JOIN sttm_customer c${ta} ON c${ta}.customer_no = ${ta}.cif")
	String fullName = "";
	
	@Formula(select = "p${ta}.FULL_NAME_KH", join = "LEFT OUTER JOIN VW_CUSTOMER_NAME_KH p${ta} ON p${ta}.customer_no = ${ta}.cif")
	String fullNameKH = "";
	
	@Formula(select = "q${ta}.DATE_OF_BIRTH", join = "LEFT OUTER JOIN STTM_CUST_PERSONAL q${ta} ON q${ta}.customer_no = ${ta}.cif")
	Date dateOfBirth;

	@OneToOne
	@JoinColumn(name = "MOBILIZER_ID")
	Mobilizer mobilizer;

	@Formula(select = "d${ta}.name_en", join = "LEFT OUTER JOIN mfi_mobilizer d${ta} ON d${ta}.id = ${ta}.mobilizer_id")
	String mobilizerName;

	@Formula(select = "e${ta}.product_category", join = "LEFT OUTER JOIN CLTB_ACCOUNT_APPS_MASTER e${ta} ON e${ta}.account_number = ${ta}.account")
	String prdCat = "";

	@Formula(select = "e${ta}.product_code", join = "LEFT OUTER JOIN CLTB_ACCOUNT_APPS_MASTER e${ta} ON e${ta}.account_number = ${ta}.account")
	String prdCd = "";

	@Formula(select = "e${ta}.amount_financed", join = "LEFT OUTER JOIN CLTB_ACCOUNT_APPS_MASTER e${ta} ON e${ta}.account_number = ${ta}.account")
	Integer amountFinance;

	@Formula(select = "e${ta}.amount_disbursed", join = "LEFT OUTER JOIN CLTB_ACCOUNT_APPS_MASTER e${ta} ON e${ta}.account_number = ${ta}.account")
	Integer amountDisbursed;
	
	@Formula(select = "e${ta}.value_date", join = "LEFT OUTER JOIN CLTB_ACCOUNT_APPS_MASTER e${ta} ON e${ta}.account_number = ${ta}.account")
	Date valueDate;
	
	@Formula(select = "e${ta}.maturity_date", join = "LEFT OUTER JOIN CLTB_ACCOUNT_APPS_MASTER e${ta} ON e${ta}.account_number = ${ta}.account")
	Date maturityDate;
	
	@Transient
	Double inRate;
	
	@Formula(select = "e${ta}.field_char_8", join = "LEFT OUTER JOIN CLTB_ACCOUNT_APPS_MASTER e${ta} ON e${ta}.account_number = ${ta}.account")
	String loanPurpose;

	@Column(length = 1)
	String isGroupLeader;

	@Column(length = 1)
	String toPrintContract;

	@Column(length = 500)
	String amountTxt;

	@Transient
	String notifyDirty;
	
	@Formula(select = "o${ta}.outstanding", join = "LEFT OUTER JOIN VW_LOAN_OUTSTANDING o${ta} ON o${ta}.account_number = ${ta}.account")
	Double outstanding;
	
	@Formula(select = "o${ta}.ccy", join = "LEFT OUTER JOIN VW_LOAN_OUTSTANDING o${ta} ON o${ta}.account_number = ${ta}.account")
	String currency;
	
	@Formula(select = "q${ta}.p_address2", join = "LEFT OUTER JOIN STTM_CUST_PERSONAL q${ta} ON q${ta}.customer_no = ${ta}.cif")
	String villageName = "";
	
	/**
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif
	 *            the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the mobilizer
	 */
	public Mobilizer getMobilizer() {
		if (mobilizer == null) {
			mobilizer = new Mobilizer();
		}
		return mobilizer;
	}

	/**
	 * @param mobilizer
	 *            the mobilizer to set
	 */
	public void setMobilizer(Mobilizer mobilizer) {
		this.mobilizer = mobilizer;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		if (group == null) {
			group = new Group();
		}
		return group;
	}

	/**
	 * @param group
	 *            the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		if (fullName == null)
			fullName = "";
		return fullName;
	}

	/**
	 * @param fullName
	 *            the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the mobilzerName
	 */
	public String getMobilizerName() {
		if (mobilizerName == null)
			mobilizerName = "";
		return mobilizerName;
	}

	/**
	 * @param mobilzerName
	 *            the mobilzerName to set
	 */
	public void setMobilizerName(String mobilzerName) {
		this.mobilizerName = mobilzerName;
	}

	/**
	 * @return the prdCat
	 */
	public String getPrdCat() {
		if (prdCat == null)
			prdCat = "";
		return prdCat;
	}

	/**
	 * @param prdCat
	 *            the prdCat to set
	 */
	public void setPrdCat(String prdCat) {
		this.prdCat = prdCat;
	}

	/**
	 * @return the prdCd
	 */
	public String getPrdCd() {
		if (prdCd == null)
			prdCd = "";
		return prdCd;
	}

	/**
	 * @param prdCd
	 *            the prdCd to set
	 */
	public void setPrdCd(String prdCd) {
		this.prdCd = prdCd;
	}

	/**
	 * @return the notifyDirty
	 */
	public String getNotifyDirty() {
		return notifyDirty;
	}

	/**
	 * @param notifyDirty
	 *            the notifyDirty to set
	 */
	public void setNotifyDirty(String notifyDirty) {
		this.notifyDirty = notifyDirty;
	}

	/**
	 * @return the groupAccNo
	 */
	public String getGroupAccNo() {
		if (groupAccNo == null)
			groupAccNo = "";
		return groupAccNo;
	}

	/**
	 * @param groupAccNo
	 *            the groupAccNo to set
	 */
	public void setGroupAccNo(String groupAccNo) {
		this.groupAccNo = groupAccNo;
	}

	/**
	 * @return the amountFinance
	 */
	public Integer getAmountFinance() {
		return amountFinance;
	}

	/**
	 * @param amountFinance
	 *            the amountFinance to set
	 */
	public void setAmountFinance(Integer amountFinance) {
		this.amountFinance = amountFinance;
	}

	/**
	 * @return the amountDisbursed
	 */
	public Integer getAmountDisbursed() {
		return amountDisbursed;
	}

	/**
	 * @param amountDisbursed
	 *            the amountDisbursed to set
	 */
	public void setAmountDisbursed(Integer amountDisbursed) {
		this.amountDisbursed = amountDisbursed;
	}

	/**
	 * @return the isGroupLeader
	 */
	public String getIsGroupLeader() {
		return isGroupLeader;
	}

	/**
	 * @param isGroupLeader
	 *            the isGroupLeader to set
	 */
	public void setIsGroupLeader(String isGroupLeader) {
		this.isGroupLeader = isGroupLeader;
	}

	/**
	 * @return the toPrintContract
	 */
	public String getToPrintContract() {
		return toPrintContract;
	}

	/**
	 * @param toPrintContract
	 *            the toPrintContract to set
	 */
	public void setToPrintContract(String toPrintContract) {
		this.toPrintContract = toPrintContract;
	}

	/**
	 * @return the amountTxt
	 */
	public String getAmountTxt() {
		return amountTxt;
	}

	/**
	 * @param amountTxt
	 *            the amountTxt to set
	 */
	public void setAmountTxt(String amountTxt) {
		this.amountTxt = amountTxt;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID:");
		sb.append(this.getId());
		sb.append("|");
		sb.append("BR:");
		sb.append(this.getBrCd());
		sb.append("|");
		sb.append("CIF:");
		sb.append(this.getCif());
		sb.append("|");
		sb.append("ACC:");
		sb.append(this.getAccount());
		sb.append("|");
		sb.append(this.getFullName());
		sb.append("|");
		sb.append("MobilizerID:");
		sb.append(getMobilizer().getId());
		sb.append("|");
		sb.append("MobilizerName:");
		sb.append(getMobilizer().getNameEN());
		sb.append("|");
		sb.append("GroupID:");
		sb.append(getGroup().getId());
		return sb.toString();
	}
	
	public String toStringSummary() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID:");
		sb.append(this.getId());
		sb.append("|");
		sb.append("CIF:");
		sb.append(this.getCif());
		sb.append("|");
		sb.append("ACC:");
		sb.append(this.getAccount());
		return sb.toString();
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
	 * @return the refAccNo
	 */
	public String getRefAccNo() {
		return refAccNo;
	}

	/**
	 * @param refAccNo the refAccNo to set
	 */
	public void setRefAccNo(String refAccNo) {
		this.refAccNo = refAccNo;
	}

	/**
	 * @return the fullNameKH
	 */
	public String getFullNameKH() {
		return fullNameKH;
	}

	/**
	 * @param fullNameKH the fullNameKH to set
	 */
	public void setFullNameKH(String fullNameKH) {
		this.fullNameKH = fullNameKH;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the valueDate
	 */
	public Date getValueDate() {
		return valueDate;
	}

	/**
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * @return the maturityDate
	 */
	public Date getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return the inRate
	 */
	public Double getInRate() {
		if(inRate == null){
			inRate = MfiFacade.getInterestRate(account);
		}
		return inRate;
	}

	/**
	 * @param inRate the inRate to set
	 */
	public void setInRate(Double inRate) {
		this.inRate = inRate;
	}

	/**
	 * @return the loanPurpose
	 */
	public String getLoanPurpose() {
		return loanPurpose;
	}

	/**
	 * @param loanPurpose the loanPurpose to set
	 */
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	/**
	 * @return the outstanding
	 */
	public Double getOutstanding() {
		if(outstanding == null) return 0.0; 
		return outstanding;
	}

	/**
	 * @param outstanding the outstanding to set
	 */
	public void setOutstanding(Double outstanding) {
		this.outstanding = outstanding;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		if(currency == null) return "";
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the villageName
	 */
	public String getVillageName() {
		return villageName;
	}

	/**
	 * @param villageName the villageName to set
	 */
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public String getStatus() {
		if(status == null){
			return "";
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDes() {
		if(status==null) return statusDes;
		switch (status) {
		case "A":
			statusDes= "Active";
			break;
		case "L":
			statusDes= "Liquid";
			break;
		/*case "Y":
			statusDes= "Y";
		case "H":
			statusDes= "H";*/
		case "V":
			statusDes= "Reversed";
			break;
		}
		return statusDes;
	}

	public void setStatusDes(String statusDes) {
		this.statusDes = statusDes;
	}
	
	
	
	
	
}
