package kredit.web.kbureau.viewmodel.guarantor;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.datatype.DatatypeConfigurationException;

import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.authentication.model.Login;
import kredit.web.kbureau.model.enquiry.Enquiry;
import kredit.web.kbureau.model.enquiry.ParamEnquiry;
import kredit.web.kbureau.model.enquiry.ValidationMessage;
import kredit.web.kbureau.model.facade.enquiry.CbcFacade;
import kredit.web.kbureau.model.facade.enquiry.CoborrowerFacade;
import kredit.web.kbureau.model.facade.enquiry.EnquiryFacade;
import kredit.web.kbureau.model.facade.enquiry.GuarantorFacade;
import kredit.web.kbureau.model.facade.report.CbcReportFacade;
import kredit.web.kbureau.model.report.CbcReport;
import kredit.web.kbureau.model.report.CodeItem;
import kredit.web.kbureau.util.XmlHelper;

import org.datacontract.schemas.kservice.model.datacontracts.Authentication;
import org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry;
import org.tempuri.IKserviceProxy;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.sun.xml.internal.ws.resources.ClientMessages;

public class GuarantorVM {
	ListModelList<Enquiry> lstGuarantor;
	Enquiry selectedEnquiry = new Enquiry();
	ParamEnquiry param = new ParamEnquiry();	
	Enquiry cbcM = new Enquiry();
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	
	Login login;
	ValidationMessage vMessage = new ValidationMessage();
	boolean visibleConf = false;
	boolean visibleEnquiry;
	
	boolean visibleEnquiryNew;
	
	CbcReport selected = new CbcReport();
	
	boolean visible;
	String rptUrl;
	
	ListModelList<kredit.web.core.util.model.CodeItem> lstIDType1;
	ListModelList<kredit.web.core.util.model.CodeItem> lstIDType2;
	ListModelList<kredit.web.core.util.model.CodeItem> lstIDType3;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstMaritalStatus;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstGender;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstProvince;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstDistrict;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstCommune;
	ListModelList<kredit.web.core.util.model.CodeItem> lstVillage;
	ListModelList<kredit.web.core.util.model.CodeItem> lstProvinceD;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstDistrictD;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstCommuneD;
	ListModelList<kredit.web.core.util.model.CodeItem> lstVillageD;
	ListModelList<kredit.web.core.util.model.CodeItem> lstProvinceE;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstDistrictE;	
	ListModelList<kredit.web.core.util.model.CodeItem> lstCommuneE;
	ListModelList<kredit.web.core.util.model.CodeItem> lstVillageE;
	ListModelList<kredit.web.core.util.model.CodeItem> lstAddressType;
	ListModelList<kredit.web.core.util.model.CodeItem> lstAddressTypeE;
	ListModelList<kredit.web.core.util.model.CodeItem> lstSelfEmployed;
	ListModelList<kredit.web.core.util.model.CodeItem> lstPhoneNumberType;
	ListModelList<kredit.web.core.util.model.CodeItem> lstCurrency;
	ListModelList<kredit.web.core.util.model.CodeItem> lstCurrency1;
	ListModelList<kredit.web.core.util.model.CodeItem> lstProductType;
	ListModelList<kredit.web.core.util.model.CodeItem> lstAccountType;
	
	ListModelList<kredit.web.core.util.model.CodeItem> lstEnquiryType;
	kredit.web.core.util.model.CodeItem enquiryType = new kredit.web.core.util.model.CodeItem();
	
	@Wire("#gualst")
	Window enqList;	
	
	@Command
	@NotifyChange({ "lstGuarantor" })
	public void doSearch()
	{
		lstGuarantor = null;
	}
	
	Boolean validate = true;
	
	@Init
	public void init(@ExecutionArgParam("obj") Object obj) {
		if (obj == null) {
			return;
		}		
	}	
	
