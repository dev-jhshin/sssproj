package net.fullstack10.manager;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.DBConnPool;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class ManagerLogoutController
 */
@WebServlet("/manager/logout.do")
public class ManagerLogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		String managerId = (String)session.getAttribute("managerId");
		session.removeAttribute(managerId);
		DBConnPool db = new DBConnPool();
		db.close();
		JSFunction.alertLocation(response, "replace", "로그아웃 되었습니다.", "./login.do");
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
