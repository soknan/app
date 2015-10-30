/**
 * 
 */
package kredit.web.kbureau.model.facade;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.db.DbHelper;
import kredit.web.kbureau.model.Scalare;
import kredit.web.kbureau.model.Authentication;
import kredit.web.kbureau.model.admin.ParamUser;
import kredit.web.kbureau.model.facade.admin.UserValidity;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

/**
 * @author vathenan
 * 
 */
public class AuthenticationFacade {

	static Logger logger = Logger.getLogger(AuthenticationFacade.class
			.getName());

	public int saveKbureauUser(int createrId, Authentication kBureauUser) {
		int userId = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "{call dbo.krd_userauthen_save ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
			Scalare scalare = queryRunner.query(conn, sql, h, kBureauUser
					.getUsername(), kBureauUser.getBranch().getCode(),
					kBureauUser.getSubBranch().getCode(), kBureauUser
							.getEmail(), kBureauUser.getStatusObj().getCode(),
					kBureauUser.getfName(), kBureauUser.getlName(), kBureauUser
							.getSexObj().getCode(), kBureauUser.getNote(),
					kBureauUser.getId(), createrId, kBureauUser.getUserType());
			userId = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while saving kBureau user.", e);
		}
		return userId;
	}

	public int saveUserValidity(int createrId, UserValidity userValidity) {
		int userId = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "{call dbo.krd_uservalidity_save ?, ?, ?, ?, ?, ?, ?}";
			Scalare scalare = queryRunner.query(conn, sql, h, createrId,
					userValidity.getUserId(), userValidity.getId(),
					userValidity.getStrRequestDate(),
					userValidity.getStrStartDate(),
					userValidity.getStrEndDate(), userValidity.getNote());
			userId = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while saving user validity.", e);
		}
		return userId;
	}
	
	public int deleteUserValidity(int createrId, UserValidity validity) {
		int userId = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "{call dbo.krd_uservalidity_delete ?, ?}";
			Scalare scalare = queryRunner.query(conn, sql, h,
					validity.getId(), createrId);
			userId = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while deleting user validity.", e);
		}
		return userId;
	}

	public int deleteKbureauUser(int createrId, Authentication kBureauUser) {
		int userId = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "{call dbo.krd_userauthen_delete_user ?, ?}";
			Scalare scalare = queryRunner.query(conn, sql, h,
					kBureauUser.getId(), createrId);
			userId = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while deleting kBureau user.", e);
		}
		return userId;
	}

	public int resetPasswordDefault(int createrId, Authentication kBureauUser) {
		int userId = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "{call krd_userauthen_reset_pwd_default ?, ?}";
			Scalare scalare = queryRunner.query(conn, sql, h,
					kBureauUser.getId(), createrId);
			userId = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while deleting kBureau user.", e);
		}
		return userId;
	}

	public boolean isExistUsername(String username, int userId) {
		boolean exist = false;
		try {
			String sql = "SELECT COUNT(*) Value FROM UserAuthen WHERE Username = ? AND Id <> ? AND Status <> 'D'";

			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);

			Scalare c = queryRunner.query(conn, sql, h, username, userId);
			exist = c.getValue() > 0;
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while checking is exist username.", e);
		}
		return exist;
	}

	public static boolean isExistEmail(String email, int userId) {
		boolean exist = false;
		try {
			String sql = "SELECT COUNT(*) Value FROM UserAuthen WHERE Email = ? AND Id <> ? AND Status = 'A'";

			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);

			Scalare c = queryRunner.query(conn, sql, h, email, userId);
			exist = c.getValue() > 0;
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while checking is exist username.", e);
		}
		return exist;
	}

	public static boolean isValidSecurityCode(int userId, String username,
			int securityCode) {
		boolean exist = false;
		try {
			String sql = "SELECT COUNT(*) Value FROM UserAuthen WHERE Id = ? AND Username = ? AND SecurityCode = ? AND SecurityCode > 0 AND Status = 'A'";

			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);

			Scalare c = queryRunner.query(conn, sql, h, userId, username,
					securityCode);
			if (c != null)
				exist = c.getValue() > 0;
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while checking is exist username.", e);
		}
		return exist;
	}

	public static Authentication getUserByEmailToResetPsw(String email) {
		Authentication user = null;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			ResultSetHandler<Authentication> h = new BeanHandler<Authentication>(
					Authentication.class);

			user = queryRunner.query(conn,
					"{call dbo.krd_userauthen_request_reset_psw ?}", h, email);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error(
					"Error while getting user by email to reset password.", e);
		}
		return user;
	}

	public boolean isExistSubCode(String subCode) {
		boolean exist = false;
		try {
			String sql = "SELECT COUNT(*) Value FROM SubBranch WHERE Code = ?";

			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);

			Scalare c = queryRunner.query(conn, sql, h, subCode);
			exist = c.getValue() > 0;
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while checking is exist username.", e);
		}
		return exist;
	}

	public List<Authentication> getListUser(ParamUser paramUser) {
		List<Authentication> lstUsers = new ArrayList<Authentication>();
		Connection conn = DbHelper.getConnection();
		try {
			ResultSetHandler<List<Authentication>> rsh = new BeanListHandler<Authentication>(
					Authentication.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();

			// Execute the query and get the results back from the handler
			lstUsers = (List<Authentication>) run.query(conn,
					"{call dbo.krd_userauthen_select ?, ?, ?, ?}", rsh,
					paramUser.getFilter(), paramUser.getBranch().getCode(),
					paramUser.getSubBranch().getCode(), paramUser.getStatus()
							.getCode());

		} catch (Exception e) {
			logger.error("Error while getting kBureau user list.", e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				logger.error("Error while getting kBureau user list.", e);
			}
		}
		return lstUsers;
	}
	
	public List<UserValidity> getListValidity(int userId) {
		List<UserValidity> lstUsers = new ArrayList<UserValidity>();
		Connection conn = DbHelper.getConnection();
		try {
			ResultSetHandler<List<UserValidity>> rsh = new BeanListHandler<UserValidity>(
					UserValidity.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();

			// Execute the query and get the results back from the handler
			lstUsers = (List<UserValidity>) run.query(conn,
					"{call dbo.krd_uservalidity_select ?}", rsh,
					userId);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting validity list for userId = " + userId, e);
		} 
		return lstUsers;
	}

	public List<Authentication> getListUserToEmail(String filter) {
		List<Authentication> lstUsers = new ArrayList<Authentication>();
		Connection conn = DbHelper.getConnection();
		try {
			ResultSetHandler<List<Authentication>> rsh = new BeanListHandler<Authentication>(
					Authentication.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();

			// Execute the query and get the results back from the handler
			lstUsers = (List<Authentication>) run.query(conn,
					"{call dbo.krd_userauthen_select_to_email ?}", rsh, filter);

		} catch (Exception e) {
			logger.error("Error while getting kBureau user list.", e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				logger.error("Error while getting kBureau user list.", e);
			}
		}
		return lstUsers;
	}

	public void updateNoteAfterDoneEmail(int id) {
		Connection conn = DbHelper.getConnection();
		try {
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();

			run.update(conn,
					"UPDATE UserAuthen SET Note = 'Done email' WHERE Id = ?",
					id);

		} catch (Exception e) {
			logger.error("Error while updating note email.", e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				logger.error("Error while updating note email.", e);
			}
		}
	}
	
	public void updateCountEmailSent(int id) {
		Connection conn = DbHelper.getConnection();
		try {
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();

			run.update(conn,
					"UPDATE UserAuthen_Validity SET CountEmailSent = CountEmailSent + 1 WHERE Id = ?",
					id);

		} catch (Exception e) {
			logger.error("Error while updating count email acting sent.", e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				logger.error("Error while updating count email acting sent.", e);
			}
		}
	}


	/****************************************************
	 * Convert Result Set Object using DbUtils
	 ****************************************************/
	/*
	 * // Create a ResultSetHandler implementation to convert the // first row
	 * into an Object[] ResultSetHandler<Object[]> h = new
	 * ResultSetHandler<Object[]>() { public Object[] handle(ResultSet rs)
	 * throws SQLException { if (!rs.next()) { return null; }
	 * 
	 * ResultSetMetaData meta = rs.getMetaData(); int cols =
	 * meta.getColumnCount(); Object[] resultEnc = new Object[cols];
	 * 
	 * for (int i = 0; i < cols; i++) { resultEnc[i] = rs.getObject(i + 1); }
	 * 
	 * return resultEnc; } };
	 * 
	 * // Create a QueryRunner that will use connections from // the given
	 * DataSource QueryRunner run = new QueryRunner();
	 * 
	 * // Execute the query and get the results back from the handler userLogs =
	 * (List<Authentication>)run.query(DbHelper.getConnection(),
	 * "SELECT * FROM UserAuthen WHERE Id>?", rsh, 1);
	 */

	/*
	 * public List<UserLog> getStaff(){ List<StaffSearchResult> staff = new
	 * ArrayList<StaffSearchResult>(); List<Object> parameterList = new
	 * ArrayList<Object>(); parameterList.add(officeId); // Use ProcRunner to
	 * populate our resultEnc list from a stored procedure try { ProcRunner prun
	 * = new ProcRunner(); ResultSetHandler<List<StaffSearchResult>> rsh = new
	 * BeanListHandler<StaffSearchResult>(StaffSearchResult.class); staff =
	 * prun.queryProc(getConnection(), "{CALL p_wcct_get_office_staff(?)}", rsh,
	 * parameterList.toArray()); } catch (Exception e){
	 * System.out.println("error in dbutil queryProc call, " + e.getMessage());
	 * e.printStackTrace(); } return staff; }
	 */

	/********************************************
	 * Store Procedure using Hibernate
	 ********************************************/
	/*
	 * @SuppressWarnings("unchecked") public List<UserLog> getListUserLog(){
	 * Session session = KHibernateUtil.openSessionMsSql(); Transaction t =
	 * session.beginTransaction(); Query query =
	 * session.createSQLQuery("{call dbo.krd_userlog_select}"); List rows =
	 * query.list(); List<UserLog> userLogs = new ArrayList<UserLog>();
	 * for(Object row: rows){ UserLog ul = (UserLog)row; userLogs.add(ul);
	 * System.out.println(ul.getAppVersion()); }
	 * 
	 * t.commit(); session.close(); return userLogs; }
	 */

	/***********************************************
	 * JDBC Store Procedure using CallableStatement
	 ***********************************************/
	/*
	 * //Connection Connection con = DbHelper.getConnection(); // Prepare and
	 * call the stored procedure CallableStatement proc = con
	 * .prepareCall("{call dbo.krd_userlog_select}"); ResultSet rs =
	 * proc.executeQuery(); while (rs.next()) {
	 * System.out.println(rs.getString("Id") + ", " +
	 * rs.getString("LoginName")); } proc.close(); con.close();
	 */
}
