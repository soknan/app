package kredit.web.writeOff.model;

import java.util.Date;

import kredit.web.core.util.model.CodeItem;

public class ParamLoan {
	String filter;
	Date stDate;
	Date enDate;
	Double stAmount;
	Double enAmount;
	CodeItem currency;
	CodeItem category;
	CodeItem product;
	CodeItem branch;
	CodeItem subBranch;
	CodeItem wCategory;
	
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
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

	public Double getStAmount() {
		return stAmount;
	}

	public void setStAmount(Double stAmount) {
		this.stAmount = stAmount;
	}

	public Double getEnAmount() {
		return enAmount;
	}

	public void setEnAmount(Double enAmount) {
		this.enAmount = enAmount;
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

	public CodeItem getProduct() {
		if(product == null)
		{
			product = new CodeItem();
			product.setId(0);
			product.setCode("");
			product.setDescription("All");
		}
		
		return product;
	}

	public void setProduct(CodeItem product) {
		this.product = product;
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

	public CodeItem getSubBranch() {
		if(subBranch == null)
		{
			subBranch = new CodeItem();
			subBranch.setId(0);
			subBranch.setCode("");
			subBranch.setDescription("All");
		}
		return subBranch;
	}

	public void setSubBranch(CodeItem subBranch) {
		this.subBranch = subBranch;
	}

	public CodeItem getCategory() {
		if(category == null)
		{
			category = new CodeItem();
			category.setId(0);
			category.setCode("");
			category.setDescription("All");
		}
		return category;
	}

	public void setCategory(CodeItem category) {
		this.category = category;
	}

	public CodeItem getwCategory() {
		if(wCategory == null)
		{
			wCategory = new CodeItem();
			wCategory.setId(0);
			wCategory.setCode("");
			wCategory.setDescription("All");
		}
		return wCategory;
	}

	public void setwCategory(CodeItem wCategory) {
		this.wCategory = wCategory;
	}
}
