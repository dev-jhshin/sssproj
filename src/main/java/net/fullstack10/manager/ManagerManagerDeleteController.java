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
 * Servlet implementation class ManagerManagerDeleteController
 */
@WebServlet(name = "manager/managerDelete.do", urlPatterns = { "/manager/managerDelete.do" })
public class ManagerManagerDeleteController extends HttpServlet {
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
		int CmanagerStatus =Integer.parseInt(request.getParameter("manager_status"));

		if(managerId != null && managerStatus == 3) {
			if(CmanagerStatus < 3 ) {
				ManagerDAO dao = new ManagerDAO();
				int rs = dao.managerDelete(CmanagerId);
				dao.close();
				if(rs > 0) {
					JSFunction.alertLocation(response, "replace", "관리자가 삭제되었습니다.", "./managerList.do");
				}else {
					JSFunction.alertLocation(response, "replace", "관리자 삭제 오류", "./managerList.do");
				}
			}else {
				JSFunction.alertLocation(response, "replace", "슈퍼관리자는 삭제를 못합니다.", "./managerList.do");
			}
		}
		else {
			 JSFunction.alertLocation(response, "replace", "권한이 없습니다.", "./managerList.do");
		}
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
