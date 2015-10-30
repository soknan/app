package kredit.web.writeOff.model.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

import kredit.web.core.util.Common;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.core.util.model.ScalarDouble;
import kredit.web.core.util.model.ScalarString;
import kredit.web.writeOff.model.Co;
import kredit.web.writeOff.model.Loan;
import kredit.web.writeOff.model.ParamAcc;
import kredit.web.writeOff.model.ParamCo;
import kredit.web.writeOff.model.ParamLoan;
import kredit.web.writeOff.model.Repayment;
import kredit.web.writeOff.model.TrnCo;

public class WriteOffFacade {
	
	private static Logger logger = Logger.getLogger(WriteOffFacade.class);
	
	public static List<Loan> getLoansList(ParamLoan param) {
		List<Loan> loans = new ArrayList<Loan>();

		try {
			Query<Loan> query = Ebean.find(Loan.class)
					.select("cif, nameEn, brCd, accountNo, prodCode, woffDate, totWoff, ccy, accuLnRec, netLoss, category");
			
			if(!(param.getFilter() == null || param.getFilter().trim().equals("")))
			{
				query.where()
				.disjunction()
				.ilike("cif", Common.addLikeExpression(param.getFilter()))
				.ilike("nameEn", Common.addLikeExpression(param.getFilter()))
				.ilike("accountNo", Common.addLikeExpression(param.getFilter()))
				.ilike("prodCode", Common.addLikeExpression(param.getFilter()))
				.endJunction();
			}
			
			if(!(param.getBranch().getCode().equals("")))
			{
				if(param.getBranch().getCode().equals("000"))
				{
					query.where()
					.in("brCd", param.getBranch().getCode());
				}
				else
				{
					query.where()
					.in("brCd", getSubBranchCodeList(param.getBranch().getId()));
				}
			}
			
			if(!(param.getSubBranch().getCode().equals("")))
			{
				query.where()
				.eq("brCd", param.getSubBranch().getCode());
			}
			
			if(!(param.getCurrency().getCode().equals("") || param.getCurrency().getCode().equals("All")))
			{
				query.where()
				.eq("ccy", param.getCurrency().getCode());
			}
			
			if(!(param.getStDate() == null))
			{
				query.where()
				.ge("woffDate", param.getStDate());
			}
			
			if(!(param.getEnDate() == null))
			{
				query.where()
				.le("woffDate", param.getEnDate());
			}
			
			if(!(param.getStAmount() == null))
			{
				query.where()
				.ge("totWoff", param.getStAmount());
			}
			
			if(!(param.getEnAmount() == null))
			{
				query.where()
				.le("totWoff", param.getEnAmount());
			}
			
			if(!(param.getCategory().getDescription().equals("All")))
			{
				query.where()
				.eq("prodCat", param.getCategory().getDescription());
			}
			
			if(!(param.getProduct().getCode().trim().equals("")))
			{
				query.where()
				.eq("prodCode", param.getProduct().getCode());
			}
			
			if(!(param.getwCategory().getCode().equals("") || param.getwCategory().getCode().equals("All")))
			{
				query.where()
				.eq("category", param.getwCategory().getDescription());
			}
			
			loans = query.orderBy("woffDate").findList();
			
			if (loans == null) {
				loans = new ArrayList<Loan>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Ebean error while getting loans List.", e);
		}
		return loans;
	}

	public static List<Repayment> getRepayments(Integer id) {
		List<Repayment> repayments = new ArrayList<Repayment>();

		try {
			
			if(id == null || id == 0)
			{
				return repayments;
			}
			else
			{
			repayments = Ebean.find(Repayment.class)
						.where()
						.eq("WOF_ID", id)
						.findList();
			}
			
		} catch (Exception e) {
			logger.error(
					"Ebean error while getting repayments List.", e);
		}
		return repayments;
	}
	
	public static List<Co> getCoList(ParamCo param) {
		List<Co> cos = new ArrayList<Co>();

		try {
			
			Query<Co> query = Ebean.find(Co.class)
					.select("fullName, shortName, branchCode, shortName2");
			
			//CO is '01'
			query.where()
			.eq("CUSTOMER_CATEGORY", "01");
			
			if(!(param.getFilter() == null || param.getFilter().trim().equals("")))
			{
				query.where()
				.disjunction()
				.ilike("fullName", Common.addLikeExpression(param.getFilter()))
				.ilike("shortName", Common.addLikeExpression(param.getFilter()))
				.ilike("shortName2", Common.addLikeExpression(param.getFilter()))
				.endJunction();
			}
			
			if(!(param.getBranch().getCode().equals("")))
			{
				query.where()
				.eq("branchCode", param.getBranch().getCode());
			}
			
			cos = query.findList();
			if (cos == null) {
				cos = new ArrayList<Co>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Ebean error while getting Co List.", e);
		}
		return cos;
	}
	
	public static String getBranchName(String code) {
		String result = "";
		try {
			String sql = "SELECT name_en value FROM SYS_BRANCH WHERE BRANCH_CODE = :code";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				ScalarString branch = con.createQuery(sql)
					.addParameter("code", code)
					.executeAndFetchFirst(ScalarString.class);
				
				result = branch.getValue();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Branch Name: code = "
					+ code, e);
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
	
	public static List<Loan> getAccountList(ParamAcc param) {
		List<Loan> accs = new ArrayList<Loan>();
		try {
			
			String sql = "SELECT AC.CUSTOMER_ID AS cif" +
						 ", C.CUSTOMER_NAME1 AS nameEn" +
						 ", LTRIM(RTRIM(F.FIELD_VAL_1))||''|| LTRIM(RTRIM(F.FIELD_VAL_2 )) AS nameKh" +
						 ", AC.ACCOUNT_NUMBER AS accountNo" +
						 ", AC.BRANCH_CODE AS brCd" +
						 ", SB.NAME_EN AS branchName" +
						 ", AC.FIELD_CHAR_1 AS coShortName" +
						 ", C1.CUSTOMER_NAME1 AS coFullName" +
						 ", AC.AMOUNT_DISBURSED AS amtDisbursed" +
						 ", AC.CURRENCY AS ccy" +
						 ", AC.PRODUCT_CODE AS prodCode" +
						 ", AC.PRODUCT_CATEGORY AS prodCat" +
						 ", NVL((SELECT DISTINCT EV.VALUE_DATE FROM CLTB_EVENT_ENTRIES EV" + 
						 " WHERE EV.DR_ACC='671109111' AND EV.ACCOUNT_NUMBER=AC.ACCOUNT_NUMBER" + 
						 " AND EV.COMPONENT_NAME='PRINCIPAL' AND EV.COMPONENT_NAME<>'PROV')" +
						 ", (SELECT DISTINCT EV.VALUE_DATE FROM CLTB_EVENT_ENTRIES EV WHERE" + 
						 " EV.DR_ACC='671109111' AND EV.ACCOUNT_NUMBER=AC.ACCOUNT_NUMBER" + 
						 " AND EV.COMPONENT_NAME='MAIN_INT' AND EV.COMPONENT_NAME<>'PROV')) AS woffDate" +
						 " FROM CLTB_ACCOUNT_MASTER AC INNER JOIN STTM_CUSTOMER C ON AC.CUSTOMER_ID=C.CUSTOMER_NO" +
						 " INNER JOIN SYS_BRANCH SB ON AC.BRANCH_CODE = SB.BRANCH_CODE" +
						 " INNER JOIN STTM_CUSTOMER C1 ON AC.FIELD_CHAR_1 = C1.SHORT_NAME" +
						 " LEFT OUTER JOIN CSTM_FUNCTION_USERDEF_FIELDS F ON AC.CUSTOMER_ID=SUBSTR(F.REC_KEY,1,9)" + 
						 " AND F.FUNCTION_ID='STDCIF'" +
						 " WHERE C1.CUSTOMER_CATEGORY='01' AND AC.ACCOUNT_STATUS='L' AND AC.ACCOUNT_NUMBER" + 
						 " IN(SELECT DISTINCT EV.ACCOUNT_NUMBER FROM CLTB_EVENT_ENTRIES EV" + 
						 " WHERE EV.DR_ACC='671109111' AND EV.COMPONENT_NAME<>'PROV') AND AC.ACCOUNT_NUMBER NOT IN" +
						 " (SELECT ACCOUNT_NO FROM WOF_LOAN)";
			
			if(!(param.getName() == null || param.getName().trim().equals("")))
			{
				sql += " AND LTRIM(RTRIM(F.FIELD_VAL_1)) || '' || LTRIM(RTRIM(F.FIELD_VAL_2 )) LIKE % :name %";
			}
			
			if(!(param.getAccount() == null || param.getAccount().trim().equals("")))
			{
				sql += " AND AC.ACCOUNT_NUMBER LIKE % :acc %";
			}
			
			if(!(param.getName() == null || param.getName().trim().equals("")))
			{
				sql += " AND AC.CUSTOMER_ID LIKE % :cif %";
			}
			
			if(!(param.getBranch().getCode().equals("") || param.getBranch().getCode().equals("All")))
			{
				sql += " AND AC.BRANCH_CODE = :branch";
			}
			
			if(!(param.getCurrency().getCode().equals("") || param.getCurrency().getCode().equals("All")))
			{
				sql += " AND AC.CURRENCY = :ccy";
			}
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				org.sql2o.Query query = con.createQuery(sql);
				
				if(!(param.getName() == null || param.getName().trim().equals("")))
				{
					query.addParameter("name", param.getName());
				}
				
				if(!(param.getAccount() == null || param.getAccount().trim().equals("")))
				{
					query.addParameter("acc", param.getAccount());
				}
				
				if(!(param.getName() == null || param.getName().trim().equals("")))
				{
					query.addParameter("cif", param.getCif());
				}
				
				if(!(param.getBranch().getCode().equals("") || param.getCurrency().getCode().equals("All")))
				{
					query.addParameter("branch", param.getBranch().getCode());
				}
				
				if(!(param.getCurrency().getCode().equals("") || param.getCurrency().getCode().equals("All")))
				{
					query.addParameter("ccy", param.getCurrency().getCode());
				}
				
				accs = query.executeAndFetch(Loan.class);
			}
			
			if (accs == null) {
				accs = new ArrayList<Loan>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Ebean error while getting Accounts List.", e);
		}
		return accs;
	}
	
	public static double getAmountByCateogry(Date woffDate, String accNo, String category) {
		Double amount = 0.0;

		try {
			String sql = "SELECT NVL(SUM(A.AMOUNT_SETTLED),0) AS value" +                   
						 " FROM cltb_event_entries A" +
						 " WHERE A.VALUE_DATE = :woffDate" +
						 " AND A.Event_Code IN ('ALIQ','MLIQ','REVP','REVC')" +
						 " AND A.STTL_ACC = '671109111'" +            
						 " AND A.ACCOUNT_NUMBER = :accNo" +                                        
						 " AND COMPONENT_NAME = :category";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				 ScalarDouble scalar = con.createQuery(sql)
					.addParameter("woffDate", woffDate)
					.addParameter("accNo", accNo)
					.addParameter("category", category)
					.executeAndFetchFirst(ScalarDouble.class);
				
				 amount = scalar.getValue();
			}
			
			if(amount == null)
				amount = 0.0;
			
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Amount for: " + woffDate + "|" + accNo + "|" + category, e);
		}
		return amount;
	}
	
	public static List<CodeItem> getProductCategories() {
		List<CodeItem> categories = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 'All' description FROM DUAL" +
						" UNION ALL " +
						"SELECT DISTINCT PRODUCT_CATEGORY description FROM CLTM_PRODUCT ORDER BY description";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				categories = con.createQuery(sql)
					.executeAndFetch(CodeItem.class);
			}
			
			if (categories == null) {
				categories = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Category List.", e);
		}
		return categories;
	}
	
	public static List<CodeItem> getProductList() {
		List<CodeItem> product = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 0 id, ' ' code, 'All' description FROM DUAL" +
						" UNION ALL " + 
						"SELECT 0 id, PRODUCT_CODE code, PRODUCT_DESC description FROM CLTM_PRODUCT ORDER BY description";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				product = con.createQuery(sql)
					.executeAndFetch(CodeItem.class);
			}
			
			if (product == null) {
				product = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Product List.", e);
		}
		return product;
	}
	
