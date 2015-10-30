/**
 * 
 */
package kredit.web.kbureau.viewmodel;

import java.util.Date;

import kredit.web.core.util.authentication.Priviledge;
import kredit.web.kbureau.model.UserLog;
import kredit.web.kbureau.model.admin.ParamUsrLog;
import kredit.web.kbureau.model.facade.CommonFacade;
import kredit.web.kbureau.model.facade.UserLogFacade;
import kredit.web.kbureau.model.facade.report.CbcReportFacade;
import kredit.web.kbureau.model.report.CodeItem;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

/**
 * @author DELL
 * 
 */
public class UserLogVM {

	boolean visible;
	ListModelList<UserLog> lst;
	UserLog selected;
	UserLogFacade userLogFacade = new UserLogFacade();
	Priviledge priviledge = new Priviledge();

	ParamUsrLog param;

	ListModelList<CodeItem> branches;
	ListModelList<CodeItem> subBranches;
	ListModelList<CodeItem> appTypes;
	ListModelList<CodeItem> usrTypes;
	ListModelList<CodeItem> logTypes;

	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;

	@Init
	public void init(@ExecutionParam("access") Priviledge priviledge) {
		this.priviledge = priviledge;
	}

	@Command
	@NotifyChange({ "selected", "lst", "count" })
	public void doSearch() {
		lst = new ListModelList<UserLog>(
				userLogFacade.getListUserLog(getParam()));
		selected = null;
	}

	@Command
	@NotifyChange({ "visible" })
	public void onViewMessage() {
		visible = true;
	}

	/**
	 * @return the lst
	 */
	public ListModelList<UserLog> getLst() {
		if (lst == null) {
			doSearch();
		}
		return lst;
	}

	/**
	 * @param lst
	 *            the lst to set
	 */
	public void setLst(ListModelList<UserLog> lst) {
		this.lst = lst;
	}

	/**
	 * @return the selected
	 */
	public UserLog getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(UserLog selected) {
		this.selected = selected;
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

	/**
	 * @return the userLogFacade
	 */
	public UserLogFacade getUserLogFacade() {
		return userLogFacade;
	}

	/**
	 * @param userLogFacade
	 *            the userLogFacade to set
	 */
	public void setUserLogFacade(UserLogFacade userLogFacade) {
		this.userLogFacade = userLogFacade;
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

	/**
	 * @return the param
	 */
	public ParamUsrLog getParam() {
		if (param != null)
			return param;
		param = new ParamUsrLog();
		Date date = new Date();
		param.setStartDate(date);
		param.setEndDate(date);
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(ParamUsrLog param) {
		this.param = param;
	}

	/**
	 * @return the appTypes
	 */
	public ListModelList<CodeItem> getAppTypes() {
		if (appTypes == null) {
			appTypes = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "kBureau", "kBureauPlus" };
			String[] code = new String[] { "0", "1", "2" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				appTypes.add(item);
			}
		}
		return appTypes;
	}

	/**
	 * @param appTypes
	 *            the appTypes to set
	 */
	public void setAppTypes(ListModelList<CodeItem> appTypes) {
		this.appTypes = appTypes;
	}

	/**
	 * @return the usrTypes
	 */
	public ListModelList<CodeItem> getUsrTypes() {
		if (usrTypes == null) {
			usrTypes = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "Senior Management",
					"Head Office / IT", "BM / ABM", "SBM / HT", "Anonymous" };
			String[] code = new String[] { "0", "1", "2", "3", "4", "-1" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				usrTypes.add(item);
			}
		}
		return usrTypes;
	}

	/**
	 * @param usrTypes
	 *            the usrTypes to set
	 */
	public void setUsrTypes(ListModelList<CodeItem> usrTypes) {
		this.usrTypes = usrTypes;
	}

	/**
	 * @return the logTypes
	 */
	public ListModelList<CodeItem> getLogTypes() {
		if (logTypes == null) {
			logTypes = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "Login", "Logout",
					"Reset Password", "Enquiry", "Closed", "Add User",
					"Update User", "Delete User" };
			String[] code = new String[] { "0", "1", "2", "3", "4", "5", "6",
					"7", "8" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				logTypes.add(item);
			}
		}
		return logTypes;
	}

	/**
	 * @param logTypes
	 *            the logTypes to set
	 */
	public void setLogTypes(ListModelList<CodeItem> logTypes) {
		this.logTypes = logTypes;
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeStartDate() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeEndDate() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeAppType() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeLogType() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeUsrType() {
		doSearch();
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
		subBranches.addAll(CommonFacade
				.getSuBranches(param.getBranch().getId()));

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

	@Command
	@NotifyChange({ "param", "lst", "selected", "subBranches" })
	public void onClear() {
		param = null;
		subBranches = null;
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

}
