package kredit.web.mfi.cb.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.mfi.model.CB;

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
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

public class CbListVM {

	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;

	@Wire("#cbls")
	Window winCB;

	CB selected;
	ListModelList<CB> lstCB;

	String cmdNew = CMD.CB_INPUT;
	String filter = "";
	
	List<CodeItem> lstStatus = new ArrayList<CodeItem>();
	CodeItem status;

	// region paging

	private int pageIndex;
	private PagingList<CB> pagingList;
	private int totalSize;

	// endregion paging

	Privilege privilege;

	@Init
	public void init() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange({"lstCB"})
	public void doSearch() {
		lstCB = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "selected", "lstCB" })
	public void onConfirmDelete() {
		Messagebox.show("Are you sure you want to delete this CB?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDelete();
						}
					}
				});
	}

	@NotifyChange({ "selected", "lstCB" })
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
			lstCB.remove(selected);

			Ebean.delete(CB.class, selected.getId());

			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" deleted CB --> ");
			strBuilder.append(getSelected().toString());
			Common.addSessionLogCommit(CMD.LIST_CB, strBuilder.toString(),
					new Date());

			selected = new CB();

			Clients.showNotification("Delete successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange({ "filter", "lstCB", "selected" })
	public void onClear() {
		filter = "";
		lstCB = null;
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
		return winCB;
	}

	/**
	 * @param winCB
	 *            the winCB to set
	 */
	public void setWinCB(Window winCB) {
		this.winCB = winCB;
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
	 * @return the lstCB
	 */
	@NotifyChange({ "lstCB", "totalSize" })
	public ListModelList<CB> getLstCB() {
		if (lstCB != null) {
			return lstCB;
		}

		String sFilter = "%" + filter + "%";
		pagingList = Ebean
				.find(CB.class)
				.select("brCd, nameEN, nameKH,recordStat, countGroup, villageName, villageID, villageSeq, currentCycleID, lastCycleNo")
				.where()
				.eq("brCd",
						UserCredentialManager.getIntance().getLoginUsr()
								.getHomeBranch())
				.disjunction().ilike("nameEN", sFilter).ilike("nameKH", sFilter)				
				.endJunction()
				.like("nvl(recordStat,'0')", Common.addLikeExpression(status.getCode()))
				.order().desc("id")
				.findPagingList(Integer.parseInt(selectedNumRow.getCode()));
		
		
		/*pagingList = Ebean
				.find(CB.class)
				.select("brCd, nameEN, nameKH, countGroup, villageName, villageID, villageSeq, currentCycleID, lastCycleNo")
				.findPagingList(Integer.parseInt(selectedNumRow.getCode()));*/

		pagingList.getFutureRowCount();

		// get the first page
		Page<CB> page = pagingList.getPage(0);

		// get the beans from the page as a list
		List<CB> lst = page.getList();

		totalSize = page.getTotalRowCount();

		lstCB = new ListModelList<>(lst);

		return lstCB;
	}

	/**
	 * @param lstCB
	 *            the lstCB to set
	 */
	public void setLstCB(ListModelList<CB> lstCB) {
		this.lstCB = lstCB;
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
	public CB getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(CB selected) {
		this.selected = selected;
	}

	@GlobalCommand
	@NotifyChange("lstCB")
	public void notifyListCbChange() {
		lstCB = null;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	@NotifyChange("lstCB")
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		// get the first page
		Page<CB> page = pagingList.getPage(pageIndex);

		// get the beans from the page as a list
		List<CB> lst = page.getList();
		lstCB = new ListModelList<CB>(lst);
	}

	/**
	 * @return the pagingList
	 */
	public PagingList<CB> getPagingList() {
		return pagingList;
	}

	/**
	 * @param pagingList
	 *            the pagingList to set
	 */
	public void setPagingList(PagingList<CB> pagingList) {
		this.pagingList = pagingList;
	}

	/**
	 * @return the totalSize
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize
	 *            the totalSize to set
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