	@Command
	@NotifyChange({ "vMessage", "validate"})
	public void formValidation(){
		
		/*if(!cbcM.getMemberReference().endsWith("_G")){
			Clients.showNotification(
					"Member reference should be end with '_G'.",
					Clients.NOTIFICATION_TYPE_WARNING, null,
					"middle_center", 0);			
		}*/
		
		validate = true;		
		vMessage.setvMemberReference("");
		if(cbcM.getMemberReference() == null || cbcM.getMemberReference().trim().equals("")){			
			vMessage.setvMemberReference("Account Number cannot be blank");
			validate = false;
		}
		vMessage.setvAmount("");
		if (cbcM.getAmount() == null || cbcM.getAmount().trim().equals("")){
			vMessage.setvAmount("Amount cannot be blank");
			validate = false;
		}
		vMessage.setvCurrency("");
		if(cbcM.getCurrency() == null || cbcM.getCurrency().trim().equals("")){
			vMessage.setvCurrency("Currency cannot be blank");
			validate = false;
		}
		vMessage.setvIdNumber1("");
		vMessage.setvIdExpiry("");
		if(cbcM.getIdType1().equals("N")){
			if(cbcM.getIdNumber1().length()!=9 || !cbcM.getIdNumber1().matches("\\d+")){
				vMessage.setvIdNumber1("ID Card Number must be the number of 9 or 10 digits");
				validate = false;
			}else if(cbcM.getIdExpiryDate1() == null){
				vMessage.setvIdExpiry("ID Expiry Date cannot be blank");
				validate = false;
			}
		}
		
		vMessage.setvIdType2("");
		vMessage.setvIdNumber2("");
		vMessage.setvIdExpiry2("");
		if(cbcM.getIdType2()!=null){
			if(cbcM.getIdNumber2()!=null){
				if(cbcM.getIdType2().equals("N")){
					if(cbcM.getIdNumber2().length()!=9 || !cbcM.getIdNumber2().matches("\\d+")){
						vMessage.setvIdNumber2("ID Card Number must be the number of 9 or 10 digits");
						validate = false;
					}
					if(cbcM.getIdExpiryDate2() == null){
						vMessage.setvIdExpiry("ID Expiry Date cannot be blank");
						validate = false;
					}
				}
			}else{
				vMessage.setvIdNumber2("ID Card Number cannot be blank");
				validate = false;
			}
		}else if(cbcM.getIdNumber2()!=null){
			vMessage.setvIdType2("ID Type cannot be blank");
			validate = false;
		}
		
		vMessage.setvIdType3("");
		vMessage.setvIdNumber3("");
		vMessage.setvIdExpiry3("");
		if(cbcM.getIdType3()!=null){
			if(cbcM.getIdNumber3()!=null){
				if(cbcM.getIdType3().equals("N")){
					if(cbcM.getIdNumber3().length()!=9 || !cbcM.getIdNumber3().matches("\\d+")){
						vMessage.setvIdNumber3("ID Card Number must be the number of 9 or 10 digits");
						validate = false;
					}
					if(cbcM.getIdExpiryDate3() == null){
						vMessage.setvIdExpiry("ID Expiry Date cannot be blank");
						validate = false;
					}
				}
			}else{
				vMessage.setvIdNumber3("ID Card Number cannot be blank");
				validate = false;
			}
		}else if(cbcM.getIdNumber3()!=null){
			vMessage.setvIdType3("ID Type cannot be blank");
			validate = false;
		}		
		
		vMessage.setvDateofbirth("");		
		if(cbcM.getDob() == null){
			vMessage.setvDateofbirth("Date of birth cannot be blank");
			validate = false;
		}else if(checkAge(cbcM.getDob())<18){
			vMessage.setvDateofbirth("Date of Birth: client must be at least 18 years old");
			validate = false;
		}
		vMessage.setvGender("");
		if(cbcM.getSex() == null || cbcM.getSex().trim().equals("")){
			vMessage.setvGender("Gender cannot be blank");
			validate = false;
		}
		vMessage.setvMaritalStatus("");
		if(cbcM.getMaritalStatus() == null || cbcM.getMaritalStatus().trim().equals("")){
			vMessage.setvMaritalStatus("Marital Status cannot be blank");
			validate = false;
		}
		vMessage.setvNationality("");
		if(cbcM.getNationalityCode() == null || cbcM.getNationalityCode().trim().equals("")){
			vMessage.setvNationality("Nationality code cannot be blank");
			validate = false;
		}
		vMessage.setvConCountry("");
		if(cbcM.getdCountry() == null || cbcM.getdCountry().trim().equals("")){
			vMessage.setvConCountry("Country cannot be blank");
			validate = false;
		}
		vMessage.setvConProvince("");
		if(cbcM.getdProvince() == null || cbcM.getdProvince().trim().equals("")){
			vMessage.setvConProvince("Province cannot be blank");
			validate = false;
		}
		vMessage.setvFirstNameKH("");
		if(cbcM.getFirstNameKH() == null || cbcM.getFirstNameKH().trim().equals("")){
			vMessage.setvFirstNameKH("First name cannot be blank");
			validate = false;
		}else if(checkUnicode(cbcM.getFirstNameKH(), true)){
			vMessage.setvFirstNameKH("First name is not enter in Khmer Unicode");
			validate = false;
		}
		vMessage.setvLastNameKH("");
		if(cbcM.getLastNameKH() == null || cbcM.getLastNameKH().trim().equals("")){
			vMessage.setvLastNameKH("Last name cannot be blank");
			validate = false;
		}else if(checkUnicode(cbcM.getLastNameKH(),true)){
			vMessage.setvLastNameKH("Last name is not enter in Khmer Unicode");
			validate = false;
		}
		vMessage.setvFirstNameEN("");
		if(cbcM.getFirstNameEN() == null || cbcM.getFirstNameEN().trim().equals("")){
			vMessage.setvFirstNameEN("First name cannot be blank");
			validate = false;
		}
		vMessage.setvLastNameEN("");
		if(cbcM.getLastNameEN() == null || cbcM.getLastNameEN().trim().equals("")){
			vMessage.setvLastNameEN("Last name cannot be blank");
			validate = false;
		}
		vMessage.setvAddressline1("");
		if(cbcM.getAddressline1() == null || cbcM.getAddressline1().trim().equals("")){
			vMessage.setvAddressline1("Address line 1 cannot be blank");
			validate = false;
		}
		vMessage.setvAddCity("");
		if(cbcM.getCity() == null || cbcM.getCity().trim().equals("")){
			vMessage.setvAddCity("City cannot be blank");
			validate = false;
		}
		vMessage.setvAddCountry("");
		if(cbcM.getCountry() == null || cbcM.getCountry().trim().equals("")){
			vMessage.setvAddCountry("Country cannot be blank");
			validate = false;
		}
		vMessage.setvPhone1("");
		if(cbcM.getPhone1().getType().equals("U") && !cbcM.getPhone1().getPhoneNumber().equals("000")){
			vMessage.setvPhone1("Invalid Phone number. Phone Type is Unknown.");
			validate = false;
		}
		if(!cbcM.getPhone1().getType().equals("U") && cbcM.getPhone1().getPhoneNumber().equals("000")){
			vMessage.setvPhone1("Invalid Phone number. Phone Type is not Unknown.");
			validate = false;
		}
		if(!cbcM.getPhone1().getType().equals("U") && !cbcM.getPhone1().getPhoneNumber().equals("000")){
			if(checkPhoneNumber(cbcM.getPhone1().getPhoneNumber())){
				vMessage.setvPhone1("Invalid Phone number");
				validate = false;
			}
		}
		
		vMessage.setvPhone2("");
		if(cbcM.getPhone2().getType().equals("U") && !cbcM.getPhone2().getPhoneNumber().equals("000")){
			vMessage.setvPhone2("Invalid Phone number. Phone Type is Unknown.");
			validate = false;
		}
		if(!cbcM.getPhone2().getType().equals("U") && cbcM.getPhone2().getPhoneNumber().equals("000")){
			vMessage.setvPhone2("Invalid Phone number. Phone Type is not Unknown.");
			validate = false;
		}
		if(!cbcM.getPhone2().getType().equals("U") && !cbcM.getPhone2().getPhoneNumber().equals("000")){
			if(checkPhoneNumber(cbcM.getPhone2().getPhoneNumber())){
				vMessage.setvPhone2("Invalid Phone number");
				validate = false;
			}
		}
		
		vMessage.setvSelfEmployed("");
		if(cbcM.getSelfEmployed() == null || cbcM.getSelfEmployed().trim().equals("")){
			vMessage.setvSelfEmployed("Self Employed cannot be blank");
			validate = false;
		}
		vMessage.setVlengthOfServince("");
		if(cbcM.getLengthOfServince() == 0){
			vMessage.setVlengthOfServince("Length of Service cannot be zero");
			validate = false;
		}
		vMessage.setvOccupation("");
		if(cbcM.getOccupation() == null || cbcM.getOccupation().trim().equals("")){
			vMessage.setvOccupation("Occupation cannot be blank");
			validate = false;
		}
		vMessage.setvIncome("");
		//if(cbcM.getTotalMonthlyIncome() == null || cbcM.getTotalMonthlyIncome().trim().equals("") || Integer.parseInt(cbcM.getTotalMonthlyIncome()) == 0){
		if(cbcM.getTotalMonthlyIncome() == null || cbcM.getTotalMonthlyIncome() == 0){	
		vMessage.setvIncome("Total monthly Income cannot be zero");
			validate = false;
		}
		vMessage.setvOccCurrency("");
		if(cbcM.getCurrency1() == null || cbcM.getCurrency1().trim().equals("")){
			vMessage.setvOccCurrency("Currency cannot be blank");
			validate = false;
		}
		vMessage.setvEmployerName("");
		if(cbcM.getEmployerName() == null || cbcM.getEmployerName().trim().equals("")){
			vMessage.setvEmployerName("Employer Name cannot be blank");
			validate = false;
		}
		vMessage.setvEmployerAddLine1("");
		if(cbcM.getEmployerAddressLine1() == null || cbcM.getEmployerAddressLine1().trim().equals("")){
			vMessage.setvEmployerAddLine1("Employer Address Line 1 cannot be blank");
			validate = false;
		}
		vMessage.setvCity("");
		if(cbcM.getEmployerCity() == null || cbcM.getEmployerCity().trim().equals("")){
			vMessage.setvCity("City cannot be blank or Unicode letters");
			validate = false;
		}else if(checkUnicode(cbcM.getEmployerCity(), false)){
			vMessage.setvCity("Employer City must be typed in English");
			validate = false;
		}
		
	}
	
