package kredit.web.main.vm;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import kredit.web.core.model.Employee;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.authentication.model.facade.UserCredentialFacade;
import kredit.web.core.util.model.CodeItem;
import kredit.web.main.model.facade.MainFacade;
import kredit.web.security.model.Function;
import kredit.web.security.model.facade.SecurityFacade;

import org.k.model.User;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.oracle.xmlns.oxp.service.v2.AccessDeniedException;

public class MainVM {
	@SuppressWarnings("rawtypes")
	private final class EventListenerImplementation implements
			org.zkoss.zk.ui.event.EventListener {
		public void onEvent(Event evt) throws InterruptedException {
			if (evt.getName().equals("onOK")) {
				UserCredentialManager.getIntance().logout();
				Executions.sendRedirect("signin.zul");
			}
		}
	}

	String cmd = "";

	@Wire("#main")
	Div main;

	Menupopup openedWindows;

	String autologin = "";
	
	boolean visibleProfile;
	
	Employee emp;

	ListModelList<Menuitem> lstMenuItem = new ListModelList<Menuitem>();
	static final String MENU_ITEM_PREFIX = "mItem";

	@Init
	public void init() {
		renderSidebarMenu();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange("lstMenuItem")
	public void onCmd() {

		String path = MainFacade.getWinPath(cmd);
		if (path.isEmpty()) {
			Clients.showNotification("Unknown command! Please try another one.");
			return;
		}

		if (main.hasFellow(cmd)) {
			Window win = (Window) main.getFellow(cmd, false);
			win.setFocus(true);
			return;
		}
		Window comp = (Window) Executions.createComponents(path, main, null);

		main.appendChild(comp);

		Menuitem menuitem = new Menuitem();
		menuitem.setId(MENU_ITEM_PREFIX + cmd);
		menuitem.setLabel(comp.getTitle());

		lstMenuItem.add(menuitem);
	}

	@GlobalCommand
	@NotifyChange({ "lstMenuItem", "cmd" })
	public void onCmd(@BindingParam("cmd") String cmd,
			@BindingParam("obj") Object obj) {
		this.cmd = cmd;
		String path = MainFacade.getWinPath(cmd);
		if (path.isEmpty()) {
			Clients.showNotification("Unknown command! Please try another one.");
			return;
		}

		if (main.hasFellow(cmd)) {
			Window win = (Window) main.getFellow(cmd, false);
			win.setFocus(true);
			return;
		}
		// Executions.getCurrent().setAttribute("obj", obj);

		Map<String, Object> args = null;
		if (obj != null) {
			args = new HashMap<String, Object>();
			args.put("obj", obj);
		}

		Window comp = (Window) Executions.createComponents(path, main, args);

		main.appendChild(comp);

		Menuitem menuitem = new Menuitem();
		menuitem.setId(MENU_ITEM_PREFIX + cmd);
		menuitem.setLabel(comp.getTitle());

		lstMenuItem.add(menuitem);
	}

	@GlobalCommand
	@NotifyChange("lstMenuItem")
	public void onCloseWin(@BindingParam("id") Window win,
			@BindingParam("postGlobal") String postGlobal) {
		if (postGlobal != null) {
			BindUtils.postGlobalCommand(null, null, postGlobal, null);
		}
		for (Menuitem item : lstMenuItem) {
			if (item.getId().equals(MENU_ITEM_PREFIX + win.getId())) {
				lstMenuItem.remove(item);
				break;
			}
		}
	}

	@Command
	public void onMenuOpenedWindowClicked(@BindingParam("id") String id) {
		String winId = id.replace(MENU_ITEM_PREFIX, "");
		Window win = (Window) main.getFellow(winId, false);
		win.setFocus(true);
	}

	/**
	 * @return the shortcutCmd
	 */
	public String getCmd() {
		return cmd;
	}

	/**
	 * @param shortcutCmd
	 *            the shortcutCmd to set
	 */
	public void setCmd(String shortcutCmd) {
		this.cmd = shortcutCmd;
	}

	public String getSidebarRender() {
		Map<String, CodeItem> functionsMap = UserCredentialManager.getIntance()
				.getFunctionsMap();
		if (functionsMap.size() == 0)
			return "";

		StringBuilder objStringBuilder = new StringBuilder();
		List<Function> modules = new ArrayList<Function>();
		List<Function> subModules = new ArrayList<Function>();

		Set<String> keys = functionsMap.keySet();
		modules = UserCredentialFacade.getModules(Common.arrayQuery(keys
				.toArray()));
		subModules = UserCredentialFacade.getSubModules(Common.arrayQuery(keys
				.toArray()));

		for (int i = 0; i < modules.size(); i++) {
			objStringBuilder.append("<li class=\"\">");
			objStringBuilder
					.append("    <a class=\"dropdown-toggle\" href=\"#\"><i class=\""
							+ modules.get(i).getIcon()
							+ "\"></i><span class=\"menu-text\">"
							+ modules.get(i).getName()
							+ "</span><b class=\"arrow z-icon-angle-down\"></b></a>");
			objStringBuilder
					.append("    <ul class=\"submenu\" style=\"display: block;\">");
			for (int j = 0; j < subModules.size(); j++) {
				if (subModules.get(j).getParent_code()
						.equalsIgnoreCase(modules.get(i).getCode())) {
					objStringBuilder.append("        <li>");
					objStringBuilder
							.append("            <a id=\""
									+ subModules.get(j).getCode()
									+ "\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>"
									+ subModules.get(j).getName() + "</a>");
					objStringBuilder.append("        </li>");
				}
			}
			objStringBuilder.append("    </ul>");
			objStringBuilder.append("</li>");
		}

		/*
		 * // region Matching menu
		 * 
		 * objStringBuilder.append("<li class=\"\">"); objStringBuilder .append(
		 * "    <a class=\"dropdown-toggle\" href=\"#\"><i class=\"z-icon-search k-orange\"></i><span class=\"menu-text\">Utility</span><b class=\"arrow z-icon-angle-down\"></b></a>"
		 * ); objStringBuilder
		 * .append("    <ul class=\"submenu\" style=\"display: block;\">");
		 * 
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"acls\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Search</a>"
		 * ); objStringBuilder.append("        </li>");
		 * objStringBuilder.append("    </ul>");
		 * objStringBuilder.append("</li>");
		 * 
		 * // endregion Matching menu
		 * 
		 * // region MFI menu
		 * 
		 * if (functionsMap.containsKey("cbls") ||
		 * functionsMap.containsKey("grls") || functionsMap.containsKey("mbls")
		 * || functionsMap.containsKey("mzls")) {
		 * objStringBuilder.append("<li class=\"\">"); objStringBuilder .append(
		 * "    <a class=\"dropdown-toggle\" href=\"#\"><i class=\"z-icon-leaf k-orange\"></i><span class=\"menu-text\">Microfinance</span><b class=\"arrow z-icon-angle-down\"></b></a>"
		 * ); objStringBuilder
		 * .append("    <ul class=\"submenu\" style=\"display: block;\">");
		 * 
		 * if (functionsMap.containsKey("cbls")) {
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"cbls\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>CB</a>"
		 * ); objStringBuilder.append("        </li>"); }
		 * 
		 * if (functionsMap.containsKey("grls")) {
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"grls\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Group</a>"
		 * ); objStringBuilder.append("        </li>"); }
		 * 
		 * if (functionsMap.containsKey("mbls")) {
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"mbls\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Group Members</a>"
		 * ); objStringBuilder.append("        </li>"); }
		 * 
		 * if (functionsMap.containsKey("mzls")) {
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"mzls\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Mobilizer</a>"
		 * ); objStringBuilder.append("        </li>"); }
		 * 
		 * objStringBuilder.append("    </ul>");
		 * objStringBuilder.append("</li>"); }
		 * 
		 * // endregion MFI menu
		 * 
		 * // region Security menu
		 * 
		 * if (functionsMap.containsKey("usrs") ||
		 * functionsMap.containsKey("rols") || functionsMap.containsKey("funs"))
		 * { objStringBuilder.append("<li class=\"\">"); objStringBuilder
		 * .append(
		 * "    <a class=\"dropdown-toggle\" href=\"#\"><i class=\"z-icon-gear k-orange\"></i><span class=\"menu-text\">Security Management</span><b class=\"arrow z-icon-angle-down\"></b></a>"
		 * );
		 * 
		 * objStringBuilder
		 * .append("    <ul class=\"submenu\" style=\"display: none;\">");
		 * 
		 * if (functionsMap.containsKey("usrs")) {
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"usrs\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Users</a>"
		 * ); objStringBuilder.append("        </li>"); }
		 * 
		 * if (functionsMap.containsKey("rols")) {
		 * objStringBuilder.append("		<li>"); objStringBuilder .append(
		 * "            <a id=\"rols\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Roles</a>"
		 * ); objStringBuilder.append("        </li>"); }
		 * 
		 * if (functionsMap.containsKey("funs")) {
		 * objStringBuilder.append("		<li>"); objStringBuilder .append(
		 * "            <a id=\"funs\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Functions</a>"
		 * ); objStringBuilder.append("        </li>"); }
		 * 
		 * objStringBuilder.append("    </ul>");
		 * objStringBuilder.append("</li>"); }
		 * 
		 * // endregion Security menu
		 * 
		 * /* objStringBuilder.append("<li class=\"\">"); objStringBuilder
		 * .append(
		 * "    <a class=\"dropdown-toggle\" href=\"#\"><i class=\"icon-a k-orange\"></i><span class=\"menu-text\">CBC</span><b class=\"arrow z-icon-angle-down\"></b></a>"
		 * );
		 * 
		 * objStringBuilder
		 * .append("    <ul class=\"submenu\" style=\"display: none;\">");
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"grls\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Check List</a>"
		 * ); objStringBuilder.append("        </li>");
		 * objStringBuilder.append("		<li>"); objStringBuilder .append(
		 * "            <a id=\"grip\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>History</a>"
		 * ); objStringBuilder.append("        </li>");
		 * objStringBuilder.append("    </ul>");
		 * 
		 * objStringBuilder.append("</li>");
		 * objStringBuilder.append("<li class=\"\">"); objStringBuilder .append(
		 * "    <a class=\"dropdown-toggle\" href=\"#\"><i class=\"z-icon-bell k-orange\"></i><span class=\"menu-text\">Risk Management</span><b class=\"arrow z-icon-angle-down\"></b></a>"
		 * );
		 * 
		 * objStringBuilder
		 * .append("    <ul class=\"submenu\" style=\"display: none;\">");
		 * 
		 * objStringBuilder.append("        <li>"); objStringBuilder .append(
		 * "            <a id=\"grls\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Maintenance</a>"
		 * ); objStringBuilder.append("        </li>");
		 * objStringBuilder.append("		<li>"); objStringBuilder .append(
		 * "            <a id=\"grip\" name=\"menu\" href=\"#\"><i class=\"z-icon-angle-double-right\"></i>Operation</a>"
		 * ); objStringBuilder.append("        </li>");
		 * objStringBuilder.append("    </ul>");
		 * 
		 * objStringBuilder.append("</li>");
		 */

		return objStringBuilder.toString();
	}

	@Command
	public void renderSidebarMenu() {
		Clients.evalJavaScript("$('#kMenu1').html('" + getSidebarRender()
				+ "'); bindMenuClick();");
	}

	@Command
	public void hello() {
		Clients.showNotification("Hello world!");
	}

	@Command
	@GlobalCommand
	@NotifyChange({ "cmd", "lstMenuItem" })
	public void notifyCmd(@BindingParam("cmd") String cmd) {
		this.cmd = cmd;
		onCmd();
	}

	/**
	 * @return the lstMenuItem
	 */
	public ListModelList<Menuitem> getLstMenuItem() {
		return lstMenuItem;
	}

	/**
	 * @param lstMenuItem
	 *            the lstMenuItem to set
	 */
	public void setLstMenuItem(ListModelList<Menuitem> lstMenuItem) {
		this.lstMenuItem = lstMenuItem;
	}

	@SuppressWarnings("unchecked")
	@Command
	public void logOff() {

		/*
		 * if (lstMenuItem.size() > 0) { Clients.showNotification(
		 * "Please close all open windows before logout", "warning", null,
		 * "middle_center", -1); return; }
		 */

		Messagebox.show("Are you sure you want to logout?",
				"Logout Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new EventListenerImplementation());

	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return UserCredentialManager.getIntance().getLoginUsr();
	}

	@Command
	public void onAbout() {
		Window resetWindow = (Window) Executions.createComponents(
				"/help/about.zul", null, null);
		resetWindow.setMode("modal");
		resetWindow.setPosition("center, center");
	}

	@Command
	public void onViewRpt() {
		
		Window resetWindow = (Window) Executions.createComponents(
				"/report/reportViewer.zul", main, null);
		resetWindow.setMode("modal");
		resetWindow.setPosition("center, center");
	}
	
	@Command
	public void onViewRpt2() {
		
		Window resetWindow = (Window) Executions.createComponents(
				"/report/reportViewer2.zul", main, null);
		resetWindow.setMode("modal");
		resetWindow.setPosition("center, center");
	}

	@Command
	@NotifyChange("autologin")
	public void onAuthenticateBI() throws AccessDeniedException,
			RemoteException {

		/*
		 * String sid = UserCredentialManager.getIntance().getBipSessionToken();
		 * System.out.println(sid);
		 * Clients.showNotification("Successfully Authenticated!");
		 */

		autologin = "/util/xmlp/autologin.html";
		System.out.println("BI Authenticating...");
	}

	/**
	 * @return the autologin
	 */
	public String getAutologin() {
		return autologin;
	}

	/**
	 * @param autologin
	 *            the autologin to set
	 */
	public void setAutologin(String autologin) {
		this.autologin = autologin;
	}

	public boolean isVisibleProfile() {
		return visibleProfile;
	}

	public void setVisibleProfile(boolean visibleProfile) {
		this.visibleProfile = visibleProfile;
	}
	
	@Command
	@NotifyChange({ "visibleProfile" })
	public void onShowProfile()
	{
		visibleProfile = true;
		
		if(main.hasFellow("winProfile"))
			return;
		
		Executions.createComponents("/main/profile.zul", main, null);
	}
	
	@Command
	@NotifyChange({ "visibleProfile" })
	public void onCloseProfile()
	{
		visibleProfile = false;
	}
	
	public Employee getEmp()
	{
		if(emp == null)
		{
			emp = SecurityFacade.getEmployee(getUser().getEmpID());
		}
		return emp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedUser" })
	public void onConfirmSync() {
		if(SecurityFacade.getSyncUserExisted(getUser().getUsername()) == 0) {
			Clients.showNotification("User: " + getUser().getUsername() + " is not existed in Flexcube.");
		}
		else
		{
			Messagebox.show("Are you sure you want to sync password with Flexcube?",
					"Sync Password Confirmation", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								try {
									syncPwdFlexcube();
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
						}
					});
		}
	}
	
	@Command
	@NotifyChange({ "" })
	public void syncPwdFlexcube() throws Throwable
	{
		String tmpP = SecurityFacade.getSyncUserPwd(getUser().getUsername());
				
		if(SecurityFacade.syncPwdWithFlexcube(tmpP, getUser().getUsername()) == 1)
		{
			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" Sync password User with Flexcube --> ");
			strBuilder.append(getUser().toString());
			Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(), new Date());
					
			Clients.showNotification("Your password has been sync with Flexcube. Please log out and log in with your new password.");
		}
		else
		{
				Clients.showNotification("Sync password failed.");
		}
	}
}
