package kredit.web.security.model.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.log4j.Logger;
import org.sql2o.Connection;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Expr;
import com.avaje.ebean.Query;

import kredit.web.core.model.Employee;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.OracleHelper;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.model.Scalar;
import kredit.web.core.util.model.ScalarString;
import kredit.web.security.model.Function;
import kredit.web.security.model.ParamEmp;
import kredit.web.security.model.ParamFunction;
import kredit.web.security.model.ParamRole;
import kredit.web.security.model.ParamUser;
import kredit.web.security.model.Role;
import kredit.web.security.model.User;
import kredit.web.security.model.UserValidity;

public class SecurityFacade {

	private static Logger logger = Logger.getLogger(SecurityFacade.class);

	// region general

	public static List<CodeItem> getBranchesList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT id, branch_code code, name_en description FROM SYS_BRANCH ORDER BY name_en";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(CodeItem.class);
			}

			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branchs List.", e);
		}
		return branchs;
	}

	public static CodeItem getBranch(int id) {
		CodeItem branch = null;
		try {
			String sql = "SELECT id, branch_code code, name_en description FROM SYS_BRANCH WHERE id = :id";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branch = con.createQuery(sql).addParameter("id", id)
						.executeAndFetchFirst(CodeItem.class);
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Branch: id = " + id, e);
		}
		return branch;
	}

	public static CodeItem getBranch(String code) {
		CodeItem branch = null;
		try {
			String sql = "SELECT id, branch_code code, name_en description FROM SYS_BRANCH WHERE branch_code = :code";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branch = con.createQuery(sql).addParameter("code", code)
						.executeAndFetchFirst(CodeItem.class);
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branch: code = " + code, e);
		}
		return branch;
	}

	public static List<CodeItem> getSysLovItem(String type) {
		List<CodeItem> lst = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT id, code, desc_en description FROM SYS_LOV WHERE type = :type ORDER BY desc_en desc";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql).addParameter("type", type)
						.executeAndFetch(CodeItem.class);
			}

			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Items List.", e);
		}
		return lst;
	}

	public static CodeItem getSysLovItem(String type, String code) {
		CodeItem branch = null;
		try {
			String sql = "SELECT id, code, desc_en description FROM SYS_LOV WHERE type = :type AND code = :code";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branch = con.createQuery(sql).addParameter("type", type)
						.addParameter("code", code)
						.executeAndFetchFirst(CodeItem.class);
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Sql2o error while getting Item: code = " + code
					+ ", Type = " + type, e);
		}
		return branch;
	}

	public static User getUser(Integer id) {
		User user = null;
		try {
			user = Ebean.find(User.class).where().eq("ID", id).findUnique();

		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Ebean error while getting User: id = " + id, e);
		}
		return user;
	}

	// Check if the name(username, name) is already used or not
	public static int duplicated(String name, int type) {
		int duplicated = 0;
		try {
			String sql = "SELECT COUNT(ID) AS value FROM #t# WHERE #p# = :name";
			switch (type) {
			case 1: // SYS_USER
				sql = sql.replace("#t#", "SYS_USER");
				sql = sql.replace("#p#", "USERNAME");
				break;
			case 2: // SYS_ROLE
				sql = sql.replace("#t#", "SYS_ROLE");
				sql = sql.replace("#p#", "NAME");
				break;

			case 3: // SYS_FUNCTION
				sql = sql.replace("#t#", "SYS_FUNCTION");
				sql = sql.replace("#p#", "NAME");
				break;
			}

			Scalar scalar = new Scalar();

			try (Connection con = Sql2oHelper.sql2o.open()) {
				scalar = con.createQuery(sql).addParameter("name", name)
						.executeAndFetchFirst(Scalar.class);
			}

			duplicated = scalar.getValue();

		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Duplicated: name = " + name, e);
		}
		return duplicated;
	}

	public static String getFullBranch(Integer id) {
		String branch = "";
		try {
			String sql = "SELECT BRANCH_CODE || ' - ' || NAME_EN AS value FROM SYS_BRANCH WHERE ID = :id";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				ScalarString scalar = con.createQuery(sql)
						.addParameter("id", id)
						.executeAndFetchFirst(ScalarString.class);

				branch = scalar.getValue();

			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Full Branch: id = " + id, e);
		}
		return branch;
	}

	public static List<CodeItem> getPositionList() {
		List<CodeItem> positions = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 'All' description FROM DUAL"
					+ " UNION ALL "
					+ "SELECT DISTINCT POSITION description FROM SYS_EMPLOYEE ORDER BY description";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				positions = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);
			}

			if (positions == null) {
				positions = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Category List.", e);
		}
		return positions;
	}

	public static List<CodeItem> getBranchesListWithAll() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 0 id, ' ' code, 'All' description FROM DUAL"
					+ " UNION ALL "
					+ "SELECT id, branch_code code, name_en description FROM SYS_BRANCH ORDER BY description";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(CodeItem.class);
			}

			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branchs List.", e);
		}
		return branchs;
	}
	
	public static Map<String, String> getRoleCode(Integer userid)
	{
		Map<String, String> roleMap = new HashMap<String, String>();
		try
		{
			List<ScalarString> lst = new ArrayList<ScalarString>();
			String sql = "SELECT r.ROLE_CODE as value FROM SYS_ROLE r " + 
						"INNER JOIN SYS_USER_ROLE ur ON r.ID = ur.ROLE_ID " +
						"INNER JOIN SYS_USER u ON ur.USER_ID = u.ID WHERE u.ID = :userid";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.addParameter("userid", userid)
						.executeAndFetch(ScalarString.class);
			}

			if (lst == null) {
				lst = new ArrayList<ScalarString>();
			}
			
			for(Integer i = 0; i < lst.size(); i++)
			{
				roleMap.put(i.toString(), lst.get(i).getValue());
			}
			
		} catch (Exception e)
		{
			logger.error("Ebean error while getting rolecode map.", e);
		}
		
		return roleMap;
	}

	// endregion general

	// region USER

	public static List<User> getUsersList(ParamUser param) {
		List<User> users = new ArrayList<User>();

		try {

			if (param.getFilter() == null
					|| param.getFilter().trim().equals("")) {
				users = Ebean.find(User.class).findList();
			} else {
				users = Ebean
						.find(User.class)
						.where()
						.disjunction()
						.eq("branch_code", param.getFilter())
						.ilike("branch_name",
								Common.addLikeExpression(param.getFilter()))
						.ilike("full_name",
								Common.addLikeExpression(param.getFilter()))
						.ilike("username",
								Common.addLikeExpression(param.getFilter()))
						.endJunction().findList();
			}

			if (users == null) {
				users = new ArrayList<User>();
			}

		} catch (Exception e) {
			logger.error("Ebean error while getting Users List.", e);
		}
		return users;
	}

	public static List<UserValidity> getValiditiesList(Integer user_id) {
		List<UserValidity> validities = new ArrayList<UserValidity>();

		try {

			if (user_id == null || user_id == 0) {
				return validities;
			} else {
				validities = Ebean.find(UserValidity.class).where()
						.eq("user_id", user_id).findList();
			}
		} catch (Exception e) {
			logger.error("Ebean error while getting Validities List.", e);
		}
		return validities;
	}

	public static List<User> getUserByBranch(String branch_code, Integer user_id) {
		List<User> users = new ArrayList<User>();

		try {

			if (branch_code != null) {
				if (user_id != null) {
					users = Ebean.find(User.class).where()
							.eq("branch_code", branch_code).ne("id", user_id)
							.findList();
				} else {
					users = Ebean.find(User.class).where()
							.eq("branch_code", branch_code).findList();
				}
			}

			if (users == null) {
				users = new ArrayList<User>();
			}

		} catch (Exception e) {
			logger.error("Ebean error while getting Users As List.", e);
		}
		return users;
	}

	// get the List of all Roles that still available to this User
	public static List<Role> getAvailableRolesList(Integer user_id) {
		List<Role> roles = new ArrayList<Role>();

		try {

			if (user_id == null || user_id == 0) {
				roles = Ebean.find(Role.class).select("name").findList();
			} else {
				String sql = "SELECT r.* FROM SYS_ROLE r WHERE ID NOT IN (SELECT ROLE_ID FROM SYS_USER_ROLE WHERE USER_ID = :id)";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					roles = con.createQuery(sql).addParameter("id", user_id)
							.executeAndFetch(Role.class);
				}

			}

			if (roles == null) {
				roles = new ArrayList<Role>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Avalable Roles List.", e);
		}
		return roles;
	}

	// get the List of all Roles of this User
	public static List<Role> getOwnRolesList(Integer user_id) {
		List<Role> roles = new ArrayList<Role>();

		try {
			if (user_id == null || user_id == 0) {
				return roles;
			} else {
				String sql = "SELECT r.* FROM SYS_ROLE r INNER JOIN SYS_USER_ROLE ur ON r.ID = ur.ROLE_ID WHERE ur.USER_ID = :id";
				try (Connection con = Sql2oHelper.sql2o.open()) {
					roles = con.createQuery(sql).addParameter("id", user_id)
							.executeAndFetch(Role.class);
				}
			}

			if (roles == null) {
				roles = new ArrayList<Role>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Own Roles List.", e);
		}
		return roles;
	}

	// get the List of all Functions of this User
	public static List<Function> getOwnFunctionsList(Integer user_id) {
		List<Function> functions = new ArrayList<Function>();

		try {
			if (user_id == null || user_id == 0) {
				return functions;
			} else {
				String sql = "SELECT f.* FROM SYS_FUNCTION f INNER JOIN SYS_USER_FUNCTION uf ON f.ID = uf.FUNCTION_ID WHERE uf.USER_ID = :id";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					functions = con.createQuery(sql)
							.addParameter("id", user_id)
							.executeAndFetch(Function.class);
				}
			}

			if (functions == null) {
				functions = new ArrayList<Function>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Own Functions List.", e);
		}
		return functions;
	}

	// get the List of all Functions that still available to this User
	public static List<Function> getAvailableFunctionsList(Integer user_id) {
		List<Function> functions = new ArrayList<Function>();

		try {
			if (user_id == null || user_id == 0) {
				functions = Ebean
						.find(Function.class)
						.select("name")
						.where()
						.and(Expr.isNotNull("PARENT_CODE"),
								Expr.isNotNull("SEQ")).findList();
			} else {
				String sql = "SELECT f.* FROM SYS_FUNCTION f WHERE f.PARENT_CODE IS NOT NULL AND SEQ IS NOT NULL AND ID NOT IN (SELECT FUNCTION_ID FROM SYS_USER_FUNCTION WHERE USER_ID = :id)";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					functions = con.createQuery(sql)
							.addParameter("id", user_id)
							.executeAndFetch(Function.class);
				}

			}

			if (functions == null) {
				functions = new ArrayList<Function>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Avalable Functions List.",
					e);
		}
		return functions;
	}

	public static List<Employee> getEmployeesList(ParamEmp param) {
		List<Employee> emps = new ArrayList<Employee>();
		try {
			Query<Employee> query = Ebean.find(Employee.class).select(
					"ID, EMP_ID, LNAME_EN, FNAME_EN, BRANCH, POSITION");

			if (!(param.getFilter() == null || param.getFilter().trim()
					.equals(""))) {
				query.where()
						.disjunction()
						.ilike("emp_id",
								Common.addLikeExpression(param.getFilter()))
						.ilike("lname_en",
								Common.addLikeExpression(param.getFilter()))
						.ilike("fname_en",
								Common.addLikeExpression(param.getFilter()))
						.endJunction();
			}

			if (!(param.getBranch().getCode().equals(""))) {
				query.where().eq("branch", param.getBranch().getId());
			}

			if (!(param.getPosition().getDescription().equals("All"))) {
				query.where().eq("position",
						param.getPosition().getDescription());
			}

			emps = query.orderBy("lname_en").findList();

			if (emps == null)
				emps = new ArrayList<Employee>();
		} catch (Exception e) {
			logger.error("Ebean error while getting Employees List.", e);
		}

		return emps;
	}

	public static Employee getEmployee(Integer id) {
		Employee emp = new Employee();
		try {
			if (id != null) {
				String sql = "SELECT * FROM SYS_EMPLOYEE WHERE EMP_ID = :id";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					emp = con.createQuery(sql).addParameter("id", id)
							.executeAndFetchFirst(Employee.class);
				}
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Employee: emp_id = " + id,
					e);
		}

		return emp;
	}

	public static List<User> getSyncUserList() {
		List<User> syncUsers = new ArrayList<User>();

		try {
			String sql = "SELECT u.EXT_USER_REF EMP_ID, b.ID BRANCH_ID, u.HOME_BRANCH BRANCH_CODE, "
					+ "b.NAME_EN BRANCH_NAME, u.USER_NAME FULL_NAME, u.USER_ID USERNAME, u.USER_PASSWORD || '!' || u.SALT PWD, "
					+ "null PWD_CHANGE_ON, 'N' PWD_CHANGE_FORCE, null TIME_LEVEL, null AUTHORIZED, "
					+ "null START_ON, null END_ON, 0 SUCCESS, 0 FAIL, 'N' LOCKED, 'A' STATUS, "
					+ ":name CREATE_BY, :date CREATE_ON, null CHANGE_BY, null CHANGE_ON, -1 SECURITY_NO "
					+ "FROM SMTB_USER u INNER JOIN SYS_BRANCH b ON u.HOME_BRANCH = b.BRANCH_CODE AND b.ID <> 342 "
					+ "WHERE USER_ID NOT IN (SELECT SYS_USER.USERNAME FROM SYS_USER)";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				syncUsers = con
						.createQuery(sql)
						.addParameter(
								"name",
								UserCredentialManager.getIntance()
										.getLoginUsr().getUsername())
						.addParameter("date", new Date())
						.executeAndFetch(User.class);
			}

			if (syncUsers == null) {
				syncUsers = new ArrayList<User>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Sync Users List.", e);
		}
		return syncUsers;
	}

	public static String getSyncUserPwd(String user) {
		String pwd = "";

		try {
			String sql = "SELECT USER_PASSWORD || '!' || SALT AS value FROM SMTB_USER WHERE USER_ID = :user";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				ScalarString scalar = con.createQuery(sql)
						.addParameter("user", user)
						.executeAndFetchFirst(ScalarString.class);

				pwd = scalar.getValue();
			}

			if (pwd == null) {
				pwd = "";
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Sync User Pwd.", e);
		}
		return pwd;
	}

	public static int getSyncUserExisted(String user) {
		int existed = 0;

		try {
			String sql = "SELECT COUNT(USER_ID) AS value FROM SMTB_USER WHERE USER_ID = :user";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				Scalar scalar = con.createQuery(sql).addParameter("user", user)
						.executeAndFetchFirst(Scalar.class);

				existed = scalar.getValue();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Sync User Existed or Not.",
					e);
		}
		return existed;
	}
		
	public static int syncPwdWithFlexcube(String pwd, String username) {
		int update = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			java.sql.Connection conn = OracleHelper.getConnection();
			String sql = "UPDATE SYS_USER SET PWD = ? WHERE USERNAME = ?";
			update = queryRunner.update(conn, sql, pwd, username);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("DBUtils error while updating Password.", e);
		}
		return update;
	}
		
