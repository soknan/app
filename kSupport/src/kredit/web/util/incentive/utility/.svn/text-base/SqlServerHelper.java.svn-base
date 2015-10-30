package kredit.web.util.incentive.utility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;


public class SqlServerHelper {

	private String _jdbcDriver;
	private String _connectionString;
	private String _username;
	private String _password;

	/**
	 * @return the _connectionString
	 */
	public String get_connectionString() {
		return _connectionString;
	}

	/**
	 * @param _connectionString
	 *       the _connectionString to set
	 */
	public void set_connectionString(String _connectionString) {
		this._connectionString = _connectionString;
	}

	/**
	 * @return the _username
	 */
	public String get_username() {
		return _username;
	}

	/**
	 * @param _username
	 *            the _username to set
	 */
	public void set_username(String _username) {
		this._username = _username;
	}

	/**
	 * @return the _password
	 */
	public String get_Password() {
		return _password;
	}

	/**
	 * @param _password
	 *            the _password to set
	 */
	public void set_Password(String _Password) {
		this._password = _Password;
	}

	/**
	 * @return the _jdbcDriver
	 */
	public String get_jdbcDriver() {
		return _jdbcDriver;
	}

	/**
	 * @param _jdbcDriver
	 *            the _jdbcDriver to set
	 */
	public void set_jdbcDriver(String _jdbcDriver) {
		this._jdbcDriver = _jdbcDriver;
	}

	/**
	 * @return connection resultEnc
	 */
	public String testConnection() {
		String result = "Connection failed!";
		try {
			Connection con = getConnection();
			if (con != null) {
				result = "Connection is OK.";
				con.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * execute none query
	 * 
	 * @param procedureName
	 *            Name of store procedure
	 */
	public void executeNoneQuery(String procedureName) {
		try {
			// Connection
			Connection con = getConnection();
			// Prepare and call the stored procedure
			CallableStatement proc = con.prepareCall("{call " + procedureName
					+ "}");
			proc.executeUpdate();
			proc.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return connection object
	 */
	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(_jdbcDriver);
			con = DriverManager.getConnection(_connectionString, _username,
					_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/**
	 * @param _jdbcDriver
	 * @param _connectionString
	 * @param _username
	 * @param _password
	 */
	public SqlServerHelper(String _jdbcDriver, String _connectionString,
			String _username, String _password) {
		this._jdbcDriver = _jdbcDriver;
		this._connectionString = _connectionString;
		this._username = _username;
		this._password = _password;
	}

}
