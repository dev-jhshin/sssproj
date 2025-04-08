package net.fullstack10.manager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;

import java.io.IOException;

/**
 * Servlet implementation class ManagerLoginController
 */
@WebServlet("/manager/login.do")
public class ManagerLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/manager/adminLogin_page.jsp").forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 세션사용 선언
		HttpSession session = request.getSession();
		
		String managerId = request.getParameter("managerId");
		managerId = (managerId != null) ? managerId.trim() : "";
		String managerPwd = request.getParameter("managerPwd");
		managerPwd = (managerPwd != null) ? managerPwd.trim() : "";
		
		//ID, PWD 유효성검사 진행
		if(managerId == null || managerId.isEmpty() || !managerId.matches("^[a-z0-9]{5,20}$")) {
			System.out.println("error1");
			JSFunction.alertLocation(response, "replace", "아이디, 비밀번호를 확인해 주세요.", "./login.do");
			return;
		}

		if(managerPwd == null || managerPwd.isEmpty() || !managerPwd.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$")) {
			System.out.println("error2");
			JSFunction.alertLocation(response, "replace", "아이디, 비밀번호를 확인해 주세요.", "./login.do");
			return;
		}
		ManagerDTO dto = new ManagerDTO();
		ManagerDAO dao = new ManagerDAO();
		
		dto.setManagerId(managerId);
		dto.setManagerPwd(managerPwd);

		dto = dao.managerLogin(dto);
		dao.close();
		if(dto != null && dto.getManagerId() != null) {
			session.setAttribute("managerId", dto.getManagerId());
			session.setAttribute("managerStatus", dto.getManagerStatus());
			session.setMaxInactiveInterval(60*60);
			JSFunction.alertLocation(response, "replace", "로그인 성공.", "./memberList.do");
		}else {
			JSFunction.alertLocation(response, "replace", "로그인 실패.", "./login.do");
		}
	}
}
