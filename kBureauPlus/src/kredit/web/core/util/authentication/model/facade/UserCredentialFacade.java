/**
 * 
 */
package kredit.web.core.util.authentication.model.facade;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kredit.web.core.util.authentication.model.BookmarkRole;
import kredit.web.core.util.authentication.model.Login;
import kredit.web.core.util.db.DbHelper;

import kredit.web.kbureau.model.ScalareString;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.zkoss.zksandbox.MainLayoutComposer;

/**
 * @author Sovathena Neth
 * 
 */
public class UserCredentialFacade {

	static Logger logger = Logger.getLogger(UserCredentialFacade.class
			.getName());

	@SuppressWarnings("rawtypes")
	private Map bookmarkMap;

	@SuppressWarnings("rawtypes")
	public UserCredentialFacade() {
		bookmarkMap = new LinkedHashMap();
	}

	/**
	 * @return the bookmarkMap
	 */
	@SuppressWarnings("rawtypes")
	public Map getBookmarkMap(Login login) {
		if (bookmarkMap.size() == 0) {
			loadBookmarkAccessRule(login);
		}
		return bookmarkMap;
	}

	@SuppressWarnings("rawtypes")
	public Map reloadBookmarkMap(Login login) {
		bookmarkMap.clear();
		loadBookmarkAccessRule(login);
		return bookmarkMap;
	}

	/**
	 * @param bookmarkMap
	 *            the bookmarkMap to set
	 */
	@SuppressWarnings("rawtypes")
	public void set_bookmarkMap(Map _bookmarkMap) {
		this.bookmarkMap = _bookmarkMap;
	}

	public Login doLogin(String username, String password) {
		Login login = null;
		try {
			ResultSetHandler<Login> rsh = new BeanHandler<Login>(Login.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			// Execute the query and get the results back from the handler
			login = (Login) run
					.query(conn,
							"{call dbo.krd_userauthen_authenticate_plus ?, ?, ?, ?, ?}",
							rsh, username, password,
							MainLayoutComposer.userAgent,
							MainLayoutComposer.remoteAddr,
							MainLayoutComposer.remoteHost);
			DbUtils.close(conn);
		} catch (Exception e) {
			int loginId = login == null ? 0 : login.getId();
			MDC.put("loginid", loginId);
			logger.error("Error while trying to login, \nCause: "
					+ e.getMessage());
			MDC.remove("loginid");
		}
		return login;
	}

	public void doLogoff(Login login) {
		if(login == null) return;
		try {
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			// Execute the query and get the results back from the handler
			run.update(conn,
					"{call dbo.krd_userlogplus_insert ?, ?, ?, ?, ?, ?, ?, ?}",
					login.getId(), login.getUsername(), login.getBranchCode(),
					login.getSubBranchCode(), MainLayoutComposer.remoteAddr,
					MainLayoutComposer.remoteHost,
					MainLayoutComposer.userAgent, "Logout kBureauPlus");
			DbUtils.close(conn);
		} catch (Exception e) {
			int loginId = login == null ? 0 : login.getId();
			MDC.put("loginid", loginId);
			logger.error("Error while trying to logoff, \nCause: "
					+ e.getMessage());
			MDC.remove("loginid");
		}
	}
	
	public String resetPassword(Login login) {
		if(login == null) return "";
		String result = "";
		try {
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<ScalareString> rsh = new BeanHandler<ScalareString>(ScalareString.class);
			
			ScalareString scalare =	 run.query(conn,
					"{call dbo.krd_userauthen_reset_password ?, ?, ?, ?, ?, ?, ?}", rsh,
					login.getId(), login.getSecurityCode(), login.getPassword(),
					login.getUsername(), "kplus",
					MainLayoutComposer.remoteAddr,
					MainLayoutComposer.userAgent);
			result = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			int loginId = login == null ? 0 : login.getId();
			MDC.put("loginid", loginId);
			logger.error("Error while trying to reset password, \nCause: "
					+ e.getMessage());
			MDC.remove("loginid");
		}
		return result;
	}

	/******
	 * load boookmark according to access rule -- BookmarkRole
	 * 
	 * @param login
	 */
	@SuppressWarnings("unchecked")
	private void loadBookmarkAccessRule(Login login) {
		try {
			List<BookmarkRole> lstBookmarkRole = new ArrayList<BookmarkRole>();

			ResultSetHandler<List<BookmarkRole>> rsh = new BeanListHandler<BookmarkRole>(
					BookmarkRole.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lstBookmarkRole = (List<BookmarkRole>) run.query(conn,
					"{call dbo.krd_bookmark_select ?}", rsh, login.getId());

			if (lstBookmarkRole.size() > 0) {
				for (BookmarkRole bookmarkRole : lstBookmarkRole) {
					bookmarkMap.put(bookmarkRole.getBookmarkCode(),
							bookmarkRole);
				}
			}

			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while trying to loadBookmarkAccessRule, \nCause: "
					+ e.getMessage());
		}
	}

	public List<BookmarkRole> getSubBookmark(int loginId, String mainCode) {
		List<BookmarkRole> lstBookmarkRole = new ArrayList<BookmarkRole>();
		try {
			ResultSetHandler<List<BookmarkRole>> rsh = new BeanListHandler<BookmarkRole>(
					BookmarkRole.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lstBookmarkRole = (List<BookmarkRole>) run.query(conn,
					"{call dbo.krd_bookmark_select_sub ?, ?}", rsh, loginId,
					mainCode);
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while getting sub bookmarks. MainCode = "
					+ mainCode, e);
		}

		return lstBookmarkRole;
	}
	
	public static void doSaveFilter(Login login) {
		if(login == null) return;
		try {
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			// Execute the query and get the results back from the handler
			run.update(conn,
					"{call dbo.krd_save_user_filter ?, ?, ?}",
					login.getId(), login.getF_branchCode(), login.getF_subBranchCode());
			DbUtils.close(conn);
			
		} catch (Exception e) {
			logger.error("Error while saving user filter, \nCause: "
					+ e.getMessage());
		}
	}

}
