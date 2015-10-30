/**
 * 
 */
package kredit.web.kbureau.viewmodel.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.zkoss.json.*;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import kredit.web.core.util.authentication.Priviledge;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.authentication.model.Login;
import kredit.web.core.util.authentication.model.facade.UserCredentialFacade;
import kredit.web.core.util.db.DbHelper;
import kredit.web.kbureau.model.facade.CommonFacade;
import kredit.web.kbureau.model.facade.report.CbcReportFacade;
import kredit.web.kbureau.model.report.Action;
import kredit.web.kbureau.model.report.CbcFee;
import kredit.web.kbureau.model.report.CbcReport;
import kredit.web.kbureau.model.report.CbcReportSummary;
import kredit.web.kbureau.model.report.CodeItem;
import kredit.web.kbureau.model.report.Decision;
import kredit.web.kbureau.model.report.IncomeExpense;
import kredit.web.kbureau.model.report.ParamCbcReport;
import kredit.web.kbureau.model.report.TotalLoan;

/**
 * @author vathenan
 * 
 */
public class CbcReportVM {

	CbcReport selected;
	ListModelList<CbcReport> lst;
	ParamCbcReport param;
	boolean visible;
	String rptUrl;
	Priviledge priviledge = new Priviledge();

	ListModelList<Integer> years;

	ListModelList<CodeItem> rptTypes;

	ListModelList<CodeItem> rptStatuses;

	ListModelList<CodeItem> currencies;

	ListModelList<CodeItem> branches;

	ListModelList<CodeItem> subBranches;

	ListModelList<CodeItem> decisions;

	ListModelList<CodeItem> rows;

	CodeItem selectedNumRow;

	ListModelList<Action> lstActionByDay;
	ListModelList<Action> lstNumActiveAccDetail;
	ListModelList<Action> lstNumActiveAccDetailAmount;
	ListModelList<Action> lstActionByBranch;
	ListModelList<TotalLoan> lstLoanByBranch;
	ListModelList<TotalLoan> lstNumActiveAcc;
	ListModelList<TotalLoan> lstLoanAll;
	ListModelList<Action> lstActionByDecision;
	ListModelList<Decision> lstDecisionByAction;
	ListModelList<Decision> lstDecisionByBranch;
	ListModelList<CbcFee> lstCbcFeeByBranch;
	ListModelList<IncomeExpense> lstIncomeExpenseByBranch;
	
	CbcReportSummary cbcSummary;

	int selectedTabIndex;
	private static final int TAB_SUMMARY = 0;
	private static final int TAB_ACTION = 1;
	private static final int TAB_DECISION = 2;
	private static final int TAB_FEE = 3;
	private static final int TAB_TOTAL_LOAN = 4;
	private static final int TAB_CRD_RPT = 5;
	private static final int TAB_ACTIVE_ACC = 6;

	Login login;
	
	@Wire("#rpt")
	Window rpt;

	@Command
	public void onDecision(){
		
		if(checkPermission()){
			Executions.createComponents("/kbureau/report/Decision.zul", rpt, null);
		}else{
			Clients.showNotification("You have no permission.", true);
		}
	}
	
	@Command
	@NotifyChange({ "lst", "visible"})
	public void onSave(@BindingParam("win") Window winDecision){
		int id = CbcReportFacade.saveDecision(selected);
		if(id != 1)
			Clients.showNotification("Save failed.", "error", null, "middle_center", -1);	
		winDecision.detach();
		if(id == 1)
		{
			visible = false;
			//visibleEnquiry = false;
			Clients.showNotification("Decision successfully saved.");
		}
		
		lst = null;
	}
	
	public boolean checkPermission()
	{
		boolean result = false;
		if((login.getBranchCode().equals("*")) && login.getSubBranchCode().equals("*"))	//Admin
		{
			result = false;
		}
		else if(login.getBranchCode().equals(selected.getBranchCode()) && login.getSubBranchCode().equals("*"))	//BM, ABM
		{
			result = true;
		}
		else if(login.getSubBranchCode().equals((selected.getSubBranchCode()).substring(0, 2)))	//SBM, or lower
		{
			result = true;
		}
		return result;
	}
	
