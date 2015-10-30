package kredit.web.writeOff.vm;

import java.util.Date;
import java.util.List;
import java.util.Map;

import kredit.web.core.model.Privilege;
import kredit.web.core.util.CMD;
import kredit.web.core.util.Common;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.model.CodeItem;
import kredit.web.security.model.facade.SecurityFacade;
import kredit.web.writeOff.model.Co;
import kredit.web.writeOff.model.Loan;
import kredit.web.writeOff.model.ParamAcc;
import kredit.web.writeOff.model.ParamCo;
import kredit.web.writeOff.model.ParamLoan;
import kredit.web.writeOff.model.Repayment;
import kredit.web.writeOff.model.TrnCo;
import kredit.web.writeOff.model.facade.WriteOffFacade;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
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
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Window;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;

public class MainVM {
	ListModelList<Loan> lst;
	ListModelList<Co> lstCo;
	ListModelList<Loan> lstAcc;
	ListModelList<CodeItem> rows;
	CodeItem selectedNumRow;
	int rowPerPage = 10;
	Privilege privilege = null;

	Loan selectedLoan = new Loan();
	Co selectedCo = new Co();
	Repayment selectedRepayment = new Repayment();
	Loan selectedImport = new Loan();
	ParamLoan param = new ParamLoan();
	ParamCo paramCo = new ParamCo();
	ParamAcc paramAcc = new ParamAcc();
	ListModelList<CodeItem> filterCurrency;
	ListModelList<CodeItem> filterBranches;
	ListModelList<CodeItem> filterCategory;
	ListModelList<CodeItem> filterProduct;
	ListModelList<CodeItem> filterSubBranches;
	ListModelList<CodeItem> filterWCategory;
	ListModelList<CodeItem> wCategory;
	ListModelList<CodeItem> filterSubBranchesCo;
	@Wire("#wols")
	private Window wols;
	
	private static final int TAB_INFO = 0;
	private static final int TAB_REPAYMENT = 1;
	
	int selectedTabIndex;
	boolean visible;
	boolean visiblePayment;
	boolean visibleImport;
	boolean visibleCo;
	boolean visibleAcc;
	boolean visibleIL;
	boolean disable = true;
	private int totalSize;
	private int totalSizeAcc;
	boolean infoChange = true;
	
	boolean disableBr;
	boolean disableSbr;
	
	boolean visibleHistory;
	boolean visibleTransfer;
	boolean visibleCoH;
	TrnCo selectedTrnCo = new TrnCo();
	
	//Pagination
	private PagingList<Loan> pagingList;
	private int pageIndex;
	private int totalSizeL;
	
//GETER & SETTER

	public PagingList<Loan> getPagingList() {
		return pagingList;
	}

	public void setPagingList(PagingList<Loan> pagingList) {
		this.pagingList = pagingList;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}

	@NotifyChange("lst")
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		// get the first page
		Page<Loan> page = pagingList.getPage(pageIndex);

