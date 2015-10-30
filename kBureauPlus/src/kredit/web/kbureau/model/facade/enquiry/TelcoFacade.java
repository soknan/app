package kredit.web.kbureau.model.facade.enquiry;

import kredit.web.core.util.db.Sql2oHelper;
import kredit.web.kbureau.model.enquiry.Telco;

import org.apache.log4j.Logger;
import org.sql2o.Connection;

public class TelcoFacade {
	
	private static Logger logger = Logger.getLogger(TelcoFacade.class);
	
	public static Telco getTelco(String telephone){
		Telco telco = new Telco();
		
		if(telephone == null){
			telco.setType("U");
			telco.setCountryCode("855");
			telco.setAreacode("");
		}else{
			telephone = telephone.replaceAll("\\s",""); //all whitespace characters removed
				
			if (telephone.matches("[0-9]+") && telephone.length() > 7){
				try(Connection con = Sql2oHelper.sql2o.open()){
					String sql = "SELECT '855' AS countryCode" +
							",PREFIX AS prefix" +
							",AREACODE AS areacode" +
							",TYPE AS type" +
							",DIGIT AS digit" +
							",DESCRIPTION AS description" +
							",CASE t.type" +
							" WHEN 'H' THEN SUBSTR(:phoneNumber,4) ELSE :phoneNumber END AS phoneNumber" + 
							" FROM utl_telco t WHERE prefix = SUBSTR(:phoneNumber,1,3)";
					telco = con.createQuery(sql).addParameter("phoneNumber", telephone)
						     .executeAndFetchFirst(Telco.class);
					
					if(telco == null)
						telco = new Telco();
				}catch(Exception e){
					logger.error("Sql2o error while getting telco", e);
				}
			}else{
				//Overseas phone number (not number 0-9) -> phone type=Unknown , number=000
				telco.setType("U");
				telco.setCountryCode("855");
				telco.setAreacode("");
				
			}
		}
		return telco;
	}

}
