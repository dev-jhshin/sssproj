package net.fullstack10.bbs;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;

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
		PrintWriter wrt = response.getWriter();
		// 세션에서 받아오기 
		String sessionMemberId = (String) request.getSession().getAttribute("memberId");
		String bbsIdx = request.getParameter("bbs_idx");
		String commentIdx = request.getParameter("comment_idx");
		String memberId = request.getParameter("memberId");
		
		if(sessionMemberId == null || !(sessionMemberId.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('로그인 세션이 만료되었습니다.');");
			wrt.println("window.location.href='/sssproj/auth/login.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if(memberId == null || !(memberId.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 없습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if (!sessionMemberId.equalsIgnoreCase(memberId)) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 일치하지 않습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		
		if(commentIdx == null || !(cUtil.parseInt(commentIdx) > 0)) {
			wrt.println("<script>");
			wrt.println("alert('댓글 정보가 올바르지 않습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		bbsDAO = new BbsDAO();
		int result = bbsDAO.setBbsCommentDelete(commentIdx);
		bbsDAO.close();
		if (result > 0) {
			wrt.println("<script>");
			wrt.println("alert('댓글을 삭제하였습니다.');");
			wrt.println("window.location.href = '/sssproj/bbs/view.do?idx=" + bbsIdx + "'");
			wrt.println("</script>");
			wrt.close();
			return;
		} else {
			wrt.println("<script>");
			wrt.println("alert('댓글 삭제에 실패했습니다.');");
			wrt.println("window.location.href = '/sssproj/bbs/view.do?idx=" + bbsIdx + "'");
			wrt.println("</script>");
			wrt.close();
			return;
		}
	}

}
