package kredit.web.util.transfer.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import kredit.web.core.model.Domain;

@Entity
@Table(name="INC_SAVING_TRNF")
public class SavingTrnf extends Domain {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private String account_no;
	private String customer_id;
	private String cif_kredit;
	private String alt_acc_no;
	private String full_name;
	private String short_name;
	private String khmer_name;
	private String currency;
	private Double exch_rate;
	private Date maturity_date;
	private String account_class;
	private String account_class_description;
	private Date ac_open_date;
	private String account_type;
	private String mode_of_operation;
	private Double deposit_amount;
	private Double balance_amount;
	private Double lcy_balance_amount;
	private Double avl_bal;
	private Integer deposit_tenor;
	private Integer no_of_day;
	private Double interest_rate;
	private Double tax;
	private String auto_rollover_maturity;
	private String rollover_type;
	private Integer no_of_rollovers;
	private String homeno;
	private String village;
	private String commune;
	private String district;
	private String province;
	private String d_addressline1;
	private String d_village;
	private String d_commune;
	private String d_district;
	private String d_province;
	private String p_addressline1;
	private String p_village;
	private String p_commune;
	private String p_district;
	private String p_province;
	private String credit_officer_sv;
	private String credit_officer_id;
	private String credit_officer_name;	
	private String cif_joint_holder_number1;
	private String joint_holder_name1;
	private String cif_joint_holder_number2;
	private String joint_holder_name2;
	private String cif_joint_holder_number3;
	private String joint_holder_name3;
	private String cif_joint_holder_number4;
	private String joint_holder_name4;
	private String customer_type;
	private String customer_category;
	private String cust_cat_desc;
	private String cust_classification;
	private String cust_class_desc;
	private String customer_prefix;
	private String sex;
	private Date date_of_birth;
	private String resident_status;
	private String telephone;
	private String mobile_number;
	private String e_mail;
	private String fax;
	private String country;
	private String nationality;
	private String national_id;
	private Date ppt_iss_date;
	private Date ppt_exp_date;
	private String passport_no;
	private String identity_type1;
	private String identity_number1;
	private String identity_type2;
	private String identity_number2;
	private String poverty_class;
	private String marital_status;
	private String educational_status;
	private Integer dependent_children;
	private Integer dependent_others;
	private String corporate_name;
	private Date incorp_date;
	private Double capital;
	private Double networth;
	private String business_description;
	private String director_name;
	private String d_telephone;
	private String d_mobile_number;
	private String d_e_mail;
	private String ac_stat_dormant;
	private String ac_stat_frozen;
	private Date int_start_date;
	private Date chg_start_date;
	private Double accrual_int;
	private Double accrual_tax;
	private Double acquired_int_amt;
	private Double acquired_tax_amt;
	private String saving_acc;
	private String drawn_down_acct;
	private String drawn_down_acct_name;
	private Date last_liq_dt;
	private Date next_liq_dt;
	private Double int_am_maturity;
	private Double int_liquidated_to_date;
	private Date report_date;

	public String getAccount_no() {
		return account_no;
	}
	
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	
	public String getCustomer_id() {
		return customer_id;
	}
	
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	
	public String getCif_kredit() {
		return cif_kredit;
	}
	
	public void setCif_kredit(String cif_kredit) {
		this.cif_kredit = cif_kredit;
	}
	
	public String getAlt_acc_no() {
		return alt_acc_no;
	}
	
	public void setAlt_acc_no(String alt_acc_no) {
		this.alt_acc_no = alt_acc_no;
	}
	
	public String getFull_name() {
		return full_name;
	}
	
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	
	public String getShort_name() {
		return short_name;
	}
	
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	
	public String getKhmer_name() {
		return khmer_name;
	}
	
