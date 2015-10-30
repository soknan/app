package kredit.web.opp.data.vm;

import java.util.Date;
import java.util.Map;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.loanLate.model.Branch;
import kredit.web.loanLate.model.facade.LoanLateFacade;
import kredit.web.opp.data.model.OpCoSummary;
import kredit.web.opp.data.model.OpCommon;
import kredit.web.opp.data.model.OpLoan;
import kredit.web.opp.data.model.OpLoanDetail;
import kredit.web.opp.data.model.OpPlan;
import kredit.web.opp.data.model.OpPlanCo;
import kredit.web.opp.data.model.OpSaving;
import kredit.web.opp.data.model.OpSavingDetail;
import kredit.web.opp.data.model.OpStaff;
import kredit.web.opp.data.model.OpStaffSummary;
import kredit.web.opp.data.model.OpSubSummary;
import kredit.web.opp.data.model.ParamBranches;
import kredit.web.opp.data.model.ParamCo;
import kredit.web.opp.data.model.ParamCommune;
import kredit.web.opp.data.model.ParamPlanList;
import kredit.web.opp.data.model.SysCo;
import kredit.web.opp.data.model.SysCommune;
import kredit.web.opp.data.model.TotalSummary;
import kredit.web.opp.data.model.facade.OperationFacade;
import kredit.web.security.model.facade.SecurityFacade;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;

public class MainVM {
	
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	int rowPerPage = 10;
	Privilege privilege = null;
	
	private ListModelList<OpPlan> lstPlan;
	private ListModelList<SysCo> lstCo;
	private ListModelList<OpStaff> lstStaff;
	
	private OpPlan selectedPlan = new OpPlan();
	private OpPlanCo selectedPlanCo = new OpPlanCo();
	private SysCo selectedCo = new SysCo();
	private SysCo selectedNewCo = new SysCo();
	private OpLoan selectedLoan = new OpLoan();
	private SysCommune selectedSysCommune = new SysCommune();
	private OpSaving selectedSaving = new OpSaving();
	private OpLoanDetail selectedLoanDetail = new OpLoanDetail();
	private OpSavingDetail selectedSavingDetail = new OpSavingDetail();
	
	private ParamPlanList param = new ParamPlanList();
	private ParamCo paramCo = new ParamCo();
	private ParamCommune paramPlanCo = new ParamCommune();
	
	private boolean visiblePlnInput = false;
	private boolean visibleLoanList = false;
	private boolean visibleLoanInput = false;
	private boolean visibleSavingList = false;
	private boolean visibleSavingInput = false;
	private boolean visiblePlnCo = false;
	private boolean visibleCoList = false;
	private boolean visibleNewCo = false;
	private boolean visibleCommuneList = false;
	private boolean visibleSumAS = false;
	
	private boolean disableNewCo = true;
	private boolean disablePlanCo = true;
	private boolean disableLoanInput = true;
	private boolean disableBrPlanCo = true;
	
	@Wire("#plns")
	Window plns;
	
	private int planCoTabIndex;
	
	private ListModelList<CodeItem> months;
	private CodeItem selectedMonth;
	
	private ListModelList<CodeItem> coSubBranchlist;
	private ListModelList<CodeItem> planCOSubBranchlist;
	private ListModelList<CodeItem> lstLoanPrd;
	private ListModelList<CodeItem> lstSavingPrd;
	private ListModelList<CodeItem> lstCurrency;
	
	private ListModelList<OpCoSummary> lstCoLoanSummary;
	private ListModelList<OpCoSummary> lstCoSavingSummary;
	
	private ListModelList<OpSubSummary> lstSubLoanSummary;
	private ListModelList<OpSubSummary> lstSubSavingSummary;
	
	private ListModelList<OpSubSummary> lstSubLoanSummaryByProduct;
	private ListModelList<OpSubSummary> lstSubSavingSummaryByProduct;
	
	private ListModelList<CodeItem> branchList;
	private ListModelList<CodeItem> subBranchList;
	
	private ParamBranches paramBranches = new ParamBranches();
	
	private ListModelList<OpSubSummary> lstAllSubLoanSummary;
	private ListModelList<OpSubSummary> lstAllSubSavingSummary;
	
	private ListModelList<OpSubSummary> lstAllSubLoanSummaryByProduct;
	private ListModelList<OpSubSummary> lstAllSubSavingSummaryByProduct;
	
	private Double loan_amt_avg_01 = 0.0;
	private Double loan_amt_avg_02 = 0.0;
	private Double loan_amt_avg_03 = 0.0;
	private Double loan_amt_avg_04 = 0.0;
	
	private Double saving_amt_avg_01 = 0.0;
	private Double saving_amt_avg_02 = 0.0;
	private Double saving_amt_avg_03 = 0.0;
	private Double saving_amt_avg_04 = 0.0;
	
	private Branch brItem = new Branch();
	
	private TotalSummary lstCoLoanSummaryTotal;
	private TotalSummary lstCoSavingSummaryTotal;
	
	private TotalSummary lstSubLoanSummaryTotal;
	private TotalSummary lstSubSavingSummaryTotal;
	private TotalSummary lstSubLoanSummaryByProductTotal;
	private TotalSummary lstSubSavingSummaryByProductTotal;
	
	private TotalSummary lstAllSubLoanSummaryTotal;
	private TotalSummary lstAllSubSavingSummaryTotal;
	private TotalSummary lstAllSubLoanSummaryByProductTotal;
	private TotalSummary lstAllSubSavingSummaryByProductTotal;
	
	private ListModelList<CodeItem> branchListPlanInput;
	private ListModelList<CodeItem> subBranchListPlanInput;
	
	private boolean disableBr = true;
	private boolean disableSbr = true;
	
	private OpStaff selectedPlanStaff = new OpStaff();
	private boolean visiblePlnStaff = false;
	private ListModelList<CodeItem> lstPosition;
	
	private ListModelList<OpStaffSummary> lstStaffSummary;
	private TotalSummary lstStaffSummaryTotal;

	private String label1 = "";
	private String label2 = "";
	private String label3 = "";
	private String label4 = "";
	private String label5 = "";
	private String label6 = "";
	
//GETTER AND SETTER

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
	
	public void setRows(ListModelList<CodeItem> rows) {
		this.rows = rows;
	}
	
	public CodeItem getSelectedNumRow() {
		if (selectedNumRow != null)
			return selectedNumRow;
		selectedNumRow = new CodeItem();
		selectedNumRow.setCode("10");
		selectedNumRow.setDescription("10");
		return selectedNumRow;
	}
	
	public void setSelectedNumRow(CodeItem selectedNumRow) {
		this.selectedNumRow = selectedNumRow;
	}
	
	public int getRowPerPage() {
		return rowPerPage;
	}
	
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	
	public Privilege getPrivilege() {
		if (privilege == null) {
			privilege = Common.getPrivilege(CMD.LIST_PLAN);
		}
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public ListModelList<OpPlan> getLstPlan() {
		if(lstPlan == null) {
			lstPlan = new ListModelList<OpPlan>(OperationFacade.getPlanList(param));
		}
		return lstPlan;
	}

	public void setLstPlan(ListModelList<OpPlan> lstPlan) {
		this.lstPlan = lstPlan;
	}

	public ListModelList<SysCo> getLstCo() {
		if(lstCo == null) {
			lstCo = new ListModelList<SysCo>(OperationFacade.getCoList(paramCo, brItem));
		}
		return lstCo;
	}

	public void setLstCo(ListModelList<SysCo> lstCo) {
		this.lstCo = lstCo;
	}

	public ListModelList<OpStaff> getLstStaff() {
		if(lstStaff == null) {
			lstStaff = new ListModelList<OpStaff>();
		}
		return lstStaff;
	}

	public void setLstStaff(ListModelList<OpStaff> lstStaff) {
		this.lstStaff = lstStaff;
	}

	public boolean isVisiblePlnInput() {
		return visiblePlnInput;
	}

	public void setVisiblePlnInput(boolean visiblePlnInput) {
		this.visiblePlnInput = visiblePlnInput;
	}

	public boolean isVisibleLoanList() {
		return visibleLoanList;
	}

	public void setVisibleLoanList(boolean visibleLoanList) {
		this.visibleLoanList = visibleLoanList;
	}

	public boolean isVisibleLoanInput() {
		return visibleLoanInput;
	}

	public void setVisibleLoanInput(boolean visibleLoanInput) {
		this.visibleLoanInput = visibleLoanInput;
	}

	public boolean isDisableLoanInput() {
		return disableLoanInput;
	}

	public void setDisableLoanInput(boolean disableLoanInput) {
		this.disableLoanInput = disableLoanInput;
	}

	public boolean isVisibleSavingList() {
		return visibleSavingList;
	}

	public void setVisibleSavingList(boolean visibleSavingList) {
		this.visibleSavingList = visibleSavingList;
	}

	public boolean isVisibleSavingInput() {
		return visibleSavingInput;
	}

	public void setVisibleSavingInput(boolean visibleSavingInput) {
		this.visibleSavingInput = visibleSavingInput;
	}

	public boolean isVisiblePlnCo() {
		return visiblePlnCo;
	}

	public void setVisiblePlnCo(boolean visiblePlnCo) {
		this.visiblePlnCo = visiblePlnCo;
	}

	public boolean isVisibleCoList() {
		return visibleCoList;
	}

	public void setVisibleCoList(boolean visibleCoList) {
		this.visibleCoList = visibleCoList;
	}

	public boolean isVisibleNewCo() {
		return visibleNewCo;
	}

	public void setVisibleNewCo(boolean visibleNewCo) {
		this.visibleNewCo = visibleNewCo;
	}
	
	public boolean isVisibleCommuneList() {
		return visibleCommuneList;
	}

	public void setVisibleCommuneList(boolean visibleCommuneList) {
		this.visibleCommuneList = visibleCommuneList;
	}

	public boolean isVisibleSumAS() {
		return visibleSumAS;
	}

	public void setVisibleSumAS(boolean visibleSumAS) {
		this.visibleSumAS = visibleSumAS;
	}

	public int getPlanCoTabIndex() {
		return planCoTabIndex;
	}

	public void setPlanCoTabIndex(int planCoTabIndex) {
		this.planCoTabIndex = planCoTabIndex;
	}
	
	public OpPlan getSelectedPlan() {
		return selectedPlan;
	}

	public void setSelectedPlan(OpPlan selectedPlan) {
		this.selectedPlan = selectedPlan;
	}
	
	public OpPlanCo getSelectedPlanCo() {
		return selectedPlanCo;
	}

	public void setSelectedPlanCo(OpPlanCo selectedPlanCo) {
		this.selectedPlanCo = selectedPlanCo;
	}

	public SysCo getSelectedCo() {
		return selectedCo;
	}

	public void setSelectedCo(SysCo selectedCo) {
		this.selectedCo = selectedCo;
	}
	
	public SysCo getSelectedNewCo() {
		return selectedNewCo;
	}

	public void setSelectedNewCo(SysCo selectedNewCo) {
		this.selectedNewCo = selectedNewCo;
	}

	public OpLoan getSelectedLoan() {
		return selectedLoan;
	}

	public void setSelectedLoan(OpLoan selectedLoan) {
		this.selectedLoan = selectedLoan;
	}

	public SysCommune getSelectedSysCommune() {
		return selectedSysCommune;
	}

	public void setSelectedSysCommune(SysCommune selectedSysCommune) {
		this.selectedSysCommune = selectedSysCommune;
	}

	public OpSaving getSelectedSaving() {
		return selectedSaving;
	}

	public void setSelectedSaving(OpSaving selectedSaving) {
		this.selectedSaving = selectedSaving;
	}

	public OpLoanDetail getSelectedLoanDetail() {
		return selectedLoanDetail;
	}

	public void setSelectedLoanDetail(OpLoanDetail selectedLoanDetail) {
		this.selectedLoanDetail = selectedLoanDetail;
	}

	public OpSavingDetail getSelectedSavingDetail() {
		return selectedSavingDetail;
	}

	public void setSelectedSavingDetail(OpSavingDetail selectedSavingDetail) {
		this.selectedSavingDetail = selectedSavingDetail;
	}

	public ParamPlanList getParam() {
		return param;
	}

	public void setParam(ParamPlanList param) {
		this.param = param;
	}
	
	public ParamCo getParamCo() {
		return paramCo;
	}

	public void setParamCo(ParamCo paramCo) {
		this.paramCo = paramCo;
	}

	public ParamCommune getParamPlanCo() {
		return paramPlanCo;
	}

	public void setParamPlanCo(ParamCommune paramPlanCo) {
		this.paramPlanCo = paramPlanCo;
	}

	public boolean isDisableNewCo() {
		return disableNewCo;
	}

	public void setDisableNewCo(boolean disableNewCo) {
		this.disableNewCo = disableNewCo;
	}
	
	public boolean isDisablePlanCo() {
		return disablePlanCo;
	}

	public void setDisablePlanCo(boolean disablePlanCo) {
		this.disablePlanCo = disablePlanCo;
	}

	public boolean isDisableBrPlanCo() {
		return disableBrPlanCo;
	}

	public void setDisableBrPlanCo(boolean disableBrPlanCo) {
		this.disableBrPlanCo = disableBrPlanCo;
	}

	public ListModelList<CodeItem> getMonths() {
		if (months != null)
			return months;
		months = new ListModelList<CodeItem>();
		
		String[] desc = new String[] { "January", "February", 
									   "March", "April",  
									   "May", "June", 
									   "July", "August", 
									   "September", "October", 
									   "November", "December"};
		String[] code = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", 
									   "Sep", "Oct", "Nov", "Dec" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setId(i);
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			months.add(item);
		}
		return months;
	}

