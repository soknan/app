package kredit.web.mfi.group.vm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import kredit.flexcube.model.Village;
import kredit.web.core.model.Domain;
import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.NumberTranslater;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.zk.MySimpleForm;
import kredit.web.mfi.model.CbCycle;
import kredit.web.mfi.model.Group;
import kredit.web.mfi.model.GroupMember;
import kredit.web.mfi.model.Leader;
import kredit.web.mfi.model.facade.MfiFacade;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.zkoss.bind.BindUtils;
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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.TxRunnable;

public class GroupInputVM11 {

	static Logger logger = Logger.getLogger(GroupInputVM.class);

	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;

	@Wire("#grip")
	Window winGroupInput;

	Textbox txtNumLetter;

	// lookup list value

	ListModelList<Village> lstFetchVillage = new ListModelList<Village>();
	Village selectedVillage;
	Village paramVillage = new Village();

	ListModelList<Group> lstFetchGroup = new ListModelList<Group>();
	Group selectedGroup;
	Group paramGroup;

	ListModelList<GroupMember> lstFetchCIF;
	GroupMember selectedCIF;
	// CIF paramCIF = new CIF();

	ListModelList<CbCycle> lstFetchCbCycle = new ListModelList<CbCycle>();
	CbCycle selectedCbCycle;
	String cbFilter = "%";

	Leader currentGroupLeader;

	// get reference when user clicks on search CIF
	Textbox txtVillage;
	Textbox txtVillageID;

	Textbox txtLeaderCIF;
	Textbox txtLeaderName;
	Textbox txtLeaderNameKH;
	Datebox dtStart;
	Datebox dtEnd;

	Textbox txtGrAccNo;
	Radio rdSG;
	Radio rdCB;
	Radio rdTB;

	Textbox txtCB;
	Textbox txtCbCycleNo;

	Group selected;
	private MySimpleForm frm = new MySimpleForm();
	private MySimpleForm frmLeader = new MySimpleForm();

	ListModelList<GroupMember> availMbr = new ListModelList<GroupMember>();
	Set<GroupMember> selectAvails;
	ListModelList<GroupMember> chosenMbr;
	Set<GroupMember> selectChosens;
	String filter = "%";

	Domain domain = new Domain();

	private boolean notifyChange;

	private Privilege privilege;
	
	boolean bStatus=false;
	
	String lStatus = "Close";
	
	// region paging

	private int pageIndex;
	private PagingList<Village> pagingList;
	private int totalSize;

	// endregion paging

