package kredit.web.mfi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import kredit.web.core.model.Domain;

@Entity
@Table(name = "MFI_COBORROWER")
public class Coborrower extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length = 9)
	String borrowerCIF;

	@Column(length = 50)
	String relationType;

	@Column(length = 50)
	String nameEN = "";

	@Column(length = 50)
	String nameKH = "";

	@Column(length = 1)
	String sex = "";

	Date dob;

	/**
	 * @return the relationType
	 */
	public String getRelationType() {
		return relationType;
	}

	/**
	 * @param relationType
	 *            the relationType to set
	 */
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	/**
	 * @return the nameEN
	 */
	public String getNameEN() {
		return nameEN;
	}

	/**
	 * @param nameEN
	 *            the nameEN to set
	 */
	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	/**
	 * @return the nameKH
	 */
	public String getNameKH() {
		return nameKH;
	}

	/**
	 * @param nameKH
	 *            the nameKH to set
	 */
	public void setNameKH(String nameKH) {
		this.nameKH = nameKH;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}

	/**
	 * @param dob
	 *            the dob to set
	 */
	public void setDob(Date dob) {
		this.dob = dob;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the borrowerCIF
	 */
	public String getBorrowerCIF() {
		return borrowerCIF;
	}

	/**
	 * @param borrowerCIF
	 *            the borrowerCIF to set
	 */
	public void setBorrowerCIF(String borrowerCIF) {
		this.borrowerCIF = borrowerCIF;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID:");
		sb.append(this.getId());
		sb.append("|");
		sb.append("Type:");
		sb.append(this.getRelationType());
		sb.append("|");
		sb.append("NameEN:");
		sb.append(this.getNameEN());
		sb.append("|");
		sb.append("NameKH:");
		sb.append(this.getNameKH());
		sb.append("|");
		sb.append("DOB");
		sb.append(this.getDob());

		return sb.toString();
	}

}