	public void setKhmer_name(String khmer_name) {
		this.khmer_name = khmer_name;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public Double getExch_rate() {
		return exch_rate;
	}
	
	public void setExch_rate(Double exch_rate) {
		this.exch_rate = exch_rate;
	}
	
	public Date getMaturity_date() {
		return maturity_date;
	}
	
	public void setMaturity_date(Date maturity_date) {
		this.maturity_date = maturity_date;
	}
	
	public String getAccount_class() {
		return account_class;
	}
	
	public void setAccount_class(String account_class) {
		this.account_class = account_class;
	}
	
	public String getAccount_class_description() {
		return account_class_description;
	}
	
	public void setAccount_class_description(String account_class_description) {
		this.account_class_description = account_class_description;
	}
	
	public Date getAc_open_date() {
		return ac_open_date;
	}
	
	public void setAc_open_date(Date ac_open_date) {
		this.ac_open_date = ac_open_date;
	}
	
	public String getAccount_type() {
		return account_type;
	}
	
	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}
	
	public String getMode_of_operation() {
		return mode_of_operation;
	}
	
	public void setMode_of_operation(String mode_of_operation) {
		this.mode_of_operation = mode_of_operation;
	}
	
	public Double getDeposit_amount() {
		return deposit_amount;
	}
	
	public void setDeposit_amount(Double deposit_amount) {
		this.deposit_amount = deposit_amount;
	}
	
	public Double getBalance_amount() {
		return balance_amount;
	}
	
	public void setBalance_amount(Double balance_amount) {
		this.balance_amount = balance_amount;
	}
	
	public Double getLcy_balance_amount() {
		return lcy_balance_amount;
	}
	
	public void setLcy_balance_amount(Double lcy_balance_amount) {
		this.lcy_balance_amount = lcy_balance_amount;
	}
	
	public Double getAvl_bal() {
		return avl_bal;
	}
	
	public void setAvl_bal(Double avl_bal) {
		this.avl_bal = avl_bal;
	}
	
	public Integer getDeposit_tenor() {
		return deposit_tenor;
	}
	
	public void setDeposit_tenor(Integer deposit_tenor) {
		this.deposit_tenor = deposit_tenor;
	}
	
	public Integer getNo_of_day() {
		return no_of_day;
	}
	
	public void setNo_of_day(Integer no_of_day) {
		this.no_of_day = no_of_day;
	}
	
	public Double getInterest_rate() {
		return interest_rate;
	}
	
	public void setInterest_rate(Double interest_rate) {
		this.interest_rate = interest_rate;
	}
	
	public Double getTax() {
		return tax;
	}
	
	public void setTax(Double tax) {
		this.tax = tax;
	}
	
	public String getAuto_rollover_maturity() {
		return auto_rollover_maturity;
	}
	
	public void setAuto_rollover_maturity(String auto_rollover_maturity) {
		this.auto_rollover_maturity = auto_rollover_maturity;
	}
	
	public String getRollover_type() {
		return rollover_type;
	}
	
	public void setRollover_type(String rollover_type) {
		this.rollover_type = rollover_type;
	}
	
	public Integer getNo_of_rollovers() {
		return no_of_rollovers;
	}
	
	public void setNo_of_rollovers(Integer no_of_rollovers) {
		this.no_of_rollovers = no_of_rollovers;
	}
	
	public String getHomeno() {
		return homeno;
	}
	
