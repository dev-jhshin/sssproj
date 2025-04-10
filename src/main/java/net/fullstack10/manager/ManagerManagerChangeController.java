package net.fullstack10.manager;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class ManagerManagerChangeController
 */
@WebServlet(name = "manager/managerStatusChange.do", urlPatterns = { "/manager/managerStatusChange.do" })
public class ManagerManagerChangeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String managerId = (String)session.getAttribute("managerId");
		int managerStatus = (int)session.getAttribute("managerStatus");
		String CmanagerId = request.getParameter("manager_id");
		String CmanagerStatus = request.getParameter("manager_status");


		if(managerId != null && managerStatus > 2) {
			if(CmanagerStatus.equals("1")) {
				ManagerDAO dao = new ManagerDAO();
				int rs = dao.managerChange1(CmanagerId);
				dao.close();
				if(rs > 0) {
					JSFunction.alertLocation(response, "replace", "관리자 직급 변경 되었습니다.(1->2)", "./managerList.do");
				}else {
					JSFunction.alertLocation(response, "replace", "관리자 직급 변경 오류(1->2)", "./managerList.do");
				}
			}else if(CmanagerStatus.equals("2")) {
				ManagerDAO dao = new ManagerDAO();
				int rs = dao.managerChange2(CmanagerId);
				dao.close();
				if(rs > 0) {
					JSFunction.alertLocation(response, "replace", "관리자 직급 변경 되었습니다.(2->1)", "./managerList.do");
				}else {
					JSFunction.alertLocation(response, "replace", "관리자 직급 변경 오류(2->1)", "./managerList.do");
				}
			}else {
				JSFunction.alertLocation(response, "replace", "관리자 상태 이상합니다.", "./managerList.do");
			}
		}else {
			 JSFunction.alertLocation(response, "replace", "권한이 없습니다.", "./managerList.do");
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