	@NotifyChange({ "cbcM"})
	private void checkReqButton() {
		if(!(login.getSubBranchCode().equals("*")) || ((login.getSubBranchCode().equals("*")) && (login.getBranchCode().equals("HQ")))){
			cbcM.setReqButton("sbm");			
			//System.out.println("getbrCodeHQ = " + login.getBranchCode() + "vs" + cbcM.getReqButton());
		}
	}
	
	private boolean checkUnicode(String str, boolean name) {
		if(!str.matches("\\A\\p{ASCII}*\\z")){
			if(name==true){
        		return false;
        	}else{
        		return true;
        	}
		}else{
			if(name==true){
        		return true;
        	}else{
        		return false;
        	}
		}
		
	}
	
	//check phone length
	private boolean checkPhoneNumber(String ph) {
		if(ph.length()<3) return true;
		String[] tmp = {"031", "038", "071","076","088","096","097"};
		Pattern pattern;
		if(Arrays.asList(tmp).contains(ph.substring(0,3))){
			pattern = Pattern.compile("\\d{3}\\d{7}");
		}else{
			pattern = Pattern.compile("\\d{3}\\d{6}");
		}		
		Matcher matcher = pattern.matcher(ph);
 
	      if (matcher.matches()) {
	    	  return false;
	      }
	      else
	      {
	    	  return true;
	      }
		/*if(ph == null || ph.length() == 0 || ph.length() == 7 || ph.length() == 3 || ph.length() == 6 || ph.length() == 9 || ph.length() == 10){
			return false;
		}*/
		
	}

