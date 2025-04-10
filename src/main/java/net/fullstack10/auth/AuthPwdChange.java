package net.fullstack10.auth;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.fullstack10.common.JSFunction;

/**
 * Servlet implementation class AuthPwdChange
 */
@WebServlet("/auth/pwdChange")
public class AuthPwdChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/auth/change_pw_page.jsp").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPwd = request.getParameter("memberPwd");
		memberPwd = (memberPwd != null) ? memberPwd.trim() : "";

		if(memberPwd == null || memberPwd.isEmpty() || !memberPwd.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,16}$")) {
			request.setAttribute("error2", "비밀번호는 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.");
			JSFunction.alertLocation(response, "replace", "비밀번호는 8~16자이며, 영문, 숫자, 특수문자를 포함해야 합니다.", "./memberVerification.do");
		}
		AuthDAO dao = new AuthDAO();
		int rs = dao.chagePwd(memberId, memberPwd);
		if(rs>0) {
			JSFunction.alertLocation(response, "replace", "비밀번호 변경완료. 변경된 비밀번호로 로그인해주세요.", "./login.do");
		}else {
			JSFunction.alertLocation(response, "replace", "비밀번호 변경실패. 다시한번 입력 부탁드립니다..", "./memberVerification.do");
		}
	}

}
