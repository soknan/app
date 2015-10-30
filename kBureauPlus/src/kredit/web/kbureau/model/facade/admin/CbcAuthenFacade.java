/**
 * 
 */
package kredit.web.kbureau.model.facade.admin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.Common;
import kredit.web.core.util.db.DbHelper;
import kredit.web.kbureau.model.Scalare;
import kredit.web.kbureau.model.admin.CbcAuthen;
import kredit.web.kbureau.model.admin.ParamCbcAuthen;

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
public class CbcAuthenFacade {

	static Logger logger = Logger.getLogger(CbcAuthenFacade.class.getName());

	/**
	 * Save CBC Authentication
	 * 
	 * @param createrId
	 * @param cbcAuthen
	 * @return Id of Authentication
	 */
	public static int saveCbcAuthen(int createrId, CbcAuthen cbcAuthen) {
		int userId = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "{call dbo.krd_cbcauthen_save ?, ?, ?, ?, ?}";

			String password = Common.encrypt(cbcAuthen.getPasswordDec(),
					cbcAuthen.getUsername());

			Scalare scalare = queryRunner.query(conn, sql, h,
					cbcAuthen.getUsername(), password,
					cbcAuthen.getSubBranchCode(), cbcAuthen.getId(), createrId);
			userId = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while saving CBC Authentication.", e);
		}
		return userId;
	}

	public static int deleteCbcAuthen(int createrId, CbcAuthen cbcAuthen) {
		int userId = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Scalare> h = new BeanHandler<Scalare>(
					Scalare.class);
			String sql = "{call dbo.krd_cbcauthen_delete ?, ?}";

			Scalare scalare = queryRunner.query(conn, sql, h,
					cbcAuthen.getId(), createrId);
			userId = scalare.getValue();
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while deleting CBC Authentication.", e);
		}
		return userId;
	}

	/**
	 * Return CBC Authentication List
	 * 
	 * @param param
	 * @return
	 */
	public static List<CbcAuthen> getCbcAuthenList(ParamCbcAuthen param) {
		List<CbcAuthen> lst = new ArrayList<CbcAuthen>();
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<List<CbcAuthen>> h = new BeanListHandler<CbcAuthen>(
					CbcAuthen.class);

			String sql = "{call dbo.krd_cbcauthen_select ?, ?, ?, ?, ?}";

			lst = queryRunner.query(conn, sql, h, param.getFilter(), param
					.getBranch().getCode(), param.getSubBranch().getCode(), param.getFlag().getCode(), param.isIncludeReserve());

			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting CBC authentication list.", e);
		}
		return lst;
	}

}