	@Init
	public void init(@ExecutionArgParam("obj") Object obj) {
		if (obj == null) {
			return;
		}
		selected = (Group) obj;
		currentGroupLeader = selected.getCurrentLeader();

		// maker info shown in status bar
		domain = (Domain) selected;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
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
	@NotifyChange({ "selectedVillage", "lstFetchVillage" })
	public void onShowFetchVillage(
			@BindingParam("txtVillage") Textbox txtVillage,
			@BindingParam("txtVillageID") Textbox txtVillageID) {
		if (this.txtVillage == null)
			this.txtVillage = txtVillage;

		if (this.txtVillageID == null)
			this.txtVillageID = txtVillageID;

		String villageName = this.txtVillage.getText(); // frm.getField("villageName").toString();
		paramVillage.setVillage(villageName + "%");

		selectedVillage = null;
		lstFetchVillage = null;

		Window comp = (Window) Executions.createComponents(
				"/common/listval/village.zul", winGroupInput, null);
		winGroupInput.appendChild(comp);
	}

	@Command
	@NotifyChange({ "paramGroup", "selectedGroup", "lstFetchGroup" })
	public void onShowFetchGroup(
			@BindingParam("txtGrAccNo") Textbox txtGrAccNo,
			@BindingParam("txtVillage") Textbox txtVillage,
			@BindingParam("txtVillageID") Textbox txtVillageID,
			@BindingParam("rdSG") Radio rdSG, @BindingParam("rdCB") Radio rdCB,
			@BindingParam("rdTB") Radio rdTB) {
		if (this.txtGrAccNo == null)
			this.txtGrAccNo = txtGrAccNo;

		if (this.txtVillage == null)
			this.txtVillage = txtVillage;

		if (this.txtVillageID == null)
			this.txtVillageID = txtVillageID;

		if (this.rdSG == null) {
			this.rdSG = rdSG;
		}

		if (this.rdCB == null) {
			this.rdCB = rdCB;
		}

		if (this.rdTB == null) {
			this.rdTB = rdTB;
		}

		String groupNo = this.txtGrAccNo.getText(); // frm.getField("villageName").toString();
		getParamGroup().setGroupAccNo(groupNo + "%");

		selectedGroup = null;
		lstFetchGroup = null;

		Executions.createComponents("/common/listval/group.zul", winGroupInput,
				null);
	}

	@Command
	@NotifyChange({ "lstFetchVillage", "pageIndex", "totalSize" })
	public void onSearchVillage() {
		pageIndex = 0;
		lstFetchVillage = null;
		System.out.println("searching..");
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
	@NotifyChange({ "lstFetchVillage", "totalSize" })
	public void onSearchVillageByID() {
		String strId = paramVillage.getStrID();
		if (strId.length() > 0) {
			if (strId.charAt(0) == '0') {
				strId = strId.substring(1);
			}
		}

		pagingList = Ebean.find(Village.class).setUseQueryCache(true).where()
				.ilike("id", strId).findPagingList(10);

		pagingList.getFutureRowCount();

		// get the first page
		Page<Village> page = pagingList.getPage(0);

		// get the beans from the page as a list
		List<Village> lst = page.getList();

		totalSize = page.getTotalRowCount();

		lstFetchVillage = new ListModelList<Village>(lst);
	}

	@Command
	@NotifyChange({ "lstFetchVillage", "paramVillage", "totalSize", "pageIndex" })
	public void onResetSearchVillage() {
		pageIndex = 0;
		lstFetchVillage = null;
		paramVillage = new Village();
	}

	@NotifyChange({ "totalSize", "lstFetchVillage" })
	public ListModelList<Village> getLstFetchVillage() {
		if (lstFetchVillage == null) {

			pagingList = Ebean.find(Village.class).setUseQueryCache(true)
					.where().ilike("village", paramVillage.getVillage())
					.ilike("commune", paramVillage.getCommune())
					.ilike("district", paramVillage.getDistrict())
					.ilike("province", paramVillage.getProvince())
					.findPagingList(10);

			pagingList.getFutureRowCount();

			totalSize = pagingList.getTotalRowCount();

			// get the first page
			Page<Village> page = pagingList.getPage(0);

			// get the beans from the page as a list
			List<Village> lst = page.getList();

			lstFetchVillage = new ListModelList<Village>(lst);
		}

		return lstFetchVillage;
	}

	/**
	 * @param lstVillage
	 *            the lstVillage to set
	 */
	public void setLstFetchVillage(ListModelList<Village> lstVillage) {
		this.lstFetchVillage = lstVillage;
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
		txtVillage.setText(selectedVillage.getVillage());
		txtVillageID.setText(villageID);

		// to notify textbox about its value change so that the form binding
		// will be notified its status too
		Events.postEvent("onChange", txtVillage, null);
		Events.postEvent("onChange", txtVillageID, null);

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
	 * @return the dtStart
	 */
	public Datebox getDtStart() {
		return dtStart;
	}

	/**
	 * @param dtStart
	 *            the dtStart to set
	 */
	public void setDtStart(Datebox dtStart) {
		this.dtStart = dtStart;
	}

	/**
	 * @return the dtEnd
	 */
	public Datebox getDtEnd() {
		return dtEnd;
	}

	/**
	 * @param dtEnd
	 *            the dtEnd to set
	 */
	public void setDtEnd(Datebox dtEnd) {
		this.dtEnd = dtEnd;
	}

	/**
	 * @return the selected
	 */
	public Group getSelected() {
		if (selected != null)
			return selected;
		selected = new Group();
		selected.setBrCd(UserCredentialManager.getIntance().getLoginUsr()
				.getHomeBranch());

		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Group selected) {
		this.selected = selected;
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

	@Command
	@NotifyChange({ "selected", "chosenMbr", "currentGroupLeader", "availMbr" })
	public void onNew2() {
		selected = null;
		currentGroupLeader = null;
		chosenMbr.clearSelection();
		chosenMbr.clear();

		availMbr.clearSelection();
		availMbr.clear();
	}

	public List<GroupMember> validateMbrType(String groupType) {

		String prdCode = MfiFacade.getProductCode(groupType);

		List<GroupMember> invalidMbr = new ArrayList<GroupMember>();

		for (GroupMember m : chosenMbr) {
			if (m.getAccount() == null) {
				continue;
			}

			if (!m.getAccount().substring(3, 7).equals(prdCode)) {
				invalidMbr.add(m);
			}
		}

		return invalidMbr;
	}

	// Allow to add existing mbr that already paid off(outstanding =0), not more
	// than 2 times in one group cycle
	public List<String> validateAddMbrPayOff(
			HashMap<String, List<GroupMember>> map) {
		List<String> invalidMbr = new ArrayList<String>();
		
		if(map == null || map.size() == 0){
			return invalidMbr;
		}
		
		String prevCIF = "";
		for (GroupMember m : chosenMbr) {

			if (m.getCif().equals(prevCIF)) {
				continue;
			}

			if (m.getAccount() == null) {
				continue;
			}

			List<GroupMember> lstMbr = map.get(m.getCif());
			if(lstMbr == null || lstMbr.size() == 0){
				continue;
			}
			int count = 0;
			for (GroupMember mbr : lstMbr) {
				//System.out.println(mbr.getOutstanding());
				if (mbr.getOutstanding() > 0) {
					count++;
				}
			}

			if (count == 1) {
				continue;
			}

			invalidMbr.add(m.getCif());

			prevCIF = m.getCif();
		}
		return invalidMbr;
	}

	// Validate client paid off then take new loan ==> allow 1 client only two
	// times per loan cycle --> Max(CIF) = 2
	public List<String> validateDuplicate3CIF(
			HashMap<String, List<GroupMember>> map) {
		
		List<String> invalidMbrs = new ArrayList<>();
		
		if (map == null || map.size() == 0) {
			return invalidMbrs;
		}
		
		String prevCIF = "";
		for (GroupMember m : chosenMbr) {

			if (m.getCif().equals(prevCIF)) {
				continue;
			}
			List<GroupMember> lstMbr = map.get(m.getCif());

			if (lstMbr == null || lstMbr.size() < 3) {
				continue;
			}

			invalidMbrs.add(m.getCif());

			prevCIF = m.getCif();
		}

		return invalidMbrs;
	}

	public HashMap<String, List<GroupMember>> countDuplicateCIF() {
		HashMap<String, List<GroupMember>> map = new HashMap<>();
		List<String> lstCIF = new ArrayList<String>();

		for (GroupMember m : chosenMbr) {
			lstCIF.add(m.getCif());
		}

		for (GroupMember m : chosenMbr) {
			int count = Collections.frequency(lstCIF, m.getCif());
			if (count == 1) {
				continue;
			}
			if (!map.containsKey(m.getCif())) {
				List<GroupMember> lg = new ArrayList<>();
				map.put(m.getCif(), lg);
			}
			map.get(m.getCif()).add(m);
		}

		return map;
	}

	private boolean isValidated() {

		// 1. Group Account Number is empty
		if (selected.getGroupAccNo().replace("%", "").trim().isEmpty()) {
			Clients.showNotification(
					"Invalid Group Account No! Please populate it before save.",
					"warning", null, "middle_center", -1, true);
			return false;
		}
		// 2. Group exists
		if (MfiFacade.isGroupAccNrExist(selected.getId(),
				selected.getGroupAccNo())) {
			Clients.showNotification(
					"Group Account No. already exists! Please populate it again to get a new one.",
					"warning", null, "middle_center", -1, true);
			return false;
		}

		if (notifyChange) {

			// 3. A group must have at least 3 members
			if (chosenMbr.getSize() < 3) {
				Clients.showNotification(
						"A group must have at least 3 members", "warning",
						null, "middle_center", -1, true);
				
				return false;
			}

			// 4. Unmatched account for group type
			List<GroupMember> invalidMbrs = validateMbrType(selected
					.getGroupType());
			if (invalidMbrs.size() > 0) {
				StringBuilder warningMsg = new StringBuilder();
				warningMsg
						.append("<b><font color=\"yellow\">Unmatched Account for </font>")
						.append(selected.getGroupType())
						.append("</font></b><br/><br/>");
				int i = 0;
				for (GroupMember m : invalidMbrs) {
					warningMsg.append(++i).append(" --> ")
							.append(m.getAccount()).append("<br/>");
				}
				Clients.showNotification(warningMsg.toString(), "warning",
						null, "middle_center", -1, true);
				return false;
			}

			// 5. Not allow 3 duplicate CIFs: 1 mbr can borrow not more than 2
			// times within a group cycle
			HashMap<String, List<GroupMember>> map = countDuplicateCIF();
			List<String> lstDuplicate3Cif = validateDuplicate3CIF(map);
			if (lstDuplicate3Cif.size() > 0) {
				StringBuilder warningMsg = new StringBuilder();
				warningMsg.append(
						"<b><font color=\"yellow\">Duplicate 3 CIFs</font>")
						.append("</font></b><br/><br/>");
				int i = 0;
				for (String m : lstDuplicate3Cif) {
					warningMsg.append(++i).append(" --> ").append(m)
							.append("<br/>");
				}
				Clients.showNotification(warningMsg.toString(), "warning",
						null, "middle_center", -1, true);
				return false;
			}

			// 6. Not allow to add existing members that are not yet payoff
			List<String> invalidMbrsPayOff = validateAddMbrPayOff(map);

			if (invalidMbrsPayOff.size() > 0) {
				StringBuilder warningMsg = new StringBuilder();
				warningMsg
						.append("<b><font color=\"yellow\">Not allow to add existing members that are not yet payoff</font>")
						.append("</font></b><br/><br/>");
				int i = 0;
				for (String m : invalidMbrsPayOff) {
					warningMsg.append(++i).append(" --> ").append(m)
							.append("<br/>");
				}
				Clients.showNotification(warningMsg.toString(), "warning",
						null, "middle_center", -1, true);
				return false;
			}

		}
		return true;
	}
	
	@Command	
	public void onStatus(){
		bStatus = true;
		onSave2();
		BindUtils.postNotifyChange(null, null, this, "*");
	}

	@Command
	@NotifyChange({ "selected", "domain", "notifyChange", "currentGroupLeader","lStatus"  })
	public void onSave2() {
		try {

			if (!isValidated())
				return;

			StringBuilder strBuilder = Common.createLogStringBuilder();

			boolean isNew = getSelected().getId() == null;
			if (isNew) {
				selected.setType(MfiFacade.getGroupType(frm.getField(
						"groupType").toString()));
				selected.setGroupSeq(Integer.parseInt(frm.getField("groupSeq")
						.toString()));
				selected.setCycleSeq(Integer.parseInt(frm.getField("cycleSeq")
						.toString()));

				selected.setBrCd(UserCredentialManager.getIntance()
						.getLoginUsr().getHomeBranch());
				selected.setCreateBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());
				//set status
				selected.setRecordStat("O");

				strBuilder.append(" created new GROUP --> ");
			} else {
				//update status
				if(bStatus){
					if(selected.getRecordStat().equals("O")){
						selected.setRecordStat("C");
					}else{
						selected.setRecordStat("O");
					}
				}
				selected.setChangeBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());

				strBuilder.append(" updated GROUP --> ");
			}

			// Assign to CB Cycle if use input, otherwise clear from CB cycle
			if (selected.getCbName().trim().length() > 0
					&& !selected.getGroupType().equals("SG")) {
				if (selectedCbCycle != null) {
					selected.setCbCycle(selectedCbCycle);
				}
			} else {
				selected.setCbCycle(null);
			}

			// 2. add groups into CB Cycle if groups has changed
			if (notifyChange) {
				if (!isNew) {
					/*
					 * Ebean.beginTransaction(); try{
					 * 
					 * List<GroupMember> mbrs = selected.getGroupMemers(); //
					 * remove old group members for (GroupMember o : mbrs) {
					 * o.setGroup(null); o.setIsGroupLeader(null); }
					 * Ebean.save(mbrs);
					 * 
					 * }finally{ Ebean.endTransaction();
					 * System.out.println("end tranction"); }
					 */

					// remove old members from group
					// and reset is_group_leader to null
					Ebean.execute(new TxRunnable() {

						@Override
						public void run() {
							System.out.println(Ebean.currentTransaction());

							List<GroupMember> mbrs = selected.getGroupMemers();
							// remove old group members
							for (GroupMember o : mbrs) {
								o.setGroup(null);
								o.setIsGroupLeader(null);
							}
							Ebean.save(mbrs);
						}
					});
				}
				// set new members for the group
				for (GroupMember g : chosenMbr) {
					g.setGroup(selected);
				}
				selected.setGroupMemers(chosenMbr);
				notifyChange = false;
			}

			boolean isNewLeader = selected.getCurrentLeader().getId() == null;

			if (frmLeader.isDirty()) {
				// 3. Leader Info
				Leader leader = currentGroupLeader; // selected.getCurrentLeader();
				if (selectedCIF != null) {
					leader.setGroupMemberID(selectedCIF.getId());
				}

				// add leaderCIF to Group
				selected.setLeaderCIF(currentGroupLeader.getCif());

				StringBuilder leaderMsg = Common.createLogStringBuilder();
				// 3.1 create new leader
				if (isNewLeader) {
					currentGroupLeader.setBrCd(selected.getBrCd());
					currentGroupLeader.setCreateBy(UserCredentialManager.getIntance()
							.getLoginUsr().getUsername());

					selected.addLeader(currentGroupLeader);
					leaderMsg.append(" assigned a new GROUP LEADER [");
					leaderMsg.append(currentGroupLeader.toStringSummary()).append(
							"] to the GROUP [");
					leaderMsg.append(getSelected().toString()).append("]");
				}
				// 3.2 update leader
				else {
					currentGroupLeader.setChangeBy(UserCredentialManager.getIntance()
							.getLoginUsr().getUsername());
					Ebean.update(currentGroupLeader);
					leaderMsg.append(" updated the GROUP LEADER [");
					leaderMsg.append(currentGroupLeader.toStringSummary()).append(
							"] of the GROUP [");
					leaderMsg.append(getSelected().toString()).append("]");
				}

				Common.addSessionLog(CMD.GROUP_INPUT, leaderMsg.toString(),
						new Date());
				System.out.println("saved leader info...");
			}

			Ebean.save(selected);

			if (frmLeader.isDirty() && isNewLeader) {
				int leaderID = MfiFacade.getCurrentGroupLeaderID(selected
						.getId());
				selected.setLeaderID(leaderID);
				Ebean.save(selected);
			}
			domain = (Domain) selected;

			strBuilder.append(selected.toString());
			Common.addSessionLogCommit(CMD.GROUP_INPUT, strBuilder.toString(),
					new Date());

			// re assign leader of the group
			if (MfiFacade.isLeaderCifChange(frmLeader.getDirtyFieldNames())) {
				final Integer leaderMbrID = currentGroupLeader
						.getGroupMemberID();
				Ebean.execute(new TxRunnable() {

					@Override
					public void run() {
						System.out.println(Ebean.currentTransaction());

						List<GroupMember> mbrs = selected.getGroupMemers();
						// mark the group leader with "Y"
						System.out.println("re assign member position");
						for (GroupMember o : mbrs) {
							if (o.getId() != leaderMbrID) {
								o.setIsGroupLeader(null);
								System.out.println(o.getId() + " is member");
							} else {
								o.setIsGroupLeader("Y");
								System.out.println(o.getId()
										+ " is group leader");
							}
						}
						Ebean.save(mbrs);
					}
				});

			}
			Clients.showNotification("Save successfully", "info", null,
					"middle_center", -1);
		} catch (OptimisticLockException e) {
			logger.error("Error while saving ebean GROUP", e);
			Clients.showNotification("Save failed!", "error", null,
					"middle_center", -1);
		}
	}

	@Command
	@NotifyChange("availMbr")
	public void onSearchMember(@BindingParam("groupType") String groupType,
			@BindingParam("villageID") String villageID) {
		/*
		 * Note: if use availMbr = null then availMbr = new ListModelList<>()
		 * ==> the listbox re render and doesn't support multi select So we just
		 * clear the model and addAll
		 */

		// availMbr = new ListModelList<>();

		availMbr.clear();
		// availMbr.removeRange(0, availMbr.getSize() - 1);
		availMbr.clearSelection();

		String prdCode = MfiFacade.getProductCode(groupType);

		availMbr.addAll(new ListModelList<GroupMember>(
				Ebean.find(GroupMember.class)
						.select("cif, account, fullName, fullNameKH, mobilizerName, amountDisbursed, valueDate, maturityDate, outstanding, currency, createBy, createOn, villageName")
						.where()
						.eq("brCd",
								UserCredentialManager.getIntance()
										.getLoginUsr().getHomeBranch())
						.not(Expr.in("id", getChosenGrpIDs()))
						.isNull("group")
						.ilike("villageName", "%" + villageID)
						.disjunction()
						.like("account", filter)
						.ilike("fullName", filter)
						.ilike("mobilizerName", filter)
						.like("cif", filter)
						.endJunction()
						.or(Expr.isNull("account"),
								Expr.like(
										"account",
										new StringBuilder()
												.append(UserCredentialManager
														.getIntance()
														.getLoginUsr()
														.getHomeBranch())
												.append(prdCode).append("%")
												.toString()))
						.order("createBy asc, createOn asc").findList()));

		System.out.println("seaching available group members...");
	}

	/**
	 * @return the availMbr
	 */
	public ListModelList<GroupMember> getAvailMbr() {
		if (availMbr == null) {
			/*
			 * String prdCode = ""; availMbr.addAll(new
			 * ListModelList<GroupMember>( Ebean.find(GroupMember.class)
			 * .select(
			 * "cif, account, fullName, fullNameKH, mobilizerName, amountDisbursed, valueDate, maturityDate, outstanding, currency, createBy, createOn"
			 * ) .where() .eq("brCd", UserCredentialManager.getIntance()
			 * .getLoginUsr().getHomeBranch()) .or(Expr.isNull("account"),
			 * Expr.like( "account", new StringBuilder()
			 * .append(UserCredentialManager .getIntance() .getLoginUsr()
			 * .getHomeBranch()) .append(prdCode) .append("%").toString()))
			 * .not(Expr.in("id", getChosenGrpIDs())) .isNull("group")
			 * .or(Expr.ilike("cif", filter), Expr.ilike("account", filter))
			 * .order("createBy asc, createOn asc").findList()));
			 */}
		return availMbr;
	}

	private List<Integer> getChosenGrpIDs() {
		List<Integer> lst = new ArrayList<Integer>();
		for (GroupMember g : getChosenMbr()) {
			lst.add(g.getId());
		}
		return lst;
	}

	/**
	 * @param availMbr
	 *            the availMbr to set
	 */
	public void setAvailMbr(ListModelList<GroupMember> availMbr) {
		this.availMbr = availMbr;
	}

	/**
	 * @return the chosenMbr
	 */
	public ListModelList<GroupMember> getChosenMbr() {
		if (chosenMbr == null) {
			chosenMbr = new ListModelList<GroupMember>(
					selected.getGroupMemers());
		}
		return chosenMbr;
	}

	/**
	 * @param chosenMbr
	 *            the chosenMbr to set
	 */
	public void setChosenMbr(ListModelList<GroupMember> chosenMbr) {
		this.chosenMbr = chosenMbr;
	}

	@Command
	@NotifyChange({ "availMbr", "chosenMbr", "selectAvails", "notifyChange" })
	public void onChoose() {

		// Do validation
		if (selected.getCurrentLeader().getId() != null) {

			// 1. Not allow new to add new members with maturity date greater
			// than
			// the group's
			List<GroupMember> invalidMbrs = new ArrayList<>();

			for (GroupMember m : selectAvails) {
				if (m.getAccount() == null) {
					continue;
				}
				if (m.getMaturityDate().after(
						selected.getCurrentLeader().getEndDate())) {
					invalidMbrs.add(m);
				}
			}

			if (invalidMbrs.size() > 0) {
				StringBuilder warningMsg = new StringBuilder();
				warningMsg
						.append("<b><font color=\"yellow\">Maturity date must not after the group's [")
						.append(Common.getDateFormat(selected
								.getCurrentLeader().getEndDate(), "dd-MM-yyyy"))
						.append("]").append("</font></b><br/><br/>");
				int i = 0;
				for (GroupMember m : invalidMbrs) {
					warningMsg
							.append(++i)
							.append(" --> ")
							.append(m.getFullNameKH())
							.append(" - ")
							.append(m.getAccount())
							.append(" - ")
							.append(Common.getDateFormat(m.getMaturityDate(),
									"dd-MM-yyyy")).append("<br/>");
				}
				Clients.showNotification(warningMsg.toString(), "warning",
						null, "middle_center", -1, true);
				return;
			}

			// 2. Do not allow client/ group to renew loan if the repayment in
			// the
			// bank is less than 4 months
			DateTime dtMaturity = new DateTime(selected.getCurrentLeader()
					.getEndDate());
			DateTime dtNow = new DateTime();

			int months = Months.monthsBetween(dtNow, dtMaturity).getMonths();

			if (months < 4) {
				Clients.showNotification(
						"New members are not allowed to add into the group because it has repayments less than 4 months",
						"warning", null, "middle_center", -1, true);
				return;
			}

		}

		StringBuilder strBuilder = Common.createLogStringBuilder();
		if (selectAvails.size() > 1)
			strBuilder.append(" added ").append(selectAvails.size())
					.append(" group members {");
		else
			strBuilder.append(" added ").append(selectAvails.size())
					.append(" group member {");
		for (GroupMember m : selectAvails) {
			if (selectAvails.size() < 6) {
				strBuilder.append("[").append(m.toStringSummary()).append("]");
			} else {
				strBuilder.append("[").append(m.getId()).append("]");
			}
		}
		strBuilder.append("} to the GROUP --> ");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.GROUP_INPUT, strBuilder.toString(), new Date());

		availMbr.removeAll(selectAvails);
		chosenMbr.addAll(selectAvails);
		availMbr.clearSelection();
		selectAvails = new HashSet<GroupMember>();
		notifyChange = true;
	}

	@Command
	@NotifyChange({ "availMbr", "chosenMbr", "selectAvails", "notifyChange" })
	public void onChooseAll() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		if (availMbr.size() > 1)
			strBuilder.append(" added all avail group member(s) (")
					.append(availMbr.size()).append(") {");
		else
			strBuilder.append(" added all avail group member (")
					.append(availMbr.size()).append(") {");
		for (GroupMember m : availMbr) {
			if (availMbr.size() < 6) {
				strBuilder.append("[").append(m.toStringSummary()).append("]");
			} else {
				strBuilder.append("[").append(m.getId()).append("]");
			}
		}
		strBuilder.append("} to the GROUP --> ");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.GROUP_INPUT, strBuilder.toString(), new Date());

