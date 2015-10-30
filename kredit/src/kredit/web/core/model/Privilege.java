package kredit.web.core.model;

public class Privilege {

	String module;
	String right = "";

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	public Boolean getCanView() {
		return right.contains("V");
	}

	public Boolean getCanUpdate() {
		return right.contains("U");
	}

	public Boolean getCanDelete() {
		return right.contains("D");
	}

	public Boolean getCanNew() {
		return right.contains("N");
	}

	public Boolean getCanCopy() {
		return right.contains("C");
	}
}
