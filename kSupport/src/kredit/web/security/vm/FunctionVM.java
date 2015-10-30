package kredit.web.security.vm;

import java.util.Date;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.Function;
import kredit.web.security.model.ParamFunction;
import kredit.web.security.model.Role;
import kredit.web.security.model.RoleFunction;
import kredit.web.security.model.User;
import kredit.web.security.model.UserFunction;
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

public class FunctionVM {
	ListModelList<Function> lst;
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	int rowPerPage = 10;
	Privilege privilege = null;
	ListModelList<CodeItem> filterStatus;
	ListModelList<CodeItem> filterFunctions;
	ListModelList<CodeItem> lstAll;
	ListModelList<CodeItem> lstAva;		//lst of Available Right for the function

	Function selectedFunction = new Function();
	ParamFunction param = new ParamFunction();
	Boolean visible = false;
	
	CodeItem selectedAll = new CodeItem();
	CodeItem selectedAva = new CodeItem();
	
	User selectedUser = new User();
	UserFunction selectedUserFunction = new UserFunction();
	Role selectedRole = new Role();
	RoleFunction selectedRoleFunction = new RoleFunction();
	
	Boolean helpU = true;
	Boolean helpR = true;
	
	@Wire("#fnls")
	private Window funs;
	
	private static final int TAB_INFO = 0;
	private static final int TAB_FUNCTION_ROLE = 1;
	private static final int TAB_FUNCTION_USER = 2;
	
	int selectedTabIndex;
	
//GETTER AND SETTER
	
	public ListModelList<Function> getLst() {
		if(lst == null)
		{
			lst = new ListModelList<Function>(SecurityFacade.getFunctionsList(param));
		}
		return lst;
	}

	public void setLst(ListModelList<Function> lst) {
		this.lst = lst;
	}
	
	public ListModelList<CodeItem> getLstAll() {
		if(lstAll == null)
		{
			lstAll = new ListModelList<CodeItem>(SecurityFacade.getRightAllList(Common.SYS_LOV_RIGHT, selectedFunction.getRight()));
		}
		return lstAll;
	}

	public void setLstAll(ListModelList<CodeItem> lstAll) {
		this.lstAll = lstAll;
	}

	public ListModelList<CodeItem> getLstAva() {
		if(lstAva == null)
		{
			lstAva = new ListModelList<CodeItem>(SecurityFacade.getRightList(Common.SYS_LOV_RIGHT, selectedFunction.getRight()));
		}
		return lstAva;
	}

