package net.fullstack10.bbs;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.CommonUtil;

/**
 * Servlet implementation class BbsCommentRegistController
 */
@WebServlet("/bbs/comment/modify.do")
public class BbsCommentModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BbsDAO bbsDAO;
    private CommonUtil cUtil;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsCommentModifyController() {
        super();
        cUtil = new CommonUtil();
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
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		String content = request.getParameter("comment_content");
		String commentIdx = request.getParameter("comment_idx");
		String commentMemberId = request.getParameter("comment_memberId");
		System.out.println("bbs comment modify memberId:"+memberId);
		System.out.println("bbs comment modify commentMemberId:"+commentMemberId);
		String bbsIdx = request.getParameter("bbs_idx");
		if(memberId == null || !(memberId.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 없습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if(!memberId.equalsIgnoreCase(commentMemberId)) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 다릅니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if(content == null || !(content.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('내용을 입력해주세요.');");
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

		int result = bbsDAO.setBbsCommentModify(commentIdx, content);
		bbsDAO.close();
		
		if (result > 0) {
			wrt.println("<script>");
			wrt.println("alert('댓글 수정이 완료되었습니다.');");
			wrt.println("window.location.href = '/sssproj/bbs/view.do?idx=" + bbsIdx + "'");
			wrt.println("</script>");
			wrt.close();
			return;
		} else {
			wrt.println("<script>");
			wrt.println("alert('댓글 수정에 실패했습니다.');");
			wrt.println("window.location.href = '/sssproj/bbs/view.do?idx=" + bbsIdx + "'");
			wrt.println("</script>");
			wrt.close();
			return;
		}
	}

}
