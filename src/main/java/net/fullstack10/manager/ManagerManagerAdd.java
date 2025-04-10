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
 * Servlet implementation class ManagerManagerAdd
 */
@WebServlet(name = "manager/managerAdd.do", urlPatterns = { "/manager/managerAdd.do" })
public class ManagerManagerAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int managerStatus = (int)session.getAttribute("managerStatus");
		String managerId = request.getParameter("managerId");
		managerId = (managerId != null) ? managerId.trim() : "";
		String managerPwd = request.getParameter("managerPwd");
		managerPwd = (managerPwd != null) ? managerPwd.trim() : "";
		String managerName = request.getParameter("managerName");
		managerName = (managerName != null) ? managerName.trim() : "";
		String managerEmail = request.getParameter("managerEmail");
		managerEmail = (managerEmail != null) ? managerEmail.trim() : "";
		int managerStatus2 = Integer.parseInt(request.getParameter("managerStatus"));

		if(managerId == null || managerId.isEmpty() || !managerId.matches("^[a-z0-9]{5,20}$")) {
			JSFunction.alertLocation(response, "replace", "아이디는 5~20자의 영문 소문자, 숫자만 가능합니다.", "./managerList.do");
			return;
		}

		if(managerPwd == null || managerPwd.isEmpty() || !managerPwd.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$")) {
			JSFunction.alertLocation(response, "replace", "비밀번호는 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.", "./managerList.do");
		}

		if(managerName == null || managerName.isEmpty() || !managerName.matches("^[가-힣]{1,20}$")) {
		    JSFunction.alertLocation(response, "replace", "이름은 한글 1~20자여야 합니다.", "./managerList.do");
			return;
		}
		if(managerEmail == null || managerEmail.isEmpty() || !managerEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			JSFunction.alertLocation(response, "replace", "이메일 형식이 올바르지 않습니다.", "./managerList.do");
			return;
		}
		if( !(managerStatus2 == 1) && !(managerStatus2 == 2) && !(managerStatus2 ==3) ) {
			JSFunction.alertLocation(response, "replace", "직급이 올바르지 않습니다..", "./managerList.do");
			return;
		}

		if(managerStatus == 3) {
			ManagerDTO dto = new ManagerDTO();
			ManagerDAO dao = new ManagerDAO();
			dto.setManagerId(managerId);
			dto.setManagerPwd(managerPwd);
			dto.setManagerName(managerName);
			dto.setManagerEmail(managerEmail);
			dto.setManagerStatus(managerStatus2);

			int rs =dao.managerAdd(dto);
			dao.close();
			if(rs>0) {
				JSFunction.alertLocation(response, "replace", "관리자 추가 성공", "./managerList.do");
			}else {
				JSFunction.alertLocation(response, "replace", "관리자 추가 실패", "./managerList.do");
			}
		}else {
			JSFunction.alertLocation(response, "replace", "관리자 추가 권한이 없습니다.", "./managerList.do");
		}
	}
}