	public void setMonths(ListModelList<CodeItem> months) {
		this.months = months;
	}

	public CodeItem getSelectedMonth() {
		return selectedMonth;
	}

	public void setSelectedMonth(CodeItem selectedMonth) {
		this.selectedMonth = selectedMonth;
	}
	
	public ListModelList<CodeItem> getCoSubBranchlist() {
		if(coSubBranchlist == null) {
			coSubBranchlist = new ListModelList<CodeItem>(OperationFacade.getSubBranchesListNoAll());
		}
		return coSubBranchlist;
	}

	public void setCoSubBranchlist(ListModelList<CodeItem> coSubBranchlist) {
		this.coSubBranchlist = coSubBranchlist;
	}

	public ListModelList<CodeItem> getPlanCOSubBranchlist() {
		if(planCOSubBranchlist == null) {
			planCOSubBranchlist = new ListModelList<CodeItem>(OperationFacade.getSubBranchesListNoAll());
		}
		return planCOSubBranchlist;
	}

	public void setPlanCOSubBranchlist(ListModelList<CodeItem> planCOSubBranchlist) {
		this.planCOSubBranchlist = planCOSubBranchlist;
	}

	public ListModelList<CodeItem> getLstLoanPrd() {
		if(lstLoanPrd == null) {
			lstLoanPrd = new ListModelList<CodeItem>(OperationFacade.getLoanProductList());
		}
		return lstLoanPrd;
	}

	public void setLstLoanPrd(ListModelList<CodeItem> lstLoanPrd) {
		this.lstLoanPrd = lstLoanPrd;
	}

	public ListModelList<CodeItem> getLstSavingPrd() {
		if(lstSavingPrd == null) {
			lstSavingPrd = new ListModelList<CodeItem>(OperationFacade.getSavingProductList());
		}
		return lstSavingPrd;
	}

	public void setLstSavingPrd(ListModelList<CodeItem> lstSavingPrd) {
		this.lstSavingPrd = lstSavingPrd;
	}
	
	public ListModelList<CodeItem> getLstCurrency() {
		if(lstCurrency == null) {
			lstCurrency = new ListModelList<CodeItem>(OperationFacade.getCurrencyList());
		}
		return lstCurrency;
	}

	public void setLstCurrency(ListModelList<CodeItem> lstCurrency) {
		this.lstCurrency = lstCurrency;
	}
	
	public ListModelList<OpCoSummary> getLstCoLoanSummary() {
		if(lstCoLoanSummary == null) {
			lstCoLoanSummary = new ListModelList<OpCoSummary>(OperationFacade.getCoSummaryList(selectedLoan.getId(), OpCommon.OP_LOAN));
		}
		
		if(lstCoLoanSummary == null) {
			lstCoLoanSummary = new ListModelList<OpCoSummary>();
		}
		
		return lstCoLoanSummary;
	}

	public void setLstCoLoanSummary(ListModelList<OpCoSummary> lstCoLoanSummary) {
		this.lstCoLoanSummary = lstCoLoanSummary;
	}

	public ListModelList<OpCoSummary> getLstCoSavingSummary() {
		if(lstCoSavingSummary == null) {
			lstCoSavingSummary = new ListModelList<OpCoSummary>(OperationFacade.getCoSummaryList(selectedSaving.getId(), OpCommon.OP_SAVING));
		}
		
		if(lstCoSavingSummary == null) {
			lstCoSavingSummary = new ListModelList<OpCoSummary>();
		}
		
		return lstCoSavingSummary;
	}

	public void setLstCoSavingSummary(ListModelList<OpCoSummary> lstCoSavingSummary) {
		this.lstCoSavingSummary = lstCoSavingSummary;
	}
	
	public ListModelList<OpSubSummary> getLstSubLoanSummary() {
		if(lstSubLoanSummary == null) {
			lstSubLoanSummary = new ListModelList<OpSubSummary>(OperationFacade.getSubSummaryList(selectedPlan.getId(), OpCommon.OP_LOAN, brItem, paramPlanCo));
		}
		return lstSubLoanSummary;
	}

	public void setLstSubLoanSummary(ListModelList<OpSubSummary> lstSubLoanSummary) {
		this.lstSubLoanSummary = lstSubLoanSummary;
	}

	public ListModelList<OpSubSummary> getLstSubSavingSummary() {
		if(lstSubSavingSummary == null) {
			lstSubSavingSummary = new ListModelList<OpSubSummary>(OperationFacade.getSubSummaryList(selectedPlan.getId(), OpCommon.OP_SAVING, brItem, paramPlanCo));
		}
		return lstSubSavingSummary;
	}

	public ListModelList<OpSubSummary> getLstSubLoanSummaryByProduct() {
		if(lstSubLoanSummaryByProduct == null) {
			lstSubLoanSummaryByProduct = new ListModelList<OpSubSummary>(OperationFacade.getSubSummaryListByProduct(selectedPlan.getId(), OpCommon.OP_LOAN, brItem, paramPlanCo));
		}
		return lstSubLoanSummaryByProduct;
	}

	public void setLstSubLoanSummaryByProduct(
			ListModelList<OpSubSummary> lstSubLoanSummaryByProduct) {
		this.lstSubLoanSummaryByProduct = lstSubLoanSummaryByProduct;
	}

	public ListModelList<OpSubSummary> getLstSubSavingSummaryByProduct() {
		if(lstSubSavingSummaryByProduct == null) {
			lstSubSavingSummaryByProduct = new ListModelList<OpSubSummary>(OperationFacade.getSubSummaryListByProduct(selectedPlan.getId(), OpCommon.OP_SAVING, brItem, paramPlanCo));
		}
		return lstSubSavingSummaryByProduct;
	}

	public void setLstSubSavingSummaryByProduct(
			ListModelList<OpSubSummary> lstSubSavingSummaryByProduct) {
		this.lstSubSavingSummaryByProduct = lstSubSavingSummaryByProduct;
	}

	public void setLstSubSavingSummary(
			ListModelList<OpSubSummary> lstSubSavingSummary) {
		this.lstSubSavingSummary = lstSubSavingSummary;
	}
	
	public Double getLoan_amt_avg_01() {
		return loan_amt_avg_01;
	}

	public void setLoan_amt_avg_01(Double loan_amt_avg_01) {
		this.loan_amt_avg_01 = loan_amt_avg_01;
	}

	public Double getLoan_amt_avg_02() {
		return loan_amt_avg_02;
	}

	public void setLoan_amt_avg_02(Double loan_amt_avg_02) {
		this.loan_amt_avg_02 = loan_amt_avg_02;
	}

	public Double getLoan_amt_avg_03() {
		return loan_amt_avg_03;
	}

	public void setLoan_amt_avg_03(Double loan_amt_avg_03) {
		this.loan_amt_avg_03 = loan_amt_avg_03;
	}

	public Double getLoan_amt_avg_04() {
		return loan_amt_avg_04;
	}

	public void setLoan_amt_avg_04(Double loan_amt_avg_04) {
		this.loan_amt_avg_04 = loan_amt_avg_04;
	}

	public Double getSaving_amt_avg_01() {
		return saving_amt_avg_01;
	}

	public void setSaving_amt_avg_01(Double saving_amt_avg_01) {
		this.saving_amt_avg_01 = saving_amt_avg_01;
	}

	public Double getSaving_amt_avg_02() {
		return saving_amt_avg_02;
	}

	public void setSaving_amt_avg_02(Double saving_amt_avg_02) {
		this.saving_amt_avg_02 = saving_amt_avg_02;
	}

	public Double getSaving_amt_avg_03() {
		return saving_amt_avg_03;
	}

	public void setSaving_amt_avg_03(Double saving_amt_avg_03) {
		this.saving_amt_avg_03 = saving_amt_avg_03;
	}

	public Double getSaving_amt_avg_04() {
		return saving_amt_avg_04;
	}

	public void setSaving_amt_avg_04(Double saving_amt_avg_04) {
		this.saving_amt_avg_04 = saving_amt_avg_04;
	}
	
	public ListModelList<CodeItem> getBranchList() {
		if(branchList == null) {
			branchList = new ListModelList<CodeItem>(LoanLateFacade.getBranchesListWithAll());
		}
		
		return branchList;
	}

	public void setBranchList(ListModelList<CodeItem> branchList) {
		this.branchList = branchList;
	}

	public ListModelList<CodeItem> getSubBranchList() {
		if(subBranchList == null) {
			subBranchList = new ListModelList<CodeItem>(LoanLateFacade.getSubBranchesListWithAll(paramBranches.getBranch().getId()));
		}
		return subBranchList;
	}

	public void setSubBranchList(ListModelList<CodeItem> subBranchList) {
		this.subBranchList = subBranchList;
	}

	public ParamBranches getParamBranches() {
		return paramBranches;
	}

	public void setParamBranches(ParamBranches paramBranches) {
		this.paramBranches = paramBranches;
	}

	public ListModelList<OpSubSummary> getLstAllSubLoanSummary() {
		if(lstAllSubLoanSummary == null) {
			lstAllSubLoanSummary = new ListModelList<OpSubSummary>(OperationFacade.getAllSubSummaryList(selectedPlan.getId(), OpCommon.OP_LOAN, paramBranches));
		}
		return lstAllSubLoanSummary;
	}

	public void setLstAllSubLoanSummary(
			ListModelList<OpSubSummary> lstAllSubLoanSummary) {
		this.lstAllSubLoanSummary = lstAllSubLoanSummary;
	}

	public ListModelList<OpSubSummary> getLstAllSubSavingSummary() {
		if(lstAllSubSavingSummary == null) {
			lstAllSubSavingSummary = new ListModelList<OpSubSummary>(OperationFacade.getAllSubSummaryList(selectedPlan.getId(), OpCommon.OP_SAVING, paramBranches));
		}
		return lstAllSubSavingSummary;
	}

	public void setLstAllSubSavingSummary(
			ListModelList<OpSubSummary> lstAllSubSavingSummary) {
		this.lstAllSubSavingSummary = lstAllSubSavingSummary;
	}
	
	public ListModelList<OpSubSummary> getLstAllSubLoanSummaryByProduct() {
		if(lstAllSubLoanSummaryByProduct == null) {
			lstAllSubLoanSummaryByProduct = new ListModelList<OpSubSummary>(OperationFacade.getAllSubSummaryListByProduct(selectedPlan.getId(), OpCommon.OP_LOAN, paramBranches));
		}
		return lstAllSubLoanSummaryByProduct;
	}

