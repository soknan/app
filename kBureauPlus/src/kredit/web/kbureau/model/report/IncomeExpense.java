/**
 * 
 */
package kredit.web.kbureau.model.report;

/**
 * @author vathenan
 *
 */
public class IncomeExpense {
	String label;
	double income;
	double expense;
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the income
	 */
	public double getIncome() {
		return income;
	}
	/**
	 * @param income the income to set
	 */
	public void setIncome(double income) {
		this.income = income;
	}
	/**
	 * @return the expense
	 */
	public double getExpense() {
		return expense;
	}
	/**
	 * @param expense the expense to set
	 */
	public void setExpense(double expense) {
		this.expense = expense;
	}
}
