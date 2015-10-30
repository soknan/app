package kredit.web.main.model.facade;

import java.util.HashMap;
import java.util.Map;

import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;

import org.apache.log4j.Logger;

public class MainFacade {
	static Logger logger = Logger.getLogger(MainFacade.class);
	
	public static String getWinPath(String winId){
		winId = winId.toLowerCase();
		Map<String, CodeItem> functionsMap = new HashMap<String, CodeItem>(); 
		functionsMap = UserCredentialManager.getIntance().getFunctionsMap();
		if(functionsMap.containsKey(winId))
		{
			return functionsMap.get(winId).getDescription();
		}
		
		return "";
	}
}
