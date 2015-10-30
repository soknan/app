package kredit.web.writeOff.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.model.Domain;
import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.facade.SecurityFacade;
import kredit.web.writeOff.model.facade.WriteOffFacade;

@Entity
@Table(name="WOF_LOAN")
public class Loan extends Domain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	String cif;
	String nameEn;
	String nameKh;
	String accountNo;
	String branchName;
	String coShortName;
	String coFullName;
	String category;
	String reason;
	Double amtDisbursed; 
	String ccy;
	String prodCode;
	String prodCat;
	Date woffDate;
	Double principle;
	Double interest;
	Double penPrin;
	Double penInt;
	Double contInt;
	Double totWoff;
	Double accuLnRec;
	Double netLoss;
	
	@Transient
	List<Repayment> repayment;
	
	@Transient
	List<TrnCo> trnCo;
	
	@Transient
	CodeItem branch;
	
	@Transient
	String fullBranch;
	
	@Transient
	String product;
	
	@Transient
	CodeItem categoryC;

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameKh() {
		return nameKh;
	}

	public void setNameKh(String nameKh) {
		this.nameKh = nameKh;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCoShortName() {
		return coShortName;
	}

	public void setCoShortName(String coShortName) {
		this.coShortName = coShortName;
	}
	
	public String getCoFullName() {
		return coFullName;
	}

	public void setCoFullName(String coFullName) {
		this.coFullName = coFullName;
	}

	public Double getAmtDisbursed() {
		return amtDisbursed;
	}

	public void setAmtDisbursed(Double amtDisbursed) {
		this.amtDisbursed = amtDisbursed;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdCode(String prodCode) {
		this.prodCode = prodCode;
	}

	public String getProdCat() {
		return prodCat;
	}

	public void setProdCat(String prodCat) {
		this.prodCat = prodCat;
	}

	public Date getWoffDate() {
		return woffDate;
	}

	public void setWoffDate(Date woffDate) {
		this.woffDate = woffDate;
	}

	public Double getPrinciple() {
		return principle;
	}

	public void setPrinciple(Double principle) {
		this.principle = principle;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getPenPrin() {
		return penPrin;
	}

	public void setPenPrin(Double penPrin) {
		this.penPrin = penPrin;
	}

	public Double getPenInt() {
		return penInt;
	}

	public void setPenInt(Double penInt) {
		this.penInt = penInt;
	}

	public Double getContInt() {
		return contInt;
	}

	public void setContInt(Double contInt) {
		this.contInt = contInt;
	}

	public Double getTotWoff() {
		return totWoff;
	}

	public void setTotWoff(Double totWoff) {
		this.totWoff = totWoff;
	}

	public Double getAccuLnRec() {
		return accuLnRec;
	}

	public void setAccuLnRec(Double accuLnRec) {
		this.accuLnRec = accuLnRec;
	}

	public Double getNetLoss() {
		return netLoss;
	}

	public void setNetLoss(Double netLoss) {
		this.netLoss = netLoss;
	}

	public List<Repayment> getRepayment() {
		if(repayment == null)
		{
			repayment = WriteOffFacade.getRepayments(this.getId());
			
		}
		return repayment;
	}

	public void setRepayment(List<Repayment> repayment) {
		this.repayment = repayment;
	}
	
	public List<TrnCo> getTrnCo() {
		if(trnCo == null)
		{
			trnCo = WriteOffFacade.getTrnCos(this.getId());
			
		}
		
		return trnCo;
	}

	public void setTrnCo(List<TrnCo> trnCo) {
		this.trnCo = trnCo;
	}

	public CodeItem getBranch() {
		if(branch == null)
		{
			String code = "";
			if(this.getBrCd() != null)
				code = this.getBrCd();
			branch = SecurityFacade.getBranch(code);
		}
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
		super.setBrCd(this.branch.getCode());
		setBranchName(this.branch.getDescription());
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "Account No:" );
        sb.append(this.getAccountNo());
        sb.append( "|" );
        sb.append( "Name:" );
        sb.append(this.getNameEn());
        sb.append( "|" );
        sb.append( "BR:" );
        sb.append(this.getBrCd());
        
        return sb.toString();
    }

	public String getFullBranch() {
		if(fullBranch == null)
		{
			fullBranch = this.getBrCd() + " - " + branchName;
		}
		return fullBranch;
	}

	public void setFullBranch(String fullBranch) {
		this.fullBranch = fullBranch;
	}

	public String getProduct() {
		if(product == null)
		{
			product = prodCode + " - " + WriteOffFacade.getProductDescription(prodCode);
		}
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public CodeItem getCategoryC() {
		return categoryC;
	}

	public void setCategoryC(CodeItem categoryC) {
		this.categoryC = categoryC;
		setCategory(categoryC.getDescription());
	}
	
}
