package kredit.web.core.util.authentication.viewmodel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kredit.web.core.util.Common;
import kredit.web.core.util.EmailHelper;
import kredit.web.core.util.EmailThread;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.authentication.model.facade.UserCredentialFacade;
import kredit.web.core.util.log.model.LoggerSession;
import kredit.web.core.util.log.model.LoggerSessionLog;
import kredit.web.security.model.facade.SecurityFacade;

import org.k.model.User;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DefaultCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;

public class LoginVM {
	Execution ex;
	String message;
	String username;
	static final String KSUPPORT_USRNAME = "ksupport_usrname";
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
			if (cookie.getName().equals(KSUPPORT_USRNAME)) {
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
		UserCredentialManager mgmt = UserCredentialManager.getIntance();
		mgmt.login(username, password);

		User loginUsr = mgmt.getLoginUsr();

		// add log
		LoggerSession ses = mgmt.getLoggerSession();
		ses.setBrCd(loginUsr.getHomeBranch());
		ses.setUsername(loginUsr.getUsername());
		ses.setIp(Common.getUserIpAddr(Executions.getCurrent()));
		ses.setHost(Executions.getCurrent().getRemoteHost());
		ses.setAgent(Executions.getCurrent().getUserAgent());

		List<LoggerSessionLog> logLst = ses.getSessionLogs();
		LoggerSessionLog log = new LoggerSessionLog();
		log.setBrCd(loginUsr.getHomeBranch());
		log.setModule("LOGIN");
		log.setMessage(mgmt.getLogMsg());

		if (mgmt.isAuthenticated()) {
			//increase count success
			SecurityFacade.increaseCount(username, "SUCCESS");
			
			log.setType("LI_S");

			HttpServletResponse response = (HttpServletResponse) Executions
					.getCurrent().getNativeResponse();
			Cookie userCookie = new Cookie(KSUPPORT_USRNAME, username);
			userCookie.setMaxAge(60 * 60 * 24 * 30);
			response.addCookie(userCookie);

			if (loginUsr.getSecurityNo() == 0) {
				Executions.createComponents("resetpsw.zul", singIn, null);
				return;
			}

			Executions.sendRedirect("/");
		} else {
			//increase count fail
			SecurityFacade.increaseCount(username, "FAIL");
			
			ses.setUsername(username);
			log.setType("LI_F");
			message = mgmt.getLoginResultMsg();
		}

		logLst.add(log);
		Ebean.save(ses);
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

	/*
	 * @Command
	 * 
	 * @NotifyChange({ "messageForgot", "instructionMsg" }) public void
	 * submitEmail() { Authentication user = AuthenticationFacade
	 * .getUserByEmailToResetPsw(email); if (user == null) { messageForgot =
	 * "No account with that email exists. Try again!"; return; }
	 * 
	 * sendEmailPwdReset(user);
	 * 
	 * 
	 * instructionMsg =
	 * "An email about kBureau Password Reset has been sent to " + email; }
	 * 
	 * 
	 * public void sendEmailPwdReset(Authentication user) { if (email == null ||
	 * email.isEmpty()) return; String template = EmailHelper
	 * .readFileTemplate("/kbureau/admin/template_email_psw.html"); String t1 =
	 * Executions.getCurrent().getServerName(); int t2 =
	 * Executions.getCurrent().getServerPort(); String t3 =
	 * Executions.getCurrent().getContextPath(); String serverRoot = t1 + ":" +
	 * t2 + t3; String content = template; content =
	 * content.replace("##server_root##", serverRoot); content =
	 * content.replace("##id##", user.getId() + ""); content =
	 * content.replace("##scd##", user.getSecurityCode() + ""); content =
	 * content.replace("##usr##", user.getUsername()); String result =
	 * EmailHelper.sendEmail(user.getEmail(),
	 * "kBureau Password Reset Confirmation", content);
	 * System.out.println(result); }
	 */

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

	String instructionMsg = "Please enter your email address or username of kSupport in order to reset your kSupport password. You may need to check your spam folder or unblock	noreply-it@kredit.com.kh.";

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

	@DefaultCommand
	public void dummy() {

	}

	@Command
	@NotifyChange({ "messageForgot", "instructionMsg" })
	public void submitEmail() throws Throwable {
		User user = UserCredentialFacade.requestSecurityNo(email);
		if (user == null) {
			messageForgot = "No account with that username or email exists. Try again!";
			return;
		}

		sendEmailPwdReset(user);
		/*
		 * if(forgotWindow != null) forgotWindow.setVisible(false);
		 */

	}

	public void sendEmailPwdReset(User user) {
		if (email == null || email.isEmpty())
			return;

		UserCredentialManager mgmt = UserCredentialManager.getIntance();

		// add log
		LoggerSession ses = mgmt.getLoggerSession();
		ses.setBrCd(user.getHomeBranch());
		ses.setUsername(user.getUsername());
		ses.setIp(Common.getUserIpAddr(Executions.getCurrent()));
		ses.setHost(Executions.getCurrent().getRemoteHost());
		ses.setAgent(Executions.getCurrent().getUserAgent());

		LoggerSessionLog log = new LoggerSessionLog();
		log.setBrCd(user.getHomeBranch());
		log.setModule("resetpwd");

		ses.getSessionLogs().add(log);
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(email).append(" requested pwd reset --> ");

		String template = "";
		String emailTitle = "";
		List<String> lstTo = new ArrayList<String>();

		if (user.getEmail() == null) {
			template = EmailHelper
					.readFileTemplate("/other/email_tmpl/req_reset_psw.html");
			template = template.replace("##fullname##", user.getFullName())
					.replace("##phone##", user.getPhone()).replace("##sec##", user.getSecurityNo() + "");
			emailTitle = "[Req. reset kSupport pwd] " + user.getFullName();
			lstTo.add("sovathena_neth@kredit.com.kh");

			log.setType("EPWD_G"); // email pwd to Agent
			strBuilder.append("no email --> sent email req to agent");

			instructionMsg = "Your request about password reset has been sent to kSupport Administrator."
					+ " IT Department will contact you and provide a security code for resetting your password very soon. "
					+ "In case no one contact you, please call us to follow up your request. Thanks";

		} else {
			template = EmailHelper
					.readFileTemplate("/other/email_tmpl/reset_psw.html");
			emailTitle = "kSupport Password Reset Confirmation";
			lstTo.add(user.getEmail());

			log.setType("EPWD_U"); // email pwd to User
			strBuilder.append("has email ").append("[").append(user.getEmail())
					.append("] --> sent email pwd reset to the user");

			instructionMsg = "An email about  kSupport Password Reset has been sent to "
					+ email
					+ ". Please check the email and follow the instruction.";
		}

		log.setMessage(strBuilder.toString());

		Ebean.save(ses);

		String t1 = Executions.getCurrent().getServerName();
		int t2 = Executions.getCurrent().getServerPort();
		String t3 = Executions.getCurrent().getContextPath();
		String serverRoot = t1 + ":" + t2 + t3;
		String content = template;
		content = content.replace("##server_root##", serverRoot);
		String securityCode = Common.encrypt(new StringBuilder()
				.append(user.getId()).append("!").append(user.getSecurityNo())
				.toString());
		content = content.replace("##scd##", securityCode + "");
		
		EmailThread emailThread = new EmailThread(emailTitle, lstTo, null,
				null, content);
		emailThread.start();
	}

}