	public void setLstAllSubLoanSummaryByProduct(
			ListModelList<OpSubSummary> lstAllSubLoanSummaryByProduct) {
		this.lstAllSubLoanSummaryByProduct = lstAllSubLoanSummaryByProduct;
	}

	public ListModelList<OpSubSummary> getLstAllSubSavingSummaryByProduct() {
		if(lstAllSubSavingSummaryByProduct == null) {
			lstAllSubSavingSummaryByProduct = new ListModelList<OpSubSummary>(OperationFacade.getAllSubSummaryListByProduct(selectedPlan.getId(), OpCommon.OP_SAVING, paramBranches));
		}
		return lstAllSubSavingSummaryByProduct;
	}

	public void setLstAllSubSavingSummaryByProduct(
			ListModelList<OpSubSummary> lstAllSubSavingSummaryByProduct) {
		this.lstAllSubSavingSummaryByProduct = lstAllSubSavingSummaryByProduct;
	}
	
	public TotalSummary getLstCoLoanSummaryTotal() {
		if(lstCoLoanSummaryTotal == null) {
			lstCoLoanSummaryTotal = new TotalSummary();
			if(lstCoLoanSummary != null) {
				for(int i = 0; i < lstCoLoanSummary.size(); i++) {
					lstCoLoanSummaryTotal.setTotalY0Cli(lstCoLoanSummaryTotal.getTotalY0Cli() + lstCoLoanSummary.get(i).getY0_cli());
					lstCoLoanSummaryTotal.setTotalY0Amt(lstCoLoanSummaryTotal.getTotalY0Amt() + lstCoLoanSummary.get(i).getY0_amt());
					lstCoLoanSummaryTotal.setTotalY1Cli(lstCoLoanSummaryTotal.getTotalY1Cli() + lstCoLoanSummary.get(i).getY1_cli());
					lstCoLoanSummaryTotal.setTotalY1Amt(lstCoLoanSummaryTotal.getTotalY1Amt() + lstCoLoanSummary.get(i).getY1_amt());
					lstCoLoanSummaryTotal.setTotalY2Cli(lstCoLoanSummaryTotal.getTotalY2Cli() + lstCoLoanSummary.get(i).getY2_cli());
					lstCoLoanSummaryTotal.setTotalY2Amt(lstCoLoanSummaryTotal.getTotalY2Amt() + lstCoLoanSummary.get(i).getY2_amt());
					lstCoLoanSummaryTotal.setTotalY3Cli(lstCoLoanSummaryTotal.getTotalY3Cli() + lstCoLoanSummary.get(i).getY3_cli());
					lstCoLoanSummaryTotal.setTotalY3Amt(lstCoLoanSummaryTotal.getTotalY3Amt() + lstCoLoanSummary.get(i).getY3_amt());
				}
			}
		}
		
		return lstCoLoanSummaryTotal;
	}

	public void setLstCoLoanSummaryTotal(TotalSummary lstCoLoanSummaryTotal) {
		this.lstCoLoanSummaryTotal = lstCoLoanSummaryTotal;
	}

	public TotalSummary getLstCoSavingSummaryTotal() {
		if(lstCoSavingSummaryTotal == null) {
			lstCoSavingSummaryTotal = new TotalSummary();
			if(lstCoSavingSummary != null) {
				for(int i = 0; i < lstCoSavingSummary.size(); i++) {
					lstCoSavingSummaryTotal.setTotalY0Cli(lstCoSavingSummaryTotal.getTotalY0Cli() + lstCoSavingSummary.get(i).getY0_cli());
					lstCoSavingSummaryTotal.setTotalY0Amt(lstCoSavingSummaryTotal.getTotalY0Amt() + lstCoSavingSummary.get(i).getY0_amt());
					lstCoSavingSummaryTotal.setTotalY1Cli(lstCoSavingSummaryTotal.getTotalY1Cli() + lstCoSavingSummary.get(i).getY1_cli());
					lstCoSavingSummaryTotal.setTotalY1Amt(lstCoSavingSummaryTotal.getTotalY1Amt() + lstCoSavingSummary.get(i).getY1_amt());
					lstCoSavingSummaryTotal.setTotalY2Cli(lstCoSavingSummaryTotal.getTotalY2Cli() + lstCoSavingSummary.get(i).getY2_cli());
					lstCoSavingSummaryTotal.setTotalY2Amt(lstCoSavingSummaryTotal.getTotalY2Amt() + lstCoSavingSummary.get(i).getY2_amt());
					lstCoSavingSummaryTotal.setTotalY3Cli(lstCoSavingSummaryTotal.getTotalY3Cli() + lstCoSavingSummary.get(i).getY3_cli());
					lstCoSavingSummaryTotal.setTotalY3Amt(lstCoSavingSummaryTotal.getTotalY3Amt() + lstCoSavingSummary.get(i).getY3_amt());
				}
			}
		}
		
		return lstCoSavingSummaryTotal;
	}

	public void setLstCoSavingSummaryTotal(TotalSummary lstCoSavingSummaryTotal) {
		this.lstCoSavingSummaryTotal = lstCoSavingSummaryTotal;
	}

	public TotalSummary getLstSubLoanSummaryTotal() {
		if(lstSubLoanSummaryTotal == null) {
			lstSubLoanSummaryTotal = new TotalSummary();
			if(lstSubLoanSummary != null) {
				for(int i = 0; i < lstSubLoanSummary.size(); i++) {
					lstSubLoanSummaryTotal.setTotalY0Cli(lstSubLoanSummaryTotal.getTotalY0Cli() + lstSubLoanSummary.get(i).getY0_cli());
					lstSubLoanSummaryTotal.setTotalY0Amt(lstSubLoanSummaryTotal.getTotalY0Amt() + lstSubLoanSummary.get(i).getY0_amt());
					lstSubLoanSummaryTotal.setTotalY1Cli(lstSubLoanSummaryTotal.getTotalY1Cli() + lstSubLoanSummary.get(i).getY1_cli());
					lstSubLoanSummaryTotal.setTotalY1Amt(lstSubLoanSummaryTotal.getTotalY1Amt() + lstSubLoanSummary.get(i).getY1_amt());
					lstSubLoanSummaryTotal.setTotalY2Cli(lstSubLoanSummaryTotal.getTotalY2Cli() + lstSubLoanSummary.get(i).getY2_cli());
					lstSubLoanSummaryTotal.setTotalY2Amt(lstSubLoanSummaryTotal.getTotalY2Amt() + lstSubLoanSummary.get(i).getY2_amt());
					lstSubLoanSummaryTotal.setTotalY3Cli(lstSubLoanSummaryTotal.getTotalY3Cli() + lstSubLoanSummary.get(i).getY3_cli());
					lstSubLoanSummaryTotal.setTotalY3Amt(lstSubLoanSummaryTotal.getTotalY3Amt() + lstSubLoanSummary.get(i).getY3_amt());
				}
			}
		}
		
		return lstSubLoanSummaryTotal;
	}

	public void setLstSubLoanSummaryTotal(TotalSummary lstSubLoanSummaryTotal) {
		this.lstSubLoanSummaryTotal = lstSubLoanSummaryTotal;
	}
	
	public TotalSummary getLstSubSavingSummaryTotal() {
		if(lstSubSavingSummaryTotal == null) {
			lstSubSavingSummaryTotal = new TotalSummary();
			if(lstSubSavingSummary != null) {
				for(int i = 0; i < lstSubSavingSummary.size(); i++) {
					lstSubSavingSummaryTotal.setTotalY0Cli(lstSubSavingSummaryTotal.getTotalY0Cli() + lstSubSavingSummary.get(i).getY0_cli());
					lstSubSavingSummaryTotal.setTotalY0Amt(lstSubSavingSummaryTotal.getTotalY0Amt() + lstSubSavingSummary.get(i).getY0_amt());
					lstSubSavingSummaryTotal.setTotalY1Cli(lstSubSavingSummaryTotal.getTotalY1Cli() + lstSubSavingSummary.get(i).getY1_cli());
					lstSubSavingSummaryTotal.setTotalY1Amt(lstSubSavingSummaryTotal.getTotalY1Amt() + lstSubSavingSummary.get(i).getY1_amt());
					lstSubSavingSummaryTotal.setTotalY2Cli(lstSubSavingSummaryTotal.getTotalY2Cli() + lstSubSavingSummary.get(i).getY2_cli());
					lstSubSavingSummaryTotal.setTotalY2Amt(lstSubSavingSummaryTotal.getTotalY2Amt() + lstSubSavingSummary.get(i).getY2_amt());
					lstSubSavingSummaryTotal.setTotalY3Cli(lstSubSavingSummaryTotal.getTotalY3Cli() + lstSubSavingSummary.get(i).getY3_cli());
					lstSubSavingSummaryTotal.setTotalY3Amt(lstSubSavingSummaryTotal.getTotalY3Amt() + lstSubSavingSummary.get(i).getY3_amt());
				}
			}
		}
		
		return lstSubSavingSummaryTotal;
	}

	public void setLstSubSavingSummaryTotal(TotalSummary lstSubSavingSummaryTotal) {
		this.lstSubSavingSummaryTotal = lstSubSavingSummaryTotal;
	}

	public TotalSummary getLstSubLoanSummaryByProductTotal() {
		if(lstSubLoanSummaryByProductTotal == null) {
			lstSubLoanSummaryByProductTotal = new TotalSummary();
			if(lstSubLoanSummaryByProduct != null) {
				for(int i = 0; i < lstSubLoanSummaryByProduct.size(); i++) {
					lstSubLoanSummaryByProductTotal.setTotalY0Cli(lstSubLoanSummaryByProductTotal.getTotalY0Cli() + lstSubLoanSummaryByProduct.get(i).getY0_cli());
					lstSubLoanSummaryByProductTotal.setTotalY0Amt(lstSubLoanSummaryByProductTotal.getTotalY0Amt() + lstSubLoanSummaryByProduct.get(i).getY0_amt());
					lstSubLoanSummaryByProductTotal.setTotalY1Cli(lstSubLoanSummaryByProductTotal.getTotalY1Cli() + lstSubLoanSummaryByProduct.get(i).getY1_cli());
					lstSubLoanSummaryByProductTotal.setTotalY1Amt(lstSubLoanSummaryByProductTotal.getTotalY1Amt() + lstSubLoanSummaryByProduct.get(i).getY1_amt());
					lstSubLoanSummaryByProductTotal.setTotalY2Cli(lstSubLoanSummaryByProductTotal.getTotalY2Cli() + lstSubLoanSummaryByProduct.get(i).getY2_cli());
					lstSubLoanSummaryByProductTotal.setTotalY2Amt(lstSubLoanSummaryByProductTotal.getTotalY2Amt() + lstSubLoanSummaryByProduct.get(i).getY2_amt());
					lstSubLoanSummaryByProductTotal.setTotalY3Cli(lstSubLoanSummaryByProductTotal.getTotalY3Cli() + lstSubLoanSummaryByProduct.get(i).getY3_cli());
					lstSubLoanSummaryByProductTotal.setTotalY3Amt(lstSubLoanSummaryByProductTotal.getTotalY3Amt() + lstSubLoanSummaryByProduct.get(i).getY3_amt());
				}
			}
		}
		
		return lstSubLoanSummaryByProductTotal;
	}

	public void setLstSubLoanSummaryByProductTotal(
			TotalSummary lstSubLoanSummaryByProductTotal) {
		this.lstSubLoanSummaryByProductTotal = lstSubLoanSummaryByProductTotal;
	}

