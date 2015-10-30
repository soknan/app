/**
 * 
 */
package kredit.web.core.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.tempuri.IUtilProxy;
import org.zkoss.io.FileReader;

/**
 * @author Sovathena Neth
 *
 */
public class Common {
	
	private static Logger logger = Logger.getLogger(Common.class.getName());
	
	public static final String TOKEN_STRING = "@k2012!Kh#";
	public static final String REPORT_URL = "http://192.168.100.2";
	public static final boolean FOR_UAT = true;
	public static final boolean FOR_PRODUCTION = false;
	
	public static IUtilProxy kUtil = new IUtilProxy();
	
	
	/**
	 * Encrypt string
	 * @param txt
	 * @param salt
	 * @return
	 */
	public static String encrypt(String txt, String salt){
		String result = "";
		try {
			result = kUtil.encryptOrDecrypt(TOKEN_STRING, txt, salt, false);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Decrypt string
	 * @param txt
	 * @param salt
	 * @return
	 */
	public static String decrypt(String txt, String salt){
		String result = "";
		try {
			result = kUtil.encryptOrDecrypt(TOKEN_STRING, txt, salt, true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/********
	 * Return date with format
	 * @param date
	 * @return
	 */
	public static String FormatDateTime(Date date)
    {
		if(date == null) return "";
		String strResult = "";
	    Date currentDate =  new Date();//new Date();

	    long diffInSeconds = (currentDate.getTime() - date.getTime()) / 1000;
	    
	    long intTotalMinutes = diffInSeconds/60;
	    
	    if (intTotalMinutes >= 0 && intTotalMinutes < 60)
        {
            intTotalMinutes = intTotalMinutes <= 0 ? 1 : intTotalMinutes;
            strResult = intTotalMinutes > 1 ? intTotalMinutes + " minutes ago" : intTotalMinutes + " minute ago";
        }
        else if (intTotalMinutes >= 60 && intTotalMinutes < 760)
        {
            long hour = intTotalMinutes / 60;
            strResult = hour + ( hour>1?" hours ago": " hour ago");
        }
        else if (intTotalMinutes >= 760 && intTotalMinutes < 10080)
        {
        	@SuppressWarnings("deprecation")
			int weekDay = currentDate.getDay() - date.getDay();
            if (weekDay == 1 || weekDay == -6){
            	SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aaa");
            	strResult = "Yesterday at " + formatter.format(date);
            }else{
            	SimpleDateFormat formatter = new SimpleDateFormat("EEEE 'at' hh:mm aaa");
            	strResult = formatter.format(date);
            }
        }
        else
        {
        	SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy 'at' h:mm aaa");
            strResult = formatter.format(date);
        }
	    
        return strResult;
    }
	
	public static String readFileContent(String fileName){
		String result = "";
		try{
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName, "UTF8"));
			String temp = "";
			while((temp = reader.readLine()) != null)
				 result = (new StringBuilder(String.valueOf(result))).append(temp).toString();
			reader.close();
		}catch (FileNotFoundException e) {
			logger.error("Error while reading file content from " + fileName + ".Caused: " + e.getMessage());
		}catch (Exception e) {
			logger.error("Error while reading file content from " + fileName + ".Caused: " + e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * Return true if email is validate
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		return email.matches(".+@.+\\.[a-z]+");
	}
	
	/**
	 * Return Sql Date String
	 * @param date
	 * @return
	 */
	public static String getSqlDateString(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}
	
	
	/**
	 * Return date with specified format
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateFormat(Date date, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	
	public static String getCbcReportIdFromUrl (String s){
	    Pattern pattern = Pattern.compile("qid=\\d+");
	    Matcher matcher = pattern.matcher(s);
	    if (matcher.find()){
	       return s.substring(matcher.start(), matcher.end()).replace("qid=", "");
	    } 
	    return "0";
	}
}
