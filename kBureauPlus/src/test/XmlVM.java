package test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;

import kredit.web.core.util.Common;
import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.kbureau.model.enquiry.Enquiry;
import kredit.web.kbureau.util.XmlHelper;

import org.datacontract.schemas.kservice.model.datacontracts.Authentication;
import org.datacontract.schemas.kservice.model.datacontracts.RequestEnquiry;
import org.tempuri.IKserviceProxy;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;

public class XmlVM {

	String loanID;
	String refNo;

	@Command
	public void onTest() {
		Customer customer = new Customer();
		customer.setId(100);
		customer.setName("mkyong");
		customer.setAge(29);

		try {

			File file = new File(
					"D:\\Code\\kSupport\\docs\\CBC\\JAXB\\test.xml");

			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(customer, System.out);

			System.out.println("Successfully generated!");

		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Command
	public void onGenerateXML() throws DatatypeConfigurationException,
			UnsupportedEncodingException {
		HttpServletResponse response = ((HttpServletResponse) Executions
				.getCurrent().getNativeResponse());

		String fileName = "RequestCBC.xml";

		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");
		response.setContentType("application/octet-stream");

		Enquiry enquiry = getEnquiryTest(loanID);

		String strXML = XmlHelper.generateXmlEnquiry(enquiry);

		InputStream is = new ByteArrayInputStream(strXML.getBytes("UTF-8"));

		Filedownload.save(is, "text/xml", fileName);
	}

	private Enquiry getEnquiryTest(String loanID) {
		StringBuilder objStringBuilder = new StringBuilder();

		objStringBuilder.append("SELECT 'NA' AS enquiryType,");
		objStringBuilder.append("       'PLN' AS productType,");

		objStringBuilder.append("       1 AS numberOfApplicants,");
		objStringBuilder.append("       'S' AS accountType,");
		objStringBuilder.append("       CL.ACCOUNT_NUMBER AS memberReference,");
		objStringBuilder.append("       cl.AMOUNT_FINANCED AS amount,");
		objStringBuilder.append("       CL.CURRENCY AS currency,");
		objStringBuilder.append("       'P' AS applicantType,");
		objStringBuilder.append("       F.FIELD_VAL_2 AS firstNameKH,");
		objStringBuilder.append("       F.FIELD_VAL_1 AS lastNameKH,");
		objStringBuilder.append("       CP.FIRST_NAME AS firstNameEN,");
		objStringBuilder.append("       CP.Last_Name AS lastNameEN,");
		objStringBuilder.append("       'N' AS idType1,");
		objStringBuilder.append("       CP.PASSPORT_NO AS idNumber1,");
		objStringBuilder.append("       CP.PPT_EXP_DATE AS idExpiryDate1,");
		objStringBuilder.append("       F.FIELD_VAL_3 AS idType2,");
		objStringBuilder.append("       F.FIELD_VAL_4 AS idNumber2,");
		objStringBuilder.append("       '' AS idExpiryDate2,");
		objStringBuilder.append("       F.FIELD_VAL_5 AS idType3,");
		objStringBuilder.append("       F.FIELD_VAL_6 AS idNumber3,");
		objStringBuilder.append("       '' AS idExpiryDate3,");
		objStringBuilder.append("       CP.DATE_OF_BIRTH AS dob,");
		objStringBuilder.append("       CP.SEX AS sex,");
		objStringBuilder.append("       CD.MARITAL_STATUS AS maritalStatus,");
		objStringBuilder
				.append("       DECODE(C.NATIONALITY, 'KH', 'KHM', C.NATIONALITY) AS nationalityCode,");
		objStringBuilder
				.append("       DECODE(CP.D_COUNTRY, 'KH', 'KHM', CP.D_COUNTRY) AS dCountry,");
		objStringBuilder.append("       '' AS dProvince,");
		objStringBuilder.append("       CP.D_ADDRESS4 AS dDistrict,");
		objStringBuilder.append("       CP.D_ADDRESS3 AS dCommune,");
		objStringBuilder.append("       CP.D_ADDRESS2 AS dVillage,");
		objStringBuilder
				.append("       DECODE(CP.D_ADDRESS1, 'N-A', 'N/A', CP.D_ADDRESS1) AS addressline1,");
		objStringBuilder.append("");
		objStringBuilder.append("       'RESID' AS addressType,");
		objStringBuilder.append("       C.ADDRESS_LINE1 AS homeNo,");
		objStringBuilder.append("       C.ADDRESS_LINE2 AS village,");
		objStringBuilder.append("       C.ADDRESS_LINE3 AS commune,");
		objStringBuilder.append("       C.ADDRESS_LINE4 AS district,");
		objStringBuilder.append("       MP.ADDRESS5 AS province,");
		objStringBuilder.append("       MP.ADDRESS5 AS city,");
		objStringBuilder
				.append("       DECODE(C.COUNTRY, 'KH', 'KHM', C.COUNTRY) AS country,");
		objStringBuilder.append("       CP.TELEPHONE AS telephone,");
		objStringBuilder.append("       CP.MOBILE_NUMBER AS mobileNumber,");
		objStringBuilder.append("       'C' AS employmentType,");
		objStringBuilder
				.append("       DECODE(P.EMPLOYMENT_STATUS, 'S', 'Y', 'N') AS selfEmployed,");
		objStringBuilder
				.append("       NVL(P.EMPLOYMENT_TENURE, 0) AS lengthOfServince,");
		objStringBuilder.append("       CF.DESCRIPTION AS occupation,");
		objStringBuilder.append("       P.SALARY AS totalMonthlyIncome,");
		objStringBuilder.append("       'USD' AS currency1,");
		objStringBuilder.append("       P.DESIGNATION AS employerName,");
		objStringBuilder.append("       'RESID' AS employerAddressType,");
		objStringBuilder
				.append("       DECODE(p.e_address1, 'N-A', 'N/A', p.e_address1) AS employerAddressLine1,");
		objStringBuilder.append("       p.e_address2 as employerCity,");
		objStringBuilder.append("       'KHM' as employerCountryCode,");
		objStringBuilder
				.append(" CL.FIELD_CHAR_8 as loanPurpose, CL.FIELD_CHAR_3 as loanFund ");
		objStringBuilder.append("  FROM CLTB_ACCOUNT_MASTER CL");
		objStringBuilder.append(" INNER JOIN STTM_CUSTOMER C");
		objStringBuilder.append("    ON CL.CUSTOMER_ID = C.CUSTOMER_NO");
		objStringBuilder.append(" INNER JOIN STTM_CUST_PERSONAL CP");
		objStringBuilder.append("    ON CL.CUSTOMER_ID = CP.CUSTOMER_NO");
		objStringBuilder
				.append("  LEFT OUTER JOIN CSTM_FUNCTION_USERDEF_FIELDS F");
		objStringBuilder
				.append("    ON CL.CUSTOMER_ID = SUBSTR(F.REC_KEY, 1, 9)");
		objStringBuilder.append("   AND F.FUNCTION_ID = 'STDCIF'");
		objStringBuilder.append("  LEFT OUTER JOIN STTM_CUST_DOMESTIC CD");
		objStringBuilder.append("    ON CL.CUSTOMER_ID = CD.CUSTOMER_NO");
		objStringBuilder.append("  LEFT OUTER JOIN STTM_MFI_CUST_DET MP");
		objStringBuilder.append("    ON CL.CUSTOMER_ID = MP.CUSTOMER_NO");
		objStringBuilder.append("  LEFT OUTER JOIN STTM_CUST_PROFESSIONAL P");
		objStringBuilder.append("    ON CL.CUSTOMER_ID = P.CUSTOMER_NO");
		objStringBuilder
				.append("  LEFT OUTER JOIN STTM_CUST_CLASSIFICATION CF");
		objStringBuilder
				.append("    ON C.CUST_CLASSIFICATION = CF.CUST_CLASSIFICATION");
		objStringBuilder.append(" WHERE CL.ACCOUNT_NUMBER =:loanID");

		Enquiry obj = Sql2oHelper.sql2o
				.createQuery(objStringBuilder.toString())
				.addParameter("loanID", loanID)
				.executeAndFetchFirst(Enquiry.class);

		return obj;
	}

	@Command
	public void onRequestCBC() throws DatatypeConfigurationException {
		IKserviceProxy kService = new IKserviceProxy();

		Enquiry enquiry = getEnquiryTest(loanID);

		StringBuilder strXML = new StringBuilder();
		strXML.append("?"); // this char will be removed by service.
		strXML.append(XmlHelper.generateXmlEnquiry(enquiry));

		System.out.println(strXML);

		RequestEnquiry requestEnquiry = new RequestEnquiry();
		requestEnquiry.setRequest(strXML.toString());
		requestEnquiry.setLoanId(enquiry.getMemberReference());
		requestEnquiry.setApplicantType("P");
		requestEnquiry.setRefNumber(refNo);
		requestEnquiry.setProduct(enquiry.getProductCode());
		requestEnquiry.setPurpose(enquiry.getLoanPurpose());
		requestEnquiry.setFund(enquiry.getLoanFund());

		Authentication auth = new Authentication();
		auth.setId(3);

		try {
			String url = kService.sendEnquiryRequestToCbc(Common.TOKEN_STRING,
					requestEnquiry, auth, true);
			System.out.println(url);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the loanID
	 */
	public String getLoanID() {
		return loanID;
	}

	/**
	 * @param loanID
	 *            the loanID to set
	 */
	public void setLoanID(String loanID) {
		this.loanID = loanID;
	}

	/**
	 * @return the refNo
	 */
	public String getRefNo() {
		return refNo;
	}

	/**
	 * @param refNo
	 *            the refNo to set
	 */
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

}
