package kredit.web.mfi.mbr.vm;

import java.util.Date;
import javax.persistence.OptimisticLockException;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;
import kredit.flexcube.model.CIF;
import kredit.flexcube.model.CltbAccountAppsMaster;
import kredit.web.core.model.Domain;
import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.NumberTranslater;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.zk.MySimpleForm;
import kredit.web.mfi.model.Coborrower;
import kredit.web.mfi.model.Group;
import kredit.web.mfi.model.GroupMember;
import kredit.web.mfi.model.Mobilizer;
import kredit.web.mfi.model.facade.MfiFacade;

public class MbrInputVM {

	static Logger logger = Logger.getLogger(MbrInputVM.class);

	GroupMember selected;
	Domain domain;

	ListModelList<CodeItem> lstFetchIDType;

	private MySimpleForm frm = new MySimpleForm();

	@Wire("#mbip")
	Window winMbrInput;

	// list val
	ListModelList<CIF> lstFetchCIF = new ListModelList<CIF>();
	CIF selectedCIF;
	CIF paramCIF;

	ListModelList<Mobilizer> lstFetchMobilizer = new ListModelList<Mobilizer>();
	Mobilizer selectedMobilizer;
	Mobilizer paramMz;

	ListModelList<Group> lstFetchGroup = new ListModelList<Group>();
	Group selectedGroup;
	Group paramGroup;

	ListModelList<String> lstFetchCoType;

	@Wire("#txtCIF")
	Textbox txtCIF;

	@Wire("#txtFullName")
	Textbox txtFullName;

	@Wire("#txtAccountNo")
	Textbox txtAccountNo;

	@Wire("#txtPrdCat")
	Textbox txtPrdCat;

	@Wire("#txtPrdCd")
	Textbox txtPrdCd;

	@Wire("#txtAmtDisb")
	Intbox txtAmtDisb;

	@Wire("#txtAmtDisbTxt")
	Textbox txtAmtDisbTxt;

	@Wire("#txtMzName")
	Textbox txtMzName;

	@Wire("#txtMzNo")
	Textbox txtMzNo;

	@Wire("#txtIdType")
	Textbox txtIdType;

	@Wire("#txtIdNo")
	Textbox txtIdNo;

	@Wire("#txtGrAccNo")
	Textbox txtGrAccNo;

	@Wire("#txtFullNameKH")
	Textbox txtFullNameKH;

	@Wire("#txtDob")
	Datebox txtDob;

	@Wire("#txtIntRate")
	Textbox txtIntRate;

	@Wire("#txtDisb")
	Datebox txtDisb;

	@Wire("#txtMat")
	Datebox txtMat;

	@Wire("#txtLnp")
	Textbox txtLnp;
	
	
	@Wire("#txtRef")
	Textbox txtRef;
	
	Coborrower coborrower;

	int selectedTabIndex;
	
	Privilege privilege;

	public MbrInputVM() {
		
	}

