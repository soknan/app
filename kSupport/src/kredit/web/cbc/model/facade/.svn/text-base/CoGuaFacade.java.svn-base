package kredit.web.cbc.model.facade;

import java.util.ArrayList;
import java.util.List;

import kredit.web.cbc.model.CoGuaModel;
import kredit.web.cbc.model.ParamCoGuaModel;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

public class CoGuaFacade {
	private static Logger logger = Logger.getLogger(CoGuaFacade.class);
	
	public static List<CoGuaModel> getCoGuaLst(ParamCoGuaModel param){
		List<CoGuaModel> lst  = new ArrayList<>();
		try{
			/*String sql = ""
					+ "select p.branch_code,p.validated,p.type,p.fullNameKH,p.accountNumber,p.customerName from ( "
					+ "select case when c.validated is null then 'N' else c.validated end validated, "
					+ "case when c.type is null then 'C' else c.type end type, "
					+ "case when c.type = 'G' then (c.last_name_kh||' '||c.first_name_kh) else m.field_char_4 end fullNameKH, "
					+ "m.account_number accountNumber,s.full_name customerName,m.branch_code "
					+ "from cltb_account_apps_master m left join cbc_co_gua c on m.account_number = c.account_number "
					+ "left outer join sttm_customer s on m.customer_id = s.customer_no order by c.create_on asc) p "
					+ "where 1=1 "*/
			String sql = "select p.accountNumber,p.branch_code branchCode,p.customerName,p.fullNameKH,p.type,p.validated,p.firstNameEN,p.lastNameEN "
					+ "from(select m.ACCOUNT_NUMBER accountNumber,m.BRANCH_CODE,s.full_name customerName,m.FIELD_CHAR_4 fullNameKH,'C' type,"
					+ "case when c.first_name_en is not null and c.type='C' then c.first_name_en else '' end firstNameEN,"
					+ "case when c.last_name_en is not null and c.type='C' then c.last_name_en else '' end lastNameEN,"
					+ "case when c.validated is null or c.type='G' then 'N' else c.validated end validated from cltb_account_master m "
					+ "left outer join sttm_customer s on s.customer_no = m.CUSTOMER_ID "
					+ "left join cbc_co_gua c on c.account_number = m.ACCOUNT_NUMBER union "
					+ "select g.account_number accountNumber,g.branch_code,s.full_name,"
					+ "(g.last_name_kh||' '||g.first_name_kh) fullNameKH,g.type,g.first_name_en firstNameEN,"
					+ "g.last_name_en lastNameEN,g.validated from cbc_co_gua g"
					+ " left outer join sttm_customer s on s.customer_no = g.customer_code) p where 1=1 "
					+ " and (p.accountNumber like '%"+param.getFilter()+"%' "
					+ " or p.customerName like '%"+param.getFilter()+"%') "
					+ " and p.branch_code = '"+UserCredentialManager.getIntance().getLoginUsr().getBrCd()+"'"
					+ " and p.type like '%"+param.getType().getCode()+"%' "	;	
					
			try(Connection con = Sql2oHelper.sql2o.open()){
				lst = con.createQuery(sql).executeAndFetch(CoGuaModel.class);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc co-gua list", e);
		}
		return lst;
	}
	
	public static CoGuaModel getLstCoborrower(String acc) {
		CoGuaModel lst = new CoGuaModel();
		try {
			String sql = "select * from ("
					+ "SELECT cc.id as id,'C' as type,cl.branch_code as branchCode,"
					+ "PD.Product_Code AS enqProductCode,"
				    + "CL.FIELD_CHAR_8 AS enqLoanPurpose,"
					+ "       c.customer_no       AS customerCode, "
					+ "       CL.account_number   AS accountNumber, "
					+ "       CL.amount_financed  AS customerAmount, "
					+ "       CL.currency         AS customerCurrency, "
					+ "       C.full_name         AS customerName, "
					+ "       cl.product_category AS productType, "
					+ "       CC.id_type1        AS idType1, "
					+ "       CC.id_number1      AS idNumber1, "
					+ "       CC.expiry_date1    AS expiryDate1, "
					+ "       CC.id_type2        AS idType2, "
					+ "       CC.id_number2      AS idNumber2, "
					+ "       CC.expiry_date2    AS expiryDate2, "
					+ "       CC.id_type3        AS idType3, "
					+ "       CC.id_number3      AS idNumber3, "
					+ "       CC.expiry_date3    AS expiryDate3, "
					+ "       CC.dob              AS dob, "
					+ "       CC.sex              AS sex, "
					+ "       CC.marital_status   AS maritalStatus, "
					+ "       'KHM' AS nationalityCode, "
					+ "       'KHM'      AS pobCountry, "
					+ "       CC.pob_province     AS pobProvince, "
					+ "       CC.pob_district     AS pobDistrict, "
					+ "       CC.pob_commune      AS pobCommune, "
					+ "       CC.pob_village      AS pobVillage, "					
					+ "       Substr(CL.field_char_4, Instr(CL.field_char_4, ' ') + 1) "					
					+ "       AS firstNameKH, "					
					+ "         Substr(CL.field_char_4, 1, Instr(CL.field_char_4, ' ') - 1) "					
					+ "       AS lastNameKH, "
					+ "case when cc.first_name_en is not null and cc.type='C' then cc.first_name_en else '' end firstNameEN,"
					+ "case when cc.last_name_en is not null and cc.type='C' then cc.last_name_en else '' end lastNameEN,"
					+ "CP.First_Name AS cusFirstNameEN, "
					+ "CP.Last_Name AS cusLastNameEN, "
					+ "       'RESID'             AS addressType, "
					+ "       CP.p_address1       AS addressLine1, "
					+ "       CP.p_address2       AS village, "
					+ "       CP.p_address3       AS commune, "
					+ "       CP.p_address4       AS district, "
					+ "       MP.ADDRESS5         AS province, "
					+ "       MP.ADDRESS5         AS city, "
					+ "       'KHM'           	  AS country, "
					+ "       ''                  AS phoneNumberType1, "
					+ "       '855'                  AS phoneCountryCode1, "
					+ "       ''                  AS areaCode1, "
					+ "CP.TELEPHONE AS phoneNumber1,"
					+ "CP.MOBILE_NUMBER AS phoneNumber2,"
					+ "       ''                  AS extension1, "
					+ "       'C'                 AS employmentType, "
					+ "       P.employment_status AS selfEmployed, "
					+ "       P.employment_tenure AS langthOfService, "
					+ "       CF.description      AS occupation, "
					+ "       P.salary            AS totalMonthlyIncome, "				
					+ "AF.AMOUNT AS totalMonthlyIncome2,"
					+ "       'USD'               AS employedCurrency, "
					+ "       P.designation       AS employerName, "
					+ "       'RESID'             AS employerAddressType, "
					+ "       p.e_address1        AS employerAddressLine1, "
					+ "       p.e_address2        AS employerCity, "
					+ "       'KHM'               AS employerCountryCode, "
					+ "       ''                  AS employerProvince, "
					+ "       ''                  AS employerDistrict, "
					+ "       ''                  AS employerCommune, "
					+ "       ''                  AS employerVillage "		
					+ "FROM   cltb_account_master CL "
					+ "       inner join sttm_customer C "
					+ "               ON CL.customer_id = C.customer_no "
					+ "       inner join sttm_cust_personal CP "
					+ "               ON CL.customer_id = CP.customer_no "
					+ "       left outer join cbc_enquiry EN "
					+ "                    ON CL.account_number = EN.account_no "
					+ "                       AND EN.status = 'P' "
					+ "       left outer join cstm_function_userdef_fields F "
					+ "                    ON CL.customer_id = Substr(F.rec_key, 1, 9) "
					+ "                       AND F.function_id = 'STDCIF' "
					+ "       left outer join sys_lov V3 "
					+ "                    ON F.field_val_3 = V3.desc_en "
					+ "       left outer join sys_lov V5 "
					+ "                    ON F.field_val_5 = V5.desc_en "
					+ "       left outer join sttm_cust_domestic CD "
					+ "                    ON CL.customer_id = CD.customer_no "
					+ "       left outer join sttm_mfi_cust_det MP "
					+ "                    ON CL.customer_id = MP.customer_no "
					+ "       left outer join sttm_cust_professional P "
					+ "                    ON CL.customer_id = P.customer_no "
					+ "       left outer join sttm_cust_classification CF "
					+ "                    ON C.cust_classification = CF.cust_classification "
					+ "       left outer join cltm_product PD "
					+ "                    ON CL.product_code = PD.product_code "
					+ "       left outer join cltb_account_financials AF "
					+ "                    ON CL.account_number = AF.account_number "
					+ "                       AND AF.finance_type = 'Total_Family_Average_Net_Income' "
					+ "       left join cbc_co_gua CC "
					+ "              ON CC.account_number = CL.account_number) p "
					+ "WHERE  p.type ='C' "
					+ "and p.accountNumber = '"+acc+"' "					
					+ "";			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql).executeAndFetchFirst(CoGuaModel.class);
			}			
			/*if (lst == null) {
				lst = new ArrayList<CoGuaModel>();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc client", e);
		}
		return lst;
	}
	
	public static CoGuaModel getLstGuarantor(String acc) {
		CoGuaModel lst = new CoGuaModel();
		try {
			String sql = "select * from ("
					+ "SELECT cc.id as id, 'G' as type,cl.branch_code as branchCode,"					
					+ "       c.customer_no       AS customerCode, "
					+ "       CL.account_number   AS accountNumber, "
					+ "       CL.amount_financed  AS customerAmount, "
					+ "       CL.currency         AS customerCurrency, "
					+ "       C.full_name         AS customerName, "
					+ "       cl.product_category AS productType, "
					+ "       CC.id_type1        AS idType1, "
					+ "       CC.id_number1      AS idNumber1, "
					+ "       CC.expiry_date1    AS expiryDate1, "
					+ "       CC.id_type2        AS idType2, "
					+ "       CC.id_number2      AS idNumber2, "
					+ "       CC.expiry_date2    AS expiryDate2, "
					+ "       CC.id_type3        AS idType3, "
					+ "       CC.id_number3      AS idNumber3, "
					+ "       CC.expiry_date3    AS expiryDate3, "
					+ "       CC.dob              AS dob, "
					+ "       CC.sex              AS sex, "
					+ "       CC.marital_status   AS maritalStatus, "
					+ "       'KHM' AS nationalityCode, "
					+ "       'KHM'      AS pobCountry, "
					+ "       CC.pob_province     AS pobProvince, "
					+ "       CC.pob_district     AS pobDistrict, "
					+ "       CC.pob_commune      AS pobCommune, "
					+ "       CC.pob_village      AS pobVillage, "
					+ "       CC.first_name_kh      AS firstNameKH, "
					+ "       CC.last_name_kh      AS lastNameKH, "
					+ "       CC.first_name_en    AS firstNameEN, "
					+ "       CC.last_name_en     AS lastNameEN, "
					+ "       CC.address_type            AS addressType, "
					+ "       CC.address_line1       AS addressline1, "
					+ "       CC.village       AS village, "
					+ "       CC.commune       AS commune, "
					+ "       CC.district       AS district, "
					+ "       CC.province         AS province, "
					+ "       cc.city        AS city, "
					+ "       'KHM'           	  AS country, "
					+ "       cc.phone_number_type1                  AS phoneNumberType1, "
					+ "       '855'                  AS phoneCountryCode1, "
					+ "       cc.area_code1                  AS areaCode1, "
					+ "       CC.phone_number1        AS phoneNumber1, "
					+ "       cc.extension1                  AS extension1, "
					+ "       cc.phone_number_type2                  AS phoneNumberType2, "
					+ "       '855'                  AS phoneCountryCode2, "
					+ "       cc.area_code2                  AS areaCode2, "
					+ "       CC.phone_number2        AS phoneNumber2, "
					+ "       cc.extension2                  AS extension2, "
					+ "       'C'                 AS employmentType, "
					+ "       CC.self_employed    AS selfEmployed, "
					+ "       CC.langth_of_service AS langthOfService, "
					+ "       CC.occupation      AS occupation, "
					+ "       CC.total_monthly_income            AS totalMonthlyIncome, "
					+ "       'USD'               AS employedCurrency, "
					+ "       CC.employer_name       AS employerName, "
					+ "       CC.employer_address_type             AS employerAddressType, "
					+ "       CC.employer_address_line1        AS employerAddressLine1, "
					+ "       cc.employer_city        AS employerCity, "
					+ "       'KHM'               AS employerCountryCode, "
					+ "       cc.employer_province                  AS employerProvince, "
					+ "       cc.employer_district                  AS employerDistrict, "
					+ "       cc.employer_commune                  AS employerCommune, "
					+ "       cc.employer_village                  AS employerVillage "
					+ "FROM   cltb_account_master CL "
					+ "       inner join sttm_customer C "
					+ "               ON CL.customer_id = C.customer_no "					
					+ "       left join cbc_co_gua CC "
					+ "              ON CC.account_number = CL.account_number) p "
					+ "WHERE type='G' "
					+ "and accountNumber = '"+acc+"' "			
					+ "";			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql).executeAndFetchFirst(CoGuaModel.class);
			}			
			/*if (lst == null) {
				lst = new ArrayList<CoGuaModel>();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc client", e);
		}
		return lst;
	}
	
	public static List<CoGuaModel> getLstAccount(ParamCoGuaModel param){
		List<CoGuaModel> lst = new ArrayList<CoGuaModel>();
		try {
			String sql = ""
					+ "select m.account_number as accountNumber,'G' as type,m.branch_code as branchCode,"
					+ "PD.Product_Code AS enqProductCode,"
				    + "m.FIELD_CHAR_8 AS enqLoanPurpose,"
					+ "'KHM' as nationalityCode,'KHM' as pobCountry,"
					+ "'KHM' as country,'C' as employmentType,'KHM' AS employerCountryCode,"
					+ " '855' AS phoneCountryCode1,'USD' as employedCurrency,"
					+ " c.customer_no AS customerCode, "
					+ "c.full_name as customerName,"
					+ "m.currency  as customerCurrency,"
					+ "m.amount_financed as customerAmount,"
					+ "m.product_category as productType "
					+ "from cltb_account_apps_master m "
					+ "       left outer join cltm_product PD "
					+ "                    ON m.product_code = PD.product_code "
					+ "left join sttm_customer c "
					+ "on m.customer_id = c.customer_no where m.account_number not in(select account_number from cbc_co_gua where type ='G' ) "
					+ " and m.branch_code = '"+UserCredentialManager.getIntance().getLoginUsr().getBrCd()+"' "
					+ "and (m.account_number like '%"+param.getFilter()+"%' or c.full_name like '%"+param.getFilter()+"%')";
					
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CoGuaModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CoGuaModel>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		
		return lst;
	}
}
