/**
 * 
 */
package kredit.web.cbc.util;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import org.apache.log4j.Logger;
import org.zkoss.zul.ListModelList;

import kredit.web.cbc.model.request.REQUEST;
import kredit.web.core.util.Common;
import kredit.web.core.util.model.CodeItem;


/**
 * @author sovathena_neth
 * 
 */
public class XmlHelper {

	static Logger logger = Logger.getLogger(XmlHelper.class);

	private static String getRptIdentifier(int sequence) {
		String rptIdentifier = "KH"
				+ Common.getDateFormat(new Date(), "yyMMdd");
		int len = 7;
		String seq = sequence + "";
		String zero = "";
		for (int i = 0; i < len - seq.length(); i++) {
			zero += "0";
		}

		return rptIdentifier + zero + seq;
	}

	public static String generateXml(int ctrPrfID)
			throws DatatypeConfigurationException {

		StringBuilder objStringBuilder = new StringBuilder();
		
		REQUEST reqCBC = new REQUEST();
		
		reqCBC.setSERVICE("ENQUIRYV2");
		reqCBC.setACTION("A");


		try {

			/*
			String fileName = "022-"
					+ Common.getDateFormat(new Date(), "yyMMdd-1") + ".xml";

			File file = new File(
					"D:\\DOCUMENTS\\Projects\\Risk Project\\NBC\\CAFIU\\OutPut\\"
							+ fileName);
			*/
			
			JAXBContext jaxbContext = JAXBContext.newInstance(REQUEST.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			StringWriter writer = new StringWriter();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			//jaxbMarshaller.marshal(lctr, file);
			//jaxbMarshaller.marshal(lctr, System.out);
			
			jaxbMarshaller.marshal(reqCBC, writer);
			
			String xml = writer.toString();
			//System.out.println(xml);
			int eof = xml.indexOf('\n');
			xml = xml.substring(eof + 1);

			
			objStringBuilder
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><?mso-infoPathSolution solutionVersion=\"1.0.0.103\" PIVersion=\"1.0.0.0\" href=\"\" name=\"urn:schemas-microsoft-com:office:infopath:CTRv1-2:\" language=\"en-us\" productVersion=\"15.0.0\" ?><?mso-application progid=\"InfoPath.Document\"?>");
			objStringBuilder.append(xml);

			/*
			try {

				Writer out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file), "UTF8"));

				out.append(objStringBuilder.toString());

				out.flush();
				out.close();

			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			*/
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return objStringBuilder.toString();
	}
}
