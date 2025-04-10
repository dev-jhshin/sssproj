package net.fullstack10.auth;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AuthMyInfoController
 */
@WebServlet("/auth/myinfo.do")
public class AuthMyInfoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AuthDAO authDAO;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		authDAO = new AuthDAO();
		String sMemberId = (String) request.getSession().getAttribute("memberId");
		if (sMemberId == null || sMemberId.length() < 1) {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter wrt = response.getWriter();
			wrt.println("<script>");
			wrt.println("alert('회원 정보가 없습니다.');");
			wrt.println("window.location.href='/sssproj/auth/login.do';");
			wrt.println("</script>");
			wrt.close();
			return;
		}
		AuthDTO dto = authDAO.getMemberInfo(sMemberId);
		request.setAttribute("member", dto);

		request.getRequestDispatcher("/WEB-INF/views/auth/myinfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
