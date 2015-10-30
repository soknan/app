package kredit.web.util.deupload.model.facade;

import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.util.deupload.model.DeUpload;
import kredit.web.util.deupload.model.DeUploadHist;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;

public class DeuploadFacade {
	private static Logger logger = Logger.getLogger(DeuploadFacade.class);
	
	public static List<DeUploadHist> getLstUploadHistory(String filter){
		List<DeUploadHist> lst = new ArrayList<DeUploadHist>();
		String tmp = "";
		try {
			/*String sql = "select p.batch_no as batchNo,p.branch_code as brCode,p.upload_date as uploadDate "
					+ "from(select batch_no,branch_code,upload_date from detb_upload_master union "
					+ "select batch_no ,branch_code,upload_date from detb_upload_master_history) p "
					+ "where p.batch_no like '"+Common.addLikeExpression(filter)+"' "
					+ "or p.branch_code like '"+Common.addLikeExpression(filter)+"' "
					+ "or p.upload_date like '"+Common.addLikeExpression(filter)+"' "
					+ "order by p.upload_date desc";*/
			
			if(!getUserRole().equals("HQ")){
				tmp = " and p.branch_code = '"+getUserRole()+"'";
			}
			String sql = ""
					+ "select p.batch_no as batchNo,p.branch_code as brCode,p.upload_date as uploadDate,p.ccy_cd as currency "
					+ "from( "
					+ "select m.batch_no,m.branch_code,d.ccy_cd,m.upload_date from detb_upload_master m inner join detb_upload_detail d "
					+ "on m.batch_no = d.batch_no group by m.batch_no,m.branch_code,d.ccy_cd,m.upload_date "
					+ "union "
					+ "select mh.batch_no,mh.branch_code,dh.ccy_cd,mh.upload_date from detb_upload_master_history mh inner join detb_upload_detail_history dh "
					+ "on mh.batch_no = dh.batch_no group by mh.batch_no,mh.branch_code,dh.ccy_cd,mh.upload_date "
					+ ") p where 1=1 "+tmp+""
					+ " and (p.batch_no like '"+Common.addLikeExpression(filter)+"' "
					+ "or p.branch_code like '"+Common.addLikeExpression(filter)+"' "
					+ "or p.ccy_cd like '"+Common.addLikeExpression(filter)+"' "
					+ "or p.upload_date like '"+Common.addLikeExpression(filter)+"' )"
					+ "order by p.upload_date desc NULLS LAST";
			try(Connection con = Sql2oHelper.sql2o.open()){
				lst = con.createQuery(sql)
	    		 			.executeAndFetch(DeUploadHist.class);
			}			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting upload history.", e);
		}	     
		return lst;
	}
	
	public static List<DeUpload> getLstUploadHistBy(String batch,String err){
		List<DeUpload> lst = new ArrayList<DeUpload>();
		try {
			String sql ="select d.CURR_NO as no,d.BRANCH_CODE as brCd,d.CCY_CD as currency,d.AMOUNT as amount,"
					/*+"SubStr(d.addl_text, 1, InStr(d.addl_text, '#', 1, 1)-1) tranDate,"*/
					+"SubStr(d.addl_text, InStr(d.addl_text, '#', 1, 1)+1, (InStr(d.addl_text, '#', 1, 2)-1) - (InStr(d.addl_text, '#', 1, 1))) tranDes,"
					+"SubStr(d.addl_text, InStr(d.addl_text, '#', 1, 2)+1, (InStr(d.addl_text, '#', 1, 3)-1) - (InStr(d.addl_text, '#', 1, 2))) voucherNo,"
					+ "case when InStr(d.addl_text, '#', 1, 3)+1 =1 then '' else SubStr(d.addl_text,InStr(d.addl_text, '#', 1, 3)+1) end cdvOff,"
					+ "d.ACCOUNT as acc,d.ACCOUNT_BRANCH as accBrCode,d.TXN_CODE as trCode,d.DR_CR as dc,"
					+ "d.LCY_EQUIVALENT as lcyAmount,d.EXCH_RATE as excRate,"
					+ "d.INSTRUMENT_NO as insNo,d.upload_stat as uploadStat from("
					+ "select CURR_NO,BRANCH_CODE,CCY_CD,AMOUNT,ACCOUNT,ACCOUNT_BRANCH,TXN_CODE,DR_CR,"
					+ "LCY_EQUIVALENT,EXCH_RATE,VALUE_DATE,BATCH_NO,ADDL_TEXT,INSTRUMENT_NO,upload_stat from detb_upload_detail "
					+ "union select CURR_NO,BRANCH_CODE,CCY_CD,AMOUNT,ACCOUNT,ACCOUNT_BRANCH,TXN_CODE,DR_CR,"
					+ "LCY_EQUIVALENT,EXCH_RATE,VALUE_DATE,BATCH_NO,ADDL_TEXT,INSTRUMENT_NO,upload_stat "
					+ "from detb_upload_detail_history) d where d.batch_no = '"+batch+"'";
			if(!err.equals("all")){
				sql+=" and d.upload_stat !='Y'";
			}
			try(Connection con = Sql2oHelper.sql2o.open()){
				lst = con.createQuery(sql)	    		 			
	    		 			.executeAndFetch(DeUpload.class);
			}			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting upload history.", e);
		}	     
		return lst;
	}
	
