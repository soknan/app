package kredit.web.writeOff.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.model.Domain;
import kredit.web.core.util.model.CodeItem;


@Entity
@Table(name="WOF_REPAYMENT")
public class Repayment extends Domain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	Integer wof_id;
	Double amount;
	String ccy;
	String coShortName;
	String coFullName;
	String remark;
	Date valueDt;

	@Transient
	CodeItem currency;
	
	public Integer getWof_id() {
		return wof_id;
	}

	public void setWof_id(Integer wof_id) {
		this.wof_id = wof_id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public CodeItem getCurrency() {
		if(currency == null)
		{
			currency = new CodeItem();
		}
		return currency;
	}

	public void setCurrency(CodeItem currency) {
		this.currency = currency;
		setCcy(currency.getCode());
	}
	
	public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "ID:" );
        sb.append(this.getId());
        sb.append( "|" );
        sb.append( "Write-Off Account ID:" );
        sb.append(wof_id);
        sb.append( "|" );
        sb.append( "Amount:" );
        sb.append(this.getAmount());
        sb.append( "|" );
        sb.append( "BR:" );
        sb.append(super.getBrCd());
        
        return sb.toString();
    }

	/**
	 * @return the valueDt
	 */
	public Date getValueDt() {
		return valueDt;
	}

	/**
	 * @param valueDt the valueDt to set
	 */
	public void setValueDt(Date valueDt) {
		this.valueDt = valueDt;
	}
}
