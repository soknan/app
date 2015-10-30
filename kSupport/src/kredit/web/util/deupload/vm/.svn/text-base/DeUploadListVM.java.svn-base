package kredit.web.util.deupload.vm;

import java.awt.Window;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.util.deupload.model.DeUpload;
import kredit.web.util.deupload.model.DeUploadHist;
import kredit.web.util.deupload.model.facade.DeuploadFacade;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;

public class DeUploadListVM {
	Media media;
	ListModelList<DeUpload> lstDe = new ListModelList<DeUpload>();
	ListModelList<DeUploadHist> lstDeHist;
	String filter="";
	DeUploadHist selectedHist;
	DeUpload de;
	DeUpload selected;
	String debit;
	String credit;
	String lcyDebit;
	String lcyCredit;
	Date valueDate;
	String batch = "";
	CodeItem branchDate;
	String msg="";
	boolean hqRole = true;
	String userFlexcube="";
	
	Date tranDate; 
	String area;
	String brCd = UserCredentialManager.getIntance().getLoginUsr().getBrCd();
	@Wire("dels")
	Window dels;
	
	ListModelList<CodeItem> lstUpload;
	CodeItem errUpload;
	
	public DeUploadListVM(){
		/*if(!DeuploadFacade.isHasRole()){
			msg="Your current user account don't has permission to upload. Please contact your administrator.";					
		}		
		lstDe = new ListModelList<DeUpload>(DeuploadFacade.getLstUploadHistBy(DeuploadFacade.getLstUploadHistory("%").get(0).getBatchNo(), "all"));
		batch = DeuploadFacade.getLstUploadHistory("%").get(0).getBatchNo();
		area = DeuploadFacade.getLstUploadHistory("%").get(0).getBrCode();
		valueDate = DeuploadFacade.getLstUploadHistory("%").get(0).getUploadDate();*/
	}

