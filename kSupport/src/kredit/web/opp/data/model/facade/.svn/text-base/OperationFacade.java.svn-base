package kredit.web.opp.data.model.facade;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.loanLate.model.Branch;
import kredit.web.loanLate.model.facade.LoanLateFacade;
import kredit.web.opp.data.model.OpCoSummary;
import kredit.web.opp.data.model.OpCommon;
import kredit.web.opp.data.model.OpPlan;
import kredit.web.opp.data.model.OpPlanCo;
import kredit.web.opp.data.model.OpStaff;
import kredit.web.opp.data.model.OpStaffSummary;
import kredit.web.opp.data.model.OpSubSummary;
import kredit.web.opp.data.model.ParamBranches;
import kredit.web.opp.data.model.ParamCo;
import kredit.web.opp.data.model.ParamCommune;
import kredit.web.opp.data.model.ParamPlanList;
import kredit.web.opp.data.model.SysCo;
import kredit.web.opp.data.model.SysCommune;
import kredit.web.security.model.facade.SecurityFacade;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

public class OperationFacade {
	
	private static Logger logger = Logger.getLogger(OperationFacade.class);
	
	public static List<OpPlan> getPlanList(ParamPlanList param) {
		List<OpPlan> plans = new ArrayList<OpPlan>();

		try {
			Query<OpPlan> query = Ebean.find(OpPlan.class);
			
			if(!(param.getFilter() == null || param.getFilter().trim().equals("")))
			{
				query.where()
				.disjunction()
				.eq("plan_year", Integer.parseInt(param.getFilter()))
				.ilike("plan_month", Common.addLikeExpression(param.getFilter()))
				.endJunction();
			}
			
			plans = query.orderBy("plan_year").findList();
			
			if (plans == null) {
				plans = new ArrayList<OpPlan>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Ebean error while getting Plan List.", e);
		}
		return plans;
	}
	
	public static List<OpPlanCo> getPlanCoList() {
		List<OpPlanCo> planCos = new ArrayList<OpPlanCo>();
		
		Map<String, String> rolesMap = SecurityFacade.getRoleCode(
				UserCredentialManager.getIntance().getLoginUsr().getId());
		
		Branch brItem = new Branch();
		String brCd = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
		
		if(rolesMap.containsValue("adm") || rolesMap.containsValue("hq") || brCd.equalsIgnoreCase("000"))
		{
			brItem.setId(0);
			brItem.setCode("000");
			brItem.setType("A");
		}
		
		brItem = LoanLateFacade.getBrItem(brCd);
		
		try {
			Query<OpPlanCo> query = Ebean.find(OpPlanCo.class);
			
			if(!brItem.getCode().equals("000")) {
				if(brItem.getType().equals("B")) {
					query.where()
					.in("branchCode", LoanLateFacade.getSubBranchCodeList(brItem.getId()));
				}
				
				if(brItem.getType().equals("S")) {
					query.where().eq("branchCode", brCd);
				}
			}
			
			planCos = query.orderBy("co_sname").findList();
			
			if (planCos == null) {
				planCos = new ArrayList<OpPlanCo>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Ebean error while getting PlanCo List.", e);
		}
		return planCos;
	}
	
	public static List<SysCo> getCoList(ParamCo param, Branch brItem) {
		List<SysCo> cos = new ArrayList<SysCo>();

		try {
			String sql = "SELECT * FROM" +
							"(SELECT 0 AS ID, c.SHORT_NAME, c.LOCAL_BRANCH AS branch_code, c.FULL_NAME AS full_name " +
							"FROM STTM_CUSTOMER c " +
							"WHERE c.CUSTOMER_CATEGORY = '01' " +
							"UNION " +
							"SELECT co.ID, co.SHORT_NAME, co.BRANCH_CODE, co.SHORT_NAME AS full_name FROM OP_DUMMY_CO co) " +
							"WHERE 1=1 ";
			
			if(!(param.getFilter() == null || param.getFilter().trim().length() == 0)) {
				sql += "AND UPPER(SHORT_NAME) LIKE UPPER(:filter) ";
			}
			
			if(!brItem.getCode().equals("000")) {				
				if(brItem.getType().equals("B")) {
					if(!brItem.getCode().equals("")) {
						sql += "AND BRANCH_CODE = :BRCD ";
					} else {
						sql += "AND BRANCH_CODE IN (" + Common.arrayQuery(LoanLateFacade.getSubBranchCodeList(brItem.getId()).toArray()) + ") ";
					}
				}
				
				if(brItem.getType().equals("S")) {
					sql += "AND BRANCH_CODE = :BRCD ";
				}
			}
			
			sql += " ORDER BY BRANCH_CODE, SHORT_NAME";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!(param.getFilter() == null || param.getFilter().trim().length() == 0)) {
					query.addParameter("filter", Common.addLikeExpression(param.getFilter()));
				}
				
				if(!brItem.getCode().equals("000")) {
					if(brItem.getType().equals("B")) {
						if(!brItem.getCode().equals("")) {
							query.addParameter("BRCD", brItem.getCode());
						} 
					}
					
					if(brItem.getType().equals("S")) {
						query.addParameter("BRCD", brItem.getCode());
					}
				}
				
				cos = query.executeAndFetch(SysCo.class);
			}
			
			if (cos == null) {
				cos = new ArrayList<SysCo>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Co List.", e);
		}
		return cos;
	}
	
	public static List<SysCommune> getCommuneList(ParamCommune param) {
		List<SysCommune> communes = new ArrayList<SysCommune>();

		try {
			String sql = "SELECT DISTINCT sc.COMMUNE, sc.DISTRICT, sc.PROVINCE FROM sttm_cust_add_main_bfsi sc";
			
			if(!(param.getFilter() == null || param.getFilter().trim().length() == 0)) {
				sql += " WHERE sc.COMMUNE LIKE :filter";
			}
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!(param.getFilter() == null || param.getFilter().trim().length() == 0)) {
					query.addParameter("filter", Common.addLikeExpression(param.getFilter()));
				}
				
				communes = query.executeAndFetch(SysCommune.class);
			}
			
			if (communes == null) {
				communes = new ArrayList<SysCommune>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Commune List.", e);
		}
		return communes;
	}
	
