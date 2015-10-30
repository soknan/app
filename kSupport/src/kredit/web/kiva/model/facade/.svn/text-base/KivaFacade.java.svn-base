package kredit.web.kiva.model.facade;


import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.Common;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.kiva.model.KivaGroupModel;
import kredit.web.kiva.model.KivaIDLModel;
import kredit.web.kiva.model.KivaLoanModel;
import kredit.web.kiva.model.ParamModel;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

public class KivaFacade {
	private static Logger logger = Logger.getLogger(KivaFacade.class);
	
	public static List<KivaIDLModel> getLstFetchCifAccIDL(KivaIDLModel param){
		List<KivaIDLModel> lst = new ArrayList<KivaIDLModel>();
		try {
			String sql = "SELECT CP.FIRST_NAME AS fnameEn,CP.Last_Name AS lnameEn,CP.SEX AS sex,"
					+ "cl.AMOUNT_FINANCED AS amountDisburse,CL.ACCOUNT_NUMBER AS accountNo,CL.ALT_ACC_NO as refAcc,"
					+ "C.CUSTOMER_NO as customerNo,CL.PRODUCT_CATEGORY as prdCat FROM CLTB_ACCOUNT_MASTER CL "
					+ "INNER JOIN STTM_CUSTOMER C ON CL.CUSTOMER_ID = C.CUSTOMER_NO "
					+ "INNER JOIN STTM_CUST_PERSONAL CP ON CL.CUSTOMER_ID = CP.CUSTOMER_NO "
					+ "WHERE CL.PRODUCT_CATEGORY = 'INDIVIDUAL' "
					+ "and (CL.ACCOUNT_NUMBER like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or CL.CUSTOMER_ID like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or CL.ALT_ACC_NO like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or cp.first_name like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or cp.last_name like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or cp.sex like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ " or cl.amount_financed like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or CL.PRODUCT_CATEGORY like '"+Common.addLikeExpression(param.getFilter())+"') ";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(KivaIDLModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<KivaIDLModel>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		
		return lst;
	}
	
	public static List<KivaGroupModel> getLstFetchCifAccGRL(KivaGroupModel param){
		List<KivaGroupModel> lst = new ArrayList<KivaGroupModel>();
		try {
			String sql ="select g.branch_code,g.group_acc_no,g.ref_grp,count(gm.account) mbr,gt.gr_leader_name_kh,gt.cb_name,g.cycle_seq,sc.village "
					+ "from mfi_group g inner join vw_mfi_group_detail gt on gt.gr_id = g.id "
					+ "left join mfi_group_member gm on gm.group_id = g.id "
					+ "LEFT OUTER JOIN sttm_cust_add_main_bfsi sc on sc.id = g.village_id "
					+ "where g.branch_code like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or g.group_acc_no like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or g.ref_grp like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or gt.gr_leader_name_kh like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or gt.cb_name like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or g.cycle_seq like '"+Common.addLikeExpression(param.getFilter())+"' "
					+ "or sc.village like '"+Common.addLikeExpression(param.getFilter())+"' "					
					+ "group by  g.branch_code,g.group_acc_no,g.ref_grp,gt.gr_leader_name_kh,gt.cb_name,g.cycle_seq,sc.village";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.addColumnMapping("branch_code", "branchCode")
						.addColumnMapping("group_acc_no","groupAccNo" )
						.addColumnMapping("ref_grp", "refGrp")
						.addColumnMapping("mbr", "mbr")
						.addColumnMapping("gr_leader_name_kh", "leaderName")
						.addColumnMapping("cb_name", "cbName")
						.addColumnMapping("cycle_seq", "cycle")
						.addColumnMapping("village", "village")
						.executeAndFetch(KivaGroupModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<KivaGroupModel>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		
		return lst;
	}
	
	public static List<CodeItem> getBranchesList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "SELECT id, branch_code code, name_en description FROM SYS_BRANCH WHERE type ='B' ORDER BY name_en";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branchs List.", e);
		}
		return branchs;
	}
	
	public static String getBranchBy(String id) {
		String branchs = "";
		try {
			String sql = "select s.name_en description from sys_branch s where s.id = (select b.parent_id from sys_branch b where b.branch_code = '"+id+"')";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetchFirst(
					CodeItem.class).getDescription();
			}
			
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branchs List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getSubBranchesList(Integer id) {
		List<CodeItem> sub = new ArrayList<CodeItem>();
		try {
			String sql = "SELECT id, branch_code code, name_en description FROM SYS_BRANCH WHERE type ='S' and parent_id = '"+id+"' ORDER BY name_en";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				sub = con.createQuery(sql)					
					.executeAndFetch(CodeItem.class);
			}
			if (sub == null) {
				sub = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branchs List.", e);
		}
		return sub;
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
	
	public static List<KivaLoanModel> getLstKiva(ParamModel param){
		List<KivaLoanModel> lstKiva = new ArrayList<KivaLoanModel>();
		try{
			Query<KivaLoanModel> query = Ebean.find(KivaLoanModel.class)
					.select("loanId,refAcc,loanAmount,dateRaise,clientId,clientName,term,dateDisburse,coName,journalStatus,brCd,subName,dayNumber,active");
			if(!(param.getFilter()==null)){
				query.where()							
				.disjunction()				
				.ilike("id", Common.addLikeExpression(param.getFilter()))
				.ilike("loan_id", Common.addLikeExpression(param.getFilter()))
				.ilike("client_name", Common.addLikeExpression(param.getFilter()))
				.ilike("co_name", Common.addLikeExpression(param.getFilter()))
				.ilike("day_number", Common.addLikeExpression(param.getFilter()))				
				.ilike("loan_amount", Common.addLikeExpression(param.getFilter()))
				.ilike("journal_status", Common.addLikeExpression(param.getFilter()))
				.ilike("subName", Common.addLikeExpression(param.getFilter()))				
				.ilike("term",Common.addLikeExpression(param.getFilter()))		
				.endJunction();
			}
			if(!param.getBranch().getCode().equals("")){
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
			if(!param.getSub().getCode().equals("")){
				query.where().eq("brCd", param.getSub().getCode());
			}
			if(!param.getStatus().getCode().equals("%")){
				query.where().eq("active", param.getStatus().getCode());
			}
			if(!(param.getStartDate()==null)){
				query.where().ge("date_raise", param.getStartDate());
			}
			if(!(param.getEndDate()==null)){
				query.where().le("date_raise", param.getEndDate());
			}
			lstKiva = query.findList();
			if(lstKiva == null){
				lstKiva = new ArrayList<KivaLoanModel>();
			}
		}catch(Exception e){
			logger.error("Sql error while getting Kiva list",e);
		}
		return lstKiva;
	}
}
