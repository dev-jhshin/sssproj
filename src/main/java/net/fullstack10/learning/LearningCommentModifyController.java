package net.fullstack10.learning;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;
import net.fullstack10.validation.CommentValidationUtil;
import net.fullstack10.validation.ValidationUtil;

/**
 * Servlet implementation class LearningCommentModifyController
 */
@WebServlet("/learning/comment/modify.do")
public class LearningCommentModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommonUtil cUtil = new CommonUtil();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String loginMemberId = (String) request.getSession().getAttribute("memberId");
		String memberId = request.getParameter("memberId");
		String commentIdx = request.getParameter("commentIdx");
		String content = request.getParameter("commentEdit");
		String learningIdx = request.getParameter("learningIdx");
		
		if(!ValidationUtil.isLoggedIn(loginMemberId, response)) return;
		if(!ValidationUtil.hasValidMemberId(memberId, response)) return;
		if(!ValidationUtil.hasPermission(loginMemberId, memberId, response)) return;
		if(!CommentValidationUtil.isValidIdx(commentIdx, response)) return;
		
		LearningCommentDAO commentDAO = new LearningCommentDAO();
		int result = commentDAO.updateLearningComment(commentIdx, content);
		commentDAO.close();
		
		JSFunction.alertLocation(response, "", "/sssproj/learning/view.do?idx=" + learningIdx + "&isVisited=" + false  + "#frmComment" + commentIdx);
		// String msg = (result > 0 ? "댓글 수정이 완료되었습니다." : "댓글 수정에 실패했습니다.");
		// response.sendRedirect("/sssproj/learning/view.do?idx=" + learningIdx + "#frmComment" + commentIdx);
	}

}
