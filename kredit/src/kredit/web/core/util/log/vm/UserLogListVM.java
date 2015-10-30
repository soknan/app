package kredit.web.core.util.log.vm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.log.model.LoggerAllSession;
import kredit.web.core.util.log.model.LoggerSessionLog;
import kredit.web.core.util.model.CodeItem;

import org.sql2o.Connection;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DefaultCommand;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.avaje.ebean.PagingList;

public class UserLogListVM {
	private static Logger logger = Logger.getLogger(UserLogListVM.class);
	private ListModelList<CodeItem> rows;

	List<LoggerAllSession> lstSession;
	ListModelList<CodeItem> filterBranches;
	ListModelList<CodeItem> filterSubBranches;	
	CodeItem subBranch;
	CodeItem branch;
	CodeItem selectedNumRow;

	LoggerAllSession selected;
	String filter = "";
	Date logonFrom = new Date();
	Date logonTo = new Date();

	@Wire("#usrlst")
	Window winSS;

	public CodeItem getBranch() {
		if (branch != null)
			return branch;
		branch = new CodeItem();
		branch.setCode("All");
		branch.setDescription("All");
		return branch;
	}

	public void setBranch(CodeItem branch) {
		this.branch = branch;
	}

	public ListModelList<CodeItem> getFilterSubBranches() {
		if (filterSubBranches == null) {
			// Messagebox.show("yes");
			filterSubBranches = new ListModelList<CodeItem>(
					getSubBranchesList());
		}
		return filterSubBranches;
	}

	public void setFilterSubBranches(ListModelList<CodeItem> filterSubBranches) {
		this.filterSubBranches = filterSubBranches;
	}

	public CodeItem getSubBranch() {
		if (subBranch != null)
			return subBranch;
		subBranch = new CodeItem();
		subBranch.setCode("All");
		subBranch.setDescription("All");
		return subBranch;
	}

	public void setSubBranch(CodeItem subBranch) {
		this.subBranch = subBranch;
	}

	public Date getLogonFrom() {
		/*if (logonFrom == null) {
			logonFrom = new Date();
		}*/
		return logonFrom;
	}

	public void setLogonFrom(Date logonFrom) {
		this.logonFrom = logonFrom;
	}

	public Date getLogonTo() {
		/*if (logonTo == null) {
			logonTo = new Date();
		}*/
		return logonTo;
	}

	public void setLogonTo(Date logonTo) {
		this.logonTo = logonTo;
	}

	private int pageIndex;
	private PagingList<LoggerSessionLog> pagingList;
	private int totalSize;

	@Init
	public void inti() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange({ "lstSession", "totalSize" })
	public void doSearch() {
		if(subBranch.getCode()=="All"){
			subBranch = new CodeItem();
			subBranch.setCode("All");
			subBranch.setDescription("All");
		}
			
		lstSession = null;
		// filterSubBranches = null;
		// subBranch = null;
	}

	@Command
	@NotifyChange({ "lstSession", "totalSize", "filterSubBranches" })
	public void subSearch() {
		if(branch.getCode()=="All"){
			branch = new CodeItem();
			branch.setCode("All");
			branch.setDescription("All");
		}
		
		subBranch = new CodeItem();
		subBranch.setCode("All");
		subBranch.setDescription("All");
		lstSession = null;
		filterSubBranches = null;

	}

	@Command
	@NotifyChange({ "filter", "lstSession", "selected","totalSize","branch","subBranch","logonFrom","logonTo","selectedNumRow"})
	public void onClear() {		
		branch = new CodeItem();
		branch.setCode("All");
		branch.setDescription("All");
		subBranch.setCode("All");
		subBranch.setDescription("All");
		logonFrom = new Date();
		logonTo = new Date();		
		filterSubBranches = null;
		selectedNumRow = new CodeItem();
		selectedNumRow.setCode("10");
		selectedNumRow.setDescription("10");
		filter = "";
		selected = null;
		lstSession = null;

	}

