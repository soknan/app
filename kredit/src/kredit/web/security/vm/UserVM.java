package kredit.web.security.vm;

import java.text.SimpleDateFormat;
import java.util.Date;

import kredit.web.core.model.Employee;
import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.security.Security;
import kredit.web.security.model.Function;
import kredit.web.security.model.ParamEmp;
import kredit.web.security.model.ParamUser;
import kredit.web.security.model.User;
import kredit.web.security.model.UserFunction;
import kredit.web.security.model.UserRole;
import kredit.web.security.model.UserValidity;
import kredit.web.security.model.facade.SecurityFacade;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;

public class UserVM {
	
	ListModelList<User> lst;
	ListModelList<User> syncUserlst;
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	int rowPerPage = 10;
	Privilege privilege = null;

	User selectedUser = new User();
	UserValidity selectedValidity = new UserValidity();
	UserRole selectedUserRoleO;
	UserRole selectedUserRoleA;
	Function selectedFunction = new Function();
	UserFunction selectedUserFunction = new UserFunction();
	ParamUser param = new ParamUser();
	ParamEmp paramEmp = new ParamEmp();
	Boolean visible = false;
	Boolean visibleValidity = false;
	Boolean disableUserAs = true;
	Boolean help = true;
	boolean visibleSync = false;
	
	ListModelList<CodeItem> filterBranches;
	ListModelList<CodeItem> filterStatus;
	ListModelList<CodeItem> filterBoolP;
	ListModelList<CodeItem> filterBoolA;
	ListModelList<CodeItem> filterBoolL;
	ListModelList<CodeItem> filterBranchesValidity;
	ListModelList<CodeItem> filterValidities;
	ListModelList<Employee> filterEmployees;
	ListModelList<CodeItem> filterBranchesAll;
	ListModelList<CodeItem> filterPositions;

	@Wire("#urls")
	private Window urls;
	
	private static final int TAB_INFO = 0;
	private static final int TAB_VALIDITY = 1;
	private static final int TAB_USER_ROLE = 2;
	private static final int TAB_USER_FUNCTION = 3;
	
	int selectedTabIndex;
	
	Employee selectedEmp = new Employee();
	
//GETTER AND SETTER

	public ListModelList<User> getLst() {
		if(lst == null)
		{
			lst = new ListModelList<User>(SecurityFacade.getUsersList(param));
		}
		return lst;
	}
	
	public void setLst(ListModelList<User> lst) {
		this.lst = lst;
	}

	public ListModelList<User> getSyncUserlst() {
		/*
		if(syncUserlst == null)
			syncUserlst = new ListModelList<User>();
			*/
		return syncUserlst;
	}

	public void setSyncUserlst(ListModelList<User> syncUserlst) {
		this.syncUserlst = syncUserlst;
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
	
	public int getRowPerPage() {
		return rowPerPage;
	}
	
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}
	
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public ParamUser getParam() {
		return param;
	}
	
	public void setParam(ParamUser param) {
		this.param = param;
	}
	
	public ParamEmp getParamEmp() {
		return paramEmp;
	}

	public void setParamEmp(ParamEmp paramEmp) {
		this.paramEmp = paramEmp;
	}

	public boolean isVisibleEmployee() {
		return visibleEmployee;
	}

	public void setVisibleEmployee(boolean visibleEmployee) {
		this.visibleEmployee = visibleEmployee;
	}