	public TotalSummary getLstSubSavingSummaryByProductTotal() {
		if(lstSubSavingSummaryByProductTotal == null) {
			lstSubSavingSummaryByProductTotal = new TotalSummary();
			if(lstSubSavingSummaryByProduct != null) {
				for(int i = 0; i < lstSubSavingSummaryByProduct.size(); i++) {
					lstSubSavingSummaryByProductTotal.setTotalY0Cli(lstSubSavingSummaryByProductTotal.getTotalY0Cli() + lstSubSavingSummaryByProduct.get(i).getY0_cli());
					lstSubSavingSummaryByProductTotal.setTotalY0Amt(lstSubSavingSummaryByProductTotal.getTotalY0Amt() + lstSubSavingSummaryByProduct.get(i).getY0_amt());
					lstSubSavingSummaryByProductTotal.setTotalY1Cli(lstSubSavingSummaryByProductTotal.getTotalY1Cli() + lstSubSavingSummaryByProduct.get(i).getY1_cli());
					lstSubSavingSummaryByProductTotal.setTotalY1Amt(lstSubSavingSummaryByProductTotal.getTotalY1Amt() + lstSubSavingSummaryByProduct.get(i).getY1_amt());
					lstSubSavingSummaryByProductTotal.setTotalY2Cli(lstSubSavingSummaryByProductTotal.getTotalY2Cli() + lstSubSavingSummaryByProduct.get(i).getY2_cli());
					lstSubSavingSummaryByProductTotal.setTotalY2Amt(lstSubSavingSummaryByProductTotal.getTotalY2Amt() + lstSubSavingSummaryByProduct.get(i).getY2_amt());
					lstSubSavingSummaryByProductTotal.setTotalY3Cli(lstSubSavingSummaryByProductTotal.getTotalY3Cli() + lstSubSavingSummaryByProduct.get(i).getY3_cli());
					lstSubSavingSummaryByProductTotal.setTotalY3Amt(lstSubSavingSummaryByProductTotal.getTotalY3Amt() + lstSubSavingSummaryByProduct.get(i).getY3_amt());
				}
			}
		}
		
		return lstSubSavingSummaryByProductTotal;
	}

	public void setLstSubSavingSummaryByProductTotal(
			TotalSummary lstSubSavingSummaryByProductTotal) {
		this.lstSubSavingSummaryByProductTotal = lstSubSavingSummaryByProductTotal;
	}
	
	public TotalSummary getLstAllSubLoanSummaryTotal() {
		if(lstAllSubLoanSummaryTotal == null) {
			lstAllSubLoanSummaryTotal = new TotalSummary();
			if(lstAllSubLoanSummary != null) {
				for(int i = 0; i < lstAllSubLoanSummary.size(); i++) {
					lstAllSubLoanSummaryTotal.setTotalY0Cli(lstAllSubLoanSummaryTotal.getTotalY0Cli() + lstAllSubLoanSummary.get(i).getY0_cli());
					lstAllSubLoanSummaryTotal.setTotalY0Amt(lstAllSubLoanSummaryTotal.getTotalY0Amt() + lstAllSubLoanSummary.get(i).getY0_amt());
					lstAllSubLoanSummaryTotal.setTotalY1Cli(lstAllSubLoanSummaryTotal.getTotalY1Cli() + lstAllSubLoanSummary.get(i).getY1_cli());
					lstAllSubLoanSummaryTotal.setTotalY1Amt(lstAllSubLoanSummaryTotal.getTotalY1Amt() + lstAllSubLoanSummary.get(i).getY1_amt());
					lstAllSubLoanSummaryTotal.setTotalY2Cli(lstAllSubLoanSummaryTotal.getTotalY2Cli() + lstAllSubLoanSummary.get(i).getY2_cli());
					lstAllSubLoanSummaryTotal.setTotalY2Amt(lstAllSubLoanSummaryTotal.getTotalY2Amt() + lstAllSubLoanSummary.get(i).getY2_amt());
					lstAllSubLoanSummaryTotal.setTotalY3Cli(lstAllSubLoanSummaryTotal.getTotalY3Cli() + lstAllSubLoanSummary.get(i).getY3_cli());
					lstAllSubLoanSummaryTotal.setTotalY3Amt(lstAllSubLoanSummaryTotal.getTotalY3Amt() + lstAllSubLoanSummary.get(i).getY3_amt());
				}
			}
		}
		
		return lstAllSubLoanSummaryTotal;
	}

	public void setLstAllSubLoanSummaryTotal(TotalSummary lstAllSubLoanSummaryTotal) {
		this.lstAllSubLoanSummaryTotal = lstAllSubLoanSummaryTotal;
	}

	public TotalSummary getLstAllSubSavingSummaryTotal() {
		if(lstAllSubSavingSummaryTotal == null) {
			lstAllSubSavingSummaryTotal = new TotalSummary();
			if(lstAllSubSavingSummary != null) {
				for(int i = 0; i < lstAllSubSavingSummary.size(); i++) {
					lstAllSubSavingSummaryTotal.setTotalY0Cli(lstAllSubSavingSummaryTotal.getTotalY0Cli() + lstAllSubSavingSummary.get(i).getY0_cli());
					lstAllSubSavingSummaryTotal.setTotalY0Amt(lstAllSubSavingSummaryTotal.getTotalY0Amt() + lstAllSubSavingSummary.get(i).getY0_amt());
					lstAllSubSavingSummaryTotal.setTotalY1Cli(lstAllSubSavingSummaryTotal.getTotalY1Cli() + lstAllSubSavingSummary.get(i).getY1_cli());
					lstAllSubSavingSummaryTotal.setTotalY1Amt(lstAllSubSavingSummaryTotal.getTotalY1Amt() + lstAllSubSavingSummary.get(i).getY1_amt());
					lstAllSubSavingSummaryTotal.setTotalY2Cli(lstAllSubSavingSummaryTotal.getTotalY2Cli() + lstAllSubSavingSummary.get(i).getY2_cli());
					lstAllSubSavingSummaryTotal.setTotalY2Amt(lstAllSubSavingSummaryTotal.getTotalY2Amt() + lstAllSubSavingSummary.get(i).getY2_amt());
					lstAllSubSavingSummaryTotal.setTotalY3Cli(lstAllSubSavingSummaryTotal.getTotalY3Cli() + lstAllSubSavingSummary.get(i).getY3_cli());
					lstAllSubSavingSummaryTotal.setTotalY3Amt(lstAllSubSavingSummaryTotal.getTotalY3Amt() + lstAllSubSavingSummary.get(i).getY3_amt());
				}
			}
		}
		
		return lstAllSubSavingSummaryTotal;
	}

	public void setLstAllSubSavingSummaryTotal(
			TotalSummary lstAllSubSavingSummaryTotal) {
		this.lstAllSubSavingSummaryTotal = lstAllSubSavingSummaryTotal;
	}

	public TotalSummary getLstAllSubLoanSummaryByProductTotal() {
		if(lstAllSubLoanSummaryByProductTotal == null) {
			lstAllSubLoanSummaryByProductTotal = new TotalSummary();
			if(lstAllSubLoanSummaryByProduct != null) {
				for(int i = 0; i < lstAllSubLoanSummaryByProduct.size(); i++) {
					lstAllSubLoanSummaryByProductTotal.setTotalY0Cli(lstAllSubLoanSummaryByProductTotal.getTotalY0Cli() + lstAllSubLoanSummaryByProduct.get(i).getY0_cli());
					lstAllSubLoanSummaryByProductTotal.setTotalY0Amt(lstAllSubLoanSummaryByProductTotal.getTotalY0Amt() + lstAllSubLoanSummaryByProduct.get(i).getY0_amt());
					lstAllSubLoanSummaryByProductTotal.setTotalY1Cli(lstAllSubLoanSummaryByProductTotal.getTotalY1Cli() + lstAllSubLoanSummaryByProduct.get(i).getY1_cli());
					lstAllSubLoanSummaryByProductTotal.setTotalY1Amt(lstAllSubLoanSummaryByProductTotal.getTotalY1Amt() + lstAllSubLoanSummaryByProduct.get(i).getY1_amt());
					lstAllSubLoanSummaryByProductTotal.setTotalY2Cli(lstAllSubLoanSummaryByProductTotal.getTotalY2Cli() + lstAllSubLoanSummaryByProduct.get(i).getY2_cli());
					lstAllSubLoanSummaryByProductTotal.setTotalY2Amt(lstAllSubLoanSummaryByProductTotal.getTotalY2Amt() + lstAllSubLoanSummaryByProduct.get(i).getY2_amt());
					lstAllSubLoanSummaryByProductTotal.setTotalY3Cli(lstAllSubLoanSummaryByProductTotal.getTotalY3Cli() + lstAllSubLoanSummaryByProduct.get(i).getY3_cli());
					lstAllSubLoanSummaryByProductTotal.setTotalY3Amt(lstAllSubLoanSummaryByProductTotal.getTotalY3Amt() + lstAllSubLoanSummaryByProduct.get(i).getY3_amt());
				}
			}
		}
		
		return lstAllSubLoanSummaryByProductTotal;
	}

	public void setLstAllSubLoanSummaryByProductTotal(
			TotalSummary lstAllSubLoanSummaryByProductTotal) {
		this.lstAllSubLoanSummaryByProductTotal = lstAllSubLoanSummaryByProductTotal;
	}

	public TotalSummary getLstAllSubSavingSummaryByProductTotal() {
		if(lstAllSubSavingSummaryByProductTotal == null) {
			lstAllSubSavingSummaryByProductTotal = new TotalSummary();
			if(lstAllSubSavingSummaryByProduct != null) {
				for(int i = 0; i < lstAllSubSavingSummaryByProduct.size(); i++) {
					lstAllSubSavingSummaryByProductTotal.setTotalY0Cli(lstAllSubSavingSummaryByProductTotal.getTotalY0Cli() + lstAllSubSavingSummaryByProduct.get(i).getY0_cli());
					lstAllSubSavingSummaryByProductTotal.setTotalY0Amt(lstAllSubSavingSummaryByProductTotal.getTotalY0Amt() + lstAllSubSavingSummaryByProduct.get(i).getY0_amt());
					lstAllSubSavingSummaryByProductTotal.setTotalY1Cli(lstAllSubSavingSummaryByProductTotal.getTotalY1Cli() + lstAllSubSavingSummaryByProduct.get(i).getY1_cli());
					lstAllSubSavingSummaryByProductTotal.setTotalY1Amt(lstAllSubSavingSummaryByProductTotal.getTotalY1Amt() + lstAllSubSavingSummaryByProduct.get(i).getY1_amt());
					lstAllSubSavingSummaryByProductTotal.setTotalY2Cli(lstAllSubSavingSummaryByProductTotal.getTotalY2Cli() + lstAllSubSavingSummaryByProduct.get(i).getY2_cli());
					lstAllSubSavingSummaryByProductTotal.setTotalY2Amt(lstAllSubSavingSummaryByProductTotal.getTotalY2Amt() + lstAllSubSavingSummaryByProduct.get(i).getY2_amt());
					lstAllSubSavingSummaryByProductTotal.setTotalY3Cli(lstAllSubSavingSummaryByProductTotal.getTotalY3Cli() + lstAllSubSavingSummaryByProduct.get(i).getY3_cli());
					lstAllSubSavingSummaryByProductTotal.setTotalY3Amt(lstAllSubSavingSummaryByProductTotal.getTotalY3Amt() + lstAllSubSavingSummaryByProduct.get(i).getY3_amt());
				}
			}
		}
		
		return lstAllSubSavingSummaryByProductTotal;
	}

	public void setLstAllSubSavingSummaryByProductTotal(
			TotalSummary lstAllSubSavingSummaryByProductTotal) {
		this.lstAllSubSavingSummaryByProductTotal = lstAllSubSavingSummaryByProductTotal;
	}
	
	public ListModelList<CodeItem> getBranchListPlanInput() {
		if(branchListPlanInput == null) {
			branchListPlanInput = new ListModelList<CodeItem>(LoanLateFacade.getBranchesListWithAll());
		}
		
		return branchListPlanInput;
	}

	public void setBranchListPlanInput(ListModelList<CodeItem> branchListPlanInput) {
		this.branchListPlanInput = branchListPlanInput;
	}

