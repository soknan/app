/**
 * 
 */
package kredit.web.kbureau.viewmodel;

import kredit.web.core.util.Common;
import kredit.web.core.util.EmailHelper;
import kredit.web.core.util.authentication.Priviledge;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.authentication.model.facade.UserCredentialFacade;
import kredit.web.kbureau.model.Authentication;
import kredit.web.kbureau.model.admin.ParamUser;
import kredit.web.kbureau.model.facade.AuthenticationFacade;
import kredit.web.kbureau.model.facade.CommonFacade;
import kredit.web.kbureau.model.facade.admin.UserValidity;
import kredit.web.kbureau.model.facade.report.CbcReportFacade;
import kredit.web.kbureau.model.report.CodeItem;

import org.apache.log4j.Logger;
import org.zkoss.bind.SimpleForm;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.DefaultCommand;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

/**
 * @author vathenan
 * 
 */
public class KBureauUserVM {
	ListModelList<Authentication> kbureauUsers;
	Authentication selectedUser;
	AuthenticationFacade AuthenticationFacade;
	ParamUser param = new ParamUser();

	String countUser = "Count: 0";
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
	String msgReset;

	static Logger log = Logger.getLogger(KBureauUserVM.class.getName());

	ListModelList<CodeItem> branches;
	ListModelList<CodeItem> subBranches;
	ListModelList<CodeItem> statuses;
	ListModelList<CodeItem> sexes;

	ListModelList<CodeItem> fBranches;

	SimpleForm userFrm = new SimpleForm();

	ListModelList<CodeItem> fSubBranches;
	ListModelList<CodeItem> fStatuses;
	ListModelList<CodeItem> fSexes;

	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;

	ListModelList<UserValidity> validities;
	UserValidity selectedValidity;
	boolean canSaveValidity;
	boolean canDeleteValidity;
	String groupLabelValidityTitle;
	String deleteMessageValidity;

	String resultMsgValidity;
	String resultMsgClassValidity;
	String resultIconValidity;

	boolean canSendEmailPermanent;
	boolean canSendEmailActing;