		chosenMbr.addAll(availMbr);
		availMbr.clear();
		availMbr.clearSelection();
		selectAvails = new HashSet<GroupMember>();
		notifyChange = true;
	}

	@Command
	@NotifyChange({ "availMbr", "chosenMbr", "selectChosens", "notifyChange" })
	public void onRemoveAll() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		if (chosenMbr.size() > 1)
			strBuilder.append(" removed all group members (")
					.append(chosenMbr.size()).append(") {");
		else
			strBuilder.append(" removed all group member (")
					.append(chosenMbr.size()).append(") {");
		for (GroupMember m : chosenMbr) {
			if (chosenMbr.size() > 5) {
				strBuilder.append("[").append(m.getId()).append("]");
			} else {
				strBuilder.append("[").append(m.toStringSummary()).append("]");
			}
		}
		strBuilder.append("} from the GROUP --> ");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.GROUP_INPUT, strBuilder.toString(), new Date());

		availMbr.addAll(chosenMbr);
		chosenMbr.clearSelection();
		chosenMbr.clear();
		selectChosens = new HashSet<GroupMember>();
		notifyChange = true;
	}

	@Command
	@NotifyChange({ "availMbr", "chosenMbr", "selectChosens", "notifyChange" })
	public void onRemove() {
		StringBuilder strBuilder = Common.createLogStringBuilder();
		if (selectChosens.size() > 1)
			strBuilder.append(" removed ").append(selectChosens.size())
					.append(" group members {");
		else
			strBuilder.append(" removed ").append(selectChosens.size())
					.append(" group member {");
		for (GroupMember m : selectChosens) {
			if (selectChosens.size() < 6) {
				strBuilder.append("[").append(m.toStringSummary()).append("]");
			} else {
				strBuilder.append("[").append(m.getId()).append("]");
			}
		}
		strBuilder.append("} from the GROUP -->");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.GROUP_INPUT, strBuilder.toString(), new Date());

		chosenMbr.removeAll(selectChosens);
		availMbr.addAll(selectChosens);
		chosenMbr.clearSelection();
		selectChosens = new HashSet<GroupMember>();
		notifyChange = true;
	}

	/**
	 * @return the selectChosens
	 */
	public Set<GroupMember> getSelectChosens() {
		if (selectChosens == null)
			selectChosens = new HashSet<GroupMember>();
		return selectChosens;
	}

	/**
	 * @param selectChosens
	 *            the selectChosens to set
	 */
	public void setSelectChosens(Set<GroupMember> selectChosens) {
		this.selectChosens = selectChosens;
		System.out.println(selectChosens.size());
	}

	/**
	 * @return the selectAvails
	 */
	public Set<GroupMember> getSelectAvails() {
		if (selectAvails == null)
			selectAvails = new HashSet<GroupMember>();
		return selectAvails;
	}

	/**
	 * @param selectAvails
	 *            the selectAvails to set
	 */
	public void setSelectAvails(Set<GroupMember> selectAvails) {
		this.selectAvails = selectAvails;
	}

	@Command
	public void onPopulateNewGroupAccNo(
			@BindingParam("txtGrAccNo") Textbox txtGrAccNo) {
		if (this.txtGrAccNo == null)
			this.txtGrAccNo = txtGrAccNo;

		String type = frm.getField("groupType").toString();
		String brCd = frm.getField("brCd").toString();
		int groupSeq = MfiFacade.getMaxGroupSeq(brCd, type);
		int cycleSeq = 0; // MfiFacade.getMaxGroupCycleSeq(brCd, type,
							// groupSeq);

		doPopulateGroupAccNo(brCd, type, groupSeq + 1, cycleSeq + 1);
	}

	private void doPopulateGroupAccNo(String brCd, String type, int groupSeq,
			int cycleSeq) {

		frm.setField("groupSeq", groupSeq);
		frm.setField("cycleSeq", cycleSeq);
		frm.setField("groupType", type);

		String grAccNo = MfiFacade.doPopulateGroupAccNo(brCd, type, groupSeq,
				cycleSeq);
		txtGrAccNo.setText(grAccNo);

		Events.postEvent("onChange", txtGrAccNo, null);
	}

	/**
	 * @return the lstFetchGroup
	 */
	public ListModelList<Group> getLstFetchGroup() {
		if (lstFetchGroup != null) {
			return lstFetchGroup;
		}

		Group paramGroup = getParamGroup();

		lstFetchGroup = new ListModelList<Group>(
				Ebean.find(Group.class)
						.select("brCd, groupAccNo, leaderName, villageID, villageName")
						.setUseQueryCache(true)
						.where()
						.eq("brCd",
								UserCredentialManager.getIntance()
										.getLoginUsr().getHomeBranch())
						.like("groupAccNo", paramGroup.getGroupAccNo())
						.ilike("leaderName", paramGroup.getLeaderName())
						.like("type", paramGroup.getGroupType()).findList());

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
	 * @param selectedGroup
	 *            the selectedGroup to set
	 */
	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
		if (!selectedGroup.getVillageID().isEmpty()) {
			this.txtVillage.setText(selectedGroup.getVillageName());
			this.txtVillageID.setText(selectedGroup.getVillageID());
			Events.postEvent("onChange", txtVillage, null);
			Events.postEvent("onChange", txtVillageID, null);
		}

		doPopulateGroupAccNo(selectedGroup.getBrCd(), selectedGroup.getType()
				.toString(), selectedGroup.getGroupSeq(),
				selectedGroup.getCycleSeq() + 1);

		String type = selectedGroup.getType().toString();

		if (type.equals("SG")) {
			rdSG.setChecked(true);
			Events.postEvent("onCheck", rdSG, null);
		} else if (type.equals("CB")) {
			rdCB.setChecked(true);
			Events.postEvent("onCheck", rdCB, null);
		} else if (type.equals("TB")) {
			rdTB.setChecked(true);
			Events.postEvent("onCheck", rdTB, null);
		}
		// notify change on radiogroup
		Events.postEvent("onCheck", rdTB.getRadiogroup(), null);
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

	@Command
	@NotifyChange({ "paramCIF", "selectedCIF" })
	public void onShowFetchCIF(@BindingParam("txtLeaderCIF") Textbox txtCIF,
			@BindingParam("txtLeaderName") Textbox txtLeaderName,
			@BindingParam("txtLeaderNameKH") Textbox txtLeaderNameKH,
			@BindingParam("dtStart") Datebox dtStart,
			@BindingParam("dtEnd") Datebox dtEnd) {
		if (this.txtLeaderCIF == null) {
			this.txtLeaderCIF = txtCIF;
		}

		if (this.txtLeaderName == null) {
			this.txtLeaderName = txtLeaderName;
		}
		
		if (this.txtLeaderNameKH == null) {
			this.txtLeaderNameKH = txtLeaderNameKH;
		}

		if (this.dtStart == null) {
			this.dtStart = dtStart;
		}

		if (this.dtEnd == null) {
			this.dtEnd = dtEnd;
		}

		selectedCIF = null;

		// paramCIF.setCustNo(txtLeaderCIF.getText() + "%");

		// Executions.createComponents("/common/listval/cif.zul", winGroupInput,
		// null);
		Executions.createComponents("/common/listval/leader.zul",
				winGroupInput, null);
	}

	@Command
	@NotifyChange("lstFetchCIF")
	public void onSearchCIF() {
		lstFetchCIF = null;
		System.out.println("searching..");
	}

	@Command
	@NotifyChange("lstFetchCIF")
	public void onResetSearchCIF() {
		lstFetchCIF = new ListModelList<GroupMember>();
		System.out.println("reseting...");
	}

	/**
	 * @return the lstFetchCIF
	 */
	public ListModelList<GroupMember> getLstFetchCIF() {
		if (lstFetchCIF == null) {
			lstFetchCIF = new ListModelList<>(getChosenMbr());
			
//			for (GroupMember m : lstFetchCIF) {
//				if(m.getAccount() == null){
//					continue;
//				}
//				if (m.getOutstanding() == 0) {
//					lstFetchCIF.remove(m);
//				}
//			}
			
			/* to avoid java.util.ConcurrentModificationException*/
			
			for(int i=0; i< lstFetchCIF.size(); i++){
				GroupMember m = lstFetchCIF.get(i);
				if(m.getAccount() == null){
					continue;
				}
				
//				if (m.getOutstanding() == 0) {
//					lstFetchCIF.remove(m);
//					i--;
//				}
			}
			
		}
		return lstFetchCIF;
	}

	/*
	 * private List<String> getGroupMbrCIFs() { List<String> lst = new
	 * ArrayList<>(); for (GroupMember g : getChosenMbr()) {
	 * lst.add(g.getCif()); } return lst; }
	 */

	/**
	 * @param lstFetchCIF
	 *            the lstFetchCIF to set
	 */
	public void setLstFetchCIF(ListModelList<GroupMember> lstFetchCIF) {
		this.lstFetchCIF = lstFetchCIF;
	}

	/**
	 * @return the selectedCIF
	 */
	public GroupMember getSelectedCIF() {
		return selectedCIF;
	}

	/**
	 * @param selectedCIF
	 *            the selectedCIF to set
	 */
	public void setSelectedCIF(GroupMember selectedCIF) {		
		this.selectedCIF = selectedCIF;
		txtLeaderCIF.setText(selectedCIF.getCif());
		txtLeaderName.setText(selectedCIF.getFullName());
		txtLeaderNameKH.setText(selectedCIF.getFullNameKH());

		Leader leader = getLeaderStartEndDate();		
		dtStart.setValue(leader.getStartDate());
		dtEnd.setValue(leader.getEndDate());		

		Events.postEvent("onChange", txtLeaderCIF, null);
		Events.postEvent("onChange", txtLeaderName, null);
		Events.postEvent("onChange", txtLeaderNameKH, null);
		Events.postEvent("onChange", dtStart, null);
		Events.postEvent("onChange", dtEnd, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void onConfirmAddNewLeader(
			@BindingParam("txtLeaderCIF") Textbox txtCIF,
			@BindingParam("txtLeaderName") Textbox txtLeaderName,
			@BindingParam("txtLeaderNameKH") Textbox txtLeaderNameKH,
			@BindingParam("dtStart") Datebox dtStart,
			@BindingParam("dtEnd") Datebox dtEnd) {

		if (this.txtLeaderCIF == null) {
			this.txtLeaderCIF = txtCIF;
		}

		if (this.txtLeaderName == null) {
			this.txtLeaderName = txtLeaderName;
		}
		
		if (this.txtLeaderNameKH == null) {
			this.txtLeaderNameKH = txtLeaderNameKH;
		}

		if (this.dtStart == null) {
			this.dtStart = dtStart;
		}

		if (this.dtEnd == null) {
			this.dtEnd = dtEnd;
		}

		if (selected.getCurrentLeader().getEndDate() == null) {
			Clients.showNotification(
					"Please put end date for the current leader", "error",
					null, "middle_center", -1);

			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder
					.append(" was notified to put end date for the current GROUP LEADER -->");
			strBuilder.append(selected.getCurrentLeader().toString());
			Common.addSessionLog(CMD.GROUP_INPUT, strBuilder.toString(),
					new Date());

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
		strBuilder
				.append(" confirmed to assign a new LEADER of the GROUP --> ");
		strBuilder.append(getSelected().toString());
		Common.addSessionLog(CMD.GROUP_INPUT, strBuilder.toString(), new Date());

		// Clear form for adding new leader
		selected.getCurrentLeader().setId(null);
		frm.setField("currentLeader.id", null);

		txtLeaderCIF.setText("."); // to prevent constraints blank
		txtLeaderName.setText(".");
		txtLeaderNameKH.setText(".");
		// dtStart.setText("");
		// dtEnd.setText("");

		Events.postEvent("onChange", txtLeaderCIF, null);
		Events.postEvent("onChange", txtLeaderName, null);
		Events.postEvent("onChange", txtLeaderNameKH, null);
		// Events.postEvent("onChange", dtStart, null);
		// Events.postEvent("onChange", dtEnd, null);
	}

	@Command
	@NotifyChange({ "cbFilter", "selectedCbCycle", "lstFetchCbCycle" })
	public void onShowFetchCbCycle(@BindingParam("txtCB") Textbox txtCB,
			@BindingParam("txtCycleNo") Textbox txtCycleNo,
			@BindingParam("txtVillageID") Textbox txtVillageID) {
		if (this.txtCB == null)
			this.txtCB = txtCB;

		if (this.txtCbCycleNo == null)
			this.txtCbCycleNo = txtCycleNo;

		if (this.txtVillageID == null)
			this.txtVillageID = txtVillageID;

		cbFilter = txtCB.getText() + "%";
		selectedCbCycle = null;
		lstFetchCbCycle = null;

		Executions.createComponents("/common/listval/cb.zul", winGroupInput,
				null);
	}

	@Command
	@NotifyChange("lstFetchCbCycle")
	public void onSearchCbCycle() {
		lstFetchCbCycle = null;
	}

	/**
	 * @return the lstFetchCbCycle
	 */
	public ListModelList<CbCycle> getLstFetchCbCycle() {
		if (lstFetchCbCycle == null) {
			List<CbCycle> ls = Ebean
					.find(CbCycle.class)
					.select("cycleNo, leaderName")
					.fetch("cb", "nameEN, nameKH")
					.where()
					.eq("brCd",
							UserCredentialManager.getIntance().getLoginUsr()
									.getHomeBranch())
					.eq("villageID", getVillageID())
					.or(Expr.ilike("cb.nameEN", cbFilter),
							Expr.ilike("cb.nameKH", cbFilter)).findList();
			lstFetchCbCycle = new ListModelList<CbCycle>(ls);
		}
		return lstFetchCbCycle;
	}

	private String getVillageID() {
		String id = "";
		if (txtVillageID != null) {
			id = txtVillageID.getText();
		}
		return id;
	}

	/**
	 * @param lstFetchCbCycle
	 *            the lstFetchCbCycle to set
	 */
	public void setLstFetchCbCycle(ListModelList<CbCycle> lstFetchCbCycle) {
		this.lstFetchCbCycle = lstFetchCbCycle;
	}

	/**
	 * @return the selectedCbCycle
	 */
	public CbCycle getSelectedCbCycle() {
		return selectedCbCycle;
	}

	/**
	 * @param selectedCbCycle
	 *            the selectedCbCycle to set
	 */
	public void setSelectedCbCycle(CbCycle selectedCbCycle) {
		this.selectedCbCycle = selectedCbCycle;

		txtCB.setText(selectedCbCycle.getCb().getNameEN());
		txtCbCycleNo.setText(selectedCbCycle.getCycleNo().toString());

		Events.postEvent("onChange", txtCB, null);
		Events.postEvent("onChange", txtCbCycleNo, null);
	}

	/**
	 * @return the cbFilter
	 */
	public String getCbFilter() {
		return cbFilter;
	}

	/**
	 * @param cbFilter
	 *            the cbFilter to set
	 */
	public void setCbFilter(String cbFilter) {
		this.cbFilter = cbFilter;
	}

	@Command
	@NotifyChange("selectedCbCycle")
	public void onClearCbCycle(@BindingParam("txtCB") Textbox txtCB,
			@BindingParam("txtCycleNo") Textbox txtCycleNo) {

		if (this.txtCB == null)
			this.txtCB = txtCB;

		if (this.txtCbCycleNo == null)
			this.txtCbCycleNo = txtCycleNo;

		this.txtCB.setText("");
		this.txtCbCycleNo.setText("");

		selectedCbCycle = null;

		Events.postEvent("onChange", txtCB, null);
		Events.postEvent("txtCbCycleNo", txtCB, null);
	}

	/**
	 * @return the currentGroupLeader
	 */
	public Leader getCurrentGroupLeader() {
		if (currentGroupLeader == null)
			currentGroupLeader = new Leader();
		return currentGroupLeader;
	}

	/**
	 * @param currentGroupLeader
	 *            the currentGroupLeader to set
	 */
	public void setCurrentGroupLeader(Leader currentGroupLeader) {
		this.currentGroupLeader = currentGroupLeader;
	}

	/**
	 * @return the frmLeader
	 */
	public MySimpleForm getFrmLeader() {
		return frmLeader;
	}

	/**
	 * @param frmLeader
	 *            the frmLeader to set
	 */
	public void setFrmLeader(MySimpleForm frmLeader) {
		this.frmLeader = frmLeader;
	}

	@Command
	@NotifyChange({ "availMbr" })
	public void onCheckGroupType(@BindingParam("txtGrAccNo") Textbox txtGrAccNo) {
		if (this.txtGrAccNo == null)
			this.txtGrAccNo = txtGrAccNo;

		txtGrAccNo.setText("%");
		availMbr.clearSelection();
		availMbr.clear();

		Events.postEvent("onChange", txtGrAccNo, null);
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
	 * @param pagingList
	 *            the pagingList to set
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
	 * @param totalSize
	 *            the totalSize to set
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Command
	public void translateToLetter(@BindingParam("amount") String amount,
			@BindingParam("txtNumLetter") Textbox txtNumLetter) {

		if (this.txtNumLetter == null) {
			this.txtNumLetter = txtNumLetter;
		}

		this.txtNumLetter.setText(NumberTranslater.translateToLetter(amount
				.toString(),""));

		Events.postEvent("onChange", txtNumLetter, null);
	}

	@Command
	public String translate(int amount) {
		return NumberTranslater.translateToLetter(amount + "","");
	}

	private Leader getLeaderStartEndDate() {
		Leader leader = new Leader();

		List<Date> lstDateStart = new ArrayList<>();
		List<Date> lstDateEnd = new ArrayList<>();
		for (GroupMember mbr : lstFetchCIF) {
			if(mbr.getAccount() == null || mbr.getValueDate()==null){
				continue;
			}
			lstDateStart.add(mbr.getValueDate());
			lstDateEnd.add(mbr.getMaturityDate());
		}
		leader.setStartDate(Collections.min(lstDateStart));
		leader.setEndDate(Collections.max(lstDateEnd));

		return leader;
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

}
