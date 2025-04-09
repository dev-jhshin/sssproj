package net.fullstack10.bbs;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class BbsCommentRegistController
 */
@WebServlet("/bbs/comment/delete.do")
public class BbsCommentDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO bbsDAO;
    private CommonUtil cUtil;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsCommentDeleteController() {
        super();
        cUtil = new CommonUtil();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String sessionMemberId = (String) request.getSession().getAttribute("memberId");
		String bbsIdx = request.getParameter("bbs_idx");
		String commentIdx = request.getParameter("comment_idx");
		String memberId = request.getParameter("comment_memberId");
		
		if (sessionMemberId == null || !(sessionMemberId.length() > 0)) { JSFunction.alertLocation(response, "로그인 세션이 만료되었습니다.", "/sssproj/auth/login.do"); }
		if (memberId == null || !(memberId.length() > 0)) { JSFunction.alertBack(response, "사용자 정보가 없습니다.");}
		if (!sessionMemberId.equalsIgnoreCase(memberId)) { JSFunction.alertBack(response, "사용자 정보가 일치하지 않습니다.");}
		
		if(commentIdx == null || !(cUtil.parseInt(commentIdx) > 0)) { JSFunction.alertBack(response, "댓글 정보가 올바르지 않습니다.");}

		bbsDAO = new BbsDAO();
		int result = bbsDAO.setBbsCommentDelete(commentIdx);
		bbsDAO.close();
		
		if (result > 0) {
			JSFunction.alertLocation(response, "댓글을 삭제하였습니다.","/sssproj/bbs/view.do?idx=" + bbsIdx);
		} else {
			JSFunction.alertLocation(response, "댓글 삭제에 실패했습니다.","/sssproj/bbs/view.do?idx=" + bbsIdx);
		}
	}

}
