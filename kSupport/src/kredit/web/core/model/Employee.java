package kredit.web.core.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.security.model.facade.SecurityFacade;

@Entity
@Table(name = "SYS_EMPLOYEE")
public class Employee {
	@Id
	Integer id;
	
	Integer emp_id;
	
	String lname_en;
	
	String lname_kh;
	
	String fname_en;
	
	String fname_kh;
	
	String gender;
	
	String phone1;
	
	String phone2;
	
	Date workday1;
	
	Date birth_date;
	
	String emergency_phone;
	
	String email;
	
	String position;
	
	Integer branch;
	
	String status;
	
	@Transient
	String fullNameEn;
	
	@Transient
	String homeBranch;
	
	@Transient
	String fEmail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(Integer emp_id) {
		this.emp_id = emp_id;
	}

	public String getLname_en() {
		return lname_en;
	}

	public void setLname_en(String lname_en) {
		this.lname_en = lname_en;
	}

	public String getLname_kh() {
		return lname_kh;
	}

	public void setLname_kh(String lname_kh) {
		this.lname_kh = lname_kh;
	}

	public String getFname_en() {
		return fname_en;
	}

	public void setFname_en(String fname_en) {
		this.fname_en = fname_en;
	}

	public String getFname_kh() {
		return fname_kh;
	}

	public void setFname_kh(String fname_kh) {
		this.fname_kh = fname_kh;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public Date getWorkday1() {
		return workday1;
	}

	public void setWorkday1(Date workday1) {
		this.workday1 = workday1;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}

	public String getEmergency_phone() {
		return emergency_phone;
	}

	public void setEmergency_phone(String emergency_phone) {
		this.emergency_phone = emergency_phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getBranch() {
		return branch;
	}

	public void setBranch(Integer branch) {
		this.branch = branch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFullNameEn() {
		if(fullNameEn == null)
		{
			if(lname_en == null)
				fullNameEn = "";
			else
				fullNameEn = lname_en + " " + fname_en;
		}
		
		return fullNameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

	public String getHomeBranch() {
		if(homeBranch == null)
		{
			homeBranch = SecurityFacade.getFullBranch(branch);
		}
		return homeBranch;
	}

	public void setHomeBranch(String homeBranch) {
		this.homeBranch = homeBranch;
	}

	public String getfEmail() {
		if(fEmail == null)
		{
			fEmail = "";
			if(!(email == null))
			{
				fEmail = email + "@kredit.com.kh";
			}
		}
		return fEmail;
	}

	public void setfEmail(String fEmail) {
		this.fEmail = fEmail;
	}
	
}