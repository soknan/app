package kredit.web.cbc.vm;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kredit.web.cbc.model.CoGuaModel;
import kredit.web.cbc.model.ParamCoGuaModel;
import kredit.web.cbc.model.ValidationMessage;
import kredit.web.cbc.model.facade.CbcFacade;
import kredit.web.cbc.model.facade.CoGuaFacade;
import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;

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

import com.avaje.ebean.Ebean;

public class CoGuaVM {
	ListModelList<CoGuaModel> lstCoGua;	
	CoGuaModel selected = new CoGuaModel();
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	ParamCoGuaModel param = new ParamCoGuaModel();
	ListModelList<CodeItem> lstIDType1;
	ListModelList<CodeItem> lstIDType2;
	ListModelList<CodeItem> lstIDType3;	
	ListModelList<CodeItem> lstMaritalStatus;	
	ListModelList<CodeItem> lstGender;	
	ListModelList<CodeItem> lstProvince;	
	ListModelList<CodeItem> lstDistrict;	
	ListModelList<CodeItem> lstCommune;
	ListModelList<CodeItem> lstVillage;
	ListModelList<CodeItem> lstProvinceD;	
	ListModelList<CodeItem> lstDistrictD;	
	ListModelList<CodeItem> lstCommuneD;
	ListModelList<CodeItem> lstVillageD;
	ListModelList<CodeItem> lstProvinceE;	
	ListModelList<CodeItem> lstDistrictE;	
	ListModelList<CodeItem> lstCommuneE;
	ListModelList<CodeItem> lstVillageE;
	ListModelList<CodeItem> lstAddressType;
	ListModelList<CodeItem> lstAddressTypeE;
	ListModelList<CodeItem> lstSelfEmployed;
	ListModelList<CodeItem> lstPhoneNumberType;
	
	ListModelList<CoGuaModel> lstAccount;
	ListModelList<CodeItem> lstType;
	
	Privilege privilege = null;
	
	@Wire("#cgls")
	Window winGuarantor;
	
	@Init
	public void init(@ExecutionArgParam("obj") Object obj) {
		if (obj == null) {
			return;
		}		
	}
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange({"selected"})
	public void onShowAcc(){
		Executions.createComponents("/cbc/account.zul", winGuarantor,null);
	}
	
	@Command
	public void onNewGuarantor(){
		lstCoGua = null;
		selected = new CoGuaModel();
		Executions.createComponents("/cbc/guarantor.zul", winGuarantor,null);
	}
	
	@Command
	@NotifyChange({"selected","vMessage"})
	public void onOpen(){
		vMessage = new ValidationMessage();
		if(selected.getType().equals("G")){
			//lstCoGua = new ListModelList<>(CoGuaFacade.getLstGuarantor(selected.getAccountNumber()));
			selected = CoGuaFacade.getLstGuarantor(selected.getAccountNumber());
			Executions.createComponents("/cbc/guarantor.zul", winGuarantor,null);
		}else {			
			//lstCoGua = new ListModelList<>(CoGuaFacade.getLstCoborrower(selected.getAccountNumber()));
			selected = CoGuaFacade.getLstCoborrower(selected.getAccountNumber());
			Executions.createComponents("/cbc/coBorrower.zul", winGuarantor,null);		
		}
		
	}
	
	@Command
	@NotifyChange({"selected","param"})
	public void onClose(@BindingParam("cmp")  Window x) {
		selected = null;
		param = new ParamCoGuaModel();
	    x.detach();
	}
	
	@Command
	@NotifyChange({"lstCoGua","selected","param"})
	public void onClear(){
		selected = null;
		lstCoGua = null;		
		param = new ParamCoGuaModel();
	}
	
	@Command
	@NotifyChange({"lstCoGua","selected"})
	public void doSearch(){		
		lstCoGua = null;
	}
	
	@Command
	@NotifyChange({"lstAccount","selected"})
	public void doSearchAcc(){		
		lstAccount = null;
	}
	
	@Command
	@NotifyChange({"lstAccount","selected","param"})
	public void onClearAcc(){
		selected = null;
		lstAccount = null;
		param = new ParamCoGuaModel();
	}
	
