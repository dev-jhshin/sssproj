package net.fullstack10.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AuthIdDuplicationController
 */
@WebServlet("/auth/idDuplicate.do")
public class AuthIdDuplicateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("member_id");
		AuthDAO dao = new AuthDAO();
		int rs = dao.idDuplicate(memberId);
		if(rs == 1) {
			//아이디 사용가능
			request.setAttribute("duplicate", 1);
		}else {
			//아이디 중복
			request.setAttribute("duplicate", 0);
		}
		request.setAttribute("savedId", memberId);
		request.getRequestDispatcher("/WEB-INF/views/auth/regist_page.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
