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
import net.fullstack10.validation.LearningValidationUtil;
import net.fullstack10.validation.ValidationUtil;

/**
 * Servlet implementation class LearningCommentController
 */
@WebServlet("/learning/comment/regist.do")
public class LearningCommentRegistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommonUtil cUtil = new CommonUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String loginMemberId = (String)session.getAttribute("memberId");
		
		String learningIdx = request.getParameter("learningIdx");
		String content = request.getParameter("commentContent");
		
		if(!ValidationUtil.isLoggedIn(loginMemberId, response)) return;
		if(!CommentValidationUtil.isValidIdx(learningIdx, response)) return;
		if(!LearningValidationUtil.isValidContent(content, response)) return;
	
		LearningCommentDAO commentDAO = new LearningCommentDAO();
		int commentIdx = commentDAO.createLearningComment(learningIdx, loginMemberId, content);
		commentDAO.close();
		
		String url = "/sssproj/learning/view.do?idx=" + learningIdx 
				+ (commentIdx > 0 ? "#frmComment" + commentIdx : "#frmCommentReigst");
		JSFunction.alertLocation(response, "", "/sssproj/learning/view.do?idx=" + learningIdx + "&isVisited=" + false  + "#frmComment" + commentIdx);
		// String msg = ( result > 0 ? "댓글 등록이 완료되었습니다." : "댓글 등록에 실패했습니다.");
		// JSFunction.alertLocation(response, "href", msg, "/sssproj/learning/view.do?idx=" + learningIdx);
	}

}
