package net.fullstack10.support;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.CommonUtil;

/**
 * Servlet implementation class SupportQuestionDeleteController
 */
@WebServlet("/support/question/delete.do")
public class SupportQuestionDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SupportDAO supportDAO;
    private CommonUtil cUtil = new CommonUtil();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupportQuestionDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		PrintWriter wrt = response.getWriter();

		String idx = request.getParameter("inquiryIdx");
		String sMemberId = (String) request.getSession().getAttribute("memberId");
		String memberId = request.getParameter("memberId");

		// 밸리데이션 체크 로직
		if(sMemberId == null || sMemberId.length() < 1 || !sMemberId.equalsIgnoreCase(memberId)) {
			wrt.println("<script>");
			wrt.println("alert('사용자 정보가 일치하지 않습니다.');");
			wrt.println("window.location.href='/sssproj/auth/login.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if(cUtil.parseInt(idx) < 1) {
			wrt.println("<script>");
			wrt.println("alert('글을 찾지 못했습니다.');");
			wrt.println("window.location.href='/sssproj/support/question/list.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		supportDAO = new SupportDAO();
		int result = supportDAO.setInquiryDelete(idx);
		supportDAO.close();
		if(result>0) {
			wrt.println("<script>");
			wrt.println("alert('문의 글을 삭제했습니다.');");
			wrt.println("window.location.href='/sssproj/support/question/list.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		} else {
			wrt.println("<script>");
			wrt.println("alert('문의 글을 삭제하지 못했습니다.');");
			wrt.println("window.location.href='/sssproj/support/question/list.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		}

	}

}
