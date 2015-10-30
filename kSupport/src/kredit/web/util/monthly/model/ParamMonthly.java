package kredit.web.util.monthly.model;

import java.util.Calendar;

import kredit.web.core.util.model.CodeItem;
import kredit.web.util.monthly.model.facade.MonthlyFacade;

public class ParamMonthly {
	
	CodeItem branch;
	CodeItem subBranch;
	CodeItem option;
	CodeItem month;
	
	public CodeItem getBranch() {
		if(branch == null)
		{
			branch = new CodeItem();
			branch.setId(0);
			branch.setCode("");
			branch.setDescription("All");
		}
		
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}

	public CodeItem getSubBranch() {
		if(subBranch == null)
		{
			subBranch = new CodeItem();
			subBranch.setId(0);
			subBranch.setCode("");
			subBranch.setDescription("All");
		}
		return subBranch;
	}

	public void setSubBranch(CodeItem subBranch) {
		this.subBranch = subBranch;
	}
	
	public CodeItem getOption() {
		if(option == null)
		{
			option = new CodeItem();
			option.setId(0);
			option.setCode("B");
			option.setDescription("Both");
		}
		return option;
	}
	
	public void setOption(CodeItem option) {
		this.option = option;
	}
	
	@SuppressWarnings("unused")
	public CodeItem getMonth() {
		if(month == null)
		{
			Calendar calendar = Calendar.getInstance();
			if(Calendar.MONTH == 0)
			{
				calendar.set(Calendar.MONTH, 11);
				calendar.add(Calendar.YEAR, -1);
			}
			else
			{
				calendar.add(Calendar.MONTH, -1);
			}
			
			String c;
			String d;
			
			c = calendar.get(Calendar.YEAR) + "";
			d = MonthlyFacade.getMonthForInt(calendar.get(Calendar.MONTH)).substring(0, 3) + 
					" - " + calendar.get(Calendar.YEAR);
			
			month = new CodeItem();
			month.setId(calendar.get(Calendar.MONTH));
			month.setCode(c);
			month.setDescription(d);
		}
		return month;
	}
	
	public void setMonth(CodeItem month) {
		this.month = month;
	}

}
