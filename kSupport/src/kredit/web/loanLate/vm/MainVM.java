package kredit.web.loanLate.vm;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.loanLate.model.Branch;
import kredit.web.loanLate.model.LoanLate;
import kredit.web.loanLate.model.LoanLateDetail;
import kredit.web.loanLate.model.LoanLateTemp;
import kredit.web.loanLate.model.ParamLoan;
import kredit.web.loanLate.model.facade.LoanLateFacade;
import kredit.web.security.model.facade.SecurityFacade;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;

public class MainVM {

	ListModelList<LoanLateTemp> lst;
	ListModelList<CodeItem> branchList;
	ListModelList<CodeItem> subBranchList;
	
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	int rowPerPage = 10;
	Privilege privilege = null;
	
	LoanLateTemp selected = new LoanLateTemp();
	ParamLoan param = new ParamLoan();
	
	ListModelList<CodeItem> cStatus;
	
	boolean visible;
	boolean infoChange = true;
	boolean disableBr = true;
	boolean disableSbr = true;
	boolean hqInfo = true;
	
	@Wire("#lols")
	private Window lols;
	
	//Pagination
	private PagingList<LoanLateTemp> pagingList;
	private int pageIndex;
	private int totalSizeL;
	
//GETER & SETTER
	
	public PagingList<LoanLateTemp> getPagingList() {
		return pagingList;
	}

	public void setPagingList(PagingList<LoanLateTemp> pagingList) {
		this.pagingList = pagingList;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	@NotifyChange("lst")
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		// get the first page
		Page<LoanLateTemp> page = pagingList.getPage(pageIndex);

		// get the beans from the page as a list
		List<LoanLateTemp> lstT = page.getList();
		lst = new ListModelList<LoanLateTemp>(lstT);
	}
	
	public int getTotalSizeL() {
		return totalSizeL;
	}

	public void setTotalSizeL(int totalSizeL) {
		this.totalSizeL = totalSizeL;
	}

	@NotifyChange({ "lst", "totalSizeL" })
	public ListModelList<LoanLateTemp> getLst() {
		
		if(lst != null)
		{
			return lst;
		}
		
		Query<LoanLateTemp> query = Ebean.find(LoanLateTemp.class);
		
		query.where()
		.gt("overdueDay", 0);
		
		if(!(param.getFilter() == null || param.getFilter().trim().equals(""))) {
			query.where()
			.eq("accNo", param.getFilter());
		}
		
		if(!(param.getBranch().getCode().equals("")))
		{
			if(param.getBranch().getCode().equals("000"))
			{
				query.where()
				.in("branchCode", param.getBranch().getCode());
			}
			else
			{
				query.where()
				.in("branchCode", LoanLateFacade.getSubBranchCodeList(param.getBranch().getId()));
			}
		}
		
		if(!(param.getSubBranch().getCode().equals("")))
		{
			query.where()
			.eq("branchCode", param.getSubBranch().getCode());
		}
		
		pagingList = query.orderBy("branchCode, overdueDay").findPagingList(Integer.parseInt(selectedNumRow.getCode()));
		
		pagingList.getFutureRowCount();

		// get the first page
		Page<LoanLateTemp> page = pagingList.getPage(0);

		// get the beans from the page as a list
		List<LoanLateTemp> lstT = page.getList();

		totalSizeL = page.getTotalRowCount();

		lst = new ListModelList<>(lstT);
		
		return lst;
	}
	
