package kredit.web.util.checker.model.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.sql2o.Connection;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;

import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.util.checker.model.Checker;
import kredit.web.util.checker.model.ResultModel;

public class CheckerFacade {
	// private static Logger logger = Logger.getLogger(CheckerFacade.class);

	public static List<ResultModel> getCheckerResult(String brCd,
			Checker checker) {
		if (checker == null || checker.getSql() == null)
			return null;

		List<ResultModel> lst = null;
		
		if(brCd.equals("All"))
			brCd = null;
		
		try(Connection con = Sql2oHelper.sql2o.open()){
			lst = con.createQuery(checker.getSql())
					.addParameter("brCd", brCd).executeAndFetch(ResultModel.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return lst;

	}

	public static String getCheckerIdList() {
		String arrayList = "";
		
		try(Connection con = Sql2oHelper.sql2o.open()){
			arrayList = con
					.createQuery(
							"select listagg(c.id,',') within group (order by c.seq asc) id_list FROM UTL_CHECKER c WHERE c.active = 'Y'")
					.executeAndFetchFirst(String.class);
		}

		return arrayList;
	}
	
	public static String getCheckerListJson(Set<Checker> ls){
		//List<Checker> ls = Ebean.find(Checker.class).select("statusMsg").where().eq("active", "Y").order().asc("seq").findList();
		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		for(Checker chk: ls){
			obj = new JSONObject();
			obj.put("id", chk.getId());
			obj.put("statusMsg", chk.getStatusMsg().replace("'", "\'"));
			arr.add(obj);
		}
		return arr.toJSONString();
	}
	
	public static List<CodeItem> getSubBranchesList(int branch_id) {
		List<CodeItem> branchs = new ArrayList<CodeItem>();

		try {
			String sql = " SELECT sb.id id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription FROM SYS_BRANCH sb" +
						" INNER JOIN SYS_BRANCH b ON sb.PARENT_ID = b.ID" +
						" WHERE sb.TYPE = 'S' ORDER BY description";
			
			if(!(branch_id == 0))
			{
				sql = " SELECT sb.id id, sb.branch_code code, sb.name_en description, b.id superId, b.branch_code superCode, b.name_en superDescription FROM SYS_BRANCH sb" +
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
			e.printStackTrace();
		}
		return branchs;
	}
}
