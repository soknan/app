package kredit.web.core.util.authentication.viewmodel;

import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.authentication.model.facade.UserCredentialFacade;
import kredit.web.security.model.facade.SecurityFacade;

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
	int securityNo = -2;
	int userId;
	String username = "";
	String flg;

	@Wire
	Textbox retypePsw;

	@Init
	public void init(@QueryParam("scd") String securityCode, @QueryParam("flg") String flg) {
		if (UserCredentialManager.getIntance().isAuthenticated())
		{
			return;
		}
		try {
			if (securityCode != null){
				String txtSec = Common.decrypt(securityCode);
				String[] txtSecArr = txtSec.split("!");
				if(txtSecArr.length < 2){
					return;
				}
				this.userId = Integer.parseInt(txtSecArr[0]);
				this.securityNo = Integer.parseInt(txtSecArr[1]);
			}
			
			if(flg != null){
				this.flg = "allow";
				this.securityNo = 0;
			}

			if(flg == null && !UserCredentialFacade.isValidSecurityCode(userId, this.securityNo)){
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
	static final String EMPTY_PWD = "Password cannot be empty.";

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
	public void doResetPassword() throws Throwable {
		UserCredentialManager mgmt = UserCredentialManager.getIntance();
		if (password.isEmpty()) {
			message = EMPTY_PWD;
			return;
		}
		if (!password.equals(confirmPassword)) {
			message = NOT_MATCH;
			return;
		}

		if (!UserCredentialManager.getIntance().isAuthenticated()) {
			
			if(!UserCredentialFacade.isValidSecurityCode(userId, this.securityNo)){
				message = "Invalid security number.";
				return;
			}
			message = mgmt.resetPasswordByLink(userId, password, securityNo);
			if(message.indexOf("successfully") > -1){
				SecurityFacade.onSetPwdChangeTime(userId);
				Executions.sendRedirect("signin.zul?flg=saved");
			}
		} else{
			message = mgmt.resetPassword(password);
		}
	}

	@Command
	@NotifyChange("message")
	public void onOK() throws Throwable {
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

	/**
	 * @return the securityNo
	 */
	public int getSecurityNo() {
		return securityNo;
	}

	/**
	 * @param securityNo the securityNo to set
	 */
	public void setSecurityNo(int securityNo) {
		this.securityNo = securityNo;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the retypePsw
	 */
	public Textbox getRetypePsw() {
		return retypePsw;
	}

	/**
	 * @param retypePsw the retypePsw to set
	 */
	public void setRetypePsw(Textbox retypePsw) {
		this.retypePsw = retypePsw;
	}

	/**
	 * @return the flg
	 */
	public String getFlg() {
		return flg;
	}

	/**
	 * @param flg the flg to set
	 */
	public void setFlg(String flg) {
		this.flg = flg;
	}
}