	public void setLstAva(ListModelList<CodeItem> lstAva) {
		this.lstAva = lstAva;
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
	
	public Boolean getVisible() {
		return visible;
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public ListModelList<CodeItem> getFilterStatus() {
		if(filterStatus == null)
		{
			filterStatus = new ListModelList<CodeItem>(SecurityFacade.getSysLovItem(Common.SYS_LOV_STATUS));
		}
		return filterStatus;
	}

	public void setFilterStatus(ListModelList<CodeItem> filterStatus) {
		this.filterStatus = filterStatus;
	}
	
	public Function getSelectedFunction() {
		return selectedFunction;
	}

	public void setSelectedFunction(Function selectedFunction) {
		this.selectedFunction = selectedFunction;
	}

	public ParamFunction getParam() {
		return param;
	}

	public void setParam(ParamFunction param) {
		this.param = param;
	}
	
	public ListModelList<CodeItem> getFilterFunctions() {
		if(filterFunctions == null)
		{
			filterFunctions = new ListModelList<CodeItem>(SecurityFacade.getSysLovItem(Common.SYS_LOV_FUNCTION));
		}
		return filterFunctions;
	}

	public void setFilterFunctions(ListModelList<CodeItem> filterFunctions) {
		this.filterFunctions = filterFunctions;
	}
	
	public CodeItem getSelectedAll() {
		return selectedAll;
	}

	public void setSelectedAll(CodeItem selectedAll) {
		this.selectedAll = selectedAll;
	}

	public CodeItem getSelectedAva() {
		return selectedAva;
	}

	public void setSelectedAva(CodeItem selectedAva) {
		this.selectedAva = selectedAva;
	}
	
	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	public UserFunction getSelectedUserFunction() {
		return selectedUserFunction;
	}

	public void setSelectedUserFunction(UserFunction selectedUserFunction) {
		this.selectedUserFunction = selectedUserFunction;
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public RoleFunction getSelectedRoleFunction() {
		return selectedRoleFunction;
	}

	public void setSelectedRoleFunction(RoleFunction selectedRoleFunction) {
		this.selectedRoleFunction = selectedRoleFunction;
	}
	
	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
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
	@NotifyChange({ "lst", "param", "selectedFunction" })
	public void onClearAll()
	{
		param = new ParamFunction();
		selectedFunction = new Function();
		doSearch();
	}

	@Command
	@NotifyChange({ "lst", "selectedFunction", "visible", "lstAll", "lstAva", "helpR", "helpU", "selectedTabIndex" })
	public void onOpen()
	{
		visible = true;
		lstAll = null;
		lstAva = null;
		helpR = true;
		helpU = true;
		
		selectedTabIndex = TAB_INFO;
		
		Boolean wait = funs.hasFellow("functionDetail");
		
		if(wait)
			return;
		
		Executions.createComponents("/security/detail/functionDetail.zul", funs, null);
	}
	
	@Command
	@NotifyChange({ "lst", "selectedFunction", "visible", "lstAll", "lstAva", "selectedRole", "selectedRoleFunction", 
					"selectedUser", "selectedUserFunction", "helpR", "helpU" })
	public void onNew()
	{
		selectedFunction = new Function();
		visible = true;
		lstAll = null;
		lstAva = null;
		selectedRole = new Role();
		selectedRoleFunction = new RoleFunction();
		selectedUser = new User();
		selectedUserFunction = new UserFunction();
		helpR = true;
		helpU = true;
		
		Executions.createComponents("/security/detail/functionDetail.zul", funs, null);
	}
	
	@Command
	@NotifyChange({ "lst", "selectedFunction" })
	public void onSave()
	{
		if(selectedFunction.getId() == null)
		{
			selectedFunction.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedFunction.setCreate_on(new Date());
		}
		else
		{
			selectedFunction.setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedFunction.setChange_on(new Date());
		}
		
		saveUser();
		saveRole();
		selectedFunction.setRight(Common.listToString(lstAva));
		
		int tmp = 0;
		
		boolean newF = false;
		
		//name available or not, tmp = 0 if name is not existed
		if(selectedFunction.getId() == null)
		{
			tmp = SecurityFacade.duplicated(selectedFunction.getName(), 3);
			newF = true;
		}
		
		//helpR is false when there are RoleFunction that right is null
		//helpU is false when there are UserFunction that right is null
		if(helpR && helpU && tmp == 0)
		{
			Ebean.save(selectedFunction);
			Clients.showNotification("Function: " + selectedFunction.getName() + " has been saved");
			if(newF)
			{
				StringBuilder strBuilder = Common.createLogStringBuilder();
				strBuilder.append(" saved Function --> ");
				strBuilder.append(selectedFunction.toString());
				Common.addSessionLogCommit(CMD.LIST_FUNCTION, strBuilder.toString(),
						new Date());
			}
		}
			
		
		if(tmp != 0)
			Clients.showNotification("This name is already existed.");
		
		lst = null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedRole" })
	public void onConfirmDelete() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeleteFunction();
						}
					}
				});
	}
	
	@Command
	@NotifyChange({ "lst", "selectedFunction" })
	public void onDeleteFunction()
	{
		Ebean.delete(selectedFunction);
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" deleted Function --> ");
		strBuilder.append(selectedFunction.toString());
		Common.addSessionLogCommit(CMD.LIST_FUNCTION, strBuilder.toString(),
				new Date());
		
		Clients.showNotification("Function: " + selectedFunction.getName() + " has been deleted");
		
		lst.remove(selectedFunction);
	}
	
	@Command
	@NotifyChange({ "selectedTabIndex" })
	public void onSelectTab(@BindingParam("tab") Tabpanel tab)
	{	 
		String url = "";
		switch (selectedTabIndex) {
		case TAB_FUNCTION_ROLE:
			url = "/security/include/functions/functionRole.zul";
			break;
		
		case TAB_FUNCTION_USER:
			url = "/security/include/functions/functionUser.zul";
			break;
		}
		
		boolean wait = tab.getChildren().isEmpty();
		if (wait) {
			Component comp = Executions.createComponents(url, funs, null);
			tab.appendChild(comp);
		}
	}
	
	@Command
	@NotifyChange({ "visible" })
	public void onCloseDetail()
	{
		visible = false;
	}
	
	//region list
	
	@Command
	@NotifyChange({ "lstAll", "lstAva", "selectedAll" })
	public void leftToRight()
	{
		lstAll.remove(selectedAll);
		lstAva.add(selectedAll);
	}
	
	@Command
	@NotifyChange({ "lstAll", "lstAva", "selectedAva" })
	public void rightToLeft()
	{
		lstAva.remove(selectedAva);
		lstAll.add(selectedAva);
	}
	
	@Command
	@NotifyChange({ "lstAll", "lstAva" })
	public void allLeftToRight()
	{
		lstAva.addAll(lstAll);
		lstAll.clear();
	}
	
	@Command
	@NotifyChange({ "lstAll", "lstAva" })
	public void allRightToLeft()
	{
		lstAll.addAll(lstAva);
		lstAva.clear();
	}
	
	public Privilege getPrivilege() {
		if(privilege == null)
		{
			privilege = Common.getPrivilege(CMD.LIST_FUNCTION);
		}
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
//region UserFunction

	@Command
	@NotifyChange({ "selectedFunction" })
	public void onAddUser()
	{
		UserFunction uf = new UserFunction();
		uf.setUser(selectedUser);
		uf.setFunction(selectedFunction);
		
		if(selectedFunction.getRight().contains("V"))
			uf.setCheckedView(true);
		
		uf.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		uf.setCreate_on(new Date());
		
		for(int i=0; i < selectedFunction.getUserFunction().size(); i++)
		{
			if(selectedFunction.getUserFunction().get(i).getUser().getId() == selectedUser.getId())
			{
				return;
			}
		}
		
		selectedFunction.getUserFunction().add(uf);	
	}
	
	@Command
	@NotifyChange("selectedFunction")
	public void onDeleteUser()
	{
		selectedFunction.getUserFunction().remove(selectedUserFunction);
		if(selectedUserFunction.getId() != null)
		{
			Ebean.delete(selectedUserFunction);
		}
	}
	
	public void saveUser()
	{
		for(int i = 0; i < selectedFunction.getUserFunction().size(); i++)
		{
			if(selectedFunction.getUserFunction().get(i).getRight().trim().equals("") || selectedFunction.getUserFunction().get(i).getRight() == null)
			{
				helpU = false;
				Clients.showNotification("You need to at least select one option for user: " +
						selectedFunction.getUserFunction().get(i).getUser().getUsername(), "error", null, "middle_center", -1);
				return;
			}
			
			helpU = true;
			
			if(selectedFunction.getUserFunction().get(i).getId() != null)
			{
				if(!selectedFunction.getUserFunction().get(i).getRight().equals(selectedFunction.getUserFunction().get(i).getOriginal_right()))
				{
					selectedFunction.getUserFunction().get(i).setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
					selectedFunction.getUserFunction().get(i).setChange_on(new Date());
				}
			}
		}
	}
	
//endregion UserFunction
	
//region RoleFunction
	
	@Command
	@NotifyChange({ "selectedFunction" })
	public void onAddRole()
	{
		RoleFunction rf = new RoleFunction();
		rf.setRole(selectedRole);
		rf.setFunction(selectedFunction);
		rf.setCheckedView(true);
		
		rf.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		rf.setCreate_on(new Date());
		boolean hasItem = false;
		
		for(int i=0; i < selectedFunction.getRoleFunction().size(); i++)
		{
			if(selectedFunction.getRoleFunction().get(i).getRole().getId() == selectedRole.getId())
			{
				hasItem = true;
				break;
			}
		}
		
		if(!hasItem)
		{
			selectedFunction.getRoleFunction().add(rf);	
		}
		
	}
	
	@Command
	@NotifyChange("selectedFunction")
	public void onDeleteRole()
	{
		selectedRole.getRoleFunction().remove(selectedRoleFunction);
		if(selectedRoleFunction.getId() != null)
		{
			Ebean.delete(selectedRoleFunction);
		}
	}
	
	public void saveRole()
	{
		for(int i = 0; i < selectedFunction.getRoleFunction().size(); i++)
		{
			if(selectedFunction.getRoleFunction().get(i).getRight().trim().equals("") || selectedFunction.getRoleFunction().get(i).getRight() == null)
			{
				helpU = false;
				Clients.showNotification("You need to at least select one option for role: " +
						selectedFunction.getRoleFunction().get(i).getRole().getName(), "error", null, "middle_center", -1);
				return;
			}
			
			helpU = true;
			
			if(selectedFunction.getRoleFunction().get(i).getId() != null)
			{
				if(!selectedFunction.getRoleFunction().get(i).getRight().equals(selectedFunction.getRoleFunction().get(i).getOriginal_right()))
				{
					selectedFunction.getRoleFunction().get(i).setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
					selectedFunction.getRoleFunction().get(i).setChange_on(new Date());
				}
			}
		}
	}
	
//endregion RoleFunction
	
//region Check All
	
	@Command
	@NotifyChange({ "selectedFunction", "selectedUserFunction" })
	public void checkAll(@BindingParam("type") int type, @BindingParam("checked") Boolean checked)
	{
		//1 = New
		//2 = View
		//3 = Copy
		//4 = Update
		//5 = Delete
		
		if(type == 1)
		{
			for(int i = 0; i < selectedFunction.getUserFunction().size(); i++)
			{
				if(!selectedFunction.getUserFunction().get(i).isDisabledNew())
					selectedFunction.getUserFunction().get(i).setCheckedNew(checked);
			}
			return;
		}
		
		if(type == 2)
		{
			for(int i = 0; i < selectedFunction.getUserFunction().size(); i++)
			{
				if(!selectedFunction.getUserFunction().get(i).isDisabledView())
					selectedFunction.getUserFunction().get(i).setCheckedView(checked);
			}
			return;
		}
		
		if(type == 3)
		{
			for(int i = 0; i < selectedFunction.getUserFunction().size(); i++)
			{
				if(!selectedFunction.getUserFunction().get(i).isDisabledCopy())
					selectedFunction.getUserFunction().get(i).setCheckedCopy(checked);
			}
			return;
		}
		
		if(type == 4)
		{
			for(int i = 0; i < selectedFunction.getUserFunction().size(); i++)
			{
				if(!selectedFunction.getUserFunction().get(i).isDisabledUpdate())
					selectedFunction.getUserFunction().get(i).setCheckedUpdate(checked);
			}
			return;
		}
		
		if(type == 5)
		{
			for(int i = 0; i < selectedFunction.getUserFunction().size(); i++)
			{
				if(!selectedFunction.getUserFunction().get(i).isDisabledDelete())
					selectedFunction.getUserFunction().get(i).setCheckedDelete(checked);
			}
			return;
		}
	}
	
	@Command
	@NotifyChange({ "selectedFunction", "selectedRoleFunction" })
	public void checkAllR(@BindingParam("type") int type, @BindingParam("checked") Boolean checked)
	{
		//1 = New
		//2 = View
		//3 = Copy
		//4 = Update
		//5 = Delete
		
		if(type == 1)
		{
			for(int i = 0; i < selectedFunction.getRoleFunction().size(); i++)
			{
				if(!selectedFunction.getRoleFunction().get(i).isDisabledNew())
					selectedFunction.getRoleFunction().get(i).setCheckedNew(checked);
			}
			return;
		}
		
		if(type == 2)
		{
			for(int i = 0; i < selectedFunction.getRoleFunction().size(); i++)
			{
				if(!selectedFunction.getRoleFunction().get(i).isDisabledView())
					selectedFunction.getRoleFunction().get(i).setCheckedView(checked);
			}
			return;
		}
		
		if(type == 3)
		{
			for(int i = 0; i < selectedFunction.getRoleFunction().size(); i++)
			{
				if(!selectedFunction.getRoleFunction().get(i).isDisabledCopy())
					selectedFunction.getRoleFunction().get(i).setCheckedCopy(checked);
			}
			return;
		}
		
		if(type == 4)
		{
			for(int i = 0; i < selectedFunction.getRoleFunction().size(); i++)
			{
				if(!selectedFunction.getRoleFunction().get(i).isDisabledUpdate())
					selectedFunction.getRoleFunction().get(i).setCheckedUpdate(checked);
			}
			return;
		}
		
		if(type == 5)
		{
			for(int i = 0; i < selectedFunction.getRoleFunction().size(); i++)
			{
				if(!selectedFunction.getRoleFunction().get(i).isDisabledDelete())
					selectedFunction.getRoleFunction().get(i).setCheckedDelete(checked);
			}
			return;
		}
	}

//endregion Check All
}
