package kredit.web.risk.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;

import kredit.web.core.util.model.CodeItem;

public class ParamModel {
	String filter;
	Date vDate;
	CodeItem tranStatus;
	CodeItem tranType;
	CodeItem branch;
	CodeItem sub;
	CodeItem fMonth;
	CodeItem fYear;
	CodeItem evenBal;
	
	public Date getvDate() {
		if(vDate!=null){
			return vDate;
		}
		Date date = new Date();
		Date vDate = new DateTime(date).minusDays(1).toDate();
		return vDate;
	}
	public void setvDate(Date vDate) {
		this.vDate = vDate;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	public CodeItem getBranch() {
		if (branch != null)
			return branch;
		branch = new CodeItem();		
		branch.setCode("");
		branch.setDescription("All");
		return branch;
	}
	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}
	public CodeItem getTranStatus() {
		if (tranStatus != null) {
			return tranStatus;
		}
		tranStatus = new CodeItem();
		tranStatus.setCode("UNUSUAL");
		tranStatus.setDescription("UNUSUAL");		
		return tranStatus;
	}
	public void setTranStatus(CodeItem tranStatus) {
		this.tranStatus = tranStatus;
	}
	public CodeItem getTranType() {
		if (tranType != null) {
			return tranType;
		}
		tranType = new CodeItem();
		tranType.setCode("%");
		tranType.setDescription("All");		
		return tranType;
	}
	public void setTranType(CodeItem tranType) {
		this.tranType = tranType;
	}
	public CodeItem getSub() {
		if (sub != null)
			return sub;
		sub = new CodeItem();		
		sub.setCode("");
		sub.setDescription("All");
		return sub;
	}
	public void setSub(CodeItem sub) {
		this.sub = sub;
	}
	public CodeItem getfMonth() {
		if (fMonth != null)
			return fMonth;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date result = cal.getTime();
		String m = new SimpleDateFormat("MMM").format(result).toUpperCase();
		fMonth = new CodeItem();		
		fMonth.setCode(m);
		fMonth.setDescription(m);

		return fMonth;
	}
	public void setfMonth(CodeItem fMonth) {
		this.fMonth = fMonth;
	}

	public CodeItem getfYear() {
		if(fYear!=null){
			return fYear;
		}		
		Calendar cal = Calendar.getInstance();		
		if(fMonth.getCode().equals("DEC")){
			cal.add(Calendar.YEAR, -1);
		}		
		Date result = cal.getTime();
		String m = new SimpleDateFormat("YYYY").format(result).toUpperCase();
		fYear = new CodeItem();		
		fYear.setCode(m);
		fYear.setDescription(m);	
		return fYear;
	}
	public void setfYear(CodeItem fYear) {
		this.fYear = fYear;
	}
	public CodeItem getEvenBal() {
		if (evenBal != null) {
			return evenBal;
		}
		evenBal = new CodeItem();
		evenBal.setCode("%");
		evenBal.setDescription("All");		
		return evenBal;
	}
	public void setEvenBal(CodeItem evenBal) {
		this.evenBal = evenBal;
	}
	
	
	
}
