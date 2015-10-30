package kredit.web.util.village.vm;

import java.util.List;

import kredit.flexcube.model.Village;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModelList;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;

public class VillageVM {
	ListModelList<Village> lstFetchVillage = new ListModelList<Village>();
	Village paramVillage = new Village();

	// region paging

	private int pageIndex;
	private PagingList<Village> pagingList;
	private int totalSize;

	// endregion paging

	/**
	 * @return the paramVillage
	 */
	public Village getParamVillage() {
		return paramVillage;
	}

	/**
	 * @param paramVillage
	 *            the paramVillage to set
	 */
	public void setParamVillage(Village paramVillage) {
		this.paramVillage = paramVillage;
	}

	@NotifyChange({ "totalSize", "lstFetchVillage" })
	public ListModelList<Village> getLstFetchVillage() {
		if (lstFetchVillage == null) {

			pagingList = Ebean.find(Village.class).setUseQueryCache(true)
					.where().ilike("village", paramVillage.getVillage())
					.ilike("commune", paramVillage.getCommune())
					.ilike("district", paramVillage.getDistrict())
					.ilike("province", paramVillage.getProvince())
					.findPagingList(10);

			pagingList.getFutureRowCount();

			// get the first page
			Page<Village> page = pagingList.getPage(0);

			// get the beans from the page as a list
			List<Village> lst = page.getList();

			totalSize = page.getTotalRowCount();

			lstFetchVillage = new ListModelList<Village>(lst);
		}

		return lstFetchVillage;
	}

	/**
	 * @param lstFetchVillage
	 *            the lstFetchVillage to set
	 */
	public void setLstFetchVillage(ListModelList<Village> lstFetchVillage) {
		this.lstFetchVillage = lstFetchVillage;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	@NotifyChange("lstFetchVillage")
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		// get the first page
		Page<Village> page = pagingList.getPage(pageIndex);

		// get the beans from the page as a list
		List<Village> lst = page.getList();
		lstFetchVillage = new ListModelList<Village>(lst);
	}

	/**
	 * @return the pagingList
	 */
	public PagingList<Village> getPagingList() {
		return pagingList;
	}

	/**
	 * @param pagingList
	 *            the pagingList to set
	 */
	public void setPagingList(PagingList<Village> pagingList) {
		this.pagingList = pagingList;
	}

	/**
	 * @return the totalSize
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize
	 *            the totalSize to set
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Command
	@NotifyChange({ "lstFetchVillage", "pageIndex", "totalSize" })
	public void onSearchVillage() {
		pageIndex = 0;
		lstFetchVillage = null;
		System.out.println("searching..");
	}

	@Command
	@NotifyChange({ "lstFetchVillage", "totalSize" })
	public void onSearchVillageByID() {
		String strId = paramVillage.getStrID();
		if (strId.length() > 0) {
			if (strId.charAt(0) == '0') {
				strId = strId.substring(1);
			}
		}

		pagingList = Ebean.find(Village.class).setUseQueryCache(true).where()
				.ilike("id", strId).findPagingList(10);

		pagingList.getFutureRowCount();

		// get the first page
		Page<Village> page = pagingList.getPage(0);

		// get the beans from the page as a list
		List<Village> lst = page.getList();

		totalSize = page.getTotalRowCount();

		lstFetchVillage = new ListModelList<Village>(lst);
	}

	@Command
	@NotifyChange({ "lstFetchVillage", "paramVillage", "pageIndex", "totalSize" })
	public void onResetSearchVillage() {
		pageIndex = 0;
		lstFetchVillage = null;
		paramVillage = new Village();
		System.out.println("reseting...");
	}

}
