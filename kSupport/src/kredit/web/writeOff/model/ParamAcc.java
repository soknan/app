package kredit.web.writeOff.model;

import java.util.Date;

import kredit.web.core.util.model.CodeItem;

public class ParamAcc {

	String name;
	String account;
	String cif;
	Date stDate;
	Date enDate;
	CodeItem currency;
	CodeItem prodCat;
	CodeItem branch;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public Date getStDate() {
		return stDate;
	}

	public void setStDate(Date stDate) {
		this.stDate = stDate;
	}

	public Date getEnDate() {
		return enDate;
	}

	public void setEnDate(Date enDate) {
		this.enDate = enDate;
	}

	public CodeItem getCurrency() {
		if(currency == null)
		{
			currency = new CodeItem();
			currency.setId(0);
			currency.setCode("");
			currency.setDescription("All");
		}
		
		return currency;
	}

	public void setCurrency(CodeItem currency) {
		this.currency = currency;
	}

	public CodeItem getProdCat() {
		if(prodCat == null)
		{
			prodCat = new CodeItem();
			prodCat.setId(0);
			prodCat.setCode("");
			prodCat.setDescription("All");
		}
		
		return prodCat;
	}

	public void setProdCat(CodeItem prodCat) {
		this.prodCat = prodCat;
	}

	public CodeItem getBranch() {
		if(branch == null)
		{
			branch = new CodeItem();
			branch.setId(0);
			branch.setCode("");
			branch.setDescription("All");
		}
		
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}
}
