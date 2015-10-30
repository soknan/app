/**
 * 
 */
package kredit.web.core.util.authentication.model.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kredit.web.core.util.Common;
import kredit.web.core.util.db.OracleHelper;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.log.model.LoggerSession;
import kredit.web.core.util.log.model.LoggerSessionLog;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.model.Scalar;
import kredit.web.security.model.Function;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;
import org.k.model.User;
import org.k.sm.LoginManger;
import org.sql2o.Connection;
import org.zkoss.zk.ui.Executions;

import com.avaje.ebean.Ebean;

/**
 * @author Sovathena Neth
 * 
 */
public class UserCredentialFacade {

	static Logger logger = Logger.getLogger(UserCredentialFacade.class
			.getName());

	public Map<String, Object> doLogin(String username, String password) {
		Map<String, Object> mapLogin = null;
		try {
			mapLogin = LoginManger.login(username, password);

		} catch (Exception e) {

		}
		return mapLogin;
	}

	public void doLogout(String username) {
		LoginManger.logout(username);
	}

	public String resetPassword(User loginUsr) throws Throwable {
		if (loginUsr == null)
			return "failed";

		int i = resetPwd(loginUsr);

		UserCredentialManager mgmt = UserCredentialManager.getIntance();
		// add log
		LoggerSession ses = mgmt.getLoggerSession();
		if (ses.getBrCd() == null) {
			ses.setBrCd(loginUsr.getHomeBranch());
			ses.setUsername(loginUsr.getUsername());
			ses.setIp(Common.getUserIpAddr(Executions.getCurrent()));
			ses.setHost(Executions.getCurrent().getRemoteHost());
			ses.setAgent(Executions.getCurrent().getUserAgent());
		}

		LoggerSessionLog log = new LoggerSessionLog();
		log.setBrCd(loginUsr.getHomeBranch());
		log.setModule("resetpwd");
		ses.getSessionLogs().add(log);
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(loginUsr.getUsername()).append(" reset pwd --> ");

		if (i < 0) {
			strBuilder.append("failed --> See error log");
			log.setMessage(strBuilder.toString());
			log.setType("RSPWD_F");
			Ebean.save(ses);
			return "failed";
		} else {
			strBuilder.append("success");
			log.setMessage(strBuilder.toString());
			log.setType("RSPWD_S");
			Ebean.save(ses);
			return "successfully";
		}
	}

	public static boolean isValidSecurityCode(int userId, int securityNo) {
		String sql = "SELECT COUNT(*) value FROM sys_user WHERE id=:id AND security_no =:security_no AND status = 'A'";

		Scalar obj = null;
		try (Connection con = Sql2oHelper.sql2o.open()) {
			obj = con.createQuery(sql).addParameter("id", userId)
					.addParameter("security_no", securityNo)
					.executeAndFetchFirst(Scalar.class);
			if (obj == null)
				return false;
		}

		return obj.getValue() > 0;
	}

	public static int resetPwd(User loginUser) throws Throwable {
		if (loginUser == null)
			return -1;

		try {
			QueryRunner run = new QueryRunner();
			java.sql.Connection conn = OracleHelper.getConnection();

			String sql = "UPDATE sys_user set pwd =?, security_no =-1 WHERE id =? AND security_no=?";
			// Execute the query and get the results back from the handler
			run.update(conn, sql, loginUser.getPwd(), loginUser.getId(),
					loginUser.getSecurityNo());
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while resetting pwd.", e);
			return -1;
		}

		return 1;
	}

	public static User requestSecurityNo(String username) throws Throwable {
		if (username == null || username.isEmpty())
			return null;

		String sql1 = "SELECT id value FROM sys_user WHERE lower(username)= lower(:username) or lower(email)= lower(:username)";
		String sql3 = "SELECT id, username, full_name fullName, security_no securityNo, email, branch_code brCd, branch_name brName, phone FROM sys_user WHERE id =:id";

		User user = null;
		Scalar obj = null;
		try {

			try (Connection con = Sql2oHelper.sql2o.open()) {
				obj = con.createQuery(sql1).addParameter("username", username)
						.executeAndFetchFirst(Scalar.class);

				if (obj == null)
					return null;

				generateSecurityNo(obj.getValue());

				user = con.createQuery(sql3).addParameter("id", obj.getValue())
						.executeAndFetchFirst(User.class);
			}
		} catch (Throwable t) {
			logger.error("error while requesting reset password", t);
			throw t;
		}
		return user;
	}

	public static void generateSecurityNo(int userID) {
		try {
			QueryRunner run = new QueryRunner();
			java.sql.Connection conn = OracleHelper.getConnection();

			String sql = "UPDATE sys_user set security_no = round(dbms_random.value(100000, 999999)) WHERE id =?";

			// Execute the query and get the results back from the handler
			run.update(conn, sql, userID);
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while generating security no.", e);
		}
	}