		// get the beans from the page as a list
		List<Loan> lstT = page.getList();
		lst = new ListModelList<Loan>(lstT);
	}
	
	public int getTotalSizeL() {
		return totalSizeL;
	}

	public void setTotalSizeL(int totalSizeL) {
		this.totalSizeL = totalSizeL;
	}
	
	/*
	public ListModelList<Loan> getLst() {
		if(lst == null)
		{
			lst = new ListModelList<Loan>(WriteOffFacade.getLoansList(param));
		}
		return lst;
	}
	*/
	
	@NotifyChange({ "lst", "totalSizeL" })
	public ListModelList<Loan> getLst() {
		if(lst != null)
		{
			return lst;
		}
		
		Query<Loan> query = Ebean.find(Loan.class)
				.select("cif, nameEn, brCd, accountNo, prodCode, woffDate, totWoff, ccy, accuLnRec, netLoss, category");
		
		if(!(param.getFilter() == null || param.getFilter().trim().equals("")))
		{
			query.where()
			.disjunction()
			.ilike("cif", Common.addLikeExpression(param.getFilter()))
			.ilike("nameEn", Common.addLikeExpression(param.getFilter()))
			.ilike("accountNo", Common.addLikeExpression(param.getFilter()))
			.ilike("prodCode", Common.addLikeExpression(param.getFilter()))
			.endJunction();
		}
		
		if(!(param.getBranch().getCode().equals("")))
		{
			if(param.getBranch().getCode().equals("000"))
			{
				query.where()
				.in("brCd", param.getBranch().getCode());
			}
			else
			{
				query.where()
				.in("brCd", WriteOffFacade.getSubBranchCodeList(param.getBranch().getId()));
			}
		}
		
		if(!(param.getSubBranch().getCode().equals("")))
		{
			query.where()
			.eq("brCd", param.getSubBranch().getCode());
		}
		
		if(!(param.getCurrency().getCode().equals("") || param.getCurrency().getCode().equals("All")))
		{
			query.where()
			.eq("ccy", param.getCurrency().getCode());
		}
		
		if(!(param.getStDate() == null))
		{
			query.where()
			.ge("woffDate", param.getStDate());
		}
		
		if(!(param.getEnDate() == null))
		{
			query.where()
			.le("woffDate", param.getEnDate());
		}
		
		if(!(param.getStAmount() == null))
		{
			query.where()
			.ge("totWoff", param.getStAmount());
		}
		
		if(!(param.getEnAmount() == null))
		{
			query.where()
			.le("totWoff", param.getEnAmount());
		}
		
		if(!(param.getCategory().getDescription().equals("All")))
		{
			query.where()
			.eq("prodCat", param.getCategory().getDescription());
		}
		
		if(!(param.getProduct().getCode().trim().equals("")))
		{
			query.where()
			.eq("prodCode", param.getProduct().getCode());
		}
		
		if(!(param.getwCategory().getCode().equals("") || param.getwCategory().getCode().equals("All")))
		{
			query.where()
			.eq("category", param.getwCategory().getDescription());
		}
		
		pagingList = query.orderBy("woffDate").findPagingList(Integer.parseInt(selectedNumRow.getCode()));
		
		pagingList.getFutureRowCount();

		// get the first page
		Page<Loan> page = pagingList.getPage(0);

		// get the beans from the page as a list
		List<Loan> lstT = page.getList();

		totalSizeL = page.getTotalRowCount();

		lst = new ListModelList<>(lstT);
		
		return lst;
	}
	
	public void setLst(ListModelList<Loan> lst) {
		this.lst = lst;
	}

	public ListModelList<Co> getLstCo() {
		if(lstCo == null)
		{
			lstCo = new ListModelList<Co>(WriteOffFacade.getCoList(paramCo));
		}
		return lstCo;
	}

	public void setLstCo(ListModelList<Co> lstCo) {
		this.lstCo = lstCo;
	}
	
	public ListModelList<Loan> getLstAcc() {
		if(lstAcc == null)
		{
			lstAcc = new ListModelList<Loan>(WriteOffFacade.getAccountList(paramAcc));
		}
		return lstAcc;
	}

	public void setLstAcc(ListModelList<Loan> lstAcc) {
		this.lstAcc = lstAcc;
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
			privilege = Common.getPrivilege(CMD.LIST_WOF_LOAN);
		}
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public Loan getSelectedLoan() {
		return selectedLoan;
	}

	public void setSelectedLoan(Loan selectedLoan) {
		this.selectedLoan = selectedLoan;
	}

	public Repayment getSelectedRepayment() {
		return selectedRepayment;
	}

	public void setSelectedRepayment(Repayment selectedRepayment) {
		this.selectedRepayment = selectedRepayment;
	}

	public ParamLoan getParam() {
		return param;
	}

	public void setParam(ParamLoan param) {
		this.param = param;
	}

	public ListModelList<CodeItem> getFilterCurrency() {
		if(filterCurrency == null)
		{
			filterCurrency = new ListModelList<CodeItem>();
			String[] desc = new String[] {"All", "KHR", "USD"};
			String[] code = new String[] {"All", "KHR", "USD"};
			for (int i = 0; i < code.length; i++) {
				CodeItem item = new CodeItem();
				item.setCode(code[i]);
				item.setDescription(desc[i]);
				filterCurrency.add(item);
			}
		}
		return filterCurrency;
	}

	public void setFilterCurrency(ListModelList<CodeItem> filterCurrency) {
		this.filterCurrency = filterCurrency;
	}

	public Co getSelectedCo() {
		return selectedCo;
	}

	public void setSelectedCo(Co selectedCo) {
		this.selectedCo = selectedCo;
	}

	public ListModelList<CodeItem> getFilterBranches() {
		if(filterBranches == null)
		{
			filterBranches = new ListModelList<CodeItem>(WriteOffFacade.getBranchesListWithAll());
		}
		return filterBranches;
	}

	public void setFilterBranches(ListModelList<CodeItem> filterBranches) {
		this.filterBranches = filterBranches;
	}
	
	public ListModelList<CodeItem> getFilterSubBranches() {
		if(filterSubBranches == null)
		{
			filterSubBranches = new ListModelList<CodeItem>(WriteOffFacade.getSubBranchesListWithAll(param.getBranch().getId()));
		}
		return filterSubBranches;
	}

	public void setFilterSubBranches(ListModelList<CodeItem> filterSubBranches) {
		this.filterSubBranches = filterSubBranches;
	}

	public ListModelList<CodeItem> getFilterCategory() {
		if(filterCategory == null)
		{
			filterCategory = new ListModelList<CodeItem>(WriteOffFacade.getProductCategories());
		}
		return filterCategory;
	}

	public void setFilterCategory(ListModelList<CodeItem> filterCategory) {
		this.filterCategory = filterCategory;
	}

	public ListModelList<CodeItem> getFilterProduct() {
		if(filterProduct == null)
		{
			filterProduct = new ListModelList<CodeItem>(WriteOffFacade.getProductList());
		}
		return filterProduct;
	}

	public void setFilterProduct(ListModelList<CodeItem> filterProduct) {
		this.filterProduct = filterProduct;
	}

	public ListModelList<CodeItem> getFilterWCategory() {
		if(filterWCategory == null)
		{
			filterWCategory = new ListModelList<CodeItem>(WriteOffFacade.getWofCategoryList());
		}
		return filterWCategory;
	}

	public void setFilterWCategory(ListModelList<CodeItem> filterWCategory) {
		this.filterWCategory = filterWCategory;
	}

	public ListModelList<CodeItem> getwCategory() {
		if(wCategory == null)
		{
			wCategory = new ListModelList<CodeItem>(WriteOffFacade.getWofCategoryList());
		}
		return wCategory;
	}

	public void setwCategory(ListModelList<CodeItem> wCategory) {
		this.wCategory = wCategory;
	}

	public int getSelectedTabIndex() {
		return selectedTabIndex;
	}

	public void setSelectedTabIndex(int selectedTabIndex) {
		this.selectedTabIndex = selectedTabIndex;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isVisiblePayment() {
		return visiblePayment;
	}

	public void setVisiblePayment(boolean visiblePayment) {
		this.visiblePayment = visiblePayment;
	}
	
	public boolean isVisibleImport() {
		return visibleImport;
	}

	public void setVisibleImport(boolean visibleImport) {
		this.visibleImport = visibleImport;
	}	
	
	public Loan getSelectedImport() {
		return selectedImport;
	}

	public void setSelectedImport(Loan selectedImport) {
		this.selectedImport = selectedImport;
	}

	public ParamCo getParamCo() {
		return paramCo;
	}

	public void setParamCo(ParamCo paramCo) {
		this.paramCo = paramCo;
	}

	public boolean isVisibleCo() {
		return visibleCo;
	}

	public void setVisibleCo(boolean visibleCo) {
		this.visibleCo = visibleCo;
	}

	public boolean isVisibleAcc() {
		return visibleAcc;
	}

	public void setVisibleAcc(boolean visibleAcc) {
		this.visibleAcc = visibleAcc;
	}
	
	public int getTotalSize() {
		totalSize = lstCo.getSize();
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public ParamAcc getParamAcc() {
		return paramAcc;
	}

	public void setParamAcc(ParamAcc paramAcc) {
		this.paramAcc = paramAcc;
	}

	public int getTotalSizeAcc() {
		totalSizeAcc = lstAcc.getSize();
		return totalSizeAcc;
	}

	public void setTotalSizeAcc(int totalSizeAcc) {
		this.totalSizeAcc = totalSizeAcc;
	}
	
	public boolean isVisibleIL() {
		return visibleIL;
	}

	public void setVisibleIL(boolean visibleIL) {
		this.visibleIL = visibleIL;
	}
	
	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
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
	
	public ListModelList<CodeItem> getFilterSubBranchesCo() {
		if(filterSubBranches == null)
		{
			filterSubBranches = new ListModelList<CodeItem>(WriteOffFacade.getSubBranchesListWithAll(param.getBranch().getId()));
		}
		return filterSubBranches;
	}

	public void setFilterSubBranchesCo(ListModelList<CodeItem> filterSubBranchesCo) {
		this.filterSubBranchesCo = filterSubBranchesCo;
	}
	
	public boolean isVisibleHistory() {
		return visibleHistory;
	}

	public void setVisibleHistory(boolean visibleHistory) {
		this.visibleHistory = visibleHistory;
	}
	
	public boolean isVisibleTransfer() {
		return visibleTransfer;
	}

	public void setVisibleTransfer(boolean visibleTransfer) {
		this.visibleTransfer = visibleTransfer;
	}

	public boolean isVisibleCoH() {
		return visibleCoH;
	}

	public void setVisibleCoH(boolean visibleCoH) {
		this.visibleCoH = visibleCoH;
	}

	public TrnCo getSelectedTrnCo() {
		return selectedTrnCo;
	}

	public void setSelectedTrnCo(TrnCo selectedTrnCo) {
		this.selectedTrnCo = selectedTrnCo;
	}
	
//END GETER & SETTER 

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}
	
	public MainVM() 
	{
		Map<String, String> rolesMap = SecurityFacade.getRoleCode(
				UserCredentialManager.getIntance().getLoginUsr().getId());
		
		CodeItem branch = WriteOffFacade.getBranch(
				UserCredentialManager.getIntance().getLoginUsr().getBrCd());
		
		if(rolesMap.containsValue("adm") || rolesMap.containsValue("hq"))
		{
			disableBr = false;
			disableSbr = false;			
			return;
		}
		
		if(rolesMap.containsValue("bm"))
		{
			disableBr = true;
			disableSbr = false;
			
			param.setBranch(branch);
			
			return;
		}
		/*
		if(rolesMap.containsValue("sbm"))
		{
			disableBr = true;
			disableSbr = true;
			
			param.setSubBranch(branch);
			
			return;
		}
		*/
		param.setSubBranch(branch);
		
		param.getBranch().setId(branch.getSuperId());
		param.getBranch().setCode(branch.getSuperCode());
		param.getBranch().setDescription(branch.getSuperDescription());
		
		disableBr = true;
		disableSbr = true;
	}
	
	@Command
	@NotifyChange({ "lst", "param" })
	public void doSearch()
	{
		lst = null;
	}
	
	@Command
	@NotifyChange({ "lst", "param", "selectedLoan" })
	public void onClearAll()
	{
		param = new ParamLoan();
		selectedLoan = new Loan();
		doSearch();
	}
	
	@Command
	@NotifyChange({ "lst", "selectedLoan", "visibleImport", "disable" })
	public void onNew()
	{
		selectedLoan = new Loan();
		visibleImport = true;
		disable = true;
		
		boolean wait = wols.hasFellow("importDetail");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/import.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "lst", "selectedLoan", "selectedTabIndex", "visible", "infoChange" })
	public void onOpen()
	{
		
		selectedTabIndex = TAB_INFO;
		visible = true;
		infoChange = true;
		
		boolean wait = wols.hasFellow("loanDetail");
		
		if(wait)
			return;

		Executions.createComponents("/writeOff/detail.zul", wols, null);
		
	}
	
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onSave()
	{	
		if(selectedLoan.getId() == null)
		{
			selectedLoan.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		else
		{
			selectedLoan.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		Ebean.save(selectedLoan);
		
		Clients.showNotification("Account: " + selectedLoan.getAccountNo() + " has been saved");
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" saved Account --> ");
		strBuilder.append(selectedLoan.toString());
		Common.addSessionLogCommit(CMD.LIST_WOF_LOAN, strBuilder.toString(),
				new Date());
		
		doSearch();
	}
	
	@Command
	@NotifyChange({ "lst", "selectedLoan", "visibleImport" })
	public void onSaveImport()
	{
		if(!(selectedLoan.getAccountNo().trim().equals("")))
		{
			if(selectedLoan.getId() == null)
			{
				selectedLoan.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			}
			else
			{
				selectedLoan.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			}
			
			Ebean.save(selectedLoan);
			
			Clients.showNotification("Account: " + selectedLoan.getAccountNo() + " has been imported");
			
			StringBuilder strBuilder = Common.createLogStringBuilder();
			strBuilder.append(" imported Account --> ");
			strBuilder.append(selectedLoan.toString());
			Common.addSessionLogCommit(CMD.LIST_WOF_LOAN, strBuilder.toString(),
					new Date());
			
			visibleImport = false;
			
			doSearch();
		}
		else
		{
			Clients.showNotification("Please input Account No.", "warning", null, "middle_center", -1);
		}
	}
	
	@Command
	@NotifyChange({ "visible" })
	public void onCloseDetail()
	{
		visible = false;
	}
	
	@Command
	@NotifyChange({ "visibleImport" })
	public void onCloseImport()
	{
		visibleImport = false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
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
	@NotifyChange({ "lst", "selectedLoan" })
	public void onDelete()
	{
		Ebean.delete(selectedLoan);
		
		lst.remove(selectedLoan);
		
		Clients.showNotification("Account: " + selectedLoan.getAccountNo() + " has been deleted");
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" deleted Account --> ");
		strBuilder.append(selectedLoan.toString());
		Common.addSessionLogCommit(CMD.LIST_WOF_LOAN, strBuilder.toString(),
				new Date());
	}
	
	@Command
	@NotifyChange({ "selectedTabIndex" })
	public void onSelectTab(@BindingParam("tab") Tabpanel tab)
	{	 
		String url = "";
		switch (selectedTabIndex) {
		case TAB_REPAYMENT:
			url = "/writeOff/payment.zul";
			break;
			
		}
		
		boolean wait = tab.getChildren().isEmpty();
		if (wait) {
			Component comp = Executions.createComponents(url, wols, null);
			tab.appendChild(comp);
		}
	}
	
	@Command
	@NotifyChange({ "selectedLoan", "selectedRepayment", "visiblePayment" })
	public void onNewPayment()
	{
		selectedRepayment = new Repayment();
		visiblePayment = true;
		selectedRepayment.setWof_id(selectedLoan.getId());
		selectedRepayment.setBrCd(selectedLoan.getBrCd());
		selectedRepayment.setCoFullName(selectedLoan.getCoFullName());
		selectedRepayment.setCoShortName(selectedLoan.getCoShortName());
		CodeItem tmp = new CodeItem();
		tmp.setCode(selectedLoan.getCcy());
		tmp.setDescription(selectedLoan.getCcy());
		selectedRepayment.setCurrency(tmp);;
		boolean wait = wols.hasFellow("repayment");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/paymentDetail.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "selectedLoan", "selectedRepayment", "visiblePayment" })
	public void onOpenPayment()
	{
		visiblePayment = true;
		
		boolean wait = wols.hasFellow("repayment");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/paymentDetail.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "lst", "selectedLoan", "selectedRepayment", "visiblePayment" })
	public void onSavePayment()
	{
		if(selectedRepayment.getId() == null)
		{
			selectedRepayment.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			selectedLoan.getRepayment().add(selectedRepayment);
			
			//Increase the amount of AccLnRec
			selectedLoan.setAccuLnRec(selectedLoan.getAccuLnRec() + selectedRepayment.getAmount());
			selectedLoan.setNetLoss(selectedLoan.getTotWoff() - selectedLoan.getAccuLnRec());
		}
		else
		{
			selectedRepayment.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
			Double sumAccuLn = 0.0;
			for(int i = 0; i < selectedLoan.getRepayment().size(); i++)
			{
				sumAccuLn += selectedLoan.getRepayment().get(i).getAmount();
			}
			selectedLoan.setAccuLnRec(sumAccuLn);
			selectedLoan.setNetLoss(selectedLoan.getTotWoff() - selectedLoan.getAccuLnRec());
		}
		
		Ebean.save(selectedRepayment);
		onSaveNoNotify();
		
		Clients.showNotification("Repayment: " + selectedRepayment.getId() + " (" + selectedRepayment.getAmount() + 
				" " + selectedRepayment.getCcy() + ") has been saved");
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" saved Repayment --> ");
		strBuilder.append(selectedRepayment.toString());
		Common.addSessionLogCommit(CMD.LIST_WOF_LOAN, strBuilder.toString(),
				new Date());
		
		onClosePayment();
		lst = null;
	}
	
	@Command
	@NotifyChange({ "visiblePayment" })
	public void onClosePayment()
	{
		visiblePayment = false;
	}
	
	@Command
	@NotifyChange({ "lst", "selectedLoan" })
	public void onSaveNoNotify()
	{	
		selectedLoan.setChangeBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		Ebean.save(selectedLoan);
		doSearch();
	}
	
	@Command
	@NotifyChange({ "lstCo", "visibleCo", "paramCo" })
	public void onShowFetchCo()
	{
		paramCo = new ParamCo();
		paramCo.setFilter(selectedRepayment.getCoFullName());
		lstCo = null;
		visibleCo = true;
		boolean wait = wols.hasFellow("winCo");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/co.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "lstCo" })
	public void onSearchCo()
	{
		lstCo = null;
	}
	
	@Command
	@NotifyChange({ "visibleCo" })
	public void onCloseCo()
	{
		visibleCo = false;
	}
	
	@Command
	@NotifyChange({ "visibleCo", "selectedRepayment" })
	public void onSelectCo()
	{
		selectedRepayment.setCoFullName(selectedCo.getFullName());
		selectedRepayment.setCoShortName(selectedCo.getShortName());
		visibleCo = false;
	}
	
	@Command
	@NotifyChange({ "lstAcc", "visibleAcc", "paramAcc" })
	public void onShowFetchAcc()
	{
		paramAcc = new ParamAcc();
		lstAcc = null;
		visibleAcc = true;
		
		boolean wait = wols.hasFellow("winAcc");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/account.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "lstAcc" })
	public void onSearchAcc()
	{
		lstAcc = null;
	}
	
	@Command
	@NotifyChange({ "visibleAcc" })
	public void onCloseAcc()
	{
		visibleAcc = false;
	}
	
	@Command
	@NotifyChange({ "visibleAcc", "selectedLoan", "disable" })
	public void onSelectAcc()
	{
		visibleAcc = false;
		disable = false;
		double principle = WriteOffFacade.getAmountByCateogry(selectedImport.getWoffDate(), 
				selectedImport.getAccountNo(), Common.PRINCIPAL);
		double interest = WriteOffFacade.getAmountByCateogry(selectedImport.getWoffDate(), 
				selectedImport.getAccountNo(), Common.INTEREST);
		double penPrin = WriteOffFacade.getAmountByCateogry(selectedImport.getWoffDate(), 
				selectedImport.getAccountNo(), Common.PEN_PRN);
		double penInt = WriteOffFacade.getAmountByCateogry(selectedImport.getWoffDate(), 
				selectedImport.getAccountNo(), Common.PEN_INT);
		double contInt = WriteOffFacade.getAmountByCateogry(selectedImport.getWoffDate(), 
				selectedImport.getAccountNo(), Common.CON_INT);
		double totWoff = principle + interest + penPrin + penInt + contInt;
		
		selectedImport.setPrinciple(principle);
		selectedImport.setInterest(interest);
		selectedImport.setPenPrin(penPrin);
		selectedImport.setPenInt(penInt);
		selectedImport.setContInt(contInt);
		selectedImport.setTotWoff(totWoff);
		selectedImport.setAccuLnRec(0.0);
		selectedImport.setNetLoss(totWoff);
		
		selectedImport.setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		
		setSelectedLoan(selectedImport);
	}
	
	@Command
	@NotifyChange({ "paramAcc", "lstAcc" })
	public void onResetSearchAcc()
	{
		paramAcc = new ParamAcc();
		lstAcc = null;
	}
	
	@Command
	@NotifyChange({ "visibleIL", "lstAcc" })
	public void onImportAll()
	{
		paramAcc = new ParamAcc();
		paramAcc.setBranch(param.getSubBranch());
		lstAcc = new ListModelList<Loan>(WriteOffFacade.getAccountList(paramAcc));
		if(lstAcc.size() == 0)
		{
			Clients.showNotification("There are no new write-off loan in Flexcube.");
		}
		else
		{
			visibleIL = true;
			
			boolean wait = wols.hasFellow("importAll");
			
			if(wait)
				return;
			
			Executions.createComponents("/writeOff/ImportAll.zul", wols, null);
		}
	}
	
	@Command
	@NotifyChange({ "visibleIL" })
	public void onCloseImportAll()
	{
		visibleIL = false;
	}
	
	@Command
	@NotifyChange({ "lstAcc", "visibleIL", "lst" })
	public void onImport()
	{
		for(int i = 0; i < lstAcc.size(); i++)
		{
			double principle = WriteOffFacade.getAmountByCateogry(lstAcc.get(i).getWoffDate(), 
					lstAcc.get(i).getAccountNo(), Common.PRINCIPAL);
			double interest = WriteOffFacade.getAmountByCateogry(lstAcc.get(i).getWoffDate(), 
					lstAcc.get(i).getAccountNo(), Common.INTEREST);
			double penPrin = WriteOffFacade.getAmountByCateogry(lstAcc.get(i).getWoffDate(), 
					lstAcc.get(i).getAccountNo(), Common.PEN_PRN);
			double penInt = WriteOffFacade.getAmountByCateogry(lstAcc.get(i).getWoffDate(), 
					lstAcc.get(i).getAccountNo(), Common.PEN_INT);
			double contInt = WriteOffFacade.getAmountByCateogry(lstAcc.get(i).getWoffDate(), 
					lstAcc.get(i).getAccountNo(), Common.CON_INT);
			double totWoff = principle + interest + penPrin + penInt + contInt;
			
			lstAcc.get(i).setPrinciple(principle);
			lstAcc.get(i).setInterest(interest);
			lstAcc.get(i).setPenPrin(penPrin);
			lstAcc.get(i).setPenInt(penInt);
			lstAcc.get(i).setContInt(contInt);
			lstAcc.get(i).setTotWoff(totWoff);
			lstAcc.get(i).setAccuLnRec(0.0);
			lstAcc.get(i).setNetLoss(totWoff);
			
			lstAcc.get(i).setCreateBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		}
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" Import Loan(s) Write-Off from Flexcube --> ");
		strBuilder.append(lstAcc.size() + "loan(s)");
		Common.addSessionLogCommit(CMD.LIST_USER, strBuilder.toString(),
				new Date());
		
		Clients.showNotification("Loan: " + lstAcc.size() + " have been imported.");
		
		Ebean.save(lstAcc);
		
		lst = null;
		
		visibleIL = false;
	}
	
