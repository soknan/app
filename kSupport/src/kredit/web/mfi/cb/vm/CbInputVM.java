package kredit.web.mfi.cb.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import kredit.flexcube.model.CIF;
import kredit.flexcube.model.Village;
import kredit.web.core.model.Domain;
import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.NumberTranslater;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.zk.MySimpleForm;
import kredit.web.mfi.model.CB;
import kredit.web.mfi.model.CbCycle;
import kredit.web.mfi.model.Group;
import kredit.web.mfi.model.GroupMember;
import kredit.web.mfi.model.Group.Type;
import kredit.web.mfi.model.facade.MfiFacade;
import kredit.web.mfi.model.Leader;

import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Form;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DefaultCommand;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.FetchConfig;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.TxRunnable;

public class CbInputVM {

	static Logger logger = Logger.getLogger(CbInputVM.class);

	@Wire("#cbip")
	Window winCbInput;

	// get reference from method onShowFetchVillage
	@Wire("#txtCbNameEN")
	Textbox txtCbNameEN;

	@Wire("#txtVillage")
	Textbox txtVillage;

	@Wire("#txtVillageID")
	Textbox txtVillageID;

	@Wire("#txtVillageSeq")
	Textbox txtVillageSeq;

	// get reference when user clicks on search CIF
	Textbox txtLeaderCIF;
	Textbox txtLeaderName;
	Datebox dtStart;
	Datebox dtEnd;
	
	Textbox txtNumLetter;

	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;

	// lookup list value
	ListModelList<CIF> lstFetchCIF = new ListModelList<CIF>();
	CIF selectedCIF;
	CIF paramCIF = new CIF();

	ListModelList<Village> lstFetchVillage = new ListModelList<Village>();
	Village selectedVillage;
	Village paramVillage = new Village();

	CB selected;

	private MySimpleForm frm = new MySimpleForm();

	private MySimpleForm frmCycle = new MySimpleForm();
	private MySimpleForm frmLeader = new MySimpleForm();

	CbCycle selectedCycle = new CbCycle();
	Leader currentCbLeader = new Leader();
	// List<CbCycle> prevCbCycles;

	ListModelList<Group> availGrps;
	Set<Group> selectAvails;
	ListModelList<Group> chosenGrps;
	Set<Group> selectChosens;
	String groupFilter = "%";

	Domain domain = new Domain();

	private boolean notifyChange;

	boolean bCurrentCycle = true;
	
	boolean bStatus=false;
	
	List<CodeItem> lstStatus = new ArrayList<CodeItem>();
	
	
	String lStatus = "Close";
	
	//region paging
	
	private int pageIndex;
	private PagingList<Village> pagingList;
	private int totalSize;

	private Privilege privilege;
	
	//endregion paging
		

	@Init
	public void init(@ExecutionArgParam("obj") Object obj) {
		if (obj == null) {
			return;
		}
		selected = (CB) obj;
		currentCbLeader = selected.getCurrentCbCycle().getCurrentLeader();

		// *************** this code cause ZK crazy! **************

		/*
		 * selectedCycle = Ebean .find(CbCycle.class)
		 * .select("cycleNo, startDate, endDate, leaderCIF, leaderName, countGroup"
		 * ) .where().eq("cb", selected) .eq("cycleNo",
		 * selected.getLastCycleNo()).findUnique();
		 */

		// **************************************************************************************************

		// maker info shown in status bar
		domain = (Domain) selected;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange({ "selected" })
	public void onNew() {
		selected = null;
	}

	@Command
	@NotifyChange({ "selectedCycle", "chosenGrps" })
	public void onNew2() {
		onNewCycle();
	}
	
	@Command	
	public void onStatus(){
		bStatus = true;
		onSave();
		BindUtils.postNotifyChange(null, null, this, "*");
	}

	@Command
	@NotifyChange({ "selected", "domain","lStatus" })
	public void onSave() {
		try {
			StringBuilder strBuilder = Common.createLogStringBuilder();
			if (getSelected().getId() == null) {
				selected.setBrCd(UserCredentialManager.getIntance()
						.getLoginUsr().getHomeBranch());
				selected.setCreateBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());
				selected.setRecordStat("O");
				strBuilder.append(" created a new CB --> ");
			} else {
				if(bStatus){
					if(selected.getRecordStat().equals("O")){
						selected.setRecordStat("C");
					}else{
						selected.setRecordStat("O");
					}
				}
				selected.setChangeBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());
				
				strBuilder.append(" updated a CB --> ");
			}

			Ebean.save(selected);
			
			strBuilder.append(getSelected().toString());
			Common.addSessionLog(CMD.CB_INPUT, strBuilder.toString(), new Date());

			domain = (Domain) selected;

			Clients.showNotification("Save successfully", "info", null,
					"middle_center", -1);
		} catch (OptimisticLockException e) {
			logger.error("Error while saving ebean CB", e);
			Clients.showNotification("Save failed!", "error", null,
					"middle_center", -1);
		}
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

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(ListModelList<CodeItem> rows) {
		this.rows = rows;
	}