	public static SysCo getCo(String co_shortname) {
		SysCo co = new SysCo();
		try {
			String sql = "";
			if(!OpCommon.containNumber(co_shortname)) {
				sql = "SELECT 0 AS ID, c.SHORT_NAME, c.LOCAL_BRANCH AS branch_code, c.FULL_NAME AS full_name " +
					"FROM STTM_CUSTOMER c " +
					"WHERE c.CUSTOMER_CATEGORY = '01' AND c.SHORT_NAME = :CO_SHORT";
			} else {
				sql = "SELECT co.ID, co.SHORT_NAME, co.BRANCH_CODE, co.SHORT_NAME AS full_name " + 
					"FROM OP_DUMMY_CO co " +
					"WHERE co.SHORT_NAME = :CO_SHORT";
			}

			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				query.addParameter("CO_SHORT", co_shortname);
						
				co = query.executeAndFetchFirst(SysCo.class);
			}
		} catch (Exception e) {
			logger.error("Operation Plan - Sql2o error while getting Co.", e);
		}
		return co;
	}
	
	public static List<CodeItem> getLoanProductList() {
		List<CodeItem> products = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT PRODUCT_CODE AS CODE, PRODUCT_DESC AS DESCRIPTION FROM CLTM_PRODUCT WHERE RECORD_STAT = 'O' ORDER BY PRODUCT_DESC";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				products = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);
			}
			
			if (products == null) {
				products = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Loan Product List.", e);
		}
		return products;
	}
	
	public static List<CodeItem> getSavingProductList() {
		List<CodeItem> products = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT DISTINCT ACCOUNT_CLASS AS CODE, DESCRIPTION FROM STTM_ACCOUNT_CLASS WHERE RECORD_STAT = 'O' ORDER BY DESCRIPTION";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				products = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);
			}
			
			if (products == null) {
				products = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Saving Product List.", e);
		}
		return products;
	}
	
	public static CodeItem getProduct(String code, Integer type) {
		CodeItem product = new CodeItem();
		try {
			String sql = "";
			if(type == OpCommon.OP_PRODUCT_TYPE_LOAN) {
				sql = "SELECT PRODUCT_CODE AS CODE, PRODUCT_DESC AS DESCRIPTION FROM CLTM_PRODUCT WHERE PRODUCT_CODE = :code";
			} else {
				sql = "SELECT ACCOUNT_CLASS AS CODE, DESCRIPTION FROM STTM_ACCOUNT_CLASS WHERE ACCOUNT_CLASS = :code";
			}
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				product = con.createQuery(sql)
							.addParameter("code", code)
							.executeAndFetchFirst(CodeItem.class);
			}
		} catch (Exception e) {
			logger.error("Operation Plan - Sql2o error while getting Product.", e);
		}
		return product;
	}
	
	public static List<CodeItem> getCurrencyList() {
		List<CodeItem> currency = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT DISTINCT CCY AS CODE, CCY AS DESCRIPTION FROM ICVW_CCY ORDER BY CCY";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				currency = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);
			}
			
			if (currency == null) {
				currency = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Currency List.", e);
		}
		return currency;
	}
	
	public static String getMonthForInt(int num) {
	    String month = "wrong";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (num >= 0 && num <= 11 ) {
	    month = months[num];
	    }
	    return month;
	}
	
	public static List<OpCoSummary> getCoSummaryList(Integer master_id, String table) {
		List<OpCoSummary> coSummary = new ArrayList<OpCoSummary>();

		String where = "";
		String table1 = "";
		String select = "";
		String join = "";
		
		if(table.equals(OpCommon.OP_LOAN))
		{
			where = "OP_LOAN_ID";
			table1 = "CLTM_PRODUCT";
			select = "PRODUCT_DESC";
			join = "PRODUCT_CODE";
			
		} else {
			where = "OP_SAVING_ID";
			table1 = "STTM_ACCOUNT_CLASS";
			select = "DESCRIPTION";
			join = "ACCOUNT_CLASS";
		}
		
		try {
			String sql = "SELECT P." +  select + " AS DESCRIPTION, " +
						"	L.CURRENCY, " +
						"	SUM(L.Y0M_CLI + L.Y0D_CLI) AS Y0_CLI, " +
						"	SUM(L.Y0M_AMT + L.Y0D_AMT) AS Y0_AMT, " +
						"	SUM(L.M01_CLI + L.M02_CLI + L.M03_CLI + L.M04_CLI + L.M05_CLI + L.M06_CLI + " + 
						"		L.M07_CLI + L.M08_CLI + L.M09_CLI + L.M10_CLI + L.M11_CLI + L.M12_CLI) Y1_CLI, " +
						"	SUM(L.M01_AMT + L.M02_AMT + L.M03_AMT + L.M04_AMT + L.M05_AMT + L.M06_AMT +  " +
						"		L.M07_AMT + L.M08_AMT + L.M09_AMT + L.M10_AMT + L.M11_AMT + L.M12_AMT) Y1_AMT, " +    
						"	SUM(L.Y1D_CLI) AS Y2_CLI, " +
						"	SUM(L.Y1D_AMT) AS Y2_AMT, " +
						"	SUM(L.Y2D_CLI) AS Y3_CLI, " +
						"	SUM(L.Y2D_AMT) AS Y3_AMT " +
						"FROM " + table + " L " +
						"INNER JOIN " + table1 + " P ON L.PRODUCT_CODE = P." + join + " " +
						"WHERE L." + where + " = :MASTER_ID " +
						"GROUP BY P." +  select + ", L.CURRENCY " +
						"ORDER BY P." +  select;
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				query.addParameter("MASTER_ID", master_id);
				
				coSummary = query.executeAndFetch(OpCoSummary.class);
			}
			
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Co Summary List [" + table + "], MASTER_ID: " + master_id, e);
		}
		return coSummary;
	}
	
	public static List<OpSubSummary> getSubSummaryList(Integer master_id, String table, Branch brItem, ParamCommune param) {
		List<OpSubSummary> subSummary = new ArrayList<OpSubSummary>();

		String where = "";
		String table2 = "";
		if(table.equals(OpCommon.OP_LOAN))
		{
			where = "OP_LOAN_ID";
			table2 = "OP_LOAN";
		} else {
			where = "OP_SAVING_ID";
			table2 = "OP_SAVING";
		}
		
		try {
			String sql = "SELECT C.FULL_NAME, " +
						 "		 SUM(L.Y0M_CLI + L.Y0D_CLI) AS Y0_CLI, " +
						 "		 SUM(L.Y0M_AMT + L.Y0D_AMT) AS Y0_AMT, " +
						 "		 SUM(L.M01_CLI + L.M02_CLI + L.M03_CLI + L.M04_CLI + L.M05_CLI + L.M06_CLI + " + 
						 "		 	 L.M07_CLI + L.M08_CLI + L.M09_CLI + L.M10_CLI + L.M11_CLI + L.M12_CLI) Y1_CLI, " +
						 "		 SUM(L.M01_AMT + L.M02_AMT + L.M03_AMT + L.M04_AMT + L.M05_AMT + L.M06_AMT +  " +
						 "		 	 L.M07_AMT + L.M08_AMT + L.M09_AMT + L.M10_AMT + L.M11_AMT + L.M12_AMT) Y1_AMT, " +    
						 "		 SUM(L.Y1D_CLI) AS Y2_CLI, " +
						 "		 SUM(L.Y1D_AMT) AS Y2_AMT, " +
						 "		 SUM(L.Y2D_CLI) AS Y3_CLI, " +
						 "		 SUM(L.Y2D_AMT) AS Y3_AMT " +
						 "FROM " + table + " L " +
						 "INNER JOIN " + table2 + " OM ON L." + where + " = OM.ID " +
						 "INNER JOIN OP_PLAN_CO OP ON OM.OP_PLAN_CO_ID = OP.ID " +
						 "INNER JOIN (SELECT * FROM " +
							"(SELECT 0 AS ID, c.SHORT_NAME, c.LOCAL_BRANCH AS branch_code, c.FULL_NAME AS full_name " +
							"FROM STTM_CUSTOMER c " +
							"WHERE c.CUSTOMER_CATEGORY = '01' " +
							"UNION " +
							"SELECT co.ID, co.SHORT_NAME, co.BRANCH_CODE, co.SHORT_NAME AS full_name FROM OP_DUMMY_CO co)) C ON C.SHORT_NAME =  OP.CO_SNAME " +
						 "WHERE OP.PLAN_ID = :PLAN_ID ";
			
			if(!param.getBranch().getCode().equals("000")) {
				if(brItem.getType().equals("B")) {
					if(!param.getSubBranch().getCode().equals("")) {
						sql += "AND OP.BRANCH_CODE = :BRCD ";
					} else {
						sql += "AND OP.BRANCH_CODE IN (" + Common.arrayQuery(LoanLateFacade.getSubBranchCodeList(brItem.getId()).toArray()) + ") ";
					}
				}
				
				if(brItem.getType().equals("S")) {
					sql += "AND OP.BRANCH_CODE = :BRCD ";
				}
			}
			
			if(!param.getFilter().trim().equals("")) {
				sql += "AND C.FULL_NAME LIKE :FILTER ";
			}
			
			sql +=   "GROUP BY C.FULL_NAME " +
				     "ORDER BY C.FULL_NAME";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!brItem.getCode().equals("000")) {
					if(brItem.getType().equals("B")) {
						if(!param.getSubBranch().getCode().equals("")) {
							query.addParameter("BRCD", param.getSubBranch().getCode());
						} 
					}
					
					if(brItem.getType().equals("S")) {
						query.addParameter("BRCD", brItem.getCode());
					}
				}
				
				if(!param.getFilter().trim().equals("")) {
					query.addParameter("FILTER", Common.addLikeExpression(param.getFilter()));
				}
				
				query.addParameter("PLAN_ID", master_id);
				
				subSummary = query.executeAndFetch(OpSubSummary.class);
			}
			
			if (subSummary == null) {
				subSummary = new ArrayList<OpSubSummary>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Sub Summary List [" + table + "], MASTER_ID: " + master_id, e);
		}
		return subSummary;
	}
	
	public static List<OpSubSummary> getSubSummaryListByProduct(Integer master_id, String table, Branch brItem, ParamCommune param) {
		List<OpSubSummary> subSummary = new ArrayList<OpSubSummary>();

		String where = "";
		String table2 = "";
		String table3 = "";
		String select = "";
		String join = "";
		
		if(table.equals(OpCommon.OP_LOAN))
		{
			where = "OP_LOAN_ID";
			table2 = "OP_LOAN";
			table3 = "CLTM_PRODUCT";
			select = "PRODUCT_DESC";
			join = "PRODUCT_CODE";
		} else {
			where = "OP_SAVING_ID";
			table2 = "OP_SAVING";
			table3 = "STTM_ACCOUNT_CLASS";
			select = "DESCRIPTION";
			join = "ACCOUNT_CLASS";
		}
		
		try {
			String sql = "SELECT P." +  select + " AS DESCRIPTION, " +
					 	 "		 P." + join + " AS PRODUCT_CODE, " +
						 "		 SUM(L.Y0M_CLI + L.Y0D_CLI) AS Y0_CLI, " +
						 "		 SUM(L.Y0M_AMT + L.Y0D_AMT) AS Y0_AMT, " +
						 "		 SUM(L.M01_CLI + L.M02_CLI + L.M03_CLI + L.M04_CLI + L.M05_CLI + L.M06_CLI + " + 
						 "		 	 L.M07_CLI + L.M08_CLI + L.M09_CLI + L.M10_CLI + L.M11_CLI + L.M12_CLI) Y1_CLI, " +
						 "		 SUM(L.M01_AMT + L.M02_AMT + L.M03_AMT + L.M04_AMT + L.M05_AMT + L.M06_AMT +  " +
						 "		 	 L.M07_AMT + L.M08_AMT + L.M09_AMT + L.M10_AMT + L.M11_AMT + L.M12_AMT) Y1_AMT, " +    
						 "		 SUM(L.Y1D_CLI) AS Y2_CLI, " +
						 "		 SUM(L.Y1D_AMT) AS Y2_AMT, " +
						 "		 SUM(L.Y2D_CLI) AS Y3_CLI, " +
						 "		 SUM(L.Y2D_AMT) AS Y3_AMT " +
						 "FROM " + table + " L " +
						 "INNER JOIN " + table2 + " OM ON L." + where + " = OM.ID " +
						 "INNER JOIN OP_PLAN_CO OP ON OM.OP_PLAN_CO_ID = OP.ID " +
						 "INNER JOIN " + table3 + " P ON L.PRODUCT_CODE = P." + join + " " +
						 "WHERE OP.PLAN_ID = :PLAN_ID ";
			
			if(!brItem.getCode().equals("000")) {
				if(brItem.getType().equals("B")) {
					if(!param.getSubBranch().getCode().equals("")) {
						sql += "AND OP.BRANCH_CODE = :BRCD ";
					} else {
						sql += "AND OP.BRANCH_CODE IN (" + Common.arrayQuery(LoanLateFacade.getSubBranchCodeList(brItem.getId()).toArray()) + ") ";
					}					
				}
				
				if(brItem.getType().equals("S")) {
					sql += "AND OP.BRANCH_CODE = :BRCD ";
				}
			}
			
			sql +=       "GROUP BY P." +  select + ", P." + join + " " +
						 "ORDER BY P." +  select;
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!brItem.getCode().equals("000")) {
					if(brItem.getType().equals("B")) {
						if(!param.getSubBranch().getCode().equals("")) {
							query.addParameter("BRCD", param.getSubBranch().getCode());
						} 
					}					
					
					if(brItem.getType().equals("S")) {
						query.addParameter("BRCD", brItem.getCode());
					}
				}
				
				query.addParameter("PLAN_ID", master_id);
				
				subSummary = query.executeAndFetch(OpSubSummary.class);
			}
			
			if (subSummary == null) {
				subSummary = new ArrayList<OpSubSummary>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Sub Summary List [" + table + "], MASTER_ID: " + master_id, e);
		}
		return subSummary;
	}
	
	public static List<OpSubSummary> getAllSubSummaryList(Integer master_id, String table, ParamBranches param) {
		List<OpSubSummary> subSummary = new ArrayList<OpSubSummary>();

		String where = "";
		String table2 = "";
		if(table.equals(OpCommon.OP_LOAN))
		{
			where = "OP_LOAN_ID";
			table2 = "OP_LOAN";
		} else {
			where = "OP_SAVING_ID";
			table2 = "OP_SAVING";
		}		
		
		try {
			String sql = "SELECT SB.NAME_EN, " +
						 "		 SB.BRANCH_CODE, " +
						 "		 SUM(L.Y0M_CLI + L.Y0D_CLI) AS Y0_CLI, " +
						 "		 SUM(L.Y0M_AMT + L.Y0D_AMT) AS Y0_AMT, " +
						 "		 SUM(L.M01_CLI + L.M02_CLI + L.M03_CLI + L.M04_CLI + L.M05_CLI + L.M06_CLI + " + 
						 "		 	 L.M07_CLI + L.M08_CLI + L.M09_CLI + L.M10_CLI + L.M11_CLI + L.M12_CLI) Y1_CLI, " +
						 "		 SUM(L.M01_AMT + L.M02_AMT + L.M03_AMT + L.M04_AMT + L.M05_AMT + L.M06_AMT +  " +
						 "		 	 L.M07_AMT + L.M08_AMT + L.M09_AMT + L.M10_AMT + L.M11_AMT + L.M12_AMT) Y1_AMT, " +    
						 "		 SUM(L.Y1D_CLI) AS Y2_CLI, " +
						 "		 SUM(L.Y1D_AMT) AS Y2_AMT, " +
						 "		 SUM(L.Y2D_CLI) AS Y3_CLI, " +
						 "		 SUM(L.Y2D_AMT) AS Y3_AMT " +
						 "FROM " + table + " L " +
						 "INNER JOIN " + table2 + " OM ON L." + where + " = OM.ID " +
						 "INNER JOIN OP_PLAN_CO OP ON OM.OP_PLAN_CO_ID = OP.ID " +
						 "INNER JOIN SYS_BRANCH SB ON OP.BRANCH_CODE = SB.BRANCH_CODE " +
						 "WHERE OP.PLAN_ID = :PLAN_ID ";
			
			if(!(param.getBranch().getCode().equals("")) && param.getSubBranch().getCode().equals(""))
			{
				if(param.getBranch().getCode().equals("000"))
				{
					sql += "AND OP.BRANCH_CODE = :BR_CODE ";
				}
				else
				{
					sql += "AND OP.BRANCH_CODE IN (" + Common.arrayQuery(LoanLateFacade.getSubBranchCodeList(param.getBranch().getId()).toArray()) + ") ";
				}
			} else if(!(param.getSubBranch().getCode().equals(""))){
				sql += "AND OP.BRANCH_CODE = :SB_CODE ";
			}
						 
				sql +=   "AND SB.TYPE = 'S' " +
						 "GROUP BY SB.NAME_EN, SB.BRANCH_CODE " +
						 "ORDER BY SB.BRANCH_CODE";
				
			System.out.println(sql);
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!(param.getBranch().getCode().equals("")) && param.getSubBranch().getCode().equals(""))
				{
					if(param.getBranch().getCode().equals("000"))
					{
						query.addParameter("BR_CODE", param.getBranch().getCode());
					}
				} else  if(!(param.getSubBranch().getCode().equals(""))){
					query.addParameter("SB_CODE", param.getSubBranch().getCode());
				}
				
				query.addParameter("PLAN_ID", master_id);
				
				subSummary = query.executeAndFetch(OpSubSummary.class);
			}
			
			if (subSummary == null) {
				subSummary = new ArrayList<OpSubSummary>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting All Sub Summary List [" + table + "], MASTER_ID: " + master_id, e);
		}
		return subSummary;
	}
	
	public static List<OpSubSummary> getAllSubSummaryListByProduct(Integer master_id, String table, ParamBranches param) {
		List<OpSubSummary> subSummary = new ArrayList<OpSubSummary>();

		String where = "";
		String table2 = "";
		String table3 = "";
		String select = "";
		String join = "";
		
		if(table.equals(OpCommon.OP_LOAN))
		{
			where = "OP_LOAN_ID";
			table2 = "OP_LOAN";
			table3 = "CLTM_PRODUCT";
			select = "PRODUCT_DESC";
			join = "PRODUCT_CODE";
		} else {
			where = "OP_SAVING_ID";
			table2 = "OP_SAVING";
			table3 = "STTM_ACCOUNT_CLASS";
			select = "DESCRIPTION";
			join = "ACCOUNT_CLASS";
		}		
		
		try {
			String sql = "SELECT P." +  select + " AS DESCRIPTION, " +
						 "		 P." + join + " AS PRODUCT_CODE, " +
						 "		 SUM(L.Y0M_CLI + L.Y0D_CLI) AS Y0_CLI, " +
						 "		 SUM(L.Y0M_AMT + L.Y0D_AMT) AS Y0_AMT, " +
						 "		 SUM(L.M01_CLI + L.M02_CLI + L.M03_CLI + L.M04_CLI + L.M05_CLI + L.M06_CLI + " + 
						 "		 	 L.M07_CLI + L.M08_CLI + L.M09_CLI + L.M10_CLI + L.M11_CLI + L.M12_CLI) Y1_CLI, " +
						 "		 SUM(L.M01_AMT + L.M02_AMT + L.M03_AMT + L.M04_AMT + L.M05_AMT + L.M06_AMT +  " +
						 "		 	 L.M07_AMT + L.M08_AMT + L.M09_AMT + L.M10_AMT + L.M11_AMT + L.M12_AMT) Y1_AMT, " +    
						 "		 SUM(L.Y1D_CLI) AS Y2_CLI, " +
						 "		 SUM(L.Y1D_AMT) AS Y2_AMT, " +
						 "		 SUM(L.Y2D_CLI) AS Y3_CLI, " +
						 "		 SUM(L.Y2D_AMT) AS Y3_AMT " +
						 "FROM " + table + " L " +
						 "INNER JOIN " + table2 + " OM ON L." + where + " = OM.ID " +
						 "INNER JOIN OP_PLAN_CO OP ON OM.OP_PLAN_CO_ID = OP.ID " +
						 "INNER JOIN " + table3 + " P ON L.PRODUCT_CODE = P." + join + " " +
						 "WHERE OP.PLAN_ID = :PLAN_ID ";
			
			if(!(param.getBranch().getCode().equals("")) && param.getSubBranch().getCode().equals(""))
			{
				if(param.getBranch().getCode().equals("000"))
				{
					sql += "AND OP.BRANCH_CODE = :BR_CODE ";
				}
				else
				{
					sql += "AND OP.BRANCH_CODE IN (" + Common.arrayQuery(LoanLateFacade.getSubBranchCodeList(param.getBranch().getId()).toArray()) + ") ";
				}
			} else if(!(param.getSubBranch().getCode().equals(""))){
				sql += "AND OP.BRANCH_CODE = :SB_CODE ";
			}
						 
				sql +=   "GROUP BY P." +  select + ", P." + join + " " +
						 "ORDER BY P." +  select;
				
			System.out.println(sql);
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!(param.getBranch().getCode().equals("")) && param.getSubBranch().getCode().equals(""))
				{
					if(param.getBranch().getCode().equals("000"))
					{
						query.addParameter("BR_CODE", param.getBranch().getCode());
					}
				} else  if(!(param.getSubBranch().getCode().equals(""))){
					query.addParameter("SB_CODE", param.getSubBranch().getCode());
				}
				
				query.addParameter("PLAN_ID", master_id);
				
				subSummary = query.executeAndFetch(OpSubSummary.class);
			}
			
			if (subSummary == null) {
				subSummary = new ArrayList<OpSubSummary>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting All Sub Summary List [" + table + "], MASTER_ID: " + master_id, e);
		}
		return subSummary;
	}
	
	public static List<OpPlanCo> getPlanCoList(ParamCommune param) {
		List<OpPlanCo> planCos = new ArrayList<OpPlanCo>();
		
		Map<String, String> rolesMap = SecurityFacade.getRoleCode(
				UserCredentialManager.getIntance().getLoginUsr().getId());
		
		Branch brItem = new Branch();
		String brCd = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
		
		if(rolesMap.containsValue("adm") || rolesMap.containsValue("hq") || brCd.equalsIgnoreCase("000"))
		{
			brItem.setId(0);
			brItem.setCode("000");
			brItem.setType("A");
		}
		
		brItem = LoanLateFacade.getBrItem(brCd);
		
		try {
			Query<OpPlanCo> query = Ebean.find(OpPlanCo.class);
			
			if(!brItem.getCode().equals("000")) {
				if(brItem.getType().equals("B")) {
					if(!param.getSubBranch().getCode().equals("")) {
						query.where().eq("branchCode", param.getSubBranch().getCode());
					} else {
						query.where()
						.in("branchCode", LoanLateFacade.getSubBranchCodeList(brItem.getId()));
					}
					
				}
				
				if(brItem.getType().equals("S")) {
					query.where().eq("branchCode", brCd);
				}
			}
			
			if(!param.getFilter().trim().equals("")) {
				query.where()
				.ilike("co_sname", Common.addLikeExpression(param.getFilter()));
			}
			
			planCos = query.orderBy("co_sname").findList();
			
			if (planCos == null) {
				planCos = new ArrayList<OpPlanCo>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Ebean error while getting PlanCo List.", e);
		}
		return planCos;
	}
	
	public static List<CodeItem> getPositionList() {
		List<CodeItem> product = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 1 id, CODE code, DESC_EN description FROM SYS_LOV WHERE TYPE = 'OP_STAFF_CODE' ORDER BY id, description";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				product = con.createQuery(sql)
					.executeAndFetch(CodeItem.class);
			}
			
			if (product == null) {
				product = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Position List.", e);
		}
		return product;
	}
	
	public static List<OpStaff> getStaffList() {
		List<OpStaff> staffList = new ArrayList<OpStaff>();
		
		Map<String, String> rolesMap = SecurityFacade.getRoleCode(
				UserCredentialManager.getIntance().getLoginUsr().getId());
		
		Branch brItem = new Branch();
		String brCd = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
		
		if(rolesMap.containsValue("adm") || rolesMap.containsValue("hq") || brCd.equalsIgnoreCase("000"))
		{
			brItem.setId(0);
			brItem.setCode("000");
			brItem.setType("A");
		}
		
		brItem = LoanLateFacade.getBrItem(brCd);
		
		try {
			Query<OpStaff> query = Ebean.find(OpStaff.class);
			
			if(!brItem.getCode().equals("000")) {
				if(brItem.getType().equals("B")) {
					query.where()
					.in("branch_code", LoanLateFacade.getSubBranchCodeList(brItem.getId()));
				}
				
				if(brItem.getType().equals("S")) {
					query.where().eq("branch_code", brCd);
				}
			}
			
			staffList = query.orderBy("branch_code, staffDesc").findList();
			
			if (staffList == null) {
				staffList = new ArrayList<OpStaff>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Ebean error while getting Staff List.", e);
		}
		return staffList;
	}
	
	public static List<OpStaff> getStaffList(ParamCommune param) {
		List<OpStaff> staffList = new ArrayList<OpStaff>();
		
		Map<String, String> rolesMap = SecurityFacade.getRoleCode(
				UserCredentialManager.getIntance().getLoginUsr().getId());
		
		Branch brItem = new Branch();
		String brCd = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
		
		if(rolesMap.containsValue("adm") || rolesMap.containsValue("hq") || brCd.equalsIgnoreCase("000"))
		{
			brItem.setId(0);
			brItem.setCode("000");
			brItem.setType("A");
		}
		
		brItem = LoanLateFacade.getBrItem(brCd);
		
		try {
			Query<OpStaff> query = Ebean.find(OpStaff.class);
			
			if(!brItem.getCode().equals("000")) {
				if(brItem.getType().equals("B")) {
					if(!param.getSubBranch().getCode().equals("")) {
						query.where().eq("branch_code", param.getSubBranch().getCode());
					} else {
						query.where()
						.in("branch_code", LoanLateFacade.getSubBranchCodeList(brItem.getId()));
					}
					
				}
				
				if(brItem.getType().equals("S")) {
					query.where().eq("branch_code", brCd);
				}
			}
			
			staffList = query.orderBy("branch_code, staffDesc").findList();
			
			if (staffList == null) {
				staffList = new ArrayList<OpStaff>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Ebean error while getting Staff List.", e);
		}
		return staffList;
	}
	
	public static CodeItem getPosition(String code, String type) {
		CodeItem position = new CodeItem();
		try {
			
			String sql = "SELECT CODE AS CODE, DESC_EN AS DESCRIPTION FROM SYS_LOV WHERE CODE = :code AND TYPE = :type";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				position = con.createQuery(sql)
							.addParameter("code", code)
							.addParameter("type", type)
							.executeAndFetchFirst(CodeItem.class);
			}
		} catch (Exception e) {
			logger.error("Operation Plan - Sql2o error while getting Position.", e);
		}
		return position;
	}
	
	public static List<OpStaffSummary> getStaffSummaryList(Integer master_id, Branch brItem, ParamCommune param) {
		List<OpStaffSummary> staffSummary = new ArrayList<OpStaffSummary>();
		
		try {
			String sql = "SELECT S.STAFF_CODE, " +
						 "SUM(S.NY0) AS NY0, " +
						 "SUM(S.NY1Q1) AS NY1Q1, " +
						 "SUM(S.NY1Q2) AS NY1Q2, " +
						 "SUM(S.NY1Q3) AS NY1Q3, " +
						 "SUM(S.NY1Q4) AS NY1Q4, " +
						 "SUM((S.NY1Q1 + S.NY1Q2 + " +
						 "  S.NY1Q3 + S.NY1Q4)) AS SY1, " +
						 "SUM(S.NY2) AS NY2, " +
						 "SUM((S.NY1Q1 + S.NY1Q2 + " + 
						 "  S.NY1Q3 + S.NY1Q4 + " + 
						 "  S.NY2)) AS SY2, " +
						 "SUM(S.NY3) AS NY3, " +
						 "SUM((S.NY1Q1 + S.NY1Q2 + " +
						 "  S.NY1Q3 + S.NY1Q4 + " + 
						 "  S.NY2 + S.NY3)) AS SY3 " +
						 "FROM OP_STAFF S " +
						 "WHERE S.PLAN_ID = :PLAN_ID ";
			
			if(!param.getBranch().getCode().equals("000")) {
				if(brItem.getType().equals("B")) {
					if(!param.getSubBranch().getCode().equals("")) {
						sql += "AND S.BRANCH_CODE = :BRCD ";
					} else {
						sql += "AND S.BRANCH_CODE IN (" + Common.arrayQuery(LoanLateFacade.getSubBranchCodeList(brItem.getId()).toArray()) + ") ";
					}
				}
				
				if(brItem.getType().equals("S")) {
					sql += "AND S.BRANCH_CODE = :BRCD ";
				}
			}
			
			sql +=   "GROUP BY S.STAFF_CODE " +
					 "ORDER BY S.STAFF_CODE";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!brItem.getCode().equals("000")) {
					if(brItem.getType().equals("B")) {
						if(!param.getSubBranch().getCode().equals("")) {
							query.addParameter("BRCD", param.getSubBranch().getCode());
						} 
					}
					
					if(brItem.getType().equals("S")) {
						query.addParameter("BRCD", brItem.getCode());
					}
				}
				
				query.addParameter("PLAN_ID", master_id);
				
				staffSummary = query.executeAndFetch(OpStaffSummary.class);
			}
			
			if (staffSummary == null) {
				staffSummary = new ArrayList<OpStaffSummary>();
			}
		} catch (Exception e) {
			logger.error(
					"Operation Plan - Sql2o error while getting Staff Summary List, PLAN_ID: " + master_id, e);
		}
		return staffSummary;
	}
	
	public static List<CodeItem> getSubBranchesListNoAll() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();

		try {
			String sql = " SELECT sb.id id, sb.branch_code code, sb.name_en description FROM SYS_BRANCH sb" +
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
					"Sql2o error while getting Sub Branch List.", e);
		}
		return branchs;
	}
	
	public static CodeItem getSubBranch(String code) {
		CodeItem branch = null;
		try {
			String sql = "SELECT id id, branch_code code, name_en description " +
						 "FROM SYS_BRANCH " +
						 "WHERE TYPE = 'S' AND branch_code = :code";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				branch = con.createQuery(sql).addParameter("code", code)
						.executeAndFetchFirst(CodeItem.class);
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Sub Branch: code = " + code, e);
		}
		return branch;
	}
}