	@Command
	@NotifyChange({"lstCoGua","param","vMessage"})
	public void onSave(){
		vMessage = new ValidationMessage();
		formValidation();
		if(!validate){			
			Clients.showNotification("Data validation failed. Please verify before continue.",
				       "warning", null, "middle_center", -1);
			return;			
		}
		selected.setValidated("Y");
		if(selected.getId() == null)
		{
			selected.setCreate_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selected.setCreate_on(new Date());
			Ebean.save(selected);
		}
		else
		{
			selected.setChange_by(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			//selected.setChange_on(new Date());	
			Ebean.update(selected);
		}		
		lstCoGua = null;		
		param = new ParamCoGuaModel();
		Clients.showNotification("Save successfully", "info", null,
				"middle_center", -1);
	}
	
	boolean validate = true;
	ValidationMessage vMessage =  new ValidationMessage();
	@Command
	@NotifyChange({"validate"})
	public void formValidation(){
		validate = true;
		vMessage.setvIdNumber1("");
		vMessage.setvIdExpiry("");
		if(selected.getIdType1().equals("N")){
			if(selected.getIdNumber1().length()!=9 || !selected.getIdNumber1().matches("\\d+")){
				vMessage.setvIdNumber1("ID Card Number must be the number of 9 or 10 digits");
				validate = false;
			}else if(selected.getExpiryDate1() == null){
				vMessage.setvIdExpiry("ID Expiry Date cannot be blank");
				validate = false;
			}
		}
		
		vMessage.setvIdType2("");
		vMessage.setvIdNumber2("");
		vMessage.setvIdExpiry2("");
		if(selected.getIdType2()!=null){
			if(selected.getIdNumber2()!=null){
				if(selected.getIdType2().equals("N")){
					if(selected.getIdNumber2().length()!=9 || !selected.getIdNumber2().matches("\\d+")){
						vMessage.setvIdNumber2("ID Card Number must be the number of 9 or 10 digits");
						validate = false;
					}
					if(selected.getExpiryDate2() == null){
						vMessage.setvIdExpiry("ID Expiry Date cannot be blank");
						validate = false;
					}
				}
			}else{
				vMessage.setvIdNumber2("ID Card Number cannot be blank");
				validate = false;
			}
		}else if(selected.getIdNumber2()!=null){
			vMessage.setvIdType2("ID Type cannot be blank");
			validate = false;
		}
		
		vMessage.setvIdType3("");
		vMessage.setvIdNumber3("");
		vMessage.setvIdExpiry3("");
		if(selected.getIdType3()!=null){
			if(selected.getIdNumber3()!=null){
				if(selected.getIdType3().equals("N")){
					if(selected.getIdNumber3().length()!=9 || !selected.getIdNumber3().matches("\\d+")){
						vMessage.setvIdNumber3("ID Card Number must be the number of 9 or 10 digits");
						validate = false;
					}
					if(selected.getExpiryDate3() == null){
						vMessage.setvIdExpiry("ID Expiry Date cannot be blank");
						validate = false;
					}
				}
			}else{
				vMessage.setvIdNumber3("ID Card Number cannot be blank");
				validate = false;
			}
		}else if(selected.getIdNumber3()!=null){
			vMessage.setvIdType3("ID Type cannot be blank");
			validate = false;
		}
		
		if(checkAge(selected.getDob())<18 || checkAge(selected.getDob())>100){
			vMessage.setvDateofbirth("Date of Birth: client must be at least 18 years old");
			validate = false;
		}
		if(checkUnicode(selected.getFirstNameKH(), true)){
			vMessage.setvFirstNameKH("First name is not enter in Khmer Unicode");
			validate = false;
		}
		if(checkUnicode(selected.getLastNameKH(),true)){
			vMessage.setvLastNameKH("Last name is not enter in Khmer Unicode");
			validate = false;
		}
		vMessage.setvPhone1("");
		if(selected.getPhone1().getType().equals("U") && !selected.getPhone1().getPhoneNumber().equals("000")){
			vMessage.setvPhone1("Invalid Phone number. Phone Type is Unknown.");
			validate = false;
		}
		if(!selected.getPhone1().getType().equals("U") && selected.getPhone1().getPhoneNumber().equals("000")){
			vMessage.setvPhone1("Invalid Phone number. Phone Type is not Unknown.");
			validate = false;
		}
		if(!selected.getPhone1().getType().equals("U") && !selected.getPhone1().getPhoneNumber().equals("000")){
			if(checkPhoneNumber(selected.getPhone1().getPhoneNumber())){
				vMessage.setvPhone1("Invalid Phone number");
				validate = false;
			}
		}
		
		vMessage.setvPhone2("");
		if(selected.getPhone2().getType().equals("U") && !selected.getPhone2().getPhoneNumber().equals("000")){
			vMessage.setvPhone2("Invalid Phone number. Phone Type is Unknown.");
			validate = false;
		}
		if(!selected.getPhone2().getType().equals("U") && selected.getPhone2().getPhoneNumber().equals("000")){
			vMessage.setvPhone2("Invalid Phone number. Phone Type is not Unknown.");
			validate = false;
		}
		if(!selected.getPhone2().getType().equals("U") && !selected.getPhone2().getPhoneNumber().equals("000")){
			if(checkPhoneNumber(selected.getPhone2().getPhoneNumber())){
				vMessage.setvPhone2("Invalid Phone number");
				validate = false;
			}
		}
		if(selected.getTotalMonthlyIncome() == null || selected.getTotalMonthlyIncome() == 0){	
			vMessage.setvIncome("Total monthly Income cannot be zero");
				validate = false;
		}		
		if(selected.getLangthOfService()==null || selected.getLangthOfService()==0){
			vMessage.setVlengthOfServince("Length of Service cannot be zero");
			validate = false;
		}
		if(checkUnicode(selected.getEmployerCity(), false)){
			vMessage.setvCity("Employer City must be typed in English");
			validate = false;
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
	}

	//check is it number
	private boolean checkNumber(String number) {
		if(number ==null) return false;
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
	
	@Command
	@NotifyChange({"lstDistrict","lstCommune","lstVillage","selected"})
	public void onProvince(){
		lstDistrict = null;
		lstCommune = null;	
		lstVillage = null;
		selected.getDistrictC().setCode(null);		
		selected.getCommuneC().setCode(null);
		selected.getVillageC().setCode(null);	
	}
	@Command
	@NotifyChange({"lstCommune","lstVillage","selected"})
	public void onDistrict(){
		lstCommune = null;	
		lstVillage = null;
		selected.getCommuneC().setCode(null);
		selected.getVillageC().setCode(null);	
	}
	
	@Command
	@NotifyChange({"lstVillage","selected"})
	public void onCommune(){
		lstVillage = null;	
		selected.getVillageC().setCode(null);
	}
	
	@Command
	@NotifyChange({"lstDistrictD","lstCommuneD","lstVillageD","selected"})
	public void onProvinceD(){
		lstDistrictD = null;
		lstCommuneD = null;
		lstVillageD = null;
		selected.getDistrictD().setCode(null);		
		selected.getCommuneD().setCode(null);		
		selected.getVillageD().setCode(null);		
	}
	@Command
	@NotifyChange({"lstCommuneD","lstVillageD","selected"})
	public void onDistrictD(){
		lstCommuneD = null;	
		lstVillageD = null;
		selected.getCommuneD().setCode(null);		
		selected.getVillageD().setCode(null);
	}
	
	@Command
	@NotifyChange({"lstVillageD","selected"})
	public void onCommuneD(){
		lstVillageD = null;	
		selected.getVillageD().setCode(null);
	}
	
	@Command
	@NotifyChange({"lstDistrictE","lstCommuneE","lstVillageE","selected"})
	public void onProvinceE(){
		lstDistrictE = null;
		lstCommuneE = null;
		selected.getDistrictE().setCode(null);		
		selected.getCommuneE().setCode(null);
		selected.getVillageE().setCode(null);
	}
	@Command
	@NotifyChange({"lstCommuneE","lstVillageE","selected"})
	public void onDistrictE(){
		lstCommuneE = null;	
		lstVillageE = null;
		selected.getCommuneE().setCode(null);
		selected.getVillageE().setCode(null);
	}
	
	@Command
	@NotifyChange({"lstVillageE","selected"})
	public void onCommuneE(){
		lstVillageE = null;	
		selected.getVillageE().setCode(null);		
	}
	
	public CoGuaModel getSelected() {
		return selected;
	}
	public void setSelected(CoGuaModel selected) {
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
	public Window getWinGuarantor() {
		return winGuarantor;
	}
	public void setWinGuarantor(Window winGuarantor) {
		this.winGuarantor = winGuarantor;
	}
	public ParamCoGuaModel getParam() {
		return param;
	}
	public void setParam(ParamCoGuaModel param) {
		this.param = param;
	}	
	public ListModelList<CoGuaModel> getLstCoGua() {
		if(lstCoGua == null){
			lstCoGua = new ListModelList<CoGuaModel>(CoGuaFacade.getCoGuaLst(param));
		}
		return lstCoGua;
	}
	public void setLstCoGua(ListModelList<CoGuaModel> lstCoGua) {
		this.lstCoGua = lstCoGua;
	}
	public ListModelList<CodeItem> getLstIDType1() {
		if(lstIDType1==null){
			lstIDType1 = new ListModelList<CodeItem>(CbcFacade.getIDTypeLst());
		}
		return lstIDType1;
	}
	public void setLstIDType1(ListModelList<CodeItem> lstIDType1) {
		this.lstIDType1 = lstIDType1;
	}
	public ListModelList<CodeItem> getLstIDType2() {
		if(lstIDType2==null){
			lstIDType2 = new ListModelList<CodeItem>(CbcFacade.getIDTypeLst());
		}
		return lstIDType2;
	}
	public void setLstIDType2(ListModelList<CodeItem> lstIDType2) {
		this.lstIDType2 = lstIDType2;
	}
	public ListModelList<CodeItem> getLstIDType3() {
		if(lstIDType3==null){
			lstIDType3 = new ListModelList<CodeItem>(CbcFacade.getIDTypeLst());
		}
		return lstIDType3;
	}
	public void setLstIDType3(ListModelList<CodeItem> lstIDType3) {
		this.lstIDType3 = lstIDType3;
	}
	public ListModelList<CodeItem> getLstMaritalStatus() {
		if(lstMaritalStatus==null){
			lstMaritalStatus = new ListModelList<>(CbcFacade.getMaritalStatusLst());
		}
		return lstMaritalStatus;
	}
	public void setLstMaritalStatus(ListModelList<CodeItem> lstMaritalStatus) {
		this.lstMaritalStatus = lstMaritalStatus;
	}
	
	public ListModelList<CodeItem> getLstGender() {
		if(lstGender==null){
			lstGender = CbcFacade.getGenderLst();
		}
		return lstGender;
	}
	public void setLstGender(ListModelList<CodeItem> lstGender) {
		this.lstGender = lstGender;
	}
	public ListModelList<CodeItem> getLstProvince() {
		if(lstProvince == null){
			lstProvince = new ListModelList<CodeItem>(CbcFacade.getProvinceLst());
		}
		return lstProvince;
	}
	public void setLstProvince(ListModelList<CodeItem> lstProvince) {
		this.lstProvince = lstProvince;
	}	

	public ListModelList<CodeItem> getLstDistrict() {
		if(lstDistrict == null){
			lstDistrict = new ListModelList<CodeItem>(CbcFacade.getDistrictLst(selected.getProvinceC().getCode()));
		}
		return lstDistrict;
	}
	public void setLstDistrict(ListModelList<CodeItem> lstDistrict) {
		this.lstDistrict = lstDistrict;
	}	

	public ListModelList<CodeItem> getLstCommune() {
		if(lstCommune == null){
			lstCommune = new ListModelList<>(CbcFacade.getCommuneLst(selected.getProvinceC().getCode(),selected.getDistrictC().getCode()));
		}
		return lstCommune;
	}
	public void setLstCommune(ListModelList<CodeItem> lstCommune) {
		this.lstCommune = lstCommune;
	}
	
	public ListModelList<CodeItem> getLstVillage() {
		if(lstVillage == null){
			lstVillage = new ListModelList<>(
					CbcFacade.getVillageLst(
							selected.getProvinceC().getCode(), 
							selected.getDistrictC().getCode(),
							selected.getCommuneC().getCode()
					));
		}
		return lstVillage;
	}
	public void setLstVillage(ListModelList<CodeItem> lstVillage) {
		this.lstVillage = lstVillage;
	}
	public ListModelList<CodeItem> getLstProvinceD() {
		if(lstProvinceD == null){
			lstProvinceD = new ListModelList<>(CbcFacade.getProvinceLst());
		}
		return lstProvinceD;
	}
	public void setLstProvinceD(ListModelList<CodeItem> lstProvinceD) {
		this.lstProvinceD = lstProvinceD;
	}
	public ListModelList<CodeItem> getLstDistrictD() {
		if(lstDistrictD == null){
			lstDistrictD = new ListModelList<>(CbcFacade.getDistrictLst(selected.getProvinceD().getCode()));
		}
		return lstDistrictD;
	}
	public void setLstDistrictD(ListModelList<CodeItem> lstDistrictD) {
		this.lstDistrictD = lstDistrictD;
	}
	public ListModelList<CodeItem> getLstCommuneD() {
		if(lstCommuneD == null){
			lstCommuneD = new ListModelList<>(CbcFacade.getCommuneLst(selected.getProvinceD().getCode(), selected.getDistrictD().getCode()));
		}
		return lstCommuneD;
	}
	public void setLstCommuneD(ListModelList<CodeItem> lstCommuneD) {
		this.lstCommuneD = lstCommuneD;
	}
	public ListModelList<CodeItem> getLstVillageD() {
		if(lstVillageD == null){
			lstVillageD = new ListModelList<>(
					CbcFacade.getVillageLst(
							selected.getProvinceD().getCode(), 
							selected.getDistrictD().getCode(),
							selected.getCommuneD().getCode()
					));
		}
		return lstVillageD;
	}
	public void setLstVillageD(ListModelList<CodeItem> lstVillageD) {
		this.lstVillageD = lstVillageD;
	}
	public ListModelList<CodeItem> getLstAddressType() {
		if(lstAddressType == null){
			lstAddressType = new ListModelList<>(CbcFacade.getAddressTypeLst());
		}
		return lstAddressType;
	}
	public void setLstAddressType(ListModelList<CodeItem> lstAddressType) {
		this.lstAddressType = lstAddressType;
	}
	public ListModelList<CodeItem> getLstProvinceE() {
		if(lstProvinceE == null){
			lstProvinceE = new ListModelList<>(CbcFacade.getProvinceLst());
		}
		return lstProvinceE;
	}
	public void setLstProvinceE(ListModelList<CodeItem> lstProvinceE) {
		this.lstProvinceE = lstProvinceE;
	}
	public ListModelList<CodeItem> getLstDistrictE() {
		if(lstDistrictE == null){
			lstDistrictE = new ListModelList<>(CbcFacade.getDistrictLst(selected.getProvinceE().getCode()));
		}
		return lstDistrictE;
	}
	public void setLstDistrictE(ListModelList<CodeItem> lstDistrictE) {
		this.lstDistrictE = lstDistrictE;
	}
	public ListModelList<CodeItem> getLstCommuneE() {
		if(lstCommuneE == null){
			lstCommuneE = new ListModelList<>(CbcFacade.getCommuneLst(selected.getProvinceE().getCode(), selected.getDistrictE().getCode()));
		}
		return lstCommuneE;
	}
	public void setLstCommuneE(ListModelList<CodeItem> lstCommuneE) {
		this.lstCommuneE = lstCommuneE;
	}
	public ListModelList<CodeItem> getLstVillageE() {
		if(lstVillageE == null){
			lstVillageE = new ListModelList<>(
					CbcFacade.getVillageLst(
							selected.getProvinceE().getCode(), 
							selected.getDistrictE().getCode(),
							selected.getCommuneE().getCode()
					));
		}
		return lstVillageE;
	}
	public void setLstVillageE(ListModelList<CodeItem> lstVillageE) {
		this.lstVillageE = lstVillageE;
	}
	public ListModelList<CodeItem> getLstSelfEmployed() {
		if(lstSelfEmployed == null){
			lstSelfEmployed = CbcFacade.getSelfEmployedLst();
		}
		return lstSelfEmployed;
	}
	public void setLstSelfEmployed(ListModelList<CodeItem> lstSelfEmployed) {
		this.lstSelfEmployed = lstSelfEmployed;
	}
	public ListModelList<CodeItem> getLstAddressTypeE() {
		if(lstAddressTypeE == null){
			lstAddressTypeE = new ListModelList<>(CbcFacade.getAddressTypeLst());
		}
		return lstAddressTypeE;
	}
	public void setLstAddressTypeE(ListModelList<CodeItem> lstAddressTypeE) {
		this.lstAddressTypeE = lstAddressTypeE;
	}
	public ListModelList<CodeItem> getLstPhoneNumberType() {
		if(lstPhoneNumberType==null){
			lstPhoneNumberType = new ListModelList<>(CbcFacade.getPhoneNumberTypeLst());
		}
		return lstPhoneNumberType;
	}
	public void setLstPhoneNumberType(ListModelList<CodeItem> lstPhoneNumberType) {
		this.lstPhoneNumberType = lstPhoneNumberType;
	}
	public ListModelList<CoGuaModel> getLstAccount() {
		if(lstAccount==null){
			lstAccount = new ListModelList<>(CoGuaFacade.getLstAccount(param));
		}
		return lstAccount;
	}
	public void setLstAccount(ListModelList<CoGuaModel> lstAccount) {
		this.lstAccount = lstAccount;
	}
	public ListModelList<CodeItem> getLstType() {
		if (lstType != null)
			return lstType;
		lstType = new ListModelList<CodeItem>();
		String[] desc = new String[] { "All", "Coborrower", "Guarantor" };
		String[] code = new String[] { "%", "C", "G" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			lstType.add(item);
		}
		return lstType;
	}
	public void setLstType(ListModelList<CodeItem> lstType) {
		this.lstType = lstType;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public ValidationMessage getvMessage() {
		return vMessage;
	}
	public void setvMessage(ValidationMessage vMessage) {
		this.vMessage = vMessage;
	}
	public Privilege getPrivilege() {
		if(privilege == null){
			privilege = Common.getPrivilege(CMD.LIST_CG);
		}
		return privilege;
	}
	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}	
	
}