	public Boolean getVisible() {
		return visible;
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	
	public UserValidity getSelectedValidity() {
		return selectedValidity;
	}

	public void setSelectedValidity(UserValidity selectedValidity) {
		this.selectedValidity = selectedValidity;
	}
	
	public Boolean getVisibleValidity() {
		return visibleValidity;
	}

	public void setVisibleValidity(Boolean visibleValidity) {
		this.visibleValidity = visibleValidity;
	}
	
	public Boolean getDisableUserAs() {
		return disableUserAs;
	}

	public void setDisableUserAs(Boolean disableUserAs) {
		this.disableUserAs = disableUserAs;
	}
	
	public UserRole getSelectedUserRoleO() {
		return selectedUserRoleO;
	}

	public void setSelectedUserRoleO(UserRole selectedUserRoleO) {
		this.selectedUserRoleO = selectedUserRoleO;
	}

	public UserRole getSelectedUserRoleA() {
		return selectedUserRoleA;
	}

	public void setSelectedUserRoleA(UserRole selectedUserRoleA) {
		this.selectedUserRoleA = selectedUserRoleA;
	}
	
	public Function getSelectedFunction() {
		return selectedFunction;
	}

	public void setSelectedFunction(Function selectedFunction) {
		this.selectedFunction = selectedFunction;
	}
	
	public UserFunction getSelectedUserFunction() {
		return selectedUserFunction;
	}

	public void setSelectedUserFunction(UserFunction selectedUserFunction) {
		this.selectedUserFunction = selectedUserFunction;
	}
	
	public ListModelList<CodeItem> getFilterBranches() {
		if(filterBranches == null)
		{
			filterBranches = new ListModelList<CodeItem>(SecurityFacade.getBranchesList());
		}
		return filterBranches;
	}

	public void setFilterBranches(ListModelList<CodeItem> filterBranches) {
		this.filterBranches = filterBranches;
	}
	
	public ListModelList<CodeItem> getFilterBranchesAll() {
		if(filterBranchesAll == null)
		{
			filterBranchesAll = new ListModelList<CodeItem>(SecurityFacade.getBranchesListWithAll());
		}
		return filterBranchesAll;
	}

	public void setFilterBranchesAll(ListModelList<CodeItem> filterBranchesAll) {
		this.filterBranchesAll = filterBranchesAll;
	}

	public ListModelList<CodeItem> getFilterPositions() {
		if(filterPositions == null)
		{
			filterPositions = new ListModelList<CodeItem>(SecurityFacade.getPositionList());
		}
		return filterPositions;
	}

	public void setFilterPositions(ListModelList<CodeItem> filterPositions) {
		this.filterPositions = filterPositions;
	}

	public ListModelList<CodeItem> getFilterStatus() {
		if(filterStatus == null)
		{
			//filterStatus = new ListModelList<CodeItem>(SecurityFacade.getSysLovItem(Common.SYS_LOV_STATUS));
			filterStatus = new ListModelList<CodeItem>();
			String[] desc = new String[] {"Active", "Inactive"};
			String[] code = new String[] {"A", "I"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				filterStatus.add(item);
			}
		}
		return filterStatus;
	}

	public void setFilterStatus(ListModelList<CodeItem> filterStatus) {
		this.filterStatus = filterStatus;
	}
	
	public ListModelList<CodeItem> getFilterBoolP() {
		if(filterBoolP == null)
		{
			filterBoolP = new ListModelList<CodeItem>();
			String[] desc = new String[] {"True", "False"};
			String[] code = new String[] {"Y", "N"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				filterBoolP.add(item);
			}
		}
		return filterBoolP;
	}

	public void setFilterBoolP(ListModelList<CodeItem> filterBoolean) {
		this.filterBoolP = filterBoolean;
	}
	
	public ListModelList<CodeItem> getFilterBoolA() {
		if(filterBoolA == null)
		{
			filterBoolA = new ListModelList<CodeItem>();
			String[] desc = new String[] {"True", "False"};
			String[] code = new String[] {"Y", "N"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				filterBoolA.add(item);
			}
		}
		return filterBoolA;
	}

	public void setFilterBoolA(ListModelList<CodeItem> filterBoolA) {
		this.filterBoolA = filterBoolA;
	}

	public ListModelList<CodeItem> getFilterBoolL() {
		if(filterBoolL == null)
		{
			filterBoolL = new ListModelList<CodeItem>();
			String[] desc = new String[] {"True", "False"};
			String[] code = new String[] {"Y", "N"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				filterBoolL.add(item);
			}
		}
		return filterBoolL;
	}

	public void setFilterBoolL(ListModelList<CodeItem> filterBoolL) {
		this.filterBoolL = filterBoolL;
	}
	
	public ListModelList<CodeItem> getFilterBranchesValidity() {
		if(filterBranchesValidity == null)
		{
			filterBranchesValidity = new ListModelList<CodeItem>(SecurityFacade.getBranchesList());
		}
		return filterBranchesValidity;
	}

	public void setFilterBranchesValidity(
			ListModelList<CodeItem> filterBranchesValidity) {
		this.filterBranchesValidity = filterBranchesValidity;
	}
	
	public ListModelList<CodeItem> getFilterValidities() {
		if(filterValidities == null)
		{
			//filterValidities = new ListModelList<CodeItem>(SecurityFacade.getSysLovItem(Common.SYS_LOV_VALIDITY));
			filterValidities = new ListModelList<CodeItem>();
			String[] desc = new String[] {"Acting", "Replacement"};
			String[] code = new String[] {"A", "R"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				filterValidities.add(item);
			}
		}
		return filterValidities;
	}

	public void setFilterValidities(ListModelList<CodeItem> filterValidities) {
		this.filterValidities = filterValidities;
	}
	
	public ListModelList<Employee> getFilterEmployees() {
		if(filterEmployees == null)
		{
			filterEmployees = new ListModelList<Employee>(SecurityFacade.getEmployeesList(paramEmp));
		}
		
		return filterEmployees;
	}

	public void setFilterEmployees(ListModelList<Employee> filterEmployees) {
		this.filterEmployees = filterEmployees;
	}
	
	public Privilege getPrivilege() {
		if(privilege == null)
		{
			privilege = Common.getPrivilege(CMD.LIST_USER);
		}
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}
	
	public boolean isVisibleSync() {
		return visibleSync;
	}

	public void setVisibleSync(boolean visibleSync) {
		this.visibleSync = visibleSync;
	}
	
	public Employee getSelectedEmp() {
		return selectedEmp;
	}

	public void setSelectedEmp(Employee selectedEmp) {
		this.selectedEmp = selectedEmp;
	}
	
//GETTER AND SETTER

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange({ "lst" })
	public void doSearch()
	{
		lst = null;
	}
	
	@Command
	@NotifyChange({ "lst", "param", "selectedUser" })
	public void onClearAll()
	{
		param = new ParamUser();
		selectedUser = new User();
		doSearch();
	}

	@Command
	@NotifyChange({ "lst", "selectedUser", "visible", "help", "selectedTabIndex" })
	public void onOpen()
	{
		visible = true;
		help = true;
		
		selectedTabIndex = TAB_INFO;
		
		Boolean wait = urls.hasFellow("userDetail");
		
		if(wait)
			return;

		Executions.createComponents("/security/detail/userDetail.zul", urls, null);
		
	}
	
	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onSave() throws Throwable
	{
		if(selectedUser.getId() == null)
		{
			selectedUser.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedUser.setCreate_on(new Date());
		}
		else
		{
			selectedUser.setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedUser.setChange_on(new Date());
		}
		
		saveFunction();
		
		int tmp = 0;
		boolean newU = false;
		
		//username available or not, tmp = 0 if username is not existed
		if(selectedUser.getId() == null)
		{
			tmp = SecurityFacade.duplicated(selectedUser.getUsername(), 1);
			newU = true;
		}
			
		
		//help is false when there are UserFunction that right is null
		if(help && tmp == 0)
		{
			if(newU)
			{
				//If the user is in SYS_EMPLOYEE
				if(!(selectedUser.getEmp_id() == null))
				{
					String tmpP = new SimpleDateFormat("ddMMyy").format(selectedUser.getEmp().getBirth_date()).toString();
					selectedUser.setPwd(Security.encPwd(tmpP));
				}
			}
		
			Ebean.save(selectedUser);
			Clients.showNotification("User: " + selectedUser.getUsername() + " has been saved");
			if(newU)
			{
				StringBuilder strBuilder = Common.createLogStringBuilder();
				strBuilder.append(" saved User --> ");
				strBuilder.append(selectedUser.toString());
				Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(),
						new Date());
			}
		}
			
		
		if(tmp != 0)
			Clients.showNotification("This username is already existed.");
		
		lst = null;
	}
	
