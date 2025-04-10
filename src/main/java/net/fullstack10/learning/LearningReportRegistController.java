package net.fullstack10.learning;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;
import net.fullstack10.validation.ReportValidationUtil;
import net.fullstack10.validation.ValidationUtil;

/**
 * Servlet implementation class LearningReportRegistController
 */
@WebServlet("/learning/report/regist.do")
public class LearningReportRegistController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object redirectURL = session.getAttribute("redirectURL");
		String url = "list.do";
		if (redirectURL != null) {
			url = redirectURL.toString();
			session.removeAttribute("redirectURL");
		}

		String loginMemberId = (String) session.getAttribute("memberId");

		String idx = request.getParameter("idx");
		String content = request.getParameter("content");
		
		if(!ValidationUtil.isLoggedIn(loginMemberId, response)) return;
		if(!ValidationUtil.isValidIdx(idx, response)) return;
		if(!ReportValidationUtil.isValidContent(content, response)) return;
		
		LearningDAO learningDAO = new LearningDAO();
		int result = learningDAO.createLearningReport(loginMemberId, idx, content);
		learningDAO.close();

		String msg = (result > 0 ? "신고 처리가 완료되었습니다." : "신고 접수에 실패했습니다.");
		JSFunction.alertLocation(response, "href", msg, url);
	}

}
