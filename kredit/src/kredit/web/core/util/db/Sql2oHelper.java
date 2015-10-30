package kredit.web.core.util.db;

import org.apache.log4j.Logger;
import org.sql2o.Sql2o;

public class Sql2oHelper
{
  static Logger logger = Logger.getLogger(Sql2oHelper.class);
  
  static
  {
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    }
    catch (ClassNotFoundException e)
    {
      logger.error("Error static constructor of Sql2oHelper.", e);
    }
  }
  
  public static Sql2o sql2o = new Sql2o("jdbc:oracle:thin:@192.168.2.12:1521/KRDPDT1", "K", 
    "Adm1nS$K");
}