	public ListModelList<CodeItem> getSubBranchListPlanInput() {
		if(subBranchListPlanInput == null) {
			subBranchListPlanInput = new ListModelList<CodeItem>(LoanLateFacade.getSubBranchesListWithAll(paramPlanCo.getBranch().getId()));
		}
		
		return subBranchListPlanInput;
	}

	public void setSubBranchListPlanInput(
			ListModelList<CodeItem> subBranchListPlanInput) {
		this.subBranchListPlanInput = subBranchListPlanInput;
	}
	
	public boolean isDisableBr() {
		return disableBr;
	}

	public void setDisableBr(boolean disableBr) {
		this.disableBr = disableBr;
	}

	public boolean isDisableSbr() {
		return disableSbr;
	}

	public void setDisableSbr(boolean disableSbr) {
		this.disableSbr = disableSbr;
	}
	
	public OpStaff getSelectedPlanStaff() {
		return selectedPlanStaff;
	}

	public void setSelectedPlanStaff(OpStaff selectedPlanStaff) {
		this.selectedPlanStaff = selectedPlanStaff;
	}
	
	public boolean isVisiblePlnStaff() {
		return visiblePlnStaff;
	}

	public void setVisiblePlnStaff(boolean visiblePlnStaff) {
		this.visiblePlnStaff = visiblePlnStaff;
	}
	
	public ListModelList<CodeItem> getLstPosition() {
		if(lstPosition == null) {
			lstPosition = new ListModelList<CodeItem>(OperationFacade.getPositionList());
		}
		
		return lstPosition;
	}

	public void setLstPosition(ListModelList<CodeItem> lstPosition) {
		this.lstPosition = lstPosition;
	}
	
	public ListModelList<OpStaffSummary> getLstStaffSummary() {
		if(lstStaffSummary == null) {
			lstStaffSummary = new ListModelList<OpStaffSummary>(OperationFacade.getStaffSummaryList(selectedPlan.getId(), brItem, paramPlanCo));
		}
		
		return lstStaffSummary;
	}

	public void setLstStaffSummary(ListModelList<OpStaffSummary> lstStaffSummary) {
		this.lstStaffSummary = lstStaffSummary;
	}

	public TotalSummary getLstStaffSummaryTotal() {
		if(lstStaffSummaryTotal == null) {
			lstStaffSummaryTotal = new TotalSummary();
			if(lstStaffSummary != null) {
				for(int i = 0; i < lstStaffSummary.size(); i++) {
					lstStaffSummaryTotal.setTotalNy0(lstStaffSummaryTotal.getTotalNy0() + lstStaffSummary.get(i).getNy0());
					lstStaffSummaryTotal.setTotalNy1q1(lstStaffSummaryTotal.getTotalNy1q1() + lstStaffSummary.get(i).getNy1q1());
					lstStaffSummaryTotal.setTotalNy1q2(lstStaffSummaryTotal.getTotalNy1q2() + lstStaffSummary.get(i).getNy1q2());
					lstStaffSummaryTotal.setTotalNy1q3(lstStaffSummaryTotal.getTotalNy1q3() + lstStaffSummary.get(i).getNy1q3());
					lstStaffSummaryTotal.setTotalNy1q4(lstStaffSummaryTotal.getTotalNy1q4() + lstStaffSummary.get(i).getNy1q4());
					lstStaffSummaryTotal.setTotalNsY1(lstStaffSummaryTotal.getTotalNsY1() + lstStaffSummary.get(i).getsY1());
					lstStaffSummaryTotal.setTotalNy2(lstStaffSummaryTotal.getTotalNy2() + lstStaffSummary.get(i).getNy2());
					lstStaffSummaryTotal.setTotalNsY2(lstStaffSummaryTotal.getTotalNsY2() + lstStaffSummary.get(i).getsY2());
					lstStaffSummaryTotal.setTotalNy3(lstStaffSummaryTotal.getTotalNy3() + lstStaffSummary.get(i).getNy3());
					lstStaffSummaryTotal.setTotalNsY3(lstStaffSummaryTotal.getTotalNsY3() + lstStaffSummary.get(i).getsY3());
				}
			}
		}
		
		return lstStaffSummaryTotal;
	}

	public void setLstStaffSummaryTotal(TotalSummary lstStaffSummaryTotal) {
		this.lstStaffSummaryTotal = lstStaffSummaryTotal;
	}
	
	public String getLabel1() {
		if(label1.trim().equals("")) {
			label1 = "DEC " + selectedPlan.getPlan_year();
		}
		return label1;
	}

	public void setLabel1(String label1) {
		this.label1 = label1;
	}

	public String getLabel2() {
		if(label2.trim().equals("")) {
			label2 = "DEC " + selectedPlan.getYear_01();
		}
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}

	public String getLabel3() {
		if(label3.trim().equals("")) {
			label3 = "RECRUIT " + selectedPlan.getYear_02();
		}
		return label3;
	}

	public void setLabel3(String label3) {
		this.label3 = label3;
	}

	public String getLabel4() {
		if(label4.trim().equals("")) {
			label4 = "DEC " + selectedPlan.getYear_02();
		}
		return label4;
	}

	public void setLabel4(String label4) {
		this.label4 = label4;
	}

	public String getLabel5() {
		if(label5.trim().equals("")) {
			label5 = "RECRUIT " + selectedPlan.getYear_03();
		}
		return label5;
	}

	public void setLabel5(String label5) {
		this.label5 = label5;
	}
	
	public String getLabel6() {
		if(label6.trim().equals("")) {
			label6 = "DEC " + selectedPlan.getYear_03();
		}
		return label6;
	}

	public void setLabel6(String label6) {
		this.label6 = label6;
	}
	
//END GETTER AND SETTER	

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	public MainVM() {
		Map<String, String> rolesMap = SecurityFacade.getRoleCode(
				UserCredentialManager.getIntance().getLoginUsr().getId());
		
		String brCd = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
		
		if(rolesMap.containsValue("adm") || rolesMap.containsValue("op") || brCd.equalsIgnoreCase("000"))
		{
			disableBr = false;
			disableSbr = false;
			
			disableBrPlanCo = false;
			
			brItem.setId(0);
			brItem.setCode("000");
			brItem.setType("A");
			return;
		}
		
		brItem = LoanLateFacade.getBrItem(brCd);
		
		if(brItem.getType().equalsIgnoreCase("B")) {	//BM
			disableSbr = false;
			
			paramPlanCo.getBranch().setId(brItem.getId());
			paramPlanCo.getBranch().setCode(brItem.getCode());
			paramPlanCo.getBranch().setDescription(brItem.getDescription());
			
		} else { //SBM OR LOWER
			
			CodeItem branch = LoanLateFacade.getBranch(brItem.getCode());
			paramPlanCo.getBranch().setId(branch.getSuperId());
			paramPlanCo.getBranch().setCode(branch.getSuperCode());
			paramPlanCo.getBranch().setDescription(branch.getSuperDescription());
			
			paramPlanCo.getSubBranch().setId(brItem.getId());
			paramPlanCo.getSubBranch().setCode(brItem.getCode());
			paramPlanCo.getSubBranch().setDescription(brItem.getDescription());
		}
	}
	
	@Command
	@NotifyChange({ "lstPlan" })
	public void doSearchPlan() {
		lstPlan = null;
	}
	
	@Command
	@NotifyChange({ "visiblePlnInput", "selectedPlan" })
	public void onNewPlnInput()
	{
		selectedPlan = new OpPlan();
		param = new ParamPlanList();
		
		selectedMonth = OpCommon.getCurrentMonth();
		selectedPlan.setPlan_year(OpCommon.getCurrentYear());
		
		visiblePlnInput = true;
		
		if(plns.hasFellow("winPlnInput"))
			return;
		
		Executions.createComponents("opp/planInput.zul", plns, null);
	}
	
	@Command
	@NotifyChange({ "visiblePlnInput", "disableNewCo",
					"label1", "label2", "label3", 
					"label4", "label5", "label6" })
	public void onOpenPlnInput()
	{
		label1 = "";
		label2 = "";
		label3 = "";
		label4 = "";
		label5 = "";
		label6 = "";
		
		visiblePlnInput = true;
		selectedMonth = OpCommon.getMonth(selectedPlan.getPlan_month());
		disableNewCo = false;
		
		if(plns.hasFellow("winPlnInput"))
			return;

		Executions.createComponents("opp/planInput.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visiblePlnInput" })
	public void onClosePlnInput() {
		visiblePlnInput = false;
	}
	
	@Command
	@NotifyChange({ "lstPlan", "selectedPlan", "disableNewCo" })
	public void onSavePlanInput() {
		
		selectedPlan.setPlan_month(selectedMonth.getCode());
		
		selectedPlan.setYear_01(selectedPlan.getPlan_year() + 1);
		selectedPlan.setYear_02(selectedPlan.getPlan_year() + 2);
		selectedPlan.setYear_03(selectedPlan.getPlan_year() + 3);
		
		if(selectedPlan.getId() == null) {
			selectedPlan.setCreateOn(new Date());
			selectedPlan.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		} else {
			selectedPlan.setChangeOn(new Date());
			selectedPlan.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		Ebean.save(selectedPlan);
		
		Clients.showNotification("Save successfully.");
		
		disableNewCo = false;
		lstPlan = null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onConfirmDeletePlnInput() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeletePlnInput();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "lstPlan", "selectedPlan" })
	public void onDeletePlnInput()
	{
		Ebean.delete(selectedPlan);
		
		lstPlan.remove(selectedPlan);
		
		Clients.showNotification("Delete successfully.");
	}
	
	@Command
	@NotifyChange({ "visibleLoanList", "disableLoanInput", "selectedLoan", "lstCoLoanSummary", 
					"lstCoLoanSummaryTotal" })
	public void onNewLoanList()
	{
		
		selectedLoan = new OpLoan();
		
		selectedLoan.setPlanCo(selectedPlanCo);
		selectedLoan.setAuth_status("A");
		selectedLoan.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		selectedLoan.setCreateOn(new Date());

		disableLoanInput = false;
		
		visibleLoanList = true;
		
		lstCoLoanSummary = null;
		lstCoLoanSummaryTotal = null;
		
		if(plns.hasFellow("winLoanList"))
			return;

		Executions.createComponents("opp/loanList.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleLoanList", "disableLoanInput", "selectedSysCommune", "lstCoLoanSummary", 
					"lstCoLoanSummaryTotal" })
	public void onOpenLoanList()
	{
		disableLoanInput = false;
		
		visibleLoanList = true;
		
		lstCoLoanSummary = null;
		lstCoLoanSummaryTotal = null;
		
		if(plns.hasFellow("winLoanList"))
			return;

		Executions.createComponents("opp/loanList.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleLoanList" })
	public void onCloseLoanList() {
		visibleLoanList = false;
	}
	
