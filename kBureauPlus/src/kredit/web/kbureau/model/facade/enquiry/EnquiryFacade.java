package kredit.web.kbureau.model.facade.enquiry;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.log4j.Logger;
import org.sql2o.Query;

import kredit.web.core.util.db.DbHelper;
import kredit.web.core.util.db.OracleHelper;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.Scalar;
import kredit.web.kbureau.model.ScalareString;
import kredit.web.kbureau.model.enquiry.Enquiry;
import kredit.web.kbureau.model.enquiry.ParamEnquiry;
import kredit.web.kbureau.model.report.CodeItem;

public class EnquiryFacade {

	private static Logger logger = Logger.getLogger(EnquiryFacade.class);

	public static Enquiry getEnquiry(String acc) {
		Enquiry cbc = new Enquiry();
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT 'NA' AS enquiryType"
					//+ ",'PLN' AS productType"
					+ ",PD.Product_Code AS enqProductCode"
				    + ",CL.FIELD_CHAR_8 AS enqLoanPurpose"
					+ ",1 AS numberOfApplicants"
					+ ",'S' AS accountType"
					+ ",CL.ACCOUNT_NUMBER AS loanID"
					+ ",EN.REF_NO AS memberReference"
					+ ",CL.AMOUNT_FINANCED AS amount"
					+ ",CL.CURRENCY AS currency"
					+ ",'P' AS applicantType"
					+ ",'N' AS idType1"
					+ ",CP.PASSPORT_NO AS idNumber1"
					+ ",CP.PPT_EXP_DATE AS idExpiryDate1"
					+ ",V3.Code AS idType2"
					+ ",F.FIELD_VAL_4 AS idNumber2"
					+ ",'' AS idExpiryDate2"
					+ ",V5.Code AS idType3"
					+ ",F.FIELD_VAL_6 AS idNumber3"
					+ ",'' AS idExpiryDate3"
					+ ",CP.DATE_OF_BIRTH AS dob"
					+ ",CP.SEX AS sex"
					+ ",DECODE(CD.MARITAL_STATUS,'S','Single','D','Divorced','M','Married','P','Separated','E','Spouse Expired','R','Remarried') AS maritalStatusDesc"
					+ ",CD.MARITAL_STATUS AS maritalStatus"
					+ ",C.NATIONALITY AS nationalityCode"
					+ ",DECODE(C.NATIONALITY, 'KH', 'KHM', C.NATIONALITY) AS nationalityCode"
					+ ",DECODE(CP.D_COUNTRY, 'KH', 'KHM', 'VN', 'VNM', CP.D_COUNTRY) AS dCountry"
					+ ",MP.Address5c AS dProvince"
					+ ",CP.D_ADDRESS4 AS dDistrict"
					+ ",CP.D_ADDRESS3 AS dCommune"
					+ ",CP.D_ADDRESS2 AS dVillage"
					+ ",F.FIELD_VAL_1 AS lastNameKH"
					+ ",F.FIELD_VAL_2 AS firstNameKH"
					+ ",'RESID' AS addressType"
					+ ",CP.P_ADDRESS1 AS addressline1"
					+ ",CP.P_ADDRESS2 AS village"
					+ ",CP.P_ADDRESS3 AS commune"
					+ ",CP.P_ADDRESS4 AS district"
					+ ",'' AS province"
					+ ",'' AS city"
					+ ",DECODE(CP.P_COUNTRY, 'KH', 'KHM', CP.P_COUNTRY) AS country"
					+ ",CP.First_Name AS firstNameEN"
					+ ",CP.Last_Name AS lastNameEN"
					+ ",CP.TELEPHONE AS telephone"
					+ ",CP.MOBILE_NUMBER AS mobileNumber"
					+ ",'C' AS employmentType"
					+ ",P.EMPLOYMENT_STATUS AS selfEmployed"
					+ ",P.EMPLOYMENT_TENURE AS lengthOfServince"
					+ ",CF.DESCRIPTION AS occupation"
					+ ",P.SALARY AS totalMonthlyIncome"
					+ ",AF.AMOUNT AS totalMonthlyIncome2"
					+ ",'USD' AS currency1"
					+ ",P.DESIGNATION AS employerName"
					+ ",'RESID' AS employerAddressType"
					+ ",p.e_address1 AS employerAddressLine1"
					+ ",p.e_address2 as employerCity"
					+ ",'KHM' as employerCountryCode"
					+ ",C.customer_no as customerNo"
					+ ",CL.FIELD_CHAR_8 as loanPurpose, CL.FIELD_CHAR_3 as loanFund"
					+ " FROM CLTB_ACCOUNT_MASTER CL INNER JOIN STTM_CUSTOMER C ON CL.CUSTOMER_ID=C.CUSTOMER_NO"
					+ " INNER JOIN STTM_CUST_PERSONAL CP ON  CL.CUSTOMER_ID=CP.CUSTOMER_NO"
					+ " LEFT OUTER JOIN CBC_ENQUIRY EN ON CL.ACCOUNT_NUMBER = EN.ACCOUNT_NO AND EN.Status = 'P'"
					+ " LEFT OUTER JOIN CSTM_FUNCTION_USERDEF_FIELDS F ON CL.CUSTOMER_ID=SUBSTR(F.REC_KEY,1,9) AND F.FUNCTION_ID='STDCIF'"
					+ " LEFT OUTER JOIN sys_lov V3 ON F.Field_Val_3 = V3.Desc_En"
					+ " LEFT OUTER JOIN sys_lov V5 ON F.Field_Val_5 = V5.Desc_En"
					+ " LEFT OUTER JOIN STTM_CUST_DOMESTIC CD ON CL.CUSTOMER_ID=CD.CUSTOMER_NO"
					+ " LEFT OUTER JOIN STTM_MFI_CUST_DET MP ON CL.CUSTOMER_ID=MP.CUSTOMER_NO"
					+ " LEFT OUTER JOIN STTM_CUST_PROFESSIONAL P ON CL.CUSTOMER_ID=P.CUSTOMER_NO"
					+ " LEFT OUTER JOIN STTM_CUST_CLASSIFICATION CF ON C.CUST_CLASSIFICATION=CF.CUST_CLASSIFICATION"
					+ " LEFT OUTER JOIN CLTM_PRODUCT PD ON CL.PRODUCT_CODE = PD.PRODUCT_CODE"
					+ " LEFT OUTER JOIN CLTB_ACCOUNT_FINANCIALS AF ON CL.ACCOUNT_NUMBER = AF.ACCOUNT_NUMBER AND AF.FINANCE_TYPE = 'Total_Family_Average_Net_Income'"
					+ " WHERE CL.ACCOUNT_NUMBER = :acc";
			
			cbc = con.createQuery(sql)
					.addParameter("acc", acc)
					.executeAndFetchFirst(Enquiry.class);
			
			//System.out.println( sql);
			
		} catch (Exception e) {
			logger.error("Sql2o error while getting CbcModel");
		}
		return cbc;
	}

	public static List<Enquiry> getEnquiryList(ParamEnquiry param) {
		List<Enquiry> enquiryList = new ArrayList<Enquiry>();
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			
			String sql = "SELECT distinct CL.ACCOUNT_NUMBER AS loanID,           "
					+ "		  EN.REF_NO AS memberReference,				"
					+ "       C.customer_no as customerNo,				"
					+ "       CL.ALT_ACC_NO as alterAcc,				"	
					+ "	      CL.VALUE_DATE AS valueDate,				"
					+ "       F.FIELD_VAL_1      AS lastNameKH,         "
					+ "       F.FIELD_VAL_2      AS firstNameKH,        "
					+ "       Cl.AMOUNT_FINANCED AS amount,             "
					+ "       CL.CURRENCY,      						"
					+ "       BR.name_en         AS branchNameEN,       "
					+ "       SB.Name_En         AS subNameEN,          "
					+ "       PRO.product_desc   AS product             "
					
					+ " FROM CLTB_ACCOUNT_MASTER CL"
					+ " INNER JOIN STTM_CUSTOMER C ON CL.CUSTOMER_ID = C.CUSTOMER_NO"
					+ " LEFT OUTER JOIN CSTM_FUNCTION_USERDEF_FIELDS F ON CL.CUSTOMER_ID = SUBSTR(F.REC_KEY, 1, 9)   AND F.FUNCTION_ID = 'STDCIF' " 
					+ " LEFT OUTER JOIN cltm_product PRO ON CL.PRODUCT_CODE = PRO.product_code"
					+ " INNER JOIN SYS_BRANCH SB ON CL.BRANCH_CODE = SB.branch_code"
					+ " INNER JOIN SYS_BRANCH BR ON SB.PARENT_ID = BR.ID"
					+ " LEFT OUTER JOIN CBC_ENQUIRY EN on CL.ACCOUNT_NUMBER = EN.Account_No"
					+ " LEFT OUTER JOIN cltb_event_entries et on CL.ACCOUNT_NUMBER = et.account_number"
					+ " WHERE CL.CURRENCY = NVL(:currency, CL.CURRENCY)"
					+ " AND CL.ACCOUNT_STATUS <> 'V'  "  // V is reversed
					// below for new created loan that the old one was reversed and already requested to CBC
					// have disbursed(record in cltb_event_entries) or as group but value_date >= today
					+ " AND ((et.event_code is null) OR (CL.PRODUCT_CODE IN ('0201', '0301', '0401') AND (CL.VALUE_DATE >= :today)) ) " 
					/*+ " AND ((CL.ACCOUNT_NUMBER NOT IN(SELECT account_no FROM cbc_enquiry WHERE status = 'O' and (type!='G' or type!='C') )) OR "
					+ " (CL.ACCOUNT_NUMBER IN (SELECT account_no FROM cbc_enquiry WHERE status = 'P' and (type!='G' or type!='C'))))" */
					+ " AND CL.ACCOUNT_NUMBER NOT IN(SELECT account_no FROM cbc_enquiry WHERE status = 'O' and type is null)"
					
					+ " AND (:brcode = 0 OR SB.BRANCH_CODE IN (select branch_code from sys_branch where parent_id = :brcode))"
					+ " AND SB.BRANCH_CODE = NVL(:sbcode, SB.BRANCH_CODE)"
			 		+ " AND (CL.VALUE_DATE > (sysdate - 10))"; // 10 days back
					
					if(param.getFromAmount() > 0 && param.getToAmount() > 0){
						sql += " AND (CL.AMOUNT_FINANCED BETWEEN :startamount AND :toamount) ";
					}else if(param.getFromAmount() > 0){
						sql += " AND CL.AMOUNT_FINANCED >= :startamount ";
					}else if(param.getToAmount() > 0){
						sql += " AND CL.AMOUNT_FINANCED <= :toamount ";
					}
					
					if(param.getFromDate().length() > 0 && param.getToDate().length() > 0){
						sql += " AND (CL.VALUE_DATE BETWEEN :startdate AND :enddate) ";
					}else if(param.getFromDate().length() > 0){
						sql += " AND CL.VALUE_DATE >= :startdate ";
					}else if(param.getToDate().length() > 0){
						sql += " AND CL.VALUE_DATE <= :enddate ";
					}
					
				sql	+=  " AND ((upper(CL.ACCOUNT_NUMBER) like upper(:filter)) OR (upper(F.FIELD_VAL_1 || ' ' || F.FIELD_VAL_2) like upper(:filter)) OR (C.customer_no like :filter))" +
						" ORDER BY CL.VALUE_DATE DESC, BR.name_en, SB.Name_En";
			 
		    //System.out.println(sql);		
			Query query = con.createQuery(sql)
					.addParameter("brcode", param.getBranch().getId())
					.addParameter("sbcode", param.getSubBranch().getCode())
					.addParameter("currency", param.getCurrency().getCode())
					.addParameter("today", getCurrentDate(new Date()))
					.addParameter("filter", param.getFilter());
			if(param.getFromAmount() > 0){
				query.addParameter("startamount", param.getFromAmount());
			}
			
			if(param.getToAmount() > 0){
				query.addParameter("toamount", param.getToAmount());
			}
			
			if(param.getFromDate().length() > 0){
				query.addParameter("startdate", param.getSDate());
			}
			
			if(param.getToDate().length() > 0){
				query.addParameter("enddate", param.getEDate());
			}
			
			enquiryList = query.executeAndFetch(Enquiry.class);
			
		
		//System.out.println("brcode:" + param.getBranch().getId());
		//System.out.println("sbcode:" + param.getSubBranch().getCode());
		//System.out.println("Today: " + new Date() + ", Valuedate: " + enquiryList.get(0).getValueDate());
			
		} catch (Exception e) {
			logger.error("Sql2o error while getting enquiryList", e);
		}
		return enquiryList;
	}

	public static List<CodeItem> getSuBranches(int branchId) {
		List<CodeItem> lstsb = new ArrayList<CodeItem>();
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT 0 id, '' code, 'All' description, 0 superId, '' superCode, 'All' superDescription FROM DUAL"
					+ " UNION "
					+ "SELECT sb.id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription "
					+ "FROM SYS_BRANCH sb INNER JOIN "
					+ "SYS_BRANCH b ON sb.parent_id = b.id "
					+ "WHERE (:branchId = 0 OR sb.PARENT_ID = :branchId) AND sb.TYPE = 'S' ORDER BY description";

			lstsb = con.createQuery(sql)
					.addParameter("branchId", branchId)
					.executeAndFetch(CodeItem.class);
		} catch (Exception e) {
			logger.error("Error while getting sub branches.", e);
		}
		return lstsb;
	}

	public static List<CodeItem> getBranches() {
		List<CodeItem> lstsb = new ArrayList<CodeItem>();
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {

			String sql = "SELECT 0 id, '' code, 'All' description FROM DUAL"
					+ " UNION "
					+ "SELECT id, branch_code code, name_en description FROM SYS_BRANCH "
					+ "WHERE type = 'B' ORDER BY description";

			lstsb = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
		} catch (Exception e) {
			logger.error("Error while getting sub branches.", e);
		}
		return lstsb;
	}

	public static CodeItem getBranch(String code) {
		CodeItem subBranch = new CodeItem();

		if (code.equals("*")) {
			subBranch.setId(0);
			subBranch.setCode("");
			subBranch.setDescription("All");
			return subBranch;
		}

		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT id, branch_code code, name_en description "
					+ "FROM SYS_BRANCH " + "WHERE code2 = :code AND TYPE = 'B'";

			subBranch = con.createQuery(sql)
					.addParameter("code", code)
					.executeAndFetchFirst(CodeItem.class);
		} catch (Exception e) {
			logger.error("Error while getting Branch - Code: " + code, e);
		}
		return subBranch;
	}

	public static CodeItem getSuBranch(String suCode) {
		CodeItem subBranch = new CodeItem();

		if (suCode.equals("*")) {
			subBranch.setId(0);
			subBranch.setCode("");
			subBranch.setDescription("All");
			return subBranch;
		}

		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT sb.id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription "
					+ "FROM SYS_BRANCH sb INNER JOIN "
					+ "SYS_BRANCH b ON sb.parent_id = b.id "
					+ "WHERE sb.code2 = :suCode AND sb.TYPE = 'S'";

			subBranch = con.createQuery(sql)
					.addParameter("suCode", suCode)
					.executeAndFetchFirst(CodeItem.class);
		} catch (Exception e) {
			logger.error("Error while getting sub branch - Code: " + suCode, e);
		}
		return subBranch;
	}

	public static void enquirySave(int Id) {
		String status;
		
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			Enquiry enquiry = getEnquirySQLServer(Id);
			if(enquiry.getStatus().equals("OK")){
			//if(!enquiry.getStatus().equals("ERROR")){
				status = "O";
				String sql;
				if(checkRef(enquiry.getRefNumber()) > 0){
					updateRef(enquiry.getRefNumber(), status);
				}else{
					sql = "INSERT INTO CBC_ENQUIRY(ID, REF_NO, ACCOUNT_NO, STATUS) VALUES(CBC_ENQUIRY_SEQ.NEXTVAL,:ref,:acc,:status)";
					con.createQuery(sql)
						.addParameter("ref", enquiry.getRefNumber())
						.addParameter("acc", (enquiry.getLoanID()).trim())
						.addParameter("status", status).executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("Error Update or Save Enquiry: " + Id, e);
		}
	}
	

	public static int checkRef(String refNumber) {
		int checkR = 0;
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT COUNT(*) as value FROM CBC_ENQUIRY WHERE REF_NO =:ref";
			Scalar scalar = con.createQuery(sql)
					.addParameter("ref", refNumber)
					.executeAndFetchFirst(Scalar.class);
			checkR = scalar.getValue();
		} catch (Exception e) {
			logger.error("Error checkRef: ");
		}
		return checkR;
	}

	public static Enquiry getEnquirySQLServer(int Id) {
		Enquiry enquiry = null;
		try {
			QueryRunner queryRunner = new QueryRunner();
			Connection conn = DbHelper.getConnection();
			ResultSetHandler<Enquiry> h = new BeanHandler<Enquiry>(
					Enquiry.class);
			String sql = "SELECT Id, LoanId, RefNumber, Status FROM CbcReport WHERE Id=?";
			enquiry = queryRunner.query(conn, sql, h, Id);
			DbUtils.close(conn);

			if (enquiry == null)
				enquiry = new Enquiry();
		} catch (Exception e) {
			logger.error("Error getting Enquiry From SQL server : " + Id, e);
		}
		return enquiry;
	}
    
	
	public static void updateRef(String ref, String status) {
		Connection conn = OracleHelper.getConnection();
		try {
			// Create a QueryRunner that will use connections from
			QueryRunner run = new QueryRunner();

			run.update(conn,
					"UPDATE CBC_ENQUIRY SET status = ? WHERE REF_NO = ?",
					status, ref);

		} catch (Exception e) {
			logger.error("Error while updating CBC_ENQUIRY.", e);
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				logger.error("Error while updating CBC_ENQUIRY.", e);
			}
		}
	}

	public static String getPerProvince(String village) {
		//System.out.println("village = " + village );
		String province = "";
		if(village.length() < 9)
			return province;
		village = village.substring((village.length())-8, village.length());
		//String province = "";
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT V.PROVINCE AS value FROM STTM_CUST_ADD_MAIN_BFSI V WHERE V.ID=:village";

			ScalareString scalar = con.createQuery(sql)
					.addParameter("village", village)
					.executeAndFetchFirst(ScalareString.class);
			
			province = scalar.getValue();
		} catch (Exception e) {
			logger.error("Error while getting Province " + e);
		}
		return province;
	}

	public static String getCurrentDate(Date date)
	{
		String result = "";
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		result = formatter.format(date);
		return result;
	}

}