	@Command
	@NotifyChange({ "lst", "selectedUser", "visible", "selectedFunction", "selectedUserFunction", "selectedValidity", 
					"selectedRoleA", "selectedRoleU", "help" })
	public void onNew() throws Throwable
	{
		selectedUser = new User();
		selectedUser.setSecurity_no(0);
		selectedUser.setPwd(Security.encPwd("123"));
		visible = true;
		help = true;
		
		selectedFunction = new Function();
		selectedUserFunction = new UserFunction();
		selectedValidity = new UserValidity();
		selectedUserRoleA = new UserRole();
		selectedUserRoleO = new UserRole();
		
		boolean wait = urls.hasFellow("userDetail");
		
		if(wait)
			return;
		
		Executions.createComponents("/security/detail/userDetail.zul", urls, null);
	}
	
	@Command
	@NotifyChange({ "selectedValidity", "visibleValidity", "disableUserAs", "lstUserAs" })
	public void onOpenValidity()
	{
		visibleValidity = true;
		
		Executions.createComponents("security/include/users/validityInfo.zul", urls, null);
		
		if(selectedValidity.getId() != null)
		{
			disableUserAs = false;
			selectedValidity.setLstUserAs(null);
		}
			
	}
	
	@Command
	@NotifyChange({ "selectedUser" })
	public void onSaveValidity()
	{
		selectedValidity.setUser_id(selectedUser.getId());
		
		if(selectedValidity.getId() == null)
		{
			selectedValidity.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedValidity.setCreate_on(new Date());
		}
		else
		{
			selectedValidity.setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedValidity.setChange_on(new Date());
		}
		
		boolean newV = false;
		if(selectedValidity.getId() == null)
			newV = true;
		
		Ebean.save(selectedValidity);
		Clients.showNotification("Validity has been saved");
		
		if(newV)
		{
			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" saved Validity --> ");
			strBuilder.append(selectedValidity.toString());
			Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(),
					new Date());
		}
		
		selectedUser.setUserValidities(null);
	}
	
	@Command
	@NotifyChange({ "selectedValidity", "visibleValidity", "disableUserAs" })
	public void onNewValidity()
	{
		selectedValidity = new UserValidity();
		if(selectedUser.getId() != null)
			selectedValidity.setUser_id(selectedUser.getId());
		visibleValidity = true;
		
		Executions.createComponents("security/include/users/validityInfo.zul", urls, null);
		
		disableUserAs = true;
	}
	
	@Command
	@NotifyChange({ "selectedValidity", "disableUserAs" })
	public void onChangeValidityBranch()
	{
		if(selectedValidity.getTypeC().getCode().equals("A"))
		{
			if(selectedValidity.getBranch() != null)
			{
				disableUserAs = false;
				selectedValidity.setLstUserAs(null);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onConfirmDelete() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDelete();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onDelete()
	{
		Ebean.delete(selectedUser);
		Ebean.delete(selectedUser.getUserValidities());
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" deleted User --> ");
		strBuilder.append(selectedUser.toString());
		Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(),
				new Date());
		
		Clients.showNotification("User: " + selectedUser.getUsername() + " has been deleted");

		lst.remove(selectedUser);
	}
	
	@Command
	@NotifyChange({ "selectedTabIndex" })
	public void onSelectTab(@BindingParam("tab") Tabpanel tab)
	{	 
		String url = "";
		switch (selectedTabIndex) {
		case TAB_VALIDITY:
			url = "/security/include/users/userValidity.zul";
			break;
			
		case TAB_USER_ROLE:
			url = "/security/include/users/userRole.zul";
			break;
		
		case TAB_USER_FUNCTION:
			url = "/security/include/users/userFunction.zul";
			break;
		}
		
		boolean wait = tab.getChildren().isEmpty();
		if (wait) {
			Component comp = Executions.createComponents(url, urls, null);
			tab.appendChild(comp);
		}
	}
	
	@Command
	@NotifyChange({ "visible" })
	public void onCloseDetail()
	{
		visible = false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onConfirmReset() {
		Messagebox.show("Are you sure you want to reset password for this user?",
				"Reset Password Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							try {
								onResetPwd();
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
					}
				});
	}

	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onResetPwd() throws Throwable
	{
		//If the user is not in SYS_EMPLOYEE
		String tmpP = "123";
		
		if(!(selectedUser.getEmp_id() == null))
		{
			tmpP = new SimpleDateFormat("ddMMyy").format(selectedUser.getEmp().getBirth_date()).toString();
		}
		
		selectedUser.setPwd(Security.encPwd(tmpP));
		selectedUser.setSecurity_no(0);
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" reset password User --> ");
		strBuilder.append(selectedUser.toString());
		Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(),
				new Date());
		
		Clients.showNotification("User: " + selectedUser.getUsername() + "'s password has been reseted");
		
		Ebean.save(selectedUser);
	}
	
	@Command
	@NotifyChange({ "syncUserlst", "visibleSync" })
	public void onSyncUser()
	{
		syncUserlst = new ListModelList<>(SecurityFacade.getSyncUserList());
		System.out.println("Size: " + syncUserlst.size());
		if(syncUserlst.size() == 0)
		{
			Clients.showNotification("No new user from Flexcube.");
		}
		else
		{
			visibleSync = true;
			
			Boolean wait = urls.hasFellow("syncUsers");
			
			if(wait)
				return;

			Executions.createComponents("/security/include/users/syncUser.zul", urls, null);
		}
	}
	
	@Command
	@NotifyChange({ "visibleSync" })
	public void onCloseSync()
	{
		visibleSync = false;
	}
	
	@Command
	@NotifyChange({ "lst", "visibleSync" })
	public void onSync()
	{	
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" Sync User(s) from Flexcube --> ");
		strBuilder.append(syncUserlst.size() + "users");
		Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(),
				new Date());
		
		Clients.showNotification("User: " + syncUserlst.size() + " have been sync");
		
		lst.addAll(syncUserlst);
		
		/*Implement Role for user
		*
		*
		*/
		
		Ebean.save(syncUserlst);
		
		visibleSync = false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onConfirmSync() {
		Messagebox.show("Are you sure you want to sync this user's password with Flexcube?",
				"Sync Password Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							try {
								onSyncPwd();
							} catch (Throwable e) {
								e.printStackTrace();
							}
						}
					}
				});
	}
	
	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onSyncPwd() throws Throwable
	{
		if (SecurityFacade.getSyncUserExisted(selectedUser.getUsername()) == 0) {
			Clients.showNotification("User: " + selectedUser.getUsername() + " is not existed in Flexcube.");
		}
		else
		{
			String tmpP = SecurityFacade.getSyncUserPwd(selectedUser.getUsername());
			
			selectedUser.setPwd(tmpP);
			selectedUser.setSecurity_no(-1);
					
			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" Sync password User with Flexcube --> ");
			strBuilder.append(selectedUser.toString());
			Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(), new Date());
					
			Clients.showNotification("User: " + selectedUser.getUsername() + "'s password has been sync with Flexcube.");

			Ebean.save(selectedUser);
		}
	}
	