	/**
	 * @return the selectedNumRow
	 */
	public CodeItem getSelectedNumRow() {
		return selectedNumRow;
	}

	/**
	 * @param selectedNumRow
	 *            the selectedNumRow to set
	 */
	public void setSelectedNumRow(CodeItem selectedNumRow) {
		this.selectedNumRow = selectedNumRow;
	}

	@DefaultCommand
	public void dummy() {

	}

	@Command
	@NotifyChange({"selectedVillage", "lstFetchVillage"})
	public void onShowFetchVillage(
			@BindingParam("txtVillage") Textbox txtVillage,
			@BindingParam("txtVillageID") Textbox txtVillageID,
			@BindingParam("txtVillageSeq") Textbox txtVillageSeq,
			@BindingParam("txtCbNameEN") Textbox txtCbNameEN) {
		if (this.txtVillage == null)
			this.txtVillage = txtVillage;
		if (this.txtCbNameEN == null)
			this.txtCbNameEN = txtCbNameEN;
		if (this.txtVillageID == null)
			this.txtVillageID = txtVillageID;
		if (this.txtVillageSeq == null)
			this.txtVillageSeq = txtVillageID;

		String villageName = this.txtVillage.getText(); // frm.getField("villageName").toString();
		paramVillage.setVillage(villageName + "%");
		
		selectedVillage = null;
		lstFetchVillage = null;

		Window comp = (Window) Executions.createComponents(
				"/common/listval/village.zul", winCbInput, null);
		winCbInput.appendChild(comp);
	}

	@Command
	@NotifyChange({"selectedCIF", "lstFetchCIF"})
	public void onShowFetchCIF(@BindingParam("txtLeaderCIF") Textbox txtCIF,
			@BindingParam("txtLeaderName") Textbox txtLeaderName) {
		if (this.txtLeaderCIF == null) {
			this.txtLeaderCIF = txtCIF;
		}

		if (this.txtLeaderName == null) {
			this.txtLeaderName = txtLeaderName;
		}

		paramCIF.setCustNo(this.txtLeaderCIF.getText() + "%");
		paramCIF.setFullName("%");
		paramCIF.setShortName("%");
		
		selectedCIF = null;
		lstFetchCIF = null;

		Executions
				.createComponents("/common/listval/cif.zul", winCbInput, null);
	}

	@Command
	@NotifyChange({"lstFetchVillage", "pageIndex", "totalSize"})
	public void onSearchVillage() {
		pageIndex = 0;
		lstFetchVillage = null;
		System.out.println("searching..");
	}

	@Command
	@NotifyChange({"lstFetchVillage", "totalSize"})
	public void onSearchVillageByID() {
		String strId = paramVillage.getStrID();
		if (strId.length() > 0) {
			if (strId.charAt(0) == '0') {
				strId = strId.substring(1);
			}
		}

		pagingList = Ebean.find(Village.class).setUseQueryCache(true)
				.where().ilike("id", strId).findPagingList(10);

		pagingList.getFutureRowCount();

		// get the first page
		Page<Village> page = pagingList.getPage(0);

		// get the beans from the page as a list
		List<Village> lst = page.getList();
		
		totalSize = page.getTotalRowCount();
		
		lstFetchVillage = new ListModelList<Village>(lst);
	}


	@Command
	@NotifyChange("lstFetchCIF")
	public void onSearchCIF() {
		lstFetchCIF = null;
		System.out.println("searching..");
	}

	@Command
	@NotifyChange({ "lstFetchVillage", "paramVillage", "pageIndex", "totalSize" })
	public void onResetSearchVillage() {
		pageIndex = 0;
		lstFetchVillage = null;
		paramVillage = new Village();
		System.out.println("reseting...");
	}

	@Command
	@NotifyChange({"lstFetchCIF", "paramCIF"})
	public void onResetSearchCIF() {
		lstFetchCIF = new ListModelList<CIF>();
		paramCIF = new CIF();
		System.out.println("reseting...");
	}

