package kredit.flexcube.model;

public class JoinHolder {
	String joinHolder;
	String jPhoto;
	String jSignature;
	String jCode;
	public String getJoinHolder() {
		return joinHolder;
	}
	public void setJoinHolder(String joinHolder) {
		this.joinHolder = joinHolder;
	}
	public String getjPhoto() {
		return jPhoto;
	}
	public void setjPhoto(String jPhoto) {
		this.jPhoto = "data:image/png;base64,"+jPhoto;
	}
	public String getjSignature() {
		return jSignature;
	}
	public void setjSignature(String jSignature) {
		this.jSignature = "data:image/png;base64,"+jSignature;
	}
	public String getjCode() {
		return jCode;
	}
	public void setjCode(String jCode) {
		this.jCode = jCode;
	}
	
	
}