	@NotifyChange({ "lstSession", "totalSize" })
	public List<LoggerAllSession> getLstSession() {
		filter = filter.toLowerCase();
		String conditionB = "";
		String conDate = "";
		if(lstSession != null){
			return lstSession;
		}
		
		if (branch.getCode() != "All") {
			conditionB = " and sl.BRANCH_CODE in(select b.BRANCH_CODE from SYS_BRANCH b where 1=1 "
				+ " and b.parent_id = '" + branch.getId() + "') ";
			if(branch.getId() == 16){
				conditionB = " and sl.branch_code = '"+ branch.getCode() + "' ";
			}
		}
		if (branch.getCode() != "All" && subBranch.getCode() != "All") {
			conditionB = conditionB + " and ss.branch_code = '"+ subBranch.getCode() + "' ";
		}
		
		if(logonFrom != null && logonTo == null){
			String logonFromF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logonFrom);
			logonTo = new Date();
			String logonToF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logonTo);
			
			conDate = " and ss.login_on between to_date('"+ logonFromF+ "','yyyy-mm-dd hh24:mi:ss') and to_date('"+ logonToF+ "','yyyy-mm-dd hh24:mi:ss') ";			
		}else if(logonTo != null && logonFrom == null){
			String logonToF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logonTo);
			conDate = " and ss.login_on <= to_date('"+ logonToF+ "','yyyy-mm-dd hh24:mi:ss')";
		}else if(logonFrom != null && logonTo!=null){
			String logonFromF = new SimpleDateFormat("yyyy-MM-dd").format(logonFrom);			
			String logonToF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logonTo);
			conDate = " and ss.login_on between to_timestamp('"+ logonFromF+ "','yyyy-mm-dd hh24:mi:ss') and to_date('"+ logonToF+ "','yyyy-mm-dd hh24:mi:ss') ";
		}else{
			conDate = "";
		}
		
		String sql = "select ss.username,ss.branch_code,ss.login_on,ss.logout_on,sl.message,sl.module,sl.type,b.name_en "
				+ "from SYS_SESSION ss RIGHT JOIN SYS_SESSION_LOG sl on ss.ID = sl.SESSION_ID "
				+ "LEFT OUTER JOIN SYS_BRANCH b on b.BRANCH_CODE = sl.BRANCH_CODE "
				+ "WHERE 1=1 "
				+ "and ( LOWER(ss.username) like '%"+ filter+ "%' or LOWER(b.name_en) like '%"+ filter+ "%' or LOWER(sl.module) like '%"+ filter+ "%' or LOWER(sl.type) like '%"+ filter+ "%') " 
				+ conDate
				+ " "+conditionB+" ORDER BY ss.id DESC";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			lstSession = con.createQuery(sql)
				.addColumnMapping("username", "username")
				.addColumnMapping("branch_code", "brCd")
				.addColumnMapping("login_on", "loginOn")
				.addColumnMapping("logout_on", "logoutOn")
				.addColumnMapping("name_en", "branchName")
				.addColumnMapping("message", "message")
				.addColumnMapping("module", "module")
				.addColumnMapping("type", "type")	
				.executeAndFetch(LoggerAllSession.class);
		}
		
		 /*if (branch.getCode().toString() == "All") { 
			 pagingList = Ebean.find(LoggerSessionLog.class)
					 .fetch("session")
		             .where()
		             .disjunction().like("session.username", filter) .like("brCd",filter).endJunction() 
		             .between("session.loginOn", logonFrom, logonTo).order() .desc("session.id")
		              .findPagingList(Integer.parseInt(selectedNumRow.getCode()));		              
		 }else{
			 
			 pagingList = Ebean.find(LoggerSessionLog.class)
					 .fetch("session")
		             .where()
		             .disjunction().like("session.username", filter) .like("brCd",filter).endJunction() 
		             .between("session.loginOn", logonFrom, logonTo)
		             .in("brCd", subBranch.getCode())
		             .order() .desc("session.id")
		              .findPagingList(Integer.parseInt(selectedNumRow.getCode()));		          
		 }
		 
		 if (branch.getCode() != "All" && subBranch.getCode() != "All") {
			 pagingList = Ebean.find(LoggerSessionLog.class).fetch("session")
					 .where().disjunction().like("session.username", filter) 
					 .like("brCd", filter).endJunction()
					 .between("session.loginOn", logonFrom, logonTo)
					 .eq("brCd", subBranch.getCode())
					 .order()
					 .desc("session.id")
					 .findPagingList(Integer.parseInt(selectedNumRow.getCode()));			
		 } 		
		 pagingList.getFutureRowCount(); 
		 Page<LoggerSessionLog> page = pagingList.getPage(0); 
		 List<LoggerSessionLog> lst = page.getList();
		totalSize = page.getTotalRowCount(); 
		lstSession = new ListModelList<>(lst);*/
		return lstSession;
	}

	public void setLstSession(List<LoggerAllSession> lstSession) {
		this.lstSession = lstSession;
	}

	public ListModelList<CodeItem> getRows() {
		if (rows != null)
			return rows;
		rows = new ListModelList<CodeItem>();
		String[] desc = new String[] { "5", "10", "15", "20", "25", "30", "40",
				"50" };
		String[] code = new String[] { "5", "10", "15", "20", "25", "30", "40",
				"50" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			rows.add(item);
		}
		return rows;
	}

	public ListModelList<CodeItem> getFilterBranches() {
		if (filterBranches == null) {
			filterBranches = new ListModelList<CodeItem>(getBranchesList());
		}
		return filterBranches;
	}

	public void setFilterBranches(ListModelList<CodeItem> filterBranches) {
		this.filterBranches = filterBranches;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(ListModelList<CodeItem> rows) {
		this.rows = rows;
	}

	/**
	 * @return the selectedNumRow
	 */
	public CodeItem getSelectedNumRow() {
		if (selectedNumRow != null)
			return selectedNumRow;
		selectedNumRow = new CodeItem();
		selectedNumRow.setCode("10");
		selectedNumRow.setDescription("10");
		return selectedNumRow;
	}

	/**
	 * @param selectedNumRow
	 *            the selectedNumRow to set
	 */
	public void setSelectedNumRow(CodeItem selectedNumRow) {
		this.selectedNumRow = selectedNumRow;
	}

	@DefaultCommand
	public void dummy() {

	}

	/**
	 * @return the winSS
	 */
	public Window getWinSS() {
		return winSS;
	}

	/**
	 * @param winSS
	 *            the winCB to set
	 */
	public void setWinSS(Window winSS) {
		this.winSS = winSS;
	}

	public LoggerAllSession getSelected() {
		return selected;
	}

	public void setSelected(LoggerAllSession selected) {
		this.selected = selected;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@GlobalCommand
	@NotifyChange("lstSession")
	public void notifyListLoggerSessionLogChange() {
		lstSession = null;
	}

	/*public int getPageIndex() {
		return pageIndex;
	}
	
	 @NotifyChange("lstSession") 
	 public void setPageIndex(int pageIndex) {
		 this.pageIndex = pageIndex; Page<LoggerSessionLog> page =
		 pagingList.getPage(pageIndex);
		 
		 List<LoggerSessionLog> lst = page.getList(); lstSession = new
		 ListModelList<LoggerSessionLog>(lst); }
	  
	 public PagingList<LoggerSessionLog> getPagingList() { 
		 return pagingList;
	 }
	  
	 public void setPagingList(PagingList<LoggerSessionLog> pagingList) {
	 this.pagingList = pagingList; 
	 }*/
	 

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	private List<CodeItem> getBranchesList() {
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

	private List<CodeItem> getSubBranchesList() {
		List<CodeItem> sub = new ArrayList<CodeItem>();
		try {
			String sql = "SELECT id, branch_code code, name_en description FROM SYS_BRANCH WHERE type ='S' and parent_id = :id ORDER BY name_en";
			
			try (Connection con = Sql2oHelper.sql2o.open()) {
				sub = con.createQuery(sql)
					.addParameter("id", branch.getId())
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
	
}
