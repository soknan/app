package kredit.web.util.transfer.vm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.util.transfer.model.AccTrnf;
import kredit.web.util.transfer.model.Email;
import kredit.web.util.transfer.model.Loan;
import kredit.web.util.transfer.model.LoanCloseTrnf;
import kredit.web.util.transfer.model.LoanTrnf;
import kredit.web.util.transfer.model.LoanWriteOffTrnf;
import kredit.web.util.transfer.model.ParamTransfer;
import kredit.web.util.transfer.model.Saving;
import kredit.web.util.transfer.model.SavingTrnf;
import kredit.web.util.transfer.model.TransferCommon;
import kredit.web.util.transfer.model.facade.TransferFacade;
import kredit.web.util.transfer.utility.EmailThread;
import kredit.web.writeOff.model.Co;
import kredit.web.writeOff.model.ParamCo;
import kredit.web.writeOff.model.facade.WriteOffFacade;

import org.apache.commons.mail.EmailAttachment;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;

public class MainVM {

	ListModelList<Loan> lstLtt;
	ListModelList<Saving> lstLst;
	ListModelList<CodeItem> lstVillageT = new ListModelList<CodeItem>();
	ListModelList<CodeItem> lstCommuneT = new ListModelList<CodeItem>();
	ListModelList<CodeItem> lstProductT = new ListModelList<CodeItem>();

	ListModelList<Co> lstCo;
	ParamCo paramCo = new ParamCo();
	boolean visibleCo;
	ListModelList<CodeItem> filterSubBranches;

	Co selectedCo = new Co();
	Co selectedCoT = new Co();
	Co selectedCoR = new Co();
	int type_co;
	
	public static int CO_TRANSFER = 1;
	public static int CO_RECEIVER = 2;

	private String filePath = Executions.getCurrent().getDesktop().getWebApp()
			.getRealPath("/")
			+ "attachments\\";
	private String fileName = "";

	@Wire("#stf")
	Window winStf;

	Privilege privilege = null;

	@Wire("#lstCommune")
	Listbox lstCommune;

	@Wire("#lstVillage")
	Listbox lstVillage;

	@Wire("#lstProduct")
	Listbox lstProduct;

	private ParamTransfer param = new ParamTransfer();
	private int mode = 1;
	
	ListModelList<CodeItem> filterRequire;
	private CodeItem selectedRequire = new CodeItem();
	
	AccTrnf accTrnf = new AccTrnf();
	private boolean visibleOption = true;
	private boolean visibleTmp = true;
	
	private List<LoanTrnf> lstLoanTrnf = new ArrayList<LoanTrnf>();
	private List<LoanCloseTrnf> lstLoanCloseTrnf = new ArrayList<LoanCloseTrnf>();
	private List<LoanWriteOffTrnf> lstLoanWriteOffTrnf = new ArrayList<LoanWriteOffTrnf>();
	private List<SavingTrnf> lstSavingTrnf = new ArrayList<SavingTrnf>();
	
	// GETTER AND SETTER

