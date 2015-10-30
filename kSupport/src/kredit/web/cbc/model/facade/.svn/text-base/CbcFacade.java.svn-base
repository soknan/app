package kredit.web.cbc.model.facade;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.sql2o.Connection;
import org.zkoss.zul.ListModelList;

import kredit.web.cbc.model.CbcModel;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;

public class CbcFacade {

	private static Logger logger = Logger.getLogger(CbcFacade.class);
	
	public static List<CodeItem> getProvinceLst(){
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select distinct(lo.province) code from sttm_cust_add_main_bfsi lo order by lo.province asc";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc idType",e);
		}
		return lst;
	}
	
	public static List<CodeItem> getDistrictLst(String param){
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select distinct(lo.district) code from sttm_cust_add_main_bfsi lo where lo.province = '"+param+"'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc idType",e);
		}
		return lst;
	}
	
	public static List<CodeItem> getCommuneLst(String pro,String district){			
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select distinct(lo.commune) code from sttm_cust_add_main_bfsi lo "
					+ "where lo.province = '"+pro+"' and lo.district = '"+district+"'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc idType",e);
		}
		return lst;	
	}
	
	public static List<CodeItem> getVillageLst(String pro,String dis,String com){			
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select lo.village code from sttm_cust_add_main_bfsi lo "
					+ "where lo.province = '"+pro+"' and lo.district = '"+dis+"' and lo.commune = '"+com+"'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc idType",e);
		}
		return lst;	
	}	
	
	public static List<CodeItem> getIDTypeLst(){
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select id,code,desc_en description from sys_lov where type = 'ID_TYPE' and status = 'A'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc idType",e);
		}
		return lst;
	}
	
	public static List<CodeItem> getMaritalStatusLst(){			
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select id,code,desc_en description from sys_lov where type = 'MARITAL_STATUS_CBC' and status = 'A'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc marital status",e);
		}
		return lst;	
	}
	
	public static ListModelList<CodeItem> getGenderLst(){			
		ListModelList<CodeItem> rows = new ListModelList<CodeItem>();
		String[] code = new String[] { "M", "F"};
		String[] desc = new String[] { "Male","Female"};
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			rows.add(item);
		}
		return rows;	
	}	
	
	public static List<CodeItem> getAddressTypeLst(){			
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select s.code code, s.desc_en description from sys_lov s where s.type = 'ADDRESS_TYPE'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc address type",e);
		}
		return lst;
	}
	
	public static List<CodeItem> getPhoneNumberTypeLst(){			
		List<CodeItem> lst = new ArrayList<CodeItem>();
		try {
			String sql = "select s.code code, s.desc_en description from sys_lov s where s.type = 'CONTACT_NUMBER_TYPE'";
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.executeAndFetch(CodeItem.class);				
			}
			if (lst == null) {
				lst = new ArrayList<CodeItem>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while getting cbc address type",e);
		}
		return lst;
	}
	
	public static ListModelList<CodeItem> getSelfEmployedLst(){			
		ListModelList<CodeItem> rows = new ListModelList<CodeItem>();
		String[] code = new String[] { "Y","N"};
		String[] desc = new String[] { "Yes","No"};
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			rows.add(item);
		}
		return rows;	
	}	
}