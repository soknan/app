/**
 * 
 */
package kredit.web.kbureau.viewmodel.admin;

import kredit.web.core.util.Common;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

/**
 * @author vathenan
 * 
 */
public class UtilVM {
	String txtEnc = "";
	String txtDec = "";
	String saltEnc = "";
	String saltDec = "";
	String resultEnc;
	String resultDec;

	/**
	 * @return the txtEnc
	 */
	public String getTxtEnc() {

		return txtEnc;
	}

	/**
	 * @param txtEnc
	 *            the txtEnc to set
	 */
	public void setTxtEnc(String txt) {
		this.txtEnc = txt;
	}

	@Command
	@NotifyChange("resultEnc")
	public void encrypt() {
		resultEnc = Common.encrypt(txtEnc.trim(), saltEnc.trim());
	}

	@Command
	@NotifyChange("resultDec")
	public void decrypt() {
		resultDec = Common.decrypt(txtDec.trim(), saltDec.trim());
	}

	/**
	 * @return the resultEnc
	 */
	public String getResultEnc() {
		return resultEnc;
	}

	/**
	 * @param resultEnc
	 *            the resultEnc to set
	 */
	public void setResultEnc(String result) {
		this.resultEnc = result;
	}

	/**
	 * @return the resultDec
	 */
	public String getResultDec() {
		return resultDec;
	}

	/**
	 * @param resultDec
	 *            the resultDec to set
	 */
	public void setResultDec(String resultDec) {
		this.resultDec = resultDec;
	}

	/**
	 * @return the txtDec
	 */
	public String getTxtDec() {
		return txtDec;
	}

	/**
	 * @param txtDec
	 *            the txtDec to set
	 */
	public void setTxtDec(String txtDec) {
		this.txtDec = txtDec;
	}

	/**
	 * @return the saltEnc
	 */
	public String getSaltEnc() {
		return saltEnc;
	}

	/**
	 * @param saltEnc
	 *            the saltEnc to set
	 */
	public void setSaltEnc(String saltEnc) {
		this.saltEnc = saltEnc;
	}

	/**
	 * @return the saltDec
	 */
	public String getSaltDec() {
		return saltDec;
	}

	/**
	 * @param saltDec
	 *            the saltDec to set
	 */
	public void setSaltDec(String saltDec) {
		this.saltDec = saltDec;
	}

}
