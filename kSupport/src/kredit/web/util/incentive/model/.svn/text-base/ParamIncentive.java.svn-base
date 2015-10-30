package kredit.web.util.incentive.model;

import java.util.Calendar;

import kredit.web.core.util.model.CodeItem;
import kredit.web.util.monthly.model.facade.MonthlyFacade;

public class ParamIncentive {

	CodeItem branch;
	CodeItem subBranch;
	CodeItem option;
	CodeItem fmonth;
	CodeItem tmonth;

	public CodeItem getBranch() {
		if (branch == null) {
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
		if (subBranch == null) {
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
			option.setId(1);
			option.setCode("S");
			option.setDescription("Saving");
		}
		return option;
	}
	
	public void setOption(CodeItem option) {
		this.option = option;
	}

	@SuppressWarnings("unused")
	public CodeItem getFmonth() {
		if(fmonth == null)
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
			
			fmonth = new CodeItem();
			fmonth.setId(calendar.get(Calendar.MONTH));
			fmonth.setCode(c);
			fmonth.setDescription(d);
		}
		return fmonth;
	}

	public void setFmonth(CodeItem fmonth) {
		this.fmonth = fmonth;
	}

	@SuppressWarnings("unused")
	public CodeItem getTmonth() {
		if(tmonth == null)
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
			
			tmonth = new CodeItem();
			tmonth.setId(calendar.get(Calendar.MONTH));
			tmonth.setCode(c);
			tmonth.setDescription(d);
		}
		return tmonth;
	}

	public void setTmonth(CodeItem tmonth) {
		this.tmonth = tmonth;
	}
}
