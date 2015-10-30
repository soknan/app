package kredit.web.loanLate.model.facade;

import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.model.Scalar;
import kredit.web.loanLate.model.Branch;
import kredit.web.loanLate.model.LoanLateDetail;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

public class LoanLateFacade {
	
	private static Logger logger = Logger.getLogger(LoanLateFacade.class);
	
	public static LoanLateDetail getLatestLoanDetail(String acc) {
		LoanLateDetail detail = null;
		try {
			String sql = "SELECT * FROM (SELECT * FROM LOAN_LATE_DETAIL " +
						 "	WHERE ACC_NO = :acc order by RECORD_NO DESC)" +
						 "WHERE ROWNUM = 1";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				detail = con.createQuery(sql).addParameter("acc", acc)
						.executeAndFetchFirst(LoanLateDetail.class);
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Detail of Loan Account: " + acc, e);
		}
		return detail;
	}
	
	public static Integer getMaxRecordNo(String acc) {
		Integer result = 0;
		try {
			String sql = "SELECT NVL(MAX(RECORD_NO), 0) AS VALUE FROM LOAN_LATE_DETAIL WHERE ACC_NO = :acc";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				Scalar branch = con.createQuery(sql)
					.addParameter("acc", acc)
					.executeAndFetchFirst(Scalar.class);
				
				result = branch.getValue();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Max RECORD_NO of Account: "
					+ acc, e);
		}
		return result;
	}
	
	public static List<CodeItem> getBranchesListWithAll() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 0 id, ' ' code, 'All' description FROM DUAL" +
					" UNION ALL " +
					"SELECT id, branch_code code, name_en description FROM SYS_BRANCH WHERE TYPE = 'B' ORDER BY description";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
			branchs = con.createQuery(sql)
					.executeAndFetch(CodeItem.class);
			}
			
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Branchs List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getSubBranchesListWithAll() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 0 id, ' ' code, 'All' description FROM DUAL" +
						" UNION ALL " +  
						" SELECT sb.id id, sb.branch_code code, sb.name_en description FROM SYS_BRANCH sb" +
						" WHERE sb.TYPE = 'S' AND sb.branch_code IS NOT NULL ORDER BY description";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				branchs = query.executeAndFetch(CodeItem.class);
			}
			
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Branch List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getSubBranchesListWithAll(int branch_id) {
		List<CodeItem> branchs = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 0 id, ' ' code, 'All' description, 0 superId, ' ' superCode, 'All' superDescription FROM DUAL" +
						" UNION ALL" +  
						" SELECT sb.id id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription FROM SYS_BRANCH sb" +
						" INNER JOIN SYS_BRANCH b ON sb.PARENT_ID = b.ID" +
						" WHERE sb.TYPE = 'S' ORDER BY description";
			
			if(!(branch_id == 0))
			{
				sql = "SELECT 0 id, ' ' code, 'All' description, 0 superId, ' ' superCode, 'All' superDescription FROM DUAL" +
						" UNION ALL" +  
						" SELECT sb.id id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription FROM SYS_BRANCH sb" +
						" INNER JOIN SYS_BRANCH b ON sb.PARENT_ID = b.ID" +
						" WHERE sb.TYPE = 'S' AND sb.PARENT_ID = :branch ORDER BY description";
			}
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!(branch_id == 0))
				{
					query.addParameter("branch", branch_id);
				}
				
				branchs = query.executeAndFetch(CodeItem.class);
			}
			
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Branchs List.", e);
		}
		return branchs;
	}
	
	public static CodeItem getBranch(String code) {
		CodeItem branch = null;
		try {
			String sql = "SELECT b.id superId, b.branch_code superCode, b.name_en superDescription FROM SYS_BRANCH sb" +
						" INNER JOIN SYS_BRANCH b ON sb.PARENT_ID = b.ID" +
						" WHERE sb.TYPE = 'S' AND sb.branch_code = :code";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branch = con.createQuery(sql).addParameter("code", code)
						.executeAndFetchFirst(CodeItem.class);
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branch: code = " + code, e);
		}
		return branch;
	}
	
	public static List<String> getSubBranchCodeList(int id)
	{
		List<String> lst = new ArrayList<String>();
		
		try {
			String sql = "SELECT BRANCH_CODE FROM SYS_BRANCH WHERE PARENT_ID = :id";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
					.addParameter("id", id)
					.executeAndFetch(String.class);
			}
		} catch (Exception e)
		{
			logger.error("Sql2o error while getting Branch Code List of Branch: id = "
					+ id, e);
		}
		
		return lst;
	}
	
	public static Branch getBrItem(String code) {
		Branch branch = null;
		try {
			String sql = "SELECT id, branch_code code, name_en description, type FROM SYS_BRANCH " +
						 "WHERE BRANCH_CODE = :code";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branch = con.createQuery(sql)
						.addParameter("code", code)
						.executeAndFetchFirst(Branch.class);
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branch: code = " + code, e);
		}
		return branch;
	}
	
}