	@Init
	public void init(@ExecutionArgParam("obj") Object obj) {
		if (obj == null) {
			return;
		}
		selected = (GroupMember) obj;

		// maker info shown in status bar
		domain = (Domain) selected;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange({ "selected", "coborrower" })
	public void onNew() {		
		selected = null;
		coborrower = null;
	}

	@Command
	@NotifyChange({ "selected", "domain" })
	public void onSave() {
		try {
			StringBuilder logStr = Common.createLogStringBuilder();

			if (getSelected().getId() == null) {
				selected.setBrCd(UserCredentialManager.getIntance()
						.getLoginUsr().getHomeBranch());
				selected.setCreateBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());				
				logStr.append(" created new member --> ");

			} else {				
				selected.setChangeBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());
				logStr.append(" update member --> ");
			}

			// assign to or remove from mobilizer
			selected.setMobilizer(selectedMobilizer);

			// assign to group or remove from group
			if (selectedGroup != null) {
				selected.setGroup(selectedGroup);
			} else if (selectedGroup == null
					&& txtGrAccNo.getText().trim().equals("")) {
				selected.setGroup(null);
			}

			Ebean.save(selected);

			logStr.append(selected.toString());
			Common.addSessionLogCommit(CMD.MEMBER_INPUT, logStr.toString(),
					new Date());

			domain = (Domain) selected;

			Clients.showNotification("Save successfully", "info", null,
					"middle_center", -1);
		} catch (OptimisticLockException e) {
			logger.error("Error while saving ebean GroupMember", e);
			Clients.showNotification("Save failed!", "error", null,
					"middle_center", -1);
		}
	}

	/**
	 * @return the selected
	 */
	public GroupMember getSelected() {
		if (selected != null)
			return selected;

		selected = new GroupMember();
		selected.setBrCd(UserCredentialManager.getIntance().getLoginUsr()
				.getHomeBranch());

		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	@NotifyChange({ "selected", "coborrower" })
	public void setSelected(GroupMember selected) {
		this.selected = selected;
		coborrower = null;
	}

	/**
	 * @return the lstFetchIDType
	 */
	public ListModelList<CodeItem> getLstFetchIDType() {
		if (lstFetchIDType != null)
			return lstFetchIDType;

		lstFetchIDType = new ListModelList<CodeItem>(
				MfiFacade.getLstFetchIDType());

		return lstFetchIDType;
	}

	/**
	 * @param lstFetchIDType
	 *            the lstFetchIDType to set
	 */
	public void setLstFetchIDType(ListModelList<CodeItem> lstFetchIDType) {
		this.lstFetchIDType = lstFetchIDType;
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
	 * @return the frm
	 */
	public MySimpleForm getFrm() {
		return frm;
	}

	/**
	 * @param frm
	 *            the frm to set
	 */
	public void setFrm(MySimpleForm frm) {
		this.frm = frm;
	}

	@Command
	@NotifyChange({ "selectedCIF", "lstFetchCIF" })
	public void onShowFetchCIF() {
		selectedCIF = null;
		lstFetchCIF = null;
		String homeBranch = UserCredentialManager.getIntance().getLoginUsr().getHomeBranch();
		getParamCIF().setCustNo((txtCIF.getText().isEmpty()? homeBranch : txtCIF.getText()) + "%");
		paramCIF.setAccountNo(homeBranch + "%");
		paramCIF.setFullName("%");
		paramCIF.setAddr2("%");

		Executions.createComponents("/common/listval/account.zul", winMbrInput,
				null);
	}

	@Command
	@NotifyChange({ "selectedMobilizer", "lstFetchMobilizer" })
	public void onShowFetchMobilizer() {
		selectedMobilizer = null;
		lstFetchMobilizer = null;
		getParamMz().setNameEN(txtMzName.getText() + "%");
		Executions.createComponents("/common/listval/mobilizer.zul",
				winMbrInput, null);
	}

	@Command
	@NotifyChange("lstFetchCIF")
	public void onSearchCIF() {
		lstFetchCIF = null;
		System.out.println("searching..");
	}

	@Command
	@NotifyChange({ "lstFetchCIF", "paramCIF" })
	public void onResetSearchCIF() {
		paramCIF = null;
		lstFetchCIF = new ListModelList<CIF>();
		System.out.println("reseting...");
	}

	/**
	 * @return the lstFetchCIF
	 */
	@NotifyChange({ "totalSize", "lstFetchCIF" })
	public ListModelList<CIF> getLstFetchCIF() {
		if (lstFetchCIF == null) {
			lstFetchCIF = new ListModelList<CIF>(
					MfiFacade.getLstFetchCifAcc(getParamCIF()));
		}
		return lstFetchCIF;
	}

	/**
	 * @param lstFetchCIF
	 *            the lstFetchCIF to set
	 */
	public void setLstFetchCIF(ListModelList<CIF> lstFetchCIF) {
		this.lstFetchCIF = lstFetchCIF;
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
		txtCIF.setText(selectedCIF.getCustNo());
		txtFullName.setText(selectedCIF.getFullName());
		txtFullNameKH.setText(selectedCIF.getFullNameKH());

		txtAccountNo.setText(selectedCIF.getAccountNo());
		txtPrdCat.setText(selectedCIF.getPrdCat());
		txtPrdCd.setText(selectedCIF.getPrdCd());
		txtAmtDisb.setText(selectedCIF.getAmountDisbursed().toString());
		txtAmtDisbTxt.setText(translateNr(selectedCIF.getAmountDisbursed()));

		txtDob.setValue(selectedCIF.getDateOfBirth());
		txtDisb.setValue(selectedCIF.getValueDate());
		txtMat.setValue(selectedCIF.getMaturityDate());
		txtIntRate.setText(selectedCIF.getInRate().toString());
		txtLnp.setText(selectedCIF.getLoanPurpose());
		
		txtRef.setText(selectedCIF.getAltAccNo());	
		

		Events.postEvent("onChange", txtCIF, null);
		Events.postEvent("onChange", txtFullName, null);
		Events.postEvent("onChange", txtAccountNo, null);
		Events.postEvent("onChange", txtPrdCat, null);
		Events.postEvent("onChange", txtPrdCd, null);
		Events.postEvent("onChange", txtAmtDisb, null);

		Events.postEvent("onChange", txtFullNameKH, null);
		Events.postEvent("onChange", txtDob, null);
		Events.postEvent("onChange", txtDisb, null);
		Events.postEvent("onChange", txtMat, null);
		Events.postEvent("onChange", txtIntRate, null);
		Events.postEvent("onChange", txtLnp, null);
		
		Events.postEvent("onChange", txtRef, null);		
	}

	/**
	 * @return the paramCIF
	 */
	public CIF getParamCIF() {
		if (paramCIF != null)
			return paramCIF;
		paramCIF = new CIF();
		paramCIF.setBrCd(UserCredentialManager.getIntance().getLoginUsr()
				.getHomeBranch());
		return paramCIF;
	}

	/**
	 * @param paramCIF
	 *            the paramCIF to set
	 */
	public void setParamCIF(CIF paramCIF) {
		this.paramCIF = paramCIF;
	}

	@Command
	@NotifyChange("lstFetchMobilizer")
	public void onSearchMobilizer() {
		lstFetchMobilizer = null;
		System.out.println("searching..");
	}

	@Command
	@NotifyChange({ "lstFetchMobilizer", "paramMz" })
	public void onResetSearchMobilizer() {
		lstFetchMobilizer = new ListModelList<Mobilizer>();
		paramMz = null;
		System.out.println("reseting...");
	}

	/**
	 * @return the lstFetchCIF
	 */
	public ListModelList<Mobilizer> getLstFetchMobilizer() {
		if (lstFetchMobilizer == null) {
			lstFetchMobilizer = new ListModelList<Mobilizer>(Ebean
					.find(Mobilizer.class)
					.select("nameEN, nameKH, idType, idNo")
					.where()
					.eq("brCd",
							UserCredentialManager.getIntance().getLoginUsr()
									.getHomeBranch())
					.ilike("nameEN", paramMz.getNameEN())
					.ilike("nameKH", paramMz.getNameKH())
					.ilike("idNo", paramMz.getIdNo()).findList());
		}
		return lstFetchMobilizer;
	}

	/**
	 * @return the selectedMobilizer
	 */
	public Mobilizer getSelectedMobilizer() {
		return selectedMobilizer;
	}

	/**
	 * @param selectedMobilizer
	 *            the selectedMobilizer to set
	 */
	public void setSelectedMobilizer(Mobilizer selectedMobilizer) {
		this.selectedMobilizer = selectedMobilizer;
		txtMzName.setText(selectedMobilizer.getNameEN());
		txtMzNo.setText(selectedMobilizer.getId().toString());
		txtIdType.setText(selectedMobilizer.getIdTypeDesc());
		txtIdNo.setText(selectedMobilizer.getIdNo());

		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" assigned MOBILIZER [");
		strBuilder.append(selectedMobilizer.toString());
		strBuilder.append("]");
		strBuilder.append(" to MEMBER --> ");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.MEMBER_INPUT, strBuilder.toString(),
				new Date());

		Events.postEvent("onChange", txtMzName, null);
		// Events.postEvent("onChange", txtMzNo, null);
		// Events.postEvent("onChange", txtIdType, null);
		// Events.postEvent("onChange", txtIdNo, null);

	}

	/**
	 * @return the paramMz
	 */
	public Mobilizer getParamMz() {
		if (paramMz != null)
			return paramMz;

		paramMz = new Mobilizer();
		paramMz.setNameEN("%");
		paramMz.setNameKH("%");
		paramMz.setIdNo("%");

		return paramMz;
	}

	/**
	 * @param paramMz
	 *            the paramMz to set
	 */
	public void setParamMz(Mobilizer paramMz) {
		this.paramMz = paramMz;
	}

	/**
	 * @param lstFetchMobilizer
	 *            the lstFetchMobilizer to set
	 */
	public void setLstFetchMobilizer(ListModelList<Mobilizer> lstFetchMobilizer) {
		this.lstFetchMobilizer = lstFetchMobilizer;
	}

	@Command
	@NotifyChange({ "paramGroup", "selectedGroup", "lstFetchGroup" })
	public void onShowFetchGroup() {
		String groupNo = this.txtGrAccNo.getText(); // frm.getField("villageName").toString();
		getParamGroup().setGroupAccNo(groupNo + "%");
		if (!txtPrdCd.getText().isEmpty()) {
			getParamGroup().setGroupType(
					MfiFacade.getGroupTypeByPrdCd(txtPrdCd.getText()));
		}
		selectedGroup = null;
		lstFetchGroup = null;

		Executions.createComponents("/common/listval/group.zul", winMbrInput,
				null);
	}

	/**
	 * @return the lstFetchGroup
	 */
	public ListModelList<Group> getLstFetchGroup() {
		if (lstFetchGroup != null) {
			return lstFetchGroup;
		}

		lstFetchGroup = new ListModelList<Group>(Ebean
				.find(Group.class)
				.select("brCd, groupAccNo, cycleSeq, leaderName")
				.setUseQueryCache(true)
				.where()
				.eq("brCd",
						UserCredentialManager.getIntance().getLoginUsr()
								.getHomeBranch())
				.ilike("groupAccNo", paramGroup.getGroupAccNo())
				.ilike("leaderName", paramGroup.getLeaderName())
				.ilike("type", paramGroup.getGroupType()).findList());

		return lstFetchGroup;
	}

	/**
	 * @param lstFetchGroup
	 *            the lstFetchGroup to set
	 */
	public void setLstFetchGroup(ListModelList<Group> lstFetchGroup) {
		this.lstFetchGroup = lstFetchGroup;
	}

	/**
	 * @return the selectedGroup
	 */
	public Group getSelectedGroup() {
		return selectedGroup;
	}

	/**
	 * @return the paramGroup
	 */
	public Group getParamGroup() {
		if (paramGroup != null)
			return paramGroup;

		paramGroup = new Group();
		paramGroup.setGroupAccNo("%");
		paramGroup.setGroupType("%");
		paramGroup.setLeaderName("%");

		return paramGroup;
	}

	/**
	 * @param paramGroup
	 *            the paramGroup to set
	 */
	public void setParamGroup(Group paramGroup) {
		this.paramGroup = paramGroup;
	}

	/**
	 * @param selectedGroup
	 *            the selectedGroup to set
	 */
	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
		this.txtGrAccNo.setText(selectedGroup.getGroupAccNo());

		Events.postEvent("onChange", txtGrAccNo, null);

		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" assigned GROUP [");
		strBuilder.append(selectedGroup.toString());
		strBuilder.append("]");
		strBuilder.append(" to MEMBER --> ");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.MEMBER_INPUT, strBuilder.toString(),
				new Date());
	}

	@Command
	@NotifyChange("selectedMobilizer")
	public void onClearMz() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" clear/remove MOBILIZER ");
		strBuilder.append(this.txtMzName.getText());
		strBuilder.append(" from MEMBER -->");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.MEMBER_INPUT, strBuilder.toString(),
				new Date());

		this.txtMzName.setText("");
		this.txtMzNo.setText("");
		this.txtIdType.setText("");
		this.txtIdNo.setText("");

		selectedMobilizer = null;

		Events.postEvent("onChange", txtMzName, null);
	}

	@Command
	@NotifyChange("lstFetchGroup")
	public void onSearchGroup() {
		lstFetchGroup = null;
	}

	@Command
	@NotifyChange({ "lstFetchGroup", "paramGroup" })
	public void onResetSearchGroup() {
		paramGroup = null;
		lstFetchGroup = new ListModelList<Group>();
	}

	@Command
	public void onClearGr() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" clear/remove GROUP ");
		strBuilder.append(this.txtGrAccNo.getText());
		strBuilder.append(" from MEMBER -->");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.MEMBER_INPUT, strBuilder.toString(),
				new Date());

		this.txtGrAccNo.setText("");
		Events.postEvent("onChange", txtGrAccNo, null);

	}

	public String translateNr(Integer num) {
		String flexAmountTxt = NumberTranslater.translateToLetter(num
				.toString(),selected.getCurrency());
		if (getSelected().getId() != null) {
			if (!flexAmountTxt.equals(selected.getAmountTxt())) {
				selected.setAmountTxt(flexAmountTxt);
				Ebean.save(selected);
				System.out.println("Saved amount txt in group_member....");
			}
		}

		return flexAmountTxt;
	}

	@Command
	public void onShowRelationType() {

	}

	/**
	 * @return the coborrower
	 */
	public Coborrower getCoborrower() {
		return coborrower;
	}

	/**
	 * @param coborrower
	 *            the coborrower to set
	 */
	public void setCoborrower(Coborrower coborrower) {
		this.coborrower = coborrower;
	}

	/**
	 * @return the lstFetchCoType
	 */
	public ListModelList<String> getLstFetchCoType() {
		if (lstFetchCoType != null)
			return lstFetchCoType;

		lstFetchCoType = new ListModelList<>(MfiFacade.getLstFetchCoType());
		return lstFetchCoType;
	}

	/**
	 * @param lstFetchCoType
	 *            the lstFetchCoType to set
	 */
	public void setLstFetchCoType(ListModelList<String> lstFetchCoType) {
		this.lstFetchCoType = lstFetchCoType;
	}

	/**
	 * @return the selectedTabIndex
	 */
	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	/**
	 * @param selectedTabIndex
	 *            the selectedTabIndex to set
	 */
	@NotifyChange({ "coborrower" })
	public void setSelectedTabIndex(int selectedTabIndex) {		
		this.selectedTabIndex = selectedTabIndex;
		if (selectedTabIndex == 0)
			return;
		if (selected.getId() == null) {			
			coborrower = new Coborrower();
			return;
		}
		
		if(coborrower != null){			
			return;
		}
		
		if (selected.getAccount() == null) {	
			
			coborrower = Ebean.find(Coborrower.class).where().eq("borrowerCIF", selected.getCif()).findUnique();			
			if(coborrower != null)
				return;
			
			coborrower = new Coborrower();			
			coborrower.setBorrowerCIF(selected.getCif());
			return;
		}		
		CltbAccountAppsMaster loan = Ebean.find(CltbAccountAppsMaster.class)
				.select("fieldChar6, fieldChar4, fieldDate1").where()
				.eq("customer_id", selected.getCif()).eq("account_status", "A")
				.in("product_code", "0401","0301", "0201")
				.findUnique();
		if(loan==null){
			return;
		}

		coborrower = new Coborrower();
		coborrower.setRelationType(loan.getFieldChar6());
		coborrower.setNameKH(loan.getFieldChar4());
		coborrower.setDob(loan.getFieldDate1());
		
	}
	
	@Command
	public void onSaveCoborrower(){
		try {
			
			StringBuilder logStr = Common.createLogStringBuilder();

			if (coborrower.getId() == null) {
				coborrower.setBrCd(UserCredentialManager.getIntance()
						.getLoginUsr().getHomeBranch());
				coborrower.setCreateBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());

				logStr.append(" created coborrower info for flood member --> ");

			} else {
				coborrower.setChangeBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());
				logStr.append(" update coborrower info for flood member --> ");
			}
			
			Ebean.save(coborrower);
			
			logStr.append(coborrower.toString());
			Common.addSessionLogCommit(CMD.MEMBER_INPUT, logStr.toString(),
					new Date());
			
			Clients.showNotification("Successfully saved coborrower info", "info", null, "middle_center", -1);
		} catch (OptimisticLockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Error while saving coborrower", e);
			Clients.showNotification("Failed to save coborrower info", "error", null, "middle_center", -1);
		}
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
	
	
}