// endregion USER
	
// region FUNCTION

	public static List<Function> getFunctionsList(ParamFunction param) {
		List<Function> functions = new ArrayList<Function>();

		try {

			if (param.getFilter() == null
					|| param.getFilter().trim().equals("")) {
				functions = Ebean.find(Function.class)
						.select("NAME, CODE, TYPE, STATUS, RIGHT")
						// .where()
						// .and(Expr.isNotNull("PARENT_CODE"),
						// Expr.isNotNull("SEQ"))
						.findList();
			} else {
				functions = Ebean
						.find(Function.class)
						.select("NAME, CODE, TYPE, STATUS, RIGHT")
						.where()
						// .and(Expr.isNotNull("PARENT_CODE"),
						// Expr.isNotNull("SEQ"))
						.disjunction()
						.ilike("name",
								Common.addLikeExpression(param.getFilter()))
						.ilike("code",
								Common.addLikeExpression(param.getFilter()))
						.ilike("right",
								Common.addLikeExpression(param.getFilter()))
						.endJunction().findList();
			}

			if (functions == null) {
				functions = new ArrayList<Function>();
			}

		} catch (Exception e) {
			logger.error("Ebean error while getting Functions List.", e);
		}
		return functions;
	}

	public static List<CodeItem> getRightList(String type, String right) {
		List<CodeItem> functions = new ArrayList<CodeItem>();

		try {

			if (right == null || right.trim().equals(""))
				return functions;

			String sql = "SELECT id, code, desc_en description FROM SYS_LOV WHERE type = :type "
					+ "AND code IN ("
					+ Common.arrayQuery(right)
					+ ") "
					+ "ORDER BY desc_en desc";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				functions = con.createQuery(sql).addParameter("type", type)
						.executeAndFetch(CodeItem.class);
			}

			if (functions == null) {
				functions = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Own Rights List.", e);
		}
		return functions;
	}

	public static List<CodeItem> getRightAllList(String type, String right) {
		List<CodeItem> functions = new ArrayList<CodeItem>();

		try {
			String sql;
			if (right == null || right.trim().equals("")) {
				sql = "SELECT id, code, desc_en description FROM SYS_LOV WHERE type = :type ORDER BY desc_en desc";
			} else {
				sql = "SELECT id, code, desc_en description FROM SYS_LOV WHERE type = :type "
						+ "AND code NOT IN ("
						+ Common.arrayQuery(right)
						+ ") "
						+ "ORDER BY desc_en desc";
			}

			try (Connection con = Sql2oHelper.sql2o.open()) {
				functions = con.createQuery(sql).addParameter("type", type)
						.executeAndFetch(CodeItem.class);
			}

			if (functions == null) {
				functions = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting All Rights List.", e);
		}
		return functions;
	}

	public static List<Role> getAvailableRole(Integer function_id) {
		List<Role> roles = new ArrayList<Role>();

		try {
			if (function_id == null || function_id == 0) {
				roles = Ebean.find(Role.class).select("name").findList();
			} else {
				String sql = "SELECT r.* FROM SYS_ROLE r WHERE ID NOT IN (SELECT ROLE_ID FROM SYS_ROLE_FUNCTION WHERE FUNCTION_ID = :id)";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					roles = con.createQuery(sql)
							.addParameter("id", function_id)
							.executeAndFetch(Role.class);
				}

			}

			if (roles == null) {
				roles = new ArrayList<Role>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Avalable Roles List.", e);
		}
		return roles;
	}

	public static List<User> getAvailableUser(Integer function_id) {
		List<User> users = new ArrayList<User>();

		try {
			if (function_id == null || function_id == 0) {
				users = Ebean.find(User.class).select("username").findList();
			} else {
				String sql = "SELECT u.* FROM SYS_USER u WHERE ID NOT IN (SELECT USER_ID FROM SYS_USER_FUNCTION WHERE FUNCTION_ID = :id)";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					users = con.createQuery(sql)
							.addParameter("id", function_id)
							.executeAndFetch(User.class);
				}
			}

			if (users == null) {
				users = new ArrayList<User>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Avalable Users List.", e);
		}
		return users;
	}

	// endregion FUNCTION

	// region ROLE

	public static List<Role> getRolesList(ParamRole param) {
		List<Role> roles = new ArrayList<Role>();

		try {

			if (param.getFilter() == null
					|| param.getFilter().trim().equals("")) {
				roles = Ebean.find(Role.class).findList();
			} else {
				roles = Ebean
						.find(Role.class)
						.where()
						.disjunction()
						.ilike("name",
								Common.addLikeExpression(param.getFilter()))
						.ilike("role_code",
								Common.addLikeExpression(param.getFilter()))
						.endJunction().findList();
			}

			if (roles == null) {
				roles = new ArrayList<Role>();
			}

		} catch (Exception e) {
			logger.error("Ebean error while getting Roles List.", e);
		}
		return roles;
	}

	// get the List of all Users with this Role
	public static List<User> getOwnUsersList(Integer role_id) {
		List<User> users = new ArrayList<User>();

		try {
			if (role_id == null || role_id == 0) {
				return users;
			} else {
				String sql = "SELECT u.* FROM SYS_USER u INNER JOIN SYS_USER_ROLE ur ON u.ID = ur.USER_ID WHERE ur.ROLE_ID = :id";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					users = con.createQuery(sql).addParameter("id", role_id)
							.executeAndFetch(User.class);
				}
			}

			if (users == null) {
				users = new ArrayList<User>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Own Users List.", e);
		}
		return users;
	}

	// get the List of all Users that still available for this Role
	public static List<User> getAvailableUsersList(Integer role_id) {
		List<User> users = new ArrayList<User>();

		try {

			if (role_id == null || role_id == 0) {
				users = Ebean.find(User.class).select("username").findList();
			} else {
				String sql = "SELECT u.* FROM SYS_USER u WHERE ID NOT IN (SELECT USER_ID FROM SYS_USER_ROLE WHERE ROLE_ID = :id)";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					users = con.createQuery(sql).addParameter("id", role_id)
							.executeAndFetch(User.class);
				}
			}

			if (users == null) {
				users = new ArrayList<User>();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Available Users List.", e);
		}
		return users;
	}

	public static List<Function> getAvailableFunctionsListForRole(
			Integer role_id) {
		List<Function> functions = new ArrayList<Function>();

		try {

			if (role_id == null || role_id == 0) {
				functions = Ebean
						.find(Function.class)
						.select("name")
						.where()
						.and(Expr.isNotNull("PARENT_CODE"),
								Expr.isNotNull("SEQ")).findList();
			} else {
				String sql = "SELECT f.* FROM SYS_FUNCTION f WHERE f.PARENT_CODE IS NOT NULL AND SEQ IS NOT NULL AND ID NOT IN (SELECT FUNCTION_ID FROM SYS_ROLE_FUNCTION WHERE ROLE_ID = :id)";

				try (Connection con = Sql2oHelper.sql2o.open()) {
					functions = con.createQuery(sql)
							.addParameter("id", role_id)
							.executeAndFetch(Function.class);
				}
			}

			if (functions == null) {
				functions = new ArrayList<Function>();
			}

		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Avalable Functions List for Role.",
					e);
		}
		return functions;
	}

	// endregion ROLE

	// region track security

	// type: SUCCESS, FAIL
	public static void increaseCount(String username, String type) {
		
		try {
			QueryRunner run = new QueryRunner();
			java.sql.Connection conn = OracleHelper.getConnection();

			String sql = "UPDATE sys_user set " + type + " = " + type
					+ " + 1 # WHERE username =?";
			
			sql = sql.replace("#", type.equals("FAIL")? "": ", LAST_LOGIN_ON = SYSDATE");

			// Execute the query and get the results back from the handler
			run.update(conn, sql, username);
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while increasing count type = " + type
					+ ", for username = " + username, e);
		}
	}

	public static void onSetPwdChangeTime(int userID) {
		try {
			QueryRunner run = new QueryRunner();
			java.sql.Connection conn = OracleHelper.getConnection();

			String sql = "UPDATE sys_user set PWD_CHANGE_ON = SYSDATE WHERE id =?";

			// Execute the query and get the results back from the handler
			run.update(conn, sql, userID);
			DbUtils.close(conn);

		} catch (Exception e) {
			logger.error("Error while update pwd_change_on for user id = "
					+ userID, e);
		}
	}
	// endregion

}