	public KBureauUserVM() {
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
					AuthenticationFacade.getListUser(param));
		countUser = "Count: " + kbureauUsers.getSize();
		return kbureauUsers;
	}

	@Command
	@NotifyChange({ "selectedUser", "kbureauUsers", "countUser" })
	public void doSearch() {
		kbureauUsers = new ListModelList<Authentication>(
				AuthenticationFacade.getListUser(param));
		selectedUser = null;
		countUser = "Count: " + kbureauUsers.getSize();
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
		// if (selectedUser == null)
		// selectedUser = new Authentication();
		return selectedUser;
	}

	/**
	 * @param selectedUser
	 *            the selectedUser to set
	 */
	@NotifyChange({"fSubBranches", "selectedUser"})
	public void setSelectedUser(Authentication selectedUser) {
		this.selectedUser = selectedUser;
		fSubBranches = new ListModelList<CodeItem>(
				CommonFacade.getSuBranchStarByBranchCd(selectedUser.getBranchCode()));
	}

	/**
	 * @return the countUser
	 */
	public String getCountUser() {
		return countUser;
	}

	/**
	 * @param countUser
	 *            the countUser to set
	 */
	public void setCountUser(String countUser) {
		this.countUser = countUser;
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
			"canDelete", "canSave", "passwordConfirm", "selectedValidity" })
	public void onNewUser() {
		try {
			this.selectedUser = new Authentication();
			CodeItem item = new CodeItem();
			item.setCode("*");
			item.setDescription("(All branches)");
			this.selectedUser.setBranch(item);

			item = new CodeItem();
			item.setCode("*");
			item.setDescription("(All subs)");
			this.selectedUser.setSubBranch(item);

			this.winTitle = "kBureau User - New";
			this.visible = true;
			this.resultMsg = null;
			this.canDelete = false;
			this.canSave = false;
			this.passwordConfirm = null;
			this.editUser = false;
			onDropFSubBranch("");

			this.selectedValidity = null;
			this.validities = null;

		} catch (Exception e) {
			log.error("Error while adding new kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "selectedUser", "winTitle", "visible", "resultMsg",
			"canDelete", "canSave", "passwordConfirm", "selectedValidity",
			"resultMsgValidity", "canSendEmailPermanent" })
	public void onEditUser() {
		try {
			this.winTitle = "kBureau User - Edit";
			this.visible = true;
			this.resultMsg = null;
			this.canDelete = true;
			this.canSave = false;
			this.passwordConfirm = null;
			this.editUser = true;
			onDropFSubBranch(selectedUser.getBranch().getDescription());

			this.selectedValidity = null;
			this.validities = null;
			this.resultMsgValidity = null;

		} catch (Exception e) {
			log.error("Error while editing kBureau user.", e);
		}
	}

	@Command
	@NotifyChange({ "kbureauUsers", "resultMsg", "resultMsgClass", "canSave",
			"resultIcon", "winTitle", "selectedUser", "canSendEmailPermanent" })
	public void onSave() {
		try {
			int myId = UserCredentialManager.getIntance().getLoginId();
			if (myId == 0) {
				flagResultMessage("You do not have permission to save user.",
						false);
				return;
			}

			if (!validateUsername(selectedUser.getUsername())
					|| !validateEmail(selectedUser.getEmail()))
				return;

			boolean newUser = selectedUser.getId() == 0;

			int userId = AuthenticationFacade.saveKbureauUser(myId,
					selectedUser);
			if (userId > 0) {
				if (newUser) {
					this.selectedUser.setId(userId);
					this.winTitle = "kBureau User - Edit";
					this.editUser = true;
				}
				flagResultMessage("Save successfully.", true);
			} else {
				flagResultMessage("Save failed.", false);
			}
			
			// set to null in order to get new updated list with getter
			kbureauUsers = null;

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
						getSelectedUser().getId())) {
					addInvalidMessage(ctx, "The username is already in use.");
					canSave = false;
				}
			}
		};
	}

	public Validator getEmailValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				String email = ctx.getProperty().getValue().toString();
				if (!email.isEmpty() && !Common.validateEmail(email)) {
					addInvalidMessage(ctx, "Invalid email address");
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
	@NotifyChange({ "visible" })
	public void onCancelEdit() {
		visible = false;
	}

	@Command
	@NotifyChange({ "msgDelete" })
	public void onCancelDelete() {
		msgDelete = null;
	}

	@Command
	@NotifyChange({ "msgReset" })
	public void onCancelReset() {
		msgReset = null;
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
	@NotifyChange({ "msgDelete", "kbureauUsers", "selectedUser", "visible" })
	public void onDeleteUser() {
		try {

			int myId = UserCredentialManager.getIntance().getLoginId();
			if (myId == 0) {
				flagResultMessage("You do not have permission to save user.",
						false);
				return;
			}

			this.msgDelete = null;
			AuthenticationFacade.deleteKbureauUser(myId, selectedUser);
			kbureauUsers.remove(selectedUser);
			selectedUser = null;
			visible = false;
		} catch (Exception e) {
			log.error("Error while deleting kBureau user.", e);
		}
	}

	@Command
	@NotifyChange("msgReset")
	public void onConfirmReset() {
		try {
			this.msgReset = "Are you sure you want to reset password to default for user "
					+ selectedUser.getUsername() + "?";
		} catch (Exception e) {
			log.error(
					"Error while resetting default password for kBureau user.",
					e);
		}
	}

	@Command
	@NotifyChange({ "msgReset", "selectedUser" })
	public void onResetPwdDefault() {
		try {
			int myId = UserCredentialManager.getIntance().getLoginId();
			if (myId == 0) {
				flagResultMessage("You do not have permission to save user.",
						false);
				return;
			}
			int id = AuthenticationFacade.resetPasswordDefault(myId,
					selectedUser);
			if (id > 0) {
				selectedUser.setSecurityCode(0);
				msgReset = null;
			}
		} catch (Exception e) {
			log.error("Error while deleting kBureau user.", e);
		}
	}

	// todo:
	@Command
	@NotifyChange({ "canSave", "resultMsg" })
	public void notifyChangingUserInfo(
			@BindingParam("txtUsername") Textbox txtUsername) {
		try {
			resultMsg = null;
			canSave = !txtUsername.getText().isEmpty();
		} catch (Exception e) {
			log.error("Error while notifying changing kbureau user info.", e);
		}
	}

	@Command
	@NotifyChange({ "resultMsg", "resultMsgClass", "canSave", "resultIcon" })
	public void notifyChangeUsername(
			@BindingParam("txtUsername") Textbox txtUsername) {
		try {
			validateUsername(txtUsername.getText());
		} catch (Exception e) {
			log.error("Error while notifying changing username.", e);
		}
	}

	private boolean validateUsername(String username) {
		if (!AuthenticationFacade.isExistUsername(username,
				selectedUser.getId())) {
			resultMsg = null;
			canSave = true;
			return true;
		}
		flagResultMessage(
				"The username is already in use. Please try another one.",
				false);
		canSave = false;
		return false;
	}

	private boolean validateEmail(String email) {
		if (!email.isEmpty() && !Common.validateEmail(email)) {
			return false;
		}
		return true;
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

	private void flagResultMessageValidity(String msg, boolean flagSuccess) {
		resultMsgValidity = msg;
		canSaveValidity = false;
		if (flagSuccess) {
			resultMsgClassValidity = "k-resultEnc-msg";
			resultIconValidity = "/img/app/ok16.png";
		} else {
			resultMsgClassValidity = "k-error";
			resultIconValidity = "/img/app/error16.png";
		}

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

	/**
	 * @return the branches
	 */
	public ListModelList<CodeItem> getBranches() {
		if (branches == null) {
			branches = new ListModelList<CodeItem>(CommonFacade.getBranches());
		}
		return branches;
	}

	/**
	 * @param branches
	 *            the branches to set
	 */
	public void setBranches(ListModelList<CodeItem> branches) {
		this.branches = branches;
	}

	/**
	 * @return the subBranches
	 */
	public ListModelList<CodeItem> getSubBranches() {
		if (subBranches == null) {
			subBranches = new ListModelList<CodeItem>(
					CommonFacade.getSuBranches(param.getBranch().getId()));
		}
		return subBranches;
	}

	/**
	 * @param subBranches
	 *            the subBranches to set
	 */
	public void setSubBranches(ListModelList<CodeItem> subBranches) {
		this.subBranches = subBranches;
	}

	/**
	 * @return the param
	 */
	public ParamUser getParam() {
		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(ParamUser param) {
		this.param = param;
	}

	/**
	 * @return the statuses
	 */
	public ListModelList<CodeItem> getStatuses() {
		if (statuses == null) {
			statuses = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "Active", "Inactive" };
			String[] code = new String[] { "", "A", "I" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				statuses.add(item);
			}
		}

		return statuses;
	}

	/**
	 * @param statuses
	 *            the statuses to set
	 */
	public void setStatuses(ListModelList<CodeItem> statuses) {
		this.statuses = statuses;
	}

	@Command
	@NotifyChange({ "param", "kbureauUsers", "selectedUser" })
	public void onChangeStatus() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "subBranches", "kbureauUsers", "selectedUser" })
	public void onChangeBranch() {
		CodeItem item = new CodeItem();
		item.setCode("");
		item.setDescription("All");
		param.setSubBranch(item);
		onDropSubBranch();
		doSearch();
	}

	public void onDropSubBranch() {
		subBranches.clear();
		subBranches.addAll(CommonFacade
				.getSuBranches(param.getBranch().getId()));
	}

	@Command
	@NotifyChange({ "param", "kbureauUsers", "selectedUser" })
	public void onChangeSubBranch() {
		CodeItem item = new CodeItem();
		item.setId(param.getSubBranch().getSuperId());
		item.setCode(param.getSubBranch().getSuperCode());
		item.setDescription(param.getSubBranch().getSuperDescription());
		param.setBranch(item);
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "kbureauUsers", "selectedUser", "subBranches" })
	public void onClear() {
		param = new ParamUser();
		subBranches = null;
		doSearch();
	}

	/**
	 * @return the fBranches
	 */
	public ListModelList<CodeItem> getfBranches() {
		if (fBranches == null) {
			fBranches = new ListModelList<CodeItem>(
					CommonFacade.getBranchStar());
		}
		return fBranches;
	}

	/**
	 * @param fBranches
	 *            the fBranches to set
	 */
	public void setfBranches(ListModelList<CodeItem> fBranches) {
		this.fBranches = fBranches;
	}

	/**
	 * @return the fSubBranches
	 */
	public ListModelList<CodeItem> getfSubBranches() {
		if (fSubBranches == null) {
			fSubBranches = new ListModelList<CodeItem>(
					CommonFacade.getSuBranchStar(""));
		}
		return fSubBranches;
	}

	/**
	 * @param fSubBranches
	 *            the fSubBranches to set
	 */
	public void setfSubBranches(ListModelList<CodeItem> fSubBranches) {
		this.fSubBranches = fSubBranches;
	}

	/**
	 * @return the fStatuses
	 */
	public ListModelList<CodeItem> getfStatuses() {
		if (fStatuses == null) {
			fStatuses = new ListModelList<CodeItem>();
			String[] desc = new String[] { "Active", "Inactive" };
			String[] code = new String[] { "A", "I" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				fStatuses.add(item);
			}
		}
		return fStatuses;
	}

	/**
	 * @param fStatuses
	 *            the fStatuses to set
	 */
	public void setfStatuses(ListModelList<CodeItem> fStatuses) {
		this.fStatuses = fStatuses;
	}

	@Command
	@NotifyChange({ "fSubBranches" })
	public void onChangeFBranch(@BindingParam("cmbBranch") Combobox cmbBranch,
			@BindingParam("cmbSubBranch") Combobox cmbSubBranch) {
		String branchName = cmbBranch.getText();
		onDropFSubBranch(branchName);
		cmbSubBranch.setText("(All subs)");
		CodeItem item = new CodeItem();
		item.setCode("*");

		userFrm.setField("subBranch", item);
		userFrm.setField("subBranch.description", "(All subs)");

	}

	public void onDropFSubBranch(String branchName) {
		if (fSubBranches != null)
			fSubBranches.clear();
		fSubBranches = new ListModelList<>(
				CommonFacade.getSuBranchStar(branchName));
	}

	@Command
	public void onChangeFSubBranch(
			@BindingParam("cmbBranch") Combobox cmbBranch) {
		CodeItem sub = (CodeItem) userFrm.getField("subBranch");
		CodeItem br = CommonFacade.getBranchBySubCd(sub.getCode());
		cmbBranch.setText(br.getDescription());
		userFrm.setField("branch", br);
	}
	
	@DefaultCommand
    public void defaultAction(){
    
    }

	/**
	 * @return the sexes
	 */
	public ListModelList<CodeItem> getSexes() {
		if (sexes == null) {
			sexes = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "Male", "Female" };
			String[] code = new String[] { "", "M", "F" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				sexes.add(item);
			}
		}
		return sexes;
	}

	/**
	 * @param sexes
	 *            the sexes to set
	 */
	public void setSexes(ListModelList<CodeItem> sexes) {
		this.sexes = sexes;
	}

	/**
	 * @return the fSexes
	 */
	public ListModelList<CodeItem> getfSexes() {
		if (fSexes == null) {
			fSexes = new ListModelList<CodeItem>();
			String[] desc = new String[] { "Male", "Female" };
			String[] code = new String[] { "M", "F" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				fSexes.add(item);
			}
		}
		return fSexes;
	}

	/**
	 * @param fSexes
	 *            the fSexes to set
	 */
	public void setfSexes(ListModelList<CodeItem> fSexes) {
		this.fSexes = fSexes;
	}

	@Command
	public void onChangeFSex() {

	}

	/**
	 * @return the msgReset
	 */
	public String getMsgReset() {
		return msgReset;
	}

	/**
	 * @param msgReset
	 *            the msgReset to set
	 */
	public void setMsgReset(String msgReset) {
		this.msgReset = msgReset;
	}

	/**
	 * @return the rows
	 */
	public ListModelList<CodeItem> getRows() {
		if (rows != null)
			return rows;
		rows = new ListModelList<CodeItem>();
		String[] desc = new String[] { "5", "10", "15", "20", "25", "30", "40",
				"50" };
		String[] code = new String[] { "5", "10", "15", "20", "25", "30", "40",
				"50" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			rows.add(item);
		}
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(ListModelList<CodeItem> rows) {
		this.rows = rows;
	}

	/**
	 * @return the selectedNumRow
	 */
	public CodeItem getSelectedNumRow() {
		if (selectedNumRow != null)
			return selectedNumRow;
		selectedNumRow = new CodeItem();
		selectedNumRow.setCode("10");
		selectedNumRow.setDescription("10");
		return selectedNumRow;
	}

	/**
	 * @param selectedNumRow
	 *            the selectedNumRow to set
	 */
	public void setSelectedNumRow(CodeItem selectedNumRow) {
		this.selectedNumRow = selectedNumRow;
	}

	@Command
	@NotifyChange("selectedNumRow")
	public void onChangeRowPerPage() {
		doSearch();
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
	 * @return the validities
	 */
	public ListModelList<UserValidity> getValidities() {
		if (validities == null) {
			validities = new ListModelList<UserValidity>(
					AuthenticationFacade.getListValidity(selectedUser.getId()));
		}
		return validities;
	}

	/**
	 * @param validities
	 *            the validities to set
	 */
	public void setValidities(ListModelList<UserValidity> validities) {
		this.validities = validities;
	}

	/**
	 * @return the selectedValidity
	 */
	public UserValidity getSelectedValidity() {
		return selectedValidity;
	}

	/**
	 * @param selectedValidity
	 *            the selectedValidity to set
	 */
	public void setSelectedValidity(UserValidity selectedValidity) {
		this.selectedValidity = selectedValidity;
	}

	/**
	 * @return the canSaveValidity
	 */
	public boolean isCanSaveValidity() {
		return canSaveValidity;
	}

	/**
	 * @param canSaveValidity
	 *            the canSaveValidity to set
	 */
	public void setCanSaveValidity(boolean canSaveValidity) {
		this.canSaveValidity = canSaveValidity;
	}

	/**
	 * @return the groupLabelValidityTitle
	 */
	public String getGroupLabelValidityTitle() {
		return groupLabelValidityTitle;
	}

	/**
	 * @param groupLabelValidityTitle
	 *            the groupLabelValidityTitle to set
	 */
	public void setGroupLabelValidityTitle(String groupLabelValidityTitle) {
		this.groupLabelValidityTitle = groupLabelValidityTitle;
	}

	/**
	 * @return the deleteMessageValidity
	 */
	public String getDeleteMessageValidity() {
		return deleteMessageValidity;
	}

	/**
	 * @param deleteMessageValidity
	 *            the deleteMessageValidity to set
	 */
	public void setDeleteMessageValidity(String deleteMessageValidity) {
		this.deleteMessageValidity = deleteMessageValidity;
	}

	@Command
	@NotifyChange({ "selectedValidity", "canSaveValidity", "canDeleteValidity",
			"groupLabelValidityTitle" })
	public void onNewValidity() {
		selectedValidity = new UserValidity();
		selectedValidity.setUserId(selectedUser.getId());

		canSaveValidity = true;
		canDeleteValidity = false;

		groupLabelValidityTitle = "Add New User Validity";
	}

	@Command
	@NotifyChange({ "validities", "resultMsgValidity",
			"resultMsgClassValidity", "resultIconValidity", "canSaveValidity",
			"canDeleteValidity", "selectedUser", "kbureauUsers" })
	public void onSaveValidity() {
		try {
			int myId = UserCredentialManager.getIntance().getLoginId();
			if (myId == 0) {
				flagResultMessageValidity(
						"You do not have permission to save user.", false);
				return;
			}

			boolean newValidity = selectedValidity.getId() == 0;
			int validityId = AuthenticationFacade.saveUserValidity(myId,
					selectedValidity);
			if (validityId > 0) {
				if (newValidity) {
					this.selectedValidity.setId(validityId);
					this.groupLabelValidityTitle = "User Validity - Edit";
				}
				flagResultMessageValidity("Save validity successfully.", true);
			} else {
				flagResultMessageValidity("Save validity failed.", false);
			}

			// set to null in order to get new updated list with getter
			validities = null;
			kbureauUsers = null;
			canDeleteValidity = true;

		} catch (Exception e) {
			log.error("Error while saving user validity.", e);
		}
	}

	@Command
	@NotifyChange({ "selectedValidity" })
	public void onCancelEditValidity() {
		selectedValidity = null;
	}

	@Command
	@NotifyChange({ "validities", "resultMsgValidity",
			"resultMsgClassValidity", "resultIconValidity", "canSaveValidity",
			"canDeleteValidity", "deleteMessageValidity", "selectedValidity" })
	public void onDeleteValidity() {
		try {
			int myId = UserCredentialManager.getIntance().getLoginId();
			if (myId == 0) {
				flagResultMessageValidity(
						"You do not have permission to save user.", false);
				return;
			}

			int validityId = AuthenticationFacade.deleteUserValidity(myId,
					selectedValidity);

			if (validityId > 0) {
				flagResultMessageValidity(
						"The validity is deleted successfully.", true);
				validities.remove(selectedValidity);
			} else {
				flagResultMessageValidity("Failed to delete the validity.",
						false);
			}

			selectedValidity = null;
			deleteMessageValidity = null;
			canDeleteValidity = false;

		} catch (Exception e) {
			log.error("Error while deleting user validity.", e);
		}
	}

	@Command
	@NotifyChange("deleteMessageValidity")
	public void onConfirmDeleteValidity() {
		deleteMessageValidity = "Are you sure you want to delete the selected validity?";
	}

	@Command
	@NotifyChange("deleteMessageValidity")
	public void cancelDeleteValidity() {
		deleteMessageValidity = null;
	}

	@Command
	@NotifyChange({ "groupLabelValidityTitle", "canDeleteValidity",
			"resultMsgValidity", "canSendEmailActing" })
	public void onSelectValidity() {
		groupLabelValidityTitle = "Edit User Validity";
		canDeleteValidity = true;
		resultMsgValidity = null;
	}

	/**
	 * @return the canDeleteValidity
	 */
	public boolean isCanDeleteValidity() {
		return canDeleteValidity;
	}

	/**
	 * @param canDeleteValidity
	 *            the canDeleteValidity to set
	 */
	public void setCanDeleteValidity(boolean canDeleteValidity) {
		this.canDeleteValidity = canDeleteValidity;
	}

	/**
	 * @return the resultMsgValidity
	 */
	public String getResultMsgValidity() {
		return resultMsgValidity;
	}

	/**
	 * @param resultMsgValidity
	 *            the resultMsgValidity to set
	 */
	public void setResultMsgValidity(String resultMsgValidity) {
		this.resultMsgValidity = resultMsgValidity;
	}

	/**
	 * @return the resultMsgClassValidity
	 */
	public String getResultMsgClassValidity() {
		return resultMsgClassValidity;
	}

	/**
	 * @param resultMsgClassValidity
	 *            the resultMsgClassValidity to set
	 */
	public void setResultMsgClassValidity(String resultMsgClassValidity) {
		this.resultMsgClassValidity = resultMsgClassValidity;
	}

	/**
	 * @return the resultIconValidity
	 */
	public String getResultIconValidity() {
		return resultIconValidity;
	}

	/**
	 * @param resultIconValidity
	 *            the resultIconValidity to set
	 */
	public void setResultIconValidity(String resultIconValidity) {
		this.resultIconValidity = resultIconValidity;
	}

	@Command
	@NotifyChange("canSaveValidity")
	public void notifyChangingValidity() {
		canSaveValidity = true;
	}

	@Command
	public void onEmailPermanent() {
		if (selectedUser == null)
			return;
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("The email has been sent successfully.");
		String template = EmailHelper
				.readFileTemplate("kbureau/admin/template_email.html");
		String content = template;
		String title = selectedUser.getSexObj().getCode().equals("M") ? "លោកគ្រូ "
				: "អ្នកគ្រូ ";
		String userFullName = title + selectedUser.getlName() + " "
				+ selectedUser.getfName();
		content = content.replace("##user##", userFullName);
		content = content.replace("##title##", title);
		content = content.replace("##username##", selectedUser.getUsername());
		String psw = selectedUser.getSecurityCode() == 0 ? selectedUser
				.getUsername() : "Your existing password.";

		content = content.replace("##psw##", psw);
		// String resultEnc = EmailHelper.sendEmail(user.getEmail(),
		// "Your kBureau User Account Activated", content);
		String result = EmailHelper.sendEmail(selectedUser.getEmail(),UserCredentialManager.getIntance().getLogin().getEmail(),
				"Your kBureau User Account is Activated", content);
		System.out.println(result);
		Clients.showNotification(strBuilder.toString());
	}

	@Command
	@NotifyChange({ "canSendEmailActing", "selectedValidity" })
	public void onEmailActing() {
		if (selectedUser == null)
			return;
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("The email has been sent successfully.");
		String template = EmailHelper
				.readFileTemplate("kbureau/admin/template_email_validity.html");
		String content = template;
		String title = selectedUser.getSexObj().getCode().equals("M") ? "លោកគ្រូ "
				: "អ្នកគ្រូ ";
		String userFullName = title + selectedUser.getlName() + " "
				+ selectedUser.getfName();
		content = content.replace("##user##", userFullName);
		content = content.replace("##title##", title);
		content = content.replace("##username##", selectedUser.getUsername());
		String psw = selectedUser.getSecurityCode() == 0 ? selectedUser
				.getUsername() : "(Your existing password)";

		content = content.replace("##psw##", psw);
		content = content.replace("##req_dt##", Common.getDateFormat(
				selectedValidity.getRequestDate(), "dd/MMM/YYYY"));
		content = content.replace("##start_dt##", Common.getDateFormat(
				selectedValidity.getStartDate(), "dd/MMM/YYYY"));
		content = content.replace("##end_dt##", Common.getDateFormat(
				selectedValidity.getEndDate(), "dd/MMM/YYYY"));
		// String resultEnc = EmailHelper.sendEmail(user.getEmail(),
		// "Your kBureau User Account Activated", content);
		String result = EmailHelper.sendEmail(selectedUser.getEmail(),UserCredentialManager.getIntance().getLogin().getEmail(),
				"Your kBureau User Account is Activated", content);
		if (result.indexOf("successfully") > 0) {
			AuthenticationFacade.updateCountEmailSent(selectedValidity.getId());
			selectedValidity.setCountEmailSent(1);
		}
		System.out.println(result);
		Clients.showNotification(strBuilder.toString());
	}

	/**
	 * @return the canSendEmailPermanent
	 */
	public boolean isCanSendEmailPermanent() {
		if (selectedUser == null)
			return false;
		canSendEmailPermanent = selectedUser.getUserType() == 0
				&& !selectedUser.getEmail().isEmpty()
				&& selectedUser.getSecurityCode() == 0
				&& !selectedUser.getfName().isEmpty()
				&& !selectedUser.getlName().isEmpty();
		return canSendEmailPermanent;
	}

	/**
	 * @param canSendEmailPermanent
	 *            the canSendEmailPermanent to set
	 */
	public void setCanSendEmailPermanent(boolean canSendEmailPermanent) {
		this.canSendEmailPermanent = canSendEmailPermanent;
	}

	/**
	 * @return the canSendEmailActing
	 */
	public boolean isCanSendEmailActing() {
		if (selectedValidity == null)
			return false;
		canSendEmailActing = !selectedUser.getEmail().isEmpty()
				&& !selectedUser.getfName().isEmpty()
				&& !selectedUser.getlName().isEmpty()
				&& selectedValidity.getCountEmailSent() == 0
				&& selectedValidity.getType().equals("E");
		return canSendEmailActing;
	}

	/**
	 * @param canSendEmailActing
	 *            the canSendEmailActing to set
	 */
	public void setCanSendEmailActing(boolean canSendEmailActing) {
		this.canSendEmailActing = canSendEmailActing;
	}

	/**
	 * @return the userFrm
	 */
	public SimpleForm getUserFrm() {
		return userFrm;
	}

	/**
	 * @param userFrm the userFrm to set
	 */
	public void setUserFrm(SimpleForm userFrm) {
		this.userFrm = userFrm;
	}
}
