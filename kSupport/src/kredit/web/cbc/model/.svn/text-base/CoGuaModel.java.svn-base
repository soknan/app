package kredit.web.cbc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.sun.org.apache.bcel.internal.generic.Select;

import kredit.web.core.util.model.CodeItem;
import kredit.web.cbc.model.Telco;
import kredit.web.cbc.model.facade.CbcFacade;
import kredit.web.cbc.model.facade.TelcoFacade;

@Entity
@Table(name = "CBC_CO_GUA")
public class CoGuaModel{
	@Id
	@SequenceGenerator(name = "CBC_CO_GUA_SEQ") 
	Integer id;
	String branchCode;
	String customerCode;
	String accountNumber;
	String customerName;
	String productType;
	Double customerAmount;
	String customerCurrency;
	String idType1;
	String idNumber1;
	Date expiryDate1;
	String idType2;
	String idNumber2;
	Date expiryDate2;
	String idType3;
	String idNumber3;
	Date expiryDate3;
	Date dob;
	String sex;
	String maritalStatus;
	String nationalityCode;	
	String pobCountry;
	String pobProvince;
	String pobDistrict;
	String pobCommune;
	String pobVillage;
	String firstNameKH;
	String lastNameKH;
	@Transient
	String fullNameKH;
	String firstNameEN;
	String lastNameEN;
	String addressType;
	String addressLine1;
	String country;
	String city;
	String province;
	String district;
	String commune;
	String village;
	String phoneNumberType1;
	String phoneCountryCode1;
	String areaCode1;	
	String phoneNumber1;
	String extension1;
	String phoneNumberType2;
	String phoneCountryCode2;
	String areaCode2;	
	String phoneNumber2;
	String extension2;
	String employmentType;
	String selfEmployed = "";
	Integer langthOfService;
	String occupation;
	Double totalMonthlyIncome;
	@Transient
	Double totalMonthlyIncome2;
	String employedCurrency;
	String employerName;
	String employerAddressType;
	String employerAddressLine1;
	String employerCity = "";
	String employerCountryCode;
	String employerDistrict;
	String employerCommune;
	String employerProvince;
	String employerVillage;
	String create_by;
	String type;
	String validated;
	@Transient
	String cusFirstNameEN = "";
	@Transient
	String cusLastNameEN= "";
	@Transient
	String cusFullNameEN = "";
	
	@Transient
	String enqLoanPurpose;
	@Transient
	String enqProductCode;
	
	@Version
	Date create_on;	
	String change_by;
	@Version
	Date change_on;
	@Transient
	CodeItem idTypeItem1;
	@Transient
	CodeItem idTypeItem2;
	@Transient
	CodeItem idTypeItem3;
	@Transient
	CodeItem maritalStatusItem;
	@Transient
	CodeItem sexItem;
	@Transient
	CodeItem addressTypeC;
	@Transient
	CodeItem addressTypeE;
	@Transient
	CodeItem provinceC;
	@Transient
	CodeItem districtC;
	@Transient
	CodeItem communeC;
	@Transient
	CodeItem villageC;
	@Transient
	CodeItem provinceD;
	@Transient
	CodeItem districtD;
	@Transient
	CodeItem communeD;
	@Transient
	CodeItem villageD;
	@Transient
	CodeItem provinceE;
	@Transient
	CodeItem districtE;
	@Transient
	CodeItem communeE;
	@Transient
	CodeItem villageE;
	@Transient
	CodeItem selfEmployedItem;
	@Transient
	CodeItem phoneNumberTypeC;
	private Telco phone1;
	private Telco phone2;
	
	public Telco getPhone1() {		
		if (phone1 != null)
			return phone1;
		phone1 = TelcoFacade.getTelco(phoneNumber1);
		if(phone1.getType().equals("U"))
			phone1.setPhoneNumber("000");
		setPhoneNumberType1(phone1.getType());
		return phone1;
	}