	/**
	 * @return the lstFetchVillage
	 */
	
	@NotifyChange({"totalSize", "lstFetchVillage"})
	public ListModelList<Village> getLstFetchVillage() {
		if (lstFetchVillage == null) {
			
			pagingList = Ebean.find(Village.class)
					.setUseQueryCache(true).where()
					.ilike("village", paramVillage.getVillage())
					.ilike("commune", paramVillage.getCommune())
					.ilike("district", paramVillage.getDistrict())
					.ilike("province", paramVillage.getProvince()).findPagingList(10);

			pagingList.getFutureRowCount();

			// get the first page
			Page<Village> page = pagingList.getPage(0);

			// get the beans from the page as a list
			List<Village> lst = page.getList();
			
			totalSize = page.getTotalRowCount();
			
			lstFetchVillage = new ListModelList<Village>(lst);
		}

		return lstFetchVillage;
	}

	/**
	 * @param lstFetchVillage
	 *            the lstFetchVillage to set
	 */
	public void setLstFetchVillage(ListModelList<Village> lstFetchVillage) {
		this.lstFetchVillage = lstFetchVillage;
	}

	/**
	 * @return the selectedVillage
	 */
	public Village getSelectedVillage() {
		return selectedVillage;
	}

	/**
	 * @param selectedVillage
	 *            the selectedVillage to set
	 */

	public void setSelectedVillage(Village selectedVillage) {
		this.selectedVillage = selectedVillage;

		/*
		 * frm.setField("villageName", selectedVillage.getVillage());
		 * frm.setField("villageID", String.format("%08d",
		 * selectedVillage.getId()));
		 */

		String villageID = String.format("%08d", selectedVillage.getId());
		int villageSeq = MfiFacade.getMaxVillageSeq(villageID) + 1;
		String cbNameEN = selectedVillage.getVillage().replace(villageID, "").trim();
		if (villageSeq > 1)
			cbNameEN = cbNameEN + "_" + villageSeq;

		txtVillage.setText(selectedVillage.getVillage());
		txtVillageID.setText(villageID);
		txtVillageSeq.setText(villageSeq + "");
		txtCbNameEN.setText(cbNameEN);

		// to notify textbox about its value change so that the form binding
		// will be notified its status too
		Events.postEvent("onChange", txtVillage, null);
		Events.postEvent("onChange", txtVillageID, null);
		Events.postEvent("onChange", txtVillageSeq, null);
		Events.postEvent("onChange", txtCbNameEN, null);

	}

	/**
	 * @return the paramVillage
	 */
	public Village getParamVillage() {
		return paramVillage;
	}

	/**
	 * @param paramVillage
	 *            the paramVillage to set
	 */
	public void setParamVillage(Village paramVillage) {
		this.paramVillage = paramVillage;
	}

	/**
	 * @return the lstFetchCIF
	 */
	public ListModelList<CIF> getLstFetchCIF() {
		if (lstFetchCIF == null) {
			List<CIF> lst = Ebean
					.find(CIF.class).select("custNo,fullName,shortName")
					.where()
					.in("custNo", getGroupMbrCIFs())
					.like("custNo", paramCIF.getCustNo())
					.ilike("fullName", paramCIF.getFullName())
					.ilike("shortName", paramCIF.getShortName()).findList();

			lstFetchCIF = new ListModelList<CIF>(lst);
		}
		return lstFetchCIF;
	}

	private List<String> getGroupMbrCIFs() {
		List<String> lst = new ArrayList<String>();
		List<GroupMember> lsMbr = Ebean.find(GroupMember.class).select("cif")
				.where().in("group", getChosenGrps()).isNotNull("isGroupLeader").findList();
		for (GroupMember mb : lsMbr) {
			lst.add(mb.getCif());
		}
		return lst;
	}

	/**
	 * @param lstFetchCIF
	 *            the lstFetchCIF to set
	 */
	public void setLstFetchCIF(ListModelList<CIF> lstFetchCIF) {
		this.lstFetchCIF = lstFetchCIF;
	}

	@Command
	@NotifyChange({ "selectedCycle", "chosenGrps" })
	public void onNewCycle() {
		selectedCycle = new CbCycle();
		selectedCycle
				.setCycleNo(MfiFacade.getMaxCbCycleNo(selected.getId()) + 1);
		getChosenGrps().clear();

		popupCbCycleWindow();
	}

