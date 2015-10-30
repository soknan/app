package kredit.web.security.vm;

import java.util.Date;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.Function;
import kredit.web.security.model.ParamRole;
import kredit.web.security.model.Role;
import kredit.web.security.model.RoleFunction;
import kredit.web.security.model.UserRole;
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

public class RoleVM {
	ListModelList<Role> lst;
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	int rowPerPage = 10;
	ListModelList<CodeItem> filterStatus;
	Privilege privilege = null;

	Role selectedRole = new Role();
	Function selectedFunction = new Function();
	RoleFunction selectedRoleFunction = new RoleFunction();
	UserRole selectedUserRoleO;
	UserRole selectedUserRoleA;
	ParamRole param = new ParamRole();
	Boolean visible = false;
	Boolean help = true;
	
	@Wire("#rols")
	private Window rols;
	
	private static final int TAB_INFO = 0;
	private static final int TAB_ROLE_USER = 2;
	private static final int TAB_ROLE_FUNCTION = 1;
	
	int selectedTabIndex;
	
//GETTER AND SETTER
	
	public ListModelList<Role> getLst() {
		if(lst == null)
		{
			lst = new ListModelList<Role>(SecurityFacade.getRolesList(param));
		}
		return lst;
	}

	public void setLst(ListModelList<Role> lst) {
		this.lst = lst;
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
	
	public Role getSelectedRole() {
		return selectedRole;
	}

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
	}

	public ParamRole getParam() {
		return param;
	}