	public Privilege getPrivilege() {
		if (privilege == null) {
			privilege = Common.getPrivilege(CMD.LIST_STF);
		}
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public ListModelList<Loan> getLstLtt() {
		return lstLtt;
	}

	public void setLstLtt(ListModelList<Loan> lstLtt) {
		this.lstLtt = lstLtt;
	}

	public ListModelList<CodeItem> getLstVillageT() {
		return lstVillageT;
	}

	public void setLstVillageT(ListModelList<CodeItem> lstVillageT) {
		this.lstVillageT = lstVillageT;
	}

	public ListModelList<CodeItem> getLstCommuneT() {
		return lstCommuneT;
	}

	public void setLstCommuneT(ListModelList<CodeItem> lstCommuneT) {
		this.lstCommuneT = lstCommuneT;
	}

	public ListModelList<CodeItem> getLstProductT() {
		return lstProductT;
	}

	public void setLstProductT(ListModelList<CodeItem> lstProductT) {
		this.lstProductT = lstProductT;
	}

	public ListModelList<Saving> getLstLst() {
		return lstLst;
	}

	public void setLstLst(ListModelList<Saving> lstLst) {
		this.lstLst = lstLst;
	}

	public ListModelList<Co> getLstCo() {
		if (lstCo == null) {
			lstCo = new ListModelList<Co>(TransferFacade.getCoList(paramCo));
		}
		return lstCo;
	}

	public void setLstCo(ListModelList<Co> lstCo) {
		this.lstCo = lstCo;
	}

	public ParamCo getParamCo() {
		return paramCo;
	}

	public void setParamCo(ParamCo paramCo) {
		this.paramCo = paramCo;
	}

	public boolean isVisibleCo() {
		return visibleCo;
	}

	public void setVisibleCo(boolean visibleCo) {
		this.visibleCo = visibleCo;
	}

	public Window getWinStf() {
		return winStf;
	}

	public void setWinStf(Window winStf) {
		this.winStf = winStf;
	}

	public Co getSelectedCo() {
		return selectedCo;
	}

	public void setSelectedCo(Co selectedCo) {
		this.selectedCo = selectedCo;
	}

	public Co getSelectedCoT() {
		return selectedCoT;
	}

	public void setSelectedCoT(Co selectedCoT) {
		this.selectedCoT = selectedCoT;
	}

	public Co getSelectedCoR() {
		return selectedCoR;
	}

	public void setSelectedCoR(Co selectedCoR) {
		this.selectedCoR = selectedCoR;
	}

	public ListModelList<CodeItem> getFilterSubBranches() {
		if (filterSubBranches == null) {
			filterSubBranches = new ListModelList<CodeItem>(
					WriteOffFacade.getSubBranchesListWithAll(0));
		}
		return filterSubBranches;
	}

	public void setFilterSubBranches(ListModelList<CodeItem> filterSubBranches) {
		this.filterSubBranches = filterSubBranches;
	}

	public ParamTransfer getParam() {
		return param;
	}

	public void setParam(ParamTransfer param) {
		this.param = param;
	}
	
	public ListModelList<CodeItem> getFilterRequire() {
		if(filterRequire == null)
		{
			filterRequire = new ListModelList<CodeItem>();
			String[] desc = new String[] {"KREDIT", "OWN"};
			String[] code = new String[] {"K", "O"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				filterRequire.add(item);
			}
		}
		return filterRequire;
	}

	public void setFilterRequire(ListModelList<CodeItem> filterRequire) {
		this.filterRequire = filterRequire;
	}
	
	public CodeItem getSelectedRequire() {
		return selectedRequire;
	}

	public void setSelectedRequire(CodeItem selectedRequire) {
		this.selectedRequire = selectedRequire;
	}

	public AccTrnf getAccTrnf() {
		return accTrnf;
	}

	public void setAccTrnf(AccTrnf accTrnf) {
		this.accTrnf = accTrnf;
	}
	
	public boolean isVisibleOption() {
		return visibleOption;
	}

	public void setVisibleOption(boolean visibleOption) {
		this.visibleOption = visibleOption;
	}
	
	public boolean isVisibleTmp() {
		return visibleTmp;
	}

	public void setVisibleTmp(boolean visibleTmp) {
		this.visibleTmp = visibleTmp;
	}
	
	// END GETTER AND SETTER

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange({ "" })
	public void doSearch() {

	}

	@Command
	@NotifyChange({ "lstVillageT", "lstCommuneT", "lstProductT", "selectedCo",
			"selectedCoT", "selectedCoR" })
	public void onReset() {

		selectedCo = new Co();
		selectedCoT = new Co();
		selectedCoR = new Co();

		lstVillageT = new ListModelList<CodeItem>();
		lstCommuneT = new ListModelList<CodeItem>();
		lstProductT = new ListModelList<CodeItem>();

	}

	// CO Region

	@Command
	@NotifyChange({ "lstCo", "visibleCo", "paramCo" })
	public void onShowFetchCo(@BindingParam("type") int type) {
		type_co = type;
		paramCo = new ParamCo();
		visibleCo = true;
		boolean wait = winStf.hasFellow("winCo");

		if (wait)
			return;

		Executions.createComponents("/transfer/co.zul", winStf, null);
	}

	@Command
	@NotifyChange({ "lstCo" })
	public void onSearchCo() {
		lstCo = null;
	}

	@Command
	@NotifyChange({ "visibleCo" })
	public void onCloseCo() {
		visibleCo = false;
	}

	@Command
	@NotifyChange({ "visibleCo", "selectedCoT", "selectedCoR", "lstCommuneT",
			"lstVillageT", "lstProductT" })
	public void onSelectCo() {
		if (type_co == CO_TRANSFER) {
			selectedCoT = selectedCo;
		}

		if (type_co == CO_RECEIVER) {
			selectedCoR = selectedCo;
		}

		lstCommuneT.clear();
		lstCommuneT.addAll(TransferFacade.getCommune(selectedCoT.getCo_id()));

		lstVillageT.clear();

		lstProductT.clear();
		lstProductT.addAll(TransferFacade.getProduct(selectedCoT.getCo_id()));

		visibleCo = false;
	}

	// End CO Region
	
	public boolean validateTransfer()
	{
		boolean result = true;
		
		if(selectedCoT.getShortName() == null || selectedCoT.getShortName().trim().equals("")) {
			Clients.showNotification("Please select CO. Transfer.", "warning",
					null, "middle_center", -1);
			return false;
		}
		
		if(selectedCoR.getShortName() == null || selectedCoR.getShortName().trim().equals("")) {
			Clients.showNotification("Please select CO. Reciever.", "warning",
					null, "middle_center", -1);
			return false;
		}
		
		if(selectedRequire == null || selectedRequire.getCode().trim().equals("")) {
			Clients.showNotification("Please select Require Type.", "warning",
					null, "middle_center", -1);
			return false;
		}
		
		if(mode == TransferCommon.MODE_PARAMETER) {
			if(!(param.getProducts().size() > 0)) {
				if(!(param.getVillages().size() > 0)) {
					Clients.showNotification("Please select option or choose 'Transfer All'.", "warning",
							null, "middle_center", -1);
					return false;
				}
			}
		}
		
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onConfirmTransfer() {
		if(validateTransfer()) {
			Messagebox.show(
				"This will transfer all the checked items of transfer CO. " + selectedCoT.getFullName() +
				" to receiver CO. " + selectedCoR.getFullName() + ". " +
				"Are you sure you want to transfer this record?",
				"Transfer Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onTransfer();
						}
					}
				});
		}
	}

	@Command
	@NotifyChange({ "accTrnf", "visibleOption" })
	public void onTransfer() {
		
		/* Test if 
		 * transfer day > then 15 days, we need to get data and save to database => Follow all steps.
		 * If < 15 we will transfer directly without getting old data => Skip step 1
		 */
		
		Calendar calendar = Calendar.getInstance();
		//Step 01
		if(calendar.get(Calendar.DATE) > 15) {
			lstLoanTrnf = new ArrayList<LoanTrnf>(TransferFacade.getLoanTrnfList(
					getParameterTransfer(), mode, selectedCoT.getShortName()));
			lstLoanCloseTrnf = new ArrayList<LoanCloseTrnf>(
					TransferFacade.getLoanCloseTrnfList(getParameterTransfer(), mode, selectedCoT.getShortName()));
			lstLoanWriteOffTrnf = new ArrayList<LoanWriteOffTrnf>(
					TransferFacade.getLoanWriteOffTrnfList(getParameterTransfer(), mode, selectedCoT.getShortName()));
			lstSavingTrnf = new ArrayList<SavingTrnf>(TransferFacade.getSavingTrnfList(
					getParameterTransfer(), mode, selectedCoT.getShortName()));
			
			Ebean.save(lstLoanTrnf);
			Ebean.save(lstLoanCloseTrnf);
			Ebean.save(lstLoanWriteOffTrnf);
			Ebean.save(lstSavingTrnf);
		}
		
		//step 02
		accTrnf = TransferFacade.getAccountTransfer(getParameterTransfer(), mode, selectedRequire.getCode(), selectedCoT, selectedCoR);
		accTrnf.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		
		visibleOption = false;
		
		BindUtils.postNotifyChange(null, null, this, "*");
		Ebean.save(accTrnf);
		
		if(calendar.get(Calendar.DATE) > 15) {
		
			// Generate file
		File file = onGenerate();

		// Download file
		onDownload(file);
		
		// Send mail
		// onSendMail();
		
		}

		Clients.showNotification("Transfer process is successful.");
	}

	@Command
	@NotifyChange({ "" })
	public File onGenerate() {
		File file = null;
		try {
			fileName = "Generate-Query-Loan Transfer[From "
					+ selectedCoT.getFullName() + " to "
					+ selectedCoR.getFullName() + "].sql";
			file = new File(fileName);
			BufferedWriter output = new BufferedWriter(new FileWriter(file));

			output.write("--CREATE ON: " + new Date());
			output.newLine();
			output.write("--Generate query for updating database to transfer loan");
			output.newLine();
			output.write("--From CO. " + selectedCoT.getFullName() + " to CO. "
					+ selectedCoR.getFullName());
			output.newLine();

			output.newLine();
			output.write("--LOAN");
			output.newLine();
			
			//Loan Transfer
			for(int i = 0; i < lstLoanTrnf.size(); i++) {
				output.write("UPDATE CLTB_ACCOUNT_MASTER SET FIELD_CHAR_1='" + selectedCoR.getShortName() + "' ");
				output.newLine();
				output.write("WHERE FIELD_CHAR_1='" + selectedCoT.getShortName() + "' AND ACCOUNT_NUMBER='" + lstLoanTrnf.get(i).getAccount_no() + "';");
				output.newLine();
				output.newLine();
			}
			
			//Loan Close Transfer
			for(int i = 0; i < lstLoanCloseTrnf.size(); i++) {
				output.write("UPDATE CLTB_ACCOUNT_MASTER SET FIELD_CHAR_1='" + selectedCoR.getShortName() + "' ");
				output.newLine();
				output.write("WHERE FIELD_CHAR_1='" + selectedCoT.getShortName() + "' AND ACCOUNT_NUMBER='" + lstLoanCloseTrnf.get(i).getAccount_no() + "';");
				output.newLine();
				output.newLine();
			}
			
			//Loan Write-Off Transfer
			for(int i = 0; i < lstLoanWriteOffTrnf.size(); i++) {
				output.write("UPDATE CLTB_ACCOUNT_MASTER SET FIELD_CHAR_1='" + selectedCoR.getShortName() + "' ");
				output.newLine();
				output.write("WHERE FIELD_CHAR_1='" + selectedCoT.getShortName() + "' AND ACCOUNT_NUMBER='" + lstLoanWriteOffTrnf.get(i).getAccount_no() + "';");
				output.newLine();
				output.newLine();
			}
			
			output.newLine();
			output.write("--SAVING");
			output.newLine();
			
			//Saving Transfer
			for(int i = 0; i < lstSavingTrnf.size(); i++) {
				output.write("UPDATE CSTM_FUNCTION_USERDEF_FIELDS SET FIELD_VAL_1='" + selectedCoR.getShortName() + "' ");
				output.newLine();
				output.write("WHERE FIELD_VAL_1='" + selectedCoT.getShortName() + "' AND SUBSTR(REC_KEY,5,16)='" + lstSavingTrnf.get(i).getAccount_no() + "' AND FUNCTION_ID IN('STDCUSAC','STDCUSTD');");
				output.newLine();
				output.newLine();
			}
			
			output.newLine();
			output.close();

			/*
			 * This block of code only needed when require send mail //Generate
			 * file and copy it to folder 'Attachments' InputStream is = new
			 * FileInputStream(file); OutputStream os = new
			 * FileOutputStream(filePath + fileName); //Copy file to server
			 * Files.copy(os, is);
			 */

		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	@Command
	@NotifyChange({ "" })
	public void onDownload(File file) {
		try {
			Filedownload.save(file, "");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange({ "" })
	public void onSendMail() {
	
		// Send mail
		String text = "Dear DBA team," + "\n";
		text += "There is recently a case of 'Loan Transfer' in kSupport System:"
				+ "\n";
		text += "- From CO. " + selectedCoT.getFullName() + " to CO. "
				+ selectedCoR.getFullName() + "\n";
		text += "- Transfer Date: " + new Date() + "\n";
		text += "- Transfer By: Puthi SUM" + "\n\n";
		text += "Please find a script[" + fileName
				+ "] in attachment to update database." + "\n";

		String subject = "Apache.Mail";
		List<Email> sendToLst = new ArrayList<Email>();
		Email sendTo = new Email();
		sendTo.setEmail("puthi_sum@kredit.com.kh");
		sendTo.setName("Puthi SUM");
		sendToLst.add(sendTo);

		List<EmailAttachment> lstAttachment = new ArrayList<EmailAttachment>();

		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(filePath + fileName);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment
				.setDescription("SQL file that generate for updating database");
		attachment.setName(fileName);

		lstAttachment.add(attachment);

		EmailThread emailThread = new EmailThread(text, subject, sendToLst,
				lstAttachment);
		emailThread.start();
	}

	@Command
	@NotifyChange({ "" })
	public void onCheckTransferAll(@BindingParam("checked") boolean checked) {

		if (checked) {
			lstCommune.setMold("select");
			lstVillage.setMold("select");
			lstProduct.setMold("select");
			mode = TransferCommon.MODE_ALL;
		} else {
			lstCommune.setMold("");
			lstVillage.setMold("");
			lstProduct.setMold("");
			mode = TransferCommon.MODE_PARAMETER;
		}

		lstCommune.setDisabled(checked);

		lstVillage.setDisabled(checked);

		lstProduct.setDisabled(checked);
	}

	@Command
	@NotifyChange({ "lstVillageT", "param" })
	public void onPopulateVillage() {
		lstVillageT.clear();
		param.getVillages().clear();
		if (param.getCommunes().size() > 0) {
			lstVillageT.addAll(TransferFacade.getVillage(
					selectedCoT.getCo_id(),
					TransferFacade.getStringFromList(param.getCommunes())));

			param.getVillages().addAll(lstVillageT);
		}
	}

	public HashMap<String, String> getParameterTransfer() {
		HashMap<String, String> maps = new HashMap<String, String>();
		
		if(lstVillageT.size() == param.getVillages().size()) {
			if(lstCommuneT.size() == param.getCommunes().size()) {
				maps.put(TransferCommon.PARAMETER_COMMUNES, "");
				maps.put(TransferCommon.PARAMETER_VILLAGES, "");
			} else {
				maps.put(TransferCommon.PARAMETER_COMMUNES,
					TransferFacade.getStringFromList(param.getCommunes()));
				maps.put(TransferCommon.PARAMETER_VILLAGES, "");
			}
		} else {
			maps.put(TransferCommon.PARAMETER_COMMUNES, "");
			maps.put(TransferCommon.PARAMETER_VILLAGES, 
					TransferFacade.getStringFromList(param.getVillages()));
		}
		
		if(lstProductT.size() == param.getProducts().size()) {
			maps.put(TransferCommon.PARAMETER_PRODUCTS, "");
		} else {
			maps.put(TransferCommon.PARAMETER_PRODUCTS, 
					TransferFacade.getStringCodeFromList(param.getProducts()));
		}
		
		return maps;
	}
}
