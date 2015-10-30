package kredit.web.kiva.vm;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import kredit.web.core.util.db.Sql2oHelper;
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

public class KivaPostVM {
	ListModelList<KivaIDLModel> lstFetchCIF = new ListModelList<KivaIDLModel>();
	ListModelList<KivaIDLModel> tmpLstCIF = new ListModelList<KivaIDLModel>();
	KivaIDLModel tmpCIF;
	KivaIDLModel tmpSelected;	
	KivaIDLModel paramCIF = new KivaIDLModel();
	Set<KivaIDLModel> selectedCIF;

	@Wire("#kipls")
	Window winKivaPost;
	
	//private PagingList<CIF> accPagingList;
	private int totalSize;
	
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
		if(tmpSelected != null){
			tmpLstCIF.remove(tmpSelected);
			tmpSelected = null;
			BindUtils.postNotifyChange(null, null, this, "*");
		}		
	}
	
	@Command
	@NotifyChange({ "lstFetchCIF","selectedCIF"})
	public void onShowFetchCIF(){
		try{
			selectedCIF = null;
			lstFetchCIF = null;	
			Executions.createComponents("/kiva/account.zul", winKivaPost,null);		
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
	    	   
	    	   Iterator<KivaIDLModel> iterator = tmpLstCIF.iterator();
	    	   Row row; 
	    	     
	    	   List<KivaScheduleModel> tmpSch = new ArrayList<KivaScheduleModel>();
	    	   List<String> tmp = new ArrayList<>();
	           while(iterator.hasNext()){	
	        	   KivaIDLModel rowCif = iterator.next();
	               String sql = "select SCHEDULE_DUE_DATE,COMPONENT_NAME,CASE WHEN COMPONENT_NAME = 'PRINCIPAL' THEN AMOUNT_DUE ELSE 0 END as priDue,"
	               		+ "CASE WHEN COMPONENT_NAME = 'MAIN_INT' THEN AMOUNT_DUE ELSE 0 END as intDue "	               		
	               		+ "from CLTB_ACCOUNT_SCHEDULES where (COMPONENT_NAME = 'MAIN_INT' or COMPONENT_NAME='PRINCIPAL') "
	               		+ "and ACCOUNT_NUMBER='"+rowCif.getAccountNo()+"' order by SCHEDULE_DUE_DATE asc";
	               tmpSch = Sql2oHelper.sql2o.open().createQuery(sql)
	            		   .addColumnMapping("SCHEDULE_DUE_DATE", "scheduleDueDate")
	            		   .addColumnMapping("COMPONENT_NAME", "componentName")
	            		   .addColumnMapping("intDue", "intDue")	 
	            		   .addColumnMapping("priDue", "priDue")	 
	            		   .executeAndFetch(KivaScheduleModel.class);
	               if(tmpSch.size()==0){
	            	   Clients.showNotification("No Data on Account Number : '"+rowCif.getAccountNo()+"'!",Clients.NOTIFICATION_TYPE_WARNING,null, "middle_center", 0);
		    		   continue;
	               }
	               //array available
	               tmp.add(rowCif.getAccountNo());
	               //Create Sheet
	        	   Sheet sheet = workbook.createSheet(rowCif.getAccountNo());	               	               
	               //System.out.println(tmpSch.size());
	               int rowIndex=5;
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
	               
	               row = sheet.createRow(1);	               
	               Cell fname = row.createCell(0);	            		  	               
	               fname.setCellValue(rowCif.getFnameEn());
	               Cell lname = row.createCell(1);	            		  	               
	               lname.setCellValue(rowCif.getLnameEn());
        		   Cell sex = row.createCell(2);	            		  	               
        		   sex.setCellValue(rowCif.getSex());
        		   Cell amount = row.createCell(3);	            		  	               
        		   amount.setCellValue(rowCif.getAmountDisburse());        		   
        		   Cell acc = row.createCell(4);	            		  	               
        		   acc.setCellValue(rowCif.getAccountNo());
        		   Cell cus = row.createCell(5);	            		  	               
        		   cus.setCellValue(rowCif.getCustomerNo());

        		   Row row1 = sheet.createRow(rowIndex-1);
	               Cell cellD = row1.createCell(0);	            		  	               
        		   cellD.setCellValue("Due Date");	            		   
        		   Cell cellP = row1.createCell(1);
        		   cellP.setCellValue("Principal");
        		   Cell cellt = row1.createCell(2);
        		   cellt.setCellValue("Interest"); 
        		   String p = "";
        		   String i = "";       		   
	               for (KivaScheduleModel c : tmpSch) {            	   
	            	   if(c.getComponentName().equals("PRINCIPAL")){	            		    
	            		   if(i.isEmpty()){
	            			   row = sheet.createRow(rowIndex++);          	   
				               Cell cellS = row.createCell(0);			               
				               cellS.setCellValue(c.getScheduleDueDate());
				               p = c.getPriDue().toString();				                         
	            		   }	            		          	   
			               Cell cellA = row.createCell(1);	            		  
			               cellA.setCellValue(c.getPriDue());
			               i = "";
	            	   }
	            	   if(c.getComponentName().equals("MAIN_INT")){ 	            		    	            		  
	            		   if(p.isEmpty()){
	            			   row = sheet.createRow(rowIndex++);          	   
	            			   Cell cellS = row.createCell(0);			               
	            			   cellS.setCellValue(c.getScheduleDueDate());
	            			   i = c.getIntDue().toString();	            			   
	            		   }	            		   
	            		   Cell cellB = row.createCell(2);
			               cellB.setCellValue(c.getIntDue()); 
			               p = "";
	            		}
	            	   
	               }	               
	           }
	           if(tmp.size()>0){
	        	   // write it as an excel attachment
	        	   ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
	        	   workbook.write(outByteStream);
		           byte [] outArray = outByteStream.toByteArray();	           
		           Filedownload.save(outArray, "application/ms-excel", "raiseLoan.xls");
		           //System.out.println("written successfully");	    	   
	           }
	           
	       }catch(Exception e){
	    	   e.printStackTrace();
	       }		
	}
	
	@Command
	@NotifyChange({"tmpLstCIF","lstFetchCIF"})
	public void onAddList(){		
		if(selectedCIF!=null){
			tmpLstCIF.addAll(selectedCIF);
			lstFetchCIF.removeAll(selectedCIF);
			selectedCIF = null;
		}		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command	
	public void onConfirmClear() {
		Messagebox.show("Are you sure you want to delete all of this record?",
				"Delete Confirmation", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						if (evt.getName().equals("onOK")) {
							onClearList();
						}
					}
				});
	}
	
	@Command
	@NotifyChange({"tmpLstCIF"})
	public void onClearList(){		
		tmpLstCIF = null;
		selectedCIF = null;
		BindUtils.postNotifyChange(null, null, this, "*");
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
		paramCIF = new KivaIDLModel();
		lstFetchCIF = null;		
	}	

	public Set<KivaIDLModel> getSelectedCIF() {
		return selectedCIF;
	}
	public void setSelectedCIF(Set<KivaIDLModel> selectedCIF) {
		this.selectedCIF = selectedCIF;
	}
	public Window getWinKivaPost() {
		return winKivaPost;
	}

	public void setWinKivaPost(Window winKivaPost) {
		this.winKivaPost = winKivaPost;
	}	
	
	public ListModelList<KivaIDLModel> getTmpLstCIF() {
		if(tmpLstCIF == null) tmpLstCIF = new ListModelList<KivaIDLModel>();
		return tmpLstCIF;
	}
	public void setTmpLstCIF(ListModelList<KivaIDLModel> tmpLstCIF) {
		this.tmpLstCIF = tmpLstCIF;
	}

	public ListModelList<KivaIDLModel> getLstFetchCIF() {
		if(lstFetchCIF==null){
			lstFetchCIF = new ListModelList<KivaIDLModel>(
					KivaFacade.getLstFetchCifAccIDL(paramCIF));
			
			
		}
		
		for (KivaIDLModel k : tmpLstCIF) {
			for (int j = 0; j < lstFetchCIF.size(); j++) {
				if(lstFetchCIF.get(j).getAccountNo().equals(k.getAccountNo())){
					lstFetchCIF.remove(j);
					break;
				}
			}	
		}
		
		//Set multiple selection to list
		lstFetchCIF.setMultiple(true);	
		return lstFetchCIF;
	}
	
	public void setLstFetchCIF(ListModelList<KivaIDLModel> lstFetchCIF) {
		this.lstFetchCIF = lstFetchCIF;		
	}	
	
	public KivaIDLModel getTmpCIF() {		
		return tmpCIF;
	}
	public void setTmpCIF(KivaIDLModel tmpCIF) {
		this.tmpCIF = tmpCIF;		
	}
	public KivaIDLModel getParamCIF() {
		if (paramCIF != null) return paramCIF;
		return paramCIF;
	}

	public void setParamCIF(KivaIDLModel paramCIF) {
		this.paramCIF = paramCIF;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}


	public KivaIDLModel getTmpSelected() {
		return tmpSelected;
	}

	public void setTmpSelected(KivaIDLModel tmpSelected) {
		this.tmpSelected = tmpSelected;
	}	
	
	
}
