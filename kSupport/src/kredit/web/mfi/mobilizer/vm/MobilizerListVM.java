package kredit.web.mfi.mobilizer.vm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.mfi.model.Mobilizer;

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

public class MobilizerListVM {

	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;

	@Wire("#mzls")
	Window winMobilzer;

	Mobilizer selected;
	ListModelList<Mobilizer> lstMobilzer;

	String cmdNew = CMD.MOBILIZER_INPUT;
	String filter = "";
	
	List<CodeItem> lstStatus = new ArrayList<CodeItem>();
	CodeItem status;


	Privilege privilege;

	@Init
	public void init() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange("lstMobilzer")
	public void doSearch() {
		lstMobilzer = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "selected", "lstMobilzer" })
	public void onConfirmDelete() {
		Messagebox.show("Are you sure you want to delete this mobilzer?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDelete();
						}
					}
				});
	}

	@NotifyChange({ "selected", "lstMobilzer" })
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
			lstMobilzer.remove(selected);

			Ebean.delete(Mobilizer.class, selected.getId());

			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" deleted MOBILIZER --> ");
			strBuilder.append(selected.toString());
			Common.addSessionLogCommit(CMD.LIST_MOBILZER,
					strBuilder.toString(), new Date());

			selected = new Mobilizer();

			Clients.showNotification("Delete successfully!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange({ "filter", "lstMobilzer", "selected" })
	public void onClear() {
		filter = "";
		lstMobilzer = null;
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
		return winMobilzer;
	}

	/**
	 * @param winCB
	 *            the winCB to set
	 */
	public void setWinCB(Window winCB) {
		this.winMobilzer = winCB;
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
	public Mobilizer getSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Mobilizer selected) {
		this.selected = selected;
	}

	@GlobalCommand
	@NotifyChange("lstMobilzer")
	public void notifyListChange() {
		lstMobilzer = null;
	}

	/**
	 * @return the lstMobilzer
	 */
	public ListModelList<Mobilizer> getLstMobilzer() {
		if (lstMobilzer != null)
			return lstMobilzer;
		
		String sFilter = "%" + filter + "%";
		
		lstMobilzer = new ListModelList<Mobilizer>(Ebean
				.find(Mobilizer.class)
				.select("brCd, nameEN, nameKH, sex, idType, idNo,recordStat")
				.where()
				.eq("brCd",
						UserCredentialManager.getIntance().getLoginUsr()
								.getHomeBranch()).disjunction()
				.ilike("nameEN", sFilter).ilike("nameKH", sFilter)
				.ilike("idNo", sFilter).ilike("cif", sFilter).endJunction()
				.like("nvl(recordStat,'0')", Common.addLikeExpression(status.getCode()))
				.orderBy().desc("id").findList());

		return lstMobilzer;
	}

	/**
	 * @param lstMobilzer
	 *            the lstMobilzer to set
	 */
	public void setLstMobilzer(ListModelList<Mobilizer> lstMobilzer) {
		this.lstMobilzer = lstMobilzer;
	}

	@GlobalCommand
	@NotifyChange("lstMobilzer")
	public void notifyListMzChange() {
		lstMobilzer = null;
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
