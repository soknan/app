package kredit.web.core.util.zk;

import java.util.HashMap;

import java.util.Map;

import org.zkoss.bind.AnnotateBinder;
import org.zkoss.bind.Form;
import org.zkoss.zk.ui.Component;

public class ZkFormBinder extends AnnotateBinder {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6535823320662957857L;
	private static final Map<Object, Form> _allForms = new HashMap<Object, Form>();

	public Form getForm(Component comp, String id) {
		Form form = super.getForm(comp, id);
		if (form != null) {
			_allForms.put(id, form);
		}
		return form;
	}

	public static Form getFormByFormId(String id) {
		if (_allForms.containsKey(id))
			return _allForms.get(id);
		return null;
	}
}