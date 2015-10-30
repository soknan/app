package kredit.web.util.monthly.vm;

import java.util.Calendar;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.model.CodeItem;
import kredit.web.util.monthly.model.Environment;
import kredit.web.util.monthly.model.LoanVsu;
import kredit.web.util.monthly.model.MCommon;
import kredit.web.util.transfer.model.LoanWriteOff;
import kredit.web.util.monthly.model.ParamMonthly;
import kredit.web.util.monthly.model.facade.MonthlyFacade;
import kredit.web.util.transfer.model.Loan;
import kredit.web.util.transfer.model.LoanClose;
import kredit.web.util.monthly.model.LoanDrop;
import kredit.web.util.transfer.model.Saving;
import kredit.web.util.transfer.model.SavingClose;
import kredit.web.writeOff.model.facade.WriteOffFacade;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;

public class MainVM {
	
	ListModelList<Loan> lstLoan;
	ListModelList<LoanClose> lstLoanClose;
	ListModelList<LoanDrop> lstLoanDrop;
	ListModelList<LoanVsu> lstLoanVsu;
	ListModelList<LoanWriteOff> lstLoanWriteOff;
	ListModelList<Saving> lstSaving;
	ListModelList<SavingClose> lstSavingClose;
	
	ListModelList<CodeItem> filterBranches;
	ListModelList<CodeItem> filterSubBranches;
	ListModelList<CodeItem> options;
	ListModelList<CodeItem> months;
	
	ParamMonthly param = new ParamMonthly();
	
	Privilege privilege = null;
	
	public static String L_TABLE = "INC_LOAN_ACTIVE";
	private Environment env = new Environment();
	private boolean visibleEnv = false;
	private boolean disableMode = true;
	
	@Wire("#smr")
	Window smr;
	
	private boolean disableRetrieve = true;
	
//GETTER AND SETTER	
	
	public Privilege getPrivilege() {
		if(privilege == null)
		{
			privilege = Common.getPrivilege(CMD.LIST_MONTHLY);
		}
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public ListModelList<CodeItem> getFilterBranches() {
		if(filterBranches == null)
		{
			filterBranches = new ListModelList<CodeItem>(WriteOffFacade.getBranchesListWithAll());
		}
		return filterBranches;
	}

	public void setFilterBranches(ListModelList<CodeItem> filterBranches) {
		this.filterBranches = filterBranches;
	}
	
	public ListModelList<CodeItem> getFilterSubBranches() {
		if(filterSubBranches == null)
		{
			filterSubBranches = new ListModelList<CodeItem>(WriteOffFacade.getSubBranchesListWithAll(param.getBranch().getId()));
		}
		return filterSubBranches;
	}

	public void setFilterSubBranches(ListModelList<CodeItem> filterSubBranches) {
		this.filterSubBranches = filterSubBranches;
	}
	
	public ListModelList<CodeItem> getOptions() {
		if (options != null)
			return options;
		options = new ListModelList<CodeItem>();
		String[] desc = new String[] { "Both", "Loan", "Saving" };
		String[] code = new String[] { "B", "L", "S" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setId(i);
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			options.add(item);
		}
		return options;
	}
	
	public void setOptions(ListModelList<CodeItem> options) {
		this.options = options;
	}

	public ListModelList<CodeItem> getMonths() {
		if (months != null)
			return months;
		months = new ListModelList<CodeItem>();
		
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		
		String[] desc = new String[] { "Jan - " + year, "Feb - " + year, 
									   "Mar - " + year, "Apr - " + year,  
									   "May - " + year, "Jun - " + year, 
									   "Jul - " + year, "Aug - " + year, 
									   "Sep - " + year, "Oct - " + year, 
									   "Nov - " + year, "Dec - " + year};
		String[] code = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", 
									   "Sep", "Oct", "Nov", "Dec" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setId(i);
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			months.add(item);
		}
		return months;
	}
	
	public void setMonths(ListModelList<CodeItem> months) {
		this.months = months;
	}
	
	public ParamMonthly getParam() {
		return param;
	}