	public void setLst(ListModelList<LoanLateTemp> lst) {
		this.lst = lst;
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
			subBranchList = new ListModelList<CodeItem>(LoanLateFacade.getSubBranchesListWithAll(param.getBranch().getId()));
		}
		return subBranchList;
	}

	public void setSubBranchList(ListModelList<CodeItem> subBranchList) {
		this.subBranchList = subBranchList;
	}

	public Window getLols() {
		return lols;
	}

	public void setLols(Window lols) {
		this.lols = lols;
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
		if(privilege == null)
		{
			privilege = Common.getPrivilege(CMD.LIST_LOAN_LATE);
		}
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public LoanLateTemp getSelected() {
		return selected;
	}

	public void setSelected(LoanLateTemp selected) {
		this.selected = selected;
	}

	public ParamLoan getParam() {
		return param;
	}

	public void setParam(ParamLoan param) {
		this.param = param;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public ListModelList<CodeItem> getcStatus() {
		if (cStatus != null)
			return cStatus;
		cStatus = new ListModelList<CodeItem>();
		String[] desc = new String[] { "Branch Level", "Court Level" };
		String[] code = new String[] { "B", "C" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			cStatus.add(item);
		}
		return cStatus;
	}

	public void setcStatus(ListModelList<CodeItem> cStatus) {
		this.cStatus = cStatus;
	}
	
	public boolean isInfoChange() {
		return infoChange;
	}

	public void setInfoChange(boolean infoChange) {
		this.infoChange = infoChange;
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
	
	public boolean isHqInfo() {
		return hqInfo;
	}

	public void setHqInfo(boolean hqInfo) {
		this.hqInfo = hqInfo;
	}
	
//GETER & SETTER

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	public MainVM() 
	{
		Map<String, String> rolesMap = SecurityFacade.getRoleCode(
				UserCredentialManager.getIntance().getLoginUsr().getId());
		
		String brCd = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
		System.out.println(brCd);
		Branch brItem = LoanLateFacade.getBrItem(brCd);
		
		if(rolesMap.containsValue("adm") || rolesMap.containsValue("hq") || brCd.equalsIgnoreCase("000"))
		{
			disableBr = false;
			disableSbr = false;
			hqInfo = false;
			return;
		}
		
		if(brItem.getType().equalsIgnoreCase("B")) {	//BM
			disableSbr = false;
			
			param.getBranch().setId(brItem.getId());
			param.getBranch().setCode(brItem.getCode());
			param.getBranch().setDescription(brItem.getDescription());
			
		} else { //SBM OR LOWER
			
			CodeItem branch = LoanLateFacade.getBranch(brItem.getCode());
			param.getBranch().setId(branch.getId());
			param.getBranch().setCode(branch.getCode());
			param.getBranch().setDescription(branch.getDescription());
			
			param.getSubBranch().setId(brItem.getId());
			param.getSubBranch().setCode(brItem.getCode());
			param.getSubBranch().setDescription(brItem.getDescription());
		}
		
	}
	
	@Command
	@NotifyChange({ "infoChange" })
	public void infoChanging()
	{
		infoChange = false;
	}
	
	@Command
	@NotifyChange({ "lst", "param" })
	public void doSearch()
	{
		lst = null;
	}
	
	@Command
	@NotifyChange({ "lst", "param", "selected" })
	public void onClearAll()
	{
		param = new ParamLoan();
		selected = new LoanLateTemp();
		doSearch();
	}
	
	@Command
	@NotifyChange({ "lst", "selected", "selectedDetail", "visible", "infoChange" })
	public void onOpen()
	{
		visible = true;
		infoChange = true;
		
		boolean wait = lols.hasFellow("loanDetail");
		
		if(wait)
			return;

		Executions.createComponents("/loanLate/detail.zul", lols, null);
	}
	
	@Command
	@NotifyChange({ "visible" })
	public void onCloseDetail()
	{
		visible = false;
	}
	
	@Command
	@NotifyChange({ "selected", "selectedDetail", "infoChange" })
	public void onSave()
	{
		//CHECK if the loan already registered in our DATABASE
		LoanLate selectedL = Ebean.find(LoanLate.class)
							.where().eq("accNo", selected.getAccNo()).findUnique();
		
		if(selectedL == null) {
			selectedL = new LoanLate();
			selectedL.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedL.setCreateOn(new Date());
			
			selected.setCreateBy(selectedL.getCreateBy());
			selected.setCreateOn(selectedL.getCreateOn());
		} else {
			selectedL.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		selectedL.setAccNo(selected.getAccNo());
		selectedL.setBrCd(selected.getBranchCode());
		selectedL.setCusName(selected.getCusName());
		selectedL.setLoanAmount(selected.getLoanAmount());
		selectedL.setProductCode(selected.getProductCode());
		selectedL.setCcy(selected.getCcy());
		selectedL.setPrinBal(selected.getPrinBal());
		selectedL.setVillage(selected.getVillage());
		selectedL.setCommune(selected.getCommune());
		selectedL.setDistrict(selected.getDistrict());
		selectedL.setProvince(selected.getProvince());
		selectedL.setDisburseDate(selected.getDisburseDate());
		selectedL.setMaturityDate(selected.getMaturityDate());
		
		selectedL.setR01(selected.getR01());
		selectedL.setR02(selected.getR02());
		selectedL.setR03(selected.getR03());
		selectedL.setR04(selected.getR04());
		selectedL.setR05(selected.getR05());
		selectedL.setR06(selected.getR06());
		selectedL.setR07(selected.getR07());
		selectedL.setR08(selected.getR08());
		selectedL.setR09(selected.getR09());
		selectedL.setR10(selected.getR10());
		selectedL.setR11(selected.getR11());
		selectedL.setR12(selected.getR12());
		
		Ebean.save(selectedL);
		
		//SAVE new record
		LoanLateDetail selectedDetail = new LoanLateDetail();
		
		selectedDetail.setAccNo(selected.getAccNo());
		selectedDetail.setCosName(selected.getCosName());
		selectedDetail.setOverduePrin(selected.getOverduePrin());
		selectedDetail.setOverdueInt(selected.getOverdueInt());
		selectedDetail.setOverdueDay(selected.getOverdueDay());
		selectedDetail.setSituation(selected.getSituation());
		selectedDetail.setHq_situation(selected.getHq_situation());
		selectedDetail.setStatus(selected.getStatus());
		
		selectedDetail.setRecordNo(LoanLateFacade.getMaxRecordNo(selected.getAccNo()) + 1);
		
		selectedDetail.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		selectedDetail.setCreateOn(new Date());
		
		Ebean.save(selectedDetail);
		
		Clients.showNotification("Loan have been saved.");
		
		infoChange = true;
	}
	
	@Command
	public void onExport()
	{
		try{
			
			Query<LoanLateTemp> query = Ebean.find(LoanLateTemp.class);
			
			query.where()
			.gt("overdueDay", 0);
			
			if(!(param.getFilter() == null || param.getFilter().trim().equals(""))) {
				query.where()
				.eq("accNo", param.getFilter());
			}
			
			if(!(param.getBranch().getCode().equals("")))
			{
				if(param.getBranch().getCode().equals("000"))
				{
					query.where()
					.in("branchCode", param.getBranch().getCode());
				}
				else
				{
					query.where()
					.in("branchCode", LoanLateFacade.getSubBranchCodeList(param.getBranch().getId()));
				}
			}
			
			if(!(param.getSubBranch().getCode().equals("")))
			{
				query.where()
				.eq("branchCode", param.getSubBranch().getCode());
			}
			
			//GET list of all loan late account according to parameter
			List<LoanLateTemp> listExport = query.orderBy("branchCode, overdueDay").findList();
			
			Workbook workbook = new HSSFWorkbook();
			Row row;
			CreationHelper createHelper = workbook.getCreationHelper();
			Integer rowIndex = 4;
			Sheet sheet = workbook.createSheet();
			
			//Set Date Type
			CellStyle cellDateType = workbook.createCellStyle();
			cellDateType.setDataFormat(createHelper.createDataFormat().getFormat("dd/mmm/yyyy"));
			
			CellStyle cellDateTypeT = workbook.createCellStyle();
			cellDateTypeT.setDataFormat(createHelper.createDataFormat().getFormat("dd-mmm-yyyy"));
			
			//sET TITLE
			row = sheet.createRow(0);
			Cell cKredit = row.createCell(0);
			cKredit.setCellValue("KREDIT Microfinance Institution Plc.");
			
			row = sheet.createRow(1);
			Cell cTitle = row.createCell(0);
			cTitle.setCellValue("Loan Default Report");
			
			row = sheet.createRow(2);
			Cell cDateT = row.createCell(0);
			Cell cDateV = row.createCell(1);
			cDateT.setCellValue("As of:");
			cDateV.setCellValue(new Date());
			cDateV.setCellStyle(cellDateType);
			
			//HEADER
			row = sheet.createRow(3);
			Cell cCofName = row.createCell(0);
			Cell cBrcd = row.createCell(1);
			Cell cAccNo = row.createCell(2);
			Cell cCusName = row.createCell(3);
			Cell cProType = row.createCell(4);
			Cell cCcy = row.createCell(5);
			Cell cLoanAmount = row.createCell(6);			
			Cell cPrinBal = row.createCell(7);
			Cell cOverduePrin = row.createCell(8);
			Cell cOverdueInt = row.createCell(9);
			Cell cOverdueDay = row.createCell(10);
			Cell cDisburseDate = row.createCell(11);
			Cell cMaturityDate = row.createCell(12);
			Cell cVillage = row.createCell(13);
			Cell cCommune = row.createCell(14);
			Cell cDistrict = row.createCell(15);
			Cell cProvince = row.createCell(16);
			Cell cStatus = row.createCell(17);
			Cell cSituation = row.createCell(18);
			Cell cHSituation = row.createCell(19);
			Cell cR01 = row.createCell(20);
			Cell cR02 = row.createCell(21);
			Cell cR03 = row.createCell(22);
			Cell cR04 = row.createCell(23);
			Cell cR05 = row.createCell(24);
			Cell cR06 = row.createCell(25);
			Cell cR07 = row.createCell(26);
			Cell cR08 = row.createCell(27);
			Cell cR09 = row.createCell(29);
			Cell cR10 = row.createCell(29);
			Cell cR11 = row.createCell(30);
			Cell cR12 = row.createCell(31);
			
			cCofName.setCellValue("Co Name");
			cBrcd.setCellValue("Branch");
			cAccNo.setCellValue("Account No.");
			cCusName.setCellValue("Customer Name");
			cProType.setCellValue("Product Desc.");
			cCcy.setCellValue("Currency");
			cLoanAmount.setCellValue("Loan Amount");			
			cPrinBal.setCellValue("Principal Balance");
			cOverduePrin.setCellValue("Overdue Principal");
			cOverdueInt.setCellValue("Overdue Interest");
			cOverdueDay.setCellValue("Day Overdue");
			cDisburseDate.setCellValue("Dibursement Date");
			cMaturityDate.setCellValue("Maturity");
			cVillage.setCellValue("Village");
			cCommune.setCellValue("Commune");
			cDistrict.setCellValue("District");
			cProvince.setCellValue("Province");
			cStatus.setCellValue("Status");
			cSituation.setCellValue("Client Situation");
			cHSituation.setCellValue("Client Situation - OP Team");
			cR01.setCellValue("Jan");
			cR02.setCellValue("Feb");
			cR03.setCellValue("Mar");
			cR04.setCellValue("Apr");
			cR05.setCellValue("May");
			cR06.setCellValue("Jun");
			cR07.setCellValue("Jul");
			cR08.setCellValue("Aug");
			cR09.setCellValue("Sep");
			cR10.setCellValue("Oct");
			cR11.setCellValue("Nov");
			cR12.setCellValue("Dec");
			
			for(int i = 0; i < listExport.size(); i++) {
				row = sheet.createRow(rowIndex++);
				Cell CofName = row.createCell(0);
				Cell Brcd = row.createCell(1);
				Cell AccNo = row.createCell(2);
				Cell CusName = row.createCell(3);
				Cell ProType = row.createCell(4);
				Cell Ccy = row.createCell(5);
				Cell LoanAmount = row.createCell(6);			
				Cell PrinBal = row.createCell(7);
				Cell OverduePrin = row.createCell(8);
				Cell OverdueInt = row.createCell(9);
				Cell OverdueDay = row.createCell(10);
				Cell DisburseDate = row.createCell(11);
				Cell MaturityDate = row.createCell(12);
				Cell Village = row.createCell(13);
				Cell Commune = row.createCell(14);
				Cell District = row.createCell(15);
				Cell Province = row.createCell(16);
				Cell Status = row.createCell(17);
				Cell Situation = row.createCell(18);
				Cell HSituation = row.createCell(19);
				Cell R01 = row.createCell(20);
				Cell R02 = row.createCell(21);
				Cell R03 = row.createCell(22);
				Cell R04 = row.createCell(23);
				Cell R05 = row.createCell(24);
				Cell R06 = row.createCell(25);
				Cell R07 = row.createCell(26);
				Cell R08 = row.createCell(27);
				Cell R09 = row.createCell(28);
				Cell R10 = row.createCell(29);
				Cell R11 = row.createCell(30);
				Cell R12 = row.createCell(31);
				
				CofName.setCellValue(listExport.get(i).getCofName());
				Brcd.setCellValue(listExport.get(i).getBranchCode());
				AccNo.setCellValue(listExport.get(i).getAccNo());
				CusName.setCellValue(listExport.get(i).getCusName());
				ProType.setCellValue(listExport.get(i).getProductType());
				Ccy.setCellValue(listExport.get(i).getCcy());
				LoanAmount.setCellValue(listExport.get(i).getLoanAmount());				
				PrinBal.setCellValue(listExport.get(i).getPrinBal());
				OverduePrin.setCellValue(listExport.get(i).getOverduePrin());
				OverdueInt.setCellValue(listExport.get(i).getOverdueInt());
				OverdueDay.setCellValue(listExport.get(i).getOverdueDay());
				DisburseDate.setCellValue(listExport.get(i).getDisburseDate());
				DisburseDate.setCellStyle(cellDateType);
				MaturityDate.setCellValue(listExport.get(i).getMaturityDate());
				MaturityDate.setCellStyle(cellDateType);
				Village.setCellValue(listExport.get(i).getVillage());
				Commune.setCellValue(listExport.get(i).getCommune());
				District.setCellValue(listExport.get(i).getDistrict());
				Province.setCellValue(listExport.get(i).getProvince());
				Status.setCellValue(listExport.get(i).getStatus());
				Situation.setCellValue(listExport.get(i).getSituation());
				HSituation.setCellValue(listExport.get(i).getHq_situation());
				R01.setCellValue(listExport.get(i).getR01());
				R02.setCellValue(listExport.get(i).getR02());
				R03.setCellValue(listExport.get(i).getR03());
				R04.setCellValue(listExport.get(i).getR04());
				R05.setCellValue(listExport.get(i).getR05());
				R06.setCellValue(listExport.get(i).getR06());
				R07.setCellValue(listExport.get(i).getR07());
				R08.setCellValue(listExport.get(i).getR08());
				R09.setCellValue(listExport.get(i).getR09());
				R10.setCellValue(listExport.get(i).getR10());
				R11.setCellValue(listExport.get(i).getR11());
				R12.setCellValue(listExport.get(i).getR12());
			}
			
			/*
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(2);
			sheet.autoSizeColumn(3);
			sheet.autoSizeColumn(4);
			sheet.autoSizeColumn(5);
			sheet.autoSizeColumn(6);
			sheet.autoSizeColumn(7);
			sheet.autoSizeColumn(8);
			sheet.autoSizeColumn(9);
			sheet.autoSizeColumn(10);
			sheet.autoSizeColumn(11);
			sheet.autoSizeColumn(12);
			sheet.autoSizeColumn(13);
			sheet.autoSizeColumn(14);
			sheet.autoSizeColumn(15);
			sheet.autoSizeColumn(16);
			sheet.autoSizeColumn(17);
			sheet.autoSizeColumn(18);
			sheet.autoSizeColumn(19);
			sheet.autoSizeColumn(20);
			sheet.autoSizeColumn(21);
			sheet.autoSizeColumn(22);
			sheet.autoSizeColumn(23);
			sheet.autoSizeColumn(24);
			sheet.autoSizeColumn(25);
			sheet.autoSizeColumn(26);
			sheet.autoSizeColumn(27);
			sheet.autoSizeColumn(28);
			sheet.autoSizeColumn(29);
			sheet.autoSizeColumn(30);
			*/
			
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			String now = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Filedownload
					.save(outArray, "application/ms-excel", "Loan Late List_" + now + ".xls");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange({ "lst", "subBranchList" })
	public void onSelectBranch()
	{
		subBranchList = null;
		lst = null;
	}
	
	@Command
	@NotifyChange({ "param", "lst" })
	public void onSelectSubBranch()
	{
		lst = null;
		param.getBranch().setId(param.getSubBranch().getSuperId());
		param.getBranch().setCode(param.getSubBranch().getSuperCode());
		param.getBranch().setDescription(param.getSubBranch().getSuperDescription());
	}

}
