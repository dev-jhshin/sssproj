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
 * Servlet implementation class ManagerMemberChange
 */
@WebServlet("/manager/memberStatusChange.do")
public class ManagerMemberChange extends HttpServlet {
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
			String memberStatus = request.getParameter("member_status");

			System.out.println(managerId);
			System.out.println(managerStatus);
			System.out.println(memberId);
			System.out.println(memberStatus);

			ManagerDAO dao = new ManagerDAO();
			if(managerId != null && managerStatus >= 2) {
				if(memberStatus.equals("1")) {
					int rs = dao.memberChange1(memberId);
					dao.close();
					if(rs > 0) {
						JSFunction.alertLocation(response, "replace", "회원 상태 변경 되었습니다.(1->2)", "./memberList.do");
					}else {
						JSFunction.alertLocation(response, "replace", "회원 상태 변경 오류(1->2)", "./memberList.do");
					}
				}else if(memberStatus.equals("2")) {
					int rs = dao.memberChange2(memberId);
					dao.close();
					if(rs > 0) {
						JSFunction.alertLocation(response, "replace", "회원 상태 변경 되었습니다.(2->1)", "./memberList.do");
					}else {
						JSFunction.alertLocation(response, "replace", "회원 상태 변경 오류(2->1)", "./memberList.do");
					}
				}else {
					JSFunction.alertLocation(response, "replace", "회원 상태 이상합니다.", "./memberList.do");
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
