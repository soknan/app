package kredit.web.util.monthly.utility;

import org.apache.log4j.Logger;
import org.sql2o.Sql2o;

public class Sql2oHelperEOM {
	static Logger logger = Logger.getLogger(Sql2oHelperEOM.class);
	private static String environment;
	private static String username;
	private static String password;
	
	  static
	  {
	    try
	    {
	      Class.forName("oracle.jdbc.driver.OracleDriver");
	    }
	    catch (ClassNotFoundException e)
	    {
	      logger.error("Error static constructor of Sql2oHelperEOM.", e);
	    }
	  }
	  
	  public Sql2oHelperEOM(String environment, String username, String password) {
		  Sql2oHelperEOM.environment = environment;
		  Sql2oHelperEOM.username = username;
		  Sql2oHelperEOM.password = password;
	  }
	  
	  public Sql2o getConnection() {
		  Sql2o sql2o = new Sql2o("jdbc:oracle:thin:@192.168.2.12:1521:" + environment, username, password);
		  return sql2o;
	  }
}
