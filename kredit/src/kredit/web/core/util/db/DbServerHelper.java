package kredit.web.core.util.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbServerHelper
{
  private String _jdbcDriver;
  private String _connectionString;
  private String _username;
  private String _password;
  
  public String get_connectionString()
  {
    return this._connectionString;
  }
  
  public void set_connectionString(String _connectionString)
  {
    this._connectionString = _connectionString;
  }
  
  public String get_username()
  {
    return this._username;
  }
  
  public void set_username(String _username)
  {
    this._username = _username;
  }
  
  public String get_Password()
  {
    return this._password;
  }
  
  public void set_Password(String _Password)
  {
    this._password = _Password;
  }
  
  public String get_jdbcDriver()
  {
    return this._jdbcDriver;
  }
  
  public void set_jdbcDriver(String _jdbcDriver)
  {
    this._jdbcDriver = _jdbcDriver;
  }
  
  public String testConnection()
  {
    String result = "Connection failed!";
    try
    {
      Connection con = getConnection();
      if (con != null)
      {
        result = "Connection is OK.";
        con.close();
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  public void executeNoneQuery(String procedureName)
  {
    try
    {
      Connection con = getConnection();
      
      CallableStatement proc = con.prepareCall("{call " + procedureName + 
        "}");
      proc.executeUpdate();
      proc.close();
      con.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public Connection getConnection()
  {
    Connection con = null;
    try
    {
      Class.forName(this._jdbcDriver);
      con = DriverManager.getConnection(this._connectionString, this._username, 
        this._password);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return con;
  }
  
  public DbServerHelper(String _jdbcDriver, String _connectionString, String _username, String _password)
  {
    this._jdbcDriver = _jdbcDriver;
    this._connectionString = _connectionString;
    this._username = _username;
    this._password = _password;
  }
}
