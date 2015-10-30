/**
 * 
 */
package kredit.web.core.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.log.model.LoggerSession;
import kredit.web.core.util.log.model.LoggerSessionLog;
import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.ValidityHelper;
import kredit.web.security.model.facade.SecurityFacade;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.k.model.User;
import org.zkoss.io.FileReader;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import com.avaje.ebean.Ebean;

/**
 * @author Sovathena Neth
 * 
 */
public class Common {
	private static Logger logger = Logger.getLogger(Common.class.getName());

	public static String SYS_LOV_STATUS = "STATUS";
	public static String SYS_LOV_BRANCH = "BRANCH";
	public static String SYS_LOV_FUNCTION = "FUNCTION";
	public static String SYS_LOV_VALIDITY = "VALIDITY";
	public static String SYS_LOV_RIGHT = "RIGHT";
	public static String SYS_LOV_BOOL = "BOOL";
	public static String PRINCIPAL = "PRINCIPAL";	//PRINCIPAL FOR PRINCIPAL
	public static String INTEREST = "MAIN_INT"; 	//MAIN_INT  FOR INTEREST
	public static String PEN_PRN = "PENAL_PRN";		//PENAL_PRN FOR PENALTY PRINCIPAL
	public static String PEN_INT = "PENAL_INT";	//PENAL_INT FOR PENALTY INTEREST
	public static String CON_INT = "CONT_INT";	//CONT_INT  FOR CONTINUE INTEREST
	
	public static String TYPE_WOFF_CATEGORY = "WOF_CATEGORY"; 
	
	private static byte[] key = { 0x71, 0x68, 0x70, 0x73, 0x49, 0x73, 0x41,
			0x53, 0x65, 0x64, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79 };
	private static BufferedReader reader;

	private static Properties biWsConfig;
	public static final String BIP_URL = "BIP_URL";
	public static final String BIP_SERVICE_CATALOG = "CatalogService";
	public static final String BIP_SERVICE_SECURITY = "SecurityService";
	public static final String BIP_SERVICE_REPORT = "ReportService";

	/********
	 * Return date with format
	 * 
	 * @param date
	 * @return
	 */
	public static String FormatDateTime(Date date) {
		if (date == null)
			return "";
		String strResult = "";
		Date currentDate = new Date();// new Date();

		long diffInSeconds = (currentDate.getTime() - date.getTime()) / 1000;

		long intTotalMinutes = diffInSeconds / 60;

		if (intTotalMinutes >= 0 && intTotalMinutes < 60) {
			intTotalMinutes = intTotalMinutes <= 0 ? 1 : intTotalMinutes;
			strResult = intTotalMinutes > 1 ? intTotalMinutes + " minutes ago"
					: intTotalMinutes + " minute ago";
		} else if (intTotalMinutes >= 60 && intTotalMinutes < 760) {
			long hour = intTotalMinutes / 60;
			strResult = hour + (hour > 1 ? " hours ago" : " hour ago");
		} else if (intTotalMinutes >= 760 && intTotalMinutes < 10080) {
			@SuppressWarnings("deprecation")
			int weekDay = currentDate.getDay() - date.getDay();
			if (weekDay == 1 || weekDay == -6) {
				SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aaa");
				strResult = "Yesterday at " + formatter.format(date);
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat(
						"EEEE 'at' hh:mm aaa");
				strResult = formatter.format(date);
			}
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"MMM d, yyyy 'at' h:mm aaa");
			strResult = formatter.format(date);
		}

