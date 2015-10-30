package kredit.web.risk.vm;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import kredit.web.core.util.model.CodeItem;
import kredit.web.risk.model.MonthlyRateModel;
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

public class MonthlyRateListVM {
	ListModelList<MonthlyRateModel> lstMonthlyRate;
	MonthlyRateModel selected;
	
	private ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	ParamModel param = new ParamModel();
	
	ListModelList<CodeItem> lstTranStatus;	
	ListModelList<CodeItem> lstFilterBranch;
	ListModelList<CodeItem> lstFilterSub;
	ListModelList<CodeItem> lstTranType;
	ListModelList<CodeItem> lstFinMonth;
	ListModelList<CodeItem> lstFinYear;
	ListModelList<CodeItem> lstEvenBal;
	ListModelList<RateDetailModel> lstRateDetail;
	
	@Wire("#mratels")
	private Window ratels;
	
	@Init
	public void init() {

	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange({"lstMonthlyRate","lstFilterSub"})
	public void doSearch(){
		lstMonthlyRate = null;
		lstFilterBranch = null;
		lstFilterSub = null;
		//lstFinMonth = null;
		//lstFinYear = null;
		selected = null;
		param.getSub().setCode("");
		param.getSub().setDescription("All");
		//System.out.println(param.getBranch().getId());
	}
	
	@Command
	@NotifyChange({"lstMonthlyRate"})
	public void doSub(){
		lstFilterSub = null;
		lstMonthlyRate = null;	
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
	@NotifyChange({"lstMonthlyRate","param","selected","lstFilterBranch","lstFilterSub"})
	public void onClear(){
		lstMonthlyRate = null;
		lstFilterBranch = null;
		lstFilterSub = null;
		//lstFinMonth = null;
		//lstFinYear = null;
		selected = null;
		param = new ParamModel();
	}
	
	@Command
	public void onExport() {
		try {
			Workbook workbook = new HSSFWorkbook();
			Iterator<MonthlyRateModel> iterator = lstMonthlyRate.iterator();
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
			Cell cPre_type = row.createCell(7);
			Cell cPre_class = row.createCell(8);
			Cell cCur_type = row.createCell(9);
			Cell cCur_class = row.createCell(10);
			Cell cBal = row.createCell(11);
			Cell cMonthly_deposit = row.createCell(12);
			Cell cMonthly_status = row.createCell(13);
			
			cNo.setCellValue("No");
			cBr.setCellValue("Branch Code");
			cCif.setCellValue("CIF");
			cClient.setCellValue("CLINET NAME");
			cNum.setCellValue("#ACC");			
			cInital_type.setCellValue("INITIAL TYPE");
			cInital_class.setCellValue("INITIAL CLASS");
			cPre_type.setCellValue("PRE TYPE");
			cPre_class.setCellValue("PRE CLASS");
			cCur_type.setCellValue("CUR TYPE");
			cCur_class.setCellValue("CUR CLASS");
			cBal.setCellValue("BALANCE");
			cMonthly_deposit.setCellValue("MONTHLY DEPOSIT");
			cMonthly_status.setCellValue("MONTHLY STATUS");
			/*CellStyle style = workbook.createCellStyle();
	        Font font = workbook.createFont();
	        font.setColor(HSSFColor.RED.index);
	        style.setFont(font);*/
			while (iterator.hasNext()) {
				MonthlyRateModel c = iterator.next();
				row = sheet.createRow(rowIndex++);
				Cell No = row.createCell(0);
				Cell Br = row.createCell(1);				
				Cell Cif = row.createCell(2);
				Cell Client = row.createCell(3);
				Cell Num = row.createCell(4);
				Cell Inital_type = row.createCell(5);
				Cell Inital_class = row.createCell(6);					
				Cell Pre_type = row.createCell(7);
				Cell Pre_class = row.createCell(8);
				Cell Cur_type = row.createCell(9);
				Cell Cur_class = row.createCell(10);
				Cell Bal = row.createCell(11);
				Cell Monthly_deposit = row.createCell(12);
				Cell Monthly_status = row.createCell(13);
				
				No.setCellValue(c.getNo());
				Br.setCellValue(c.getBranch_code()+'-'+c.getBranch_name());
				Cif.setCellValue(c.getCif());
				Client.setCellValue(c.getCust_name());
				Num.setCellValue(c.getNum_account());
				Inital_type.setCellValue(c.getInitial_type());
				Inital_class.setCellValue(c.getInitial_class());
				Pre_type.setCellValue(c.getPre_type());
				Pre_class.setCellValue(c.getPre_class());
				Cur_type.setCellValue(c.getCur_type());
				Cur_class.setCellValue(c.getCur_class());
				Bal.setCellValue(c.getBalance());
				Monthly_deposit.setCellValue(c.getMonthly_deposit());
				Monthly_status.setCellValue(c.getMonthly_status());
				
			}
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			workbook.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			String now = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Filedownload
					.save(outArray, "application/ms-excel", "Monthly_Rating-"+now+".xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ListModelList<MonthlyRateModel> getLstMonthlyRate() {
		if(lstMonthlyRate==null){
			lstMonthlyRate = new ListModelList<>(RiskRateFacade.getLstMonthlyRate(param));
		}
		return lstMonthlyRate;
	}

	public void setLstMonthlyRate(ListModelList<MonthlyRateModel> lstMonthlyRate) {
		this.lstMonthlyRate = lstMonthlyRate;
	}	

	public ListModelList<RateDetailModel> getLstRateDetail() {
		if(lstRateDetail==null){
			lstRateDetail = new ListModelList<>(RiskRateFacade.getLstMonthlyDetail(selected.getCif(),selected.getFin_month(),selected.getFin_year()));
		}
		return lstRateDetail;
	}

	public void setLstRateDetail(ListModelList<RateDetailModel> lstRateDetail) {
		this.lstRateDetail = lstRateDetail;
	}	

	public ListModelList<CodeItem> getLstFinMonth() {
		if(lstFinMonth==null){
			if (lstFinMonth != null)
				return lstFinMonth;
			lstFinMonth = new ListModelList<CodeItem>();
			String[] desc = new String[] { "JAN", "FEB", "MAR","APR","MAY","JUN", "JUL", "AUG", "SEP",
					"OCT","NOV","DEC" };
			String[] code = new String[] { "JAN", "FEB", "MAR","APR","MAY","JUN", "JUL", "AUG", "SEP",
					"OCT","NOV","DEC" };
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				lstFinMonth.add(item);
			}
		}
		return lstFinMonth;
	}

	public void setLstFinMonth(ListModelList<CodeItem> lstFinMonth) {
		this.lstFinMonth = lstFinMonth;
	}

	public ListModelList<CodeItem> getLstFinYear() {
		if(lstFinYear==null){
			lstFinYear = new ListModelList<>(RiskRateFacade.getFinYearList());
		}
		return lstFinYear;
	}

	public void setLstFinYear(ListModelList<CodeItem> lstFinYear) {
		this.lstFinYear = lstFinYear;
	}

	public MonthlyRateModel getSelected() {
		return selected;
	}
	public void setSelected(MonthlyRateModel selected) {
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
	
	public ListModelList<CodeItem> getLstEvenBal() {
		if (lstEvenBal != null)
			return lstEvenBal;
		lstEvenBal = new ListModelList<CodeItem>();
		String[] desc = new String[] { "All", "MoveUp", "MoveDown" };
		String[] code = new String[] { "%", ">", "<" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			lstEvenBal.add(item);
		}		
		return lstEvenBal;
	}

	public void setLstEvenBal(ListModelList<CodeItem> lstEvenBal) {
		this.lstEvenBal = lstEvenBal;
	}

	public ParamModel getParam() {
		return param;
	}

	public void setParam(ParamModel param) {
		this.param = param;
	}
	
	
}
