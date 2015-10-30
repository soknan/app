/**
 * 
 */
package kredit.web.util.incentive.utility;

import java.sql.Connection;


import org.apache.log4j.Logger;

/**
 * @author vathenan
 *
 */
public class MsSqlHelper {

	static Logger logger = Logger.getLogger(MsSqlHelper.class.getName());
	static final String JDBC_DRIVER_JTDS = "net.sourceforge.jtds.jdbc.Driver";
	static final String USER = "puthi";
	static final String PWD = "1l0v3g@m3";
	static final String SERVER = "localhost";
	static final String DB = "Incentive";
	static final String INSTANCE = "";
	static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://"+ SERVER +":1433/"+ DB +";instance=" + INSTANCE;
	//static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://"+ SERVER +":1433/"+ DB;
	
	private static SqlServerHelper sqlServerHelper;
	
	static
	{
		try{
			
			sqlServerHelper = new SqlServerHelper(JDBC_DRIVER_JTDS, CONNECTION_STRING, USER, PWD);
			
		}catch (Exception e) {
			logger.error("Error static constructor of DbHelper.", e);
		}
	}
	
	public static Connection getConnection(){
		return sqlServerHelper.getConnection();
	}
}
