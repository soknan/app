/**
 * 
 */
package kredit.web.kbureau.viewmodel.admin;

import kredit.web.core.util.authentication.Priviledge;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.kbureau.model.admin.CbcAuthen;
import kredit.web.kbureau.model.admin.ParamCbcAuthen;
import kredit.web.kbureau.model.facade.CommonFacade;
import kredit.web.kbureau.model.facade.admin.CbcAuthenFacade;
import kredit.web.kbureau.model.report.CodeItem;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

/**
 * @author vathenan
 * 
 */
public class CbcAuthenVM {

	ListModelList<CbcAuthen> lst;
	CbcAuthen selected;
	ParamCbcAuthen param = new ParamCbcAuthen();
	boolean visible;
	String winTitle;
	boolean canSave;
	boolean canDelete;
	String msgDelete;
	String resultMsg;
	String resultMsgClass;
	String resultIcon;
	boolean isEdit;

	ListModelList<CodeItem> branches;
	ListModelList<CodeItem> subBranches;
	ListModelList<CodeItem> flags;
	
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;


	Priviledge priviledge = new Priviledge();

	static Logger log = Logger.getLogger(CbcAuthenVM.class.getName());

	@Init
	public void init(@ExecutionParam("access") Priviledge priviledge) {
		this.priviledge = priviledge;
	}

	/**
	 * @return the branches
	 */
	public ListModelList<CodeItem> getBranches() {
		if (branches == null) {
			branches = new ListModelList<CodeItem>(CommonFacade.getBranches());
		}
		return branches;
	}

	/**
	 * @param branches
	 *            the branches to set
	 */
	public void setBranches(ListModelList<CodeItem> branches) {
		this.branches = branches;
	}

	/**
	 * @return the subBranches
	 */
	public ListModelList<CodeItem> getSubBranches() {
		if (subBranches == null) {
			subBranches = new ListModelList<CodeItem>(
					CommonFacade.getSuBranches(param.getBranch().getId()));
		}
		return subBranches;
	}

	/**
	 * @param subBranches
	 *            the subBranches to set
	 */
	public void setSubBranches(ListModelList<CodeItem> subBranches) {
		this.subBranches = subBranches;
	}

	@Command
	@NotifyChange({ "selected", "lst" })
	public void doSearch() {
		lst = new ListModelList<CbcAuthen>(
				CbcAuthenFacade.getCbcAuthenList(param));
		selected = null;
	}

	/**
	 * @return the lst
	 */
	public ListModelList<CbcAuthen> getLst() {
		if (lst == null) {
			lst = new ListModelList<CbcAuthen>(
					CbcAuthenFacade.getCbcAuthenList(param));
		}
		return lst;
	}

	/**
	 * @param lst
	 *            the lst to set
	 */
	public void setLst(ListModelList<CbcAuthen> lst) {
		this.lst = lst;
	}