	@Command
	@NotifyChange({"lstDe","debit","credit","lcyDebit","lcyCredit","valueDate","batch","tranDate","selectedHist"})
	public void onUpload(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) {	
		onClear();
		lstDe = new ListModelList<DeUpload>();
		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			media = upEvent.getMedia();			
			try {
				// Check Valid file
				if (!Arrays.asList("xlsx", "xls").contains(media.getFormat())) {
					Clients.showNotification(
							"invalid file, should be .xls or .xlsx !",
							Clients.NOTIFICATION_TYPE_ERROR, null,
							"middle_center", 0);
					return;
				}

				if (media.getName().endsWith("csv")) {
					Clients.showNotification(
							"invalid file, should be .xls or .xlsx !",
							Clients.NOTIFICATION_TYPE_ERROR, null,
							"middle_center", 0);
					return;
				}			
				
				
				InputStream excelFile = media.getStreamData();
				Workbook wb = WorkbookFactory.create(excelFile);
				Sheet sheet = wb.getSheetAt((short)0);				
				//Check Number of Field
				if(sheet.getRow(7) == null){
					Clients.showNotification(" Invalid Excel File !",
							Clients.NOTIFICATION_TYPE_ERROR, null,
							"middle_center", 0);
					return;
				}
				if (sheet.getRow(7).getPhysicalNumberOfCells() != 19) {
					Clients.showNotification(" Invalid Excel File !",
							Clients.NOTIFICATION_TYPE_ERROR, null,
							"middle_center", 0);
					return;
				}
					
				handleSheet(sheet);				
				//System.out.println(area);
				valueDate = new SimpleDateFormat("yyyy-MM-dd").parse(DeuploadFacade.getDateByBranch(area));				
				batch  = DeuploadFacade.getAutoBatch();
				//Update Batch to sys_setting
				DeuploadFacade.updateBatch(batch);
				//Check Debit and Credit.
				if(lcyDebit!=null){
				if(!lcyDebit.equalsIgnoreCase(lcyCredit)){
					onClear();
					Clients.showNotification(
							"LCY Debit and LCY Credit not equal.Please check your excel file. And try again!.",
							Clients.NOTIFICATION_TYPE_ERROR, null,
							"middle_center", 0);
					return;
				}}
				selectedHist = null;				
			} catch (Exception e) {
				e.printStackTrace();
				onClear();
				Clients.showNotification("There are some Invalid format fields. Please Check your excel file and try again !.",
						Clients.NOTIFICATION_TYPE_ERROR, null,
						"middle_center", 0);
			}
		}
	}
	
	@Command
	@NotifyChange({"selected","lstDe","debit","credit","lcyDebit","lcyCredit","batch","area"})
	public void onUploadFlexcube(){		
		//recall batch_no
		//batch  = DeuploadFacade.getAutoBatch();
		try {
			if(valueDate.equals(new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01"))){
				/*valueDate = new SimpleDateFormat("yyyy-MM-dd").parse("2014-12-31");
				
				//insert DETB_UPLOAD_MASTER
				String sql2 = "Insert into DETB_UPLOAD_MASTER (BRANCH_CODE,SOURCE_CODE,BATCH_NO,TOTAL_ENTRIES,UPLOADED_ENTRIES,BALANCING,BATCH_DESC,MIS_REQUIRED,AUTO_AUTH,"
						+ "GL_OFFSET_ENTRY_REQD,UDF_UPLOAD_REQD,OFFSET_GL,TXN_CODE,DR_ENT_TOTAL,CR_ENT_TOTAL,USER_ID,UPLOAD_STAT,JOBNO,SYSTEM_BATCH,POSITION_REQD,MAKER_ID,"
						+ "MAKER_DT_STAMP,CHECKER_ID,CHECKER_DT_STAMP,MOD_NO,AUTH_STAT,RECORD_STAT,ONCE_AUTH,UPLOAD_DATE,UPLOAD_FILE_NAME) "
						+ "Values("
						+ "'"+area+"'," // branch_code
						+ "'FLEXCUBE'," //source_code
						+ "'"+batch+"',"//batch_no
						+ "'',"//total_entries
						+ "'',"//upload_entries
						+ "'N',"//balancing
						+ "'DE Upload User = "+userFlexcube.toUpperCase()+"  and Batch = "+batch+" For "+new SimpleDateFormat("MMM-yy").format(valueDate)+"',"//batch_des
						+ "'',"//mis_required
						+ "'N',"//auto_auth
						+ "'N',"//gl_offset_entry
						+ "'N',"//UDF_upload
						+ "'N',"//offset_gl
						+ "'',"//txn_code
						+ "'',"//dr_ent_total
						+ "'',"//cr_ent_total
						+ "'"+userFlexcube.toUpperCase()+"',"//user_id
						+ "'U',"//upload_stat
						+ "'',"//jobno
						+ "'',"//system_batch
						+ "'Y',"//position_reqd
						+ "'"+userFlexcube.toUpperCase()+"',"//maker_id
						+ "TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),"//maker_dt_stamp
						+ "'',"//checker_id
						+ "'',"//checker_dt_stamp
						+ "'1',"//mod_no
						+ "'U',"//auth_stat
						+ "'O',"//record_stat
						+ "'Y',"//once_auth
						+ "TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),"//upload_date
						+ "'')";
				
				SqlUpdate update2 = Ebean.createSqlUpdate(sql2);
				Ebean.execute(update2);
				
				StringBuilder strBuilder = Common.createLogStringBuilder();
				strBuilder.append("Add New DETB_UPLOAD_MASTER -->"+sql2);
				Common.addSessionLog("dels", strBuilder.toString(), new Date());
		
				for (DeUpload d : lstDe) {
					//Insert DETB_UPLOAD_DETAIL
					String sql = "Insert into DETB_UPLOAD_DETAIL (SOURCE_CODE,BRANCH_CODE,CURR_NO,UPLOAD_STAT,CCY_CD,INITIATION_DATE,AMOUNT,"
							+ "ACCOUNT,ACCOUNT_BRANCH,TXN_CODE,DR_CR,LCY_EQUIVALENT,EXCH_RATE,VALUE_DATE,BATCH_NO,UPLOAD_DATE,FIN_CYCLE,PERIOD_CODE,ADDL_TEXT,RELATED_ACCOUNT,INSTRUMENT_NO)"
							+ " VALUES("
							+ "'FLEXCUBE','" // source_code
							+area+"','"//branch_code
							+d.getNo()//curr_no
							+"','U','"//Upload_stat
							+d.getCurrency()//CCY_CD
							+"',TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),'"//INITIATION_DATE
							+d.getAmount()+"','"//AMOUNT
							+d.getAcc()+"','"//ACCOUNT
							+d.getAccBrCode()+"','"//ACCOUNT_BRANCH
							+d.getTrCode()+"','"//TXN_CODE
							+d.getDc()+"','"//DR_CR
							+d.getLcyAmount()+"','"//LCY_EQUIVALENT
							+d.getExcRate()//EXCH_RATE
							+"',TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(valueDate)+"','yyyy-mm-dd'),'"//VALUE_DATE
							+batch//BATCH_NO
							+"',TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),"//UPLOAD_DATE
							+ "'FY"+new SimpleDateFormat("yyyy").format(valueDate)+"',"//FIN_CYCLE
							+ "'FIN','"//PERIOD_CODE
							+d.getTranDes().replaceAll("'", "''")+'#'+d.getVoucherNo()+'#'+d.getCdvOff()+'#'+d.getRefNo()+"',"//ADDL_TEXT
							+ "'','"//RELATED_ACCOUNT
							+d.getInsNo()+"'"//INSTRUMENT_NO
							+ ")";			
					SqlUpdate update = Ebean.createSqlUpdate(sql);
					Ebean.execute(update);
					
					strBuilder = Common.createLogStringBuilder();
					strBuilder.append("Add New DETB_UPLOAD_DETAIL -->"+sql);
					Common.addSessionLog("dels", strBuilder.toString(), new Date());
				}*/
			}else{
				//insert DETB_UPLOAD_MASTER
				String sql2 = "Insert into DETB_UPLOAD_MASTER (BRANCH_CODE,SOURCE_CODE,BATCH_NO,TOTAL_ENTRIES,UPLOADED_ENTRIES,BALANCING,BATCH_DESC,MIS_REQUIRED,AUTO_AUTH,"
						+ "GL_OFFSET_ENTRY_REQD,UDF_UPLOAD_REQD,OFFSET_GL,TXN_CODE,DR_ENT_TOTAL,CR_ENT_TOTAL,USER_ID,UPLOAD_STAT,JOBNO,SYSTEM_BATCH,POSITION_REQD,MAKER_ID,"
						+ "MAKER_DT_STAMP,CHECKER_ID,CHECKER_DT_STAMP,MOD_NO,AUTH_STAT,RECORD_STAT,ONCE_AUTH,UPLOAD_DATE,UPLOAD_FILE_NAME) "
						+ "Values("
						+ "'"+area+"'," // branch_code
						+ "'FLEXCUBE'," //source_code
						+ "'"+batch+"',"//batch_no
						+ "'',"//total_entries
						+ "'',"//upload_entries
						+ "'N',"//balancing
						+ "'DE Upload User = "+userFlexcube.toUpperCase()+"  and Batch = "+batch+" For "+new SimpleDateFormat("MMM-yy").format(valueDate)+"',"//batch_des
						+ "'',"//mis_required
						+ "'N',"//auto_auth
						+ "'N',"//gl_offset_entry
						+ "'N',"//UDF_upload
						+ "'N',"//offset_gl
						+ "'',"//txn_code
						+ "'',"//dr_ent_total
						+ "'',"//cr_ent_total
						+ "'"+userFlexcube.toUpperCase()+"',"//user_id
						+ "'U',"//upload_stat
						+ "'',"//jobno
						+ "'',"//system_batch
						+ "'Y',"//position_reqd
						+ "'"+userFlexcube.toUpperCase()+"',"//maker_id
						+ "TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),"//maker_dt_stamp
						+ "'',"//checker_id
						+ "'',"//checker_dt_stamp
						+ "'1',"//mod_no
						+ "'U',"//auth_stat
						+ "'O',"//record_stat
						+ "'Y',"//once_auth
						+ "TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),"//upload_date
						+ "'')";
				
				SqlUpdate update2 = Ebean.createSqlUpdate(sql2);
				Ebean.execute(update2);
				
				StringBuilder strBuilder = Common.createLogStringBuilder();
				strBuilder.append("Add New DETB_UPLOAD_MASTER -->"+sql2);
				Common.addSessionLog("dels", strBuilder.toString(), new Date());
		
				for (DeUpload d : lstDe) {
					//Insert DETB_UPLOAD_DETAIL
					String sql = "Insert into DETB_UPLOAD_DETAIL (SOURCE_CODE,BRANCH_CODE,CURR_NO,UPLOAD_STAT,CCY_CD,INITIATION_DATE,AMOUNT,"
							+ "ACCOUNT,ACCOUNT_BRANCH,TXN_CODE,DR_CR,LCY_EQUIVALENT,EXCH_RATE,VALUE_DATE,BATCH_NO,UPLOAD_DATE,FIN_CYCLE,PERIOD_CODE,ADDL_TEXT,RELATED_ACCOUNT,INSTRUMENT_NO)"
							+ " VALUES("
							+ "'FLEXCUBE','" // source_code
							+area+"','"//branch_code
							+d.getNo()//curr_no
							+"','U','"//Upload_stat
							+d.getCurrency()//CCY_CD
							+"',TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),'"//INITIATION_DATE
							+d.getAmount()+"','"//AMOUNT
							+d.getAcc()+"','"//ACCOUNT
							+d.getAccBrCode()+"','"//ACCOUNT_BRANCH
							+d.getTrCode()+"','"//TXN_CODE
							+d.getDc()+"','"//DR_CR
							+d.getLcyAmount()+"','"//LCY_EQUIVALENT
							+d.getExcRate()//EXCH_RATE
							+"',TO_DATE('"+new SimpleDateFormat("yyyy-MM-dd").format(valueDate)+"','yyyy-mm-dd'),'"//VALUE_DATE
							+batch//BATCH_NO
							+"',TO_DATE('"+DeuploadFacade.getDateByBranch(this.area)+"','yyyy-mm-dd'),"//UPLOAD_DATE
							+ "'FY"+new SimpleDateFormat("yyyy").format(valueDate)+"',"//FIN_CYCLE
							+ "'"+new SimpleDateFormat("MMM").format(valueDate).toUpperCase()+"','"//PERIOD_CODE
							+d.getTranDes().replaceAll("'", "''")+'#'+d.getVoucherNo()+'#'+d.getCdvOff()+'#'+d.getRefNo()+"',"//ADDL_TEXT
							+ "'','"//RELATED_ACCOUNT
							+d.getInsNo()+"'"//INSTRUMENT_NO
							+ ")";			
					SqlUpdate update = Ebean.createSqlUpdate(sql);
					Ebean.execute(update);
					
					strBuilder = Common.createLogStringBuilder();
					strBuilder.append("Add New DETB_UPLOAD_DETAIL -->"+sql);
					Common.addSessionLog("dels", strBuilder.toString(), new Date());
				}
			}
			if(Integer.valueOf(batch) > Integer.valueOf(DeuploadFacade.getAutoBatch())){
				//Update Batch to sys_setting
				DeuploadFacade.updateBatch(batch);
			}			
					
			Clients.showNotification("Upload Successfully ",
						Clients.NOTIFICATION_TYPE_INFO, null,
						"middle_center", 4100);		
			// Clear List
			onClear();
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		
	}
	
	@NotifyChange({"lstDe","debit","credit","lcyDebit","lcyCredit","batch","area"})
	public void handleSheet(Sheet sheet) {
		int i=1;
		for (Row row : sheet) {
			 
			// Skip 8 row			
			if (row.getRowNum() < 8) {				
				if(row.getRowNum()==1){
					debit = String.format("%.2f",row.getCell(13).getNumericCellValue());
					lcyDebit = String.format("%.2f",row.getCell(14).getNumericCellValue());
				}
				if(row.getRowNum()==2){
					row.getCell(1,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
					area = row.getCell(1).getStringCellValue();
					credit = String.format("%.2f",row.getCell(13).getNumericCellValue());
					lcyCredit = String.format("%.2f",row.getCell(14).getNumericCellValue());					
				}				
				if(row.getRowNum()==5){
					try{						
						tranDate = row.getCell(1).getDateCellValue();
					}catch(Exception e){
						e.printStackTrace();
					}					
				}
				continue;
			}
			//System.out.println(area);
			row.getCell(0,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);			
			RichTextString str = (RichTextString) row.getCell(0).getRichStringCellValue(); 
			//System.out.println(str);
			if((str != null && str.length() > 0) && !str.getString().startsWith("~")) {
				// Convert type of excel cell
				row.getCell(1,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(2,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(3,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(4,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(5,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(6,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(7,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(8,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);	
				row.getCell(9,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(10,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(11,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(12,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(13,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(14,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_NUMERIC);
				row.getCell(15,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_NUMERIC);
				row.getCell(16,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_NUMERIC);
				row.getCell(17,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_NUMERIC);
				row.getCell(18,Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_NUMERIC);

				//Check account Exist
				if(!DeuploadFacade.isMatchAccount(row.getCell(8).getStringCellValue())){					
					lstDe = null;
					Clients.showNotification("Record No : "+row.getCell(0).getStringCellValue()
					+"</br> Account No : "+row.getCell(8).getStringCellValue()
					+"</br> is not match Account Code in FLEXCUBE. Please Change Account Code in Excel File and try again!",
						Clients.NOTIFICATION_TYPE_ERROR,null,"middle_center", 0);						
					return;
				}
				if(!DeuploadFacade.isMatchSystemDate(area, row.getCell(3).getStringCellValue())){
					lstDe = null;
					Clients.showNotification("Record No : "+row.getCell(0).getStringCellValue()
							+"</br> Account No : "+row.getCell(8).getStringCellValue()
							+"</br> Cannot upload due to branch "+row.getCell(3).getStringCellValue()+" date different from branch "+area+" !",
								Clients.NOTIFICATION_TYPE_ERROR,null,"middle_center", 0);
					return;
				}
				// Write data to List
				de = new DeUpload();		
				de.setNo(String.valueOf(i));
				//System.out.println(de.getNo());
				de.setTranDes(row.getCell(1).getStringCellValue());				
				de.setVoucherNo(row.getCell(2).getStringCellValue());
				de.setAccBrCode(row.getCell(3).getStringCellValue());
				de.setBrName(row.getCell(4).getStringCellValue());
				de.setCdvOff(row.getCell(5).getStringCellValue().toUpperCase());								
				de.setReffNo(row.getCell(6).getStringCellValue());				
				de.setAccDes(row.getCell(7).getStringCellValue());				
				de.setAcc(row.getCell(8).getStringCellValue());				
				de.setTrCode(row.getCell(9).getStringCellValue());				
				de.setTranCodeDes(row.getCell(10).getStringCellValue());				
				de.setInsNo(row.getCell(11).getStringCellValue());				
				de.setDc(row.getCell(12).getStringCellValue().toUpperCase());				
				de.setCurrency(row.getCell(13).getStringCellValue());
				//format amount				
				de.setAmount(String.format("%.2f", row.getCell(14)
						.getNumericCellValue()));				
				de.setExcRate(String.format("%.2f", row.getCell(15)
						.getNumericCellValue()));				
				de.setLcyAmount(String.format("%.2f", row.getCell(16)
						.getNumericCellValue()));				
				de.setChecking(String.format("%.2f", row.getCell(17)
						.getNumericCellValue()));				
				de.setCheckingLcy(String.format("%.2f", row.getCell(18)
						.getNumericCellValue()));				
				//add to list
				lstDe.add(de);
				i++;
			}			
		}		
	}
	
	@Command
	@NotifyChange({"selected","lstDe","debit","credit","batch"})
	public void onClear(){
		tranDate = null;
		debit = null;		
		credit = null;
		lcyDebit = null;
		lcyCredit = null;
		batch = "";
		area = "";
		selected = null;
		valueDate = null;
		lstDe = null;
		//Force NotifyChange
		BindUtils.postNotifyChange(null, null, this, "*");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@Command
	public void onConfirmUpload() {	

		//check user role from flexcube
		if(!DeuploadFacade.isHasRole(userFlexcube.toUpperCase())){
			Clients.showNotification(
					"Your current user account don't have permission to upload. Please contact your administrator.",
					Clients.NOTIFICATION_TYPE_ERROR, null,
					"middle_center", 0);
			return;
		}		
		//System.out.println(DeuploadFacade.getHomeBranch(area,userFlexcube.toUpperCase()));
		if(!DeuploadFacade.getHomeBranch(area,userFlexcube.toUpperCase()).equals(area)){
			Clients.showNotification("Your Home Branch '"+DeuploadFacade.getHomeBranch(area,userFlexcube.toUpperCase())+"' On Flexcube Not Match With Your Current Branch '"+area+"' !",
					Clients.NOTIFICATION_TYPE_ERROR, null,
					"middle_center", 0);
			return;
		}
		
		if(lstDe.size()==0){
			lstDe = new ListModelList<DeUpload>();
			Clients.showNotification(
					"No Data on List. Please browse your Data.",
					Clients.NOTIFICATION_TYPE_ERROR, null,
					"middle_center", 0);
			return;
		}
		// Check history
		if(selectedHist!=null){
			Clients.showNotification(
					"Do not upload data from history. Please browse the new source.",
					Clients.NOTIFICATION_TYPE_ERROR, null,
					"middle_center", 0);
			return;
		}
		// Check Batch exist
		if(DeuploadFacade.isBatchExist(batch)){
			Clients.showNotification(
					"Batch No is Duplicate. ("+batch+"). Please Change Batch Number and Try again!",
					Clients.NOTIFICATION_TYPE_ERROR, null,
					"middle_center", 0);
			return;
		}
		Messagebox.show("Are you sure you want to upload records to FLEXCUBE ?",
				"Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event evtt)
							throws InterruptedException {
						if (evtt.getName().equals("onOK")) {												
							onUploadFlexcube();							
						}
					}
				});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void onConfirmDelete() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {			 		
					public void onEvent(org.zkoss.zk.ui.event.Event evt) throws InterruptedException{
						if (evt.getName().equals("onOK")) {								
							onDelete();
						}
					}
				});
	}

	@Command	
	@NotifyChange({"selected","lstDe","debit","credit"})
	public void onDelete() {		
		//valueDate = null;
		debit = null;
		credit = null;
		batch = "";
		lstDe.remove(selected);
		selected = null;		
		if(lstDe.getSize()== 0){
			lstDe = null;
			
		}
	}
	
	@Command
	@NotifyChange({"lstDeHist"})
	public void onSearchHist(){		
		lstDeHist = null;		
	}
	
	@Command
	@NotifyChange({"lstDe"})
	public void onOpen(){
		if(selectedHist == null){
			return;
		}
		onClear();
		lstDe = new ListModelList<DeUpload>(DeuploadFacade.getLstUploadHistBy(selectedHist.getBatchNo().toString(),errUpload.getCode()));
		batch = selectedHist.getBatchNo();
		area = selectedHist.getBrCode();
		valueDate = selectedHist.getUploadDate();
	}
	
	// Getter and Setter

	public ListModelList<DeUpload> getLstDe() {
		if(lstDe == null){
			lstDe = new ListModelList<DeUpload>();
		}
		return lstDe;
	}

	public CodeItem getBranchDate() {
		return branchDate;
	}

	public void setBranchDate(CodeItem branchDate) {
		this.branchDate = branchDate;
	}

	public void setLstDe(ListModelList<DeUpload> lstDe) {
		this.lstDe = lstDe;
	}

	public DeUpload getDe() {
		return de;
	}

	public void setDe(DeUpload de) {
		this.de = de;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public DeUpload getSelected() {
		return selected;
	}

	public void setSelected(DeUpload selected) {
		this.selected = selected;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public Window getDels() {
		return dels;
	}

	public void setDels(Window dels) {
		this.dels = dels;
	}

	public Date getValueDate(){		
		return valueDate;
	}

	public void setValueDate(Date valueDate) {		
		this.valueDate = valueDate;
	}

	public String getBatch() {		
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getLcyDebit() {
		return lcyDebit;
	}

	public void setLcyDebit(String lcyDebit) {
		this.lcyDebit = lcyDebit;
	}

	public String getLcyCredit() {
		return lcyCredit;
	}

	public void setLcyCredit(String lcyCredit) {
		this.lcyCredit = lcyCredit;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public ListModelList<DeUploadHist> getLstDeHist() {
		if(lstDeHist==null){
			lstDeHist = new ListModelList<DeUploadHist>(DeuploadFacade.getLstUploadHistory(filter));
		}
		return lstDeHist;
	}

	public void setLstDeHist(ListModelList<DeUploadHist> lstDeHist) {
		this.lstDeHist = lstDeHist;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public DeUploadHist getSelectedHist() {
		return selectedHist;
	}

	public void setSelectedHist(DeUploadHist selectedHist) {
		this.selectedHist = selectedHist;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	public ListModelList<CodeItem> getLstUpload() {
		if (lstUpload != null)
			return lstUpload;
		lstUpload = new ListModelList<CodeItem>();
		String[] desc = new String[] { "All", "Error"};
		String[] code = new String[] { "all", "error" };
		for (int i = 0; i < code.length; i++) {
			CodeItem item = new CodeItem();
			item.setCode(code[i]);
			item.setDescription(desc[i]);
			lstUpload.add(item);
		}		
		return lstUpload;
	}

	public void setLstUpload(ListModelList<CodeItem> lstUpload) {
		this.lstUpload = lstUpload;
	}

	public CodeItem getErrUpload() {
		if (errUpload != null) {
			return errUpload;
		}
		errUpload = new CodeItem();
		errUpload.setCode("all");
		errUpload.setDescription("All");		
		return errUpload;
	}

	public void setErrUpload(CodeItem errUpload) {
		this.errUpload = errUpload;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isHqRole() {
		if(DeuploadFacade.getUserRole().equals("HQ")){
			hqRole = false;
		}
		return hqRole;
	}

	public void setHqRole(boolean hqRole) {
		this.hqRole = hqRole;
	}

	public String getuserFlexcube() {
		return userFlexcube;
	}

	public void setuserFlexcube(String userFlexcube) {
		this.userFlexcube = userFlexcube;
	}

	
}
