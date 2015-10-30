package kredit.web.util.checker.vm;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.facade.SecurityFacade;
import kredit.web.util.checker.model.Checker;
import kredit.web.util.checker.model.ParamChecker;
import kredit.web.util.checker.model.facade.CheckerFacade;
import kredit.web.writeOff.model.facade.WriteOffFacade;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Tabbox;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

public class CheckerVM {

	@Wire("#divResult")
	Div divResult;

	@Wire("#checkerContainer")
	Div checkerContainer;

	@Wire("#tab")
	Tabbox tab;

	String checkerStatus;

	ListModelList<CodeItem> filterBranches;
	ListModelList<CodeItem> filterSubBranches;
	ListModelList<Checker> lstChecker;
	List<Checker> ls;
	Set<Checker> selectAvails;
	ParamChecker param = new ParamChecker();

	boolean visibleBr;
	boolean visibleSbr;

	private int selectedTab;
	private static final int TAB_CHECK_LIST = 0;
	private static final int TAB_RESULT = 1;
	String msg;
	String filter = "";

	@Wire("#bd")
	Bandbox bd;

	public ListModelList<CodeItem> getFilterBranches() {
		if (filterBranches == null) {
			filterBranches = new ListModelList<CodeItem>(
					WriteOffFacade.getBranchesListWithAll());
		}
		return filterBranches;
	}

	public void setFilterBranches(ListModelList<CodeItem> filterBranches) {
		this.filterBranches = filterBranches;
	}

	public ListModelList<CodeItem> getFilterSubBranches() {
		if (filterSubBranches == null) {
			filterSubBranches = new ListModelList<CodeItem>(
					CheckerFacade.getSubBranchesList(param.getBranch().getId()));
		}
		return filterSubBranches;
	}

	public void setFilterSubBranches(ListModelList<CodeItem> filterSubBranches) {
		this.filterSubBranches = filterSubBranches;
	}

	public ParamChecker getParam() {
		return param;
	}

	public void setParam(ParamChecker param) {
		this.param = param;
	}

	public boolean isVisibleBr() {
		return visibleBr;
	}

	public boolean isVisibleSbr() {
		return visibleSbr;
	}

	// region Branch & SubBranch

	@Command
	@NotifyChange({ "filterSubBranches" })
	public void onSelectBranch() {
		filterSubBranches.clear();
		filterSubBranches.addAll(CheckerFacade.getSubBranchesList(param
				.getBranch().getId()));
		param.getSbrList().clear();
	}

	@Command
	@NotifyChange({ "param" })
	public void onSelectSubBranch() {
		/*
		 * bd.setValue(param.getSubBranch().getDescription()); bd.close();
		 */

		param.getBranch().setId(param.getSubBranch().getSuperId());
		param.getBranch().setCode(param.getSubBranch().getSuperCode());
		param.getBranch().setDescription(
				param.getSubBranch().getSuperDescription());
	}

	// endregion Branch & SubBranch

	public CheckerVM() {
		Map<String, String> rolesMap = SecurityFacade
				.getRoleCode(UserCredentialManager.getIntance().getLoginUsr()
						.getId());

		CodeItem branch = SecurityFacade.getBranch(UserCredentialManager
				.getIntance().getLoginUsr().getBrCd());

		if (rolesMap.containsValue("adm") || rolesMap.containsValue("hq")) {
			visibleBr = true;
			visibleSbr = true;
			return;
		}

		if (rolesMap.containsValue("bm")) {
			visibleBr = false;
			visibleSbr = true;

			param.setBranch(branch);

			return;
		}

		if (rolesMap.containsValue("sbm")) {
			visibleBr = false;
			visibleSbr = false;

			param.getSbrList().add(branch);

			return;
		}

		param.getSbrList().add(branch);
		visibleBr = false;
		visibleSbr = false;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

	}

	@Command
	@NotifyChange({ "param", "msg" })
	public void onClear() {
		msg = "";
		param = new ParamChecker();
		filterSubBranches.clear();
		filterSubBranches.addAll(CheckerFacade.getSubBranchesList(param
				.getBranch().getId()));
		Clients.evalJavaScript("$('#divAjaxResult').html(''); $('#spanStatus').html('');");
	}

