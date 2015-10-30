package kredit.web.kbureau.model.enquiry;

import java.util.Date;

import kredit.web.core.util.model.CodeItem;
import kredit.web.kbureau.model.facade.enquiry.EnquiryFacade;
import kredit.web.kbureau.model.facade.enquiry.TelcoFacade;
import kredit.web.kbureau.viewmodel.enquiry.EnquiryVM;

public class Enquiry {

	private String service = "ENQUIRYV2";
	private String action = "L";
	private String memberID = "211";
	private String runNo = "000001";
	private String totItems = "1";
	private String userID = "#CBC_ID#";
	//private String userID = "KRD_TEST";
	private String loanID = "";

	private String enquiryType = "NA";
	private String language;
	private String productType;
	private String enqProductCode;
	private String enqLoanPurpose;
	private int numberOfApplicants;
	private String accountType;
	private String memberReference;
	private String alterAcc;
	private String amount;
	private String currency = "USD";
	private String applicantType = "P";
	private String idType1;
	private String idType1Desc;
	private String idNumber1;
	private Date idExpiryDate1;
	private String idType2;
	private String idType2Desc;
	private String idNumber2;
	private Date idExpiryDate2;
	private String idType3;
	private String idType3Desc;
	private String idNumber3;
	private Date idExpiryDate3;
	
	private String idType4;
	private String idType4Desc;
	private String idNumber4;
	private String idExpiryDate4;
	
	private Date dob;
	private String sex;
	private String maritalStatus;
	private String maritalStatusDesc;
	private String nationalityCode;
	private String email;
	private String dCountry;
	private String dProvince;
	private String dDistrict;
	private String dCommune;
	private String dVillage;
	private String addressline1;
	private String addressline2;
	private String firstNameKH = "";
	private String lastNameKH = "";
	private String firstNameEN = "";
	private String lastNameEN = "";
	private String addressType;
	private String homeNo;
	private String village;
	private String commune;
	private String district;
	private String province;
	private String city;
	private String country;
	private String employmentType;
	private String selfEmployed;
	private int lengthOfServince;
	private String occupation;
	private Integer totalMonthlyIncome;
	private Integer totalMonthlyIncome2;
	private String currency1;
	private String employerName;
	private String employerAddressType;
	private String employerAddressLine1;
	private String employerAddressLine2;
	private String employerCity;
	private String employerCountryCode;
	
	private String fullNameKH;
	private String fullNameEN;
	private String branchNameEN;
	private String subNameEN;
	private String product;
	private Date valueDate;
	

	private String telephone;
	private String mobileNumber;

	private Telco phone1;
	private Telco phone2;

	private String loanPurpose;
	private String loanFund;
	private String productCode;
	
	private String refNumber;
	private String status;
	
	private String amountCurrency;
	private Double amountformat;
	private Float cbcFee;
	private String cbcFeeCurrency;
	private String customerNo;
	
	private String reqButton;
	
	CodeItem idTypeItem1;	
	CodeItem idTypeItem2;	
	CodeItem idTypeItem3;
	CodeItem maritalStatusItem;	
	CodeItem sexItem;	
	CodeItem addressTypeC;	
	CodeItem addressTypeE;	
	CodeItem provinceC;	
	CodeItem districtC;	
	CodeItem communeC;	
	CodeItem villageC;	
	CodeItem provinceD;	
	CodeItem districtD;	
	CodeItem communeD;	
	CodeItem villageD;	
	CodeItem provinceE;	
	CodeItem districtE;	
	CodeItem communeE;	
	CodeItem villageE;	
	CodeItem selfEmployedItem;	
	CodeItem phoneNumberTypeC;
	CodeItem currencyItem;
	CodeItem currencyItem1;
	CodeItem productItem;
	CodeItem accountTypeItem;

	public Telco getPhone1() {
		if (phone1 != null)
			return phone1;
		phone1 = TelcoFacade.getTelco(telephone);
		if(phone1.getType().equals("U"))
			phone1.setPhoneNumber("000");
			
		return phone1;
	}

	public void setPhone1(Telco phone1) {
		this.phone1 = phone1;
	}

	public Telco getPhone2() {
		if (phone2 != null)
			return phone2;
		phone2 = TelcoFacade.getTelco(mobileNumber);
		if (phone2.getType().equals("U"))
			phone2.setPhoneNumber("000");
		return phone2;
	}

	public void setPhone2(Telco phone2) {
		this.phone2 = phone2;
	}

