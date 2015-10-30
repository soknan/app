package kredit.web.core.util.db;

import java.sql.Connection;
import org.apache.log4j.Logger;

public class OracleHelper
{
  static Logger logger = Logger.getLogger(OracleHelper.class.getName());
  static final String JDBC_DRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
  static final String USER = "K";
  static final String PWD = "Adm1nS$K";
  static final String SERVER = "192.168.2.12";
  static final String SID = "KRDPDT1";
  static final String PORT = "1521";
  static final String CONNECTION_STRING = "jdbc:oracle:thin:@192.168.2.12:1521/KRDPDT1";
  private static DbServerHelper DbHelper;
  
  static
  {
    try
    {
      DbHelper = new DbServerHelper("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.2.12:1521/KRDPDT1", "K", "Adm1nS$K");
    }
    catch (Exception e)
    {
      logger.error("Error static constructor of DbHelper.", e);
    }
  }
  
  public static Connection getConnection()
  {
    return DbHelper.getConnection();
  }
}
