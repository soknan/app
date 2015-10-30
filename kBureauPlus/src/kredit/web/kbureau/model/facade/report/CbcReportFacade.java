/**
 * 
 */
package kredit.web.kbureau.model.facade.report;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.db.DbHelper;
import kredit.web.core.util.db.OracleHelper;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.Scalar;
import kredit.web.kbureau.model.report.Action;
import kredit.web.kbureau.model.report.CbcFee;
import kredit.web.kbureau.model.report.CbcReport;
import kredit.web.kbureau.model.report.CbcReportSummary;
import kredit.web.kbureau.model.report.Decision;
import kredit.web.kbureau.model.report.IncomeExpense;
import kredit.web.kbureau.model.report.ParamCbcReport;
import kredit.web.kbureau.model.report.TotalLoan;

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
public class CbcReportFacade {

	static Logger logger = Logger.getLogger(CbcReportFacade.class.getName());

	public static List<CbcReport> getListCbcReport(ParamCbcReport param) {
		List<CbcReport> lst = new ArrayList<CbcReport>();
		try {

			ResultSetHandler<List<CbcReport>> rsh = new BeanListHandler<CbcReport>(
					CbcReport.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<CbcReport>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of CBC Report.", e);
		}
		return lst;
	}
	
	
	public static List<Action> getListActionByDay(ParamCbcReport param) {
		List<Action> lst = new ArrayList<Action>();
		try {

			ResultSetHandler<List<Action>> rsh = new BeanListHandler<Action>(
					Action.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<Action>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_total_rpt_by_day ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total report daily.", e);
		}
		return lst;
	}
	
	public static List<Action> getListActiveAccDetail(ParamCbcReport param) {
		List<Action> lst = new ArrayList<Action>();
		try {

			ResultSetHandler<List<Action>> rsh = new BeanListHandler<Action>(
					Action.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<Action>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_num_active_accounts_detail ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of number active accounts detail (KREDIT clients vs outside).", e);
		}
		return lst;
	}
	
	public static List<Action> getListActiveAccDetailAmount(ParamCbcReport param) {
		List<Action> lst = new ArrayList<Action>();
		try {

			ResultSetHandler<List<Action>> rsh = new BeanListHandler<Action>(
					Action.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<Action>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_num_active_accounts_detail_amount ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of number active accounts detail (KREDIT clients vs outside).", e);
		}
		return lst;
	}
	
	public static List<Action> getListActionByBranch(ParamCbcReport param) {
		List<Action> lst = new ArrayList<Action>();
		try {

			ResultSetHandler<List<Action>> rsh = new BeanListHandler<Action>(
					Action.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<Action>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_total_rpt_by_branch ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total report by branch.", e);
		}
		return lst;
	}
	
	public static List<TotalLoan> getListLoanByBranch(ParamCbcReport param) {
		List<TotalLoan> lst = new ArrayList<TotalLoan>();
		try {

			ResultSetHandler<List<TotalLoan>> rsh = new BeanListHandler<TotalLoan>(
					TotalLoan.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<TotalLoan>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_total_loan_by_branch ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total report by branch.", e);
		}
		return lst;
	}
	
	
	public static List<TotalLoan> getListNumActiveAcc(ParamCbcReport param) {
		List<TotalLoan> lst = new ArrayList<TotalLoan>();
		try {

			ResultSetHandler<List<TotalLoan>> rsh = new BeanListHandler<TotalLoan>(
					TotalLoan.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<TotalLoan>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_num_active_accounts ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of num active account.", e);
		}
		return lst;
	}
	
	public static List<TotalLoan> getListLoanAll(ParamCbcReport param) {
		List<TotalLoan> lst = new ArrayList<TotalLoan>();
		try {

			ResultSetHandler<List<TotalLoan>> rsh = new BeanListHandler<TotalLoan>(
					TotalLoan.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<TotalLoan>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_total_loan ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total report by branch.", e);
		}
		return lst;
	}
	
	public static List<Action> getListActionByDecision(ParamCbcReport param) {
		List<Action> lst = new ArrayList<Action>();
		try {

			ResultSetHandler<List<Action>> rsh = new BeanListHandler<Action>(
					Action.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<Action>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_action_by_decision ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total report by decision.", e);
		}
		return lst;
	}
	
	public static List<Decision> getListDecisionByAction(ParamCbcReport param) {
		List<Decision> lst = new ArrayList<Decision>();
		try {

			ResultSetHandler<List<Decision>> rsh = new BeanListHandler<Decision>(
					Decision.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<Decision>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_decision_by_action ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total decision by action.", e);
		}
		return lst;
	}

	public static List<Decision> getListDecisionByBranch(ParamCbcReport param) {
		List<Decision> lst = new ArrayList<Decision>();
		try {

			ResultSetHandler<List<Decision>> rsh = new BeanListHandler<Decision>(
					Decision.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<Decision>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_select_decision_by_branch ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total decision by branch.", e);
		}
		return lst;
	}
	
	public static List<CbcFee> getListCbcFeeByBranch(ParamCbcReport param) {
		List<CbcFee> lst = new ArrayList<CbcFee>();
		try {

			ResultSetHandler<List<CbcFee>> rsh = new BeanListHandler<CbcFee>(
					CbcFee.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<CbcFee>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_cbc_fee_by_branch ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total decision by branch.", e);
		}
		return lst;
	}
	
	public static List<IncomeExpense> getListIncomeExpenseByBranch(ParamCbcReport param) {
		List<IncomeExpense> lst = new ArrayList<IncomeExpense>();
		try {

			ResultSetHandler<List<IncomeExpense>> rsh = new BeanListHandler<IncomeExpense>(
					IncomeExpense.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			lst = (List<IncomeExpense>) run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_income_expense_by_branch ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting list of total decision by branch.", e);
		}
		return lst;
	}
	
	public static CbcReportSummary getCbcReportSummary(ParamCbcReport param) {
		CbcReportSummary summary = new CbcReportSummary();
		try {

			ResultSetHandler<CbcReportSummary> rsh = new BeanHandler<CbcReportSummary>(
					CbcReportSummary.class);
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			double fromAmount = param.getFromAmount().isEmpty()?0:Double.parseDouble(param.getFromAmount());
			double toAmount = param.getToAmount().isEmpty()?0:Double.parseDouble(param.getToAmount());
			String status = param.getStatus().getCode().equals("")? "" : param.getStatus().getCode().substring(0, 2);
			// Execute the query and get the results back from the handler
			summary =  run
					.query(DbHelper.getConnection(),
							"{call dbo.krd_cbcreport_summary ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}",
							rsh, param.getFilter(), param.getFilter(), param
									.getBranch().getCode(), param
									.getSubBranch().getCode(), param
									.getRptType().getCode(), status, Integer.parseInt(param.getDecision().getCode()),
							fromAmount, toAmount, param
									.getCurrency().getCode(), param
									.getFromDate(), param.getToDate());
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("Error while getting cbc report summary.", e);
		}
		return summary;
	}
	
	public static int saveDecision(CbcReport decision) {
		int id = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			id = queryRunner.update(conn, "{call krd_save_decision ?, ?, ?}", decision.getId(),
					decision.getDecision(), decision.getNote());
			DbUtils.close(conn);
			
		} catch (Exception e) {
			logger.error("Error while updating Desicion.", e);
		}
		return id;
	}
	
	public static int saveNewRef(CbcReport cbcReportM) {
		int i = 0;
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String status = "P";
			
			if(checkRef(cbcReportM.getLoanId().trim(), status) > 0){
				updateRef(cbcReportM.getRefNumber(), cbcReportM.getLoanId(), status);
			}else{
				String sql = "INSERT INTO CBC_ENQUIRY (ID, REF_NO, ACCOUNT_NO, STATUS) values(CBC_ENQUIRY_SEQ.NEXTVAL,:ref,:acc,:status)";
				con.createQuery(sql)
						.addParameter("ref", cbcReportM.getRefNumber())
						.addParameter("acc", cbcReportM.getLoanId().trim())
						.addParameter("status", status).executeUpdate();
			}
			i = 1;
			return i;
		} catch (Exception e) {
			logger.error("Error Save new reference: ", e);
		}
		return i;
	}
	
	private static int checkRef(String acc, String status) {
		int checkR = 0;
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT COUNT(*) as value FROM CBC_ENQUIRY WHERE account_no =:acc AND status=:status";
			Scalar scalar = con.createQuery(sql)
					.addParameter("acc", acc)
					.addParameter("status", status)
					.executeAndFetchFirst(Scalar.class);
			checkR = scalar.getValue();
		} catch (Exception e) {
			logger.error("Error checkRef: ");
		}
		return checkR;
	}
	
	public static void updateRef(String ref, String acc, String status) {
		Connection conn = OracleHelper.getConnection();
		try {
			QueryRunner run = new QueryRunner();

			run.update(conn,
					"UPDATE CBC_ENQUIRY SET REF_NO = ? WHERE account_no = ? AND status =?", ref, acc, status);

		} catch (Exception e) {
			logger.error("Error while updating Ref.", e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				logger.error("Error while updating Ref.", e);
			}
		}
	}
	
}
