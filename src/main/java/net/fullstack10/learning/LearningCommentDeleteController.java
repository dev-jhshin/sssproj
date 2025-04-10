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
import net.fullstack10.validation.CommentValidationUtil;
import net.fullstack10.validation.ValidationUtil;

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
		
		if(!ValidationUtil.isLoggedIn(loginMemberId, response)) return;
		if(!ValidationUtil.hasValidMemberId(memberId, response)) return;
		if(!ValidationUtil.hasPermission(loginMemberId, memberId, response)) return;
		if(!CommentValidationUtil.isValidIdx(commentIdx, response)) return;

		LearningCommentDAO commentDAO = new LearningCommentDAO();
		int result = commentDAO.deleteLearningCommentByIdx(commentIdx);
		commentDAO.close();

		String msg = (result > 0 ? "댓글을 삭제했습니다." : "댓글 삭제에 실패했습니다.");
		JSFunction.alertLocation(response, "", "/sssproj/learning/view.do?idx=" + learningIdx + "&isVisited=" + false);
	}

}
