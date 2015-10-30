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
import kredit.web.kbureau.model.enquiry.Enquiry;
import kredit.web.kbureau.model.enquiry.ParamEnquiry;

public class GuarantorFacade {
	private static Logger logger = Logger.getLogger(GuarantorFacade.class);
	
	public static Enquiry getGuarantor(String acc){
		Enquiry lst = new Enquiry();
		try{
			String sql = "SELECT 'NA' AS enquiryType"
					//+ ",'PLN' AS productType"
					+ ",cl.product_code AS enqProductCode"
				    + ",CL.FIELD_CHAR_8 AS enqLoanPurpose"
					+ ",1 AS numberOfApplicants"
					+ ",'S' AS accountType"
					+ ",cc.ACCOUNT_NUMBER AS loanID"
					+ ",EN.REF_NO AS memberReference"
					+ ",CL.AMOUNT_FINANCED AS amount"
					+ ",CL.CURRENCY AS currency"
					+ ",'P' AS applicantType"
					+ ",cc.id_type1 AS idType1"
					+ ",cc.id_number1 AS idNumber1"
					+ ",cc.expiry_date1 AS idExpiryDate1"
					+ ",cc.id_type2 AS idType2"
					+ ",cc.id_number2 AS idNumber2"
					+ ",cc.expiry_date2 AS idExpiryDate2"
					+ ",cc.id_type3 AS idType3"
					+ ",cc.id_number3 AS idNumber3"
					+ ",cc.expiry_date3 AS idExpiryDate3"
					+ ",cc.dob AS dob"
					+ ",cc.sex AS sex"
					+ ",DECODE(cc.marital_status,'S','Single','D','Divorced','M','Married','P','Separated','U','Unknown','W','Widow/Widower','F','Defacto') AS maritalStatusDesc"
					+ ",cc.marital_status AS maritalStatus"
					+ ",cc.NATIONALITY_code AS nationalityCode"
					+ ",DECODE(cc.NATIONALITY_code, 'KH', 'KHM', cc.NATIONALITY_code) AS nationalityCode"
					+ ",DECODE(cc.POB_COUNTRY, 'KH', 'KHM', cc.POB_COUNTRY) AS dCountry"
					+ ",cc.pob_province AS dProvince"
					+ ",cc.pob_district AS dDistrict"
					+ ",cc.pob_commune AS dCommune"
					+ ",cc.pob_village AS dVillage"
					+ ",cc.last_name_kh AS lastNameKH"
					+ ",cc.first_name_kh AS firstNameKH"
					+ ",'RESID' AS addressType"
					+ ",cc.address_line1 AS addressline1"
					+ ",cc.village AS village"
					+ ",cc.commune AS commune"
					+ ",cc.district AS district"
					+ ",'' AS province"
					+ ",'' AS city"
					+ ",DECODE(cc.city, 'KH', 'KHM', cc.COUNTRY) AS country"
					+ ",cc.first_name_en AS firstNameEN"
					+ ",cc.last_name_en AS lastNameEN"
					+ ",cc.phone_number1 AS telephone"
					+ ",cc.phone_number2 AS mobileNumber"
					+ ",'C' AS employmentType"
					+ ",cc.self_employed AS selfEmployed"
					+ ",cc.langth_of_service AS lengthOfServince"
					+ ",cc.occupation AS occupation"
					+ ",cc.total_monthly_income AS totalMonthlyIncome"					
					+ ",cc.employed_currency AS currency1"
					+ ",cc.employer_name AS employerName"
					+ ",'RESID' AS employerAddressType"
					+ ",cc.employer_address_line1 AS employerAddressLine1"
					+ ",cc.employer_city as employerCity"
					+ ",'KHM' as employerCountryCode"
					+ ",cc.customer_code as customerNo"
					+ ",CL.FIELD_CHAR_8 as enqLoanPurpose, CL.FIELD_CHAR_3 as loanFund"
					+ " FROM CLTB_ACCOUNT_MASTER CL INNER JOIN STTM_CUSTOMER C ON CL.CUSTOMER_ID=C.CUSTOMER_NO"								
					+ " LEFT OUTER JOIN CBC_CO_GUA CC ON CC.ACCOUNT_NUMBER = CL.ACCOUNT_NUMBER "
					+ " LEFT OUTER JOIN CBC_ENQUIRY EN ON CL.ACCOUNT_NUMBER = EN.ACCOUNT_NO AND EN.Status = 'P'"
					+ " WHERE CC.type ='G' and CC.ACCOUNT_NUMBER = '"+acc+"'";
			
			try(org.sql2o.Connection con= Sql2oHelper.sql2o.open()){
				lst = con.createQuery(sql).executeAndFetchFirst(Enquiry.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return lst;
	}
	
	public static List<Enquiry> getGuarantorList(ParamEnquiry param){
		List<Enquiry> lst = new ArrayList<Enquiry>();
		try{
			String sql = ""
					+ "select cg.customer_code as customerNo "
					+ ",cg.account_number as loanID "
					+ ",cg.first_name_kh as firstNameKH "
					+ ",cg.last_name_kh as lastNameKH "
					+ ",cg.customer_currency as currency "
					+ ",cl.amount_financed as amount "
					+ ",cl.alt_acc_no as alterAcc "
					+ ",cl.value_date as valueDate "
					+ ",br.name_en as branchNameEN "
					+ ",sb.name_en as subNameEN "
					+ ",cg.product_type as product from cbc_co_gua cg left outer join cltb_account_apps_master cl "
					+ "on cg.account_number = cl.account_number "
					+ "INNER JOIN SYS_BRANCH SB ON CL.BRANCH_CODE = SB.branch_code "
					+ "INNER JOIN SYS_BRANCH BR ON SB.PARENT_ID = BR.ID where 1=1 and cg.type ='G' "			
					+ "  and CL.CURRENCY = NVL(:currency, CL.CURRENCY)"	
					+ " AND CL.ACCOUNT_STATUS <> 'V'  "
					+ " AND ((CL.ACCOUNT_NUMBER NOT IN(SELECT account_no FROM cbc_enquiry WHERE status = 'O' and type='G')) OR "
					+ " (CL.ACCOUNT_NUMBER IN (SELECT account_no FROM cbc_enquiry WHERE status = 'P')))" 
					+ " AND (:brcode = 0 OR SB.BRANCH_CODE IN (select branch_code from sys_branch where parent_id = :brcode))"
					+ " AND SB.BRANCH_CODE = NVL(:sbcode, SB.BRANCH_CODE)";
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
			sql	+=  " AND ((upper(cg.ACCOUNT_NUMBER) like upper(:filter)) OR (upper(cg.last_name_kh || ' ' || cg.first_name_kh) like upper(:filter)) OR (cg.customer_code like :filter))" +
					" ORDER BY CL.VALUE_DATE DESC, BR.name_en, SB.Name_En";
			try(org.sql2o.Connection con= Sql2oHelper.sql2o.open()){
				Query query = con.createQuery(sql)
						.addParameter("brcode", param.getBranch().getId())
						.addParameter("sbcode", param.getSubBranch().getCode())
						.addParameter("currency", param.getCurrency().getCode())						
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
				lst = query.executeAndFetch(Enquiry.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e);
		}
		return lst;
	}
	
	public static void enquirySave(int Id) {
		String status;
		
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			Enquiry enquiry = getEnquirySQLServer(Id);
			if(enquiry.getStatus().equals("OK")){
				status = "O";
				String sql;
				System.out.println(enquiry.getRefNumber());
				if(checkRef(enquiry.getRefNumber()) > 0){
					/*sql = "UPDATE CBC_ENQUIRY SET STATUS =:status WHERE REF_NO = :ref";
					con.createQuery(sql)
						.addParameter("ref", enquiry.getRefNumber())
						.addParameter("status", status).executeUpdate();*/
					updateRef(enquiry.getRefNumber(), status);
				}else{
					sql = "INSERT INTO CBC_ENQUIRY(ID, REF_NO, ACCOUNT_NO, STATUS,TYPE) VALUES(CBC_ENQUIRY_SEQ.NEXTVAL,:ref,:acc,:status,'G')";
					con.createQuery(sql)
						.addParameter("ref", enquiry.getRefNumber())
						.addParameter("acc", enquiry.getLoanID())
						.addParameter("status", status).executeUpdate();
				}
			}
		} catch (Exception e) {
			logger.error("Error Update or Save Enquiry: " + Id, e);
		}
	}
	

	private static int checkRef(String refNumber) {
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
	public static String getCurrentDate(Date date)
	{
		String result = "";
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		result = formatter.format(date);
		return result;
	}
}
