package net.fullstack10.learning;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

import java.io.IOException;

/**
 * Servlet implementation class LearningLikeDeleteController
 */
@WebServlet("/learning/like/delete.do")
public class LearningLikeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommonUtil cUtil = new CommonUtil();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		
		String idx = request.getParameter("idx");
		
		if (memberId == null || memberId.isBlank()) {
			JSFunction.alertBack(response, "잘못된 접근입니다.");
			return;
		}
		if (cUtil.parseInt(idx) < 1) {
			JSFunction.alertBack(response, "게시글 정보가 올바르지 않습니다.");
			return;
		}
		
		LearningLikeDAO likeDAO = new LearningLikeDAO();
		int result = likeDAO.deleteLearningLikeByMemberId(idx, memberId);
		likeDAO.close();
		
		// String msg = (result > 0 ? "좋아요 취소를 성공했습니다." : "좋아요 취소를 실패했습니다.");
		// JSFunction.alertLocation(response, "href", msg, "/sssproj/learning/view.do?idx=" + idx);
		JSFunction.alertLocation(response, "", "/sssproj/learning/view.do?idx=" + idx + "&isVisited=" + false);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
