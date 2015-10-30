package kredit.web.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kredit.web.util.checker.model.Checker;
import kredit.web.util.checker.util.CheckerRenderer;

import com.avaje.ebean.Ebean;

/**
 * Servlet implementation class CommunityServlet
 */
@WebServlet(description = "Community Servlet", urlPatterns = { "/CommunityServlet" })
public class GeneralProcess1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DO_KCHECKER = 1;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GeneralProcess1() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final int procId = Integer.parseInt(request.getParameter("proc_id"));

		System.out.println("ProcID: " + procId);

		switch (procId) {
		case DO_KCHECKER:
			response.setCharacterEncoding("UTF-8");
			onChecker(request, response);
			break;
		}
	}

	public void onChecker(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int checkerID = Integer.parseInt(request.getParameter("chk_id"));
		String brCd = request.getParameter("br_cd");
		Checker chk = Ebean.find(Checker.class, checkerID);
		response.getWriter().append(CheckerRenderer.renderChecker(brCd, chk));
	}

}
