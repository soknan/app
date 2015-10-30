package kredit.web.util.incentive.model.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import kredit.web.core.util.Common;
import kredit.web.core.util.db.OracleHelper;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.Scalar;
import kredit.web.util.incentive.model.IncentiveLoan;
import kredit.web.util.incentive.model.IncentiveLoanVSU;
import kredit.web.util.incentive.model.IncentiveSaving;
import kredit.web.util.incentive.model.ParamIncentive;
import kredit.web.util.incentive.utility.MsSqlHelperAuth;
import kredit.web.util.monthly.model.facade.MonthlyFacade;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;
import org.sql2o.Connection;

public class IncentiveFacade {
	
	private static Logger logger = Logger.getLogger(IncentiveFacade.class);
	
//Saving
	
	public static List<IncentiveSaving> getIncentiveSavingList(ParamIncentive param)
	{
		List<IncentiveSaving> lst = new ArrayList<IncentiveSaving>();
		
		String sql = "SELECT CrdtOfcr_ID, " +
				"CrdtOfcr_Name, " +
				"SUM(ACT_Account) AS ACT_Account, " +
				"SUM(OSND_Balance) AS OSND_Balance, " +
				"SUM(Account_Open_Monthly) AS Account_Open_Monthly, " +
				"SUM(Amount_Open_Monthly) AS Amount_Open_Monthly, " +
				"ACMT_Date, " +
				"Branch_Code " +
				"FROM (SELECT CASE SAV.CREDIT_OFFICER_NAME " +
				"	WHEN 'OFFICE' THEN " +
				"		'0' " +
				"	ELSE " +
				"		SAV.CREDIT_OFFICER_ID " +
				"	END AS CrdtOfcr_ID, " +
				"	CASE " +
				"		WHEN SAV.CREDIT_OFFICER_ID = '0' THEN " +
				"			'OFFICE' " +
				"		ELSE " +
				"			SAV.CREDIT_OFFICER_NAME " +
				"		END AS CrdtOfcr_Name, " +
				"	1 AS ACT_Account, " +
				"	SAV.LCY_BALANCE_AMOUNT AS OSND_Balance, " +
				"	CASE " +
				"		WHEN TRUNC(SAV.AC_OPEN_DATE) BETWEEN " +
				"			TRUNC(:P_DATE) AND " +
				"			TRUNC(LAST_DAY(:P_DATE)) AND " +
				"			TRUNC(SAV.REPORT_DATE) = TRUNC(:P_DATE) AND " +
				"			SAV.RECORD_STATUS = 'O' THEN 1 ELSE 0 " +
				"			END AS Account_Open_Monthly, " +
				"	CASE " +
				"		WHEN TRUNC(SAV.AC_OPEN_DATE) BETWEEN " +
				"			TRUNC(:P_DATE) AND " +
				"			TRUNC(LAST_DAY(:P_DATE)) AND " +
				"			TRUNC(SAV.REPORT_DATE) = TRUNC(:P_DATE) AND " +
				"			SAV.RECORD_STATUS = 'O' THEN " +
				"			CASE SAV.CURRENCY " +
				"				WHEN 'USD' THEN " +
				"					SAV.AVL_BAL " +
				"				ELSE " +
				"					SAV.AVL_BAL / SAV.EXCH_RATE " +
				"				END " +
				"		ELSE 0 " +
				"		END AS Amount_Open_Monthly, " +
				"	:P_DATE AS ACMT_Date, " +
				"	KRD_FUN_MAP_BRANCH(SAV.BRANCH_CODE) AS Branch_Code " +
				"	FROM INC_SAVING SAV " +
				"	WHERE SAV.RECORD_STATUS = 'O' ";
		
		if(!param.getSubBranch().getCode().equals(""))
		{
			sql += " AND CU.BRANCH_CODE = :BRANCH ";
		}
		
		sql +=  "	AND TRUNC(SAV.REPORT_DATE) = TRUNC(:P_DATE) " +
				"AND SAV.BRANCH_CODE IN ('000', '031', '072', '133')) SAVING " +
				"GROUP BY CrdtOfcr_ID, CrdtOfcr_Name, ACMT_Date, Branch_Code " +
				"ORDER BY 8, 2, 1, 4";
			
		try (Connection con = Sql2oHelper.sql2o.open()) {
			org.sql2o.Query query = con.createQuery(sql)
							.addParameter("P_DATE", MonthlyFacade.getEdofMonth(param.getFmonth().getId(), param.getFmonth().getCode()));
			
			if(!param.getSubBranch().getCode().equals(""))
			{
					query.addParameter("BRANCH", param.getSubBranch().getCode());
			}
				
			lst = query.executeAndFetch(IncentiveSaving.class);
				
			if (lst == null) {
				lst = new ArrayList<IncentiveSaving>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting IncentiveSaving List.", e);
		}
		
		return lst;
	}
	
	public static int saveIncentiveSaving(IncentiveSaving saving) {
		int updates = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			java.sql.Connection conn = MsSqlHelperAuth.getConnection(); //MsSqlHelper.getConnection();
			String sql = "INSERT INTO ACMT_Saving(CrdtOfcr_ID, CrdtOfcr_Name, ACT_Account, " +
						"OSND_Balance, Account_Open_Monthly, Amount_Open_Monthly, ACMT_Date, " +
						"Branch_Code, Batch_Code, Created_Date, Created_User, Note) " +
						"VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			
			updates = queryRunner.update(conn, sql, saving.getCrdtofcr_id(), saving.getCrdtofcr_name(),
					saving.getAct_account(), saving.getOsnd_balance(), saving.getAccount_open_monthly(),
					saving.getAmount_open_monthly(), saving.getAcmt_date(), saving.getBranch_code(), 
					saving.getBatch_code(), Common.getSqlDateString(saving.getCreated_date()), 
					saving.getCreated_user(), saving.getNote());
			
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("DBUtils error while saving IncentiveSaving.", e);
		}
		return updates;
	}
	
