package kredit.web.util.checker.util;

import java.lang.reflect.Type;
import java.util.List;

import kredit.web.util.checker.model.Checker;
import kredit.web.util.checker.model.ColumnProp;
import kredit.web.util.checker.model.ResultModel;
import kredit.web.util.checker.model.facade.CheckerFacade;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CheckerRenderer {

	public static String renderChecker(String brCd, Checker ch) {

		List<ResultModel> lstResult = CheckerFacade.getCheckerResult(brCd, ch);

		if (lstResult.size() == 0)
			return "";

		StringBuilder strBuilder = new StringBuilder();

		Gson gson = new Gson();
		Type listType = new TypeToken<List<ColumnProp>>() {
		}.getType();
		List<ColumnProp> lstCol = gson.fromJson(ch.getColProp(), listType);

		strBuilder
				.append("<table cellspacing=\"2px\" class=\"table-bordered table-striped\">");
		strBuilder.append("<thead><tr>");
		strBuilder.append("<th width=\"20px\">");
		strBuilder.append("N");
		strBuilder.append("</th>");
		for (ColumnProp colProp : lstCol) {
			strBuilder.append("<th width=\"").append(colProp.getW())
					.append("\">");
			strBuilder.append(colProp.getName());
			strBuilder.append("</th>");
		}
		strBuilder.append("</tr></thead>");

		strBuilder.append("<tbody>");
		int i = 0;
		
		for (ResultModel rs : lstResult) {
			int lSize = lstCol.size();
			strBuilder.append("<tr>");
			strBuilder.append("	<td width=\"60px\">");
			strBuilder.append(++i);
			strBuilder.append("	</td>");
			if (rs.getId() != 0) {
				strBuilder.append("	<td width=\"80px\">");
				strBuilder.append(rs.getId());
				strBuilder.append("	</td>");
			}else{
				lSize = lSize + 1;
			}
			strBuilder.append("	<td width=\"120px\">");
			strBuilder.append(rs.getC1());
			strBuilder.append("	</td>");

			if (lSize == 2) {
				strBuilder.append("</tr>");
				continue;
			}

			strBuilder.append("	<td width=\"160px\">");
			strBuilder.append(rs.getC2());
			strBuilder.append("	</td>");

			if (lSize == 3) {
				strBuilder.append("</tr>");
				continue;
			}

			strBuilder.append("	<td>");
			strBuilder.append(rs.getC3());
			strBuilder.append("	</td>");

			if (lSize == 4) {
				strBuilder.append("</tr>");
				continue;
			}

			strBuilder.append("	<td>");
			strBuilder.append(rs.getC4());
			strBuilder.append("	</td>");

			if (lSize == 5) {
				strBuilder.append("</tr>");
				continue;
			}
			
			strBuilder.append("	<td>");
			strBuilder.append(rs.getC5());
			strBuilder.append("	</td>");

			if (lSize == 6) {
				strBuilder.append("</tr>");
				continue;
			}
			
			strBuilder.append("	<td>");
			strBuilder.append(rs.getC6());
			strBuilder.append("	</td>");

			if (lSize == 7) {
				strBuilder.append("</tr>");
				continue;
			}
			
			strBuilder.append("	<td>");
			strBuilder.append(rs.getC7());
			strBuilder.append("	</td>");

			if (lSize == 8) {
				strBuilder.append("</tr>");
				continue;
			}
			
			strBuilder.append("	<td>");
			strBuilder.append(rs.getC8());
			strBuilder.append("	</td>");

			if (lSize == 9) {
				strBuilder.append("</tr>");
				continue;
			}

			strBuilder.append("	<td>");
			strBuilder.append(rs.getC9());
			strBuilder.append("	</td>");
			
			if (lSize == 10) {
				strBuilder.append("</tr>");
				continue;
			}

			strBuilder.append("	<td>");
			strBuilder.append(rs.getC10());
			strBuilder.append("	</td>");

			strBuilder.append("</tr>");
		}

		strBuilder.append("</tbody></table>");
		ch.setResult(strBuilder.toString());

		ch.setFound(i);

		return getMsgTemplate(ch);
	}

	private static String getMsgTemplate(Checker checker) {
		StringBuilder objStringBuilder = new StringBuilder();
		objStringBuilder
				.append("<div class=\"widget-box transparent\" style=\"opacity: 1;\">");
		objStringBuilder.append("    <div class=\"widget-header\">");
		objStringBuilder.append("		<p class=\"").append(checker.getCssClass())
				.append("\">");
		objStringBuilder.append("		<i class=\"z-icon-hand-o-right\"></i>");
		objStringBuilder.append(checker.getValidationMsg());
		objStringBuilder.append("        </p>");
		objStringBuilder
				.append("        <div class=\"widget-toolbar no-border\">");
		objStringBuilder
				.append("            <a data-action=\"collapse\" href=\"#\">");
		objStringBuilder.append("<small id=\"done" + checker.getId() + "\">");
		objStringBuilder.append(checker.getFound()).append(
				checker.getFound() > 1 ? " records found in "
						: " record found in ");
		objStringBuilder.append("</small>");
		objStringBuilder
				.append("            <i class=\"z-icon-chevron-up\"></i>");
		objStringBuilder.append("            </a>");
		objStringBuilder.append("        </div>");
		objStringBuilder.append("    </div>");
		objStringBuilder.append("    <div class=\"widget-body\">");
		objStringBuilder
				.append("        <div class=\"widget-body-inner\" style=\"display: block;\">");
		objStringBuilder
				.append("            <div class=\"\" style=\"display: block;\">");
		objStringBuilder
				.append("                <div class=\"\" style=\"display: block;\">");
		objStringBuilder
				.append("                    <div class=\"widget-main padding-6 no-padding-left no-padding-right\">");
		objStringBuilder.append(checker.getResult());
		objStringBuilder.append("                    </div>");
		objStringBuilder.append("                </div>");
		objStringBuilder.append("            </div>");
		objStringBuilder.append("        </div>");
		objStringBuilder.append("    </div>");
		objStringBuilder.append("</div>");
		return objStringBuilder.toString();
	}
}
