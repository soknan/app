package org.k.sm;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import java.util.HashMap;
import java.util.Map;
import kredit.web.core.util.security.Security;
import org.apache.log4j.Logger;
import org.k.model.User;

public class LoginManger
{
  static Logger logger = Logger.getLogger(LoginManger.class);
  
  public static Map<String, Object> login(String username, String pwd)
  {
    Map<String, Object> result = new HashMap();
    


    User user = (User)Ebean.find(User.class).where().ilike("username", username).findUnique();
    if (user == null)
    {
      logger.info("login --> " + username + " --> No record in the database. Invalid username.");
      result.put("log", "login --> " + username + " --> No record in the database. Invalid username.");
      result.put("msg", "Invalid username or password.");
      result.put("ok", "n");
      return result;
    }
    if (!Security.comparePwd(pwd, user.getPwd()))
    {
      logger.info(user.getUsername() + 
        " : login --> invalid password.");
      
      result.put("log", "login --> " + user.getUsername() + " --> invalid password --> failed login");
      result.put("msg", "Invalid username or password");
      result.put("ok", "n");
      
      return result;
    }
    logger.info(user.getUsername() + 
      " : login --> Successful login to kSupport.");
    
    result.put("log", "login --> " + user.getUsername() + " --> valid password --> success login");
    result.put("msg", "login success");
    result.put("ok", "y");
    result.put("usr", user);
    
    return result;
  }
  
  public static void logout(String username)
  {
    logger.info(username + 
      " : logout --> logout from kSupport");
  }
}
