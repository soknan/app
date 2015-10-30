package kredit.web.kbureau.model.report;

public class Decision {
	String label;
	int totalPending;
	int totalApproved;
	int totalRejected;
	int totalCanceled;
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
	 * @return the totalPending
	 */
	public int getTotalPending() {
		return totalPending;
	}
	/**
	 * @param totalPending the totalPending to set
	 */
	public void setTotalPending(int totalPending) {
		this.totalPending = totalPending;
	}
	/**
	 * @return the totalApproved
	 */
	public int getTotalApproved() {
		return totalApproved;
	}
	/**
	 * @param totalApproved the totalApproved to set
	 */
	public void setTotalApproved(int totalApproved) {
		this.totalApproved = totalApproved;
	}
	/**
	 * @return the totalRejected
	 */
	public int getTotalRejected() {
		return totalRejected;
	}
	/**
	 * @param totalRejected the totalRejected to set
	 */
	public void setTotalRejected(int totalRejected) {
		this.totalRejected = totalRejected;
	}
	/**
	 * @return the totalCanceled
	 */
	public int getTotalCanceled() {
		return totalCanceled;
	}
	/**
	 * @param totalCanceled the totalCanceled to set
	 */
	public void setTotalCanceled(int totalCanceled) {
		this.totalCanceled = totalCanceled;
	}
}
