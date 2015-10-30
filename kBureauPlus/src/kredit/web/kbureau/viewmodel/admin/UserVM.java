/**
 * 
 */
package kredit.web.kbureau.viewmodel.admin;

import kredit.web.core.util.Common;
import kredit.web.core.util.EmailHelper;
import kredit.web.core.util.authentication.Priviledge;
import kredit.web.kbureau.model.Authentication;
import kredit.web.kbureau.model.facade.AuthenticationFacade;
import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;


/**
 * @author vathenan
 * 
 */
public class UserVM {
	ListModelList<Authentication> kbureauUsers;
	Authentication selectedUser;
	AuthenticationFacade AuthenticationFacade;
	String filter = "";

	Priviledge priviledge = new Priviledge();

	boolean visible;
	String winTitle;
	boolean canSave;
	boolean canDelete;
	String msgDelete;
	String resultMsg;
	String resultMsgClass;
	String resultIcon;
	String passwordConfirm;
	boolean editUser;

	static Logger log = Logger.getLogger(UserVM.class.getName());

	public UserVM() {
		AuthenticationFacade = new AuthenticationFacade();
	}

	@Init
	public void init(@ExecutionParam("access") Priviledge priviledge) {
		this.priviledge = priviledge;
	}

	/**
	 * @return the kbureauUsers
	 */
	public ListModelList<Authentication> getKbureauUsers() {
		if (kbureauUsers == null)
			kbureauUsers = new ListModelList<Authentication>(
					AuthenticationFacade.getListUserToEmail(filter));
		return kbureauUsers;
	}

	@Command
	@NotifyChange({ "selectedUser", "kbureauUsers" })
	public void doSearch() {
		kbureauUsers = new ListModelList<Authentication>(
				AuthenticationFacade.getListUserToEmail(filter));
		selectedUser = null;
	}

	/**
	 * @param kbureauUsers
	 *            the kbureauUsers to set
	 */
	public void setKbureauUsers(ListModelList<Authentication> kbureauUsers) {
		this.kbureauUsers = kbureauUsers;
	}

	/**
	 * @return the selectedUser
	 */
	public Authentication getSelectedUser() {
		if (selectedUser == null)
			selectedUser = new Authentication();
		return selectedUser;
	}

