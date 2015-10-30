/**
 * 
 */
package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import kredit.web.core.util.db.DbHelper;
import kredit.web.kbureau.model.report.ParamCbcReport;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WebApp;

/**
 * @author vathenan
 *
 */
public class TestVM {
	
	ParamCbcReport param = new ParamCbcReport();
	
	@Command
	public void onShowReport() {

		try {

			ExpXLS();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ExpXLS(){
        try {
                //JRXhtmlExporter exporter=new JRXhtmlExporter();
     
                JRXlsxExporter exporter = new JRXlsxExporter();
            //request.getSession(true).setAttribute("IMAGES_MAP", imageMap);
                //
                //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                        //response.setHeader("Content-Disposition", "attachment;filename=example.xlsx");
                ServletOutputStream out = null;
                try {
					out = ((HttpServletResponse) Executions.getCurrent().getNativeResponse()).getOutputStream();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            HttpServletResponse res = (HttpServletResponse)Executions.getCurrent().getNativeResponse(); 
            
            res.setContentType("application/vnd.ms-excel"); 
            //res.setHeader("Content-Disposition", "attachment; filename="+"Report.xls"); 
                
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
			
			File sourceFile = new File(webApp.getRealPath("/rpt") + "/ActionByDayRprt.jasper");
			
			InputStream inputStream = webApp.getResourceAsStream("/rpt/ActionByDayRprt.jasper");
			
            JasperPrint jpPrint = JasperFillManager.fillReport(inputStream, params, conn);
            
            File destFile = new File(sourceFile.getParent(),
            		jpPrint.getName() + ".xls");
                
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jpPrint);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                          
            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
            
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
            exporter.exportReport();
        }
    
        catch (JRException ex) {
            ex.printStackTrace();
            
        }
    }
}
