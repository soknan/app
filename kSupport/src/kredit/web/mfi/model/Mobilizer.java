package kredit.web.mfi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import kredit.web.core.model.Domain;
import kredit.web.core.util.model.CodeItem;

@Entity
@Table(name = "MFI_MOBILIZER")
public class Mobilizer extends Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(length = 50)
	String nameEN = "";

	@Column(length = 50)
	String nameKH = "";
	
	@Column(length = 1)
	String recordStat="";
	
	@Transient
	String recordStatDes="";

	@Column(length = 1)
	String sex = "";

	@Column(length = 9)
	String cif = "";

	@Column(length = 1)
	String idType = "";

	@Transient
	CodeItem idTypeItem;

	@Column(length = 30)
	String idNo = "";

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
	 * @return the cif
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * @param cif
	 *            the cif to set
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * @return the idType
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 *            the idType to set
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return the idNo
	 */
	public String getIdNo() {
		return idNo;
	}

	/**
	 * @param idNo
	 *            the idNo to set
	 */
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the idTypeItem
	 */
	public CodeItem getIdTypeItem() {
		if (idTypeItem != null)
			return idTypeItem;

		idTypeItem = new CodeItem();

		if (idType != null) {

			idTypeItem.setCode(idType);
			if (idType.equals("B")) {

				idTypeItem.setDescription("BIRTH CERTIFICATE");
			} else if (idType.equals("F")) {
				idTypeItem.setDescription("FAMILY BOOK");
			} else if (idType.equals("R")) {
				idTypeItem.setDescription("RESIDENT BOOK");
			} else if (idType.equals("G")) {
				idTypeItem.setDescription("GOVT ISSUED ID");
			} else if (idType.equals("V")) {
				idTypeItem.setDescription("VOTERS REG. CARD");
			} else if (idType.equals("C")) {
				idTypeItem.setDescription("CERTIFIED ID DOCUMENT");
			} else if (idType.equals("N")){
				idTypeItem.setDescription("NATIONAL ID");
			}
		}
		return idTypeItem;
	}

	public String getIdTypeDesc() {
		return getIdTypeItem().getDescription();
	}

	/**
	 * @param idTypeItem
	 *            the idTypeItem to set
	 */
	public void setIdTypeItem(CodeItem idTypeItem) {
		this.idTypeItem = idTypeItem;
	}

	public String getRecordStat() {
		return recordStat;
	}

	public void setRecordStat(String recordStat) {
		this.recordStat = recordStat;
	}
	
	public String getRecordStatDes() {
		if(recordStat==null) return recordStatDes;
		switch (recordStat) {
		case "O":
			recordStatDes = "Open";
			break;
		case "C":
			recordStatDes = "Close";
			break;		
		}
		return recordStatDes;
	}

	public void setRecordStatDes(String recordStatDes) {
		this.recordStatDes = recordStatDes;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ID:");
		sb.append(this.getId());
		sb.append("|");
		sb.append("BR:");
		sb.append(this.getBrCd());
		sb.append("|");
		sb.append(this.getNameEN());
		sb.append("|");
		sb.append(this.getNameKH());
		sb.append("|");
		sb.append("IDType:");
		sb.append(this.getIdType());
		sb.append("|");
		sb.append("IDNo:");
		sb.append(this.getIdNo());

		return sb.toString();
	}
}
