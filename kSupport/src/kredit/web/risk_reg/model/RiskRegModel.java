package kredit.web.risk_reg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.util.model.CodeItem;
import kredit.web.risk_reg.model.facade.RiskRegFacade;

@Entity
@Table(name = "RISK_REG")
public class RiskRegModel{	
	private static final long serialVersionUID = 1L;
	@Id
	Integer id;
	@Transient
	Integer rownum;
	
	String code;
	String process;
	String risk_desc;
	String indicator;
	String existing_control;
	String risk_likelihood;
	String risk_impact;
	String risk_rr;
	String for_ts;
	String additional_control;
	String risk_owner;
	Date due_date;
	String af_risk_likelihood;
	String af_risk_impact;
	String af_risk_er;
	String status;
	String fin_month;
	String fin_year;
	Date submmitted_date;
	String branch_code;
	@Transient
	String color_rr;
	@Transient
	String color_er;
	
	String create_by;	
	Date create_on;	
	String change_by;	
	Date change_on;	
	
	@Transient
	CodeItem processC;
	@Transient
	CodeItem likelihoodC;
	@Transient
	CodeItem impactC;
	@Transient
	CodeItem for_tsC;
	@Transient
	CodeItem risk_ownerC;
	@Transient
	CodeItem af_likelihoodC;
	@Transient
	CodeItem af_impactC;
	@Transient
	CodeItem statusC;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getRisk_desc() {
		return risk_desc;
	}
	public void setRisk_desc(String risk_desc) {
		this.risk_desc = risk_desc;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public String getExisting_control() {
		return existing_control;
	}
	public void setExisting_control(String existing_control) {
		this.existing_control = existing_control;
	}
	public String getRisk_likelihood() {
		return risk_likelihood;
	}
	public void setRisk_likelihood(String risk_likelihood) {
		this.risk_likelihood = risk_likelihood;
	}
	public String getRisk_impact() {
		return risk_impact;
	}
	public void setRisk_impact(String risk_impact) {
		this.risk_impact = risk_impact;
	}
	public String getRisk_rr() {
		return risk_rr;
	}
	public void setRisk_rr(String risk_rr) {
		this.risk_rr = risk_rr;
	}
	public String getFor_ts() {
		return for_ts;
	}
	public void setFor_ts(String for_ts) {
		this.for_ts = for_ts;
	}
	public String getAdditional_control() {
		return additional_control;
	}
	public void setAdditional_control(String additional_control) {
		this.additional_control = additional_control;
	}
	public String getRisk_owner() {
		return risk_owner;
	}
	public void setRisk_owner(String risk_owner) {
		this.risk_owner = risk_owner;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public String getAf_risk_likelihood() {
		return af_risk_likelihood;
	}
	public void setAf_risk_likelihood(String af_risk_likelihood) {
		this.af_risk_likelihood = af_risk_likelihood;
	}
	public String getAf_risk_impact() {
		return af_risk_impact;
	}
	public void setAf_risk_impact(String af_risk_impact) {
		this.af_risk_impact = af_risk_impact;
	}
	
	public String getAf_risk_er() {
		return af_risk_er;
	}
	public void setAf_risk_er(String af_risk_er) {
		this.af_risk_er = af_risk_er;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFin_month() {
		return fin_month;
	}
	public void setFin_month(String fin_month) {
		this.fin_month = fin_month;
	}
	public String getFin_year() {
		return fin_year;
	}
	public void setFin_year(String fin_year) {
		this.fin_year = fin_year;
	}
	public Date getSubmmitted_date() {
		return submmitted_date;
	}
	public void setSubmmitted_date(Date submmitted_date) {
		this.submmitted_date = submmitted_date;
	}
	public String getBranch_code() {
		return branch_code;
	}
	public void setBranch_code(String branch_code) {
		this.branch_code = branch_code;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_on() {
		return create_on;
	}
	public void setCreate_on(Date create_on) {
		this.create_on = create_on;
	}
	public String getChange_by() {
		return change_by;
	}
	public void setChange_by(String change_by) {
		this.change_by = change_by;
	}
	public Date getChange_on() {
		return change_on;
	}
	public void setChange_on(Date change_on) {
		this.change_on = change_on;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public CodeItem getProcessC() {
		if(processC==null) {
			processC = new CodeItem();					
			processC.setDescription(RiskRegFacade.getSysLovName("RISK_REG_PROCESS", process));
			//System.out.println(processC.getDescription());
		}	
		return processC;
	}
	public void setProcessC(CodeItem processC) {
		this.processC = processC;
		setProcess(this.processC.getDescription());
	}
	public CodeItem getLikelihoodC() {	
		if(likelihoodC!=null) return likelihoodC;			
			
		likelihoodC = new CodeItem();
		if(risk_likelihood!=null){ 			
			likelihoodC.setCode(risk_likelihood);			
		}
		return likelihoodC;
	}
	public void setLikelihoodC(CodeItem likelihoodC) {
		this.likelihoodC = likelihoodC;		
		setRisk_likelihood(this.likelihoodC.getCode());
			
	}
	public CodeItem getImpactC() {
		if(impactC!=null) return impactC;			
		
		impactC = new CodeItem();
		if(risk_impact != null){
			impactC.setCode(risk_impact);			
		}
		return impactC;
	}
	public void setImpactC(CodeItem impactC) {
		this.impactC = impactC;
		setRisk_impact(this.impactC.getCode());
	}
	public CodeItem getFor_tsC() {
		if(for_tsC==null){
			for_tsC = new CodeItem();
			for_tsC.setDescription(RiskRegFacade.getSysLovName("RISK_REG_FTS", for_ts));
		}		
		return for_tsC;
	}
	public void setFor_tsC(CodeItem for_tsC) {
		this.for_tsC = for_tsC;
		setFor_ts(this.for_tsC.getDescription());
	}
	public CodeItem getRisk_ownerC() {
		if(risk_ownerC==null) {
			risk_ownerC = new CodeItem();
			risk_ownerC.setDescription(RiskRegFacade.getPositionName(risk_owner));
		}		
		return risk_ownerC;
	}
	public void setRisk_ownerC(CodeItem risk_ownerC) {
		this.risk_ownerC = risk_ownerC;
		setRisk_owner(this.risk_ownerC.getDescription());
	}
	public CodeItem getAf_likelihoodC() {
		if(af_likelihoodC!=null) return af_likelihoodC;
		af_likelihoodC = new CodeItem();
		if(af_risk_likelihood!=null){
			af_likelihoodC.setCode(af_risk_likelihood);			
		}
		return af_likelihoodC;
	}
	public void setAf_likelihoodC(CodeItem af_likelihoodC) {
		this.af_likelihoodC = af_likelihoodC;
		setAf_risk_likelihood(this.af_likelihoodC.getCode());
	}
	public CodeItem getAf_impactC() {
		if(af_impactC!=null) return af_impactC;
		af_impactC = new CodeItem();
		if(af_risk_impact != null){
			af_impactC.setCode(af_risk_impact);			
		}
		return af_impactC;
	}
	public void setAf_impactC(CodeItem af_impactC) {
		this.af_impactC = af_impactC;
		setAf_risk_impact(this.af_impactC.getCode());
	}
	public CodeItem getStatusC() {
		if(statusC==null){
			statusC = new CodeItem();
			statusC.setDescription(RiskRegFacade.getSysLovName("RISK_REG_STATUS", status));
		}		
		return statusC;
	}
	public void setStatusC(CodeItem statusC) {
		this.statusC = statusC;
		setStatus(this.statusC.getDescription());
	}
	public String getColor_rr() {
		return color_rr;
	}
	public void setColor_rr(String color_rr) {
		this.color_rr = color_rr;
	}
	public String getColor_er() {
		return color_er;
	}
	public void setColor_er(String color_er) {
		this.color_er = color_er;
	}
	
}
