package kredit.web.main.mvc;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;

@SuppressWarnings("rawtypes")
public class MainComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void onCmd$mainComposer(Event event) {
		ForwardEvent eventx = (ForwardEvent) event;
		String data = (String) eventx.getOrigin().getData();

		JSONObject obj = (JSONObject) new JSONParser().parse(data);
		String cmd = (String) obj.get("cmd");
		
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("cmd", cmd);

		BindUtils.postGlobalCommand(null, null, "notifyCmd", args);
	}
}
