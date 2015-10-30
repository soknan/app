package kredit.web.core.util.authentication.viewmodel;

import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.kbureau.model.facade.AuthenticationFacade;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

public class ResetPswVM {

	String message = "";
	String password = "";
	String confirmPassword = "";
	int securityCode = -2;
	int userId;
	String username = "";

	@Wire
	Textbox retypePsw;

	@Init
	public void init(@QueryParam("scd") String securityCode,
			@QueryParam("id") String id, @QueryParam("usr") String username) {
		if (UserCredentialManager.getIntance().isAuthenticated())
		{
			return;
		}
		try {
			if (securityCode != null)
				this.securityCode = Integer.parseInt(securityCode);
			if (id != null)
				this.userId = Integer.parseInt(id);
			if(username != null)
				this.username = username;
			if(!AuthenticationFacade.isValidSecurityCode(userId, this.username, this.securityCode)){
				Executions.sendRedirect("forgotpswlnk.zul?flg=inv");
			}
		} catch (Exception e) {
			Executions.sendRedirect("forgotpswlnk.zul?flg=inv");
		}
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

	static final String NOT_MATCH = "Password and retype must be matched.";

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
	@NotifyChange({ "message" })
	public void doResetPassword() {
		UserCredentialManager mgmt = UserCredentialManager.getIntance();
		if (password.isEmpty()) {
			message = "";
			return;
		}
		if (!password.equals(confirmPassword)) {
			message = NOT_MATCH;
			return;
		}

		if (!UserCredentialManager.getIntance().isAuthenticated()) {
			message = mgmt.resetPasswordByLink(password, userId, securityCode,
					username);
			if(message.indexOf("successfully") > 0){
				Executions.sendRedirect("signin.zul?flg=saved");
			}
		} else
			message = mgmt.resetPassword(password);
	}

	@Command
	@NotifyChange("message")
	public void onOK() {
		doResetPassword();
	}

	@Command
	@NotifyChange({ "message", "password" })
	public void onCancel() {
		password = "";
		message = "";
	}

	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * @param confirmPassword
	 *            the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