	@Command
	@NotifyChange({ "selectedTab", "msg", "selectAvails" })
	public void onCheckJS() {

		if (selectAvails == null || selectAvails.size() == 0) {
			ls = null;
			filter = "";
			selectAvails = new HashSet<>(getLs());
		}

		tab.setSelectedIndex(TAB_RESULT);

		// if(selectAvails.size() == 0){
		// Clients.showNotification("Please check one or more rules in the check list",
		// "warning", null, "after_pointer", -1);
		// return;
		// }

		setSelectedTab(TAB_RESULT);
		setMsg("  Checker has run "
				+ (selectAvails == null ? 0 : selectAvails.size()) + " of "
				+ lstChecker.size() + " rule(s) in the check list");

		doChecker();
	}

	public void doChecker() {
		String lstCheckerJson = CheckerFacade.getCheckerListJson(selectAvails);
		StringBuilder jsFunc = new StringBuilder();

		jsFunc.append("onChecker(");

		jsFunc.append("'").append(getBranchParameter()).append("','")
				.append(lstCheckerJson).append("');");

		System.out.println(jsFunc.toString());
		Clients.evalJavaScript(jsFunc.toString());
	}

	/**
	 * @return the checkerStatus
	 */
	public String getCheckerStatus() {
		return checkerStatus;
	}

	/**
	 * @param checkerStatus
	 *            the checkerStatus to set
	 */
	public void setCheckerStatus(String checkerStatus) {
		this.checkerStatus = checkerStatus;
	}

	// endregion ZK

	public String getBranchParameter() {
		String result = "";
		if (param.getBranch().getCode().trim().equals("")
				&& (param.getSbrList().size() == 0 || param.getSbrList().size() == filterSubBranches
						.size()))
			return "All";

		if (param.getSbrList().size() == 0) {
			param.getSbrList().addAll(filterSubBranches);
		}

		// result = param.getSubBranch().getCode();
		result = getStringFromList(param.getSbrList());

		return result;
	}

	public String getStringFromList(Set<CodeItem> lst) {
		String result = "";

		if (lst.size() > 0) {
			for (CodeItem c : lst) {
				result += c.getCode() + ",";
			}

			if (result.length() > 0
					&& result.charAt(result.length() - 1) == ',') {
				result = result.substring(0, result.length() - 1);
			}
		}

		return result;
	}

	/**
	 * @return the lstChecker
	 */
	public ListModelList<Checker> getLstChecker() {
		if (lstChecker == null) {
			lstChecker = new ListModelList<>(getLs());
		}
		return lstChecker;
	}

	public List<Checker> getLs() {
		if (ls != null) {
			return ls;
		}
		Query<Checker> query = Ebean.find(Checker.class);
		query.select("title, statusMsg, validationMsg");
		query.where().eq("active", "Y").ilike("title", "%" + filter + "%");
		if (!visibleBr) {
			query.where().eq("admin", "N");
		}
		query.order().asc("seq");

		ls = query.findList();

		return ls;
	}

	/**
	 * @param lstChecker
	 *            the lstChecker to set
	 */
	public void setLstChecker(ListModelList<Checker> lstChecker) {
		this.lstChecker = lstChecker;
	}

	/**
	 * @return the selectAvails
	 */
	public Set<Checker> getSelectAvails() {
		return selectAvails;
	}

	/**
	 * @param selectAvails
	 *            the selectAvails to set
	 */
	public void setSelectAvails(Set<Checker> selectAvails) {
		this.selectAvails = selectAvails;
	}

	/**
	 * @return the selectedTab
	 */
	public int getSelectedTab() {
		return selectedTab;
	}

	/**
	 * @param selectedTab
	 *            the selectedTab to set
	 */
	public void setSelectedTab(int selectedTab) {
		this.selectedTab = selectedTab;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Command
	@NotifyChange("selectedTab")
	public void onGoToCheckList() {
		selectedTab = TAB_CHECK_LIST;
	}

	@Command
	public String showSelectedSubs(Set<CodeItem> subLst) {
		StringBuilder sb = new StringBuilder();
		for (CodeItem i : subLst) {
			sb.append(i.getDescription()).append(", ");
		}
		return sb.toString();
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Command
	@NotifyChange({ "ls", "lstChecker" })
	public void doSearch() {
		ls = null;
		lstChecker.clear();
		lstChecker.addAll(getLs());
	}
}
