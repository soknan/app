package kredit.flexcube.model;

public class CasaDetail {
	String accNo;
	String cif;
	String cusName;
	String accClass;
	String accType;
	String mop;
	String ccy;
	String bal;
	String photo;
	String signature;	
	
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getCif() {
		return cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	public String getAccClass() {
		return accClass;
	}
	public void setAccClass(String accClass) {
		this.accClass = accClass;
	}
	public String getAccType() {
		return accType;
	}
	public void setAccType(String accType) {
		this.accType = accType;
	}
	public String getMop() {
		return mop;
	}
	public void setMop(String mop) {
		this.mop = mop;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	
	@SuppressWarnings("static-access")
	public String getBal() {
		try {
		    if(!bal.equals("N/A")){			
			bal = bal.format("%,.2f", Double.valueOf(bal));
		}
		} catch (NumberFormatException ignored) {}
		
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getPhoto() {		
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = "data:image/png;base64,"+photo;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = "data:image/png;base64,"+signature;
	}	
	
}
