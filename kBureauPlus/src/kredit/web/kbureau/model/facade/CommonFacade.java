/**
 * 
 */
package kredit.web.kbureau.model.facade;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.db.DbHelper;
import kredit.web.kbureau.model.report.CodeItem;
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
public class CommonFacade {

	static Logger logger = Logger.getLogger(CommonFacade.class.getName());

	public static List<CodeItem> getBranches() {
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {

			ResultSetHandler<List<CodeItem>> rsh = new BeanListHandler<CodeItem>(
					CodeItem.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lst = (List<CodeItem>) run.query(DbHelper.getConnection(),
					"{call dbo.krd_branch_select}", rsh);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting branches.", e);
		}
		return lst;
	}

	public static List<CodeItem> getSuBranches(int branchId) {
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {

			ResultSetHandler<List<CodeItem>> rsh = new BeanListHandler<CodeItem>(
					CodeItem.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lst = (List<CodeItem>) run.query(DbHelper.getConnection(),
					"{call dbo.krd_subbranch_select ?}", rsh, branchId);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting sub branches.", e);
		}
		return lst;
	}

	public static List<CodeItem> getBranchStar() {
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {

			ResultSetHandler<List<CodeItem>> rsh = new BeanListHandler<CodeItem>(
					CodeItem.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lst = (List<CodeItem>) run.query(DbHelper.getConnection(),
					"{call dbo.krd_branch_select_star}", rsh);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting branches star.", e);
		}
		return lst;
	}

	public static CodeItem getBranchBySubName(String subBranchName) {
		CodeItem lst = new CodeItem();
		try {

			ResultSetHandler<CodeItem> rsh = new BeanHandler<CodeItem>(
					CodeItem.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lst = run.query(DbHelper.getConnection(),
					"{call dbo.krd_branch_select_by_sub ?}", rsh, subBranchName);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting branch by sub branch label.", e);
		}
		return lst;
	}
	
	public static CodeItem getBranchBySubCd(String subCd) {
		CodeItem lst = new CodeItem();
		try {

			ResultSetHandler<CodeItem> rsh = new BeanHandler<CodeItem>(
					CodeItem.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lst = run.query(DbHelper.getConnection(),
					"{call dbo.krd_branch_select_by_sub_cd ?}", rsh, subCd);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting branch by sub branch label.", e);
		}
		return lst;
	}

	public static List<CodeItem> getSuBranchStar(String branchName) {
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			ResultSetHandler<List<CodeItem>> rsh = new BeanListHandler<CodeItem>(
					CodeItem.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lst = (List<CodeItem>) run.query(DbHelper.getConnection(),
					"{call dbo.krd_subbranch_select_star ?}", rsh, branchName);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting sub branches star.", e);
		}
		return lst;
	}
	
	public static List<CodeItem> getSuBranchStarByBranchCd(String code) {
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			ResultSetHandler<List<CodeItem>> rsh = new BeanListHandler<CodeItem>(
					CodeItem.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();

			// Execute the query and get the results back from the handler
			lst = (List<CodeItem>) run.query(DbHelper.getConnection(),
					"{call dbo.krd_subbranch_select_star_by_branch_cd ?}", rsh, code);
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting sub branches star.", e);
		}
		return lst;
	}

}
