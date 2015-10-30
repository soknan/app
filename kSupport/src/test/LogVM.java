package test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import kredit.flexcube.model.CIF;
import kredit.web.core.util.log.model.LoggerSession;
import kredit.web.core.util.log.model.LoggerSessionLog;
import kredit.web.mfi.model.CB;

import org.apache.log4j.Logger;
import org.k.model.User;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Paging;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

public class LogVM {

	Logger logger = Logger.getLogger(LogVM.class);
	private ListModelList<CIF> lstFetchCIF;
	private CIF paramCIF;

	private int pageIndex;
	private PagingList<CIF> pagingList;
	
	private int totalSize;
	private int pageSize = 10;


	@Command
	public void onTestLogger() throws Exception {
		try {
			List<LoggerSessionLog> lst = Ebean.find(LoggerSessionLog.class)
					.findList();
			
		} catch (Exception e) {
			logger.error("Error onTestLogger ", e);
			throw e;
		}
	}

	@Command
	public void onTestUser() throws Exception {
		try {
			List<User> lstU = Ebean.find(User.class).findList();
			Clients.showNotification("<br/> User: " + lstU.size());
		} catch (Exception e) {
			logger.error("Error onTestUser ", e);
			throw e;
		}
	}

	@Command
	public void onDownloadLog(@BindingParam("i") int i) throws Exception {
		try {
			String solPath = "/u01/app/oracle/product/Middleware/user_projects/domains/subsys_domain/servers/SubSystem/logs/my_logs/";
			String nuxPath = "D:\\home\\opt\\ksupport_log\\";
			String path = "";

			if (i == 0) {
				path = solPath;
			} else {
				path = nuxPath;
			}

			path = path + "ksupport.log";

			String content = readFile(path);
			InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));

			Filedownload.save(is, "text/xml", "ksuppport.log");

		} catch (Exception e) {
			logger.error("Error download log ", e);
			throw e;
		}
	}

	public String readFile(String filePath) throws Exception {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"));
			try {
				String str;

				while ((str = in.readLine()) != null) {
					builder.append(str);
				}
			} catch (IOException e) {
				logger.error("Error reading file template.", e);
				throw e;
			}
		} catch (UnsupportedEncodingException e1) {
			logger.error("Error reading file template.", e1);
			throw e1;
		} catch (FileNotFoundException e1) {
			logger.error("Error reading file template.", e1);
			throw e1;
		}
		return builder.toString();
	}

	@NotifyChange({"totalSize","lstFetchCIF"})
	public ListModelList<CIF> getLstFetchCIF() {
		if (lstFetchCIF == null) {

			pagingList = Ebean.find(CIF.class).findPagingList(
					pageSize);
			pagingList.getFutureRowCount();

			// get the first page
			Page<CIF> page = pagingList.getPage(0);

			// get the beans from the page as a list
			List<CIF> list = page.getList();
			
			totalSize = page.getTotalRowCount();

			lstFetchCIF = new ListModelList<CIF>(list);
		}
		return lstFetchCIF;
	}

	public CIF getParamCIF() {
		if (paramCIF != null)
			return paramCIF;
		paramCIF = new CIF();
		paramCIF.setBrCd("133");
		return paramCIF;
	}

	/**
	 * @param lstFetchCIF
	 *            the lstFetchCIF to set
	 */
	public void setLstFetchCIF(ListModelList<CIF> lstFetchCIF) {
		this.lstFetchCIF = lstFetchCIF;
	}

	@GlobalCommand
	@NotifyChange("pageIndex")
	public void notifyPaging(@BindingParam("pageIndex") int pageIndex) {
		setPageIndex(pageIndex);
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	
	@NotifyChange("lstFetchCIF")
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		
		// get the first page
		Page<CIF> page = pagingList.getPage(pageIndex);

		// get the beans from the page as a list
		List<CIF> list = page.getList();
		lstFetchCIF = new ListModelList<CIF>(list);
	}

	/**
	 * @return the totalSize
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize the totalSize to set
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@Command
	public void renderLst(){
		
	}

}