	//This function is for testing with MsSqlHelperAuth which works on Window Authentication
	public static int getIncentiveCount()
	{
		int result = 0;
		
		try {
			QueryRunner queryRunner = new QueryRunner();
			java.sql.Connection conn = MsSqlHelperAuth.getConnection();
			ResultSetHandler<Scalar> h = new BeanHandler<Scalar>(Scalar.class);
			String sql = "SELECT COUNT(*) As value FROM ACMT_SAVING;";

			Scalar scalare = queryRunner.query(conn, sql, h);

			result = scalare.getValue();
			DbUtils.close(conn);
		}
		catch(Exception e)
		{
			logger.error(
					"DBUtils error while getting IncentiveSaving. --Testing", e);
		}
		
		return result;
	}

//End Saving
	
//Loan
	
	public static List<IncentiveLoan> getIncentiveLoanList(ParamIncentive param)
	{
		List<IncentiveLoan> lst = new ArrayList<IncentiveLoan>();
		
		String sql = "SELECT LN_SUM.CREDIT_OFFICER_ID AS crdtofcr_id, " +
				"LN_SUM.CREDIT_OFFICER_NAME AS crdtofcr_name, " +
				"LN_SUM.CREDIT_OFFICER_CL AS crdtofcr_shortname, " +
				"SUM(NVL(LN_SUM.OSND_BALANCE,0)) AS osnd_balance, " +
				"SUM(NVL(LN_SUM.PAR30,0)) AS par30, " +
				"SUM(NVL(LN_SUM.ACT_CLIENT,0)) AS act_client, " +
				"SUM(NVL(LN_SUM.INT_COLLECTED,0)) AS int_collected, " + 
				"SUM(NVL(LN_SUM.NEW_CLIENT,0)) AS new_client, " +
				"SUM(NVL(LN_SUM.ONGOING_CLIENT,0)) AS ongoing_client, " +
				"SUM(NVL(LN_SUM.RURAL_CLIENT,0)) AS rural_client, " +
				"SUM(NVL(LN_SUM.FEMALE_CLIENT,0)) AS femal_client, " +
				"SUM(NVL(LN_SUM.FEMALE_CLIENT_VSU,0)) AS female_client_vsu, " +
				"SUM(NVL(LN_SUM.NEW_RURAL,0)) AS new_rural_client, " +
				"SUM(NVL(LN_SUM.NEW_ACC_DISB,0)) AS new_acc_disb, " +
				"SUM(NVL(LN_SUM.TOTAL_ACC_DISB,0)) AS total_acc_disb, " +
				"LN_SUM.BRANCH_CODE AS branch_code, " +
				":T_DATE AS acmt_date " + 
				"FROM (SELECT " +
				"	LOAN.CREDIT_OFFICER_ID, " +
				"	LOAN.CREDIT_OFFICER_NAME, " +
				"	LOAN.CREDIT_OFFICER_CL, " +
				"	CASE WHEN LOAN.CURRENCY='USD' THEN LOAN.PRINCIPAL_OUTSTANDING ELSE LOAN.PRINCIPAL_OUTSTANDING/LOAN.EXCH_RATE END AS OSND_BALANCE, " +
				"	CASE WHEN LOAN.LOAN_DAY_LATE > 30 THEN 1 ELSE 0 END AS PAR30, " +
				"	1 AS ACT_CLIENT, " +
				"	CASE WHEN LOAN.CURRENCY='USD' THEN LOAN.PRIN_AMT_SETTLED ELSE LOAN.PRIN_AMT_SETTLED/LOAN.EXCH_RATE END AS INT_COLLECTED, " +
				"	CASE WHEN LOAN.LOAN_CYCLE=1 THEN 1 ELSE 0 END AS NEW_CLIENT, " +
				"	CASE WHEN LOAN.LOAN_CYCLE >1 THEN 1 ELSE 0 END AS ONGOING_CLIENT, " +
				"	CASE WHEN (SELECT COUNT(*)CNT FROM STTM_CUST_ADD_MAIN_BFSI ADDR WHERE ADDR.AREA IN ('RURAL','SEMI URBAN') AND LOAN.VILLAGE=ADDR.VILLAGE )>0 THEN 1 ELSE 0 END AS RURAL_CLIENT, " +
				"	CASE WHEN LOAN.SEX ='F' THEN 1 ELSE 0 END AS FEMALE_CLIENT, " +
				"	CASE WHEN LOAN.SEX ='F' AND LOAN.PRODUCT_CODE IN('301','401')  THEN 1 ELSE 0 END AS FEMALE_CLIENT_VSU, " +
				"	CASE WHEN (SELECT COUNT(*)CNT FROM STTM_CUST_ADD_MAIN_BFSI ADDR WHERE ADDR.AREA IN ('RURAL','SEMI URBAN') AND LOAN.VILLAGE=ADDR.VILLAGE AND LOAN.LOAN_CYCLE=1)>0 THEN 1 ELSE 0 END AS NEW_RURAL, " +
				"	CASE WHEN TRUNC(LOAN.ACTUAL_DISBUR_DATE) BETWEEN TRUNC(:F_DATE) AND LAST_DAY(TRUNC(:T_DATE)) AND TRUNC(LOAN.REPORT_DATE) BETWEEN TRUNC(:F_DATE) AND LAST_DAY(TRUNC(:T_DATE)) AND LOAN.LOAN_CYCLE=1 THEN 1 ELSE 0 END AS NEW_ACC_DISB, " +
				"	CASE WHEN TRUNC(LOAN.ACTUAL_DISBUR_DATE) BETWEEN TRUNC(:F_DATE) AND LAST_DAY(TRUNC(:T_DATE)) AND TRUNC(LOAN.REPORT_DATE) BETWEEN TRUNC(:F_DATE) AND LAST_DAY(TRUNC(:T_DATE)) THEN 1 ELSE 0 END AS TOTAL_ACC_DISB, " +
				"	LOAN.BRANCH_CODE " +
				"	FROM INC_LOAN LOAN WHERE "; 
				
		if(!param.getSubBranch().getCode().equals(""))
		{
			sql += "LOAN.BRANCH_CODE = :BRANCH AND ";
		}
		
		sql +=	"TRUNC(LOAN.REPORT_DATE) BETWEEN TRUNC(:F_DATE) AND LAST_DAY(TRUNC(:T_DATE)) AND LOAN.ACCOUNT_STATUS ='A') LN_SUM " + 
				"GROUP BY LN_SUM.CREDIT_OFFICER_ID,LN_SUM.CREDIT_OFFICER_NAME ,LN_SUM.CREDIT_OFFICER_CL,LN_SUM.BRANCH_CODE " +
				"ORDER BY LN_SUM.BRANCH_CODE,LN_SUM.CREDIT_OFFICER_ID,LN_SUM.CREDIT_OFFICER_NAME"; 
			
		try (Connection con = Sql2oHelper.sql2o.open()) {
			org.sql2o.Query query = con.createQuery(sql)
							.addParameter("F_DATE", MonthlyFacade.getSdofMonth(param.getFmonth().getId(), param.getFmonth().getCode()))
							.addParameter("T_DATE", MonthlyFacade.getEdofMonth(param.getTmonth().getId(), param.getTmonth().getCode()));
			
			if(!param.getSubBranch().getCode().equals(""))
			{
					query.addParameter("BRANCH", param.getSubBranch().getCode());
			}
				
			lst = query.executeAndFetch(IncentiveLoan.class);
				
			if (lst == null) {
				lst = new ArrayList<IncentiveLoan>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting IncentiveLoan List.", e);
		}
		
		return lst;
	}

	public static List<IncentiveLoanVSU> getIncentiveLoanVSUList(ParamIncentive param)
	{
		List<IncentiveLoanVSU> lst = new ArrayList<IncentiveLoanVSU>();
		
		String sql = "SELECT CL.ACCOUNT_NUMBER, " +
				"CL.CUSTOMER_ID, " +
				"CL.BRANCH_CODE, " +
				"CL.PRODUCT_CODE, " +
				"P.PRODUCT_DESC, " +
				"C.CUSTOMER_NAME1 AS CUSTOMER_NAME, " +
				"LTRIM(RTRIM(CF.FIELD_VAL_1))||' '||LTRIM(RTRIM(CF.FIELD_VAL_2)) AS CUSTOMER_KHMER, " +
				"CASE WHEN LENGTH(REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,''))>4 Or LENGTH(REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,''))=0 THEN 'OFFICE' " +
				"	ELSE NVL(CL.FIELD_CHAR_1, 'OFFICE') END AS CO_SHORT_NAME, " +           
				"CASE WHEN LENGTH(REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,''))>4 Or LENGTH(REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,''))=0 OR REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,'')='00' THEN '0' " +
				"	ELSE NVL(REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,''), '0') END AS CO_ID, " +
				"CASE WHEN LENGTH(REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,''))>4 Or LENGTH(REGEXP_REPLACE(CL.FIELD_CHAR_1, '[^0-9]*' ,''))=0 THEN 'OFFICE' " +
				"	ELSE NVL(LTRIM(RTRIM(REPLACE(COD.CUSTOMER_NAME1,',',' '))), 'OFFICE') END AS CO_NAME, " +               
				"CL.VALUE_DATE AS Disbursed_Date, " +
				"CL.AMOUNT_DISBURSED, " +
				"CL.CURRENCY, " +
				"KRD_FUN_GET_EXCH_RATE(1,CL.CURRENCY,to_date(:T_DATE))EXCH_RATE, " +
				"CASE WHEN CL.CURRENCY ='USD' THEN CL.AMOUNT_DISBURSED ELSE ROUND(CL.AMOUNT_DISBURSED/KRD_FUN_GET_EXCH_RATE(1,CL.CURRENCY,to_date(:T_DATE)),4)END AS LCY_AMOUNT " +
				"FROM CLTB_ACCOUNT_MASTER CL " +
				"INNER JOIN STTM_CUSTOMER C ON C.CUSTOMER_NO=CL.CUSTOMER_ID " +
				"LEFT OUTER JOIN CSTM_FUNCTION_USERDEF_FIELDS CF ON CL.CUSTOMER_ID=SUBSTR(CF.REC_KEY,1,9) AND CF.FUNCTION_ID='STDCIF' " +
				"LEFT OUTER JOIN CLTM_PRODUCT P ON CL.PRODUCT_CODE=P.PRODUCT_CODE " +
				"LEFT OUTER JOIN STTM_CUSTOMER COD ON UPPER(CL.FIELD_CHAR_1) = UPPER(COD.SHORT_NAME) " +
				"LEFT OUTER JOIN STTM_CUST_PERSONAL CODP ON CODP.CUSTOMER_NO = COD.CUSTOMER_NO " +
				"WHERE CL.VALUE_DATE BETWEEN :F_DATE AND :T_DATE AND CL.ACCOUNT_STATUS NOT IN ('V') AND CL.PRODUCT_CODE IN('0401','0301') " +
				"ORDER BY 3,8"; 
				
		if(!param.getSubBranch().getCode().equals(""))
		{
			sql += "LOAN.BRANCH_CODE = :BRANCH AND ";
		}
		
		sql +=	"TRUNC(LOAN.REPORT_DATE) BETWEEN TRUNC(:F_DATE) AND LAST_DAY(TRUNC(:T_DATE)) AND LOAN.ACCOUNT_STATUS ='A') LN_SUM " + 
				"GROUP BY LN_SUM.CREDIT_OFFICER_ID,LN_SUM.CREDIT_OFFICER_NAME ,LN_SUM.CREDIT_OFFICER_CL,LN_SUM.BRANCH_CODE " +
				"ORDER BY LN_SUM.BRANCH_CODE,LN_SUM.CREDIT_OFFICER_ID,LN_SUM.CREDIT_OFFICER_NAME"; 
			
		try (Connection con = Sql2oHelper.sql2o.open()) {
			org.sql2o.Query query = con.createQuery(sql)
							.addParameter("F_DATE", MonthlyFacade.getSdofMonth(param.getFmonth().getId(), param.getFmonth().getCode()))
							.addParameter("T_DATE", MonthlyFacade.getEdofMonth(param.getTmonth().getId(), param.getTmonth().getCode()));
			
			if(!param.getSubBranch().getCode().equals(""))
			{
					query.addParameter("BRANCH", param.getSubBranch().getCode());
			}
				
			lst = query.executeAndFetch(IncentiveLoanVSU.class);
				
			if (lst == null) {
				lst = new ArrayList<IncentiveLoanVSU>();
			}
			
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting IncentiveLoanVSU List.", e);
		}
		
		return lst;
	}

//End Loan
	
//General
	
	public static List<Date> getAvailableDD() {
		List<Date> lst = new ArrayList<Date>();
		
		try {
			
			String sql = "SELECT DISTINCT REPORT_DATE FROM INC_LOAN_ACTIVE";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
							.executeAndFetch(Date.class);
				
				if (lst == null) {
					lst = new ArrayList<Date>();
				}
			}
			
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting Available Date List.", e);
		}
		
		return lst;
	}