	@Command
	@NotifyChange({ "selectedCycle", "bCurrentCycle" })
	public void onEditCycle(@BindingParam("id") int cycleID,
			@BindingParam("isCurrent") boolean isCurrent) {

		this.bCurrentCycle = isCurrent;
		
		//todo
		//selectedCycle = MfiFacade.getCbCycle(cycleID);
		 
		
		selectedCycle = Ebean
				.find(CbCycle.class)
				.select("cycleNo, startDate, endDate, amountMaxIndiv, amountMaxIndivTxt")
				.fetch("groups", "groupAccNo, leaderName, villageName",
						new FetchConfig().lazy()).where().eq("id", cycleID)
				.findUnique();
		

		// selectedCycle = selected.getCurrentCbCycle();

		popupCbCycleWindow();
	}

	private void popupCbCycleWindow() {
		if (!winCbInput.hasFellow(CMD.CB_CYCLE_INPUT)) {
			Window comp = (Window) Executions.createComponents(
					"/mfi/cb/cbCycleInput.zul", winCbInput, null);
			winCbInput.appendChild(comp);
		} else {
			winCbInput.getFellow(CMD.CB_CYCLE_INPUT).setVisible(true);
		}
	}

	/**
	 * @return the selectedCIF
	 */
	public CIF getSelectedCIF() {
		return selectedCIF;
	}