//region Branch & SubBranch
	
	@Command
	@NotifyChange({ "lst", "filterSubBranches" })
	public void onSelectBranch()
	{
		filterSubBranches = null;
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
	
//endregion Branch & SubBranch
	
	@Command
	@NotifyChange({ "infoChange" })
	public void infoChanging()
	{
		infoChange = false;
	}
	
	@Command
	@NotifyChange({ "selectedLoan", "visibleHistory" })
	public void onOpenHistory()
	{
		visibleHistory = true;
		
		boolean wait = wols.hasFellow("history");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/history.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "visibleHistory" })
	public void onCloseHistory()
	{
		visibleHistory = false;
	}
	
	@Command
	@NotifyChange({ "lstCo", "visibleCoH", "paramCo" })
	public void onShowFetchCoH()
	{
		paramCo = new ParamCo();
		lstCo = null;
		visibleCoH = true;
		boolean wait = wols.hasFellow("winCoH");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/coH.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "visibleCoH", "selectedLoan", "selectedTrnCo" })
	public void onSelectCoH()
	{
		selectedTrnCo.setTrnCoFame(selectedCo.getFullName());
		selectedTrnCo.setTrnCoSame(selectedCo.getShortName());
		visibleCoH = false;
	}
	
	@Command
	@NotifyChange({ "visibleCoH" })
	public void onCloseCoH()
	{
		visibleCoH = false;
	}
	
	@Command
	@NotifyChange({ "selectedLoan", "visibleTransfer" })
	public void onOpenTrnCo()
	{
		visibleTransfer = true;
		
		selectedTrnCo.setOrgCoFame(selectedLoan.getCoFullName());
		selectedTrnCo.setOrgCoSame(selectedLoan.getCoShortName());
		
		boolean wait = wols.hasFellow("transfer");
		
		if(wait)
			return;
		
		Executions.createComponents("/writeOff/transferDetail.zul", wols, null);
	}
	
	@Command
	@NotifyChange({ "visibleTransfer" })
	public void onCloseTrnCo()
	{
		visibleTransfer = false;
	}
	
	@Command
	@NotifyChange({ "lst", "selectedLoan", "selectedTrnCo", "visibleTransfer" })
	public void onTransferCo()
	{
		selectedTrnCo.setWof_id(selectedLoan.getId());
		selectedTrnCo.setTrnDate(new Date());
		selectedTrnCo.setTrnBy(UserCredentialManager.getIntance().getLoginUsr().getUsername());
		selectedLoan.setCoFullName(selectedTrnCo.getTrnCoFame());
		selectedLoan.setCoShortName(selectedTrnCo.getTrnCoSame());
		
		Ebean.save(selectedTrnCo);
		onSaveNoNotify();
		
		Ebean.save(selectedLoan);
		onSaveNoNotify();
		
		selectedLoan.getTrnCo().add(selectedTrnCo);
		
		Clients.showNotification("Co transfered information has been saved");
		
		StringBuilder strBuilder = Common.createLogStringBuilder();
		strBuilder.append(" transfer CO --> ");
		strBuilder.append(selectedTrnCo.toString());
		Common.addSessionLogCommit(CMD.LIST_WOF_LOAN, strBuilder.toString(),
				new Date());
		
		onCloseTrnCo();
		lst = null;
	}
}
