package kredit.web.core.util.authentication;

import java.util.List;
import java.util.Map;

import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.model.BookmarkRole;
import kredit.web.core.util.authentication.model.Login;
import kredit.web.core.util.authentication.model.facade.UserCredentialFacade;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/**
 * @author Sovathena Neth
 * 
 *         This class provides a class which manages authentication
 * 
 */
public class UserCredentialManager {

	private static final String KEY_USER_MODEL = UserCredentialManager.class
			.getName()
			+ "_MODEL";
	private Login login;
	
	private UserCredentialFacade userCrFacade;
	
	private UserCredentialManager() {
		userCrFacade = new UserCredentialFacade();
	}

	public static UserCredentialManager getIntance() {
		return getIntance(Sessions.getCurrent());
	}

	public static UserCredentialManager getIntance(Session zkSession) {
		synchronized (zkSession) {
			UserCredentialManager userModel = (UserCredentialManager) zkSession
					.getAttribute(KEY_USER_MODEL);
			if (userModel == null) {
				zkSession.setAttribute(KEY_USER_MODEL,
						userModel = new UserCredentialManager());
			}
			return userModel;
		}
	}

	public synchronized void login(String username, String password) {
		login =  userCrFacade.doLogin(username, password);
		if(login == null || (login != null && login.getId() < 0)) return;
		userCrFacade.reloadBookmarkMap(login);
	}
	
	public synchronized String resetPassword(String password) {
		login.setPassword(password);
		String result = userCrFacade.resetPassword(login);
		return result;
	}
	
	public synchronized String resetPasswordByLink(String password, int id, int securityCode, String username){
		Login mLogin = new Login();
		mLogin.setId(id);
		mLogin.setPassword(password);
		mLogin.setSecurityCode(securityCode);
		mLogin.setUsername(username);
		String result = userCrFacade.resetPassword(mLogin);
		return result;
	}

	public synchronized void logOff() {
		userCrFacade.doLogoff(login);
		login = null;
	}

	public synchronized Login getLogin() {
		return login;
	}
	
	public synchronized int getLoginId() {
		return login == null?0:login.getId();
	}
	
	public synchronized String getLastLoginDate() {
		return login != null? Common.FormatDateTime(login.getLastLoginDate()): "";
	}

	public synchronized boolean isAuthenticated() {
		return login != null && login.getId() > 0;
	}
	
	public synchronized Map getBookmarkMap() {
		return userCrFacade.getBookmarkMap(login);
	}
	
	public synchronized void reloadBookmarkMap() {
		userCrFacade.reloadBookmarkMap(login);
	}
	
	public List<BookmarkRole> getSubBookmark(String mainCode){
		return userCrFacade.getSubBookmark(getLoginId(), mainCode);
	}

}
