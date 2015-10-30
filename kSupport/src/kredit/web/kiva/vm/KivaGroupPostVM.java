package kredit.web.kiva.vm;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.kiva.model.KivaGroupModel;
import kredit.web.kiva.model.KivaIDLModel;
import kredit.web.kiva.model.KivaScheduleModel;
import kredit.web.kiva.model.facade.KivaFacade;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.DefaultCommand;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class KivaGroupPostVM {
	ListModelList<KivaGroupModel> lstFetchCIF = new ListModelList<KivaGroupModel>();
	ListModelList<KivaGroupModel> tmpLstCIF = new ListModelList<KivaGroupModel>();
	KivaGroupModel tmpCIF;
	KivaGroupModel tmpSelected;	
	KivaGroupModel paramCIF=new KivaGroupModel();
	Set<KivaGroupModel> selectedCIF;
		
	@Wire("#kgpls")
	Window winKivaGrlPost;
	

	@Init
	public void init(@ExecutionArgParam("obj") Object obj) {
		if (obj == null) {
			return;
		}		
	}
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command	
	public void onConfirmDelete() {
		Messagebox.show("Are you sure you want to delete this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDelete();
						}
					}
				});
	}
	
	@Command
	@NotifyChange({"tmpSelected","tmpLstCIF"})
	public void onDelete(){
		if(tmpSelected !=null){
			tmpLstCIF.remove(tmpSelected);
			tmpSelected = null;
			BindUtils.postNotifyChange(null, null, this, "*");
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command	
	public void onConfirmDeleteAll() {
		Messagebox.show("Are you sure you want to delete all of this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onDeleteAll();
						}
					}
				});
	}
	
	@Command
	@NotifyChange({"tmpSelected","tmpLstCIF"})
	public void onDeleteAll(){
		tmpLstCIF = null;
		tmpSelected = null;
		BindUtils.postNotifyChange(null, null, this, "*");		
	}
	
	@Command
	@NotifyChange({ "lstFetchCIF","selectedCIF"})
	public void onShowFetchCIF(){
		try{
			selectedCIF = null;
			lstFetchCIF = null;		
			Executions.createComponents("/kiva/groupAccount.zul", winKivaGrlPost,null);
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	@Command
	@NotifyChange({"lstFetchCIF"})
	public void onSearchCIF() {			
		lstFetchCIF = null;	
		selectedCIF = null;
	}
	
	@DefaultCommand
	public void dummy() {

	}
	
	@Command
	public void onPost(){		
	       try {
	    	   if(tmpLstCIF.size()<=0){
	    		   Clients.showNotification("No Data On List !",Clients.NOTIFICATION_TYPE_ERROR,null, "middle_center", 4100);
	    		   return;
	    	   }
	    	   Workbook workbook = new HSSFWorkbook();	    	  
	    	   
	    	   Iterator<KivaGroupModel> iterator = tmpLstCIF.iterator();
	    	   Row row; 
	    	     
	    	   List<KivaScheduleModel> tmpSch = new ArrayList<KivaScheduleModel>();
	    	   List<String> tmp = new ArrayList<>();
	           while(iterator.hasNext()){	
	        	   KivaGroupModel rowCif = iterator.next();
	               String sql = "select SCHEDULE_DUE_DATE,sum(CASE WHEN COMPONENT_NAME = 'MAIN_INT' THEN AMOUNT_DUE ELSE 0 END) as intDue,"
	            		+ "sum(CASE WHEN COMPONENT_NAME = 'PRINCIPAL' THEN AMOUNT_DUE ELSE 0 END) as priDue  "
	               		+ "from CLTB_ACCOUNT_SCHEDULES sch LEFT JOIN CLTB_ACCOUNT_APPS_MASTER CL on CL.ACCOUNT_NUMBER = sch.ACCOUNT_NUMBER "
	               		+ "LEFT JOIN MFI_GROUP_MEMBER gm on gm.ACCOUNT = sch.ACCOUNT_NUMBER "
	               		+ "LEFT JOIN MFI_GROUP g on g.ID = gm.GROUP_ID "
	               		+ "where (COMPONENT_NAME = 'MAIN_INT' or COMPONENT_NAME='PRINCIPAL') "
	               		+ "and CL.PRODUCT_CODE in('0201','0301','0401') "
	               		+ "and g.GROUP_ACC_NO = '"+rowCif.getGroupAccNo()+"' GROUP BY sch.SCHEDULE_DUE_DATE ORDER BY sch.SCHEDULE_DUE_DATE asc";
	               tmpSch = Sql2oHelper.sql2o.open().createQuery(sql)
	            		   .addColumnMapping("SCHEDULE_DUE_DATE", "scheduleDueDate")
	            		   .addColumnMapping("priDue", "priDue")
	            		   .addColumnMapping("intDue", "intDue")
	            		   .executeAndFetch(KivaScheduleModel.class);
	               if(tmpSch.size()==0){
	            	   Clients.showNotification("No Data on Group Account Number : </br>"+rowCif.getGroupAccNo()+" !",Clients.NOTIFICATION_TYPE_WARNING,null, "middle_center", 0);
		    		   continue;
	               }
	               //count available
	               tmp.add(rowCif.getGroupAccNo());
	               String sqlGrl = "SELECT CP.FIRST_NAME AS fnameEn,CP.Last_Name AS lnameEn,CP.SEX AS sex,"
	               		+ "cl.AMOUNT_FINANCED AS amountDisburse,CL.ACCOUNT_NUMBER AS accountNo,"
	               		+ "C.CUSTOMER_NO as customerNo,CL.PRODUCT_CATEGORY as prdCat FROM CLTB_ACCOUNT_MASTER CL "
	               		+ "INNER JOIN STTM_CUSTOMER C "
	               		+ "ON CL.CUSTOMER_ID = C.CUSTOMER_NO INNER JOIN STTM_CUST_PERSONAL CP "
	               		+ "ON CL.CUSTOMER_ID = CP.CUSTOMER_NO "
	               		+ "INNER JOIN MFI_GROUP_MEMBER gm on gm.account = CL.account_number "
	               		+ "INNER JOIN MFI_GROUP g on g.ID = gm.GROUP_ID "
	               		+ "where CL.PRODUCT_CODE in ('0201','0301','0401') and g.GROUP_ACC_NO = '"+rowCif.getGroupAccNo()+"'";
	               List<KivaIDLModel> tmpCif = Sql2oHelper.sql2o.open().createQuery(sqlGrl)	            		   	
	            		   .executeAndFetch(KivaIDLModel.class);	              
	               //Create Sheet
	        	   Sheet sheet = workbook.createSheet(rowCif.getGroupAccNo());	              
	               int rowIndex=1;
	               row = sheet.createRow(0);
	               Cell cell0 = row.createCell(0);
	               Cell cell1 = row.createCell(1);
	               Cell cell2 = row.createCell(2);
	               Cell cell3 = row.createCell(3);
	               Cell cell4 = row.createCell(4);
	               Cell cell5 = row.createCell(5);
	               
	               cell0.setCellValue("First Name");
	               cell1.setCellValue("Last Name");
	               cell2.setCellValue("Sex");
	               cell3.setCellValue("Amount");
	               cell4.setCellValue("Loan ID");
	               cell5.setCellValue("Customer ID");
	               for (KivaIDLModel c : tmpCif) {	
	            	   row = sheet.createRow(rowIndex++);           	             
		               Cell fname = row.createCell(0);	            		  	               
		               fname.setCellValue(c.getFnameEn());
		               Cell lname = row.createCell(1);	            		  	               
		               lname.setCellValue(c.getLnameEn());
	        		   Cell sex = row.createCell(2);	            		  	               
	        		   sex.setCellValue(c.getSex());
	        		   Cell amount = row.createCell(3);	            		  	               
	        		   amount.setCellValue(c.getAmountDisburse());        		   
	        		   Cell acc = row.createCell(4);	            		  	               
	        		   acc.setCellValue(c.getAccountNo());
	        		   Cell cus = row.createCell(5);	            		  	               
	        		   cus.setCellValue(c.getCustomerNo());
	               }
	               rowIndex = rowIndex +2;
	               Row row1 = sheet.createRow(rowIndex-1);
	               Cell cellD = row1.createCell(0);	            		  	               
        		   cellD.setCellValue("Due Date");	            		   
        		   Cell cellP = row1.createCell(1);
        		   cellP.setCellValue("Principal");
        		   Cell cellt = row1.createCell(2);
        		   cellt.setCellValue("Interest"); 
	               for(KivaScheduleModel s : tmpSch){	            	   
            		   row = sheet.createRow(rowIndex++);
	            	   Cell cellS = row.createCell(0);		               
		               cellS.setCellValue(s.getScheduleDueDate());
		               Cell cellA = row.createCell(1);
		               cellA.setCellValue(s.getPriDue());
		               Cell cellB = row.createCell(2);
		               cellB.setCellValue(s.getIntDue());
	               }
	           }	       
	           //if stream data 
	           if(tmp.size() >0){ 
	        	   // write it as an excel attachment
	        	   ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
	        	   workbook.write(outByteStream);
		           byte [] outArray = outByteStream.toByteArray();	           
		           Filedownload.save(outArray, "application/ms-excel", "raiseGrlLoan.xls");		           	    
	           }
	           	   
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }		
	}
	
	@Command
	@NotifyChange("tmpLstCIF")
	public void onAddList(){		
		if(selectedCIF!=null){
			tmpLstCIF.addAll(selectedCIF);	
			lstFetchCIF.removeAll(selectedCIF);
			selectedCIF = null;
		}		
	}
	
	@Command
	@NotifyChange({"lstFetchCIF","selectedCIF"})
	public void onClearSelect(){
		if(selectedCIF !=null){
			selectedCIF.clear();
		}		
	}
	@Command
	@NotifyChange({"lstFetchCIF","paramCIF"})
	public void onResetSearchCIF() {
		paramCIF = new KivaGroupModel();
		lstFetchCIF = null;		
	}	

	public Set<KivaGroupModel> getSelectedCIF() {
		return selectedCIF;
	}
	public void setSelectedCIF(Set<KivaGroupModel> selectedCIF) {
		this.selectedCIF = selectedCIF;
	}
	public Window getWinKivaGrlPost() {
		return winKivaGrlPost;
	}

	public void setWinKivaGrlPost(Window winKivaPost) {
		this.winKivaGrlPost = winKivaPost;
	}	
	
	public ListModelList<KivaGroupModel> getTmpLstCIF() {
		if(tmpLstCIF == null) tmpLstCIF = new ListModelList<KivaGroupModel>();
		return tmpLstCIF;
	}
	public void setTmpLstCIF(ListModelList<KivaGroupModel> tmpLstCIF) {
		this.tmpLstCIF = tmpLstCIF;
	}

	public ListModelList<KivaGroupModel> getLstFetchCIF() {
		if(lstFetchCIF==null){
			lstFetchCIF = new ListModelList<KivaGroupModel>(
					KivaFacade.getLstFetchCifAccGRL(paramCIF));
		}
		for (KivaGroupModel k : tmpLstCIF) {
			for (int j = 0; j < lstFetchCIF.size(); j++) {
				if(lstFetchCIF.get(j).getGroupAccNo().equals(k.getGroupAccNo())){
					lstFetchCIF.remove(j);
					break;
				}
			}	
		}
		//Set multiple selection to list
		lstFetchCIF.setMultiple(true);	
		return lstFetchCIF;
	}
	
	public void setLstFetchCIF(ListModelList<KivaGroupModel> lstFetchCIF) {
		this.lstFetchCIF = lstFetchCIF;		
	}	
	
	public KivaGroupModel getTmpCIF() {		
		return tmpCIF;
	}
	public void setTmpCIF(KivaGroupModel tmpCIF) {
		this.tmpCIF = tmpCIF;		
	}
	public KivaGroupModel getParamCIF() {
		if (paramCIF != null) return paramCIF;
		paramCIF = new KivaGroupModel();
		return paramCIF;
	}

	public void setParamCIF(KivaGroupModel paramCIF) {
		this.paramCIF = paramCIF;
	}

	public KivaGroupModel getTmpSelected() {
		return tmpSelected;
	}

	public void setTmpSelected(KivaGroupModel tmpSelected) {
		this.tmpSelected = tmpSelected;
	}	
	
	
}
