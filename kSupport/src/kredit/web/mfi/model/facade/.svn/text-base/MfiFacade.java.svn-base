package kredit.web.mfi.model.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import kredit.flexcube.model.CIF;
import kredit.web.core.util.authentication.UserCredentialManager;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.core.util.model.CodeItem;
import kredit.web.mfi.model.CbCycle;
import kredit.web.mfi.model.Group.Type;
import kredit.web.mfi.model.Leader;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

public class MfiFacade {

	private static Logger logger = Logger.getLogger(MfiFacade.class);

	public static final String FORMAT_GROUP_ACC_NO = "%s-%s-%04d-%02d";

	/*
	 * ('TB-USD','TB-KHR') THEN '0301' ('VSU-USD','VSU-KHR') THEN '0401'
	 * ('SG-USD','SG-KHR' ) THEN '0201' ('SG AGR-USD','SG AGR-KHR') THEN '0202'
	 */
	public static String PRODUCT_CODE_GROUP_LOAN = "('0201', '0301', '0401', '0202', '0302', '0402')";

	public static int getMaxVillageSeq(String villageId) {
		Integer seq = 0;
		String sql = "SELECT max_village_seq value FROM vw_mfi_cb_village_max_seq WHERE village_id =:village_id";
		try (Connection con = Sql2oHelper.sql2o.open()) {
			seq = con.createQuery(sql).addParameter("village_id", villageId)
					.executeScalar(Integer.class);
		}
		return seq == null ? 0 : seq;
	}

