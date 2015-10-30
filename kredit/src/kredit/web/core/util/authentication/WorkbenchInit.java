package kredit.web.core.util.authentication;

import java.util.Map;

import kredit.web.core.util.Common;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

/**
 * @author zkessentials store
 * 
 *         This is a class which catches the initialization of a ZK page and
 *         redirects accordingly if no user credentials are found
 * 
 */
public class WorkbenchInit implements Initiator {

	/*
	 * Invoked when the ZK Parser starts
	 */
	@SuppressWarnings("rawtypes")
	public void doInit(Page page, Map arg) throws Exception {
		if (UserCredentialManager.getIntance().isAuthenticated())
			return;
		Executions.getCurrent().sendRedirect(Common.getAppAddr(Executions.getCurrent()) + "/signin.zul");
	}

	public boolean doCatch(Throwable parsingError) throws Exception {
		return false;
	}

	public void doAfterCompose(Page page) throws Exception {
	}

	public void doFinally() throws Exception {
	}

}
