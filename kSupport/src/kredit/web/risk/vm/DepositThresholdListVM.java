package kredit.web.risk.vm;

import java.util.Date;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.risk.model.DepositThresholdModel;
import kredit.web.risk.model.ParamModel;
import kredit.web.risk.model.facade.RiskRateFacade;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;

public class DepositThresholdListVM {
	ListModelList<DepositThresholdModel> lstDepositThreshold;
	DepositThresholdModel selected;
	ParamModel param = new ParamModel();
	Privilege privilege;
	
	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	
	@Wire("#dethrels")
	private Window ratels;
	
	@Init
	public void init() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange({"selected"})
	public void onSave(){
		System.out.println(selected.getId());
		if(selected.getId() == null)
		{
			selected.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selected.setCreate_on(new Date());
			Ebean.save(selected);
		}
		else
		{
			selected.setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selected.setChange_on(new Date());
			Ebean.update(selected);
		}
		
		Clients.showNotification("Saved");
	}
	
	@Command
	@NotifyChange()
	public void onOpen(){
		Boolean wait = ratels.hasFellow("dethreip");		
		if(wait)
			return;
		Executions.createComponents("/risk/depositThresholdInput.zul", ratels, null);
	}
	
	@Command
	@NotifyChange({"lstDepositThreshold"})
	public void doSearch(){
		lstDepositThreshold = null;
		selected = null;
	}
	
	@Command
	@NotifyChange({"lstDepositThreshold","selected","param"})
	public void onClear(){
		lstDepositThreshold = null;
		selected = null;
		param = new ParamModel();
	}
	
	public ListModelList<DepositThresholdModel> getLstDepositThreshold() {
		if(lstDepositThreshold==null){
			lstDepositThreshold = new ListModelList<>(RiskRateFacade.getLstDepositThreshold(param));
		}
		return lstDepositThreshold;
	}
	public void setLstDepositThreshold(
			ListModelList<DepositThresholdModel> lstDepositThreshold) {
		this.lstDepositThreshold = lstDepositThreshold;
	}
	public DepositThresholdModel getSelected() {
		return selected;
	}
	public void setSelected(DepositThresholdModel selected) {
		this.selected = selected;
	}
	public ListModelList<CodeItem> getRows() {
		if (rows != null)
			return rows;
		rows = new ListModelList<CodeItem>();
		String[] desc = new String[] { "5", "10", "15", "20", "25", "30", "40",
				"50" };
		String[] code = new String[] { "5", "10", "15", "20", "25", "30", "40",
				"50" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			rows.add(item);
		}
		return rows;
	}
	public void setRows(ListModelList<CodeItem> rows) {
		this.rows = rows;
	}
	public CodeItem getSelectedNumRow() {
		if (selectedNumRow != null)
			return selectedNumRow;
		selectedNumRow = new CodeItem();
		selectedNumRow.setCode("10");
		selectedNumRow.setDescription("10");
		return selectedNumRow;
	}
	public void setSelectedNumRow(CodeItem selectedNumRow) {
		this.selectedNumRow = selectedNumRow;
	}


	public ParamModel getParam() {
		return param;
	}


	public void setParam(ParamModel param) {
		this.param = param;
	}

	public Window getRatels() {
		return ratels;
	}

	public void setRatels(Window ratels) {
		this.ratels = ratels;
	}

	public Privilege getPrivilege() {
		if (privilege != null)
			return privilege;

		privilege = Common.getPrivilege(CMD.LIST_CB);
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
	
	
}