	public void setPhone1(Telco phone1) {
		this.phone1 = phone1;
	}

	public Telco getPhone2() {
		if (phone2 != null)
			return phone2;
		phone2 = TelcoFacade.getTelco(phoneNumber2);
		if (phone2.getType().equals("U"))
			phone2.setPhoneNumber("000");
		return phone2;
	}

	public void setPhone2(Telco phone2) {
		this.phone2 = phone2;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getProductType() {		
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Double getCustomerAmount() {
		return customerAmount;
	}
	public void setCustomerAmount(Double customerAmount) {
		this.customerAmount = customerAmount;
	}
	public String getCustomerCurrency() {
		return customerCurrency;
	}
	public void setCustomerCurrency(String customerCurrency) {
		this.customerCurrency = customerCurrency;
	}
	public String getIdType1() {
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
	public Date getExpiryDate1() {
		return expiryDate1;
	}
	public void setExpiryDate1(Date expiryDate1) {
		this.expiryDate1 = expiryDate1;
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
	public Date getExpiryDate2() {		
		return expiryDate2;
	}
	public void setExpiryDate2(Date expiryDate2) {
		this.expiryDate2 = expiryDate2;
	}
	public String getIdType3() {
		return idType3;
	}
	public void setIdType3(String idType3) {
		this.idType3 = idType3;
	}
	public String getIdNumber3() {
		return idNumber3;
	}
	public void setIdNumber3(String idNumber3) {
		this.idNumber3 = idNumber3;
	}
	public Date getExpiryDate3() {
		return expiryDate3;
	}
	public void setExpiryDate3(Date expiryDate3) {
		this.expiryDate3 = expiryDate3;
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
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	public String getPobCountry() {
		return pobCountry;
	}
	public void setPobCountry(String pobCountry) {
		this.pobCountry = pobCountry;
	}
	public String getPobProvince() {
		return pobProvince;
	}
	public void setPobProvince(String pobProvince) {
		this.pobProvince = pobProvince;
	}
	public String getPobDistrict() {
		return pobDistrict;
	}
	public void setPobDistrict(String pobDistrict) {
		this.pobDistrict = pobDistrict;
	}
	public String getPobCommune() {
		return pobCommune;
	}
	public void setPobCommune(String pobCommune) {
		this.pobCommune = pobCommune;
	}
	public String getPobVillage() {
		return pobVillage;
	}
	public void setPobVillage(String pobVillage) {
		this.pobVillage = pobVillage;
	}
	public String getFirstNameKH() {
		if(firstNameKH==null){
			return firstNameKH;
		}
		if(checkUnicode(firstNameKH, true)){			
			setFirstNameEN(firstNameKH);
			setFirstNameKH("");
		}
		return firstNameKH;
	}
	public void setFirstNameKH(String firstNameKH) {
		this.firstNameKH = firstNameKH;
	}
	public String getLastNameKH() {
		if(lastNameKH==null){
			return lastNameKH;
		}
		if(checkUnicode(lastNameKH, true)){			
			setLastNameEN(lastNameKH);
			setLastNameKH("");
		}
		return lastNameKH;
	}
	public void setLastNameKH(String lastNameKH) {
		this.lastNameKH = lastNameKH;
	}
	public String getFirstNameEN() {
		return firstNameEN;
	}
	public void setFirstNameEN(String firstNameEN) {
		this.firstNameEN = firstNameEN;
	}
	public String getLastNameEN() {
		return lastNameEN;
	}
	public void setLastNameEN(String lastNameEN) {
		this.lastNameEN = lastNameEN;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCommune() {
		return commune;
	}
	public void setCommune(String commune) {
		this.commune = commune;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getSelfEmployed() {
		return selfEmployed;
	}
	public void setSelfEmployed(String selfEmployed) {
		this.selfEmployed = selfEmployed;
	}
	
	public CodeItem getSelfEmployedItem() {
		if (selfEmployedItem != null)
			return selfEmployedItem;

		selfEmployedItem = new CodeItem();

		if(selfEmployed == null){
			selfEmployedItem.setCode("Y");
			selfEmployedItem.setDescription("Yes");
		}else if(selfEmployed.equals("F")){
			selfEmployedItem.setCode("N");
			selfEmployedItem.setDescription("No");
		}else if(selfEmployed.equals("T")){
			selfEmployedItem.setCode("Y");
			selfEmployedItem.setDescription("Yes");
		}else if(selfEmployed.equals("U")){
			selfEmployedItem.setCode("Y");
			selfEmployedItem.setDescription("Yes");
		}else if(selfEmployed.equals("S")){
			selfEmployedItem.setCode("Y");
			selfEmployedItem.setDescription("Yes");
		}else if(selfEmployed.equals("P")){
			selfEmployedItem.setDescription("Yes");
			selfEmployedItem.setCode("Y");
		}else{
			selfEmployedItem.setDescription("Yes");
			selfEmployedItem.setCode("Y");
		}
		return selfEmployedItem;
	}
	public void setSelfEmployedItem(CodeItem selfEmployedItem) {
		this.selfEmployedItem = selfEmployedItem;
		if(selfEmployedItem!=null){
			setSelfEmployed(this.selfEmployedItem.getCode());
		}
	}
	public String getPhoneNumberType1() {		
		return phoneNumberType1;
	}
	public void setPhoneNumberType1(String phoneNumberType1) {
		this.phoneNumberType1 = phoneNumberType1;
	}
	public String getPhoneCountryCode1() {
		return phoneCountryCode1;
	}
	public void setPhoneCountryCode1(String phoneCountryCode1) {
		this.phoneCountryCode1 = phoneCountryCode1;
	}
	public String getAreaCode1() {
		return areaCode1;
	}
	public void setAreaCode1(String areaCode1) {
		this.areaCode1 = areaCode1;
	}
	public String getPhoneNumber1() {
		return phoneNumber1;
	}
	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}
	public String getExtension1() {
		return extension1;
	}
	public void setExtension1(String extension1) {
		this.extension1 = extension1;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	
	public Integer getLangthOfService() {
		if(selfEmployed!=null){
			if(getSelfEmployed().equals("U")){
				langthOfService = 1;
			}
		}
		
		return langthOfService;
	}
	public void setLangthOfService(Integer langthOfService) {
		this.langthOfService = langthOfService;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public Double getTotalMonthlyIncome() {		
		if (totalMonthlyIncome == null){
			totalMonthlyIncome = Double.valueOf(0);
			if(getSelfEmployedItem().getCode().equals("Y") && totalMonthlyIncome2 != null){
				totalMonthlyIncome = totalMonthlyIncome2;
			}
		}
		return totalMonthlyIncome;
	}
	public void setTotalMonthlyIncome(Double totalMonthlyIncome) {
		this.totalMonthlyIncome = totalMonthlyIncome;
	}
	
	public Double getTotalMonthlyIncome2() {
		return totalMonthlyIncome2;
	}

	public void setTotalMonthlyIncome2(Double totalMonthlyIncome2) {
		this.totalMonthlyIncome2 = totalMonthlyIncome2;
	}

	public String getEmployedCurrency() {
		return employedCurrency;
	}
	public void setEmployedCurrency(String employedCurrency) {
		this.employedCurrency = employedCurrency;
	}
	public String getEmployerName() {
		if (employerName == null){
			employerName = new String();
			if(getSelfEmployedItem().getCode().equals("Y")){
				employerName = cusLastNameEN+" "+cusFirstNameEN;
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
			if(getSelfEmployedItem().getCode().equals("Y")){
				if(addressLine1 != null){
					employerAddressLine1 = addressLine1;
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
			if(getSelfEmployedItem().getCode().equals("Y")){				
				employerCity =province.toString();
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
	public String getEmployerDistrict() {
		return employerDistrict;
	}
	public void setEmployerDistrict(String employerDistrict) {
		this.employerDistrict = employerDistrict;
	}
	public String getEmployerCommune() {
		return employerCommune;
	}
	public void setEmployerCommune(String employerCommune) {
		this.employerCommune = employerCommune;
	}
	public String getEmployerProvince() {
		return employerProvince;
	}
	public void setEmployerProvince(String employerProvince) {
		this.employerProvince = employerProvince;
	}
	public String getEmployerVillage() {
		return employerVillage;
	}
	public void setEmployerVillage(String employerVillage) {
		this.employerVillage = employerVillage;
	}
	public String getFullNameKH() {
		if(lastNameKH == null || lastNameKH.equals("")){
			return fullNameKH;		
		}
		fullNameKH = lastNameKH+" "+firstNameKH; 
		return fullNameKH;
	}
	public void setFullNameKH(String fullNameKH) {
		this.fullNameKH = fullNameKH;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public Date getCreate_on() {
		return create_on;
	}
	public void setCreate_on(Date create_on) {
		this.create_on = create_on;
	}
	public String getChange_by() {
		return change_by;
	}
	public void setChange_by(String change_by) {
		this.change_by = change_by;
	}
	public Date getChange_on() {
		return change_on;
	}
	public void setChange_on(Date change_on) {
		this.change_on = change_on;
	}
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
		setIdType1(null);
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
			}else if(maritalStatus.equals("W")){
				maritalStatusItem.setCode("W");
				maritalStatusItem.setDescription("Widow/Widower");
			}else if(maritalStatus.equals("F")){
				maritalStatusItem.setCode("F");
				maritalStatusItem.setDescription("Defactor");
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
			provinceC.setCode(pobProvince);
		}
		return provinceC;
	}
	public void setProvinceC(CodeItem provinceC) {
		this.provinceC = provinceC;		
		setPobProvince(this.provinceC.getCode());
		
	}
	public CodeItem getDistrictC() {
		if(districtC!=null){
			return districtC;
		}
		districtC = new CodeItem();
		if(pobDistrict !=null){
			districtC.setCode(pobDistrict);
		}
		return districtC;
	}
	public void setDistrictC(CodeItem districtC) {
		this.districtC = districtC;		
		setPobDistrict(this.districtC.getCode());
		
	}
	public CodeItem getCommuneC() {
		if(communeC!=null){
			return communeC;
		}
		communeC = new CodeItem();
		if(pobCommune !=null){
			communeC.setCode(pobCommune);
		}
		return communeC;
	}
	public void setCommuneC(CodeItem communeC) {
		this.communeC = communeC;		
		setPobCommune(this.communeC.getCode());		
		
	}
	
	
	public CodeItem getVillageC() {
		if(villageC!=null){
			return villageC;
		}
		villageC = new CodeItem();
		if(pobVillage !=null){
			villageC.setCode(pobVillage);
		}
		return villageC;
	}
	public void setVillageC(CodeItem villageC) {
		this.villageC = villageC;
		setPobVillage(this.villageC.getCode());		
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
		if(addressTypeE!=null){
			setEmployerAddressType(this.addressTypeE.getCode());
		}
	}
	public CodeItem getProvinceE() {
		if(provinceE!=null){
			return provinceE;
		}
		provinceE = new CodeItem();
		if(employerProvince !=null){
			provinceE.setCode(employerProvince);
		}
		return provinceE;
	}
	public void setProvinceE(CodeItem provinceE) {
		this.provinceE = provinceE;
		setEmployerProvince(this.provinceE.getCode());		
	}
	public CodeItem getDistrictE() {
		if(districtE!=null){
			return districtE;
		}
		districtE = new CodeItem();
		if(employerDistrict !=null){
			districtE.setCode(employerDistrict);
		}
		return districtE;
	}
	public void setDistrictE(CodeItem districtE) {
		this.districtE = districtE;		
		setEmployerDistrict(this.getDistrictE().getCode());
		
	}
	public CodeItem getCommuneE() {
		if(communeE!=null){
			return communeE;
		}
		communeE = new CodeItem();		
		if(employerCommune !=null){
			communeE.setCode(employerCommune);
		}
		return communeE;
	}
	public void setCommuneE(CodeItem communeE) {
		this.communeE = communeE;		
		setEmployerCommune(this.getCommuneE().getCode());
		
	}
	public CodeItem getVillageE() {
		if(villageE!=null){
			return villageE;
		}
		villageE = new CodeItem();
		if(employerVillage !=null){
			villageE.setCode(employerVillage);
		}
		return villageE;
	}
	public void setVillageE(CodeItem villageE) {
		this.villageE = villageE;		
		setEmployerVillage(this.getVillageE().getCode());
		
	}
	
	public CodeItem getPhoneNumberTypeC() {
		if(phoneNumberTypeC!=null){
			return phoneNumberTypeC;
		}
		phoneNumberTypeC = new CodeItem();
		if(phoneNumberType1!=null){
			phoneNumberTypeC.setCode(phoneNumberType1);
			if(phoneNumberType1.equals("O")){
				phoneNumberTypeC.setCode("O");
				phoneNumberTypeC.setDescription("Office");
			}
			if(phoneNumberType1.equals("F")){
				phoneNumberTypeC.setCode("O");
				phoneNumberTypeC.setDescription("Office");
			}
			if(phoneNumberType1.equals("H")){
				phoneNumberTypeC.setCode("H");
				phoneNumberTypeC.setDescription("Home");
			}
			if(phoneNumberType1.equals("M")){
				phoneNumberTypeC.setCode("M");
				phoneNumberTypeC.setDescription("Mobile");
			}
			if(phoneNumberType1.equals("U")){
				phoneNumberTypeC.setCode("U");
				phoneNumberTypeC.setDescription("Unknown");
			}
		}
		return phoneNumberTypeC;
	}
	public void setPhoneNumberTypeC(CodeItem phoneNumberTypeC) {
		this.phoneNumberTypeC = phoneNumberTypeC;		
		setPhoneNumberType1(this.phoneNumberTypeC.getCode());	
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValidated() {
		return validated;
	}
	public void setValidated(String validated) {
		this.validated = validated;
	}
	public String getPhoneNumberType2() {
		return phoneNumberType2;
	}
	public void setPhoneNumberType2(String phoneNumberType2) {
		this.phoneNumberType2 = phoneNumberType2;
	}
	public String getPhoneCountryCode2() {
		return phoneCountryCode2;
	}
	public void setPhoneCountryCode2(String phoneCountryCode2) {
		this.phoneCountryCode2 = phoneCountryCode2;
	}
	public String getAreaCode2() {
		return areaCode2;
	}
	public void setAreaCode2(String areaCode2) {
		this.areaCode2 = areaCode2;
	}
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	public String getExtension2() {
		return extension2;
	}
	public void setExtension2(String extension2) {
		this.extension2 = extension2;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCusFirstNameEN() {
		return cusFirstNameEN;
	}

	public void setCusFirstNameEN(String cusFirstNameEN) {
		this.cusFirstNameEN = cusFirstNameEN;
	}

	public String getCusLastNameEN() {
		return cusLastNameEN;
	}

	public void setCusLastNameEN(String cusLastNameEN) {
		this.cusLastNameEN = cusLastNameEN;
	}

	public String getCusFullNameEN() {
		if(cusFullNameEN == null){
			if(cusLastNameEN == null || cusFirstNameEN.equals("")){
				return cusFullNameEN;		
			}
			cusFullNameEN = cusLastNameEN + " " + cusFirstNameEN;
		}
		return cusFullNameEN;
	}

	public void setCusFullNameEN(String cusFullNameEN) {
		this.cusFullNameEN = cusFullNameEN;
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
	
}