	public static String getDateByBranch(String branch){		
		String dateString = null;
		try {
			String sql ="Select to_char(Today,'yyyy-MM-dd') from sttm_dates where branch_code= '"+branch+"'";
			try(Connection con = Sql2oHelper.sql2o.open()){
				dateString = con.createQuery(sql)	    		 			
	    		 			.executeAndFetchFirst(String.class);
			}			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting Date by Branch.", e);
		}	     
	     return dateString;
	}
	
	public static String getAutoBatch(){
		String id = "";
		try {
			String sql ="select krd_de_next_batch_no() from dual";
			try(Connection con = Sql2oHelper.sql2o.open()){
				id = con.createQuery(sql)				
				.executeAndFetchFirst(String.class);				
			}			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting auto batch.", e);
		}		
		return id;			
	}
	
	public static boolean isBatchExist(String batch){
		int exist;
		try {
			String sql = "select count(batch_no) BATCHCOUNT from detb_batch_master where batch_no='"+batch+"'";
			try(Connection con = Sql2oHelper.sql2o.open()){
				exist = con.createQuery(sql)
						.executeAndFetchFirst(Integer.class);
				if(exist >0){
					return true;
				}
			}	
			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting batch exist.", e);
		}		
		return false;
	}
	
	public static void updateBatch(String batch){
		String sql ="update sys_setting set value = '"+batch+"' where type = 'DE_BATCH_NO'";
		SqlUpdate update = Ebean.createSqlUpdate(sql);
		Ebean.execute(update);
	}
	
	public static boolean isMatchAccount(String accId){
		int acc;
		try {
			String sql = "SELECT count(ac_gl_no) GLCOUNT FROM sttb_account WHERE AC_GL_REC_STATUS = 'O' AND auth_stat = 'A' and ac_or_gl <>'L' AND ac_gl_no ='"+accId+"'";
			try(Connection con = Sql2oHelper.sql2o.open()){
				acc = con.createQuery(sql)
						.executeAndFetchFirst(Integer.class);
				if(acc >0){
					return true;
				}
			}	
			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting match account.", e);
		}		
		return false;
	}
	
	public static String getUserRole(){
		String lst = "";
		String sql = ""
				+ "select r.name from sys_user u left join sys_user_role ur "
				+ "on u.id = ur.user_id left join sys_role r "
				+ "on r.id = ur.role_id where u.username = '"+UserCredentialManager.getIntance().getLoginUsr().getUsername()+"' and r.name='HQ'";
		try(Connection con = Sql2oHelper.sql2o.open()){
		lst = con.createQuery(sql)
    		 			.executeAndFetchFirst(String.class);
			if(lst==null){
				lst = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
			}
		}
		return lst;
	}
	
	public static boolean isHasRole(String userId){
		int acc;
		String sql = "select krd_fun_get_DE_role('"+userId+"') from dual";
				
		try(Connection con = Sql2oHelper.sql2o.open()){
			acc = con.createQuery(sql).executeAndFetchFirst(Integer.class);
				if(acc>0){
					return true;
				}
			}
		return false;
	}
	
	public static String getHomeBranch(String br,String UserID){		
		String home="";
		try {
			String sql = "select u.home_branch from smtb_user u "
					+ " where u.user_id ='"+UserID+"'";
			try(Connection con = Sql2oHelper.sql2o.open()){
				home = con.createQuery(sql)
						.executeAndFetchFirst(String.class);
				if(home.isEmpty()) return "";
			}	
			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting batch exist.", e);
		}		
		return home;
	}
	
	public static boolean isMatchSystemDate(String area,String br){
		String hq;
		String ac_branch;
		try {
			String sql = "select d.today from STTM_DATES d where d.branch_code = '"+area+"'";
			String sql1 = "select d.today from STTM_DATES d where d.branch_code = '"+br+"'";	
			try(Connection con = Sql2oHelper.sql2o.open()){
				hq= con.createQuery(sql).executeAndFetchFirst(String.class);
				ac_branch= con.createQuery(sql1).executeAndFetchFirst(String.class);
				
				if(hq.equals(ac_branch)){
					return true;
				}
				return false;
			}	
			
		} catch (Exception e) {			
			logger.error("Sql2o error while getting sttm date.", e);
		}		
		return true;
	}
}
