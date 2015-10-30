package kredit.web.core.util.authentication.viewmodel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kredit.web.core.util.EmailHelper;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.kbureau.model.Authentication;
import kredit.web.kbureau.model.facade.AuthenticationFacade;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginVM {
	Execution ex;
	String message;
	String username;
	static final String KPLUS_USRNAME = "kplus_usrname";
	boolean flgSave;
	boolean flgInvalid;
	@Wire
	Window singIn;

	@Init
	@NotifyChange("flgSave")
	public void init(@QueryParam("flg") String flag) {
		this.flgSave = (flag != null && flag.equals("saved"));
		this.flgInvalid = (flag != null && flag.equals("inv"));
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		if (username == null)
			username = getUsernameCookie();
		return username;
	}

	private String getUsernameCookie() {
		String usrnameCookie = "";
		Cookie[] cookies = ((HttpServletRequest) Executions.getCurrent()
				.getNativeRequest()).getCookies();
		if (cookies == null)
			return "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(KPLUS_USRNAME)) {
				usrnameCookie = cookie.getValue();
				break;
			}
		}
		return usrnameCookie;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	@NotifyChange
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	@NotifyChange
	public void setPassword(String password) {
		this.password = password;
	}

	String password;

	static final String INVALID_LOGIN = "Invalid username or password. Try again!";
	static final String EXPIRED_DISABLED = "This account has been disabled or expired.";

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Command
	@NotifyChange("message")
	public void doLogin() {
		UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions
				.getCurrent());
		mgmt.login(username, password);
		if (mgmt.isAuthenticated()) {
			HttpServletResponse response = (HttpServletResponse) Executions
					.getCurrent().getNativeResponse();
			Cookie userCookie = new Cookie(KPLUS_USRNAME, username);
			userCookie.setMaxAge(60*60*24*30);
			response.addCookie(userCookie);

			if (mgmt.getLogin().getSecurityCode() == 0) {
				Window resetWindow = (Window) Executions.createComponents(
						"resetpsw.zul", singIn, null);
				resetWindow.setMode("modal");
				resetWindow.setPosition("center, center");
				return;
			}
			String bmk = Executions.getCurrent().getDesktop().getBookmark();
			Executions.sendRedirect("/#" + bmk);
		} else {
			switch (mgmt.getLoginId()) 
			{
				case -2:
					message = EXPIRED_DISABLED;
					break;
				default:
					message = INVALID_LOGIN;
					break;
			}
		}
	}

	@Command
	@NotifyChange("message")
	public void onOK() {
		doLogin();
	}

	@Command
	@NotifyChange({ "message", "username", "password" })
	public void onCancel() {
		username = "";
		password = "";
		message = "";
	}

	Window forgotWindow = null;
	String messageForgot;

	@Command
	public void showForgot() {
		Executions.sendRedirect("forgotpswlnk.zul");
		/*
		 * if (forgotWindow == null) forgotWindow = (Window)
		 * Executions.createComponents( "forgotpsw.zul", singIn, null);
		 * forgotWindow.setMode("modal");
		 * forgotWindow.setPosition("center, center");
		 * forgotWindow.setVisible(true);
		 */
	}

	String email;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Command
	@NotifyChange({ "messageForgot", "instructionMsg" })
	public void submitEmail() {
		Authentication user = AuthenticationFacade
				.getUserByEmailToResetPsw(email);
		if (user == null) {
			messageForgot = "No account with that email exists. Try again!";
			return;
		}

		sendEmailPwdReset(user);
		/*
		 * if(forgotWindow != null) forgotWindow.setVisible(false);
		 */

		instructionMsg = "An email about kBureau Password Reset has been sent to "
				+ email;
	}

	public void sendEmailPwdReset(Authentication user) {
		if (email == null || email.isEmpty())
			return;
		String template = EmailHelper
				.readFileTemplate("/kbureau/admin/template_email_psw.html");
		String t1 = Executions.getCurrent().getServerName();
		int t2 = Executions.getCurrent().getServerPort();
		String t3 = Executions.getCurrent().getContextPath();
		String serverRoot = t1 + ":" + t2 + t3;
		String content = template;
		content = content.replace("##server_root##", serverRoot);
		content = content.replace("##id##", user.getId() + "");
		content = content.replace("##scd##", user.getSecurityCode() + "");
		content = content.replace("##usr##", user.getUsername());
		String result = EmailHelper.sendEmail(user.getEmail(),
				"kBureau Password Reset Confirmation", content);
		System.out.println(result);
	}

	/**
	 * @return the messageForgot
	 */
	public String getMessageForgot() {
		return messageForgot;
	}

	/**
	 * @param messageForgot
	 *            the messageForgot to set
	 */
	public void setMessageForgot(String messageForgot) {
		this.messageForgot = messageForgot;
	}

	String instructionMsg = "Please enter your email address in order to reset your password. You may need to check your spam folder or unblock	noreply-it@kredit.com.kh.";

	/**
	 * @return the instructionMsg
	 */
	public String getInstructionMsg() {
		return instructionMsg;
	}

	/**
	 * @param instructionMsg
	 *            the instructionMsg to set
	 */
	public void setInstructionMsg(String instructionMsg) {
		this.instructionMsg = instructionMsg;
	}

	/**
	 * @return the flgSave
	 */
	public boolean isFlgSave() {
		return flgSave;
	}

	/**
	 * @param flgSave
	 *            the flgSave to set
	 */
	public void setFlgSave(boolean flgSave) {
		this.flgSave = flgSave;
	}

	/**
	 * @return the flgInvalid
	 */
	public boolean isFlgInvalid() {
		return flgInvalid;
	}

	/**
	 * @param flgInvalid
	 *            the flgInvalid to set
	 */
	public void setFlgInvalid(boolean flgInvalid) {
		this.flgInvalid = flgInvalid;
	}

}
