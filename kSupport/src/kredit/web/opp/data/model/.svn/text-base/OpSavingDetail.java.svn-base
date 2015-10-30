package kredit.web.opp.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import kredit.web.core.util.model.CodeItem;
import kredit.web.opp.data.model.facade.OperationFacade;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
@Table(name="OP_SAVING_DETAIL")
public class OpSavingDetail {

	@Id
	Integer id;
	
	@CreatedTimestamp
    private Date createOn;

    @Version
    private Date changeOn;
 
    @Column(length=12)
    private String createBy;
    
    @Column(length=12)
    private String changeBy;
    
    @ManyToOne
	@JoinColumn(name = "OP_SAVING_ID")
    OpSaving saving;
    
    @Transient
    private CodeItem product;
    
    @Transient
    private CodeItem ccy;
    
    private String product_code;
    private String currency;
    
    private Integer y0m_cli;
    private Double y0m_amt;
    
    private Integer y0d_cli;
    private Double y0d_amt;
    
    private Integer m01_cli;
    private Double m01_amt;
    private Integer m02_cli;
    private Double m02_amt;    
    private Integer m03_cli;
    private Double m03_amt;    
    private Integer m04_cli;
    private Double m04_amt;    
    private Integer m05_cli;
    private Double m05_amt;    
    private Integer m06_cli;
    private Double m06_amt;    
    private Integer m07_cli;
    private Double m07_amt;    
    private Integer m08_cli;
    private Double m08_amt;    
    private Integer m09_cli;
    private Double m09_amt;
    private Integer m10_cli;
    private Double m10_amt;    
    private Integer m11_cli;
    private Double m11_amt;    
    private Integer m12_cli;
    private Double m12_amt;
    
    private Integer y1d_cli;
    private Double y1d_amt;
    
    private Integer y2d_cli;
    private Double y2d_amt;
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getCreateOn() {
		return createOn;
	}
	
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	
	public Date getChangeOn() {
		return changeOn;
	}
	
	public void setChangeOn(Date changeOn) {
		this.changeOn = changeOn;
	}
	
	public String getCreateBy() {
		return createBy;
	}
	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	public String getChangeBy() {
		return changeBy;
	}
	
	public void setChangeBy(String changeBy) {
		this.changeBy = changeBy;
	}
	
	public OpSaving getSaving() {
		return saving;
	}

	public void setSaving(OpSaving saving) {
		this.saving = saving;
	}
	
	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public Integer getY0m_cli() {
		return y0m_cli;
	}
	
	public void setY0m_cli(Integer y0m_cli) {
		this.y0m_cli = y0m_cli;
	}
	
	public Double getY0m_amt() {
		return y0m_amt;
	}
	
	public void setY0m_amt(Double y0m_amt) {
		this.y0m_amt = y0m_amt;
	}
	
	public Integer getY0d_cli() {
		return y0d_cli;
	}
	
	public void setY0d_cli(Integer y0d_cli) {
		this.y0d_cli = y0d_cli;
	}
	
	public Double getY0d_amt() {
		return y0d_amt;
	}
	
	public void setY0d_amt(Double y0d_amt) {
		this.y0d_amt = y0d_amt;
	}
	
	public Integer getM01_cli() {
		return m01_cli;
	}
	
	public void setM01_cli(Integer m01_cli) {
		this.m01_cli = m01_cli;
	}
	
	public Double getM01_amt() {
		return m01_amt;
	}
	
	public void setM01_amt(Double m01_amt) {
		this.m01_amt = m01_amt;
	}
	
	public Integer getM02_cli() {
		return m02_cli;
	}
	
	public void setM02_cli(Integer m02_cli) {
		this.m02_cli = m02_cli;
	}
	
	public Double getM02_amt() {
		return m02_amt;
	}
	
	public void setM02_amt(Double m02_amt) {
		this.m02_amt = m02_amt;
	}
	
	public Integer getM03_cli() {
		return m03_cli;
	}
	
	public void setM03_cli(Integer m03_cli) {
		this.m03_cli = m03_cli;
	}
	
	public Double getM03_amt() {
		return m03_amt;
	}
	
	public void setM03_amt(Double m03_amt) {
		this.m03_amt = m03_amt;
	}
	
	public Integer getM04_cli() {
		return m04_cli;
	}
	
	public void setM04_cli(Integer m04_cli) {
		this.m04_cli = m04_cli;
	}
	
