package net.fullstack10.learning;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

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
		String content = request.getParameter("commentContent");
		String learningIdx = request.getParameter("learningIdx");
		
		if(loginMemberId == null || loginMemberId.isBlank()) {
			JSFunction.alertLocation(response, "로그인 세션이 만료되었습니다.", "/sssproj/auth/login.do");
			return;
		}
		if (memberId == null || memberId.isBlank()) {
			JSFunction.alertBack(response, "사용자 정보가 없습니다.");
			return;
		}
		if (!loginMemberId.equalsIgnoreCase(memberId)) {
			JSFunction.alertBack(response, "사용자 정보가 일치하지 않습니다.");
			return;
		}
		if (commentIdx == null || commentIdx.isBlank()) {
			JSFunction.alertBack(response, "댓글 정보가 올바르지 않습니다.");
			return;
		}
		
		LearningCommentDAO commentDAO = new LearningCommentDAO();
		int result = commentDAO.updateLearningComment(commentIdx, content);
		commentDAO.close();
		
		response.sendRedirect("/sssproj/learning/view.do?idx=" + learningIdx + "#frmComment" + commentIdx);
		// String msg = (result > 0 ? "댓글 수정이 완료되었습니다." : "댓글 수정에 실패했습니다.");
		// response.sendRedirect("/sssproj/learning/view.do?idx=" + learningIdx + "#frmComment" + commentIdx);
	}

}