	/**
	 * @param selectedUser
	 *            the selectedUser to set
	 */
	public void setSelectedUser(Authentication selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * @return the priviledge
	 */
	public Priviledge getPriviledge() {
		return priviledge;
	}

	/**
	 * @param priviledge
	 *            the priviledge to set
	 */
	public void setPriviledge(Priviledge priviledge) {
		this.priviledge = priviledge;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the AuthenticationFacade
	 */
	public AuthenticationFacade getAuthenticationFacade() {
		return AuthenticationFacade;
	}

	/**
	 * @param AuthenticationFacade
	 *            the AuthenticationFacade to set
	 */
	public void setAuthenticationFacade(
			AuthenticationFacade AuthenticationFacade) {
		this.AuthenticationFacade = AuthenticationFacade;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the winTitle
	 */
	public String getWinTitle() {
		return winTitle;
	}

	/**
	 * @param winTitle
	 *            the winTitle to set
	 */
	public void setWinTitle(String winTitle) {
		this.winTitle = winTitle;
	}

	/**
	 * @return the canSave
	 */
	public boolean isCanSave() {
		return canSave;
	}

	/**
	 * @param canSave
	 *            the canSave to set
	 */
	public void setCanSave(boolean canSave) {
		this.canSave = canSave;
	}

	/**
	 * @return the canDelete
	 */
	public boolean isCanDelete() {
		return canDelete;
	}

	/**
	 * @param canDelete
	 *            the canDelete to set
	 */
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	/**
	 * @return the msgDelete
	 */
	public String getMsgDelete() {
		return msgDelete;
	}

	/**
	 * @param msgDelete
	 *            the msgDelete to set
	 */
	public void setMsgDelete(String msgDelete) {
		this.msgDelete = msgDelete;
	}

	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg
	 *            the resultMsg to set
	 */
	public void setResultMsg(String result) {
		this.resultMsg = result;
	}

	/**
	 * @return the resultMsgClass
	 */
	public String getResultMsgClass() {
		return resultMsgClass;
	}

	/**
	 * @param resultMsgClass
	 *            the resultMsgClass to set
	 */
	public void setResultMsgClass(String resultMsgClass) {
		this.resultMsgClass = resultMsgClass;
	}

	/**
	 * @return the resultIcon
	 */
	public String getResultIcon() {
		return resultIcon;
	}

	/**
	 * @param resultIcon
	 *            the resultIcon to set
	 */
	public void setResultIcon(String resultIcon) {
		this.resultIcon = resultIcon;
	}

	@Command
	@NotifyChange({ "selectedUser", "winTitle", "visible", "resultMsg",
			"canDelete", "canSave", "passwordConfirm" })
	public void onNewUser() {
		try {
			this.selectedUser = new Authentication();
			this.winTitle = "kBureau User - New";
			this.visible = true;
			this.resultMsg = null;
			this.canDelete = false;
			this.canSave = false;
			this.passwordConfirm = null;
			this.editUser = false;
		} catch (Exception e) {
			log.error("Error while adding new kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "selectedUser", "winTitle", "visible", "resultMsg",
			"canDelete", "canSave", "passwordConfirm" })
	public void onEditUser() {
		try {
			this.winTitle = "kBureau User - Edit";
			this.visible = true;
			this.resultMsg = null;
			this.canDelete = true;
			this.canSave = false;
			this.passwordConfirm = null;
			this.editUser = true;
		} catch (Exception e) {
			log.error("Error while editing kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "kbureauUsers", "resultMsg", "resultMsgClass", "canSave",
			"resultIcon", "winTitle" })
	public void onSave() {
		try {
			boolean newUser = selectedUser.getId() == 0;
			int userId = AuthenticationFacade.saveKbureauUser(0, selectedUser);
			if (userId > 0) {
				if (newUser) {
					kbureauUsers.add(selectedUser);
					this.winTitle = "kBureau User - Edit";
					this.editUser = true;
				}
				flagResultMessage("Save successfully.", true);
			} else {
				flagResultMessage("Save failed.", false);
			}
			canSave = false;
			canDelete = true;

		} catch (Exception e) {
			log.error("Error while editing kBureau user.", e);
		}
	}

	@NotifyChange({ "canSave", "resultMsg", "resultMsgClass", "resultIcon" })
	public Validator getUsernameValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				String username = ctx.getProperty().getValue().toString();
				if (AuthenticationFacade.isExistUsername(username,
						selectedUser.getId())) {
					addInvalidMessage(ctx, "The username is already in use.");
				}
			}
		};
	}

	public Validator getEmailValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				String email = ctx.getProperty().getValue().toString();
				if (email.isEmpty()) {
					addInvalidMessage(ctx, " Ã¢â€”ï¿½ Email cannot be blank.");
				} else if (!Common.validateEmail(email)) {
					addInvalidMessage(ctx, " Ã¢â€”ï¿½ Invalid email address");
				}

			}
		};
	}

	public Validator getPasswordValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(ValidationContext ctx) {
				String pwd = ctx.getProperty().getValue().toString();
				if (pwd.isEmpty()) {
					if (!editUser)
						addInvalidMessage(ctx, " Ã¢â€”ï¿½ Password cannot be blank.");
				} else if (pwd.length() < 6) {
					addInvalidMessage(ctx,
							" Ã¢â€”ï¿½ Password must be at least 6 characters.");
				}
			}
		};
	}

	public Validator getPasswordConfirmValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				String pwdConfirm = ctx.getProperty().getValue().toString();
				String pwd = ((Textbox) ctx.getValidatorArg("pwd")).getText();
				if (!editUser && !pwdConfirm.equals(pwd)) {
					addInvalidMessage(ctx, " Ã¢â€”ï¿½ Your passwords do not match!");
				}
			}
		};
	}

	public Validator getSubCodeValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				String subCode = ctx.getProperty().getValue().toString();
				if (subCode.isEmpty()) {
					addInvalidMessage(ctx,
							" Ã¢â€”ï¿½ Sub Branch Code cannot be blank.");
				} else if (!AuthenticationFacade.isExistSubCode(subCode)) {
					addInvalidMessage(ctx, " Ã¢â€”ï¿½ " + subCode
							+ " is not a valid code of existing Sub Branch.");
				}
			}
		};
	}

	@Command
	@NotifyChange({ "selectedUser", "canSave", "resultMsg" })
	public void onCancelEdit() {
		selectedUser = null;
		resultMsg = null;
		canSave = false;
		passwordConfirm = null;
	}

	@Command
	@NotifyChange({ "msgDelete" })
	public void onCancelDelete() {
		try {
			msgDelete = null;
		} catch (Exception e) {
			log.error("Error while cancel delete kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "msgDelete" })
	public void onConfirmDelete() {
		try {
			this.msgDelete = "Are you sure you want to delete user "
					+ selectedUser.getUsername() + "?";
		} catch (Exception e) {
			log.error("Error while deleting kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "msgDelete", "kbureauUsers", "selectedUser" })
	public void onDeleteUser() {
		try {
			this.msgDelete = null;
			AuthenticationFacade.deleteKbureauUser(0, selectedUser);
			kbureauUsers.remove(selectedUser);
			selectedUser = null;
		} catch (Exception e) {
			log.error("Error while deleting kBureau user.", e);
		}
	}

	private void validate(Authentication kBureauUser) {
		canSave = !kBureauUser.getUsername().isEmpty()
				&& !kBureauUser.getEmail().isEmpty()
				&& !kBureauUser.getPassword().isEmpty()
				&& !kBureauUser.getSubBranchCode().isEmpty();
	}

	// todo:
	@Command
	@NotifyChange({ "canSave", "resultMsg" })
	public void notifyChangingUserInfo(
			@BindingParam("txtUsername") Textbox txtUsername,
			@BindingParam("txtEmail") Textbox txtEmail,
			@BindingParam("txtPassword") Textbox txtPassword,
			@BindingParam("txtPasswordConfirm") Textbox txtPasswordConfirm,
			@BindingParam("txtSubCode") Textbox txtSubCode,
			@BindingParam("txtNote") Textbox txtNote) {
		try {
			resultMsg = null;
			canSave = !txtUsername.getText().isEmpty()
					&& !txtEmail.getText().isEmpty()
					&& !txtSubCode.getText().isEmpty()
					&& !txtNote.getText().isEmpty();
			if (!editUser) {
				canSave = canSave
						&& !txtPassword.getText().isEmpty()
						&& !txtPasswordConfirm.getText().isEmpty()
						&& txtPassword.getText().equals(
								txtPasswordConfirm.getText());
			}

		} catch (Exception e) {
			log.error("Error while notifying changing kbureau user info.", e);
		}
	}

	@Command
	@NotifyChange({ "resultMsg", "resultMsgClass", "canSave", "resultIcon" })
	public void notifyChangeUsername() {
		try {
			if (!AuthenticationFacade.isExistUsername(
					selectedUser.getUsername(), selectedUser.getId()))
				return;
			flagResultMessage(
					"The username is already in use. Please try with another one.",
					false);
		} catch (Exception e) {
			log.error("Error while notifying changing username.", e);
		}
	}

	private void flagResultMessage(String msg, boolean flagSuccess) {
		resultMsg = msg;
		canSave = false;
		if (flagSuccess) {
			resultMsgClass = "k-resultEnc-msg";
			resultIcon = "/img/app/ok16.png";
		} else {
			resultMsgClass = "k-error";
			resultIcon = "/img/app/error16.png";
		}

	}

	@Command
	@NotifyChange({ "resultMsg", "resultMsgClass", "canSave", "resultIcon" })
	public void notifyChangePasswordConfirm() {
		try {
			if (!passwordConfirm.equals(selectedUser.getPassword()))
				return;
			flagResultMessage("Your retype password does not match.", false);

		} catch (Exception e) {
			log.error("Error while notifying retype password.", e);
		}
	}

	/**
	 * @return the passwordConfirm
	 */
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	/**
	 * @param passwordConfirm
	 *            the passwordConfirm to set
	 */
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	/**
	 * @return the editUser
	 */
	public boolean isEditUser() {
		return editUser;
	}

	/**
	 * @param editUser
	 *            the editUser to set
	 */
	public void setEditUser(boolean editUser) {
		this.editUser = editUser;
	}

	@Command
	@NotifyChange({"kbureauUsers"})
	public void onEmailAll(@BindingParam("isTest") boolean isTest) {
		
		if(kbureauUsers.size() == 0) return;
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(isTest?"Email (Test) has been sent successfully." : "Email (Production) has been sent successfully.");
		String template = EmailHelper
				.readFileTemplate(isTest?"/kbureau/admin/template_email_test.html":"/kbureau/admin/template_email.html");
		for (Authentication user : kbureauUsers) {
			String content = template;
			String title = user.getSex().equals("M") ? "Mr." : "Ms.";
			String userFullName = title + user.getlName() + " "
					+ user.getfName();
			content = content.replace("##user##", userFullName);
			content = content.replace("##title##", title);
			content = content.replace("##username##", user.getUsername());
			content = content.replace("##psw##", user.getUsername());
			// String resultEnc = EmailHelper.sendEmail(user.getEmail(),
			// "Your kBureau User Account Activated", content);
			String result = EmailHelper.sendEmail(user.getEmail(),
					isTest? "Your kBureau User Account Activated for Testing" : "Your kBureau User Account Activated for Production", content);
			AuthenticationFacade.updateNoteAfterDoneEmail(user.getId());
			user.setNote("Done email");
			System.out.println(result);
		}
		System.out.println("Send all done!");
		Clients.showNotification(strBuilder.toString());
	}
}
