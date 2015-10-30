package kredit.web.opp.data.model;

import java.util.Calendar;

import kredit.web.core.util.model.CodeItem;
import kredit.web.opp.data.model.facade.OperationFacade;

public class OpCommon {
	
	public final static int OP_PRODUCT_TYPE_LOAN = 0;
	public final static int OP_PRODUCT_TYPE_SAVING = 1;
	
	public final static String OP_LOAN = "OP_LOAN_DETAIL";
	public final static String OP_SAVING = "OP_SAVING_DETAIL";
	
	public final static String OP_STAFF_CODE = "OP_STAFF_CODE";

	public static CodeItem getCurrentMonth() {
		CodeItem month = new CodeItem();
		
		Calendar calendar = Calendar.getInstance();
		month.setId(calendar.get(Calendar.MONTH));
		month.setCode(OperationFacade.getMonthForInt(calendar.get(Calendar.MONTH)).substring(0, 3));
		month.setDescription(OperationFacade.getMonthForInt(calendar.get(Calendar.MONTH)));
		return month;
	}
	
	public static int getCurrentYear() {
		int year = 0;
		
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		
		return year;
	}
	
	public static CodeItem getMonth(String code) {
		CodeItem month = new CodeItem();
		
		switch (code) {
		case "Jan":
			month.setId(0);
			break;

		case "Feb":
			month.setId(1);
			break;
		
		case "Mar":
			month.setId(2);
			break;
			
		case "Apr":
			month.setId(3);
			break;
			
		case "May":
			month.setId(4);
			break;
			
		case "Jun":
			month.setId(5);
			break;
			
		case "Jul":
			month.setId(6);
			break;
			
		case "Aug":
			month.setId(7);
			break;
	
		case "Sep":
			month.setId(8);
			break;
	
		case "Oct":
			month.setId(9);
			break;
	
		case "Nov":
			month.setId(10);
			break;
	
		case "Dec":
			month.setId(11);
			break;
		}
		
		month.setCode(OperationFacade.getMonthForInt(month.getId()).substring(0, 3));
		month.setDescription(OperationFacade.getMonthForInt(month.getId()));
		
		return month;
	}
	
	public static boolean containNumber(String str) {
		boolean result = false;
		
		String tmp = str.substring(0, 3);
		if(tmp.matches("[0-9]+"))
		{
			result = true;
		}
		
		return result;
	}
}