	public static List<String> getAvailableMonths() {
		List<String> lst = new ArrayList<String>();
		
		List<Date> lstD = new ArrayList<Date>(getAvailableDD());
		for(int i = 0; i < lstD.size(); i++)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lstD.get(i));
			String m = MonthlyFacade.getMonthForInt(calendar.get(Calendar.MONTH)).substring(0, 3);
			String result = m + " - " + calendar.get(Calendar.YEAR);
			
			lst.add(result);
		}
		
		return lst;
	}
	
	public static int removeOldACMTData(Date dat, String table) {
		int result = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			java.sql.Connection conn = OracleHelper.getConnection();
			String sql = "DELETE FROM " + table + " WHERE TRUNC(ACMT_DATE) = ?";
			queryRunner.update(conn, sql, MonthlyFacade.getOracleformatDate(dat));
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("DBUtils error while removing Incentive ACMT Old Data.", e);
		}
		return result;
	}
	
	public static int removeMSSQLIncentiveOld(String table, Date eom) {
		int delete = 0;
		try {
			QueryRunner queryRunner = new QueryRunner();
			java.sql.Connection conn = MsSqlHelperAuth.getConnection();
			String sql = "DELETE FROM " + table + " WHERE ACMT_Date = ?";
			delete = queryRunner.update(conn, sql, Common.getSqlDateString(eom));
			DbUtils.close(conn);
		} catch (Exception e) {
			logger.error("DBUtils error while removing IncSavingOld.", e);
		}
		return delete;
	}

	public static int countData() {
		int result = 0;
		try {
			String sql = "SELECT COUNT(*) AS value FROM INC_LOAN";

			try (Connection con = Sql2oHelper.sql2o.open()) {
				Scalar scalar = con.createQuery(sql)
						.executeAndFetchFirst(Scalar.class);

				result = scalar.getValue();

			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting count data of INC_LOAN", e);
		}
		return result;
	}
	
//End General
	
}