//region function for move from listbox -> listbox

	@Command
	@NotifyChange({ "selectedUser", "selectedUserRoleA" })
	public void leftToRightR()
	{		
		selectedUser.getAvaUserRole().remove(selectedUserRoleA);
		selectedUserRoleA.setUser(selectedUser);
		selectedUserRoleA.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		selectedUserRoleA.setCreate_on(new Date());
		selectedUser.getUserRole().add(selectedUserRoleA);		
	}
	
	@Command
	@NotifyChange({ "selectedUser", "selectedUserRoleO" })
	public void rightToLeftR()
	{
		selectedUser.getUserRole().remove(selectedUserRoleO);

		//Just remove it from List userRole of selectedUser for some reason dont work, so delete it manually
		if(!(selectedUserRoleO.getId() == null || selectedUserRoleO.getId() == 0))
			Ebean.delete(selectedUserRoleO);
		
		selectedUserRoleO.setCreate_by("");
		selectedUserRoleO.setCreate_on(null);
			
		selectedUser.getAvaUserRole().add(selectedUserRoleO);
	}
	
	@Command
	@NotifyChange({ "selectedUser" })
	public void allRightToLeftR()
	{
		for(int i = 0; i < selectedUser.getUserRole().size(); i++)
		{
			//Just remove it from List userRole of selectedUser for some reason dont work, so delete it manually
			if(!(selectedUser.getUserRole().get(i).getId() == null || selectedUser.getUserRole().get(i).getId() == 0))
				Ebean.delete(selectedUser.getUserRole().get(i));
				
			selectedUser.getUserRole().get(i).setCreate_by("");
			selectedUser.getUserRole().get(i).setCreate_on(null);
		}
		selectedUser.getAvaUserRole().addAll(selectedUser.getUserRole());		
		selectedUser.getUserRole().clear();
	}
	
	@Command
	@NotifyChange({ "selectedUser" })
	public void allLeftToRightR()
	{
		for(int i = 0; i < selectedUser.getAvaUserRole().size(); i++)
		{
			selectedUser.getAvaUserRole().get(i).setUser(selectedUser);
			selectedUser.getAvaUserRole().get(i).setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedUser.getAvaUserRole().get(i).setCreate_on(new Date());
		}
		selectedUser.getUserRole().addAll(selectedUser.getAvaUserRole());
		selectedUser.getAvaUserRole().clear();
	}
	
	