	public Double getM04_amt() {
		return m04_amt;
	}
	
	public void setM04_amt(Double m04_amt) {
		this.m04_amt = m04_amt;
	}
	
	public Integer getM05_cli() {
		return m05_cli;
	}
	
	public void setM05_cli(Integer m05_cli) {
		this.m05_cli = m05_cli;
	}
	
	public Double getM05_amt() {
		return m05_amt;
	}
	
	public void setM05_amt(Double m05_amt) {
		this.m05_amt = m05_amt;
	}
	
	public Integer getM06_cli() {
		return m06_cli;
	}
	
	public void setM06_cli(Integer m06_cli) {
		this.m06_cli = m06_cli;
	}
	
	public Double getM06_amt() {
		return m06_amt;
	}
	
	public void setM06_amt(Double m06_amt) {
		this.m06_amt = m06_amt;
	}
	
	public Integer getM07_cli() {
		return m07_cli;
	}
	
	public void setM07_cli(Integer m07_cli) {
		this.m07_cli = m07_cli;
	}
	
	public Double getM07_amt() {
		return m07_amt;
	}
	
	public void setM07_amt(Double m07_amt) {
		this.m07_amt = m07_amt;
	}
	
	public Integer getM08_cli() {
		return m08_cli;
	}
	
	public void setM08_cli(Integer m08_cli) {
		this.m08_cli = m08_cli;
	}
	
	public Double getM08_amt() {
		return m08_amt;
	}
	
	public void setM08_amt(Double m08_amt) {
		this.m08_amt = m08_amt;
	}
	
	public Integer getM09_cli() {
		return m09_cli;
	}
	
	public void setM09_cli(Integer m09_cli) {
		this.m09_cli = m09_cli;
	}
	
	public Double getM09_amt() {
		return m09_amt;
	}
	
	public void setM09_amt(Double m09_amt) {
		this.m09_amt = m09_amt;
	}
	
	public Integer getM10_cli() {
		return m10_cli;
	}
	
	public void setM10_cli(Integer m10_cli) {
		this.m10_cli = m10_cli;
	}
	
	public Double getM10_amt() {
		return m10_amt;
	}
	
	public void setM10_amt(Double m10_amt) {
		this.m10_amt = m10_amt;
	}
	
	public Integer getM11_cli() {
		return m11_cli;
	}
	
	public void setM11_cli(Integer m11_cli) {
		this.m11_cli = m11_cli;
	}
	
	public Double getM11_amt() {
		return m11_amt;
	}
	
	public void setM11_amt(Double m11_amt) {
		this.m11_amt = m11_amt;
	}
	
	public Integer getM12_cli() {
		return m12_cli;
	}
	
	public void setM12_cli(Integer m12_cli) {
		this.m12_cli = m12_cli;
	}
	
	public Double getM12_amt() {
		return m12_amt;
	}
	
	public void setM12_amt(Double m12_amt) {
		this.m12_amt = m12_amt;
	}
	
	public Integer getY1d_cli() {
		return y1d_cli;
	}
	
	public void setY1d_cli(Integer y1d_cli) {
		this.y1d_cli = y1d_cli;
	}
	
	public Double getY1d_amt() {
		return y1d_amt;
	}
	
	public void setY1d_amt(Double y1d_amt) {
		this.y1d_amt = y1d_amt;
	}
	
	public Integer getY2d_cli() {
		return y2d_cli;
	}
	
	public void setY2d_cli(Integer y2d_cli) {
		this.y2d_cli = y2d_cli;
	}
	
	public Double getY2d_amt() {
		return y2d_amt;
	}
	
	public void setY2d_amt(Double y2d_amt) {
		this.y2d_amt = y2d_amt;
	}
	
	public CodeItem getProduct() {
		if(product == null) {
			product = new CodeItem();
			
			if(product_code != null) {
				product = OperationFacade.getProduct(product_code, OpCommon.OP_PRODUCT_TYPE_SAVING);
			}
		}
		return product;
	}

	public void setProduct(CodeItem product) {
		this.product = product;
		product_code = this.product.getCode();
	}
	
	public CodeItem getCcy() {
		if(ccy == null) {
			ccy = new CodeItem();
			
			if(currency != null) {
				ccy.setCode(currency);
				ccy.setDescription(currency);
			}
		}
		return ccy;
	}

	public void setCcy(CodeItem ccy) {
		this.ccy = ccy;
		currency = this.ccy.getCode();
	}
}
