/**
 * 
 */
package kredit.web.core.util;

/**
 * @author vathenan
 *
 */
public class Pager {

	public static String GetPaging(int intTotalRecord, int intPageNo, int intNoOfPageList, int intNoOfRecordInPage, int intStatus, String strPageLinkClick)
    {
        String strResult = "";
        if (intTotalRecord > 0)
        {
            StringBuilder objStringBuilder = new StringBuilder();

            int intSelectedPageNo = intPageNo;

            if (intStatus == 0)//if previous click
                intPageNo = intPageNo + intNoOfPageList - 1;

            //Find total of page
            int intTotalNoOfPage = intTotalRecord > intNoOfRecordInPage ? intTotalRecord / intNoOfRecordInPage : 1;
            int intRemainRecord = intTotalRecord > intNoOfRecordInPage ? intTotalRecord % intNoOfRecordInPage : 0;
            intTotalNoOfPage = intRemainRecord > 0 ? intTotalNoOfPage + 1 : intTotalNoOfPage;

            intNoOfPageList = intNoOfPageList > intTotalNoOfPage ? intTotalNoOfPage : intNoOfPageList;

            objStringBuilder.append("<div class=\"pageNav\">");

            int j = 0;
            if (intPageNo > intNoOfPageList)
                j += intPageNo - intNoOfPageList;

            if (intPageNo > intNoOfPageList)
            {
            	objStringBuilder.append("<a href=\"javascript:"+ strPageLinkClick +"("+ j +",0);\">< Prev</a> | ");
            }


            int n = 0;
            for (int i = 1; i <= intNoOfPageList; i++)
            {
                if (i + j == intSelectedPageNo)
                    objStringBuilder.append("<span>"+ (i+j) +"</span> | ");
                else
                    objStringBuilder.append("<a href=\"javascript:"+ strPageLinkClick +"("+ (i+j) +",1);\"><span>"+ (i+j) +"</span></a> | ");

                n = i + j + 1;
            }

            if (intTotalNoOfPage > intNoOfPageList && intPageNo < intTotalNoOfPage)
            {
                strResult = objStringBuilder.append("<a href=\"javascript:"+ strPageLinkClick +"("+ n +",2);\">Next ></a>").toString();
            }
            else
            {
                strResult = objStringBuilder.toString();
                strResult = strResult.substring(0, strResult.length() - 3);
            }

            strResult = strResult + "</div>";
        }

        return strResult;
    }
	
	public static String GetResultInfo(int intTotalRecord, int intPageNo, int intNoOfRecordInPage)
    {
        String strResult = "";
        if (intTotalRecord > 0)
        {
            int intFrom = ((intPageNo - 1) * intNoOfRecordInPage) + 1;
            int intTo = intPageNo * intNoOfRecordInPage > intTotalRecord ? intTotalRecord : intPageNo * intNoOfRecordInPage;

            strResult = "<div class=\"rightAlign result\"> Results " + intFrom + " - " + intTo + " of " + intTotalRecord + "</div>";
        }
        else
        {
            strResult = "<span class=\"red\"> No Record Found.</span>";
        }
        return strResult;
    }
	
}
