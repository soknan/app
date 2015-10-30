package kredit.web.mfi.mbr.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.mfi.model.GroupMember;

import org.sql2o.Connection;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DefaultCommand;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.SqlUpdate;

public class MbrListVM {

	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;

	@Wire("#mbls")
	Window winMbr;

	GroupMember selected;
	ListModelList<GroupMember> lstMbr;

	String cmdNew = CMD.MEMBER_INPUT;
	String filter = "";
	
	ListModelList<CodeItem> lstStatus;
	CodeItem status;

	//region paging
	
	private int pageIndex;
	private PagingList<GroupMember> pagingList;
	private int totalSize;
	
	//endregion paging
	
	Privilege privilege;
	
	@Init
	public void init() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange("lstMbr")
	public void doSearch() {
		lstMbr = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "selected", "lstMbr" })
	public void onConfirmDelete() {
		Messagebox.show("Are you sure you want to delete this group member?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDelete();
						}
					}
				});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "selected", "lstMbr" })
	public void onConfirmDeleteMbr() {
		Messagebox.show("Are you sure you want to delete all group member that don't have group and status reverse ?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeleteMbr();
						}
					}
				});
	}

	@NotifyChange({ "selected", "lstMbr" })
	public void onDelete() {
		try {
			/*
			 * if (UserCredentialManager.getIntance().getLoginId() == 0 ||
			 * !priviledge.isCanDelete()) { Messagebox
			 * .show("You do not have permission to delete. Please contact your IT."
			 * ); return; } int id = PoliticalPersonFacade.delete(selected,
			 * UserCredentialManager .getIntance().getLogin()); if (id == 0) {
			 * Messagebox.show("Oops! Delete failed. Please contact your IT.");
			 * return; }
			 */
			lstMbr.remove(selected);

			Ebean.delete(GroupMember.class, selected.getId());
			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" deleted GROUP MEMBER --> ");
			strBuilder.append(selected.toString());
			Common.addSessionLogCommit(CMD.LIST_MEMBER, strBuilder.toString(), new Date());
			
			selected = new GroupMember();
			Clients.showNotification("Delete successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@NotifyChange({ "selected", "lstMbr" })
	public void onDeleteMbr() {
		try {			
			String sql = "delete from mfi_group_member gm where gm.id IN(select m.id from mfi_group_member m "
					+ "inner join (select a.ACCOUNT_NUMBER from cltb_account_master a where a.ACCOUNT_STATUS ='V') a "
					+ "on a.ACCOUNT_NUMBER = m.account and m.group_id is null and m.branch_code = '"+UserCredentialManager.getIntance().getLoginUsr().getBrCd()+"')";
			SqlUpdate update2 = Ebean.createSqlUpdate(sql);
			Ebean.execute(update2);
			
			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" deleted GROUP MEMBER --> ");
			strBuilder.append(selected.toString());
			Common.addSessionLogCommit(CMD.LIST_MEMBER, strBuilder.toString(), new Date());
			
			selected = new GroupMember();
			Clients.showNotification("Delete successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange({ "filter", "lstMbr", "selected" })
	public void onClear() {
		filter = "";
		lstMbr = null;
		selected = null;
	}

	@Command
	public void onEdit() {

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

	@DefaultCommand
	public void dummy() {

	}

	/**
	 * @return the winCB
	 */
	public Window getWinCB() {
		return winMbr;
	}

	/**
	 * @param winCB
	 *            the winCB to set
	 */
	public void setWinCB(Window winCB) {
		this.winMbr = winCB;
	}

	/**
	 * @return the cmdNew
	 */
	public String getCmdNew() {
		return cmdNew;
	}

	/**
	 * @param cmdNew
	 *            the cmdNew to set
	 */
	public void setCmdNew(String cmdNew) {
		this.cmdNew = cmdNew;
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

	/**
	 * @return the selected
	 */
	public GroupMember getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(GroupMember selected) {
		this.selected = selected;
	}

	@GlobalCommand
	@NotifyChange("lstMbr")
	public void notifyListChange() {
		lstMbr = null;
	}

	/**
	 * @return the lstMbr
	 */
	
	@NotifyChange({"lstMbr", "totalSize"})
	public ListModelList<GroupMember> getLstMbr() {
		if (lstMbr != null)
			return lstMbr;
		
		System.out.println(status.getCode());
		
		String sFilter = "%" + filter + "%";
		pagingList = Ebean
				.find(GroupMember.class)
				.select("brCd, fullName, cif, account, refAccNo, amountDisbursed, mobilizerName, groupAccNo, villageName,status")
				.where()
				.eq("brCd", UserCredentialManager.getIntance().getLoginUsr().getHomeBranch())
				.disjunction().like("cif", sFilter).like("account", sFilter).like("refAccNo", sFilter)
				.ilike("fullName", sFilter).ilike("mobilizerName", sFilter).ilike("groupAccNo", sFilter).endJunction()	
				.like("status", "%"+status.getCode()+"%")
				.orderBy()
				.desc("id").findPagingList(Integer.parseInt(selectedNumRow.getCode()));
		
		
		pagingList.getFutureRowCount();

		// get the first page
		Page<GroupMember> page = pagingList.getPage(0);

		// get the beans from the page as a list
		List<GroupMember> lst = page.getList();
		
		totalSize = page.getTotalRowCount();

		lstMbr = new ListModelList<>(lst);
		
		return lstMbr;
	}

	/**
	 * @param lstMbr
	 *            the lstMbr to set
	 */
	public void setLstMbr(ListModelList<GroupMember> lstMbr) {
		this.lstMbr = lstMbr;
	}

	@GlobalCommand
	@NotifyChange("lstMbr")
	public void notifyListMbrChange() {
		lstMbr = null;
	}
	
	@Command
	public void onQueryMbr(){
		//todo
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
	@NotifyChange("lstMbr")
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		// get the first page
		Page<GroupMember> page = pagingList.getPage(pageIndex);

		// get the beans from the page as a list
		List<GroupMember> lst = page.getList();
		lstMbr = new ListModelList<GroupMember>(lst);
	}

	/**
	 * @return the pagingList
	 */
	public PagingList<GroupMember> getPagingList() {
		return pagingList;
	}

	/**
	 * @param pagingList the pagingList to set
	 */
	public void setPagingList(PagingList<GroupMember> pagingList) {
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
	
	public ListModelList<CodeItem> getLstStatus() {		
		if (lstStatus != null)
			return lstStatus;
		lstStatus = new ListModelList<CodeItem>();
		String[] code = new String[] { "%","A","L","V"};
		String[] desc = new String[] { "All","Active","Liquid","Reversed"};
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			lstStatus.add(item);
		}
		return lstStatus;
	}

	public void setLstStatus(ListModelList<CodeItem> lstStatus) {
		this.lstStatus = lstStatus;
	}

	public CodeItem getStatus() {
		if (status != null) {
			return status;
		}
		status = new CodeItem();
		status.setCode("%");
		status.setDescription("All");
		return status;
	}

	public void setStatus(CodeItem status) {
		this.status = status;
	}
}
