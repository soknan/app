package kredit.web.core.util.authentication;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Map;

import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.model.facade.UserCredentialFacade;
import kredit.web.core.util.log.model.LoggerSession;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.security.Security;
import kredit.web.security.model.ValidityHelper;

import org.k.model.User;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.avaje.ebean.Ebean;
import com.oracle.xmlns.oxp.service.v2.AccessDeniedException;
import com.oracle.xmlns.oxp.service.v2.SecurityServiceProxy;

/**
 * @author Sovathena Neth
 * 
 *         This class provides a class which manages authentication
 * 
 */
public class UserCredentialManager {

	private static final String KEY_USER_MODEL = UserCredentialManager.class
			.getName() + "_MODEL";
	private Map<String, Object> mapLogin;
	private LoggerSession loggerSession;
	private String bipSessionToken;
	private Map<String, CodeItem> functionsMap;

	private UserCredentialFacade userCrFacade;

	private UserCredentialManager() {
		userCrFacade = new UserCredentialFacade();
	}

	public static UserCredentialManager getIntance() {
		return getIntance(Sessions.getCurrent());
	}

	private static UserCredentialManager getIntance(Session zkSession) {
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
		mapLogin = userCrFacade.doLogin(username, password);
		
		if(!isAuthenticated()) return;
		
		ValidityHelper validity = new ValidityHelper();
		validity = Common.varifyValidity();
		if(validity.getExist())
		{
			functionsMap = UserCredentialFacade.getLoginFunctions(validity.getUser_as());			
			UserCredentialManager.getIntance().getLoginUsr().setHomeBranch(validity.getHomeBranch());
		}
		else
		{
			System.out.println(UserCredentialFacade.getLoginFunctions());
			functionsMap = UserCredentialFacade.getLoginFunctions();
		}
	}

	public synchronized void logout() {
		userCrFacade.doLogout(getLoginUsr().getUsername());
		mapLogin = null;

		if (loggerSession == null)
			return;
		loggerSession.setLogoutOn(new Date());
		Ebean.update(loggerSession);
		loggerSession = null;
	}

	public synchronized Map<String, Object> getMapLogin() {
		return mapLogin;
	}

	public synchronized User getLoginUsr() {
		if (mapLogin == null) {
			return new User();
		}

		if (mapLogin.get("usr") == null) {
			return new User();
		}
		return (User) mapLogin.get("usr");
	}

	public synchronized boolean isAuthenticated() {
		if (mapLogin == null)
			return false;
		if (mapLogin.get("ok") == null)
			return false;
		if (mapLogin.get("ok").equals("y"))
			return true;
		return false;
	}

	public synchronized String getLoginResultMsg() {
		if (mapLogin == null || mapLogin.get("msg") == null)
			return "Invalid username or password. Try again!";
		return mapLogin.get("msg").toString();
	}

	public synchronized String getLogMsg() {
		if (mapLogin == null || mapLogin.get("log") == null)
			return "Invalid username or passwordd";
		return mapLogin.get("log").toString();
	}

	/**
	 * @return the loggerSession
	 */
	public LoggerSession getLoggerSession() {
		if (loggerSession == null) {
			loggerSession = new LoggerSession();
		}
		return loggerSession;
	}

	/**
	 * @param loggerSession
	 *            the loggerSession to set
	 */
	public void setLoggerSession(LoggerSession loggerSession) {
		this.loggerSession = loggerSession;
	}

	public synchronized String resetPasswordByLink(int id, String password,
			int securityNo) throws Throwable {
		User mLogin = new User();
		mLogin.setId(id);
		mLogin.setPwd(Security.encPwd(password));
		mLogin.setSecurityNo(securityNo);
		String result = userCrFacade.resetPassword(mLogin);
		return result;
	}

	public synchronized String resetPassword(String password) throws Throwable {
		User user = getLoginUsr();
		user.setPwd(Security.encPwd(password));
		String result = userCrFacade.resetPassword(user);
		return result;
	}

	/**
	 * @return the bipSessionToken
	 * @throws RemoteException 
	 * @throws AccessDeniedException 
	 */
	public String getBipSessionToken() throws AccessDeniedException, RemoteException {
		if (mapLogin == null)
			return null;
		if (bipSessionToken != null)
			return bipSessionToken;

		String url =  Common.getBiWsConfig().getProperty(Common.BIP_URL) + Common.getBiWsConfig().getProperty(Common.BIP_SERVICE_SECURITY);
		
		SecurityServiceProxy securityServiceProxy = new SecurityServiceProxy(url);
		//http://192.168.2.8:7001/xmlpserver/Kredit%20Report/4.%20LOAN/4.2%20Loan%20Classification/4.2.008%20List%20of%20Loan%20by%20Location.xdo?_xmode=2
		bipSessionToken = securityServiceProxy.login("todo", "todo");
		
		return bipSessionToken;
	}

	public Map<String, CodeItem> getFunctionsMap() {
		return functionsMap;
	}

	public void setFunctionsMap(Map<String, CodeItem> functionsMap) {
		this.functionsMap = functionsMap;
	}
}
