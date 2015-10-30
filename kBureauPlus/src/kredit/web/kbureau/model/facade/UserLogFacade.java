/**
 * 
 */
package kredit.web.kbureau.model.facade;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.db.DbHelper;
import kredit.web.kbureau.model.Scalare;
import kredit.web.kbureau.model.UserLog;
import kredit.web.kbureau.model.admin.ParamUsrLog;

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
public class UserLogFacade {

	static Logger logger = Logger.getLogger(UserLogFacade.class.getName());

	public List<UserLog> getListUserLog(ParamUsrLog param) {
		List<UserLog> userLogs = new ArrayList<UserLog>();
		try {

			ResultSetHandler<List<UserLog>> rsh = new BeanListHandler<UserLog>(
					UserLog.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			userLogs = (List<UserLog>) run.query(DbHelper.getConnection(),
					"{call dbo.krd_userlog_select ?, ?, ?, ?, ?, ?, ?, ?}", rsh,
					param.getFilter(), param.getBranch().getCode(), param.getSubBranch().getCode(),
					param.getFromDate(), param.getToDate(), Integer.parseInt(param.getAppType().getCode()),
					Integer.parseInt(param.getUsrType().getCode()), Integer.parseInt(param.getLogType().getCode()));
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of user log.", e);
		}
		return userLogs;
	}

	/***
	 * @return total user log
	 */
	public int getTotalUserLog() {
		int count = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "SELECT COUNT(*) Value FROM UserLog";
			Scalare scalare = queryRunner.query(conn, sql, h);
			count = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while counting user log.", e);
		}
		return count;
	}

}
