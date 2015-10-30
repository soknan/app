package kredit.web.loanLate.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.util.model.CodeItem;

import com.avaje.ebean.annotation.Formula;

@Entity
@Table(name="LOAN_LATE_TEMP")
public class LoanLateTemp {

	private String accNo;
	private String branchCode;
	private String cusName;
	private Double loanAmount;
	private String productCode;
	private String ccy;
	private Double prinBal;
	private String village;
	private String commune;
	private String district;
	private String province;
	private Date disburseDate;
	private Date maturityDate;
	private Double r01;
	private Double r02;
	private Double r03;
	private Double r04;
	private Double r05;
	private Double r06;
	private Double r07;
	private Double r08;
	private Double r09;
	private Double r10;
	private Double r11;
	private Double r12;
	
	private String cosName;
	
	private String cofName;
	
	private Double overduePrin;
	
	private Double overdueInt;
	
	private Integer overdueDay;
	
	private Date importDate;
	
	@Formula(select = "b${ta}.STATUS", join = "LEFT OUTER JOIN (SELECT * FROM LOAN_LATE_DETAIL c${ta} WHERE c${ta}.RECORD_NO = (SELECT MAX(d${ta}.RECORD_NO) FROM LOAN_LATE_DETAIL d${ta} WHERE c${ta}.ACC_NO = d${ta}.ACC_NO)) b${ta} on ${ta}.ACC_NO = b${ta}.ACC_NO")
	private String status;
	
	@Formula(select = "b${ta}.SITUATION", join = "LEFT OUTER JOIN (SELECT * FROM LOAN_LATE_DETAIL c${ta} WHERE c${ta}.RECORD_NO = (SELECT MAX(d${ta}.RECORD_NO) FROM LOAN_LATE_DETAIL d${ta} WHERE c${ta}.ACC_NO = d${ta}.ACC_NO)) b${ta} on ${ta}.ACC_NO = b${ta}.ACC_NO")
	private String situation;
	
	@Formula(select = "b${ta}.CREATE_ON", join = "LEFT OUTER JOIN (SELECT * FROM LOAN_LATE_DETAIL c${ta} WHERE c${ta}.RECORD_NO = (SELECT MAX(d${ta}.RECORD_NO) FROM LOAN_LATE_DETAIL d${ta} WHERE c${ta}.ACC_NO = d${ta}.ACC_NO)) b${ta} on ${ta}.ACC_NO = b${ta}.ACC_NO")
	private Date createOn;
	
	@Formula(select = "b${ta}.CREATE_BY", join = "LEFT OUTER JOIN (SELECT * FROM LOAN_LATE_DETAIL c${ta} WHERE c${ta}.RECORD_NO = (SELECT MAX(d${ta}.RECORD_NO) FROM LOAN_LATE_DETAIL d${ta} WHERE c${ta}.ACC_NO = d${ta}.ACC_NO)) b${ta} on ${ta}.ACC_NO = b${ta}.ACC_NO")
	private String createBy;
	
	@Formula(select = "b${ta}.CHANGE_ON", join = "LEFT OUTER JOIN (SELECT * FROM LOAN_LATE_DETAIL c${ta} WHERE c${ta}.RECORD_NO = (SELECT MAX(d${ta}.RECORD_NO) FROM LOAN_LATE_DETAIL d${ta} WHERE c${ta}.ACC_NO = d${ta}.ACC_NO)) b${ta} on ${ta}.ACC_NO = b${ta}.ACC_NO")
	private Date changeOn;
	
	@Formula(select = "b${ta}.CHANGE_BY", join = "LEFT OUTER JOIN (SELECT * FROM LOAN_LATE_DETAIL c${ta} WHERE c${ta}.RECORD_NO = (SELECT MAX(d${ta}.RECORD_NO) FROM LOAN_LATE_DETAIL d${ta} WHERE c${ta}.ACC_NO = d${ta}.ACC_NO)) b${ta} on ${ta}.ACC_NO = b${ta}.ACC_NO")
	private String changeBy;
	
	@Formula(select = "e${ta}.PRODUCT_DESC", join = "LEFT OUTER JOIN CLTM_PRODUCT e${ta} ON ${ta}.PRODUCT_CODE = e${ta}.PRODUCT_CODE")
	private String productType;
	
	@Formula(select = "b${ta}.HQ_SITUATION", join = "LEFT OUTER JOIN (SELECT * FROM LOAN_LATE_DETAIL c${ta} WHERE c${ta}.RECORD_NO = (SELECT MAX(d${ta}.RECORD_NO) FROM LOAN_LATE_DETAIL d${ta} WHERE c${ta}.ACC_NO = d${ta}.ACC_NO)) b${ta} on ${ta}.ACC_NO = b${ta}.ACC_NO")
	private String hq_situation;
	
	@Transient
    private CodeItem cStatus;
	
	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public Double getPrinBal() {
		return prinBal;
	}

	public void setPrinBal(Double prinBal) {
		this.prinBal = prinBal;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Date getDisburseDate() {
		return disburseDate;
	}

	public void setDisburseDate(Date disburseDate) {
		this.disburseDate = disburseDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Double getR01() {
		return r01;
	}

	public void setR01(Double r01) {
		this.r01 = r01;
	}

	public Double getR02() {
		return r02;
	}

	public void setR02(Double r02) {
		this.r02 = r02;
	}

	public Double getR03() {
		return r03;
	}

	public void setR03(Double r03) {
		this.r03 = r03;
	}

	public Double getR04() {
		return r04;
	}

	public void setR04(Double r04) {
		this.r04 = r04;
	}

	public Double getR05() {
		return r05;
	}

	public void setR05(Double r05) {
		this.r05 = r05;
	}

	public Double getR06() {
		return r06;
	}

	public void setR06(Double r06) {
		this.r06 = r06;
	}

	public Double getR07() {
		return r07;
	}

	public void setR07(Double r07) {
		this.r07 = r07;
	}

	public Double getR08() {
		return r08;
	}

	public void setR08(Double r08) {
		this.r08 = r08;
	}

	public Double getR09() {
		return r09;
	}

	public void setR09(Double r09) {
		this.r09 = r09;
	}

	public Double getR10() {
		return r10;
	}

	public void setR10(Double r10) {
		this.r10 = r10;
	}

	public Double getR11() {
		return r11;
	}

	public void setR11(Double r11) {
		this.r11 = r11;
	}

	public Double getR12() {
		return r12;
	}

	public void setR12(Double r12) {
		this.r12 = r12;
	}

	public String getCosName() {
		return cosName;
	}

	public void setCosName(String cosName) {
		this.cosName = cosName;
	}

	public String getCofName() {
		return cofName;
	}

	public void setCofName(String cofName) {
		this.cofName = cofName;
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
	
	public String getStatus() {
		if(status == null) {
			status = "Branch Level";	
		}
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getChangeOn() {
		return changeOn;
	}

	public void setChangeOn(Date changeOn) {
		this.changeOn = changeOn;
	}

	public String getChangeBy() {
		return changeBy;
	}

	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public CodeItem getcStatus() {
		return cStatus;
	}

	public void setcStatus(CodeItem cStatus) {
		this.cStatus = cStatus;
		setStatus(cStatus.getDescription());
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public String getHq_situation() {
		return hq_situation;
	}

	public void setHq_situation(String hq_situation) {
		this.hq_situation = hq_situation;
	}
	
}
