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
 * Servlet implementation class ManagerMemberDeleteController
 */
@WebServlet("/manager/memberDelete.do")
public class ManagerMemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String managerId = (String)session.getAttribute("managerId");
		int managerStatus = (int)session.getAttribute("managerStatus");
		String memberId = request.getParameter("member_id");

		ManagerDAO dao = new ManagerDAO();

		if(managerId != null && managerStatus == 3) {
			int rs = dao.memberDelete(memberId);
			dao.close();
			if(rs > 0) {
				JSFunction.alertLocation(response, "replace", "회원이 삭제되었습니다.", "./memberList.do");
			}else {
				JSFunction.alertLocation(response, "replace", "회원이 삭제 오류", "./memberList.do");
			}
		}else {
			 JSFunction.alertLocation(response, "replace", "권한이 없습니다.", "./memberList.do");
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