	public void setParam(ParamMonthly param) {
		this.param = param;
	}
	
	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}
	
	public boolean isVisibleEnv() {
		return visibleEnv;
	}

	public void setVisibleEnv(boolean visibleEnv) {
		this.visibleEnv = visibleEnv;
	}
	
	public boolean isDisableMode() {
		return disableMode;
	}

	public void setDisableMode(boolean disableMode) {
		this.disableMode = disableMode;
	}
	
	public boolean isDisableRetrieve() {
		return disableRetrieve;
	}

	public void setDisableRetrieve(boolean disableRetrieve) {
		this.disableRetrieve = disableRetrieve;
	}
	
//END GETTER AND SETTER

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	public MainVM() {
		/*
		System.out.println(MonthlyFacade.getSdofMonth(param.getMonth().getId(), param.getMonth().getCode()));
		System.out.println(MonthlyFacade.getEdofMonth(param.getMonth().getId(), param.getMonth().getCode()));
		*/
		
		env.setMode(MCommon.AUTHEN_MODE_DEFAULT);
	}
	
	@Command
	@NotifyChange({ "lst", "filterSubBranches" })
	public void onSelectBranch()
	{
		filterSubBranches = null;
	}
	
	@Command
	@NotifyChange({ "param", "lst" })
	public void onSelectSubBranch()
	{
		param.getBranch().setId(param.getSubBranch().getSuperId());
		param.getBranch().setCode(param.getSubBranch().getSuperCode());
		param.getBranch().setDescription(param.getSubBranch().getSuperDescription());
	}
	
	@Command
	@NotifyChange({ "param" })
	public void onClear()
	{
		param = new ParamMonthly();
	}
	
	//This function is for testing only
	@Command
	public void testRetrieve()
	{	
		lstLoanVsu = new ListModelList<LoanVsu>(MonthlyFacade.getLoanVSUList(param));
		MonthlyFacade.removeOldIncData(lstLoanVsu.get(0).getReport_date(), "INC_LOAN_VSU");
		Ebean.save(lstLoanVsu);
		//RETRIEVE ALL DATA
		/*
		lstLoan = new ListModelList<Loan>(MonthlyFacade.getLoanList(param));
		lstLoanClose = new ListModelList<LoanClose>(MonthlyFacade.getLoanCloseList(param));
		lstLoanDrop = new ListModelList<LoanDrop>(MonthlyFacade.getLoanDropList(param));
		lstLoanVsu = new ListModelList<LoanVsu>(MonthlyFacade.getLoanVSUList(param));
		lstLoanWriteOff = new ListModelList<LoanWriteOff>(MonthlyFacade.getLoanWriteOffList(param));
		lstSaving = new ListModelList<Saving>(MonthlyFacade.getSavingList(param));
		lstSavingClose = new ListModelList<SavingClose>(MonthlyFacade.getSavingCloseList(param));
		*/
		
		//DELETE ALL DATA
		/*
		MonthlyFacade.removeOldIncData(lstLoan.get(0).getReport_date(), "INC_LOAN_ACTIVE");
		MonthlyFacade.removeOldIncData(lstLoanClose.get(0).getReport_date(), "INC_LOAN_CLOSE");
		MonthlyFacade.removeOldIncData(lstLoanDrop.get(0).getReport_date(), "INC_LOAN_DROP_OUT");
		MonthlyFacade.removeOldIncData(lstLoanVsu.get(0).getReport_date(), "INC_LOAN_VSU");
		MonthlyFacade.removeOldIncData(lstLoanWriteOff.get(0).getReport_date(), "INC_LOAN_WRITE_OFF");
		MonthlyFacade.removeOldIncData(lstSaving.get(0).getReport_date(), "INC_SAVING_ACTIVE");
		MonthlyFacade.removeOldIncData(lstSavingClose.get(0).getReport_date(), "INC_SAVING_CLOSE");
		*/
		
		/*
		for(int i = 0; i < lstLoanClose.size(); i++)
		{
			System.out.println("NO: " + i + " || LAST_ALIQ_DATE: " + lstLoanClose.get(i).getLast_aliq_date());;
		}
		*/
		
		//SAVE ALL DATA
		/*
		Ebean.save(lstLoan);
		Ebean.save(lstLoanClose);
		Ebean.save(lstLoanDrop);
		Ebean.save(lstLoanVsu);
		Ebean.save(lstLoanWriteOff);
		Ebean.save(lstSaving);
		Ebean.save(lstSavingClose);
		*/
		Clients.showNotification("Done.");
	}
	
	@Command
	@NotifyChange({ "lstLoan", "lstLoanVsu", "lstLoanWriteOff", "lstSaving", "message" })
	public void onRetrieve()
	{
		if(param.getOption().getId() == 0)
		{
			lstLoan = new ListModelList<Loan>(MonthlyFacade.getLoanList(param));
			lstLoanClose = new ListModelList<LoanClose>(MonthlyFacade.getLoanCloseList(param));
			lstLoanDrop = new ListModelList<LoanDrop>(MonthlyFacade.getLoanDropList(param));
			lstLoanVsu = new ListModelList<LoanVsu>(MonthlyFacade.getLoanVSUList(param));
			lstLoanWriteOff = new ListModelList<LoanWriteOff>(MonthlyFacade.getLoanWriteOffList(param));
			lstSaving = new ListModelList<Saving>(MonthlyFacade.getSavingList(param));
			lstSavingClose = new ListModelList<SavingClose>(MonthlyFacade.getSavingCloseList(param));
			
			if(lstLoan.size() > 0)
			{
				MonthlyFacade.removeOldIncData(lstLoan.get(0).getReport_date(), "INC_LOAN_ACTIVE");
				
				Ebean.save(lstLoan);
			}
			
			if(lstLoanClose.size() > 0)
			{
				MonthlyFacade.removeOldIncData(lstLoanClose.get(0).getReport_date(), "INC_LOAN_CLOSE");
				
				Ebean.save(lstLoanClose);
			}
			
			if(lstLoanDrop.size() > 0)
			{
				MonthlyFacade.removeOldIncData(lstLoanDrop.get(0).getReport_date(), "INC_LOAN_DROP_OUT");
				
				Ebean.save(lstLoanDrop);
			}
			
			if(lstLoanVsu.size() > 0)
			{
				MonthlyFacade.removeOldIncData(lstLoanVsu.get(0).getReport_date(), "INC_LOAN_VSU");
				
				Ebean.save(lstLoanVsu);
			}
			
			if(lstLoanWriteOff.size() > 0)
			{
				MonthlyFacade.removeOldIncData(lstLoanWriteOff.get(0).getReport_date(), "INC_LOAN_WRITE_OFF");
				
				Ebean.save(lstLoanWriteOff);
			}
			
			if(lstSaving.size() > 0)
			{
				MonthlyFacade.removeOldIncData(lstSaving.get(0).getReport_date(), "INC_SAVING_ACTIVE");
				
				Ebean.save(lstSaving);
			}
			
			if(lstSavingClose.size() > 0)
			{
				MonthlyFacade.removeOldIncData(lstSavingClose.get(0).getReport_date(), "INC_SAVING_CLOSE");
				
				Ebean.save(lstSavingClose);
			}
			
			Clients.showNotification("Retrieve both Loan and Saving Successfully.");
			
		}
		
		if(param.getOption().getId() == 1)
		{
			lstLoan = new ListModelList<Loan>(MonthlyFacade.getLoanList(param));
			lstLoanClose = new ListModelList<LoanClose>(MonthlyFacade.getLoanCloseList(param));
			lstLoanDrop = new ListModelList<LoanDrop>(MonthlyFacade.getLoanDropList(param));
			lstLoanVsu = new ListModelList<LoanVsu>(MonthlyFacade.getLoanVSUList(param));
			lstLoanWriteOff = new ListModelList<LoanWriteOff>(MonthlyFacade.getLoanWriteOffList(param));
			
			if(lstLoan.size() > 0)
			{
				//Delete from kSupport DB
				MonthlyFacade.removeOldIncData(lstLoan.get(0).getReport_date(), "INC_LOAN_ACTIVE");
				
				Ebean.save(lstLoan);
			}
			
			if(lstLoanClose.size() > 0)
			{
				//Delete from kSupport DB
				MonthlyFacade.removeOldIncData(lstLoanClose.get(0).getReport_date(), "INC_LOAN_CLOSE");
				
				Ebean.save(lstLoanClose);
			}
			
			if(lstLoanDrop.size() > 0)
			{
				//Delete from kSupport DB
				MonthlyFacade.removeOldIncData(lstLoanDrop.get(0).getReport_date(), "INC_LOAN_DROP_OUT");
				
				Ebean.save(lstLoanDrop);
			}
			
			if(lstLoanVsu.size() > 0)
			{
				//Delete from kSupport DB
				MonthlyFacade.removeOldIncData(lstLoanVsu.get(0).getReport_date(), "INC_LOAN_VSU");
				
				Ebean.save(lstLoanVsu);
			}
			
			if(lstLoanWriteOff.size() > 0)
			{
				//Delete from kSupport DB
				MonthlyFacade.removeOldIncData(lstLoanWriteOff.get(0).getReport_date(), "INC_LOAN_WRITE_OFF");
				
				Ebean.save(lstLoanWriteOff);
			}
			
			Clients.showNotification("Retrieve Loan Successfully."); 
		}
		
		if(param.getOption().getId() == 2)
		{
			
			lstSaving = new ListModelList<Saving>(MonthlyFacade.getSavingList(param));
			lstSavingClose = new ListModelList<SavingClose>(MonthlyFacade.getSavingCloseList(param));
			
			if(lstSaving.size() > 0)
			{
				//Delete from kSupport DB
				MonthlyFacade.removeOldIncData(lstSaving.get(0).getReport_date(), "INC_SAVING_ACTIVE");
				
				Ebean.save(lstSaving);
			}
			
			if(lstSavingClose.size() > 0)
			{
				//Delete from kSupport DB
				MonthlyFacade.removeOldIncData(lstSavingClose.get(0).getReport_date(), "INC_SAVING_CLOSE");
				
				Ebean.save(lstSavingClose);
			}
			
			Clients.showNotification("Retrieve Saving Successfully.");
		}
	}
	
	@Command
	@NotifyChange({ "visibleEnv" })
	public void onOpenEnv() {
		visibleEnv = true;

		if(smr.hasFellow("userDetail"))
			return;
		
		Executions.createComponents("/util/mdr/environment.zul", smr, null);
	}

	@Command
	@NotifyChange({ "visibleEnv" })
	public void onCloseEnv() {
		visibleEnv = false;
	}
	
	@Command
	@NotifyChange({ "visibleEnv", "disableRetrieve" })
	public void onConfig() {
		if(env.getEnvironment() == null || env.getEnvironment().trim().equals("")) {
			Clients.showNotification("Please input Environment.");
			return;
		}
		
		if(env.getMode() == MCommon.AUTHEN_MODE_DEFAULT) {
			env.setUsername("K");
			env.setPassword("K123");
		} else {
			
			if(env.getUsername() == null || env.getUsername().trim().equals("")) {
				Clients.showNotification("Please input Username.");
				return;
			}
			
			if(env.getPassword() == null || env.getPassword().trim().equals("")) {
				Clients.showNotification("Please input Password.");
				return;
			}
		}
		
		MonthlyFacade.environment = env.getEnvironment();
		MonthlyFacade.username = env.getUsername();
		MonthlyFacade.password = env.getPassword();
		
		disableRetrieve = false;
		visibleEnv = false;
	}
	
	@Command
	@NotifyChange({ "disableMode" })
	public void onChangeAuthenMode() {
		if(env.getMode() == MCommon.AUTHEN_MODE_OTHER) {
			disableMode = false;
		} else {
			disableMode = true;
		}
		
	}
}
