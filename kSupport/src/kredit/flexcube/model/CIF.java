package kredit.flexcube.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.mfi.model.facade.MfiFacade;

@Entity
@Table(name = "STTM_CUSTOMER")
public class CIF {

	@Id
	@Column(name = "CUSTOMER_NO")
	String custNo = "";

	@Column(name = "CUSTOMER_TYPE")
	String custType;

	@Column(name = "FULL_NAME")
	String fullName = "";

	@Column(name = "SHORT_NAME")
	String shortName = "";

	@Column(name = "ADDRESS_LINE1")
	String addr1;

	@Column(name = "ADDRESS_LINE2")
	String addr2 = "";

	@Column(name = "ADDRESS_LINE3")
	String addr3;

	@Column(name = "ADDRESS_LINE4")
	String addr4;

	@Transient
	String accountNo = "";

	@Transient
	String prdCd = "";

	@Transient
	String prdCat = "";

	@Transient
	String brCd = "";

	@Transient
	Integer amountDisbursed = 0;

	@Transient
	int row_num;

	@Transient
	String fullNameKH = "";

	@Transient
	Date dateOfBirth;

	@Transient
	Date valueDate;

	@Transient
	Date maturityDate;

	@Transient
	Double inRate;

	@Transient
	String loanPurpose;

	@Transient
	String altAccNo;
	
	@Transient
	int startNumRow = 0;
	
	@Transient
	int endNumRow = 10;
	

	/**
	 * @return the fullName
	 */
	public String getFullName() {
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
	 * @return the addr1
	 */
	public String getAddr1() {
		return addr1;
	}

	/**
	 * @param addr1
	 *            the addr1 to set
	 */
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	/**
	 * @return the addr2
	 */
	public String getAddr2() {
		return addr2;
	}

	/**
	 * @param addr2
	 *            the addr2 to set
	 */
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	/**
	 * @return the addr3
	 */
	public String getAddr3() {
		return addr3;
	}

	/**
	 * @param addr3
	 *            the addr3 to set
	 */
	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	/**
	 * @return the addr4
	 */
	public String getAddr4() {
		return addr4;
	}

	/**
	 * @param addr4
	 *            the addr4 to set
	 */
	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}

	/**
	 * @return the custNo
	 */
	public String getCustNo() {
		return custNo;
	}

	/**
	 * @param custNo
	 *            the custNo to set
	 */
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType
	 *            the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName
	 *            the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo
	 *            the accountNo to set
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return the prdCd
	 */
	public String getPrdCd() {
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
	 * @return the prdType
	 */
	public String getPrdCat() {
		return prdCat;
	}

	/**
	 * @param prdType
	 *            the prdType to set
	 */
	public void setPrdCat(String prdType) {
		this.prdCat = prdType;
	}

	/**
	 * @return the brCd
	 */
	public String getBrCd() {
		return brCd;
	}

	/**
	 * @param brCd
	 *            the brCd to set
	 */
	public void setBrCd(String brCd) {
		this.brCd = brCd;
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
	 * @return the row_num
	 */
	public int getRow_num() {
		return row_num;
	}

	/**
	 * @param row_num
	 *            the row_num to set
	 */
	public void setRow_num(int row_num) {
		this.row_num = row_num;
	}

	/**
	 * @return the fullNameKH
	 */
	public String getFullNameKH() {
		return fullNameKH;
	}

	/**
	 * @param fullNameKH
	 *            the fullNameKH to set
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
	 * @param dateOfBirth
	 *            the dateOfBirth to set
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
	 * @param valueDate
	 *            the valueDate to set
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
	 * @param maturityDate
	 *            the maturityDate to set
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return the inRate
	 */
	public Double getInRate() {
		if (inRate != null)
			return inRate;

		inRate = MfiFacade.getInterestRate(this.accountNo);
		
		return inRate;
	}

	/**
	 * @param inRate
	 *            the inRate to set
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
	 * @param loanPurpose
	 *            the loanPurpose to set
	 */
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	/**
	 * @return the altAccNo
	 */
	public String getAltAccNo() {
		return altAccNo;
	}

	/**
	 * @param altAccNo
	 *            the altAccNo to set
	 */
	public void setAltAccNo(String altAccNo) {
		this.altAccNo = altAccNo;
	}

	/**
	 * @return the startNumRow
	 */
	public int getStartNumRow() {
		return startNumRow;
	}

	/**
	 * @param startNumRow the startNumRow to set
	 */
	public void setStartNumRow(int startNumRow) {
		this.startNumRow = startNumRow;
	}

	/**
	 * @return the endNumRow
	 */
	public int getEndNumRow() {
		return endNumRow;
	}

	/**
	 * @param endNumRow the endNumRow to set
	 */
	public void setEndNumRow(int endNumRow) {
		this.endNumRow = endNumRow;
	}
	
	
}