	public static int getMaxCbCycleNo(int cbID) {
		Integer cbCycleNo = 0;

		String sql = "SELECT max_cycle_no value FROM vw_mfi_cbcycle_max_no WHERE cb_id =:cb_id";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			cbCycleNo = con.createQuery(sql).addParameter("cb_id", cbID)
					.executeScalar(Integer.class);

		}
		return cbCycleNo == null ? 0 : cbCycleNo;
	}

	public static int getCurrentCbCycleID(int cbID) {
		Integer cbCycleID = 0;
		String sql = "SELECT max(id) value, cb_id id FROM mfi_cbcycle GROUP BY cb_id WHERE cb_id =:cb_id";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			cbCycleID = con.createQuery(sql).addParameter("cb_id", cbID)
					.executeScalar(Integer.class);
		}

		return cbCycleID == null ? 0 : cbCycleID;
	}

	public static boolean isLeaderCifChange(Set<String> dirtyFields) {
		boolean dirty = false;
		for (String name : dirtyFields) {
			if (name.equals("cif")) {
				dirty = true;
				break;
			}
		}
		return dirty;
	}

	public static int getCurrentCbCycleLeaderID(int cbCycleID) {
		Integer cbCycleNo = 0;
		String sql = "SELECT max(id) value, cbcycle_id id FROM mfi_leader WHERE cbcycle_id =:cbcycle_id group by cbcycle_id";
		try (Connection con = Sql2oHelper.sql2o.open()) {
			cbCycleNo = con.createQuery(sql)
					.addParameter("cbcycle_id", cbCycleID)
					.executeScalar(Integer.class);

		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting current cb cycle leader - cycle id = "
							+ cbCycleID, e);
		}

		return cbCycleNo == null ? 0 : cbCycleNo;
	}

	public static int getCurrentGroupLeaderID(int groupID) {
		Integer cbCycleNo = 0;
		String sql = "SELECT max(id) value, group_id id FROM mfi_leader WHERE group_id =:group_id group by group_id";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			cbCycleNo = con.createQuery(sql).addParameter("group_id", groupID)
					.executeScalar(Integer.class);
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting current group leader - group id = "
							+ groupID, e);
		}

		return cbCycleNo == null ? 0 : cbCycleNo;
	}

	public static CbCycle getCbCycle(int cycleID) {
		CbCycle cycle = null;
		String sql = "SELECT cyc.id, cyc.cycle_no cycleNo, cyc.start_date startDate, cyc.end_date endDate, cyc.leader_id leaderID, cyc.leader_cif leaderCIF, cus.full_name leaderName, coalesce(vw.count_group,0) countGroup "
				+ "FROM mfi_cbcycle cyc LEFT JOIN sttm_customer cus ON cus.customer_no = cyc.leader_cif "
				+ "LEFT JOIN (SELECT cbcycle_id, count_group FROM VW_MFI_CBCYCLE_COUNT_GROUP) vw ON vw.cbcycle_id = cyc.id "
				+ "WHERE cyc.id = :id";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			cycle = con.createQuery(sql).addParameter("id", cycleID)
					.executeAndFetchFirst(CbCycle.class);

		} catch (Exception e) {
			logger.error("Sql2o error while getting cb cycles - cycle id = "
					+ cycleID, e);
		}
		return cycle;
	}

	public static List<CbCycle> getPrevCbCycles(Integer cbID, int curCycleID) {
		List<CbCycle> cycles = new ArrayList<CbCycle>();
		if (cbID == null)
			return cycles;

		String sql = "SELECT cyc.id, cyc.cycle_no cycleNo, cyc.start_date startDate, cyc.end_date endDate, cyc.leader_cif leaderCIF, cus.full_name leaderName, coalesce(vw.count_group,0) countGroup "
				+ "FROM mfi_cbcycle cyc LEFT JOIN sttm_customer cus ON cus.customer_no = cyc.leader_cif "
				+ "LEFT JOIN (SELECT cbcycle_id, count_group FROM VW_MFI_CBCYCLE_COUNT_GROUP) vw ON vw.cbcycle_id = cyc.id "
				+ "WHERE cyc.cb_id = :cbID AND cyc.id <> :id ORDER BY cyc.id DESC";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			cycles = con.createQuery(sql).addParameter("cbID", cbID)
					.addParameter("id", curCycleID)
					.executeAndFetch(CbCycle.class);
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting previous cb cycles - cb id = "
							+ cbID, e);
		}

		if (cycles == null) {
			cycles = new ArrayList<CbCycle>();
		}
		return cycles;
	}

	public static Leader getLeader(int id) {
		Leader leader = null;
		String sql = "SELECT l.id, l.group_member_id groupMemberID, l.branch_code brCd, l.cif, c.full_name leaderName, kh.full_name_kh leaderNameKH, l.group_id groupID, l.cbcycle_id cbcycleID, l.start_date startDate, l.end_date endDate FROM mfi_leader l "
				+ "INNER JOIN sttm_customer c ON c.customer_no = l.cif LEFT JOIN vw_customer_name_kh kh ON kh.customer_no = l.cif "
				+ "WHERE l.id = :id";
		try (Connection con = Sql2oHelper.sql2o.open()) {
			leader = con.createQuery(sql).addParameter("id", id)
					.executeAndFetchFirst(Leader.class);
		} catch (Exception e) {
			logger.error("Sql2o error while getting leader: id = " + id, e);
		}

		return leader;
	}

	public static int getMaxGroupSeq(String brCd, String type) {
		Integer groupSeq = 0;
		String sql = "SELECT max_group_no value FROM VW_MFI_GROUP_MAX_NO WHERE branch_code =:brCd AND type=:type";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			groupSeq = con.createQuery(sql).addParameter("brCd", brCd)
					.addParameter("type", type).executeScalar(Integer.class);

		} catch (Exception e) {
			logger.error("Sql2o error while getting max group seq:  BrCd= "
					+ brCd + ", type = " + type, e);
		}

		return groupSeq == null ? 0 : groupSeq;
	}

	public static int getMaxGroupCycleSeq(String brCd, String type, int groupSeq) {
		Integer cycleSeq = 0;
		String sql = "SELECT max_cycle value FROM VW_MFI_GROUP_MAX_CYCLE WHERE branch_code =:brCd AND type =:type AND group_seq =:groupSeq";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			cycleSeq = con.createQuery(sql).addParameter("brCd", brCd)
					.addParameter("type", type)
					.addParameter("groupSeq", groupSeq)
					.executeScalar(Integer.class);
		} catch (Exception e) {
			logger.error(
					"Sql2o error while getting max group cycle seq:  BrCd= "
							+ brCd + ", type = " + type + ", groupSeq = "
							+ groupSeq, e);
		}

		return cycleSeq;
	}

	public static String doPopulateGroupAccNo(String brCd, String type,
			int groupSeq, int cycleSeq) {
		return String.format(FORMAT_GROUP_ACC_NO, type, brCd, groupSeq,
				cycleSeq);
	}

	public static Type getGroupType(String type) {
		if (type.equals("CB")) {
			return Type.CB;
		} else if (type.equals("TB")) {
			return Type.TB;
		} else {
			return Type.SG;
		}
	}

	public static String getProductCode(String groupType) {
		String prdCode = "";
		if (groupType != null) {
			if (groupType.equals("CB")) {
				prdCode = "0401";
			} else if (groupType.equals("TB")) {

				prdCode = "0301";
			} else {
				prdCode = "0201";
			}
		}
		return prdCode;
	}

	public static String getGroupTypeByPrdCd(String groupType) {
		String gType = "";
		if (groupType != null) {
			if (groupType.equals("0401")) {
				gType = "CB";
			} else if (groupType.equals("0301")) {
				gType = "TB";
			} else if (groupType.equals("0201")) {
				gType = "SG";
			}
		}
		return gType;
	}

	public static void removeGroupMembers(Integer groupID) {
		if (groupID == null)
			return;
		String updateSql = "update mfi_group_member set group_id = null where group_id = :groupID";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			con.createQuery(updateSql).addParameter("groupID", groupID)
					.executeUpdate();
		}
	}

	public static void removeGroups(Integer cycleID) {
		if (cycleID == null)
			return;
		String updateSql = "update mfi_group set cbcycle_id = null where cbcycle_id = :cycleID";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			con.createQuery(updateSql).addParameter("cycleID", cycleID)
					.executeUpdate();
		}

	}

	public static void updateGroupCbCycle(Integer groupID, Integer cycleID) {
		if (groupID == null || cycleID == null)
			return;
		String updateSql = "update mfi_group set cbcycle_id =:cycleID where id = :groupID";
		try (Connection con = Sql2oHelper.sql2o.open()) {
			con.createQuery(updateSql).addParameter("cycleID", cycleID)
					.addParameter("groupID", cycleID).executeUpdate();
		}

	}

	public static List<CodeItem> getLstFetchIDType() {
		List<CodeItem> lst = new ArrayList<CodeItem>();

		//String sql = "select substr(lov, 1, 1) code, lov description from udtm_lov WHERE field_name = 'IDENTITY_TYPE'";
		String sql = "select code, desc_en description from sys_lov WHERE type = 'ID_TYPE'";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			lst = con.createQuery(sql).executeAndFetch(CodeItem.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Sql2o error while selecting identity type.", e);
		}

		if (lst == null) {
			lst = new ArrayList<CodeItem>();
		}

		return lst;
	}

	public static List<String> getLstFetchCoType() {
		List<String> lst = new ArrayList<String>();

		String sql = "select lov from udtm_lov WHERE field_name = 'RELATED_TO_BORROWER'";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			lst = con.createQuery(sql).executeAndFetch(String.class);
		} catch (Exception e) {

			e.printStackTrace();
			logger.error(
					"Sql2o error while selecting RELATED_TO_BORROWER type.", e);
		}

		if (lst == null) {
			lst = new ArrayList<String>();
		}

		return lst;
	}

	public static Double getInterestRate(String accNo) {
		Double inRate = 0.0;
		if (accNo == null || accNo.isEmpty())
			return inRate;
		String sql = "SELECT KREDIT_FUN_GET_LOAN_INT_RATED(ln.account_number, :systemDate)/12 FROM cltb_account_apps_master ln WHERE ln.account_number =:accNo";
		try (Connection con = Sql2oHelper.sql2o.open()) {
			inRate = con.createQuery(sql).addParameter("accNo", accNo)
					.addParameter("systemDate", getSystemDate())
					.executeScalar(Double.class);
		}
		return inRate;
	}
	
	public static String getSystemDate()
	{
		String result = "";
		String sql = "SELECT TO_CHAR(TODAY) FROM STTM_DATES WHERE BRANCH_CODE = :brcd";
		try (Connection con = Sql2oHelper.sql2o.open()) {
			result = con.createQuery(sql).addParameter("brcd", UserCredentialManager.getIntance().getLoginUsr()
					.getHomeBranch())
					.executeScalar(String.class);
		}
		return result;
	}

	public static int getTotalRow(CIF param){
		Integer totalRow = 0;
		String sql = "SELECT PKG_MFI.func_mbr_lov_total_row(:brCd, :accNoBr, :accNo, :cusNo, :fullName, :village) FROM DUAL";
		try (Connection con = Sql2oHelper.sql2o.open()) {
			totalRow = con.createQuery(sql)
					.addParameter("accNoBr", param.getBrCd() + "%")
					.addParameter("brCd", param.getBrCd())
					.addParameter("cusNo", param.getCustNo())
					.addParameter("fullName", param.getFullName())
					.addParameter("accNo", param.getAccountNo())
					.addParameter("village", param.getAddr2())
					.executeScalar(Integer.class);
		}
		return totalRow == null? 0: totalRow;
	}
	
	public static List<CIF> getLstFetchCifAcc(CIF param) {
		List<CIF> lst = new ArrayList<CIF>();

		try {
			String sql = "SELECT * FROM TABLE(PKG_MFI.func_mbr_lov(:brCd, :accNoBr, :accNo, :cusNo, :fullName, :village))";
			
			//sql = "SELECT * FROM (" + sql + ") WHERE row_num < 50";
			
			//System.out.println(sql);
			System.out.println("start: " + new Date());
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.addParameter("accNoBr", param.getBrCd() + "%")
						.addParameter("brCd", param.getBrCd())
						.addParameter("cusNo", param.getCustNo())
						.addParameter("fullName", param.getFullName())
						.addParameter("accNo", param.getAccountNo())
						.addParameter("village", param.getAddr2())
						.executeAndFetch(CIF.class);
			}
			System.out.println("end: " + new Date());

			if (lst == null) {
				lst = new ArrayList<CIF>();
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		return lst;
	}
	
	//region Backup
	
	
	public static List<CIF> getLstFetchCifAcc3(CIF param) {
		List<CIF> lst = new ArrayList<CIF>();

		try {
			String sql = "SELECT * FROM TABLE(PKG_MFI.func_mbr_lov_page_10(:brCd, :accNoBr, :accNo, :cusNo, :fullName, :village, :start, :end))";
			
			//sql = "SELECT * FROM (" + sql + ") WHERE row_num < 50";

			//System.out.println(sql);
			System.out.println("start: " + new Date());
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.addParameter("accNoBr", param.getBrCd() + "%")
						.addParameter("brCd", param.getBrCd())
						.addParameter("cusNo", param.getCustNo())
						.addParameter("fullName", param.getFullName())
						.addParameter("accNo", param.getAccountNo())
						.addParameter("village", param.getAddr2())
						.addParameter("start", param.getStartNumRow())
						.addParameter("end", param.getEndNumRow())
						.executeAndFetch(CIF.class);
			}
			System.out.println("end: " + new Date());

			if (lst == null) {
				lst = new ArrayList<CIF>();
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		return lst;
	}
	
	public static List<CIF> getLstFetchCifAcc2(CIF param) {
		List<CIF> lst = new ArrayList<CIF>();

		try {
			String sql = "";
			
			StringBuilder objStringBuilder = new StringBuilder();
			
			if (param.getAccountNo().replace("%", "").trim().isEmpty()) {

				// KREDIT_FUN_GET_LOAN_INT_RATED(ln.account_number, sysdate)/12
				
				objStringBuilder.append("SELECT row_number() over(order by cus.customer_no) row_num,");
				objStringBuilder.append("       cus.customer_no custNo,");
				objStringBuilder.append("       cus.full_name fullName,");
				objStringBuilder.append("       ln.account_number accountNo,");
				objStringBuilder.append("       ln.product_code prdCd,");
				objStringBuilder.append("       ln.product_category prdCat,");
				objStringBuilder.append("       ln.amount_disbursed amountDisbursed,");
				objStringBuilder.append("       kh.FULL_NAME_KH fullNameKH,");
				objStringBuilder.append("       psn.DATE_OF_BIRTH dateOfBirth,");
				objStringBuilder.append("       ln.value_date valueDate,");
				objStringBuilder.append("       ln.maturity_date maturityDate,");
				objStringBuilder.append("       field_char_8 loanPurpose,");
				objStringBuilder.append("       ln.alt_acc_no altAccNo,");
				objStringBuilder.append("       cus.address_line2 addr2");
				objStringBuilder.append("  FROM sttm_customer cus");
				objStringBuilder.append(" INNER JOIN cltb_account_apps_master ln");
				objStringBuilder.append("    ON cus.customer_no = ln.customer_id");
				objStringBuilder.append("  LEFT OUTER JOIN VW_CUSTOMER_NAME_KH kh");
				objStringBuilder.append("    ON cus.customer_no = kh.CUSTOMER_NO");
				objStringBuilder.append(" INNER JOIN STTM_CUST_PERSONAL psn");
				objStringBuilder.append("    ON cus.customer_no = psn.CUSTOMER_NO");
				objStringBuilder.append("  LEFT OUTER JOIN mfi_group_member mbr");
				objStringBuilder.append("    ON mbr.account = ln.account_number");
				objStringBuilder.append("   AND mbr.branch_code = :brCd");
				objStringBuilder.append(" WHERE ln.product_code IN ('0201', '0301', '0401', '0202', '0302', '0402')");
				objStringBuilder.append("   AND cus.customer_no like :cusNo");
				objStringBuilder.append("   AND lower(cus.full_name) like lower(:fullName)");
				objStringBuilder.append("   AND ln.account_number like :accNoBr");
				objStringBuilder.append("   AND ln.account_number like :accNo");
				objStringBuilder.append("   AND mbr.id IS NULL");
				objStringBuilder.append("   AND cus.address_line2 like :village");
				objStringBuilder.append(" ");
				objStringBuilder.append(" UNION ");
				
				//Note: add /*+ USE_MERGE(MBR2,PSN2,KH2,CUS2) */ for SQL tuning
				
				objStringBuilder.append("SELECT /*+ USE_MERGE(MBR2,PSN2,KH2,CUS2) */ row_number() over(order by cus2.customer_no) row_num,");
				objStringBuilder.append("       cus2.customer_no custNo,");
				objStringBuilder.append("       cus2.full_name fullName,");
				objStringBuilder.append("       '' accountNo,");
				objStringBuilder.append("       '' prdCd,");
				objStringBuilder.append("       '' prdCat,");
				objStringBuilder.append("       0 amountDisbursed,");
				objStringBuilder.append("       kh2.FULL_NAME_KH fullNameKH,");
				objStringBuilder.append("       psn2.DATE_OF_BIRTH dateOfBirth,");
				objStringBuilder.append("       null valueDate,");
				objStringBuilder.append("       null maturityDate,");
				objStringBuilder.append("       '' loanPurpose,");
				objStringBuilder.append("       '' altAccNo,");
				objStringBuilder.append("       cus2.address_line2 addr2");
				objStringBuilder.append("  FROM sttm_customer cus2");
				objStringBuilder.append("  LEFT OUTER JOIN VW_CUSTOMER_NAME_KH kh2");
				objStringBuilder.append("    ON cus2.customer_no = kh2.CUSTOMER_NO");
				objStringBuilder.append(" INNER JOIN STTM_CUST_PERSONAL psn2");
				objStringBuilder.append("    ON cus2.customer_no = psn2.CUSTOMER_NO");
				objStringBuilder.append("  LEFT OUTER JOIN mfi_group_member mbr2");
				objStringBuilder.append("    ON mbr2.cif = cus2.customer_no");
				objStringBuilder.append("   AND mbr2.branch_code = :brCd");
				objStringBuilder.append("   AND mbr2.account IS NULL");
				objStringBuilder.append(" ");
				objStringBuilder.append(" WHERE cus2.customer_no like :cusNo");
				objStringBuilder.append("   AND lower(cus2.full_name) like lower(:fullName)");
				objStringBuilder.append("   AND mbr2.id IS NULL");
				objStringBuilder.append("   AND cus2.address_line2 like :village");

			} else {
				objStringBuilder.append("SELECT row_number() over(order by cus.customer_no) row_num,");
				objStringBuilder.append("       cus.customer_no custNo,");
				objStringBuilder.append("       cus.full_name fullName,");
				objStringBuilder.append("       ln.account_number accountNo,");
				objStringBuilder.append("       ln.product_code prdCd,");
				objStringBuilder.append("       ln.product_category prdCat,");
				objStringBuilder.append("       ln.amount_disbursed amountDisbursed,");
				objStringBuilder.append("       kh.FULL_NAME_KH fullNameKH,");
				objStringBuilder.append("       psn.DATE_OF_BIRTH dateOfBirth,");
				objStringBuilder.append("       ln.value_date valueDate,");
				objStringBuilder.append("       ln.maturity_date maturityDate,");
				objStringBuilder.append("       field_char_8 loanPurpose,");
				objStringBuilder.append("       ln.alt_acc_no altAccNo,");
				objStringBuilder.append("       cus.address_line2 addr2");
				objStringBuilder.append("  FROM sttm_customer cus");
				objStringBuilder.append(" INNER JOIN cltb_account_apps_master ln");
				objStringBuilder.append("    ON cus.customer_no = ln.customer_id");
				objStringBuilder.append("  LEFT OUTER JOIN VW_CUSTOMER_NAME_KH kh");
				objStringBuilder.append("    ON cus.customer_no = kh.CUSTOMER_NO");
				objStringBuilder.append(" INNER JOIN STTM_CUST_PERSONAL psn");
				objStringBuilder.append("    ON cus.customer_no = psn.CUSTOMER_NO");
				objStringBuilder.append("  LEFT OUTER JOIN mfi_group_member mbr");
				objStringBuilder.append("    ON mbr.account = ln.account_number");
				objStringBuilder.append("   AND mbr.branch_code = &brCd");
				objStringBuilder.append(" WHERE ln.product_code IN ('0201', '0301', '0401', '0202', '0302', '0402')");
				objStringBuilder.append("   AND cus.customer_no like &cusNo");
				objStringBuilder.append("   AND lower(cus.full_name) like lower(&fullName)");
				objStringBuilder.append("   AND ln.account_number like &accNoBr");
				objStringBuilder.append("   AND ln.account_number like &accNo");
				objStringBuilder.append("   AND mbr.id IS NULL");
				objStringBuilder.append("   AND cus.address_line2 like &village;");

			}

			sql = "SELECT * FROM (" + objStringBuilder.toString() + ") WHERE row_num < 50";

			System.out.println(sql);

			System.out.println("start: " + new Date());
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.addParameter("accNoBr", param.getBrCd() + "%")
						.addParameter("brCd", param.getBrCd())
						.addParameter("cusNo", param.getCustNo())
						.addParameter("fullName", param.getFullName())
						.addParameter("accNo", param.getAccountNo())
						.addParameter("village", param.getAddr2())
						.executeAndFetch(CIF.class);
			}
			System.out.println("start: " + new Date());

			if (lst == null) {
				lst = new ArrayList<CIF>();
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		return lst;
	}
	
	
	
	public static List<CIF> getLstFetchCifAcc1(CIF param) {
		List<CIF> lst = new ArrayList<CIF>();

		try {
			String sql = "";
			if (param.getAccountNo().replace("%", "").trim().isEmpty()) {

				// KREDIT_FUN_GET_LOAN_INT_RATED(ln.account_number, sysdate)/12

				sql = "SELECT row_number() over(order by cus.customer_no) row_num, cus.customer_no custNo, cus.full_name fullName, ln.account_number accountNo, ln.product_code prdCd, "
						+ "ln.product_category prdCat, ln.amount_disbursed amountDisbursed "
						+ ", kh.FULL_NAME_KH fullNameKH, psn.DATE_OF_BIRTH dateOfBirth "
						+ ", ln.value_date valueDate, ln.maturity_date maturityDate, field_char_8 loanPurpose "
						+ ", ln.alt_acc_no altAccNo, cus.address_line2 addr2 "
						+ "FROM sttm_customer cus "
						+ "INNER JOIN cltb_account_apps_master ln ON cus.customer_no = ln.customer_id "
						+ "LEFT OUTER JOIN VW_CUSTOMER_NAME_KH kh ON cus.customer_no = kh.CUSTOMER_NO "
						+ "INNER JOIN STTM_CUST_PERSONAL psn ON cus.customer_no = psn.CUSTOMER_NO "
						+ "WHERE ln.product_code IN "
						+ PRODUCT_CODE_GROUP_LOAN
						+ " AND cus.customer_no like :cusNo AND lower(cus.full_name) like lower(:fullName) "
						+ "AND NVL(ln.account_number,' ') like :accNoBr "
						+ "AND NVL(ln.account_number,' ') like :accNo "
						+ "AND NVL(ln.account_number, ' ') NOT IN (SELECT NVL(account,' ') FROM mfi_group_member WHERE branch_code =:brCd) "
						+ "AND cus.address_line2 like :village "
						+ "UNION "
						+ "SELECT row_number() over(order by cus2.customer_no) row_num, cus2.customer_no custNo, cus2.full_name fullName, '' accountNo, '' prdCd, '' prdCat, 0 amountDisbursed"
						+ ",kh2.FULL_NAME_KH fullNameKH, psn2.DATE_OF_BIRTH dateOfBirth"
						+ ", null valueDate, null maturityDate, '' loanPurpose"
						+ ", '' altAccNo, cus2.address_line2 addr2 "
						+ "FROM sttm_customer cus2 "
						+ "LEFT OUTER JOIN VW_CUSTOMER_NAME_KH kh2 ON cus2.customer_no = kh2.CUSTOMER_NO "
						+ "INNER JOIN STTM_CUST_PERSONAL psn2 ON cus2.customer_no = psn2.CUSTOMER_NO "
						+ "WHERE cus2.customer_no like :cusNo AND lower(cus2.full_name) like lower(:fullName) "
						+ "AND cus.address_line2 like :village "
						+ "AND cus2.customer_no NOT IN (SELECT cif FROM mfi_group_member WHERE account IS NULL AND branch_code =:brCd)";

			} else {
				sql = "SELECT row_number() over(order by cus.customer_no) row_num, cus.customer_no custNo, cus.full_name fullName, ln.account_number accountNo, ln.product_code prdCd, "
						+ "ln.product_category prdCat, ln.amount_disbursed amountDisbursed, "
						+ "kh.FULL_NAME_KH fullNameKH, psn.DATE_OF_BIRTH dateOfBirth, "
						+ "ln.value_date valueDate, ln.maturity_date maturityDate, field_char_8 loanPurpose, ln.alt_acc_no altAccNo, cus.address_line2 addr2 "
						+ "FROM sttm_customer cus "
						+ "INNER JOIN cltb_account_apps_master ln ON cus.customer_no = ln.customer_id "
						+ "LEFT OUTER JOIN VW_CUSTOMER_NAME_KH kh ON cus.customer_no = kh.CUSTOMER_NO "
						+ "INNER JOIN STTM_CUST_PERSONAL psn ON cus.customer_no = psn.CUSTOMER_NO "
						+ "WHERE ln.product_code IN "
						+ PRODUCT_CODE_GROUP_LOAN
						+ " AND cus.customer_no like :cusNo AND lower(cus.full_name) like lower(:fullName) "
						+ "AND NVL(ln.account_number,' ') like :accNoBr "
						+ "AND NVL(ln.account_number,' ') like :accNo "
						+ "AND NVL(ln.account_number,' ') NOT IN (SELECT NVL(account,' ') FROM mfi_group_member WHERE branch_code =:brCd)"
						+ "AND cus2.address_line2 like :village ";
			}

			sql = "SELECT * FROM (" + sql + ") WHERE row_num < 50";

			// System.out.println(sql);

			// System.out.println("start: " + new Date());
			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.addParameter("accNoBr", param.getBrCd() + "%")
						.addParameter("brCd", param.getBrCd())
						.addParameter("cusNo", param.getCustNo())
						.addParameter("fullName", param.getFullName())
						.addParameter("accNo", param.getAccountNo())
						.addParameter("village", param.getAddr2())
						.executeAndFetch(CIF.class);
			}
			// System.out.println("start: " + new Date());

			if (lst == null) {
				lst = new ArrayList<CIF>();
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		return lst;
	}

	//endregion Backup
	
	/*public static List<CIF> getLstFetchCifAcc0(CIF param) {
		List<CIF> lst = new ArrayList<CIF>();

		try {
			String sql = "";
			if (param.getAccountNo().replace("%", "").trim().isEmpty()) {

				sql = "SELECT row_number() over(order by cus.customer_no) row_num, cus.customer_no custNo, cus.full_name fullName, ln.account_number accountNo, ln.product_code prdCd, "
						+ "ln.product_category prdCat, ln.amount_disbursed amountDisbursed FROM sttm_customer cus "
						+ "INNER JOIN cltb_account_apps_master ln ON cus.customer_no = ln.customer_id "
						+ "WHERE ln.product_code IN "
						+ PRODUCT_CODE_GROUP_LOAN
						+ " AND cus.customer_no like :cusNo AND lower(cus.full_name) like lower(:fullName) "
						+ "AND NVL(ln.account_number,' ') like :accNoBr "
						+ "AND NVL(ln.account_number,' ') like :accNo "
						+ "AND NVL(ln.account_number, ' ') NOT IN (SELECT NVL(account,' ') FROM mfi_group_member WHERE branch_code =:brCd)"
						+ "UNION "
						+ "SELECT row_number() over(order by cus2.customer_no) row_num, cus2.customer_no custNo, cus2.full_name fullName, '', '', '', 0 FROM sttm_customer cus2 "
						+ "WHERE cus2.customer_no like :cusNo AND lower(cus2.full_name) like lower(:fullName) "
						+ "AND cus2.customer_no NOT IN (SELECT cif FROM mfi_group_member WHERE account IS NULL AND branch_code =:brCd)";

			} else {
				sql = "SELECT row_number() over(order by cus.customer_no) row_num, cus.customer_no custNo, cus.full_name fullName, ln.account_number accountNo, ln.product_code prdCd, "
						+ "ln.product_category prdCat, ln.amount_disbursed amountDisbursed FROM sttm_customer cus "
						+ "INNER JOIN cltb_account_apps_master ln ON cus.customer_no = ln.customer_id "
						+ "WHERE ln.product_code IN "
						+ PRODUCT_CODE_GROUP_LOAN
						+ " AND cus.customer_no like :cusNo AND lower(cus.full_name) like lower(:fullName) "
						+ "AND NVL(ln.account_number,' ') like :accNoBr "
						+ "AND NVL(ln.account_number,' ') like :accNo "
						+ "AND NVL(ln.account_number,' ') NOT IN (SELECT NVL(account,' ') FROM mfi_group_member WHERE branch_code =:brCd)";
			}

			sql = "SELECT * FROM (" + sql + ") WHERE row_num < 100";

			System.out.println(sql);

			System.out.println("start: " + new Date());

			try (Connection con = Sql2oHelper.sql2o.open()) {
				lst = con.createQuery(sql)
						.addParameter("accNoBr", param.getBrCd() + "%")
						.addParameter("brCd", param.getBrCd())
						.addParameter("cusNo", param.getCustNo())
						.addParameter("fullName", param.getFullName())
						.addParameter("accNo", param.getAccountNo())
						.executeAndFetch(CIF.class);
			}

			System.out.println("end: " + new Date());

			if (lst == null) {
				lst = new ArrayList<CIF>();
			}
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Sql2o error while selecting cif and account.", e);
		}
		return lst;
	}
*/
	public static boolean isMobilizerExist(Integer integerID, String brCd,
			String sex, String idType, String idNo) {
		int id = integerID == null ? 0 : integerID;
		Integer count = 0;
		String sql = "SELECT count(*) value FROM mfi_mobilizer WHERE id <> :id AND branch_code=:brCd AND sex =:sex AND id_type =:idType AND id_no =:idNo";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			count = con.createQuery(sql).addParameter("id", id)
					.addParameter("brCd", brCd).addParameter("sex", sex)
					.addParameter("idType", idType).addParameter("idNo", idNo)
					.executeScalar(Integer.class);
		} catch (Exception e) {
			logger.error(
					"Sql2o error while checking mobilizier is exist. brCd= "
							+ brCd + ", sex = " + sex + ", id_type = " + idType
							+ ", idNo = " + idNo, e);
		}
		int c = count == null ? 0 : count;
		return c > 0;
	}

	public static boolean isGroupAccNrExist(Integer integerID, String groupAccNo) {
		int id = integerID == null ? 0 : integerID;
		Integer count = 0;

		String sql = "SELECT count(*) value FROM mfi_group WHERE id <> :id AND GROUP_ACC_NO =:groupAccNo";

		try (Connection con = Sql2oHelper.sql2o.open()) {
			count = con.createQuery(sql).addParameter("id", id)
					.addParameter("groupAccNo", groupAccNo)
					.executeScalar(Integer.class);

		} catch (Exception e) {
			logger.error(
					"Sql2o error while checking group_acc_no is exist. Group Acc. No= "
							+ groupAccNo, e);
		}

		int c = count == null ? 0 : count;

		return c > 0;
	}

	/*
	 * 
	 * public static void reassignGroupLeader(Integer groupID, String cif,
	 * String account) { if (groupID == null) return;
	 * 
	 * String sql1 =
	 * "update mfi_group_member set is_group_leader = null where group_id =:groupID"
	 * ; String sql2 =
	 * "update mfi_group_member set is_group_leader = 'Y' where cif = :cif and account =:account"
	 * ;
	 * 
	 * Connection connection = null; try{ connection =
	 * Sql2oHelper.sql2o.beginTransaction();
	 * connection.createQuery(sql1).addParameter("group_acc_no",
	 * groupAccNo).executeUpdate();
	 * connection.createQuery(sql2).addParameter("id", mbrId).executeUpdate();
	 * connection.commit(); } catch(Throwable t){
	 * logger.error("error while reassigning group leader", t); if (connection
	 * != null){ connection.rollback(); } throw t; } }
	 */

}
