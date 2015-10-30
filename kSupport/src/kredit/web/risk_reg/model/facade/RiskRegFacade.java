package kredit.web.risk_reg.model.facade;

import java.util.ArrayList;
import java.util.List;

import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.risk_reg.model.ParamModel;
import kredit.web.risk_reg.model.RiskRegModel;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

public class RiskRegFacade {
private static Logger logger = Logger.getLogger(RiskRegFacade.class);
	
	public static List<RiskRegModel> getLstRiskReg(ParamModel param){
		List<RiskRegModel> lst = new ArrayList<RiskRegModel>();
		try {			
			String sql ="SELECT ROWNUM,substr(R.risk_rr,-1,1) color_rr,substr(R.af_risk_er,-1,1) color_er,R.* FROM(SELECT * FROM RISK_REG) R "
					+ "WHERE ('"+param.getBranch().getId()+"' = 0 OR R.BRANCH_CODE IN (select branch_code from sys_branch where parent_id = '"+param.getBranch().getId()+"'))"
					+ " AND R.BRANCH_CODE = NVL('"+param.getSub().getCode()+"', R.BRANCH_CODE) "
					+ " and R.FIN_MONTH = '"+param.getfMonth().getCode()+"' AND R.FIN_YEAR = '"+param.getfYear().getCode()+"' ";
			
			
			if(param.getFilter() !=null){
				sql+=" and R.CODE LIKE '%"+param.getFilter()+"%'";
			}
			
			//System.out.println(sql);
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(RiskRegModel.class);				
			}
			if (lst == null) {
				lst = new ArrayList<RiskRegModel>();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error while selecting risk reg.", e);
		}
		
		return lst;
	}
	
	public static List<CodeItem> getRowsReg() {
		List<CodeItem> reg = new ArrayList<CodeItem>();
		try {
			String[] desc = new String[] { "1", "2", "3", "4", "5"};
			String[] code = new String[] {"1", "2", "3", "4", "5"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				reg.add(item);
			}		
		} catch (Exception e) {
			logger.error("Sql2o error while getting List.", e);
		}
		return reg;
	}
	
	public static List<CodeItem> getProcessList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "select code,desc_en description from sys_lov where type ='RISK_REG_PROCESS'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getForTsList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "select code,desc_en description from sys_lov where type ='RISK_REG_FTS'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getStatusList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "select code,desc_en description from sys_lov where type ='RISK_REG_STATUS'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getPositionList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "select  distinct(e.position) description FROM SYS_EMPLOYEE E";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
			}
			if (branchs == null) {
				branchs = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			logger.error("Sql2o error while getting List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getFinMonthList() {
		List<CodeItem> branchs = new ArrayList<CodeItem>();
		try {
			String sql = "select distinct(m.fin_month) code from risk_reg m";
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
			String sql = "select distinct(m.fin_year) code from risk_reg m";
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
		List<CodeItem> lstsb = new ArrayList<CodeItem>();
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {

			String sql = "SELECT 0 id, '' code, 'All' description FROM DUAL"
					+ " UNION "
					+ "SELECT id, branch_code code, name_en description FROM SYS_BRANCH "
					+ "WHERE type = 'B' ORDER BY id,code";

			lstsb = con.createQuery(sql).executeAndFetch(
					CodeItem.class);
		} catch (Exception e) {
			logger.error("Error while getting sub branches.", e);
		}
		return lstsb;
	}
	
	public static CodeItem getBranchBy(String id) {
		CodeItem branchs = new CodeItem();
		try {
			String sql = "";			
			sql = "select s.id,s.branch_code code,s.name_en description from sys_branch s where s.id = (select b.parent_id from sys_branch b where b.branch_code = '"+id+"')";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				branchs = con.createQuery(sql).executeAndFetchFirst(
					CodeItem.class);
			}
			
		} catch (Exception e) {
			logger.error("Sql2o error while getting Branchs List.", e);
		}
		return branchs;
	}
	
	public static List<CodeItem> getSubBranchesList(Integer id) {
		List<CodeItem> lstsb = new ArrayList<CodeItem>();
		try(org.sql2o.Connection con = Sql2oHelper.sql2o.open()) {
			String sql = "SELECT 0 id, '' code, 'All' description, 0 superId, '' superCode, 'All' superDescription FROM DUAL"
					+ " UNION "
					+ "SELECT sb.id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription "
					+ "FROM SYS_BRANCH sb INNER JOIN "
					+ "SYS_BRANCH b ON sb.parent_id = b.id "
					+ "WHERE (:branchId = 0 OR sb.PARENT_ID = :branchId) AND sb.TYPE = 'S' ORDER BY id,code";

			lstsb = con.createQuery(sql)
					.addParameter("branchId", id)
					.executeAndFetch(CodeItem.class);
		} catch (Exception e) {
			logger.error("Error while getting sub branches.", e);
		}
		return lstsb;
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
	
	public static String getSysLovName(String type,String desc){
		String name = "";
		try {
			String sql = "select desc_en description from sys_lov where upper(type)='"+type.toUpperCase()+"' and upper(desc_en) = '"+desc.toUpperCase()+"'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				name = con.createQuery(sql).executeAndFetchFirst(
					CodeItem.class).getDescription();
			}
			
		} catch (Exception e) {
			logger.error("Sql2o error while getting Sys Lov Name.", e);
		}
		return name;
	}
	
	public static String getPositionName(String desc){
		String name = "";
		try {
			String sql = "select distinct e.position description from sys_employee e where upper(e.position) = '"+desc.toUpperCase()+"'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				name = con.createQuery(sql).executeAndFetchFirst(
					CodeItem.class).getDescription();
			}
			
		} catch (Exception e) {
			logger.error("Sql2o error while getting Sys Lov Name.", e);
		}
		return name;
	}
	
	public static int getMaxSeq() {
		Integer riskSeq = 0;
		String sql = "select max(nvl(substr(r.code,-6),0)) from risk_reg r";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			riskSeq = con.createQuery(sql).executeScalar(Integer.class);

		} catch (Exception e) {
			logger.error("Sql2o error while getting max risk reg seq ", e);
		}
		
		return riskSeq;
	}
	
}