	/**
	 * @return the selected
	 */
	public CbcAuthen getSelected() {
		if(selected == null) selected = new CbcAuthen();
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(CbcAuthen selected) {
		this.selected = selected;
	}

	/**
	 * @return the param
	 */
	public ParamCbcAuthen getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(ParamCbcAuthen param) {
		this.param = param;
	}

	@Command
	@NotifyChange({ "selected", "winTitle", "visible", "resultMsg",
			"canDelete", "canSave" })
	public void onNew() {
		selected = new CbcAuthen();
		winTitle = "CBC Authentication - New";
		visible = true;
		resultMsg = null;
		canDelete = false;
		canSave = false;
		isEdit = false;
	}

	@Command
	@NotifyChange({ "selected", "winTitle", "visible", "resultMsg",
			"canDelete", "canSave" })
	public void onEdit() {
		winTitle = "CBC Authentication - Edit";
		visible = true;
		resultMsg = null;
		canDelete = true;
		canSave = false;
		isEdit = true;
	}

	private void flagResultMessage(String msg, boolean flagSuccess) {
		resultMsg = msg;
		canSave = false;
		if (flagSuccess) {
			resultMsgClass = "k-resultEnc-msg";
			resultIcon = "/img/app/ok16.png";
		} else {
			resultMsgClass = "k-error";
			resultIcon = "/img/app/error16.png";
		}

	}

	@Command
	@NotifyChange({ "lst", "resultMsg", "resultMsgClass", "canSave",
			"resultIcon", "winTitle", "selected" })
	public void onSave() {
		try {
			int myId = UserCredentialManager.getIntance().getLoginId();
			if (myId == 0) {
				flagResultMessage("You do not have permission to save user.",
						false);
				return;
			}

			boolean newUser = selected.getId() == 0;
			int userId = CbcAuthenFacade.saveCbcAuthen(myId, selected);
			if (userId > 0) {
				if (newUser) {
					selected.setId(userId);
					winTitle = "kBureau User - Edit";
					isEdit = true;
				}
				flagResultMessage("Save successfully.", true);
			} else {
				flagResultMessage("Save failed.", false);
			}

			// set to null in order to get new updated list with getter
			lst = null;

			canSave = false;
			canDelete = true;

		} catch (Exception e) {
			log.error("Error while editing kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "msgDelete" })
	public void onConfirmDelete() {
		try {
			this.msgDelete = "Are you sure you want to delete user "
					+ selected.getUsername() + "?";
		} catch (Exception e) {
			log.error("Error while deleting kBureau user.", e);
		}
	}
	
	@Command
	@NotifyChange({ "msgDelete", "lst", "selected", "visible" })
	public void onDelete() {
		try {
			int myId = UserCredentialManager.getIntance().getLoginId();
			if (myId == 0) {
				flagResultMessage("You do not have permission to save user.",
						false);
				return;
			}
			msgDelete = null;
			CbcAuthenFacade.deleteCbcAuthen(myId, selected);
			lst.remove(selected);
			selected = null;
			visible = false;
		} catch (Exception e) {
			log.error("Error while deleting kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "visible" })
	public void onCancelEdit() {
		visible = false;
	}

	@Command
	@NotifyChange({ "msgDelete" })
	public void onCancelDelete() {
		msgDelete = null;
	}

	@Command
	@NotifyChange({ "param", "lst", "selected", "subBranches" })
	public void onClear() {
		param = new ParamCbcAuthen();
		selected = null;
		subBranches = null;
		doSearch();
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the winTitle
	 */
	public String getWinTitle() {
		return winTitle;
	}

	/**
	 * @param winTitle
	 *            the winTitle to set
	 */
	public void setWinTitle(String winTitle) {
		this.winTitle = winTitle;
	}

	/**
	 * @return the canSave
	 */
	public boolean isCanSave() {
		return canSave;
	}

	/**
	 * @param canSave
	 *            the canSave to set
	 */
	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	/**
	 * @return the canDelete
	 */
	public boolean isCanDelete() {
		return canDelete;
	}

	/**
	 * @param canDelete
	 *            the canDelete to set
	 */
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	/**
	 * @return the msgDelete
	 */
	public String getMsgDelete() {
		return msgDelete;
	}

	/**
	 * @param msgDelete
	 *            the msgDelete to set
	 */
	public void setMsgDelete(String msgDelete) {
		this.msgDelete = msgDelete;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	/**
	 * @return the resultMsgClass
	 */
	public String getResultMsgClass() {
		return resultMsgClass;
	}

	/**
	 * @param resultMsgClass
	 *            the resultMsgClass to set
	 */
	public void setResultMsgClass(String resultMsgClass) {
		this.resultMsgClass = resultMsgClass;
	}

	/**
	 * @return the resultIcon
	 */
	public String getResultIcon() {
		return resultIcon;
	}

	/**
	 * @param resultIcon
	 *            the resultIcon to set
	 */
	public void setResultIcon(String resultIcon) {
		this.resultIcon = resultIcon;
	}

	/**
	 * @return the isEdit
	 */
	public boolean isEdit() {
		return isEdit;
	}

	/**
	 * @param isEdit
	 *            the isEdit to set
	 */
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	/**
	 * @return the priviledge
	 */
	public Priviledge getPriviledge() {
		return priviledge;
	}

	/**
	 * @param priviledge
	 *            the priviledge to set
	 */
	public void setPriviledge(Priviledge priviledge) {
		this.priviledge = priviledge;
	}

	@Command
	@NotifyChange({ "param", "subBranches", "lst", "selected" })
	public void onChangeBranch() {
		CodeItem item = new CodeItem();
		item.setCode("");
		item.setDescription("All");
		param.setSubBranch(item);
		onDropSubBranch();
		doSearch();
	}

	public void onDropSubBranch() {
		subBranches.clear();
		subBranches.addAll(CommonFacade.getSuBranches(param.getBranch()
				.getId()));
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeSubBranch() {
		CodeItem item = new CodeItem();
		item.setId(param.getSubBranch().getSuperId());
		item.setCode(param.getSubBranch().getSuperCode());
		item.setDescription(param.getSubBranch().getSuperDescription());
		param.setBranch(item);
		doSearch();
	}
	
	/**
	 * @return the rows
	 */
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
		if (selectedNumRow != null)
			return selectedNumRow;
		selectedNumRow = new CodeItem();
		selectedNumRow.setCode("10");
		selectedNumRow.setDescription("10");
		return selectedNumRow;
	}

	/**
	 * @param selectedNumRow
	 *            the selectedNumRow to set
	 */
	public void setSelectedNumRow(CodeItem selectedNumRow) {
		this.selectedNumRow = selectedNumRow;
	}

	@Command
	@NotifyChange("selectedNumRow")
	public void onChangeRowPerPage() {
		doSearch();
	}

	/**
	 * @return the flags
	 */
	public ListModelList<CodeItem> getFlags() {
		
		if (flags != null)
			return flags;
		flags = new ListModelList<CodeItem>();
		String[] desc = new String[] { "All", "Expired", "Nearly Expired", "Normal"};
		String[] code = new String[] { "", "r", "y", "b"}; // red, yellow, blue
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			flags.add(item);
		}
		
		return flags;
	}

	/**
	 * @param flags the flags to set
	 */
	public void setFlags(ListModelList<CodeItem> flags) {
		this.flags = flags;
	}
	
	@Command
	@NotifyChange({"param", "lst", "selected"})
	public void onChangeFlag(){
		doSearch();
	}
}
