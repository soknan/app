package kredit.web.util.incentive.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.util.incentive.model.IncentiveLoan;
import kredit.web.util.incentive.model.IncentiveSaving;
import kredit.web.util.incentive.model.ParamIncentive;
import kredit.web.util.incentive.model.facade.IncentiveFacade;
import kredit.web.writeOff.model.facade.WriteOffFacade;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import com.avaje.ebean.Ebean;

public class SummaryVM {
	
	ListModelList<IncentiveSaving> lstISaving;
	ListModelList<IncentiveLoan> lstILoan;
	
	ListModelList<CodeItem> filterBranches;
	ListModelList<CodeItem> filterSubBranches;
	ListModelList<CodeItem> options;
	ListModelList<CodeItem> fMonths;
	ListModelList<CodeItem> tMonths;
	
	ParamIncentive param = new ParamIncentive();
	boolean disableF;
	boolean disableT;
	
	Privilege privilege = null;
	
//GETTER AND SETTER	
	
	public Privilege getPrivilege() {
		if(privilege == null)
		{
			privilege = Common.getPrivilege(CMD.LIST_INCENTIVE_SUMMARY);
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
		String[] desc = new String[] { "Loan", "Saving" };
		String[] code = new String[] { "L", "S" };
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

	public ListModelList<CodeItem> getfMonths() {
		if (fMonths != null)
			return fMonths;
		fMonths = new ListModelList<CodeItem>();
		
		List<String> lstMonths = new ArrayList<String>(IncentiveFacade.getAvailableMonths());
		for (int i = 0; i < lstMonths.size(); i++) {
			CodeItem item = new CodeItem();
			item.setId(i);
			item.setCode(lstMonths.get(i));
			item.setDescription(lstMonths.get(i));
			fMonths.add(item);
		}
		return fMonths;
	}

	public void setfMonths(ListModelList<CodeItem> fMonths) {
		this.fMonths = fMonths;
	}

	public ListModelList<CodeItem> gettMonths() {
		if (tMonths != null)
			return tMonths;
		tMonths = new ListModelList<CodeItem>();
		
		List<String> lstMonths = new ArrayList<String>(IncentiveFacade.getAvailableMonths());
		for (int i = 0; i < lstMonths.size(); i++) {
			CodeItem item = new CodeItem();
			item.setId(i);
			item.setCode(lstMonths.get(i));
			item.setDescription(lstMonths.get(i));
			tMonths.add(item);
		}
		return tMonths;
	}

	public void settMonths(ListModelList<CodeItem> tMonths) {
		this.tMonths = tMonths;
	}

	public ParamIncentive getParam() {
		return param;
	}

	public void setParam(ParamIncentive param) {
		this.param = param;
	}
	
	public boolean isDisableF() {
		return disableF;
	}

	public void setDisableF(boolean disableF) {
		this.disableF = disableF;
	}

	public boolean isDisableT() {
		return disableT;
	}

	public void setDisableT(boolean disableT) {
		this.disableT = disableT;
	}
	
//END GETTER AND SETTER	

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	public SummaryVM() {
		
		if(param.getOption().getId() == 1)
		{
			disableT = true;	
		}
		
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
		param = new ParamIncentive();
	}
	
	@Command
	@NotifyChange({ })
	public void onGenerate()
	{
		if(param.getOption().getId() == 0)
		{
			//Retrieving
			lstILoan = new ListModelList<IncentiveLoan>(IncentiveFacade.getIncentiveLoanList(param));
			
			if(lstILoan.size() > 0) {
				//Delete from kSupport DB
				IncentiveFacade.removeOldACMTData(lstILoan.get(0).getAcmt_date(), "INC_ACMT_LOAN");
				
				for(int i = 0; i < lstILoan.size(); i++)
				{
					lstILoan.get(i).setCreated_date(new Date());
					lstILoan.get(i).setCreated_user(UserCredentialManager.getIntance()
									.getLoginUsr().getUsername());
				}
				
				//Saving to kSupport DB
				Ebean.save(lstILoan);
				
				Clients.showNotification(lstILoan.size() + " Loan records has been saved.");
			}
		}
		else
		{
			//Retrieving
			lstISaving = new ListModelList<IncentiveSaving>(IncentiveFacade.getIncentiveSavingList(param));
			
			if(lstISaving.size() > 0) {
				//Delete from kSupport DB
				IncentiveFacade.removeOldACMTData(lstISaving.get(0).getAcmt_date(), "INC_ACMT_SAVING");
				
				for(int i = 0; i < lstISaving.size(); i++)
				{
					lstISaving.get(i).setCreated_date(new Date());
					lstISaving.get(i).setCreated_user(UserCredentialManager.getIntance()
									.getLoginUsr().getUsername());
				}
				
				//Saving to kSupport DB
				Ebean.save(lstISaving);
				
				Clients.showNotification(lstISaving.size() + " Saving records has been saved.");
				
				/*Problem with login to 23 database from Apache (Can login from Eclipse - Dev phrase) 
				//Remove the old data
				IncentiveFacade.removeIncSavingOld(lstISaving.get(0).getAcmt_date());
				
				//Saving to SQL Server DB
				for(int i = 0; i < lstISaving.size(); i++)
				{
					IncentiveFacade.saveIncentiveSaving(lstISaving.get(i));
				}
				
				Clients.showNotification("Data has been imported to SQL Server. There are " + IncentiveFacade.getIncentiveCount() + " Records.");
				*/
			}
			
		}
	}
	
	@Command
	@NotifyChange({ "disableT" })
	public void onChangeOption()
	{
		if(param.getOption().getId() == 1)
		{
			disableT = true;	
		}
		else
		{
			disableT = false;
		}
	}
}