//endregion function for move from listbox -> listbox
	
//region UserFunction
	
	@Command
	@NotifyChange({ "selectedUser" })
	public void onAddFunction()
	{
		UserFunction uf = new UserFunction();
		uf.setFunction(selectedFunction);
		
		if(selectedFunction.getRight().contains("V"))
			uf.setCheckedView(true);
		
		uf.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		uf.setCreate_on(new Date());
		
		for(int i=0; i < selectedUser.getUserFunction().size(); i++)
		{
			if(selectedUser.getUserFunction().get(i).getFunction().getName().equals(selectedFunction.getName()))
			{
				return;
			}
		}
		
		selectedUser.getUserFunction().add(uf);	
	}
	
	@Command
	@NotifyChange("selectedUser")
	public void onDeleteFunction()
	{
		selectedUser.getUserFunction().remove(selectedUserFunction);
		if(selectedUserFunction.getId() != null)
		{
			Ebean.delete(selectedUserFunction);
		}
	}
	
	public void saveFunction()
	{
		for(int i = 0; i < selectedUser.getUserFunction().size(); i++)
		{
			
			if(selectedUser.getUserFunction().get(i).getRight().trim().equals("") || selectedUser.getUserFunction().get(i).getRight() == null)
			{
				help = false;
				Clients.showNotification("You need to at least select one option for function: " +
						selectedUser.getUserFunction().get(i).getFunction().getName(), "error", null, "middle_center", -1);
				return;
			}
			
			help = true;
			
			if(selectedUser.getUserFunction().get(i).getId() != null)
			{
				if(!selectedUser.getUserFunction().get(i).getRight().equals(selectedUser.getUserFunction().get(i).getOriginal_right()))
				{
					selectedUser.getUserFunction().get(i).setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
					selectedUser.getUserFunction().get(i).setChange_on(new Date());
				}
			}
		}
	}
	
