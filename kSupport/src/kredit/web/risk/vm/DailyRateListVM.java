package kredit.web.risk.vm;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import kredit.web.core.util.model.CodeItem;
import kredit.web.risk.model.DailyRateModel;
import kredit.web.risk.model.ParamModel;
import kredit.web.risk.model.RateDetailModel;
import kredit.web.risk.model.facade.RiskRateFacade;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

public class DailyRateListVM {
	ListModelList<DailyRateModel> lstDailyRate;
	DailyRateModel selected;
	
	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	ParamModel param = new ParamModel();
	
	ListModelList<CodeItem> lstTranStatus;	
	ListModelList<CodeItem> lstFilterBranch;
	ListModelList<CodeItem> lstFilterSub;
	ListModelList<CodeItem> lstTranType;
	ListModelList<RateDetailModel> lstRateDetail;
	
	@Wire("#dratels")
	private Window ratels;
	
	@Init
	public void init() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange({"lstDailyRate","lstFilterSub"})
	public void doSearch(){
		lstDailyRate = null;
		lstFilterSub = null;
		selected = null;
		param.getSub().setCode("");
		param.getSub().setDescription("All");
		//System.out.println(param.getBranch().getId());
	}
	
	@Command
	@NotifyChange({"lstDailyRate"})
	public void doSub(){
		lstFilterSub = null;
		lstDailyRate = null;	
		selected = null;	
		
	}
	
	@Command
	@NotifyChange({"lstRateDetail"})
	public void onDetail(){
		lstRateDetail = null;
		Boolean wait = ratels.hasFellow("detaills");		
		if(wait)
			return;
		Executions.createComponents("/risk/rateDetailList.zul", ratels, null);
	}
	
	@Command
	@NotifyChange({"lstDailyRate","param","selected","lstFilterBranch","lstFilterSub"})
	public void onClear(){
		lstDailyRate = null;
		lstFilterBranch = null;
		lstFilterSub = null;
		selected = null;
		param = new ParamModel();
	}
	
	@Command
	public void onExport() {
		try {
			Workbook workbook = new HSSFWorkbook();
			Iterator<DailyRateModel> iterator = lstDailyRate.iterator();
			Row row;
			// List<KivaLoanModel> tmpSch = new ArrayList<KivaLoanModel>();
			Integer rowIndex = 1;
			Sheet sheet = workbook.createSheet();
			row = sheet.createRow(0);
			Cell cNo = row.createCell(0);
			Cell cBr = row.createCell(1);
			Cell cCif = row.createCell(2);
			Cell cClient = row.createCell(3);
			Cell cNum = row.createCell(4);
			Cell cInital_type = row.createCell(5);
			Cell cInital_class = row.createCell(6);			
			Cell cMonthly_deposit = row.createCell(7);
			Cell cMonthly_status = row.createCell(8);
			
			cNo.setCellValue("No");
			cBr.setCellValue("Branch Code");
			cCif.setCellValue("CIF");
			cClient.setCellValue("CLINET NAME");
			cNum.setCellValue("#ACC");			
			cInital_type.setCellValue("PRE TYPE");
			cInital_class.setCellValue("PRE CLASS");			
			cMonthly_deposit.setCellValue("DAILY DEPOSIT");
			cMonthly_status.setCellValue("DAILY STATUS");
			while (iterator.hasNext()) {
				DailyRateModel c = iterator.next();
				row = sheet.createRow(rowIndex++);
				Cell No = row.createCell(0);
				Cell Br = row.createCell(1);
				Cell Cif = row.createCell(2);
				Cell Client = row.createCell(3);
				Cell Num = row.createCell(4);
				Cell Inital_type = row.createCell(5);
				Cell Inital_class = row.createCell(6);							
				Cell Monthly_deposit = row.createCell(7);
				Cell Monthly_status = row.createCell(8);
				
				No.setCellValue(c.getNo());
				Br.setCellValue(c.getBranch_code()+'-'+c.getBranch_name());
				Cif.setCellValue(c.getCif());
				Client.setCellValue(c.getCust_name());
				Num.setCellValue(c.getNum_account());
				Inital_type.setCellValue(c.getInitial_type());
				Inital_class.setCellValue(c.getInitial_class());			
				
				Monthly_deposit.setCellValue(c.getDaily_deposit());
				Monthly_status.setCellValue(c.getDaily_status());
				
			}
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			String now = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Filedownload
					.save(outArray, "application/ms-excel", "Daily_Rating-"+now+".xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListModelList<DailyRateModel> getLstDailyRate() {
		if(lstDailyRate==null){
			lstDailyRate = new ListModelList<>(RiskRateFacade.getLstDailyRate(param));
		}
		return lstDailyRate;
	}

	public void setLstDailyRate(ListModelList<DailyRateModel> lstDailyRate) {
		this.lstDailyRate = lstDailyRate;
	}	

	public ListModelList<RateDetailModel> getLstRateDetail() {
		if(lstRateDetail==null){			
			lstRateDetail = new ListModelList<>(RiskRateFacade.getLstDailyDetail(selected.getCif(),selected.getValue_date()));
		}
		return lstRateDetail;
	}

	public void setLstRateDetail(ListModelList<RateDetailModel> lstRateDetail) {
		this.lstRateDetail = lstRateDetail;
	}

	public DailyRateModel getSelected() {
		return selected;
	}
	public void setSelected(DailyRateModel selected) {
		this.selected = selected;
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
	public Window getRatels() {
		return ratels;
	}
	public void setRatels(Window ratels) {
		this.ratels = ratels;
	}	

	public ListModelList<CodeItem> getLstTranStatus() {
		if (lstTranStatus != null)
			return lstTranStatus;
		lstTranStatus = new ListModelList<CodeItem>();
		String[] desc = new String[] { "All", "USUAL", "UNUSUAL" };
		String[] code = new String[] { "%", "USUAL", "UNUSUAL" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			lstTranStatus.add(item);
		}
		return lstTranStatus;
	}

	public void setLstTranStatus(ListModelList<CodeItem> lstTranStatus) {
		this.lstTranStatus = lstTranStatus;
	}

	public ListModelList<CodeItem> getLstFilterBranch() {
		if(lstFilterBranch==null){
			lstFilterBranch = new ListModelList<>(RiskRateFacade.getBranchesList());
		}
		return lstFilterBranch;
	}

	public void setLstFilterBranch(ListModelList<CodeItem> lstFilterBranch) {
		this.lstFilterBranch = lstFilterBranch;
	}
	
	public ListModelList<CodeItem> getLstFilterSub() {
		if(lstFilterSub==null){
			lstFilterSub = new ListModelList<>(RiskRateFacade.getSubBranchesList(param.getBranch().getId()));
		}
		return lstFilterSub;
	}

	public void setLstFilterSub(ListModelList<CodeItem> lstFilterSub) {
		this.lstFilterSub = lstFilterSub;
	}


	public ListModelList<CodeItem> getLstTranType() {
		if (lstTranType != null)
			return lstTranType;
		lstTranType = new ListModelList<CodeItem>();
		String[] desc = new String[] { "All", "LOAN", "DEPOSIT" };
		String[] code = new String[] { "%", "LOAN", "DEPOSIT" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			lstTranType.add(item);
		}		
		return lstTranType;
	}

	public void setLstTranType(ListModelList<CodeItem> lstTranType) {
		this.lstTranType = lstTranType;
	}
	public ParamModel getParam() {
		return param;
	}

	public void setParam(ParamModel param) {
		this.param = param;
	}
	
	
}