	public void setParam(ParamRole param) {
		this.param = param;
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

	public RoleFunction getSelectedRoleFunction() {
		return selectedRoleFunction;
	}

	public void setSelectedRoleFunction(RoleFunction selectedRoleFunction) {
		this.selectedRoleFunction = selectedRoleFunction;
	}
	
	public Privilege getPrivilege() {
		if(privilege == null)
		{
			privilege = Common.getPrivilege(CMD.LIST_ROLE);
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
	@NotifyChange({ "lst", "param", "selectedRole" })
	public void onClearAll()
	{
		param = new ParamRole();
		selectedRole = new Role();
		doSearch();
	}
	
	@Command
	@NotifyChange({ "lst", "selectedFunction", "visible", "help", "selectedTabIndex" })
	public void onOpen()
	{
		visible = true;
		help = true;
		
		selectedTabIndex = TAB_INFO;
		
		Boolean wait = rols.hasFellow("roleDetail");
		
		if(wait)
			return;
		
		Executions.createComponents("/security/detail/roleDetail.zul", rols, null);
	}
	
	@Command
	@NotifyChange({ "lst", "selectedRole", "visible", "selectedUserA", "selectedUserO", "selectedFunction",
					"selectedRoleFunction", "help" })
	public void onNew()
	{
		selectedRole = new Role();
		visible = true;
		help = true;
		selectedUserRoleA = new UserRole();
		selectedUserRoleO = new UserRole();
		selectedFunction = new Function();
		selectedRoleFunction = new RoleFunction();
		
		Executions.createComponents("/security/detail/roleDetail.zul", rols, null);
	}
	
	@Command
	@NotifyChange({ "lst", "selectedRole" })
	public void onSave()
	{
		if(selectedRole.getId() == null)
		{
			selectedRole.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedRole.setCreate_on(new Date());
		}
		else
		{
			selectedRole.setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedRole.setChange_on(new Date());
		}
		
		saveFunction();
		
		int tmp = 0;
		
		boolean newR = false;
		
		//name available or not, tmp = 0 if name is not existed
		if(selectedRole.getId() == null)
		{
			tmp = SecurityFacade.duplicated(selectedRole.getName(), 2);
			newR = true;
		}
				
		
		//help is false when there are UserFunction that right is null
		if(help && tmp == 0)
		{
			Ebean.save(selectedRole);
			Clients.showNotification("Role: " + selectedRole.getName() + " has been saved");
			if(newR)
			{
				StringBuilder strBuilder = Common.createLogStringBuilder();
				strBuilder.append(" saved Role --> ");
				strBuilder.append(selectedRole.toString());
				Common.addSessionLogCommit(CMD.LIST_ROLE, strBuilder.toString(),
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
							onDelete();
						}
					}
				});
	}
	
	@Command
	@NotifyChange({ "lst", "selectedRole" })
	public void onDelete()
	{
		Ebean.delete(selectedRole);
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" deleted Role --> ");
		strBuilder.append(selectedRole.toString());
		Common.addSessionLogCommit(CMD.LIST_ROLE, strBuilder.toString(),
				new Date());
		
		Clients.showNotification("Role: " + selectedRole.getName() + " has been deleted");
		
		lst.remove(selectedRole);
	}
	
	@Command
	@NotifyChange({ "selectedTabIndex" })
	public void onSelectTab(@BindingParam("tab") Tabpanel tab)
	{	 
		String url = "";
		switch (selectedTabIndex) {
		case TAB_ROLE_USER:
			url = "/security/include/roles/roleUser.zul";
			break;
		
		case TAB_ROLE_FUNCTION:
			url = "/security/include/roles/roleFunction.zul";
			break;
		}
		
		boolean wait = tab.getChildren().isEmpty();
		if (wait) {
			Component comp = Executions.createComponents(url, rols, null);
			tab.appendChild(comp);
		}
	}
	
	@Command
	@NotifyChange({ "visible" })
	public void onCloseDetail()
	{
		visible = false;
	}
	
//region function for move from listbox -> listbox

	@Command
	@NotifyChange({ "selectedRole", "selectedUserRoleA" })
	public void leftToRightU()
	{
		selectedRole.getAvaUserRole().remove(selectedUserRoleA);
		
		selectedUserRoleA.setRole(selectedRole);
		selectedUserRoleA.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		selectedUserRoleA.setCreate_on(new Date());
		
		selectedRole.getUserRole().add(selectedUserRoleA);
	}
		
	@Command
	@NotifyChange({ "selectedRole", "selectedUserRoleO" })
	public void rightToLeftU()
	{
		selectedRole.getUserRole().remove(selectedUserRoleO);
		
		//Just remove it from List userRole of selectedUser for some reason dont work, so delete it manually
		if(!(selectedUserRoleO.getId() == null || selectedUserRoleO.getId() == 0))
			Ebean.delete(selectedUserRoleO);
					
		selectedUserRoleO.setCreate_by("");
		selectedUserRoleO.setCreate_on(null);
		
		selectedRole.getAvaUserRole().add(selectedUserRoleO);
	}
	
	@Command
	@NotifyChange({ "selectedRole" })
	public void allRightToLeftU()
	{
		for(int i = 0; i < selectedRole.getUserRole().size(); i++)
		{
			//Just remove it from List userRole of selectedUser for some reason dont work, so delete it manually
			if(!(selectedRole.getUserRole().get(i).getId() == null || selectedRole.getUserRole().get(i).getId() == 0))
				Ebean.delete(selectedRole.getUserRole().get(i));
				
			selectedRole.getUserRole().get(i).setCreate_by("");
			selectedRole.getUserRole().get(i).setCreate_on(null);
		}
		
		selectedRole.getAvaUserRole().addAll(selectedRole.getUserRole());
		selectedRole.getUserRole().clear();
	}
		
	@Command
	@NotifyChange({ "selectedRole" })
	public void allLeftToRightU()
	{
		for(int i = 0; i < selectedRole.getAvaUserRole().size(); i++)
		{
			selectedRole.getAvaUserRole().get(i).setRole(selectedRole);;
			selectedRole.getAvaUserRole().get(i).setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedRole.getAvaUserRole().get(i).setCreate_on(new Date());;
		}
		
		selectedRole.getUserRole().addAll(selectedRole.getAvaUserRole());
		selectedRole.getAvaUserRole().clear();
	}
		
//endregion function for move from listbox -> listbox
	
//region RoleFunction
	
	@Command
	@NotifyChange({ "selectedRole" })
	public void onAddFunction()
	{
		RoleFunction rf = new RoleFunction();
		rf.setFunction(selectedFunction);
		
		if(selectedFunction.getRight().contains("V"))
			rf.setCheckedView(true);
		
		rf.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		rf.setCreate_on(new Date());
		
		for(int i=0; i < selectedRole.getRoleFunction().size(); i++)
		{
			if(selectedRole.getRoleFunction().get(i).getFunction().getName().equals(selectedFunction.getName()))
			{
				return;
			}
		}
		
		selectedRole.getRoleFunction().add(rf);	
	}
		
	@Command
	@NotifyChange("selectedRole")
	public void onDeleteFunction()
	{
		selectedRole.getRoleFunction().remove(selectedRoleFunction);
		if(selectedRoleFunction.getId() != null)
		{			
			Ebean.delete(selectedRoleFunction);
		}
	}
	
	public void saveFunction()
	{
		for(int i = 0; i < selectedRole.getRoleFunction().size(); i++)
		{
			if(selectedRole.getRoleFunction().get(i).getRight().trim().equals("") || selectedRole.getRoleFunction().get(i).getRight() == null)
			{
				help = false;
				Clients.showNotification("You need to at least select one option for function: " +
						selectedRole.getRoleFunction().get(i).getFunction().getName(), "error", null, "middle_center", -1);
				return;
			}
			
			help = true;
			
			if(selectedRole.getRoleFunction().get(i).getId() != null)
			{
				if(!selectedRole.getRoleFunction().get(i).getRight().equals(selectedRole.getRoleFunction().get(i).getOriginal_right()))
				{
					selectedRole.getRoleFunction().get(i).setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
					selectedRole.getRoleFunction().get(i).setChange_on(new Date());
				}
			}
		}
	}
	
//endregion RoleFunction
	
	@Command
	@NotifyChange({ "selectedRole", "selectedRoleFunction" })
	public void checkAll(@BindingParam("type") int type, @BindingParam("checked") Boolean checked)
	{
		//1 = New
		//2 = View
		//3 = Copy
		//4 = Update
		//5 = Delete
		
		if(type == 1)
		{
			for(int i = 0; i < selectedRole.getRoleFunction().size(); i++)
			{
				if(!selectedRole.getRoleFunction().get(i).isDisabledNew())
					selectedRole.getRoleFunction().get(i).setCheckedNew(checked);
			}
			return;
		}
		
		if(type == 2)
		{
			for(int i = 0; i < selectedRole.getRoleFunction().size(); i++)
			{
				if(!selectedRole.getRoleFunction().get(i).isDisabledView())
					selectedRole.getRoleFunction().get(i).setCheckedView(checked);
			}
			return;
		}
		
		if(type == 3)
		{
			for(int i = 0; i < selectedRole.getRoleFunction().size(); i++)
			{
				if(!selectedRole.getRoleFunction().get(i).isDisabledCopy())
					selectedRole.getRoleFunction().get(i).setCheckedCopy(checked);
			}
			return;
		}
		
		if(type == 4)
		{
			for(int i = 0; i < selectedRole.getRoleFunction().size(); i++)
			{
				if(!selectedRole.getRoleFunction().get(i).isDisabledUpdate())
					selectedRole.getRoleFunction().get(i).setCheckedUpdate(checked);
			}
			return;
		}
		
		if(type == 5)
		{
			for(int i = 0; i < selectedRole.getRoleFunction().size(); i++)
			{
				if(!selectedRole.getRoleFunction().get(i).isDisabledDelete())
					selectedRole.getRoleFunction().get(i).setCheckedDelete(checked);
			}
			return;
		}
		
	}
}
