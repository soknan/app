package kredit.web.cbc.model;

import kredit.web.core.util.model.CodeItem;

public class ParamCoGuaModel {
	String filter="";
	CodeItem type;
	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public CodeItem getType() {
		if (type != null) {
			return type;
		}
		type = new CodeItem();
		type.setCode("%");
		type.setDescription("All");
		return type;
	}

	public void setType(CodeItem type) {
		this.type = type;
	}
	
	
}