		return strResult;
	}

	public static String readFileContent(String fileName) {
		String result = "";
		try {

			reader = new BufferedReader(new FileReader(fileName, "UTF8"));
			String temp = "";
			while ((temp = reader.readLine()) != null)
				result = (new StringBuilder(String.valueOf(result))).append(
						temp).toString();
		} catch (FileNotFoundException e) {
			logger.error("Error while reading file content from " + fileName
					+ ".Caused: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error while reading file content from " + fileName
					+ ".Caused: " + e.getMessage());
		}

		return result;
	}

	/**
	 * Return true if email is validate
	 * 
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		return email.matches(".+@.+\\.[a-z]+");
	}

	/**
	 * Return Sql Date String
	 * 
	 * @param date
	 * @return
	 */
	public static String getSqlDateString(Date date) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	/**
	 * Return date with specified format
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateFormat(Date date, String format) {
		if (date == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static boolean isKhmerUnicode(char c) {
		return c >= '\u1780' && c <= '\u17FF';
	}

	public static void notifyShowMsg(Event event) {
		ForwardEvent eventx = (ForwardEvent) event;
		String data = (String) eventx.getOrigin().getData();
		JSONObject obj = (JSONObject) new JSONParser().parse(data);
		String msg = obj.get("msg").toString();
		String type = obj.get("type").toString();
		Clients.showNotification(msg, type, null, "middle_center", 2500);
	}

	public static String encrypt(String strToEncrypt) {
		try {

			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			String encryptedString = Base64.encodeBase64String(cipher
					.doFinal(strToEncrypt.getBytes()));

			return encryptedString.replace("+", "-");
		} catch (Exception e) {
			logger.error("Error while encrypting", e);
		}
		return null;

	}

	public static String decrypt(String strToDecrypt) {
		try {
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			final SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			final String decryptedString = new String(cipher.doFinal(Base64
					.decodeBase64(strToDecrypt)));
			return decryptedString;
		} catch (Exception e) {
			logger.error("Error while decrypting", e);

		}
		return null;
	}

	public static String getAppAddr(Execution ex) {
		String appAddr = "http://" + ex.getServerName();
		if (ex.getServerPort() != 80) {
			appAddr = appAddr + ":" + ex.getServerPort();
		}
		appAddr = appAddr + ex.getContextPath();
		return appAddr;
	}

	public static String getUserIpAddr(Execution ex) {
		String ip = ((HttpServletRequest) ex.getNativeRequest())
				.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = ex.getRemoteAddr();
		}
		return ip;
	}

	public static String getKhmerDate(Date value) {
		int intDay = Integer.parseInt(getDateFormat(value, "dd"));
		int intMon = Integer.parseInt(getDateFormat(value, "MM"));
		int intYear = Integer.parseInt(getDateFormat(value, "yyyy"));

		String strMon = null;
		switch (intMon) {
		case 1:
			strMon = "មករា";
			break;
		case 2:
			strMon = "កុម្ភះ";
			break;
		case 3:
			strMon = "មីនា";
			break;
		case 4:
			strMon = "មេសា";
			break;
		case 5:
			strMon = "ឧសភា";
			break;
		case 6:
			strMon = "មិថុនា";
			break;
		case 7:
			strMon = "កក្កដា";
			break;
		case 8:
			strMon = "សីហា";
			break;
		case 9:
			strMon = "កញ្ញា";
			break;
		case 10:
			strMon = "តុលា";
			break;
		case 11:
			strMon = "វិច្ឆិកា";
			break;
		case 12:
			strMon = "ធ្នូ";
			break;
		}
		return "ថ្ងៃទី " + intDay + " ខែ " + strMon + " ឆ្នាំ " + intYear;

	}

	// region Add LoggerSession

	/*****
	 * Add SessionLog to Session --> Just store in memory, not yet save to
	 * database. Need to call method writeSessionLogToDB to save log into
	 * database
	 * 
	 * @param module
	 * @param message
	 */
	public static void addSessionLog(String module, String message, Date logOn) {
		UserCredentialManager mgmt = UserCredentialManager.getIntance();
		User loginUsr = mgmt.getLoginUsr();
		LoggerSession ses = mgmt.getLoggerSession();
		LoggerSessionLog log = new LoggerSessionLog();
		log.setLogOn(logOn);
		log.setBrCd(loginUsr.getHomeBranch());
		log.setModule(module);
		log.setMessage(message);
		ses.getSessionLogs().add(log);
	}

	/***********
	 * Write SessionLog to Database --> Commit
	 */
	public static void writeSessionLogToDB() {
		Ebean.save(UserCredentialManager.getIntance().getLoggerSession());
	}

	public static void addSessionLogCommit(String module, String message,
			Date logOn) {
		addSessionLog(module, message, logOn);
		writeSessionLogToDB();
	}

	public static StringBuilder createLogStringBuilder() {
		StringBuilder logStr = new StringBuilder();
		User loginUsr = UserCredentialManager.getIntance().getLoginUsr();
		logStr.append(loginUsr.getUsername()).append(" [")
				.append(loginUsr.getFullName()).append("]");
		return logStr;
	}

	public static String arrayQuery(String str) {
		String result = "";
		char[] characters = str.toCharArray();

		for (int i = 0; i < characters.length; i++) {
			result += "'" + characters[i] + "'";
			if (i != characters.length - 1)
				result += ",";
		}

		return result;
	}

	public static String listToString(ListModelList<CodeItem> lst) {
		String result = "";

		for (int i = 0; i < lst.size(); i++) {
			result += lst.get(i).getCode();
		}

		return result;
	}

	public static String deleteCharacter(String str, String d) {
		String result = "";
		StringBuilder tmp = new StringBuilder(str);
		tmp.deleteCharAt(str.indexOf(d));
		result = tmp.toString();
		return result;
	}

	// region properties
	public static Properties initBIWebServiceConfig() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			String filename = "xmlp_ws.properties";

			input = Common.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return prop;
			}

			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	// endregion properties

	/**
	 * @return the biWsConfig
	 */
	public static Properties getBiWsConfig() {
		if (biWsConfig == null)
			biWsConfig = initBIWebServiceConfig();
		return biWsConfig;
	}

	public static String addLikeExpression(String value) {
		String result = "%" + value + "%";
		return result;
	}

	public static Privilege getPrivilege(String module) {
		Privilege privilege = new Privilege();
		privilege.setModule(module);

		Map<String, CodeItem> functionsMap = UserCredentialManager.getIntance()
				.getFunctionsMap();

		if (functionsMap.size() == 0)
			return privilege;

		privilege.setRight(functionsMap.get(module).getCode());

		return privilege;
	}

	public static String arrayQuery(Object[] str) {
		String result = "";

		for (int i = 0; i < str.length; i++) {
			result += "'" + str[i].toString() + "'";
			if (i != str.length - 1)
				result += ",";
		}

		return result;
	}

	public static ValidityHelper varifyValidity() {
		ValidityHelper help = new ValidityHelper();
		Date d = new Date();
		kredit.web.security.model.User user = SecurityFacade
				.getUser(UserCredentialManager.getIntance().getLoginUsr()
						.getId());
		if (user.getUserValidities().size() != 0) {
			for (int i = 0; i < user.getUserValidities().size(); i++) {
				if (d.after(user.getUserValidities().get(i).getStart_on())
						&& d.before(user.getUserValidities().get(i).getEnd_on())) {
					help.setExist(true);
					help.setUser_as(user.getUserValidities().get(i)
							.getUser_id_as());
					help.setHomeBranch(user.getUserValidities().get(i)
							.getBranch_code());
					help.setType(user.getUserValidities().get(i).getTypeC()
							.getDescription());
					help.setFrom(user.getUserValidities().get(i).getStart_on());
					help.setTo(user.getUserValidities().get(i).getEnd_on());
				}
			}
		}
		return help;
	}
}
