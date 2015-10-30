package kredit.web.util.incentive.utility;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

public class MsSqlHelperAuth {
	static Logger logger = Logger.getLogger(MsSqlHelperAuth.class);
	
	static final String url = "jdbc:jtds:sqlserver://192.168.1.23;DatabaseName=Incentive;integratedSecurity=true;domain=kredit.com;useNTLMv2=true;";
	//static final String url = "jdbc:jtds:sqlserver://localhost;DatabaseName=Incentive;integratedSecurity=true;";
	static final String driver = "net.sourceforge.jtds.jdbc.Driver";

	public static Connection getConnection()
	{
		Connection con = null;
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, "", "");
			
		} catch (Exception e) {
			logger.error("Error getting Connection form MsSqlHelperAuth.", e);
		}
		return con;
	}
}