	@Command
	@NotifyChange({ "lst", "visible" })
	public void onApproveClose () {
		if(checkPermission()){
			selected.setDecision(1);
			int id = CbcReportFacade.saveDecision(selected);
			if(id != 1)
				Clients.showNotification("Approve error.", "error", null, "middle_center", -1);
			if(id == 1)
			{
				Clients.showNotification("Successfully approved.");
			}
			
			lst = null;
			visible = false;
		}else{
			Clients.showNotification("You have no permission.", true);
		}
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	private void renderChartSummary() {
		
		String dataAction = getChartDataAction();
		String dataDecision = getChartDataDecision();
		String dataCbcFee = getDataSummaryCbcFee();
		String dataIncomeExpense = getDataSummaryIncomeExpense();
		String dataLoanAll = getChartDataLoanAll();
		String js = "renderChartSummary('" + dataAction + "','" + dataDecision
				+ "','" + dataCbcFee + "','" + dataIncomeExpense + "','"
				+ dataLoanAll + "');";
		Clients.evalJavaScript(js);
		
	}

	private void renderChartAction() {
		String dataActionByDay = getChartDataActionByDay();
		String dataActionByBranch = getChartDataActionByBranch();
		String js = "renderChartAction('" + dataActionByDay + "','"
				+ dataActionByBranch + "');";
		Clients.evalJavaScript(js);
	}

	private void renderChartTotalLoan() {
		String dataLoanByBranch = getChartDataLoanByBranch();
		String js = "renderChartLoan('" + dataLoanByBranch + "');";
		Clients.evalJavaScript(js);
	}
	
	private void renderChartActiveAcc() {
		String dataNumActiveAccDetailAmount = getChartDataActiveAccDetailAmount();
		String dataNumActiveAccDetail = getChartDataActiveAccDetail();
		String js = "renderChartActiveAcc('"+ dataNumActiveAccDetailAmount + "','" + dataNumActiveAccDetail + "');";
		Clients.evalJavaScript(js);
	}
	

	private void renderChartDecision() {
		String dataActionByDecision = getChartDataActionByDecision();
		String dataDecisionByAction = getChartDataDecisionByAction();
		String dataDecisionByBranch = getChartDataDecisionByBranch();
		String js = "renderChartDecision('" + dataActionByDecision + "','"
				+ dataDecisionByAction + "','" + dataDecisionByBranch + "');";
		Clients.evalJavaScript(js);
	}

	private void renderChartFee() {
		String dataCbcFeeByBranch = getDataCbcFeeByBranch();
		String dataIncomeExpenseByBranch = getDataIncomeExpenseByBranch();
		String js = "renderChartFee('" + dataCbcFeeByBranch + "','"
				+ dataIncomeExpenseByBranch + "');";
		Clients.evalJavaScript(js);
	}

	private String getChartDataAction() {
		String data = "";
		try {

			JSONObject obj = new JSONObject();
			JSONArray lst = new JSONArray();

			obj.put("type", "Lite");
			obj.put("value", new Integer(getCbcSummary().getCountLite()));
			lst.add(obj);

			obj = new JSONObject();
			obj.put("type", "Standard");
			obj.put("value", new Integer(getCbcSummary().getCountStd()));
			lst.add(obj);
			
			obj = new JSONObject();
			obj.put("type", "Standard2");
			obj.put("value", new Integer(getCbcSummary().getCountStd2()));
			lst.add(obj);

			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	private String getChartDataDecision() {
		String data = "";
		try {

			JSONObject obj = new JSONObject();
			JSONArray lst = new JSONArray();

			obj.put("type", "Pending");
			obj.put("value", new Integer(getCbcSummary().getCountPending()));
			lst.add(obj);

			obj = new JSONObject();
			obj.put("type", "Approved");
			obj.put("value", new Integer(getCbcSummary().getCountApproved()));
			lst.add(obj);

			obj = new JSONObject();
			obj.put("type", "Rejected");
			obj.put("value", new Integer(getCbcSummary().getCountRejected()));
			lst.add(obj);

			obj = new JSONObject();
			obj.put("type", "Cancel");
			obj.put("value", new Integer(getCbcSummary().getCountCancel()));
			lst.add(obj);

			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	private String getChartDataActionByDay() {
		String data = "";
		try {
			if (getLstActionByDay().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (Action rAction : lstActionByDay) {
				obj = new JSONObject();
				obj.put("date", rAction.getLabel().toString());
				obj.put("lite", new Integer(rAction.getTotalLite()));
				obj.put("std", new Integer(rAction.getTotalStd()));
				obj.put("std2", new Integer(rAction.getTotalStd2()));
				lst.add(obj);
			}

			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}
	
	
	private String getChartDataActiveAccDetailAmount() {
		String data = "";
		try {
			if (getLstActiveAccDetailAmount().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (Action rAction : lstNumActiveAccDetailAmount) {
				obj = new JSONObject();
				obj.put("label", rAction.getLabel().toString());
				Double outside = new Double(rAction.getTotalLite());
				Double kredit = new Double(rAction.getTotalStd());
				obj.put("outside", outside);
				obj.put("kredit", kredit);
				obj.put("total", kredit + outside);
				lst.add(obj);
			}

			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getChartDataActionByBranch() {
		String data = "";
		try {
			if (getLstActionByBranch().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (Action rActionByBranch : lstActionByBranch) {
				obj = new JSONObject();
				obj.put("label", rActionByBranch.getLabel().toString());
				obj.put("lite", new Integer(rActionByBranch.getTotalLite()));
				obj.put("std", new Integer(rActionByBranch.getTotalStd()));
				obj.put("std2", new Integer(rActionByBranch.getTotalStd2()));
				lst.add(obj);
			}
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getChartDataLoanByBranch() {
		String data = "";
		try {
			if (getLstLoanByBranch().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (TotalLoan rTotalLoan : lstLoanByBranch) {
				obj = new JSONObject();
				obj.put("label", rTotalLoan.getLabel().toString());
				obj.put("loan", new Integer(rTotalLoan.getTotalLoan()));
				obj.put("amount", new Float(rTotalLoan.getTotalAmount()));
				lst.add(obj);
			}
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}
	
	private String getChartDataActiveAccDetail() {
		String data = "";
		try {
			if (getLstActiveAccDetail().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (Action rAction : lstNumActiveAccDetail) {
				obj = new JSONObject();
				obj.put("label", rAction.getLabel().toString());
				Double outside = new Double(rAction.getTotalLite());
				Double kredit = new Double(rAction.getTotalStd());
				obj.put("outside", outside);
				obj.put("kredit", kredit);
				obj.put("total", kredit + outside);
				lst.add(obj);
			}

			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}
	

	
	private String getChartDataLoanAll() {
		String data = "";
		try {
			if (getLstLoanAll().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();
			int totalLoan = 0;
			float totalAmount = 0;
			for (TotalLoan rTotalLoan : lstLoanAll) {
				obj = new JSONObject();
				obj.put("label", rTotalLoan.getLabel().toString());
				obj.put("loan", new Integer(rTotalLoan.getTotalLoan()));
				totalLoan += rTotalLoan.getTotalLoan();
				obj.put("amount", new Float(rTotalLoan.getTotalAmount()));
				totalAmount += rTotalLoan.getTotalAmount();
				lst.add(obj);
			}

			obj = new JSONObject();
			obj.put("label", "Total");
			obj.put("loan", totalLoan);
			obj.put("amount", totalAmount);
			lst.add(obj);

			data = lst.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	
	private String getChartDataActionByDecision() {
		String data = "";
		try {
			if (getLstActionByDecision().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (Action rAction : lstActionByDecision) {
				obj = new JSONObject();
				obj.put("label", rAction.getLabel().toString());
				obj.put("lite", new Integer(rAction.getTotalLite()));
				obj.put("std", new Integer(rAction.getTotalStd()));
				obj.put("std2", new Integer(rAction.getTotalStd2()));
				lst.add(obj);
			}
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getChartDataDecisionByAction() {
		String data = "";
		try {
			if (getLstDecisionByAction().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (Decision rDecision : lstDecisionByAction) {
				obj = new JSONObject();
				obj.put("label", rDecision.getLabel().toString());
				obj.put("pending", new Integer(rDecision.getTotalPending()));
				obj.put("approved", new Integer(rDecision.getTotalApproved()));
				obj.put("rejected", new Integer(rDecision.getTotalRejected()));
				obj.put("cancelled", new Integer(rDecision.getTotalCanceled()));
				lst.add(obj);
			}
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getChartDataDecisionByBranch() {
		String data = "";
		try {
			if (getLstDecisionByBranch().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (Decision rDecision : lstDecisionByBranch) {
				obj = new JSONObject();
				obj.put("label", rDecision.getLabel().toString());
				obj.put("pending", new Integer(rDecision.getTotalPending()));
				obj.put("approved", new Integer(rDecision.getTotalApproved()));
				obj.put("rejected", new Integer(rDecision.getTotalRejected()));
				obj.put("cancelled", new Integer(rDecision.getTotalCanceled()));
				lst.add(obj);
			}
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getDataSummaryCbcFee() {
		String data = "";
		try {
			if (getCbcSummary() == null)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			obj = new JSONObject();
			obj.put("type", "Lite");
			obj.put("color", "#99CC33");
			obj.put("value", getCbcSummary().getTotalCbcFeeLite());
			lst.add(obj);

			obj = new JSONObject();
			obj.put("type", "Standard");
			obj.put("color", "#FF9D09");
			obj.put("value", getCbcSummary().getTotalCbcFeeStd() + getCbcSummary().getTotalCbcFeeStd0());
			lst.add(obj);
			
			obj = new JSONObject();
			obj.put("type", "Standard2");
			obj.put("color", "#F66206");
			obj.put("value", getCbcSummary().getTotalCbcFeeStd2());
			lst.add(obj);
			
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getDataSummaryIncomeExpense() {
		String data = "";
		try {
			if (getCbcSummary() == null)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			obj = new JSONObject();
			obj.put("type", "Income");
			obj.put("color", "#99CC33");
			obj.put("value", getCbcSummary().getTotalKreditFee());
			lst.add(obj);

			obj = new JSONObject();
			obj.put("type", "Expense");
			obj.put("color", "#FF9D09");
			obj.put("value", getCbcSummary().getTotalCbcFeeStd0() + getCbcSummary().getTotalCbcFeeStd() + getCbcSummary().getTotalCbcFeeStd2()
					+ getCbcSummary().getTotalCbcFeeLite());
			lst.add(obj);

			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getDataCbcFeeByBranch() {
		String data = "";
		try {
			if (getLstCbcFeeByBranch().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (CbcFee rCbcFee : lstCbcFeeByBranch) {
				obj = new JSONObject();
				obj.put("label", rCbcFee.getLabel().toString());
				obj.put("feeStd", rCbcFee.getFeeStd());
				obj.put("feeStd2", rCbcFee.getFeeStd2());
				obj.put("feeLite", rCbcFee.getFeeLite());
				lst.add(obj);
			}
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	
	private String getDataIncomeExpenseByBranch() {
		String data = "";
		try {
			if (getLstIncomeExpenseByBranch().size() == 0)
				return data;

			JSONObject obj = null;
			JSONArray lst = new JSONArray();

			for (IncomeExpense rIncomeExpense : lstIncomeExpenseByBranch) {
				obj = new JSONObject();
				obj.put("label", rIncomeExpense.getLabel().toString());
				obj.put("income", rIncomeExpense.getIncome());
				obj.put("expense", rIncomeExpense.getExpense());
				lst.add(obj);
			}
			data = lst.toString();
		} catch (Exception e) {

		}
		return data;
	}

	@Init
	public void init(@ExecutionParam("access") Priviledge priviledge) {
		this.priviledge = priviledge;
	}

	public CbcReportVM() {
		login = UserCredentialManager.getIntance().getLogin();
		doSearch();
	}

	@Command
	@NotifyChange({ "lst", "selected", "param" })
	public void doSearch() {

		switch (selectedTabIndex) {
		case TAB_SUMMARY:
			cbcSummary = null;
			lstLoanAll = null;
			renderChartSummary();
			break;
		case TAB_ACTION:
			lstActionByDay = null;
			lstActionByBranch = null;
			renderChartAction();
			break;
		case TAB_DECISION:
			lstActionByDecision = null;
			lstDecisionByAction = null;
			lstDecisionByBranch = null;
			renderChartDecision();
			break;
		case TAB_FEE:
			lstCbcFeeByBranch = null;
			lstIncomeExpenseByBranch = null;
			renderChartFee();
			break;
		case TAB_TOTAL_LOAN:
			lstLoanByBranch = null;
			renderChartTotalLoan();
			break;
		case TAB_ACTIVE_ACC:
			lstNumActiveAccDetailAmount = null;
			lstNumActiveAccDetail = null;
			renderChartActiveAcc();
			break;
		case TAB_CRD_RPT:
			lst = new ListModelList<CbcReport>(
					CbcReportFacade.getListCbcReport(getParam()));
			break;

		}

		selected = null;
	}

	/**
	 * @return the selected
	 */
	public CbcReport getSelected() {
		if (selected == null)
			selected = new CbcReport();
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(CbcReport selected) {
		this.selected = selected;
	}

	/**
	 * @return the lst
	 */
	public ListModelList<CbcReport> getLst() {

		if (lst == null) {
			lst = new ListModelList<>(CbcReportFacade.getListCbcReport(param));
			renderChartSummary();
		}
		return lst;
	};

	/**
	 * @param lst
	 *            the lst to set
	 */
	public void setLst(ListModelList<CbcReport> lst) {
		this.lst = lst;
	}

	/**
	 * @return the param
	 */
	public ParamCbcReport getParam() {
		if (param != null)
			return param;
		Login login = UserCredentialManager.getIntance().getLogin();
		param = new ParamCbcReport();
		CodeItem br = new CodeItem();
		br.setCode(login.getF_branchCode().equals("*") ? "" : login
				.getF_branchCode());
		br.setDescription(login.getF_branch());
		br.setId(login.getF_branchId());
		param.setBranch(br);

		CodeItem sub = new CodeItem();
		sub.setCode(login.getF_subBranchCode().equals("*") ? "" : login
				.getF_subBranchCode());
		sub.setDescription(login.getF_subBranch());
		param.setSubBranch(sub);

		Date today = new Date();
		param.setStartDate(today);
		param.setEndDate(today);

		return param;
	}

	/**
	 * @param param
	 *            the param to set
	 */
	public void setParam(ParamCbcReport param) {
		this.param = param;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Command
	@NotifyChange({ "visible", "rptUrl" })
	public void onView() {
		visible = true;
		String serverRoot = Executions.getCurrent().getServerName();
		String[] rptServer = new String[] { "192.168.111.1", "192.168.100.2" };
		
		String flagTest = ""; //"&flg=t";
		
		if (serverRoot.indexOf("k.kredit") > -1) {
			rptUrl = "http://kbureauplus.kredit.com:2013/kbureaureport.aspx?qid="
					+ selected.getId() + flagTest;
			/*
			int response = 0;
			int i=0;
			do {
				try {
					response = HttpRequest.get(
							"http://" + rptServer[i] + ":2013/kbureaureport.aspx")
							.code();
					
					if(response == 200)
						rptUrl = "http://" + rptServer[i] + ":2013/kbureaureport.aspx?qid="
								+ selected.getId();
					
					i++;
				} catch (Exception e) {
					e.printStackTrace();
					response = 0;
				}
			} while (response != 200 && i< rptServer.length - 1);
			*/
			
		}else if(serverRoot.equals("192.168.111.1") || serverRoot.equals("192.168.111.2")){
			rptUrl = "http://" + rptServer[0] + ":2013/kbureaureport.aspx?qid="
					+ selected.getId()+ flagTest;
		}else if(serverRoot.equals("192.168.100.9") || serverRoot.equals("192.168.100.2")){
			rptUrl = "http://" + rptServer[1] + ":2013/kbureaureport.aspx?qid="
					+ selected.getId()+ flagTest;
		}else{
			rptUrl = "http://kbureauplus.kredit.com:2013/kbureaureport.aspx?qid="
					+ selected.getId()+ flagTest;
		}
	}

	/**
	 * @return the rptUrl
	 */
	public String getRptUrl() {
		return rptUrl;
	}

	/**
	 * @param rptUrl
	 *            the rptUrl to set
	 */
	public void setRptUrl(String rptUrl) {
		this.rptUrl = rptUrl;
	}

	/**
	 * @return the priviledge
	 */
	public Priviledge getPriviledge() {
		return priviledge;
	}

	/**
	 * @param priviledge
	 *            the priviledge to set
	 */
	public void setPriviledge(Priviledge priviledge) {
		this.priviledge = priviledge;
	}

	/**
	 * @return the years
	 */
	public ListModelList<Integer> getYears() {
		if (years == null) {
			years = new ListModelList<Integer>();
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			Integer currentYear = Integer.parseInt(dateFormat
					.format(new Date()));
			int lowYear = currentYear - 101;
			for (Integer i = currentYear; i > lowYear; i--) {
				years.add(i);
			}
		}

		return years;
	}

	/**
	 * @param years
	 *            the years to set
	 */
	public void setYears(ListModelList<Integer> years) {
		this.years = years;
	}

	/**
	 * @return the rptTypes
	 */
	public ListModelList<CodeItem> getRptTypes() {
		if (rptTypes == null) {
			rptTypes = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "Standard", "Lite" };
			String[] code = new String[] { "", "A", "L" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				rptTypes.add(item);
			}
		}
		return rptTypes;
	}

	/**
	 * @param rptTypes
	 *            the rptTypes to set
	 */
	public void setRptTypes(ListModelList<CodeItem> rptTypes) {
		this.rptTypes = rptTypes;
	}

	/**
	 * @return the rptStatuses
	 */
	public ListModelList<CodeItem> getRptStatuses() {
		if (rptStatuses == null) {
			rptStatuses = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "OK", "ERROR", "PARSE" };
			String[] code = new String[] { "", "OK", "ERROR", "PARSE" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				rptStatuses.add(item);
			}
		}
		return rptStatuses;
	}

	/**
	 * @param rptStatuses
	 *            the rptStatuses to set
	 */
	public void setRptStatuses(ListModelList<CodeItem> rptStatuses) {
		this.rptStatuses = rptStatuses;
	}

	/**
	 * @return the currencies
	 */
	public ListModelList<CodeItem> getCurrencies() {
		if (currencies == null) {
			currencies = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "USD", "KHR" };
			String[] code = new String[] { "", "USD", "KHR" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				currencies.add(item);
			}
		}
		return currencies;
	}

	/**
	 * @param currencies
	 *            the currencies to set
	 */
	public void setCurrencies(ListModelList<CodeItem> currencies) {
		this.currencies = currencies;
	}

	/**
	 * @return the branches
	 */
	public ListModelList<CodeItem> getBranches() {
		if (branches == null) {
			branches = new ListModelList<CodeItem>(CommonFacade.getBranches());
		}
		return branches;
	}

	/**
	 * @param branches
	 *            the branches to set
	 */
	public void setBranches(ListModelList<CodeItem> branches) {
		this.branches = branches;
	}

	/**
	 * @return the subBranches
	 */
	public ListModelList<CodeItem> getSubBranches() {
		if (subBranches == null) {
			subBranches = new ListModelList<CodeItem>(
					CommonFacade.getSuBranches(param.getBranch().getId()));
		}
		return subBranches;
	}

	/**
	 * @param subBranches
	 *            the subBranches to set
	 */
	public void setSubBranches(ListModelList<CodeItem> subBranches) {
		this.subBranches = subBranches;
	}

	@Command
	@NotifyChange({ "param", "subBranches", "lst", "selected" })
	public void onChangeBranch() {
		CodeItem item = new CodeItem();
		item.setCode("");
		item.setDescription("All");
		param.setSubBranch(item);
		onDropSubBranch();
		doSearch();
		
		//save filter
		login.setF_branchCode(param.getBranch().getCode().equals("")? "*":param.getBranch().getCode() );
		login.setF_subBranchCode("*");
		UserCredentialFacade.doSaveFilter(login);
	}

	public void onDropSubBranch() {
		subBranches.clear();
		subBranches.addAll(CommonFacade
				.getSuBranches(param.getBranch().getId()));
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeSubBranch() {
		
		CodeItem item = new CodeItem();
		item.setId(param.getSubBranch().getSuperId());
		item.setCode(param.getSubBranch().getSuperCode());
		item.setDescription(param.getSubBranch().getSuperDescription());
		param.setBranch(item);
		doSearch();
		
		// save filter
		login.setF_branchCode(param.getBranch().getCode().equals("")? "*":param.getBranch().getCode() );
		login.setF_subBranchCode(param.getSubBranch().getCode().equals("")? "*":param.getSubBranch().getCode());
		UserCredentialFacade.doSaveFilter(login);
	}

	/**
	 * @return the decisions
	 */
	public ListModelList<CodeItem> getDecisions() {
		if (decisions == null) {
			decisions = new ListModelList<CodeItem>();
			String[] desc = new String[] { "All", "Pending", "Approved",
					"Rejected", "Cancelled" };
			String[] code = new String[] { "2", "0", "1", "-1", "-2" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				decisions.add(item);
			}
		}
		return decisions;
	}

	/**
	 * @param decisions
	 *            the decisions to set
	 */
	public void setDecisions(ListModelList<CodeItem> decisions) {
		this.decisions = decisions;
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeType() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeStatus() {
		if (param.getStatus().getCode().equals("ERROR")
				|| param.getStatus().getCode().equals("PARSE")) {
			CodeItem item = new CodeItem();
			item.setCode("-3");
			item.setDescription("N/A");
			param.setDecision(item);
		} else {
			CodeItem item = new CodeItem();
			item.setCode("2");
			item.setDescription("All");
			param.setDecision(item);
		}
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeStartDate() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeEndDate() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeFromAmount() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeToAmount() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeCurrency() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onChangeDecision() {
		doSearch();
	}

	@Command
	@NotifyChange({ "param", "lst", "selected" })
	public void onClear() {
		param = null;
		doSearch();
	}

	/**
	 * @return the rows
	 */
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

	@Command
	@NotifyChange("selectedNumRow")
	public void onChangeRowPerPage() {
		doSearch();
	}

	/**
	 * @return the lstActionByDay
	 */
	public ListModelList<Action> getLstActionByDay() {
		if (lstActionByDay == null) {
			lstActionByDay = new ListModelList<Action>(
					CbcReportFacade.getListActionByDay(getParam()));
		}
		return lstActionByDay;
	}
	
	public ListModelList<Action> getLstActiveAccDetail() {
		if (lstNumActiveAccDetail == null) {
			lstNumActiveAccDetail = new ListModelList<Action>(
					CbcReportFacade.getListActiveAccDetail(getParam()));
		}
		return lstNumActiveAccDetail;
	}
	
	public ListModelList<Action> getLstActiveAccDetailAmount() {
		if (lstNumActiveAccDetailAmount == null) {
			lstNumActiveAccDetailAmount = new ListModelList<Action>(
					CbcReportFacade.getListActiveAccDetailAmount(getParam()));
		}
		return lstNumActiveAccDetailAmount;
	}

	/**
	 * @param lstActionByDay
	 *            the lstActionByDay to set
	 */
	public void setLstActionByDay(ListModelList<Action> lstTotalDaily) {
		this.lstActionByDay = lstTotalDaily;
	}

	/**
	 * @return the cbcSummary
	 */
	public CbcReportSummary getCbcSummary() {
		if (cbcSummary == null) {
			cbcSummary = CbcReportFacade.getCbcReportSummary(getParam());
		}
		return cbcSummary;
	}

	/**
	 * @param cbcSummary
	 *            the cbcSummary to set
	 */
	public void setCbcSummary(CbcReportSummary cbcSummary) {
		this.cbcSummary = cbcSummary;
	}

	/**
	 * @return the selectedTabIndex
	 */
	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	/**
	 * @param selectedTabIndex
	 *            the selectedTabIndex to set
	 */
	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}

	/**
	 * @return the lstActionByBranch
	 */
	public ListModelList<Action> getLstActionByBranch() {
		if (lstActionByBranch == null) {
			lstActionByBranch = new ListModelList<Action>(
					CbcReportFacade.getListActionByBranch(getParam()));
		}
		return lstActionByBranch;
	}

	public ListModelList<TotalLoan> getLstLoanByBranch() {
		if (lstLoanByBranch == null) {
			lstLoanByBranch = new ListModelList<TotalLoan>(
					CbcReportFacade.getListLoanByBranch(getParam()));
		}
		return lstLoanByBranch;
	}
	
	public ListModelList<TotalLoan> getLstNumActiveAcc() {
		if (lstNumActiveAcc == null) {
			lstNumActiveAcc = new ListModelList<TotalLoan>(
					CbcReportFacade.getListNumActiveAcc(getParam()));
		}
		return lstNumActiveAcc;
	}
	
	public ListModelList<TotalLoan> getLstLoanAll() {
		if (lstLoanAll == null) {
			lstLoanAll = new ListModelList<TotalLoan>(
					CbcReportFacade.getListLoanAll(getParam()));
		}
		return lstLoanAll;
	}

	/**
	 * @param lstActionByBranch
	 *            the lstActionByBranch to set
	 */
	public void setLstActionByBranch(ListModelList<Action> lstActionByBranch) {
		this.lstActionByBranch = lstActionByBranch;
	}

	/**
	 * @return the lstActionByDecision
	 */
	public ListModelList<Action> getLstActionByDecision() {
		if (lstActionByDecision == null) {
			lstActionByDecision = new ListModelList<Action>(
					CbcReportFacade.getListActionByDecision(getParam()));
		}
		return lstActionByDecision;
	}

	/**
	 * @param lstActionByDecision
	 *            the lstActionByDecision to set
	 */
	public void setLstActionByDecision(ListModelList<Action> lstActionByDecision) {
		this.lstActionByDecision = lstActionByDecision;
	}

	/**
	 * @return the lstDecisionByAction
	 */
	public ListModelList<Decision> getLstDecisionByAction() {
		if (lstDecisionByAction == null) {
			lstDecisionByAction = new ListModelList<Decision>(
					CbcReportFacade.getListDecisionByAction(getParam()));
		}
		return lstDecisionByAction;
	}

	/**
	 * @param lstDecisionByAction
	 *            the lstDecisionByAction to set
	 */
	public void setLstDecisionByAction(
			ListModelList<Decision> lstDecisionByAction) {
		this.lstDecisionByAction = lstDecisionByAction;
	}

	/**
	 * @return the lstDecisionByBranch
	 */
	public ListModelList<Decision> getLstDecisionByBranch() {
		if (lstDecisionByBranch == null) {
			lstDecisionByBranch = new ListModelList<Decision>(
					CbcReportFacade.getListDecisionByBranch(getParam()));
		}
		return lstDecisionByBranch;
	}

	/**
	 * @param lstDecisionByBranch
	 *            the lstDecisionByBranch to set
	 */
	public void setLstDecisionByBranch(
			ListModelList<Decision> lstDecisionByBranch) {
		this.lstDecisionByBranch = lstDecisionByBranch;
	}

	/**
	 * @return the lstCbcFeeByBranch
	 */
	public ListModelList<CbcFee> getLstCbcFeeByBranch() {
		if (lstCbcFeeByBranch == null)
			lstCbcFeeByBranch = new ListModelList<CbcFee>(
					CbcReportFacade.getListCbcFeeByBranch(getParam()));
		return lstCbcFeeByBranch;
	}

	/**
	 * @param lstCbcFeeByBranch
	 *            the lstCbcFeeByBranch to set
	 */
	public void setLstCbcFeeByBranch(ListModelList<CbcFee> lstCbcFeeByBranch) {
		this.lstCbcFeeByBranch = lstCbcFeeByBranch;
	}

	/**
	 * @return the lstIncomeExpenseByBranch
	 */
	public ListModelList<IncomeExpense> getLstIncomeExpenseByBranch() {
		if (lstIncomeExpenseByBranch == null)
			lstIncomeExpenseByBranch = new ListModelList<IncomeExpense>(
					CbcReportFacade.getListIncomeExpenseByBranch(getParam()));
		return lstIncomeExpenseByBranch;
	}

	/**
	 * @param lstIncomeExpenseByBranch
	 *            the lstIncomeExpenseByBranch to set
	 */
	public void setLstIncomeExpenseByBranch(
			ListModelList<IncomeExpense> lstIncomeExpenseByBranch) {
		this.lstIncomeExpenseByBranch = lstIncomeExpenseByBranch;
	}

	@Command
	public void onShowReport() {

		try {

			/*
			 * 
			 * //String path =
			 * Executions.getCurrent().getDesktop().getWebApp().getRealPath
			 * ("/kbureau/report/ActionByDayRprt.jasper"); //JasperReport
			 * jasperReport = JasperCompileManager.compileReport(path); WebApp
			 * webApp = Executions.getCurrent().getDesktop().getWebApp();
			 * InputStream inputStream =
			 * webApp.getResourceAsStream("/rpt/test.jasper"); Connection conn =
			 * DbHelper.getConnection();
			 * 
			 * Map<String, Object> params = new HashMap<String, Object>();
			 * params.put("branch", ""); params.put("sub_branch", "");
			 * params.put("report_type", ""); params.put("status", "");
			 * params.put("from_date", ""); params.put("to_date", "");
			 * params.put("from_amount", new Double(0)); params.put("to_amount",
			 * new Double(0)); params.put("currency", "");
			 * params.put("decision", 2); params.put("name", "");
			 * params.put("filter_info", "");
			 * 
			 * //JasperPrint jasperPrint =
			 * JasperFillManager.fillReport(jasperReport, params, conn);
			 * JasperPrint jasperPrint =
			 * JasperFillManager.fillReport(inputStream, null, conn);
			 * inputStream.close(); conn.close();
			 * JasperViewer.viewReport(jasperPrint, false);
			 */
			ExpXLS();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ExpXLS() {
		try {
			// JRXhtmlExporter exporter=new JRXhtmlExporter();

			JRXlsxExporter exporter = new JRXlsxExporter();
			// request.getSession(true).setAttribute("IMAGES_MAP", imageMap);
			//
			// response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// response.setHeader("Content-Disposition",
			// "attachment;filename=example.xlsx");
			ServletOutputStream out = null;
			try {
				out = ((HttpServletResponse) Executions.getCurrent()
						.getNativeResponse()).getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Connection conn = DbHelper.getConnection();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("branch", param.getBranch().getCode());
			params.put("sub_branch", param.getSubBranch().getCode());
			params.put("report_type", param.getRptType().getCode());
			params.put("status", param.getStatus().getCode());
			params.put("from_date", param.getFromDate());
			params.put("to_date", param.getToDate());
			double from_amount = param.getFromAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getFromAmount());
			double to_amount = param.getToAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getToAmount());
			params.put("from_amount", from_amount);
			params.put("to_amount", to_amount);
			params.put("currency", param.getCurrency().getCode());
			params.put("decision",
					Integer.parseInt(param.getDecision().getCode()));
			params.put("name", param.getFilter());
			params.put("filter_info", "");

			WebApp webApp = Executions.getCurrent().getDesktop().getWebApp();

			InputStream inputStream = webApp
					.getResourceAsStream("/rpt/ActionByDayRprt.jasper");

			JasperPrint jpPrint = JasperFillManager.fillReport(inputStream,
					params, conn);

			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jpPrint);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);

			exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
					Boolean.TRUE);
			exporter.setParameter(
					JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);

			exporter.setParameter(
					JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
					Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
			exporter.exportReport();
		}

		catch (JRException ex) {
			ex.printStackTrace();

		}
	}

	public void xls2() throws JRException {
		try {
			long start = System.currentTimeMillis();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("branch", param.getBranch().getCode());
			params.put("sub_branch", param.getSubBranch().getCode());
			params.put("report_type", param.getRptType().getCode());
			params.put("status", param.getStatus().getCode());
			params.put("from_date", param.getFromDate());
			params.put("to_date", param.getToDate());
			double from_amount = param.getFromAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getFromAmount());
			double to_amount = param.getToAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getToAmount());
			params.put("from_amount", from_amount);
			params.put("to_amount", to_amount);
			params.put("currency", param.getCurrency().getCode());
			params.put("decision",
					Integer.parseInt(param.getDecision().getCode()));
			params.put("name", param.getFilter());
			params.put("filter_info", "");

			Connection conn = DbHelper.getConnection();

			WebApp webApp = Executions.getCurrent().getDesktop().getWebApp();

			JasperFillManager.fillReportToFile(webApp.getRealPath("/rpt")
					+ "/ActionByDayRprt.jasper", params, conn);

			// InputStream inputStream =
			// webApp.getResourceAsStream("/rpt/ActionByDayRprt.jasper");
			// JasperPrint jasperPrint =
			// JasperFillManager.fillReport(inputStream, params, conn);

			File sourceFile = new File(webApp.getRealPath("/rpt")
					+ "/ActionByDayRprt.jrprint");
			JasperPrint jasperPrint = (JasperPrint) JRLoader
					.loadObject(sourceFile);

			File destFile = new File(sourceFile.getParent(),
					jasperPrint.getName() + ".xls");

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			OutputStream outputfile = null;
			try {
				outputfile = new FileOutputStream(destFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			JExcelApiExporter exporterXLS = new JExcelApiExporter();

			exporterXLS.setParameter(JExcelApiExporterParameter.JASPER_PRINT,
					jasperPrint);
			exporterXLS.setParameter(
					JExcelApiExporterParameter.OUTPUT_FILE_NAME,
					destFile.toString());
			exporterXLS.setParameter(
					JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,

					Boolean.FALSE);
			exporterXLS.setParameter(
					JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,

					Boolean.TRUE);
			exporterXLS.setParameter(
					JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,

					Boolean.FALSE);
			exporterXLS
					.setParameter(
							JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,

							Boolean.TRUE);
			exporterXLS.exportReport();

			/*
			 * 
			 * JRXlsExporter exporter = new JRXlsExporter();
			 * 
			 * exporter.setParameter(JRExporterParameter.JASPER_PRINT,
			 * jasperPrint);
			 * exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
			 * destFile.toString());
			 * exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
			 * Boolean.TRUE);
			 * exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET
			 * , Boolean.TRUE);
			 * exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN
			 * , Boolean.FALSE);
			 * 
			 * exporter.exportReport();
			 */

			System.err.println("XLS creation time : "
					+ (System.currentTimeMillis() - start));

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * try { inputStream.close(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public void xls() throws JRException {
		try {
			long start = System.currentTimeMillis();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("branch", param.getBranch().getCode());
			params.put("sub_branch", param.getSubBranch().getCode());
			params.put("report_type", param.getRptType().getCode());
			params.put("status", param.getStatus().getCode());
			params.put("from_date", param.getFromDate());
			params.put("to_date", param.getToDate());
			double from_amount = param.getFromAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getFromAmount());
			double to_amount = param.getToAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getToAmount());
			params.put("from_amount", from_amount);
			params.put("to_amount", to_amount);
			params.put("currency", param.getCurrency().getCode());
			params.put("decision",
					Integer.parseInt(param.getDecision().getCode()));
			params.put("name", param.getFilter());
			params.put("filter_info", "");

			Connection conn = DbHelper.getConnection();

			WebApp webApp = Executions.getCurrent().getDesktop().getWebApp();

			JasperFillManager.fillReportToFile(webApp.getRealPath("/rpt")
					+ "/ActionByDayRprt.jasper", params, conn);

			// InputStream inputStream =
			// webApp.getResourceAsStream("/rpt/ActionByDayRprt.jasper");
			// JasperPrint jasperPrint =
			// JasperFillManager.fillReport(inputStream, params, conn);

			File sourceFile = new File(webApp.getRealPath("/rpt")
					+ "/ActionByDayRprt.jrprint");
			JasperPrint jasperPrint = (JasperPrint) JRLoader
					.loadObject(sourceFile);

			File destFile = new File(sourceFile.getParent(),
					jasperPrint.getName() + ".xls");

			JRXlsExporter exporter = new JRXlsExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
					destFile.toString());
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);

			exporter.exportReport();

			System.err.println("XLS creation time : "
					+ (System.currentTimeMillis() - start));

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * try { inputStream.close(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	private AMedia getAMedia(String fileName, String fileExt, String contentType) {
		AMedia media = null;
		try {

			/*
			 * 
			 * //String path =
			 * Executions.getCurrent().getDesktop().getWebApp().getRealPath
			 * ("/kbureau/report/ActionByDayRprt.jasper"); //JasperReport
			 * jasperReport = JasperCompileManager.compileReport(path); WebApp
			 * webApp = Executions.getCurrent().getDesktop().getWebApp();
			 * InputStream inputStream =
			 * webApp.getResourceAsStream("/rpt/test.jasper"); Connection conn =
			 * DbHelper.getConnection();
			 * 
			 * Map<String, Object> params = new HashMap<String, Object>();
			 * params.put("branch", ""); params.put("sub_branch", "");
			 * params.put("report_type", ""); params.put("status", "");
			 * params.put("from_date", ""); params.put("to_date", "");
			 * params.put("from_amount", new Double(0)); params.put("to_amount",
			 * new Double(0)); params.put("currency", "");
			 * params.put("decision", 2); params.put("name", "");
			 * params.put("filter_info", "");
			 * 
			 * //JasperPrint jasperPrint =
			 * JasperFillManager.fillReport(jasperReport, params, conn);
			 * JasperPrint jasperPrint =
			 * JasperFillManager.fillReport(inputStream, null, conn);
			 * inputStream.close(); conn.close();
			 * JasperViewer.viewReport(jasperPrint, false);
			 */

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("branch", param.getBranch().getCode());
			params.put("sub_branch", param.getSubBranch().getCode());
			params.put("report_type", param.getRptType().getCode());
			params.put("status", param.getStatus().getCode());
			params.put("from_date", param.getFromDate());
			params.put("to_date", param.getToDate());
			double from_amount = param.getFromAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getFromAmount());
			double to_amount = param.getToAmount().isEmpty() ? 0 : Double
					.parseDouble(param.getToAmount());
			params.put("from_amount", from_amount);
			params.put("to_amount", to_amount);
			params.put("currency", param.getCurrency().getCode());
			params.put("decision",
					Integer.parseInt(param.getDecision().getCode()));
			params.put("name", param.getFilter());
			params.put("filter_info", "");

			Connection conn = DbHelper.getConnection();

			WebApp webApp = Executions.getCurrent().getDesktop().getWebApp();
			InputStream inputStream = webApp
					.getResourceAsStream("/rpt/ActionByDayRprt.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream,
					params, conn);

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			JasperExportManager.exportReportToPdfStream(jasperPrint, out);

			media = new AMedia(fileName, fileExt,
					"application/pdf;charset=UTF-8", out.toByteArray());

			conn.close();
			inputStream.close();
			out.close();

			/*
			 * AMedia media = new AMedia("test", "xls",
			 * "application/vnd.ms-excel", out.toByteArray());
			 * 
			 * Window win = (Window) Executions.createComponents(
			 * "/kbureau/report/report.zul", null, null); Iframe iframe =
			 * (Iframe) win.getFellow("rptFrame"); iframe.setContent(media);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

		return media;
	}

	private String tagreport(String string) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		return string + calendar.get(calendar.MONTH)
				+ calendar.get(calendar.DAY_OF_MONTH)
				+ calendar.get(calendar.YEAR) + calendar.get(calendar.HOUR)
				+ calendar.get(calendar.MINUTE) + calendar.get(calendar.SECOND);
	}

	private String renderTableLoanSummary() {
		StringBuilder strBuilder = new StringBuilder();
		try {
			strBuilder
					.append("<table class=\"table table-bordered table-striped\">");
			strBuilder.append("    <thead>");
			strBuilder.append("        <tr>");
			strBuilder.append("            <th>");
			strBuilder.append("            </th>");
			strBuilder.append("            <th>");
			strBuilder.append("                Loan");
			strBuilder.append("            </th>");
			strBuilder.append("            <th>");
			strBuilder.append("                Amount in USD");
			strBuilder.append("            </th>");
			strBuilder.append("        </tr>");
			strBuilder.append("    </thead>");
			strBuilder.append("    <tbody>");
			strBuilder.append("        <tr>");
			strBuilder.append("            <td>");
			strBuilder.append("                KHR");
			strBuilder.append("            </td>");
			strBuilder.append("            <td>");
			strBuilder.append("                20000");
			strBuilder.append("            </td>");
			strBuilder.append("            <td>");
			strBuilder.append("                $ 984775");
			strBuilder.append("            </td>");
			strBuilder.append("        </tr>");
			strBuilder.append("        <tr>");
			strBuilder.append("            <td>");
			strBuilder.append("                USD");
			strBuilder.append("            </td>");
			strBuilder.append("            <td>");
			strBuilder.append("                20000");
			strBuilder.append("            </td>");
			strBuilder.append("            <td>");
			strBuilder.append("                $ 984775");
			strBuilder.append("            </td>");
			strBuilder.append("        </tr>");
			strBuilder.append("        <tr>");
			strBuilder.append("            <td>");
			strBuilder.append("                Total");
			strBuilder.append("            </td>");
			strBuilder.append("            <td>");
			strBuilder.append("                20000");
			strBuilder.append("            </td>");
			strBuilder.append("            <td>");
			strBuilder.append("                $ 984775");
			strBuilder.append("            </td>");
			strBuilder.append("        </tr>");
			strBuilder.append("    </tbody>");
			strBuilder.append("</table>");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return strBuilder.toString();
	}
	
	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	@Command
	@NotifyChange({ "visible" })
	public void onSaveRefChange(@BindingParam("change") String change){
		/*
		if(!change.contains("_"))
			selected.setRefNumber(selected.getLoanId() + "_" + change);
		else
		*/
		selected.setRefNumber(selected.getLoanId() + change);
		if(CbcReportFacade.saveNewRef(selected) == 1){
			Clients.showNotification("Number Reference was saved", true);
			visible = false;
		}
	}
}
