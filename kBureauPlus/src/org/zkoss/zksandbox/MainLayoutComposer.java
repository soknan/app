/* MainLayoutComposer.java

{{IS_NOTE
	Purpose:
		
	description:
		
	History:
		Nov 12, 2008 3:10:06 PM , Created by jumperchen
}}IS_NOTE

Copyright (C) 2008 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 3.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
 */
package org.zkoss.zksandbox;

import java.util.List;
import java.util.Map;
import kredit.web.core.util.authentication.Priviledge;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.authentication.model.BookmarkRole;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 * @author Sovathena Neth
 * 
 */
@SuppressWarnings("rawtypes")
public class MainLayoutComposer extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	// private static final Log log = Log.lookup(MainLayoutComposer.class);

	String lastLoginDate;
	String loginName;
	Label lbLoginDate;
	Listbox itemList;

	Include xcontents;
	Div header;

	Button _selected;

	Window aboutWindow;

	private Map _bookmarkMap;

	public static String userAgent;
	public static String remoteAddr;
	public static String remoteHost;

	public MainLayoutComposer() {
		initLogProperty();
		_bookmarkMap = UserCredentialManager.getIntance().getBookmarkMap();
		setLastLoginDate(UserCredentialManager.getIntance().getLastLoginDate());
		setLoginName(UserCredentialManager.getIntance().getLogin()
				.getFullName());
	}

	/*
	 * private Map getBookmarkMap() { return
	 * UserCredentialManager.getIntance().getBookmarkMap();//_bookmarkMap;
	 * //DemoWebAppInit.getCateMap(); }
	 */

	private void initLogProperty() {
		userAgent = Executions.getCurrent().getUserAgent();
		remoteAddr = Executions.getCurrent().getRemoteAddr();
		remoteHost = Executions.getCurrent().getRemoteHost();
	}

	@SuppressWarnings("unchecked")
	public void onCategorySelect(ForwardEvent event) {
		Button btn = (Button) event.getOrigin().getTarget();
		if (_selected != btn) {
			_selected = btn;
		}
		BookmarkRole bookmarkRole = getBookmarkRole(_selected.getId());
		String href = bookmarkRole.getHref();
		updateLastLoginDateText();

		ListModel lst = getSelectedModel();
		if (lst.getSize() == 0)
			itemList.setVisible(false);
		else {
			itemList.setVisible(true);
			itemList.setModel(lst);
		}

		if (href.equals("logout.zul")) {
			Messagebox.show("Are you sure you want to logout?",
					"Logout Confirmation", Messagebox.OK | Messagebox.CANCEL,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt)
								throws InterruptedException {
							if (evt.getName().equals("onOK")) {
								UserCredentialManager.getIntance().logOff();
								Executions.sendRedirect("signin.zul");
							}
						}
					});
		} else if (bookmarkRole.getBookmarkCode().equals("abt")) {
			showAboutWindow();
		} else {
			execution.setAttribute("access",
					new Priviledge(bookmarkRole.getC(), bookmarkRole.getR(),
							bookmarkRole.getU(), bookmarkRole.getD()));
			xcontents.setSrc(href);

		}

		self.getDesktop().setBookmark(bookmarkRole.getBookmarkCode());
	}

	private void showAboutWindow() {
		if (aboutWindow == null) {
			aboutWindow = (Window) Executions.createComponents(
					"/kbureau/help/About.zul", null, null);
		}
		aboutWindow.setMode("modal");
		aboutWindow.setPosition("center, center");
		aboutWindow.setVisible(true);
	}

	public void onBookmarkChange$main(BookmarkEvent event) {
		String code = event.getBookmark();
		BookmarkRole bookmarkRole = (BookmarkRole) _bookmarkMap.get(code);
		if (code.length() <= 0)
			return;

		setSelectedCategory(code);
		execution.setAttribute("access", new Priviledge(bookmarkRole.getC(),
				bookmarkRole.getR(), bookmarkRole.getU(), bookmarkRole.getD()));
		xcontents.setSrc(bookmarkRole.getHref());
		updateLastLoginDateText();

		ListModel lst = getSelectedModel();
		if (lst.getSize() == 0)
			itemList.setVisible(false);
	}

	public void onMainCreate(Event event) {
		final Execution exec = Executions.getCurrent();
		String id = exec.getParameter("id");
		if (id == null) {
			id = "hom";
			if (UserCredentialManager.getIntance().getLogin().isAdm())
				id = "cbr";
		}
		BookmarkRole bookmarkRole = (BookmarkRole) _bookmarkMap.get(id);
		updateLastLoginDateText();
		setSelectedCategory(id);

		ListModel lst = getSelectedModel();
		if (lst.getSize() == 0)
			itemList.setVisible(false);
		else {
			itemList.setVisible(true);
			itemList.setModel(lst);
		}

		if (id == "abt") {
			showAboutWindow();
			return;
		}

		execution.setAttribute("access", new Priviledge(bookmarkRole.getC(),
				bookmarkRole.getR(), bookmarkRole.getU(), bookmarkRole.getD()));
		xcontents.setSrc(bookmarkRole.getHref());

	}

	private void setSelectedCategory(String code) {
		_selected = (Button) self.getFellow(code);
		String deselect = _selected != null ? "jq('#"
				+ _selected.getUuid()
				+ "').addClass('demo-seld').siblings().removeClass('demo-seld');"
				: "";
		Clients.evalJavaScript(deselect);
		self.getDesktop().setBookmark(code);
	}

	private BookmarkRole getBookmarkRole(String mainBookmarkCode) {
		return (BookmarkRole) _bookmarkMap.get(mainBookmarkCode);
	}

	// Composer Implementation
	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Events.postEvent("onMainCreate", comp, null);
	}

	/**
	 * @return the lastLoginDate
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate
	 *            the lastLoginDate to set
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	private void updateLastLoginDateText() {
		lbLoginDate.setValue(UserCredentialManager.getIntance()
				.getLastLoginDate());
	}

	public BookmarkRole[] getBookmarkRoles() {
		BookmarkRole[] bookmarkRoles = (BookmarkRole[]) _bookmarkMap.values()
				.toArray(new BookmarkRole[] {});
		return bookmarkRoles;
	}

	public ListitemRenderer getItemRenderer() {
		return _defRend;
	}

	private static final ListitemRenderer _defRend = new ItemRender();

	private static class ItemRender implements ListitemRenderer,
			java.io.Serializable {
		public void render(Listitem item, Object data, int index) {
			BookmarkRole di = (BookmarkRole) data;
			Listcell lc = new Listcell();
			item.setValue(di);
			lc.setHeight("30px");
			lc.setImage(di.getIcon());
			item.setId(di.getBookmarkCode());
			lc.setLabel(di.getLabel());
			lc.setParent(item);
		}
	};

	public ListModel getSelectedModel() {
		if (_selected == null)
			return null;
		BookmarkRole mainBookmark = getBookmarkRole(_selected.getId());
		List<BookmarkRole> lstBookmarkRole = UserCredentialManager.getIntance()
				.getSubBookmark(mainBookmark.getBookmarkCode());
		return new ListModelList<>(lstBookmarkRole);
	}

	public void onSelect$itemList(SelectEvent event) {
		Listitem item = itemList.getSelectedItem();
		if (item != null) {
			// sometimes the item is unloaded.
			if (!item.isLoaded()) {
				itemList.renderItem(item);
			}
			BookmarkRole sb = (BookmarkRole) item.getValue();
			updateLastLoginDateText();
			/*
			 * if (sb.getBookmarkCode().equals("abt")) { if(aboutWindow == null)
			 * { aboutWindow = (Window) Executions.createComponents(
			 * "/kbureau/help/About.zul", null, null);
			 * aboutWindow.setMode("modal");
			 * aboutWindow.setPosition("center, center"); }
			 * aboutWindow.setVisible(true); return; }
			 */
			execution.setAttribute("access",
					new Priviledge(sb.getC(), sb.getR(), sb.getU(), sb.getD()));
			xcontents.setSrc(sb.getHref());

		}
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