	/**
	 * @param selectedCIF
	 *            the selectedCIF to set
	 */
	public void setSelectedCIF(CIF selectedCIF) {
		this.selectedCIF = selectedCIF;
		txtLeaderCIF.setText(selectedCIF.getCustNo());
		txtLeaderName.setText(selectedCIF.getFullName());

		Events.postEvent("onChange", txtLeaderCIF, null);
		Events.postEvent("onChange", txtLeaderName, null);
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" assigned CBCycle LEADER [");
		//todo
		strBuilder.append(selectedCIF.toString());
		strBuilder.append("]");
		strBuilder.append(" to CbCycle --> ");
		strBuilder.append(getSelectedCycle().toString());
		Common.addSessionLog(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());
	}

	/**
	 * @return the paramCIF
	 */
	public CIF getParamCIF() {
		return paramCIF;
	}

	/**
	 * @param paramCIF
	 *            the paramCIF to set
	 */
	public void setParamCIF(CIF paramCIF) {
		this.paramCIF = paramCIF;
	}

	/**
	 * @return the selected
	 */
	public CB getSelected() {
		if (selected == null) {
			selected = new CB();
			selected.setBrCd(UserCredentialManager.getIntance().getLoginUsr()
					.getHomeBranch());
		}
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(CB selected) {
		this.selected = selected;
	}

	/**
	 * @return the frm
	 */
	public Form getFrm() {
		return frm;
	}

	/**
	 * @param frm
	 *            the frm to set
	 */
	public void setFrm(MySimpleForm frm) {
		this.frm = frm;
	}

	/**
	 * @return the selectedCycle
	 */
	public CbCycle getSelectedCycle() {
		return selectedCycle;
	}

	/**
	 * @param selectedCycle
	 *            the selectedCycle to set
	 */
	public void setSelectedCycle(CbCycle selectedCycle) {
		this.selectedCycle = selectedCycle;
	}

	/**
	 * @return the frmCycle
	 */
	public Form getFrmCycle() {
		return frmCycle;
	}

	/**
	 * @param frmCycle
	 *            the frmCycle to set
	 */
	public void setFrmCycle(MySimpleForm frmCycle) {
		this.frmCycle = frmCycle;
	}

	@Command
	@NotifyChange("availGrps")
	public void onSearchGroup() {
		/*
		 * Note: if use availGrps = null then availGrps = new ListModelList<>()
		 * ==> the listbox re render and doesn't support multi select So we just
		 * clear the model and addAll
		 */

		// availGrps = new ListModelList<>();

		availGrps.clear();
		// availGrps.removeRange(0, availGrps.getSize() - 1);
		availGrps.clearSelection();

		availGrps.addAll(new ListModelList<Group>(Ebean
				.find(Group.class)
				.select("groupAccNo, leaderName, villageName")
				.where()
				.eq("brCd",
						UserCredentialManager.getIntance().getLoginUsr()
								.getHomeBranch())
				.not(Expr.in("id", getChosenGrpIDs()))
				.eq("villageID", selected.getVillageID()).isNull("cbCycle")
				.eq("type", Type.CB).ilike("groupAccNo", groupFilter)
				.findList()));
		System.out.println("seaching available groups...");
	}

	/**
	 * @return the availGrps
	 */
	public ListModelList<Group> getAvailGrps() {
		if (availGrps == null) {
			availGrps = new ListModelList<Group>(Ebean
					.find(Group.class)
					.select("groupAccNo, leaderName, villageName")
					.where()
					.eq("brCd",
							UserCredentialManager.getIntance().getLoginUsr()
									.getHomeBranch())
					.not(Expr.in("id", getChosenGrpIDs()))
					.eq("villageID", selected.getVillageID()).isNull("cbCycle")
					.eq("type", Type.CB)
					.ilike("groupAccNo", groupFilter).findList());
		}
		System.out.println("avail groups: " + availGrps.size());
		return availGrps;
	}

	private List<Integer> getChosenGrpIDs() {
		List<Integer> lst = new ArrayList<Integer>();
		for (Group g : getChosenGrps()) {
			lst.add(g.getId());
		}
		return lst;
	}

	/**
	 * @param availGrps
	 *            the availGrps to set
	 */
	public void setAvailGrps(ListModelList<Group> availGrps) {
		this.availGrps = availGrps;
	}

	/**
	 * @return the chosenGrps
	 */
	public ListModelList<Group> getChosenGrps() {
		if (chosenGrps == null) {
			chosenGrps = new ListModelList<Group>(selectedCycle.getGroups());
		}
		return chosenGrps;
	}

	/**
	 * @param chosenGrps
	 *            the chosenGrps to set
	 */
	public void setChosenGrps(ListModelList<Group> chosenGrps) {
		this.chosenGrps = chosenGrps;
	}

	/**
	 * @return the groupFilter
	 */
	public String getGroupFilter() {
		return groupFilter;
	}

	/**
	 * @param groupFilter
	 *            the groupFilter to set
	 */
	public void setGroupFilter(String groupFilter) {
		this.groupFilter = groupFilter;
	}

	@Command
	@NotifyChange({ "availGrps", "chosenGrps", "selectAvails", "notifyChange" })
	public void onChoose() {
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		if(selectChosens.size() > 1){
			strBuilder.append(" added ").append(selectChosens.size()).append(" groups {");
		}else{
			strBuilder.append(" added ").append(selectChosens.size()).append(" group {");
		}
		
		for(Group m : selectChosens){
			if(selectChosens.size() < 6){
				strBuilder.append("[").append(m.toStringSummary()).append("]");
			}else{
				strBuilder.append("[").append(m.getId()).append("]");
			}
		}
		strBuilder.append("} to the CBCycle -->");
		strBuilder.append(getSelectedCycle().toString());
		Common.addSessionLog(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());
		
		availGrps.removeAll(selectAvails);
		chosenGrps.addAll(selectAvails);
		availGrps.clearSelection();
		selectAvails = new HashSet<Group>();
		notifyChange = true;
	}

	@Command
	@NotifyChange({ "availGrps", "chosenGrps", "selectAvails", "notifyChange" })
	public void onChooseAll() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		if(availGrps.size() > 1)
			strBuilder.append(" added all avail groups (").append(availGrps.size()).append(") {");
		else
			strBuilder.append(" added all avail group (").append(availGrps.size()).append(") {");
		for(Group m : availGrps){
			if(availGrps.size() < 6){
				strBuilder.append("[").append(m.toStringSummary()).append("]");
			}else{
				strBuilder.append("[").append(m.getId()).append("]");
			}
		}
		strBuilder.append("} to the CBCycle --> ");
		strBuilder.append(getSelectedCycle().toString());
		Common.addSessionLog(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());

		chosenGrps.addAll(availGrps);
		availGrps.clear();
		availGrps.clearSelection();
		selectAvails = new HashSet<Group>();
		notifyChange = true;
	}

	@Command
	@NotifyChange({ "availGrps", "chosenGrps", "selectChosens", "notifyChange" })
	public void onRemoveAll() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		if(chosenGrps.size() > 1)
			strBuilder.append(" removed all groups (").append(chosenGrps.size()).append(") {");
		else
			strBuilder.append(" removed all group (").append(chosenGrps.size()).append(") {");
		for(Group m : chosenGrps){
			if(chosenGrps.size() < 6){
				strBuilder.append("[").append(m.toStringSummary()).append("]");
			}else{
				strBuilder.append("[").append(m.getId()).append("]");
			}
		}
		strBuilder.append("} from the CBCycle --> ");
		strBuilder.append(getSelectedCycle().toString());
		Common.addSessionLog(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());
		
		availGrps.addAll(chosenGrps);
		chosenGrps.clearSelection();
		chosenGrps.clear();
		selectChosens = new HashSet<Group>();
		notifyChange = true;
	}

	@Command
	@NotifyChange({ "availGrps", "chosenGrps", "selectChosens", "notifyChange" })
	public void onRemove() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		if(selectChosens.size() > 1)
			strBuilder.append(" removed ").append(selectChosens.size()).append(" groups {");
		else
			strBuilder.append(" removed ").append(selectChosens.size()).append(" group {");
		for(Group m : selectChosens){
			if(selectChosens.size() < 6){
				strBuilder.append("[").append(m.toStringSummary()).append("]");	
			}else{
				strBuilder.append("[").append(m.getId()).append("]");
			}
		}
		strBuilder.append("} from the CBCycle -->");
		strBuilder.append(getSelectedCycle().toString());
		Common.addSessionLog(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());
		
		chosenGrps.removeAll(selectChosens);
		availGrps.addAll(selectChosens);
		chosenGrps.clearSelection();
		selectChosens = new HashSet<Group>();
		notifyChange = true;
	}

	/**
	 * @return the selectChosens
	 */
	public Set<Group> getSelectChosens() {
		if (selectChosens == null)
			selectChosens = new HashSet<Group>();
		return selectChosens;
	}

	/**
	 * @param selectChosens
	 *            the selectChosens to set
	 */
	public void setSelectChosens(Set<Group> selectChosens) {
		this.selectChosens = selectChosens;
		System.out.println(selectChosens.size());
	}

	/**
	 * @return the selectAvails
	 */
	public Set<Group> getSelectAvails() {
		if (selectAvails == null)
			selectAvails = new HashSet<Group>();
		return selectAvails;
	}

	/**
	 * @param selectAvails
	 *            the selectAvails to set
	 */
	public void setSelectAvails(Set<Group> selectAvails) {
		this.selectAvails = selectAvails;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void onConfirmAddNewLeader(
			@BindingParam("txtLeaderCIF") Textbox txtCIF,
			@BindingParam("txtLeaderName") Textbox txtLeaderName,
			@BindingParam("dtStart") Datebox dtStart,
			@BindingParam("dtEnd") Datebox dtEnd) {

		if (this.txtLeaderCIF == null) {
			this.txtLeaderCIF = txtCIF;
		}

		if (this.txtLeaderName == null) {
			this.txtLeaderName = txtLeaderName;
		}

		if (this.dtStart == null) {
			this.dtStart = dtStart;
		}

		if (this.dtEnd == null) {
			this.dtEnd = dtEnd;
		}

		if (selectedCycle.getCurrentLeader().getEndDate() == null) {
			Clients.showNotification(
					"Please put end date for the current leader", "error",
					null, "middle_center", -1);
			
			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" was notified to put end date for the current CBCycle LEADER -->");
			strBuilder.append(selectedCycle.getCurrentLeader().toString());
			Common.addSessionLog(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());
			
			return;
		}

		Messagebox
				.show("The current leader will be added into History Leaders. Do you want to add a new leader for this CB cycle?",
						"Add New Leader Confirmation", Messagebox.YES
								| Messagebox.NO, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event evt)
									throws InterruptedException {
								if (evt.getName().equals("onYes")) {
									onNewLeader();
								}
							}
						});
	}

	public void onNewLeader() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" confirmed to assign a new LEADER of the CBCycle --> ");
		strBuilder.append(getSelectedCycle().toString());
		Common.addSessionLog(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());
		
		// Clear form for adding new leader
		selectedCycle.getCurrentLeader().setId(null);
		frmCycle.setField("currentLeader.id", null);

		txtLeaderCIF.setText("");
		txtLeaderName.setText("");
		dtStart.setText("");
		dtEnd.setText("");

		Events.postEvent("onChange", txtLeaderCIF, null);
		Events.postEvent("onChange", txtLeaderName, null);
		Events.postEvent("onChange", dtStart, null);
		Events.postEvent("onChange", dtEnd, null);
	}

	@Command
	public void onTest() {
		System.out.println("test");
	}

	// Save2 --> Save CB Cycle
	@Command
	@NotifyChange({ "selectedCycle", "selected", "notifyChange", "currentCbLeader" })
	public void onSave2() {

		getSelected();

		StringBuilder strBuilder = Common.createLogStringBuilder();
		
		// 1. CB Cycle Info
		if (selectedCycle.getId() == null) {
			selectedCycle.setCb(selected);
			selectedCycle.setVillageID(selected.getVillageID());
			selectedCycle.setCreateBy(UserCredentialManager.getIntance()
					.getLoginUsr().getUsername());
			selectedCycle.setBrCd(selected.getBrCd());
			
			strBuilder.append(" created new CBCycle --> ");
			
		} else {
			selected.setChangeBy(UserCredentialManager.getIntance()
					.getLoginUsr().getUsername());
			strBuilder.append(" updated CBCycle --> ");
		}

		// 2. add groups into CB Cycle if groups has changed
		if (notifyChange) {
			// remove old members
			Ebean.execute(new TxRunnable() {

				@Override
				public void run() {
					System.out.println(Ebean.currentTransaction());
					List<Group> lsGr = selectedCycle.getGroups();
					for (Group mbr : lsGr) {
						mbr.setCbCycle(null);
					}
					Ebean.save(lsGr);
				}
			});

			for (Group g : chosenGrps) {
				g.setCbCycle(selectedCycle);
			}
			selectedCycle.setGroups(chosenGrps);
			notifyChange = false;
		}

		try {
			boolean isNewLeader = selectedCycle.getCurrentLeader().getId() == null;

			if (frmLeader.isDirty()) {

				// 3. Leader Info
				Leader leader = currentCbLeader;
				
				// add leaderCIF to Cycle
				selectedCycle.setLeaderCIF(leader.getCif());

				StringBuilder leaderMsg = Common.createLogStringBuilder();
				// 3.1 create new leader
				if (isNewLeader) {
					leader.setId(null);
					leader.setBrCd(selected.getBrCd());
					leader.setCreateBy(UserCredentialManager.getIntance()
							.getLoginUsr().getUsername());

					selectedCycle.addLeader(leader);
					leaderMsg.append(" assigned a new CbCycle LEADER [");
					leaderMsg.append(leader.toStringSummary()).append("] to the CBCycle [");
				}
				// 3.2 update leader
				else {
					leader.setChangeBy(UserCredentialManager.getIntance()
							.getLoginUsr().getUsername());
					Ebean.update(leader);
					leaderMsg.append(" updated the CbCycle LEADER [");
					leaderMsg.append(leader.toStringSummary()).append("] of the CBCycle [");
				}

				
				leaderMsg.append(getSelected().toString()).append("]");
				Common.addSessionLog(CMD.CB_CYCLE_INPUT, leaderMsg.toString(), new Date());
				
				System.out.println("saved leader info...");

			}

			boolean isNewCycle = selectedCycle.getId() == null;
			// save CB Cycle
			Ebean.save(selectedCycle);

			if (isNewCycle) {
				selected.setPrevCbCycles(null);
			}

			if (frmLeader.isDirty() && isNewLeader) {
				int leaderID = MfiFacade
						.getCurrentCbCycleLeaderID(selectedCycle.getId());
				selectedCycle.setLeaderID(leaderID);
				Ebean.save(selectedCycle);
			}
			
			//refresh current leader --> set to null --> then it will get udpated data from getter method
			if (frmLeader.isDirty()){
				selectedCycle.setCurrentLeader(null);
			}

			// to refresh current cb cycle
			if (bCurrentCycle) {
				selected.setCurrentCycleID(selectedCycle.getId());
				selected.setCurrentCbCycle(null);
			}
			
			strBuilder.append(selected.toString());
			Common.addSessionLogCommit(CMD.CB_CYCLE_INPUT, strBuilder.toString(), new Date());

			System.out.println("saved CB info...");
			Clients.showNotification(
					"CB Cycle No " + selectedCycle.getCycleNo() + " of "
							+ selected.getNameEN()
							+ " has been saved successfully", "info", null,
					"middle_center", -1);
		} catch (OptimisticLockException e) {
			logger.error("Error while saving CB Cycle: cyle_no = "
					+ selectedCycle.getCycleNo(), e);
			Clients.showNotification(
					"Oops! CBC Cycle No "
							+ selectedCycle.getCycleNo()
							+ " of "
							+ selected.getNameEN()
							+ " has failed to save. Please contact IT Department for help.",
					"error", null, "middle_center", -1);
		}

		// update Leader Name at Current Cycle in window CB
		/*
		 * if (txtLeaderName != null) {
		 * currentCycle.setLeaderName(txtLeaderName.getText()); }
		 */
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * @return the notifyChange
	 */
	public boolean isNotifyChange() {
		return notifyChange;
	}

	/**
	 * @param notifyChange
	 *            the notifyChange to set
	 */
	public void setNotifyChange(boolean notifyChange) {
		this.notifyChange = notifyChange;
	}

	/*
	 * public List<CbCycle> getPrevCbCycles() { if (prevCbCycles == null) {
	 * prevCbCycles = MfiFacade.getPrevCbCycles(getSelected().getId(),
	 * getSelected().getCurrentCycleID()); } return prevCbCycles; }
	 * 
	 * 
	 * public void setPrevCbCycles(List<CbCycle> prevCbCycles) {
	 * this.prevCbCycles = prevCbCycles; }
	 */

	@Command
	public void onTabPrevCycle(@BindingParam("tabPrevCycles") Div tabPrevCycles) {

		if (tabPrevCycles.hasFellow("prevCycles")) {
			return;
		}

		Executions.createComponents("/mfi/cb/include/previousCbCycle.zul",
				tabPrevCycles, null);

	}

	/**
	 * @return the bCurrentCycle
	 */
	public boolean isbCurrentCycle() {
		return bCurrentCycle;
	}

	/**
	 * @param bCurrentCycle
	 *            the bCurrentCycle to set
	 */
	public void setbCurrentCycle(boolean bCurrentCycle) {
		this.bCurrentCycle = bCurrentCycle;
	}

	@Command
	public void translateToLetter(@BindingParam("amount") String amount,
			@BindingParam("txtNumLetter") Textbox txtNumLetter) {
		
		System.out.println(amount);
		
		if(this.txtNumLetter == null){
			this.txtNumLetter = txtNumLetter;
		}
		
		this.txtNumLetter.setText(NumberTranslater.translateToLetter(amount
				.toString(),""));
		
		Events.postEvent("onChange", txtNumLetter, null);
	}

	/**
	 * @return the frmLeader
	 */
	public MySimpleForm getFrmLeader() {
		return frmLeader;
	}

	/**
	 * @param frmLeader the frmLeader to set
	 */
	public void setFrmLeader(MySimpleForm frmLeader) {
		this.frmLeader = frmLeader;
	}

	/**
	 * @return the currentCbLeader
	 */
	public Leader getCurrentCbLeader() {
		if(currentCbLeader == null){
			currentCbLeader = new Leader();
		}
		return currentCbLeader;
	}

	/**
	 * @param currentCbLeader the currentCbLeader to set
	 */
	public void setCurrentCbLeader(Leader currentCbLeader) {
		this.currentCbLeader = currentCbLeader;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	@NotifyChange("lstFetchVillage")
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		// get the first page
		Page<Village> page = pagingList.getPage(pageIndex);

		// get the beans from the page as a list
		List<Village> lst = page.getList();
		lstFetchVillage = new ListModelList<Village>(lst);
	}

	/**
	 * @return the pagingList
	 */
	public PagingList<Village> getPagingList() {
		return pagingList;
	}

	/**
	 * @param pagingList the pagingList to set
	 */
	public void setPagingList(PagingList<Village> pagingList) {
		this.pagingList = pagingList;
	}

	/**
	 * @return the totalSize
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	/**
	 * @return the privilege
	 */
	public Privilege getPrivilege() {
		if (privilege != null)
			return privilege;

		privilege = Common.getPrivilege(CMD.LIST_CB);
		return privilege;
	}

	/**
	 * @param privilege
	 *            the privilege to set
	 */
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public boolean isbStatus() {
		return bStatus;
	}

	public void setbStatus(boolean bStatus) {
		this.bStatus = bStatus;
	}

	public String getlStatus() {
		if(selected.getRecordStat()!=null){
			if(selected.getRecordStat().equals("C")){
				lStatus = "Reopen";
			}else{
				lStatus = "Close";
			}
		}
		return lStatus;
	}

	public void setlStatus(String lStatus) {
		this.lStatus = lStatus;
	}
	
	public List<CodeItem> getLstStatus() {		
		try {			
			String sql = "select * from(SELECT id, code, desc_en description FROM sys_lov where type = 'MFI_STATUS' "
					+ "union select null,'%','All' from dual) order by code";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lstStatus = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (lstStatus == null) {
				lstStatus = new ArrayList<CodeItem>();
			}
			
		} catch (Exception e) {
			
		}
		return lstStatus;
	}

	public void setLstStatus(List<CodeItem> lstStatus) {
		this.lstStatus = lstStatus;
	}	
	
}
