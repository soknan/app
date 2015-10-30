/**
 * 
 */
package kredit.web.kbureau.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import org.apache.log4j.Logger;

import kredit.web.core.util.Common;
import kredit.web.kbureau.model.enquiry.Addr;
import kredit.web.kbureau.model.enquiry.Enquiry;
import kredit.web.kbureau.model.enquiry.Telco;
import kredit.web.kbureau.model.enquiry.request.REQUEST;
import kredit.web.kbureau.model.enquiry.request.REQUEST.HEADER;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CADR;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CCNT;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CDOB;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CEMP;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CEMP.EADR;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CID;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CNAM;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CNAM.CNM1A;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CPLB;
import kredit.web.kbureau.model.enquiry.request.REQUEST.MESSAGE.ENQUIRY.CONSUMER.CNAM.CNMFA;

/**
 * @author sovathena_neth
 * 
 */
public class XmlHelper {

	static Logger logger = Logger.getLogger(XmlHelper.class);

	public static String generateXmlEnquiry(Enquiry enquiry)
			throws DatatypeConfigurationException {

		String xml = "";

		REQUEST reqCBC = new REQUEST();

		reqCBC.setSERVICE(enquiry.getService());
		reqCBC.setACTION(getAction(Double.parseDouble(enquiry.getAmount()), enquiry.getCurrency()));

		// region I. HEADER
		HEADER header = new HEADER();
		header.setMEMBERID(enquiry.getMemberID());
		header.setRUNNO(enquiry.getRunNo());
		header.setUSERID(enquiry.getUserID());
		header.setTOTITEMS(enquiry.getTotItems());
		reqCBC.getHEADER().add(header);
		// endregion I. HEADER

		// region II. MESSAGE
		MESSAGE message = new MESSAGE();
		
		// region 2.1 ENQUIRY
		ENQUIRY enq = new ENQUIRY();
		
		enq.setENQUIRYTYPE(enquiry.getEnquiryType());
		enq.setLANGUAGE(enquiry.getLanguage());
		enq.setPRODUCTTYPE(enquiry.getProductType());
		enq.setNOOFAPPLICANTS(enquiry.getNumberOfApplicants() + "");
		enq.setACCOUNTTYPE(enquiry.getAccountType());
		enq.setENQUIRYREFERENCE(enquiry.getMemberReference());
		enq.setAMOUNT(enquiry.getAmount());
		enq.setCURRENCY(enquiry.getCurrency());

		// region 2.1.1 CONSUMER
		CONSUMER consumer = new CONSUMER();
		
		// region 2.1.1.1 CAPL
		consumer.setCAPL(enquiry.getApplicantType());
		// endregion 2.1.1.1 CAPL
		
		// region 2.1.1.2 CID: Consumer ID
		CID cid1 = new CID();
		cid1.setCID1(enquiry.getIdType1());
		cid1.setCID2(enquiry.getIdNumber1());
		consumer.getCID().add(cid1);

		if (enquiry.getIdType2() != null && !enquiry.getIdType2().isEmpty()) {
			CID cid2 = new CID();
			cid2.setCID1(enquiry.getIdType2());
			cid2.setCID2(enquiry.getIdNumber2());
			consumer.getCID().add(cid2);
		}

		if (enquiry.getIdType3() != null && !enquiry.getIdType3().isEmpty()) {
			CID cid3 = new CID();
			cid3.setCID1(enquiry.getIdType3());
			cid3.setCID2(enquiry.getIdNumber3());
			consumer.getCID().add(cid3);
		}

		// endregion 2.1.1.1 CID: Consumer ID

		// region 2.1.1.3 CDOB
		CDOB cdob = new CDOB();
		cdob.setCDBD(Common.getDateFormat(enquiry.getDob(), "dd"));
		cdob.setCDBM(Common.getDateFormat(enquiry.getDob(), "MM"));
		cdob.setCDBY(Common.getDateFormat(enquiry.getDob(), "yyyy"));
		consumer.getCDOB().add(cdob);
		// endregion CDOB

		// region 2.1.1.4 CPLB
		CPLB cplb = new CPLB();
		Addr pob = getAddr(enquiry.getdVillage());
		cplb.setCPLBC(enquiry.getdCountry());
		if(pob == null) {
			cplb.setCPLBP("");
			cplb.setCPLBD("");
			cplb.setCPLBCM("");
		} else {
			cplb.setCPLBP(pob.getP());
			cplb.setCPLBD(pob.getD());
			cplb.setCPLBCM(pob.getC());
		}
		
		consumer.getCPLB().add(cplb);
		// endregion CPLB

		// region 2.1.1.5 CGND
		consumer.setCGND(enquiry.getSex());
		// endregion 2.1.1.4 CGND
		
		// region 2.1.1.6 CMAR
		consumer.setCMAR(enquiry.getMaritalStatus());
		// endregion 2.1.1.5 CMAR
		
		// region 2.1.1.7 CNAT
		consumer.setCNAT(enquiry.getNationalityCode());
		// endregion 2.1.1.6 CNAT
		
		// region 2.1.1.8 CNAM
		CNAM cnam = new CNAM();
		// region Family Name KH
		CNMFA cnmfa = new CNMFA();
		cnmfa.setLang("kh");
		cnmfa.setValue(enquiry.getLastNameKH());
		cnam.getCNMFA().add(cnmfa);
		// endregion Family Name KH

		// region Last Name KH
		CNM1A cnm1a = new CNM1A();
		cnm1a.setLang("kh");
		cnm1a.setValue(enquiry.getFirstNameKH());
		cnam.getCNM1A().add(cnm1a);
		// endregion Last Name KH

		// Last Name EN (Family Name)
		cnam.setCNMFE(enquiry.getLastNameEN());
		// First Name EN
		cnam.setCNM1E(enquiry.getFirstNameEN());

		consumer.getCNAM().add(cnam);
		// endregion CNAM

		// region 2.1.1.9 CEML
		consumer.setCEML(enquiry.getEmail());
		// endregion 2.1.1.8 CEML
		
		// region 2.1.1.10 Consumer Address
		CADR cadr = new CADR();
		cadr.setCADT(enquiry.getAddressType());
		
		Addr addr = getAddr(enquiry.getVillage());
		
		cadr.setCADPR(addr.getP());
		cadr.setCADDS(addr.getD());
		cadr.setCADCM(addr.getC());
		cadr.setCADVL(addr.getV());
		cadr.setCAD1E(enquiry.getAddressline1());
		cadr.setCAD2E(enquiry.getAddressline2());
		cadr.setCAD8E(enquiry.getCity());
		cadr.setCAD9(enquiry.getCountry());

		consumer.getCADR().add(cadr);
		// endregion Consumer Address

		// region 2.1.1.11 Contact (Phone Number)
		// todo: Phone Mobile vs Home
		CCNT ccnt1 = getPhoneNumber(enquiry.getPhone1());
		if(ccnt1 != null){
			consumer.getCCNT().add(ccnt1);
		}
		
		CCNT ccnt2 = getPhoneNumber(enquiry.getPhone2());
		if(ccnt2 != null){
			consumer.getCCNT().add(ccnt2);
		}
		
		// endregion Contact (Phone Number)

		// region 2.1.1.12 CEMP
		CEMP cemp = new CEMP();
		cemp.setETYP(enquiry.getEmploymentType());
		cemp.setESLF(enquiry.getSelfEmployed());
		cemp.setEOCE(enquiry.getOccupation());
		cemp.setELEN(enquiry.getLengthOfServince() + "");
		cemp.setETMS(enquiry.getTotalMonthlyIncome().toString());
		cemp.setECURR(enquiry.getCurrency1());
		cemp.setENME(enquiry.getEmployerName());

		EADR eadr = new EADR();
		eadr.setEADT(enquiry.getEmployerAddressType());
		eadr.setEADP(new String());
		eadr.setEADD(new String());
		eadr.setEADC(new String());
		eadr.setEADV(new String());
		eadr.setEAD1E(enquiry.getEmployerAddressLine1());
		eadr.setEAD2E(enquiry.getEmployerAddressLine2());
		eadr.setEAD8E(enquiry.getEmployerCity());
		eadr.setEAD9(enquiry.getEmployerCountryCode());

		cemp.getEADR().add(eadr);
		// region EADR

		consumer.getCEMP().add(cemp);

		// endregion EADR

		// endregion CEMP

		enq.getCONSUMER().add(consumer);
		
		// endregion 2.1.1 CONSUMER

		message.getENQUIRY().add(enq);
		// endregion 2.1 ENQUIRY

		reqCBC.getMESSAGE().add(message);
		// endregion MESSAGE

		try {

			/*
			 * String fileName = "022-" + Common.getDateFormat(new Date(),
			 * "yyMMdd-1") + ".xml";
			 * 
			 * File file = new File(
			 * "D:\\DOCUMENTS\\Projects\\Risk Project\\NBC\\CAFIU\\OutPut\\" +
			 * fileName);
			 */

			JAXBContext jaxbContext = JAXBContext.newInstance(REQUEST.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			StringWriter writer = new StringWriter();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			// jaxbMarshaller.marshal(lctr, file);
			// jaxbMarshaller.marshal(lctr, System.out);

			jaxbMarshaller.marshal(reqCBC, writer);

			xml = writer.toString();
			// System.out.println(xml);
			int eof = xml.indexOf('\n');
			xml = xml.substring(eof + 1);
			
			xml = xml.replace("<REQUEST>", "<?xml version=\"1.0\" encoding=\"utf-8\"?> <REQUEST xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Enquiry.xsd\">");
			
			/*
			 * try {
			 * 
			 * Writer out = new BufferedWriter(new OutputStreamWriter( new
			 * FileOutputStream(file), "UTF8"));
			 * 
			 * out.append(objStringBuilder.toString());
			 * 
			 * out.flush(); out.close();
			 * 
			 * } catch (UnsupportedEncodingException e) {
			 * System.out.println(e.getMessage()); } catch (IOException e) {
			 * System.out.println(e.getMessage()); } catch (Exception e) {
			 * System.out.println(e.getMessage()); }
			 */

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return xml;
	}

	// todo:
	public static CCNT getPhoneNumber(Telco telco) {
		if (telco == null)
			return null;
		if(telco.getPhoneNumber() == null)
			return null;
		CCNT ccnt = new CCNT();
		
		String phoneNumber =  telco.getPhoneNumber().replace(" ", "").replace("​", ""); // zero space

		ccnt.setCCN1(telco.getType());
		ccnt.setCCN2(telco.getCountryCode());
		ccnt.setCCN3(telco.getAreacode());
		ccnt.setCCN4(phoneNumber);

		return ccnt;
	}

	public static Addr getAddr(String village) {
		if (village == null)
			return null;
		int i = village.trim().lastIndexOf(" ");
		String code = village.substring(i + 1);

		if (code.length() != 8)
			return null;

		Addr addr = new Addr();
		addr.setP(code.substring(0, 2));
		addr.setD(code.substring(2, 4));
		addr.setC(code.substring(4, 6));
		addr.setV(code.substring(6, 8));
		return addr;
	}
	
	public static String getAction(double amount, String currency)
    {
        String action = "L"; // Lite Report
        switch (currency)
        {
            case "USD":
                if (amount > 500)
                    action = "A";
                break;
            case "KHR":
                if (amount / 4000 > 500)
                    action = "A";
                break;
            case "THB":
                if (amount > 15500)
                    action = "A";
                break;
        }
        return action;
    }

}