	@Command
	@NotifyChange({ "visibleLoanInput", "selectedLoanDetail", "loan_amt_avg_01", "loan_amt_avg_02", 
					"loan_amt_avg_03", "loan_amt_avg_04" })
	public void onNewLoanInput()
	{
		selectedLoanDetail = new OpLoanDetail();
		visibleLoanInput = true;
		
		loan_amt_avg_01 = 0.0;
		loan_amt_avg_02 = 0.0;
		loan_amt_avg_03 = 0.0;
		loan_amt_avg_04 = 0.0;
		
		if(plns.hasFellow("winLoanInput"))
			return;

		Executions.createComponents("opp/loanInput.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleLoanInput", "loan_amt_avg_01", "loan_amt_avg_02", "loan_amt_avg_03", 
					"loan_amt_avg_04" })
	public void onOpenLoanInput()
	{
		
		loan_amt_avg_01 = 0.0;
		loan_amt_avg_02 = 0.0;
		loan_amt_avg_03 = 0.0;
		loan_amt_avg_04 = 0.0;
		
		visibleLoanInput = true;
		
		if(plns.hasFellow("winLoanInput"))
			return;

		Executions.createComponents("opp/loanInput.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleLoanInput", "selectedLoan", "selectedPlanCo", "lstCoLoanSummary", 
					"lstSubLoanSummary", "lstSubLoanSummaryByProduct", "lstCoLoanSummaryTotal",
					"lstSubLoanSummaryTotal", "lstSubLoanSummaryByProductTotal", "lstStaffSummary", 
					"lstStaffSummaryTotal" })
	public void onSaveLoanInput() {
		
		//VERIFY
		if(selectedLoanDetail.getProduct().getCode().equals("") || 
		   selectedLoanDetail.getCcy().getCode().equals("") ||
		   selectedLoanDetail.getY0m_cli() == null || selectedLoanDetail.getY0m_amt() == null ||
		   selectedLoanDetail.getY0d_cli() == null || selectedLoanDetail.getY0d_amt() == null ||
		   selectedLoanDetail.getM01_cli() == null || selectedLoanDetail.getM01_amt() == null ||
		   selectedLoanDetail.getM02_cli() == null || selectedLoanDetail.getM02_amt() == null ||
		   selectedLoanDetail.getM03_cli() == null || selectedLoanDetail.getM03_amt() == null ||
		   selectedLoanDetail.getM04_cli() == null || selectedLoanDetail.getM04_amt() == null ||
		   selectedLoanDetail.getM05_cli() == null || selectedLoanDetail.getM05_amt() == null ||
		   selectedLoanDetail.getM06_cli() == null || selectedLoanDetail.getM06_amt() == null ||
		   selectedLoanDetail.getM07_cli() == null || selectedLoanDetail.getM07_amt() == null ||
		   selectedLoanDetail.getM08_cli() == null || selectedLoanDetail.getM08_amt() == null ||
		   selectedLoanDetail.getM09_cli() == null || selectedLoanDetail.getM09_amt() == null ||
		   selectedLoanDetail.getM10_cli() == null || selectedLoanDetail.getM10_amt() == null ||
		   selectedLoanDetail.getM11_cli() == null || selectedLoanDetail.getM11_amt() == null ||
		   selectedLoanDetail.getM12_cli() == null || selectedLoanDetail.getM12_amt() == null ||
		   selectedLoanDetail.getY1d_cli() == null || selectedLoanDetail.getY1d_amt() == null ||
		   selectedLoanDetail.getY2d_cli() == null || selectedLoanDetail.getY2d_amt() == null) {
			Clients.showNotification("Please fill in all the field.");
			return;
		}
		
		if(selectedLoan.getId() == null) {
			Ebean.save(selectedLoan);
			
			selectedPlanCo.getLoans().add(selectedLoan);
		}
		
		selectedLoanDetail.setLoan(selectedLoan);
		
		if(selectedLoanDetail.getId() == null) {
			selectedLoanDetail.setCreateOn(new Date());
			selectedLoanDetail.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		} else {
			selectedLoanDetail.setChangeOn(new Date());
			selectedLoanDetail.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		Ebean.save(selectedLoanDetail);
		Clients.showNotification("Save successfully.");
		
		selectedLoan.getLoanDetails().add(selectedLoanDetail);
		
		visibleLoanInput = false;
		
		lstCoLoanSummary = null;
		lstSubLoanSummary = null;
		lstSubLoanSummaryByProduct = null;
		lstStaffSummary = null;
		
		lstCoLoanSummaryTotal = null;
		lstSubLoanSummaryTotal = null;
		lstSubLoanSummaryByProductTotal = null;
		lstStaffSummaryTotal = null;
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command
	@NotifyChange({ "visibleLoanInput" })
	public void onCloseLoanInput() {
		visibleLoanInput = false;
	}
	
	@Command
	@NotifyChange({ "visibleSavingList", "selectedSaving", "lstCoSavingSummary", "lstCoSavingSummaryTotal" })
	public void onNewSavingList()
	{
		selectedSaving = new OpSaving();

		selectedSaving.setPlanCo(selectedPlanCo);
		selectedSaving.setAuth_status("A");
		selectedSaving.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		selectedSaving.setCreateOn(new Date());
		
		visibleSavingList = true;
		
		lstCoSavingSummary = null;
		lstCoSavingSummaryTotal = null;
		
		if(plns.hasFellow("winSavingList"))
			return;

		Executions.createComponents("opp/savingList.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleSavingList", "lstCoSavingSummary", "lstCoSavingSummaryTotal" })
	public void onOpenSavingList()
	{
		
		visibleSavingList = true;
		
		lstCoSavingSummary = null;
		lstCoSavingSummaryTotal = null;
		
		if(plns.hasFellow("winSavingList"))
			return;

		Executions.createComponents("opp/savingList.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleSavingList" })
	public void onCloseSavingList() {
		visibleSavingList = false;
	}
	
	@Command
	@NotifyChange({ "visibleSavingInput", "selectedSavingDetail", "saving_amt_avg_01", 
					"saving_amt_avg_02", "saving_amt_avg_03", "saving_amt_avg_04" })
	public void onNewSavingInput()
	{
		selectedSavingDetail = new OpSavingDetail();
		visibleSavingInput = true;
		
		saving_amt_avg_01 = 0.0;
		saving_amt_avg_02 = 0.0;
		saving_amt_avg_03 = 0.0;
		saving_amt_avg_04 = 0.0;
		
		if(plns.hasFellow("winSavingInput"))
			return;

		Executions.createComponents("opp/savingInput.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleSavingInput", "saving_amt_avg_01", "saving_amt_avg_02", "saving_amt_avg_03", 
					"saving_amt_avg_04" })
	public void onOpenSavingInput()
	{
		saving_amt_avg_01 = 0.0;
		saving_amt_avg_02 = 0.0;
		saving_amt_avg_03 = 0.0;
		saving_amt_avg_04 = 0.0;
		
		visibleSavingInput = true;
		
		if(plns.hasFellow("winSavingInput"))
			return;

		Executions.createComponents("opp/savingInput.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleSavingInput" })
	public void onCloseSavingInput() {
		visibleSavingInput = false;
	}
	
	@Command
	@NotifyChange({ "visibleSavingInput", "selectedPlanCo", "selectedSaving", "lstCoSavingSummary", 
					"lstSubSavingSummary", "lstSubSavingSummaryByProduct", "lstCoSavingSummaryTotal",
					"lstSubSavingSummaryTotal", "lstSubSavingSummaryByProductTotal"})
	public void onSaveSavingInput() {
		
		//VERIFY
		if(selectedSavingDetail.getProduct().getCode().equals("") || 
		   selectedSavingDetail.getCcy().getCode().equals("") ||
		   selectedSavingDetail.getY0m_cli() == null || selectedSavingDetail.getY0m_amt() == null ||
		   selectedSavingDetail.getY0d_cli() == null || selectedSavingDetail.getY0d_amt() == null ||
		   selectedSavingDetail.getM01_cli() == null || selectedSavingDetail.getM01_amt() == null ||
		   selectedSavingDetail.getM02_cli() == null || selectedSavingDetail.getM02_amt() == null ||
		   selectedSavingDetail.getM03_cli() == null || selectedSavingDetail.getM03_amt() == null ||
		   selectedSavingDetail.getM04_cli() == null || selectedSavingDetail.getM04_amt() == null ||
		   selectedSavingDetail.getM05_cli() == null || selectedSavingDetail.getM05_amt() == null ||
		   selectedSavingDetail.getM06_cli() == null || selectedSavingDetail.getM06_amt() == null ||
		   selectedSavingDetail.getM07_cli() == null || selectedSavingDetail.getM07_amt() == null ||
		   selectedSavingDetail.getM08_cli() == null || selectedSavingDetail.getM08_amt() == null ||
		   selectedSavingDetail.getM09_cli() == null || selectedSavingDetail.getM09_amt() == null ||
		   selectedSavingDetail.getM10_cli() == null || selectedSavingDetail.getM10_amt() == null ||
		   selectedSavingDetail.getM11_cli() == null || selectedSavingDetail.getM11_amt() == null ||
		   selectedSavingDetail.getM12_cli() == null || selectedSavingDetail.getM12_amt() == null ||
		   selectedSavingDetail.getY1d_cli() == null || selectedSavingDetail.getY1d_amt() == null ||
		   selectedSavingDetail.getY2d_cli() == null || selectedSavingDetail.getY2d_amt() == null) {
			Clients.showNotification("Please fill in all the field.");
			return;
		}
		
		if(selectedSaving.getId() == null) {
			Ebean.save(selectedSaving);
			
			selectedPlanCo.getSavings().add(selectedSaving);
		}
		
		selectedSavingDetail.setSaving(selectedSaving);
		
		if(selectedSavingDetail.getId() == null) {
			selectedSavingDetail.setCreateOn(new Date());
			selectedSavingDetail.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		} else {
			selectedSavingDetail.setChangeOn(new Date());
			selectedSavingDetail.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		selectedSavingDetail.setCreateOn(new Date());
		selectedSavingDetail.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		
		Ebean.save(selectedSavingDetail);
		
		Clients.showNotification("Save successfully.");
		
		selectedSaving.getSavingDetails().add(selectedSavingDetail);
		
		visibleSavingInput = false;
		
		lstCoSavingSummary = null;
		lstSubSavingSummary = null;
		lstSubSavingSummaryByProduct = null;
		
		lstCoSavingSummaryTotal = null;
		lstSubSavingSummaryTotal = null;
		lstSubSavingSummaryByProductTotal = null;
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command
	@NotifyChange({ "visiblePlnCo", "selectedPlanCo", "selectedCo", "lstSubLoanSummary", 
					"lstSubSavingSummary", "lstSubLoanSummaryByProduct", "lstSubSavingSummaryByProduct",
					"lstSubLoanSummaryTotal", "lstSubSavingSummaryTotal", "lstSubLoanSummaryByProductTotal", 
					"lstSubSavingSummaryByProductTotal", "lstStaffSummary", "lstStaffSummaryTotal" })
	public void onNewPlnCo()
	{
		selectedPlanCo = new OpPlanCo();
		selectedCo = new SysCo();
		selectedPlanCo.setPlan(selectedPlan);
		selectedPlanCo.setBranchCode(brItem.getCode());
		
		selectedPlanCo.getBranch().setId(brItem.getId());
		selectedPlanCo.getBranch().setCode(brItem.getCode());
		selectedPlanCo.getBranch().setDescription(brItem.getDescription());
		
		visiblePlnCo = true;
		
		lstSubLoanSummary = null;
		lstSubSavingSummary = null;
		lstSubLoanSummaryByProduct = null;
		lstSubSavingSummaryByProduct = null;
		lstStaffSummary = null;
		
		lstSubLoanSummaryTotal = null;
		lstSubSavingSummaryTotal = null;
		lstSubLoanSummaryByProductTotal = null;
		lstSubSavingSummaryByProductTotal = null;
		lstStaffSummaryTotal = null;
		
		if(plns.hasFellow("winPlnCo"))
			return;
		
		Executions.createComponents("opp/planCo.zul", plns, null);
	}
	
	@Command
	@NotifyChange({ "visiblePlnCo", "selectedCo", "disablePlanCo", "lstSubLoanSummary", 
					"lstSubSavingSummary", "lstSubLoanSummaryByProduct", "lstSubSavingSummaryByProduct",
					"lstSubLoanSummaryTotal", "lstSubSavingSummaryTotal", "lstSubLoanSummaryByProductTotal", 
					"lstSubSavingSummaryByProductTotal", "lstStaffSummary", "lstStaffSummaryTotal" })
	public void onOpenPlnCo()
	{
		selectedCo = selectedPlanCo.getCo();
		
		visiblePlnCo = true;
		
		disablePlanCo = false;
		
		lstSubLoanSummary = null;
		lstSubSavingSummary = null;
		lstSubLoanSummaryByProduct = null;
		lstSubSavingSummaryByProduct = null;
		lstStaffSummary = null;
		
		lstSubLoanSummaryTotal = null;
		lstSubSavingSummaryTotal = null;
		lstSubLoanSummaryByProductTotal = null;
		lstSubSavingSummaryByProductTotal = null;
		lstStaffSummaryTotal = null;
		
		if(plns.hasFellow("winPlnCo"))
			return;

		Executions.createComponents("opp/planCo.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visiblePlnCo" })
	public void onClosePlnCo() {
		visiblePlnCo = false;
	}
	
	@Command
	@NotifyChange({ "selectedPlan", "disablePlanCo" })
	public void onSavePlanCo() {
		
		if(selectedCo.getShort_name() == null) {
			Clients.showNotification("Please select CO");
			return;
		}
		
		if(selectedPlanCo.getBranchCode() == null) {
			Clients.showNotification("Please select Branch");
			return;
		}
		
		selectedPlanCo.setPlan(selectedPlan);
		selectedPlanCo.setCo(selectedCo);
		
		if(selectedPlanCo.getId() == null) {
			selectedPlanCo.setCreateOn(new Date());
			selectedPlanCo.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		} else {
			selectedPlanCo.setChangeOn(new Date());
			selectedPlanCo.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		Ebean.save(selectedPlanCo);
		
		Clients.showNotification("Save successfully.");
		
		disablePlanCo = false;
		selectedPlan.getPlanCo().add(selectedPlanCo);
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command
	@NotifyChange({ "visibleCoList" })
	public void onOpenCoList()
	{
		
		visibleCoList = true;
		
		if(plns.hasFellow("winCoList"))
			return;

		Executions.createComponents("opp/coList.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleCoList" })
	public void onCloseCoList() {
		visibleCoList = false;
	}
	
	@Command
	@NotifyChange({ "selectedNewCo", "visibleNewCo" })
	public void onNewNewCo() {
		
		selectedNewCo = new SysCo();
		
		visibleNewCo = true;
		
		if(plns.hasFellow("winNewCo"))
			return;

		Executions.createComponents("opp/newCo.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleNewCo" })
	public void onOpenNewCo()
	{
		
		visibleNewCo = true;
		
		if(plns.hasFellow("winNewCo"))
			return;

		Executions.createComponents("opp/newCo.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleNewCo" })
	public void onCloseNewCo() {
		visibleNewCo = false;
	}
	
	@Command
	@NotifyChange({ "lstCo", "selectedCo", "visibleNewCo" })
	public void onSaveNewCo() {
		
		if(selectedNewCo.getId() == null) {
			selectedNewCo.setCreateOn(new Date());
			selectedNewCo.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		} else {
			selectedNewCo.setChangeOn(new Date());
			selectedNewCo.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		Ebean.save(selectedNewCo);
		
		Clients.showNotification("Save successfully.");
		
		lstCo = null;
		visibleNewCo = false;
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command
	@NotifyChange({ "selectedNewCo" })
	public void onClearAllNewCo() {
		selectedNewCo = new SysCo();
	}
	
	@Command
	@NotifyChange({ "lstCo" })
	public void doSearchCo() {
		lstCo = null;
	}
	
	@Command
	@NotifyChange({ "visibleCoList" })
	public void onSelectCo() {
		visibleCoList = false;
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command
	@NotifyChange({ "" })
	public void doSearchCommune() {
		
	}
	
	@Command
	@NotifyChange({ "visibleCommuneList" })
	public void onOpenCommuneList()
	{
		
		visibleCommuneList = true;
		
		if(plns.hasFellow("winCommuneList"))
			return;

		Executions.createComponents("opp/communeList.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visibleCommuneList" })
	public void onCloseCommuneList() {
		visibleCommuneList = false;
	}
	
	@Command
	@NotifyChange({ "visibleCommuneList" })
	public void onSelectCommune() {
		
		visibleCommuneList = false;
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command
	@NotifyChange({ "disableLoanInput" })
	public void onSaveOPLoan() {
		
		selectedLoan.setPlanCo(selectedPlanCo);
		selectedLoan.setAuth_status("A");
		
		if(selectedLoan.getId() == null) {
			selectedLoan.setCreateOn(new Date());
			selectedLoan.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		} else {
			selectedLoan.setChangeOn(new Date());
			selectedLoan.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		Ebean.save(selectedLoan);
		
		Clients.showNotification("Save successfully.");
		
		disableLoanInput = false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onConfirmDeleteLoan() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeleteLoan();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "selectedPlanCo" })
	public void onDeleteLoan()
	{
		Ebean.delete(selectedLoan);

		selectedPlanCo.getLoans().remove(selectedLoan);
		
		Clients.showNotification("Delete successfully.");
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onConfirmDeleteLoanDetail() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeleteloanDetail();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "selectedLoan" })
	public void onDeleteloanDetail()
	{
		Ebean.delete(selectedLoanDetail);

		selectedLoan.getLoanDetails().remove(selectedLoanDetail);
		
		Clients.showNotification("Delete successfully.");
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onConfirmDeleteSaving() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeleteSaving();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "selectedPlanCo" })
	public void onDeleteSaving()
	{
		Ebean.delete(selectedSaving);

		selectedPlanCo.getSavings().remove(selectedSaving);
		
		Clients.showNotification("Delete successfully.");
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onConfirmDeleteSavingDetail() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeleteSavingDetail();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "selectedSaving" })
	public void onDeleteSavingDetail()
	{
		Ebean.delete(selectedSavingDetail);

		selectedSaving.getSavingDetails().remove(selectedSavingDetail);
		
		Clients.showNotification("Delete successfully.");
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onConfirmDeletePlnCo() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeletePlnCo();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "selectedLoan" })
	public void onDeletePlnCo()
	{
		Ebean.delete(selectedPlanCo);

		selectedPlan.getPlanCo().remove(selectedPlanCo);
		
		Clients.showNotification("Delete successfully.");
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@Command
	@NotifyChange({ "selectedLoanDetail", "loan_amt_avg_01" })
	public void onGenerateLoanY01()
	{	
		if(selectedLoanDetail.getY0m_cli() == null || selectedLoanDetail.getY0d_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		
		selectedLoanDetail.setY0m_amt((double) (selectedLoanDetail.getY0m_cli() * loan_amt_avg_01));
		selectedLoanDetail.setY0d_amt((double) (selectedLoanDetail.getY0d_cli() * loan_amt_avg_01));
	}
	
	@Command
	@NotifyChange({ "selectedLoanDetail", "loan_amt_avg_02" })
	public void onGenerateLoanY02()
	{	
		if(selectedLoanDetail.getM01_cli() == null || selectedLoanDetail.getM02_cli() == null ||
		   selectedLoanDetail.getM03_cli() == null || selectedLoanDetail.getM04_cli() == null ||
		   selectedLoanDetail.getM05_cli() == null || selectedLoanDetail.getM06_cli() == null ||
		   selectedLoanDetail.getM07_cli() == null || selectedLoanDetail.getM08_cli() == null ||
		   selectedLoanDetail.getM09_cli() == null || selectedLoanDetail.getM10_cli() == null ||
		   selectedLoanDetail.getM11_cli() == null || selectedLoanDetail.getM12_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		
		selectedLoanDetail.setM01_amt((double) (selectedLoanDetail.getM01_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM02_amt((double) (selectedLoanDetail.getM02_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM03_amt((double) (selectedLoanDetail.getM03_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM04_amt((double) (selectedLoanDetail.getM04_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM05_amt((double) (selectedLoanDetail.getM05_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM06_amt((double) (selectedLoanDetail.getM06_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM07_amt((double) (selectedLoanDetail.getM07_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM08_amt((double) (selectedLoanDetail.getM08_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM09_amt((double) (selectedLoanDetail.getM09_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM10_amt((double) (selectedLoanDetail.getM10_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM11_amt((double) (selectedLoanDetail.getM11_cli() * loan_amt_avg_02));
		selectedLoanDetail.setM12_amt((double) (selectedLoanDetail.getM12_cli() * loan_amt_avg_02));
	}
	
	@Command
	@NotifyChange({ "selectedLoanDetail", "loan_amt_avg_03" })
	public void onGenerateLoanY03()
	{	
		if(selectedLoanDetail.getY1d_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		
		selectedLoanDetail.setY1d_amt((double) (selectedLoanDetail.getY1d_cli() * loan_amt_avg_03));
	}
	
	@Command
	@NotifyChange({ "selectedLoanDetail", "loan_amt_avg_04" })
	public void onGenerateLoanY04()
	{	
		if(selectedLoanDetail.getY2d_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		selectedLoanDetail.setY2d_amt((double) (selectedLoanDetail.getY2d_cli() * loan_amt_avg_04));
	}
	
	@Command
	@NotifyChange({ "selectedSavingDetail", "saving_amt_avg_01" })
	public void onGenerateSavingY01()
	{	
		if(selectedSavingDetail.getY0m_cli() == null || selectedSavingDetail.getY0d_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		
		selectedSavingDetail.setY0m_amt((double) (selectedSavingDetail.getY0m_cli() * saving_amt_avg_01));
		selectedSavingDetail.setY0d_amt((double) (selectedSavingDetail.getY0d_cli() * saving_amt_avg_01));
	}
	
	@Command
	@NotifyChange({ "selectedSavingDetail", "saving_amt_avg_02" })
	public void onGenerateSavingY02()
	{	
		if(selectedSavingDetail.getM01_cli() == null || selectedSavingDetail.getM02_cli() == null ||
		   selectedSavingDetail.getM03_cli() == null || selectedSavingDetail.getM04_cli() == null ||
		   selectedSavingDetail.getM05_cli() == null || selectedSavingDetail.getM06_cli() == null ||
		   selectedSavingDetail.getM07_cli() == null || selectedSavingDetail.getM08_cli() == null ||
		   selectedSavingDetail.getM09_cli() == null || selectedSavingDetail.getM10_cli() == null ||
		   selectedSavingDetail.getM11_cli() == null || selectedSavingDetail.getM12_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		
		selectedSavingDetail.setM01_amt((double) (selectedSavingDetail.getM01_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM02_amt((double) (selectedSavingDetail.getM02_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM03_amt((double) (selectedSavingDetail.getM03_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM04_amt((double) (selectedSavingDetail.getM04_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM05_amt((double) (selectedSavingDetail.getM05_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM06_amt((double) (selectedSavingDetail.getM06_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM07_amt((double) (selectedSavingDetail.getM07_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM08_amt((double) (selectedSavingDetail.getM08_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM09_amt((double) (selectedSavingDetail.getM09_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM10_amt((double) (selectedSavingDetail.getM10_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM11_amt((double) (selectedSavingDetail.getM11_cli() * saving_amt_avg_02));
		selectedSavingDetail.setM12_amt((double) (selectedSavingDetail.getM12_cli() * saving_amt_avg_02));
	}
	
	@Command
	@NotifyChange({ "selectedSavingDetail", "saving_amt_avg_03" })
	public void onGenerateSavingY03()
	{	
		if(selectedSavingDetail.getY1d_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		
		selectedSavingDetail.setY1d_amt((double) (selectedSavingDetail.getY1d_cli() * saving_amt_avg_03));
	}
	
	@Command
	@NotifyChange({ "selectedSavingDetail", "saving_amt_avg_04" })
	public void onGenerateSavingY04()
	{	
		if(selectedSavingDetail.getY2d_cli() == null) {
			Clients.showNotification("Please input the number of client.");
			return;
		}
		
		selectedSavingDetail.setY2d_amt((double) (selectedSavingDetail.getY2d_cli() * saving_amt_avg_04));
	}
	
	@Command
	@NotifyChange({ "lstAllSubLoanSummary", "lstAllSubSavingSummary", "lstAllSubLoanSummaryByProduct", 
					"lstAllSubSavingSummaryByProduct", "visibleSumAS", "lstAllSubLoanSummaryTotal",
					"lstAllSubSavingSummaryTotal", "lstAllSubLoanSummaryByProductTotal", 
					"lstAllSubSavingSummaryByProductTotal" })
	public void onSummaryAllSub() 
	{
		lstAllSubLoanSummary = null;
		lstAllSubSavingSummary = null;
		lstAllSubLoanSummaryByProduct = null;
		lstAllSubSavingSummaryByProduct = null;
		
		lstAllSubLoanSummaryTotal = null;
		lstAllSubSavingSummaryTotal = null;
		lstAllSubLoanSummaryByProductTotal = null;
		lstAllSubSavingSummaryByProductTotal = null;
		
		visibleSumAS = true;
		
		if(plns.hasFellow("winSumAS"))
			return;
		
		Executions.createComponents("opp/summaryAllSub.zul", plns, null);
	}
	
	@Command
	@NotifyChange({ "visibleSumAS" })
	public void onCloseSumAS() 
	{
		visibleSumAS = false;
	}
	
	@Command
	@NotifyChange({ "lstAllSubLoanSummary", "lstAllSubSavingSummary", "lstAllSubLoanSummaryByProduct", 
					"lstAllSubSavingSummaryByProduct", "subBranchList", "lstAllSubLoanSummaryTotal",
					"lstAllSubSavingSummaryTotal", "lstAllSubLoanSummaryByProductTotal", 
					"lstAllSubSavingSummaryByProductTotal" })
	public void onSelectBranch()
	{
		subBranchList = null;
		
		lstAllSubLoanSummary = null;
		lstAllSubSavingSummary = null;
		lstAllSubLoanSummaryByProduct = null;
		lstAllSubSavingSummaryByProduct = null;
		
		lstAllSubLoanSummaryTotal = null;
		lstAllSubSavingSummaryTotal = null;
		lstAllSubLoanSummaryByProductTotal = null;
		lstAllSubSavingSummaryByProductTotal = null;
	}
	
	@Command
	@NotifyChange({ "paramBranches", "lstAllSubLoanSummary", "lstAllSubSavingSummary", 
					"lstAllSubLoanSummaryByProduct", "lstAllSubSavingSummaryByProduct",
					"lstAllSubLoanSummaryTotal", "lstAllSubSavingSummaryTotal", 
					"lstAllSubLoanSummaryByProductTotal", "lstAllSubSavingSummaryByProductTotal" })
	public void onSelectSubBranch()
	{
		lstAllSubLoanSummary = null;
		lstAllSubSavingSummary = null;
		lstAllSubLoanSummaryByProduct = null;
		lstAllSubSavingSummaryByProduct = null;
		
		lstAllSubLoanSummaryTotal = null;
		lstAllSubSavingSummaryTotal = null;
		lstAllSubLoanSummaryByProductTotal = null;
		lstAllSubSavingSummaryByProductTotal = null;
		
		paramBranches.getBranch().setId(paramBranches.getSubBranch().getSuperId());
		paramBranches.getBranch().setCode(paramBranches.getSubBranch().getSuperCode());
		paramBranches.getBranch().setDescription(paramBranches.getSubBranch().getSuperDescription());
	}
	
	@Command
	@NotifyChange({ "paramBranches", "lstAllSubLoanSummary", "lstAllSubSavingSummary", 
					"lstAllSubLoanSummaryByProduct", "lstAllSubSavingSummaryByProduct",
					"lstAllSubLoanSummaryTotal", "lstAllSubSavingSummaryTotal", 
					"lstAllSubLoanSummaryByProductTotal", "lstAllSubSavingSummaryByProductTotal" })
	public void onClearAll() {
		paramBranches = new ParamBranches();
		
		lstAllSubLoanSummary = null;
		lstAllSubSavingSummary = null;
		lstAllSubLoanSummaryByProduct = null;
		lstAllSubSavingSummaryByProduct = null;
		
		lstAllSubLoanSummaryTotal = null;
		lstAllSubSavingSummaryTotal = null;
		lstAllSubLoanSummaryByProductTotal = null;
		lstAllSubSavingSummaryByProductTotal = null;
	}
	
	@Command
	@NotifyChange({ "lstSubLoanSummary", "lstSubSavingSummary", "lstSubLoanSummaryByProduct", 
					"lstSubSavingSummaryByProduct", "subBranchListPlanInput", "lstSubLoanSummaryTotal",
					"lstSubSavingSummaryTotal", "lstSubLoanSummaryByProductTotal", "selectedPlan",
					"lstSubSavingSummaryByProductTotal", "lstStaffSummary", "lstStaffSummaryTotal" })
	public void onSelectBranchPlanCo() {
		subBranchListPlanInput = null;
		selectedPlan.setPlanCo(new ListModelList<OpPlanCo>(OperationFacade.getPlanCoList(paramPlanCo)));
		selectedPlan.setPlanStaff(new ListModelList<OpStaff>(OperationFacade.getStaffList(paramPlanCo)));
		
		lstSubLoanSummary = null;
		lstSubSavingSummary = null;
		lstSubLoanSummaryByProduct = null;
		lstSubSavingSummaryByProduct = null;
		lstStaffSummary = null;
		
		lstSubLoanSummaryTotal = null;
		lstSubSavingSummaryTotal = null;
		lstSubLoanSummaryByProductTotal = null;
		lstSubSavingSummaryByProductTotal = null;
		lstStaffSummaryTotal = null;
	}
	
	@Command
	@NotifyChange({ "paramPlanCo", "lstSubLoanSummary", "lstSubSavingSummary", "selectedPlan",
					"lstSubLoanSummaryByProduct", "lstSubSavingSummaryByProduct",
					"lstSubLoanSummaryTotal", "lstSubSavingSummaryTotal", 
					"lstSubLoanSummaryByProductTotal", "lstSubSavingSummaryByProductTotal",
					"lstStaffSummary", "lstStaffSummaryTotal"})
	public void onSelectSubBranchPlanCo() {
		
		selectedPlan.setPlanCo(new ListModelList<OpPlanCo>(OperationFacade.getPlanCoList(paramPlanCo)));
		selectedPlan.setPlanStaff(new ListModelList<OpStaff>(OperationFacade.getStaffList(paramPlanCo)));
		
		lstSubLoanSummary = null;
		lstSubSavingSummary = null;
		lstSubLoanSummaryByProduct = null;
		lstSubSavingSummaryByProduct = null;
		lstStaffSummary = null;
		
		lstSubLoanSummaryTotal = null;
		lstSubSavingSummaryTotal = null;
		lstSubLoanSummaryByProductTotal = null;
		lstSubSavingSummaryByProductTotal = null;
		lstStaffSummaryTotal = null;
		
		paramPlanCo.getBranch().setId(paramPlanCo.getSubBranch().getSuperId());
		paramPlanCo.getBranch().setCode(paramPlanCo.getSubBranch().getSuperCode());
		paramPlanCo.getBranch().setDescription(paramPlanCo.getSubBranch().getSuperDescription());
	}
	
	@Command
	@NotifyChange({ "lstSubLoanSummary", "lstSubSavingSummary", "selectedPlan",
					"lstSubLoanSummaryByProduct", "lstSubSavingSummaryByProduct",
					"lstSubLoanSummaryTotal", "lstSubSavingSummaryTotal", 
					"lstSubLoanSummaryByProductTotal", "lstSubSavingSummaryByProductTotal",
					"lstStaffSummary", "lstStaffSummaryTotal"})
	public void doSearchPlanInput() {
		
		selectedPlan.setPlanCo(new ListModelList<OpPlanCo>(OperationFacade.getPlanCoList(paramPlanCo)));
		selectedPlan.setPlanStaff(new ListModelList<OpStaff>(OperationFacade.getStaffList(paramPlanCo)));
		
		lstSubLoanSummary = null;
		lstSubSavingSummary = null;
		lstSubLoanSummaryByProduct = null;
		lstSubSavingSummaryByProduct = null;
		lstStaffSummary = null;
		
		lstSubLoanSummaryTotal = null;
		lstSubSavingSummaryTotal = null;
		lstSubLoanSummaryByProductTotal = null;
		lstSubSavingSummaryByProductTotal = null;
		lstStaffSummaryTotal = null;
	}
	
	@Command
	@NotifyChange({ "paramPlanCo", "lstSubLoanSummary", "lstSubSavingSummary", "selectedPlan",
					"lstSubLoanSummaryByProduct", "lstSubSavingSummaryByProduct",
					"lstSubLoanSummaryTotal", "lstSubSavingSummaryTotal", 
					"lstSubLoanSummaryByProductTotal", "lstSubSavingSummaryByProductTotal",
					"lstStaffSummary", "lstStaffSummaryTotal"})
	public void onClearAllPlanInput() {
		paramPlanCo = new ParamCommune();
		
		selectedPlan.setPlanCo(new ListModelList<OpPlanCo>(OperationFacade.getPlanCoList()));
		selectedPlan.setPlanStaff(new ListModelList<OpStaff>(OperationFacade.getStaffList(paramPlanCo)));
		
		lstSubLoanSummary = null;
		lstSubSavingSummary = null;
		lstSubLoanSummaryByProduct = null;
		lstSubSavingSummaryByProduct = null;
		lstStaffSummary = null;
		
		lstSubLoanSummaryTotal = null;
		lstSubSavingSummaryTotal = null;
		lstSubLoanSummaryByProductTotal = null;
		lstSubSavingSummaryByProductTotal = null;
		lstStaffSummaryTotal = null;
	}
	
	@Command
	@NotifyChange({ "visiblePlnStaff", "selectedPlanStaff" })
	public void onNewPlnStaff()
	{
		selectedPlanStaff = new OpStaff();
		selectedPlanStaff.setBranch_code(brItem.getCode());
		
		visiblePlnStaff = true;
		
		if(plns.hasFellow("winPlnStaff"))
			return;
		
		Executions.createComponents("opp/planStaff.zul", plns, null);
	}
	
	@Command
	@NotifyChange({ "visiblePlnStaff" })
	public void onOpenPlnStaff()
	{
		
		visiblePlnStaff = true;
		
		if(plns.hasFellow("winPlnStaff"))
			return;

		Executions.createComponents("opp/planStaff.zul", plns, null);
		
	}
	
	@Command
	@NotifyChange({ "visiblePlnStaff" })
	public void onClosePlnStaff() {
		visiblePlnStaff = false;
	}
	
	@Command
	@NotifyChange({ "selectedPlan", "visiblePlnStaff", "lstStaffSummary", "lstStaffSummaryTotal" })
	public void onSavePlanStaff() {
		
		//VERIFY
		if(selectedPlanStaff.getPosition().getCode().equals("") || selectedPlanStaff.getNy0() == null ||
		   selectedPlanStaff.getNy1q1() == null || selectedPlanStaff.getNy1q2() == null ||
		   selectedPlanStaff.getNy1q3() == null || selectedPlanStaff.getNy1q4() == null ||
		   selectedPlanStaff.getNy2() == null || selectedPlanStaff.getNy3() == null) {
			Clients.showNotification("Please fill in all the field.");
			return;
		}
		
		selectedPlanStaff.setPlan(selectedPlan);
		
		if(selectedPlanStaff.getId() == null) {
			selectedPlanStaff.setCreateOn(new Date());
			selectedPlanStaff.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		} else {
			selectedPlanStaff.setChangeOn(new Date());
			selectedPlanStaff.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		Ebean.save(selectedPlanStaff);
		
		selectedPlan.setPlanStaff(new ListModelList<OpStaff>(OperationFacade.getStaffList()));
		lstStaffSummary = null;
		lstStaffSummaryTotal = null;
		
		visiblePlnStaff = false;
		
		Clients.showNotification("Save successfully.");
		
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "selectedPlan", "selectedPlanStaff" })
	public void onConfirmDeletePlnStaff() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeletePlnStaff();
						}
					}
				});
	}

	@Command
	@NotifyChange({ "selectedPlan", "selectedPlanStaff" })
	public void onDeletePlnStaff()
	{
		Ebean.delete(selectedPlanStaff);
		
		selectedPlan.getPlanStaff().remove(selectedPlanStaff);
		
		Clients.showNotification("Delete successfully.");
	}
	
}
