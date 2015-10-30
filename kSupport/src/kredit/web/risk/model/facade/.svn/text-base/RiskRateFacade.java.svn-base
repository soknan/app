package kredit.web.risk.model.facade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kredit.web.core.util.Common;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.risk.model.DailyRateModel;
import kredit.web.risk.model.DepositThresholdModel;
import kredit.web.risk.model.MonthlyRateModel;
import kredit.web.risk.model.ParamModel;
import kredit.web.risk.model.RateDetailModel;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

public class RiskRateFacade {
private static Logger logger = Logger.getLogger(RiskRateFacade.class);
	
	public static List<MonthlyRateModel> getLstMonthlyRate(ParamModel param){
		List<MonthlyRateModel> lst = new ArrayList<MonthlyRateModel>();
		try {			
			String sql = "select rownum no, m.branch_code,m.branch_name,m.cif,m.cust_name,m.num_account,m.balance,m.monthly_deposit,m.monthly_status "
					+ " ,m.fin_month,m.fin_year,a.initial_type,a.initial_class,b.pre_type,b.pre_class,b.pre_bal,m.initial_type cur_type,m.initial_class cur_class "
					+ " from clr_monthly_rating m inner join (select r.initial_type initial_type,r.initial_class initial_class,r.cif,r.seq "
					+ " from clr_monthly_rating r where r.seq =0) a on a.cif = m.cif left join (select r.initial_type pre_type "
					+ " ,r.initial_class pre_class,r.balance pre_bal,r.cif,r.seq from clr_monthly_rating r)b on b.cif = m.cif and b.seq = m.seq-1 "
					+ " where m.fin_month='"+param.getfMonth().getCode()+"' and m.fin_year='"+param.getfYear().getCode()+"' ";
			if(param.getFilter() !=null){
				sql+=" and ( m.cif like '"+Common.addLikeExpression(param.getFilter())+"' "
						+ " or m.cust_name like '"+Common.addLikeExpression(param.getFilter())+"' "
						+ " or m.num_account like '"+Common.addLikeExpression(param.getFilter())+"' "						
						+ ")";
			}
			if(!param.getBranch().getCode().equals("")){
				if(param.getBranch().getCode().equals("000"))
				{					
					sql+=" and m.branch_code in('"+param.getBranch().getCode()+"') ";
				}
				else
				{	
					sql+=" and m.branch_code in("+getSubBranchCodeList(param.getBranch().getId()).toString().replaceAll("[\\s\\[\\]]", "")+") ";					
				}
			}
			if(!param.getSub().getCode().equals("")){
				sql+=" and m.branch_code = '"+param.getSub().getCode()+"' ";
			}
			if(!param.getTranStatus().getCode().equals("%")){
				sql+=" and m.monthly_status = '"+param.getTranStatus().getDescription()+"' ";
			}
			if(!param.getTranType().getCode().equals("%")){
				sql+=" and m.initial_type = '"+param.getTranType().getDescription()+"' ";
			}
			if(!param.getEvenBal().getCode().equals("%")){
				sql+=" and m.balance "+param.getEvenBal().getCode()+" nvl(b.pre_bal,0) ";
			}
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(MonthlyRateModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<MonthlyRateModel>();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error while selecting monthly rate.", e);
		}
		
		return lst;
	}
	
	public static List<DailyRateModel> getLstDailyRate(ParamModel param){
		List<DailyRateModel> lst = new ArrayList<DailyRateModel>();
		try {
			
			String sql = "select rownum no,branch_code,branch_name,cif,cust_name,num_account,initial_class"
					+ ",initial_type,daily_deposit,daily_status,value_date "
					+ " from clr_daily_rating d where to_date(d.value_date,'DD-MM-YY') = to_date('"+new SimpleDateFormat("d-MMM-YYYY").format(param.getvDate())+"','DD-MM-YY') ";
			if(param.getFilter()!=null){
				sql+=" and (cif like '"+Common.addLikeExpression(param.getFilter())+"' "
						+ " or cust_name like '"+Common.addLikeExpression(param.getFilter())+"' "
						+ " or num_account like '"+Common.addLikeExpression(param.getFilter())+"' "						
						+ ")";
			}
			if(!param.getBranch().getCode().equals("")){
				if(param.getBranch().getCode().equals("000"))
				{					
					sql+=" and branch_code = '"+param.getBranch().getCode()+"' ";
				}
				else
				{					
					sql+=" and branch_code in("+getSubBranchCodeList(param.getBranch().getId()).toString().replaceAll("[\\s\\[\\]]", "")+") ";
				}
			}
			if(!param.getSub().getCode().equals("")){
				sql+=" and branch_code = '"+param.getSub().getCode()+"' ";
			}
			if(!param.getTranStatus().getCode().equals("%")){
				sql+=" and d.daily_status = '"+param.getTranStatus().getDescription()+"' ";
			}
			if(!param.getTranType().getCode().equals("%")){
				sql+=" and d.initial_type = '"+param.getTranType().getDescription()+"' ";
			}
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(DailyRateModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<DailyRateModel>();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error while selecting daily rate.", e);
		}
		
		return lst;
	}
	
	public static List<RateDetailModel> getLstMonthlyDetail(String cif,String f_month,String f_year){
		List<RateDetailModel> lst = new ArrayList<RateDetailModel>();
		try {
			String sql = ""
					+ "select rownum no,p.* from(select cus.branch_code,cus.cust_ac_no acc_no,(c.account_class||'-'||c.description) product_name "
					+ "          ,cus.ccy,NVL(krd_fun_get_risk_bal_forward(cus.cust_ac_no,to_date('01-'||'"+f_month+"'||'-'||'"+f_year+"','DD-MM-YYYY')),0) b_balance,NVL(td.deposit,0) deposit,NVL(td.withdrawal,0) withdrawal,0 other_deposit "
					+ "          ,0 disbursement,0 principal_repayment,0 other_loan,NVL(krd_fun_get_risk_bal_forward(cus.cust_ac_no,last_day(to_date('01-'||'"+f_month+"'||'-'||'"+f_year+"','DD-MM-YYYY'))),0) e_balance "
					+ "          from sttm_cust_account cus left join "
					+ "          (select sum(vd.acy_dr_tur) withdrawal,sum(vd.acy_cr_tur) deposit,vd.account from actb_accbal_history vd "
					+ "          where vd.bkg_date between to_date('01-'||'"+f_month+"'||'-'||'"+f_year+"','DD-MM-YYYY') and last_day(to_date('01-'||'"+f_month+"'||'-'||'"+f_year+"','DD-MM-YYYY')) "
					+ "          group by vd.account)td on td.account = cus.cust_ac_no "
					+ "          inner join sttm_account_class c on c.account_class = cus.account_class "
					+ "          where cus.cust_no ='"+cif+"' and cus.record_stat = 'O' "
					+ "          union "
					+ "          select lm.BRANCH_CODE,lm.ACCOUNT_NUMBER acc_no,(lm.PRODUCT_CODE||'-'||lm.PRODUCT_CATEGORY) product_name,lm.CURRENCY ccy,0 b_balance,0 deposit,0 withdrawal,0 other_deposit "
					+ "          ,lm.AMOUNT_DISBURSED disbursement,nvl(out_bal.amt_paid,0) principal_repayment,0 other_loan,out_bal.outstanding_bal e_balance "
					+ "          from (select dibs.branch_code, "
					+ "                              dibs.account_number, "
					+ "                              amt_paid, "
					+ "                              amt_disb - nvl(amt_paid,0) as outstanding_bal "
					+ "                         from (select en.branch_code, "
					+ "                                      en.account_number, "
					+ "                                      sum(en.amount) amt_disb "
					+ "                                 from cltb_event_entries en "
					+ "                                where en.event_code in ('DSBR') "
					+ "                                  and amount_tag in ('PRINCIPAL') "
					+ "                                group by en.branch_code, en.account_number) dibs "
					+ "                        left join (select en.branch_code, "
					+ "                                          en.account_number, "
					+ "                                          sum(en.amount) amt_paid "
					+ "                                     from cltb_event_entries en "
					+ "                                    where en.event_code in ('MLIQ') "
					+ "                                      and amount_tag in "
					+ "                                          ( 'PRINCIPAL_LIQD', 'PRINCIPAL_WAVD' ) "
					+ "                                      and en.value_date <= last_day(to_date('01-'||'"+f_month+"'||'-'||'"+f_year+"','DD-MM-YYYY')) "
					+ "                                    group by en.branch_code, en.account_number) paid "
					+ "                           on dibs.account_number = paid.account_number) out_bal "
					+ "           left join cltb_account_master lm "
					+ "              on lm.ACCOUNT_NUMBER = out_bal.ACCOUNT_NUMBER "
					+ "           where lm.CUSTOMER_ID = '"+cif+"' and lm.ACCOUNT_STATUS ='A' "
					+ "           group by lm.BRANCH_CODE, lm.ACCOUNT_NUMBER,lm.PRODUCT_CODE,lm.PRODUCT_CATEGORY,lm.CURRENCY,lm.AMOUNT_DISBURSED,out_bal.amt_paid,out_bal.outstanding_bal)p "
					+ "           where 1=1 ";
					
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(RateDetailModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<RateDetailModel>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		
		return lst;
	}
	
	public static List<RateDetailModel> getLstDailyDetail(String cif,Date v_date){
		List<RateDetailModel> lst = new ArrayList<RateDetailModel>();
		try {			
			String sql = ""
					+ "select rownum no,p.* from(select cus.branch_code,cus.cust_ac_no acc_no,(c.account_class||'-'||c.description) product_name "
					+ "          ,cus.ccy,NVL(krd_fun_get_risk_bal_forward(cus.cust_ac_no,'"+new SimpleDateFormat("d-MMM-YYYY").format(v_date)+"'),0) b_balance,NVL(td.deposit,0) deposit,NVL(td.withdrawal,0) withdrawal,0 other_deposit "
					+ "          ,0 disbursement,0 principal_repayment,0 other_loan,NVL(krd_fun_get_risk_bal_forward(cus.cust_ac_no,'"+new SimpleDateFormat("d-MMM-YYYY").format(v_date)+"'),0) e_balance "
					+ "          from sttm_cust_account cus left join "
					+ "          (select sum(vd.acy_dr_tur) withdrawal,sum(vd.acy_cr_tur) deposit,vd.account from actb_accbal_history vd "
					+ "          where vd.bkg_date = '"+new SimpleDateFormat("d-MMM-YYYY").format(v_date)+"' "
					+ "          group by vd.account)td on td.account = cus.cust_ac_no "
					+ "          inner join sttm_account_class c on c.account_class = cus.account_class "
					+ "          where cus.cust_no ='"+cif+"' and cus.record_stat = 'O' "
					+ "          union "
					+ "          select lm.BRANCH_CODE,lm.ACCOUNT_NUMBER acc_no,(lm.PRODUCT_CODE||'-'||lm.PRODUCT_CATEGORY) product_name,lm.CURRENCY ccy,0 b_balance,0 deposit,0 withdrawal,0 other_deposit "
					+ "          ,lm.AMOUNT_DISBURSED disbursement,nvl(out_bal.amt_paid,0) principal_repayment,0 other_loan,out_bal.outstanding_bal e_balance "
					+ "          from (select dibs.branch_code, "
					+ "                              dibs.account_number, "
					+ "                              amt_paid, "
					+ "                              amt_disb - nvl(amt_paid,0) as outstanding_bal "
					+ "                         from (select en.branch_code, "
					+ "                                      en.account_number, "
					+ "                                      sum(en.amount) amt_disb "
					+ "                                 from cltb_event_entries en "
					+ "                                where en.event_code in ('DSBR') "
					+ "                                  and amount_tag in ('PRINCIPAL') "
					+ "                                group by en.branch_code, en.account_number) dibs "
					+ "                        left join (select en.branch_code, "
					+ "                                          en.account_number, "
					+ "                                          sum(en.amount) amt_paid "
					+ "                                     from cltb_event_entries en "
					+ "                                    where en.event_code in ('MLIQ') "
					+ "                                      and amount_tag in "
					+ "                                          ( 'PRINCIPAL_LIQD', 'PRINCIPAL_WAVD' ) "
					+ "                                      and en.value_date <= '"+new SimpleDateFormat("d-MMM-YYYY").format(v_date)+"' "
					+ "                                    group by en.branch_code, en.account_number) paid "
					+ "                           on dibs.account_number = paid.account_number) out_bal "
					+ "           left join cltb_account_master lm "
					+ "              on lm.ACCOUNT_NUMBER = out_bal.ACCOUNT_NUMBER "
					+ "           where lm.CUSTOMER_ID = '"+cif+"' and lm.ACCOUNT_STATUS ='A' "
					+ "           group by lm.BRANCH_CODE, lm.ACCOUNT_NUMBER,lm.PRODUCT_CODE,lm.PRODUCT_CATEGORY,lm.CURRENCY,lm.AMOUNT_DISBURSED,out_bal.amt_paid,out_bal.outstanding_bal)p "
					+ "           where 1=1 ";
					
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(RateDetailModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<RateDetailModel>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		
		return lst;
	}
	
	public static List<CodeItem> getFinMonthList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "select distinct(m.fin_month) code from clr_monthly_rating m";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting fin month List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getFinYearList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "select distinct(m.fin_year) code from clr_monthly_rating m";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting fin Year List.", e);
		}
		return branchs;
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
	
	public static List<DepositThresholdModel> getLstDepositThreshold(ParamModel param){
		List<DepositThresholdModel> lst = new ArrayList<DepositThresholdModel>();
		try{
			String sql = "SELECT rownum no,d.* FROM CLR_DEPOSIT_THRESHOLD d where 1=1 ";
			if(param.getFilter() !=null){
				sql+=" and (rating_class like '"+Common.addLikeExpression(param.getFilter())+"' "
						+ " or per_day_deposit like '"+Common.addLikeExpression(param.getFilter())+"' "
						+ " or per_month_deposit like '"+Common.addLikeExpression(param.getFilter())+"' "						
						+ ")";
			}
			try(Connection con = Sql2oHelper.sql2o.open()){
				lst = con.createQuery(sql)
						.executeAndFetch(DepositThresholdModel.class);							
			}
			if (lst == null) {
				lst = new ArrayList<DepositThresholdModel>();
			}
		}catch(Exception e){
			logger.error("Sql2o error while getting Deposit Threshold List.", e);
		}
		return lst;
	}
	
	
}
