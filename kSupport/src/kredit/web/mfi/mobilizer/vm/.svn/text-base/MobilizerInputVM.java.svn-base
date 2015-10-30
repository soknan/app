package kredit.web.mfi.mobilizer.vm;

import java.util.Date;

import javax.persistence.OptimisticLockException;

import org.apache.log4j.Logger;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import com.avaje.ebean.Ebean;

import kredit.web.core.model.Domain;
import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.zk.MySimpleForm;
import kredit.web.mfi.model.Mobilizer;
import kredit.web.mfi.model.facade.MfiFacade;

public class MobilizerInputVM {

	static Logger logger = Logger.getLogger(MobilizerInputVM.class);

	Mobilizer selected;
	Domain domain;

	ListModelList<CodeItem> lstFetchIDType;
	
	boolean bStatus=false;
	
	String lStatus = "Close";
	
	Privilege privilege;

	private MySimpleForm frm = new MySimpleForm();

	@Init
	public void init(@ExecutionArgParam("obj") Object obj) {
		if (obj == null) {
			return;
		}
		selected = (Mobilizer) obj;

		// maker info shown in status bar
		domain = (Domain) selected;
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange({ "selected" })
	public void onNew() {
		selected = null;
	}
	
	@Command	
	public void onStatus(){
		bStatus = true;
		onSave();
		BindUtils.postNotifyChange(null, null, this, "*");
	}


	@Command
	@NotifyChange({ "selected", "domain","lStatus" })
	public void onSave() {
		try {

			if (MfiFacade.isMobilizerExist(selected.getId(),
					UserCredentialManager.getIntance().getLoginUsr()
							.getHomeBranch(), selected.getSex(), selected
							.getIdTypeItem().getCode(), selected.getIdNo())) {
				
				StringBuilder uniqMsg = new StringBuilder();
				uniqMsg.append("<b>A mobilizer with the following info is already exist</b>");
				uniqMsg.append("<hr class=\"k-hr\"/><br/>");
				uniqMsg.append("<table cellpadding=\"2\">");
				uniqMsg.append("<tr>");
				uniqMsg.append("<td>Sex</td>");
				uniqMsg.append("<td>:</td>");
				uniqMsg.append("<td>").append(selected.getSex().equals("F")?"Female":"Male").append("</td>");
				uniqMsg.append("</tr>");
				uniqMsg.append("<tr>");
				uniqMsg.append("<td>ID Type</td>");
				uniqMsg.append("<td>:</td>");
				uniqMsg.append("<td>").append(selected.getIdTypeItem().getDescription()).append("</td>");
				uniqMsg.append("</tr>");
				uniqMsg.append("<tr>");
				uniqMsg.append("<td>ID No</td>");
				uniqMsg.append("<td>:</td>");
				uniqMsg.append("<td>").append(selected.getIdNo()).append("</td>");
				uniqMsg.append("</tr>");
				uniqMsg.append("</table><br/>");
				
				uniqMsg.append("The same info is not allowed. Please input different mobilizer.<br/><br/>");
				
				Clients.showNotification(uniqMsg.toString(), "warning", null,
						"middle_center", -1);

				return;
			}

			StringBuilder strBuilder = Common.createLogStringBuilder();

			if (getSelected().getId() == null) {
				selected.setBrCd(UserCredentialManager.getIntance()
						.getLoginUsr().getHomeBranch());
				selected.setCreateBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());
				selected.setRecordStat("O");
				strBuilder.append(" create new MOBILIZER --> ");
			} else {
				selected.setChangeBy(UserCredentialManager.getIntance()
						.getLoginUsr().getUsername());
				if(bStatus){
					if(selected.getRecordStat().equals("O")){
						selected.setRecordStat("C");
					}else{
						selected.setRecordStat("O");
					}
				}
			}

			if (frm.getDirtyFieldNames().contains("idTypeItem")) {
				selected.setIdType(selected.getIdTypeItem().getCode());
			}

			Ebean.save(selected);

			strBuilder.append(selected.toString());
			Common.addSessionLogCommit(CMD.MOBILIZER_INPUT,
					strBuilder.toString(), new Date());

			domain = (Domain) selected;

			Clients.showNotification("Save successfully", "info", null,
					"middle_center", -1);
		} catch (OptimisticLockException e) {
			logger.error("Error while saving ebean Mobilizer", e);
			Clients.showNotification("Save failed!", "error", null,
					"middle_center", -1);
		}
	}

	/**
	 * @return the selected
	 */
	public Mobilizer getSelected() {
		if (selected != null)
			return selected;

		selected = new Mobilizer();
		selected.setBrCd(UserCredentialManager.getIntance().getLoginUsr()
				.getHomeBranch());

		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(Mobilizer selected) {
		this.selected = selected;
	}

	/**
	 * @return the lstFetchIDType
	 */
	public ListModelList<CodeItem> getLstFetchIDType() {
		if (lstFetchIDType != null)
			return lstFetchIDType;

		lstFetchIDType = new ListModelList<CodeItem>(MfiFacade.getLstFetchIDType());

		return lstFetchIDType;
	}

	/**
	 * @param lstFetchIDType
	 *            the lstFetchIDType to set
	 */
	public void setLstFetchIDType(ListModelList<CodeItem> lstFetchIDType) {
		this.lstFetchIDType = lstFetchIDType;
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain
	 *            the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	/**
	 * @return the frm
	 */
	public MySimpleForm getFrm() {
		return frm;
	}

	/**
	 * @param frm
	 *            the frm to set
	 */
	public void setFrm(MySimpleForm frm) {
		this.frm = frm;
	}

	/**
	 * @return the privilege
	 */
	public Privilege getPrivilege() {
		if (privilege != null)
			return privilege;

		privilege = Common.getPrivilege(CMD.LIST_CB);
		return privilege;
	}

	/**
	 * @param privilege
	 *            the privilege to set
	 */
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}
	
	public boolean isbStatus() {
		return bStatus;
	}

	public void setbStatus(boolean bStatus) {
		this.bStatus = bStatus;
	}

	public String getlStatus() {
		if(selected.getRecordStat()!=null){
			if(selected.getRecordStat().equals("C")){
				lStatus = "Reopen";
			}else{
				lStatus = "Close";
			}
		}
		return lStatus;
	}

	public void setlStatus(String lStatus) {
		this.lStatus = lStatus;
	}
	
}