	public static Map<String, CodeItem> getLoginFunctions() {
		Map<String, CodeItem> functionsMap = new HashMap<String, CodeItem>();
		List<CodeItem> tmpUF = new ArrayList<CodeItem>();
		List<CodeItem> tmpRF = new ArrayList<CodeItem>();
		int loginUserId = UserCredentialManager.getIntance().getLoginUsr()
				.getId();

		try {
			try (Connection con = Sql2oHelper.sql2o.open()) {
				String sqlUF = "SELECT CODE code, uf.RIGHT description, HREF superDescription FROM SYS_USER_FUNCTION uf "
						+ "INNER JOIN SYS_FUNCTION f ON uf.FUNCTION_ID = f.ID "
						+ "WHERE uf.USER_ID = :id AND f.STATUS = 'A'";

				tmpUF = con.createQuery(sqlUF).addParameter("id", loginUserId)
						.executeAndFetch(CodeItem.class);

				String sqlRF = "SELECT CODE code, rf.RIGHT description, HREF superDescription FROM SYS_USER_ROLE ur "
						+ "INNER JOIN SYS_ROLE r on ur.ROLE_ID = r.ID "
						+ "INNER JOIN SYS_ROLE_FUNCTION rf ON ur.ROLE_ID = rf.ROLE_ID "
						+ "INNER JOIN SYS_FUNCTION f on rf.FUNCTION_ID = f.ID "
						+ "WHERE ur.USER_ID = :id AND f.STATUS = 'A' AND r.STATUS = 'A'";

				tmpRF = con.createQuery(sqlRF).addParameter("id", loginUserId)
						.executeAndFetch(CodeItem.class);
			}

			if (tmpUF != null) {
				for (int i = 0; i < tmpUF.size(); i++) {
					CodeItem tmpCUF = new CodeItem();
					tmpCUF.setCode(tmpUF.get(i).getDescription());
					tmpCUF.setDescription(tmpUF.get(i).getSuperDescription());
					functionsMap.put(tmpUF.get(i).getCode(), tmpCUF);
				}
			}

			if (tmpRF != null) {
				for (int i = 0; i < tmpRF.size(); i++) {
					if (!functionsMap.containsKey(tmpRF.get(i).getCode())) {
						CodeItem tmpRUF = new CodeItem();
						tmpRUF.setCode(tmpRF.get(i).getDescription());
						tmpRUF.setDescription(tmpRF.get(i)
								.getSuperDescription());
						functionsMap.put(tmpRF.get(i).getCode(), tmpRUF);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Functions List.", e);
		}
		return functionsMap;
	}

	public static List<Function> getModules(String functions) {
		List<Function> modules = new ArrayList<Function>();
		try {
			String sql = "SELECT DISTINCT fp.NAME, fp.CODE, fp.ICON, fp.SEQ FROM SYS_FUNCTION fp "
					+ "INNER JOIN SYS_FUNCTION f ON fp.CODE = f.PARENT_CODE "
					+ "WHERE f.CODE IN ( " + functions + " ) ORDER BY fp.SEQ";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				modules = con.createQuery(sql).executeAndFetch(Function.class);
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Module List.", e);
		}
		return modules;
	}

	public static List<Function> getSubModules(String functions) {
		List<Function> modules = new ArrayList<Function>();
		try {
			String sql = "SELECT DISTINCT f.NAME, f.CODE, f.HREF, f.PARENT_CODE, f.SEQ FROM SYS_FUNCTION f "
					+ "WHERE f.MENU = 'Y' AND f.CODE IN ( "
					+ functions
					+ " ) ORDER BY f.SEQ";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				modules = con.createQuery(sql).executeAndFetch(Function.class);
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Sub Module List.", e);
		}
		return modules;
	}

	public static Map<String, CodeItem> getLoginFunctions(Integer UserId) {
		Map<String, CodeItem> functionsMap = new HashMap<String, CodeItem>();
		List<CodeItem> tmpUF = new ArrayList<CodeItem>();
		List<CodeItem> tmpRF = new ArrayList<CodeItem>();

		try {

			try (Connection con = Sql2oHelper.sql2o.open()) {
				String sqlUF = "SELECT CODE code, uf.RIGHT description, HREF superDescription FROM SYS_USER_FUNCTION uf "
						+ "INNER JOIN SYS_FUNCTION f ON uf.FUNCTION_ID = f.ID "
						+ "WHERE uf.USER_ID = :id AND f.STATUS = 'A'";

				tmpUF = con.createQuery(sqlUF).addParameter("id", UserId)
						.executeAndFetch(CodeItem.class);

				String sqlRF = "SELECT CODE code, rf.RIGHT description, HREF superDescription FROM SYS_USER_ROLE ur "
						+ "INNER JOIN SYS_ROLE r on ur.ROLE_ID = r.ID "
						+ "INNER JOIN SYS_ROLE_FUNCTION rf ON ur.ROLE_ID = rf.ROLE_ID "
						+ "INNER JOIN SYS_FUNCTION f on rf.FUNCTION_ID = f.ID "
						+ "WHERE ur.USER_ID = :id AND f.STATUS = 'A' AND r.STATUS = 'A'";
				tmpRF = con.createQuery(sqlRF).addParameter("id", UserId)
						.executeAndFetch(CodeItem.class);
			}

			if (tmpUF != null) {
				for (int i = 0; i < tmpUF.size(); i++) {
					CodeItem tmpCUF = new CodeItem();
					tmpCUF.setCode(tmpUF.get(i).getDescription());
					tmpCUF.setDescription(tmpUF.get(i).getSuperDescription());
					functionsMap.put(tmpUF.get(i).getCode(), tmpCUF);
				}
			}

			if (tmpRF != null) {
				for (int i = 0; i < tmpRF.size(); i++) {
					if (!functionsMap.containsKey(tmpRF.get(i).getCode())) {
						CodeItem tmpRUF = new CodeItem();
						tmpRUF.setCode(tmpRF.get(i).getDescription());
						tmpRUF.setDescription(tmpRF.get(i)
								.getSuperDescription());
						functionsMap.put(tmpRF.get(i).getCode(), tmpRUF);
					}
				}
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Functions List.", e);
		}
		return functionsMap;
	}
}