	public void setHomeno(String homeno) {
		this.homeno = homeno;
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
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getD_addressline1() {
		return d_addressline1;
	}
	
	public void setD_addressline1(String d_addressline1) {
		this.d_addressline1 = d_addressline1;
	}
	
	public String getD_village() {
		return d_village;
	}
	
	public void setD_village(String d_village) {
		this.d_village = d_village;
	}
	
	public String getD_commune() {
		return d_commune;
	}
	
	public void setD_commune(String d_commune) {
		this.d_commune = d_commune;
	}
	
	public String getD_district() {
		return d_district;
	}
	
	public void setD_district(String d_district) {
		this.d_district = d_district;
	}
	
	public String getD_province() {
		return d_province;
	}
	
	public void setD_province(String d_province) {
		this.d_province = d_province;
	}
	
	public String getP_addressline1() {
		return p_addressline1;
	}
	
	public void setP_addressline1(String p_addressline1) {
		this.p_addressline1 = p_addressline1;
	}
	
	public String getP_village() {
		return p_village;
	}
	
	public void setP_village(String p_village) {
		this.p_village = p_village;
	}
	
	public String getP_commune() {
		return p_commune;
	}
	
	public void setP_commune(String p_commune) {
		this.p_commune = p_commune;
	}
	
	public String getP_district() {
		return p_district;
	}
	
	public void setP_district(String p_district) {
		this.p_district = p_district;
	}
	
	public String getP_province() {
		return p_province;
	}
	
	public void setP_province(String p_province) {
		this.p_province = p_province;
	}
	
	public String getCredit_officer_sv() {
		return credit_officer_sv;
	}
	
	public void setCredit_officer_sv(String credit_officer_sv) {
		this.credit_officer_sv = credit_officer_sv;
	}
	
	public String getCredit_officer_id() {
		return credit_officer_id;
	}
	
	public void setCredit_officer_id(String credit_officer_id) {
		this.credit_officer_id = credit_officer_id;
	}
	
	public String getCredit_officer_name() {
		return credit_officer_name;
	}
	
	public void setCredit_officer_name(String credit_officer_name) {
		this.credit_officer_name = credit_officer_name;
	}
	
	public String getCif_joint_holder_number1() {
		return cif_joint_holder_number1;
	}
	
	public void setCif_joint_holder_number1(String cif_joint_holder_number1) {
		this.cif_joint_holder_number1 = cif_joint_holder_number1;
	}
	
	public String getJoint_holder_name1() {
		return joint_holder_name1;
	}
	
	public void setJoint_holder_name1(String joint_holder_name1) {
		this.joint_holder_name1 = joint_holder_name1;
	}
	
	public String getCif_joint_holder_number2() {
		return cif_joint_holder_number2;
	}
	
	public void setCif_joint_holder_number2(String cif_joint_holder_number2) {
		this.cif_joint_holder_number2 = cif_joint_holder_number2;
	}
	
	public String getJoint_holder_name2() {
		return joint_holder_name2;
	}
	
	public void setJoint_holder_name2(String joint_holder_name2) {
		this.joint_holder_name2 = joint_holder_name2;
	}
	
	public String getCif_joint_holder_number3() {
		return cif_joint_holder_number3;
	}
	
	public void setCif_joint_holder_number3(String cif_joint_holder_number3) {
		this.cif_joint_holder_number3 = cif_joint_holder_number3;
	}
	
	public String getJoint_holder_name3() {
		return joint_holder_name3;
	}
	
	public void setJoint_holder_name3(String joint_holder_name3) {
		this.joint_holder_name3 = joint_holder_name3;
	}
	
	public String getCif_joint_holder_number4() {
		return cif_joint_holder_number4;
	}
	
	public void setCif_joint_holder_number4(String cif_joint_holder_number4) {
		this.cif_joint_holder_number4 = cif_joint_holder_number4;
	}
	
	public String getJoint_holder_name4() {
		return joint_holder_name4;
	}
	
	public void setJoint_holder_name4(String joint_holder_name4) {
		this.joint_holder_name4 = joint_holder_name4;
	}
	
	public String getCustomer_type() {
		return customer_type;
	}
	
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	
	public String getCustomer_category() {
		return customer_category;
	}
	
	public void setCustomer_category(String customer_category) {
		this.customer_category = customer_category;
	}
	
	public String getCust_cat_desc() {
		return cust_cat_desc;
	}
	
	public void setCust_cat_desc(String cust_cat_desc) {
		this.cust_cat_desc = cust_cat_desc;
	}
	
	public String getCust_classification() {
		return cust_classification;
	}
	
	public void setCust_classification(String cust_classification) {
		this.cust_classification = cust_classification;
	}
	
	public String getCust_class_desc() {
		return cust_class_desc;
	}
	
	public void setCust_class_desc(String cust_class_desc) {
		this.cust_class_desc = cust_class_desc;
	}
	
	public String getCustomer_prefix() {
		return customer_prefix;
	}
	
	public void setCustomer_prefix(String customer_prefix) {
		this.customer_prefix = customer_prefix;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	
	public String getResident_status() {
		return resident_status;
	}
	
	public void setResident_status(String resident_status) {
		this.resident_status = resident_status;
	}
	
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getMobile_number() {
		return mobile_number;
	}
	
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	
	public String getE_mail() {
		return e_mail;
	}
	
	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}
	
	public String getFax() {
		return fax;
	}
	
	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getNationality() {
		return nationality;
	}
	
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getNational_id() {
		return national_id;
	}
	
	public void setNational_id(String national_id) {
		this.national_id = national_id;
	}
	
	public Date getPpt_iss_date() {
		return ppt_iss_date;
	}
	
	public void setPpt_iss_date(Date ppt_iss_date) {
		this.ppt_iss_date = ppt_iss_date;
	}
	
	public Date getPpt_exp_date() {
		return ppt_exp_date;
	}
	
	public void setPpt_exp_date(Date ppt_exp_date) {
		this.ppt_exp_date = ppt_exp_date;
	}
	
	public String getPassport_no() {
		return passport_no;
	}
	
	public void setPassport_no(String passport_no) {
		this.passport_no = passport_no;
	}
	
	public String getIdentity_type1() {
		return identity_type1;
	}
	
	public void setIdentity_type1(String identity_type1) {
		this.identity_type1 = identity_type1;
	}
	
	public String getIdentity_number1() {
		return identity_number1;
	}
	
	public void setIdentity_number1(String identity_number1) {
		this.identity_number1 = identity_number1;
	}
	
	public String getIdentity_type2() {
		return identity_type2;
	}
	
	public void setIdentity_type2(String identity_type2) {
		this.identity_type2 = identity_type2;
	}
	
	public String getIdentity_number2() {
		return identity_number2;
	}
	
	public void setIdentity_number2(String identity_number2) {
		this.identity_number2 = identity_number2;
	}
	
	public String getPoverty_class() {
		return poverty_class;
	}
	
	public void setPoverty_class(String poverty_class) {
		this.poverty_class = poverty_class;
	}
	
	public String getMarital_status() {
		return marital_status;
	}
	
	public void setMarital_status(String marital_status) {
		this.marital_status = marital_status;
	}
	
	public String getEducational_status() {
		return educational_status;
	}
	
	public void setEducational_status(String educational_status) {
		this.educational_status = educational_status;
	}
	
	public Integer getDependent_children() {
		return dependent_children;
	}
	
	public void setDependent_children(Integer dependent_children) {
		this.dependent_children = dependent_children;
	}
	
	public Integer getDependent_others() {
		return dependent_others;
	}
	
	public void setDependent_others(Integer dependent_others) {
		this.dependent_others = dependent_others;
	}
	
	public String getCorporate_name() {
		return corporate_name;
	}
	
	public void setCorporate_name(String corporate_name) {
		this.corporate_name = corporate_name;
	}
	
	public Date getIncorp_date() {
		return incorp_date;
	}
	
	public void setIncorp_date(Date incorp_date) {
		this.incorp_date = incorp_date;
	}
	
	public Double getCapital() {
		return capital;
	}
	
	public void setCapital(Double capital) {
		this.capital = capital;
	}
	
	public Double getNetworth() {
		return networth;
	}
	
	public void setNetworth(Double networth) {
		this.networth = networth;
	}
	
	public String getBusiness_description() {
		return business_description;
	}
	
	public void setBusiness_description(String business_description) {
		this.business_description = business_description;
	}
	
	public String getDirector_name() {
		return director_name;
	}
	
	public void setDirector_name(String director_name) {
		this.director_name = director_name;
	}
	
	public String getD_telephone() {
		return d_telephone;
	}
	
	public void setD_telephone(String d_telephone) {
		this.d_telephone = d_telephone;
	}
	
	public String getD_mobile_number() {
		return d_mobile_number;
	}
	
	public void setD_mobile_number(String d_mobile_number) {
		this.d_mobile_number = d_mobile_number;
	}
	
	public String getD_e_mail() {
		return d_e_mail;
	}
	
	public void setD_e_mail(String d_e_mail) {
		this.d_e_mail = d_e_mail;
	}
	
	public String getAc_stat_dormant() {
		return ac_stat_dormant;
	}
	
	public void setAc_stat_dormant(String ac_stat_dormant) {
		this.ac_stat_dormant = ac_stat_dormant;
	}
	
	public String getAc_stat_frozen() {
		return ac_stat_frozen;
	}
	
	public void setAc_stat_frozen(String ac_stat_frozen) {
		this.ac_stat_frozen = ac_stat_frozen;
	}
	
	public Date getInt_start_date() {
		return int_start_date;
	}
	
	public void setInt_start_date(Date int_start_date) {
		this.int_start_date = int_start_date;
	}
	
	public Date getChg_start_date() {
		return chg_start_date;
	}
	
	public void setChg_start_date(Date chg_start_date) {
		this.chg_start_date = chg_start_date;
	}
	
	public Double getAccrual_int() {
		return accrual_int;
	}
	
	public void setAccrual_int(Double accrual_int) {
		this.accrual_int = accrual_int;
	}
	
	public Double getAccrual_tax() {
		return accrual_tax;
	}
	
	public void setAccrual_tax(Double accrual_tax) {
		this.accrual_tax = accrual_tax;
	}
	
	public Double getAcquired_int_amt() {
		return acquired_int_amt;
	}
	
	public void setAcquired_int_amt(Double acquired_int_amt) {
		this.acquired_int_amt = acquired_int_amt;
	}
	
	public Double getAcquired_tax_amt() {
		return acquired_tax_amt;
	}
	
	public void setAcquired_tax_amt(Double acquired_tax_amt) {
		this.acquired_tax_amt = acquired_tax_amt;
	}
	
	public String getSaving_acc() {
		return saving_acc;
	}
	
	public void setSaving_acc(String saving_acc) {
		this.saving_acc = saving_acc;
	}
	
	public String getDrawn_down_acct() {
		return drawn_down_acct;
	}
	
	public void setDrawn_down_acct(String drawn_down_acct) {
		this.drawn_down_acct = drawn_down_acct;
	}
	
	public String getDrawn_down_acct_name() {
		return drawn_down_acct_name;
	}
	
	public void setDrawn_down_acct_name(String drawn_down_acct_name) {
		this.drawn_down_acct_name = drawn_down_acct_name;
	}
	
	public Date getLast_liq_dt() {
		return last_liq_dt;
	}
	
	public void setLast_liq_dt(Date last_liq_dt) {
		this.last_liq_dt = last_liq_dt;
	}
	
	public Date getNext_liq_dt() {
		return next_liq_dt;
	}
	
	public void setNext_liq_dt(Date next_liq_dt) {
		this.next_liq_dt = next_liq_dt;
	}
	
	public Double getInt_am_maturity() {
		return int_am_maturity;
	}
	
	public void setInt_am_maturity(Double int_am_maturity) {
		this.int_am_maturity = int_am_maturity;
	}
	
	public Double getInt_liquidated_to_date() {
		return int_liquidated_to_date;
	}
	
	public void setInt_liquidated_to_date(Double int_liquidated_to_date) {
		this.int_liquidated_to_date = int_liquidated_to_date;
	}
	
	public Date getReport_date() {
		return report_date;
	}
	
	public void setReport_date(Date report_date) {
		this.report_date = report_date;
	}
	
}

