package net.fullstack10.support;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class SupportQuestionRegistController
 */
@WebServlet("/support/question/regist.do")
public class SupportQuestionRegistController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SupportDAO supportDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SupportQuestionRegistController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter wrt = response.getWriter();
		
		String sMemberId = (String) request.getSession().getAttribute("memberId");
		if (sMemberId == null || sMemberId.length() < 1) {
			wrt.println("<script>");
			wrt.println("alert('로그인 해주세요.');");
			wrt.println("window.location.href='/sssproj/auth/login.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		request.getRequestDispatcher("/WEB-INF/views/support/supMyQuestionRegist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter wrt = response.getWriter();
		
		String memberId = (String) request.getSession().getAttribute("memberId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		// 밸리데이션 체크 로직
		if (memberId == null || !(memberId.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('로그인 세션이 만료되었습니다.');");
			wrt.println("window.location.href='/sssproj/auth/login.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if (title == null || !(title.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('제목을 입력하세요.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		if (content == null || !(content.length() > 0)) {
			wrt.println("<script>");
			wrt.println("alert('내용을 입력하세요.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		
		supportDAO = new SupportDAO();
		int result = supportDAO.setInquiryRegist(memberId, title, content);
		supportDAO.close();
		if (result > 0) {
			wrt.println("<script>");
			wrt.println("alert('문의 글 작성이 완료되었습니다.');");
			wrt.println("window.location.href='/sssproj/support/question/list.do'");
			wrt.println("</script>");
			wrt.close();
			return;
		} else {
			wrt.println("<script>");
			wrt.println("alert('문의 글 작성에 실패했습니다.');");
			wrt.println("history.back();");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		
	}

}
