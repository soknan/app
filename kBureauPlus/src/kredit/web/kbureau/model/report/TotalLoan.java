/**
 * 
 */
package kredit.web.kbureau.model.report;

/**
 * @author vathenan
 *
 */
public class TotalLoan {
	String label;
	int totalLoan;
	float totalAmount;
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
	 * @return the totalLoan
	 */
	public int getTotalLoan() {
		return totalLoan;
	}
	/**
	 * @param totalLoan the totalLoan to set
	 */
	public void setTotalLoan(int totalLoan) {
		this.totalLoan = totalLoan;
	}
	/**
	 * @return the totalAmount
	 */
	public float getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
}