//endregion UserFunction
	
//region Check All
	
	@Command
	@NotifyChange({ "selectedUser", "selectedUserFunction" })
	public void checkAll(@BindingParam("type") int type, @BindingParam("checked") Boolean checked)
	{
		//1 = New
		//2 = View
		//3 = Copy
		//4 = Update
		//5 = Delete
		
		if(type == 1)
		{
			for(int i = 0; i < selectedUser.getUserFunction().size(); i++)
			{
				if(!selectedUser.getUserFunction().get(i).isDisabledNew())
					selectedUser.getUserFunction().get(i).setCheckedNew(checked);
			}
			return;
		}
		
		if(type == 2)
		{
			for(int i = 0; i < selectedUser.getUserFunction().size(); i++)
			{
				if(!selectedUser.getUserFunction().get(i).isDisabledView())
					selectedUser.getUserFunction().get(i).setCheckedView(checked);
			}
			return;
		}
		
		if(type == 3)
		{
			for(int i = 0; i < selectedUser.getUserFunction().size(); i++)
			{
				if(!selectedUser.getUserFunction().get(i).isDisabledCopy())
					selectedUser.getUserFunction().get(i).setCheckedCopy(checked);
			}
			return;
		}
		
		if(type == 4)
		{
			for(int i = 0; i < selectedUser.getUserFunction().size(); i++)
			{
				if(!selectedUser.getUserFunction().get(i).isDisabledUpdate())
					selectedUser.getUserFunction().get(i).setCheckedUpdate(checked);
			}
			return;
		}
		
		if(type == 5)
		{
			for(int i = 0; i < selectedUser.getUserFunction().size(); i++)
			{
				if(!selectedUser.getUserFunction().get(i).isDisabledDelete())
					selectedUser.getUserFunction().get(i).setCheckedDelete(checked);
			}
			return;
		}
		
	}