	public String getEnquiryType() {
		return enquiryType;
	}

	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}

	public String getEnqLoanPurpose() {
		
		return enqLoanPurpose;
	}

	public void setEnqLoanPurpose(String enqLoanPurpose) {
		this.enqLoanPurpose = enqLoanPurpose;
	}
	
	public String getEnqProductCode() {
		return enqProductCode;
	}

	public void setEnqProductCode(String enqProductCode) {
		this.enqProductCode = enqProductCode;
	}

	public String getProductType() {
		if(productType!=null){
			return productType;
		}
		productType = "";
		
		if(enqLoanPurpose == null){
			enqLoanPurpose = "";
		}
		if(enqProductCode == null){
			enqProductCode = "";
		}
		if(enqProductCode.equals("0201") || enqProductCode.equals("0301") || enqProductCode.equals("0401")) {
			productType = "CMT";
		}
		else if(enqProductCode.equals("0109")) {
			productType = "EDU";
		}
		else if(enqProductCode.equals("0105") || enqProductCode.equals("0107")){
			productType = "SHL";
		}
		else if(enqProductCode.equals("0104") || enqProductCode.equals("0106")){
			productType = "STL";
		}
		else if(enqLoanPurpose.equals("Agriculture") || enqLoanPurpose.equals("Animal Husbandry")){
			productType = "AGL";
		}
		else if(enqLoanPurpose.equals("Production") || enqLoanPurpose.equals("Trade & Commerce") || enqLoanPurpose.equals("Services") || enqLoanPurpose.equals("Investment")){
			productType = "AFI";
		}
		else if(enqLoanPurpose.equals("Working Capital")){
			productType = "WCL";
		}
		else if(enqLoanPurpose.equals("Consumption")){
			productType = "CDL";
		}
		else if(enqLoanPurpose.equals("Complete House")){
			productType = "HIL";
		}
		else if(enqLoanPurpose.equals("Agr-Assets Acquisition")){
			productType = "MCL";
		}
		else if(enqLoanPurpose.equals("Other")){
			productType = "PEL";
		}else if(enqLoanPurpose.equals("Real Estate")){
			productType = "MRA";
		}
		else if(enqLoanPurpose.equals("Emergency")){
			productType = "EML";
		}
		else if(enqLoanPurpose.equals("Water Filter") || enqLoanPurpose.equals("Latrine")){
			productType = "GRL";
		}
		else{
			productType = "PEL";
		}
		
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public int getNumberOfApplicants() {
		return numberOfApplicants;
	}

	public void setNumberOfApplicants(int numberOfApplicants) {
		this.numberOfApplicants = numberOfApplicants;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getMemberReference() {
		if(memberReference == null) return loanID;
		return memberReference;
	}

	public void setMemberReference(String memberReference) {
		this.memberReference = memberReference;
	}

	public String getAlterAcc() {
		return alterAcc;
	}

	public void setAlterAcc(String alterAcc) {
		this.alterAcc = alterAcc;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String aplicantType) {
		this.applicantType = aplicantType;
	}

	public String getIdType1() {
		if(idNumber1 == null){
			idType1 = "";
		}
		return idType1;
	}

	public void setIdType1(String idType1) {
		this.idType1 = idType1;
	}

	public String getIdNumber1() {
		return idNumber1;
	}

	public void setIdNumber1(String idNumber1) {
		this.idNumber1 = idNumber1;
	}

	public Date getIdExpiryDate1() {
		return idExpiryDate1;
	}

	public void setIdExpiryDate1(Date idExpiryDate1) {
		this.idExpiryDate1 = idExpiryDate1;
	}

	public String getIdType2() {
		return idType2;
	}
	
	public void setIdType2(String idType2) {
		this.idType2 = idType2;
	}

	public String getIdNumber2() {
		return idNumber2;
	}

	public void setIdNumber2(String idNumber2) {
		this.idNumber2 = idNumber2;
	}

	public Date getIdExpiryDate2() {
		return idExpiryDate2;
	}

	public void setIdExpiryDate2(Date idExpiryDate2) {
		this.idExpiryDate2 = idExpiryDate2;
	}

	public String getIdType3() {
		return idType3;
	}

	public void setIdType3(String idType3) {
		this.idType3 = idType3;
	}

	public String getIdType2Desc() {
		if(idType2 != null)
			idType2Desc = EnquiryVM.idTypeDesc(idType2);
		return idType2Desc;
	}

	public void setIdType2Desc(String idType2Desc) {
		this.idType2Desc = idType2Desc;
	}

	public String getIdType3Desc() {
		if(idType3 != null)
			idType3Desc = EnquiryVM.idTypeDesc(idType3);
		return idType3Desc;
	}

	public void setIdType3Desc(String idType3Desc) {
		this.idType3Desc = idType3Desc;
	}

	public String getIdNumber3() {
		return idNumber3;
	}

	public void setIdNumber3(String idNumber3) {
		this.idNumber3 = idNumber3;
	}

	public Date getIdExpiryDate3() {
		return idExpiryDate3;
	}

	public void setIdExpiryDate3(Date idExpiryDate3) {
		this.idExpiryDate3 = idExpiryDate3;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMaritalStatus() {
		if(maritalStatus != null){
			if(maritalStatus.equals("E")){
				maritalStatus = "W";
			}else if(maritalStatus.equals("R")){
				maritalStatus = "U";
			}
		}
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMaritalStatusDesc() {
		return maritalStatusDesc;
	}

	public void setMaritalStatusDesc(String maritalStatusDesc) {
		this.maritalStatusDesc = maritalStatusDesc;
	}

	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public String getdCountry() {
		return dCountry;
	}

	public void setdCountry(String dCountry) {
		this.dCountry = dCountry;
	}

	public String getdProvince() {
		if(dProvince == null){
			dProvince = "";
		}
		return dProvince;
	}

	public void setdProvince(String dProvince) {
		this.dProvince = dProvince;
	}

	public String getdDistrict() {
		return dDistrict;
	}

	public void setdDistrict(String dDistrict) {
		this.dDistrict = dDistrict;
	}

	public String getdCommune() {
		return dCommune;
	}

	public void setdCommune(String dCommune) {
		this.dCommune = dCommune;
	}

	public String getdVillage() {
		if(!dCountry.equals("KHM")){
			dVillage = "";
		}
		return dVillage;
	}

	public void setdVillage(String dVillage) {
		this.dVillage = dVillage;
	}

	public String getAddressline1() {
		return addressline1;
	}

	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getHomeNo() {
		return homeNo;
	}

	public void setHomeNo(String homeNo) {
		this.homeNo = homeNo;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		if(village != null){
			province = EnquiryFacade.getPerProvince(village);
		}
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		if(village != null){
			city = EnquiryFacade.getPerProvince(village);
		}
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getSelfEmployed() {
		//System.out.println(selfEmployed);
		if(selfEmployed == null){
			return "Y";
		}else if(selfEmployed.equals("F")){
			return "N";
		}else if(selfEmployed.equals("T")){
			return "Y";
		}else if(selfEmployed.equals("U")){
			return "Y";
		}else if(selfEmployed.equals("S")){
			return "Y";
		}else if(selfEmployed.equals("P")){
			return "Y";
		}
		
		return selfEmployed;
	}

	public void setSelfEmployed(String selfEmployed) {
		this.selfEmployed = selfEmployed;
	}

	public int getLengthOfServince() {
		//System.out.println("Selfemp = " + selfEmployed + getSelfEmployed());
		if(selfEmployed!=null){
			if(selfEmployed.equals("U")){
				lengthOfServince = 1;
			}
		}
		return lengthOfServince;
	}

	public void setLengthOfServince(int lengthOfServince) {
		this.lengthOfServince = lengthOfServince;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Integer getTotalMonthlyIncome() {
		if (totalMonthlyIncome == null){
			totalMonthlyIncome = 0;
			if(getSelfEmployed().equals("Y") && totalMonthlyIncome2 != null){
				totalMonthlyIncome = totalMonthlyIncome2;
			}
		}
		return totalMonthlyIncome;
	}

	public void setTotalMonthlyIncome(Integer totalMonthlyIncome) {
		this.totalMonthlyIncome = totalMonthlyIncome;
	}
	

	/*public String getTotalMonthlyIncome2() {
		if(totalMonthlyIncome2 == null)
			return "0";
		return totalMonthlyIncome2;
	}

	public void setTotalMonthlyIncome2(String totalMonthlyIncome2) {
		this.totalMonthlyIncome2 = totalMonthlyIncome2;
	}*/
	
	public Integer getTotalMonthlyIncome2() {
		return totalMonthlyIncome2;
	}

	public void setTotalMonthlyIncome2(Integer totalMonthlyIncome2) {
		this.totalMonthlyIncome2 = totalMonthlyIncome2;
	}

	

	public String getCurrency1() {
		return currency1;
	}

	
	public void setCurrency1(String currency1) {
		this.currency1 = currency1;
	}

	public String getEmployerName() {
		if (employerName == null){
			employerName = new String();
			if(getSelfEmployed().equals("Y")){
				employerName = getFullNameEN();
			}
		}
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getEmployerAddressType() {
		return employerAddressType;
	}

	public void setEmployerAddressType(String employerAddressType) {
		this.employerAddressType = employerAddressType;
	}

	public String getEmployerAddressLine1() {
		if (employerAddressLine1 == null){
			employerAddressLine1 = new String();
			if(getSelfEmployed().equals("Y")){
				if(addressline1 != null){
					employerAddressLine1 = addressline1;
				}
			}
		}
			
		return employerAddressLine1;
	}

	public void setEmployerAddressLine1(String employerAddressLine1) {
		this.employerAddressLine1 = employerAddressLine1;
	}

	public String getEmployerCity() {
		if (employerCity == null){
			employerCity = new String();
			if(getSelfEmployed().equals("Y")){
				if(village != null){
					employerCity = EnquiryFacade.getPerProvince(village);
				}
			}
		}
		return employerCity;
	}

	public void setEmployerCity(String employerCity) {
		this.employerCity = employerCity;
	}

	public String getEmployerCountryCode() {
		return employerCountryCode;
	}

	public void setEmployerCountryCode(String employerCountryCode) {
		this.employerCountryCode = employerCountryCode;
	}

	/**
	 * @return the service
	 */
	public String getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the memberID
	 */
	public String getMemberID() {
		return memberID;
	}

	/**
	 * @param memberID
	 *            the memberID to set
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	/**
	 * @return the runNo
	 */
	public String getRunNo() {
		return runNo;
	}

	/**
	 * @param runNo
	 *            the runNo to set
	 */
	public void setRunNo(String runNo) {
		this.runNo = runNo;
	}

	/**
	 * @return the totItems
	 */
	public String getTotItems() {
		return totItems;
	}

	/**
	 * @param totItems
	 *            the totItems to set
	 */
	public void setTotItems(String totItems) {
		this.totItems = totItems;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		if (language == null)
			language = new String();
		return language;
	}

	/**
	 * @param language
	 *            the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		if (email == null)
			email = new String();

		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstNameKH
	 */
	public String getFirstNameKH() {
		return firstNameKH;
	}

	/**
	 * @param firstNameKH
	 *            the firstNameKH to set
	 */
	public void setFirstNameKH(String firstNameKH) {
		this.firstNameKH = firstNameKH;
	}

	/**
	 * @return the lastNameKH
	 */
	public String getLastNameKH() {
		return lastNameKH;
	}

	/**
	 * @param lastNameKH
	 *            the lastNameKH to set
	 */
	public void setLastNameKH(String lastNameKH) {
		this.lastNameKH = lastNameKH;
	}

	/**
	 * @return the firstNameEN
	 */
	public String getFirstNameEN() {
		return firstNameEN;
	}

	/**
	 * @param firstNameEN
	 *            the firstNameEN to set
	 */
	public void setFirstNameEN(String firstNameEN) {
		this.firstNameEN = firstNameEN;
	}

	/**
	 * @return the lastNameEN
	 */
	public String getLastNameEN() {
		return lastNameEN;
	}

	/**
	 * @param lastNameEN
	 *            the lastNameEN to set
	 */
	public void setLastNameEN(String lastNameEN) {
		this.lastNameEN = lastNameEN;
	}

	/**
	 * @return the addressline2
	 */
	public String getAddressline2() {
		return addressline2;
	}

	/**
	 * @param addressline2
	 *            the addressline2 to set
	 */
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}

	/**
	 * @return the employerAddressLine2
	 */
	public String getEmployerAddressLine2() {
		return employerAddressLine2;
	}

	/**
	 * @param employerAddressLine2
	 *            the employerAddressLine2 to set
	 */
	public void setEmployerAddressLine2(String employerAddressLine2) {
		this.employerAddressLine2 = employerAddressLine2;
	}

	/**
	 * @return the loanID
	 */
	public String getLoanID() {
		return loanID;
	}

	/**
	 * @param loanID
	 *            the loanID to set
	 */
	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}

	/**
	 * @return the loanPurpose
	 */
	public String getLoanPurpose() {
		if(loanPurpose == null) return "";
		return loanPurpose;
	}

	/**
	 * @param loanPurpose
	 *            the loanPurpose to set
	 */
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	/**
	 * @return the loanFund
	 */
	public String getLoanFund() {
		if(loanFund == null) return "";
		return loanFund;
	}

	/**
	 * @param loanFund
	 *            the loanFund to set
	 */
	public void setLoanFund(String loanFund) {
		this.loanFund = loanFund;
	}

	/**
	 * @return the loanProductCode
	 */
	public String getProductCode() {
		if (productCode != null)
			return productCode;

		if (memberReference == null)
			return "";

		productCode = memberReference.substring(3, 7);

		return productCode;
	}

	/**
	 * @param loanProductCode
	 *            the loanProductCode to set
	 */
	public void setProductCode(String loanProductCode) {
		this.productCode = loanProductCode;
	}
	
	public String getFullNameKH() {
		fullNameKH = lastNameKH + " " + firstNameKH;
		return fullNameKH;
	}

	public void setFullNameKH(String fullNameKH) {
		this.fullNameKH = fullNameKH;
	}
	
	

	public String getFullNameEN() {
		if(fullNameEN == null){
			fullNameEN = lastNameEN + " " + firstNameEN;
		}
		return fullNameEN;
	}

	public void setFullNameEN(String fullNameEN) {
		this.fullNameEN = fullNameEN;
	}

	public String getBranchNameEN() {
		return branchNameEN;
	}

	public void setBranchNameEN(String branchNameEN) {
		this.branchNameEN = branchNameEN;
	}

	public String getSubNameEN() {
		return subNameEN;
	}

	public void setSubNameEN(String subNameEN) {
		this.subNameEN = subNameEN;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getAmountCurrency() {
		amountCurrency = amount + " " +currency;
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public Float getCbcFee() {
		if(amount==null) return cbcFee;
		switch (currency.toUpperCase()){
			case "USD":
				if(Float.parseFloat(amount) <= 500) cbcFee = 0.18f;
				else if(Float.parseFloat(amount) <= 10000) cbcFee = 2.5f;
				else cbcFee = 3.5f;
				break;
			case "KHR":
				if(Float.parseFloat(amount) <= 2000000) cbcFee = 0.18f;
				else if(Float.parseFloat(amount) <= 40000000) cbcFee = 2.5f;
				else cbcFee = 3.5f;
				break;
			case "THB":
				if(Float.parseFloat(amount) <= 15500) cbcFee = 0.18f;
				else if(Float.parseFloat(amount) <= 310000) cbcFee = 2.5f;
				else cbcFee = 3.5f;
		}
		return cbcFee;
	}

	public void setCbcFee(Float cbcFee) {
		this.cbcFee = cbcFee;
	}

	public String getCbcFeeCurrency() {			
		cbcFeeCurrency = String.valueOf(this.getCbcFee()) + " " + "USD";				
		return cbcFeeCurrency;
	}

	public void setCbcFeeCurrency(String cbcFeeCurrency) {
		this.cbcFeeCurrency = cbcFeeCurrency;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public Double getAmountformat() {
		amountformat = Double.parseDouble(amount);
		return amountformat;
	}

	public void setAmountformat(Double amountformat) {
		this.amountformat = amountformat;
	}

	public String getReqButton() {
		return reqButton;
	}

	public void setReqButton(String reqButton) {
		this.reqButton = reqButton;
	}

	/**
	 * @return the idType4
	 */
	public String getIdType4() {
		return idType4;
	}

	/**
	 * @param idType4 the idType4 to set
	 */
	public void setIdType4(String idType4) {
		this.idType4 = idType4;
	}

	/**
	 * @return the idType4Desc
	 */
	public String getIdType4Desc() {
		if(idType4 != null)
			idType4Desc = EnquiryVM.idTypeDesc(idType2);
		return idType4Desc;
	}

	/**
	 * @param idType4Desc the idType4Desc to set
	 */
	public void setIdType4Desc(String idType4Desc) {
		this.idType4Desc = idType4Desc;
	}

	/**
	 * @return the idNumber4
	 */
	public String getIdNumber4() {
		return idNumber4;
	}

	/**
	 * @param idNumber4 the idNumber4 to set
	 */
	public void setIdNumber4(String idNumber4) {
		this.idNumber4 = idNumber4;
	}

	/**
	 * @return the idExpiryDate4
	 */
	public String getIdExpiryDate4() {
		return idExpiryDate4;
	}

	/**
	 * @param idExpiryDate4 the idExpiryDate4 to set
	 */
	public void setIdExpiryDate4(String idExpiryDate4) {
		this.idExpiryDate4 = idExpiryDate4;
	}

	// Soknan 
	public CodeItem getIdTypeItem1() {
		if (idTypeItem1 != null)
			return idTypeItem1;

		idTypeItem1 = new CodeItem();

		if (idType1 != null) {

			idTypeItem1.setCode(idType1);
			if (idType1.equals("B")) {
				idTypeItem1.setDescription("BIRTH CERTIFICATE");
			} else if (idType1.equals("N")) {
				idTypeItem1.setDescription("NATIONAL ID");
			}else if (idType1.equals("F")) {
					idTypeItem1.setDescription("FAMILY BOOK");				
			} else if (idType1.equals("R")) {
				idTypeItem1.setDescription("RESIDENT BOOK");
			} else if (idType1.equals("G")) {
				idTypeItem1.setDescription("GOVT ISSUED ID");
			} else if (idType1.equals("V")) {
				idTypeItem1.setDescription("VOTERS REG. CARD");
			} else if (idType1.equals("O")) {
				idTypeItem1.setDescription("CERTIFIED ID DOCUMENT");
			}
		}
		return idTypeItem1;
	}

	public void setIdTypeItem1(CodeItem idTypeItem1) {
		this.idTypeItem1 = idTypeItem1;
		//setIdType1(null);
		if(idTypeItem1!=null){
			setIdType1(this.idTypeItem1.getCode());
		}
	}

	public CodeItem getIdTypeItem2() {
		if (idTypeItem2 != null)
			return idTypeItem2;

		idTypeItem2 = new CodeItem();

		if (idType2 != null) {

			idTypeItem2.setCode(idType2);
			if (idType2.equals("B")) {
				idTypeItem2.setDescription("BIRTH CERTIFICATE");
			} else if (idType2.equals("N")) {
				idTypeItem2.setDescription("NATIONAL ID");
			}else if (idType2.equals("F")) {
					idTypeItem2.setDescription("FAMILY BOOK");				
			} else if (idType2.equals("R")) {
				idTypeItem1.setDescription("RESIDENT BOOK");
			} else if (idType2.equals("G")) {
				idTypeItem1.setDescription("GOVT ISSUED ID");
			} else if (idType2.equals("V")) {
				idTypeItem1.setDescription("VOTERS REG. CARD");
			} else if (idType2.equals("O")) {
				idTypeItem2.setDescription("CERTIFIED ID DOCUMENT");
			}
		}
		return idTypeItem2;
	}

	public void setIdTypeItem2(CodeItem idTypeItem2) {
		this.idTypeItem2 = idTypeItem2;
		setIdType2(null);
		if(idTypeItem2!=null){
		setIdType2(this.idTypeItem2.getCode());
		}
	}

	public CodeItem getIdTypeItem3() {
		if (idTypeItem3 != null)
			return idTypeItem3;

		idTypeItem3 = new CodeItem();

		if (idType3 != null) {

			idTypeItem3.setCode(idType3);
			if (idType3.equals("B")) {
				idTypeItem3.setDescription("BIRTH CERTIFICATE");
			} else if (idType3.equals("N")) {
				idTypeItem1.setDescription("NATIONAL ID");
			}else if (idType3.equals("F")) {
					idTypeItem3.setDescription("FAMILY BOOK");				
			} else if (idType3.equals("R")) {
				idTypeItem3.setDescription("RESIDENT BOOK");
			} else if (idType3.equals("G")) {
				idTypeItem3.setDescription("GOVT ISSUED ID");
			} else if (idType3.equals("V")) {
				idTypeItem3.setDescription("VOTERS REG. CARD");
			} else if (idType3.equals("O")) {
				idTypeItem3.setDescription("CERTIFIED ID DOCUMENT");
			}else if (idType3.equals("P")) {
				idTypeItem3.setDescription("Passport");
			}else if (idType3.equals("T")) {
				idTypeItem3.setDescription("Tax Number");
			}else if (idType3.equals("D")) {
				idTypeItem3.setDescription("Drivers Licence");
			}
		}
		return idTypeItem3;
	}

	public void setIdTypeItem3(CodeItem idTypeItem3) {
		this.idTypeItem3 = idTypeItem3;
		setIdType3(null);
		if(idTypeItem3!=null){
		setIdType3(this.idTypeItem3.getCode());
		}
	}

	public CodeItem getMaritalStatusItem() {
		if (maritalStatusItem != null)
			return maritalStatusItem;

		maritalStatusItem= new CodeItem();		
		if (maritalStatus != null) {			
			if(maritalStatus.equals("S")){
				maritalStatusItem.setCode("S");
				maritalStatusItem.setDescription("Single");
			}else if (maritalStatus.equals("D")) {
				maritalStatusItem.setCode("D");
				maritalStatusItem.setDescription("Devorced");
			}else if(maritalStatus.equals("M")){
				maritalStatusItem.setCode("M");
				maritalStatusItem.setDescription("Married");
			}else if(maritalStatus.equals("P")){
				maritalStatusItem.setCode("P");
				maritalStatusItem.setDescription("Separated");
			}else if(maritalStatus.equals("U")){
				maritalStatusItem.setCode("U");
				maritalStatusItem.setDescription("Unknown");
			}else if(maritalStatus.equals("F")){
				maritalStatusItem.setCode("F");
				maritalStatusItem.setDescription("Defacto");
			}else if(maritalStatus.equals("W")){
				maritalStatusItem.setCode("W");
				maritalStatusItem.setDescription("Widow/Widower");
			}
		}
		return maritalStatusItem;
	}

	public void setMaritalStatusItem(CodeItem maritalStatusItem) {
		this.maritalStatusItem = maritalStatusItem;		
		setMaritalStatus(this.maritalStatusItem.getCode());
	}

	public CodeItem getSexItem() {
		if (sexItem != null)
			return sexItem;

		sexItem = new CodeItem();

		if (sex != null) {
			if(sex.equals("M")){
				sexItem.setCode("M");
				sexItem.setDescription("Male");
			}else{
				sexItem.setCode("F");
				sexItem.setDescription("Female");
			}
		}
		return sexItem;
	}

	public void setSexItem(CodeItem sexItem) {
		this.sexItem = sexItem;
		if(sexItem!=null){
		setSex(this.sexItem.getCode());
		}
	}

	public CodeItem getProvinceC() {
		if(provinceC!=null){
			return provinceC;
		}
		provinceC = new CodeItem();
		if(province !=null){
			provinceC.setCode(dProvince);
		}
		return provinceC;
	}
	public void setProvinceC(CodeItem provinceC) {
		this.provinceC = provinceC;		
		setdProvince(this.provinceC.getCode());
		
	}
	public CodeItem getDistrictC() {
		if(districtC!=null){
			return districtC;
		}
		districtC = new CodeItem();
		if(dDistrict !=null){
			districtC.setCode(dDistrict);
		}
		return districtC;
	}
	public void setDistrictC(CodeItem districtC) {
		this.districtC = districtC;		
		setdDistrict(this.districtC.getCode());
		
	}
	public CodeItem getCommuneC() {
		if(communeC!=null){
			return communeC;
		}
		communeC = new CodeItem();
		if(dCommune !=null){
			communeC.setCode(dCommune);
		}
		return communeC;
	}
	public void setCommuneC(CodeItem communeC) {
		this.communeC = communeC;		
		setdCommune(this.communeC.getCode());		
		
	}
	
	
	public CodeItem getVillageC() {
		if(villageC!=null){
			return villageC;
		}
		villageC = new CodeItem();
		if(dVillage !=null){
			villageC.setCode(dVillage);
		}
		return villageC;
	}
	public void setVillageC(CodeItem villageC) {
		this.villageC = villageC;
		setdVillage(this.villageC.getCode());		
	}
	public CodeItem getProvinceD() {
		if(provinceD!=null){
			return provinceD;
		}
		provinceD = new CodeItem();
		if(province !=null){
			provinceD.setCode(province);
		}
		return provinceD;
	}
	public void setProvinceD(CodeItem provinceD) {
		this.provinceD = provinceD;		
		setProvince(this.provinceD.getCode());
		
	}
	public CodeItem getDistrictD() {
		if(districtD!=null){
			return districtD;
		}
		districtD = new CodeItem();
		if(district !=null){
			districtD.setCode(district);
		}
		return districtD;
	}
	public void setDistrictD(CodeItem districtD) {
		this.districtD = districtD;		
		setDistrict(this.districtD.getCode());
		
	}
	public CodeItem getCommuneD() {
		if(communeD!=null){
			return communeD;
		}
		communeD = new CodeItem();
		if(commune !=null){
			communeD.setCode(commune);
		}
		return communeD;
	}
	public void setCommuneD(CodeItem communeD) {
		this.communeD = communeD;		
		setCommune(this.communeD.getCode());
		
	}
	public CodeItem getVillageD() {
		if(villageD!=null){
			return villageD;
		}
		villageD = new CodeItem();
		if(village !=null){
			villageD.setCode(village);
		}
		return villageD;
	}
	public void setVillageD(CodeItem villageD) {
		this.villageD = villageD;		
		setVillage(this.villageD.getCode());
		
	}

	public CodeItem getProvinceE() {
		return provinceE;
	}

	public void setProvinceE(CodeItem provinceE) {
		this.provinceE = provinceE;
	}

	public CodeItem getDistrictE() {
		return districtE;
	}

	public void setDistrictE(CodeItem districtE) {
		this.districtE = districtE;
	}

	public CodeItem getCommuneE() {
		return communeE;
	}

	public void setCommuneE(CodeItem communeE) {
		this.communeE = communeE;
	}

	public CodeItem getVillageE() {
		return villageE;
	}

	public void setVillageE(CodeItem villageE) {
		this.villageE = villageE;
	}

	public CodeItem getSelfEmployedItem() {
		selfEmployedItem = new CodeItem();

		if (selfEmployed != null) {
			if(selfEmployed.equals("Y")){
				selfEmployedItem.setCode("Y");
				selfEmployedItem.setDescription("Yes");
			}else{
				selfEmployedItem.setCode("N");
				selfEmployedItem.setDescription("No");
			}
		}
		return selfEmployedItem;
	}
	public void setSelfEmployedItem(CodeItem selfEmployedItem) {
		this.selfEmployedItem = selfEmployedItem;
		if(selfEmployedItem!=null){
			setSelfEmployed(this.selfEmployedItem.getCode());
		}
	}
	public CodeItem getPhoneNumberTypeC() {
		if(phoneNumberTypeC!=null){
			return phoneNumberTypeC;
		}
		phoneNumberTypeC = new CodeItem();
		if(phone1!=null){
			phoneNumberTypeC.setCode(phone1.getType());
			if(phone1.getType().equals("O")){
				phoneNumberTypeC.setCode("O");
				phoneNumberTypeC.setDescription("Office");
			}
			if(phone1.getType().equals("F")){
				phoneNumberTypeC.setCode("O");
				phoneNumberTypeC.setDescription("Office");
			}
			if(phone1.getType().equals("H")){
				phoneNumberTypeC.setCode("H");
				phoneNumberTypeC.setDescription("Home");
			}
			if(phone1.getType().equals("M")){
				phoneNumberTypeC.setCode("M");
				phoneNumberTypeC.setDescription("Mobile");
			}
			if(phone1.getType().equals("U")){
				phoneNumberTypeC.setCode("U");
				phoneNumberTypeC.setDescription("Unknown");
			}
		}
		return phoneNumberTypeC;
	}
	public void setPhoneNumberTypeC(CodeItem phoneNumberTypeC) {
		this.phoneNumberTypeC = phoneNumberTypeC;		
		phone1.setType(this.phoneNumberTypeC.getCode());	
	}

	public CodeItem getAddressTypeC() {
		if (addressTypeC != null)
			return addressTypeC;

		addressTypeC= new CodeItem();

		if (addressType != null) {
			if(addressType.equals("RESID")){
				addressTypeC.setCode("RESID");
				addressTypeC.setDescription("Residential");
			}else if (addressType.equals("WORK")) {
				addressTypeC.setCode("WORK");
				addressTypeC.setDescription("Work");
			}
		}
		return addressTypeC;
	}
	public void setAddressTypeC(CodeItem addressTypeC) {
		this.addressTypeC = addressTypeC;		
		setAddressType(this.addressTypeC.getCode());		
	}

	public CodeItem getAddressTypeE() {
		if (addressTypeE != null)
			return addressTypeE;

		addressTypeE= new CodeItem();

		if (employerAddressType != null) {
			if(employerAddressType.equals("RESID")){
				addressTypeE.setCode("RESID");
				addressTypeE.setDescription("Residential");
			}else if (employerAddressType.equals("WORK")) {
				addressTypeE.setCode("WORK");
				addressTypeE.setDescription("Work");
			}
		}
		return addressTypeE;
	}

	public void setAddressTypeE(CodeItem addressTypeE) {
		this.addressTypeE = addressTypeE;
		setEmployerAddressType(this.addressTypeE.getCode());	
	}

	public CodeItem getCurrencyItem() {
		if (currencyItem != null)
			return currencyItem;

		currencyItem= new CodeItem();

		if (currency != null) {
			if(currency.equals("USD")){
				currencyItem.setCode("USD");
				currencyItem.setDescription("US Dollar");
			}else if (currency.equals("KHR")) {
				currencyItem.setCode("KHR");
				currencyItem.setDescription("Riel");
			}else if (currency.equals("THB")) {
				currencyItem.setCode("THB");
				currencyItem.setDescription("Baht");
			}
		}
		return currencyItem;
	}

	public void setCurrencyItem(CodeItem currencyItem) {
		this.currencyItem = currencyItem;
		setCurrency(currencyItem.getCode());
	}
	
	public CodeItem getCurrencyItem1() {
		if (currencyItem1 != null)
			return currencyItem1;

		currencyItem1= new CodeItem();

		if (currency1 != null) {
			if(currency1.equals("USD")){
				currencyItem1.setCode("USD");
				currencyItem1.setDescription("US Dollar");
			}else if (currency1.equals("KHR")) {
				currencyItem1.setCode("KHR");
				currencyItem1.setDescription("Riel");
			}else if (currency1.equals("THB")) {
				currencyItem1.setCode("THB");
				currencyItem1.setDescription("Baht");
			}
		}
		return currencyItem1;
	}

	public void setCurrencyItem1(CodeItem currencyItem1) {
		this.currencyItem1 = currencyItem1;
		setCurrency1(currencyItem1.getCode());
	}

	public CodeItem getProductItem() {
		if (productItem != null)
			return productItem;

		productItem= new CodeItem();
		if (productType != null) {
			if(productType.equals("PEL")){
				productItem.setCode("PEL");
				productItem.setDescription("Personal Loan");
			}else if (productType.equals("CMT")) {
				productItem.setCode("CMT");
				productItem.setDescription("Community Loan");
			}else if (productType.equals("EDU")) {
				productItem.setCode("EDU");
				productItem.setDescription("Education Loan");
			}else if (productType.equals("SHL")) {
				productItem.setCode("SHL");
				productItem.setDescription("Staff Housing Loan");
			}else if (productType.equals("AGL")) {
				productItem.setCode("AGL");
				productItem.setDescription("Agriculture Loan");
			}else if (productType.equals("AFI")) {
				productItem.setCode("AFI");
				productItem.setDescription("Asset Financing");
			}else if (productType.equals("WCL")) {
				productItem.setCode("WCL");
				productItem.setDescription("Working Capialtloan");
			}else if (productType.equals("CDL")) {
				productItem.setCode("CDL");
				productItem.setDescription("Consumer Durables Loan");
			}else if (productType.equals("HIL")) {
				productItem.setCode("HIL");
				productItem.setDescription("Home Improvement Loan");
			}else if (productType.equals("MCL")) {
				productItem.setCode("MCL");
				productItem.setDescription("Machinery Loan");
			}else if (productType.equals("MRA")) {
				productItem.setCode("MRA");
				productItem.setDescription("Real Estate Loan");
			}else if (productType.equals("EML")) {
				productItem.setCode("EML");
				productItem.setDescription("Emergency Loan");
			}else if (productType.equals("GRL")) {
				productItem.setCode("GRL");
				productItem.setDescription("Green Loan");
			}else if (productType.equals("MBL")) {
				productItem.setCode("MBL");
				productItem.setDescription("Mobile Phone Loan");
			}else if (productType.equals("CPL")) {
				productItem.setCode("CPL");
				productItem.setDescription("Computer Loan");
			}else if (productType.equals("MTL")) {
				productItem.setCode("MTL");
				productItem.setDescription("Motor Loan");
			}else if (productType.equals("STL")) {
				productItem.setCode("STL");
				productItem.setDescription("Staff Loan");
			}
			
		}		
		return productItem;
	}

	public void setProductItem(CodeItem productItem) {
		this.productItem = productItem;
		setProductType(productItem.getCode());
	}

	public CodeItem getAccountTypeItem() {	
		if (accountTypeItem != null)
			return accountTypeItem;

		accountTypeItem= new CodeItem();

		if (accountType != null) {
			if(accountType.equals("S")){
				accountTypeItem.setCode("S");
				accountTypeItem.setDescription("Single");
			}else if (accountType.equals("J")) {
				accountTypeItem.setCode("J");
				accountTypeItem.setDescription("Joint");
			}else if (accountType.equals("G")) {
				accountTypeItem.setCode("G");
				accountTypeItem.setDescription("Group");
			}
		}		
		return accountTypeItem;
	}

	public void setAccountTypeItem(CodeItem accountTypeItem) {
		this.accountTypeItem = accountTypeItem;
		setAccountType(accountTypeItem.getCode());
	}

	public String getIdType1Desc() {
		if(idType1 != null)
			idType1Desc = EnquiryVM.idTypeDesc(idType1);
		return idType1Desc;
	}

	public void setIdType1Desc(String idType1Desc) {
		this.idType1Desc = idType1Desc;
	}
	
	
	//Soknan
	
	

	
}
