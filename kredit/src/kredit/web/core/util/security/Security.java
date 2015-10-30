package kredit.web.core.util.security;

import org.apache.log4j.Logger;
import org.fc.util.PasswordDigest;
import org.fc.util.StringUtils;

public class Security
{
  static Logger logger = Logger.getLogger(Security.class);
  public static int HASH_ITERATION = 10000;
  
  public static boolean comparePwd(String rawPwd, String dbPwd)
  {
    String[] dbPwdArray = dbPwd.split("!", -1);
    if (dbPwdArray.length < 4) {
      return false;
    }
    int iteration = Integer.parseInt(dbPwdArray[1]);
    
    String databasePwd = dbPwdArray[2];
    String salt = dbPwdArray[3];
    
    byte[] decodedSalt = StringUtils.hexToByte(salt);
    String double_hash = null;
    try
    {
      byte[] actualDigest = PasswordDigest.getDigest(rawPwd.getBytes(), 
        decodedSalt, iteration);
      double_hash = StringUtils.byteToHex(actualDigest);
    }
    catch (Exception e)
    {
      logger.error("Error while comparing pwd", e);
    }
    return double_hash.equals(databasePwd);
  }
  
  public static String encPwd(String rawPwd)
    throws Exception
  {
    String salt = StringUtils.byteToHex(PasswordDigest.generateSalt());
    byte[] saltBytes = StringUtils.hexToByte(salt);
    String newpwd = StringUtils.byteToHex(PasswordDigest.getDigest(rawPwd.getBytes(), saltBytes, HASH_ITERATION));
    
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("SHA-512!").append(HASH_ITERATION).append("!").append(newpwd).append("!").append(salt);
    return strBuilder.toString();
  }
}