	//check is it number
	private boolean checkNumber(String number) { 
		number = number.replaceAll("\\s","");	
		if (number.matches("[0-9]+") && number.length() > 2){
			return false;
		}
		return true;
	}

	public static int checkAge(Date dateOfBirth) {

	    Calendar today = Calendar.getInstance();
	    Calendar birthDate = Calendar.getInstance();

	    int age = 0;

	    birthDate.setTime(dateOfBirth);
	    //if (birthDate.after(today)) {
	      //  throw new IllegalArgumentException("Can't be born in the future");
	    //}

	    age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

	    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year   
	    if ( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
	            (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
	        age--;

	     // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
	    }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
	              (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
	        age--;
	    }

	    return age;
	}
	
	public GuarantorVM(){
		login = UserCredentialManager.getIntance().getLogin();
		
		if((login.getBranchCode().equals("*")) && login.getSubBranchCode().equals("*"))	//Admin
		{
			param = new ParamEnquiry();
			
			param.setStartDate(new Date());
			param.setEndDate(new Date());
		}
		else if(!(login.getBranchCode().equals("*")) && login.getSubBranchCode().equals("*"))	//BM, ABM
		{
			CodeItem branch = EnquiryFacade.getBranch(login.getBranchCode());
			param.getBranch().setId(branch.getId());
			param.getBranch().setCode(branch.getCode());
			param.getBranch().setDescription(branch.getDescription());
		}
		else if(!login.getBranchCode().equals("HQ"))	//SBM, or lower
		{
			CodeItem subBranch = EnquiryFacade.getSuBranch(login.getSubBranchCode());
			param.getSubBranch().setId(subBranch.getId());
			param.getSubBranch().setCode(subBranch.getCode());
			param.getSubBranch().setDescription(subBranch.getDescription());
			
			param.getBranch().setId(subBranch.getSuperId());
			param.getBranch().setCode(subBranch.getSuperCode());
			param.getBranch().setDescription(subBranch.getSuperDescription());
		}
		else if(login.getBranchCode().equals("HQ"))	//HQ
		{
			CodeItem branch = EnquiryFacade.getBranch(login.getBranchCode());
			param.getBranch().setId(branch.getId());
			param.getBranch().setCode(branch.getCode());
			param.getBranch().setDescription(branch.getDescription());
			
			param.getSubBranch().setId(0);
			param.getSubBranch().setCode("");
			param.getSubBranch().setDescription("All");
		}
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange({ "cbcM", "visibleEnquiry", "vMessage","validate","visible" })
	public void onView(){
		visible = false;
		cbcM = new Enquiry();
		vMessage = new ValidationMessage();
		cbcM = GuarantorFacade.getGuarantor(selectedEnquiry.getMemberReference());
		cbcM.setMemberReference(cbcM.getMemberReference()+"_G");
		checkReqButton();
		formValidation();
		if(!validate)
		{
			validate = true;
			Clients.showNotification("Data validation failed. Please verify before continue.",
				       "warning", null, "middle_center", -1);
		}
		visibleEnquiry = true;
		if(enqList.hasFellow("cbc")){			
			return;
		}		
		Executions.createComponents("/kbureau/guarantor/Enquiry.zul",enqList, null);
	}
	
	@Command	
	@NotifyChange({ "cbcM", "visibleEnquiryNew", "vMessage","visible" })
	public void onNew(){		
		vMessage = new ValidationMessage();			
		cbcM = new Enquiry();
		cbcM.setNationalityCode("KHM");
		cbcM.setCountry("KHM");
		cbcM.setdCountry("KHM");
		cbcM.setEmploymentType("C");
		cbcM.setEmployerCountryCode("KHM");	
		cbcM.setNumberOfApplicants(1);
		cbcM.setLengthOfServince(1);
	
		checkReqButton();		
		visibleEnquiryNew = true;
		visible = false;
		if(enqList.hasFellow("cbcNew")){			
			return;
		}		
		Executions.createComponents("/kbureau/guarantor/EnquiryNew.zul",enqList, null);
	}
	
	@Command
	@NotifyChange({ "visibleConf", "vMessage","validate","cbcM"})
	public void enquiryConfirmation()
	{			
		
		if(validate)
			formValidation();
		
		visibleConf = validate;
		if(validate)
		{	
			boolean wait = enqList.hasFellow("confirmation");
			  if(wait)
				  return;

			  Executions.createComponents("/kbureau/enquiry/confirmEnquiry.zul", enqList, null);
			
		}
		else{	
			validate = true;			
			Clients.showNotification("Data validation failed. Please verify before continue.",
				       "warning", null, "middle_center", -1);
		}
	}
	
	@Command
	@NotifyChange({ "visibleConf", "vMessage","validate","cbcM"})
	public void enquiryConfirmationNew()	{		
		if(validate)
			formValidation();
		
		visibleConf = validate;
		if(validate)
		{	
			boolean wait = enqList.hasFellow("confirmation");
			  if(wait)
				  return;

			  Executions.createComponents("/kbureau/enquiry/confirmEnquiry.zul", enqList, null);
			
		}
		else{	
			validate = true;			
			Clients.showNotification("Data validation failed. Please verify before continue.",
				       "warning", null, "middle_center", -1);
		}
	}
	
	@Command
	@NotifyChange({ "rptUrl", "visible", "selected", "visibleConf", "lstGuarantor"})
	public void onTestRequestCBC() throws DatatypeConfigurationException {
		
		visibleConf = false;
		
		if(cbcM.getIdType1().equals("")) {
			if(cbcM.getIdType2() != null){
				cbcM.setIdType1(cbcM.getIdType2());
				cbcM.setIdNumber1(cbcM.getIdNumber2());
				
				cbcM.setIdType2(null);
				cbcM.setIdNumber2(null);
			
			}else if(cbcM.getIdType3() != null){
				cbcM.setIdType1(cbcM.getIdType3());
				cbcM.setIdNumber1(cbcM.getIdNumber3());
				
				cbcM.setIdType3(null);
				cbcM.setIdNumber3(null);
			}
			
		}	

		//kServer for UAT: http://192.168.100.2:2013/services/Kservice.svc/basic
		//IKserviceProxy kService = new IKserviceProxy("http://192.168.100.2:2013/services/Kservice.svc/basic");
		IKserviceProxy kService = new IKserviceProxy();
		selected = null;
		StringBuilder strXML = new StringBuilder();
		strXML.append("?"); // this char will be removed by service.
		strXML.append(XmlHelper.generateXmlEnquiry(cbcM));

		RequestEnquiry requestEnquiry = new RequestEnquiry();
		requestEnquiry.setRequest(strXML.toString());
		requestEnquiry.setLoanId(cbcM.getLoanID());
		requestEnquiry.setApplicantType("P");
		requestEnquiry.setRefNumber(cbcM.getMemberReference());
		requestEnquiry.setProduct(cbcM.getProductCode());
		requestEnquiry.setPurpose(cbcM.getLoanPurpose());
		requestEnquiry.setFund(cbcM.getLoanFund());

		Authentication auth = new Authentication();
		auth.setId(UserCredentialManager.getIntance().getLoginId());

		try {
			rptUrl = kService.sendEnquiryRequestToCbc(Common.TOKEN_STRING,
			
					requestEnquiry, auth, Common.FOR_UAT);
			if(rptUrl == null) return;
			getSelected().setId(Integer.parseInt(Common.getCbcReportIdFromUrl(rptUrl)));
			
			rptUrl = Common.REPORT_URL + rptUrl;
			visible = true;
			System.out.println("URL  " + rptUrl);
			if(enquiryType.getCode().equals("G")){
				GuarantorFacade.enquirySave(selected.getId());
			}else if (enquiryType.getCode().equals("C")) {
				CoborrowerFacade.enquirySave(selected.getId());
			}else{
				EnquiryFacade.enquirySave(selected.getId());
			}
			
			lstGuarantor = null;
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Command
	public void onDecision(){
		Executions.createComponents("/kbureau/report/Decision.zul", enqList, null);
	}
	
	@Command
	@NotifyChange({ "lst", "visible", "visibleEnquiry","visibleEnquiryNew" })
	public void onSave(@BindingParam("win") Window winDecision){
		int id = CbcReportFacade.saveDecision(selected);
		if(id != 1)
			Clients.showNotification("Save failed.", "error", null, "middle_center", -1);
		winDecision.detach();
		if(id == 1)
		{
			visible = false;
			visibleEnquiry = false;
			visibleEnquiryNew= false;
			Clients.showNotification("Decision successfully saved.");
		}
		
	lstGuarantor = null;
		
	}
	
	@Command
	@NotifyChange({ "lst", "visible", "visibleEnquiry","visibleEnquiryNew" })
	public void onApproveClose () {
		getSelected().setDecision(1);
		int id = CbcReportFacade.saveDecision(selected);
		if(id != 1)
			Clients.showNotification("Approve error.", "error", null, "middle_center", -1);
		
		if(id == 1)
		{
			visible = false;
			visibleEnquiry = false;
			visibleEnquiryNew = false;
			Clients.showNotification("Successfully approved.");
		}
		
		lstGuarantor = null;
		
	}	
	
	public ListModelList<Enquiry> getLstGuarantor() {
		if(lstGuarantor==null){
			lstGuarantor = new ListModelList<>(GuarantorFacade.getGuarantorList(param));
		}
		return lstGuarantor;
	}
	public void setLstGuarantor(ListModelList<Enquiry> lstGuarantor) {
		this.lstGuarantor = lstGuarantor;
	}	
	public Enquiry getSelectedEnquiry() {
		return selectedEnquiry;
	}

	public void setSelectedEnquiry(Enquiry selectedEnquiry) {
		this.selectedEnquiry = selectedEnquiry;
	}

	public CbcReport getSelected() {		
		if(selected == null)
		{
			selected = new CbcReport();
			selected.setName(cbcM.getFullNameKH());
			selected.setLoanId(cbcM.getMemberReference());
			selected.setAmount(Double.parseDouble(cbcM.getAmount()));
			selected.setCurrency(cbcM.getCurrency());
		}		
		return selected;		
	}

	public void setSelected(CbcReport selected) {
		this.selected = selected;
	}

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
	public void setRows(ListModelList<CodeItem> rows) {
		this.rows = rows;
	}
	public CodeItem getSelectedNumRow() {
		if (selectedNumRow != null)
			return selectedNumRow;
		selectedNumRow = new CodeItem();
		selectedNumRow.setCode("10");
		selectedNumRow.setDescription("10");
		return selectedNumRow;
	}
	public void setSelectedNumRow(CodeItem selectedNumRow) {
		this.selectedNumRow = selectedNumRow;
	}
	public ParamEnquiry getParam() {
		return param;
	}
	public void setParam(ParamEnquiry param) {
		this.param = param;
	}
	
	ListModelList<CodeItem> rptTypes;
	ListModelList<CodeItem> rptStatuses;
	ListModelList<CodeItem> currencies;
	ListModelList<CodeItem> branches;
	ListModelList<CodeItem> subBranches;
	
	public ListModelList<CodeItem> getRptTypes() {
		if (rptTypes == null) {
			rptTypes = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "Standard", "Lite" };
			String[] code = new String[] { "", "A", "L" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				rptTypes.add(item);
			}
		}
		return rptTypes;
	}

	/**
	 * @param rptTypes
	 *            the rptTypes to set
	 */
	public void setRptTypes(ListModelList<CodeItem> rptTypes) {
		this.rptTypes = rptTypes;
	}

	/**
	 * @return the rptStatuses
	 */
	public ListModelList<CodeItem> getRptStatuses() {
		if (rptStatuses == null) {
			rptStatuses = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "OK", "ERROR", "PARSE" };
			String[] code = new String[] { "", "OK", "ERROR", "PARSE" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				rptStatuses.add(item);
			}
		}
		return rptStatuses;
	}

	/**
	 * @param rptStatuses
	 *            the rptStatuses to set
	 */
	public void setRptStatuses(ListModelList<CodeItem> rptStatuses) {
		this.rptStatuses = rptStatuses;
	}

	/**
	 * @return the currencies
	 */
	public ListModelList<CodeItem> getCurrencies() {
		if (currencies == null) {
			currencies = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "USD", "KHR" };
			String[] code = new String[] { "", "USD", "KHR" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				currencies.add(item);
			}
		}
		return currencies;
	}

	/**
	 * @param currencies
	 *            the currencies to set
	 */
	public void setCurrencies(ListModelList<CodeItem> currencies) {
		this.currencies = currencies;
	}

	/**
	 * @return the branches
	 */
	public ListModelList<CodeItem> getBranches() {
		if (branches == null) {
			branches = new ListModelList<CodeItem>(EnquiryFacade.getBranches());
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
					EnquiryFacade.getSuBranches(param.getBranch().getId()));
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

	@Command
	@NotifyChange({ "param", "subBranches", "lstGuarantor", "selected" })
	public void onChangeBranch() {
		CodeItem item = new CodeItem();
		item.setId(0);
		item.setCode("");
		item.setDescription("All");
		param.setSubBranch(item);
		onDropSubBranch();
		doSearch();
	}

	public void onDropSubBranch() {
		subBranches.clear();
		subBranches.addAll(EnquiryFacade.getSuBranches(param.getBranch().getId()));
	}

	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected" })
	public void onChangeSubBranch() {
		
		CodeItem item = new CodeItem();
		if(param.getSubBranch().getId() == 0)
		{
			item.setId(0);
			item.setCode("");
			item.setDescription("All");
		}
		else
		{
			item.setId(param.getSubBranch().getSuperId());
			item.setCode(param.getSubBranch().getSuperCode());
			item.setDescription(param.getSubBranch().getSuperDescription());
		}
		
		param.setBranch(item);
		doSearch();
		}

	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected" })
	public void onChangeType() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected" })
	public void onChangeStartDate() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected" })
	public void onChangeEndDate() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected" })
	public void onChangeFromAmount() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected" })
	public void onChangeToAmount() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected" })
	public void onChangeCurrency() {
		doSearch();
	}
	
	@Command
	@NotifyChange({ "param", "lstGuarantor", "selected", })
	public void onClearAll()
	{
		param = new ParamEnquiry();
		doSearch();
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}



	
	public Window getEnqList() {
		return enqList;
	}

	public void setEnqList(Window enqList) {
		this.enqList = enqList;
	}

	public Enquiry getCbcM() {
		return cbcM;
	}

	public void setCbcM(Enquiry cbcM) {
		this.cbcM = cbcM;
	}


	public ValidationMessage getvMessage() {
		return vMessage;
	}


	public void setvMessage(ValidationMessage vMessage) {
		this.vMessage = vMessage;
	}


	public boolean isVisibleConf() {
		return visibleConf;
	}


	public void setVisibleConf(boolean visibleConf) {
		this.visibleConf = visibleConf;
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}


	public String getRptUrl() {
		return rptUrl;
	}


	public void setRptUrl(String rptUrl) {
		this.rptUrl = rptUrl;
	}


	public Boolean getValidate() {
		return validate;
	}


	public void setValidate(Boolean validate) {
		this.validate = validate;
	}


	public boolean isVisibleEnquiry() {
		return visibleEnquiry;
	}


	public void setVisibleEnquiry(boolean visibleEnquiry) {
		this.visibleEnquiry = visibleEnquiry;
	}

	public boolean isVisibleEnquiryNew() {
		return visibleEnquiryNew;
	}

	public void setVisibleEnquiryNew(boolean visibleEnquiryNew) {
		this.visibleEnquiryNew = visibleEnquiryNew;
	}
	
	//Soknan Code
	
	@Command
	@NotifyChange({"lstDistrict","lstCommune","lstVillage","cbcM"})
	public void onProvince(){
		lstDistrict = null;
		lstCommune = null;	
		lstVillage = null;
		cbcM.getDistrictC().setCode(null);		
		cbcM.getCommuneC().setCode(null);
		cbcM.getVillageC().setCode(null);	
	}
	@Command
	@NotifyChange({"lstCommune","lstVillage","cbcM"})
	public void onDistrict(){
		lstCommune = null;	
		lstVillage = null;
		cbcM.getCommuneC().setCode(null);
		cbcM.getVillageC().setCode(null);	
	}
	
	@Command
	@NotifyChange({"lstVillage","cbcM"})
	public void onCommune(){
		lstVillage = null;	
		cbcM.getVillageC().setCode(null);
	}
	
	@Command
	@NotifyChange({"lstDistrictD","lstCommuneD","lstVillageD","cbcM"})
	public void onProvinceD(){
		lstDistrictD = null;
		lstCommuneD = null;
		lstVillageD = null;
		cbcM.getDistrictD().setCode(null);		
		cbcM.getCommuneD().setCode(null);		
		cbcM.getVillageD().setCode(null);		
	}
	@Command
	@NotifyChange({"lstCommuneD","lstVillageD","cbcM"})
	public void onDistrictD(){
		lstCommuneD = null;	
		lstVillageD = null;
		cbcM.getCommuneD().setCode(null);		
		cbcM.getVillageD().setCode(null);
	}
	
	@Command
	@NotifyChange({"lstVillageD","cbcM"})
	public void onCommuneD(){
		lstVillageD = null;	
		cbcM.getVillageD().setCode(null);
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstIDType1() {
		if(lstIDType1==null){
			lstIDType1 = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getIDTypeLst());
		}
		return lstIDType1;
	}

	public void setLstIDType1(ListModelList<kredit.web.core.util.model.CodeItem> lstIDType1) {
		this.lstIDType1 = lstIDType1;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstIDType2() {
		if(lstIDType2==null){
			lstIDType2 = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getIDTypeLst());
		}
		return lstIDType2;
	}

	public void setLstIDType2(
			ListModelList<kredit.web.core.util.model.CodeItem> lstIDType2) {
		this.lstIDType2 = lstIDType2;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstIDType3() {
		if(lstIDType3==null){
			lstIDType3 = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getIDTypeLst());
		}
		return lstIDType3;
	}

	public void setLstIDType3(
			ListModelList<kredit.web.core.util.model.CodeItem> lstIDType3) {
		this.lstIDType3 = lstIDType3;
	}	

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstMaritalStatus() {
		if(lstMaritalStatus==null){
			lstMaritalStatus = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getMaritalStatusLst());
		}
		return lstMaritalStatus;
	}

	public void setLstMaritalStatus(
			ListModelList<kredit.web.core.util.model.CodeItem> lstMaritalStatus) {
		this.lstMaritalStatus = lstMaritalStatus;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstGender() {
		if(lstGender==null){
			lstGender = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getGenderLst());
		}
		return lstGender;
	}

	public void setLstGender(
			ListModelList<kredit.web.core.util.model.CodeItem> lstGender) {
		this.lstGender = lstGender;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstProvince() {
		if(lstProvince==null){
			lstProvince = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getProvinceLst());
		}
		return lstProvince;
	}

	public void setLstProvince(
			ListModelList<kredit.web.core.util.model.CodeItem> lstProvince) {
		this.lstProvince = lstProvince;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstDistrict() {
		if(lstDistrict==null){
			lstDistrict = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getDistrictLst(cbcM.getProvinceC().getCode()));
		}
		return lstDistrict;
	}

	public void setLstDistrict(
			ListModelList<kredit.web.core.util.model.CodeItem> lstDistrict) {
		this.lstDistrict = lstDistrict;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstCommune() {
		if(lstCommune==null){
			lstCommune = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getCommuneLst(cbcM.getProvinceC().getCode(),cbcM.getDistrictC().getCode()));
		}
		return lstCommune;
	}

	public void setLstCommune(
			ListModelList<kredit.web.core.util.model.CodeItem> lstCommune) {
		this.lstCommune = lstCommune;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstVillage() {
		if(lstVillage==null){
			lstVillage = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getVillageLst(cbcM.getProvinceC().getCode(),cbcM.getDistrictC().getCode(),cbcM.getCommuneC().getCode()));
		}
		return lstVillage;
	}

	public void setLstVillage(
			ListModelList<kredit.web.core.util.model.CodeItem> lstVillage) {
		this.lstVillage = lstVillage;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstProvinceD() {
		if(lstProvinceD==null){
			lstProvinceD = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getProvinceLst());
		}
		return lstProvinceD;
	}

	public void setLstProvinceD(
			ListModelList<kredit.web.core.util.model.CodeItem> lstProvinceD) {
		this.lstProvinceD = lstProvinceD;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstDistrictD() {
		if(lstDistrictD==null){
			lstDistrictD = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getDistrictLst(cbcM.getProvinceD().getCode()));
		}
		return lstDistrictD;
	}

	public void setLstDistrictD(
			ListModelList<kredit.web.core.util.model.CodeItem> lstDistrictD) {
		this.lstDistrictD = lstDistrictD;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstCommuneD() {
		if(lstCommuneD==null){
			lstCommuneD = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getCommuneLst(cbcM.getProvinceD().getCode(),cbcM.getDistrictD().getCode()));
		}
		return lstCommuneD;
	}

	public void setLstCommuneD(
			ListModelList<kredit.web.core.util.model.CodeItem> lstCommuneD) {
		this.lstCommuneD = lstCommuneD;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstVillageD() {
		if(lstVillageD==null){
			lstVillageD = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getVillageLst(cbcM.getProvinceD().getCode(),cbcM.getDistrictD().getCode(),cbcM.getCommuneD().getCode()));
		}
		return lstVillageD;
	}

	public void setLstVillageD(
			ListModelList<kredit.web.core.util.model.CodeItem> lstVillageD) {
		this.lstVillageD = lstVillageD;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstProvinceE() {
		return lstProvinceE;
	}

	public void setLstProvinceE(
			ListModelList<kredit.web.core.util.model.CodeItem> lstProvinceE) {
		this.lstProvinceE = lstProvinceE;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstDistrictE() {
		return lstDistrictE;
	}

	public void setLstDistrictE(
			ListModelList<kredit.web.core.util.model.CodeItem> lstDistrictE) {
		this.lstDistrictE = lstDistrictE;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstCommuneE() {
		return lstCommuneE;
	}

	public void setLstCommuneE(
			ListModelList<kredit.web.core.util.model.CodeItem> lstCommuneE) {
		this.lstCommuneE = lstCommuneE;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstVillageE() {
		return lstVillageE;
	}

	public void setLstVillageE(
			ListModelList<kredit.web.core.util.model.CodeItem> lstVillageE) {
		this.lstVillageE = lstVillageE;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstAddressType() {
		if(lstAddressType==null){
			lstAddressType = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getAddressTypeLst());
		}
		return lstAddressType;
	}

	public void setLstAddressType(
			ListModelList<kredit.web.core.util.model.CodeItem> lstAddressType) {
		this.lstAddressType = lstAddressType;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstAddressTypeE() {
		if(lstAddressTypeE==null){
			lstAddressTypeE = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getAddressTypeLst());
		}
		return lstAddressTypeE;
	}

	public void setLstAddressTypeE(
			ListModelList<kredit.web.core.util.model.CodeItem> lstAddressTypeE) {
		this.lstAddressTypeE = lstAddressTypeE;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstSelfEmployed() {
		if(lstSelfEmployed==null){
			lstSelfEmployed = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getSelfEmployedLst());
		}
		return lstSelfEmployed;
	}

	public void setLstSelfEmployed(
			ListModelList<kredit.web.core.util.model.CodeItem> lstSelfEmployed) {
		this.lstSelfEmployed = lstSelfEmployed;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstPhoneNumberType() {
		if(lstPhoneNumberType==null){
			lstPhoneNumberType = new ListModelList<kredit.web.core.util.model.CodeItem>(CbcFacade.getPhoneNumberTypeLst());
		}
		return lstPhoneNumberType;
	}

	public void setLstPhoneNumberType(
			ListModelList<kredit.web.core.util.model.CodeItem> lstPhoneNumberType) {
		this.lstPhoneNumberType = lstPhoneNumberType;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstCurrency() {
		if(lstCurrency==null){
			lstCurrency = new ListModelList<>(CbcFacade.getCurrencyLst());
		}
		return lstCurrency;
	}

	public void setLstCurrency(
			ListModelList<kredit.web.core.util.model.CodeItem> lstCurrency) {
		this.lstCurrency = lstCurrency;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstCurrency1() {
		if(lstCurrency1==null){
			lstCurrency1 = new ListModelList<>(CbcFacade.getCurrencyLst());
		}
		return lstCurrency1;
	}

	public void setLstCurrency1(
			ListModelList<kredit.web.core.util.model.CodeItem> lstCurrency1) {
		this.lstCurrency1 = lstCurrency1;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstProductType() {
		if(lstProductType==null){
			lstProductType = new ListModelList<>(CbcFacade.getProductTypeLst());
		}
		return lstProductType;
	}

	public void setLstProductType(
			ListModelList<kredit.web.core.util.model.CodeItem> lstProductType) {
		this.lstProductType = lstProductType;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstAccountType() {
		if(lstAccountType==null){
			lstAccountType = new ListModelList<>(CbcFacade.getAccountTypeLst());
		}
		return lstAccountType;
	}

	public void setLstAccountType(
			ListModelList<kredit.web.core.util.model.CodeItem> lstAccountType) {
		this.lstAccountType = lstAccountType;
	}

	public ListModelList<kredit.web.core.util.model.CodeItem> getLstEnquiryType() {
		if (lstEnquiryType == null) {
			lstEnquiryType = new ListModelList<kredit.web.core.util.model.CodeItem>();
			String[] desc = new String[] { "Guarantor", "Coborrower", "Customer" };
			String[] code = new String[] { "G", "C", "" };
			for (int i = 0; i < code.length; i++) {
				kredit.web.core.util.model.CodeItem item = new kredit.web.core.util.model.CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				lstEnquiryType.add(item);
			}
		}
		return lstEnquiryType;
	}

	public void setLstEnquiryType(
			ListModelList<kredit.web.core.util.model.CodeItem> lstEnquiryType) {
		this.lstEnquiryType = lstEnquiryType;
	}

	public kredit.web.core.util.model.CodeItem getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(kredit.web.core.util.model.CodeItem enquiryType) {
		this.enquiryType = enquiryType;
	}	
	
	
	
	// End Soknan
	
}