//endregion Check All
	
//region Fetch Employee
	
	boolean visibleEmployee = false;
	
	@Command
	@NotifyChange({ "filterEmployees", "visibleEmployee", "paramEmp" })
	public void onShowFetchEmp()
	{
		paramEmp = new ParamEmp();
		String tmp = "";
		if(!(selectedUser.getEmp_id() == null))
		{
			tmp = selectedUser.getEmp_id().toString();
		}
		paramEmp.setFilter(tmp);
		filterEmployees = null;
		visibleEmployee = true;
		boolean wait = urls.hasFellow("winEmp");
		
		if(wait)
			return;
		
		Executions.createComponents("/security/include/users/employee.zul", urls, null);
	}
	
	@Command
	@NotifyChange({ "filterEmployees" })
	public void onSearchEmployee()
	{
		filterEmployees = null;
	}
	
	@Command
	@NotifyChange({ "filterEmployees", "paramEmp" })
	public void onResetSearchEmployee()
	{
		paramEmp = new ParamEmp();
		filterEmployees = null;
	}
	
	@Command
	@NotifyChange({ "visibleEmployee" })
	public void onCloseEmployee()
	{
		visibleEmployee = false;
	}
	
	@Command
	@NotifyChange({ "visibleEmployee", "selectedUser" })
	public void onSelectEmployee()
	{
		selectedUser.setEmp_id(selectedEmp.getEmp_id());
		selectedUser.setFull_name(selectedEmp.getFullNameEn());
		selectedUser.setUsername(selectedEmp.getFname_en().toLowerCase() + "_" + selectedEmp.getLname_en().toLowerCase());
		selectedUser.setBranch(SecurityFacade.getBranch(selectedEmp.getBranch()));
		
		visibleEmployee = false;
	}
	
//endregion Fetch Employee
	
}
