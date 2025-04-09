package net.fullstack10.learning;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class LearningCommentDeleteController
 */
@WebServlet("/learning/comment/delete.do")
public class LearningCommentDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CommonUtil cUtil = new CommonUtil();

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
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Object redirectURL = session.getAttribute("redirectURL");
		String url = "list.do";
		if (redirectURL != null) {
			url = redirectURL.toString();
		}

		String loginMemberId = (String)session.getAttribute("memberId");

		String memberId = request.getParameter("memberId");
		String learningIdx = request.getParameter("learningIdx");
		String commentIdx = request.getParameter("commentIdx");

		if (loginMemberId == null || loginMemberId.isBlank()) {
			JSFunction.alertLocation(response, "href", "로그인 세션이 만료되었습니다.", "/sssproj/auth/login.do");
		}
		if (memberId == null || memberId.isBlank()) {
			JSFunction.alertBack(response, "사용자 정보가 없습니다.");
		}
		if (!loginMemberId.equalsIgnoreCase(memberId)) {
			JSFunction.alertBack(response, "사용자 정보가 일치하지 않습니다.");
		}
		if (commentIdx == null || commentIdx.isBlank()) {
			JSFunction.alertBack(response, "댓글 정보가 올바르지 않습니다.");
		}

		LearningCommentDAO commentDAO = new LearningCommentDAO();
		int result = commentDAO.deleteLearningCommentByIdx(commentIdx);
		commentDAO.close();

		String msg = (result > 0 ? "댓글을 삭제했습니다." : "댓글 삭제에 실패했습니다.");
		JSFunction.alertLocation(response, "href", msg, "/sssproj/learning/view.do?idx=" + learningIdx);
	}

}