	public static String getProductDescription(String code) {
		String result = "";
		try {
			String sql = "SELECT PRODUCT_DESC value FROM CLTM_PRODUCT WHERE PRODUCT_CODE = :code";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				ScalarString branch = con.createQuery(sql)
					.addParameter("code", code)
					.executeAndFetchFirst(ScalarString.class);
				
				result = branch.getValue();
			}

		} catch (Exception e) {
			logger.error("Sql2o error while getting Product Description: code = "
					+ code, e);
		}
		return result;
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
	
	public static List<CodeItem> getWofCategoryList() {
		List<CodeItem> cates = new ArrayList<CodeItem>();

		try {
			String sql = "SELECT 0 id, ' ' code, 'All' description FROM DUAL" +
					" UNION ALL " +
					"SELECT id, code, desc_en description FROM SYS_LOV WHERE TYPE = 'WOF_CATEGORY' AND STATUS = 'A' ORDER BY description";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				cates = con.createQuery(sql)
					.executeAndFetch(CodeItem.class);
			}
			
			if (cates == null) {
				cates = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Loan Write-Off Category List.", e);
		}
		return cates;
	}
	
	public static CodeItem getBranch(String code) {
		CodeItem branch = null;
		try {
			String sql = "SELECT sb.id id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription FROM SYS_BRANCH sb" +
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
	
	public static List<TrnCo> getTrnCos(Integer id) {
		List<TrnCo> trnCos = new ArrayList<TrnCo>();

		try {
			
			if(id == null || id == 0)
			{
				return trnCos;
			}
			else
			{
				trnCos = Ebean.find(TrnCo.class)
						.where()
						.eq("WOF_ID", id)
						.findList();
			}
			
		} catch (Exception e) {
			logger.error(
					"Ebean error while getting transfer Co List.", e);
		}
		return trnCos;
	}
}
